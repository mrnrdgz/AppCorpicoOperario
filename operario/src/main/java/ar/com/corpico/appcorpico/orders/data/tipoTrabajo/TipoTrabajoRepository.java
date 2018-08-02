package ar.com.corpico.appcorpico.orders.data.tipoTrabajo;

import android.content.ContentProviderOperation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoTrabajo;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class TipoTrabajoRepository implements ITipoTrabajoRepository {
    private static TipoTrabajoRepository tipotrabajorepository;

    // Relaciones de composición
    private TipoTrabajoRestStore mTipoTrabajoRestStore;
    private TipoTrabajoSqliteStore mTipoTrabajoSqliteStore;
    private SessionsPrefsStore mSessionPrefStore;


    private TipoTrabajoRepository(TipoTrabajoRestStore tipoTrabajoRestStore, TipoTrabajoSqliteStore tipoTrabajoSqliteStore, SessionsPrefsStore sessionPrefStore) {
        mTipoTrabajoRestStore = Preconditions.checkNotNull(tipoTrabajoRestStore,
                "La fuente de datos rest de tipo trabajos no puede ser null");
        mTipoTrabajoSqliteStore = Preconditions.checkNotNull(tipoTrabajoSqliteStore);
        mSessionPrefStore = Preconditions.checkNotNull(sessionPrefStore);

    }

    public static TipoTrabajoRepository getInstance(TipoTrabajoRestStore tipoTrabajoRestStore, TipoTrabajoSqliteStore tipoTrabajoSqliteStore, SessionsPrefsStore sessionPrefStore) {
        if (tipotrabajorepository == null) {
            tipotrabajorepository = new TipoTrabajoRepository(tipoTrabajoRestStore, tipoTrabajoSqliteStore, sessionPrefStore);
        }
        return tipotrabajorepository;
    }

    @Override
    public void query(final TipoTrabajoRepositoryCallBack callback, Criteria filter) {

        // Consultar tabla "tipo_trabajo" SQLite
        mTipoTrabajoSqliteStore.getTipoTrabajo(
                new TipoTrabajoStore.GetTipoTrabajoStoreCallBack() {
                    @Override
                    //public void onSuccess(List<Tipo_Trabajo> tipoTrabajo) {
                    public void onSuccess(List<Tipo_Trabajo> tipoTrabajoCuadrilla) {
                        // TODO: Guardar datos en SQLite. Posible método save()/insert()/add()
                        callback.onSuccess(tipoTrabajoCuadrilla);
                    }

                    @Override
                    public void onError(String error) {
                        callback.onError(error);
                    }
                },
                filter);
    }

    @Override
    public List<RestTipoTrabajo> fetchDataTipoTrabajoIfNewer() {
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
        Call<List<RestTipoTrabajo>> gettipotrabajo = service.GetTipoTrabajo(Integer.parseInt(mSessionPrefStore.getServicio()), Integer.parseInt(mSessionPrefStore.getSector()), new DateTime(mSessionPrefStore.getUltimaSync()));
        //Call<List<RestTipoTrabajo>> gettipotrabajo = service.GetTipoTrabajo(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()),new DateTime(mSessionPrefStore.getUltimaSync()));
        List<RestTipoTrabajo> tipoTrabajo = new ArrayList<>();
        try {
            tipoTrabajo = gettipotrabajo.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tipoTrabajo;
    }

    public List<ContentProviderOperation> providerOperations(List<RestTipoTrabajo> tipoTrabajos) {
        return mTipoTrabajoSqliteStore.replicarServidor(tipoTrabajos);
    }
}
