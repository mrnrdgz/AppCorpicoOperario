package ar.com.corpico.appcorpico.login.data;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import ar.com.corpico.appcorpico.login.domain.entity.ErrorApiLogin;
import ar.com.corpico.appcorpico.login.domain.entity.PostLogin;
import ar.com.corpico.appcorpico.login.domain.entity.Session;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SessionsCloudStore implements SessionsStore {
    private static SessionsCloudStore INSTANCE = null;

    private SessionsCloudStore() {
    }

    public static SessionsCloudStore get() {
        if (INSTANCE == null) {
            INSTANCE = new SessionsCloudStore();
        }
        return INSTANCE;
    }

    @Override
    public void getSessionByUserCredentials(final String userCode,
                                            final String password,
                                            final GetCallback callback) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http://192.178.1.203:1052/")
                .baseUrl(ApiServiceOrders.URL_BASE)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServiceLogin service = retrofit.create(ApiServiceLogin.class);
        Call<PostLogin> postLogin = service.getToken(userCode,password,"password");
        postLogin.enqueue(new Callback<PostLogin>() {
            @Override
            public void onResponse(Call<PostLogin> call, Response<PostLogin> response) {
                switch (response.code()) {
                    case 200:
                        if (response.body().getRol().equals("Admin")  || response.body().getRol().equals("Supervisor")) {
                            Session newSession = new Session(response.body().getUserCode(),
                                                            response.body().getUserName(),
                                                            response.body().getAccessToken(),
                                                            response.body().getRol(),
                                                            response.body().getUserServicio(),
                                                            response.body().getUserSector(),
                                                            response.body().getUserOperario(),
                                                            response.body().getUserNombre(),
                                                            response.body().getUserApellido());

                            callback.onSucess(newSession);
                        } else{
                            callback.onError("El usuario no tiene permisos para esta aplicación");
                        }
                        break;
                    /*case 401:

                        break;*/
                    default:
                        if (response.errorBody().contentType().subtype().equals("json")){
                            Gson gson = new Gson();
                            ErrorApiLogin errorApiLogin;
                            try {
                                errorApiLogin = gson.fromJson(response.errorBody().string(), ErrorApiLogin.class);
                            } catch (IOException e) {
                                e.printStackTrace();
                                errorApiLogin = new ErrorApiLogin();
                                errorApiLogin.setErrorDescripcion("Error en el servidor");
                            }
                            callback.onError (errorApiLogin.getErrorDescripcion());
                        }
                        break;
                }

            }
            @Override
            public void onFailure(Call<PostLogin> call, Throwable t) {
                Log.e("SessionsCloudStore","Error Retrofit: "+t.getMessage());
                callback.onError("Error al realizar la conexión");
            }
        });
    }

    @Override
    public void save(Session session) {
        // No-op
    }

    @Override
    public void destroy() {

    }
}
