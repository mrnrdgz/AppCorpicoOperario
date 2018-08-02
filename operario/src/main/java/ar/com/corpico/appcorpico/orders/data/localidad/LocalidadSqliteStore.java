package ar.com.corpico.appcorpico.orders.data.localidad;

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

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestLocalidad;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;

/**
 * Created by Administrador on 12/03/2018.
 */

public class LocalidadSqliteStore implements LocalidadStore {
    private static final String TAG = LocalidadSqliteStore.class.getSimpleName();
    private LocalidadSqliteStore INSTANCE;
    private ContentResolver mContentResolver;


    public LocalidadSqliteStore(@NonNull ContentResolver mContentResolver) {
        this.mContentResolver = Preconditions.checkNotNull(mContentResolver, "mContentResolver no puede ser null");
    }

    public LocalidadSqliteStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocalidadSqliteStore(mContentResolver);
        }
        return INSTANCE;
    }

    @Override
    public void getLocalidad(final GetLocalidadStoreCallBack callback, Criteria filter) {
        AsyncQueryLocalidades handler = new AsyncQueryLocalidades(mContentResolver);
        handler.setQueryListener(new  AsyncQueryLocalidades.AsyncOpListener() {
            @Override
            public void onQueryComplete(int token, Object cookie, Cursor cursor) {
                List<RestLocalidad> ListLocalidad = new ArrayList<>();
                if (cursor != null && cursor.getCount()>0) {
                    while(cursor.moveToNext()) {
                        ListLocalidad.add(new RestLocalidad(cursor.getShort(cursor.getColumnIndex(ContratoCorpicoApp.Localidad.LOC_ID)),
                                cursor.getString(cursor.getColumnIndex(ContratoCorpicoApp.Localidad.LOC_DESCRIPCION)),
                                cursor.getShort(cursor.getColumnIndex(ContratoCorpicoApp.Localidad.LOC_MUNICIPIO)),
                                cursor.getString(cursor.getColumnIndex(ContratoCorpicoApp.Localidad.LOC_INFORMA_SUCURSAL)),
                                cursor.getShort(cursor.getColumnIndex(ContratoCorpicoApp.Localidad.LOC_SUCURSAL)),
                                (byte) cursor.getInt(cursor.getColumnIndex(ContratoCorpicoApp.Localidad.LOC_ZONA_TARIFARIA)),
                                cursor.getString(cursor.getColumnIndex(ContratoCorpicoApp.Localidad.LOC_ACTIVO)),
                                cursor.getString(cursor.getColumnIndex(ContratoCorpicoApp.Localidad.LOC_ID_USER)),
                                cursor.getString(cursor.getColumnIndex(ContratoCorpicoApp.Localidad.LOC_FECHA_UPDATE)),
                                cursor.getColumnIndex(ContratoCorpicoApp.Localidad.LOC_LOTE_REPLICACION)));



                    }
                    callback.onSuccess(ListLocalidad);
                }else{
                    callback.onError("No existen Localidades");
                }
            }
        });
// todo: paso 1 para dejarlo sucio localmente campo sync
        handler.startQuery(0, null, ContratoCorpicoApp.Localidad.URI_CONTENIDO, null, null, null, null);
    }
    public List<ContentProviderOperation> replicarServidor(List<RestLocalidad> localidades) {
        //Crea lista de operaciones content provider
        List<ContentProviderOperation> operations = new ArrayList<>();

        // Tabla hash para recibir los tipos de cuadrillas entrantes
        HashMap<Integer, RestLocalidad> localidadMap = new HashMap<>();
        for (RestLocalidad l : localidades) {
            localidadMap.put((int) l.getLOC_ID(), l);
        }

        //Consultar registros remotos actuales
        Uri uri = ContratoCorpicoApp.Localidad.URI_CONTENIDO;
        String select = ContratoCorpicoApp.Localidad.TABLE_NAME;
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Localidades - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        short id;
        String descripcion;
        short municipio;
        String informa_sucursal;
        short sucursal;
        byte zona_tarifaria;
        String activo;
        String user_id;
        String fecha_update;
        int lote_replicacion;

        while (c.moveToNext()) {

            id = c.getShort(0);
            descripcion = c.getString(1);
            municipio = c.getShort(2);
            informa_sucursal = c.getString(3);
            sucursal = c.getShort(4);
            zona_tarifaria = (byte)c.getInt(5);
            activo = c.getString(6);
            user_id = c.getString(7);
            fecha_update = c.getString(8);
            lote_replicacion = c.getInt(9);


            RestLocalidad match = localidadMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                localidadMap.remove(id);

                Uri existingUri = ContratoCorpicoApp.Localidad.crearUriLocalidad((int) id);//PASAR LOS ID A INTEGER

                // Comprobar si tipo de cuadrillas necesita ser actualizado
                boolean b = match.getLOC_ID() != id;
                boolean b1 = match.getLOC_DESCRIPCION() != descripcion;
                boolean b2 =  match.getLOC_MUNICIPIO() != municipio;
                boolean b3 =  !match.getLOC_INFORMA_SUCURSAL().equals(informa_sucursal);
                boolean b4 = match.getLOC_SUCURSAL() != sucursal;
                boolean b5 = match.getLOC_ZONA_TARIFARIA() != zona_tarifaria;
                boolean b6 = match.getLOC_ACTIVO() != activo;
                boolean b7 = match.getLOC_ID_USER() != user_id;
                boolean b8 = match.getLOC_FECHA_UPDATE() != fecha_update;
                boolean b9 = match.getLOC_LOTE_REPLICACION() != lote_replicacion;


                if (b || b1 || b2 || b3 || b4  || b5  || b6  || b7  || b8  || b9) {

                    Log.i(TAG, "Localidades - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoCorpicoApp.Localidad.LOC_ID, match.getLOC_ID())
                            .withValue(ContratoCorpicoApp.Localidad.LOC_DESCRIPCION, match.getLOC_DESCRIPCION())
                            .withValue(ContratoCorpicoApp.Localidad.LOC_MUNICIPIO, match.getLOC_MUNICIPIO())
                            .withValue(ContratoCorpicoApp.Localidad.LOC_INFORMA_SUCURSAL, match.getLOC_INFORMA_SUCURSAL())
                            .withValue(ContratoCorpicoApp.Localidad.LOC_SUCURSAL, match.getLOC_SUCURSAL())
                            .withValue(ContratoCorpicoApp.Localidad.LOC_ZONA_TARIFARIA, match.getLOC_ZONA_TARIFARIA())
                            .withValue(ContratoCorpicoApp.Localidad.LOC_ACTIVO, match.getLOC_ACTIVO())
                            .withValue(ContratoCorpicoApp.Localidad.LOC_ID_USER, match.getLOC_ID_USER())
                            .withValue(ContratoCorpicoApp.Localidad.LOC_FECHA_UPDATE, match.getLOC_FECHA_UPDATE())
                            .withValue(ContratoCorpicoApp.Localidad.LOC_LOTE_REPLICACION, match.getLOC_LOTE_REPLICACION())
                            .build());
                } else {
                    Log.i(TAG, "Localidades - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoCorpicoApp.Localidad.crearUriLocalidad((int) id);
                Log.i(TAG, "Localidades - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestLocalidad localidad : localidadMap.values()){
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.Localidad.URI_CONTENIDO)
                    .withValue(ContratoCorpicoApp.Localidad.LOC_ID, localidad.getLOC_ID())
                    .withValue(ContratoCorpicoApp.Localidad.LOC_DESCRIPCION, localidad.getLOC_DESCRIPCION())
                    .withValue(ContratoCorpicoApp.Localidad.LOC_MUNICIPIO, localidad.getLOC_MUNICIPIO())
                    .withValue(ContratoCorpicoApp.Localidad.LOC_INFORMA_SUCURSAL, localidad.getLOC_INFORMA_SUCURSAL())
                    .withValue(ContratoCorpicoApp.Localidad.LOC_SUCURSAL, localidad.getLOC_SUCURSAL())
                    .withValue(ContratoCorpicoApp.Localidad.LOC_ZONA_TARIFARIA, localidad.getLOC_ZONA_TARIFARIA())
                    .withValue(ContratoCorpicoApp.Localidad.LOC_ACTIVO, localidad.getLOC_ACTIVO())
                    .withValue(ContratoCorpicoApp.Localidad.LOC_ID_USER, localidad.getLOC_ID_USER())
                    .withValue(ContratoCorpicoApp.Localidad.LOC_FECHA_UPDATE, localidad.getLOC_FECHA_UPDATE())
                    .withValue(ContratoCorpicoApp.Localidad.LOC_LOTE_REPLICACION, localidad.getLOC_LOTE_REPLICACION())
                    .build());
        }
        return operations;
    }
    public static class AsyncQueryLocalidades extends AsyncQueryHandler {
        private WeakReference<AsyncOpListener> mListener;

        public interface AsyncOpListener {
            void onQueryComplete(int token, Object cookie, Cursor cursor);
        }

        public AsyncQueryLocalidades(ContentResolver cr) {
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
