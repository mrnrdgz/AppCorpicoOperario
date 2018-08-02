package ar.com.corpico.appcorpico.orders.data.estado;

import android.content.ContentProviderOperation;

import com.google.common.base.Preconditions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestEstado;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class EstadoRepository implements IEstadoRepository {
    private static EstadoRepository tipocuadrillasrepository;

    // Relaciones de composición
    private EstadoRestStore mEstadoRestStore;
    private EstadoSqliteStore mEstadoSqliteStore;
    private SessionsPrefsStore mSessionPrefStore;


    private EstadoRepository(EstadoRestStore estadoRestStore,
                             EstadoSqliteStore estadoSqliteStore,
                             SessionsPrefsStore sessionPrefStore) {
        mEstadoRestStore = Preconditions.checkNotNull(estadoRestStore,
                "La fuente de datos rest de Estados no puede ser null");
        mEstadoSqliteStore = Preconditions.checkNotNull(estadoSqliteStore,"La fuente de datos sqlite de Estados no puede ser null");
        mSessionPrefStore = Preconditions.checkNotNull(sessionPrefStore);

    }

    public static EstadoRepository getInstance(EstadoRestStore estadoRestStore,
                                               EstadoSqliteStore estadoSqliteStore,
                                               SessionsPrefsStore sessionPrefStore) {
        if (tipocuadrillasrepository == null) {
            tipocuadrillasrepository = new EstadoRepository(estadoRestStore, estadoSqliteStore, sessionPrefStore);
        }
        return tipocuadrillasrepository;
    }

    @Override
    public void query(final EstadoRepositoryCallBack callback) {

        mEstadoSqliteStore.getEstado(new EstadoStore.GetEstadoStoreCallBack() {
            @Override
            public void onSuccess(List<RestEstado> estado) {
                callback.onSuccess(estado);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public List<RestEstado> fetchDataEstadoIfNewer() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http://192.178.1.203:1052/")
                .baseUrl(ApiServiceOrders.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        ApiServiceOrders service = retrofit.create(ApiServiceOrders.class);

        //Call<List<RestTipoCuadrilla>> gettipocuadrilla = service.GetTipoCuadrilla(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()), new DateTime("2017-12-29"));
        Call<List<RestEstado>> getestado = service.GetEstado();
        List<RestEstado> estado = new ArrayList<>();
        try {
            estado = getestado.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return estado;
    }

    public List<ContentProviderOperation> providerOperations(List<RestEstado> estados) {
        return mEstadoSqliteStore.replicarServidor(estados);
    }
}
