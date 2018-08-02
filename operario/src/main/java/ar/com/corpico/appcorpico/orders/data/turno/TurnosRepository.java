package ar.com.corpico.appcorpico.orders.data.turno;

import android.content.ContentProviderOperation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTurnos;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class TurnosRepository implements ITurnosRepository {
    private static TurnosRepository turnosRepository;

    // Relaciones de composición
    private TurnoRestStore mTurnoRestStore;
    private TurnoSqliteStore mTurnoSqliteStore;
    private SessionsPrefsStore mSessionPrefStore;


    private TurnosRepository(TurnoRestStore turnoRestStore, TurnoSqliteStore turnoSqliteStore, SessionsPrefsStore sessionPrefStore) {
        mTurnoRestStore = Preconditions.checkNotNull(turnoRestStore,
                "La fuente de datos rest de Tipo Usuario no puede ser null");
        mTurnoSqliteStore = Preconditions.checkNotNull(turnoSqliteStore);
        mSessionPrefStore = Preconditions.checkNotNull(sessionPrefStore);

    }

    public static TurnosRepository getInstance(TurnoRestStore turnoRestStore, TurnoSqliteStore turnoSqliteStore, SessionsPrefsStore sessionPrefStore) {
        if (turnosRepository == null) {
            turnosRepository = new TurnosRepository(turnoRestStore, turnoSqliteStore, sessionPrefStore);
        }
        return turnosRepository;
    }

    @Override
    public void query(final TurnoRepositoryCallBack callback, Criteria filter) {

        mTurnoSqliteStore.getTurno(new TurnoStore.GetTurnoStoreCallBack() {
            @Override
            public void onSuccess(List<RestTurnos> turno) {
                callback.onSuccess(turno);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }, filter);
    }

    @Override
    public List<RestTurnos> fetchDataTurnoIfNewer() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        /*OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http://192.178.1.203:1052/")
                .baseUrl(ApiServiceOrders.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();*/
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
        Call<List<RestTurnos>> getturno = service.GetTurno(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()),new DateTime("2000-01-01"));
        //Call<List<RestTipoCuadrilla>> gettipocuadrilla = service.GetTipoCuadrilla(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()),new DateTime(mSessionPrefStore.getUltimaSync()));
        List<RestTurnos> turno = new ArrayList<>();
        try {
            turno = getturno.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return turno;
    }

    public List<ContentProviderOperation> providerOperations(List<RestTurnos> turnos) {
        return mTurnoSqliteStore.replicarServidor(turnos);
    }
}
