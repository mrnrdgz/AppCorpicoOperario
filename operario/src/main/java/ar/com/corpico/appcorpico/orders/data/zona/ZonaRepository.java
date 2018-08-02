package ar.com.corpico.appcorpico.orders.data.zona;

import android.content.ContentProviderOperation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestZona;
import ar.com.corpico.appcorpico.orders.domain.entity.Zona;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class ZonaRepository implements IZonasRepository {
    private static ZonaRepository zonarepository = null;

    // Relaciones de composición
    private ZonaRestStore mZonaRestStore;
    private ZonaSqliteStore mZonaSqliteStore;
    private SessionsPrefsStore mSessionPrefStore;


    private ZonaRepository(ZonaRestStore zonasRestStore, ZonaSqliteStore zonaSqliteStore, SessionsPrefsStore sessionPrefStore) {
        mZonaRestStore = Preconditions.checkNotNull(zonasRestStore,
                "La fuente de datos rest de zonas no puede ser null");
        mZonaSqliteStore = Preconditions.checkNotNull(zonaSqliteStore);
        mSessionPrefStore = Preconditions.checkNotNull(sessionPrefStore);

    }

    public static ZonaRepository getInstance(ZonaRestStore zonasRestStore, ZonaSqliteStore zonaSqliteStore,SessionsPrefsStore sessionPrefStore) {
        if (zonarepository == null) {
            zonarepository = new ZonaRepository(zonasRestStore, zonaSqliteStore,sessionPrefStore);
        }
        return zonarepository;
    }

    @Override
    public void query(final ZonaRepositoryCallBack callback) {
        mZonaSqliteStore.getZona(
                new ZonaStore.GetZonaStoreCallBack() {
                    @Override
                    public void onSuccess(List<Zona> zona) {
                           //List<Zona> zona = new ArrayList<>();

                            callback.onSuccess(zona);
                    }

                    @Override
                    public void onError(String error) {
                        callback.onError(error);
                    }
                }
        );
    }

    @Override
    public List<RestZona> fetchDataZonaIfNewer() {

    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

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
    Call<List<RestZona>> getzona = service.GetZona( new DateTime(mSessionPrefStore.getUltimaSync()));
    List<RestZona> zona = new ArrayList<>();
        try {
            zona = getzona.execute().body();
    } catch (IOException e) {
        e.printStackTrace();
    }

        return zona;
}

    public List<ContentProviderOperation> providerOperations(List<RestZona> zonas) {
        return mZonaSqliteStore.replicarServidor(zonas);
    }
}

