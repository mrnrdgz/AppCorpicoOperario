package ar.com.corpico.appcorpico.orders.data.tipoEmpresa;

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

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoEmpresa;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;

/**
 * Created by Administrador on 12/03/2018.
 */

public class TipoEmpresaSqliteStore implements TipoEmpresaStore {
    private static final String TAG = TipoEmpresaSqliteStore.class.getSimpleName();
    private TipoEmpresaSqliteStore INSTANCE;
    private ContentResolver mContentResolver;


    public TipoEmpresaSqliteStore(@NonNull ContentResolver mContentResolver) {
        this.mContentResolver = Preconditions.checkNotNull(mContentResolver, "mContentResolver no puede ser null");
    }

    public TipoEmpresaSqliteStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TipoEmpresaSqliteStore(mContentResolver);
        }
        return INSTANCE;
    }

    @Override
    public void getTipoEmpresa(final GetTipoEmpresaStoreCallBack callback, Criteria filter) {
        AsyncQueryTipoEmpresa handler = new AsyncQueryTipoEmpresa(mContentResolver);
        handler.setQueryListener(new  AsyncQueryTipoEmpresa.AsyncOpListener() {
            @Override
            public void onQueryComplete(int token, Object cookie, Cursor cursor) {
                List<RestTipoEmpresa> ListTipoEmpresa= new ArrayList<>();
                if (cursor != null && cursor.getCount()>0) {
                    while(cursor.moveToNext()) {
                        ListTipoEmpresa.add(new RestTipoEmpresa((byte) cursor.getInt(0),cursor.getString(1),cursor.getString(2), cursor.getString(3),
                                cursor.getString(4), cursor.getDouble(5), cursor.getShort(6), cursor.getString(7),
                                cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11),
                                cursor.getString(12), cursor.getInt(13), cursor.getString(14), cursor.getString(15),
                                cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getShort(19),
                                cursor.getString(20), cursor.getShort(21), (byte) cursor.getInt(22)));

                    }
                    callback.onSuccess(ListTipoEmpresa);
                }else{
                    callback.onError("No existen Tipo Empresa");
                }
            }
        });
