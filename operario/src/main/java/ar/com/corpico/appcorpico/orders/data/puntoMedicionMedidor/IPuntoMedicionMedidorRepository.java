package ar.com.corpico.appcorpico.orders.data.puntoMedicionMedidor;

import android.content.ContentProviderOperation;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestPuntoMedicionMedidor;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface IPuntoMedicionMedidorRepository {
    void query(PuntoMedicionMedidorRepositoryCallBack callback, Criteria filter);
    interface PuntoMedicionMedidorRepositoryCallBack {
        void onSuccess(List<RestPuntoMedicionMedidor> puntoMedicionMedidor);
        void onError(String error);
    }
    List<RestPuntoMedicionMedidor> fetchDataPuntoMedicionMedidorIfNewer();
    List<ContentProviderOperation> providerOperations(List<RestPuntoMedicionMedidor> fetchedPuntoMedicionMedidor);
}
