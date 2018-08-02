package ar.com.corpico.appcorpico.orders.data.tipoCuadrilla;

import android.content.ContentProviderOperation;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoCuadrilla;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface ITipoCuadrillasRepository {
    void query(TipoCuadrillaRepositoryCallBack callback, Criteria filter);
    interface TipoCuadrillaRepositoryCallBack {
        void onSuccess(List<Tipo_Cuadrilla> tipoCuadrilla);
        void onError(String error);
    }
    List<RestTipoCuadrilla> fetchDataCuadrillaIfNewer();
    List<ContentProviderOperation>providerOperations(List<RestTipoCuadrilla> fetchedTipoCuadrillas);
}
