package ar.com.corpico.appcorpico.orders.data.estado;

import android.content.ContentProviderOperation;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestEstado;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface IEstadoRepository {
    void query(EstadoRepositoryCallBack callback);
    interface EstadoRepositoryCallBack {
        void onSuccess(List<RestEstado> tipoCuadrilla);
        void onError(String error);
    }
    List<RestEstado> fetchDataEstadoIfNewer();
    List<ContentProviderOperation>providerOperations(List<RestEstado> fetchedEstados);
}
