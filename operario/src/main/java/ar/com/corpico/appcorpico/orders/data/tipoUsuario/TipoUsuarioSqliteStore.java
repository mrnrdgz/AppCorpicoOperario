package ar.com.corpico.appcorpico.orders.data.tipoUsuario;

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

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoUsuario;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;

/**
 * Created by Administrador on 12/03/2018.
 */

public class TipoUsuarioSqliteStore implements TipoUsuarioStore {
    private static final String TAG = TipoUsuarioSqliteStore.class.getSimpleName();
    private TipoUsuarioSqliteStore INSTANCE;
    private ContentResolver mContentResolver;


    public TipoUsuarioSqliteStore(@NonNull ContentResolver mContentResolver) {
        this.mContentResolver = Preconditions.checkNotNull(mContentResolver, "mContentResolver no puede ser null");
    }

    public TipoUsuarioSqliteStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TipoUsuarioSqliteStore(mContentResolver);
        }
        return INSTANCE;
    }

    @Override
    public void getTipoUsuario(final GetTipoUsuarioStoreCallBack callback, Criteria filter) {
        AsyncQueryTipoUsuario handler = new AsyncQueryTipoUsuario(mContentResolver);
        handler.setQueryListener(new  AsyncQueryTipoUsuario.AsyncOpListener() {
            @Override
            public void onQueryComplete(int token, Object cookie, Cursor cursor) {
                List<RestTipoUsuario> ListTipoUsuario = new ArrayList<>();
                if (cursor != null && cursor.getCount()>0) {
                    while(cursor.moveToNext()) {
                        ListTipoUsuario.add(new RestTipoUsuario(cursor.getShort(0), cursor.getString(1), (byte) cursor.getInt(2), cursor.getString(3),
                                cursor.getString(4), cursor.getString(5), cursor.getString(6),
                                cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getInt(10)));

                    }
                    callback.onSuccess(ListTipoUsuario);
                }else{
                    callback.onError("No existen Tipo Usuario");
                }
            }
        });
// todo: paso 1 para dejarlo sucio localmente campo sync
        handler.startQuery(0, null, ContratoCorpicoApp.TipoUsuario.URI_CONTENIDO, null, null, null, null);
    }
    public List<ContentProviderOperation> replicarServidor(List<RestTipoUsuario> tiposUsuarios) {
        //Crea lista de operaciones content provider
        List<ContentProviderOperation> operations = new ArrayList<>();

        // Tabla hash para recibir los tipos de cuadrillas entrantes
        HashMap<Integer, RestTipoUsuario> tipoUsuarioMap = new HashMap<>();
        for (RestTipoUsuario tiu : tiposUsuarios) {
            tipoUsuarioMap.put((int) tiu.getTIU_ID(), tiu);
        }

        //Consultar registros remotos actuales
        Uri uri = ContratoCorpicoApp.TipoUsuario.URI_CONTENIDO;
        String select = ContratoCorpicoApp.TipoUsuario.TABLE_NAME;
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Tipo Usuario - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        short id;
        String descripcion;
        byte tipo_empresa;
        String abreviatura;
        String solicita_ciiu;
        String punto_medicion_multiple;
        String medidor_funcion_multiple;
        String activo;
        String user_id;
        String fecha_update;
        int lote_replicacion;

        while (c.moveToNext()) {
            id = c.getShort(0);
            descripcion = c.getString(1);
            tipo_empresa = (byte) c.getInt(2);
            abreviatura = abreviatura = c.getString(3);
            solicita_ciiu = c.getString(4);
            punto_medicion_multiple = c.getString(5);
            medidor_funcion_multiple = c.getString(6);
            activo = c.getString(7);
            user_id = c.getString(8);
            fecha_update = c.getString(9);
            lote_replicacion = c.getInt(10);


            RestTipoUsuario match = tipoUsuarioMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                tipoUsuarioMap.remove(id);

                Uri existingUri = ContratoCorpicoApp.TipoUsuario.crearUriTipoUsuario((int) id);//PASAR LOS ID A INTEGER

                // Comprobar si tipo de cuadrillas necesita ser actualizado
                boolean b = match.getTIU_ID() != id;
                boolean b1 = match.getTIU_DESCRIPCION() != descripcion;
                boolean b2 =  match.getTIU_TIPO_EMPRESA() != tipo_empresa;
                boolean b3 = match.getTIU_ABREVIATURA() != abreviatura;
                boolean b4 = match.getTIU_SOLICITA_CIIU() != solicita_ciiu;
                boolean b5 = match.getTIU_PUNTOS_MEDICION_MULTIPLES() != punto_medicion_multiple;
                boolean b6 = match.getTIU_MEDIDOR_FUNCION_MULTIPLE() != medidor_funcion_multiple;
                boolean b7 = match.getTIU_ACTIVO() != activo;
                boolean b8 = match.getTIU_ID_USER() != user_id;
                boolean b9 = match.getTIU_FECHA_UPDATE() != fecha_update;
                boolean b10 = match.getTIU_LOTE_REPLICACION() != lote_replicacion;


                if (b || b1 || b2 || b3 || b4  || b5  || b6 || b7 || b8 || b9 || b10 ) {

                    Log.i(TAG, "Tipo Usuario - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoCorpicoApp.TipoUsuario.TIU_ID, match.getTIU_ID())
                            .withValue(ContratoCorpicoApp.TipoUsuario.TIU_DESCRIPCION, match.getTIU_DESCRIPCION())
                            .withValue(ContratoCorpicoApp.TipoUsuario.TIU_TIPO_EMPRESA, match.getTIU_TIPO_EMPRESA())
                            .withValue(ContratoCorpicoApp.TipoUsuario.TIU_ABREVIATURA, match.getTIU_ABREVIATURA())
                            .withValue(ContratoCorpicoApp.TipoUsuario.TIU_SOLICITA_CIIU, match.getTIU_SOLICITA_CIIU())
                            .withValue(ContratoCorpicoApp.TipoUsuario.TIU_PUNTOS_MEDICION_MULTIPLES , match.getTIU_PUNTOS_MEDICION_MULTIPLES())
                            .withValue(ContratoCorpicoApp.TipoUsuario.TIU_MEDIDOR_FUNCION_MULTIPLE , match.getTIU_MEDIDOR_FUNCION_MULTIPLE())
                            .withValue(ContratoCorpicoApp.TipoUsuario.TIU_ACTIVO , match.getTIU_ACTIVO())
                            .withValue(ContratoCorpicoApp.TipoUsuario.TIU_ID_USER, match.getTIU_ID_USER())
                            .withValue(ContratoCorpicoApp.TipoUsuario.TIU_FECHA_UPDATE , match.getTIU_FECHA_UPDATE())
                            .withValue(ContratoCorpicoApp.TipoUsuario.TIU_LOTE_REPLICACION , match.getTIU_LOTE_REPLICACION())
                            .build());
                } else {
                    Log.i(TAG, "Tipo Usuario - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoCorpicoApp.TipoUsuario.crearUriTipoUsuario((int) id);
                Log.i(TAG, "Tipo Usuario - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestTipoUsuario tipoUsuario : tipoUsuarioMap.values()){
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.TipoUsuario.URI_CONTENIDO)
                    .withValue(ContratoCorpicoApp.TipoUsuario.TIU_ID, tipoUsuario.getTIU_ID())
                    .withValue(ContratoCorpicoApp.TipoUsuario.TIU_DESCRIPCION, tipoUsuario.getTIU_DESCRIPCION())
                    .withValue(ContratoCorpicoApp.TipoUsuario.TIU_TIPO_EMPRESA, tipoUsuario.getTIU_TIPO_EMPRESA())
                    .withValue(ContratoCorpicoApp.TipoUsuario.TIU_ABREVIATURA, tipoUsuario.getTIU_ABREVIATURA())
                    .withValue(ContratoCorpicoApp.TipoUsuario.TIU_SOLICITA_CIIU, tipoUsuario.getTIU_SOLICITA_CIIU())
                    .withValue(ContratoCorpicoApp.TipoUsuario.TIU_PUNTOS_MEDICION_MULTIPLES , tipoUsuario.getTIU_PUNTOS_MEDICION_MULTIPLES())
                    .withValue(ContratoCorpicoApp.TipoUsuario.TIU_MEDIDOR_FUNCION_MULTIPLE , tipoUsuario.getTIU_MEDIDOR_FUNCION_MULTIPLE())
                    .withValue(ContratoCorpicoApp.TipoUsuario.TIU_ACTIVO , tipoUsuario.getTIU_ACTIVO())
                    .withValue(ContratoCorpicoApp.TipoUsuario.TIU_ID_USER, tipoUsuario.getTIU_ID_USER())
                    .withValue(ContratoCorpicoApp.TipoUsuario.TIU_FECHA_UPDATE , tipoUsuario.getTIU_FECHA_UPDATE())
                    .withValue(ContratoCorpicoApp.TipoUsuario.TIU_LOTE_REPLICACION , tipoUsuario.getTIU_LOTE_REPLICACION())
                    .build());
        }
        return operations;
    }
    public static class AsyncQueryTipoUsuario extends AsyncQueryHandler {
        private WeakReference<AsyncOpListener> mListener;

        public interface AsyncOpListener {
            void onQueryComplete(int token, Object cookie, Cursor cursor);
        }

        public AsyncQueryTipoUsuario(ContentResolver cr) {
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
