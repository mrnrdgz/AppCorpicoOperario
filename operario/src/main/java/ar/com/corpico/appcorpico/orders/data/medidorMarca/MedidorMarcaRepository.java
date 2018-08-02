package ar.com.corpico.appcorpico.orders.data.medidorMarca;

import android.content.ContentProviderOperation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMedidorMarca;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class MedidorMarcaRepository implements IMedidorMarcaRepository {
    private static MedidorMarcaRepository medidorMarcaRepository;

    // Relaciones de composición
    private MedidorMarcaRestStore mMedidorMarcaRestStore;
    private MedidorMarcaSqliteStore mMedidorMarcaSqliteStore;
    private SessionsPrefsStore mSessionPrefStore;


    private MedidorMarcaRepository(MedidorMarcaRestStore medidorMarcaRestStore, MedidorMarcaSqliteStore medidorMarcaSqliteStore, SessionsPrefsStore sessionPrefStore) {
        mMedidorMarcaRestStore = Preconditions.checkNotNull(medidorMarcaRestStore,
                "La fuente de datos rest de Medidores Marca no puede ser null");
        mMedidorMarcaSqliteStore = Preconditions.checkNotNull(medidorMarcaSqliteStore);
        mSessionPrefStore = Preconditions.checkNotNull(sessionPrefStore);

    }

    public static MedidorMarcaRepository getInstance(MedidorMarcaRestStore medidorMarcaRestStore, MedidorMarcaSqliteStore medidorMarcaSqliteStore, SessionsPrefsStore sessionPrefStore) {
        if (medidorMarcaRepository == null) {
            medidorMarcaRepository = new MedidorMarcaRepository(medidorMarcaRestStore, medidorMarcaSqliteStore, sessionPrefStore);
        }
        return medidorMarcaRepository;
    }

    @Override
    public void query(final MedidorMarcaRepositoryCallBack callback, Criteria filter) {

        mMedidorMarcaSqliteStore.getMedidorMarca(new MedidorMarcaStore.GetMedidorMarcaStoreCallBack() {
            @Override
            //public void onSuccess(List<Tipo_Cuadrilla> tipoCuadrilla) {
            public void onSuccess(List<RestMedidorMarca> medidorMarcas) {
                callback.onSuccess(medidorMarcas);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }, filter);
    }

    @Override
    public List<RestMedidorMarca> fetchDataMedidorMarcaIfNewer() {
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
        Call<List<RestMedidorMarca>> getmedidorMarca = service.GetMedidorMarca(new DateTime(mSessionPrefStore.getUltimaSync()));
        //Call<List<RestTipoCuadrilla>> gettipocuadrilla = service.GetTipoCuadrilla(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()),new DateTime(mSessionPrefStore.getUltimaSync()));
        List<RestMedidorMarca> medidorMarca = new ArrayList<>();
        try {
            medidorMarca = getmedidorMarca.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return medidorMarca;
    }

    public List<ContentProviderOperation> providerOperations(List<RestMedidorMarca> medidoresMarca) {
        return mMedidorMarcaSqliteStore.replicarServidor(medidoresMarca);
    }
}
