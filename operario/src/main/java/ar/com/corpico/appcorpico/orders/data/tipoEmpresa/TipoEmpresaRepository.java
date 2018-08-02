package ar.com.corpico.appcorpico.orders.data.tipoEmpresa;

import android.content.ContentProviderOperation;

import com.google.common.base.Preconditions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoEmpresa;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class TipoEmpresaRepository implements ITipoEmpresaRepository {
    private static TipoEmpresaRepository tipoEmpresaRepository;

    // Relaciones de composición
    private TipoEmpresaRestStore mTipoEmpresaRestStore;
    private TipoEmpresaSqliteStore mTipoEmpresaSqliteStore;
    private SessionsPrefsStore mSessionPrefStore;


    private TipoEmpresaRepository(TipoEmpresaRestStore tipoEmpresaRestStore, TipoEmpresaSqliteStore tipoEmpresaSqliteStore, SessionsPrefsStore sessionPrefStore) {
        mTipoEmpresaRestStore = Preconditions.checkNotNull(tipoEmpresaRestStore,
                "La fuente de datos rest de Tipo Empresa no puede ser null");
        mTipoEmpresaSqliteStore = Preconditions.checkNotNull(tipoEmpresaSqliteStore);
        mSessionPrefStore = Preconditions.checkNotNull(sessionPrefStore);

    }

    public static TipoEmpresaRepository getInstance(TipoEmpresaRestStore tipoEmpresaRestStore, TipoEmpresaSqliteStore tipoEmpresaoSqliteStore, SessionsPrefsStore sessionPrefStore) {
        if (tipoEmpresaRepository == null) {
            tipoEmpresaRepository = new TipoEmpresaRepository(tipoEmpresaRestStore, tipoEmpresaoSqliteStore, sessionPrefStore);
        }
        return tipoEmpresaRepository;
    }

    @Override
    public void query(final TipoEmpresaRepositoryCallBack callback, Criteria filter) {

        mTipoEmpresaSqliteStore.getTipoEmpresa(new TipoEmpresaStore.GetTipoEmpresaStoreCallBack() {
            @Override
            public void onSuccess(List<RestTipoEmpresa> tiposEmpresas) {
                callback.onSuccess(tiposEmpresas);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }, filter);
    }

    @Override
    public List<RestTipoEmpresa> fetchDataTipoEmpresaIfNewer() {
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
        Call<List<RestTipoEmpresa>> getTipoEmpresa = service.GetTipoEmpresa();
        //Call<List<RestTipoCuadrilla>> gettipocuadrilla = service.GetTipoCuadrilla(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()),new DateTime(mSessionPrefStore.getUltimaSync()));
        List<RestTipoEmpresa> tipoEmpresa = new ArrayList<>();
        try {
            tipoEmpresa = getTipoEmpresa.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tipoEmpresa;
    }

    public List<ContentProviderOperation> providerOperations(List<RestTipoEmpresa> tipoEmpresas) {
        return mTipoEmpresaSqliteStore.replicarServidor(tipoEmpresas);
    }
}
