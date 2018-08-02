package ar.com.corpico.appcorpico.notes.data;

import android.content.ContentProviderOperation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.notes.data.entity.RestNotes;
import ar.com.corpico.appcorpico.notes.domain.entity.Note;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class NotesRepository implements INotesRepository {
    private static NotesRepository notasRepository;

    // Relaciones de composición
    private NoteRestStore mNoteRestStore;
    private NoteSqliteStore mNoteSqliteStore;
    private SessionsPrefsStore mSessionPrefStore;


    private NotesRepository(NoteRestStore noteRestStore,
                            NoteSqliteStore noteSqliteStore,
                            SessionsPrefsStore sessionPrefStore) {
        mNoteRestStore = Preconditions.checkNotNull(noteRestStore,
                "La fuente de datos rest de Estados no puede ser null");
        mNoteSqliteStore = Preconditions.checkNotNull(noteSqliteStore,"La fuente de datos sqlite de Estados no puede ser null");
        mSessionPrefStore = Preconditions.checkNotNull(sessionPrefStore);

    }

    public static NotesRepository getInstance(NoteRestStore noteRestStore,
                                              NoteSqliteStore noteSqliteStore,
                                              SessionsPrefsStore sessionPrefStore) {
        if (notasRepository == null) {
            notasRepository = new NotesRepository(noteRestStore, noteSqliteStore, sessionPrefStore);
        }
        return notasRepository;
    }

    @Override
    public void query(final NotesRepositoryCallBack callback,int servicio, int sector) {

        mNoteSqliteStore.getNotes(new NoteStore.GetNoteStoreCallBack() {
            @Override
            public void onSuccess(List<Note> notes) {
                callback.onSuccess(notes);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        },servicio, sector);
    }

    @Override
    public void add(final AddNotesRepositoryCallBack callback, String nota) {
        mNoteSqliteStore.addNote(new NoteStore.AddNoteStoreCallBack() {
            @Override
            public void onSuccess(String satisfaction) {

                callback.onSuccess(satisfaction);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        },nota);
        // mNoteServerStore.addNote();
    }

    @Override
    public void edit(final EditNotesRepositoryCallBack callback, int id, String nota) {
        mNoteSqliteStore.editNote(new NoteStore.EditNoteStoreCallBack() {
            @Override
            public void onSuccess(String satisfaction) {

                callback.onSuccess(satisfaction);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        },id,nota);
    }

    @Override
    public List<RestNotes> fetchDataNotesIfNewer() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http://192.178.1.203:1052/")
                .baseUrl(ApiServiceOrders.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        ApiServiceOrders service = retrofit.create(ApiServiceOrders.class);

        //Call<List<RestTipoCuadrilla>> gettipocuadrilla = service.GetTipoCuadrilla(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()), new DateTime("2017-12-29"));
        Call<List<RestNotes>> getnote = service.GetNote(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()), new DateTime(mSessionPrefStore.getUltimaSync()));
        List<RestNotes> note = new ArrayList<>();
        try {
            note = getnote.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return note;
    }

    public List<ContentProviderOperation> providerOperations(List<RestNotes> notes) {
        return mNoteSqliteStore.replicarServidor(notes);
    }
}
