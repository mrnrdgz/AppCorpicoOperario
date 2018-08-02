package ar.com.corpico.appcorpico.orders.data.foto;

import java.util.List;

import ar.com.corpico.appcorpico.orderdetailimages.FotoOrden;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface FotoStore {
    void getFoto(GetFotoStoreCallBack callback);
    interface GetFotoStoreCallBack {
        void onSuccess(List<FotoOrden> foto);
        void onError(String error);
    }
}
