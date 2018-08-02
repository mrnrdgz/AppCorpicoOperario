package ar.com.corpico.appcorpico.orders.data.cliente;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestCliente;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface ClienteStore {
    interface GetClienteStoreCallBack {
        void onSuccess(List<RestCliente> cliente);
        void onError(String error);
    }

    void getCliente(GetClienteStoreCallBack callback, Criteria filter);
}