// todo: paso 1 para dejarlo sucio localmente campo sync
        handler.startQuery(0, null, ContratoCorpicoApp.TipoEmpresa.URI_CONTENIDO, null, null, null, null);
    }
    public List<ContentProviderOperation> replicarServidor(List<RestTipoEmpresa> tiposEmpresas) {
        //Crea lista de operaciones content provider
        List<ContentProviderOperation> operations = new ArrayList<>();

        // Tabla hash para recibir los tipos de cuadrillas entrantes
        HashMap<Integer, RestTipoEmpresa> tipoEmpresaMap = new HashMap<>();
        for (RestTipoEmpresa tie : tiposEmpresas) {
            tipoEmpresaMap.put((int) tie.getTIE_ID(), tie);
        }

        //Consultar registros remotos actuales
        Uri uri = ContratoCorpicoApp.TipoEmpresa.URI_CONTENIDO;
        String select = ContratoCorpicoApp.TipoEmpresa.TABLE_NAME;
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Tipo Empresa - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        byte id;
        String descripcion;
        String abreviatura;
        String abreviatura_atp;
        String muestra_consumo_convertido;
        double factor_conversion_consumo;
        short orden;
        String utiliza_modulo_lectura;
        String usa_transformador;
        String baja_permite_cambiar_estado_adm;
        String activo;
        String user_id;
        String fecha_update;
        int lote_replicacion;
        String modo_calculo_tarifa_consumo;
        String ajusta_cargo_fijo_alta_suministro;
        String ajusta_escala_consumo_alta_suministro;
        String ajusta_cargo_fijo_baja_suministro;
        String ajusta_escala_consumo_baja_suministro;
        short medidor_funcion;
        String color;
        short empresa_cobranza;
        byte estado_ope_baja;

        while (c.moveToNext()) {
            id = (byte) c.getInt(0);
            descripcion = c.getString(1);
            abreviatura = c.getString(2);
            abreviatura_atp = c.getString(3);
            muestra_consumo_convertido = c.getString(4);
            factor_conversion_consumo = c.getDouble(5);
            orden = c.getShort(6);
            utiliza_modulo_lectura = c.getString(7);
            usa_transformador = c.getString(8);
            baja_permite_cambiar_estado_adm = c.getString(9);
            activo = c.getString(10);
            user_id = c.getString(11);
            fecha_update = c.getString(12);
            lote_replicacion = c.getInt(13);
            modo_calculo_tarifa_consumo = c.getString(14);
            ajusta_cargo_fijo_alta_suministro = c.getString(15);
            ajusta_escala_consumo_alta_suministro = c.getString(16);
            ajusta_cargo_fijo_baja_suministro = c.getString(17);
            ajusta_escala_consumo_baja_suministro = c.getString(18);
            medidor_funcion = c.getShort(19);
            color = c.getString(20);
            empresa_cobranza = c.getShort(21);
            estado_ope_baja =(byte) c.getInt(22);


            RestTipoEmpresa match = tipoEmpresaMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                tipoEmpresaMap.remove(id);

                Uri existingUri = ContratoCorpicoApp.TipoEmpresa.crearUriTipoEmpresa((int) id);//PASAR LOS ID A INTEGER

                // Comprobar si tipo de cuadrillas necesita ser actualizado
                boolean b = match.getTIE_ID() != id;
                boolean b1 = match.getTIE_DESCRIPCION() != descripcion;
                boolean b2 =  match.getTIE_ABREVIATURA() != abreviatura;
                boolean b3 = match.getTIE_ABREVIATURA_ATP() != abreviatura_atp;
                boolean b4 = match.getTIE_MUESTRA_CONSUMO_CONVERTIDO() != muestra_consumo_convertido;
                boolean b5 = match.getTIE_FACTOR_CONVERSION_CONSUMO() != factor_conversion_consumo;
                boolean b6 = match.getTIE_ORDEN() != orden;
                boolean b7 = match.getTIE_UTILIZA_MODULO_LECTURAS() != utiliza_modulo_lectura;
                boolean b8 = match.getTIE_USA_TRANSFORMADOR() != usa_transformador;
                boolean b9 = match.getTIE_BAJA_PERMITE_CAMBIAR_EST_ADM() != baja_permite_cambiar_estado_adm;
                boolean b10 = match.getTIE_ACTIVO() != activo;
                boolean b11 = match.getTIE_ID_USER() != user_id;
                boolean b12 = match.getTIE_FECHA_UPDATE() != fecha_update;
                boolean b13 = match.getTIE_LOTE_REPLICACION() != lote_replicacion;
                boolean b14 = match.getTIE_MODO_CALCULO_TARIFA_CONSUMO() != modo_calculo_tarifa_consumo;
                boolean b15 = match.getTIE_AJUSTA_CARGO_FIJO_ALTA_SUMINISTRO() != ajusta_cargo_fijo_alta_suministro;
                boolean b16 = match.getTIE_AJUSTA_ESCALA_CONSUMO_ALTA_SUMINISTRO() != ajusta_escala_consumo_alta_suministro;
                boolean b17 = match.getTIE_AJUSTA_CARGO_FIJO_BAJA_SUMINISTRO() != ajusta_cargo_fijo_baja_suministro;
                boolean b18 = match.getTIE_AJUSTA_ESCALA_CONSUMO_BAJA_SUMINISTRO() != ajusta_escala_consumo_baja_suministro;
                boolean b19 = match.getTIE_MEDIDOR_FUNCION() != medidor_funcion;
                boolean b20 = match.getTIE_COLOR() != color;
                boolean b21 = match.getTIE_EMPRESA_COBRANZA() != empresa_cobranza;
                boolean b22 = match.getTIE_ESTADO_OPE_BAJA() != estado_ope_baja;


                if (b || b1 || b2 || b3 || b4  || b5  || b6 || b7 || b8 || b9 || b10 || b11  || b12
                        || b13 || b14 || b15 || b16 || b17 || b18  || b19  || b20 || b21|| b22 ) {

                    Log.i(TAG, "Tipo Empresa - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_ID, match.getTIE_ID())
                            .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_DESCRIPCION, match.getTIE_DESCRIPCION())
                            .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_ABREVIATURA , match.getTIE_ABREVIATURA())
                            .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_ABREVIATURA_ATP , match.getTIE_ABREVIATURA_ATP())
                            .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_MUESTRA_CONSUMO_CONVERTIDO, match.getTIE_MUESTRA_CONSUMO_CONVERTIDO())
                            .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_FACTOR_CONVERSION_CONSUMO , match.getTIE_FACTOR_CONVERSION_CONSUMO())
                            .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_ORDEN, match.getTIE_ORDEN())
                            .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_UTILIZA_MODULO_LECTURAS, match.getTIE_UTILIZA_MODULO_LECTURAS())
                            .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_USA_TRANSFORMADOR, match.getTIE_USA_TRANSFORMADOR())
                            .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_BAJA_PERMITE_CAMBIAR_EST_ADM , match.getTIE_BAJA_PERMITE_CAMBIAR_EST_ADM())
                            .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_ACTIVO , match.getTIE_ACTIVO())
                            .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_ID_USER , match.getTIE_ID_USER())
                            .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_FECHA_UPDATE, match.getTIE_FECHA_UPDATE())
                            .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_LOTE_REPLICACION , match.getTIE_LOTE_REPLICACION())
                            .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_MODO_CALCULO_TARIFA_CONSUMO, match.getTIE_MODO_CALCULO_TARIFA_CONSUMO())
                            .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_AJUSTA_CARGO_FIJO_ALTA_SUMINISTRO , match.getTIE_AJUSTA_CARGO_FIJO_ALTA_SUMINISTRO())
                            .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_AJUSTA_ESCALA_CONSUMO_ALTA_SUMINISTRO , match.getTIE_AJUSTA_ESCALA_CONSUMO_ALTA_SUMINISTRO())
                            .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_AJUSTA_CARGO_FIJO_BAJA_SUMINISTRO , match.getTIE_AJUSTA_CARGO_FIJO_BAJA_SUMINISTRO())
                            .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_AJUSTA_ESCALA_CONSUMO_BAJA_SUMINISTRO , match.getTIE_AJUSTA_ESCALA_CONSUMO_BAJA_SUMINISTRO())
                            .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_MEDIDOR_FUNCION , match.getTIE_MEDIDOR_FUNCION())
                            .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_COLOR , match.getTIE_COLOR())
                            .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_EMPRESA_COBRANZA, match.getTIE_EMPRESA_COBRANZA())
                            .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_ESTADO_OPE_BAJA , match.getTIE_ESTADO_OPE_BAJA())
                            .build());
                } else {
                    Log.i(TAG, "Tipo Empresa - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoCorpicoApp.TipoEmpresa.crearUriTipoEmpresa((int) id);
                Log.i(TAG, "Tipo Empresa - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestTipoEmpresa tipoEmpresa : tipoEmpresaMap.values()){
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.TipoEmpresa.URI_CONTENIDO)
                    .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_ID, tipoEmpresa.getTIE_ID())
                    .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_DESCRIPCION, tipoEmpresa.getTIE_DESCRIPCION())
                    .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_ABREVIATURA , tipoEmpresa.getTIE_ABREVIATURA())
                    .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_ABREVIATURA_ATP , tipoEmpresa.getTIE_ABREVIATURA_ATP())
                    .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_MUESTRA_CONSUMO_CONVERTIDO, tipoEmpresa.getTIE_MUESTRA_CONSUMO_CONVERTIDO())
                    .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_FACTOR_CONVERSION_CONSUMO , tipoEmpresa.getTIE_FACTOR_CONVERSION_CONSUMO())
                    .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_ORDEN, tipoEmpresa.getTIE_ORDEN())
                    .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_UTILIZA_MODULO_LECTURAS, tipoEmpresa.getTIE_UTILIZA_MODULO_LECTURAS())
                    .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_USA_TRANSFORMADOR, tipoEmpresa.getTIE_USA_TRANSFORMADOR())
                    .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_BAJA_PERMITE_CAMBIAR_EST_ADM , tipoEmpresa.getTIE_BAJA_PERMITE_CAMBIAR_EST_ADM())
                    .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_ACTIVO , tipoEmpresa.getTIE_ACTIVO())
                    .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_ID_USER , tipoEmpresa.getTIE_ID_USER())
                    .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_FECHA_UPDATE, tipoEmpresa.getTIE_FECHA_UPDATE())
                    .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_LOTE_REPLICACION , tipoEmpresa.getTIE_LOTE_REPLICACION())
                    .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_MODO_CALCULO_TARIFA_CONSUMO, tipoEmpresa.getTIE_MODO_CALCULO_TARIFA_CONSUMO())
                    .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_AJUSTA_CARGO_FIJO_ALTA_SUMINISTRO , tipoEmpresa.getTIE_AJUSTA_CARGO_FIJO_ALTA_SUMINISTRO())
                    .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_AJUSTA_ESCALA_CONSUMO_ALTA_SUMINISTRO , tipoEmpresa.getTIE_AJUSTA_ESCALA_CONSUMO_ALTA_SUMINISTRO())
                    .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_AJUSTA_CARGO_FIJO_BAJA_SUMINISTRO , tipoEmpresa.getTIE_AJUSTA_CARGO_FIJO_BAJA_SUMINISTRO())
                    .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_AJUSTA_ESCALA_CONSUMO_BAJA_SUMINISTRO , tipoEmpresa.getTIE_AJUSTA_ESCALA_CONSUMO_BAJA_SUMINISTRO())
                    .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_MEDIDOR_FUNCION , tipoEmpresa.getTIE_MEDIDOR_FUNCION())
                    .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_COLOR , tipoEmpresa.getTIE_COLOR())
                    .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_EMPRESA_COBRANZA, tipoEmpresa.getTIE_EMPRESA_COBRANZA())
                    .withValue(ContratoCorpicoApp.TipoEmpresa.TIE_ESTADO_OPE_BAJA , tipoEmpresa.getTIE_ESTADO_OPE_BAJA())
                    .build());
        }
        return operations;
    }
    public static class AsyncQueryTipoEmpresa extends AsyncQueryHandler {
        private WeakReference<AsyncOpListener> mListener;

        public interface AsyncOpListener {
            void onQueryComplete(int token, Object cookie, Cursor cursor);
        }

        public AsyncQueryTipoEmpresa(ContentResolver cr) {
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
