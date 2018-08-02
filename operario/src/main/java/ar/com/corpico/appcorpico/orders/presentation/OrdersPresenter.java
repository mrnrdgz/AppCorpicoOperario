package ar.com.corpico.appcorpico.orders.presentation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.UseCaseHandler;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.usecase.AddOrdersState;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetCuadrillaxTipo;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetOrders;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetTipoTrabajo;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetZonas;

/**
 * Created by Administrador on 06/01/2017.
 */

public class OrdersPresenter implements OrdersListMvp.Presenter {
    private AddOrdersState maddOrdersState;
    private GetOrders mGetOrders;
    //private RestTipoCuadrilla mGetTipoCuadrilla;
    private GetTipoTrabajo mGetTipoTrabajo;
    private GetZonas mGetZona;
    private GetCuadrillaxTipo mgetCuadrillaxTipo;

    private OrdersListMvp.View mOrdersView;
    private Integer mCuadrilla;

    private final UseCaseHandler mUseCaseHandler;


    public OrdersPresenter(GetOrders getOrders, AddOrdersState addOrdersState, OrdersListMvp.View ordersView, UseCaseHandler useCaseHandler) {
        mGetOrders = Preconditions.checkNotNull(getOrders, "El presentador no puede ser null");
        maddOrdersState=Preconditions.checkNotNull(addOrdersState, "El presentador no puede ser null");
        mOrdersView = Preconditions.checkNotNull(ordersView, "La vista no puede ser null");
        mOrdersView.setPresenter(this);
        mUseCaseHandler = Preconditions.checkNotNull(useCaseHandler, "useCaseHandler no puede ser null");
    }

    @Override
    public void loadOrders(String tipoCuadrilla, String estado, List<String> tiposTrabajo,
                           List<String> zona, DateTime desde,
                           DateTime hasta, String search,
                           Boolean estadoActual,String fieldSort) {

        mOrdersView.showProgressIndicator(true);

        // Parámetro #1
        GetOrders.RequestValues requestValues = new GetOrders.RequestValues(
                tipoCuadrilla, estado, tiposTrabajo, zona, desde, hasta, search, estadoActual,fieldSort
        );

        // Parámetro #2
        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback<GetOrders.ResponseValue>() {
            @Override
            public void onSuccess(GetOrders.ResponseValue response) {
                // Ocultar indicador de progreso
                mOrdersView.showProgressIndicator(false);

                // ¿La lista tiene uno o más elementos?
                List<Order> orders = response.getOrders();

                if (orders.size() >= 1) {
                    // Mostrar la lista en la vista
                    mOrdersView.showOrderList(orders);

                } else {
                    // Mostrar estado vacío
                    mOrdersView.showOrderList(orders);
                    mOrdersView.showOrdesEmpty();
                }
            }

            @Override
            public void onError(String error) {
                // Ocultar indicador de progreso
                mOrdersView.showProgressIndicator(false);
                mOrdersView.showOrderError(error);
            }
        };

        // Ejecutar obtención de ordenes en un hilo de trabajo
        mGetOrders.setUseCaseCallback(useCaseCallback);
        mUseCaseHandler.execute(mGetOrders, requestValues, useCaseCallback);

    }

    @Override
    public void asignarOrder(String estado, List<Integer> listorder,String observacion,Integer tipoCuadrillaId, Double latitud, Double longitud) {
        //mCuadrilla = cuadrilla;

        AddOrdersState.RequestValues requestValues = new AddOrdersState.RequestValues(estado, listorder,observacion,tipoCuadrillaId,latitud,longitud);
        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback<AddOrdersState.ResponseValue>() {
            @Override
            public void onSuccess(AddOrdersState.ResponseValue response) {
                // Ocultar indicador de progreso
                mOrdersView.showProgressIndicator(false);
                // Se obtiene el valor de respuesta del caso de uso
                AddOrdersState.ResponseValue responseValue = (AddOrdersState.ResponseValue) response;
                // Actualiza la lista luego de hacer el cambio
                //TODO VER ESTO XQ NO TIENE ACTUALIZADO EL VALOR DE mTipoCuadrilla
                //como debo hacer q llame a una instancia en vez de load del presentador ?
                //o que aca devuelva la cuadrilla y que llame al load de la vista pasandole esa cuadrilla?

                mOrdersView.close(); //refreshListUI

                // loadOrders();
            }

            @Override
            public void onError(String error) {
                // Ocultar indicador de progreso
                mOrdersView.showProgressIndicator(false);
                mOrdersView.showOrderError(error);
            }
        };

        maddOrdersState.setUseCaseCallback(useCaseCallback);
        mUseCaseHandler.execute(maddOrdersState, requestValues, useCaseCallback);
        //maddOrdersState.executeUcase(requestValues);
    }
}
