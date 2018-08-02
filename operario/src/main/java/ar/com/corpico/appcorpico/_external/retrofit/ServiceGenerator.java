package ar.com.corpico.appcorpico._external.retrofit;

import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(ApiServiceOrders.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static HttpLoggingInterceptor loggingInterceptor =
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder client = new OkHttpClient.Builder();

    public static <S> S createService(Class<S> service){

        if(!client.interceptors().contains(loggingInterceptor)) {
            client.addInterceptor(loggingInterceptor);
            builder.client(client.build());
            retrofit = builder.build();
        }

        return retrofit.create(service);
    }
}
