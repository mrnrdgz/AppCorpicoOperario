package ar.com.corpico.appcorpico.orders.data.medidorModelo;

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

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMedidorModelo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;

/**
 * Created by Administrador on 12/03/2018.
 */

public class MedidorModeloSqliteStore implements MedidorModeloStore {
    private static final String TAG = MedidorModeloSqliteStore.class.getSimpleName();
    private MedidorModeloSqliteStore INSTANCE;
    private ContentResolver mContentResolver;


    public MedidorModeloSqliteStore(@NonNull ContentResolver mContentResolver) {
        this.mContentResolver = Preconditions.checkNotNull(mContentResolver, "mContentResolver no puede ser null");
    }

    public MedidorModeloSqliteStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MedidorModeloSqliteStore(mContentResolver);
        }
        return INSTANCE;
    }

    @Override
    public void getMedidorModelo(final GetMedidorModeloStoreCallBack callback, Criteria filter) {
        AsyncQueryMedidorModelo handler = new AsyncQueryMedidorModelo(mContentResolver);
        handler.setQueryListener(new  AsyncQueryMedidorModelo.AsyncOpListener() {
            @Override
            public void onQueryComplete(int token, Object cookie, Cursor cursor) {
                // Procesa resultados del cursor
                // Retorna resultados en la callback entrante del método getOrders()
                List<RestMedidorModelo> ListMedidorModelo = new ArrayList<>();
                if (cursor != null && cursor.getCount()>0) {
                    while(cursor.moveToNext()) {
                        ListMedidorModelo.add(new RestMedidorModelo(cursor.getShort(0), cursor.getShort(1), cursor.getString(2), (byte) cursor.getInt(3),
                                cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getInt(7)));


                    }
                    callback.onSuccess(ListMedidorModelo);
                }else{
                    callback.onError("No existen Medidores Modelos");
                }
            }
        });
// todo: paso 1 para dejarlo sucio localmente campo sync
        handler.startQuery(0, null, ContratoCorpicoApp.MedidorModelo.URI_CONTENIDO, null, null, null, null);
    }
    public List<ContentProviderOperation> replicarServidor(List<RestMedidorModelo> medidoresModelos) {
        //Crea lista de operaciones content provider
        List<ContentProviderOperation> operations = new ArrayList<>();

        // Tabla hash para recibir los tipos de cuadrillas entrantes
        HashMap<Integer, RestMedidorModelo> medidorModeloMap = new HashMap<>();
        for (RestMedidorModelo mmo : medidoresModelos) {
            medidorModeloMap.put((int) mmo.getMMO_ID(), mmo);
        }

        //Consultar registros remotos actuales
        Uri uri = ContratoCorpicoApp.MedidorModelo.URI_CONTENIDO;
        String select = ContratoCorpicoApp.MedidorModelo.TABLE_NAME;
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Medidores Modelos - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        short marca;
        short id;
        String descripcion;
        byte tipo_empresa;
        String activo;
        String user_id;
        String fecha_update;
        int lote_replicacion;

        while (c.moveToNext()) {
            marca = c.getShort(0);
            id = c.getShort(1);
            descripcion = c.getString(2);
            tipo_empresa = (byte) c.getInt(3);
            activo = c.getString(4);
            user_id = c.getString(5);
            fecha_update = c.getString(6);
            lote_replicacion = c.getInt(7);


            RestMedidorModelo match = medidorModeloMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                medidorModeloMap.remove(id);

                Uri existingUri = ContratoCorpicoApp.MedidorModelo.crearUriMedidorModelo((int) id);//PASAR LOS ID A INTEGER

                // Comprobar si tipo de cuadrillas necesita ser actualizado
                boolean b = match.getMMO_MARCA_MEDIDOR() != marca;
                boolean b1 = match.getMMO_ID() != id;
                boolean b2 =  match.getMMO_DESCRIPCION() != descripcion;
                boolean b3 = match.getMMO_TIPO_EMPRESA() != tipo_empresa;
                boolean b4 = match.getMMO_ACTIVO() != activo;
                boolean b5 = match.getMMO_ID_USER() != user_id;
                boolean b6 = match.getMMO_FECHA_UPDATE() != fecha_update;
                boolean b7 = match.getMMO_LOTE_REPLICACION() != lote_replicacion;


                if (b || b1 || b2 || b3 || b4  || b5  || b6 || b7  ) {

                    Log.i(TAG, "Medidores Modelos - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoCorpicoApp.MedidorModelo.MMO_MARCA_MEDIDOR, match.getMMO_MARCA_MEDIDOR())
                            .withValue(ContratoCorpicoApp.MedidorModelo.MMO_ID, match.getMMO_ID())
                            .withValue(ContratoCorpicoApp.MedidorModelo.MMO_DESCRIPCION, match.getMMO_DESCRIPCION())
                            .withValue(ContratoCorpicoApp.MedidorModelo.MMO_TIPO_EMPRESA, match.getMMO_TIPO_EMPRESA())
                            .withValue(ContratoCorpicoApp.MedidorModelo.MMO_ACTIVO, match.getMMO_ACTIVO())
                            .withValue(ContratoCorpicoApp.MedidorModelo.MMO_ID_USER, match.getMMO_ID_USER())
                            .withValue(ContratoCorpicoApp.MedidorModelo.MMO_FECHA_UPDATE, match.getMMO_FECHA_UPDATE())
                            .withValue(ContratoCorpicoApp.MedidorModelo.MMO_LOTE_REPLICACION, match.getMMO_LOTE_REPLICACION())
                            .build());
                } else {
                    Log.i(TAG, "Medidores Modelos - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoCorpicoApp.MedidorModelo.crearUriMedidorModelo((int) id);
                Log.i(TAG, "Medidores Modelos - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestMedidorModelo medidorModelo : medidorModeloMap.values()){
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.MedidorModelo.URI_CONTENIDO)
                    .withValue(ContratoCorpicoApp.MedidorModelo.MMO_MARCA_MEDIDOR, medidorModelo.getMMO_MARCA_MEDIDOR())
                    .withValue(ContratoCorpicoApp.MedidorModelo.MMO_ID, medidorModelo.getMMO_ID())
                    .withValue(ContratoCorpicoApp.MedidorModelo.MMO_DESCRIPCION, medidorModelo.getMMO_DESCRIPCION())
                    .withValue(ContratoCorpicoApp.MedidorModelo.MMO_TIPO_EMPRESA, medidorModelo.getMMO_TIPO_EMPRESA())
                    .withValue(ContratoCorpicoApp.MedidorModelo.MMO_ACTIVO, medidorModelo.getMMO_ACTIVO())
                    .withValue(ContratoCorpicoApp.MedidorModelo.MMO_ID_USER, medidorModelo.getMMO_ID_USER())
                    .withValue(ContratoCorpicoApp.MedidorModelo.MMO_FECHA_UPDATE, medidorModelo.getMMO_FECHA_UPDATE())
                    .withValue(ContratoCorpicoApp.MedidorModelo.MMO_LOTE_REPLICACION, medidorModelo.getMMO_LOTE_REPLICACION())
                    .build());
        }
        return operations;
    }
    public static class AsyncQueryMedidorModelo extends AsyncQueryHandler {
        private WeakReference<AsyncOpListener> mListener;

        public interface AsyncOpListener {
            void onQueryComplete(int token, Object cookie, Cursor cursor);
        }

        public AsyncQueryMedidorModelo(ContentResolver cr) {
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
