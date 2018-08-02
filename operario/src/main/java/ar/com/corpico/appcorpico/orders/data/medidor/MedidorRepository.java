package ar.com.corpico.appcorpico.orders.data.medidor;

import android.content.ContentProviderOperation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMedidor;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class MedidorRepository implements IMedidorRepository {
    private static MedidorRepository etapasRepository;

    // Relaciones de composición
    private MedidorRestStore mMedidorRestStore;
    private MedidorSqliteStore mMedidorSqliteStore;
    private SessionsPrefsStore mSessionPrefStore;


    private MedidorRepository(MedidorRestStore medidorRestStore, MedidorSqliteStore medidorSqliteStore, SessionsPrefsStore sessionPrefStore) {
        mMedidorRestStore = Preconditions.checkNotNull(medidorRestStore,
                "La fuente de datos rest de Medidores no puede ser null");
        mMedidorSqliteStore = Preconditions.checkNotNull(medidorSqliteStore);
        mSessionPrefStore = Preconditions.checkNotNull(sessionPrefStore);

    }

    public static MedidorRepository getInstance(MedidorRestStore medidorRestStore, MedidorSqliteStore medidorSqliteStore, SessionsPrefsStore sessionPrefStore) {
        if (etapasRepository == null) {
            etapasRepository = new MedidorRepository(medidorRestStore, medidorSqliteStore, sessionPrefStore);
        }
        return etapasRepository;
    }

    @Override
    public void query(final MedidorRepositoryCallBack callback, Criteria filter) {

        mMedidorSqliteStore.getMedidor(new MedidorStore.GetMedidorStoreCallBack() {
            @Override
            public void onSuccess(List<RestMedidor> medidor) {
                callback.onSuccess(medidor);
            }
            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }, filter);
    }

    @Override
    public List<RestMedidor> fetchDataMedidorIfNewer() {
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
        Call<List<RestMedidor>> getmedidor = service.GetMedidor(Integer.parseInt(mSessionPrefStore.getServicio()),new DateTime(mSessionPrefStore.getUltimaSync()));
        //Call<List<RestTipoCuadrilla>> gettipocuadrilla = service.GetTipoCuadrilla(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()),new DateTime(mSessionPrefStore.getUltimaSync()));
        List<RestMedidor> medidor = new ArrayList<>();
        try {
            medidor = getmedidor.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return medidor;
    }

    public List<ContentProviderOperation> providerOperations(List<RestMedidor> medidores) {
        return mMedidorSqliteStore.replicarServidor(medidores);
    }
}
