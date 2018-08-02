package ar.com.corpico.appcorpico.orders.data.tipoTrabajoCuadrilla;

import android.content.ContentProviderOperation;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoTrabajoCuadrilla;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface ITipoTrabajoCuadrillaRepository {
    void query(TipoTrabajoCuadrillaRepositoryCallBack callback, Criteria filter);
    interface TipoTrabajoCuadrillaRepositoryCallBack {
        void onSuccess(List<RestTipoTrabajoCuadrilla> tipoTrabajoCuadrilla);
        void onError(String error);
    }
    List<RestTipoTrabajoCuadrilla> fetchDataTipoTrabajoCuadrillaIfNewer();
    List<ContentProviderOperation> providerOperations(List<RestTipoTrabajoCuadrilla> fetchedTipoTrabajoCuadrilla);
}
