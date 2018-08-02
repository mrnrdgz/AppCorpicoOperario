package ar.com.corpico.appcorpico.orders.data.medidorMarca;

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

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMedidorMarca;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;

/**
 * Created by Administrador on 12/03/2018.
 */

public class MedidorMarcaSqliteStore implements MedidorMarcaStore {
    private static final String TAG = MedidorMarcaSqliteStore.class.getSimpleName();
    private MedidorMarcaSqliteStore INSTANCE;
    private ContentResolver mContentResolver;


    public MedidorMarcaSqliteStore(@NonNull ContentResolver mContentResolver) {
        this.mContentResolver = Preconditions.checkNotNull(mContentResolver, "mContentResolver no puede ser null");
    }

    public MedidorMarcaSqliteStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MedidorMarcaSqliteStore(mContentResolver);
        }
        return INSTANCE;
    }

    @Override
    public void getMedidorMarca(final GetMedidorMarcaStoreCallBack callback, Criteria filter) {
        AsyncQueryMedidorMarca handler = new AsyncQueryMedidorMarca(mContentResolver);
        handler.setQueryListener(new  AsyncQueryMedidorMarca.AsyncOpListener() {
            @Override
            public void onQueryComplete(int token, Object cookie, Cursor cursor) {
                // Procesa resultados del cursor
                // Retorna resultados en la callback entrante del método getOrders()
                List<RestMedidorMarca> ListMedidorMarca = new ArrayList<>();
                if (cursor != null && cursor.getCount()>0) {
                    while(cursor.moveToNext()) {
                        ListMedidorMarca.add(new RestMedidorMarca(cursor.getShort(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                                cursor.getString(4), cursor.getString(5),cursor.getInt(6)));

                    }
                    callback.onSuccess(ListMedidorMarca);
                }else{
                    callback.onError("No existen Medidores Marcas");
                }
            }
        });
// todo: paso 1 para dejarlo sucio localmente campo sync
        handler.startQuery(0, null, ContratoCorpicoApp.MedidorMarca.URI_CONTENIDO, null, null, null, null);
    }
    public List<ContentProviderOperation> replicarServidor(List<RestMedidorMarca> medidoresMarcas) {
        //Crea lista de operaciones content provider
        List<ContentProviderOperation> operations = new ArrayList<>();

        // Tabla hash para recibir los tipos de cuadrillas entrantes
        HashMap<Integer, RestMedidorMarca> medidorMarcaMap = new HashMap<>();
        for (RestMedidorMarca mca : medidoresMarcas) {
            medidorMarcaMap.put((int) mca.getMCA_ID(), mca);
        }

        //Consultar registros remotos actuales
        Uri uri = ContratoCorpicoApp.MedidorMarca.URI_CONTENIDO;
        String select = ContratoCorpicoApp.MedidorMarca.TABLE_NAME;
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Medidores Marcas - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        short id;
        String descripcion;
        String abreviatura;
        String activo;
        String user_id;
        String fecha_update;
        int lote_replicacion;

        while (c.moveToNext()) {
            id= c.getShort(0);
            descripcion = c.getString(1);
            abreviatura = c.getString(2);
            activo = c.getString(3);
            user_id = c.getString(4);
            fecha_update = c.getString(5);
            lote_replicacion = c.getInt(6);


            RestMedidorMarca match = medidorMarcaMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                medidorMarcaMap.remove(id);

                Uri existingUri = ContratoCorpicoApp.MedidorMarca.crearUriMedidorMarca((int) id);//PASAR LOS ID A INTEGER

                // Comprobar si tipo de cuadrillas necesita ser actualizado
                boolean b = match.getMCA_ID() != id;
                boolean b1 = match.getMCA_DESCRIPCION() != descripcion;
                boolean b2 =  match.getMCA_ABREVIATURA() != abreviatura;
                boolean b3 = match.getMCA_ACTIVO() != activo;
                boolean b4 = match.getMCA_ID_USER() != user_id;
                boolean b5 = match.getMCA_FECHA_UPDATE() != fecha_update;
                boolean b6 = match.getMCA_LOTE_REPLICACION() != lote_replicacion;


                if (b || b1 || b2 || b3 || b4  || b5  || b6 ) {

                    Log.i(TAG, "Medidores Marcas - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoCorpicoApp.MedidorMarca.MCA_ID, match.getMCA_ID())
                            .withValue(ContratoCorpicoApp.MedidorMarca.MCA_DESCRIPCION, match.getMCA_DESCRIPCION())
                            .withValue(ContratoCorpicoApp.MedidorMarca.MCA_ABREVIATURA, match.getMCA_ABREVIATURA())
                            .withValue(ContratoCorpicoApp.MedidorMarca.MCA_ACTIVO, match.getMCA_ACTIVO())
                            .withValue(ContratoCorpicoApp.MedidorMarca.MCA_ID_USER, match.getMCA_ID_USER())
                            .withValue(ContratoCorpicoApp.MedidorMarca.MCA_FECHA_UPDATE, match.getMCA_FECHA_UPDATE())
                            .withValue(ContratoCorpicoApp.MedidorMarca.MCA_LOTE_REPLICACION, match.getMCA_LOTE_REPLICACION())
                            .build());
                } else {
                    Log.i(TAG, "Medidores Marcas - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoCorpicoApp.MedidorMarca.crearUriMedidorMarca((int) id);
                Log.i(TAG, "Medidores Marcas - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestMedidorMarca medidorMarca : medidorMarcaMap.values()){
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.MedidorMarca.URI_CONTENIDO)
                    .withValue(ContratoCorpicoApp.MedidorMarca.MCA_ID, medidorMarca.getMCA_ID())
                    .withValue(ContratoCorpicoApp.MedidorMarca.MCA_DESCRIPCION, medidorMarca.getMCA_DESCRIPCION())
                    .withValue(ContratoCorpicoApp.MedidorMarca.MCA_ABREVIATURA, medidorMarca.getMCA_ABREVIATURA())
                    .withValue(ContratoCorpicoApp.MedidorMarca.MCA_ACTIVO, medidorMarca.getMCA_ACTIVO())
                    .withValue(ContratoCorpicoApp.MedidorMarca.MCA_ID_USER, medidorMarca.getMCA_ID_USER())
                    .withValue(ContratoCorpicoApp.MedidorMarca.MCA_FECHA_UPDATE, medidorMarca.getMCA_FECHA_UPDATE())
                    .withValue(ContratoCorpicoApp.MedidorMarca.MCA_LOTE_REPLICACION, medidorMarca.getMCA_LOTE_REPLICACION())
                    .build());
        }
        return operations;
    }
    public static class AsyncQueryMedidorMarca extends AsyncQueryHandler {
        private WeakReference<AsyncOpListener> mListener;

        public interface AsyncOpListener {
            void onQueryComplete(int token, Object cookie, Cursor cursor);
        }

        public AsyncQueryMedidorMarca(ContentResolver cr) {
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
