package ar.com.corpico.appcorpico.orders.data.medidor;

import android.content.ContentProviderOperation;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMedidor;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface IMedidorRepository {
    void query(MedidorRepositoryCallBack callback, Criteria filter);
    interface MedidorRepositoryCallBack {
        void onSuccess(List<RestMedidor> medidor);
        void onError(String error);
    }
    List<RestMedidor> fetchDataMedidorIfNewer();
    List<ContentProviderOperation> providerOperations(List<RestMedidor> fetchedMedidor);
}
