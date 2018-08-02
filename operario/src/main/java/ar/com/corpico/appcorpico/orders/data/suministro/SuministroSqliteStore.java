package ar.com.corpico.appcorpico.orders.data.suministro;

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

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestSuministro;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;

/**
 * Created by Administrador on 12/03/2018.
 */

public class SuministroSqliteStore implements SuministroStore {
    private static final String TAG = SuministroSqliteStore.class.getSimpleName();
    private SuministroSqliteStore INSTANCE;
    private ContentResolver mContentResolver;


    public SuministroSqliteStore(@NonNull ContentResolver mContentResolver) {
        this.mContentResolver = Preconditions.checkNotNull(mContentResolver, "mContentResolver no puede ser null");
    }

    public SuministroSqliteStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SuministroSqliteStore(mContentResolver);
        }
        return INSTANCE;
    }

    @Override
    public void getSuministro(final GetSuministroStoreCallBack callback, Criteria filter) {
        AsyncQuerySuministro handler = new AsyncQuerySuministro(mContentResolver);
        handler.setQueryListener(new  AsyncQuerySuministro.AsyncOpListener() {
            @Override
            public void onQueryComplete(int token, Object cookie, Cursor cursor) {
                List<RestSuministro> ListSuministro = new ArrayList<>();
                if (cursor != null && cursor.getCount()>0) {
                    while(cursor.moveToNext()) {
                        ListSuministro.add(new RestSuministro(cursor.getShort(0), cursor.getInt(1), cursor.getShort(2), cursor.getShort(3),cursor.getShort(4),
                                cursor.getShort(5), cursor.getInt(6), cursor.getShort(7), cursor.getString(8),
                                cursor.getString(9), cursor.getInt(10), cursor.getString(11), cursor.getString(12), cursor.getString(13),
                                cursor.getString(14), cursor.getString(15), cursor.getString(16), cursor.getString(17),
                                cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getString(21), cursor.getString(22),
                                cursor.getString(23), cursor.getString(24), cursor.getShort(25), cursor.getString(26),
                                cursor.getString(27), cursor.getString(28), cursor.getString(29), (byte) cursor.getInt(30),
                                cursor.getString(31), cursor.getString(32),
                                cursor.getString(33), cursor.getString(34), cursor.getString(35),
                                cursor.getString(36), cursor.getString(37), cursor.getString(38),
                                cursor.getString(39), cursor.getString(40), cursor.getInt(41)));

                    }
                    callback.onSuccess(ListSuministro);
                }else{
                    callback.onError("No existen Suministros");
                }
            }
        });
// todo: paso 1 para dejarlo sucio localmente campo sync
        handler.startQuery(0, null, ContratoCorpicoApp.Suministro.URI_CONTENIDO, null, null, null, null);
    }
    public List<ContentProviderOperation> replicarServidor(List<RestSuministro> suministros) {
        //Crea lista de operaciones content provider
        List<ContentProviderOperation> operations = new ArrayList<>();

        // Tabla hash para recibir los tipos de cuadrillas entrantes
        HashMap<String, RestSuministro> suministroMap = new HashMap<>();
        String ids;
        for (RestSuministro sum : suministros) {
            ids = sum.getSUM_CLIENTE() + "#" + sum.getSUM_ID();
            suministroMap.put(ids,sum);

        }

        //Consultar registros remotos actuales
        Uri uri = ContratoCorpicoApp.Suministro.URI_CONTENIDO;
        String select = ContratoCorpicoApp.Suministro.TABLE_NAME;
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Suministros - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        short empresa;
        int cliente;
        short suministro;
        short sucursal;
        short grupo;
        short ruta;
        int orden_lectura;
        short localidad;
        String codigo_postal;
        String calle;
        int altura;
        String piso;
        String departamento;
        String calle_entre1;
        String calle_entre2;
        String anexo;
        String referencia_municipal;
        String circunscripcion;
        String radio;
        String manzana;
        String quinta;
        String parcela;
        String subparcela;
        String inquilino;
        String fecha_vto_alquiler;
        short ciiu;
        String direccion_postal;
        String garante;
        String morosidad;
        String facturable;
        byte estado_adm;
        String fecha_estado_adm;
        String fecha_cambio_titularidad;
        String tiene_coneptos_puntales;
        String validad_puntual;
        String suministro_anterior1;
        String suministro_anterior2;
        String suministro_anterior3;
        String suministro_anterior4;
        String user_id;
        String fecha_update;
        int lote_replicacion;

        while (c.moveToNext()) {
            empresa = c.getShort(0);
            cliente = c.getInt(1);
            suministro = c.getShort(2);
            sucursal = c.getShort(3);
            grupo = c.getShort(4);
            ruta = c.getShort(5);
            orden_lectura = c.getInt(6);
            localidad = c.getShort(7);
            codigo_postal = c.getString(8);
            calle =c.getString(9);
            altura = c.getInt(10);
            piso = c.getString(11);
            departamento = c.getString(12);
            calle_entre1 = c.getString(13);
            calle_entre2 = c.getString(14);
            anexo = c.getString(15);
            referencia_municipal = c.getString(16);
            circunscripcion = c.getString(17);
            radio = c.getString(18);
            manzana = c.getString(19);
            quinta = c.getString(20);
            parcela = c.getString(21);
            subparcela = c.getString(22);
            inquilino = c.getString(23);
            fecha_vto_alquiler = c.getString(24);
            ciiu = c.getShort(25);
            direccion_postal = c.getString(26);
            garante = c.getString(27);
            morosidad = c.getString(28);
            facturable = c.getString(29);
            estado_adm = (byte) c.getInt(30);
            fecha_estado_adm = c.getString(31);
            fecha_cambio_titularidad = c.getString(32);
            tiene_coneptos_puntales = c.getString(33);
            validad_puntual = c.getString(34);
            suministro_anterior1 = c.getString(35);
            suministro_anterior2 = c.getString(36);
            suministro_anterior3 = c.getString(37);
            suministro_anterior4 = c.getString(38);
            user_id = c.getString(39);
            fecha_update = c.getString(40);
            lote_replicacion = c.getInt(41);


            ids = cliente + "#" + suministro;
            RestSuministro match = suministroMap.get(ids);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                suministroMap.remove(ids);

                Uri existingUri = ContratoCorpicoApp.Suministro.crearUriSuministro(cliente,suministro);//PASAR LOS ID A INTEGER

                // Comprobar si tipo de cuadrillas necesita ser actualizado
                boolean b = match.getSUM_EMPRESA() !=empresa;
                boolean b1 = match.getSUM_CLIENTE() !=cliente;
                boolean b2 = match.getSUM_ID() !=suministro;
                boolean b3 = match.getSUM_SUCURSAL() !=sucursal;
                boolean b4 = match.getSUM_GRUPO() !=grupo;
                boolean b5 = match.getSUM_RUTA() !=ruta;
                boolean b6 = match.getSUM_ORDEN_LECTURA() !=orden_lectura;
                boolean b7 = match.getSUM_LOCALIDAD() !=localidad;
                boolean b8 = match.getSUM_CODIGO_POSTAL() !=codigo_postal;
                boolean b9 = match.getSUM_CALLE() !=calle;
                boolean b10 = match.getSUM_ALTURA() !=altura;
                boolean b11 = match.getSUM_PISO() !=piso;
                boolean b12 = match.getSUM_DEPARTAMENTO() !=departamento;
                boolean b13 = match.getSUM_CALLE_ENTRE1() !=calle_entre1;
                boolean b14 = match.getSUM_CALLE_ENTRE2() !=calle_entre2;
                boolean b15 = match.getSUM_ANEXO() !=anexo;
                boolean b16 = match.getSUM_REFERENCIA_MUNICIPAL() !=referencia_municipal;
                boolean b17 = match.getSUM_CIRCUNSCRIPCION() !=circunscripcion;
                boolean b18 = match.getSUM_RADIO() !=radio;
                boolean b19 = match.getSUM_MANZANA() !=manzana;
                boolean b20 = match.getSUM_QUINTA() !=quinta;
                boolean b21 = match.getSUM_PARCELA() !=parcela;
                boolean b22 = match.getSUM_SUBPARCELA() !=subparcela;
                boolean b23 = match.getSUM_INQUILINO() !=inquilino;
                boolean b24 = match.getSUM_FECHA_VTO_ALQUILER() !=fecha_vto_alquiler;
                boolean b25 = match.getSUM_CIIU() !=ciiu;
                boolean b26 = match.getSUM_DIRECCION_POSTAL() !=direccion_postal;
                boolean b27 = match.getSUM_GARANTE() !=garante;
                boolean b28 = match.getSUM_MOROSIDAD() !=morosidad;
                boolean b29 = match.getSUM_FACTURABLE() !=facturable;
                boolean b30 = match.getSUM_ESTADO_ADM() !=estado_adm;
                boolean b31 = match.getSUM_FECHA_ESTADO_ADM() !=fecha_estado_adm;
                boolean b32 = match.getSUM_FECHA_CAMBIO_TITULARIDAD() !=fecha_cambio_titularidad;
                boolean b33 = match.getSUM_TIENE_CONCEPTOS_PUNTUALES() !=tiene_coneptos_puntales;
                boolean b34 = match.getSUM_VALIDA_PUNTUAL() !=validad_puntual ;
                boolean b35 = match.getSUM_SUMINISTRO_ANTERIOR1() !=suministro_anterior1;
                boolean b36 = match.getSUM_SUMINISTRO_ANTERIOR2() !=suministro_anterior2;
                boolean b37 = match.getSUM_SUMINISTRO_ANTERIOR3() !=suministro_anterior3;
                boolean b38 = match.getSUM_SUMINISTRO_ANTERIOR4() !=suministro_anterior4;
                boolean b39 = match.getSUM_ID_USER() !=user_id;
                boolean b40 = match.getSUM_FECHA_UPDATE() !=fecha_update;
                boolean b41 = match.getSUM_LOTE_REPLICACION() !=lote_replicacion;


                if (b || b1 || b2 || b3 || b4  || b5  || b6 || b7 || b8 || b9 || b10 || b11
                || b12 || b13 || b14 || b15  || b16  || b17 || b18 || b19 || b20 || b21 || b22
                || b23 || b24 || b25 || b26  || b27  || b28 || b29 || b30 || b31 || b32 || b33
                || b34 || b35 || b36 || b37  || b38  || b39 || b40 || b41) {

                    Log.i(TAG, "Suministros - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoCorpicoApp.Suministro.SUM_EMPRESA, match.getSUM_EMPRESA())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_CLIENTE, match.getSUM_CLIENTE())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_ID, match.getSUM_ID())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_SUCURSAL, match.getSUM_SUCURSAL())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_GRUPO, match.getSUM_GRUPO())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_RUTA, match.getSUM_RUTA())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_ORDEN_LECTURA, match.getSUM_ORDEN_LECTURA())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_LOCALIDAD, match.getSUM_LOCALIDAD())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_CODIGO_POSTAL, match.getSUM_CODIGO_POSTAL())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_CALLE, match.getSUM_CALLE())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_ALTURA, match.getSUM_ALTURA())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_PISO, match.getSUM_PISO())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_DEPARTAMENTO, match.getSUM_DEPARTAMENTO())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_CALLE_ENTRE1, match.getSUM_CALLE_ENTRE1())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_CALLE_ENTRE2, match.getSUM_CALLE_ENTRE2())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_ANEXO, match.getSUM_ANEXO())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_REFERENCIA_MUNICIPAL, match.getSUM_REFERENCIA_MUNICIPAL())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_CIRCUNSCRIPCION , match.getSUM_CIRCUNSCRIPCION())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_RADIO , match.getSUM_RADIO())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_MANZANA, match.getSUM_MANZANA())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_QUINTA, match.getSUM_QUINTA())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_PARCELA , match.getSUM_PARCELA())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_SUBPARCELA, match.getSUM_SUBPARCELA())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_INQUILINO , match.getSUM_INQUILINO())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_FECHA_VTO_ALQUILER , match.getSUM_FECHA_VTO_ALQUILER())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_CIIU, match.getSUM_CIIU())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_DIRECCION_POSTAL, match.getSUM_DIRECCION_POSTAL())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_GARANTE , match.getSUM_GARANTE())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_MOROSIDAD , match.getSUM_MOROSIDAD())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_FACTURABLE, match.getSUM_FACTURABLE())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_ESTADO_ADM, match.getSUM_ESTADO_ADM())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_FECHA_ESTADO_ADM, match.getSUM_FECHA_ESTADO_ADM())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_FECHA_CAMBIO_TITULARIDAD, match.getSUM_FECHA_CAMBIO_TITULARIDAD())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_TIENE_CONCEPTOS_PUNTUALES, match.getSUM_TIENE_CONCEPTOS_PUNTUALES())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_VALIDA_PUNTUAL, match.getSUM_VALIDA_PUNTUAL())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_SUMINISTRO_ANTERIOR1, match.getSUM_SUMINISTRO_ANTERIOR1())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_SUMINISTRO_ANTERIOR2, match.getSUM_SUMINISTRO_ANTERIOR2())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_SUMINISTRO_ANTERIOR3, match.getSUM_SUMINISTRO_ANTERIOR3())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_SUMINISTRO_ANTERIOR4, match.getSUM_SUMINISTRO_ANTERIOR4())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_ID_USER, match.getSUM_ID_USER())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_FECHA_UPDATE, match.getSUM_FECHA_UPDATE())
                            .withValue(ContratoCorpicoApp.Suministro.SUM_LOTE_REPLICACION, match.getSUM_LOTE_REPLICACION())
                            .build());
                } else {
                    Log.i(TAG, "Suministros - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoCorpicoApp.Suministro.crearUriSuministro(cliente,suministro);
                Log.i(TAG, "Suministros - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestSuministro sumin : suministroMap.values()){
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.Suministro.URI_CONTENIDO)
                    .withValue(ContratoCorpicoApp.Suministro.SUM_EMPRESA, sumin.getSUM_EMPRESA())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_CLIENTE, sumin.getSUM_CLIENTE())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_ID, sumin.getSUM_ID())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_SUCURSAL, sumin.getSUM_SUCURSAL())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_GRUPO, sumin.getSUM_GRUPO())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_RUTA, sumin.getSUM_RUTA())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_ORDEN_LECTURA, sumin.getSUM_ORDEN_LECTURA())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_LOCALIDAD, sumin.getSUM_LOCALIDAD())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_CODIGO_POSTAL, sumin.getSUM_CODIGO_POSTAL())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_CALLE, sumin.getSUM_CALLE())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_ALTURA, sumin.getSUM_ALTURA())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_PISO, sumin.getSUM_PISO())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_DEPARTAMENTO, sumin.getSUM_DEPARTAMENTO())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_CALLE_ENTRE1, sumin.getSUM_CALLE_ENTRE1())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_CALLE_ENTRE2, sumin.getSUM_CALLE_ENTRE2())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_ANEXO, sumin.getSUM_ANEXO())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_REFERENCIA_MUNICIPAL, sumin.getSUM_REFERENCIA_MUNICIPAL())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_CIRCUNSCRIPCION , sumin.getSUM_CIRCUNSCRIPCION())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_RADIO , sumin.getSUM_RADIO())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_MANZANA, sumin.getSUM_MANZANA())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_QUINTA, sumin.getSUM_QUINTA())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_PARCELA , sumin.getSUM_PARCELA())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_SUBPARCELA, sumin.getSUM_SUBPARCELA())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_INQUILINO , sumin.getSUM_INQUILINO())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_FECHA_VTO_ALQUILER , sumin.getSUM_FECHA_VTO_ALQUILER())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_CIIU, sumin.getSUM_CIIU())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_DIRECCION_POSTAL, sumin.getSUM_DIRECCION_POSTAL())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_GARANTE , sumin.getSUM_GARANTE())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_MOROSIDAD , sumin.getSUM_MOROSIDAD())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_FACTURABLE, sumin.getSUM_FACTURABLE())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_ESTADO_ADM, sumin.getSUM_ESTADO_ADM())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_FECHA_ESTADO_ADM, sumin.getSUM_FECHA_ESTADO_ADM())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_FECHA_CAMBIO_TITULARIDAD, sumin.getSUM_FECHA_CAMBIO_TITULARIDAD())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_TIENE_CONCEPTOS_PUNTUALES, sumin.getSUM_TIENE_CONCEPTOS_PUNTUALES())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_VALIDA_PUNTUAL, sumin.getSUM_VALIDA_PUNTUAL())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_SUMINISTRO_ANTERIOR1, sumin.getSUM_SUMINISTRO_ANTERIOR1())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_SUMINISTRO_ANTERIOR2, sumin.getSUM_SUMINISTRO_ANTERIOR2())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_SUMINISTRO_ANTERIOR3, sumin.getSUM_SUMINISTRO_ANTERIOR3())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_SUMINISTRO_ANTERIOR4, sumin.getSUM_SUMINISTRO_ANTERIOR4())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_ID_USER, sumin.getSUM_ID_USER())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_FECHA_UPDATE, sumin.getSUM_FECHA_UPDATE())
                    .withValue(ContratoCorpicoApp.Suministro.SUM_LOTE_REPLICACION, sumin.getSUM_LOTE_REPLICACION())
                    .build());
        }
        return operations;
    }
    public static class AsyncQuerySuministro extends AsyncQueryHandler {
        private WeakReference<AsyncOpListener> mListener;

        public interface AsyncOpListener {
            void onQueryComplete(int token, Object cookie, Cursor cursor);
        }

        public AsyncQuerySuministro(ContentResolver cr) {
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
