package ar.com.corpico.appcorpico.orders.data.medidorModelo;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMedidorModelo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface MedidorModeloStore {
    interface GetMedidorModeloStoreCallBack {
        void onSuccess(List<RestMedidorModelo> medidiorModelo);
        void onError(String error);
    }
    void getMedidorModelo(GetMedidorModeloStoreCallBack callback, Criteria filter);
}
