package ar.com.corpico.appcorpico.orders.data.tipoConsumo;

import android.content.ContentProviderOperation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoConsumo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class TipoConsumoRepository implements ITipoConsumoRepository {
    private static TipoConsumoRepository tipoConsumoRepository;

    // Relaciones de composición
    private TipoConsumoRestStore mTipoConsumoRestStore;
    private TipoConsumoSqliteStore mTipoConsumoSqliteStore;
    private SessionsPrefsStore mSessionPrefStore;


    private TipoConsumoRepository(TipoConsumoRestStore tipoConsumoRestStore, TipoConsumoSqliteStore tipoConsumoSqliteStore, SessionsPrefsStore sessionPrefStore) {
        mTipoConsumoRestStore = Preconditions.checkNotNull(tipoConsumoRestStore,
                "La fuente de datos rest de Tipo Consumo no puede ser null");
        mTipoConsumoSqliteStore = Preconditions.checkNotNull(tipoConsumoSqliteStore);
        mSessionPrefStore = Preconditions.checkNotNull(sessionPrefStore);

    }

    public static TipoConsumoRepository getInstance(TipoConsumoRestStore tipoConsumoRestStore, TipoConsumoSqliteStore tipoConsumoSqliteStore, SessionsPrefsStore sessionPrefStore) {
        if (tipoConsumoRepository == null) {
            tipoConsumoRepository = new TipoConsumoRepository(tipoConsumoRestStore, tipoConsumoSqliteStore, sessionPrefStore);
        }
        return tipoConsumoRepository;
    }

    @Override
    public void query(final TipoConsumoRepositoryCallBack callback, Criteria filter) {

        mTipoConsumoSqliteStore.getTipoConsumo(new TipoConsumoStore.GetTipoConsumoStoreCallBack() {
            @Override
            public void onSuccess(List<RestTipoConsumo> tiposConsumos) {
                callback.onSuccess(tiposConsumos);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }, filter);
    }

    @Override
    public List<RestTipoConsumo> fetchDataTipoConsumoIfNewer() {
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
        Call<List<RestTipoConsumo>> getTipoConsumo = service.GetTipoConsumo(Integer.parseInt(mSessionPrefStore.getServicio()),new DateTime(mSessionPrefStore.getUltimaSync()));
        //Call<List<RestTipoCuadrilla>> gettipocuadrilla = service.GetTipoCuadrilla(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()),new DateTime(mSessionPrefStore.getUltimaSync()));
        List<RestTipoConsumo> tipoConsumo = new ArrayList<>();
        try {
            tipoConsumo = getTipoConsumo.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tipoConsumo;
    }

    public List<ContentProviderOperation> providerOperations(List<RestTipoConsumo> tipoConsumos) {
        return mTipoConsumoSqliteStore.replicarServidor(tipoConsumos);
    }
}
