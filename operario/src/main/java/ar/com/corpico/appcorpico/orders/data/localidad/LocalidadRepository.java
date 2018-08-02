package ar.com.corpico.appcorpico.orders.data.localidad;

import android.content.ContentProviderOperation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestLocalidad;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class LocalidadRepository implements ILocalidadRepository {
    private static LocalidadRepository localidadRepository;

    // Relaciones de composición
    private LocalidadRestStore mLocalidadRestStore;
    private LocalidadSqliteStore mLocalidadSqliteStore;
    private SessionsPrefsStore mSessionPrefStore;


    private LocalidadRepository(LocalidadRestStore localidadRestStore, LocalidadSqliteStore localidadSqliteStore, SessionsPrefsStore sessionPrefStore) {
        mLocalidadRestStore = Preconditions.checkNotNull(localidadRestStore,
                "La fuente de datos rest de Localidades no puede ser null");
        mLocalidadSqliteStore = Preconditions.checkNotNull(localidadSqliteStore);
        mSessionPrefStore = Preconditions.checkNotNull(sessionPrefStore);

    }

    public static LocalidadRepository getInstance(LocalidadRestStore localidadRestStore, LocalidadSqliteStore localidadSqliteStore, SessionsPrefsStore sessionPrefStore) {
        if (localidadRepository == null) {
            localidadRepository = new LocalidadRepository(localidadRestStore, localidadSqliteStore, sessionPrefStore);
        }
        return localidadRepository;
    }

    @Override
    public void query(final LocalidadRepositoryCallBack callback, Criteria filter) {

        mLocalidadSqliteStore.getLocalidad(new LocalidadStore.GetLocalidadStoreCallBack() {
            @Override
            public void onSuccess(List<RestLocalidad> localidad) {
                callback.onSuccess(localidad);
            }
            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }, filter);
    }

    @Override
    public List<RestLocalidad> fetchDataLocalidadIfNewer() {
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
        Call<List<RestLocalidad>> getlocalidad = service.GetLocalidad(new DateTime(mSessionPrefStore.getUltimaSync()));
        //Call<List<RestTipoCuadrilla>> gettipocuadrilla = service.GetTipoCuadrilla(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()),new DateTime(mSessionPrefStore.getUltimaSync()));
        List<RestLocalidad> localidad = new ArrayList<>();
        try {
            localidad = getlocalidad.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return localidad;
    }

    public List<ContentProviderOperation> providerOperations(List<RestLocalidad> localidades) {
        return mLocalidadSqliteStore.replicarServidor(localidades);
    }
}
