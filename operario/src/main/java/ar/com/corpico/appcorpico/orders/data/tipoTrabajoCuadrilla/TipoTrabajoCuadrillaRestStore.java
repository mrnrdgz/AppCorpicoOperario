package ar.com.corpico.appcorpico.orders.data.tipoTrabajoCuadrilla;

import com.google.gson.Gson;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;

import ar.com.corpico.appcorpico.login.domain.entity.ErrorApiLogin;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoTrabajoCuadrilla;
import ar.com.corpico.appcorpico.orders.data.tipoTrabajo.TipoTrabajoRestStore;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 12/03/2018.
 */

public class TipoTrabajoCuadrillaRestStore implements TipoTrabajoCuadrillaStore {
    private static TipoTrabajoCuadrillaRestStore INSTANCE;

    private TipoTrabajoCuadrillaRestStore() {
    }

    public static TipoTrabajoCuadrillaRestStore getInstance(){
        if(INSTANCE==null){
            INSTANCE = new TipoTrabajoCuadrillaRestStore();
        }
        return INSTANCE;
    }
    @Override
    public void getTipoTrabajoCuadrilla(final GetTipoTrabajoCuadrillaStoreCallBack callback, final Criteria filter) {
        //callback.onSuccess(filter.match(mFakeRestTipo_Trabajo));
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.178.1.203:1052/")
                //.baseUrl("http://192.168.1.34:1052/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        DateTime dt = new DateTime(2010, 10, 18,0,0);
        ApiServiceOrders service = retrofit.create(ApiServiceOrders.class);
        Call<List<RestTipoTrabajoCuadrilla>> gettipotrabajocuadrilla = service.GetTipoTrabajoCuadrilla(3,2,dt.toDateTime());
        gettipotrabajocuadrilla.enqueue(new Callback<List<RestTipoTrabajoCuadrilla>>() {
            @Override
            public void onResponse(Call<List<RestTipoTrabajoCuadrilla>> call, Response<List<RestTipoTrabajoCuadrilla>> response) {
                switch (response.code()) {
                    case 200:
                        List<RestTipoTrabajoCuadrilla> ListTipoTrabajoCuadrillaResponse = response.body();
                        callback.onSuccess(filter.match(ListTipoTrabajoCuadrillaResponse));
                        break;
                    case 401:

                        break;
                    default:
                        if (response.errorBody().contentType().subtype().equals("json")) {
                            Gson gson = new Gson();
                            ErrorApiLogin errorApiLogin = null;
                            try {
                                errorApiLogin = gson.fromJson(response.errorBody().string(), ErrorApiLogin.class);
                            } catch (IOException e) {
                                e.printStackTrace();
                                errorApiLogin = new ErrorApiLogin();
                            }
                            callback.onError(errorApiLogin.getErrorDescripcion());
                        }
                        break;
                }
            }
            @Override
            public void onFailure(Call<List<RestTipoTrabajoCuadrilla>> call, Throwable t) {
                callback.onError(t.getMessage().toString());
            }
        });
    }
}
