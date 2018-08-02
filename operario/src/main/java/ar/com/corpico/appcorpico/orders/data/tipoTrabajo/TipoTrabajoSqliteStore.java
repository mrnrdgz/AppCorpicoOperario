package ar.com.corpico.appcorpico.orders.data.tipoTrabajo;

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

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoTrabajo;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;

/**
 * Created by Administrador on 12/03/2018.
 */

public class TipoTrabajoSqliteStore implements TipoTrabajoStore {
    private static final String TAG = TipoTrabajoSqliteStore.class.getSimpleName();
    private static TipoTrabajoSqliteStore INSTANCE;

    private ContentResolver mContentResolver;

    private TipoTrabajoSqliteStore(@NonNull ContentResolver mContentResolver) {
        this.mContentResolver = Preconditions.checkNotNull(mContentResolver,
                "mContentResolver no puede ser null");
    }
    public static TipoTrabajoSqliteStore getInstance(@NonNull ContentResolver mContentResolver) {
        if (INSTANCE == null) {
            INSTANCE = new TipoTrabajoSqliteStore(mContentResolver);
        }
        return INSTANCE;
    }
    @Override
    public void getTipoTrabajo(final GetTipoTrabajoStoreCallBack callback, final Criteria filter) {
        Cursor tipoTrabajosCursor = mContentResolver.query(ContratoCorpicoApp.TiposTrabajo.crearUriConCuadrilla("default"), null, null, null, null);
        List<Tipo_Trabajo> ListTipoTrabajo = new ArrayList<>();

        if (tipoTrabajosCursor != null && tipoTrabajosCursor.getCount()>0) {
            while(tipoTrabajosCursor.moveToNext()) {
                ListTipoTrabajo.add(new Tipo_Trabajo(tipoTrabajosCursor.getString(0),tipoTrabajosCursor.getString(1)));
            }

            callback.onSuccess(filter.match(ListTipoTrabajo)); // ESto esta bien?
        }else {
            callback.onError("No existen Tipos de Trabajos");
        }
        tipoTrabajosCursor.close();
        /*AsyncQueryTipoTabajos handler = new AsyncQueryTipoTabajos(mContentResolver);
        handler.setQueryListener(new  AsyncQueryTipoTabajos.AsyncOpListener() {
            @Override
            public void onQueryComplete(int token, Object cookie, Cursor cursor) {
                //TODO: Esta query debo consultar al la tabla tipo_trabajo_cuadrillas...hacer un inner join (ver)
                List<Tipo_Trabajo> ListTipoTrabajo = new ArrayList<>();

                if (cursor != null && cursor.getCount()>0) {
                    while(cursor.moveToNext()) {
                        ListTipoTrabajo.add(new Tipo_Trabajo(cursor.getString(0),cursor.getString(1)));
                    }

                    callback.onSuccess(filter.match(ListTipoTrabajo)); // ESto esta bien?
                }else {
                    callback.onError("No existen Tipos de Trabajos");
                }
            }
        });

        handler.startQuery(0, null, ContratoCorpicoApp.TiposTrabajo.crearUriConCuadrilla("default"), null, null, null, null);
        //handler.startQuery(0, null, ContratoCorpicoApp.TiposTrabajo.URI_CONTENIDO, null, null, null, null);*/
    }
    public List<ContentProviderOperation> replicarServidor(List<RestTipoTrabajo> tipoTrabajos) {
        //TODO CREAR UNA LISTA DE OPERACION DEL PROVIDER
        List<ContentProviderOperation> operations = new ArrayList<>();
        // Tabla hash para recibir los tipos de trabajos entrantes
        HashMap<Integer, RestTipoTrabajo> tipoTrabajoMap = new HashMap<>();
        for (RestTipoTrabajo tt : tipoTrabajos) {
            tipoTrabajoMap.put(tt.getId(), tt);
        }

        //Consultar registros remotos actuales
        Uri uri = ContratoCorpicoApp.TiposTrabajo.URI_CONTENIDO;
        String select = ContratoCorpicoApp.TiposTrabajo.TABLE_NAME;
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Tipos de Trabajos - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        Integer id;
        String descripcion;
        String abreviatura;
        int tipoEmpresa;
        int clasificacionTrabajo;
        String permiteEliminar;
        String activo;
        String userId;
        String fechaUpdate;
        int loteReplicacion;

        while (c.moveToNext()) {
            id = c.getInt(0);
            descripcion = c.getString(1);
            abreviatura = c.getString(2);
            tipoEmpresa = c.getInt(3);
            clasificacionTrabajo = c.getInt(4);
            permiteEliminar = c.getString(5);
            activo = c.getString(6);
            userId = c.getString(7);
            fechaUpdate = c.getString(8);
            loteReplicacion = c.getInt(9);

            RestTipoTrabajo match = tipoTrabajoMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                tipoTrabajoMap.remove(id);

                Uri existingUri = ContratoCorpicoApp.TiposTrabajo.crearUriTipoTrabajo(id);

                // Comprobar si tipo de trabajos necesita ser actualizado
                boolean b = match.getId() != id;
                boolean b1 = match.getDescripcion() != descripcion;
                boolean b2 = match.getAbreviatura() != abreviatura;
                boolean b3 = match.getTipoEmpresa() != tipoEmpresa;
                boolean b4 = match.getClasificacionTrabajo() != clasificacionTrabajo;
                boolean b5 = match.getPermiteEliminar() != permiteEliminar;
                boolean b6 = match.getActivo() != activo;
                boolean b7 = match.getUserId() != userId;
                boolean b8 = match.getFechaUpdate() != fechaUpdate;
                boolean b9 = match.getLoteReplicacion() != loteReplicacion;

                if (b || b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8 || b9) {

                    Log.i(TAG, "Tipos de Trabajos - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoCorpicoApp.TiposTrabajo.TIT_ID, match.getId())
                            .withValue(ContratoCorpicoApp.TiposTrabajo.TIT_DESCRIPCION, match.getDescripcion())
                            .withValue(ContratoCorpicoApp.TiposTrabajo.TIT_ABREVIATURA, match.getAbreviatura())
                            .withValue(ContratoCorpicoApp.TiposTrabajo.TIT_TIPO_EMPRESA, match.getTipoEmpresa())
                            .withValue(ContratoCorpicoApp.TiposTrabajo.TIT_CLASIFICACION_TRABAJO, match.getClasificacionTrabajo())
                            .withValue(ContratoCorpicoApp.TiposTrabajo.TIT_PERMITE_ELIMINAR, match.getPermiteEliminar())
                            .withValue(ContratoCorpicoApp.TiposTrabajo.TIT_ACTIVO, match.getActivo())
                            .withValue(ContratoCorpicoApp.TiposTrabajo.TIT_ID_USER, match.getUserId())
                            .withValue(ContratoCorpicoApp.TiposTrabajo.TIT_FECHA_UPDATE, match.getFechaUpdate())
                            .withValue(ContratoCorpicoApp.TiposTrabajo.TIT_LOTE_REPLICACION, match.getLoteReplicacion())
                            .build());
                } else {
                    Log.i(TAG, "Tipos de Trabajos - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoCorpicoApp.TiposTrabajo.crearUriTipoTrabajo(id);
                Log.i(TAG, "Tipos de Trabajos - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestTipoTrabajo tipoTrabajo : tipoTrabajoMap.values()){
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.TiposTrabajo.URI_CONTENIDO)
                    .withValue(ContratoCorpicoApp.TiposTrabajo.TIT_ID, tipoTrabajo.getId())
                    .withValue(ContratoCorpicoApp.TiposTrabajo.TIT_DESCRIPCION, tipoTrabajo.getDescripcion())
                    .withValue(ContratoCorpicoApp.TiposTrabajo.TIT_ABREVIATURA, tipoTrabajo.getAbreviatura())
                    .withValue(ContratoCorpicoApp.TiposTrabajo.TIT_TIPO_EMPRESA, tipoTrabajo.getTipoEmpresa())
                    .withValue(ContratoCorpicoApp.TiposTrabajo.TIT_CLASIFICACION_TRABAJO, tipoTrabajo.getClasificacionTrabajo())
                    .withValue(ContratoCorpicoApp.TiposTrabajo.TIT_PERMITE_ELIMINAR, tipoTrabajo.getPermiteEliminar())
                    .withValue(ContratoCorpicoApp.TiposTrabajo.TIT_ACTIVO, tipoTrabajo.getActivo())
                    .withValue(ContratoCorpicoApp.TiposTrabajo.TIT_ID_USER, tipoTrabajo.getUserId())
                    .withValue(ContratoCorpicoApp.TiposTrabajo.TIT_FECHA_UPDATE, tipoTrabajo.getFechaUpdate())
                    .withValue(ContratoCorpicoApp.TiposTrabajo.TIT_LOTE_REPLICACION, tipoTrabajo.getLoteReplicacion())
                    .build());
        }
        return operations;

    }

    public static class AsyncQueryTipoTabajos extends AsyncQueryHandler {
        private WeakReference<AsyncOpListener> mListener;

        public interface AsyncOpListener {
            void onQueryComplete(int token, Object cookie, Cursor cursor);
        }

        public AsyncQueryTipoTabajos(ContentResolver cr) {
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
