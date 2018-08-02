package ar.com.corpico.appcorpico.orders.data.puntoMedicionMedidor;

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

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestPuntoMedicionMedidor;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;

/**
 * Created by Administrador on 12/03/2018.
 */

public class PuntoMedicionMedidorSqliteStore implements PuntoMedicionMedidorStore {
    private static final String TAG = PuntoMedicionMedidorSqliteStore.class.getSimpleName();
    private PuntoMedicionMedidorSqliteStore INSTANCE;
    private ContentResolver mContentResolver;


    public PuntoMedicionMedidorSqliteStore(@NonNull ContentResolver mContentResolver) {
        this.mContentResolver = Preconditions.checkNotNull(mContentResolver, "mContentResolver no puede ser null");
    }

    public PuntoMedicionMedidorSqliteStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PuntoMedicionMedidorSqliteStore(mContentResolver);
        }
        return INSTANCE;
    }

    @Override
    public void getPuntoMedicionMedidor(final GetPuntoMedicionMedidorStoreCallBack callback, Criteria filter) {
        AsyncQueryPuntoMedicioMedicion handler = new AsyncQueryPuntoMedicioMedicion(mContentResolver);
        handler.setQueryListener(new  AsyncQueryPuntoMedicioMedicion.AsyncOpListener() {
            @Override
            public void onQueryComplete(int token, Object cookie, Cursor cursor) {
                List<RestPuntoMedicionMedidor> ListPuntoMedicionMedidor = new ArrayList<>();
                if (cursor != null && cursor.getCount()>0) {
                    while(cursor.moveToNext()) {
                        ListPuntoMedicionMedidor.add(new RestPuntoMedicionMedidor((byte)cursor.getInt(0), cursor.getInt(1), cursor.getShort(2), (byte) cursor.getInt(3),
                                cursor.getShort(4), cursor.getInt(5), (byte) cursor.getInt(6),
                                cursor.getShort(7), cursor.getString(8), cursor.getString(9),
                                cursor.getString(10), cursor.getInt(11)));

                    }
                    callback.onSuccess(ListPuntoMedicionMedidor);
                }else{
                    callback.onError("No existen Punto Medicion Medidor");
                }
            }
        });
// todo: paso 1 para dejarlo sucio localmente campo sync
        handler.startQuery(0, null, ContratoCorpicoApp.PuntoMedicioMedidor.URI_CONTENIDO, null, null, null, null);
    }
    public List<ContentProviderOperation> replicarServidor(List<RestPuntoMedicionMedidor> puntoMedicionMedidors) {
        //Crea lista de operaciones content provider
        List<ContentProviderOperation> operations = new ArrayList<>();

        // Tabla hash para recibir los tipos de cuadrillas entrantes
        HashMap<String, RestPuntoMedicionMedidor> puntoMedicionMedidorMap = new HashMap<>();
        String ids;
        for (RestPuntoMedicionMedidor pmm : puntoMedicionMedidors) {
            ids = pmm.getPMM_CLIENTE() + "#" + pmm.getPMM_SUMINISTRO() + "#" + pmm.getPMM_TIPO_EMPRESA()
                    + "#" + pmm.getPMM_PUNTO_MEDICION() + "#" + pmm.getPMM_MEDIDOR();
            puntoMedicionMedidorMap.put(ids,pmm);

        }

        //Consultar registros remotos actuales
        Uri uri = ContratoCorpicoApp.PuntoMedicioMedidor.URI_CONTENIDO;
        String select = ContratoCorpicoApp.PuntoMedicioMedidor.TABLE_NAME;
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Punto Medicion Medidor - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        short empresa;
        int cliente;
        short suministro;
        byte tipo_empresa;
        short punto_medicion;
        int medidor;
        byte ubicacion_habitaculo;
        short advertencia;
        String es_facturable;
        String user_id;
        String fecha_update;
        int lote_replicacion;

        while (c.moveToNext()) {
            empresa = (byte)c.getInt(0);
            cliente = c.getInt(1);
            suministro = c.getShort(2);
            tipo_empresa = (byte) c.getInt(3);
            punto_medicion = c.getShort(4);
            medidor = c.getInt(5);
            ubicacion_habitaculo = (byte) c.getInt(6);
            advertencia = c.getShort(7);
            es_facturable = c.getString(8);
            user_id = c.getString(9);
            fecha_update = c.getString(10);
            lote_replicacion = c.getInt(11);


            ids = cliente + "#" + suministro + "#" + tipo_empresa + "#" + punto_medicion + "#" + medidor;
            RestPuntoMedicionMedidor match = puntoMedicionMedidorMap.get(ids);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                puntoMedicionMedidorMap.remove(ids);

                Uri existingUri = ContratoCorpicoApp.PuntoMedicioMedidor.crearUriPuntoMedicionMedidor(cliente,suministro,tipo_empresa,punto_medicion,medidor);//PASAR LOS ID A INTEGER

                // Comprobar si tipo de cuadrillas necesita ser actualizado
                boolean b = match.getPMM_EMPRESA() != empresa;
                boolean b1 = match.getPMM_CLIENTE() != cliente;
                boolean b2 =  match.getPMM_SUMINISTRO() != suministro;
                boolean b3 = match.getPMM_TIPO_EMPRESA() != tipo_empresa;
                boolean b4 = match.getPMM_PUNTO_MEDICION() != punto_medicion;
                boolean b5 = match.getPMM_MEDIDOR() != medidor;
                boolean b6 = match.getPMM_UBICACION_HABITACULO() != ubicacion_habitaculo;
                boolean b7 = match.getPMM_ADVERTENCIA() != advertencia;
                boolean b8 = match.getPMM_ES_FACTURABLE() != es_facturable;
                boolean b9 = match.getPMM_ID_USER() != user_id;
                boolean b10 = match.getPMM_FECHA_UPDATE() != fecha_update;
                boolean b11 = match.getPMM_LOTE_REPLICACION() != lote_replicacion;


                if (b || b1 || b2 || b3 || b4  || b5  || b6 || b7 || b8 || b9 || b10  || b11) {

                    Log.i(TAG, "Punto Medicion Medidor - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_EMPRESA, match.getPMM_EMPRESA())
                            .withValue(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_CLIENTE, match.getPMM_CLIENTE())
                            .withValue(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_SUMINISTRO, match.getPMM_SUMINISTRO())
                            .withValue(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_TIPO_EMPRESA, match.getPMM_TIPO_EMPRESA())
                            .withValue(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_PUNTO_MEDICION, match.getPMM_PUNTO_MEDICION())
                            .withValue(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_MEDIDOR, match.getPMM_MEDIDOR())
                            .withValue(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_UBICACION_HABITACULO, match.getPMM_UBICACION_HABITACULO())
                            .withValue(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_ADVERTENCIA, match.getPMM_ADVERTENCIA())
                            .withValue(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_ES_FACTURABLE, match.getPMM_ES_FACTURABLE())
                            .withValue(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_ID_USER, match.getPMM_ID_USER())
                            .withValue(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_FECHA_UPDATE, match.getPMM_FECHA_UPDATE())
                            .withValue(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_LOTE_REPLICACION, match.getPMM_LOTE_REPLICACION())
                            .build());
                } else {
                    Log.i(TAG, "Punto Medicion Medidor - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoCorpicoApp.PuntoMedicioMedidor.crearUriPuntoMedicionMedidor(cliente,suministro,tipo_empresa,punto_medicion,medidor);
                Log.i(TAG, "Punto Medicion Medidor - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestPuntoMedicionMedidor puntoMedicionMedidor : puntoMedicionMedidorMap.values()){
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.PuntoMedicioMedidor.URI_CONTENIDO)
                    .withValue(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_EMPRESA, puntoMedicionMedidor.getPMM_EMPRESA())
                    .withValue(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_CLIENTE, puntoMedicionMedidor.getPMM_CLIENTE())
                    .withValue(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_SUMINISTRO, puntoMedicionMedidor.getPMM_SUMINISTRO())
                    .withValue(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_TIPO_EMPRESA, puntoMedicionMedidor.getPMM_TIPO_EMPRESA())
                    .withValue(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_PUNTO_MEDICION, puntoMedicionMedidor.getPMM_PUNTO_MEDICION())
                    .withValue(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_MEDIDOR, puntoMedicionMedidor.getPMM_MEDIDOR())
                    .withValue(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_UBICACION_HABITACULO, puntoMedicionMedidor.getPMM_UBICACION_HABITACULO())
                    .withValue(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_ADVERTENCIA, puntoMedicionMedidor.getPMM_ADVERTENCIA())
                    .withValue(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_ES_FACTURABLE, puntoMedicionMedidor.getPMM_ES_FACTURABLE())
                    .withValue(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_ID_USER, puntoMedicionMedidor.getPMM_ID_USER())
                    .withValue(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_FECHA_UPDATE, puntoMedicionMedidor.getPMM_FECHA_UPDATE())
                    .withValue(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_LOTE_REPLICACION, puntoMedicionMedidor.getPMM_LOTE_REPLICACION())
                    .build());
        }
        return operations;
    }
    public static class AsyncQueryPuntoMedicioMedicion extends AsyncQueryHandler {
        private WeakReference<AsyncOpListener> mListener;

        public interface AsyncOpListener {
            void onQueryComplete(int token, Object cookie, Cursor cursor);
        }

        public AsyncQueryPuntoMedicioMedicion(ContentResolver cr) {
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
