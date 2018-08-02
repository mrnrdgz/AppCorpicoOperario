package ar.com.corpico.appcorpico.orders.data.estadoOperativo;

import android.content.ContentProviderOperation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestEstadoOperativo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class EstadosOperativoRepository implements IEstadosOperativoRepository {
    private static EstadosOperativoRepository etapasRepository;

    // Relaciones de composición
    private EstadoOperativoRestStore mEstadoOperativoRestStore;
    private EstadoOperativoSqliteStore mEstadoOperativoSqliteStore;
    private SessionsPrefsStore mSessionPrefStore;


    private EstadosOperativoRepository(EstadoOperativoRestStore estadoOperativoRestStore, EstadoOperativoSqliteStore estadoOperativoSqliteStore, SessionsPrefsStore sessionPrefStore) {
        mEstadoOperativoRestStore = Preconditions.checkNotNull(estadoOperativoRestStore,
                "La fuente de datos rest de cuadrillas no puede ser null");
        mEstadoOperativoSqliteStore = Preconditions.checkNotNull(estadoOperativoSqliteStore);
        mSessionPrefStore = Preconditions.checkNotNull(sessionPrefStore);

    }

    public static EstadosOperativoRepository getInstance(EstadoOperativoRestStore estadoOperativoRestStore, EstadoOperativoSqliteStore estadoOperativoSqliteStore, SessionsPrefsStore sessionPrefStore) {
        if (etapasRepository == null) {
            etapasRepository = new EstadosOperativoRepository(estadoOperativoRestStore, estadoOperativoSqliteStore, sessionPrefStore);
        }
        return etapasRepository;
    }

    @Override
    public void query(final EstadoOperativoRepositoryCallBack callback, Criteria filter) {

        mEstadoOperativoSqliteStore.getEstadoOperativo(new EstadoOperativoStore.GetEstadoOperativoStoreCallBack() {
            @Override
            //public void onSuccess(List<Tipo_Cuadrilla> tipoCuadrilla) {
            public void onSuccess(List<RestEstadoOperativo> estadoOperativo) {
                callback.onSuccess(estadoOperativo);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }, filter);
    }

    @Override
    public List<RestEstadoOperativo> fetchDataEstadoOperativoIfNewer() {
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
        Call<List<RestEstadoOperativo>> getestadoOperativo = service.GetEstadoOperativo(Integer.parseInt(mSessionPrefStore.getServicio()), new DateTime(mSessionPrefStore.getUltimaSync()));
        //Call<List<RestTipoCuadrilla>> gettipocuadrilla = service.GetTipoCuadrilla(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()),new DateTime(mSessionPrefStore.getUltimaSync()));
        List<RestEstadoOperativo> estadooperativo = new ArrayList<>();
        try {
            estadooperativo = getestadoOperativo.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return estadooperativo;
    }

    public List<ContentProviderOperation> providerOperations(List<RestEstadoOperativo> estadoOperativos) {
        return mEstadoOperativoSqliteStore.replicarServidor(estadoOperativos);
    }
}
