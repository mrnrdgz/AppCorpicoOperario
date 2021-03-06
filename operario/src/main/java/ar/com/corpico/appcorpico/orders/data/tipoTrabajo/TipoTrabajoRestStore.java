package ar.com.corpico.appcorpico.orders.data.tipoTrabajo;

import com.google.gson.Gson;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;

import ar.com.corpico.appcorpico.login.domain.entity.ErrorApiLogin;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoTrabajo;
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

public class TipoTrabajoRestStore implements TipoTrabajoStore {
    private static TipoTrabajoRestStore INSTANCE;

    private TipoTrabajoRestStore() {
    }

    public static TipoTrabajoRestStore getInstance(){
        if(INSTANCE==null){
            INSTANCE = new TipoTrabajoRestStore();
        }
        return INSTANCE;
    }

    @Override
    public void getTipoTrabajo(final GetTipoTrabajoStoreCallBack callback, final Criteria filter) {
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
        Call<List<RestTipoTrabajo>> gettipotrabajo = service.GetTipoTrabajo(3,2,dt.toDateTime());
        gettipotrabajo.enqueue(new Callback<List<RestTipoTrabajo>>() {
            @Override
            public void onResponse(Call<List<RestTipoTrabajo>> call, Response<List<RestTipoTrabajo>> response) {
                switch (response.code()) {
                    case 200:
                        List<RestTipoTrabajo> ListTipoTrabajoResponse = response.body();
                        callback.onSuccess(filter.match(ListTipoTrabajoResponse));
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
            public void onFailure(Call<List<RestTipoTrabajo>> call, Throwable t) {
                callback.onError(t.getMessage().toString());
            }
        });
    }
}
