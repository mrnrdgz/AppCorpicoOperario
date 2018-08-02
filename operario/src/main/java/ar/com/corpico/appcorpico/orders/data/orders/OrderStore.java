package ar.com.corpico.appcorpico.orders.data.orders;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.home.data.Cuadrilla_Home;
import ar.com.corpico.appcorpico.home.data.Estados_Home;
import ar.com.corpico.appcorpico.home.data.TiposTrabajo_Home;
import ar.com.corpico.appcorpico.home.data.Zonas_Home;
import ar.com.corpico.appcorpico.orderdetailimages.FotoOrden;
import ar.com.corpico.appcorpico.orders.domain.Query;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 07/01/2017.
 */

public interface OrderStore {
    //void getOrders(GetCallback callback, Specification filter);
    void getOrders(GetCallbacks callback, Query query);
    interface GetCallbacks{
        void onSuccess(List<Order> orders);
        void onError(String error);
    }

    void queryCuadrillaHome(GetCuadrillaHomeStoreCallBack callback, int servicio,int sector);
    interface GetCuadrillaHomeStoreCallBack{
        void onSuccess(List<Cuadrilla_Home> cuadrilla_homes);
        void onError(String error);
    }
    void queryTipoTrabajoHome(GetTiposTrabajoHomeStoreCallBack callback, int servicio,int sector);
    interface GetTiposTrabajoHomeStoreCallBack{
        void onSuccess(List<TiposTrabajo_Home> tiposTrabajo_home);
        void onError(String error);
    }
    void queryEstadosHome(GetEstadosHomeStoreCallBack callback, int servicio,int sector);
    interface GetEstadosHomeStoreCallBack{
        void onSuccess(List<Estados_Home> estados_home);
        void onError(String error);
    }
    void queryZonasHome(GetZonasHomeStoreCallBack callback, int servicio,int sector);
    interface GetZonasHomeStoreCallBack{
        void onSuccess(List<Zonas_Home> zonas_home);
        void onError(String error);
    }
    void getOrder(GetCallback callback, Integer orden,Query query);
    interface GetCallback{
        void onSuccess(List<Order> order);
        void onError(String error);
    }
    void getFotoOrder(GetFotoCallback callback, Integer orden, Query query);
    interface GetFotoCallback{
        void onSuccess(List<FotoOrden> fotos);
        void onError(String error);
    }
    void addOrderEtape(String estado, ArrayList<Integer> numero,String observacion,Integer tipoCuadrillaId,
                       Double latitud, Double longitud);
    void addTurno(Integer numero,String turno);//DateTime turno);
    void cancelTurno(Integer numero);
    void getCuadrillaxTipo(GetCuadrillaxTipoStoreCallBack callback, Criteria filter);
    interface GetCuadrillaxTipoStoreCallBack{
        void onSuccess(List<Tipo_Trabajo> tipoCuadrilla);
        void onError(String error);
    }
    /*void getMedidor(GetEstadoOperativoStoreCallBack callback, Criteria filter);
    interface GetEstadoOperativoStoreCallBack{
        //void onSuccess(List<Tipo_Cuadrilla> tipoCuadrilla);
        void onSuccess(List<RestTipoCuadrilla> tipoCuadrilla);
        void onError(String error);
    }*/
    /*void getTipoTrabajoCuadrilla(GetTipoTrabajoCuadrillaStoreCallBack callback, Criteria filter);
    interface GetTipoTrabajoCuadrillaStoreCallBack{
        void onSuccess(List<Tipo_Trabajo> tipoTrabajo);
        void onError(String error);
    }*/
    /*void getZona(GetFotoStoreCallBack callback);
    interface GetFotoStoreCallBack{
        void onSuccess(List<Zona> zona);
        void onError(String error);
    }*/

}
