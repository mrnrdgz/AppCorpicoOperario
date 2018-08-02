package ar.com.corpico.appcorpico.operarios.data;

import java.util.List;

import ar.com.corpico.appcorpico.operarios.domain.entity.Operario;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface OperarioStore {
    interface GetOperarioStoreCallBack {
        void onSuccess(List<Operario> operario);
        void onError(String error);
    }
    void getOperario(GetOperarioStoreCallBack callback, int sector);
    void addOperario(AddOperarioStoreCallBack callback, List<Integer> operario,int tipoCuadrillaId, int servicio, int sector);
    interface AddOperarioStoreCallBack {
        void onSuccess();
        void onError(String error);
    }
}
