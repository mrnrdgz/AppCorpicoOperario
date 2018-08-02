package ar.com.corpico.appcorpico.orders.data.cliente;

import android.content.ContentProviderOperation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestCliente;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class ClientesRepository implements IClientesRepository {
    private static ClientesRepository clientesrepository;

    // Relaciones de composición
    private ClienteRestStore mClienteRestStore;
    private ClienteSqliteStore mClienteSqliteStore;
    private SessionsPrefsStore mSessionPrefStore;


    private ClientesRepository(ClienteRestStore clienteRestStore, ClienteSqliteStore clienteSqliteStore, SessionsPrefsStore sessionPrefStore) {
        mClienteRestStore = Preconditions.checkNotNull(clienteRestStore,
                "La fuente de datos rest de clientes no puede ser null");
        mClienteSqliteStore = Preconditions.checkNotNull(clienteSqliteStore);
        mSessionPrefStore = Preconditions.checkNotNull(sessionPrefStore);

    }

    public static ClientesRepository getInstance(ClienteRestStore clienteRestStore, ClienteSqliteStore clienteSqliteStore, SessionsPrefsStore sessionPrefStore) {
        if (clientesrepository == null) {
            clientesrepository = new ClientesRepository(clienteRestStore, clienteSqliteStore, sessionPrefStore);
        }
        return clientesrepository;
    }

    @Override
    public void query(final ClienteRepositoryCallBack callback, Criteria filter) {

        mClienteSqliteStore.getCliente(new ClienteStore.GetClienteStoreCallBack() {
            @Override
            public void onSuccess(List<RestCliente> cliente) {
                callback.onSuccess(cliente);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }, filter);
    }

    @Override
    public List<RestCliente> fetchDataClienteIfNewer() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        //OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …
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

        // add logging as last interceptor
        /*httpClient.addInterceptor(logging);  // <-- this is the important line!

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http://192.178.1.203:1052/")
                .baseUrl("http://192.168.1.34:1052/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();*/

        ApiServiceOrders service = retrofit.create(ApiServiceOrders.class);
        Call<List<RestCliente>> getCliente = service.GetCliente(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()),new DateTime(mSessionPrefStore.getUltimaSync()));
        //Call<List<RestCliente>> getCliente = service.GetCliente(new DateTime(mSessionPrefStore.getUltimaSync()));
        List<RestCliente> cliente = new ArrayList<>();
        try {
            cliente = getCliente.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cliente;
    }

    public List<ContentProviderOperation> providerOperations(List<RestCliente> cliente) {
        return mClienteSqliteStore.replicarServidor(cliente);
    }
}
