package ar.com.corpico.appcorpico.orders.data.empresaContratista;

import android.content.ContentProviderOperation;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestEmpresaContratista;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface IEmpresaContratistaRepository {
    void query(EmpresaContratistaRepositoryCallBack callback, Criteria filter);
    interface EmpresaContratistaRepositoryCallBack {
        //void onSuccess(List<Tipo_Cuadrilla> tipoCuadrilla);
        void onSuccess(List<RestEmpresaContratista> empresaContratista);
        void onError(String error);
    }
    List<RestEmpresaContratista> fetchDataEmpresaContratistaIfNewer();
    List<ContentProviderOperation> providerOperations(List<RestEmpresaContratista> fetchedEmpresaContratista);
}
