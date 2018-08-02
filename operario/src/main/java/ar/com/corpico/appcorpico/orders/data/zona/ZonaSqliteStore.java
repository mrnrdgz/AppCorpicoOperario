package ar.com.corpico.appcorpico.orders.data.zona;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestZona;
import ar.com.corpico.appcorpico.orders.domain.entity.Zona;
import ar.com.corpico.appcorpico.orders.domain.entity.ZonaPuntoSqlite;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp.PuntoZona;

/**
 * Created by Administrador on 12/03/2018.
 */

public class ZonaSqliteStore implements ZonaStore {
    private static final String TAG = ZonaSqliteStore.class.getSimpleName();
    private static ZonaSqliteStore INSTANCE;

    private ContentResolver mContentResolver;

    private ZonaSqliteStore(@NonNull ContentResolver mContentResolver) {
        this.mContentResolver = Preconditions.checkNotNull(mContentResolver,
                "mContentResolver no puede ser null");
    }

    public static ZonaSqliteStore getInstance(ContentResolver mContentResolver) {
        if (INSTANCE == null) {
            INSTANCE = new ZonaSqliteStore(mContentResolver);
        }
        return INSTANCE;
    }

    @Override
    public void getZona(GetZonaStoreCallBack callback) {
        Cursor zonaCursor = mContentResolver.query(ContratoCorpicoApp.Zona.URI_CONTENIDO, null, null, null, null);
        List<Zona> ListZona = new ArrayList<>();

        if (zonaCursor != null && zonaCursor.getCount() > 0) {
            while (zonaCursor.moveToNext()) {
                ListZona.add(new Zona(zonaCursor.getString(zonaCursor.getColumnIndex(ContratoCorpicoApp.Zona.ZON_DESCRIPCION))));
            }
            zonaCursor.close();
            callback.onSuccess(ListZona);
        } else {
            callback.onError("No existen Zonas");
        }
        //assert zonaCursor != null;

    }

    @Override
    public List<ZonaSqlite> getZones(List<String> ids) {
        // TODO: Cargar zonas con puntos

        Cursor zonasCursor = null;
        Cursor puntoZonaCursor = null;
        List<Integer> listIds = new ArrayList<>();
        for (String id : ids) {
            zonasCursor = mContentResolver.query(ContratoCorpicoApp.Zona.crearUriZonaFilter(id),
                    null,null, null, null);
            if (zonasCursor != null && zonasCursor.getCount() > 0) {
                zonasCursor.moveToNext();
                listIds.add(zonasCursor.getInt(zonasCursor.getColumnIndex(ContratoCorpicoApp.Zona.ZON_ID)));
                zonasCursor.close();
            }
        }

        List<ZonaSqlite> puntosZona = new ArrayList<>();
        List<LatLng> listLatLng = new ArrayList<>();
        for (Integer idd : listIds) {
            puntoZonaCursor = mContentResolver.query(PuntoZona.URI_CONTENIDO,
                    new String[]{PuntoZona.PZON_ID_ZONA,PuntoZona.PZON_LATITUD, PuntoZona.PZON_LONGITUD},
                    PuntoZona.PZON_ID_ZONA + "=?",
                    new String[]{idd.toString()},
                    null);

            if (puntoZonaCursor != null && puntoZonaCursor.getCount() > 0){
                while (puntoZonaCursor.moveToNext()) {
                    double lat = puntoZonaCursor.getDouble(puntoZonaCursor.getColumnIndex(PuntoZona.PZON_LATITUD));
                    double lng = puntoZonaCursor.getDouble(puntoZonaCursor.getColumnIndex(PuntoZona.PZON_LONGITUD));
                    listLatLng.add(new LatLng(lat, lng));
                    puntosZona.add(new ZonaSqlite(puntoZonaCursor.getInt(puntoZonaCursor.getColumnIndex(PuntoZona.PZON_ID_ZONA)), "",listLatLng));
                }
            }
            puntoZonaCursor.close();
        }

        return puntosZona;
    }

