package ar.com.corpico.appcorpico.orders.data.suministroPosicionGlobal;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestSuministroPosicionGlobal;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface SuministroPosicionGlobalStore {
    interface GetSuministroPosicionGlobalStoreCallBack {
        void onSuccess(List<RestSuministroPosicionGlobal> suministroPosicionGlobal);
        void onError(String error);
    }
    void getSuministroPosicionGlobal(GetSuministroPosicionGlobalStoreCallBack callback, Criteria filter);
}
