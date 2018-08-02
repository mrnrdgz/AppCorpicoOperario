package ar.com.corpico.appcorpico.orders.data.cliente;

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

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestCliente;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;

/**
 * Created by Administrador on 12/03/2018.
 */

public class ClienteSqliteStore implements ClienteStore {
    private static final String TAG = ClienteSqliteStore.class.getSimpleName();
    private ClienteSqliteStore INSTANCE;
    private ContentResolver mContentResolver;


    public ClienteSqliteStore(@NonNull ContentResolver mContentResolver) {
        this.mContentResolver = Preconditions.checkNotNull(mContentResolver, "mContentResolver no puede ser null");
    }

    public ClienteSqliteStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClienteSqliteStore(mContentResolver);
        }
        return INSTANCE;
    }

    @Override
    public void getCliente(final GetClienteStoreCallBack callback, Criteria filter) {
        AsyncQueryCliente handler = new AsyncQueryCliente(mContentResolver);
        handler.setQueryListener(new  AsyncQueryCliente.AsyncOpListener() {
            @Override
            public void onQueryComplete(int token, Object cookie, Cursor cursor) {
                List<RestCliente> ListCliente = new ArrayList<>();
                if (cursor != null && cursor.getCount()>0) {
                    while(cursor.moveToNext()) {
                        ListCliente.add(new RestCliente(cursor.getShort(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3),
                                cursor.getShort(4), cursor.getString(5), cursor.getString(6), cursor.getInt(7),
                                cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11),
                                cursor.getString(12), cursor.getString(13), cursor.getShort(14), cursor.getString(15),
                                cursor.getString(16), cursor.getInt(17), cursor.getString(18), cursor.getString(19),
                                cursor.getString(20), cursor.getString(21), (byte)cursor.getInt(22), cursor.getString(23),
                                (byte)cursor.getInt(24), cursor.getString(25), cursor.getDouble(26), cursor.getDouble(27),
                                cursor.getDouble(28), cursor.getDouble(29), cursor.getString(30), cursor.getShort(31),
                                cursor.getShort(32),cursor.getShort(33), cursor.getString(34), cursor.getString(35),
                                cursor.getString(36), cursor.getString(37), (byte) cursor.getInt(38), cursor.getString(39),
                                cursor.getString(40), cursor.getString(41), cursor.getInt(42) ));

                    }
                    callback.onSuccess(ListCliente);
                }else{
                    callback.onError("No existen Clientes");
                }
            }
        });
