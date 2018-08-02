package ar.com.corpico.appcorpico.orders.data.tipoUsuario;

import android.content.ContentProviderOperation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoUsuario;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class TipoUsuarioRepository implements ITipoUsuarioRepository {
    private static TipoUsuarioRepository tipoUsuarioRepository;

    // Relaciones de composición
    private TipoUsuarioRestStore mTipoUsuarioRestStore;
    private TipoUsuarioSqliteStore mTipoUsuarioSqliteStore;
    private SessionsPrefsStore mSessionPrefStore;


    private TipoUsuarioRepository(TipoUsuarioRestStore tipoUsuarioRestStore, TipoUsuarioSqliteStore tipoUsuarioSqliteStore, SessionsPrefsStore sessionPrefStore) {
        mTipoUsuarioRestStore = Preconditions.checkNotNull(tipoUsuarioRestStore,
                "La fuente de datos rest de Tipo Usuario no puede ser null");
        mTipoUsuarioSqliteStore = Preconditions.checkNotNull(tipoUsuarioSqliteStore);
        mSessionPrefStore = Preconditions.checkNotNull(sessionPrefStore);

    }

    public static TipoUsuarioRepository getInstance(TipoUsuarioRestStore tipoUsuarioRestStore, TipoUsuarioSqliteStore tipoUsuarioSqliteStore, SessionsPrefsStore sessionPrefStore) {
        if (tipoUsuarioRepository == null) {
            tipoUsuarioRepository = new TipoUsuarioRepository(tipoUsuarioRestStore, tipoUsuarioSqliteStore, sessionPrefStore);
        }
        return tipoUsuarioRepository;
    }

    @Override
    public void query(final TipoUsuarioRepositoryCallBack callback, Criteria filter) {

        mTipoUsuarioSqliteStore.getTipoUsuario(new TipoUsuarioStore.GetTipoUsuarioStoreCallBack() {
            @Override
            public void onSuccess(List<RestTipoUsuario> tiposUsuarios) {
                callback.onSuccess(tiposUsuarios);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }, filter);
    }

    @Override
    public List<RestTipoUsuario> fetchDataTipoUsuarioIfNewer() {
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
        Call<List<RestTipoUsuario>> getTipoUsuario = service.GetTipoUsuario(Integer.parseInt(mSessionPrefStore.getServicio()),new DateTime(mSessionPrefStore.getUltimaSync()));
        //Call<List<RestTipoCuadrilla>> gettipocuadrilla = service.GetTipoCuadrilla(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()),new DateTime(mSessionPrefStore.getUltimaSync()));
        List<RestTipoUsuario> tipoUsuario = new ArrayList<>();
        try {
            tipoUsuario = getTipoUsuario.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tipoUsuario;
    }

    public List<ContentProviderOperation> providerOperations(List<RestTipoUsuario> tipoUsuarios) {
        return mTipoUsuarioSqliteStore.replicarServidor(tipoUsuarios);
    }
}
