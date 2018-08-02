package ar.com.corpico.appcorpico.orders.data.orders;

import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.os.RemoteException;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico._external.retrofit.ServiceGenerator;
import ar.com.corpico.appcorpico.home.data.Cuadrilla_Home;
import ar.com.corpico.appcorpico.home.data.Estados_Home;
import ar.com.corpico.appcorpico.home.data.TiposTrabajo_Home;
import ar.com.corpico.appcorpico.home.data.Zonas_Home;
import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orderdetailimages.FotoOrden;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import ar.com.corpico.appcorpico.orders.data.entity.PostEtapa;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestEtapa;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestOrder;
import ar.com.corpico.appcorpico.orders.data.entity.sql.OrderSqlOrderMapper;
import ar.com.corpico.appcorpico.orders.domain.Query;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import retrofit2.Call;

/**
 * Created by Administrador on 07/01/2017.
 */

public class OrdersRepository implements IOrdersRepository {
    private static OrdersRepository repository;

    // Relaciones de composición
    private OrdersRestStore mOrdenesRestStore;
    private OrdersSqliteStore mOrdersSqliteSotre;
    private SessionsPrefsStore mSessionPrefStore;
    private OrderSqlOrderMapper mOrderSqlMapper;
    //Todo mapper hacerlo como singleton y pasarlo como parametro


    private OrdersRepository(OrdersRestStore ordersRestStore, OrdersSqliteStore ordersSqliteStore, SessionsPrefsStore sessionsPrefsStore, OrderSqlOrderMapper orderSqlMapper) {
        mOrdenesRestStore = Preconditions.checkNotNull(ordersRestStore,"La fuente de datos rest de ordenes no puede ser null");
        mOrdersSqliteSotre = Preconditions.checkNotNull(ordersSqliteStore);
        mSessionPrefStore = Preconditions.checkNotNull(sessionsPrefsStore);
        mOrderSqlMapper = Preconditions.checkNotNull(orderSqlMapper,"OrderSqlMapper no puede ser null");

    }

    public static OrdersRepository getInstance(OrdersRestStore ordersRestStore, OrdersSqliteStore orderSqlStore, SessionsPrefsStore sessionsPrefsStore,OrderSqlOrderMapper orderSqlMapper) {
        if (repository == null) {
            repository = new OrdersRepository(ordersRestStore, orderSqlStore,sessionsPrefsStore, orderSqlMapper);
        }
        return repository;
    }


    public void queryOrderDetail(final OrdersRepositoryCallback callback, Integer orden,final Query query /*, llamarAlServidor*/) {
        /**
         * Estrategia:
         * 1. Se consuta primero el servicio REST
         * 2. Guardar los datos del servidor en SQLite
         * 3. Enviar los datos al invocador
         */

        // if(llamarAlServidor)
        mOrdersSqliteSotre.getOrder(
                new OrderStore.GetCallback() {
                    @Override
                    public void onSuccess(List<Order> orders) {
                        // TODO: aca puede ser que no hagas todo juntos q los vaya haciendo de a una orden?
                        //List<Order> orders = new ArrayList<>();
                        callback.onSuccess(orders.get(0));
                    }

                    @Override
                    public void onError(String error) {
                        callback.onError(error);
                    }
                }, orden,
                query);
    }

    public void queryFotoOrder(final FotoOrdersRepositoryCallback callback,Integer orden, final Query query /*, llamarAlServidor*/) {
        /**
         * Estrategia:
         * 1. Se consuta primero el servicio REST
         * 2. Guardar los datos del servidor en SQLite
         * 3. Enviar los datos al invocador
         */

        // if(llamarAlServidor)
        mOrdersSqliteSotre.getFotoOrder(
                new OrderStore.GetFotoCallback() {
                    @Override
                    public void onSuccess(List<FotoOrden> fotos) {
                        // TODO: aca puede ser que no hagas todo juntos q los vaya haciendo de a una orden?
                        //List<Order> orders = new ArrayList<>();
                        callback.onSuccess(fotos);
                    }

                    @Override
                    public void onError(String error) {
                        callback.onError(error);
                    }
                },orden,
                query);
    }

