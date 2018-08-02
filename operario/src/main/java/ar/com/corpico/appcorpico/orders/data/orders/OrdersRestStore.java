package ar.com.corpico.appcorpico.orders.data.orders;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.PostEtapa;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestEtapa;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestOrder;
import ar.com.corpico.appcorpico.orders.domain.Query;
import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
///

/**
 * Created by Administrador on 07/01/2017.
 */

public class OrdersRestStore implements OrderStore {
    private static OrdersRestStore INSTANCE;

    // TODO: Reemplazar esta fuente falsa por una conexión real al servidor
    private static final ArrayList<Order> mFakeRestOrder = new ArrayList<>();
    private static final ArrayList<Tipo_Trabajo> mFakeRestTipo_Trabajo = new ArrayList<>();

    private OrdersRestStore() {
    }

    public static OrdersRestStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrdersRestStore();
        }
        return INSTANCE;
    }

    @Override
    public void getOrders(final GetCallbacks callback, final Query query) {
        //Handler handler = new Handler();

        //handler.postDelayed(new Runnable() {
        // @Override
        // public void run() {
        // TODO: Realizar filtro
        //callback.onSuccess(filter.match(mFakeRestOrder));
                /*callback.onSuccess(Lists.newArrayList(Collections2.filter(mFakeRestOrder, new Predicate<Order>() {
                    @Override
                    public boolean apply(Order input) {
                        return filter.isSatisfiedBy(input);
                    }
                })));*/
        //Query mQuery = new Query(filter,"FechaSolicitud",1,0,0);
        //OrdersSelector ordersSelector = new OrdersSelector(query);
        //callback.onSuccess(ordersSelector.selectListRows(mFakeRestOrder));
        //  }
        //}, 2000);

    }

    @Override
    public void queryCuadrillaHome(GetCuadrillaHomeStoreCallBack callback, int servicio, int sector) {

    }

    @Override
    public void queryTipoTrabajoHome(GetTiposTrabajoHomeStoreCallBack callback, int servicio, int sector) {

    }

    @Override
    public void queryEstadosHome(GetEstadosHomeStoreCallBack callback, int servicio, int sector) {

    }

    @Override
    public void queryZonasHome(GetZonasHomeStoreCallBack callback, int servicio, int sector) {

    }


    @Override
    public void getOrder(GetCallback callback, Integer orden,Query query) {

    }

    @Override
    public void getFotoOrder(GetFotoCallback callback, Integer orden, Query query) {

    }

    @Override
    public void addOrderEtape(String estado, ArrayList<Integer> numero, String observacion,
                              Integer tipoCuadrillaId, Double latitud, Double longitud) {
        /*final Calendar c = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String date = df.format(Calendar.getInstance().getTime());*/
//todo: aca como hago la insercion local...y como aplico lo remoto? uso Content Provider?
        for (Integer number : numero) {
            for (Order order : mFakeRestOrder) {
                if (order.getNumero().equals(number)) {
                    //TODO: PONER EL DATO DEL USUARIO QUE HIZO ESTA ETAPA.
                    Etapa etapa = new Etapa(new DateTime(DateTime.now()), estado, observacion, "");
                    order.addEtapas(etapa);
                }
            }
        }

    }

    @Override
    public void getCuadrillaxTipo(final GetCuadrillaxTipoStoreCallBack callback, final Criteria filter) {
        callback.onSuccess(filter.match(mFakeRestTipo_Trabajo));
    }


    /*@Override
    //TODO: ACA VA LA LLAMADA RETROFIT -  APIREST
    // VER COMO ANALIZO EL TEMA DEL CRITERIA...DEBERA IR?
    public void getMedidor(final GetEstadoOperativoStoreCallBack callback, final Criteria filter) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http://192.178.1.204:1052/")
                .baseUrl("http://192.168.1.44:1052/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        ApiServiceOrders service = retrofit.create(ApiServiceOrders.class);
        Call<List<RestTipoCuadrilla>> gettipocuadrilla = service.GetTipoCuadrilla();
        //todo: llamar con execute no con callback cuando sea sincronizacion...xq ya el servicio hace la llamada asincrona

        /*try {
            List<Orders> ordenes = gettipocuadrilla.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
       /* gettipocuadrilla.enqueue(new Callback<List<RestTipoCuadrilla>>() {
            @Override
            public void onResponse(Call<List<RestTipoCuadrilla>> call, Response<List<RestTipoCuadrilla>> response) {
                switch (response.code()) {
                    case 200:
                        List<RestTipoCuadrilla> ListTipoCuadrillaResponse = response.body();
                        callback.onSuccess(filter.match(ListTipoCuadrillaResponse));
                        break;
                    /*case 401:

                        break;*/
                   /* default:
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
    }*/
    /*@Override
    public void getTipoTrabajoCuadrilla(final GetTipoTrabajoStoreCallBack callback, final Criteria filter) {
        //callback.onSuccess(filter.match(mFakeRestTipo_Trabajo));
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http://192.178.1.204:1052/")
                .baseUrl("http://192.168.1.44:1052/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        ApiServiceOrders service = retrofit.create(ApiServiceOrders.class);
        Call<List<Tipo_Trabajo>> gettipotrabajocuadrilla = service.RestTipoTrabajoCuadrilla();
        gettipotrabajocuadrilla.enqueue(new Callback<List<Tipo_Trabajo>>() {
            @Override
            public void onResponse(Call<List<Tipo_Trabajo>> call, Response<List<Tipo_Trabajo>> response) {
                switch (response.code()) {
                    case 200:
                        List<Tipo_Trabajo> ListTipoTrabajoCuadrillaResponse = response.body();
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
            public void onFailure(Call<List<Tipo_Trabajo>> call, Throwable t) {
                callback.onError(t.getMessage().toString());
            }
        });
    }*/
    /*@Override
    public void getZona(GetFotoStoreCallBack callback) {
        callback.onSuccess(mFakeRestZona);
    }*/

    @Override
    public void addTurno(Integer numero, String turno) {//DateTime turno) {
        for (Order order : mFakeRestOrder) {
            //TODO: VER
                /*if (order.getNumero()== numero) {
                    order.setTurno(turno);
                }*/
        }
    }

    @Override
    public void cancelTurno(Integer numero) {

    }

    public List<PostEtapa> enviarEtapasInsertadas(List<RestEtapa> etapasInsertadas) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiServiceOrders.URL_BASE)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServiceOrders service = retrofit.create(ApiServiceOrders.class);
        Call<List<PostEtapa>> postEtapas = service.PostEtapa(etapasInsertadas);
        List<PostEtapa> etapasInsertadasId = new ArrayList<>();
        try {
            etapasInsertadasId = postEtapas.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return etapasInsertadasId;
    }
    public boolean enviarOrdersModificadas(List<RestOrder> orderModificadas) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiServiceOrders.URL_BASE)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServiceOrders service = retrofit.create(ApiServiceOrders.class);
        Call<Boolean> postOrders = service.PostOrdenOperativa(orderModificadas);
        boolean ordersModificadasServer = false;
        try {
            ordersModificadasServer = postOrders.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ordersModificadasServer;
    }
}
