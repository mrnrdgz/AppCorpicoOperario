package ar.com.corpico.appcorpico.orders.data.medidorModelo;

import android.content.ContentProviderOperation;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMedidorModelo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface IMedidorModeloRepository {
    void query(MedidorModeloRepositoryCallBack callback, Criteria filter);
    interface MedidorModeloRepositoryCallBack {
        //void onSuccess(List<Tipo_Cuadrilla> tipoCuadrilla);
        void onSuccess(List<RestMedidorModelo> medidorModelo);
        void onError(String error);
    }
    List<RestMedidorModelo> fetchDataMedidorModeloIfNewer();
    List<ContentProviderOperation> providerOperations(List<RestMedidorModelo> fetchedMedidorModelo);
}
