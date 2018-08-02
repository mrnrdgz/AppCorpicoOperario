package ar.com.corpico.appcorpico.orders.data.empresaContratista;

import android.content.ContentProviderOperation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestEmpresaContratista;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class EmpresaContratistaRepository implements IEmpresaContratistaRepository {
    private static EmpresaContratistaRepository turnosRepository;

    // Relaciones de composición
    private EmpresaContratistaRestStore mEmpresaContratistaRestStore;
    private EmpresaContratistaSqliteStore mEmpresaContratistaSqliteStore;
    private SessionsPrefsStore mSessionPrefStore;


    private EmpresaContratistaRepository(EmpresaContratistaRestStore empresaContratistaRestStore, EmpresaContratistaSqliteStore empresaContratistaSqliteStore, SessionsPrefsStore sessionPrefStore) {
        mEmpresaContratistaRestStore = Preconditions.checkNotNull(empresaContratistaRestStore,
                "La fuente de datos rest de Empresas Contratistas no puede ser null");
        mEmpresaContratistaSqliteStore = Preconditions.checkNotNull(empresaContratistaSqliteStore);
        mSessionPrefStore = Preconditions.checkNotNull(sessionPrefStore);

    }

    public static EmpresaContratistaRepository getInstance(EmpresaContratistaRestStore empresaContratistaRestStore, EmpresaContratistaSqliteStore empresaContratistaSqliteStore, SessionsPrefsStore sessionPrefStore) {
        if (turnosRepository == null) {
            turnosRepository = new EmpresaContratistaRepository(empresaContratistaRestStore, empresaContratistaSqliteStore, sessionPrefStore);
        }
        return turnosRepository;
    }

    @Override
    public void query(final EmpresaContratistaRepositoryCallBack callback, Criteria filter) {

        mEmpresaContratistaSqliteStore.getEmpresaContratista(new EmpresaContratistaStore.GetEmpresaContratistaStoreCallBack() {
            @Override
            public void onSuccess(List<RestEmpresaContratista> empresaContratista) {
                callback.onSuccess(empresaContratista);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }, filter);
    }

    @Override
    public List<RestEmpresaContratista> fetchDataEmpresaContratistaIfNewer() {
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
        Call<List<RestEmpresaContratista>> getempresacontratista = service.GetEmpresaContratista(Integer.parseInt(mSessionPrefStore.getServicio()), new DateTime(mSessionPrefStore.getUltimaSync()));
        //Call<List<RestTipoCuadrilla>> gettipocuadrilla = service.GetTipoCuadrilla(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()),new DateTime(mSessionPrefStore.getUltimaSync()));
        List<RestEmpresaContratista> empresaContratista = new ArrayList<>();
        try {
            empresaContratista = getempresacontratista.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return empresaContratista;
    }

    public List<ContentProviderOperation> providerOperations(List<RestEmpresaContratista> empresaContratista) {
        return mEmpresaContratistaSqliteStore.replicarServidor(empresaContratista);
    }
}
