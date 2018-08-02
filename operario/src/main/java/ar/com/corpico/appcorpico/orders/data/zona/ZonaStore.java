package ar.com.corpico.appcorpico.orders.data.zona;

import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Zona;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface ZonaStore {
    void getZona(GetZonaStoreCallBack callback);

    List<ZonaSqlite> getZones(List<String> ids);
    List<ZonaSqlite> getZones();

    interface GetZonaStoreCallBack{
        void onSuccess(List<Zona> zona);
        void onError(String error);
    }

    interface LoadZonesCallback {
        void onSuccess(List<ZonaSqlite> zones);
        void onError(String error);
    }
}
