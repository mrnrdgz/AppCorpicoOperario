package ar.com.corpico.appcorpico.cuadrillas.data;

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

import ar.com.corpico.appcorpico.cuadrillas.data.entity.RestCuadrilla;
import ar.com.corpico.appcorpico.cuadrillas.domain.entity.Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;

/**
 * Created by Administrador on 12/03/2018.
 */

public class CuadrillaSqliteStore implements CuadrillaStore {
    private static final String TAG = CuadrillaSqliteStore.class.getSimpleName();
    private static CuadrillaSqliteStore INSTANCE;
    private ContentResolver mContentResolver;

    private CuadrillaSqliteStore(@NonNull ContentResolver mContentResolver) {
        this.mContentResolver = Preconditions.checkNotNull(mContentResolver, "mContentResolver no puede ser null");
    }

    public static CuadrillaSqliteStore getInstance(@NonNull ContentResolver mContentResolver) {
        if (INSTANCE == null) {
            INSTANCE = new CuadrillaSqliteStore(mContentResolver);
        }
        return INSTANCE;
    }

    @Override
    public void getCuadrilla(final GetCuadrillaStoreCallBack callback, Criteria filter) {
        Cursor cuadrillasCursor = mContentResolver.query(ContratoCorpicoApp.Cuadrilla.crearUriConOperario(ContratoCorpicoApp.Cuadrilla.DISPLAY_FULL), null, null, null, null);
        List<Cuadrilla> ListCuadrilla = new ArrayList<>();
        if (cuadrillasCursor != null && cuadrillasCursor.getCount() > 0) {
            while (cuadrillasCursor.moveToNext()) {
                ListCuadrilla.add(new Cuadrilla(cuadrillasCursor.getInt(cuadrillasCursor.getColumnIndex(ContratoCorpicoApp.Cuadrilla.CU_ID)),
                        cuadrillasCursor.getInt(cuadrillasCursor.getColumnIndex(ContratoCorpicoApp.Cuadrilla.CU_TIPO_CUADRILLA)),
                        cuadrillasCursor.getInt(cuadrillasCursor.getColumnIndex(ContratoCorpicoApp.Cuadrilla.CU_OPERARIO)),
                        cuadrillasCursor.getString(cuadrillasCursor.getColumnIndex(ContratoCorpicoApp.Operario.OPE_DESCRIPCION)),
                        cuadrillasCursor.getString(cuadrillasCursor.getColumnIndex(ContratoCorpicoApp.Cuadrilla.CU_FECHA)),
                        cuadrillasCursor.getString(cuadrillasCursor.getColumnIndex(ContratoCorpicoApp.Cuadrilla.CU_FECHA_UPDATE)),
                        cuadrillasCursor.getInt(cuadrillasCursor.getColumnIndex(ContratoCorpicoApp.Cuadrilla.CU_SERVICIO)),
                        cuadrillasCursor.getInt(cuadrillasCursor.getColumnIndex(ContratoCorpicoApp.Cuadrilla.CU_SECTOR))));

            }
            callback.onSuccess(filter.match(ListCuadrilla));
        } else {
            //callback.onError("No existen Cuadrillas");
            //todo: como hago aca xq si muestro el error no me manda la lista vacia y no me refresca
            callback.onSuccess(ListCuadrilla);
        }
        cuadrillasCursor.close();
    }

    @Override
    public void removeOperario(RemoveOperarioStoreCallBack callback, int operario) {
        Uri deleteUri = ContratoCorpicoApp.Operario.crearUriOperario(operario);

        Log.i(TAG, "Elimina Operario - Programando eliminación de: " + deleteUri);

        int result = mContentResolver.delete(deleteUri, null, null);

        if (result > 0) {
            callback.onSuccess();
        } else {
            callback.onError("No se ha podido eliminar el operario de la cuadrilla");
        }

    }


