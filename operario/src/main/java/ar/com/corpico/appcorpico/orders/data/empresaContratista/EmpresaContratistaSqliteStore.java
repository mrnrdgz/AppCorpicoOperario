package ar.com.corpico.appcorpico.orders.data.empresaContratista;

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

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestEmpresaContratista;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;

/**
 * Created by Administrador on 12/03/2018.
 */

public class EmpresaContratistaSqliteStore implements EmpresaContratistaStore {
    private static final String TAG = EmpresaContratistaSqliteStore.class.getSimpleName();
    private static EmpresaContratistaSqliteStore INSTANCE;
    private ContentResolver mContentResolver;

    private EmpresaContratistaSqliteStore(@NonNull ContentResolver mContentResolver) {
        this.mContentResolver = Preconditions.checkNotNull(mContentResolver, "mContentResolver no puede ser null");
    }

    public static EmpresaContratistaSqliteStore getInstance(@NonNull ContentResolver mContentResolver) {
        if (INSTANCE == null) {
            INSTANCE = new EmpresaContratistaSqliteStore(mContentResolver);
        }
        return INSTANCE;
    }

    @Override
    public void getEmpresaContratista(final GetEmpresaContratistaStoreCallBack callback, Criteria filter) {
        AsyncQueryEmpresaContratista handler = new AsyncQueryEmpresaContratista(mContentResolver);
        handler.setQueryListener(new  AsyncQueryEmpresaContratista.AsyncOpListener() {
            @Override
            public void onQueryComplete(int token, Object cookie, Cursor cursor) {
                List<RestEmpresaContratista> ListEmpresaContratista = new ArrayList<>();
                if (cursor != null && cursor.getCount()>0) {
                    while(cursor.moveToNext()) {
                        ListEmpresaContratista.add(new RestEmpresaContratista(cursor.getShort(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getShort(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getInt(9)));
                    }
                    callback.onSuccess(ListEmpresaContratista);
                }else{
                    callback.onError("No existen Empresas Contratistas");
                }
            }
        });
// todo: paso 1 para dejarlo sucio localmente campo sync
        handler.startQuery(0, null, ContratoCorpicoApp.EmpresasContratista.URI_CONTENIDO, null, null, null, null);
    }
    public List<ContentProviderOperation> replicarServidor(List<RestEmpresaContratista> empresaContratistas) {
        //Crea lista de operaciones content provider
        List<ContentProviderOperation> operations = new ArrayList<>();

        // Tabla hash para recibir los tipos de cuadrillas entrantes
        HashMap<Integer, RestEmpresaContratista> empresaContratistaMap = new HashMap<>();
        for (RestEmpresaContratista ec : empresaContratistas) {
            empresaContratistaMap.put((int) ec.getEML_ID(), ec);
        }

        //Consultar registros remotos actuales
        Uri uri = ContratoCorpicoApp.EmpresasContratista.URI_CONTENIDO;
        String select = ContratoCorpicoApp.EmpresasContratista.TABLE_NAME;
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Empresas Contratistas - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        short id;
        String descripcion;
        String direccion;
        String telefono;
        String tipo_toma;
        short tipo_empresa;
        String activo;
        String user_id;
        String fecha_update;
        int lote_replicacion;

        while (c.moveToNext()) {
            id = c.getShort(0);
            descripcion = c.getString(1);
            direccion = c.getString(2);
            telefono = c.getString(3);
            tipo_toma = c.getString(4);
            tipo_empresa = c.getShort(5);
            activo = c.getString(6);
            user_id = c.getString(7);
            fecha_update = c.getString(8);
            lote_replicacion = c.getInt(9);


            RestEmpresaContratista match = empresaContratistaMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                empresaContratistaMap.remove(id);

                Uri existingUri = ContratoCorpicoApp.EmpresasContratista.crearUriEmpresaContratista((int) id);//PASAR LOS ID A INTEGER

                // Comprobar si tipo de cuadrillas necesita ser actualizado
                boolean b = match.getEML_ID() != id;
                boolean b1 = match.getEML_DESCRIPCION() != descripcion;
                boolean b2 =  match.getEML_DIRECCION() != direccion;
                boolean b3 =  match.getEML_TELEFONO() != telefono;
                boolean b4 =  match.getEML_TIPO_TOMA() != tipo_toma;
                boolean b5 = match.getEML_TIPO_EMPRESA() != tipo_empresa;
                boolean b6 = match.getEML_ACTIVO() != activo;
                boolean b7 = match.getEML_ID_USER() != user_id;
                boolean b8 = match.getEML_FECHA_UPDATE() != fecha_update;
                boolean b9 = match.getEML_LOTE_REPLICACION() != lote_replicacion;


                if (b || b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8 || b9) {

                    Log.i(TAG, "Empresas Contratistas - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoCorpicoApp.EmpresasContratista.EML_ID, match.getEML_ID())
                            .withValue(ContratoCorpicoApp.EmpresasContratista.EML_DESCRIPCION, match.getEML_DESCRIPCION())
                            .withValue(ContratoCorpicoApp.EmpresasContratista.EML_DIRECCION, match.getEML_DIRECCION())
                            .withValue(ContratoCorpicoApp.EmpresasContratista.EML_TELEFONO, match.getEML_TELEFONO())
                            .withValue(ContratoCorpicoApp.EmpresasContratista.EML_TIPO_TOMA, match.getEML_TIPO_TOMA())
                            .withValue(ContratoCorpicoApp.EmpresasContratista.EML_TIPO_EMPRESA, match.getEML_TIPO_EMPRESA())
                            .withValue(ContratoCorpicoApp.EmpresasContratista.EML_ACTIVO, match.getEML_ACTIVO())
                            .withValue(ContratoCorpicoApp.EmpresasContratista.EML_ID_USER, match.getEML_ID_USER())
                            .withValue(ContratoCorpicoApp.EmpresasContratista.EML_FECHA_UPDATE, match.getEML_FECHA_UPDATE())
                            .withValue(ContratoCorpicoApp.EmpresasContratista.EML_LOTE_REPLICACION, match.getEML_LOTE_REPLICACION())
                            .build());
                } else {
                    Log.i(TAG, "Empresas Contratistas - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoCorpicoApp.EmpresasContratista.crearUriEmpresaContratista((int) id);
                Log.i(TAG, "Empresas Contratistas - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestEmpresaContratista empresaContratista : empresaContratistaMap.values()){
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.EmpresasContratista.URI_CONTENIDO)
                    .withValue(ContratoCorpicoApp.EmpresasContratista.EML_ID, empresaContratista.getEML_ID())
                    .withValue(ContratoCorpicoApp.EmpresasContratista.EML_DESCRIPCION, empresaContratista.getEML_DESCRIPCION())
                    .withValue(ContratoCorpicoApp.EmpresasContratista.EML_DIRECCION, empresaContratista.getEML_DIRECCION())
                    .withValue(ContratoCorpicoApp.EmpresasContratista.EML_TELEFONO, empresaContratista.getEML_TELEFONO())
                    .withValue(ContratoCorpicoApp.EmpresasContratista.EML_TIPO_TOMA, empresaContratista.getEML_TIPO_TOMA())
                    .withValue(ContratoCorpicoApp.EmpresasContratista.EML_TIPO_EMPRESA, empresaContratista.getEML_TIPO_EMPRESA())
                    .withValue(ContratoCorpicoApp.EmpresasContratista.EML_ACTIVO, empresaContratista.getEML_ACTIVO())
                    .withValue(ContratoCorpicoApp.EmpresasContratista.EML_ID_USER, empresaContratista.getEML_ID_USER())
                    .withValue(ContratoCorpicoApp.EmpresasContratista.EML_FECHA_UPDATE, empresaContratista.getEML_FECHA_UPDATE())
                    .withValue(ContratoCorpicoApp.EmpresasContratista.EML_LOTE_REPLICACION, empresaContratista.getEML_LOTE_REPLICACION())
                    .build());
        }
        return operations;
    }
    public static class AsyncQueryEmpresaContratista extends AsyncQueryHandler {
        private WeakReference<AsyncOpListener> mListener;

        public interface AsyncOpListener {
            void onQueryComplete(int token, Object cookie, Cursor cursor);
        }

        public AsyncQueryEmpresaContratista(ContentResolver cr) {
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
