package ar.com.corpico.appcorpico.orders.data.tipoConexion;

import android.content.ContentProviderOperation;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoConexion;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface ITipoConexionRepository {
    void query(TipoConexionRepositoryCallBack callback, Criteria filter);
    interface TipoConexionRepositoryCallBack {
        void onSuccess(List<RestTipoConexion> tipoConexion);
        void onError(String error);
    }
    List<RestTipoConexion> fetchDataTipoConexionIfNewer();
    List<ContentProviderOperation> providerOperations(List<RestTipoConexion> fetchedTipoConexion);
}
