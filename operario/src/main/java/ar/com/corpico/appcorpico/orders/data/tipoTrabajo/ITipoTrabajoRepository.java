package ar.com.corpico.appcorpico.orders.data.tipoTrabajo;

import android.content.ContentProviderOperation;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoTrabajo;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface ITipoTrabajoRepository {
    void query(TipoTrabajoRepositoryCallBack callback, Criteria filter);
    interface TipoTrabajoRepositoryCallBack {
        void onSuccess(List<Tipo_Trabajo> tipoTrabajo);
        void onError(String error);
    }
    List<RestTipoTrabajo> fetchDataTipoTrabajoIfNewer();
    List<ContentProviderOperation> providerOperations(List<RestTipoTrabajo> fetchedTipoTrabajo);
}
