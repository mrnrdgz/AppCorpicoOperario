package ar.com.corpico.appcorpico.orders.data.puntoZona;

import android.content.ContentProviderOperation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestPuntoZona;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class PuntoZonaRepository implements IPuntoZonasRepository {
    private static PuntoZonaRepository puntoZonarepository = null;

    // Relaciones de composición
    private PuntoZonaRestStore mPuntoZonaRestStore;
    private PuntoZonaSqliteStore mPuntoZonaSqliteStore;
    private SessionsPrefsStore mSessionPrefStore;


    private PuntoZonaRepository(PuntoZonaRestStore puntoZonasRestStore, PuntoZonaSqliteStore puntoZonaSqliteStore, SessionsPrefsStore sessionPrefStore) {
        mPuntoZonaRestStore = Preconditions.checkNotNull(puntoZonasRestStore,
                "La fuente de datos rest de zonas no puede ser null");
        mPuntoZonaSqliteStore = Preconditions.checkNotNull(puntoZonaSqliteStore);
        mSessionPrefStore = Preconditions.checkNotNull(sessionPrefStore);

    }

    public static PuntoZonaRepository getInstance(PuntoZonaRestStore puntoZonasRestStore, PuntoZonaSqliteStore puntoZonaSqliteStore, SessionsPrefsStore sessionPrefStore) {
        if (puntoZonarepository == null) {
            puntoZonarepository = new PuntoZonaRepository(puntoZonasRestStore, puntoZonaSqliteStore,sessionPrefStore);
        }
        return puntoZonarepository;
    }

    @Override
    public void query(final PuntoZonaRepositoryCallBack callback) {
        mPuntoZonaSqliteStore.getPuntoZona(
                new PuntoZonaStore.GetPuntoZonaStoreCallBack() {
                    @Override
                    public void onSuccess(List<RestPuntoZona> puntozona) {
                           //List<Zona> zona = new ArrayList<>();

                            callback.onSuccess(puntozona);
                    }

                    @Override
                    public void onError(String error) {
                        callback.onError(error);
                    }
                }
        );
    }

    @Override
    public List<RestPuntoZona> fetchDataPuntoZonaIfNewer() {

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
    Call<List<RestPuntoZona>> getpuntozona = service.GetPuntoZona( new DateTime(mSessionPrefStore.getUltimaSync()));
    List<RestPuntoZona> puntoZona = new ArrayList<>();
        try {
            puntoZona = getpuntozona.execute().body();
    } catch (IOException e) {
        e.printStackTrace();
    }

        return puntoZona;
}

    public List<ContentProviderOperation> providerOperations(List<RestPuntoZona> puntoZonas) {
        return mPuntoZonaSqliteStore.replicarServidor(puntoZonas);
    }
}

