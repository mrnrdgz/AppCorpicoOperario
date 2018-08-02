package ar.com.corpico.appcorpico.orders.data.tipoCuadrilla;

import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface TipoCuadrillaStore {
    interface GetTipoCuadrillaStoreCallBack {
        void onSuccess(List<Tipo_Cuadrilla> tipoCuadrilla);
        void onError(String error);
    }
    void getTipoCuadrilla(GetTipoCuadrillaStoreCallBack callback, Criteria filter);
}
