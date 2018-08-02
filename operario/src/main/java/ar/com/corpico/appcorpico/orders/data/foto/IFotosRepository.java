package ar.com.corpico.appcorpico.orders.data.foto;

import android.content.ContentProviderOperation;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestFoto;
import ar.com.corpico.appcorpico.orderdetailimages.FotoOrden;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface IFotosRepository {
    void query(FotoRepositoryCallBack callback);
    interface FotoRepositoryCallBack {
        void onSuccess(List<FotoOrden> foto);
        void onError(String error);
    }
    List<RestFoto> fetchDataFotoIfNewer();
    List<ContentProviderOperation> providerOperations(List<RestFoto> fetchedFoto);
}
