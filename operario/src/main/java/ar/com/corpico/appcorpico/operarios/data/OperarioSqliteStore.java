package ar.com.corpico.appcorpico.operarios.data;

import android.content.AsyncQueryHandler;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.lang.ref.WeakReference;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import ar.com.corpico.appcorpico.operarios.data.entity.RestOperario;
import ar.com.corpico.appcorpico.operarios.domain.entity.Operario;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;

/**
 * Created by Administrador on 12/03/2018.
 */

public class OperarioSqliteStore implements OperarioStore {
    private static final String TAG = OperarioSqliteStore.class.getSimpleName();
    private static OperarioSqliteStore INSTANCE;
    private ContentResolver mContentResolver;

    private OperarioSqliteStore(@NonNull ContentResolver mContentResolver) {
        this.mContentResolver = Preconditions.checkNotNull(mContentResolver, "mContentResolver no puede ser null");
    }

    public static OperarioSqliteStore getInstance(@NonNull ContentResolver mContentResolver) {
        if (INSTANCE == null) {
            INSTANCE = new OperarioSqliteStore(mContentResolver);
        }
        return INSTANCE;
    }

    @Override
    public void getOperario(final GetOperarioStoreCallBack callback, int sector) {
        Cursor operariosCursor = mContentResolver.query(ContratoCorpicoApp.Operario.crearUriConSector(ContratoCorpicoApp.Operario.SECTOR_KEY, String.valueOf(sector)),
                null, null, null, null);
        List<Operario> ListOperario = new ArrayList<>();
        if (operariosCursor != null && operariosCursor.getCount()>0) {
            while(operariosCursor.moveToNext()) {
                ListOperario.add(new Operario(operariosCursor.getString(operariosCursor.getColumnIndex(ContratoCorpicoApp.Operario.OPE_DESCRIPCION)),operariosCursor.getInt(operariosCursor.getColumnIndex(ContratoCorpicoApp.Operario.OPE_ID)),operariosCursor.getInt(operariosCursor.getColumnIndex(ContratoCorpicoApp.EmpresasContratista.EML_TIPO_EMPRESA)),operariosCursor.getInt(operariosCursor.getColumnIndex(ContratoCorpicoApp.EmpresasContratista.EML_ID))));
            }
            callback.onSuccess(ListOperario);
        }else{
            callback.onError("No existen Operarios");
        }
        operariosCursor.close();
    }
    @Override
    //TODO: COMO HAGO ACA? PASO EL ID DE LA CUADRILLA Y TRAIGO EN UN CURSOR TODOS LOS DATOS?
    public void addOperario(AddOperarioStoreCallBack callback, List<Integer> operarios, int tipoCuadrillaId, int servicio, int sector) { // int cuadrilla
        Uri insertUri = ContratoCorpicoApp.Cuadrilla.URI_CONTENIDO;
        Uri u = null;
        boolean result=true;
        Log.i(TAG, "Agrega Operario - Programando insercion de: " + insertUri);
        ContentValues valores = new ContentValues();
        for(Integer oper: operarios){
            Integer id = generateUniqueId().intValue();
            valores.put(ContratoCorpicoApp.Cuadrilla.CU_ID, id);
            valores.put(ContratoCorpicoApp.Cuadrilla.CU_TIPO_CUADRILLA, tipoCuadrillaId);
            valores.put(ContratoCorpicoApp.Cuadrilla.CU_SERVICIO, servicio);
            valores.put(ContratoCorpicoApp.Cuadrilla.CU_SECTOR, sector);
            valores.put(ContratoCorpicoApp.Cuadrilla.CU_OPERARIO, oper);
            valores.put(ContratoCorpicoApp.Cuadrilla.CU_FECHA, DateTime.now().toString());
            valores.put(ContratoCorpicoApp.Cuadrilla.CU_FECHA_UPDATE, DateTime.now().toString());
            valores.put(ContratoCorpicoApp.Cuadrilla.CU_SYNC, ContratoCorpicoApp.SYNC_NOT);

            u = mContentResolver.insert(insertUri,valores);
            if(u == null) {
                result = false;
            }
        }

        if (result){
            callback.onSuccess();
        }else{
            callback.onError("No se ha podido eliminar el operario de la cuadrilla");
        }
    }
    public List<ContentProviderOperation> replicarServidor(List<RestOperario> medidoresTipos) {
        //Crea lista de operaciones content provider
        List<ContentProviderOperation> operations = new ArrayList<>();

        // Tabla hash para recibir los tipos de cuadrillas entrantes
        HashMap<Integer, RestOperario> operarioMap = new HashMap<>();
        for (RestOperario ope : medidoresTipos) {
            operarioMap.put((int) ope.getOPE_ID(), ope);
        }

        //Consultar registros remotos actuales
        Uri uri = ContratoCorpicoApp.Operario.URI_CONTENIDO;
        String select = ContratoCorpicoApp.Operario.TABLE_NAME;
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Operarios - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        short id;
        String descripcion;
        short contratista;
        short sucursal;
        String activo;
        String user_id;
        String  fecha_update;
        int lote_replicacion;

        while (c.moveToNext()) {
            id = c.getShort(0);
            descripcion = c.getString(1);
            contratista = c.getShort(2);
            sucursal = c.getShort(3);
            activo =  c.getString(4);
            user_id = c.getString(5);
            fecha_update = c.getString(6);
            lote_replicacion = c.getInt(7);


            RestOperario match = operarioMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                operarioMap.remove(id);

                Uri existingUri = ContratoCorpicoApp.MedidorTipo.crearUriMedidorTipo((int) id);//PASAR LOS ID A INTEGER

                // Comprobar si tipo de cuadrillas necesita ser actualizado
                boolean b = match.getOPE_ID() != id;
                boolean b1 = match.getOPE_DESCRIPCION() != descripcion;
                boolean b2 =  match.getOPE_CONTRATISTA() != contratista;
                boolean b3 = match.getOPE_SUCURSAL() != sucursal;
                boolean b4 = match.getOPE_ACTIVO() != activo;
                boolean b5 = match.getOPE_ID_USER() != user_id;
                boolean b6 = match.getOPE_FECHA_UPDATE() != fecha_update;
                boolean b7 = match.getOPE_LOTE_REPLICACION() != lote_replicacion;


                if (b || b1 || b2 || b3 || b4  || b5  || b6 || b7) {

                    Log.i(TAG, "Operarios - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoCorpicoApp.Operario.OPE_ID, match.getOPE_ID())
                            .withValue(ContratoCorpicoApp.Operario.OPE_DESCRIPCION, match.getOPE_DESCRIPCION())
                            .withValue(ContratoCorpicoApp.Operario.OPE_CONTRATISTA, match.getOPE_CONTRATISTA())
                            .withValue(ContratoCorpicoApp.Operario.OPE_SUCURSAL, match.getOPE_SUCURSAL())
                            .withValue(ContratoCorpicoApp.Operario.OPE_ACTIVO, match.getOPE_ACTIVO())
                            .withValue(ContratoCorpicoApp.Operario.OPE_ID_USER, match.getOPE_ID_USER())
                            .withValue(ContratoCorpicoApp.Operario.OPE_FECHA_UPDATE, match.getOPE_FECHA_UPDATE())
                            .withValue(ContratoCorpicoApp.Operario.OPE_LOTE_REPLICACION, match.getOPE_LOTE_REPLICACION())
                            .build());
                } else {
                    Log.i(TAG, "Operarios - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoCorpicoApp.Operario.crearUriOperario((int) id);
                Log.i(TAG, "Operarios - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestOperario operario : operarioMap.values()){
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.Operario.URI_CONTENIDO)
                    .withValue(ContratoCorpicoApp.Operario.OPE_ID, operario.getOPE_ID())
                    .withValue(ContratoCorpicoApp.Operario.OPE_DESCRIPCION, operario.getOPE_DESCRIPCION())
                    .withValue(ContratoCorpicoApp.Operario.OPE_CONTRATISTA, operario.getOPE_CONTRATISTA())
                    .withValue(ContratoCorpicoApp.Operario.OPE_SUCURSAL, operario.getOPE_SUCURSAL())
                    .withValue(ContratoCorpicoApp.Operario.OPE_ACTIVO, operario.getOPE_ACTIVO())
                    .withValue(ContratoCorpicoApp.Operario.OPE_ID_USER, operario.getOPE_ID_USER())
                    .withValue(ContratoCorpicoApp.Operario.OPE_FECHA_UPDATE, operario.getOPE_FECHA_UPDATE())
                    .withValue(ContratoCorpicoApp.Operario.OPE_LOTE_REPLICACION, operario.getOPE_LOTE_REPLICACION())
                    .build());
        }
        return operations;
    }
    public static class AsyncQueryOperario extends AsyncQueryHandler {
        private WeakReference<AsyncOpListener> mListener;

        public interface AsyncOpListener {
            void onQueryComplete(int token, Object cookie, Cursor cursor);
        }

        public AsyncQueryOperario(ContentResolver cr) {
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
    private Long generateUniqueId()
    {
        long val = -1;
        do
        {
            final UUID uid = UUID.randomUUID();
            final ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
            buffer.putLong(uid.getLeastSignificantBits());
            buffer.putLong(uid.getMostSignificantBits());
            final BigInteger bi = new BigInteger(buffer.array());
            val = bi.longValue();
        } while (val < 0);
        return val;
    }
}