    @Override
    public List<ZonaSqlite> getZones() {
        Cursor zonasCursor = null;
        List<ZonaPuntoSqlite> listIds = new ArrayList<>();
        Cursor puntoZonaCursor = null;
        zonasCursor = mContentResolver.query(ContratoCorpicoApp.Zona.URI_CONTENIDO,
                null,null, null, null);
        if (zonasCursor != null ) {
            while (zonasCursor.moveToNext()) {
                listIds.add(new ZonaPuntoSqlite(zonasCursor.getInt(zonasCursor.getColumnIndex(ContratoCorpicoApp.Zona.ZON_ID)), zonasCursor.getString(zonasCursor.getColumnIndex(ContratoCorpicoApp.Zona.ZON_DESCRIPCION))));
            }
        }
        assert zonasCursor != null;
        zonasCursor.close();

        List<ZonaSqlite> puntosZona = new ArrayList<>();

        for (ZonaPuntoSqlite idd : listIds) {
            puntoZonaCursor = mContentResolver.query(PuntoZona.URI_CONTENIDO,
                    new String[]{PuntoZona.PZON_ID_ZONA, PuntoZona.PZON_LATITUD, PuntoZona.PZON_LONGITUD},
                    PuntoZona.PZON_ID_ZONA + "=?",
                    new String[]{String.valueOf(idd.getId())},
                    null);


        /*puntoZonaCursor = mContentResolver.query(PuntoZona.crearUriConDisplay(PuntoZona.DISPLAY_ZONA),
                        null,
                        null,
                        null,
                        null);

        List<ZonaPuntoSqlite> puntosZona = new ArrayList<>();
        List<LatLng> listLatLng = new ArrayList<>();
        if (puntoZonaCursor != null && puntoZonaCursor.getCount() > 0){
            while (puntoZonaCursor.moveToNext()) {
                double lat = puntoZonaCursor.getDouble(puntoZonaCursor.getColumnIndex(PuntoZona.PZON_LATITUD));
                double lng = puntoZonaCursor.getDouble(puntoZonaCursor.getColumnIndex(PuntoZona.PZON_LONGITUD));
                listLatLng.add(new LatLng(lat, lng));
                puntosZona.add(new ZonaPuntoSqlite(puntoZonaCursor.getInt(puntoZonaCursor.getColumnIndex(PuntoZona.PZON_ID_ZONA)), puntoZonaCursor.getString(puntoZonaCursor.getColumnIndex(ContratoCorpicoApp.Zona.ZON_DESCRIPCION)),listLatLng));
            }
        }*/
            if (puntoZonaCursor != null ) {
                List<LatLng> listLatLng = new ArrayList<>();
                while (puntoZonaCursor.moveToNext()) {
                    double lat = puntoZonaCursor.getDouble(puntoZonaCursor.getColumnIndex(PuntoZona.PZON_LATITUD));
                    double lng = puntoZonaCursor.getDouble(puntoZonaCursor.getColumnIndex(PuntoZona.PZON_LONGITUD));
                    listLatLng.add(new LatLng(lat, lng));
                }
                int i = idd.getId();
                String s = idd.getZona();
                //puntoZonaCursor.getInt(puntoZonaCursor.getColumnIndex(PuntoZona.PZON_ID_ZONA))
                puntosZona.add(new ZonaSqlite(idd.getId(), idd.getZona(), listLatLng));
            }
            assert puntoZonaCursor != null;
            puntoZonaCursor.close();
        }

        return puntosZona;
    }

    public List<ContentProviderOperation> replicarServidor(List<RestZona> zonas) {
        //TODO CREAR UNA LISTA DE OPERACION DEL PROVIDER
        List<ContentProviderOperation> operations = new ArrayList<>();
        // Tabla hash para recibir los tipos de trabajos entrantes
        HashMap<Integer, RestZona> zonaMap = new HashMap<>();
        for (RestZona z : zonas) {
            zonaMap.put((int) z.getZON_ID(), z);
        }

        //Consultar registros remotos actuales
        Uri uri = ContratoCorpicoApp.Zona.URI_CONTENIDO;
        String select = ContratoCorpicoApp.Zona.TABLE_NAME;

        //TODO: ACA
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Zonas - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        short id;
        String descripcion;
        String observacion;
        String fecha_update;

        while (c.moveToNext()) {
            id = (short) c.getShort(c.getColumnIndex(ContratoCorpicoApp.Zona.ZON_ID));
            descripcion = c.getString(c.getColumnIndex(ContratoCorpicoApp.Zona.ZON_DESCRIPCION));
            observacion = c.getString(c.getColumnIndex(ContratoCorpicoApp.Zona.ZON_OBSERVACION));
            fecha_update = c.getString(c.getColumnIndex(ContratoCorpicoApp.Zona.ZON_FECHA_UPDATE));

            RestZona match = zonaMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                zonaMap.remove(id);

                Uri existingUri = ContratoCorpicoApp.Zona.crearUriZona((int) id);

                // Comprobar si tipo de trabajos necesita ser actualizado
                boolean b = match.getZON_ID() != id;
                boolean b1 = match.getZON_DESCRIPCION() != descripcion;
                boolean b2 = match.getZON_OBSERVACION() != observacion;
                boolean b3 = match.getZON_FECHA_UPDATE() != fecha_update;


                if (b || b1 || b2 || b3) {

                    Log.i(TAG, "Tipos de Trabajos - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoCorpicoApp.Zona.ZON_ID, match.getZON_ID())
                            .withValue(ContratoCorpicoApp.Zona.ZON_DESCRIPCION, match.getZON_DESCRIPCION())
                            .withValue(ContratoCorpicoApp.Zona.ZON_OBSERVACION, match.getZON_OBSERVACION())
                            .withValue(ContratoCorpicoApp.Zona.ZON_FECHA_UPDATE, match.getZON_FECHA_UPDATE())
                            .build());
                } else {
                    Log.i(TAG, "Tipos de Trabajos - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoCorpicoApp.Zona.crearUriZona((int) id);
                Log.i(TAG, "Zonas - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestZona zona : zonaMap.values()) {
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.Zona.URI_CONTENIDO)
                    .withValue(ContratoCorpicoApp.Zona.ZON_ID, zona.getZON_ID())
                    .withValue(ContratoCorpicoApp.Zona.ZON_DESCRIPCION, zona.getZON_DESCRIPCION())
                    .withValue(ContratoCorpicoApp.Zona.ZON_OBSERVACION, zona.getZON_OBSERVACION())
                    .withValue(ContratoCorpicoApp.Zona.ZON_FECHA_UPDATE, zona.getZON_FECHA_UPDATE())
                    .build());
        }
        return operations;

    }
}