    public List<ContentProviderOperation> replicarServidor(List<RestCuadrilla> cuadrillas) {
        //Crea lista de operaciones content provider
        List<ContentProviderOperation> operations = new ArrayList<>();

        // Tabla hash para recibir los tipos de cuadrillas entrantes
        HashMap<Integer, RestCuadrilla> cuadrillaMap = new HashMap<>();
        for (RestCuadrilla c : cuadrillas) {
            cuadrillaMap.put(c.getId(), c);
        }

        //Consultar registros remotos actuales
        Uri uri = ContratoCorpicoApp.Cuadrilla.URI_CONTENIDO;
        String select = ContratoCorpicoApp.Cuadrilla.TABLE_NAME;
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Tipo Cuadrillas - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        int id;
        int tipo_cuadrilla;
        int operario;
        String fecha;
        String fecha_update;
        int servicio;
        int sector;

        while (c.moveToNext()) {

            id = c.getInt(c.getColumnIndex(ContratoCorpicoApp.Cuadrilla.CU_ID));
            tipo_cuadrilla = c.getInt(c.getColumnIndex(ContratoCorpicoApp.Cuadrilla.CU_TIPO_CUADRILLA));
            operario = c.getInt(c.getColumnIndex(ContratoCorpicoApp.Cuadrilla.CU_OPERARIO));
            fecha = c.getString(c.getColumnIndex(ContratoCorpicoApp.Cuadrilla.CU_FECHA));
            fecha_update = c.getString(c.getColumnIndex(ContratoCorpicoApp.Cuadrilla.CU_FECHA_UPDATE));
            servicio = c.getInt(c.getColumnIndex(ContratoCorpicoApp.Cuadrilla.CU_SERVICIO));
            sector = c.getInt(c.getColumnIndex(ContratoCorpicoApp.Cuadrilla.CU_SECTOR));

            RestCuadrilla match = cuadrillaMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                cuadrillaMap.remove(id);

                Uri existingUri = ContratoCorpicoApp.Cuadrilla.crearUriCuadrilla(id);//PASAR LOS ID A INTEGER

                // Comprobar si tipo de cuadrillas necesita ser actualizado
                boolean b = match.getId() != id;
                boolean b1 = match.getTipo_cuadrilla() != tipo_cuadrilla;
                boolean b2 = match.getOperario() != operario;
                boolean b3 = !match.getFecha().equals(fecha);
                boolean b4 = match.getFecha_update() != null && !match.getFecha_update().equals(fecha_update);
                boolean b5 = match.getServicio() != servicio;
                boolean b6 = match.getSector() != sector;

                if (b || b1 || b2 || b3 || b4 || b5 || b6) {

                    Log.i(TAG, "Tipo Cuadrillas - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoCorpicoApp.Cuadrilla.CU_TIPO_CUADRILLA, match.getTipo_cuadrilla())
                            .withValue(ContratoCorpicoApp.Cuadrilla.CU_OPERARIO, match.getOperario())
                            .withValue(ContratoCorpicoApp.Cuadrilla.CU_FECHA, match.getFecha())
                            .withValue(ContratoCorpicoApp.Cuadrilla.CU_FECHA_UPDATE, match.getFecha_update())
                            .withValue(ContratoCorpicoApp.Cuadrilla.CU_SERVICIO, match.getServicio())
                            .withValue(ContratoCorpicoApp.Cuadrilla.CU_SECTOR, match.getSector())
                            .build());
                } else {
                    Log.i(TAG, "Tipo Cuadrillas - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoCorpicoApp.Cuadrilla.crearUriCuadrilla(id);
                Log.i(TAG, "Tipo Cuadrillas - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestCuadrilla cuadrilla : cuadrillaMap.values()) {
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.Cuadrilla.URI_CONTENIDO)
                    .withValue(ContratoCorpicoApp.Cuadrilla.CU_ID, cuadrilla.getId())
                    .withValue(ContratoCorpicoApp.Cuadrilla.CU_TIPO_CUADRILLA, cuadrilla.getTipo_cuadrilla())
                    .withValue(ContratoCorpicoApp.Cuadrilla.CU_OPERARIO, cuadrilla.getOperario())
                    .withValue(ContratoCorpicoApp.Cuadrilla.CU_FECHA, cuadrilla.getFecha())
                    .withValue(ContratoCorpicoApp.Cuadrilla.CU_FECHA_UPDATE, cuadrilla.getFecha_update())
                    .withValue(ContratoCorpicoApp.Cuadrilla.CU_SERVICIO, cuadrilla.getServicio())
                    .withValue(ContratoCorpicoApp.Cuadrilla.CU_SECTOR, cuadrilla.getSector())
                    .build());
        }
        return operations;
    }

    public static class AsyncQueryCuadrillas extends AsyncQueryHandler {
        private WeakReference<AsyncOpListener> mListener;

        public interface AsyncOpListener {
            void onQueryComplete(int token, Object cookie, Cursor cursor);
        }

        public AsyncQueryCuadrillas(ContentResolver cr) {
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
