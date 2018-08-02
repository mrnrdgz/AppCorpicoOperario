package ar.com.corpico.appcorpico.orders.data.estado;

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

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestEstado;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;

/**
 * Created by Administrador on 12/03/2018.
 */

public class EstadoSqliteStore implements EstadoStore {
    private static final String TAG = EstadoSqliteStore.class.getSimpleName();
    private static EstadoSqliteStore INSTANCE;
    private ContentResolver mContentResolver;

    private EstadoSqliteStore(@NonNull ContentResolver mContentResolver) {
        this.mContentResolver = Preconditions.checkNotNull(mContentResolver, "mContentResolver no puede ser null");
    }

    public static EstadoSqliteStore getInstance(@NonNull ContentResolver mContentResolver) {
        if (INSTANCE == null) {
            INSTANCE = new EstadoSqliteStore(mContentResolver);
        }
        return INSTANCE;
    }

    @Override
    public void getEstado(final GetEstadoStoreCallBack callback) {
        Cursor estadoCursor = mContentResolver.query(ContratoCorpicoApp.Estado.URI_CONTENIDO, null, null, null, null);
        List<RestEstado> ListEstado = new ArrayList<>();
        if (estadoCursor != null && estadoCursor.getCount()>0) {
            while(estadoCursor.moveToNext()) {
                ListEstado.add(new RestEstado(estadoCursor.getString(estadoCursor.getColumnIndex(ContratoCorpicoApp.Estado.OOS_ID)),
                        estadoCursor.getString(estadoCursor.getColumnIndex(ContratoCorpicoApp.Estado.OOS_DESCRIPCION))));

            }
            callback.onSuccess(ListEstado);
        }else{
            callback.onError("No existen Estados");
        }
    }
    public List<ContentProviderOperation> replicarServidor(List<RestEstado> estados) {
        //Crea lista de operaciones content provider
        List<ContentProviderOperation> operations = new ArrayList<>();

        // Tabla hash para recibir los tipos de cuadrillas entrantes
        HashMap<String, RestEstado> estadoMap = new HashMap<>();
        for (RestEstado e : estados) {
            estadoMap.put(e.getId(), e);
        }

        //Consultar registros remotos actuales
        Uri uri = ContratoCorpicoApp.Estado.URI_CONTENIDO;
        String select = ContratoCorpicoApp.Estado.TABLE_NAME;
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Estados - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        String estado;

        while (c.moveToNext()) {

            id = c.getString(c.getColumnIndex(ContratoCorpicoApp.Estado.OOS_ID));
            estado = c.getString(c.getColumnIndex(ContratoCorpicoApp.Estado.OOS_DESCRIPCION));

            RestEstado match = estadoMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                estadoMap.remove(id);

                Uri existingUri = ContratoCorpicoApp.Estado.crearUriEstado(id);//PASAR LOS ID A INTEGER

                // Comprobar si tipo de cuadrillas necesita ser actualizado
                boolean b = match.getId() != id;
                boolean b1 = match.getEstado() != estado;

                if (b || b1) {

                    Log.i(TAG, "Estados - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoCorpicoApp.Estado.OOS_DESCRIPCION, match.getEstado())
                            .build());
                } else {
                    Log.i(TAG, "Estados - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoCorpicoApp.Estado.crearUriEstado(id);
                Log.i(TAG, "Estado - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestEstado state : estadoMap.values()){
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.Estado.URI_CONTENIDO)
                    .withValue(ContratoCorpicoApp.Estado.OOS_ID, state.getId())
                    .withValue(ContratoCorpicoApp.Estado.OOS_DESCRIPCION, state.getEstado())
                    .build());
        }
        return operations;
    }
    public static class AsyncQueryEstados extends AsyncQueryHandler {
        private WeakReference<AsyncOpListener> mListener;

        public interface AsyncOpListener {
            void onQueryComplete(int token, Object cookie, Cursor cursor);
        }

        public AsyncQueryEstados(ContentResolver cr) {
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
