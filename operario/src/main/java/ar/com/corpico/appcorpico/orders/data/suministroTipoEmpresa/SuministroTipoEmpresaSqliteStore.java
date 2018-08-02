package ar.com.corpico.appcorpico.orders.data.suministroTipoEmpresa;

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

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestSuministroTipoEmpresa;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;

/**
 * Created by Administrador on 12/03/2018.
 */

public class SuministroTipoEmpresaSqliteStore implements SuministroTipoEmpresaStore {
    private static final String TAG = SuministroTipoEmpresaSqliteStore.class.getSimpleName();
    private SuministroTipoEmpresaSqliteStore INSTANCE;
    private ContentResolver mContentResolver;


    public SuministroTipoEmpresaSqliteStore(@NonNull ContentResolver mContentResolver) {
        this.mContentResolver = Preconditions.checkNotNull(mContentResolver, "mContentResolver no puede ser null");
    }

    public SuministroTipoEmpresaSqliteStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SuministroTipoEmpresaSqliteStore(mContentResolver);
        }
        return INSTANCE;
    }

    @Override
    public void getSuministroTipoEmpresa(final GetSuministroTipoEmpresaStoreCallBack callback, Criteria filter) {
        AsyncQuerySuministroTipoEmpresa handler = new AsyncQuerySuministroTipoEmpresa(mContentResolver);
        handler.setQueryListener(new  AsyncQuerySuministroTipoEmpresa.AsyncOpListener() {
            @Override
            public void onQueryComplete(int token, Object cookie, Cursor cursor) {
                List<RestSuministroTipoEmpresa> ListSuministroTipoEmpresa = new ArrayList<>();
                if (cursor != null && cursor.getCount()>0) {
                    while(cursor.moveToNext()) {
                        ListSuministroTipoEmpresa.add(new RestSuministroTipoEmpresa(cursor.getShort(0),cursor.getInt(1), cursor.getShort(2), (byte) cursor.getInt(3),
                                cursor.getString(4), (byte) cursor.getInt(5), cursor.getString(6),
                                cursor.getShort(7), cursor.getShort(8), (byte) cursor.getInt(9),
                                cursor.getShort(10), cursor.getShort(11), cursor.getString(12),
                                cursor.getInt(13), cursor.getShort(14), cursor.getShort(15),
                                cursor.getShort(16), cursor.getString(17), cursor.getString(18),
                                cursor.getInt(19)));

                    }
                    callback.onSuccess(ListSuministroTipoEmpresa);
                }else{
                    callback.onError("No existen Suministro Tipo Empresa");
                }
            }
        });
// todo: paso 1 para dejarlo sucio localmente campo sync
        handler.startQuery(0, null, ContratoCorpicoApp.SuministroTipoEmpresa.URI_CONTENIDO, null, null, null, null);
    }
    public List<ContentProviderOperation> replicarServidor(List<RestSuministroTipoEmpresa> suministrosTipoEmpresa) {
        //Crea lista de operaciones content provider
        List<ContentProviderOperation> operations = new ArrayList<>();

        // Tabla hash para recibir los tipos de cuadrillas entrantes
        HashMap<String, RestSuministroTipoEmpresa> suministroTipoEmpresaMap = new HashMap<>();
        String ids;
        for (RestSuministroTipoEmpresa ste : suministrosTipoEmpresa) {
            ids = ste.getSTE_CLIENTE() + "#" + ste.getSTE_SUMINISTRO();
            suministroTipoEmpresaMap.put(ids,ste);

        }

        //Consultar registros remotos actuales
        Uri uri = ContratoCorpicoApp.SuministroTipoEmpresa.URI_CONTENIDO;
        String select = ContratoCorpicoApp.SuministroTipoEmpresa.TABLE_NAME;
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Suministro Tipo Empresa - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        short empresa;
        int cliente;
        short suministro;
        byte tipo_empresa;
        String fecha_ingreso;
        byte estado_ope;
        String fecha_estado_ope;
        short tipo_consumo;
        short tipo_usuario;
        byte tipo_servicio;
        short tipo_conexion;
        short potencia;
        String posee_proyecto;
        int reserva_capacidad;
        short unidad_proveedora;
        short sub_unidad_proveedora;
        short transformador;
        String user_id;
        String fecha_update;
        int lote_replicacion;

        while (c.moveToNext()) {
            empresa = c.getShort(0);
            cliente = c.getInt(1);
            suministro = c.getShort(2);
            tipo_empresa = (byte) c.getInt(3);
            fecha_ingreso = c.getString(4);
            estado_ope = (byte) c.getInt(5);
            fecha_estado_ope = c.getString(6);
            tipo_consumo = c.getShort(7);
            tipo_usuario = c.getShort(8);
            tipo_servicio = (byte) c.getInt(9);
            tipo_conexion = c.getShort(10);
            potencia = c.getShort(11);
            posee_proyecto = c.getString(12);
            reserva_capacidad = c.getInt(13);
            unidad_proveedora = c.getShort(14);
            sub_unidad_proveedora = c.getShort(15);
            transformador = c.getShort(16);
            user_id = c.getString(17);
            fecha_update = c.getString(18);
            lote_replicacion = c.getInt(19);


            ids = cliente + "#" + suministro;
            RestSuministroTipoEmpresa match = suministroTipoEmpresaMap.get(ids);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                suministroTipoEmpresaMap.remove(ids);

                Uri existingUri = ContratoCorpicoApp.SuministroTipoEmpresa.crearUriSuministroTipoEmpresa(cliente,suministro);//PASAR LOS ID A INTEGER

                // Comprobar si tipo de cuadrillas necesita ser actualizado
                boolean b = match.getSTE_EMPRESA() !=empresa;
                boolean b1 = match.getSTE_CLIENTE() !=cliente;
                boolean b2 = match.getSTE_SUMINISTRO() !=suministro;
                boolean b3 = match.getSTE_TIPO_EMPRESA() !=tipo_empresa;
                boolean b4 = match.getSTE_FECHA_INGRESO() !=fecha_ingreso;
                boolean b5 = match.getSTE_ESTADO_OPE() !=estado_ope;
                boolean b6 = match.getSTE_FECHA_ESTADO_OPE() !=fecha_estado_ope;
                boolean b7 = match.getSTE_TIPO_CONSUMO() !=tipo_consumo;
                boolean b8 = match.getSTE_TIPO_USUARIO() !=tipo_usuario;
                boolean b9 = match.getSTE_TIPO_SERVICIO() !=tipo_servicio;
                boolean b10 = match.getSTE_TIPO_CONEXION() !=tipo_conexion;
                boolean b11 = match.getSTE_POTENCIA() !=potencia;
                boolean b12 = match.getSTE_POSEE_PROYECTO() !=posee_proyecto;
                boolean b13 = match.getSTE_RESERVA_CAPACIDAD() !=reserva_capacidad;
                boolean b14 = match.getSTE_UNIDAD_PROVEEDORA() !=unidad_proveedora;
                boolean b15 = match.getSTE_SUB_UNIDAD_PROVEEDORA() !=sub_unidad_proveedora;
                boolean b16 = match.getSTE_TRANSFORMADOR() !=transformador;
                boolean b17 = match.getSTE_ID_USER() !=user_id;
                boolean b18 = match.getSTE_FECHA_UPDATE() !=fecha_update;
                boolean b19 = match.getSTE_LOTE_REPLICACION() !=lote_replicacion;


                if (b || b1 || b2 || b3 || b4  || b5  || b6 || b7 || b8 || b9 || b10 || b11
                || b12 || b13 || b14 || b15  || b16  || b17 || b18 || b19 ) {

                    Log.i(TAG, "Suministro Tipo Empresa - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_EMPRESA, match.getSTE_EMPRESA())
                            .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_CLIENTE , match.getSTE_CLIENTE())
                            .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_SUMINISTRO, match.getSTE_SUMINISTRO())
                            .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_TIPO_EMPRESA, match.getSTE_TIPO_EMPRESA())
                            .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_FECHA_INGRESO, match.getSTE_FECHA_INGRESO())
                            .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_ESTADO_OPE, match.getSTE_ESTADO_OPE())
                            .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_FECHA_ESTADO_OPE, match.getSTE_FECHA_ESTADO_OPE())
                            .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_TIPO_CONSUMO, match.getSTE_TIPO_CONSUMO())
                            .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_TIPO_USUARIO, match.getSTE_TIPO_USUARIO())
                            .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_TIPO_SERVICIO, match.getSTE_TIPO_SERVICIO())
                            .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_TIPO_CONEXION, match.getSTE_TIPO_CONEXION())
                            .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_POTENCIA, match.getSTE_POTENCIA())
                            .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_POSEE_PROYECTO, match.getSTE_POSEE_PROYECTO())
                            .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_RESERVA_CAPACIDAD, match.getSTE_RESERVA_CAPACIDAD())
                            .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_UNIDAD_PROVEEDORA, match.getSTE_UNIDAD_PROVEEDORA())
                            .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_SUB_UNIDAD_PROVEEDORA, match.getSTE_SUB_UNIDAD_PROVEEDORA())
                            .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_TRANSFORMADOR, match.getSTE_TRANSFORMADOR())
                            .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_ID_USER, match.getSTE_ID_USER())
                            .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_FECHA_UPDATE, match.getSTE_FECHA_UPDATE())
                            .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_LOTE_REPLICACION, match.getSTE_LOTE_REPLICACION())
                            .build());
                } else {
                    Log.i(TAG, "Suministro Tipo Empresa - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoCorpicoApp.SuministroTipoEmpresa.crearUriSuministroTipoEmpresa(cliente,suministro);
                Log.i(TAG, "Suministro Tipo Empresa - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestSuministroTipoEmpresa suministroTipoEmpresa : suministroTipoEmpresaMap.values()){
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.SuministroTipoEmpresa.URI_CONTENIDO)
                    .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_EMPRESA, suministroTipoEmpresa.getSTE_EMPRESA())
                    .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_CLIENTE , suministroTipoEmpresa.getSTE_CLIENTE())
                    .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_SUMINISTRO, suministroTipoEmpresa.getSTE_SUMINISTRO())
                    .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_TIPO_EMPRESA, suministroTipoEmpresa.getSTE_TIPO_EMPRESA())
                    .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_FECHA_INGRESO, suministroTipoEmpresa.getSTE_FECHA_INGRESO())
                    .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_ESTADO_OPE, suministroTipoEmpresa.getSTE_ESTADO_OPE())
                    .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_FECHA_ESTADO_OPE, suministroTipoEmpresa.getSTE_FECHA_ESTADO_OPE())
                    .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_TIPO_CONSUMO, suministroTipoEmpresa.getSTE_TIPO_CONSUMO())
                    .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_TIPO_USUARIO, suministroTipoEmpresa.getSTE_TIPO_USUARIO())
                    .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_TIPO_SERVICIO, suministroTipoEmpresa.getSTE_TIPO_SERVICIO())
                    .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_TIPO_CONEXION, suministroTipoEmpresa.getSTE_TIPO_CONEXION())
                    .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_POTENCIA, suministroTipoEmpresa.getSTE_POTENCIA())
                    .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_POSEE_PROYECTO, suministroTipoEmpresa.getSTE_POSEE_PROYECTO())
                    .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_RESERVA_CAPACIDAD, suministroTipoEmpresa.getSTE_RESERVA_CAPACIDAD())
                    .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_UNIDAD_PROVEEDORA, suministroTipoEmpresa.getSTE_UNIDAD_PROVEEDORA())
                    .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_SUB_UNIDAD_PROVEEDORA, suministroTipoEmpresa.getSTE_SUB_UNIDAD_PROVEEDORA())
                    .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_TRANSFORMADOR, suministroTipoEmpresa.getSTE_TRANSFORMADOR())
                    .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_ID_USER, suministroTipoEmpresa.getSTE_ID_USER())
                    .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_FECHA_UPDATE, suministroTipoEmpresa.getSTE_FECHA_UPDATE())
                    .withValue(ContratoCorpicoApp.SuministroTipoEmpresa.STE_LOTE_REPLICACION, suministroTipoEmpresa.getSTE_LOTE_REPLICACION())
                    .build());
        }
        return operations;
    }
    public static class AsyncQuerySuministroTipoEmpresa extends AsyncQueryHandler {
        private WeakReference<AsyncOpListener> mListener;

        public interface AsyncOpListener {
            void onQueryComplete(int token, Object cookie, Cursor cursor);
        }

        public AsyncQuerySuministroTipoEmpresa(ContentResolver cr) {
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
