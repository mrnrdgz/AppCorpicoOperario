package ar.com.corpico.appcorpico.orders.data.tipoTrabajo;

import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface TipoTrabajoStore {
    void getTipoTrabajo(GetTipoTrabajoStoreCallBack callback, Criteria filter);
    interface GetTipoTrabajoStoreCallBack{
        void onSuccess(List<Tipo_Trabajo> tipoTrabajo);
        void onError(String error);
    }
}
