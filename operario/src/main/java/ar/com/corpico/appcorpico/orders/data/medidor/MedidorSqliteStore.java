package ar.com.corpico.appcorpico.orders.data.medidor;

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

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMedidor;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;

/**
 * Created by Administrador on 12/03/2018.
 */

public class MedidorSqliteStore implements MedidorStore {
    private static final String TAG = MedidorSqliteStore.class.getSimpleName();
    private MedidorSqliteStore INSTANCE;
    private ContentResolver mContentResolver;


    public MedidorSqliteStore(@NonNull ContentResolver mContentResolver) {
        this.mContentResolver = Preconditions.checkNotNull(mContentResolver, "mContentResolver no puede ser null");
    }

    public MedidorSqliteStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MedidorSqliteStore(mContentResolver);
        }
        return INSTANCE;
    }

    @Override
    public void getMedidor(final GetMedidorStoreCallBack callback, Criteria filter) {
        AsyncQueryMedidores handler = new AsyncQueryMedidores(mContentResolver);
        handler.setQueryListener(new  AsyncQueryMedidores.AsyncOpListener() {
            @Override
            public void onQueryComplete(int token, Object cookie, Cursor cursor) {
                // Procesa resultados del cursor
                // Retorna resultados en la callback entrante del método getOrders()
                List<RestMedidor> ListMedidor = new ArrayList<>();
                if (cursor != null && cursor.getCount()>0) {
                    while(cursor.moveToNext()) {
                        ListMedidor.add(new RestMedidor(cursor.getInt(0), cursor.getShort(1), cursor.getShort(2), (byte) cursor.getInt(3), cursor.getShort(4),
                                cursor.getString(5), cursor.getString(6), cursor.getShort(7), (byte) cursor.getInt(8), cursor.getShort(9),
                                (byte) cursor.getInt(10), cursor.getShort(11), (byte) cursor.getInt(12), cursor.getString(13),
                                cursor.getString(14), cursor.getDouble(15), cursor.getString(16), (byte) cursor.getInt(17),
                                cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getInt(21)));

                    }
                    callback.onSuccess(ListMedidor);
                }else{
                    callback.onError("No existen Medidores");
                }
            }
        });
// todo: paso 1 para dejarlo sucio localmente campo sync
        handler.startQuery(0, null, ContratoCorpicoApp.Medidor.URI_CONTENIDO, null, null, null, null);
    }
    public List<ContentProviderOperation> replicarServidor(List<RestMedidor> medidores) {
        //Crea lista de operaciones content provider
        List<ContentProviderOperation> operations = new ArrayList<>();

        // Tabla hash para recibir los tipos de cuadrillas entrantes
        HashMap<Integer, RestMedidor> medidorMap = new HashMap<>();
        for (RestMedidor m : medidores) {
            medidorMap.put((int) m.getMED_ID(), m);
        }

        //Consultar registros remotos actuales
        Uri uri = ContratoCorpicoApp.Medidor.URI_CONTENIDO;
        String select = ContratoCorpicoApp.Medidor.TABLE_NAME;
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Medidores - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        int id;
        short empresa;
        short almacen;
        byte estado;
        short marca;
        String tipo_serie;
        String serie;
        short modelo;
        byte clase;
        short tipo;
        byte ruedas;
        short ano_fabricacion;
        byte ano_vida;
        String fecha_entrada;
        String vto_garantia;
        double factor;
        String posee_led;
        byte motivo_baja;
        String fecha_baja;
        String user_id;
        String fecha_update;
        int lote_replicacion;

        while (c.moveToNext()) {

            id = c.getInt(0);
            empresa = c.getShort(1);
            almacen = c.getShort(2);
            estado = (byte) c.getInt(3);
            marca = c.getShort(4);
            tipo_serie = c.getString(5);
            serie = c.getString(6);
            modelo = c.getShort(7);
            clase = (byte) c.getInt(8);
            tipo = c.getShort(9);
            ruedas = (byte) c.getInt(10);
            ano_fabricacion = c.getShort(11);
            ano_vida = (byte) c.getInt(12);
            fecha_entrada = c.getString(13);
            vto_garantia = c.getString(14);
            factor = c.getDouble(15);
            posee_led = c.getString(16);
            motivo_baja = (byte) c.getInt(17);
            fecha_baja = c.getString(18);
            user_id = c.getString(19);
            fecha_update = c.getString(20);
            lote_replicacion = c.getInt(21);


            RestMedidor match = medidorMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                medidorMap.remove(id);

                Uri existingUri = ContratoCorpicoApp.Medidor.crearUriMedidor((int) id);//PASAR LOS ID A INTEGER

                // Comprobar si tipo de cuadrillas necesita ser actualizado
                boolean b = match.getMED_ID() != id;
                boolean b1 = match.getMED_EMPRESA() != empresa;
                boolean b2 =  match.getMED_ALMACEN() != almacen;
                boolean b3 =  match.getMED_ESTADO() != (estado);
                boolean b4 = match.getMED_MARCA() != marca;
                boolean b5 = match.getMED_TIPOSERIE() != tipo_serie;
                boolean b6 = match.getMED_SERIE() != serie;
                boolean b7 = match.getMED_MODELO() != modelo;
                boolean b8 = match.getMED_CLASE() != clase;
                boolean b9 = match.getMED_TIPO() != tipo;
                boolean b10 = match.getMED_RUEDAS() != ruedas;
                boolean b11 = match.getMED_ANO_FABRICA() != ano_fabricacion;
                boolean b12 = match.getMED_ANOS_VIDA() != ano_vida;
                boolean b13 = match.getMED_FECHA_ENTRADA() != fecha_entrada;
                boolean b14 = match.getMED_VTO_GARANTIA() != vto_garantia;
                boolean b15 = match.getMED_FACTOR() != factor;
                boolean b16 = match.getMED_POSEE_LEDS() != posee_led;
                boolean b17 = match.getMED_MOTIVO_BAJA() != motivo_baja;
                boolean b18 = match.getMED_FECHA_BAJA() != fecha_baja;
                boolean b19 = match.getMED_ID_USER() != user_id;
                boolean b20 = match.getMED_FECHA_UPDATE() != fecha_update;
                boolean b21 = match.getMED_LOTE_REPLICACION() != lote_replicacion;



                if (b || b1 || b2 || b3 || b4  || b5  || b6  || b7 || b8 || b9 || b10
                    || b11 || b12 || b13 || b14  || b15  || b16  || b17 || b18 || b19 || b20 || b21) {

                    Log.i(TAG, "Medidores - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoCorpicoApp.Medidor.MED_ID, match.getMED_ID())
                            .withValue(ContratoCorpicoApp.Medidor.MED_EMPRESA, match.getMED_EMPRESA())
                            .withValue(ContratoCorpicoApp.Medidor.MED_ALMACEN, match.getMED_ALMACEN())
                            .withValue(ContratoCorpicoApp.Medidor.MED_ESTADO, match.getMED_ESTADO())
                            .withValue(ContratoCorpicoApp.Medidor.MED_MARCA, match.getMED_MARCA())
                            .withValue(ContratoCorpicoApp.Medidor.MED_TIPOSERIE, match.getMED_TIPOSERIE())
                            .withValue(ContratoCorpicoApp.Medidor.MED_SERIE, match.getMED_SERIE())
                            .withValue(ContratoCorpicoApp.Medidor.MED_MODELO, match.getMED_MODELO())
                            .withValue(ContratoCorpicoApp.Medidor.MED_CLASE, match.getMED_CLASE())
                            .withValue(ContratoCorpicoApp.Medidor.MED_TIPO, match.getMED_TIPO())
                            .withValue(ContratoCorpicoApp.Medidor.MED_RUEDAS, match.getMED_RUEDAS())
                            .withValue(ContratoCorpicoApp.Medidor.MED_ANO_FABRICA, match.getMED_ANO_FABRICA())
                            .withValue(ContratoCorpicoApp.Medidor.MED_ANOS_VIDA, match.getMED_ANOS_VIDA())
                            .withValue(ContratoCorpicoApp.Medidor.MED_FECHA_ENTRADA, match.getMED_FECHA_ENTRADA())
                            .withValue(ContratoCorpicoApp.Medidor.MED_VTO_GARANTIA, match.getMED_VTO_GARANTIA())
                            .withValue(ContratoCorpicoApp.Medidor.MED_FACTOR, match.getMED_FACTOR())
                            .withValue(ContratoCorpicoApp.Medidor.MED_POSEE_LEDS, match.getMED_POSEE_LEDS())
                            .withValue(ContratoCorpicoApp.Medidor.MED_MOTIVO_BAJA, match.getMED_MOTIVO_BAJA())
                            .withValue(ContratoCorpicoApp.Medidor.MED_FECHA_BAJA, match.getMED_FECHA_BAJA())
                            .withValue(ContratoCorpicoApp.Medidor.MED_ID_USER, match.getMED_ID_USER())
                            .withValue(ContratoCorpicoApp.Medidor.MED_FECHA_UPDATE, match.getMED_FECHA_UPDATE())
                            .withValue(ContratoCorpicoApp.Medidor.MED_LOTE_REPLICACION, match.getMED_LOTE_REPLICACION())
                            .build());
                } else {
                    Log.i(TAG, "Medidores - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoCorpicoApp.Medidor.crearUriMedidor((int) id);
                Log.i(TAG, "Medidores - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestMedidor medidor : medidorMap.values()){
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.Medidor.URI_CONTENIDO)
                    .withValue(ContratoCorpicoApp.Medidor.MED_ID, medidor.getMED_ID())
                    .withValue(ContratoCorpicoApp.Medidor.MED_ID, medidor.getMED_ID())
                    .withValue(ContratoCorpicoApp.Medidor.MED_EMPRESA, medidor.getMED_EMPRESA())
                    .withValue(ContratoCorpicoApp.Medidor.MED_ALMACEN, medidor.getMED_ALMACEN())
                    .withValue(ContratoCorpicoApp.Medidor.MED_ESTADO, medidor.getMED_ESTADO())
                    .withValue(ContratoCorpicoApp.Medidor.MED_MARCA, medidor.getMED_MARCA())
                    .withValue(ContratoCorpicoApp.Medidor.MED_TIPOSERIE, medidor.getMED_TIPOSERIE())
                    .withValue(ContratoCorpicoApp.Medidor.MED_SERIE, medidor.getMED_SERIE())
                    .withValue(ContratoCorpicoApp.Medidor.MED_MODELO, medidor.getMED_MODELO())
                    .withValue(ContratoCorpicoApp.Medidor.MED_CLASE, medidor.getMED_CLASE())
                    .withValue(ContratoCorpicoApp.Medidor.MED_TIPO, medidor.getMED_TIPO())
                    .withValue(ContratoCorpicoApp.Medidor.MED_RUEDAS, medidor.getMED_RUEDAS())
                    .withValue(ContratoCorpicoApp.Medidor.MED_ANO_FABRICA, medidor.getMED_ANO_FABRICA())
                    .withValue(ContratoCorpicoApp.Medidor.MED_ANOS_VIDA, medidor.getMED_ANOS_VIDA())
                    .withValue(ContratoCorpicoApp.Medidor.MED_FECHA_ENTRADA, medidor.getMED_FECHA_ENTRADA())
                    .withValue(ContratoCorpicoApp.Medidor.MED_VTO_GARANTIA, medidor.getMED_VTO_GARANTIA())
                    .withValue(ContratoCorpicoApp.Medidor.MED_FACTOR, medidor.getMED_FACTOR())
                    .withValue(ContratoCorpicoApp.Medidor.MED_POSEE_LEDS, medidor.getMED_POSEE_LEDS())
                    .withValue(ContratoCorpicoApp.Medidor.MED_MOTIVO_BAJA, medidor.getMED_MOTIVO_BAJA())
                    .withValue(ContratoCorpicoApp.Medidor.MED_FECHA_BAJA, medidor.getMED_FECHA_BAJA())
                    .withValue(ContratoCorpicoApp.Medidor.MED_ID_USER, medidor.getMED_ID_USER())
                    .withValue(ContratoCorpicoApp.Medidor.MED_FECHA_UPDATE, medidor.getMED_FECHA_UPDATE())
                    .withValue(ContratoCorpicoApp.Medidor.MED_LOTE_REPLICACION, medidor.getMED_LOTE_REPLICACION())
                    .build());
        }
        return operations;
    }
    public static class AsyncQueryMedidores extends AsyncQueryHandler {
        private WeakReference<AsyncOpListener> mListener;

        public interface AsyncOpListener {
            void onQueryComplete(int token, Object cookie, Cursor cursor);
        }

        public AsyncQueryMedidores(ContentResolver cr) {
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
