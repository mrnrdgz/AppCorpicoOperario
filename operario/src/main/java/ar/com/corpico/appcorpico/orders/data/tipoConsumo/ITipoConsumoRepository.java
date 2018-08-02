package ar.com.corpico.appcorpico.orders.data.tipoConsumo;

import android.content.ContentProviderOperation;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoConsumo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface ITipoConsumoRepository {
    void query(TipoConsumoRepositoryCallBack callback, Criteria filter);
    interface TipoConsumoRepositoryCallBack {
        void onSuccess(List<RestTipoConsumo> tipoConsumos);
        void onError(String error);
    }
    List<RestTipoConsumo> fetchDataTipoConsumoIfNewer();
    List<ContentProviderOperation> providerOperations(List<RestTipoConsumo> fetchedTipoConsumo);
}
