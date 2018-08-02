package ar.com.corpico.appcorpico.orders.data.tipoTrabajoCuadrilla;

import android.content.AsyncQueryHandler;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.common.base.Preconditions;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoTrabajoCuadrilla;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;

/**
 * Created by Administrador on 12/03/2018.
 */

public class TipoTrabajoCuadrillaSqliteStore implements TipoTrabajoCuadrillaStore {
    private static final String TAG = TipoTrabajoCuadrillaSqliteStore.class.getSimpleName();
    private static TipoTrabajoCuadrillaSqliteStore INSTANCE;
    private ContentResolver mContentResolver;

    public TipoTrabajoCuadrillaSqliteStore(@NonNull ContentResolver mContentResolver) {
        this.mContentResolver = Preconditions.checkNotNull(mContentResolver, "mContentResolver no puede ser null");
    }
    public static TipoTrabajoCuadrillaSqliteStore getInstance(ContentResolver mContentResolver) {
        if (INSTANCE == null) {
            INSTANCE = new TipoTrabajoCuadrillaSqliteStore(mContentResolver);
        }
        return INSTANCE;
    }
    @Override
    public void getTipoTrabajoCuadrilla(final GetTipoTrabajoCuadrillaStoreCallBack callback, Criteria filter) {
        AsyncQueryTipoTabajosCuadrillas handler = new AsyncQueryTipoTabajosCuadrillas(mContentResolver);
        handler.setQueryListener(new  AsyncQueryTipoTabajosCuadrillas.AsyncOpListener() {
            @Override
            public void onQueryComplete(int token, Object cookie, Cursor cursor) {
                //TODO: Esta query debo consultar al la tabla tipo_trabajo_cuadrillas...hacer un inner join (ver)
                List<RestTipoTrabajoCuadrilla> ListTipoTrabajoCuadrilla = new ArrayList<>();
                if (cursor != null) {
                    while(cursor.moveToNext()) {
                        ListTipoTrabajoCuadrilla.add(new RestTipoTrabajoCuadrilla(cursor.getInt(cursor.getColumnIndex(ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_CUADRILLA)),
                                cursor.getInt(cursor.getColumnIndex(ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_TIPO_TRABAJO)),
                                cursor.getString(cursor.getColumnIndex(ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_FECHA_UPDATE))));

                    }

                    callback.onSuccess(ListTipoTrabajoCuadrilla);
                }
                callback.onError("No existen Tipos de Trabajos");
            }
        });

        handler.startQuery(0, null, ContratoCorpicoApp.TipoTrabajoCuadrilla.URI_CONTENIDO, null, null, null, null);
    }
    public List<ContentProviderOperation> replicarServidor(List<RestTipoTrabajoCuadrilla> tipoTrabajoCuadrillas) {
        //TODO CREAR UNA LISTA DE OPERACION DEL PROVIDER
        List<ContentProviderOperation> operations = new ArrayList<>();
        // Tabla hash para recibir los tipos de trabajos entrantes
        String ids;
        HashMap<String, RestTipoTrabajoCuadrilla> tipoTrabajoCuadrillaMap = new HashMap<>();
        for (RestTipoTrabajoCuadrilla ttc : tipoTrabajoCuadrillas) {
            ids = ttc.getTipoCuadrillaID() + "#" + ttc.getTipoTabajoId();
            tipoTrabajoCuadrillaMap.put(ids, ttc);
        }

        //Consultar registros remotos actuales
        Uri uri = ContratoCorpicoApp.TipoTrabajoCuadrilla.URI_CONTENIDO;
        String select = ContratoCorpicoApp.TipoTrabajoCuadrilla.TABLE_NAME;
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Tipos de Trabajos - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        Integer cuadrilla;
        Integer tipoTrabajo;
        String fecha_update;

        while (c.moveToNext()) {
            cuadrilla = c.getInt(c.getColumnIndex(ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_CUADRILLA));
            tipoTrabajo = c.getInt(c.getColumnIndex(ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_TIPO_TRABAJO));
            fecha_update = c.getString(c.getColumnIndex(ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_FECHA_UPDATE));


            ids = cuadrilla + "#" + tipoTrabajo;
            RestTipoTrabajoCuadrilla match = tipoTrabajoCuadrillaMap.get(ids);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                tipoTrabajoCuadrillaMap.remove(ids);

                Uri existingUri = ContratoCorpicoApp.TipoTrabajoCuadrilla.crearUriTipoTrabajoCuadrilla(cuadrilla,tipoTrabajo);

                // Comprobar si tipo de trabajos necesita ser actualizado
                boolean b = match.getTipoCuadrillaID() != cuadrilla;
                boolean b1 = match.getTipoTabajoId() != tipoTrabajo;
                boolean b2 = match.getFecha_update() != fecha_update;


                if (b || b1 || b2 ) {

                    Log.i(TAG, "Tipos de Trabajos - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_CUADRILLA, match.getTipoCuadrillaID())
                            .withValue(ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_TIPO_TRABAJO, match.getTipoTabajoId())
                            .withValue(ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_FECHA_UPDATE, match.getFecha_update())
                            .build());
                } else {
                    Log.i(TAG, "Tipos de Trabajos - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoCorpicoApp.TipoTrabajoCuadrilla.crearUriTipoTrabajoCuadrilla(cuadrilla,tipoTrabajo);
                Log.i(TAG, "Tipos de Trabajos - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestTipoTrabajoCuadrilla tipoTrabajoCuadrilla : tipoTrabajoCuadrillaMap.values()){
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.TipoTrabajoCuadrilla.URI_CONTENIDO)
                    .withValue(ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_CUADRILLA, tipoTrabajoCuadrilla.getTipoCuadrillaID())
                    .withValue(ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_TIPO_TRABAJO, tipoTrabajoCuadrilla.getTipoTabajoId())
                    .withValue(ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_FECHA_UPDATE, tipoTrabajoCuadrilla.getFecha_update())
                    .build());
        }
        return operations;

    }

    public static class AsyncQueryTipoTabajosCuadrillas extends AsyncQueryHandler {
        private WeakReference<AsyncOpListener> mListener;

        public interface AsyncOpListener {
            void onQueryComplete(int token, Object cookie, Cursor cursor);
        }

        public AsyncQueryTipoTabajosCuadrillas(ContentResolver cr) {
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
