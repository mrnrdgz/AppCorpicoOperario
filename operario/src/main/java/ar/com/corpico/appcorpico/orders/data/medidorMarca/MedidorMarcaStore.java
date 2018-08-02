package ar.com.corpico.appcorpico.orders.data.medidorMarca;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMedidorMarca;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface MedidorMarcaStore {
    interface GetMedidorMarcaStoreCallBack {
        void onSuccess(List<RestMedidorMarca> medidiorMarca);
        void onError(String error);
    }
    void getMedidorMarca(GetMedidorMarcaStoreCallBack callback, Criteria filter);
}
