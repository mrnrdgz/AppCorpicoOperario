package ar.com.corpico.appcorpico.orders.data.estadoOperativo;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestEstadoOperativo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface EstadoOperativoStore {
    interface GetEstadoOperativoStoreCallBack {
        //void onSuccess(List<Tipo_Cuadrilla> tipoCuadrilla);
        void onSuccess(List<RestEstadoOperativo> estadoOperativos);

        void onError(String error);
    }

    void getEstadoOperativo(GetEstadoOperativoStoreCallBack callback, Criteria filter);
}
