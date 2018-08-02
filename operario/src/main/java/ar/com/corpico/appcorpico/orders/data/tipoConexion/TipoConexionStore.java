package ar.com.corpico.appcorpico.orders.data.tipoConexion;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoConexion;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface TipoConexionStore {
    interface GetTipoConexionStoreCallBack {
        void onSuccess(List<RestTipoConexion> tipoConexion);
        void onError(String error);
    }
    void getTipoConexion(GetTipoConexionStoreCallBack callback, Criteria filter);
}
