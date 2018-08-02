package ar.com.corpico.appcorpico.orders.data.etapa;

import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface EtapaStore {
    interface GetEtapaStoreCallBack {
        void onSuccess(List<Etapa> etapa);
        void onError(String error);
    }
    void getEtapa(GetEtapaStoreCallBack callback, Criteria filter);
}
