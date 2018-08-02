package ar.com.corpico.appcorpico.orders.data.medidorTipo;

import android.content.ContentProviderOperation;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMedidorTipo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface IMedidorTipoRepository {
    void query(MedidorTipoRepositoryCallBack callback, Criteria filter);
    interface MedidorTipoRepositoryCallBack {
        void onSuccess(List<RestMedidorTipo> medidorTipos);
        void onError(String error);
    }
    List<RestMedidorTipo> fetchDataMedidorTipoIfNewer();
    List<ContentProviderOperation> providerOperations(List<RestMedidorTipo> fetchedMedidorTipo);
}
