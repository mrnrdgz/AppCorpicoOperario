package ar.com.corpico.appcorpico.orders.data.medidorClase;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMedidorClase;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface MedidorClaseStore {
    interface GetMedidorClaseStoreCallBack {
        void onSuccess(List<RestMedidorClase> medidiorClase);
        void onError(String error);
    }
    void getMedidorClase(GetMedidorClaseStoreCallBack callback, Criteria filter);
}
