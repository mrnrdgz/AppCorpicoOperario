package ar.com.corpico.appcorpico.orders.data.suministroTipoEmpresa;

import android.content.ContentProviderOperation;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestSuministroTipoEmpresa;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface ISuministroTipoEmpresaRepository {
    void query(SuministroTipoEmpresaRepositoryCallBack callback, Criteria filter);
    interface SuministroTipoEmpresaRepositoryCallBack {
        void onSuccess(List<RestSuministroTipoEmpresa> suministrosTipoEmpresas);
        void onError(String error);
    }
    List<RestSuministroTipoEmpresa> fetchDataSuministroTipoEmpresaIfNewer();
    List<ContentProviderOperation> providerOperations(List<RestSuministroTipoEmpresa> fetchedSuministroTipoEmpresa);
}
