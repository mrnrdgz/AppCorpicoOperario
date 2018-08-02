package ar.com.corpico.appcorpico.orders.data.tipoEmpresa;

import android.content.ContentProviderOperation;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoEmpresa;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface ITipoEmpresaRepository {
    void query(TipoEmpresaRepositoryCallBack callback, Criteria filter);
    interface TipoEmpresaRepositoryCallBack {
        //void onSuccess(List<Tipo_Cuadrilla> tipoCuadrilla);
        void onSuccess(List<RestTipoEmpresa> tipoEmpresas);
        void onError(String error);
    }
    List<RestTipoEmpresa> fetchDataTipoEmpresaIfNewer();
    List<ContentProviderOperation> providerOperations(List<RestTipoEmpresa> fetchedTipoEmpresa);
}
