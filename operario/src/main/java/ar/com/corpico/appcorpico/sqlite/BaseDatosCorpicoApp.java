package ar.com.corpico.appcorpico.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;


/**
 * Created by Administrador on 20/02/2018.
 */

public class BaseDatosCorpicoApp extends SQLiteOpenHelper {
    private static final String NOMBRE_BASE_DATOS = "corpico_app.db";

    private static final int VERSION_ACTUAL = 1;

    private final Context contexto;

    interface Tablas {
        String ORDEN_OPERATIVA = "orden_operativa";
        String TIPO_TRABAJO = "tipo_trabajo";
        String MOTIVO_TRABAJO = "motivo_trabajo";
        String TIPO_CUADRILLA = "tipo_cuadrilla";
        String CLIENTE = "cliente";
        String ETAPA = "etapa";
        String TURNO = "turno";
        String EMPRESA_CONTRATISTA = "empresa_contratista";
        String ESTADO_OPERATIVO = "estado_operativo";
        String LOCALIDAD = "localidad";
        String MEDIDOR = "medidor";
        String MEDIDOR_CLASE = "medidor_clase";
        String MEDIDOR_MARCA = "medidor_marca";
        String MEDIDOR_MODELO = "medidor_modelo";
        String MEDIDOR_TIPO = "medidor_tipo";
        String PUNTO_MEDICION_MEDIDOR = "punto_medicion_medidor";
        String OPERARIO = "operario";
        String TIPO_CONEXION = "tipo_conexion";
        String TIPO_CONSUMO = "tipo_consumo";
        String TIPO_EMPRESA = "tipo_empresa";
        String TIPO_USUARIO = "tipo_usuario";
        String SECTOR = "sector";
        String SUMINISTRO = "suministro";
        String SUMINISTRO_TIPO_EMPRESA = "suministro_tipo_empresa";
        String SUMINISTRO_POSICION_GLOBAL = "suministro_posicion_global";
        String TIPO_TRABAJO_CUADRILLA = "tipo_trabajo_cuadrilla";
        String ZONA = "zona";
        String PUNTO_ZONA = "punto_zona";
        String FOTO = "foto";
        String CUADRILLA = "cuadrilla";
        String ESTADO = "estado";
        String NOTA = "nota";

    }

    public BaseDatosCorpicoApp(Context contexto) {
        super(contexto, NOMBRE_BASE_DATOS, null, VERSION_ACTUAL);
        this.contexto = contexto;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //Crea Tabla OrdenesOperativas
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.OrdenesOperativas.TABLE_NAME + " ("
                + ContratoCorpicoApp.OrdenesOperativas.fecha_solicitud + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.numero + " INTEGER NOT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.asociado + " INTEGER NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.suministro + " INTEGER NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.titular + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.calle + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.altura + " INTEGER NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.piso + " INTEGER NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.dpto + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.localidad + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.anexo + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.tipo_trabajo + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.motivo_trabajo + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.observacion_al_operario + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.latitud + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.longitud + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.grupo + " INTEGER NOT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.ruta  + " INTEGER NOT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.orden_lectura + " INTEGER NOT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.tipo_usuario+ " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.tipo_consumo + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.potencia_declarada + " INTEGER NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.medidor + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.medidor_marca + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.medidor_modelo + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.factorM + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.capacidad + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.tension + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.estado + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.sector + " INTEGER NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.fecha_asignacion + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.user_asigo + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.fecha_culminacion + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.cuadrilla + " INTEGER NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.observacion_del_ope + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.user_realizacion + " INTEGER NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.fecha_update + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.sync + " INTEGER NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.user_id + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.zona + " TEXT NULL,"
                + "UNIQUE (" + ContratoCorpicoApp.OrdenesOperativas.numero + "))");

       /* db.execSQL("CREATE TABLE " + ContratoCorpicoApp.OrdenesOperativas.TABLE_NAME + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_SUCURSAL + " INTEGER NOT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_NUMERO + " INTEGER NOT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_CENTRO_ATENCION + " INTEGER NOT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_EMPRESA + " INTEGER NOT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_CLIENTE + " INTEGER NOT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_SUMINISTRO + " INTEGER NOT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_PUNTO_MEDICION + " INTEGER NOT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_FECHA_SOLICITUD + " TEXT NOT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_TIPO_TRABAJO + " INTEGER NOT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_MOTIVO_TRABAJO + " INTEGER NOT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_FECHA_VENCIMIENTO + " TEXT NOT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_OBSERVACIONES_AL_OPE + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_ID_USER_GENERO + " TEXT NOT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_FORMA_GENERACION + " TEXT NOT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_ESTADO_OPERATIVO_ANTES_GENERAR + " INTEGER NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_FECHA_EST_OPERATIVO_ANTES_GENERAR + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_ESTADO_OPERATIVO_LUEGO_GENERAR + " INTEGER NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_EMPRESA_CONTRATISTA + " INTEGER NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_FECHA_ASIGNACION + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_ID_USER_ASIGNO + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_FECHA_CULMINACION + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_OPERARIO + " INTEGER NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_OBSERVACION_DEL_OPE + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_ID_USER_RETENCION + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_FECHA_RETENCION + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_MOTIVO_RETENCION + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_ID_USER_LIBERACION + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_FECHA_LIBERACION + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_OPERARIO_REALIZACION + " INTEGER NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_ESTADO + " TEXT NOT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_ID_USER + " TEXT NOT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_FECHA_UPDATE + " TEXT NOT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_ID_USER_ALTA + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_FECHA_ALTA + " TEXT NULL,"
                + ContratoCorpicoApp.OrdenesOperativas.OTR_LOTE_REPLICACION + " TEXT NOT NULL,"
                //+ "UNIQUE (" + ContratoCorpicoApp.OrdenesOperativas.OTR_SUCURSAL + "," + ContratoCorpicoApp.OrdenesOperativas.OTR_CENTRO_ATENCION + "," + ContratoCorpicoApp.OrdenesOperativas.OTR_NUMERO + "))");
                + "UNIQUE (" + ContratoCorpicoApp.OrdenesOperativas.OTR_NUMERO + "))");*/

