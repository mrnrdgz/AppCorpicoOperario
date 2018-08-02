package ar.com.corpico.appcorpico.orders.data.localidad;

import android.content.ContentProviderOperation;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestLocalidad;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface ILocalidadRepository {
    void query(LocalidadRepositoryCallBack callback, Criteria filter);
    interface LocalidadRepositoryCallBack {
        void onSuccess(List<RestLocalidad> localidad);
        void onError(String error);
    }
    List<RestLocalidad> fetchDataLocalidadIfNewer();
    List<ContentProviderOperation> providerOperations(List<RestLocalidad> fetchedLocalidad);
}
