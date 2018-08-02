package ar.com.corpico.appcorpico.orders.data.tipoConsumo;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoConsumo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface TipoConsumoStore {
    interface GetTipoConsumoStoreCallBack {
        void onSuccess(List<RestTipoConsumo> tipoConsumos);
        void onError(String error);
    }
    void getTipoConsumo(GetTipoConsumoStoreCallBack callback, Criteria filter);
}
