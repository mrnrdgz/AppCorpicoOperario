package ar.com.corpico.appcorpico.orders.data.turno;

import android.content.ContentProviderOperation;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTurnos;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface ITurnosRepository {
    void query(TurnoRepositoryCallBack callback, Criteria filter);
    interface TurnoRepositoryCallBack {
        void onSuccess(List<RestTurnos> turno);
        void onError(String error);
    }
    List<RestTurnos> fetchDataTurnoIfNewer();
    List<ContentProviderOperation> providerOperations(List<RestTurnos> fetchedTurnos);
}
