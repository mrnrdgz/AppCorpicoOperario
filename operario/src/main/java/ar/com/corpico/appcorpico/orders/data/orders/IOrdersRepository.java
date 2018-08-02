package ar.com.corpico.appcorpico.orders.data.orders;

import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.home.data.Cuadrilla_Home;
import ar.com.corpico.appcorpico.home.data.Estados_Home;
import ar.com.corpico.appcorpico.home.data.TiposTrabajo_Home;
import ar.com.corpico.appcorpico.home.data.Zonas_Home;
import ar.com.corpico.appcorpico.orderdetailimages.FotoOrden;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestOrder;
import ar.com.corpico.appcorpico.orders.domain.Query;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 07/01/2017.
 */

public interface IOrdersRepository {
    //void query(OrdersRepositoryCallback callback, Specification filter);

    void query(OrdersRepositoryCallbacks callback, Query query);
    interface OrdersRepositoryCallbacks {
        void onSuccess(List<Order> orders);
        void onError(String error);
    }
    void queryCuadrillaHome(CuadrillaHomeRepositoryCallBack callback, int servicio, int sector);
    interface CuadrillaHomeRepositoryCallBack {
        void onSuccess(List<Cuadrilla_Home> cuadrillasHome);
        void onError(String error);
    }
    void queryTipoTrabajoHome(TipoTrabajoHomeRepositoryCallBack callback, int servicio, int sector);
    interface TipoTrabajoHomeRepositoryCallBack {
        void onSuccess(List<TiposTrabajo_Home> tiposTrabajoHome);
        void onError(String error);
    }
    void queryEstadoHome(EstadoHomeRepositoryCallBack callback, int servicio, int sector);
    interface EstadoHomeRepositoryCallBack {
        void onSuccess(List<Estados_Home> estadosHome);
        void onError(String error);
    }
    void queryZonaHome(ZonaHomeRepositoryCallBack callback, int servicio, int sector);
    interface ZonaHomeRepositoryCallBack {
        void onSuccess(List<Zonas_Home> zonasHome);
        void onError(String error);
    }
    void queryOrderDetail(OrdersRepositoryCallback callback, Integer orden,Query query);
    interface OrdersRepositoryCallback {
        void onSuccess(Order order);
        void onError(String error);
    }
    void queryFotoOrder(FotoOrdersRepositoryCallback callback, Integer orden,Query query);
    interface FotoOrdersRepositoryCallback {
        void onSuccess(List<FotoOrden> fotos);
        void onError(String error);
    }
    void addOrderState(String estado, ArrayList<Integer> numero,String observacion,
                       Integer tipoCuadrillaid, Double latitud, Double longitud);
    void addTurno(Integer numero, String turno);//DateTime turno);
    void cancelTurno(Integer numero);
    void findCuadrilla(CuadrillaxTipoRepositoryCallBack callback, Criteria filter);
    interface CuadrillaxTipoRepositoryCallBack {
        void onSuccess(List<Tipo_Trabajo> tipocuadrilla);
        void onError(String error);
    }
    /*void query(EstadoOperativoRepositoryCallBack callback, Criteria filter);
    interface EstadoOperativoRepositoryCallBack {
        //void onSuccess(List<Tipo_Cuadrilla> tipoCuadrilla);
        void onSuccess(List<RestTipoCuadrilla> tipoCuadrilla);
        void onError(String error);
    }*/
    /*void query(TipoTrabajoCuadrillaRepositoryCallBack callback, Criteria filter);
    interface TipoTrabajoCuadrillaRepositoryCallBack {
        void onSuccess(List<Tipo_Trabajo> tipoTrabajo);
        void onError(String error);
    }*/
   /* void query(FotoRepositoryCallBack callback);
   interface FotoRepositoryCallBack {
        void onSuccess(List<Zona> zona);
        void onError(String error);
    }*/
    List<RestOrder> fetchDataOrdersIfNewer();
    List<ContentProviderOperation> providerOperations(List<RestOrder> fetchedOrders);

    boolean SyncRemota() throws RemoteException, OperationApplicationException;
}
