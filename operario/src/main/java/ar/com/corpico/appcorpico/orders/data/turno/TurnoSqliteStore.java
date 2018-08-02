package ar.com.corpico.appcorpico.orders.data.turno;

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

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTurnos;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;

/**
 * Created by Administrador on 12/03/2018.
 */

public class TurnoSqliteStore implements TurnoStore {
    private static final String TAG = TurnoSqliteStore.class.getSimpleName();
    private static TurnoSqliteStore INSTANCE;
    private ContentResolver mContentResolver;


    public TurnoSqliteStore(@NonNull ContentResolver mContentResolver) {
        this.mContentResolver = Preconditions.checkNotNull(mContentResolver, "mContentResolver no puede ser null");
    }

    public static TurnoSqliteStore getInstance(ContentResolver mContentResolver) {
        if (INSTANCE == null) {
            INSTANCE = new TurnoSqliteStore(mContentResolver);
        }
        return INSTANCE;
    }

    @Override
    public void getTurno(final GetTurnoStoreCallBack callback, Criteria filter) {
        AsyncQueryTurnos handler = new AsyncQueryTurnos(mContentResolver);
        handler.setQueryListener(new  AsyncQueryTurnos.AsyncOpListener() {
            @Override
            public void onQueryComplete(int token, Object cookie, Cursor cursor) {
                List<RestTurnos> ListTurno = new ArrayList<>();
                if (cursor != null && cursor.getCount()>0) {
                    while(cursor.moveToNext()) {
                        ListTurno.add(new RestTurnos(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getInt(4)));
                    }
                    callback.onSuccess(ListTurno);
                }else{
                    callback.onError("No existen Turno");
                }
            }
        });
// todo: paso 1 para dejarlo sucio localmente campo sync
        handler.startQuery(0, null, ContratoCorpicoApp.Etapas.URI_CONTENIDO, null, null, null, null);
    }
    public List<ContentProviderOperation> replicarServidor(List<RestTurnos> turnos) {
        //Crea lista de operaciones content provider
        List<ContentProviderOperation> operations = new ArrayList<>();

        // Tabla hash para recibir los tipos de cuadrillas entrantes
        HashMap<Integer, RestTurnos> turnoMap = new HashMap<>();
        for (RestTurnos t : turnos) {
            turnoMap.put(t.getTUR_ORDEN(), t);
        }

        //Consultar registros remotos actuales
        Uri uri = ContratoCorpicoApp.Turnos.URI_CONTENIDO;
        String select = ContratoCorpicoApp.Turnos.TABLE_NAME;
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Turno - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        int id;
        String turn;
        String fecha_update;
        int servicio;
        int sector;

        while (c.moveToNext()) {

            id = c.getInt(0);
            turn = c.getString(1);
            fecha_update = c.getString(2);
            servicio = c.getInt(3);
            sector = c.getInt(4);

            RestTurnos match = turnoMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                turnoMap.remove(id);

                Uri existingUri = ContratoCorpicoApp.Turnos.crearUriTurno(id);//PASAR LOS ID A INTEGER

                // Comprobar si tipo de cuadrillas necesita ser actualizado
                boolean b = match.getTUR_ORDEN() != id;
                boolean b1 = match.getTUR_TURNO() != turn;
                boolean b2 =  match.getTUR_FECHA_UPDATE() != fecha_update;
                boolean b3 =  match.getTUR_SERVICIO() != servicio;
                boolean b4 =  match.getTUR_SECTOR() != sector;

                if (b || b1 || b2 || b3 || b4 ) {

                    Log.i(TAG, "Turno - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoCorpicoApp.Turnos.TUR_ORDEN, match.getTUR_ORDEN())
                            .withValue(ContratoCorpicoApp.Turnos.TUR_TURNO, match.getTUR_TURNO())
                            .withValue(ContratoCorpicoApp.Turnos.TUR_FECHA_UPDATE, match.getTUR_FECHA_UPDATE())
                            .withValue(ContratoCorpicoApp.Turnos.TUR_SERVICIO, match.getTUR_SERVICIO())
                            .withValue(ContratoCorpicoApp.Turnos.TUR_SECTOR, match.getTUR_SECTOR())
                            .build());
                } else {
                    Log.i(TAG, "Turno - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoCorpicoApp.Turnos.crearUriTurno(id);
                Log.i(TAG, "Turno - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestTurnos turno : turnoMap.values()){
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.Turnos.URI_CONTENIDO)
                    .withValue(ContratoCorpicoApp.Turnos.TUR_ORDEN, turno.getTUR_ORDEN())
                    .withValue(ContratoCorpicoApp.Turnos.TUR_TURNO, turno.getTUR_TURNO())
                    .withValue(ContratoCorpicoApp.Turnos.TUR_FECHA_UPDATE, turno.getTUR_FECHA_UPDATE())
                    .withValue(ContratoCorpicoApp.Turnos.TUR_SERVICIO, turno.getTUR_SERVICIO())
                    .withValue(ContratoCorpicoApp.Turnos.TUR_SECTOR, turno.getTUR_SECTOR())
                    .build());
        }
        return operations;
    }
    public static class AsyncQueryTurnos extends AsyncQueryHandler {
        private WeakReference<AsyncOpListener> mListener;

        public interface AsyncOpListener {
            void onQueryComplete(int token, Object cookie, Cursor cursor);
        }

        public AsyncQueryTurnos(ContentResolver cr) {
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
