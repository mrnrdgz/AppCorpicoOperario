package ar.com.corpico.appcorpico.orders.data.tipoEmpresa;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoEmpresa;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface TipoEmpresaStore {
    interface GetTipoEmpresaStoreCallBack {
        void onSuccess(List<RestTipoEmpresa> tipoEmpresas);
        void onError(String error);
    }
    void getTipoEmpresa(GetTipoEmpresaStoreCallBack callback, Criteria filter);
}
