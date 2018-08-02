package ar.com.corpico.appcorpico.orders.data.tipoConexion;

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

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoConexion;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;

/**
 * Created by Administrador on 12/03/2018.
 */

public class TipoConexionSqliteStore implements TipoConexionStore {
    private static final String TAG = TipoConexionSqliteStore.class.getSimpleName();
    private TipoConexionSqliteStore INSTANCE;
    private ContentResolver mContentResolver;


    public TipoConexionSqliteStore(@NonNull ContentResolver mContentResolver) {
        this.mContentResolver = Preconditions.checkNotNull(mContentResolver, "mContentResolver no puede ser null");
    }

    public TipoConexionSqliteStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TipoConexionSqliteStore(mContentResolver);
        }
        return INSTANCE;
    }

    @Override
    public void getTipoConexion(final GetTipoConexionStoreCallBack callback, Criteria filter) {
        AsyncQueryTipoConexion handler = new AsyncQueryTipoConexion(mContentResolver);
        handler.setQueryListener(new  AsyncQueryTipoConexion.AsyncOpListener() {
            @Override
            public void onQueryComplete(int token, Object cookie, Cursor cursor) {
                List<RestTipoConexion> ListTipoConexion = new ArrayList<>();
                if (cursor != null && cursor.getCount()>0) {
                    while(cursor.moveToNext()) {
                        ListTipoConexion.add(new RestTipoConexion(cursor.getShort(0),
                                cursor.getString(1), (byte) cursor.getInt(2), cursor.getDouble(3), cursor.getString(4),
                                cursor.getString(5), cursor.getString(6), cursor.getInt(7)));

                    }
                    callback.onSuccess(ListTipoConexion);
                }else{
                    callback.onError("No existen Tipo Conexion");
                }
            }
        });
// todo: paso 1 para dejarlo sucio localmente campo sync
        handler.startQuery(0, null, ContratoCorpicoApp.TipoConexion.URI_CONTENIDO, null, null, null, null);
    }
    public List<ContentProviderOperation> replicarServidor(List<RestTipoConexion> tiposConexiones) {
        //Crea lista de operaciones content provider
        List<ContentProviderOperation> operations = new ArrayList<>();

        // Tabla hash para recibir los tipos de cuadrillas entrantes
        HashMap<Integer, RestTipoConexion> tipoConexionMap = new HashMap<>();
        for (RestTipoConexion tco : tiposConexiones) {
            tipoConexionMap.put((int) tco.getTCO_ID(), tco);
        }

        //Consultar registros remotos actuales
        Uri uri = ContratoCorpicoApp.TipoConexion.URI_CONTENIDO;
        String select = ContratoCorpicoApp.TipoConexion.TABLE_NAME;
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Tipo Conexion - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        short id;
        String descripcion;
        byte tipo_empresa;
        double factor;
        String activo;
        String user_id;
        String fecha_update;
        int lote_replicacion;

        while (c.moveToNext()) {
            id = c.getShort(0);
            descripcion = c.getString(1);
            tipo_empresa = (byte) c.getInt(2);
            factor = c.getDouble(3);
            activo = activo = c.getString(4);
            user_id = c.getString(5);
            fecha_update = c.getString(6);
            lote_replicacion = c.getInt(7);


            RestTipoConexion match = tipoConexionMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                tipoConexionMap.remove(id);

                Uri existingUri = ContratoCorpicoApp.TipoConexion.crearUriTipoConexion((int) id);//PASAR LOS ID A INTEGER

                // Comprobar si tipo de cuadrillas necesita ser actualizado
                boolean b = match.getTCO_ID() != id;
                boolean b1 = match.getTCO_DESCRIPCION() != descripcion;
                boolean b2 =  match.getTCO_TIPO_EMPRESA() != tipo_empresa;
                boolean b3 = match.getTCO_FACTOR() != factor;
                boolean b4 = match.getTCO_ACTIVO() != activo;
                boolean b5 = match.getTCO_ID_USER() != user_id;
                boolean b6 = match.getTCO_FECHA_UPDATE() != fecha_update;
                boolean b7 = match.getTCO_LOTE_REPLICACION() != lote_replicacion;


                if (b || b1 || b2 || b3 || b4  || b5  || b6 || b7) {

                    Log.i(TAG, "Tipo Conexion - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoCorpicoApp.TipoConexion.TCO_ID, match.getTCO_ID())
                            .withValue(ContratoCorpicoApp.TipoConexion.TCO_DESCRIPCION, match.getTCO_DESCRIPCION())
                            .withValue(ContratoCorpicoApp.TipoConexion.TCO_TIPO_EMPRESA, match.getTCO_TIPO_EMPRESA())
                            .withValue(ContratoCorpicoApp.TipoConexion.TCO_FACTOR, match.getTCO_FACTOR())
                            .withValue(ContratoCorpicoApp.TipoConexion.TCO_ACTIVO, match.getTCO_ACTIVO())
                            .withValue(ContratoCorpicoApp.TipoConexion.TCO_ID_USER, match.getTCO_ID_USER())
                            .withValue(ContratoCorpicoApp.TipoConexion.TCO_FECHA_UPDATE, match.getTCO_FECHA_UPDATE())
                            .withValue(ContratoCorpicoApp.TipoConexion.TCO_LOTE_REPLICACION, match.getTCO_LOTE_REPLICACION())
                            .build());
                } else {
                    Log.i(TAG, "Tipo Conexion - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoCorpicoApp.TipoConexion.crearUriTipoConexion((int) id);
                Log.i(TAG, "Tipo Conexion - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestTipoConexion tipoConexion : tipoConexionMap.values()){
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.TipoConexion.URI_CONTENIDO)
                    .withValue(ContratoCorpicoApp.TipoConexion.TCO_ID, tipoConexion.getTCO_ID())
                    .withValue(ContratoCorpicoApp.TipoConexion.TCO_DESCRIPCION, tipoConexion.getTCO_DESCRIPCION())
                    .withValue(ContratoCorpicoApp.TipoConexion.TCO_TIPO_EMPRESA, tipoConexion.getTCO_TIPO_EMPRESA())
                    .withValue(ContratoCorpicoApp.TipoConexion.TCO_FACTOR, tipoConexion.getTCO_FACTOR())
                    .withValue(ContratoCorpicoApp.TipoConexion.TCO_ACTIVO, tipoConexion.getTCO_ACTIVO())
                    .withValue(ContratoCorpicoApp.TipoConexion.TCO_ID_USER, tipoConexion.getTCO_ID_USER())
                    .withValue(ContratoCorpicoApp.TipoConexion.TCO_FECHA_UPDATE, tipoConexion.getTCO_FECHA_UPDATE())
                    .withValue(ContratoCorpicoApp.TipoConexion.TCO_LOTE_REPLICACION, tipoConexion.getTCO_LOTE_REPLICACION())
                    .build());
        }
        return operations;
    }
    public static class AsyncQueryTipoConexion extends AsyncQueryHandler {
        private WeakReference<AsyncOpListener> mListener;

        public interface AsyncOpListener {
            void onQueryComplete(int token, Object cookie, Cursor cursor);
        }

        public AsyncQueryTipoConexion(ContentResolver cr) {
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
