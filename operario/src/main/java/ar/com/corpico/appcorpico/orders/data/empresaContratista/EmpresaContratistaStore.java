package ar.com.corpico.appcorpico.orders.data.empresaContratista;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestEmpresaContratista;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface EmpresaContratistaStore {
    interface GetEmpresaContratistaStoreCallBack {
        void onSuccess(List<RestEmpresaContratista> empresaContratistas);
        void onError(String error);
    }
    void getEmpresaContratista(GetEmpresaContratistaStoreCallBack callback, Criteria filter);
}
