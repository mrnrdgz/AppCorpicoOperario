package ar.com.corpico.appcorpico.orders.data.suministroTipoEmpresa;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestSuministroTipoEmpresa;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface SuministroTipoEmpresaStore {
    interface GetSuministroTipoEmpresaStoreCallBack {
        void onSuccess(List<RestSuministroTipoEmpresa> suministroTipoEmpresa);
        void onError(String error);
    }
    void getSuministroTipoEmpresa(GetSuministroTipoEmpresaStoreCallBack callback, Criteria filter);
}
