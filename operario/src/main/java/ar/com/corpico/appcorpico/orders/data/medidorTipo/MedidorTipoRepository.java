package ar.com.corpico.appcorpico.orders.data.medidorTipo;

import android.content.ContentProviderOperation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMedidorTipo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class MedidorTipoRepository implements IMedidorTipoRepository {
    private static MedidorTipoRepository medidorTipoRepository;

    // Relaciones de composición
    private MedidorTipoRestStore mMedidorTipoRestStore;
    private MedidorTipoSqliteStore mMedidorTipoSqliteStore;
    private SessionsPrefsStore mSessionPrefStore;


    private MedidorTipoRepository(MedidorTipoRestStore medidorTipoRestStore, MedidorTipoSqliteStore medidorTipoSqliteStore, SessionsPrefsStore sessionPrefStore) {
        mMedidorTipoRestStore = Preconditions.checkNotNull(medidorTipoRestStore,
                "La fuente de datos rest de Medidores Tipos no puede ser null");
        mMedidorTipoSqliteStore = Preconditions.checkNotNull(medidorTipoSqliteStore);
        mSessionPrefStore = Preconditions.checkNotNull(sessionPrefStore);

    }

    public static MedidorTipoRepository getInstance(MedidorTipoRestStore medidorMarcaRestStore, MedidorTipoSqliteStore medidorMarcaSqliteStore, SessionsPrefsStore sessionPrefStore) {
        if (medidorTipoRepository == null) {
            medidorTipoRepository = new MedidorTipoRepository(medidorMarcaRestStore, medidorMarcaSqliteStore, sessionPrefStore);
        }
        return medidorTipoRepository;
    }

    @Override
    public void query(final MedidorTipoRepositoryCallBack callback, Criteria filter) {

        mMedidorTipoSqliteStore.getMedidorTipo(new MedidorTipoStore.GetMedidorTipoStoreCallBack() {
            @Override
            public void onSuccess(List<RestMedidorTipo> medidorTipos) {
                callback.onSuccess(medidorTipos);
            }
            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }, filter);
    }

    @Override
    public List<RestMedidorTipo> fetchDataMedidorTipoIfNewer() {
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
        Call<List<RestMedidorTipo>> getMedidorTipo = service.GetMedidorTipo(Integer.parseInt(mSessionPrefStore.getServicio()),new DateTime(mSessionPrefStore.getUltimaSync()));
        //Call<List<RestTipoCuadrilla>> gettipocuadrilla = service.GetTipoCuadrilla(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()),new DateTime(mSessionPrefStore.getUltimaSync()));
        List<RestMedidorTipo> medidorTipo = new ArrayList<>();
        try {
            medidorTipo = getMedidorTipo.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return medidorTipo;
    }

    public List<ContentProviderOperation> providerOperations(List<RestMedidorTipo> medidoresTipo) {
        return mMedidorTipoSqliteStore.replicarServidor(medidoresTipo);
    }
}
