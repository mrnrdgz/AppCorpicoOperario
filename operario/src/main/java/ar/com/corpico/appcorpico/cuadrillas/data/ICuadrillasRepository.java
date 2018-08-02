package ar.com.corpico.appcorpico.cuadrillas.data;

import android.content.ContentProviderOperation;

import java.util.List;

import ar.com.corpico.appcorpico.cuadrillas.data.entity.RestCuadrilla;
import ar.com.corpico.appcorpico.cuadrillas.domain.entity.Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface ICuadrillasRepository {
    void query(CuadrillaRepositoryCallBack callback, Criteria filter);
    interface CuadrillaRepositoryCallBack {
        void onSuccess(List<Cuadrilla> Cuadrilla);
        void onError(String error);
    }
    void remove(RemoveOperarioRepositoryCallBack callback, int operario);
    interface RemoveOperarioRepositoryCallBack {
        void onSuccess();
        void onError(String error);
    }

    List<RestCuadrilla> fetchDataCuadrillaIfNewer();
    List<ContentProviderOperation>providerOperations(List<RestCuadrilla> fetchedCuadrillas);
}
