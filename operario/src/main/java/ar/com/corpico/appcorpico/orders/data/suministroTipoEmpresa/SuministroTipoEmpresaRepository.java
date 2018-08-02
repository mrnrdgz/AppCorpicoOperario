package ar.com.corpico.appcorpico.orders.data.suministroTipoEmpresa;

import android.content.ContentProviderOperation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestSuministroTipoEmpresa;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class SuministroTipoEmpresaRepository implements ISuministroTipoEmpresaRepository {
    private static SuministroTipoEmpresaRepository suministroTipoEmpresaRepository;

    // Relaciones de composición
    private SuministroTipoEmpresaRestStore mSuministroTipoEmpresaRestStore;
    private SuministroTipoEmpresaSqliteStore mSuministroTipoEmpresaSqliteStore;
    private SessionsPrefsStore mSessionPrefStore;


    private SuministroTipoEmpresaRepository(SuministroTipoEmpresaRestStore suministroTipoEmpresaRestStore, SuministroTipoEmpresaSqliteStore suministroTipoEmpresaSqliteStore, SessionsPrefsStore sessionPrefStore) {
        mSuministroTipoEmpresaRestStore = Preconditions.checkNotNull(suministroTipoEmpresaRestStore,
                "La fuente de datos rest de Suministro Tipo Empresa no puede ser null");
        mSuministroTipoEmpresaSqliteStore = Preconditions.checkNotNull(suministroTipoEmpresaSqliteStore);
        mSessionPrefStore = Preconditions.checkNotNull(sessionPrefStore);

    }

    public static SuministroTipoEmpresaRepository getInstance(SuministroTipoEmpresaRestStore suministroTipoEmpresaRestStore, SuministroTipoEmpresaSqliteStore suministroTipoEmpresaSqliteStore, SessionsPrefsStore sessionPrefStore) {
        if (suministroTipoEmpresaRepository == null) {
            suministroTipoEmpresaRepository = new SuministroTipoEmpresaRepository(suministroTipoEmpresaRestStore, suministroTipoEmpresaSqliteStore, sessionPrefStore);
        }
        return suministroTipoEmpresaRepository;
    }

    @Override
    public void query(final SuministroTipoEmpresaRepositoryCallBack callback, Criteria filter) {

        mSuministroTipoEmpresaSqliteStore.getSuministroTipoEmpresa(new SuministroTipoEmpresaStore.GetSuministroTipoEmpresaStoreCallBack() {
            @Override
            public void onSuccess(List<RestSuministroTipoEmpresa> suministrosTipoEmpresa) {
                callback.onSuccess(suministrosTipoEmpresa);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }, filter);
    }

    @Override
    public List<RestSuministroTipoEmpresa> fetchDataSuministroTipoEmpresaIfNewer() {
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
        Call<List<RestSuministroTipoEmpresa>> getSuministroTipoEmpresa = service.GetSuministroTipoEmpresa(Integer.parseInt(mSessionPrefStore.getServicio()),new DateTime("2017-12-01"));
        //Call<List<RestTipoCuadrilla>> gettipocuadrilla = service.GetTipoCuadrilla(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()),new DateTime(mSessionPrefStore.getUltimaSync()));
        List<RestSuministroTipoEmpresa> suministroTipoEmpresa = new ArrayList<>();
        try {
            suministroTipoEmpresa = getSuministroTipoEmpresa.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return suministroTipoEmpresa;
    }

    public List<ContentProviderOperation> providerOperations(List<RestSuministroTipoEmpresa> suministrosTipoEmpresa) {
        return mSuministroTipoEmpresaSqliteStore.replicarServidor(suministrosTipoEmpresa);
    }
}
