package ar.com.corpico.appcorpico.orders.data.estado;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestEstado;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface EstadoStore {
    interface GetEstadoStoreCallBack {
        void onSuccess(List<RestEstado> estados);
        void onError(String error);
    }
    void getEstado(GetEstadoStoreCallBack callback);
}
