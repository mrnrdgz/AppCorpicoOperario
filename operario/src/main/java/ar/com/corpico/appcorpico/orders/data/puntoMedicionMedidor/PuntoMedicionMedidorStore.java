package ar.com.corpico.appcorpico.orders.data.puntoMedicionMedidor;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestPuntoMedicionMedidor;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface PuntoMedicionMedidorStore {
    interface GetPuntoMedicionMedidorStoreCallBack {
        void onSuccess(List<RestPuntoMedicionMedidor> puntoMedicionMedidor);
        void onError(String error);
    }
    void getPuntoMedicionMedidor(GetPuntoMedicionMedidorStoreCallBack callback, Criteria filter);
}
