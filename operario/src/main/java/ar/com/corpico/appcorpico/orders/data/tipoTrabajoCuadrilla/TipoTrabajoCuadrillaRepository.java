package ar.com.corpico.appcorpico.orders.data.tipoTrabajoCuadrilla;

import android.content.ContentProviderOperation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoTrabajoCuadrilla;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class TipoTrabajoCuadrillaRepository implements ITipoTrabajoCuadrillaRepository {
    private static TipoTrabajoCuadrillaRepository tipotrabajoCuadrillarepository;

    // Relaciones de composición
    private TipoTrabajoCuadrillaRestStore mTipoTrabajoCuadrillaRestStore;
    private TipoTrabajoCuadrillaSqliteStore mTipoTrabajoCuadrillaSqliteStore;
    private SessionsPrefsStore mSessionPrefStore;


    private TipoTrabajoCuadrillaRepository(TipoTrabajoCuadrillaRestStore tipoTrabajoCuadrillaRestStore, TipoTrabajoCuadrillaSqliteStore tipoTrabajoCuadrillaSqliteStore, SessionsPrefsStore sessionPrefStore) {
        mTipoTrabajoCuadrillaRestStore = Preconditions.checkNotNull(tipoTrabajoCuadrillaRestStore,
                "La fuente de datos rest de tipo trabajos no puede ser null");
        mTipoTrabajoCuadrillaSqliteStore = Preconditions.checkNotNull(tipoTrabajoCuadrillaSqliteStore);
        mSessionPrefStore = Preconditions.checkNotNull(sessionPrefStore);

    }

    public static TipoTrabajoCuadrillaRepository getInstance(TipoTrabajoCuadrillaRestStore tipoTrabajoCuadrillaRestStore, TipoTrabajoCuadrillaSqliteStore tipoTrabajoCuadrillaSqliteStore, SessionsPrefsStore sessionPrefStore) {
        if (tipotrabajoCuadrillarepository == null) {
            tipotrabajoCuadrillarepository = new TipoTrabajoCuadrillaRepository(tipoTrabajoCuadrillaRestStore, tipoTrabajoCuadrillaSqliteStore,sessionPrefStore);
        }
        return tipotrabajoCuadrillarepository;
    }
    @Override
    public void query(final TipoTrabajoCuadrillaRepositoryCallBack callback, Criteria filter) {
        TipoTrabajoCuadrillaStore.GetTipoTrabajoCuadrillaStoreCallBack callback1 = new TipoTrabajoCuadrillaStore.GetTipoTrabajoCuadrillaStoreCallBack() {
            @Override
            //public void onSuccess(List<Tipo_Trabajo> tipoTrabajo) {
            public void onSuccess(List<RestTipoTrabajoCuadrilla> tipoTrabajoCuadrilla) {
                // TODO: Guardar datos en SQLite. Posible método save()/insert()/add()
                callback.onSuccess(tipoTrabajoCuadrilla);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        };

        mTipoTrabajoCuadrillaSqliteStore.getTipoTrabajoCuadrilla(callback1, filter);
    }
    @Override
    public List<RestTipoTrabajoCuadrilla> fetchDataTipoTrabajoCuadrillaIfNewer() {
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
        Call<List<RestTipoTrabajoCuadrilla>> gettipoTrabajoCuadrilla = service.GetTipoTrabajoCuadrilla(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()),new DateTime(mSessionPrefStore.getUltimaSync()));
        //Call<List<RestTipoTrabajo>> gettipotrabajo = service.GetTipoTrabajo(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()),new DateTime(mSessionPrefStore.getUltimaSync()));
        List<RestTipoTrabajoCuadrilla> tipoTrabajoCuadrilla = new ArrayList<>();
        try {
            tipoTrabajoCuadrilla = gettipoTrabajoCuadrilla.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tipoTrabajoCuadrilla;
    }
    public List<ContentProviderOperation> providerOperations(List<RestTipoTrabajoCuadrilla> tipoTrabajosCuadrillas) {
        return mTipoTrabajoCuadrillaSqliteStore.replicarServidor(tipoTrabajosCuadrillas);
    }
}
