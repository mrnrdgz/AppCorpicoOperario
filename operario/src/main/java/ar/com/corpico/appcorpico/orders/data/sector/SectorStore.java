package ar.com.corpico.appcorpico.orders.data.sector;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestSector;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface SectorStore {
    interface GetSectorStoreCallBack {
        void onSuccess(List<RestSector> sector);
        void onError(String error);
    }

    void getSector(GetSectorStoreCallBack callback, Criteria filter);
}