    @Override
    public void query(final OrdersRepositoryCallbacks callback, final Query query /*, llamarAlServidor*/) {
        /**
         * Estrategia:
         * 1. Se consuta primero el servicio REST
         * 2. Guardar los datos del servidor en SQLite
         * 3. Enviar los datos al invocador
         */

        // if(llamarAlServidor)
        mOrdersSqliteSotre.getOrders(
                new OrderStore.GetCallbacks() {
                    @Override
                    public void onSuccess(List<Order> orders) {
                        // TODO: aca puede ser que no haga todo juntos q los vaya haciendo de a una orden?
                        //List<Order> orders = new ArrayList<>();
                        //orders = mOrderSqlMapper.transform(orderssql);
                        callback.onSuccess(orders);
                    }

                    @Override
                    public void onError(String error) {
                        callback.onError(error);
                    }
                },
                query);
    }

    @Override
    public void queryCuadrillaHome(final CuadrillaHomeRepositoryCallBack callback, int servicio, int sector) {
        mOrdersSqliteSotre.queryCuadrillaHome(new OrderStore.GetCuadrillaHomeStoreCallBack() {
            @Override
            public void onSuccess(List<Cuadrilla_Home> cuadrilla_homes) {
                callback.onSuccess(cuadrilla_homes);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        },servicio,sector);
    }

    @Override
    public void queryTipoTrabajoHome(final TipoTrabajoHomeRepositoryCallBack callback, int servicio, int sector) {
        mOrdersSqliteSotre.queryTipoTrabajoHome(new OrderStore.GetTiposTrabajoHomeStoreCallBack() {
            @Override
            public void onSuccess(List<TiposTrabajo_Home> tiposTrabajo_homes) {
                callback.onSuccess(tiposTrabajo_homes);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        },servicio,sector);
    }

    @Override
    public void queryEstadoHome(final EstadoHomeRepositoryCallBack callback, int servicio, int sector) {
        mOrdersSqliteSotre.queryEstadosHome(new OrderStore.GetEstadosHomeStoreCallBack() {
            @Override
            public void onSuccess(List<Estados_Home> estados_home) {
                callback.onSuccess(estados_home);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        },servicio,sector);
    }

    @Override
    public void queryZonaHome(final ZonaHomeRepositoryCallBack callback, int servicio, int sector) {
        mOrdersSqliteSotre.queryZonasHome(new OrderStore.GetZonasHomeStoreCallBack() {
            @Override
            public void onSuccess(List<Zonas_Home> zonas_home) {
                callback.onSuccess(zonas_home);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        },servicio,sector);
    }

    @Override
    public void addOrderState(String estado, ArrayList<Integer> numero, String observacion,Integer tipoCuadrillaId, Double latitud, Double longitud) {
        mOrdersSqliteSotre.addOrderEtape(estado, numero, observacion,tipoCuadrillaId,latitud,longitud);
    }

    @Override
    public void findCuadrilla(final CuadrillaxTipoRepositoryCallBack callback, Criteria filter) {
        OrderStore.GetCuadrillaxTipoStoreCallBack callback1 = new OrderStore.GetCuadrillaxTipoStoreCallBack() {
            @Override
            public void onSuccess(List<Tipo_Trabajo> tiposcuadrilla) {
                // TODO: Guardar datos en SQLite. Posible método save()/insert()/add()
                callback.onSuccess(tiposcuadrilla);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        };

        mOrdenesRestStore.getCuadrillaxTipo(callback1, filter);
    }

    @Override
    public List<RestOrder> fetchDataOrdersIfNewer() {
        ApiServiceOrders service = ServiceGenerator.createService(ApiServiceOrders.class);

        Call<List<RestOrder>> getOrdenOperativa = service.GetOrdenOperativa(
                Integer.parseInt(mSessionPrefStore.getServicio()),
                Integer.parseInt(mSessionPrefStore.getSector()),
                new DateTime(mSessionPrefStore.getUltimaSync()),
                mSessionPrefStore.esPrimeraCarga());

        List<RestOrder> listOrders = null;
        try {
            listOrders = new ArrayList<>();
            listOrders = getOrdenOperativa.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listOrders;
    }

    @Override
    public boolean SyncRemota() throws RemoteException, OperationApplicationException {
        //TODO: aca hago las 4 operaciones de de syncronizacio (obtentiendo los registros sucios de etapas,turnos y ordenes)
        boolean datosModificados=false;

        //Obtengo las etapas insertadas (Registros sucios)
        List <RestEtapa>  etapasInsertadas = mOrdersSqliteSotre.obtenerEtapasInsertadas();
        if (etapasInsertadas != null){
            List<PostEtapa> resultEtapasInsertadasRemotas = mOrdenesRestStore.enviarEtapasInsertadas(etapasInsertadas);
            if (resultEtapasInsertadasRemotas != null){
                mOrdersSqliteSotre.actualizarEtapasId(resultEtapasInsertadasRemotas);
            }
        }

       /* //Obtengo las orders modificadas (Registros sucios)
        List<RestOrder> ordersModificadas = mOrdersSqliteSotre.obtenerOrdersModificadas();
        if (ordersModificadas != null){
            boolean resultOrdersModificadasRemotas = mOrdenesRestStore.enviarOrdersModificadas(ordersModificadas);
            if(resultOrdersModificadasRemotas){
                mOrdersSqliteSotre.actualizarOrders(ordersModificadas);
            }
        }*/
        //todo: me faltaria hacer lo mismo con las fotos y con los turnos...mas la tabla de medidores
        return true;
    }

   /* @Override
    public void query(final EstadoOperativoRepositoryCallBack callback, Criteria filter) {
        OrderStore.GetEstadoOperativoStoreCallBack callback1 = new OrderStore.GetEstadoOperativoStoreCallBack() {
            @Override
            //public void onSuccess(List<Tipo_Cuadrilla> tipoCuadrilla) {
            public void onSuccess(List<RestTipoCuadrilla> tipoCuadrilla) {
                // TODO: Guardar datos en SQLite. Posible método save()/insert()/add()
                callback.onSuccess(tipoCuadrilla);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        };

        mOrdenesRestStore.getMedidor(callback1, filter);
    }*/

   /* @Override
    public void query(final TipoTrabajoCuadrillaRepositoryCallBack callback, Criteria filter) {
        OrderStore.GetTipoTrabajoCuadrillaStoreCallBack callback1 = new OrderStore.GetTipoTrabajoCuadrillaStoreCallBack() {
            @Override
            //public void onSuccess(List<Tipo_Trabajo> tipoTrabajo) {
            public void onSuccess(List<Tipo_Trabajo> tipoTrabajoCuadrilla) {
                // TODO: Guardar datos en SQLite. Posible método save()/insert()/add()
                callback.onSuccess(tipoTrabajoCuadrilla);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        };

        mOrdenesRestStore.getTipoTrabajoCuadrilla(callback1, filter);
    }*/

    /*@Override
    //public void query(final FotoRepositoryCallBack callback, Criteria filter) {
    public void query(final FotoRepositoryCallBack callback) {
        OrderStore.GetFotoStoreCallBack callback1 = new OrderStore.GetFotoStoreCallBack() {
            @Override
            public void onSuccess(List<Zona> zona) {
                // TODO: Guardar datos en SQLite. Posible método save()/insert()/add()
                callback.onSuccess(zona);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        };

        mOrdenesRestStore.getZona(callback1);
    }*/

    @Override
    public void addTurno(Integer numero, String turno){//DateTime turno) {
        mOrdersSqliteSotre.addTurno(numero, turno);
    }
    @Override
    public void cancelTurno(Integer numero){
        mOrdersSqliteSotre.cancelTurno(numero);
    }

    public List<ContentProviderOperation> providerOperations(List<RestOrder> orders) {
        return mOrdersSqliteSotre.replicarServidor(orders);

    }
}
