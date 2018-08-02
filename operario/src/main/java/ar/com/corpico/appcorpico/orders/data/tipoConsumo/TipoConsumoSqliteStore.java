package ar.com.corpico.appcorpico.orders.data.tipoConsumo;

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

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoConsumo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;

/**
 * Created by Administrador on 12/03/2018.
 */

public class TipoConsumoSqliteStore implements TipoConsumoStore {
    private static final String TAG = TipoConsumoSqliteStore.class.getSimpleName();
    private TipoConsumoSqliteStore INSTANCE;
    private ContentResolver mContentResolver;


    public TipoConsumoSqliteStore(@NonNull ContentResolver mContentResolver) {
        this.mContentResolver = Preconditions.checkNotNull(mContentResolver, "mContentResolver no puede ser null");
    }

    public TipoConsumoSqliteStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TipoConsumoSqliteStore(mContentResolver);
        }
        return INSTANCE;
    }

    @Override
    public void getTipoConsumo(final GetTipoConsumoStoreCallBack callback, Criteria filter) {
        AsyncQueryTipoConsumo handler = new AsyncQueryTipoConsumo(mContentResolver);
        handler.setQueryListener(new  AsyncQueryTipoConsumo.AsyncOpListener() {
            @Override
            public void onQueryComplete(int token, Object cookie, Cursor cursor) {
                List<RestTipoConsumo> ListTipoConsumo = new ArrayList<>();
                if (cursor != null && cursor.getCount()>0) {
                    while(cursor.moveToNext()) {
                        ListTipoConsumo.add(new RestTipoConsumo(cursor.getShort(0), cursor.getString(1), (byte) cursor.getInt(2), cursor.getString(3),
                                cursor.getString(4), cursor.getString(5), cursor.getString(6),
                                cursor.getString(7), cursor.getString(8), cursor.getString(9),
                                cursor.getString(10), cursor.getString(11), cursor.getString(12),
                                cursor.getString(13), cursor.getString(14), cursor.getString(15),
                                cursor.getString(16), cursor.getString(17), cursor.getInt(18), (byte) cursor.getInt(19),
                                cursor.getDouble(20), cursor.getDouble(21), cursor.getDouble(22), cursor.getString(23), cursor.getString(24)));
                    }
                    callback.onSuccess(ListTipoConsumo);
                }else{
                    callback.onError("No existen Tipo Consumo");
                }
            }
        });
// todo: paso 1 para dejarlo sucio localmente campo sync
        handler.startQuery(0, null, ContratoCorpicoApp.TipoConsumo.URI_CONTENIDO, null, null, null, null);
    }
    public List<ContentProviderOperation> replicarServidor(List<RestTipoConsumo> tiposConsumos) {
        //Crea lista de operaciones content provider
        List<ContentProviderOperation> operations = new ArrayList<>();

        // Tabla hash para recibir los tipos de cuadrillas entrantes
        HashMap<Integer, RestTipoConsumo> tipoConsumoMap = new HashMap<>();
        for (RestTipoConsumo tic : tiposConsumos) {
            tipoConsumoMap.put((int) tic.getTIC_ID(), tic);
        }

        //Consultar registros remotos actuales
        Uri uri = ContratoCorpicoApp.TipoConsumo.URI_CONTENIDO;
        String select = ContratoCorpicoApp.TipoConsumo.TABLE_NAME;
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Tipo Consumo - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        short id;
        String descripcion;
        byte tipo_empresa;
        String abreviatura;
        String tipo_consumo;
        String tarifa_consumo;
        String tarifa_reserva;
        String cargo_fijo;
        String factura_minima;
        String transporte;
        String imputacion_contable;
        String factor_presion;
        String utiliza_transformador;
        String cargo_fijo_descuento;
        String tarifa_consumo_descuento;
        String activo;
        String user_id;
        String fecha_update;
        int lote_replicacion;
        byte situacion_iva;
        double porcentaje_control_consumo_reducido;
        double porcentaje_control_consumo_elevado;
        double consumo_minimo_control;
        String muestra_en_atp;
        String muestra_en_admin;

        while (c.moveToNext()) {
            id = c.getShort(0);
            descripcion = c.getString(1);
            tipo_empresa = (byte) c.getInt(2);
            abreviatura = c.getString(3);
            tipo_consumo = c.getString(4);
            tarifa_consumo = c.getString(5);
            tarifa_reserva = c.getString(6);
            cargo_fijo = c.getString(7);
            factura_minima = c.getString(8);
            transporte = c.getString(9);
            imputacion_contable = c.getString(10);
            factor_presion = c.getString(11);
            utiliza_transformador = c.getString(12);
            cargo_fijo_descuento = c.getString(13);
            tarifa_consumo_descuento = c.getString(14);
            activo = c.getString(15);
            user_id = c.getString(16);
            fecha_update =  c.getString(17);
            lote_replicacion = c.getInt(18);
            situacion_iva = (byte) c.getInt(19);
            porcentaje_control_consumo_reducido = c.getDouble(20);
            porcentaje_control_consumo_elevado = c.getDouble(21);
            consumo_minimo_control = c.getDouble(22);
            muestra_en_atp = c.getString(23);
            muestra_en_admin = c.getString(24);


            RestTipoConsumo match = tipoConsumoMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                tipoConsumoMap.remove(id);

                Uri existingUri = ContratoCorpicoApp.TipoConsumo.crearUriTipoConsumo((int) id);//PASAR LOS ID A INTEGER

                // Comprobar si tipo de cuadrillas necesita ser actualizado
                boolean b = match.getTIC_ID() != id;
                boolean b1 = match.getTIC_DESCRIPCION() != descripcion;
                boolean b2 =  match.getTIC_TIPO_EMPRESA() != tipo_empresa;
                boolean b3 = match.getTIC_ABREVIATURA() != abreviatura;
                boolean b4 = match.getTIC_TIPO_CONSUMO() != tipo_consumo;
                boolean b5 = match.getTIC_TARIFA_CONSUMO() != tarifa_consumo;
                boolean b6 = match.getTIC_TARIFA_RESERVA() != tarifa_reserva;
                boolean b7 = match.getTIC_CARGO_FIJO() != cargo_fijo;
                boolean b8 = match.getTIC_FACTURA_MINIMA() != factura_minima;
                boolean b9 = match.getTIC_TRANSPORTE() != transporte;
                boolean b10 = match.getTIC_IMPUTACION_CONTABLE() != imputacion_contable;
                boolean b11 = match.getTIC_UTILIZA_FACTOR_PRESION() != factor_presion;
                boolean b12 = match.getTIC_UTILIZA_TRANSFORMADOR() != utiliza_transformador;
                boolean b13 = match.getTIC_CARGO_FIJO_DESCUENTO() != cargo_fijo_descuento;
                boolean b14 = match.getTIC_TARIFA_CONSUMO_DESCUENTO() != tarifa_consumo_descuento;
                boolean b15 = match.getTIC_ACTIVO() != activo;
                boolean b16 = match.getTIC_ID_USER() != user_id;
                boolean b17 = match.getTIC_FECHA_UPDATE() != fecha_update;
                boolean b18 = match.getTIC_LOTE_REPLICACION() != lote_replicacion;
                boolean b19 = match.getTIC_SITUACION_IVA() != situacion_iva;
                boolean b20 = match.getTIC_PORCENTAJE_CONTROL_CONSUMO_REDUCIDO() != porcentaje_control_consumo_reducido;
                boolean b21 = match.getTIC_PORCENTAJE_CONTROL_CONSUMO_ELEVADO() != porcentaje_control_consumo_elevado;
                boolean b22 = match.getTIC_CONSUMO_MINIMO_CONTROL() != consumo_minimo_control;
                boolean b23 = match.getTIC_MUESTRA_EN_ATP() != muestra_en_atp;
                boolean b24 = match.getTIC_MUESTRA_EN_ADMIN() != muestra_en_admin;


                if (b || b1 || b2 || b3 || b4  || b5  || b6 || b7 || b8 || b9 || b10 || b11  || b12
                        || b13 || b14 || b15 || b16 || b17 || b18  || b19  || b20 || b21|| b22  || b23 || b24) {

                    Log.i(TAG, "Tipo Consumo - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoCorpicoApp.TipoConsumo.TIC_ID, match.getTIC_ID())
                            .withValue(ContratoCorpicoApp.TipoConsumo.TIC_DESCRIPCION, match.getTIC_DESCRIPCION())
                            .withValue(ContratoCorpicoApp.TipoConsumo.TIC_TIPO_EMPRESA, match.getTIC_TIPO_EMPRESA())
                            .withValue(ContratoCorpicoApp.TipoConsumo.TIC_ABREVIATURA, match.getTIC_ABREVIATURA())
                            .withValue(ContratoCorpicoApp.TipoConsumo.TIC_TIPO_CONSUMO , match.getTIC_TIPO_CONSUMO())
                            .withValue(ContratoCorpicoApp.TipoConsumo.TIC_TARIFA_CONSUMO, match.getTIC_TARIFA_CONSUMO())
                            .withValue(ContratoCorpicoApp.TipoConsumo.TIC_TARIFA_RESERVA, match.getTIC_TARIFA_RESERVA())
                            .withValue(ContratoCorpicoApp.TipoConsumo.TIC_CARGO_FIJO , match.getTIC_CARGO_FIJO())
                            .withValue(ContratoCorpicoApp.TipoConsumo.TIC_FACTURA_MINIMA, match.getTIC_FACTURA_MINIMA())
                            .withValue(ContratoCorpicoApp.TipoConsumo.TIC_TRANSPORTE , match.getTIC_TRANSPORTE())
                            .withValue(ContratoCorpicoApp.TipoConsumo.TIC_IMPUTACION_CONTABLE, match.getTIC_IMPUTACION_CONTABLE())
                            .withValue(ContratoCorpicoApp.TipoConsumo.TIC_UTILIZA_FACTOR_PRESION , match.getTIC_UTILIZA_FACTOR_PRESION())
                            .withValue(ContratoCorpicoApp.TipoConsumo.TIC_UTILIZA_TRANSFORMADOR, match.getTIC_UTILIZA_TRANSFORMADOR())
                            .withValue(ContratoCorpicoApp.TipoConsumo.TIC_CARGO_FIJO_DESCUENTO , match.getTIC_CARGO_FIJO_DESCUENTO())
                            .withValue(ContratoCorpicoApp.TipoConsumo.TIC_TARIFA_CONSUMO_DESCUENTO , match.getTIC_TARIFA_CONSUMO_DESCUENTO())
                            .withValue(ContratoCorpicoApp.TipoConsumo.TIC_ACTIVO , match.getTIC_ACTIVO())
                            .withValue(ContratoCorpicoApp.TipoConsumo.TIC_ID_USER, match.getTIC_ID_USER())
                            .withValue(ContratoCorpicoApp.TipoConsumo.TIC_FECHA_UPDATE, match.getTIC_FECHA_UPDATE())
                            .withValue(ContratoCorpicoApp.TipoConsumo.TIC_LOTE_REPLICACION , match.getTIC_LOTE_REPLICACION())
                            .withValue(ContratoCorpicoApp.TipoConsumo.TIC_SITUACION_IVA , match.getTIC_SITUACION_IVA())
                            .withValue(ContratoCorpicoApp.TipoConsumo.TIC_PORCENTAJE_CONTROL_CONSUMO_REDUCIDO, match.getTIC_PORCENTAJE_CONTROL_CONSUMO_REDUCIDO())
                            .withValue(ContratoCorpicoApp.TipoConsumo.TIC_PORCENTAJE_CONTROL_CONSUMO_ELEVADO, match.getTIC_PORCENTAJE_CONTROL_CONSUMO_ELEVADO())
                            .withValue(ContratoCorpicoApp.TipoConsumo.TIC_CONSUMO_MINIMO_CONTROL, match.getTIC_CONSUMO_MINIMO_CONTROL())
                            .withValue(ContratoCorpicoApp.TipoConsumo.TIC_MUESTRA_EN_ATP, match.getTIC_MUESTRA_EN_ATP())
                            .withValue(ContratoCorpicoApp.TipoConsumo.TIC_MUESTRA_EN_ADMIN, match.getTIC_MUESTRA_EN_ADMIN())
                            .build());
                } else {
                    Log.i(TAG, "Tipo Consumo - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoCorpicoApp.TipoConsumo.crearUriTipoConsumo((int) id);
                Log.i(TAG, "Tipo Consumo - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestTipoConsumo tipoConsumo : tipoConsumoMap.values()){
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.TipoConsumo.URI_CONTENIDO)
                    .withValue(ContratoCorpicoApp.TipoConsumo.TIC_ID, tipoConsumo.getTIC_ID())
                    .withValue(ContratoCorpicoApp.TipoConsumo.TIC_DESCRIPCION, tipoConsumo.getTIC_DESCRIPCION())
                    .withValue(ContratoCorpicoApp.TipoConsumo.TIC_TIPO_EMPRESA, tipoConsumo.getTIC_TIPO_EMPRESA())
                    .withValue(ContratoCorpicoApp.TipoConsumo.TIC_ABREVIATURA, tipoConsumo.getTIC_ABREVIATURA())
                    .withValue(ContratoCorpicoApp.TipoConsumo.TIC_TIPO_CONSUMO , tipoConsumo.getTIC_TIPO_CONSUMO())
                    .withValue(ContratoCorpicoApp.TipoConsumo.TIC_TARIFA_CONSUMO, tipoConsumo.getTIC_TARIFA_CONSUMO())
                    .withValue(ContratoCorpicoApp.TipoConsumo.TIC_TARIFA_RESERVA, tipoConsumo.getTIC_TARIFA_RESERVA())
                    .withValue(ContratoCorpicoApp.TipoConsumo.TIC_CARGO_FIJO , tipoConsumo.getTIC_CARGO_FIJO())
                    .withValue(ContratoCorpicoApp.TipoConsumo.TIC_FACTURA_MINIMA, tipoConsumo.getTIC_FACTURA_MINIMA())
                    .withValue(ContratoCorpicoApp.TipoConsumo.TIC_TRANSPORTE , tipoConsumo.getTIC_TRANSPORTE())
                    .withValue(ContratoCorpicoApp.TipoConsumo.TIC_IMPUTACION_CONTABLE, tipoConsumo.getTIC_IMPUTACION_CONTABLE())
                    .withValue(ContratoCorpicoApp.TipoConsumo.TIC_UTILIZA_FACTOR_PRESION , tipoConsumo.getTIC_UTILIZA_FACTOR_PRESION())
                    .withValue(ContratoCorpicoApp.TipoConsumo.TIC_UTILIZA_TRANSFORMADOR, tipoConsumo.getTIC_UTILIZA_TRANSFORMADOR())
                    .withValue(ContratoCorpicoApp.TipoConsumo.TIC_CARGO_FIJO_DESCUENTO , tipoConsumo.getTIC_CARGO_FIJO_DESCUENTO())
                    .withValue(ContratoCorpicoApp.TipoConsumo.TIC_TARIFA_CONSUMO_DESCUENTO , tipoConsumo.getTIC_TARIFA_CONSUMO_DESCUENTO())
                    .withValue(ContratoCorpicoApp.TipoConsumo.TIC_ACTIVO , tipoConsumo.getTIC_ACTIVO())
                    .withValue(ContratoCorpicoApp.TipoConsumo.TIC_ID_USER, tipoConsumo.getTIC_ID_USER())
                    .withValue(ContratoCorpicoApp.TipoConsumo.TIC_FECHA_UPDATE, tipoConsumo.getTIC_FECHA_UPDATE())
                    .withValue(ContratoCorpicoApp.TipoConsumo.TIC_LOTE_REPLICACION , tipoConsumo.getTIC_LOTE_REPLICACION())
                    .withValue(ContratoCorpicoApp.TipoConsumo.TIC_SITUACION_IVA , tipoConsumo.getTIC_SITUACION_IVA())
                    .withValue(ContratoCorpicoApp.TipoConsumo.TIC_PORCENTAJE_CONTROL_CONSUMO_REDUCIDO, tipoConsumo.getTIC_PORCENTAJE_CONTROL_CONSUMO_REDUCIDO())
                    .withValue(ContratoCorpicoApp.TipoConsumo.TIC_PORCENTAJE_CONTROL_CONSUMO_ELEVADO, tipoConsumo.getTIC_PORCENTAJE_CONTROL_CONSUMO_ELEVADO())
                    .withValue(ContratoCorpicoApp.TipoConsumo.TIC_CONSUMO_MINIMO_CONTROL, tipoConsumo.getTIC_CONSUMO_MINIMO_CONTROL())
                    .withValue(ContratoCorpicoApp.TipoConsumo.TIC_MUESTRA_EN_ATP, tipoConsumo.getTIC_MUESTRA_EN_ATP())
                    .withValue(ContratoCorpicoApp.TipoConsumo.TIC_MUESTRA_EN_ADMIN, tipoConsumo.getTIC_MUESTRA_EN_ADMIN())
                    .build());
        }
        return operations;
    }
    public static class AsyncQueryTipoConsumo extends AsyncQueryHandler {
        private WeakReference<AsyncOpListener> mListener;

        public interface AsyncOpListener {
            void onQueryComplete(int token, Object cookie, Cursor cursor);
        }

        public AsyncQueryTipoConsumo(ContentResolver cr) {
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
