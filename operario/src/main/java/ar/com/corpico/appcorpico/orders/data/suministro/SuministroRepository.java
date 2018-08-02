package ar.com.corpico.appcorpico.orders.data.suministro;

import android.content.ContentProviderOperation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestSuministro;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class SuministroRepository implements ISuministroRepository {
    private static SuministroRepository suministroRepository;

    // Relaciones de composición
    private SuministroRestStore mSuministroRestStore;
    private SuministroSqliteStore mSuministroSqliteStore;
    private SessionsPrefsStore mSessionPrefStore;


    private SuministroRepository(SuministroRestStore suministroRestStore, SuministroSqliteStore suministroSqliteStore, SessionsPrefsStore sessionPrefStore) {
        mSuministroRestStore = Preconditions.checkNotNull(suministroRestStore,
                "La fuente de datos rest de Suministros no puede ser null");
        mSuministroSqliteStore = Preconditions.checkNotNull(suministroSqliteStore);
        mSessionPrefStore = Preconditions.checkNotNull(sessionPrefStore);

    }

    public static SuministroRepository getInstance(SuministroRestStore suministroRestStore, SuministroSqliteStore suministroSqliteStore, SessionsPrefsStore sessionPrefStore) {
        if (suministroRepository == null) {
            suministroRepository = new SuministroRepository(suministroRestStore, suministroSqliteStore, sessionPrefStore);
        }
        return suministroRepository;
    }

    @Override
    public void query(final SuministroRepositoryCallBack callback, Criteria filter) {

        mSuministroSqliteStore.getSuministro(new SuministroStore.GetSuministroStoreCallBack() {
            @Override
            public void onSuccess(List<RestSuministro> suministros) {
                callback.onSuccess(suministros);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }, filter);
    }

    @Override
    public List<RestSuministro> fetchDataSuministroIfNewer() {
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
        Call<List<RestSuministro>> getSuministro = service.GetSuministro(new DateTime("2017-12-25"));
        //Call<List<RestTipoCuadrilla>> gettipocuadrilla = service.GetTipoCuadrilla(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()),new DateTime(mSessionPrefStore.getUltimaSync()));
        List<RestSuministro> suministro = new ArrayList<>();
        try {
            suministro = getSuministro.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return suministro;
    }

    public List<ContentProviderOperation> providerOperations(List<RestSuministro> suministros) {
        return mSuministroSqliteStore.replicarServidor(suministros);
    }
}