        //Crea Tabla TiposTrabajo
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.TiposTrabajo.TABLE_NAME + " ("
                + ContratoCorpicoApp.TiposTrabajo.TIT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + ContratoCorpicoApp.TiposTrabajo.TIT_DESCRIPCION + " TEXT NOT NULL,"
                + ContratoCorpicoApp.TiposTrabajo.TIT_ABREVIATURA + " TEXT NOT NULL,"
                + ContratoCorpicoApp.TiposTrabajo.TIT_TIPO_EMPRESA + " INTEGER NOT NULL,"
                + ContratoCorpicoApp.TiposTrabajo.TIT_CLASIFICACION_TRABAJO + " INTEGER NOT NULL,"
                + ContratoCorpicoApp.TiposTrabajo.TIT_PERMITE_ELIMINAR + " TEXT NOT NULL,"
                + ContratoCorpicoApp.TiposTrabajo.TIT_ACTIVO + " TEXT NOT NULL,"
                + ContratoCorpicoApp.TiposTrabajo.TIT_ID_USER + " TEXT NOT NULL,"
                + ContratoCorpicoApp.TiposTrabajo.TIT_FECHA_UPDATE + " TEXT NOT NULL,"
                + ContratoCorpicoApp.TiposTrabajo.TIT_LOTE_REPLICACION + " INTEGER NOT NULL,"
                + "UNIQUE (" + ContratoCorpicoApp.TiposTrabajo.TIT_ID + "))");

        //Crea Tabla MotivosTrabajo
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.MotivosTrabajo.TABLE_NAME + " ("
                + ContratoCorpicoApp.ColumnasMotivoTrabajo.MTR_TIPO_TRABAJO + " INTEGER NOT NULL,"
                + ContratoCorpicoApp.ColumnasMotivoTrabajo.MTR_ID + " INTEGER NOT NULL,"
                + ContratoCorpicoApp.ColumnasMotivoTrabajo.MTR_DESCRIPCION + " TEXT NOT NULL,"
                + ContratoCorpicoApp.ColumnasMotivoTrabajo.MTR_ABREVIATURA + " TEXT NOT NULL,"
                + ContratoCorpicoApp.ColumnasMotivoTrabajo.MTR_DIAS_RESOLUCION + " INTEGER NOT NULL,"
                + ContratoCorpicoApp.ColumnasMotivoTrabajo.MTR_ALTERA_ESTADO_LUEGO_GENERAR + " TEXT NOT NULL,"
                + ContratoCorpicoApp.ColumnasMotivoTrabajo.MTR_ESTADO_LUEGO_GENERAR + " INTEGER NULL,"
                + ContratoCorpicoApp.ColumnasMotivoTrabajo.MTR_SOLICITA_COLOCACION + " TEXT NOT NULL,"
                + ContratoCorpicoApp.ColumnasMotivoTrabajo.MTR_SOLICITA_RETIRO + " TEXT NOT NULL,"
                + ContratoCorpicoApp.ColumnasMotivoTrabajo.MTR_SOLICITA_INFORMATIVO + " TEXT NOT NULL,"
                + ContratoCorpicoApp.ColumnasMotivoTrabajo.MTR_SOLICITA_PRECINTO_HAB + " TEXT NOT NULL,"
                + ContratoCorpicoApp.ColumnasMotivoTrabajo.MTR_SOLICITA_PRECINTO_MED + " TEXT NOT NULL,"
                + ContratoCorpicoApp.ColumnasMotivoTrabajo.MTR_SOLICITA_INSTALACION + " TEXT NOT NULL,"
                + ContratoCorpicoApp.ColumnasMotivoTrabajo.MTR_SOLICITA_MATERIALES + " TEXT NOT NULL,"
                + ContratoCorpicoApp.ColumnasMotivoTrabajo.MTR_PERMITE_ELIMINAR + " TEXT NOT NULL,"
                + ContratoCorpicoApp.ColumnasMotivoTrabajo.MTR_PROCESA_TOMAESTADO + " TEXT NOT NULL,"
                + ContratoCorpicoApp.ColumnasMotivoTrabajo.MTR_ACTIVO + " TEXT NOT NULL,"
                + ContratoCorpicoApp.ColumnasMotivoTrabajo.MTR_ID_USER + " TEXT NOT NULL,"
                + ContratoCorpicoApp.ColumnasMotivoTrabajo.MTR_FECHA_UPDATE + " TEXT NOT NULL,"
                + ContratoCorpicoApp.ColumnasMotivoTrabajo.MTR_LOTE_REPLICACION + " INTEGER NULL,"
                + "UNIQUE (" + ContratoCorpicoApp.ColumnasMotivoTrabajo.MTR_TIPO_TRABAJO + "," + ContratoCorpicoApp.ColumnasMotivoTrabajo.MTR_ID + "))");

        //Crea Tabla TipoCuadrilla
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.TiposCuadrilla.TABLE_NAME + " ("
                + ContratoCorpicoApp.TiposCuadrilla.TC_ID + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.TiposCuadrilla.TC_ID_SERVICIO + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.TiposCuadrilla.TC_ID_SECTOR + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.TiposCuadrilla.TC_DESCRIPCION + " TEXT NOT NULL,"
                + ContratoCorpicoApp.TiposCuadrilla.TC_FECHA_UPDATE + " TEXT NOT NULL,"
                + ContratoCorpicoApp.TiposCuadrilla.TC_GEASYS_ID + " INTEGER NULL,"
                + "UNIQUE (" + ContratoCorpicoApp.TiposCuadrilla.TC_ID + "))");

        //Crea Tabla Cliente
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.Clientes.TABLE_NAME + " ("
                + ContratoCorpicoApp.Clientes.CLI_ID + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Clientes.CLI_EMPRESA + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Clientes.CLI_FECHA_INGRESO + " TEXT NOT NULL,"
                + ContratoCorpicoApp.Clientes.CLI_TITULAR + " TEXT NOT NULL,"
                + ContratoCorpicoApp.Clientes.CLI_LOCALIDAD_RESIDENCIA + " INTEGER NULL, "
                + ContratoCorpicoApp.Clientes.CLI_CODIGO_POSTAL_RESIDENCIA + " TEXT NULL,"
                + ContratoCorpicoApp.Clientes.CLI_CALLE_RESIDENCIA + " TEXT NULL,"
                + ContratoCorpicoApp.Clientes.CLI_ALTURA_RESIDENCIA + " INTEGER NULL, "
                + ContratoCorpicoApp.Clientes.CLI_PISO_RESIDENCIA + " TEXT NULL,"
                + ContratoCorpicoApp.Clientes.CLI_DEPARTAMENTO_RESIDENCIA + " TEXT NULL,"
                + ContratoCorpicoApp.Clientes.CLI_TELEFONO_RESIDENCIA + " TEXT NULL,"
                + ContratoCorpicoApp.Clientes.CLI_TELEFONO_CELULAR + " TEXT NULL,"
                + ContratoCorpicoApp.Clientes.CLI_FAX + " TEXT NULL,"
                + ContratoCorpicoApp.Clientes.CLI_E_MAIL + " TEXT NULL,"
                + ContratoCorpicoApp.Clientes.CLI_LOCALIDAD_LABORAL + " INTEGER NULL, "
                + ContratoCorpicoApp.Clientes.CLI_CODIGO_POSTAL_LABORAL + " TEXT NULL,"
                + ContratoCorpicoApp.Clientes.CLI_CALLE_LABORAL + " TEXT NULL,"
                + ContratoCorpicoApp.Clientes.CLI_ALTURA_LABORAL + " INTEGER NULL, "
                + ContratoCorpicoApp.Clientes.CLI_PISO_LABORAL + " TEXT NULL,"
                + ContratoCorpicoApp.Clientes.CLI_DEPARTAMENTO_LABORAL + " TEXT NULL,"
                + ContratoCorpicoApp.Clientes.CLI_ANEXO + " TEXT NULL,"
                + ContratoCorpicoApp.Clientes.CLI_TELEFONO_LABORAL + " TEXT NULL,"
                + ContratoCorpicoApp.Clientes.CLI_SITUACION_IVA + " INTEGER NULL, "
                + ContratoCorpicoApp.Clientes.CLI_CUIT + " TEXT NULL,"
                + ContratoCorpicoApp.Clientes.CLI_SITUACION_IIBB + " INTEGER NULL, "
                + ContratoCorpicoApp.Clientes.CLI_NUMERO_IIBB + " TEXT NULL,"
                + ContratoCorpicoApp.Clientes.CLI_EXENCION_PERCEPCION_IVA + " INTEGER NULL, "
                + ContratoCorpicoApp.Clientes.CLI_EXENCION_NACIONAL + " INTEGER NULL, "
                + ContratoCorpicoApp.Clientes.CLI_EXENCION_PROVINCIAL + " INTEGER NULL, "
                + ContratoCorpicoApp.Clientes.CLI_EXENCION_MUNICIPAL + " INTEGER NULL, "
                + ContratoCorpicoApp.Clientes.CLI_POTENCIAL + " TEXT NOT NULL,"
                + ContratoCorpicoApp.Clientes.CLI_CLASIFICACION + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Clientes.CLI_ESTADO_CIVIL + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Clientes.CLI_PROFESION + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Clientes.CLI_FECHA_NACIMIENTO + " TEXT NULL,"
                + ContratoCorpicoApp.Clientes.CLI_NACIONALIDAD + " TEXT NULL,"
                + ContratoCorpicoApp.Clientes.CLI_LUGAR_NACIMIENTO + " TEXT NOT NULL,"
                + ContratoCorpicoApp.Clientes.CLI_SEXO + " TEXT NOT NULL,"
                + ContratoCorpicoApp.Clientes.CLI_ESTADO + " INTEGER NULL, "
                + ContratoCorpicoApp.Clientes.CLI_CLAVE_PERSONAL + " TEXT NULL,"
                + ContratoCorpicoApp.Clientes.CLI_ID_USER + " TEXT NULL,"
                + ContratoCorpicoApp.Clientes.CLI_FECHA_UPDATE + " TEXT NOT NULL,"
                + ContratoCorpicoApp.Clientes.CLI_LOTE_REPLICACION + " INTEGER NOT NULL, "
                + "UNIQUE (" + ContratoCorpicoApp.Clientes.CLI_ID + "))");

        //Crea Tabla Etapas
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.Etapas.TABLE_NAME + " ("
                        + ContratoCorpicoApp.Etapas.ETA_NUMERO_ORDEN + " INTEGER NOT NULL,"
                        + ContratoCorpicoApp.Etapas.ETA_FECHA + " TEXT NOT NULL,"
                        + ContratoCorpicoApp.Etapas.ETA_ESTADO + " TEXT NULL,"
                        + ContratoCorpicoApp.Etapas.ETA_OBSERVACION + " TEXT NULL,"
                        + ContratoCorpicoApp.Etapas.ETA_USUARIO + " TEXT NULL,"
                        + ContratoCorpicoApp.Etapas.ETA_ID + " INTEGER NOT NULL,"
                        + ContratoCorpicoApp.Etapas.ETA_FECHA_UPDATE + " TEXT NULL,"
                        + ContratoCorpicoApp.Etapas.ETA_SERVICIO + " INTEGER NULL,"
                        + ContratoCorpicoApp.Etapas.ETA_SECTOR + " INTEGER  NULL,"
                        + ContratoCorpicoApp.Etapas.ETA_LATITUD + " TEXT  NULL,"
                        + ContratoCorpicoApp.Etapas.ETA_LONGITUD + " TEXT  NULL,"
                        + ContratoCorpicoApp.Etapas.ETA_SYNC + " INTEGER  NULL,"
                        + ContratoCorpicoApp.Etapas.ETA_ID_TEMP + " INTEGER  NULL,"
                        + "UNIQUE (" + ContratoCorpicoApp.Etapas.ETA_ID + "))");

        //Crea Tabla Turnos
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.Turnos.TABLE_NAME + " ("
                + ContratoCorpicoApp.Turnos.TUR_ORDEN + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Turnos.TUR_TURNO + " TEXT NOT NULL, "
                + ContratoCorpicoApp.Turnos.TUR_FECHA_UPDATE + " TEXT NULL, "
                + ContratoCorpicoApp.Turnos.TUR_SERVICIO + " INTEGER NULL, "
                + ContratoCorpicoApp.Turnos.TUR_SECTOR + " INTEGER NULL, "
                + "UNIQUE (" + ContratoCorpicoApp.Turnos.TUR_ORDEN + "))");

        //Crea Tabla Empresa Contratista
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.EmpresasContratista.TABLE_NAME + " ("
                + ContratoCorpicoApp.EmpresasContratista.EML_ID + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.EmpresasContratista.EML_DESCRIPCION + " TEXT NOT NULL, "
                + ContratoCorpicoApp.EmpresasContratista.EML_DIRECCION + " TEXT NULL, "
                + ContratoCorpicoApp.EmpresasContratista.EML_TELEFONO + " TEXT NULL, "
                + ContratoCorpicoApp.EmpresasContratista.EML_TIPO_TOMA + " TEXT NOT NULL, "
                + ContratoCorpicoApp.EmpresasContratista.EML_TIPO_EMPRESA + " INTEGER NULL, "
                + ContratoCorpicoApp.EmpresasContratista.EML_ACTIVO + " TEXT NOT NULL, "
                + ContratoCorpicoApp.EmpresasContratista.EML_ID_USER + " TEXT NOT NULL, "
                + ContratoCorpicoApp.EmpresasContratista.EML_FECHA_UPDATE + " TEXT NOT NULL, "
                + ContratoCorpicoApp.EmpresasContratista.EML_LOTE_REPLICACION + " INTEGER NOT NULL, "
                + "UNIQUE (" + ContratoCorpicoApp.EmpresasContratista.EML_ID + "))");

        //Crea Tabla Estado Operativo
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.EstadoOperativo.TABLE_NAME + " ("
                + ContratoCorpicoApp.EstadoOperativo.EOP_ID + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.EstadoOperativo.EOP_DESCRIPCION + " TEXT NULL, "
                + ContratoCorpicoApp.EstadoOperativo.EOP_ABREVIATURA + " TEXT NULL, "
                + ContratoCorpicoApp.EstadoOperativo.EOP_TIPO_EMPRESA + " INTEGER NULL, "
                + ContratoCorpicoApp.EstadoOperativo.EOP_SALE_A_LEER + " TEXT NULL, "
                + ContratoCorpicoApp.EstadoOperativo.EOP_ES_FACTURABLE + " TEXT NULL, "
                + ContratoCorpicoApp.EstadoOperativo.EOP_CAMB_TITULARIDAD + " TEXT NULL, "
                + ContratoCorpicoApp.EstadoOperativo.EOP_CAMB_TARIFA + " TEXT NULL, "
                + ContratoCorpicoApp.EstadoOperativo.EOP_CAMB_DIR_POSTAL + " TEXT NULL, "
                + ContratoCorpicoApp.EstadoOperativo.EOP_CAMB_DAT_IMPOSITIVOS + " TEXT NULL, "
                + ContratoCorpicoApp.EstadoOperativo.EOP_BAJA_DEB_AUTOMATIVO + " TEXT NULL, "
                + ContratoCorpicoApp.EstadoOperativo.EOP_RECLAMOS + " TEXT NULL, "
                + ContratoCorpicoApp.EstadoOperativo.EOP_ORDEN_TRABAJO + " TEXT NULL, "
                + ContratoCorpicoApp.EstadoOperativo.EOP_BAJA_SUMINISTRO + " TEXT NULL, "
                + ContratoCorpicoApp.EstadoOperativo.EOP_COBRANZA + " TEXT NULL, "
                + ContratoCorpicoApp.EstadoOperativo.EOP_INCLUYE_AVISO_DEUDA + " TEXT NULL, "
                + ContratoCorpicoApp.EstadoOperativo.EOP_POSEE_MEDIDOR + " TEXT NULL, "
                + ContratoCorpicoApp.EstadoOperativo.EOP_ACCION_SI_NO_POSEE_MED + " TEXT NULL, "
                + ContratoCorpicoApp.EstadoOperativo.EOP_PERMITE_RE_SOLICITAR_SERVICIO + " TEXT NULL, "
                + ContratoCorpicoApp.EstadoOperativo.EOP_COLOR + " TEXT NULL, "
                + ContratoCorpicoApp.EstadoOperativo.EOP_ESTADO_CONEXION + " TEXT NULL, "
                + ContratoCorpicoApp.EstadoOperativo.EOP_CLIENTE + " TEXT NULL, "
                + ContratoCorpicoApp.EstadoOperativo.EOP_ACTIVO + " TEXT NULL, "
                + ContratoCorpicoApp.EstadoOperativo.EOP_ID_USER + " TEXT NULL, "
                + ContratoCorpicoApp.EstadoOperativo.EOP_FECHA_UPDATE + " TEXT NULL, "
                + ContratoCorpicoApp.EstadoOperativo.EOP_LOTE_REPLICACION+ " INTEGER NULL, "
                + "UNIQUE (" + ContratoCorpicoApp.EstadoOperativo.EOP_ID + "))");

        //Crea Tabla Localidad
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.Localidad.TABLE_NAME + " ("
                + ContratoCorpicoApp.Localidad.LOC_ID + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Localidad.LOC_DESCRIPCION	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.Localidad.LOC_MUNICIPIO+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Localidad.LOC_INFORMA_SUCURSAL	+ " TEXT NULL, "
                + ContratoCorpicoApp.Localidad.LOC_SUCURSAL+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Localidad.LOC_ZONA_TARIFARIA + " INTEGER NULL, "
                + ContratoCorpicoApp.Localidad.LOC_ACTIVO	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.Localidad.LOC_ID_USER	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.Localidad.LOC_FECHA_UPDATE	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.Localidad.LOC_LOTE_REPLICACION	+ " INTEGER NOT NULL, "
                + "UNIQUE (" + ContratoCorpicoApp.Localidad.LOC_ID + "))");

        //Crea Tabla Medidor
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.Medidor.TABLE_NAME + " ("
                + ContratoCorpicoApp.Medidor.MED_ID + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Medidor.MED_EMPRESA + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Medidor.MED_ALMACEN + " INTEGER NULL, "
                + ContratoCorpicoApp.Medidor.MED_ESTADO	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Medidor.MED_MARCA	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Medidor.MED_TIPOSERIE	+ " INTEGER NULL, "
                + ContratoCorpicoApp.Medidor.MED_SERIE	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Medidor.MED_MODELO	+ " INTEGER NULL, "
                + ContratoCorpicoApp.Medidor.MED_CLASE	+ " INTEGER NULL, "
                + ContratoCorpicoApp.Medidor.MED_TIPO	+ " INTEGER NULL, "
                + ContratoCorpicoApp.Medidor.MED_RUEDAS	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Medidor.MED_ANO_FABRICA	+ " INTEGER NULL, "
                + ContratoCorpicoApp.Medidor.MED_ANOS_VIDA	+ " INTEGER NULL, "
                + ContratoCorpicoApp.Medidor.MED_FECHA_ENTRADA	+ " TEXT NULL, "
                + ContratoCorpicoApp.Medidor.MED_VTO_GARANTIA	+ " TEXT NULL, "
                + ContratoCorpicoApp.Medidor.MED_FACTOR	+ " REAL NULL, "
                + ContratoCorpicoApp.Medidor.MED_POSEE_LEDS	+ " TEXT NULL, "
                + ContratoCorpicoApp.Medidor.MED_MOTIVO_BAJA	+ " INTEGER NULL, "
                + ContratoCorpicoApp.Medidor.MED_FECHA_BAJA	+ " TEXT NULL, "
                + ContratoCorpicoApp.Medidor.MED_ID_USER	+ " TEXT NULL, "
                + ContratoCorpicoApp.Medidor.MED_FECHA_UPDATE	+ " TEXT NULL, "
                + ContratoCorpicoApp.Medidor.MED_LOTE_REPLICACION	+ " INTEGER NULL, "
                + "UNIQUE (" + ContratoCorpicoApp.Medidor.MED_ID + "))");

        //Crea Tabla Medidor Clase
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.MedidorClase.TABLE_NAME + " ("
                + ContratoCorpicoApp.MedidorClase.MCL_ID + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.MedidorClase.MCL_DESCRIPCION	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.MedidorClase.MCL_TIPO_EMPRESA	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.MedidorClase.MCL_CAPACIDAD	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.MedidorClase.MCL_ACTIVO	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.MedidorClase.MCL_ID_USER	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.MedidorClase.MCL_FECHA_UPDATE	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.MedidorClase.MCL_LOTE_REPLICACION	+ " INTEGER NOT NULL, "
                + "UNIQUE (" + ContratoCorpicoApp.MedidorClase.MCL_ID + "))");

        //Crea Tabla Medidor Marca
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.MedidorMarca.TABLE_NAME + " ("
                + ContratoCorpicoApp.MedidorMarca.MCA_ID + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.MedidorMarca.MCA_DESCRIPCION	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.MedidorMarca.MCA_ABREVIATURA	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.MedidorMarca.MCA_ACTIVO	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.MedidorMarca.MCA_ID_USER	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.MedidorMarca.MCA_FECHA_UPDATE	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.MedidorMarca.MCA_LOTE_REPLICACION	+ " INTEGER NOT NULL, "
                + "UNIQUE (" + ContratoCorpicoApp.MedidorMarca.MCA_ID + "))");

        //Crea Tabla Medidor Modelo
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.MedidorModelo.TABLE_NAME + " ("
                + ContratoCorpicoApp.MedidorModelo.MMO_MARCA_MEDIDOR + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.MedidorModelo.MMO_ID	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.MedidorModelo.MMO_DESCRIPCION	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.MedidorModelo.MMO_TIPO_EMPRESA	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.MedidorModelo.MMO_ACTIVO	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.MedidorModelo.MMO_ID_USER	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.MedidorModelo.MMO_FECHA_UPDATE	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.MedidorModelo.MMO_LOTE_REPLICACION	+ " INTEGER NOT NULL, "
                + "UNIQUE (" + ContratoCorpicoApp.MedidorModelo.MMO_ID + "))");

        //Crea Tabla Medidor Tipo
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.MedidorTipo.TABLE_NAME + " ("
                + ContratoCorpicoApp.MedidorTipo.MET_ID + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.MedidorTipo.MET_DESCRIPCION	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.MedidorTipo.MET_TIPO_EMPRESA	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.MedidorTipo.MET_ACTIVO	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.MedidorTipo.MET_ID_USER	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.MedidorTipo.MET_FECHA_UPDATE	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.MedidorTipo.MET_LOTE_REPLICACION	+ " INTEGER NOT NULL, "
                + "UNIQUE (" + ContratoCorpicoApp.MedidorTipo.MET_ID + "))");

        //Crea Tabla Medidor Punto Medición Meidor
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.PuntoMedicioMedidor.TABLE_NAME + " ("
                + ContratoCorpicoApp.PuntoMedicioMedidor.PMM_EMPRESA + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.PuntoMedicioMedidor.PMM_CLIENTE + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.PuntoMedicioMedidor.PMM_SUMINISTRO + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.PuntoMedicioMedidor.PMM_TIPO_EMPRESA + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.PuntoMedicioMedidor.PMM_PUNTO_MEDICION + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.PuntoMedicioMedidor.PMM_MEDIDOR + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.PuntoMedicioMedidor.PMM_UBICACION_HABITACULO + " INTEGE NULL, "
                + ContratoCorpicoApp.PuntoMedicioMedidor.PMM_ADVERTENCIA + " INTEGER NULL, "
                + ContratoCorpicoApp.PuntoMedicioMedidor.PMM_ES_FACTURABLE + " TEXT NULL, "
                + ContratoCorpicoApp.PuntoMedicioMedidor.PMM_ID_USER + " TEXT NULL, "
                + ContratoCorpicoApp.PuntoMedicioMedidor.PMM_FECHA_UPDATE + " TEXT NULL, "
                + ContratoCorpicoApp.PuntoMedicioMedidor.PMM_LOTE_REPLICACION + " INTEGER NULL, "
                + "UNIQUE (" + ContratoCorpicoApp.PuntoMedicioMedidor.PMM_CLIENTE + "," + ContratoCorpicoApp.PuntoMedicioMedidor.PMM_SUMINISTRO
                             + "," + ContratoCorpicoApp.PuntoMedicioMedidor.PMM_TIPO_EMPRESA + "," +  ContratoCorpicoApp.PuntoMedicioMedidor.PMM_PUNTO_MEDICION
                             +  "," + ContratoCorpicoApp.PuntoMedicioMedidor.PMM_MEDIDOR + "))");

        //Crea Tabla Medidor Operario
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.Operario.TABLE_NAME + " ("
                + ContratoCorpicoApp.Operario.OPE_ID	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Operario.OPE_DESCRIPCION	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.Operario.OPE_CONTRATISTA	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Operario.OPE_SUCURSAL	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Operario.OPE_ACTIVO	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.Operario.OPE_ID_USER	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.Operario.OPE_FECHA_UPDATE	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.Operario.OPE_LOTE_REPLICACION	+ " INTEGER NOT NULL, "
                + "UNIQUE (" + ContratoCorpicoApp.Operario.OPE_ID + "))");

        //Crea Tabla Tipo Conexion
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.TipoConexion.TABLE_NAME + " ("
                + ContratoCorpicoApp.TipoConexion.TCO_ID	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.TipoConexion.TCO_DESCRIPCION	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoConexion.TCO_TIPO_EMPRESA	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.TipoConexion.TCO_FACTOR	+ " REAL NOT NULL, "
                + ContratoCorpicoApp.TipoConexion.TCO_ACTIVO	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoConexion.TCO_ID_USER	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoConexion.TCO_FECHA_UPDATE	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoConexion.TCO_LOTE_REPLICACION	+ " INTEGER NOT NULL, "
                + "UNIQUE (" + ContratoCorpicoApp.Operario.OPE_ID + "))");

        //Crea Tabla Tipo Consumo
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.TipoConsumo.TABLE_NAME + " ("
                + ContratoCorpicoApp.TipoConsumo.TIC_ID	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.TipoConsumo.TIC_DESCRIPCION	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoConsumo.TIC_TIPO_EMPRESA	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.TipoConsumo.TIC_ABREVIATURA	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoConsumo.TIC_TIPO_CONSUMO	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoConsumo.TIC_TARIFA_CONSUMO	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoConsumo.TIC_TARIFA_RESERVA	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoConsumo.TIC_CARGO_FIJO	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoConsumo.TIC_FACTURA_MINIMA	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoConsumo.TIC_TRANSPORTE	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoConsumo.TIC_IMPUTACION_CONTABLE	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoConsumo.TIC_UTILIZA_FACTOR_PRESION	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoConsumo.TIC_UTILIZA_TRANSFORMADOR	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoConsumo.TIC_CARGO_FIJO_DESCUENTO	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoConsumo.TIC_TARIFA_CONSUMO_DESCUENTO	+ " TEXT NULL, "
                + ContratoCorpicoApp.TipoConsumo.TIC_ACTIVO	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoConsumo.TIC_ID_USER	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoConsumo.TIC_FECHA_UPDATE	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoConsumo.TIC_LOTE_REPLICACION	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.TipoConsumo.TIC_SITUACION_IVA	+ " INTEGER NULL, "
                + ContratoCorpicoApp.TipoConsumo.TIC_PORCENTAJE_CONTROL_CONSUMO_REDUCIDO	+ " REAL NOT NULL, "
                + ContratoCorpicoApp.TipoConsumo.TIC_PORCENTAJE_CONTROL_CONSUMO_ELEVADO	+ " REAL NOT NULL, "
                + ContratoCorpicoApp.TipoConsumo.TIC_CONSUMO_MINIMO_CONTROL	+ " REAL NOT NULL, "
                + ContratoCorpicoApp.TipoConsumo.TIC_MUESTRA_EN_ATP	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoConsumo.TIC_MUESTRA_EN_ADMIN	+ " TEXT NULL, "
                + "UNIQUE (" + ContratoCorpicoApp.TipoConsumo.TIC_ID + "))");

        //Crea Tabla Tipo Empresa
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.TipoEmpresa.TABLE_NAME + " ("
                + ContratoCorpicoApp.TipoEmpresa.TIE_ID	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.TipoEmpresa.TIE_DESCRIPCION	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoEmpresa.TIE_ABREVIATURA	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoEmpresa.TIE_ABREVIATURA_ATP	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoEmpresa.TIE_MUESTRA_CONSUMO_CONVERTIDO	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoEmpresa.TIE_FACTOR_CONVERSION_CONSUMO		+ " REAL NOT NULL, "
                + ContratoCorpicoApp.TipoEmpresa.TIE_ORDEN		+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.TipoEmpresa.TIE_UTILIZA_MODULO_LECTURAS	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoEmpresa.TIE_USA_TRANSFORMADOR	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoEmpresa.TIE_BAJA_PERMITE_CAMBIAR_EST_ADM	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoEmpresa.TIE_ACTIVO	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoEmpresa.TIE_ID_USER	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoEmpresa.TIE_FECHA_UPDATE	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoEmpresa.TIE_LOTE_REPLICACION + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.TipoEmpresa.TIE_MODO_CALCULO_TARIFA_CONSUMO	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoEmpresa.TIE_AJUSTA_CARGO_FIJO_ALTA_SUMINISTRO	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoEmpresa.TIE_AJUSTA_ESCALA_CONSUMO_ALTA_SUMINISTRO	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoEmpresa.TIE_AJUSTA_CARGO_FIJO_BAJA_SUMINISTRO	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoEmpresa.TIE_AJUSTA_ESCALA_CONSUMO_BAJA_SUMINISTRO	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoEmpresa.TIE_MEDIDOR_FUNCION + " INTEGER NULL, "
                + ContratoCorpicoApp.TipoEmpresa.TIE_COLOR	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoEmpresa.TIE_EMPRESA_COBRANZA		+ " INTEGER NULL, "
                + ContratoCorpicoApp.TipoEmpresa.TIE_ESTADO_OPE_BAJA		+ " INTEGER NULL, "
                + "UNIQUE (" + ContratoCorpicoApp.TipoEmpresa.TIE_ID + "))");

        //Crea Tabla Tipo Empresa
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.TipoUsuario.TABLE_NAME + " ("
                + ContratoCorpicoApp.TipoUsuario.TIU_ID	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.TipoUsuario.TIU_DESCRIPCION + " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoUsuario.TIU_TIPO_EMPRESA + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.TipoUsuario.TIU_ABREVIATURA + " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoUsuario.TIU_SOLICITA_CIIU + " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoUsuario.TIU_PUNTOS_MEDICION_MULTIPLES + " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoUsuario.TIU_MEDIDOR_FUNCION_MULTIPLE+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoUsuario.TIU_ACTIVO + " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoUsuario.TIU_ID_USER + " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoUsuario.TIU_FECHA_UPDATE + " TEXT NOT NULL, "
                + ContratoCorpicoApp.TipoUsuario.TIU_LOTE_REPLICACION + " INTEGER NOT NULL, "
                + "UNIQUE (" + ContratoCorpicoApp.TipoUsuario.TIU_ID + "))");

        //Crea Tabla Sector
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.Sector.TABLE_NAME + " ("
                + ContratoCorpicoApp.Sector.SEC_ID	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Sector.SEC_ID_SERVICIO	+ " INTEGER NULL, "
                + ContratoCorpicoApp.Sector.SEC_DESCRIPCION	+ " TEXT NULL, "
                + ContratoCorpicoApp.Sector.SEC_FECHA_UPDATE	+ " TEXT NULL, "
                + "UNIQUE (" + ContratoCorpicoApp.Sector.SEC_ID + "))");

        //Crea Tabla Suministro
                db.execSQL("CREATE TABLE " + ContratoCorpicoApp.Suministro.TABLE_NAME + " ("
                + ContratoCorpicoApp.Suministro.SUM_EMPRESA	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_CLIENTE	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_ID	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_SUCURSAL	+ " INTEGER NULL, "
                + ContratoCorpicoApp.Suministro.SUM_GRUPO	+ " INTEGER NULL, "
                + ContratoCorpicoApp.Suministro.SUM_RUTA	+ " INTEGER NULL, "
                + ContratoCorpicoApp.Suministro.SUM_ORDEN_LECTURA	+ " INTEGER NULL, "
                + ContratoCorpicoApp.Suministro.SUM_LOCALIDAD	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_CODIGO_POSTAL	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_CALLE	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_ALTURA	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_PISO	+ " TEXT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_DEPARTAMENTO	+ " TEXT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_CALLE_ENTRE1	+ " TEXT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_CALLE_ENTRE2	+ " TEXT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_ANEXO	+ " TEXT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_REFERENCIA_MUNICIPAL	+ " TEXT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_CIRCUNSCRIPCION + " TEXT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_RADIO	+ " TEXT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_MANZANA	+ " TEXT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_QUINTA	+ " TEXT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_PARCELA	+ " TEXT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_SUBPARCELA	+ " TEXT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_INQUILINO	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_FECHA_VTO_ALQUILER	+ " TEXT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_CIIU	+ " INTEGER NULL, "
                + ContratoCorpicoApp.Suministro.SUM_DIRECCION_POSTAL	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_GARANTE	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_MOROSIDAD	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_FACTURABLE	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_ESTADO_ADM	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_FECHA_ESTADO_ADM	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_FECHA_CAMBIO_TITULARIDAD	+ " TEXT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_TIENE_CONCEPTOS_PUNTUALES	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_VALIDA_PUNTUAL	+ " TEXT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_SUMINISTRO_ANTERIOR1	+ " TEXT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_SUMINISTRO_ANTERIOR2	+ " TEXT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_SUMINISTRO_ANTERIOR3	+ " TEXT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_SUMINISTRO_ANTERIOR4	+ " TEXT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_ID_USER	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_FECHA_UPDATE	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.Suministro.SUM_LOTE_REPLICACION	+ " INTEGER NOT NULL, "
                + "UNIQUE (" + ContratoCorpicoApp.Suministro.SUM_CLIENTE + ","
                + ContratoCorpicoApp.Suministro.SUM_ID + "))");

        //Crea Tabla Suministro Tipo Empresa
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.SuministroTipoEmpresa.TABLE_NAME + " ("
                + ContratoCorpicoApp.SuministroTipoEmpresa.STE_EMPRESA	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.SuministroTipoEmpresa.STE_CLIENTE	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.SuministroTipoEmpresa.STE_SUMINISTRO	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.SuministroTipoEmpresa.STE_TIPO_EMPRESA	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.SuministroTipoEmpresa.STE_FECHA_INGRESO	+ " TEXT NULL, "
                + ContratoCorpicoApp.SuministroTipoEmpresa.STE_ESTADO_OPE	+ " INTEGER NULL, "
                + ContratoCorpicoApp.SuministroTipoEmpresa.STE_FECHA_ESTADO_OPE	+ " TEXT NULL, "
                + ContratoCorpicoApp.SuministroTipoEmpresa.STE_TIPO_CONSUMO	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.SuministroTipoEmpresa.STE_TIPO_USUARIO	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.SuministroTipoEmpresa.STE_TIPO_SERVICIO	+ " INTEGER NULL, "
                + ContratoCorpicoApp.SuministroTipoEmpresa.STE_TIPO_CONEXION	+ " INTEGER NULL, "
                + ContratoCorpicoApp.SuministroTipoEmpresa.STE_POTENCIA	+ " INTEGER NULL, "
                + ContratoCorpicoApp.SuministroTipoEmpresa.STE_POSEE_PROYECTO	+ " TEXT NULL, "
                + ContratoCorpicoApp.SuministroTipoEmpresa.STE_RESERVA_CAPACIDAD	+ " INTEGER NULL, "
                + ContratoCorpicoApp.SuministroTipoEmpresa.STE_UNIDAD_PROVEEDORA	+ " INTEGER NULL, "
                + ContratoCorpicoApp.SuministroTipoEmpresa.STE_SUB_UNIDAD_PROVEEDORA	+ " INTEGER NULL, "
                + ContratoCorpicoApp.SuministroTipoEmpresa.STE_TRANSFORMADOR	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.SuministroTipoEmpresa.STE_ID_USER	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.SuministroTipoEmpresa.STE_FECHA_UPDATE	+ " TEXT NOT NULL, "
                + ContratoCorpicoApp.SuministroTipoEmpresa.STE_LOTE_REPLICACION	+ " INTEGER NOT NULL, "
                + "UNIQUE (" + ContratoCorpicoApp.SuministroTipoEmpresa.STE_CLIENTE + ","
                + ContratoCorpicoApp.SuministroTipoEmpresa.STE_SUMINISTRO + "))");

        //Crea Tabla Suministro Posicion Global
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.SuministroPosicionGlobal.TABLE_NAME + " ("
                + ContratoCorpicoApp.SuministroPosicionGlobal.SPG_EMPRESA	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.SuministroPosicionGlobal.SPG_CLIENTE	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.SuministroPosicionGlobal.SPG_SUMINISTRO	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.SuministroPosicionGlobal.SPG_LATITUD	+ " TEXT NULL, "
                + ContratoCorpicoApp.SuministroPosicionGlobal.SPG_LONGITUD	+ " TEXT NULL, "
                + ContratoCorpicoApp.SuministroPosicionGlobal.SPG_FECHA_CAPTURA	+ " TEXT NULL, "
                + ContratoCorpicoApp.SuministroPosicionGlobal.SPG_USER_ID	+ " TEXT NULL, "
                + ContratoCorpicoApp.SuministroPosicionGlobal.SPG_FECHA_UPDATE	+ " TEXT NULL, "
                + "UNIQUE (" + ContratoCorpicoApp.SuministroPosicionGlobal.SPG_CLIENTE + ","
                + ContratoCorpicoApp.SuministroPosicionGlobal.SPG_SUMINISTRO + "))");

        //Crea Tabla Tipo Trabajo Cuadrilla
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.TipoTrabajoCuadrilla.TABLE_NAME + " ("
                + ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_CUADRILLA	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_TIPO_TRABAJO	+ " INTEGER NOT NULL, "
                + ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_FECHA_UPDATE	+ " TEXT NULL, "
                + "UNIQUE (" + ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_CUADRILLA + ","
                + ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_TIPO_TRABAJO + "))");

        //Crea Tabla Zonas
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.Zona.TABLE_NAME + " ("
                + ContratoCorpicoApp.Zona.ZON_ID + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Zona.ZON_DESCRIPCION + " TEXT NULL, "
                + ContratoCorpicoApp.Zona.ZON_OBSERVACION + " TEXT NULL, "
                + ContratoCorpicoApp.Zona.ZON_FECHA_UPDATE + " TEXT NULL, "
                + "UNIQUE (" + ContratoCorpicoApp.Zona.ZON_ID + "))");
        //Crea Tabla Punto Zonas
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.PuntoZona.TABLE_NAME + " ("
                + ContratoCorpicoApp.PuntoZona.PZON_ID + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.PuntoZona.PZON_ID_ZONA + " INTEGER NULL, "
                + ContratoCorpicoApp.PuntoZona.PZON_INDICE + " INTEGER NULL, "
                + ContratoCorpicoApp.PuntoZona.PZON_LATITUD + " DOUBLE NULL, "
                + ContratoCorpicoApp.PuntoZona.PZON_LONGITUD + " DOUBLE NULL, "
                + ContratoCorpicoApp.PuntoZona.PZON_FECHA_UPDATE + " TEXT NULL, "
                + "UNIQUE (" + ContratoCorpicoApp.PuntoZona.PZON_ID + "))");

        //Crea Tabla Cuadrillas
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.Cuadrilla.TABLE_NAME + " ("
                + ContratoCorpicoApp.Cuadrilla.CU_ID + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Cuadrilla.CU_TIPO_CUADRILLA + " INTEGER NULL, "
                + ContratoCorpicoApp.Cuadrilla.CU_OPERARIO	+ " INTEGER NULL, "
                + ContratoCorpicoApp.Cuadrilla.CU_FECHA	+ " TEXT NULL, "
                + ContratoCorpicoApp.Cuadrilla.CU_FECHA_UPDATE	+ " TEXT NULL, "
                + ContratoCorpicoApp.Cuadrilla.CU_SERVICIO	+ " INTEGER NULL, "
                + ContratoCorpicoApp.Cuadrilla.CU_SECTOR	+ " INTEGER NULL, "
                + ContratoCorpicoApp.Cuadrilla.CU_SYNC	+ " INTEGER NULL, "
                + "UNIQUE (" + ContratoCorpicoApp.Cuadrilla.CU_ID + "))");

        //Crea Tabla Fotos
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.Foto.TABLE_NAME + " ("
                + ContratoCorpicoApp.Foto.OTF_NUMERO + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Foto.OTF_NFOTO + " INTEGER NULL, "
                + ContratoCorpicoApp.Foto.OTF_FECHA + " TEXT NULL, "
                + ContratoCorpicoApp.Foto.OTF_OBSERVACIONES + " TEXT NULL, "
                + ContratoCorpicoApp.Foto.OTF_FECHA_UPDATE + " TEXT NULL, "
                + ContratoCorpicoApp.Foto.OTF_ID_USER + " TEXT NULL, "
                + ContratoCorpicoApp.Foto.OTF_PATH + " TEXT NULL, "
                + ContratoCorpicoApp.Foto.OTF_SECTOR + " INTEGER NULL, "
                + ContratoCorpicoApp.Foto.OTF_SERVICIO + " INTEGER NULL, "
                + "UNIQUE (" + ContratoCorpicoApp.Foto.OTF_NUMERO + ","
                + ContratoCorpicoApp.Foto.OTF_NFOTO + "))");

        //Crea Tabla Estados
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.Estado.TABLE_NAME + " ("
                + ContratoCorpicoApp.Estado.OOS_ID + " TEXT NOT NULL, "
                + ContratoCorpicoApp.Estado.OOS_DESCRIPCION + " TEXT NULL, "
                + ContratoCorpicoApp.Estado.OOS_FECHA_UPDATE + " TEXT NULL, "
                + "UNIQUE (" + ContratoCorpicoApp.Estado.OOS_ID + "))");

        //Crea Tabla Notas
        db.execSQL("CREATE TABLE " + ContratoCorpicoApp.Nota.TABLE_NAME + " ("
                + ContratoCorpicoApp.Nota.NOV_ID + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Nota.NOV_FECHA + " TEXT NULL, "
                + ContratoCorpicoApp.Nota.NOV_DESCRIPCION + " TEXT NULL, "
                + ContratoCorpicoApp.Nota.NOV_SERVICIO + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Nota.NOV_SECTOR + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Nota.NOV_OPERARIO + " TEXT NOT NULL, "
                + ContratoCorpicoApp.Nota.NOV_IDOPERARIO + " INTEGER NOT NULL, "
                + ContratoCorpicoApp.Nota.NOV_FECHA_UPDATE + " TEXT NULL, "
                + ContratoCorpicoApp.Nota.NOV_SYNC + " INTEGER NULL, "
                + "UNIQUE (" + ContratoCorpicoApp.Nota.NOV_ID + "))");
        }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //TODO: ATENCION ACA PARA LOS POSIBLS CAMBIOS CUANDO HAGA MODIFICACIONES EN EL FUTURO Y NO PISAR LAS TABLAS
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.ORDEN_OPERATIVA);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TIPO_TRABAJO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.MOTIVO_TRABAJO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TIPO_CUADRILLA);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.CLIENTE);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TURNO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.ETAPA);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.EMPRESA_CONTRATISTA);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.ESTADO_OPERATIVO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.LOCALIDAD);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.MEDIDOR);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.MEDIDOR_CLASE);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.MEDIDOR_MARCA);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.MEDIDOR_MODELO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.MEDIDOR_TIPO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.PUNTO_MEDICION_MEDIDOR);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.OPERARIO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TIPO_CONEXION);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TIPO_CONSUMO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TIPO_EMPRESA);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TIPO_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.SECTOR);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.SUMINISTRO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.SUMINISTRO_TIPO_EMPRESA);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.SUMINISTRO_POSICION_GLOBAL);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TIPO_TRABAJO_CUADRILLA);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.ZONA);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.PUNTO_ZONA);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.FOTO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.CUADRILLA);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.ESTADO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.NOTA);
        onCreate(db);
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys=ON");
            }
        }
    }
}
