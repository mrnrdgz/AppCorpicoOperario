package ar.com.corpico.appcorpico.orders.data.tipoUsuario;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoUsuario;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface TipoUsuarioStore {
    interface GetTipoUsuarioStoreCallBack {
        void onSuccess(List<RestTipoUsuario> tipoUsuarios);
        void onError(String error);
    }
    void getTipoUsuario(GetTipoUsuarioStoreCallBack callback, Criteria filter);
}
