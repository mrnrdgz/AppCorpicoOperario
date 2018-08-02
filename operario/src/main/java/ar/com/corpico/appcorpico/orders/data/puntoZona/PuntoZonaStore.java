package ar.com.corpico.appcorpico.orders.data.puntoZona;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestPuntoZona;
import ar.com.corpico.appcorpico.orders.data.puntoZona.zona.ZonaSqlite;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface PuntoZonaStore {
    void getPuntoZona(GetPuntoZonaStoreCallBack callback);

    List<ZonaSqlite> getZones(List<String> ids);

    interface GetPuntoZonaStoreCallBack{
        void onSuccess(List<RestPuntoZona> zona);
        void onError(String error);
    }

    interface LoadPuntoZonesCallback {
        void onSuccess(List<ZonaSqlite> zones);
        void onError(String error);
    }
}