// todo: paso 1 para dejarlo sucio localmente campo sync
        handler.startQuery(0, null, ContratoCorpicoApp.Clientes.URI_CONTENIDO, null, null, null, null);
    }
    public List<ContentProviderOperation> replicarServidor(List<RestCliente> clientes) {
        //Crea lista de operaciones content provider
        List<ContentProviderOperation> operations = new ArrayList<>();

        // Tabla hash para recibir los tipos de cuadrillas entrantes
        HashMap<Integer, RestCliente> clienteMap = new HashMap<>();
        for (RestCliente cli : clientes) {
            clienteMap.put(cli.getCLI_ID(), cli);
        }

        //Consultar registros remotos actuales
        Uri uri = ContratoCorpicoApp.Clientes.URI_CONTENIDO;
        String select = ContratoCorpicoApp.Clientes.TABLE_NAME;
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Clientes - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        short empresa;
        int id;
        String fecha_ingreso;
        String titular;
        short localidad_residencia;
        String codigo_postal_residencia;
        String calle_residencia;
        int altura_residencia;
        String piso_residencia;
        String departamento_residencia;
        String telefono_residencia;
        String celular;
        String fax;
        String email;
        short localidad_laboral;
        String codigo_postal_laboral;
        String calle_laboral;
        int altura_laboral;
        String piso_laboral;
        String departamento_laboral;
        String anexo;
        String telefono_laboral;
        byte situacion_iva;
        String cuit;
        byte situacion_iibb;
        String numero_iibb;
        double extension_persepcion_iva;
        double exencion_nacional;
        double exencion_provincial;
        double exencion_municipal;
        String potencial;
        short clasificacion;
        short estado_civil;
        short profesion;
        String fecha_nacimiento;
        String nacionalidad;
        String lugar_nacimiento;
        String sexo;
        byte estado;
        String clave_personal;
        String id_user;
        String fecha_update;
        int lote_replicacion;

        while (c.moveToNext()) {

            empresa = c.getShort(0);
            id = c.getInt(1);
            fecha_ingreso = c.getString(2);
            titular = c.getString(3);
            localidad_residencia = c.getShort(4);
            codigo_postal_residencia = c.getString(5);
            calle_residencia = c.getString(5);
            altura_residencia = c.getInt(7);
            piso_residencia = c.getString(8);
            departamento_residencia = c.getString(9);
            telefono_residencia = c.getString(10);
            celular = c.getString(11);
            fax = c.getString(12);
            email = c.getString(13);
            localidad_laboral = c.getShort(14);
            codigo_postal_laboral = c.getString(15);
            calle_laboral = c.getString(16);
            altura_laboral = c.getInt(17);
            piso_laboral = c.getString(18);
            departamento_laboral = c.getString(19);
            anexo = c.getString(20);
            telefono_laboral = c.getString(21);
            situacion_iva  = (byte) c.getInt(22);
            cuit = c.getString(23);
            situacion_iibb = (byte) c.getInt(24);
            numero_iibb = c.getString(25);
            extension_persepcion_iva = c.getDouble(26);
            exencion_nacional = c.getDouble(27);
            exencion_provincial = c.getDouble(28);
            exencion_municipal = c.getDouble(29);
            potencial = c.getString(30);
            clasificacion = c.getShort(31);
            estado_civil = c.getShort(32);
            profesion = c.getShort(33);
            fecha_nacimiento = c.getString(34);
            nacionalidad = c.getString(34);
            lugar_nacimiento = c.getString(36);
            sexo = c.getString(37);
            estado = (byte) c.getInt(38);
            clave_personal = c.getString(39);
            id_user = c.getString(40);
            fecha_update = c.getString(41);
            lote_replicacion = c.getInt(42);

            RestCliente match = clienteMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                clienteMap.remove(id);

                Uri existingUri = ContratoCorpicoApp.Clientes.crearUriCliente(id);//PASAR LOS ID A INTEGER

                // Comprobar si tipo de cuadrillas necesita ser actualizado
                boolean b = match.getCLI_EMPRESA() != empresa;
                boolean b1 = match.getCLI_ID() != id;
                boolean b2 =  !match.getCLI_FECHA_INGRESO().equals(fecha_ingreso);
                boolean b3 =  match.getCLI_TITULAR()!= titular;
                boolean b4 = match.getCLI_LOCALIDAD_RESIDENCIA() != localidad_residencia;
                boolean b5 =  match.getCLI_CODIGO_POSTAL_RESIDENCIA() != codigo_postal_residencia;
                boolean b6 =  match.getCLI_CALLE_RESIDENCIA() != calle_residencia;
                boolean b7 =  match.getCLI_ALTURA_RESIDENCIA() != altura_residencia;
                boolean b8 =  match.getCLI_PISO_RESIDENCIA() != piso_residencia;
                boolean b9 =  match.getCLI_DEPARTAMENTO_RESIDENCIA() != departamento_residencia;
                boolean b10 =  match.getCLI_TELEFONO_RESIDENCIA() != telefono_residencia;
                boolean b11 =  match.getCLI_TELEFONO_CELULAR() != celular;
                boolean b12 =  match.getCLI_FAX() != fax;
                boolean b13 =  match.getCLI_E_MAIL() != email;
                boolean b14 =  match.getCLI_LOCALIDAD_LABORAL() != localidad_laboral;
                boolean b15 =  match.getCLI_CODIGO_POSTAL_LABORAL() != codigo_postal_laboral;
                boolean b16 =  match.getCLI_CALLE_LABORAL() != calle_laboral;
                boolean b17 =  match.getCLI_ALTURA_LABORAL() != altura_laboral;
                boolean b18 =  match.getCLI_PISO_LABORAL() != piso_laboral;
                boolean b19 =  match.getCLI_DEPARTAMENTO_LABORAL() != departamento_laboral;
                boolean b20 =  match.getCLI_ANEXO() != anexo;
                boolean b21 =  match.getCLI_TELEFONO_LABORAL() != telefono_laboral;
                boolean b22 =  match.getCLI_SITUACION_IVA() != situacion_iva;
                boolean b23 =  match.getCLI_CUIT() != cuit;
                boolean b24 =  match.getCLI_SITUACION_IIBB() != situacion_iibb;
                boolean b25 =  match.getCLI_NUMERO_IIBB() != numero_iibb;
                boolean b26 =  match.getCLI_EXENCION_PERCEPCION_IVA() != extension_persepcion_iva;
                boolean b27 =  match.getCLI_EXENCION_NACIONAL() != exencion_nacional;
                boolean b28 =  match.getCLI_EXENCION_PROVINCIAL() != exencion_provincial;
                boolean b29 =  match.getCLI_EXENCION_MUNICIPAL() != exencion_municipal;
                boolean b30 =  match.getCLI_POTENCIAL() != potencial;
                boolean b31 =  match.getCLI_CLASIFICACION() != clasificacion;
                boolean b32 =  match.getCLI_ESTADO_CIVIL() != estado_civil;
                boolean b33 =  match.getCLI_PROFESION() != profesion;
                boolean b34 =  match.getCLI_FECHA_NACIMIENTO() != fecha_nacimiento;
                boolean b35 =  match.getCLI_NACIONALIDAD() != nacionalidad;
                boolean b36 =  match.getCLI_LUGAR_NACIMIENTO() != lugar_nacimiento;
                boolean b37 =  match.getCLI_SEXO() != sexo;
                boolean b38 =  match.getCLI_ESTADO() != estado;
                boolean b39 =  match.getCLI_CLAVE_PERSONAL() != clave_personal;
                boolean b40 =  match.getCLI_ID_USER() != id_user;
                boolean b41 =  match.getCLI_FECHA_UPDATE() != fecha_update;
                boolean b42 =  match.getCLI_LOTE_REPLICACION() != lote_replicacion;


                if (b || b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8 || b9 || b10 || b11 || b12 || b13 || b14 || b15
                        || b16 || b17 || b18 || b19 || b20 || b21 || b22 || b23 || b24 || b25 || b26 || b27 || b28
                        || b29 || b30 || b31 || b32 || b33 || b34 || b35 || b36 || b37 || b38 || b39 || b40 || b41 || b42) {

                    Log.i(TAG, "Clientes - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoCorpicoApp.Clientes.CLI_EMPRESA, match.getCLI_EMPRESA())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_ID, match.getCLI_ID())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_FECHA_INGRESO, match.getCLI_FECHA_INGRESO())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_TITULAR, match.getCLI_TITULAR())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_LOCALIDAD_RESIDENCIA, match.getCLI_LOCALIDAD_RESIDENCIA())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_CODIGO_POSTAL_RESIDENCIA, match.getCLI_CODIGO_POSTAL_RESIDENCIA())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_CALLE_RESIDENCIA, match.getCLI_CALLE_RESIDENCIA())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_ALTURA_RESIDENCIA, match.getCLI_ALTURA_RESIDENCIA())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_PISO_RESIDENCIA, match.getCLI_PISO_RESIDENCIA())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_DEPARTAMENTO_RESIDENCIA, match.getCLI_DEPARTAMENTO_RESIDENCIA())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_TELEFONO_RESIDENCIA, match.getCLI_TELEFONO_RESIDENCIA())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_TELEFONO_CELULAR, match.getCLI_TELEFONO_CELULAR())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_FAX, match.getCLI_FAX())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_E_MAIL, match.getCLI_E_MAIL())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_LOCALIDAD_LABORAL, match.getCLI_LOCALIDAD_LABORAL())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_CODIGO_POSTAL_LABORAL, match.getCLI_CODIGO_POSTAL_LABORAL())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_CALLE_LABORAL, match.getCLI_CALLE_LABORAL())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_ALTURA_LABORAL, match.getCLI_ALTURA_LABORAL())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_PISO_LABORAL, match.getCLI_PISO_LABORAL())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_DEPARTAMENTO_LABORAL, match.getCLI_DEPARTAMENTO_LABORAL())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_ANEXO, match.getCLI_ANEXO())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_TELEFONO_LABORAL, match.getCLI_TELEFONO_LABORAL())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_SITUACION_IVA, match.getCLI_SITUACION_IVA())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_CUIT, match.getCLI_CUIT())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_SITUACION_IIBB, match.getCLI_SITUACION_IIBB())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_NUMERO_IIBB, match.getCLI_NUMERO_IIBB())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_EXENCION_PERCEPCION_IVA, match.getCLI_EXENCION_PERCEPCION_IVA())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_EXENCION_NACIONAL, match.getCLI_EXENCION_NACIONAL())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_EXENCION_PROVINCIAL, match.getCLI_EXENCION_PROVINCIAL())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_EXENCION_MUNICIPAL, match.getCLI_EXENCION_MUNICIPAL())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_POTENCIAL, match.getCLI_POTENCIAL())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_CLASIFICACION, match.getCLI_CLASIFICACION())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_ESTADO_CIVIL, match.getCLI_ESTADO_CIVIL())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_PROFESION, match.getCLI_PROFESION())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_FECHA_NACIMIENTO, match.getCLI_FECHA_NACIMIENTO())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_NACIONALIDAD, match.getCLI_NACIONALIDAD())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_LUGAR_NACIMIENTO, match.getCLI_LUGAR_NACIMIENTO())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_SEXO, match.getCLI_SEXO())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_ESTADO, match.getCLI_ESTADO())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_CLAVE_PERSONAL, match.getCLI_CLAVE_PERSONAL())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_ID_USER, match.getCLI_ID_USER())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_FECHA_UPDATE, match.getCLI_FECHA_UPDATE())
                            .withValue(ContratoCorpicoApp.Clientes.CLI_LOTE_REPLICACION, match.getCLI_LOTE_REPLICACION())
                            .build());
                } else {
                    Log.i(TAG, "Clientes - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoCorpicoApp.Clientes.crearUriCliente(id);
                Log.i(TAG, "Clientes - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestCliente cliente : clienteMap.values()){
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.Clientes.URI_CONTENIDO)
                    .withValue(ContratoCorpicoApp.Clientes.CLI_EMPRESA, cliente.getCLI_EMPRESA())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_ID, cliente.getCLI_ID())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_FECHA_INGRESO, cliente.getCLI_FECHA_INGRESO())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_TITULAR, cliente.getCLI_TITULAR())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_LOCALIDAD_RESIDENCIA, cliente.getCLI_LOCALIDAD_RESIDENCIA())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_CODIGO_POSTAL_RESIDENCIA, cliente.getCLI_CODIGO_POSTAL_RESIDENCIA())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_CALLE_RESIDENCIA, cliente.getCLI_CALLE_RESIDENCIA())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_ALTURA_RESIDENCIA, cliente.getCLI_ALTURA_RESIDENCIA())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_PISO_RESIDENCIA, cliente.getCLI_PISO_RESIDENCIA())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_DEPARTAMENTO_RESIDENCIA, cliente.getCLI_DEPARTAMENTO_RESIDENCIA())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_TELEFONO_RESIDENCIA, cliente.getCLI_TELEFONO_RESIDENCIA())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_TELEFONO_CELULAR, cliente.getCLI_TELEFONO_CELULAR())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_FAX, cliente.getCLI_FAX())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_E_MAIL, cliente.getCLI_E_MAIL())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_LOCALIDAD_LABORAL, cliente.getCLI_LOCALIDAD_LABORAL())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_CODIGO_POSTAL_LABORAL, cliente.getCLI_CODIGO_POSTAL_LABORAL())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_CALLE_LABORAL, cliente.getCLI_CALLE_LABORAL())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_ALTURA_LABORAL, cliente.getCLI_ALTURA_LABORAL())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_PISO_LABORAL, cliente.getCLI_PISO_LABORAL())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_DEPARTAMENTO_LABORAL, cliente.getCLI_DEPARTAMENTO_LABORAL())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_ANEXO, cliente.getCLI_ANEXO())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_TELEFONO_LABORAL, cliente.getCLI_TELEFONO_LABORAL())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_SITUACION_IVA, cliente.getCLI_SITUACION_IVA())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_CUIT, cliente.getCLI_CUIT())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_SITUACION_IIBB, cliente.getCLI_SITUACION_IIBB())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_NUMERO_IIBB, cliente.getCLI_NUMERO_IIBB())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_EXENCION_PERCEPCION_IVA, cliente.getCLI_EXENCION_PERCEPCION_IVA())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_EXENCION_NACIONAL, cliente.getCLI_EXENCION_NACIONAL())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_EXENCION_PROVINCIAL, cliente.getCLI_EXENCION_PROVINCIAL())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_EXENCION_MUNICIPAL, cliente.getCLI_EXENCION_MUNICIPAL())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_POTENCIAL, cliente.getCLI_POTENCIAL())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_CLASIFICACION, cliente.getCLI_CLASIFICACION())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_ESTADO_CIVIL, cliente.getCLI_ESTADO_CIVIL())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_PROFESION, cliente.getCLI_PROFESION())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_FECHA_NACIMIENTO, cliente.getCLI_FECHA_NACIMIENTO())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_NACIONALIDAD, cliente.getCLI_NACIONALIDAD())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_LUGAR_NACIMIENTO, cliente.getCLI_LUGAR_NACIMIENTO())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_SEXO, cliente.getCLI_SEXO())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_ESTADO, cliente.getCLI_ESTADO())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_CLAVE_PERSONAL, cliente.getCLI_CLAVE_PERSONAL())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_ID_USER, cliente.getCLI_ID_USER())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_FECHA_UPDATE, cliente.getCLI_FECHA_UPDATE())
                    .withValue(ContratoCorpicoApp.Clientes.CLI_LOTE_REPLICACION, cliente.getCLI_LOTE_REPLICACION())
                    .build());
        }
        return operations;
    }
    public static class AsyncQueryCliente extends AsyncQueryHandler {
        private WeakReference<AsyncOpListener> mListener;

        public interface AsyncOpListener {
            void onQueryComplete(int token, Object cookie, Cursor cursor);
        }

        public AsyncQueryCliente(ContentResolver cr) {
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
