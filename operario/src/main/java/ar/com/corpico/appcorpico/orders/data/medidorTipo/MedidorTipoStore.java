package ar.com.corpico.appcorpico.orders.data.medidorTipo;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMedidorTipo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface MedidorTipoStore {
    interface GetMedidorTipoStoreCallBack {
        void onSuccess(List<RestMedidorTipo> medidiorTipo);
        void onError(String error);
    }
    void getMedidorTipo(GetMedidorTipoStoreCallBack callback, Criteria filter);
}
