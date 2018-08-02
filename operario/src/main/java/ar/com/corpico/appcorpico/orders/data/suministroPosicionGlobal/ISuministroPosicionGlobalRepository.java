package ar.com.corpico.appcorpico.orders.data.suministroPosicionGlobal;

import android.content.ContentProviderOperation;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestSuministroPosicionGlobal;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface ISuministroPosicionGlobalRepository {
    void query(SuministroPosicionGlobalRepositoryCallBack callback, Criteria filter);
    interface SuministroPosicionGlobalRepositoryCallBack {
        void onSuccess(List<RestSuministroPosicionGlobal> suministrosPosicionGlobal);
        void onError(String error);
    }
    List<RestSuministroPosicionGlobal> fetchDataSuministroPosicionGlobalIfNewer();
    List<ContentProviderOperation> providerOperations(List<RestSuministroPosicionGlobal> fetchedSuministroPosicionGlobal);
}
