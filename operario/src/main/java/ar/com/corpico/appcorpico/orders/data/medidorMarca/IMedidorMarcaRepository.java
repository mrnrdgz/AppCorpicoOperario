package ar.com.corpico.appcorpico.orders.data.medidorMarca;

import android.content.ContentProviderOperation;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMedidorMarca;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface IMedidorMarcaRepository {
    void query(MedidorMarcaRepositoryCallBack callback, Criteria filter);
    interface MedidorMarcaRepositoryCallBack {
        void onSuccess(List<RestMedidorMarca> medidorMarca);
        void onError(String error);
    }
    List<RestMedidorMarca> fetchDataMedidorMarcaIfNewer();
    List<ContentProviderOperation> providerOperations(List<RestMedidorMarca> fetchedMedidorMarca);
}
