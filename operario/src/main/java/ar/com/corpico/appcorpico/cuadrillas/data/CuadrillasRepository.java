package ar.com.corpico.appcorpico.cuadrillas.data;

import android.content.ContentProviderOperation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.cuadrillas.data.entity.RestCuadrilla;
import ar.com.corpico.appcorpico.cuadrillas.domain.entity.Cuadrilla;
import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class CuadrillasRepository implements ICuadrillasRepository {
    private static CuadrillasRepository cuadrillasrepository;

    // Relaciones de composición
    private CuadrillaRestStore mCuadrillaRestStore;
    private CuadrillaSqliteStore mCuadrillaSqliteStore;
    private SessionsPrefsStore mSessionPrefStore;


    private CuadrillasRepository(CuadrillaRestStore cuadrillaRestStore,
                                 CuadrillaSqliteStore cuadrillaSqliteStore,
                                 SessionsPrefsStore sessionPrefStore) {
        mCuadrillaRestStore = Preconditions.checkNotNull(cuadrillaRestStore,
                "La fuente de datos rest de cuadrillas no puede ser null");
        mCuadrillaSqliteStore = Preconditions.checkNotNull(cuadrillaSqliteStore);
        mSessionPrefStore = Preconditions.checkNotNull(sessionPrefStore);

    }

    public static CuadrillasRepository getInstance(CuadrillaRestStore cuadrillaRestStore,
                                                   CuadrillaSqliteStore cuadrillaSqliteStore,
                                                   SessionsPrefsStore sessionPrefStore) {
        if (cuadrillasrepository == null) {
            cuadrillasrepository = new CuadrillasRepository(cuadrillaRestStore, cuadrillaSqliteStore, sessionPrefStore);
        }
        return cuadrillasrepository;
    }

    @Override
    public void query(final CuadrillaRepositoryCallBack callback, Criteria filter) {

        mCuadrillaSqliteStore.getCuadrilla(new CuadrillaStore.GetCuadrillaStoreCallBack() {
            @Override
            public void onSuccess(List<Cuadrilla> Cuadrilla) {
                callback.onSuccess(Cuadrilla);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }, filter);
    }

    @Override
    public void remove(final RemoveOperarioRepositoryCallBack callback, int operario) {
        mCuadrillaSqliteStore.removeOperario(new CuadrillaStore.RemoveOperarioStoreCallBack() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }, operario);
    }



    @Override
    public List<RestCuadrilla> fetchDataCuadrillaIfNewer() {
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
        Call<List<RestCuadrilla>> getcuadrilla = service.GetCuadrilla(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()),new DateTime(mSessionPrefStore.getUltimaSync()));
        List<RestCuadrilla> cuadrilla = new ArrayList<>();
        try {
            cuadrilla = getcuadrilla.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cuadrilla;
    }

    public List<ContentProviderOperation> providerOperations(List<RestCuadrilla> Cuadrillas) {
        return mCuadrillaSqliteStore.replicarServidor(Cuadrillas);
    }
}
