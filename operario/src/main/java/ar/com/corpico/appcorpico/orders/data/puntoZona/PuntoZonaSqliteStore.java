package ar.com.corpico.appcorpico.orders.data.puntoZona;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestPuntoZona;
import ar.com.corpico.appcorpico.orders.data.puntoZona.zona.ZonaSqlite;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;

/**
 * Created by Administrador on 12/03/2018.
 */

public class PuntoZonaSqliteStore implements PuntoZonaStore {
    private static final String TAG = PuntoZonaSqliteStore.class.getSimpleName();
    private static PuntoZonaSqliteStore INSTANCE;

    private ContentResolver mContentResolver;

    private PuntoZonaSqliteStore(@NonNull ContentResolver mContentResolver) {
        this.mContentResolver = Preconditions.checkNotNull(mContentResolver,
                "mContentResolver no puede ser null");
    }
    public static PuntoZonaSqliteStore getInstance(ContentResolver mContentResolver) {
        if (INSTANCE == null) {
            INSTANCE = new PuntoZonaSqliteStore(mContentResolver);
        }
        return INSTANCE;
    }
    @Override
    public void getPuntoZona(GetPuntoZonaStoreCallBack callback) {
        Cursor puntoZonaCursor = mContentResolver.query(ContratoCorpicoApp.PuntoZona.URI_CONTENIDO, null, null, null, null);
        List<RestPuntoZona> ListPuntoZona = new ArrayList<>();

        if (puntoZonaCursor != null && puntoZonaCursor.getCount()>0) {
            while(puntoZonaCursor.moveToNext()) {
                ListPuntoZona.add(new RestPuntoZona(puntoZonaCursor.getInt(puntoZonaCursor.getColumnIndex(ContratoCorpicoApp.PuntoZona.PZON_ID)),
                        puntoZonaCursor.getInt(puntoZonaCursor.getColumnIndex(ContratoCorpicoApp.PuntoZona.PZON_ID_ZONA)),
                        puntoZonaCursor.getInt(puntoZonaCursor.getColumnIndex(ContratoCorpicoApp.PuntoZona.PZON_INDICE)),
                        puntoZonaCursor.getDouble(puntoZonaCursor.getColumnIndex(ContratoCorpicoApp.PuntoZona.PZON_LATITUD)),
                        puntoZonaCursor.getDouble(puntoZonaCursor.getColumnIndex(ContratoCorpicoApp.PuntoZona.PZON_LONGITUD)),
                        puntoZonaCursor.getString(puntoZonaCursor.getColumnIndex(ContratoCorpicoApp.PuntoZona.PZON_FECHA_UPDATE))));

            }

            callback.onSuccess(ListPuntoZona);
        }else {
            callback.onError("No existen Zonas");
        }

    }

    @Override
    public List<ZonaSqlite> getZones(List<String> ids) {
        // TODO: Cargar zonas con puntos
        return null;
    }

    public List<ContentProviderOperation> replicarServidor(List<RestPuntoZona> puntoZonas) {
        //TODO CREAR UNA LISTA DE OPERACION DEL PROVIDER
        List<ContentProviderOperation> operations = new ArrayList<>();
        // Tabla hash para recibir los tipos de trabajos entrantes
        HashMap<Integer, RestPuntoZona> puntoZonaMap = new HashMap<>();
        for (RestPuntoZona pz : puntoZonas) {
            puntoZonaMap.put((int) pz.getPZON_ID(),pz);
        }

        //Consultar registros remotos actuales
        Uri uri = ContratoCorpicoApp.PuntoZona.URI_CONTENIDO;
        String select = ContratoCorpicoApp.PuntoZona.TABLE_NAME;

        //TODO: ACA
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Zonas - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        int id;
        int zona_id;
        int indice;
        double latitud;
        double longitud;
        String fecha_update;


        while (c.moveToNext()) {
            id = c.getInt(c.getColumnIndex(ContratoCorpicoApp.PuntoZona.PZON_ID));
            zona_id = c.getInt(c.getColumnIndex(ContratoCorpicoApp.PuntoZona.PZON_ID_ZONA));
            indice = c.getInt(c.getColumnIndex(ContratoCorpicoApp.PuntoZona.PZON_INDICE));
            latitud = c.getDouble(c.getColumnIndex(ContratoCorpicoApp.PuntoZona.PZON_LATITUD));
            longitud = c.getDouble(c.getColumnIndex(ContratoCorpicoApp.PuntoZona.PZON_LONGITUD));
            fecha_update = c.getString(c.getColumnIndex(ContratoCorpicoApp.PuntoZona.PZON_FECHA_UPDATE));

            RestPuntoZona match = puntoZonaMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                puntoZonaMap.remove(id);

                Uri existingUri = ContratoCorpicoApp.PuntoZona.crearUriPuntoZona(id);

                // Comprobar si tipo de trabajos necesita ser actualizado
                boolean b = match.getPZON_ID() != id;
                boolean b1 = match.getPZON_ID_ZONA() != zona_id;
                boolean b2 = match.getPZON_INDICE() != indice;
                boolean b3 = match.getPZON_LATITUD() != latitud;
                boolean b4 = match.getPZON_LONGITUD() != longitud;
                boolean b5 = match.getPZON_FECHA_UPDATE() != fecha_update;


                if (b || b1 || b2 || b3 || b4 || b5 ) {

                    Log.i(TAG, "Tipos de Trabajos - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoCorpicoApp.PuntoZona.PZON_ID, match.getPZON_ID())
                            .withValue(ContratoCorpicoApp.PuntoZona.PZON_ID_ZONA, match.getPZON_ID_ZONA())
                            .withValue(ContratoCorpicoApp.PuntoZona.PZON_INDICE, match.getPZON_INDICE())
                            .withValue(ContratoCorpicoApp.PuntoZona.PZON_LATITUD, match.getPZON_LATITUD())
                            .withValue(ContratoCorpicoApp.PuntoZona.PZON_LONGITUD, match.getPZON_LONGITUD())
                            .withValue(ContratoCorpicoApp.PuntoZona.PZON_FECHA_UPDATE, match.getPZON_FECHA_UPDATE())
                            .build());
                } else {
                    Log.i(TAG, "Tipos de Trabajos - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoCorpicoApp.PuntoZona.crearUriPuntoZona(id);
                Log.i(TAG, "Punto Zonas - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestPuntoZona puntoZona : puntoZonaMap.values()){
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.PuntoZona.URI_CONTENIDO)
                    .withValue(ContratoCorpicoApp.PuntoZona.PZON_ID, puntoZona.getPZON_ID())
                    .withValue(ContratoCorpicoApp.PuntoZona.PZON_ID_ZONA, puntoZona.getPZON_ID_ZONA())
                    .withValue(ContratoCorpicoApp.PuntoZona.PZON_INDICE, puntoZona.getPZON_INDICE())
                    .withValue(ContratoCorpicoApp.PuntoZona.PZON_LATITUD, puntoZona.getPZON_LATITUD())
                    .withValue(ContratoCorpicoApp.PuntoZona.PZON_LONGITUD, puntoZona.getPZON_LONGITUD())
                    .withValue(ContratoCorpicoApp.PuntoZona.PZON_FECHA_UPDATE, puntoZona.getPZON_FECHA_UPDATE())
                    .build());
        }
        return operations;

    }
}
