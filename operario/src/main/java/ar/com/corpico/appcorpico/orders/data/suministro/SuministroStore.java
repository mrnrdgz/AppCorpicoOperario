package ar.com.corpico.appcorpico.orders.data.suministro;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestSuministro;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface SuministroStore {
    interface GetSuministroStoreCallBack {
        void onSuccess(List<RestSuministro> suministro);
        void onError(String error);
    }
    void getSuministro(GetSuministroStoreCallBack callback, Criteria filter);
}
