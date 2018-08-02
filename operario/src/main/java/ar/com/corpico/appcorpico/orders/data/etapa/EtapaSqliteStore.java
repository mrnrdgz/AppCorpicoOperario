package ar.com.corpico.appcorpico.orders.data.etapa;

import android.content.AsyncQueryHandler;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestEtapa;
import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;

/**
 * Created by Administrador on 12/03/2018.
 */

public class EtapaSqliteStore implements EtapaStore {
    private static final String TAG = EtapaSqliteStore.class.getSimpleName();
    private static EtapaSqliteStore INSTANCE;
    private ContentResolver mContentResolver;


    private EtapaSqliteStore(@NonNull ContentResolver mContentResolver) {
        this.mContentResolver = Preconditions.checkNotNull(mContentResolver, "mContentResolver no puede ser null");
    }

    public static EtapaSqliteStore getInstance(@NonNull ContentResolver mContentResolver) {
        if (INSTANCE == null) {
            INSTANCE = new EtapaSqliteStore(mContentResolver);
        }
        return INSTANCE;
    }

    @Override
    public void getEtapa(final GetEtapaStoreCallBack callback, Criteria filter) {
        AsyncQueryEtapas handler = new AsyncQueryEtapas(mContentResolver);
        handler.setQueryListener(new  AsyncQueryEtapas.AsyncOpListener() {
            @Override
            public void onQueryComplete(int token, Object cookie, Cursor cursor) {
                List<Etapa> ListEtapa = new ArrayList<>();
                if (cursor != null && cursor.getCount()>0) {
                    while(cursor.moveToNext()) {
                        ListEtapa.add(new Etapa(new DateTime(cursor.getString(cursor.getColumnIndex(ContratoCorpicoApp.Etapas.ETA_FECHA))),cursor.getString(cursor.getColumnIndex(ContratoCorpicoApp.Etapas.ETA_ESTADO)),cursor.getString(cursor.getColumnIndex(ContratoCorpicoApp.Etapas.ETA_OBSERVACION)),cursor.getString(cursor.getColumnIndex(ContratoCorpicoApp.Etapas.ETA_USUARIO))));
                    }
                    callback.onSuccess(ListEtapa);
                }else{
                    callback.onError("No existen Etapas");
                }
            }
        });
// todo: paso 1 para dejarlo sucio localmente campo sync
        handler.startQuery(0, null, ContratoCorpicoApp.Etapas.URI_CONTENIDO, null, null, null, null);
    }
    public List<ContentProviderOperation> replicarServidor(List<RestEtapa> etapas) {
        //Crea lista de operaciones content provider
        List<ContentProviderOperation> operations = new ArrayList<>();

        // Tabla hash para recibir los tipos de cuadrillas entrantes
        HashMap<Integer, RestEtapa> etapaMap = new HashMap<>();
        for (RestEtapa e : etapas) {
            etapaMap.put(e.getETA_ID(), e);
        }

        //Consultar registros remotos actuales
        Uri uri = ContratoCorpicoApp.Etapas.URI_CONTENIDO;
        String select = ContratoCorpicoApp.Etapas.TABLE_NAME;
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Etapas - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        int orden;
        String fecha;
        String estado;
        String observacion;
        String usuario_id;
        int id;
        String fecha_update;
        int servicio;
        int sector;
        String latitud;
        String longitud;
        int id_temp;

        while (c.moveToNext()) {

            orden = c.getInt(0);
            fecha = c.getString(1);
            estado = c.getString(2);
            observacion = c.getString(3);
            usuario_id = c.getString(4);
            id = c.getInt(5);
            fecha_update = c.getString(6);
            servicio = c.getInt(7);
            sector = c.getInt(8);
            latitud = c.getString(9);
            longitud = c.getString(10);
            id_temp = c.getInt(11);

            RestEtapa match = etapaMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                etapaMap.remove(id);

                Uri existingUri = ContratoCorpicoApp.Etapas.crearUriEtapa(id);//PASAR LOS ID A INTEGER

                // Comprobar si tipo de cuadrillas necesita ser actualizado
                boolean b = match.getETA_NUMERO_ORDEN() != orden;
                boolean b1 = match.getETA_FECHA() != fecha;
                boolean b2 =  match.getETA_ESTADO() != estado;
                boolean b3 =  !match.getETA_OBSERVACION().equals(observacion);
                boolean b4 = match.getETA_USUARIO() != usuario_id;
                boolean b5 = match.getETA_SERVICIO() != servicio;
                boolean b6 = match.getETA_SECTOR() != sector;
                boolean b7 = match.getETA_FECHA_UPDATE() != fecha_update;
                boolean b8 = match.getETA_ID() != id;
                boolean b9 = match.getETA_LATITUD() != latitud;
                boolean b10 = match.getETA_LONGITUD() != longitud;
                boolean b11 = match.getETA_ID_TEMP() != id_temp;


                if (b || b1 || b2 || b3 || b4  || b5  || b6  || b7  || b8 || b9 || b10 || b11) {

                    Log.i(TAG, "Etapas - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoCorpicoApp.Etapas.ETA_ID, match.getETA_ID())
                            .withValue(ContratoCorpicoApp.Etapas.ETA_ID_TEMP, match.getETA_ID_TEMP())
                            .withValue(ContratoCorpicoApp.Etapas.ETA_NUMERO_ORDEN, match.getETA_NUMERO_ORDEN())
                            .withValue(ContratoCorpicoApp.Etapas.ETA_FECHA, match.getETA_FECHA())
                            .withValue(ContratoCorpicoApp.Etapas.ETA_ESTADO, match.getETA_ESTADO())
                            .withValue(ContratoCorpicoApp.Etapas.ETA_OBSERVACION, match.getETA_OBSERVACION())
                            .withValue(ContratoCorpicoApp.Etapas.ETA_USUARIO, match.getETA_USUARIO())
                            .withValue(ContratoCorpicoApp.Etapas.ETA_FECHA_UPDATE, match.getETA_FECHA_UPDATE())
                            .withValue(ContratoCorpicoApp.Etapas.ETA_SERVICIO, match.getETA_SERVICIO())
                            .withValue(ContratoCorpicoApp.Etapas.ETA_SECTOR, match.getETA_SECTOR())
                            .withValue(ContratoCorpicoApp.Etapas.ETA_LATITUD, match.getETA_LATITUD())
                            .withValue(ContratoCorpicoApp.Etapas.ETA_LONGITUD, match.getETA_LONGITUD())
                            .withValue(ContratoCorpicoApp.Etapas.ETA_SYNC, ContratoCorpicoApp.SYNC_OK)
                            .build());
                } else {
                    Log.i(TAG, "Etapas - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoCorpicoApp.Etapas.crearUriEtapa(id);
                Log.i(TAG, "Etapas - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestEtapa etapa : etapaMap.values()){
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.Etapas.URI_CONTENIDO)
                    .withValue(ContratoCorpicoApp.Etapas.ETA_ID, etapa.getETA_ID())
                    .withValue(ContratoCorpicoApp.Etapas.ETA_ID_TEMP, etapa.getETA_ID_TEMP())
                    .withValue(ContratoCorpicoApp.Etapas.ETA_NUMERO_ORDEN, etapa.getETA_NUMERO_ORDEN())
                    .withValue(ContratoCorpicoApp.Etapas.ETA_FECHA, etapa.getETA_FECHA())
                    .withValue(ContratoCorpicoApp.Etapas.ETA_ESTADO, etapa.getETA_ESTADO())
                    .withValue(ContratoCorpicoApp.Etapas.ETA_OBSERVACION, etapa.getETA_OBSERVACION())
                    .withValue(ContratoCorpicoApp.Etapas.ETA_USUARIO, etapa.getETA_USUARIO())
                    .withValue(ContratoCorpicoApp.Etapas.ETA_FECHA_UPDATE, etapa.getETA_FECHA_UPDATE())
                    .withValue(ContratoCorpicoApp.Etapas.ETA_SERVICIO, etapa.getETA_SERVICIO())
                    .withValue(ContratoCorpicoApp.Etapas.ETA_SECTOR, etapa.getETA_SECTOR())
                    .withValue(ContratoCorpicoApp.Etapas.ETA_LATITUD, etapa.getETA_LATITUD())
                    .withValue(ContratoCorpicoApp.Etapas.ETA_LONGITUD, etapa.getETA_LONGITUD())
                    .withValue(ContratoCorpicoApp.Etapas.ETA_SYNC, ContratoCorpicoApp.SYNC_OK)
                    .build());
        }
        return operations;
    }
    public static class AsyncQueryEtapas extends AsyncQueryHandler {
        private WeakReference<AsyncOpListener> mListener;

        public interface AsyncOpListener {
            void onQueryComplete(int token, Object cookie, Cursor cursor);
        }

        public AsyncQueryEtapas(ContentResolver cr) {
            super(cr);
        }

        public void setQueryListener(AsyncOpListener listener) {
            mListener = new WeakReference<>(listener);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            final AsyncOpListener listener = mListener.get();
            if (listener != null) {
                listener.onQueryComplete(token, cookie, cursor);
            } else if (cursor != null) {
                cursor.close();
            }
        }

    }
}
