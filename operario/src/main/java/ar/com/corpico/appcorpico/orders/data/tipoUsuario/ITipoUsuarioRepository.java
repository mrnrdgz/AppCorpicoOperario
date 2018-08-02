package ar.com.corpico.appcorpico.orders.data.tipoUsuario;

import android.content.ContentProviderOperation;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoUsuario;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface ITipoUsuarioRepository {
    void query(TipoUsuarioRepositoryCallBack callback, Criteria filter);
    interface TipoUsuarioRepositoryCallBack {
        void onSuccess(List<RestTipoUsuario> tipoUsuarios);
        void onError(String error);
    }
    List<RestTipoUsuario> fetchDataTipoUsuarioIfNewer();
    List<ContentProviderOperation> providerOperations(List<RestTipoUsuario> fetchedTipoUsuario);
}
