package ar.com.corpico.appcorpico.operarios.data;

import android.content.ContentProviderOperation;

import java.util.List;

import ar.com.corpico.appcorpico.operarios.data.entity.RestOperario;
import ar.com.corpico.appcorpico.operarios.domain.entity.Operario;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface IOperarioRepository {
    void query(OperarioRepositoryCallBack callback, int servicio);
    interface OperarioRepositoryCallBack {
        void onSuccess(List<Operario> operario);
        void onError(String error);
    }
    void add(AddOperarioRepositoryCallBack callback, List<Integer> operario,int tipoCuadrillaId, int servicio, int sector);
    interface AddOperarioRepositoryCallBack {
        void onSuccess();
        void onError(String error);
    }
    List<RestOperario> fetchDataOperarioIfNewer();
    List<ContentProviderOperation> providerOperations(List<RestOperario> fetchedOperario);
}
