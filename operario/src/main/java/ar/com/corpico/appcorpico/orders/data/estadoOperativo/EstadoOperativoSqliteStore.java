package ar.com.corpico.appcorpico.orders.data.estadoOperativo;

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

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestEstadoOperativo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;

/**
 * Created by Administrador on 12/03/2018.
 */

public class EstadoOperativoSqliteStore implements EstadoOperativoStore {
    private static final String TAG = EstadoOperativoSqliteStore.class.getSimpleName();
    private EstadoOperativoSqliteStore INSTANCE;
    private ContentResolver mContentResolver;


    public EstadoOperativoSqliteStore(@NonNull ContentResolver mContentResolver) {
        this.mContentResolver = Preconditions.checkNotNull(mContentResolver, "mContentResolver no puede ser null");
    }

    public EstadoOperativoSqliteStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EstadoOperativoSqliteStore(mContentResolver);
        }
        return INSTANCE;
    }

    @Override
    public void getEstadoOperativo(final GetEstadoOperativoStoreCallBack callback, Criteria filter) {
        AsyncQueryEtapas handler = new AsyncQueryEtapas(mContentResolver);
        handler.setQueryListener(new  AsyncQueryEtapas.AsyncOpListener() {
            @Override
            public void onQueryComplete(int token, Object cookie, Cursor cursor) {
                List<RestEstadoOperativo> ListEstadoOperativo = new ArrayList<>();
                if (cursor != null && cursor.getCount()>0) {
                    while(cursor.moveToNext()) {
                        ListEstadoOperativo.add(new RestEstadoOperativo((byte) cursor.getInt(0), cursor.getString(1),  cursor.getString(2), (byte) cursor.getInt(3),
                                cursor.getString(4), cursor.getString(5), cursor.getString(6),
                                cursor.getString(7), cursor.getString(8), cursor.getString(9),
                                cursor.getString(10), cursor.getString(11),cursor.getString(12),
                                cursor.getString(13), cursor.getString(14), cursor.getString(15),
                                cursor.getString(16), cursor.getString(17),
                                cursor.getString(18), cursor.getString(19), cursor.getString(20),
                                cursor.getString(21), cursor.getString(22),
                                cursor.getString(23), cursor.getString(24), cursor.getInt(25)));

                    }
                    callback.onSuccess(ListEstadoOperativo);
                }else{
                    callback.onError("No existen Estados Operativo");
                }
            }
        });
// todo: paso 1 para dejarlo sucio localmente campo sync
        handler.startQuery(0, null, ContratoCorpicoApp.Etapas.URI_CONTENIDO, null, null, null, null);
    }
    public List<ContentProviderOperation> replicarServidor(List<RestEstadoOperativo> estadoOperativos) {
        //Crea lista de operaciones content provider
        List<ContentProviderOperation> operations = new ArrayList<>();

        // Tabla hash para recibir los tipos de cuadrillas entrantes
        HashMap<Integer, RestEstadoOperativo> estadoOperativoMap = new HashMap<>();
        for (RestEstadoOperativo eo : estadoOperativos) {
            estadoOperativoMap.put((int) eo.getEOP_ID(), eo);
        }

        //Consultar registros remotos actuales
        Uri uri = ContratoCorpicoApp.EstadoOperativo.URI_CONTENIDO;
        String select = ContratoCorpicoApp.EstadoOperativo.TABLE_NAME;
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Estados Operativo - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        byte id;
        String descripcion;
        String abreviatura;
        byte tipo_empresa;
        String sale_a_leer;
        String es_facturable;
        String camb_titularidad;
        String camb_tarifa;
        String camb_direccion_postal;
        String camb_dat_impositivos;
        String baja_deb_automatioo;
        String reclamo;
        String orden_trabajo;
        String baja_suministro;
        String cobranza;
        String incluya_aviso_deuda;
        String posee_medidor;
        String accion_si_no_posee_medicion;
        String permite_re_solicitar_servicio;
        String color;
        String estado_conexion;
        String cliente;
        String activo;
        String user_id;
        String fecha_update;
        int lote_replicacion;

        while (c.moveToNext()) {

            id = (byte) c.getInt(0);
            descripcion = c.getString(1);
            abreviatura = c.getString(2);
            tipo_empresa = (byte) c.getInt(3);
            sale_a_leer = c.getString(4);
            es_facturable = c.getString(5);
            camb_titularidad = c.getString(6);
            camb_tarifa = c.getString(7);
            camb_direccion_postal = c.getString(8);
            camb_dat_impositivos = c.getString(9);
            baja_deb_automatioo = c.getString(10);
            reclamo = c.getString(11);
            orden_trabajo = c.getString(12);
            baja_suministro = c.getString(13);
            cobranza = c.getString(14);
            incluya_aviso_deuda = c.getString(15);
            posee_medidor = c.getString(16);
            accion_si_no_posee_medicion = c.getString(17);
            permite_re_solicitar_servicio = c.getString(18);
            color = c.getString(19);
            estado_conexion = c.getString(20);
            cliente = c.getString(21);
            activo = c.getString(22);
            user_id = c.getString(23);
            fecha_update = c.getString(24);
            lote_replicacion = c.getInt(25);


            RestEstadoOperativo match = estadoOperativoMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                estadoOperativoMap.remove(id);

                Uri existingUri = ContratoCorpicoApp.EstadoOperativo.crearUriEstadoOperativo((int) id);//PASAR LOS ID A INTEGER

                // Comprobar si tipo de cuadrillas necesita ser actualizado
                boolean b = match.getEOP_ID() != id;
                boolean b1 = match.getEOP_DESCRIPCION() != descripcion;
                boolean b2 =  match.getEOP_ABREVIATURA() != abreviatura;
                boolean b3 = match.getEOP_TIPO_EMPRESA() != tipo_empresa;
                boolean b4 = match.getEOP_SALE_A_LEER() != sale_a_leer;
                boolean b5 = match.getEOP_ES_FACTURABLE() != es_facturable;
                boolean b6 = match.getEOP_CAMB_TITULARIDAD() != camb_titularidad;
                boolean b7 = match.getEOP_CAMB_TARIFA() != camb_tarifa;
                boolean b8 = match.getEOP_CAMB_DIR_POSTAL() != camb_direccion_postal;
                boolean b9 = match.getEOP_CAMB_DAT_IMPOSITIVOS() != camb_dat_impositivos;
                boolean b10 = match.getEOP_BAJA_DEB_AUTOMATIVO() != baja_deb_automatioo;
                boolean b11 = match.getEOP_RECLAMOS() != reclamo;
                boolean b12 = match.getEOP_ORDEN_TRABAJO() != orden_trabajo;
                boolean b13 = match.getEOP_BAJA_SUMINISTRO() != baja_suministro;
                boolean b14 = match.getEOP_COBRANZA() != cobranza;
                boolean b15 = match.getEOP_INCLUYE_AVISO_DEUDA() != incluya_aviso_deuda;
                boolean b16 = match.getEOP_POSEE_MEDIDOR() != posee_medidor;
                boolean b17 = match.getEOP_ACCION_SI_NO_POSEE_MED() != accion_si_no_posee_medicion;
                boolean b18 = match.getEOP_PERMITE_RE_SOLICITAR_SERVICIO() != permite_re_solicitar_servicio;
                boolean b19 = match.getEOP_COLOR() != color;
                boolean b20 = match.getEOP_ESTADO_CONEXION() != estado_conexion;
                boolean b21 = match.getEOP_CLIENTE() != cliente;
                boolean b22 = match.getEOP_ACTIVO() != activo;
                boolean b23 = match.getEOP_ID_USER() != user_id;
                boolean b24 = match.getEOP_FECHA_UPDATE() != fecha_update;
                boolean b25 = match.getEOP_LOTE_REPLICACION() != lote_replicacion;


                if (b || b1 || b2 || b3 || b4  || b5  || b6  || b7  || b8 || b9 || b10 || b11 || b12 || b13
                 || b14 || b15 || b16 || b17 || b18 || b19 || b20 || b21 || b22 || b23 || b24 || b25) {

                    Log.i(TAG, "Estados Operativo - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_ID, match.getEOP_ID())
                            .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_DESCRIPCION, match.getEOP_DESCRIPCION())
                            .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_ABREVIATURA, match.getEOP_ABREVIATURA())
                            .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_TIPO_EMPRESA,match.getEOP_TIPO_EMPRESA())
                            .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_SALE_A_LEER, match.getEOP_SALE_A_LEER())
                            .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_ES_FACTURABLE, match.getEOP_ES_FACTURABLE())
                            .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_CAMB_TITULARIDAD, match.getEOP_CAMB_TITULARIDAD())
                            .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_CAMB_TARIFA, match.getEOP_CAMB_TARIFA())
                            .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_CAMB_DIR_POSTAL, match.getEOP_CAMB_DIR_POSTAL())
                            .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_CAMB_DAT_IMPOSITIVOS,match.getEOP_CAMB_DAT_IMPOSITIVOS())
                            .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_BAJA_DEB_AUTOMATIVO, match.getEOP_BAJA_DEB_AUTOMATIVO())
                            .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_RECLAMOS, match.getEOP_RECLAMOS())
                            .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_ORDEN_TRABAJO,match.getEOP_ORDEN_TRABAJO())
                            .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_BAJA_SUMINISTRO, match.getEOP_BAJA_SUMINISTRO())
                            .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_COBRANZA, match.getEOP_COBRANZA())
                            .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_INCLUYE_AVISO_DEUDA, match.getEOP_INCLUYE_AVISO_DEUDA())
                            .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_POSEE_MEDIDOR, match.getEOP_POSEE_MEDIDOR())
                            .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_ACCION_SI_NO_POSEE_MED, match.getEOP_ACCION_SI_NO_POSEE_MED())
                            .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_PERMITE_RE_SOLICITAR_SERVICIO, match.getEOP_PERMITE_RE_SOLICITAR_SERVICIO())
                            .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_COLOR, match.getEOP_COLOR())
                            .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_ESTADO_CONEXION, match.getEOP_ESTADO_CONEXION())
                            .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_CLIENTE, match.getEOP_CLIENTE())
                            .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_ACTIVO, match.getEOP_ACTIVO())
                            .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_ID_USER, match.getEOP_ID_USER())
                            .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_FECHA_UPDATE, match.getEOP_FECHA_UPDATE())
                            .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_LOTE_REPLICACION, match.getEOP_LOTE_REPLICACION())
                            .build());
                } else {
                    Log.i(TAG, "Estados Operativo - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoCorpicoApp.EstadoOperativo.crearUriEstadoOperativo((int) id);
                Log.i(TAG, "Estados Operativo - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestEstadoOperativo estadoOperativo : estadoOperativoMap.values()){
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.EstadoOperativo.URI_CONTENIDO)
                    .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_ID, estadoOperativo.getEOP_ID())
                    .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_DESCRIPCION, estadoOperativo.getEOP_DESCRIPCION())
                    .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_ABREVIATURA, estadoOperativo.getEOP_ABREVIATURA())
                    .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_TIPO_EMPRESA, estadoOperativo.getEOP_TIPO_EMPRESA())
                    .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_SALE_A_LEER, estadoOperativo.getEOP_SALE_A_LEER())
                    .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_ES_FACTURABLE, estadoOperativo.getEOP_ES_FACTURABLE())
                    .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_CAMB_TITULARIDAD, estadoOperativo.getEOP_CAMB_TITULARIDAD())
                    .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_CAMB_TARIFA, estadoOperativo.getEOP_CAMB_TARIFA())
                    .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_CAMB_DIR_POSTAL, estadoOperativo.getEOP_CAMB_DIR_POSTAL())
                    .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_CAMB_DAT_IMPOSITIVOS, estadoOperativo.getEOP_CAMB_DAT_IMPOSITIVOS())
                    .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_BAJA_DEB_AUTOMATIVO, estadoOperativo.getEOP_BAJA_DEB_AUTOMATIVO())
                    .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_RECLAMOS, estadoOperativo.getEOP_RECLAMOS())
                    .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_ORDEN_TRABAJO, estadoOperativo.getEOP_ORDEN_TRABAJO())
                    .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_BAJA_SUMINISTRO, estadoOperativo.getEOP_BAJA_SUMINISTRO())
                    .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_COBRANZA, estadoOperativo.getEOP_COBRANZA())
                    .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_INCLUYE_AVISO_DEUDA, estadoOperativo.getEOP_INCLUYE_AVISO_DEUDA())
                    .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_POSEE_MEDIDOR, estadoOperativo.getEOP_POSEE_MEDIDOR())
                    .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_ACCION_SI_NO_POSEE_MED, estadoOperativo.getEOP_ACCION_SI_NO_POSEE_MED())
                    .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_PERMITE_RE_SOLICITAR_SERVICIO, estadoOperativo.getEOP_PERMITE_RE_SOLICITAR_SERVICIO())
                    .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_COLOR, estadoOperativo.getEOP_COLOR())
                    .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_ESTADO_CONEXION, estadoOperativo.getEOP_ESTADO_CONEXION())
                    .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_CLIENTE, estadoOperativo.getEOP_CLIENTE())
                    .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_ACTIVO, estadoOperativo.getEOP_ACTIVO())
                    .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_ID_USER, estadoOperativo.getEOP_ID_USER())
                    .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_FECHA_UPDATE, estadoOperativo.getEOP_FECHA_UPDATE())
                    .withValue(ContratoCorpicoApp.EstadoOperativo.EOP_LOTE_REPLICACION, estadoOperativo.getEOP_LOTE_REPLICACION())
                    .build());
        }
        return operations;
    }
    public static class AsyncQueryEtapas extends AsyncQueryHandler {
        private WeakReference<AsyncOpListener> mListener;

        public interface AsyncOpListener {
            void onQueryComplete(int token, Object cookie, Cursor cursor);
        }

        public AsyncQueryEtapas(ContentResolver cr) {
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
