package ar.com.corpico.appcorpico.orders.data.tipoCuadrilla;

import com.google.gson.Gson;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;

import ar.com.corpico.appcorpico.login.domain.entity.ErrorApiLogin;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoCuadrilla;
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

public class TipoCuadrillaRestStore implements TipoCuadrillaStore {

    private static TipoCuadrillaRestStore INSTANCE;

    private TipoCuadrillaRestStore() {

    }

    public static TipoCuadrillaRestStore getInstance(){
        if(INSTANCE==null){
            INSTANCE = new TipoCuadrillaRestStore();
        }
        return INSTANCE;
    }

    @Override
    public void getTipoCuadrilla(final GetTipoCuadrillaStoreCallBack callback, final Criteria filter) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http://192.178.1.204:1052/")
                .baseUrl("http://192.168.1.34:1052/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        DateTime dt = new DateTime(2010, 10, 18,0,0);
        ApiServiceOrders service = retrofit.create(ApiServiceOrders.class);
        Call<List<RestTipoCuadrilla>> gettipocuadrilla = service.GetTipoCuadrilla(3,2,dt.toDateTime());
        gettipocuadrilla.enqueue(new Callback<List<RestTipoCuadrilla>>() {
            @Override
            public void onResponse(Call<List<RestTipoCuadrilla>> call, Response<List<RestTipoCuadrilla>> response) {
                switch (response.code()) {
                    case 200:
                        List<RestTipoCuadrilla> ListTipoCuadrillaResponse = response.body();
                        callback.onSuccess(filter.match(ListTipoCuadrillaResponse));
                        break;
                    /*case 401:

                        break;*/
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
            public void onFailure(Call<List<RestTipoCuadrilla>> call, Throwable t) {
                callback.onError(t.getMessage().toString());
            }
        });
    }
}
