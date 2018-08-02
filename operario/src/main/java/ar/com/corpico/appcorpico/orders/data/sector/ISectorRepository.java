package ar.com.corpico.appcorpico.orders.data.sector;

import android.content.ContentProviderOperation;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestSector;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface ISectorRepository {
    void query(TipoUsuarioRepositoryCallBack callback, Criteria filter);
    interface TipoUsuarioRepositoryCallBack {
        void onSuccess(List<RestSector> sectors);
        void onError(String error);
    }
    List<RestSector> fetchDataSectorIfNewer();
    List<ContentProviderOperation> providerOperations(List<RestSector> fetchedSector);
}
