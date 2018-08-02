package ar.com.corpico.appcorpico.orders.data.medidorTipo;

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

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMedidorTipo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;

/**
 * Created by Administrador on 12/03/2018.
 */

public class MedidorTipoSqliteStore implements MedidorTipoStore {
    private static final String TAG = MedidorTipoSqliteStore.class.getSimpleName();
    private MedidorTipoSqliteStore INSTANCE;
    private ContentResolver mContentResolver;


    public MedidorTipoSqliteStore(@NonNull ContentResolver mContentResolver) {
        this.mContentResolver = Preconditions.checkNotNull(mContentResolver, "mContentResolver no puede ser null");
    }

    public MedidorTipoSqliteStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MedidorTipoSqliteStore(mContentResolver);
        }
        return INSTANCE;
    }

    @Override
    public void getMedidorTipo(final GetMedidorTipoStoreCallBack callback, Criteria filter) {
        AsyncQueryMedidorTipo handler = new AsyncQueryMedidorTipo(mContentResolver);
        handler.setQueryListener(new  AsyncQueryMedidorTipo.AsyncOpListener() {
            @Override
            public void onQueryComplete(int token, Object cookie, Cursor cursor) {
                List<RestMedidorTipo> ListMedidorTipo = new ArrayList<>();
                if (cursor != null && cursor.getCount()>0) {
                    while(cursor.moveToNext()) {
                        ListMedidorTipo.add(new RestMedidorTipo(cursor.getShort(0),cursor.getString(1), (byte) cursor.getInt(2), cursor.getString(3), cursor.getString(4),
                                cursor.getString(5), cursor.getInt(6)));

                    }
                    callback.onSuccess(ListMedidorTipo);
                }else{
                    callback.onError("No existen Medidores Tipos");
                }
            }
        });
// todo: paso 1 para dejarlo sucio localmente campo sync
        handler.startQuery(0, null, ContratoCorpicoApp.MedidorTipo.URI_CONTENIDO, null, null, null, null);
    }
    public List<ContentProviderOperation> replicarServidor(List<RestMedidorTipo> medidoresTipos) {
        //Crea lista de operaciones content provider
        List<ContentProviderOperation> operations = new ArrayList<>();

        // Tabla hash para recibir los tipos de cuadrillas entrantes
        HashMap<Integer, RestMedidorTipo> medidorTipoMap = new HashMap<>();
        for (RestMedidorTipo met : medidoresTipos) {
            medidorTipoMap.put((int) met.getMET_ID(), met);
        }

        //Consultar registros remotos actuales
        Uri uri = ContratoCorpicoApp.MedidorTipo.URI_CONTENIDO;
        String select = ContratoCorpicoApp.MedidorTipo.TABLE_NAME;
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Medidores Tipos - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        short id;
        String descripcion;
        byte tipo_empresa;
        String activo;
        String user_id;
        String fecha_update;
        int lote_replicacion;

        while (c.moveToNext()) {
            id = c.getShort(0);
            descripcion = c.getString(1);
            tipo_empresa = (byte) c.getInt(2);
            activo = c.getString(3);
            user_id = c.getString(4);
            fecha_update = c.getString(5);
            lote_replicacion = c.getInt(6);


            RestMedidorTipo match = medidorTipoMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                medidorTipoMap.remove(id);

                Uri existingUri = ContratoCorpicoApp.MedidorTipo.crearUriMedidorTipo((int) id);//PASAR LOS ID A INTEGER

                // Comprobar si tipo de cuadrillas necesita ser actualizado
                boolean b = match.getMET_ID() != id;
                boolean b1 = match.getMET_DESCRIPCION() != descripcion;
                boolean b2 =  match.getMET_TIPO_EMPRESA() != tipo_empresa;
                boolean b3 = match.getMET_ACTIVO() != activo;
                boolean b4 = match.getMET_ID_USER() != user_id;
                boolean b5 = match.getMET_FECHA_UPDATE() != fecha_update;
                boolean b6 = match.getMET_LOTE_REPLICACION() != lote_replicacion;


                if (b || b1 || b2 || b3 || b4  || b5  || b6 ) {

                    Log.i(TAG, "Medidores Tipos - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoCorpicoApp.MedidorTipo.MET_ID, match.getMET_ID())
                            .withValue(ContratoCorpicoApp.MedidorTipo.MET_DESCRIPCION, match.getMET_DESCRIPCION())
                            .withValue(ContratoCorpicoApp.MedidorTipo.MET_TIPO_EMPRESA, match.getMET_TIPO_EMPRESA())
                            .withValue(ContratoCorpicoApp.MedidorTipo.MET_ACTIVO, match.getMET_ACTIVO())
                            .withValue(ContratoCorpicoApp.MedidorTipo.MET_ID_USER, match.getMET_ID_USER())
                            .withValue(ContratoCorpicoApp.MedidorTipo.MET_FECHA_UPDATE, match.getMET_FECHA_UPDATE())
                            .withValue(ContratoCorpicoApp.MedidorTipo.MET_LOTE_REPLICACION, match.getMET_LOTE_REPLICACION())
                            .build());
                } else {
                    Log.i(TAG, "Medidores Tipos - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoCorpicoApp.MedidorTipo.crearUriMedidorTipo((int) id);
                Log.i(TAG, "Medidores Tipos - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestMedidorTipo medidorTipo : medidorTipoMap.values()){
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.MedidorTipo.URI_CONTENIDO)
                    .withValue(ContratoCorpicoApp.MedidorTipo.MET_ID, medidorTipo.getMET_ID())
                    .withValue(ContratoCorpicoApp.MedidorTipo.MET_DESCRIPCION, medidorTipo.getMET_DESCRIPCION())
                    .withValue(ContratoCorpicoApp.MedidorTipo.MET_TIPO_EMPRESA, medidorTipo.getMET_TIPO_EMPRESA())
                    .withValue(ContratoCorpicoApp.MedidorTipo.MET_ACTIVO, medidorTipo.getMET_ACTIVO())
                    .withValue(ContratoCorpicoApp.MedidorTipo.MET_ID_USER, medidorTipo.getMET_ID_USER())
                    .withValue(ContratoCorpicoApp.MedidorTipo.MET_FECHA_UPDATE, medidorTipo.getMET_FECHA_UPDATE())
                    .withValue(ContratoCorpicoApp.MedidorTipo.MET_LOTE_REPLICACION, medidorTipo.getMET_LOTE_REPLICACION())
                    .build());
        }
        return operations;
    }
    public static class AsyncQueryMedidorTipo extends AsyncQueryHandler {
        private WeakReference<AsyncOpListener> mListener;

        public interface AsyncOpListener {
            void onQueryComplete(int token, Object cookie, Cursor cursor);
        }

        public AsyncQueryMedidorTipo(ContentResolver cr) {
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
