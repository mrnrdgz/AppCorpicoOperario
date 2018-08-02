package ar.com.corpico.appcorpico.orders.data.turno;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTurnos;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface TurnoStore {
    interface GetTurnoStoreCallBack {
        void onSuccess(List<RestTurnos> turno);
        void onError(String error);
    }
    void getTurno(GetTurnoStoreCallBack callback, Criteria filter);
}
