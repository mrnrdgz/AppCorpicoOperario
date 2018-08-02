package ar.com.corpico.appcorpico.orders.data.zona;

import android.content.ContentProviderOperation;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestZona;
import ar.com.corpico.appcorpico.orders.domain.entity.Zona;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface IZonasRepository {
    void query(ZonaRepositoryCallBack callback);
    interface ZonaRepositoryCallBack {
        void onSuccess(List<Zona> zona);
        void onError(String error);
    }
    List<RestZona> fetchDataZonaIfNewer();
    List<ContentProviderOperation> providerOperations(List<RestZona> fetchedZona);
}
