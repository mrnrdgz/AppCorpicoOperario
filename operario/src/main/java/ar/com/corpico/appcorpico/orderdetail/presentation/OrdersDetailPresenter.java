package ar.com.corpico.appcorpico.orderdetail.presentation;

import android.support.annotation.Nullable;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import org.joda.time.DateTime;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.UseCaseHandler;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.usecase.AddOrdersState;
import ar.com.corpico.appcorpico.orders.domain.usecase.AddTurno;
import ar.com.corpico.appcorpico.orders.domain.usecase.CancelTurno;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetOrder;

/**
 * Created by Administrador on 06/01/2017.
 */

public class OrdersDetailPresenter implements Presenter {
    private AddOrdersState maddOrdersState;
    private AddTurno mAddTurno;
    private CancelTurno mCancelTurno;
    private GetOrder mGetOrder;
    private ar.com.corpico.appcorpico.orderdetail.presentation.View mOrdersView;
    private final UseCaseHandler mUseCaseHandler;

    public OrdersDetailPresenter(AddOrdersState addOrdersState, AddTurno AddTurno,CancelTurno CancelTurno, GetOrder getOrder,View ordersView, UseCaseHandler useCaseHandler) {
        maddOrdersState = Preconditions.checkNotNull(addOrdersState, "AddOrderState no puede ser null");
        mAddTurno = Preconditions.checkNotNull(AddTurno, "AddTurno no puede ser null");
        mCancelTurno = Preconditions.checkNotNull(CancelTurno, "AddTurno no puede ser null");
        mGetOrder = Preconditions.checkNotNull(getOrder, "GetOrder no puede ser null");
        mOrdersView = Preconditions.checkNotNull(ordersView, "La vista no puede ser null");
        mUseCaseHandler = Preconditions.checkNotNull(useCaseHandler, "El UseCaseHandler no puede ser null");
        mOrdersView.setPresenter(this);

    }
    @Override
    public void loadOrder(Integer numero) {

        // Parámetro #1
        GetOrder.RequestValues requestValues = new GetOrder.RequestValues(numero);

        // Parámetro #2
        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback<GetOrder.ResponseValue>() {
            @Override
            public void onSuccess(GetOrder.ResponseValue response) {
                // ¿La lista tiene uno o más elementos?
                Order order = response.getOrder();

                if (order != null) {
                    // Mostrar la lista en la vista
                    mOrdersView.showOrder(order);

                } else {
                   //TODO: Q HAGO ACA? SI NO TRAE LA RESPUESTA DEL SERVIDOR?
                }
            }

            @Override
            public void onError(String error) {
                // Ocultar indicador de progreso
                mOrdersView.showOrderError(error);
            }
        };

        // Ejecutar obtención de ordenes en un hilo de trabajo
        mGetOrder.setUseCaseCallback(useCaseCallback);
        mUseCaseHandler.execute(mGetOrder, requestValues, useCaseCallback);

    }

    @Override
    public void asignarOrder(String estadoPost,String cuadrilla, List<Integer> listorder, String observacion,Integer tipoCuadrillaId,Double latitud,Double longitud) {

        AddOrdersState.RequestValues requestValues = new AddOrdersState.RequestValues(estadoPost, listorder, observacion,tipoCuadrillaId,latitud,longitud);
        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback() {
            @Override
            public void onSuccess(Object response) {
                // Se obtiene el valor de respuesta del caso de uso
                AddOrdersState.ResponseValue responseValue = (AddOrdersState.ResponseValue) response;
                // Cierra el detalle
                mOrdersView.closeDetail();
            }

            @Override
            public void onError(String error) {
                // Ocultar indicador de progreso
                mOrdersView.showOrderError(error);
            }
        };

        //maddOrdersState.execute(requestValues, useCaseCallback);
        mUseCaseHandler.execute(maddOrdersState, requestValues, useCaseCallback);
    }

    /**
     * @param numero
     * @param turno  Recibe el turno nevo seleccionado por el usuario.
     *               Puede ser null para eliminar el turno
     */
    @Override
    public void asignarTurno(Integer numero, @Nullable final String turno) {
        ////final DateTime mTurno = turno;
        AddTurno.RequestValues requestValues = new AddTurno.RequestValues(numero, turno);
        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback() {
            @Override
            public void onSuccess(Object response) {
                // Se obtiene el valor de respuesta del caso de uso
                AddTurno.ResponseValue responseValue = (AddTurno.ResponseValue) response;

                DateTime mTurno = new DateTime(turno);
                if (!Strings.isNullOrEmpty(turno)) {
                    mOrdersView.refreshTurno(mTurno.toString("dd-MM-yyyy HH:mm"));
                } else {
                    mOrdersView.refreshTurno("Sin Turno");
                }
            }

            @Override
            public void onError(String error) {
                // Ocultar indicador de progreso
                //mOrdersView.showOrderError(error);
            }
        };
        //mAddTurno.execute(requestValues, useCaseCallback);
        mUseCaseHandler.execute(mAddTurno, requestValues, useCaseCallback);
    }

    @Override
    public void anularTurno(Integer numero) {
        ////final DateTime mTurno = turno;
        CancelTurno.RequestValues requestValues = new CancelTurno.RequestValues(numero);
        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback() {
            @Override
            public void onSuccess(Object response) {
                // Se obtiene el valor de respuesta del caso de uso
                CancelTurno.ResponseValue responseValue = (CancelTurno.ResponseValue) response;

                /*DateTime mTurno = new DateTime(turno);
                if (!Strings.isNullOrEmpty(turno)) {
                    mOrdersView.refreshTurno(mTurno.toString("dd-MM-yyyy HH:mm"));
                } else {*/
                    mOrdersView.refreshTurno("Sin Turno");
               // }
            }

            @Override
            public void onError(String error) {
                // Ocultar indicador de progreso
                //mOrdersView.showOrderError(error);
            }
        };
        //mAddTurno.execute(requestValues, useCaseCallback);
        mUseCaseHandler.execute(mCancelTurno, requestValues, useCaseCallback);
    }

}
