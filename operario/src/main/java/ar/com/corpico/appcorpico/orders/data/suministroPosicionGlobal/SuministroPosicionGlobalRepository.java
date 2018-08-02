package ar.com.corpico.appcorpico.orders.data.suministroPosicionGlobal;

import android.content.ContentProviderOperation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestSuministroPosicionGlobal;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class SuministroPosicionGlobalRepository implements ISuministroPosicionGlobalRepository {
    private static SuministroPosicionGlobalRepository suministroPosicionGlobalRepository;

    // Relaciones de composición
    private SuministroPosicionGlobalRestStore mSuministroPosicionGlobalRestStore;
    private SuministroPosicionGlobalSqliteStore mSuministroPosicionGlobalSqliteStore;
    private SessionsPrefsStore mSessionPrefStore;


    private SuministroPosicionGlobalRepository(SuministroPosicionGlobalRestStore suministroPosicionGlobalRestStore, SuministroPosicionGlobalSqliteStore suministroPosicionGlobalSqliteStore, SessionsPrefsStore sessionPrefStore) {
        mSuministroPosicionGlobalRestStore = Preconditions.checkNotNull(suministroPosicionGlobalRestStore,
                "La fuente de datos rest de Suministro Posicion Global no puede ser null");
        mSuministroPosicionGlobalSqliteStore = Preconditions.checkNotNull(suministroPosicionGlobalSqliteStore);
        mSessionPrefStore = Preconditions.checkNotNull(sessionPrefStore);

    }

    public static SuministroPosicionGlobalRepository getInstance(SuministroPosicionGlobalRestStore suministroPosicionGlobalRestStore, SuministroPosicionGlobalSqliteStore suministroPosicionGlobalSqliteStore, SessionsPrefsStore sessionPrefStore) {
        if (suministroPosicionGlobalRepository == null) {
            suministroPosicionGlobalRepository = new SuministroPosicionGlobalRepository(suministroPosicionGlobalRestStore, suministroPosicionGlobalSqliteStore, sessionPrefStore);
        }
        return suministroPosicionGlobalRepository;
    }

    @Override
    public void query(final SuministroPosicionGlobalRepositoryCallBack callback, Criteria filter) {

        mSuministroPosicionGlobalSqliteStore.getSuministroPosicionGlobal(new SuministroPosicionGlobalStore.GetSuministroPosicionGlobalStoreCallBack() {
            @Override
            //public void onSuccess(List<Tipo_Cuadrilla> tipoCuadrilla) {
            public void onSuccess(List<RestSuministroPosicionGlobal> suministrosPosicionGlobal) {
                callback.onSuccess(suministrosPosicionGlobal);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }, filter);
    }

    @Override
    public List<RestSuministroPosicionGlobal> fetchDataSuministroPosicionGlobalIfNewer() {
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
        Call<List<RestSuministroPosicionGlobal>> getSuministroPosicionGlobal = service.GetSuministroPosicionGlobal(new DateTime(mSessionPrefStore.getUltimaSync()));
        //Call<List<RestTipoCuadrilla>> gettipocuadrilla = service.GetTipoCuadrilla(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()),new DateTime(mSessionPrefStore.getUltimaSync()));
        List<RestSuministroPosicionGlobal> suministroPosicionGlobal = new ArrayList<>();
        try {
            suministroPosicionGlobal = getSuministroPosicionGlobal.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return suministroPosicionGlobal;
    }

    public List<ContentProviderOperation> providerOperations(List<RestSuministroPosicionGlobal> suministrosPosicionGlobal) {
        return mSuministroPosicionGlobalSqliteStore.replicarServidor(suministrosPosicionGlobal);
    }
}
