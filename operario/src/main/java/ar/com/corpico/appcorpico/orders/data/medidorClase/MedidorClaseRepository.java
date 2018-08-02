package ar.com.corpico.appcorpico.orders.data.medidorClase;

import android.content.ContentProviderOperation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMedidorClase;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class MedidorClaseRepository implements IMedidorClaseRepository {
    private static MedidorClaseRepository medidorClaseRepository;

    // Relaciones de composición
    private MedidorClaseRestStore mMedidorClaseRestStore;
    private MedidorClaseSqliteStore mMedidorClaseSqliteStore;
    private SessionsPrefsStore mSessionPrefStore;


    private MedidorClaseRepository(MedidorClaseRestStore medidorClaseRestStore, MedidorClaseSqliteStore medidorClaseSqliteStore, SessionsPrefsStore sessionPrefStore) {
        mMedidorClaseRestStore = Preconditions.checkNotNull(medidorClaseRestStore,
                "La fuente de datos rest de Medidor Clase no puede ser null");
        mMedidorClaseSqliteStore = Preconditions.checkNotNull(medidorClaseSqliteStore);
        mSessionPrefStore = Preconditions.checkNotNull(sessionPrefStore);

    }

    public static MedidorClaseRepository getInstance(MedidorClaseRestStore medidorClaseRestStore, MedidorClaseSqliteStore medidorClaseSqliteStore, SessionsPrefsStore sessionPrefStore) {
        if (medidorClaseRepository == null) {
            medidorClaseRepository = new MedidorClaseRepository(medidorClaseRestStore, medidorClaseSqliteStore, sessionPrefStore);
        }
        return medidorClaseRepository;
    }

    @Override
    public void query(final MedidorClaseRepositoryCallBack callback, Criteria filter) {

        mMedidorClaseSqliteStore.getMedidorClase(new MedidorClaseStore.GetMedidorClaseStoreCallBack() {
            @Override
            public void onSuccess(List<RestMedidorClase> medidorClases) {
                callback.onSuccess(medidorClases);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }, filter);
    }

    @Override
    public List<RestMedidorClase> fetchDataMedidorClaseIfNewer() {
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
        Call<List<RestMedidorClase>> getmedidorClase = service.GetMedidorClase(Integer.parseInt(mSessionPrefStore.getServicio()),new DateTime(mSessionPrefStore.getUltimaSync()));
        //Call<List<RestTipoCuadrilla>> gettipocuadrilla = service.GetTipoCuadrilla(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()),new DateTime(mSessionPrefStore.getUltimaSync()));
        List<RestMedidorClase> medidorClase = new ArrayList<>();
        try {
            medidorClase = getmedidorClase.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return medidorClase;
    }

    public List<ContentProviderOperation> providerOperations(List<RestMedidorClase> medidoresClase) {
        return mMedidorClaseSqliteStore.replicarServidor(medidoresClase);
    }
}
