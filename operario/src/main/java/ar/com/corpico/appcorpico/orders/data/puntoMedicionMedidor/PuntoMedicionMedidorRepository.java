package ar.com.corpico.appcorpico.orders.data.puntoMedicionMedidor;

import android.content.ContentProviderOperation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestPuntoMedicionMedidor;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class PuntoMedicionMedidorRepository implements IPuntoMedicionMedidorRepository {
    private static PuntoMedicionMedidorRepository puntoMedicionMedidorRepository;

    // Relaciones de composición
    private PuntoMedicionMedidorRestStore mPuntoMedicionMedidorRestStore;
    private PuntoMedicionMedidorSqliteStore mPuntoMedicionMedidorSqliteStore;
    private SessionsPrefsStore mSessionPrefStore;


    private PuntoMedicionMedidorRepository(PuntoMedicionMedidorRestStore puntoMedicionMedidorRestStore, PuntoMedicionMedidorSqliteStore puntoMedicionMedidorSqliteStore, SessionsPrefsStore sessionPrefStore) {
        mPuntoMedicionMedidorRestStore = Preconditions.checkNotNull(puntoMedicionMedidorRestStore,
                "La fuente de datos rest de Punto Medicion Medidor no puede ser null");
        mPuntoMedicionMedidorSqliteStore = Preconditions.checkNotNull(puntoMedicionMedidorSqliteStore);
        mSessionPrefStore = Preconditions.checkNotNull(sessionPrefStore);

    }

    public static PuntoMedicionMedidorRepository getInstance(PuntoMedicionMedidorRestStore puntoMedicionMedidorRestStore, PuntoMedicionMedidorSqliteStore puntoMedicionMedidorSqliteStore, SessionsPrefsStore sessionPrefStore) {
        if (puntoMedicionMedidorRepository == null) {
            puntoMedicionMedidorRepository = new PuntoMedicionMedidorRepository(puntoMedicionMedidorRestStore, puntoMedicionMedidorSqliteStore, sessionPrefStore);
        }
        return puntoMedicionMedidorRepository;
    }

    @Override
    public void query(final PuntoMedicionMedidorRepositoryCallBack callback, Criteria filter) {

        mPuntoMedicionMedidorSqliteStore.getPuntoMedicionMedidor(new PuntoMedicionMedidorStore.GetPuntoMedicionMedidorStoreCallBack() {
            @Override
            public void onSuccess(List<RestPuntoMedicionMedidor> puntoMedicionMedidores) {
                callback.onSuccess(puntoMedicionMedidores);
            }
            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }, filter);
    }

    @Override
    public List<RestPuntoMedicionMedidor> fetchDataPuntoMedicionMedidorIfNewer() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        /*OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http://192.178.1.203:1052/")
                .baseUrl("http://192.168.1.34:1052/")
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
        Call<List<RestPuntoMedicionMedidor>> getPuntoMedicionMedidor = service.GetPuntoMedicionMedidor(Integer.parseInt(mSessionPrefStore.getServicio()),new DateTime(mSessionPrefStore.getUltimaSync()));
        //Call<List<RestTipoCuadrilla>> gettipocuadrilla = service.GetTipoCuadrilla(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()),new DateTime(mSessionPrefStore.getUltimaSync()));
        List<RestPuntoMedicionMedidor> puntoMedicionMedidor = new ArrayList<>();
        try {
            puntoMedicionMedidor = getPuntoMedicionMedidor.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return puntoMedicionMedidor;
    }

    public List<ContentProviderOperation> providerOperations(List<RestPuntoMedicionMedidor> puntoMedicionMedidor) {
        return mPuntoMedicionMedidorSqliteStore.replicarServidor(puntoMedicionMedidor);
    }
}
