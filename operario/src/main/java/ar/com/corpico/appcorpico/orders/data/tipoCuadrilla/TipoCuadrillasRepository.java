package ar.com.corpico.appcorpico.orders.data.tipoCuadrilla;

import android.content.ContentProviderOperation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.cuadrillas.data.CuadrillaStore;
import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoCuadrilla;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class TipoCuadrillasRepository implements ITipoCuadrillasRepository {
    private static TipoCuadrillasRepository tipocuadrillasrepository;

    // Relaciones de composición
    private TipoCuadrillaRestStore mTipoCuadrillaRestStore;
    private TipoCuadrillaSqliteStore mTipoCuadrillaSqliteStore;
    private SessionsPrefsStore mSessionPrefStore;


    private TipoCuadrillasRepository(TipoCuadrillaRestStore tipocuadrillaRestStore,
                                 TipoCuadrillaSqliteStore tipocuadrillaSqliteStore,
                                 SessionsPrefsStore sessionPrefStore) {
        mTipoCuadrillaRestStore = Preconditions.checkNotNull(tipocuadrillaRestStore,
                "La fuente de datos rest de cuadrillas no puede ser null");
        mTipoCuadrillaSqliteStore = Preconditions.checkNotNull(tipocuadrillaSqliteStore);
        mSessionPrefStore = Preconditions.checkNotNull(sessionPrefStore);

    }

    public static TipoCuadrillasRepository getInstance(TipoCuadrillaRestStore tipocuadrillaRestStore,
                                                   TipoCuadrillaSqliteStore tipocuadrillaSqliteStore,
                                                   SessionsPrefsStore sessionPrefStore) {
        if (tipocuadrillasrepository == null) {
            tipocuadrillasrepository = new TipoCuadrillasRepository(tipocuadrillaRestStore, tipocuadrillaSqliteStore, sessionPrefStore);
        }
        return tipocuadrillasrepository;
    }

    @Override
    public void query(final TipoCuadrillaRepositoryCallBack callback, Criteria filter) {

        mTipoCuadrillaSqliteStore.getTipoCuadrilla(new TipoCuadrillaStore.GetTipoCuadrillaStoreCallBack() {
            @Override
            public void onSuccess(List<Tipo_Cuadrilla> tipoCuadrilla) {
                callback.onSuccess(tipoCuadrilla);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }, filter);
    }

    @Override
    public List<RestTipoCuadrilla> fetchDataCuadrillaIfNewer() {
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
        Call<List<RestTipoCuadrilla>> gettipocuadrilla = service.GetTipoCuadrilla(Integer.parseInt(mSessionPrefStore.getServicio()),Integer.parseInt(mSessionPrefStore.getSector()),new DateTime(mSessionPrefStore.getUltimaSync()));
        List<RestTipoCuadrilla> tipoCuadrilla = new ArrayList<>();
        try {
            tipoCuadrilla = gettipocuadrilla.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tipoCuadrilla;
    }

    public List<ContentProviderOperation> providerOperations(List<RestTipoCuadrilla> tipoCuadrillas) {
        return mTipoCuadrillaSqliteStore.replicarServidor(tipoCuadrillas);
    }
}
