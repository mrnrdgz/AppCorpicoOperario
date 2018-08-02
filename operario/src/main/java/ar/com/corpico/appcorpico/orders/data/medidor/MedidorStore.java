package ar.com.corpico.appcorpico.orders.data.medidor;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMedidor;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface MedidorStore {
    interface GetMedidorStoreCallBack {
        void onSuccess(List<RestMedidor> medidor);
        void onError(String error);
    }
    void getMedidor(GetMedidorStoreCallBack callback, Criteria filter);
}
