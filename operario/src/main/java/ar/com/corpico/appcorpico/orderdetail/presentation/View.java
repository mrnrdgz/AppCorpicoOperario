package ar.com.corpico.appcorpico.orderdetail.presentation;

import ar.com.corpico.appcorpico.orders.domain.entity.Order;

/**
 * Created by Administrador on 06/01/2017.
 */

public interface View {
    void setPresenter(Presenter presenter);
    void closeDetail();
    void showOrderError(String error);
    void refreshTurno(String turno);
    void showOrder(Order o);
}
