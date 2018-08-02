package ar.com.corpico.appcorpico.orders.data.sector;

import android.content.ContentProviderOperation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestSector;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class SectorRepository implements ISectorRepository {
    private static SectorRepository sectorRepository;

    // Relaciones de composición
    private SectorRestStore mSectorRestStore;
    private SectorSqliteStore mSectorSqliteStore;
    private SessionsPrefsStore mSessionPrefStore;


    private SectorRepository(SectorRestStore sectorRestStore, SectorSqliteStore sectorSqliteStore, SessionsPrefsStore sessionPrefStore) {
        mSectorRestStore = Preconditions.checkNotNull(sectorRestStore,
                "La fuente de datos rest de Sectores no puede ser null");
        mSectorSqliteStore = Preconditions.checkNotNull(sectorSqliteStore);
        mSessionPrefStore = Preconditions.checkNotNull(sessionPrefStore);

    }

    public static SectorRepository getInstance(SectorRestStore sectorRestStore, SectorSqliteStore sectorSqliteStore, SessionsPrefsStore sessionPrefStore) {
        if (sectorRepository == null) {
            sectorRepository = new SectorRepository(sectorRestStore, sectorSqliteStore, sessionPrefStore);
        }
        return sectorRepository;
    }

    @Override
    public void query(final TipoUsuarioRepositoryCallBack callback, Criteria filter) {

        mSectorSqliteStore.getSector(new SectorStore.GetSectorStoreCallBack() {
            @Override
            public void onSuccess(List<RestSector> sectors) {
                callback.onSuccess(sectors);
            }
            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }, filter);
    }

    @Override
    public List<RestSector> fetchDataSectorIfNewer() {
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
        Call<List<RestSector>> getSector = service.GetSector(Integer.parseInt(mSessionPrefStore.getServicio()),new DateTime(mSessionPrefStore.getUltimaSync()));
        //Call<List<RestTipoCuadrilla>> gettipocuadrilla = service.GetTipoCuadrilla(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()),new DateTime(mSessionPrefStore.getUltimaSync()));
        List<RestSector> sector = new ArrayList<>();
        try {
            sector = getSector.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sector;
    }

    public List<ContentProviderOperation> providerOperations(List<RestSector> sectors) {
        return mSectorSqliteStore.replicarServidor(sectors);
    }
}
