package ar.com.corpico.appcorpico.orders.data.tipoCuadrilla;

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

import ar.com.corpico.appcorpico.cuadrillas.data.CuadrillaSqliteStore;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoCuadrilla;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;

/**
 * Created by Administrador on 12/03/2018.
 */

public class TipoCuadrillaSqliteStore implements TipoCuadrillaStore {
    private static final String TAG = CuadrillaSqliteStore.class.getSimpleName();
    private static TipoCuadrillaSqliteStore INSTANCE;
    private ContentResolver mContentResolver;

    private TipoCuadrillaSqliteStore(@NonNull ContentResolver mContentResolver) {
        this.mContentResolver = Preconditions.checkNotNull(mContentResolver, "mContentResolver no puede ser null");
    }

    public static TipoCuadrillaSqliteStore getInstance(@NonNull ContentResolver mContentResolver) {
        if (INSTANCE == null) {
            INSTANCE = new TipoCuadrillaSqliteStore(mContentResolver);
        }
        return INSTANCE;
    }

    @Override
    public void getTipoCuadrilla(final GetTipoCuadrillaStoreCallBack callback, Criteria filter) {
        Cursor tipoCuadrillasCursor = mContentResolver.query(ContratoCorpicoApp.TiposCuadrilla.URI_CONTENIDO, null, null, null, null);
        List<Tipo_Cuadrilla> ListTipoCuadrilla = new ArrayList<>();
        if (tipoCuadrillasCursor != null && tipoCuadrillasCursor.getCount()>0) {
            while(tipoCuadrillasCursor.moveToNext()) {
                ListTipoCuadrilla.add(new Tipo_Cuadrilla(tipoCuadrillasCursor.getInt(tipoCuadrillasCursor.getColumnIndex(ContratoCorpicoApp.TiposCuadrilla.TC_ID)),
                        tipoCuadrillasCursor.getInt(tipoCuadrillasCursor.getColumnIndex(ContratoCorpicoApp.TiposCuadrilla.TC_ID_SERVICIO)),
                        tipoCuadrillasCursor.getInt(tipoCuadrillasCursor.getColumnIndex(ContratoCorpicoApp.TiposCuadrilla.TC_ID_SECTOR)),
                        tipoCuadrillasCursor.getString(tipoCuadrillasCursor.getColumnIndex(ContratoCorpicoApp.TiposCuadrilla.TC_DESCRIPCION)),
                        tipoCuadrillasCursor.getInt(tipoCuadrillasCursor.getColumnIndex(ContratoCorpicoApp.TiposCuadrilla.TC_GEASYS_ID))));

            }
            callback.onSuccess(ListTipoCuadrilla);
        }else{
            callback.onError("No existen Cuadrillas");
        }
        /*Looper.prepare();
        AsyncQueryTipoCuadrillas handler = new AsyncQueryTipoCuadrillas(mContentResolver);
        handler.setQueryListener(new  AsyncQueryTipoCuadrillas.AsyncOpListener() {
            @Override
            public void onQueryComplete(int token, Object cookie, Cursor cursor) {
                List<Tipo_Cuadrilla> ListTipoCuadrilla = new ArrayList<>();
                if (cursor != null && cursor.getCount()>0) {
                    while(cursor.moveToNext()) {
                        ListTipoCuadrilla.add(new Tipo_Cuadrilla(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),cursor.getString(3)));
                    }
                    callback.onSuccess(ListTipoCuadrilla);
                }else{
                    callback.onError("No existen Cuadrillas");
                }
            }
        });
        Looper.loop();*/
// todo: paso 1 para dejarlo sucio localmente campo sync
        //handler.startQuery(0, null, ContratoCorpicoApp.TiposCuadrilla.URI_CONTENIDO, null, null, null, null);
    }
    public List<ContentProviderOperation> replicarServidor(List<RestTipoCuadrilla> tipoCuadrillas) {
        //Crea lista de operaciones content provider
        List<ContentProviderOperation> operations = new ArrayList<>();

        // Tabla hash para recibir los tipos de cuadrillas entrantes
        HashMap<Integer, RestTipoCuadrilla> tipoCuadrillaMap = new HashMap<>();
        for (RestTipoCuadrilla tc : tipoCuadrillas) {
            tipoCuadrillaMap.put(tc.getID(), tc);
        }

        //Consultar registros remotos actuales
        Uri uri = ContratoCorpicoApp.TiposCuadrilla.URI_CONTENIDO;
        String select = ContratoCorpicoApp.TiposCuadrilla.TABLE_NAME;
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Tipo Cuadrillas - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        Integer id;
        Integer idServicio;
        Integer idSector;
        String descripcion;
        String fechaUpdate;
        Integer geasysId;

        while (c.moveToNext()) {

            id = c.getInt(c.getColumnIndex(ContratoCorpicoApp.TiposCuadrilla.TC_ID));
            idServicio = c.getInt(c.getColumnIndex(ContratoCorpicoApp.TiposCuadrilla.TC_ID_SERVICIO));
            idSector = c.getInt(c.getColumnIndex(ContratoCorpicoApp.TiposCuadrilla.TC_ID_SECTOR));
            descripcion = c.getString(c.getColumnIndex(ContratoCorpicoApp.TiposCuadrilla.TC_DESCRIPCION));
            fechaUpdate = c.getString(c.getColumnIndex(ContratoCorpicoApp.TiposCuadrilla.TC_FECHA_UPDATE));
            geasysId = c.getInt(c.getColumnIndex(ContratoCorpicoApp.TiposCuadrilla.TC_GEASYS_ID));

            RestTipoCuadrilla match = tipoCuadrillaMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                tipoCuadrillaMap.remove(id);

                Uri existingUri = ContratoCorpicoApp.TiposCuadrilla.crearUriTipoCuadrilla(id);//PASAR LOS ID A INTEGER

                // Comprobar si tipo de cuadrillas necesita ser actualizado
                boolean b = match.getID() != id;
                boolean b1 = match.getSERVICIO() != idServicio;
                boolean b2 =  !match.getSECTOR().equals(idSector);
                boolean b3 =  !match.getTipoCuadrilla().equals(descripcion);
                boolean b4 = match.getTipoCuadrillaUpdate() != null && !match.getTipoCuadrillaUpdate().equals(fechaUpdate);
                boolean b5 = match.getTipoCuadrillaGeasysId() != geasysId;

                if (b || b1 || b2 || b3 || b4 ) {

                    Log.i(TAG, "Tipo Cuadrillas - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoCorpicoApp.TiposCuadrilla.TC_ID_SERVICIO, match.getSERVICIO())
                            .withValue(ContratoCorpicoApp.TiposCuadrilla.TC_ID_SECTOR, match.getSECTOR())
                            .withValue(ContratoCorpicoApp.TiposCuadrilla.TC_DESCRIPCION, match.getTipoCuadrilla())
                            .withValue(ContratoCorpicoApp.TiposCuadrilla.TC_FECHA_UPDATE, match.getTipoCuadrillaUpdate())
                            .withValue(ContratoCorpicoApp.TiposCuadrilla.TC_GEASYS_ID, match.getTipoCuadrillaGeasysId())
                            .build());
                } else {
                    Log.i(TAG, "Tipo Cuadrillas - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoCorpicoApp.TiposCuadrilla.crearUriTipoCuadrilla(id);
                Log.i(TAG, "Tipo Cuadrillas - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestTipoCuadrilla tipoCuadrilla : tipoCuadrillaMap.values()){
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.TiposCuadrilla.URI_CONTENIDO)
                    .withValue(ContratoCorpicoApp.TiposCuadrilla.TC_ID, tipoCuadrilla.getID())
                    .withValue(ContratoCorpicoApp.TiposCuadrilla.TC_ID_SERVICIO, tipoCuadrilla.getSERVICIO())
                    .withValue(ContratoCorpicoApp.TiposCuadrilla.TC_ID_SECTOR, tipoCuadrilla.getSECTOR())
                    .withValue(ContratoCorpicoApp.TiposCuadrilla.TC_DESCRIPCION, tipoCuadrilla.getTipoCuadrilla())
                    .withValue(ContratoCorpicoApp.TiposCuadrilla.TC_FECHA_UPDATE, tipoCuadrilla.getTipoCuadrillaUpdate())
                    .withValue(ContratoCorpicoApp.TiposCuadrilla.TC_GEASYS_ID, tipoCuadrilla.getTipoCuadrillaGeasysId())
                    .build());
        }
        return operations;
    }
    public static class AsyncQueryTipoCuadrillas extends AsyncQueryHandler {
        private WeakReference<AsyncOpListener> mListener;

        public interface AsyncOpListener {
            void onQueryComplete(int token, Object cookie, Cursor cursor);
        }

        public AsyncQueryTipoCuadrillas(ContentResolver cr) {
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
