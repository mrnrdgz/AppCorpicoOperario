package ar.com.corpico.appcorpico.orders.data.etapa;

import android.content.ContentProviderOperation;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestEtapa;
import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface IEtapasRepository {
    void query(EtapaRepositoryCallBack callback, Criteria filter);
    interface EtapaRepositoryCallBack {
        //void onSuccess(List<Tipo_Cuadrilla> tipoCuadrilla);
        void onSuccess(List<Etapa> etapas);
        void onError(String error);
    }
    List<RestEtapa> fetchDataEtapaIfNewer();
    List<ContentProviderOperation> providerOperations(List<RestEtapa> fetchedEtapas);
}
