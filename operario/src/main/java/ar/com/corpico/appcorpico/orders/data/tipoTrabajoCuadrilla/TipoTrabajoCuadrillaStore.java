package ar.com.corpico.appcorpico.orders.data.tipoTrabajoCuadrilla;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoTrabajoCuadrilla;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface TipoTrabajoCuadrillaStore {
    void getTipoTrabajoCuadrilla(GetTipoTrabajoCuadrillaStoreCallBack callback, Criteria filter);
    interface GetTipoTrabajoCuadrillaStoreCallBack {
        void onSuccess(List<RestTipoTrabajoCuadrilla> tipoTrabajoCuadrilla);
        void onError(String error);
    }
}
