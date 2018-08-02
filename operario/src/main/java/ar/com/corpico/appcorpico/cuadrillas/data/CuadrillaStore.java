package ar.com.corpico.appcorpico.cuadrillas.data;

import java.util.List;

import ar.com.corpico.appcorpico.cuadrillas.domain.entity.Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface CuadrillaStore {
    void getCuadrilla(GetCuadrillaStoreCallBack callback, Criteria filter);
    interface GetCuadrillaStoreCallBack {
        void onSuccess(List<Cuadrilla> Cuadrilla);
        void onError(String error);
    }
    void removeOperario(RemoveOperarioStoreCallBack callback, int operario);
    interface RemoveOperarioStoreCallBack {
        void onSuccess();
        void onError(String error);
    }

}
