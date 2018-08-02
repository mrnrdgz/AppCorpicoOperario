package ar.com.corpico.appcorpico.orders.data.medidorModelo;

import android.content.ContentProviderOperation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMedidorModelo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class MedidorModeloRepository implements IMedidorModeloRepository {
    private static MedidorModeloRepository medidorModeloRepository;

    // Relaciones de composición
    private MedidorModeloRestStore mMedidorModeloRestStore;
    private MedidorModeloSqliteStore mMedidorModeloSqliteStore;
    private SessionsPrefsStore mSessionPrefStore;


    private MedidorModeloRepository(MedidorModeloRestStore medidorModeloRestStore, MedidorModeloSqliteStore medidorModeloSqliteStore, SessionsPrefsStore sessionPrefStore) {
        mMedidorModeloRestStore = Preconditions.checkNotNull(medidorModeloRestStore,
                "La fuente de datos rest de Medidores Modelos no puede ser null");
        mMedidorModeloSqliteStore = Preconditions.checkNotNull(medidorModeloSqliteStore);
        mSessionPrefStore = Preconditions.checkNotNull(sessionPrefStore);

    }

    public static MedidorModeloRepository getInstance(MedidorModeloRestStore medidorModeloRestStore, MedidorModeloSqliteStore medidorModeloSqliteStore, SessionsPrefsStore sessionPrefStore) {
        if (medidorModeloRepository == null) {
            medidorModeloRepository = new MedidorModeloRepository(medidorModeloRestStore, medidorModeloSqliteStore, sessionPrefStore);
        }
        return medidorModeloRepository;
    }

    @Override
    public void query(final MedidorModeloRepositoryCallBack callback, Criteria filter) {

        mMedidorModeloSqliteStore.getMedidorModelo(new MedidorModeloStore.GetMedidorModeloStoreCallBack() {
            @Override
            //public void onSuccess(List<Tipo_Cuadrilla> tipoCuadrilla) {
            public void onSuccess(List<RestMedidorModelo> medidorModelo) {
                callback.onSuccess(medidorModelo);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }, filter);
    }

    @Override
    public List<RestMedidorModelo> fetchDataMedidorModeloIfNewer() {
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
        Call<List<RestMedidorModelo>> getmedidorModelo = service.GetMedidorModelo(Integer.parseInt(mSessionPrefStore.getServicio()),new DateTime(mSessionPrefStore.getUltimaSync()));
        //Call<List<RestTipoCuadrilla>> gettipocuadrilla = service.GetTipoCuadrilla(Integer.parseInt(mSessionPrefStore.geServicio()),Integer.parseInt(mSessionPrefStore.getSector()),new DateTime(mSessionPrefStore.getUltimaSync()));
        List<RestMedidorModelo> medidorModelo = new ArrayList<>();
        try {
            medidorModelo = getmedidorModelo.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return medidorModelo;
    }

    public List<ContentProviderOperation> providerOperations(List<RestMedidorModelo> medidoresModelo) {
        return mMedidorModeloSqliteStore.replicarServidor(medidoresModelo);
    }
}
