package ar.com.corpico.appcorpico.orders.data.foto;

import android.content.ContentProviderOperation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestFoto;
import ar.com.corpico.appcorpico.orderdetailimages.FotoOrden;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class FotoRepository implements IFotosRepository {
    private static FotoRepository fotorepository = null;

    // Relaciones de composición
    private FotoRestStore mFotoRestStore;
    private FotoSqliteStore mFotoSqliteStore;
    private SessionsPrefsStore mSessionPrefStore;


    private FotoRepository(FotoRestStore fotosRestStore, FotoSqliteStore fotoSqliteStore, SessionsPrefsStore sessionPrefStore) {
        mFotoRestStore = Preconditions.checkNotNull(fotosRestStore,
                "La fuente de datos rest de zonas no puede ser null");
        mFotoSqliteStore = Preconditions.checkNotNull(fotoSqliteStore);
        mSessionPrefStore = Preconditions.checkNotNull(sessionPrefStore);

    }

    public static FotoRepository getInstance(FotoRestStore fotosRestStore, FotoSqliteStore fotoSqliteStore, SessionsPrefsStore sessionPrefStore) {
        if (fotorepository == null) {
            fotorepository = new FotoRepository(fotosRestStore, fotoSqliteStore,sessionPrefStore);
        }
        return fotorepository;
    }

    @Override
    public void query(final FotoRepositoryCallBack callback) {
        mFotoSqliteStore.getFoto(
                new FotoStore.GetFotoStoreCallBack() {
                    @Override
                    public void onSuccess(List<FotoOrden> foto) {
                           //List<Zona> zona = new ArrayList<>();

                            callback.onSuccess(foto);
                    }

                    @Override
                    public void onError(String error) {
                        callback.onError(error);
                    }
                }
        );
    }

    @Override
    public List<RestFoto> fetchDataFotoIfNewer() {

    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .build();

    Retrofit retrofit = new Retrofit.Builder()
            //.baseUrl("http://192.178.1.203:1052/")
            .baseUrl(ApiServiceOrders.URL_BASE)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ApiServiceOrders service = retrofit.create(ApiServiceOrders.class);
    Call<List<RestFoto>> getfoto = service.GetFoto(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()), new DateTime(mSessionPrefStore.getUltimaSync()));
    List<RestFoto> foto = new ArrayList<>();
        try {
            foto = getfoto.execute().body();
    } catch (IOException e) {
        e.printStackTrace();
    }

        return foto;
}

    public List<ContentProviderOperation> providerOperations(List<RestFoto> fotos) {
        return mFotoSqliteStore.replicarServidor(fotos);
    }
}

