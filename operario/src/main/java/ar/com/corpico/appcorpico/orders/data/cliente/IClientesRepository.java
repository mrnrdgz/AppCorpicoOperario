package ar.com.corpico.appcorpico.orders.data.cliente;

import android.content.ContentProviderOperation;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestCliente;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface IClientesRepository {
    void query(ClienteRepositoryCallBack callback, Criteria filter);
    interface ClienteRepositoryCallBack {
        void onSuccess(List<RestCliente> cliente);
        void onError(String error);
    }
    List<RestCliente> fetchDataClienteIfNewer();
    List<ContentProviderOperation> providerOperations(List<RestCliente> fetchedClientes);
}
