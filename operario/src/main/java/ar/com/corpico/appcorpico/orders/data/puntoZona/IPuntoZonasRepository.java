package ar.com.corpico.appcorpico.orders.data.puntoZona;

import android.content.ContentProviderOperation;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestPuntoZona;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface IPuntoZonasRepository {
    void query(PuntoZonaRepositoryCallBack callback);
    interface PuntoZonaRepositoryCallBack {
        void onSuccess(List<RestPuntoZona> puntoZona);
        void onError(String error);
    }
    List<RestPuntoZona> fetchDataPuntoZonaIfNewer();
    List<ContentProviderOperation> providerOperations(List<RestPuntoZona> fetchedPuntoZona);
}
