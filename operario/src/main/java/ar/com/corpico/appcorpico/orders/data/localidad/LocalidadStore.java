package ar.com.corpico.appcorpico.orders.data.localidad;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestLocalidad;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface LocalidadStore {
    interface GetLocalidadStoreCallBack {
        void onSuccess(List<RestLocalidad> etapa);
        void onError(String error);
    }
    void getLocalidad(GetLocalidadStoreCallBack callback, Criteria filter);
}
