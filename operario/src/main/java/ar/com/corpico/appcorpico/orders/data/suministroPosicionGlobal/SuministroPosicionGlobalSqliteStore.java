package ar.com.corpico.appcorpico.orders.data.suministroPosicionGlobal;

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

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestSuministroPosicionGlobal;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;

/**
 * Created by Administrador on 12/03/2018.
 */

public class SuministroPosicionGlobalSqliteStore implements SuministroPosicionGlobalStore {
    private static final String TAG = SuministroPosicionGlobalSqliteStore.class.getSimpleName();
    private SuministroPosicionGlobalSqliteStore INSTANCE;
    private ContentResolver mContentResolver;


    public SuministroPosicionGlobalSqliteStore(@NonNull ContentResolver mContentResolver) {
        this.mContentResolver = Preconditions.checkNotNull(mContentResolver, "mContentResolver no puede ser null");
    }

    public SuministroPosicionGlobalSqliteStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SuministroPosicionGlobalSqliteStore(mContentResolver);
        }
        return INSTANCE;
    }

    @Override
    public void getSuministroPosicionGlobal(final GetSuministroPosicionGlobalStoreCallBack callback, Criteria filter) {
        AsyncQuerySuministroPosicionGlobal handler = new AsyncQuerySuministroPosicionGlobal(mContentResolver);
        handler.setQueryListener(new  AsyncQuerySuministroPosicionGlobal.AsyncOpListener() {
            @Override
            public void onQueryComplete(int token, Object cookie, Cursor cursor) {
                List<RestSuministroPosicionGlobal> ListSuministroPosicionGlobal = new ArrayList<>();
                if (cursor != null && cursor.getCount()>0) {
                    while(cursor.moveToNext()) {
                        ListSuministroPosicionGlobal.add(new RestSuministroPosicionGlobal(cursor.getShort(0), cursor.getInt(1), cursor.getShort(2), cursor.getString(3),
                                cursor.getString(4),  cursor.getString(5),  cursor.getString(6),
                                cursor.getString(7)));


                    }
                    callback.onSuccess(ListSuministroPosicionGlobal);
                }else{
                    callback.onError("No existen Suministro Posicion Global");
                }
            }
        });
// todo: paso 1 para dejarlo sucio localmente campo sync
        handler.startQuery(0, null, ContratoCorpicoApp.SuministroPosicionGlobal.URI_CONTENIDO, null, null, null, null);
    }
    public List<ContentProviderOperation> replicarServidor(List<RestSuministroPosicionGlobal> suministrosPosicionGlobal) {
        //Crea lista de operaciones content provider
        List<ContentProviderOperation> operations = new ArrayList<>();

        // Tabla hash para recibir los tipos de cuadrillas entrantes
        HashMap<String, RestSuministroPosicionGlobal> suministroPosicionGlobalMap = new HashMap<>();
        String ids;
        for (RestSuministroPosicionGlobal spg : suministrosPosicionGlobal) {
            ids = spg.getSPG_CLIENTE() + "#" + spg.getSPG_SUMINISTRO();
            suministroPosicionGlobalMap.put(ids,spg);

        }

        //Consultar registros remotos actuales
        Uri uri = ContratoCorpicoApp.SuministroPosicionGlobal.URI_CONTENIDO;
        String select = ContratoCorpicoApp.SuministroPosicionGlobal.TABLE_NAME;
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Suministro Posicion Global - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        short empresa;
        int cliente;
        short suministro;
        String latitud;
        String longitud;
        String fecha_captura;
        String user_id;
        String fecha_update;

        while (c.moveToNext()) {
            empresa = c.getShort(0);
            cliente = c.getInt(1);
            suministro = c.getShort(2);
            latitud = c.getString(3);
            longitud = c.getString(4);
            fecha_captura = c.getString(5);
            user_id = c.getString(6);
            fecha_update = c.getString(7);


            ids = cliente + "#" + suministro;
            RestSuministroPosicionGlobal match = suministroPosicionGlobalMap.get(ids);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                suministroPosicionGlobalMap.remove(ids);

                Uri existingUri = ContratoCorpicoApp.SuministroPosicionGlobal.crearUriSuministroPosicionGlobal(cliente,suministro);//PASAR LOS ID A INTEGER

                // Comprobar si tipo de cuadrillas necesita ser actualizado
                boolean b = match.getSPG_EMPRESA() !=empresa;
                boolean b1 = match.getSPG_CLIENTE() !=cliente;
                boolean b2 = match.getSPG_SUMINISTRO() !=suministro;
                boolean b3 = match.getSPG_LATITUD() !=latitud;
                boolean b4 = match.getSPG_LONGITUD() !=longitud;
                boolean b5 = match.getSPG_FECHA_CAPTURA() !=fecha_captura;
                boolean b6 = match.getSPG_USER_ID() !=user_id;
                boolean b7 = match.getSPG_FECHA_UPDATE() !=fecha_update;


                if (b || b1 || b2 || b3 || b4  || b5  || b6 || b7 ) {

                    Log.i(TAG, "Suministro Posicion Global - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_EMPRESA, match.getSPG_EMPRESA())
                            .withValue(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_CLIENTE, match.getSPG_CLIENTE())
                            .withValue(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_SUMINISTRO, match.getSPG_SUMINISTRO())
                            .withValue(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_LATITUD, match.getSPG_LATITUD())
                            .withValue(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_LONGITUD, match.getSPG_LONGITUD())
                            .withValue(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_FECHA_CAPTURA, match.getSPG_FECHA_CAPTURA())
                            .withValue(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_USER_ID, match.getSPG_USER_ID())
                            .withValue(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_FECHA_UPDATE, match.getSPG_FECHA_UPDATE())
                            .build());
                } else {
                    Log.i(TAG, "Suministro Posicion Global - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoCorpicoApp.SuministroPosicionGlobal.crearUriSuministroPosicionGlobal(cliente,suministro);
                Log.i(TAG, "Suministro Posicion Global - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestSuministroPosicionGlobal sumininistroPosicionGlobal : suministroPosicionGlobalMap.values()){
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.SuministroPosicionGlobal.URI_CONTENIDO)
                    .withValue(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_EMPRESA, sumininistroPosicionGlobal.getSPG_EMPRESA())
                    .withValue(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_CLIENTE, sumininistroPosicionGlobal.getSPG_CLIENTE())
                    .withValue(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_SUMINISTRO, sumininistroPosicionGlobal.getSPG_SUMINISTRO())
                    .withValue(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_LATITUD, sumininistroPosicionGlobal.getSPG_LATITUD())
                    .withValue(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_LONGITUD, sumininistroPosicionGlobal.getSPG_LONGITUD())
                    .withValue(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_FECHA_CAPTURA, sumininistroPosicionGlobal.getSPG_FECHA_CAPTURA())
                    .withValue(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_USER_ID, sumininistroPosicionGlobal.getSPG_USER_ID())
                    .withValue(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_FECHA_UPDATE, sumininistroPosicionGlobal.getSPG_FECHA_UPDATE())
                    .build());
        }
        return operations;
    }
    public static class AsyncQuerySuministroPosicionGlobal extends AsyncQueryHandler {
        private WeakReference<AsyncOpListener> mListener;

        public interface AsyncOpListener {
            void onQueryComplete(int token, Object cookie, Cursor cursor);
        }

        public AsyncQuerySuministroPosicionGlobal(ContentResolver cr) {
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
