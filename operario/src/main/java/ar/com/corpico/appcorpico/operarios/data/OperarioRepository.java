package ar.com.corpico.appcorpico.operarios.data;

import android.content.ContentProviderOperation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.operarios.data.entity.RestOperario;
import ar.com.corpico.appcorpico.operarios.domain.entity.Operario;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class OperarioRepository implements IOperarioRepository {
    private static OperarioRepository operarioRepository;

    // Relaciones de composición
    private OperarioRestStore mOperarioRestStore;
    private OperarioSqliteStore mOperarioSqliteStore;
    private SessionsPrefsStore mSessionPrefStore;


    private OperarioRepository(OperarioRestStore operarioRestStore, OperarioSqliteStore operarioSqliteStore, SessionsPrefsStore sessionPrefStore) {
        mOperarioRestStore = Preconditions.checkNotNull(operarioRestStore,
                "La fuente de datos rest de Operarios no puede ser null");
        mOperarioSqliteStore = Preconditions.checkNotNull(operarioSqliteStore);
        mSessionPrefStore = Preconditions.checkNotNull(sessionPrefStore);

    }

    public static OperarioRepository getInstance(OperarioRestStore operarioRestStore, OperarioSqliteStore operarioSqliteStore, SessionsPrefsStore sessionPrefStore) {
        if (operarioRepository == null) {
            operarioRepository = new OperarioRepository(operarioRestStore, operarioSqliteStore, sessionPrefStore);
        }
        return operarioRepository;
    }

    @Override
    public void query(final OperarioRepositoryCallBack callback, int sector) {

        mOperarioSqliteStore.getOperario(new OperarioStore.GetOperarioStoreCallBack() {
            @Override
            public void onSuccess(List<Operario> operarios) {
                callback.onSuccess(operarios);
            }
            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }, sector);
    }

    @Override
    public void add(final AddOperarioRepositoryCallBack callback, List<Integer> operario,int tipoCuadrillaId, int servicio, int sector) {
        mOperarioSqliteStore.addOperario(new OperarioStore.AddOperarioStoreCallBack() {
            @Override
            public void onSuccess() {

                callback.onSuccess();
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }, operario,tipoCuadrillaId, servicio, sector);
    }
    @Override
    public List<RestOperario> fetchDataOperarioIfNewer() {
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
        Call<List<RestOperario>> getOperario = service.GetOperario(Integer.parseInt(mSessionPrefStore.getServicio()),new DateTime(mSessionPrefStore.getUltimaSync()));
        //Call<List<RestTipoCuadrilla>> gettipocuadrilla = service.GetTipoCuadrilla(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()),new DateTime(mSessionPrefStore.getUltimaSync()));
        List<RestOperario> operario = new ArrayList<>();
        try {
            operario = getOperario.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return operario;
    }

    public List<ContentProviderOperation> providerOperations(List<RestOperario> operarios) {
        return mOperarioSqliteStore.replicarServidor(operarios);
    }
}
