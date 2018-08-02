package ar.com.corpico.appcorpico.orders.data.tipoConexion;

import android.content.ContentProviderOperation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoConexion;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class TipoConexionRepository implements ITipoConexionRepository {
    private static TipoConexionRepository tipoConexionRepository;

    // Relaciones de composición
    private TipoConexionRestStore mTipoConexionRestStore;
    private TipoConexionSqliteStore mTipoConexionSqliteStore;
    private SessionsPrefsStore mSessionPrefStore;


    private TipoConexionRepository(TipoConexionRestStore tipoConexioRestStore, TipoConexionSqliteStore tipoConexionSqliteStore, SessionsPrefsStore sessionPrefStore) {
        mTipoConexionRestStore = Preconditions.checkNotNull(tipoConexioRestStore,
                "La fuente de datos rest de Tipo Conexiones no puede ser null");
        mTipoConexionSqliteStore = Preconditions.checkNotNull(tipoConexionSqliteStore);
        mSessionPrefStore = Preconditions.checkNotNull(sessionPrefStore);

    }

    public static TipoConexionRepository getInstance(TipoConexionRestStore tipoConexionRestStore, TipoConexionSqliteStore tipoConexionSqliteStore, SessionsPrefsStore sessionPrefStore) {
        if (tipoConexionRepository == null) {
            tipoConexionRepository = new TipoConexionRepository(tipoConexionRestStore, tipoConexionSqliteStore, sessionPrefStore);
        }
        return tipoConexionRepository;
    }

    @Override
    public void query(final TipoConexionRepositoryCallBack callback, Criteria filter) {

        mTipoConexionSqliteStore.getTipoConexion(new TipoConexionStore.GetTipoConexionStoreCallBack() {
            @Override
            public void onSuccess(List<RestTipoConexion> tiposConexiones) {
                callback.onSuccess(tiposConexiones);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }, filter);
    }

    @Override
    public List<RestTipoConexion> fetchDataTipoConexionIfNewer() {
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
        Call<List<RestTipoConexion>> getTipoConexion = service.GetTipoConexion(Integer.parseInt(mSessionPrefStore.getServicio()),new DateTime(mSessionPrefStore.getUltimaSync()));
        //Call<List<RestTipoCuadrilla>> gettipocuadrilla = service.GetTipoCuadrilla(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()),new DateTime(mSessionPrefStore.getUltimaSync()));
        List<RestTipoConexion> tipoConexion = new ArrayList<>();
        try {
            tipoConexion = getTipoConexion.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tipoConexion;
    }

    public List<ContentProviderOperation> providerOperations(List<RestTipoConexion> tipoConexiones) {
        return mTipoConexionSqliteStore.replicarServidor(tipoConexiones);
    }
}
