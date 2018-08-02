package ar.com.corpico.appcorpico.orders.data.medidorClase;

import android.content.ContentProviderOperation;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMedidorClase;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface IMedidorClaseRepository {
    void query(MedidorClaseRepositoryCallBack callback, Criteria filter);
    interface MedidorClaseRepositoryCallBack {
        //void onSuccess(List<Tipo_Cuadrilla> tipoCuadrilla);
        void onSuccess(List<RestMedidorClase> medidorClase);
        void onError(String error);
    }
    List<RestMedidorClase> fetchDataMedidorClaseIfNewer();
    List<ContentProviderOperation> providerOperations(List<RestMedidorClase> fetchedMedidorClase);
}
