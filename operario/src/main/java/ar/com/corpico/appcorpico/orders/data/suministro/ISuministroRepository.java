package ar.com.corpico.appcorpico.orders.data.suministro;

import android.content.ContentProviderOperation;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestSuministro;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface ISuministroRepository {
    void query(SuministroRepositoryCallBack callback, Criteria filter);
    interface SuministroRepositoryCallBack {
        void onSuccess(List<RestSuministro> suministros);
        void onError(String error);
    }
    List<RestSuministro> fetchDataSuministroIfNewer();
    List<ContentProviderOperation> providerOperations(List<RestSuministro> fetchedSuministro);
}
