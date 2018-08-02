package ar.com.corpico.appcorpico.orders.data.medidorClase;

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

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMedidorClase;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;

/**
 * Created by Administrador on 12/03/2018.
 */

public class MedidorClaseSqliteStore implements MedidorClaseStore {
    private static final String TAG = MedidorClaseSqliteStore.class.getSimpleName();
    private MedidorClaseSqliteStore INSTANCE;
    private ContentResolver mContentResolver;


    public MedidorClaseSqliteStore(@NonNull ContentResolver mContentResolver) {
        this.mContentResolver = Preconditions.checkNotNull(mContentResolver, "mContentResolver no puede ser null");
    }

    public MedidorClaseSqliteStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MedidorClaseSqliteStore(mContentResolver);
        }
        return INSTANCE;
    }

    @Override
    public void getMedidorClase(final GetMedidorClaseStoreCallBack callback, Criteria filter) {
        AsyncQueryMedidorClase handler = new AsyncQueryMedidorClase(mContentResolver);
        handler.setQueryListener(new  AsyncQueryMedidorClase.AsyncOpListener() {
            @Override
            public void onQueryComplete(int token, Object cookie, Cursor cursor) {
                // Procesa resultados del cursor
                // Retorna resultados en la callback entrante del método getOrders()
                List<RestMedidorClase> ListMedidorClase = new ArrayList<>();
                if (cursor != null && cursor.getCount()>0) {
                    while(cursor.moveToNext()) {
                        ListMedidorClase.add(new RestMedidorClase((byte)cursor.getInt(0), cursor.getString(1), (byte)cursor.getInt(2), cursor.getInt(3), cursor.getString(4),
                                cursor.getString(5), cursor.getString(6), cursor.getInt(7)));
                    }
                    callback.onSuccess(ListMedidorClase);
                }else{
                    callback.onError("No existen Medidores Clase");
                }
            }
        });
// todo: paso 1 para dejarlo sucio localmente campo sync
        handler.startQuery(0, null, ContratoCorpicoApp.MedidorClase.URI_CONTENIDO, null, null, null, null);
    }
    public List<ContentProviderOperation> replicarServidor(List<RestMedidorClase> medidoresClases) {
        //Crea lista de operaciones content provider
        List<ContentProviderOperation> operations = new ArrayList<>();

        // Tabla hash para recibir los tipos de cuadrillas entrantes
        HashMap<Integer, RestMedidorClase> medidorClaseMap = new HashMap<>();
        for (RestMedidorClase mcl : medidoresClases) {
            medidorClaseMap.put((int) mcl.getMCL_ID(), mcl);
        }

        //Consultar registros remotos actuales
        Uri uri = ContratoCorpicoApp.MedidorClase.URI_CONTENIDO;
        String select = ContratoCorpicoApp.MedidorClase.TABLE_NAME;
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Medidores Clase - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        byte id;
        String descripcion;
        byte tipo_empresa;
        int capacidad;
        String activo;
        String user_id;
        String fecha_update;
        int lote_resplicacion;

        while (c.moveToNext()) {

            id = (byte)c.getInt(0);
            descripcion = c.getString(1);
            tipo_empresa = (byte)c.getInt(2);
            capacidad = c.getInt(3);
            activo = c.getString(4);
            user_id = c.getString(5);
            fecha_update = c.getString(6);
            lote_resplicacion = c.getInt(7);


            RestMedidorClase match = medidorClaseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                medidorClaseMap.remove(id);

                Uri existingUri = ContratoCorpicoApp.MedidorClase.crearUriMedidorClase((int) id);//PASAR LOS ID A INTEGER

                // Comprobar si tipo de cuadrillas necesita ser actualizado
                boolean b = match.getMCL_ID() != id;
                boolean b1 = match.getMCL_DESCRIPCION() != descripcion;
                boolean b2 =  match.getMCL_TIPO_EMPRESA() != tipo_empresa;
                boolean b3 = match.getMCL_CAPACIDAD() != capacidad;
                boolean b4 = match.getMCL_ACTIVO() != activo;
                boolean b5 = match.getMCL_ID_USER() != user_id;
                boolean b6 = match.getMCL_FECHA_UPDATE() != fecha_update;
                boolean b7 = match.getMCL_LOTE_REPLICACION() != lote_resplicacion;

                if (b || b1 || b2 || b3 || b4  || b5  || b6  || b7) {

                    Log.i(TAG, "Medidores Clase - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoCorpicoApp.MedidorClase.MCL_ID, match.getMCL_ID())
                            .withValue(ContratoCorpicoApp.MedidorClase.MCL_DESCRIPCION, match.getMCL_DESCRIPCION())
                            .withValue(ContratoCorpicoApp.MedidorClase.MCL_TIPO_EMPRESA, match.getMCL_TIPO_EMPRESA())
                            .withValue(ContratoCorpicoApp.MedidorClase.MCL_CAPACIDAD, match.getMCL_CAPACIDAD())
                            .withValue(ContratoCorpicoApp.MedidorClase.MCL_ACTIVO, match.getMCL_ACTIVO())
                            .withValue(ContratoCorpicoApp.MedidorClase.MCL_ID_USER, match.getMCL_ID_USER())
                            .withValue(ContratoCorpicoApp.MedidorClase.MCL_FECHA_UPDATE, match.getMCL_FECHA_UPDATE())
                            .withValue(ContratoCorpicoApp.MedidorClase.MCL_LOTE_REPLICACION, match.getMCL_LOTE_REPLICACION())
                            .build());
                } else {
                    Log.i(TAG, "Medidores Clase - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoCorpicoApp.MedidorClase.crearUriMedidorClase((int) id);
                Log.i(TAG, "Medidores Clase - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestMedidorClase medidorClase : medidorClaseMap.values()){
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.MedidorClase.URI_CONTENIDO)
                    .withValue(ContratoCorpicoApp.MedidorClase.MCL_ID, medidorClase.getMCL_ID())
                    .withValue(ContratoCorpicoApp.MedidorClase.MCL_DESCRIPCION, medidorClase.getMCL_DESCRIPCION())
                    .withValue(ContratoCorpicoApp.MedidorClase.MCL_TIPO_EMPRESA, medidorClase.getMCL_TIPO_EMPRESA())
                    .withValue(ContratoCorpicoApp.MedidorClase.MCL_CAPACIDAD, medidorClase.getMCL_CAPACIDAD())
                    .withValue(ContratoCorpicoApp.MedidorClase.MCL_ACTIVO, medidorClase.getMCL_ACTIVO())
                    .withValue(ContratoCorpicoApp.MedidorClase.MCL_ID_USER, medidorClase.getMCL_ID_USER())
                    .withValue(ContratoCorpicoApp.MedidorClase.MCL_FECHA_UPDATE, medidorClase.getMCL_FECHA_UPDATE())
                    .withValue(ContratoCorpicoApp.MedidorClase.MCL_LOTE_REPLICACION, medidorClase.getMCL_LOTE_REPLICACION())
                    .build());
        }
        return operations;
    }
    public static class AsyncQueryMedidorClase extends AsyncQueryHandler {
        private WeakReference<AsyncOpListener> mListener;

        public interface AsyncOpListener {
            void onQueryComplete(int token, Object cookie, Cursor cursor);
        }

        public AsyncQueryMedidorClase(ContentResolver cr) {
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
