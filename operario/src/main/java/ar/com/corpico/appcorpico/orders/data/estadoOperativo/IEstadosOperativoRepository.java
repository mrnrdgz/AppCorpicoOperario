package ar.com.corpico.appcorpico.orders.data.estadoOperativo;

import android.content.ContentProviderOperation;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestEstadoOperativo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface IEstadosOperativoRepository {
    void query(EstadoOperativoRepositoryCallBack callback, Criteria filter);

    List<ContentProviderOperation> providerOperations(List<RestEstadoOperativo> fetchedEstadoOperativo);

    interface EstadoOperativoRepositoryCallBack {
        //void onSuccess(List<Tipo_Cuadrilla> tipoCuadrilla);
        void onSuccess(List<RestEstadoOperativo> estadoOperativo);
        void onError(String error);
    }
    List<RestEstadoOperativo> fetchDataEstadoOperativoIfNewer();
}
