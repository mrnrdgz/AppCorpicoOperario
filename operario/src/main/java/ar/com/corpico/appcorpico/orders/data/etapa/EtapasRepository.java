package ar.com.corpico.appcorpico.orders.data.etapa;

import android.content.ContentProviderOperation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestEtapa;
import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class EtapasRepository implements IEtapasRepository {
    private static EtapasRepository etapasRepository;

    // Relaciones de composición
    private EtapaRestStore mEtapaRestStore;
    private EtapaSqliteStore mEtapaSqliteStore;
    private SessionsPrefsStore mSessionPrefStore;


    private EtapasRepository(EtapaRestStore etapaRestStore, EtapaSqliteStore etapaSqliteStore, SessionsPrefsStore sessionPrefStore) {
        mEtapaRestStore = Preconditions.checkNotNull(etapaRestStore,
                "La fuente de datos rest de Etapas no puede ser null");
        mEtapaSqliteStore = Preconditions.checkNotNull(etapaSqliteStore);
        mSessionPrefStore = Preconditions.checkNotNull(sessionPrefStore);

    }

    public static EtapasRepository getInstance(EtapaRestStore etapaRestStore, EtapaSqliteStore etapaSqliteStore, SessionsPrefsStore sessionPrefStore) {
        if (etapasRepository == null) {
            etapasRepository = new EtapasRepository(etapaRestStore, etapaSqliteStore, sessionPrefStore);
        }
        return etapasRepository;
    }

    @Override
    public void query(final EtapaRepositoryCallBack callback, Criteria filter) {

        mEtapaSqliteStore.getEtapa(new EtapaStore.GetEtapaStoreCallBack() {
            @Override
            public void onSuccess(List<Etapa> etapa) {
                callback.onSuccess(etapa);
            }
            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }, filter);
    }

    @Override
    public List<RestEtapa> fetchDataEtapaIfNewer() {
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
        Call<List<RestEtapa>> getetapa = service.GetEtapa(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()), new DateTime(mSessionPrefStore.getUltimaSync()));
        //Call<List<RestTipoCuadrilla>> gettipocuadrilla = service.GetTipoCuadrilla(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()),new DateTime(mSessionPrefStore.getUltimaSync()));
        List<RestEtapa> etapa = new ArrayList<>();
        try {
            etapa = getetapa.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return etapa;
    }

    public List<ContentProviderOperation> providerOperations(List<RestEtapa> etapas) {
        return mEtapaSqliteStore.replicarServidor(etapas);
    }
}
