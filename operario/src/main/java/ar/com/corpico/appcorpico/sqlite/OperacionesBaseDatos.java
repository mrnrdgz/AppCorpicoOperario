package ar.com.corpico.appcorpico.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestCliente;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestEmpresaContratista;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestEstadoOperativo;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestEtapa;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestLocalidad;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMedidor;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMedidorClase;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMedidorMarca;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMedidorModelo;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMedidorTipo;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMotivoTrabajoOperativo;
import ar.com.corpico.appcorpico.operarios.data.entity.RestOperario;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestPuntoMedicionMedidor;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestSector;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestSuministro;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestSuministroPosicionGlobal;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestSuministroTipoEmpresa;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoConexion;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoConsumo;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoCuadrilla;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoEmpresa;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoTrabajo;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoTrabajoCuadrilla;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoUsuario;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTurnos;

import static ar.com.corpico.appcorpico.sqlite.BaseDatosCorpicoApp.Tablas.CLIENTE;
import static ar.com.corpico.appcorpico.sqlite.BaseDatosCorpicoApp.Tablas.EMPRESA_CONTRATISTA;
import static ar.com.corpico.appcorpico.sqlite.BaseDatosCorpicoApp.Tablas.ESTADO_OPERATIVO;
import static ar.com.corpico.appcorpico.sqlite.BaseDatosCorpicoApp.Tablas.ETAPA;
import static ar.com.corpico.appcorpico.sqlite.BaseDatosCorpicoApp.Tablas.LOCALIDAD;
import static ar.com.corpico.appcorpico.sqlite.BaseDatosCorpicoApp.Tablas.MEDIDOR;
import static ar.com.corpico.appcorpico.sqlite.BaseDatosCorpicoApp.Tablas.MEDIDOR_CLASE;
import static ar.com.corpico.appcorpico.sqlite.BaseDatosCorpicoApp.Tablas.MEDIDOR_MARCA;
import static ar.com.corpico.appcorpico.sqlite.BaseDatosCorpicoApp.Tablas.MEDIDOR_MODELO;
import static ar.com.corpico.appcorpico.sqlite.BaseDatosCorpicoApp.Tablas.MEDIDOR_TIPO;
import static ar.com.corpico.appcorpico.sqlite.BaseDatosCorpicoApp.Tablas.MOTIVO_TRABAJO;
import static ar.com.corpico.appcorpico.sqlite.BaseDatosCorpicoApp.Tablas.OPERARIO;
import static ar.com.corpico.appcorpico.sqlite.BaseDatosCorpicoApp.Tablas.ORDEN_OPERATIVA;
import static ar.com.corpico.appcorpico.sqlite.BaseDatosCorpicoApp.Tablas.PUNTO_MEDICION_MEDIDOR;
import static ar.com.corpico.appcorpico.sqlite.BaseDatosCorpicoApp.Tablas.SECTOR;
import static ar.com.corpico.appcorpico.sqlite.BaseDatosCorpicoApp.Tablas.SUMINISTRO;
import static ar.com.corpico.appcorpico.sqlite.BaseDatosCorpicoApp.Tablas.SUMINISTRO_POSICION_GLOBAL;
import static ar.com.corpico.appcorpico.sqlite.BaseDatosCorpicoApp.Tablas.SUMINISTRO_TIPO_EMPRESA;
import static ar.com.corpico.appcorpico.sqlite.BaseDatosCorpicoApp.Tablas.TIPO_CONEXION;
import static ar.com.corpico.appcorpico.sqlite.BaseDatosCorpicoApp.Tablas.TIPO_CONSUMO;
import static ar.com.corpico.appcorpico.sqlite.BaseDatosCorpicoApp.Tablas.TIPO_CUADRILLA;
import static ar.com.corpico.appcorpico.sqlite.BaseDatosCorpicoApp.Tablas.TIPO_EMPRESA;
import static ar.com.corpico.appcorpico.sqlite.BaseDatosCorpicoApp.Tablas.TIPO_TRABAJO;
import static ar.com.corpico.appcorpico.sqlite.BaseDatosCorpicoApp.Tablas.TIPO_TRABAJO_CUADRILLA;
import static ar.com.corpico.appcorpico.sqlite.BaseDatosCorpicoApp.Tablas.TIPO_USUARIO;
import static ar.com.corpico.appcorpico.sqlite.BaseDatosCorpicoApp.Tablas.TURNO;


/**
 * Created by Administrador on 20/02/2018.
 */

public final class OperacionesBaseDatos {
    private static BaseDatosCorpicoApp baseDatos;

    private static OperacionesBaseDatos instancia = new OperacionesBaseDatos();

    private OperacionesBaseDatos() {
    }

    public static OperacionesBaseDatos obtenerInstancia(Context contexto) {
        if (baseDatos == null) {
            baseDatos = new BaseDatosCorpicoApp(contexto);
        }
        return instancia;
    }

    //ORDENES OPERATIVAS
    public Cursor obtenerOrdenesOperativas(){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(ORDEN_OPERATIVA);
        return builder.query(db,null,null,null,null,null,null);
    }
    public Cursor obtenerOrdenOperativaPorId(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String seleccion = String.format("%s=?", ContratoCorpicoApp.OrdenesOperativas.numero);
        String [] selectionArgs = {String.valueOf(id)};

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(ORDEN_OPERATIVA);

        return builder.query(db,null,seleccion,selectionArgs,null,null,null);
    }
   /* public int  insertarOrdenOperativa(_RestOrder ordenoperativa) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_SUCURSAL, ordenoperativa.getSUCURSAL());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_CENTRO_ATENCION, ordenoperativa.getCENTRO_ATENCION());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_NUMERO, ordenoperativa.getNUMERO());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_EMPRESA,ordenoperativa.getEMPRESA() );
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_CLIENTE, ordenoperativa.getCLIENTE());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_SUMINISTRO, ordenoperativa.getSUMINISTRO());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_PUNTO_MEDICION, ordenoperativa.getPUNTO_MEDICION() );
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_FECHA_SOLICITUD, ordenoperativa.getFECHA_SOLICITUD());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_TIPO_TRABAJO, ordenoperativa.getTIPO_TRABAJO() );
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_MOTIVO_TRABAJO, ordenoperativa.getMOTIVO_TRABAJO() );
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_FECHA_VENCIMIENTO, ordenoperativa.getFECHA_VENCIMIENTO());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_OBSERVACIONES_AL_OPE, ordenoperativa.getOBSERVACIONES_AL_OPE());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_ID_USER_GENERO, ordenoperativa.getID_USER_GENERO());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_FORMA_GENERACION, ordenoperativa.getFORMA_GENERACION());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_ESTADO_OPERATIVO_ANTES_GENERAR, ordenoperativa.getESTADO_OPERATIVO_ANTES_GENERAR());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_FECHA_EST_OPERATIVO_ANTES_GENERAR,ordenoperativa.getFECHA_EST_OPERATIVO_ANTES_GENERAR());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_ESTADO_OPERATIVO_LUEGO_GENERAR, ordenoperativa.getESTADO_OPERATIVO_ANTES_GENERAR());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_EMPRESA_CONTRATISTA, ordenoperativa.getEMPRESA_CONTRATISTA());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_FECHA_ASIGNACION, ordenoperativa.getFECHA_ASIGNACION() );
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_ID_USER_ASIGNO, ordenoperativa.getID_USER_ASIGNO() );
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_FECHA_CULMINACION, ordenoperativa.getFECHA_CULMINACION());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_OPERARIO, ordenoperativa.getOPERARIO() );
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_OBSERVACION_DEL_OPE, ordenoperativa.getOBSERVACION_DEL_OPE());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_ID_USER_RETENCION,ordenoperativa.getID_USER_RETENCION() );
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_FECHA_RETENCION,ordenoperativa.getFECHA_RETENCION());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_MOTIVO_RETENCION, ordenoperativa.getMOTIVO_RETENCION());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_ID_USER_LIBERACION, ordenoperativa.getID_USER_LIBERACION());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_FECHA_LIBERACION, ordenoperativa.getFECHA_LIBERACION() );
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_OPERARIO_REALIZACION, ordenoperativa.getOPERARIO_REALIZACION());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_ESTADO, ordenoperativa.getESTADO() );
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_ID_USER, ordenoperativa.getID_USER());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_FECHA_UPDATE, ordenoperativa.getFECHA_UPDATE());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_FECHA_ALTA, ordenoperativa.getFECHA_ALTA());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_ID_USER_ALTA, ordenoperativa.getID_USER_ALTA() );
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_LOTE_REPLICACION, ordenoperativa.getLOTE_REPLICACION() );*

        db.insertOrThrow(ORDEN_OPERATIVA, null, valores);
        //return String.format("%s#%d",ordenoperativa.sucursal,ordenoperativa.centro_atencion,ordenoperativa.numero);
        return ordenoperativa.getNUMERO();

    }
    public boolean actualizarOrdenOperativa (_RestOrder ordennueva){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_SUCURSAL, ordennueva.getSUCURSAL());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_CENTRO_ATENCION, ordennueva.getCENTRO_ATENCION());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_NUMERO, ordennueva.getNUMERO());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_EMPRESA,ordennueva.getEMPRESA() );
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_CLIENTE, ordennueva.getCLIENTE());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_SUMINISTRO, ordennueva.getSUMINISTRO());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_PUNTO_MEDICION, ordennueva.getPUNTO_MEDICION() );
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_FECHA_SOLICITUD, ordennueva.getFECHA_SOLICITUD());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_TIPO_TRABAJO, ordennueva.getTIPO_TRABAJO() );
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_MOTIVO_TRABAJO, ordennueva.getMOTIVO_TRABAJO() );
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_FECHA_VENCIMIENTO, ordennueva.getFECHA_VENCIMIENTO());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_OBSERVACIONES_AL_OPE, ordennueva.getOBSERVACIONES_AL_OPE());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_ID_USER_GENERO, ordennueva.getID_USER_GENERO());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_FORMA_GENERACION, ordennueva.getFORMA_GENERACION());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_ESTADO_OPERATIVO_ANTES_GENERAR, ordennueva.getESTADO_OPERATIVO_ANTES_GENERAR());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_FECHA_EST_OPERATIVO_ANTES_GENERAR,ordennueva.getFECHA_EST_OPERATIVO_ANTES_GENERAR());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_ESTADO_OPERATIVO_LUEGO_GENERAR, ordennueva.getESTADO_OPERATIVO_ANTES_GENERAR());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_EMPRESA_CONTRATISTA, ordennueva.getEMPRESA_CONTRATISTA());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_FECHA_ASIGNACION, ordennueva.getFECHA_ASIGNACION() );
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_ID_USER_ASIGNO, ordennueva.getID_USER_ASIGNO() );
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_FECHA_CULMINACION, ordennueva.getFECHA_CULMINACION());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_OPERARIO, ordennueva.getOPERARIO() );
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_OBSERVACION_DEL_OPE, ordennueva.getOBSERVACION_DEL_OPE());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_ID_USER_RETENCION,ordennueva.getID_USER_RETENCION() );
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_FECHA_RETENCION,ordennueva.getFECHA_RETENCION());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_MOTIVO_RETENCION, ordennueva.getMOTIVO_RETENCION());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_ID_USER_LIBERACION, ordennueva.getID_USER_LIBERACION());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_FECHA_LIBERACION, ordennueva.getFECHA_LIBERACION() );
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_OPERARIO_REALIZACION, ordennueva.getOPERARIO_REALIZACION());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_ESTADO, ordennueva.getESTADO() );
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_ID_USER, ordennueva.getID_USER());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_FECHA_UPDATE, ordennueva.getFECHA_UPDATE());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_FECHA_ALTA, ordennueva.getFECHA_ALTA());
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_ID_USER_ALTA, ordennueva.getID_USER_ALTA() );
        valores.put(ContratoCorpicoApp.OrdenesOperativas.OTR_LOTE_REPLICACION, ordennueva.getLOTE_REPLICACION() );

        String whereClause = String.format("%s=?", ContratoCorpicoApp.OrdenesOperativas.OTR_NUMERO);
        String [] whereArgs = {String.valueOf(ordennueva.getNUMERO())};

        int resultado = db.update(BaseDatosCorpicoApp.Tablas.ORDEN_OPERATIVA,valores,whereClause,whereArgs);
        return resultado > 0;
    }
    public boolean eliminarOrdenOperativa (int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = ContratoCorpicoApp.OrdenesOperativas.OTR_NUMERO + "=?";
        String [] whereArgs = {String.valueOf(id)};
        int resultado = db.delete(BaseDatosCorpicoApp.Tablas.ORDEN_OPERATIVA,whereClause,whereArgs);
        return resultado >0;
    }*/

    // TIPOS TRABAJOS
    public Cursor obtenerTiposTrabajos(){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(TIPO_TRABAJO);
        return builder.query(db,null,null,null,null,null,null);
    }
    public Cursor obtenerTipoTrabajoPorId(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String seleccion = String.format("%s=?", ContratoCorpicoApp.TiposTrabajo.TIT_ID);
        String [] selectionArgs = {String.valueOf(id)};

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(TIPO_TRABAJO);

        return builder.query(db,null,seleccion,selectionArgs,null,null,null);
    }
    public int  insertarTipoTrabajo(RestTipoTrabajo tipotrabajo) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.TiposTrabajo.TIT_ID, tipotrabajo.getId());
        valores.put(ContratoCorpicoApp.TiposTrabajo.TIT_DESCRIPCION, tipotrabajo.getDescripcion());
        valores.put(ContratoCorpicoApp.TiposTrabajo.TIT_ABREVIATURA, tipotrabajo.getAbreviatura());
        valores.put(ContratoCorpicoApp.TiposTrabajo.TIT_TIPO_EMPRESA, tipotrabajo.getTipoEmpresa());
        valores.put(ContratoCorpicoApp.TiposTrabajo.TIT_CLASIFICACION_TRABAJO, tipotrabajo.getClasificacionTrabajo());
        valores.put(ContratoCorpicoApp.TiposTrabajo.TIT_PERMITE_ELIMINAR, tipotrabajo.getPermiteEliminar());
        valores.put(ContratoCorpicoApp.TiposTrabajo.TIT_ACTIVO, tipotrabajo.getActivo());
        valores.put(ContratoCorpicoApp.TiposTrabajo.TIT_ID_USER, tipotrabajo.getUserId());
        valores.put(ContratoCorpicoApp.TiposTrabajo.TIT_FECHA_UPDATE, tipotrabajo.getFechaUpdate());
        valores.put(ContratoCorpicoApp.TiposTrabajo.TIT_LOTE_REPLICACION, tipotrabajo.getLoteReplicacion());

        db.insertOrThrow(TIPO_TRABAJO, null, valores);
        return tipotrabajo.getId();
    }
    public boolean actualizarTipoTrabajo(RestTipoTrabajo tipotrabajonuevo ){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        //valores.put(ContratoCorpicoApp.TiposTrabajo.TIT_ID, tipotrabajonuevo.id);
        valores.put(ContratoCorpicoApp.TiposTrabajo.TIT_ID, tipotrabajonuevo.getId());
        valores.put(ContratoCorpicoApp.TiposTrabajo.TIT_DESCRIPCION, tipotrabajonuevo.getDescripcion());
        valores.put(ContratoCorpicoApp.TiposTrabajo.TIT_ABREVIATURA, tipotrabajonuevo.getAbreviatura());
        valores.put(ContratoCorpicoApp.TiposTrabajo.TIT_TIPO_EMPRESA, tipotrabajonuevo.getTipoEmpresa());
        valores.put(ContratoCorpicoApp.TiposTrabajo.TIT_CLASIFICACION_TRABAJO, tipotrabajonuevo.getClasificacionTrabajo());
        valores.put(ContratoCorpicoApp.TiposTrabajo.TIT_PERMITE_ELIMINAR, tipotrabajonuevo.getPermiteEliminar());
        valores.put(ContratoCorpicoApp.TiposTrabajo.TIT_ACTIVO, tipotrabajonuevo.getActivo());
        valores.put(ContratoCorpicoApp.TiposTrabajo.TIT_ID_USER, tipotrabajonuevo.getUserId());
        valores.put(ContratoCorpicoApp.TiposTrabajo.TIT_FECHA_UPDATE, tipotrabajonuevo.getFechaUpdate());
        valores.put(ContratoCorpicoApp.TiposTrabajo.TIT_LOTE_REPLICACION, tipotrabajonuevo.getLoteReplicacion());


        String whereClause = String.format("%s=?", ContratoCorpicoApp.TiposTrabajo.TIT_ID);
        String [] whereArgs = {String.valueOf(tipotrabajonuevo.getId())};

        int resultado = db.update(BaseDatosCorpicoApp.Tablas.TIPO_TRABAJO,valores,whereClause,whereArgs);
        return resultado > 0;
    }
    public boolean eliminarTipoTrabajo(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = ContratoCorpicoApp.TiposTrabajo.TIT_ID + "=?";
        String [] whereArgs = {String.valueOf(id)};
        int resultado = db.delete(BaseDatosCorpicoApp.Tablas.TIPO_TRABAJO,whereClause,whereArgs);
        return resultado >0;
    }

    // MOTIVOS OPERATIVOS
    public Cursor obtenerMotivosTrabajos(){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(MOTIVO_TRABAJO);
        return builder.query(db,null,null,null,null,null,null);
    }
    public Cursor obtenerMotivoTrabajoPorId(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String seleccion = String.format("%s=?", ContratoCorpicoApp.MotivosTrabajo.MTR_ID);
        String [] selectionArgs = {String.valueOf(id)};

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(MOTIVO_TRABAJO);

        return builder.query(db,null,seleccion,selectionArgs,null,null,null);
    }
    public int  insertarMotivoTrabajo(RestMotivoTrabajoOperativo motivotrabajo) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_ID, motivotrabajo.getMTR_ID());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_TIPO_TRABAJO, motivotrabajo.getMTR_TIPO_TRABAJO());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_DESCRIPCION, motivotrabajo.getMTR_DESCRIPCION());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_ABREVIATURA, motivotrabajo.getMTR_ABREVIATURA());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_DIAS_RESOLUCION, motivotrabajo.getMTR_DIAS_RESOLUCION());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_ALTERA_ESTADO_LUEGO_GENERAR, motivotrabajo.getMTR_ALTERA_ESTADO_LUEGO_GENERAR());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_ESTADO_LUEGO_GENERAR, motivotrabajo.getMTR_ESTADO_LUEGO_GENERAR());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_SOLICITA_COLOCACION, motivotrabajo.getMTR_SOLICITA_COLOCACION());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_SOLICITA_RETIRO, motivotrabajo.getMTR_SOLICITA_RETIRO());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_SOLICITA_INFORMATIVO, motivotrabajo.getMTR_SOLICITA_INFORMATIVO());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_SOLICITA_PRECINTO_HAB, motivotrabajo.getMTR_SOLICITA_PRECINTO_HAB());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_SOLICITA_PRECINTO_MED, motivotrabajo.getMTR_SOLICITA_PRECINTO_MED());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_SOLICITA_INSTALACION, motivotrabajo.getMTR_SOLICITA_INSTALACION());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_SOLICITA_MATERIALES, motivotrabajo.getMTR_SOLICITA_MATERIALES());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_PERMITE_ELIMINAR, motivotrabajo.getMTR_PERMITE_ELIMINAR());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_PROCESA_TOMAESTADO, motivotrabajo.getMTR_PROCESA_TOMAESTADO());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_ACTIVO, motivotrabajo.getMTR_ACTIVO());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_ID_USER, motivotrabajo.getMTR_ID_USER());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_FECHA_UPDATE, motivotrabajo.getMTR_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_LOTE_REPLICACION, motivotrabajo.getMTR_LOTE_REPLICACION());

        db.insertOrThrow(MOTIVO_TRABAJO, null, valores);
        return motivotrabajo.getMTR_ID();
    }
    public boolean actualizarMotivoTrabajo(RestMotivoTrabajoOperativo motivotrabajonuevo ){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_ID, motivotrabajonuevo.getMTR_ID());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_TIPO_TRABAJO, motivotrabajonuevo.getMTR_TIPO_TRABAJO());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_DESCRIPCION, motivotrabajonuevo.getMTR_DESCRIPCION());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_ABREVIATURA, motivotrabajonuevo.getMTR_ABREVIATURA());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_DIAS_RESOLUCION, motivotrabajonuevo.getMTR_DIAS_RESOLUCION());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_ALTERA_ESTADO_LUEGO_GENERAR, motivotrabajonuevo.getMTR_ALTERA_ESTADO_LUEGO_GENERAR());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_ESTADO_LUEGO_GENERAR, motivotrabajonuevo.getMTR_ESTADO_LUEGO_GENERAR());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_SOLICITA_COLOCACION, motivotrabajonuevo.getMTR_SOLICITA_COLOCACION());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_SOLICITA_RETIRO, motivotrabajonuevo.getMTR_SOLICITA_RETIRO());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_SOLICITA_INFORMATIVO, motivotrabajonuevo.getMTR_SOLICITA_INFORMATIVO());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_SOLICITA_PRECINTO_HAB, motivotrabajonuevo.getMTR_SOLICITA_PRECINTO_HAB());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_SOLICITA_PRECINTO_MED, motivotrabajonuevo.getMTR_SOLICITA_PRECINTO_MED());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_SOLICITA_INSTALACION, motivotrabajonuevo.getMTR_SOLICITA_INSTALACION());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_SOLICITA_MATERIALES, motivotrabajonuevo.getMTR_SOLICITA_MATERIALES());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_PERMITE_ELIMINAR, motivotrabajonuevo.getMTR_PERMITE_ELIMINAR());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_PROCESA_TOMAESTADO, motivotrabajonuevo.getMTR_PROCESA_TOMAESTADO());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_ACTIVO, motivotrabajonuevo.getMTR_ACTIVO());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_ID_USER, motivotrabajonuevo.getMTR_ID_USER());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_FECHA_UPDATE, motivotrabajonuevo.getMTR_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.MotivosTrabajo.MTR_LOTE_REPLICACION, motivotrabajonuevo.getMTR_LOTE_REPLICACION());

        String whereClause = String.format("%s=?", ContratoCorpicoApp.MotivosTrabajo.MTR_ID);
        String [] whereArgs = {String.valueOf(motivotrabajonuevo.getMTR_ID())};

        int resultado = db.update(BaseDatosCorpicoApp.Tablas.MOTIVO_TRABAJO,valores,whereClause,whereArgs);
        return resultado > 0;
    }
    public boolean eliminarMotivoTrabajo(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = ContratoCorpicoApp.MotivosTrabajo.MTR_ID + "=?";
        String [] whereArgs = {String.valueOf(id)};
        int resultado = db.delete(BaseDatosCorpicoApp.Tablas.MOTIVO_TRABAJO,whereClause,whereArgs);
        return resultado >0;
    }

    // TIPOS CUADRILLAS
    public Cursor obtenerTiposCuadrillas(){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(TIPO_CUADRILLA);
        return builder.query(db,null,null,null,null,null,null);
    }
    public Cursor obtenerTipoCuadrillaPorId(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String seleccion = String.format("%s=?", ContratoCorpicoApp.TiposCuadrilla.TC_ID);
        String [] selectionArgs = {String.valueOf(id)};

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(TIPO_CUADRILLA);

        return builder.query(db,null,seleccion,selectionArgs,null,null,null);
    }
    public int  insertarTipoCuadrilla(RestTipoCuadrilla tipocuadrilla) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.TiposCuadrilla.TC_ID, tipocuadrilla.getID());
        valores.put(ContratoCorpicoApp.TiposCuadrilla.TC_ID_SERVICIO, tipocuadrilla.getSERVICIO());
        valores.put(ContratoCorpicoApp.TiposCuadrilla.TC_ID_SECTOR, tipocuadrilla.getSECTOR());
        valores.put(ContratoCorpicoApp.TiposCuadrilla.TC_DESCRIPCION, tipocuadrilla.getTipoCuadrilla());
        valores.put(ContratoCorpicoApp.TiposCuadrilla.TC_FECHA_UPDATE, tipocuadrilla.getTipoCuadrillaUpdate());

        db.insertOrThrow(TIPO_CUADRILLA, null, valores);
        return tipocuadrilla.getID();
    }
    public boolean actualizarTipoCuadrilla(RestTipoCuadrilla tipocuadrillanueva ){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.TiposCuadrilla.TC_ID, tipocuadrillanueva.getID());
        valores.put(ContratoCorpicoApp.TiposCuadrilla.TC_ID_SERVICIO, tipocuadrillanueva.getSERVICIO());
        valores.put(ContratoCorpicoApp.TiposCuadrilla.TC_ID_SECTOR, tipocuadrillanueva.getSECTOR());
        valores.put(ContratoCorpicoApp.TiposCuadrilla.TC_DESCRIPCION, tipocuadrillanueva.getTipoCuadrilla());
        valores.put(ContratoCorpicoApp.TiposCuadrilla.TC_FECHA_UPDATE, tipocuadrillanueva.getTipoCuadrillaUpdate());

        String whereClause = String.format("%s=?", ContratoCorpicoApp.TiposCuadrilla.TC_ID);
        String [] whereArgs = {String.valueOf(tipocuadrillanueva.getID())};

        int resultado = db.update(BaseDatosCorpicoApp.Tablas.TIPO_CUADRILLA,valores,whereClause,whereArgs);
        return resultado > 0;
    }
    public boolean eliminarTipoCuadrilla(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = ContratoCorpicoApp.TiposCuadrilla.TC_ID + "=?";
        String [] whereArgs = {String.valueOf(id)};
        int resultado = db.delete(BaseDatosCorpicoApp.Tablas.TIPO_CUADRILLA,whereClause,whereArgs);
        return resultado >0;
    }

    // CLIENTES
    public Cursor obtenerClientes(){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(CLIENTE);
        return builder.query(db,null,null,null,null,null,null);
    }
    public Cursor obtenerClientePorId(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String seleccion = String.format("%s=?", ContratoCorpicoApp.Clientes.CLI_ID);
        String [] selectionArgs = {String.valueOf(id)};

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(CLIENTE);

        return builder.query(db,null,seleccion,selectionArgs,null,null,null);
    }
    public int  insertarCliente(RestCliente cliente) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.Clientes.CLI_EMPRESA, cliente.getCLI_EMPRESA());
        valores.put(ContratoCorpicoApp.Clientes.CLI_ID, cliente.getCLI_ID());
        valores.put(ContratoCorpicoApp.Clientes.CLI_FECHA_INGRESO, cliente.getCLI_FECHA_INGRESO());
        valores.put(ContratoCorpicoApp.Clientes.CLI_TITULAR, cliente.getCLI_TITULAR());
        valores.put(ContratoCorpicoApp.Clientes.CLI_LOCALIDAD_RESIDENCIA, cliente.getCLI_LOCALIDAD_RESIDENCIA());
        valores.put(ContratoCorpicoApp.Clientes.CLI_CODIGO_POSTAL_RESIDENCIA, cliente.getCLI_CODIGO_POSTAL_RESIDENCIA());
        valores.put(ContratoCorpicoApp.Clientes.CLI_CALLE_RESIDENCIA, cliente.getCLI_CALLE_RESIDENCIA());
        valores.put(ContratoCorpicoApp.Clientes.CLI_ALTURA_RESIDENCIA, cliente.getCLI_ALTURA_RESIDENCIA());
        valores.put(ContratoCorpicoApp.Clientes.CLI_PISO_RESIDENCIA, cliente.getCLI_PISO_RESIDENCIA());
        valores.put(ContratoCorpicoApp.Clientes.CLI_DEPARTAMENTO_RESIDENCIA, cliente.getCLI_DEPARTAMENTO_RESIDENCIA());
        valores.put(ContratoCorpicoApp.Clientes.CLI_TELEFONO_RESIDENCIA, cliente.getCLI_TELEFONO_RESIDENCIA());
        valores.put(ContratoCorpicoApp.Clientes.CLI_TELEFONO_CELULAR, cliente.getCLI_TELEFONO_RESIDENCIA());
        valores.put(ContratoCorpicoApp.Clientes.CLI_FAX, cliente.getCLI_FAX());
        valores.put(ContratoCorpicoApp.Clientes.CLI_E_MAIL, cliente.getCLI_E_MAIL());
        valores.put(ContratoCorpicoApp.Clientes.CLI_LOCALIDAD_LABORAL, cliente.getCLI_LOCALIDAD_LABORAL());
        valores.put(ContratoCorpicoApp.Clientes.CLI_CODIGO_POSTAL_LABORAL, cliente.getCLI_CODIGO_POSTAL_LABORAL());
        valores.put(ContratoCorpicoApp.Clientes.CLI_CALLE_LABORAL, cliente.getCLI_CALLE_LABORAL());
        valores.put(ContratoCorpicoApp.Clientes.CLI_ALTURA_LABORAL, cliente.getCLI_ALTURA_LABORAL());
        valores.put(ContratoCorpicoApp.Clientes.CLI_PISO_LABORAL, cliente.getCLI_PISO_LABORAL());
        valores.put(ContratoCorpicoApp.Clientes.CLI_DEPARTAMENTO_LABORAL, cliente.getCLI_DEPARTAMENTO_LABORAL());
        valores.put(ContratoCorpicoApp.Clientes.CLI_ANEXO, cliente.getCLI_ANEXO());
        valores.put(ContratoCorpicoApp.Clientes.CLI_TELEFONO_LABORAL, cliente.getCLI_TELEFONO_LABORAL());
        valores.put(ContratoCorpicoApp.Clientes.CLI_SITUACION_IVA, cliente.getCLI_SITUACION_IVA());
        valores.put(ContratoCorpicoApp.Clientes.CLI_CUIT, cliente.getCLI_CUIT());
        valores.put(ContratoCorpicoApp.Clientes.CLI_SITUACION_IIBB, cliente.getCLI_SITUACION_IVA());
        valores.put(ContratoCorpicoApp.Clientes.CLI_NUMERO_IIBB, cliente.getCLI_NUMERO_IIBB());
        valores.put(ContratoCorpicoApp.Clientes.CLI_EXENCION_PERCEPCION_IVA, cliente.getCLI_EXENCION_PERCEPCION_IVA());
        valores.put(ContratoCorpicoApp.Clientes.CLI_EXENCION_NACIONAL, cliente.getCLI_EXENCION_NACIONAL());
        valores.put(ContratoCorpicoApp.Clientes.CLI_EXENCION_PROVINCIAL, cliente.getCLI_EXENCION_PROVINCIAL());
        valores.put(ContratoCorpicoApp.Clientes.CLI_EXENCION_MUNICIPAL, cliente.getCLI_EXENCION_MUNICIPAL());
        valores.put(ContratoCorpicoApp.Clientes.CLI_POTENCIAL, cliente.getCLI_POTENCIAL());
        valores.put(ContratoCorpicoApp.Clientes.CLI_CLASIFICACION, cliente.getCLI_CLASIFICACION());
        valores.put(ContratoCorpicoApp.Clientes.CLI_ESTADO_CIVIL, cliente.getCLI_ESTADO_CIVIL());
        valores.put(ContratoCorpicoApp.Clientes.CLI_PROFESION, cliente.getCLI_PROFESION());
        valores.put(ContratoCorpicoApp.Clientes.CLI_FECHA_NACIMIENTO, cliente.getCLI_FECHA_NACIMIENTO());
        valores.put(ContratoCorpicoApp.Clientes.CLI_NACIONALIDAD, cliente.getCLI_NACIONALIDAD());
        valores.put(ContratoCorpicoApp.Clientes.CLI_LUGAR_NACIMIENTO, cliente.getCLI_LUGAR_NACIMIENTO());
        valores.put(ContratoCorpicoApp.Clientes.CLI_SEXO, cliente.getCLI_SEXO());
        valores.put(ContratoCorpicoApp.Clientes.CLI_ESTADO, cliente.getCLI_ESTADO());
        valores.put(ContratoCorpicoApp.Clientes.CLI_CLAVE_PERSONAL, cliente.getCLI_CLAVE_PERSONAL());
        valores.put(ContratoCorpicoApp.Clientes.CLI_ID_USER, cliente.getCLI_ID_USER());
        valores.put(ContratoCorpicoApp.Clientes.CLI_FECHA_UPDATE, cliente.getCLI_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.Clientes.CLI_LOTE_REPLICACION, cliente.getCLI_LOTE_REPLICACION());

        db.insertOrThrow(CLIENTE, null, valores);
        return cliente.getCLI_ID();
    }
    public boolean actualizarCliente(RestCliente clientenuevo ){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.Clientes.CLI_EMPRESA, clientenuevo.getCLI_EMPRESA());
        valores.put(ContratoCorpicoApp.Clientes.CLI_ID, clientenuevo.getCLI_ID());
        valores.put(ContratoCorpicoApp.Clientes.CLI_FECHA_INGRESO, clientenuevo.getCLI_FECHA_INGRESO());
        valores.put(ContratoCorpicoApp.Clientes.CLI_TITULAR, clientenuevo.getCLI_TITULAR());
        valores.put(ContratoCorpicoApp.Clientes.CLI_LOCALIDAD_RESIDENCIA, clientenuevo.getCLI_LOCALIDAD_RESIDENCIA());
        valores.put(ContratoCorpicoApp.Clientes.CLI_CODIGO_POSTAL_RESIDENCIA, clientenuevo.getCLI_CODIGO_POSTAL_RESIDENCIA());
        valores.put(ContratoCorpicoApp.Clientes.CLI_CALLE_RESIDENCIA, clientenuevo.getCLI_CALLE_RESIDENCIA());
        valores.put(ContratoCorpicoApp.Clientes.CLI_ALTURA_RESIDENCIA, clientenuevo.getCLI_ALTURA_RESIDENCIA());
        valores.put(ContratoCorpicoApp.Clientes.CLI_PISO_RESIDENCIA, clientenuevo.getCLI_PISO_RESIDENCIA());
        valores.put(ContratoCorpicoApp.Clientes.CLI_DEPARTAMENTO_RESIDENCIA, clientenuevo.getCLI_DEPARTAMENTO_RESIDENCIA());
        valores.put(ContratoCorpicoApp.Clientes.CLI_TELEFONO_RESIDENCIA, clientenuevo.getCLI_TELEFONO_RESIDENCIA());
        valores.put(ContratoCorpicoApp.Clientes.CLI_TELEFONO_CELULAR, clientenuevo.getCLI_TELEFONO_RESIDENCIA());
        valores.put(ContratoCorpicoApp.Clientes.CLI_FAX, clientenuevo.getCLI_FAX());
        valores.put(ContratoCorpicoApp.Clientes.CLI_E_MAIL, clientenuevo.getCLI_E_MAIL());
        valores.put(ContratoCorpicoApp.Clientes.CLI_LOCALIDAD_LABORAL, clientenuevo.getCLI_LOCALIDAD_LABORAL());
        valores.put(ContratoCorpicoApp.Clientes.CLI_CODIGO_POSTAL_LABORAL, clientenuevo.getCLI_CODIGO_POSTAL_LABORAL());
        valores.put(ContratoCorpicoApp.Clientes.CLI_CALLE_LABORAL, clientenuevo.getCLI_CALLE_LABORAL());
        valores.put(ContratoCorpicoApp.Clientes.CLI_ALTURA_LABORAL, clientenuevo.getCLI_ALTURA_LABORAL());
        valores.put(ContratoCorpicoApp.Clientes.CLI_PISO_LABORAL, clientenuevo.getCLI_PISO_LABORAL());
        valores.put(ContratoCorpicoApp.Clientes.CLI_DEPARTAMENTO_LABORAL, clientenuevo.getCLI_DEPARTAMENTO_LABORAL());
        valores.put(ContratoCorpicoApp.Clientes.CLI_ANEXO, clientenuevo.getCLI_ANEXO());
        valores.put(ContratoCorpicoApp.Clientes.CLI_TELEFONO_LABORAL, clientenuevo.getCLI_TELEFONO_LABORAL());
        valores.put(ContratoCorpicoApp.Clientes.CLI_SITUACION_IVA, clientenuevo.getCLI_SITUACION_IVA());
        valores.put(ContratoCorpicoApp.Clientes.CLI_CUIT, clientenuevo.getCLI_CUIT());
        valores.put(ContratoCorpicoApp.Clientes.CLI_SITUACION_IIBB, clientenuevo.getCLI_SITUACION_IVA());
        valores.put(ContratoCorpicoApp.Clientes.CLI_NUMERO_IIBB, clientenuevo.getCLI_NUMERO_IIBB());
        valores.put(ContratoCorpicoApp.Clientes.CLI_EXENCION_PERCEPCION_IVA, clientenuevo.getCLI_EXENCION_PERCEPCION_IVA());
        valores.put(ContratoCorpicoApp.Clientes.CLI_EXENCION_NACIONAL, clientenuevo.getCLI_EXENCION_NACIONAL());
        valores.put(ContratoCorpicoApp.Clientes.CLI_EXENCION_PROVINCIAL, clientenuevo.getCLI_EXENCION_PROVINCIAL());
        valores.put(ContratoCorpicoApp.Clientes.CLI_EXENCION_MUNICIPAL, clientenuevo.getCLI_EXENCION_MUNICIPAL());
        valores.put(ContratoCorpicoApp.Clientes.CLI_POTENCIAL, clientenuevo.getCLI_POTENCIAL());
        valores.put(ContratoCorpicoApp.Clientes.CLI_CLASIFICACION, clientenuevo.getCLI_CLASIFICACION());
        valores.put(ContratoCorpicoApp.Clientes.CLI_ESTADO_CIVIL, clientenuevo.getCLI_ESTADO_CIVIL());
        valores.put(ContratoCorpicoApp.Clientes.CLI_PROFESION, clientenuevo.getCLI_PROFESION());
        valores.put(ContratoCorpicoApp.Clientes.CLI_FECHA_NACIMIENTO, clientenuevo.getCLI_FECHA_NACIMIENTO());
        valores.put(ContratoCorpicoApp.Clientes.CLI_NACIONALIDAD, clientenuevo.getCLI_NACIONALIDAD());
        valores.put(ContratoCorpicoApp.Clientes.CLI_LUGAR_NACIMIENTO, clientenuevo.getCLI_LUGAR_NACIMIENTO());
        valores.put(ContratoCorpicoApp.Clientes.CLI_SEXO, clientenuevo.getCLI_SEXO());
        valores.put(ContratoCorpicoApp.Clientes.CLI_ESTADO, clientenuevo.getCLI_ESTADO());
        valores.put(ContratoCorpicoApp.Clientes.CLI_CLAVE_PERSONAL, clientenuevo.getCLI_CLAVE_PERSONAL());
        valores.put(ContratoCorpicoApp.Clientes.CLI_ID_USER, clientenuevo.getCLI_ID_USER());
        valores.put(ContratoCorpicoApp.Clientes.CLI_FECHA_UPDATE, clientenuevo.getCLI_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.Clientes.CLI_LOTE_REPLICACION, clientenuevo.getCLI_LOTE_REPLICACION());
        String whereClause = String.format("%s=?", ContratoCorpicoApp.Clientes.CLI_ID);
        String [] whereArgs = {String.valueOf(clientenuevo.getCLI_ID())};

        int resultado = db.update(BaseDatosCorpicoApp.Tablas.CLIENTE,valores,whereClause,whereArgs);
        return resultado > 0;
    }
    public boolean eliminarCliente(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = ContratoCorpicoApp.Clientes.CLI_ID + "=?";
        String [] whereArgs = {String.valueOf(id)};
        int resultado = db.delete(BaseDatosCorpicoApp.Tablas.CLIENTE,whereClause,whereArgs);
        return resultado >0;
    }

    // TURNOS
    public Cursor obtenerTurnos(){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(TURNO);
        return builder.query(db,null,null,null,null,null,null);
    }
    public Cursor obtenerTurnoPorId(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String seleccion = String.format("%s=?", ContratoCorpicoApp.Turnos.TUR_ORDEN);
        String [] selectionArgs = {String.valueOf(id)};

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(TURNO);

        return builder.query(db,null,seleccion,selectionArgs,null,null,null);
    }
    public int  insertarCliente(RestTurnos turno) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.Turnos.TUR_ORDEN, turno.getTUR_ORDEN());
        valores.put(ContratoCorpicoApp.Turnos.TUR_TURNO, turno.getTUR_TURNO());
        valores.put(ContratoCorpicoApp.Turnos.TUR_FECHA_UPDATE, turno.getTUR_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.Turnos.TUR_SERVICIO,turno.getTUR_SERVICIO());
        valores.put(ContratoCorpicoApp.Turnos.TUR_SECTOR, turno.getTUR_SECTOR());

        db.insertOrThrow(TURNO, null, valores);
        return turno.getTUR_ORDEN();
    }
    public boolean actualizarTurno(RestTurnos turnonuevo ){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.Turnos.TUR_ORDEN, turnonuevo.getTUR_ORDEN());
        valores.put(ContratoCorpicoApp.Turnos.TUR_TURNO, turnonuevo.getTUR_TURNO());
        valores.put(ContratoCorpicoApp.Turnos.TUR_FECHA_UPDATE, turnonuevo.getTUR_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.Turnos.TUR_SERVICIO,turnonuevo.getTUR_SERVICIO());
        valores.put(ContratoCorpicoApp.Turnos.TUR_SECTOR, turnonuevo.getTUR_SECTOR());

        String whereClause = String.format("%s=?", ContratoCorpicoApp.Turnos.TUR_ORDEN);
        String [] whereArgs = {String.valueOf(turnonuevo.getTUR_ORDEN())};

        int resultado = db.update(BaseDatosCorpicoApp.Tablas.TURNO,valores,whereClause,whereArgs);
        return resultado > 0;
    }
    public boolean eliminarTurno(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = ContratoCorpicoApp.Turnos.TUR_ORDEN + "=?";
        String [] whereArgs = {String.valueOf(id)};
        int resultado = db.delete(BaseDatosCorpicoApp.Tablas.TURNO,whereClause,whereArgs);
        return resultado >0;
    }

    // ETAPAS
    public Cursor obtenerEtapas(){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(ETAPA);
        return builder.query(db,null,null,null,null,null,null);
    }
    public Cursor obtenerEtapaPorId(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String seleccion = String.format("%s=?", ContratoCorpicoApp.Etapas.ETA_ID);
        String [] selectionArgs = {String.valueOf(id)};

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(ETAPA);

        return builder.query(db,null,seleccion,selectionArgs,null,null,null);
    }
    public int  insertarEtapa(RestEtapa etapa) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.Etapas.ETA_ID, etapa.getETA_ID());
        valores.put(ContratoCorpicoApp.Etapas.ETA_FECHA, etapa.getETA_FECHA());
        valores.put(ContratoCorpicoApp.Etapas.ETA_ESTADO, etapa.getETA_ESTADO());
        valores.put(ContratoCorpicoApp.Etapas.ETA_OBSERVACION, etapa.getETA_OBSERVACION());
        valores.put(ContratoCorpicoApp.Etapas.ETA_USUARIO, etapa.getETA_USUARIO());
        valores.put(ContratoCorpicoApp.Etapas.ETA_FECHA_UPDATE, etapa.getETA_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.Etapas.ETA_SERVICIO, etapa.getETA_SERVICIO());
        valores.put(ContratoCorpicoApp.Etapas.ETA_SECTOR, etapa.getETA_SECTOR());

        db.insertOrThrow(ETAPA, null, valores);
        return etapa.getETA_ID();
    }
    public boolean actualizarEtapa(RestEtapa etapaonuevo ){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.Etapas.ETA_ID, etapaonuevo.getETA_ID());
        valores.put(ContratoCorpicoApp.Etapas.ETA_FECHA, etapaonuevo.getETA_FECHA());
        valores.put(ContratoCorpicoApp.Etapas.ETA_ESTADO, etapaonuevo.getETA_ESTADO());
        valores.put(ContratoCorpicoApp.Etapas.ETA_OBSERVACION, etapaonuevo.getETA_OBSERVACION());
        valores.put(ContratoCorpicoApp.Etapas.ETA_USUARIO, etapaonuevo.getETA_USUARIO());
        valores.put(ContratoCorpicoApp.Etapas.ETA_FECHA_UPDATE, etapaonuevo.getETA_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.Etapas.ETA_SERVICIO, etapaonuevo.getETA_SERVICIO());
        valores.put(ContratoCorpicoApp.Etapas.ETA_SECTOR, etapaonuevo.getETA_SECTOR());

        String whereClause = String.format("%s=?", ContratoCorpicoApp.Etapas.ETA_ID);
        String [] whereArgs = {String.valueOf(etapaonuevo.getETA_ID())};

        int resultado = db.update(BaseDatosCorpicoApp.Tablas.ETAPA,valores,whereClause,whereArgs);
        return resultado > 0;
    }
    public boolean eliminarEtapa(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = ContratoCorpicoApp.Etapas.ETA_ID + "=?";
        String [] whereArgs = {String.valueOf(id)};
        int resultado = db.delete(BaseDatosCorpicoApp.Tablas.ETAPA,whereClause,whereArgs);
        return resultado >0;
    }

    // EMPRESAS CONTRATISTA
    public Cursor obtenerEmpresasContratista(){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(EMPRESA_CONTRATISTA);
        return builder.query(db,null,null,null,null,null,null);
    }
    public Cursor obtenerEmpresaContratistaPorId(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String seleccion = String.format("%s=?", ContratoCorpicoApp.EmpresasContratista.EML_ID);
        String [] selectionArgs = {String.valueOf(id)};

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(EMPRESA_CONTRATISTA);

        return builder.query(db,null,seleccion,selectionArgs,null,null,null);
    }
    public int  insertarEmpresaContratista(RestEmpresaContratista empresaContratista) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.EmpresasContratista.EML_ID, empresaContratista.getEML_ID());
        valores.put(ContratoCorpicoApp.EmpresasContratista.EML_DESCRIPCION, empresaContratista.getEML_DESCRIPCION());
        valores.put(ContratoCorpicoApp.EmpresasContratista.EML_DIRECCION, empresaContratista.getEML_DIRECCION());
        valores.put(ContratoCorpicoApp.EmpresasContratista.EML_TELEFONO, empresaContratista.getEML_TELEFONO());
        valores.put(ContratoCorpicoApp.EmpresasContratista.EML_TIPO_TOMA, empresaContratista.getEML_TIPO_TOMA());
        valores.put(ContratoCorpicoApp.EmpresasContratista.EML_TIPO_EMPRESA, empresaContratista.getEML_TIPO_EMPRESA());
        valores.put(ContratoCorpicoApp.EmpresasContratista.EML_ACTIVO, empresaContratista.getEML_ACTIVO());
        valores.put(ContratoCorpicoApp.EmpresasContratista.EML_ID_USER, empresaContratista.getEML_ID_USER());
        valores.put(ContratoCorpicoApp.EmpresasContratista.EML_FECHA_UPDATE, empresaContratista.getEML_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.EmpresasContratista.EML_LOTE_REPLICACION , empresaContratista.getEML_LOTE_REPLICACION());

        db.insertOrThrow(EMPRESA_CONTRATISTA, null, valores);
        return empresaContratista.getEML_ID();
    }
    public boolean actualizarEmpresaContratista(RestEmpresaContratista empresaContratistanueva ){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.EmpresasContratista.EML_ID, empresaContratistanueva.getEML_ID());
        valores.put(ContratoCorpicoApp.EmpresasContratista.EML_DESCRIPCION, empresaContratistanueva.getEML_DESCRIPCION());
        valores.put(ContratoCorpicoApp.EmpresasContratista.EML_DIRECCION, empresaContratistanueva.getEML_DIRECCION());
        valores.put(ContratoCorpicoApp.EmpresasContratista.EML_TELEFONO, empresaContratistanueva.getEML_TELEFONO());
        valores.put(ContratoCorpicoApp.EmpresasContratista.EML_TIPO_TOMA, empresaContratistanueva.getEML_TIPO_TOMA());
        valores.put(ContratoCorpicoApp.EmpresasContratista.EML_TIPO_EMPRESA, empresaContratistanueva.getEML_TIPO_EMPRESA());
        valores.put(ContratoCorpicoApp.EmpresasContratista.EML_ACTIVO, empresaContratistanueva.getEML_ACTIVO());
        valores.put(ContratoCorpicoApp.EmpresasContratista.EML_ID_USER, empresaContratistanueva.getEML_ID_USER());
        valores.put(ContratoCorpicoApp.EmpresasContratista.EML_FECHA_UPDATE, empresaContratistanueva.getEML_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.EmpresasContratista.EML_LOTE_REPLICACION , empresaContratistanueva.getEML_LOTE_REPLICACION());

        String whereClause = String.format("%s=?", ContratoCorpicoApp.EmpresasContratista.EML_ID);
        String [] whereArgs = {String.valueOf(empresaContratistanueva.getEML_ID())};

        int resultado = db.update(BaseDatosCorpicoApp.Tablas.EMPRESA_CONTRATISTA,valores,whereClause,whereArgs);
        return resultado > 0;
    }
    public boolean eliminarEmpresaContratista(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = ContratoCorpicoApp.EmpresasContratista.EML_ID + "=?";
        String [] whereArgs = {String.valueOf(id)};
        int resultado = db.delete(BaseDatosCorpicoApp.Tablas.EMPRESA_CONTRATISTA,whereClause,whereArgs);
        return resultado >0;
    }

    // LOCALIDAD
    public Cursor obtenerLocalidad(){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(LOCALIDAD);
        return builder.query(db,null,null,null,null,null,null);
    }
    public Cursor obtenerLocaliadPorId(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String seleccion = String.format("%s=?", ContratoCorpicoApp.Localidad.LOC_ID);
        String [] selectionArgs = {String.valueOf(id)};

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(LOCALIDAD);

        return builder.query(db,null,seleccion,selectionArgs,null,null,null);
    }
    public int  insertarLocalidad(RestLocalidad localidad) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.Localidad.LOC_ID, localidad.getLOC_ID());
        valores.put(ContratoCorpicoApp.Localidad.LOC_DESCRIPCION, localidad.getLOC_DESCRIPCION());
        valores.put(ContratoCorpicoApp.Localidad.LOC_MUNICIPIO, localidad.getLOC_MUNICIPIO());
        valores.put(ContratoCorpicoApp.Localidad.LOC_INFORMA_SUCURSAL, localidad.getLOC_INFORMA_SUCURSAL());
        valores.put(ContratoCorpicoApp.Localidad.LOC_SUCURSAL, localidad.getLOC_SUCURSAL());
        valores.put(ContratoCorpicoApp.Localidad.LOC_ZONA_TARIFARIA, localidad.getLOC_ZONA_TARIFARIA());
        valores.put(ContratoCorpicoApp.Localidad.LOC_ACTIVO, localidad.getLOC_ACTIVO());
        valores.put(ContratoCorpicoApp.Localidad.LOC_ID_USER, localidad.getLOC_ID_USER());
        valores.put(ContratoCorpicoApp.Localidad.LOC_FECHA_UPDATE, localidad.getLOC_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.Localidad.LOC_LOTE_REPLICACION, localidad.getLOC_LOTE_REPLICACION());

        db.insertOrThrow(LOCALIDAD, null, valores);
        return localidad.getLOC_ID();
    }
    public boolean actualizarLocalidad(RestLocalidad localidadnueva ){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.Localidad.LOC_ID, localidadnueva.getLOC_ID());
        valores.put(ContratoCorpicoApp.Localidad.LOC_DESCRIPCION, localidadnueva.getLOC_DESCRIPCION());
        valores.put(ContratoCorpicoApp.Localidad.LOC_MUNICIPIO, localidadnueva.getLOC_MUNICIPIO());
        valores.put(ContratoCorpicoApp.Localidad.LOC_INFORMA_SUCURSAL, localidadnueva.getLOC_INFORMA_SUCURSAL());
        valores.put(ContratoCorpicoApp.Localidad.LOC_SUCURSAL, localidadnueva.getLOC_SUCURSAL());
        valores.put(ContratoCorpicoApp.Localidad.LOC_ZONA_TARIFARIA, localidadnueva.getLOC_ZONA_TARIFARIA());
        valores.put(ContratoCorpicoApp.Localidad.LOC_ACTIVO, localidadnueva.getLOC_ACTIVO());
        valores.put(ContratoCorpicoApp.Localidad.LOC_ID_USER, localidadnueva.getLOC_ID_USER());
        valores.put(ContratoCorpicoApp.Localidad.LOC_FECHA_UPDATE, localidadnueva.getLOC_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.Localidad.LOC_LOTE_REPLICACION, localidadnueva.getLOC_LOTE_REPLICACION());
        String whereClause = String.format("%s=?", ContratoCorpicoApp.Localidad.LOC_ID);
        String [] whereArgs = {String.valueOf(localidadnueva.getLOC_ID())};

        int resultado = db.update(BaseDatosCorpicoApp.Tablas.LOCALIDAD,valores,whereClause,whereArgs);
        return resultado > 0;
    }
    public boolean eliminarLocalidad(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = ContratoCorpicoApp.Localidad.LOC_ID + "=?";
        String [] whereArgs = {String.valueOf(id)};
        int resultado = db.delete(BaseDatosCorpicoApp.Tablas.LOCALIDAD,whereClause,whereArgs);
        return resultado >0;
    }
    // ESTADO OPERATIVO
    public Cursor obtenerEstadoOperativo(){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(ESTADO_OPERATIVO);
        return builder.query(db,null,null,null,null,null,null);
    }
    public Cursor obtenerEstadoOperativoPorId(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String seleccion = String.format("%s=?", ContratoCorpicoApp.EstadoOperativo.EOP_ID);
        String [] selectionArgs = {String.valueOf(id)};

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(ESTADO_OPERATIVO);

        return builder.query(db,null,seleccion,selectionArgs,null,null,null);
    }
    public int  insertarEstadoOperativo(RestEstadoOperativo estadoOperativo) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_ID, estadoOperativo.getEOP_ID());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_DESCRIPCION, estadoOperativo.getEOP_DESCRIPCION());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_ABREVIATURA, estadoOperativo.getEOP_ABREVIATURA());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_TIPO_EMPRESA, estadoOperativo.getEOP_TIPO_EMPRESA());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_SALE_A_LEER, estadoOperativo.getEOP_SALE_A_LEER());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_ES_FACTURABLE, estadoOperativo.getEOP_ES_FACTURABLE());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_CAMB_TITULARIDAD, estadoOperativo.getEOP_CAMB_TITULARIDAD());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_CAMB_TARIFA, estadoOperativo.getEOP_CAMB_TARIFA());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_CAMB_DIR_POSTAL, estadoOperativo.getEOP_CAMB_DIR_POSTAL());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_CAMB_DAT_IMPOSITIVOS, estadoOperativo.getEOP_CAMB_DAT_IMPOSITIVOS());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_BAJA_DEB_AUTOMATIVO, estadoOperativo.getEOP_BAJA_DEB_AUTOMATIVO());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_RECLAMOS, estadoOperativo.getEOP_RECLAMOS());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_ORDEN_TRABAJO, estadoOperativo.getEOP_ORDEN_TRABAJO());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_BAJA_SUMINISTRO, estadoOperativo.getEOP_BAJA_SUMINISTRO());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_COBRANZA, estadoOperativo.getEOP_COBRANZA());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_INCLUYE_AVISO_DEUDA, estadoOperativo.getEOP_INCLUYE_AVISO_DEUDA());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_POSEE_MEDIDOR, estadoOperativo.getEOP_POSEE_MEDIDOR());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_ACCION_SI_NO_POSEE_MED, estadoOperativo.getEOP_ACCION_SI_NO_POSEE_MED());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_PERMITE_RE_SOLICITAR_SERVICIO, estadoOperativo.getEOP_PERMITE_RE_SOLICITAR_SERVICIO());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_COLOR, estadoOperativo.getEOP_COLOR());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_ESTADO_CONEXION, estadoOperativo.getEOP_ESTADO_CONEXION());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_CLIENTE, estadoOperativo.getEOP_CLIENTE());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_ACTIVO, estadoOperativo.getEOP_ACTIVO());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_ID_USER, estadoOperativo.getEOP_ID_USER());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_FECHA_UPDATE, estadoOperativo.getEOP_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_LOTE_REPLICACION, estadoOperativo.getEOP_LOTE_REPLICACION());

        db.insertOrThrow(ESTADO_OPERATIVO, null, valores);
        return estadoOperativo.getEOP_ID();
    }
    public boolean actualizarEstadoOperatio(RestEstadoOperativo estadoOperativonuevo ){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_ID, estadoOperativonuevo.getEOP_ID());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_DESCRIPCION, estadoOperativonuevo.getEOP_DESCRIPCION());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_ABREVIATURA, estadoOperativonuevo.getEOP_ABREVIATURA());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_TIPO_EMPRESA, estadoOperativonuevo.getEOP_TIPO_EMPRESA());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_SALE_A_LEER, estadoOperativonuevo.getEOP_SALE_A_LEER());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_ES_FACTURABLE, estadoOperativonuevo.getEOP_ES_FACTURABLE());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_CAMB_TITULARIDAD, estadoOperativonuevo.getEOP_CAMB_TITULARIDAD());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_CAMB_TARIFA, estadoOperativonuevo.getEOP_CAMB_TARIFA());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_CAMB_DIR_POSTAL, estadoOperativonuevo.getEOP_CAMB_DIR_POSTAL());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_CAMB_DAT_IMPOSITIVOS, estadoOperativonuevo.getEOP_CAMB_DAT_IMPOSITIVOS());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_BAJA_DEB_AUTOMATIVO, estadoOperativonuevo.getEOP_BAJA_DEB_AUTOMATIVO());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_RECLAMOS, estadoOperativonuevo.getEOP_RECLAMOS());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_ORDEN_TRABAJO, estadoOperativonuevo.getEOP_ORDEN_TRABAJO());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_BAJA_SUMINISTRO, estadoOperativonuevo.getEOP_BAJA_SUMINISTRO());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_COBRANZA, estadoOperativonuevo.getEOP_COBRANZA());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_INCLUYE_AVISO_DEUDA, estadoOperativonuevo.getEOP_INCLUYE_AVISO_DEUDA());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_POSEE_MEDIDOR, estadoOperativonuevo.getEOP_POSEE_MEDIDOR());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_ACCION_SI_NO_POSEE_MED, estadoOperativonuevo.getEOP_ACCION_SI_NO_POSEE_MED());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_PERMITE_RE_SOLICITAR_SERVICIO, estadoOperativonuevo.getEOP_PERMITE_RE_SOLICITAR_SERVICIO());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_COLOR, estadoOperativonuevo.getEOP_COLOR());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_ESTADO_CONEXION, estadoOperativonuevo.getEOP_ESTADO_CONEXION());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_CLIENTE, estadoOperativonuevo.getEOP_CLIENTE());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_ACTIVO, estadoOperativonuevo.getEOP_ACTIVO());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_ID_USER, estadoOperativonuevo.getEOP_ID_USER());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_FECHA_UPDATE, estadoOperativonuevo.getEOP_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.EstadoOperativo.EOP_LOTE_REPLICACION, estadoOperativonuevo.getEOP_LOTE_REPLICACION());

        String whereClause = String.format("%s=?", ContratoCorpicoApp.EstadoOperativo.EOP_ID);
        String [] whereArgs = {String.valueOf(estadoOperativonuevo.getEOP_ID())};

        int resultado = db.update(BaseDatosCorpicoApp.Tablas.ESTADO_OPERATIVO,valores,whereClause,whereArgs);
        return resultado > 0;
    }
    public boolean eliminarEstadoOperativo(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = ContratoCorpicoApp.EstadoOperativo.EOP_ID + "=?";
        String [] whereArgs = {String.valueOf(id)};
        int resultado = db.delete(BaseDatosCorpicoApp.Tablas.ESTADO_OPERATIVO,whereClause,whereArgs);
        return resultado >0;
    }

    // MEDIDOR
    public Cursor obtenerMedidor(){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(MEDIDOR);
        return builder.query(db,null,null,null,null,null,null);
    }
    public Cursor obtenerMedidorPorId(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String seleccion = String.format("%s=?", ContratoCorpicoApp.Medidor.MED_ID);
        String [] selectionArgs = {String.valueOf(id)};

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(MEDIDOR);

        return builder.query(db,null,seleccion,selectionArgs,null,null,null);
    }
    public int  insertarMedidor(RestMedidor medidor) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.Medidor.MED_ID, medidor.getMED_ID());
        valores.put(ContratoCorpicoApp.Medidor.MED_EMPRESA, medidor.getMED_EMPRESA());
        valores.put(ContratoCorpicoApp.Medidor.MED_ALMACEN, medidor.getMED_ALMACEN());
        valores.put(ContratoCorpicoApp.Medidor.MED_ESTADO, medidor.getMED_ESTADO());
        valores.put(ContratoCorpicoApp.Medidor.MED_MARCA, medidor.getMED_MARCA());
        valores.put(ContratoCorpicoApp.Medidor.MED_TIPOSERIE, medidor.getMED_TIPOSERIE());
        valores.put(ContratoCorpicoApp.Medidor.MED_SERIE, medidor.getMED_SERIE());
        valores.put(ContratoCorpicoApp.Medidor.MED_MODELO, medidor.getMED_MODELO());
        valores.put(ContratoCorpicoApp.Medidor.MED_CLASE, medidor.getMED_CLASE());
        valores.put(ContratoCorpicoApp.Medidor.MED_TIPO, medidor.getMED_TIPO());
        valores.put(ContratoCorpicoApp.Medidor.MED_RUEDAS, medidor.getMED_RUEDAS());
        valores.put(ContratoCorpicoApp.Medidor.MED_ANO_FABRICA, medidor.getMED_ANO_FABRICA());
        valores.put(ContratoCorpicoApp.Medidor.MED_ANOS_VIDA, medidor.getMED_ANOS_VIDA());
        valores.put(ContratoCorpicoApp.Medidor.MED_FECHA_ENTRADA, medidor.getMED_FECHA_ENTRADA());
        valores.put(ContratoCorpicoApp.Medidor.MED_VTO_GARANTIA, medidor.getMED_VTO_GARANTIA());
        valores.put(ContratoCorpicoApp.Medidor.MED_FACTOR, medidor.getMED_FACTOR());
        valores.put(ContratoCorpicoApp.Medidor.MED_POSEE_LEDS, medidor.getMED_POSEE_LEDS());
        valores.put(ContratoCorpicoApp.Medidor.MED_MOTIVO_BAJA, medidor.getMED_MOTIVO_BAJA());
        valores.put(ContratoCorpicoApp.Medidor.MED_FECHA_BAJA, medidor.getMED_FECHA_BAJA());
        valores.put(ContratoCorpicoApp.Medidor.MED_ID_USER, medidor.getMED_ID_USER());
        valores.put(ContratoCorpicoApp.Medidor.MED_FECHA_UPDATE, medidor.getMED_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.Medidor.MED_LOTE_REPLICACION, medidor.getMED_LOTE_REPLICACION());

        db.insertOrThrow(MEDIDOR, null, valores);
        return medidor.getMED_ID();
    }
    public boolean actualizarMedidor(RestMedidor medidornuevo ){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.Medidor.MED_ID, medidornuevo.getMED_ID());
        valores.put(ContratoCorpicoApp.Medidor.MED_EMPRESA, medidornuevo.getMED_EMPRESA());
        valores.put(ContratoCorpicoApp.Medidor.MED_ALMACEN, medidornuevo.getMED_ALMACEN());
        valores.put(ContratoCorpicoApp.Medidor.MED_ESTADO, medidornuevo.getMED_ESTADO());
        valores.put(ContratoCorpicoApp.Medidor.MED_MARCA, medidornuevo.getMED_MARCA());
        valores.put(ContratoCorpicoApp.Medidor.MED_TIPOSERIE, medidornuevo.getMED_TIPOSERIE());
        valores.put(ContratoCorpicoApp.Medidor.MED_SERIE, medidornuevo.getMED_SERIE());
        valores.put(ContratoCorpicoApp.Medidor.MED_MODELO, medidornuevo.getMED_MODELO());
        valores.put(ContratoCorpicoApp.Medidor.MED_CLASE, medidornuevo.getMED_CLASE());
        valores.put(ContratoCorpicoApp.Medidor.MED_TIPO, medidornuevo.getMED_TIPO());
        valores.put(ContratoCorpicoApp.Medidor.MED_RUEDAS, medidornuevo.getMED_RUEDAS());
        valores.put(ContratoCorpicoApp.Medidor.MED_ANO_FABRICA, medidornuevo.getMED_ANO_FABRICA());
        valores.put(ContratoCorpicoApp.Medidor.MED_ANOS_VIDA, medidornuevo.getMED_ANOS_VIDA());
        valores.put(ContratoCorpicoApp.Medidor.MED_FECHA_ENTRADA, medidornuevo.getMED_FECHA_ENTRADA());
        valores.put(ContratoCorpicoApp.Medidor.MED_VTO_GARANTIA, medidornuevo.getMED_VTO_GARANTIA());
        valores.put(ContratoCorpicoApp.Medidor.MED_FACTOR, medidornuevo.getMED_FACTOR());
        valores.put(ContratoCorpicoApp.Medidor.MED_POSEE_LEDS, medidornuevo.getMED_POSEE_LEDS());
        valores.put(ContratoCorpicoApp.Medidor.MED_MOTIVO_BAJA, medidornuevo.getMED_MOTIVO_BAJA());
        valores.put(ContratoCorpicoApp.Medidor.MED_FECHA_BAJA, medidornuevo.getMED_FECHA_BAJA());
        valores.put(ContratoCorpicoApp.Medidor.MED_ID_USER, medidornuevo.getMED_ID_USER());
        valores.put(ContratoCorpicoApp.Medidor.MED_FECHA_UPDATE, medidornuevo.getMED_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.Medidor.MED_LOTE_REPLICACION, medidornuevo.getMED_LOTE_REPLICACION());

        String whereClause = String.format("%s=?", ContratoCorpicoApp.Medidor.MED_ID);
        String [] whereArgs = {String.valueOf(medidornuevo.getMED_ID())};

        int resultado = db.update(BaseDatosCorpicoApp.Tablas.MEDIDOR,valores,whereClause,whereArgs);
        return resultado > 0;
    }
    public boolean eliminarMedidor(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = ContratoCorpicoApp.Medidor.MED_ID + "=?";
        String [] whereArgs = {String.valueOf(id)};
        int resultado = db.delete(BaseDatosCorpicoApp.Tablas.MEDIDOR,whereClause,whereArgs);
        return resultado >0;
    }

    // MEDIDOR CLASE
    public Cursor obtenerMedidorClase(){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(MEDIDOR_CLASE);
        return builder.query(db,null,null,null,null,null,null);
    }
    public Cursor obtenerMedidorClasePorId(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String seleccion = String.format("%s=?", ContratoCorpicoApp.MedidorClase.MCL_ID);
        String [] selectionArgs = {String.valueOf(id)};

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(MEDIDOR_CLASE);

        return builder.query(db,null,seleccion,selectionArgs,null,null,null);
    }
    public int  insertarMedidorClase(RestMedidorClase medidorClase) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.MedidorClase.MCL_ID, medidorClase.getMCL_ID());
        valores.put(ContratoCorpicoApp.MedidorClase.MCL_DESCRIPCION, medidorClase.getMCL_DESCRIPCION());
        valores.put(ContratoCorpicoApp.MedidorClase.MCL_TIPO_EMPRESA, medidorClase.getMCL_TIPO_EMPRESA());
        valores.put(ContratoCorpicoApp.MedidorClase.MCL_CAPACIDAD, medidorClase.getMCL_CAPACIDAD());
        valores.put(ContratoCorpicoApp.MedidorClase.MCL_ACTIVO, medidorClase.getMCL_ACTIVO());
        valores.put(ContratoCorpicoApp.MedidorClase.MCL_ID_USER, medidorClase.getMCL_ID_USER());
        valores.put(ContratoCorpicoApp.MedidorClase.MCL_FECHA_UPDATE, medidorClase.getMCL_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.MedidorClase.MCL_LOTE_REPLICACION, medidorClase.getMCL_LOTE_REPLICACION());
        db.insertOrThrow(MEDIDOR_CLASE, null, valores);
        return medidorClase.getMCL_ID();
    }
    public boolean actualizarMedidorClase(RestMedidorClase medidorClasenuevo ){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.MedidorClase.MCL_ID, medidorClasenuevo.getMCL_ID());
        valores.put(ContratoCorpicoApp.MedidorClase.MCL_DESCRIPCION, medidorClasenuevo.getMCL_DESCRIPCION());
        valores.put(ContratoCorpicoApp.MedidorClase.MCL_TIPO_EMPRESA, medidorClasenuevo.getMCL_TIPO_EMPRESA());
        valores.put(ContratoCorpicoApp.MedidorClase.MCL_CAPACIDAD, medidorClasenuevo.getMCL_CAPACIDAD());
        valores.put(ContratoCorpicoApp.MedidorClase.MCL_ACTIVO, medidorClasenuevo.getMCL_ACTIVO());
        valores.put(ContratoCorpicoApp.MedidorClase.MCL_ID_USER, medidorClasenuevo.getMCL_ID_USER());
        valores.put(ContratoCorpicoApp.MedidorClase.MCL_FECHA_UPDATE, medidorClasenuevo.getMCL_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.MedidorClase.MCL_LOTE_REPLICACION, medidorClasenuevo.getMCL_LOTE_REPLICACION());

        String whereClause = String.format("%s=?", ContratoCorpicoApp.MedidorClase.MCL_ID);
        String [] whereArgs = {String.valueOf(medidorClasenuevo.getMCL_ID())};

        int resultado = db.update(BaseDatosCorpicoApp.Tablas.MEDIDOR_CLASE,valores,whereClause,whereArgs);
        return resultado > 0;
    }
    public boolean eliminarMedidorClase(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = ContratoCorpicoApp.MedidorClase.MCL_ID + "=?";
        String [] whereArgs = {String.valueOf(id)};
        int resultado = db.delete(BaseDatosCorpicoApp.Tablas.MEDIDOR_CLASE,whereClause,whereArgs);
        return resultado >0;
    }

    // MEDIDOR MARCA
    public Cursor obtenerMedidorMarca(){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(MEDIDOR_MARCA);
        return builder.query(db,null,null,null,null,null,null);
    }
    public Cursor obtenerMedidorMarcaPorId(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String seleccion = String.format("%s=?", ContratoCorpicoApp.MedidorMarca.MCA_ID);
        String [] selectionArgs = {String.valueOf(id)};

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(MEDIDOR_MARCA);

        return builder.query(db,null,seleccion,selectionArgs,null,null,null);
    }
    public int  insertarMedidorMarca(RestMedidorMarca medidorMarca) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.MedidorMarca.MCA_ID, medidorMarca.getMCA_ID());
        valores.put(ContratoCorpicoApp.MedidorMarca.MCA_DESCRIPCION, medidorMarca.getMCA_DESCRIPCION());
        valores.put(ContratoCorpicoApp.MedidorMarca.MCA_ABREVIATURA, medidorMarca.getMCA_ABREVIATURA());
        valores.put(ContratoCorpicoApp.MedidorMarca.MCA_ACTIVO, medidorMarca.getMCA_ACTIVO());
        valores.put(ContratoCorpicoApp.MedidorMarca.MCA_ID_USER, medidorMarca.getMCA_ID_USER());
        valores.put(ContratoCorpicoApp.MedidorMarca.MCA_FECHA_UPDATE, medidorMarca.getMCA_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.MedidorMarca.MCA_LOTE_REPLICACION, medidorMarca.getMCA_LOTE_REPLICACION());
        db.insertOrThrow(MEDIDOR_MARCA, null, valores);
        return medidorMarca.getMCA_ID();
    }
    public boolean actualizarMedidorMarca(RestMedidorMarca medidorMarcanuevo ){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.MedidorMarca.MCA_ID, medidorMarcanuevo.getMCA_ID());
        valores.put(ContratoCorpicoApp.MedidorMarca.MCA_DESCRIPCION, medidorMarcanuevo.getMCA_DESCRIPCION());
        valores.put(ContratoCorpicoApp.MedidorMarca.MCA_ABREVIATURA, medidorMarcanuevo.getMCA_ABREVIATURA());
        valores.put(ContratoCorpicoApp.MedidorMarca.MCA_ACTIVO, medidorMarcanuevo.getMCA_ACTIVO());
        valores.put(ContratoCorpicoApp.MedidorMarca.MCA_ID_USER, medidorMarcanuevo.getMCA_ID_USER());
        valores.put(ContratoCorpicoApp.MedidorMarca.MCA_FECHA_UPDATE, medidorMarcanuevo.getMCA_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.MedidorMarca.MCA_LOTE_REPLICACION, medidorMarcanuevo.getMCA_LOTE_REPLICACION());

        String whereClause = String.format("%s=?", ContratoCorpicoApp.MedidorMarca.MCA_ID);
        String [] whereArgs = {String.valueOf(medidorMarcanuevo.getMCA_ID())};

        int resultado = db.update(BaseDatosCorpicoApp.Tablas.MEDIDOR_CLASE,valores,whereClause,whereArgs);
        return resultado > 0;
    }
    public boolean eliminarMedidorMarca(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = ContratoCorpicoApp.MedidorMarca.MCA_ID + "=?";
        String [] whereArgs = {String.valueOf(id)};
        int resultado = db.delete(BaseDatosCorpicoApp.Tablas.MEDIDOR_MARCA,whereClause,whereArgs);
        return resultado >0;
    }

    // MEDIDOR MODELO
    public Cursor obtenerMedidorModelo(){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(MEDIDOR_MODELO);
        return builder.query(db,null,null,null,null,null,null);
    }
    public Cursor obtenerMedidorModeloPorId(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String seleccion = String.format("%s=?", ContratoCorpicoApp.MedidorModelo.MMO_ID);
        String [] selectionArgs = {String.valueOf(id)};

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(MEDIDOR_MODELO);

        return builder.query(db,null,seleccion,selectionArgs,null,null,null);
    }
    public int  insertarMedidorModelo(RestMedidorModelo medidorModelo) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.MedidorModelo.MMO_MARCA_MEDIDOR, medidorModelo.getMMO_MARCA_MEDIDOR());
        valores.put(ContratoCorpicoApp.MedidorModelo.MMO_ID, medidorModelo.getMMO_ID());
        valores.put(ContratoCorpicoApp.MedidorModelo.MMO_DESCRIPCION, medidorModelo.getMMO_DESCRIPCION());
        valores.put(ContratoCorpicoApp.MedidorModelo.MMO_TIPO_EMPRESA, medidorModelo.getMMO_TIPO_EMPRESA());
        valores.put(ContratoCorpicoApp.MedidorModelo.MMO_ACTIVO, medidorModelo.getMMO_ACTIVO());
        valores.put(ContratoCorpicoApp.MedidorModelo.MMO_ID_USER, medidorModelo.getMMO_ID_USER());
        valores.put(ContratoCorpicoApp.MedidorModelo.MMO_FECHA_UPDATE, medidorModelo.getMMO_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.MedidorModelo.MMO_LOTE_REPLICACION, medidorModelo.getMMO_LOTE_REPLICACION());

        db.insertOrThrow(MEDIDOR_MODELO, null, valores);
        return medidorModelo.getMMO_ID();
    }
    public boolean actualizarMedidorModelo(RestMedidorModelo medidorModelonuevo ){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.MedidorModelo.MMO_MARCA_MEDIDOR, medidorModelonuevo.getMMO_MARCA_MEDIDOR());
        valores.put(ContratoCorpicoApp.MedidorModelo.MMO_ID, medidorModelonuevo.getMMO_ID());
        valores.put(ContratoCorpicoApp.MedidorModelo.MMO_DESCRIPCION, medidorModelonuevo.getMMO_DESCRIPCION());
        valores.put(ContratoCorpicoApp.MedidorModelo.MMO_TIPO_EMPRESA, medidorModelonuevo.getMMO_TIPO_EMPRESA());
        valores.put(ContratoCorpicoApp.MedidorModelo.MMO_ACTIVO, medidorModelonuevo.getMMO_ACTIVO());
        valores.put(ContratoCorpicoApp.MedidorModelo.MMO_ID_USER, medidorModelonuevo.getMMO_ID_USER());
        valores.put(ContratoCorpicoApp.MedidorModelo.MMO_FECHA_UPDATE, medidorModelonuevo.getMMO_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.MedidorModelo.MMO_LOTE_REPLICACION, medidorModelonuevo.getMMO_LOTE_REPLICACION());
        String whereClause = String.format("%s=?", ContratoCorpicoApp.MedidorModelo.MMO_ID);
        String [] whereArgs = {String.valueOf(medidorModelonuevo.getMMO_ID())};

        int resultado = db.update(BaseDatosCorpicoApp.Tablas.MEDIDOR_MODELO,valores,whereClause,whereArgs);
        return resultado > 0;
    }
    public boolean eliminarMedidorModelo(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = ContratoCorpicoApp.MedidorModelo.MMO_ID + "=?";
        String [] whereArgs = {String.valueOf(id)};
        int resultado = db.delete(BaseDatosCorpicoApp.Tablas.MEDIDOR_MODELO,whereClause,whereArgs);
        return resultado >0;
    }

    // OPERARIO
    public Cursor obtenerOperario(){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(OPERARIO);
        return builder.query(db,null,null,null,null,null,null);
    }
    public Cursor obtenerOperarioPorId(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String seleccion = String.format("%s=?", ContratoCorpicoApp.Operario.OPE_ID);
        String [] selectionArgs = {String.valueOf(id)};

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(OPERARIO);

        return builder.query(db,null,seleccion,selectionArgs,null,null,null);
    }
    public int  insertarOperario(RestOperario operario) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.Operario.OPE_ID, operario.getOPE_ID());
        valores.put(ContratoCorpicoApp.Operario.OPE_DESCRIPCION, operario.getOPE_DESCRIPCION());
        valores.put(ContratoCorpicoApp.Operario.OPE_CONTRATISTA, operario.getOPE_CONTRATISTA());
        valores.put(ContratoCorpicoApp.Operario.OPE_SUCURSAL, operario.getOPE_SUCURSAL());
        valores.put(ContratoCorpicoApp.Operario.OPE_ACTIVO, operario.getOPE_ACTIVO());
        valores.put(ContratoCorpicoApp.Operario.OPE_ID_USER, operario.getOPE_ID_USER());
        valores.put(ContratoCorpicoApp.Operario.OPE_FECHA_UPDATE, operario.getOPE_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.Operario.OPE_LOTE_REPLICACION, operario.getOPE_LOTE_REPLICACION());

        db.insertOrThrow(OPERARIO, null, valores);
        return operario.getOPE_ID();
    }
    public boolean actualizarOperario(RestOperario operarionuevo ){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.Operario.OPE_ID, operarionuevo.getOPE_ID());
        valores.put(ContratoCorpicoApp.Operario.OPE_DESCRIPCION, operarionuevo.getOPE_DESCRIPCION());
        valores.put(ContratoCorpicoApp.Operario.OPE_CONTRATISTA, operarionuevo.getOPE_CONTRATISTA());
        valores.put(ContratoCorpicoApp.Operario.OPE_SUCURSAL, operarionuevo.getOPE_SUCURSAL());
        valores.put(ContratoCorpicoApp.Operario.OPE_ACTIVO, operarionuevo.getOPE_ACTIVO());
        valores.put(ContratoCorpicoApp.Operario.OPE_ID_USER, operarionuevo.getOPE_ID_USER());
        valores.put(ContratoCorpicoApp.Operario.OPE_FECHA_UPDATE, operarionuevo.getOPE_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.Operario.OPE_LOTE_REPLICACION, operarionuevo.getOPE_LOTE_REPLICACION());

        String whereClause = String.format("%s=?", ContratoCorpicoApp.Operario.OPE_ID);
        String [] whereArgs = {String.valueOf(operarionuevo.getOPE_ID())};

        int resultado = db.update(BaseDatosCorpicoApp.Tablas.OPERARIO,valores,whereClause,whereArgs);
        return resultado > 0;
    }
    public boolean eliminarOperario(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = ContratoCorpicoApp.Operario.OPE_ID + "=?";
        String [] whereArgs = {String.valueOf(id)};
        int resultado = db.delete(BaseDatosCorpicoApp.Tablas.OPERARIO,whereClause,whereArgs);
        return resultado >0;
    }
    // MEDIDOR TIPO
    public Cursor obtenerMedidorTipo(){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(MEDIDOR_TIPO);
        return builder.query(db,null,null,null,null,null,null);
    }
    public Cursor obtenerMedidorTipoPorId(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String seleccion = String.format("%s=?", ContratoCorpicoApp.MedidorTipo.MET_ID);
        String [] selectionArgs = {String.valueOf(id)};

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(MEDIDOR_TIPO);

        return builder.query(db,null,seleccion,selectionArgs,null,null,null);
    }
    public int  insertarMedidorTipo(RestMedidorTipo medidorTipo) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.MedidorTipo.MET_ID, medidorTipo.getMET_ID());
        valores.put(ContratoCorpicoApp.MedidorTipo.MET_DESCRIPCION, medidorTipo.getMET_DESCRIPCION());
        valores.put(ContratoCorpicoApp.MedidorTipo.MET_TIPO_EMPRESA, medidorTipo.getMET_TIPO_EMPRESA());
        valores.put(ContratoCorpicoApp.MedidorTipo.MET_ACTIVO, medidorTipo.getMET_ACTIVO());
        valores.put(ContratoCorpicoApp.MedidorTipo.MET_ID_USER, medidorTipo.getMET_ID_USER());
        valores.put(ContratoCorpicoApp.MedidorTipo.MET_FECHA_UPDATE, medidorTipo.getMET_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.MedidorTipo.MET_LOTE_REPLICACION, medidorTipo.getMET_LOTE_REPLICACION());

        db.insertOrThrow(MEDIDOR_TIPO, null, valores);
        return medidorTipo.getMET_ID();
    }
    public boolean actualizarMedidorTipo(RestMedidorTipo medidorTiponuevo ){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.MedidorTipo.MET_ID, medidorTiponuevo.getMET_ID());
        valores.put(ContratoCorpicoApp.MedidorTipo.MET_DESCRIPCION, medidorTiponuevo.getMET_DESCRIPCION());
        valores.put(ContratoCorpicoApp.MedidorTipo.MET_TIPO_EMPRESA, medidorTiponuevo.getMET_TIPO_EMPRESA());
        valores.put(ContratoCorpicoApp.MedidorTipo.MET_ACTIVO, medidorTiponuevo.getMET_ACTIVO());
        valores.put(ContratoCorpicoApp.MedidorTipo.MET_ID_USER, medidorTiponuevo.getMET_ID_USER());
        valores.put(ContratoCorpicoApp.MedidorTipo.MET_FECHA_UPDATE, medidorTiponuevo.getMET_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.MedidorTipo.MET_LOTE_REPLICACION, medidorTiponuevo.getMET_LOTE_REPLICACION());

        String whereClause = String.format("%s=?", ContratoCorpicoApp.MedidorTipo.MET_ID);
        String [] whereArgs = {String.valueOf(medidorTiponuevo.getMET_ID())};

        int resultado = db.update(BaseDatosCorpicoApp.Tablas.MEDIDOR_TIPO,valores,whereClause,whereArgs);
        return resultado > 0;
    }
    public boolean eliminarMedidorTipo(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = ContratoCorpicoApp.MedidorTipo.MET_ID + "=?";
        String [] whereArgs = {String.valueOf(id)};
        int resultado = db.delete(BaseDatosCorpicoApp.Tablas.MEDIDOR_TIPO,whereClause,whereArgs);
        return resultado >0;
    }

    // TIPO CONEXION
    public Cursor obtenerTipoConexion(){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(TIPO_CONEXION);
        return builder.query(db,null,null,null,null,null,null);
    }
    public Cursor obtenerTipoConexionPorId(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String seleccion = String.format("%s=?", ContratoCorpicoApp.TipoConexion.TCO_ID);
        String [] selectionArgs = {String.valueOf(id)};

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(TIPO_CONEXION);

        return builder.query(db,null,seleccion,selectionArgs,null,null,null);
    }
    public int  insertarTipoConexion(RestTipoConexion tipoConexion) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.TipoConexion.TCO_ID, tipoConexion.getTCO_ID());
        valores.put(ContratoCorpicoApp.TipoConexion.TCO_DESCRIPCION, tipoConexion.getTCO_DESCRIPCION());
        valores.put(ContratoCorpicoApp.TipoConexion.TCO_TIPO_EMPRESA, tipoConexion.getTCO_TIPO_EMPRESA());
        valores.put(ContratoCorpicoApp.TipoConexion.TCO_FACTOR, tipoConexion.getTCO_FACTOR());
        valores.put(ContratoCorpicoApp.TipoConexion.TCO_ACTIVO, tipoConexion.getTCO_ACTIVO());
        valores.put(ContratoCorpicoApp.TipoConexion.TCO_ID_USER, tipoConexion.getTCO_ID_USER());
        valores.put(ContratoCorpicoApp.TipoConexion.TCO_FECHA_UPDATE, tipoConexion.getTCO_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.TipoConexion.TCO_LOTE_REPLICACION, tipoConexion.getTCO_LOTE_REPLICACION());

        db.insertOrThrow(TIPO_CONEXION, null, valores);
        return tipoConexion.getTCO_ID();
    }
    public boolean actualizarTipoConexion(RestTipoConexion tipoConexionnuevo ){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.TipoConexion.TCO_ID, tipoConexionnuevo.getTCO_ID());
        valores.put(ContratoCorpicoApp.TipoConexion.TCO_DESCRIPCION, tipoConexionnuevo.getTCO_DESCRIPCION());
        valores.put(ContratoCorpicoApp.TipoConexion.TCO_TIPO_EMPRESA, tipoConexionnuevo.getTCO_TIPO_EMPRESA());
        valores.put(ContratoCorpicoApp.TipoConexion.TCO_FACTOR, tipoConexionnuevo.getTCO_FACTOR());
        valores.put(ContratoCorpicoApp.TipoConexion.TCO_ACTIVO, tipoConexionnuevo.getTCO_ACTIVO());
        valores.put(ContratoCorpicoApp.TipoConexion.TCO_ID_USER, tipoConexionnuevo.getTCO_ID_USER());
        valores.put(ContratoCorpicoApp.TipoConexion.TCO_FECHA_UPDATE, tipoConexionnuevo.getTCO_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.TipoConexion.TCO_LOTE_REPLICACION, tipoConexionnuevo.getTCO_LOTE_REPLICACION());

        String whereClause = String.format("%s=?", ContratoCorpicoApp.TipoConexion.TCO_ID);
        String [] whereArgs = {String.valueOf(tipoConexionnuevo.getTCO_ID())};

        int resultado = db.update(BaseDatosCorpicoApp.Tablas.TIPO_CONEXION,valores,whereClause,whereArgs);
        return resultado > 0;
    }
    public boolean eliminarTipoConexion(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = ContratoCorpicoApp.TipoConexion.TCO_ID + "=?";
        String [] whereArgs = {String.valueOf(id)};
        int resultado = db.delete(BaseDatosCorpicoApp.Tablas.TIPO_CONEXION,whereClause,whereArgs);
        return resultado >0;
    }

    // TIPO CONSUMO
    public Cursor obtenerTipoConsumo(){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(TIPO_CONSUMO);
        return builder.query(db,null,null,null,null,null,null);
    }
    public Cursor obtenerTipoConsumoPorId(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String seleccion = String.format("%s=?", ContratoCorpicoApp.TipoConsumo.TIC_ID);
        String [] selectionArgs = {String.valueOf(id)};

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(TIPO_CONSUMO);

        return builder.query(db,null,seleccion,selectionArgs,null,null,null);
    }
    public int  insertarTipoConsumo(RestTipoConsumo tipoConsumo) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_ID, tipoConsumo.getTIC_ID());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_DESCRIPCION, tipoConsumo.getTIC_DESCRIPCION());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_TIPO_EMPRESA, tipoConsumo.getTIC_TIPO_EMPRESA());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_ABREVIATURA, tipoConsumo.getTIC_ABREVIATURA());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_TIPO_CONSUMO, tipoConsumo.getTIC_TIPO_CONSUMO());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_TARIFA_CONSUMO, tipoConsumo.getTIC_TARIFA_CONSUMO());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_TARIFA_RESERVA, tipoConsumo.getTIC_TARIFA_RESERVA());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_CARGO_FIJO, tipoConsumo.getTIC_CARGO_FIJO());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_FACTURA_MINIMA, tipoConsumo.getTIC_FACTURA_MINIMA());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_TRANSPORTE, tipoConsumo.getTIC_TRANSPORTE());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_IMPUTACION_CONTABLE, tipoConsumo.getTIC_IMPUTACION_CONTABLE());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_UTILIZA_FACTOR_PRESION, tipoConsumo.getTIC_UTILIZA_FACTOR_PRESION());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_UTILIZA_TRANSFORMADOR, tipoConsumo.getTIC_UTILIZA_TRANSFORMADOR());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_CARGO_FIJO_DESCUENTO, tipoConsumo.getTIC_CARGO_FIJO());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_TARIFA_CONSUMO_DESCUENTO, tipoConsumo.getTIC_TARIFA_CONSUMO_DESCUENTO());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_ACTIVO, tipoConsumo.getTIC_ACTIVO());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_ID_USER, tipoConsumo.getTIC_ID_USER());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_FECHA_UPDATE, tipoConsumo.getTIC_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_LOTE_REPLICACION, tipoConsumo.getTIC_LOTE_REPLICACION());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_SITUACION_IVA, tipoConsumo.getTIC_SITUACION_IVA());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_PORCENTAJE_CONTROL_CONSUMO_REDUCIDO, tipoConsumo.getTIC_PORCENTAJE_CONTROL_CONSUMO_REDUCIDO());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_PORCENTAJE_CONTROL_CONSUMO_ELEVADO, tipoConsumo.getTIC_PORCENTAJE_CONTROL_CONSUMO_ELEVADO());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_CONSUMO_MINIMO_CONTROL, tipoConsumo.getTIC_CONSUMO_MINIMO_CONTROL());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_MUESTRA_EN_ATP, tipoConsumo.getTIC_MUESTRA_EN_ATP());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_MUESTRA_EN_ADMIN, tipoConsumo.getTIC_MUESTRA_EN_ADMIN());

        db.insertOrThrow(TIPO_CONSUMO, null, valores);
        return tipoConsumo.getTIC_ID();
    }
    public boolean actualizarTipoConsumo(RestTipoConsumo tipoConsumonuevo ){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_ID, tipoConsumonuevo.getTIC_ID());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_DESCRIPCION, tipoConsumonuevo.getTIC_DESCRIPCION());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_TIPO_EMPRESA, tipoConsumonuevo.getTIC_TIPO_EMPRESA());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_ABREVIATURA, tipoConsumonuevo.getTIC_ABREVIATURA());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_TIPO_CONSUMO, tipoConsumonuevo.getTIC_TIPO_CONSUMO());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_TARIFA_CONSUMO, tipoConsumonuevo.getTIC_TARIFA_CONSUMO());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_TARIFA_RESERVA, tipoConsumonuevo.getTIC_TARIFA_RESERVA());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_CARGO_FIJO, tipoConsumonuevo.getTIC_CARGO_FIJO());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_FACTURA_MINIMA, tipoConsumonuevo.getTIC_FACTURA_MINIMA());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_TRANSPORTE, tipoConsumonuevo.getTIC_TRANSPORTE());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_IMPUTACION_CONTABLE, tipoConsumonuevo.getTIC_IMPUTACION_CONTABLE());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_UTILIZA_FACTOR_PRESION, tipoConsumonuevo.getTIC_UTILIZA_FACTOR_PRESION());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_UTILIZA_TRANSFORMADOR, tipoConsumonuevo.getTIC_UTILIZA_TRANSFORMADOR());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_CARGO_FIJO_DESCUENTO, tipoConsumonuevo.getTIC_CARGO_FIJO());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_TARIFA_CONSUMO_DESCUENTO, tipoConsumonuevo.getTIC_TARIFA_CONSUMO_DESCUENTO());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_ACTIVO, tipoConsumonuevo.getTIC_ACTIVO());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_ID_USER, tipoConsumonuevo.getTIC_ID_USER());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_FECHA_UPDATE, tipoConsumonuevo.getTIC_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_LOTE_REPLICACION, tipoConsumonuevo.getTIC_LOTE_REPLICACION());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_SITUACION_IVA, tipoConsumonuevo.getTIC_SITUACION_IVA());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_PORCENTAJE_CONTROL_CONSUMO_REDUCIDO, tipoConsumonuevo.getTIC_PORCENTAJE_CONTROL_CONSUMO_REDUCIDO());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_PORCENTAJE_CONTROL_CONSUMO_ELEVADO, tipoConsumonuevo.getTIC_PORCENTAJE_CONTROL_CONSUMO_ELEVADO());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_CONSUMO_MINIMO_CONTROL, tipoConsumonuevo.getTIC_CONSUMO_MINIMO_CONTROL());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_MUESTRA_EN_ATP, tipoConsumonuevo.getTIC_MUESTRA_EN_ATP());
        valores.put(ContratoCorpicoApp.TipoConsumo.TIC_MUESTRA_EN_ADMIN, tipoConsumonuevo.getTIC_MUESTRA_EN_ADMIN());


        String whereClause = String.format("%s=?", ContratoCorpicoApp.TipoConsumo.TIC_ID);
        String [] whereArgs = {String.valueOf(tipoConsumonuevo.getTIC_ID())};

        int resultado = db.update(BaseDatosCorpicoApp.Tablas.TIPO_CONSUMO,valores,whereClause,whereArgs);
        return resultado > 0;
    }
    public boolean eliminarTipoConsumo(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = ContratoCorpicoApp.TipoConsumo.TIC_ID + "=?";
        String [] whereArgs = {String.valueOf(id)};
        int resultado = db.delete(BaseDatosCorpicoApp.Tablas.TIPO_CONSUMO,whereClause,whereArgs);
        return resultado >0;
    }
    // TIPO EMPRESA
    public Cursor obtenerTipoEmpresa(){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(TIPO_EMPRESA);
        return builder.query(db,null,null,null,null,null,null);
    }
    public Cursor obtenerTipoEmpresaPorId(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String seleccion = String.format("%s=?", ContratoCorpicoApp.TipoEmpresa.TIE_ID);
        String [] selectionArgs = {String.valueOf(id)};

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(TIPO_EMPRESA);

        return builder.query(db,null,seleccion,selectionArgs,null,null,null);
    }
    public int  insertarTipoEmpresa(RestTipoEmpresa tipoEmpresa) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_ID, tipoEmpresa.getTIE_ID());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_DESCRIPCION, tipoEmpresa.getTIE_DESCRIPCION());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_ABREVIATURA, tipoEmpresa.getTIE_ABREVIATURA());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_ABREVIATURA_ATP, tipoEmpresa.getTIE_ABREVIATURA_ATP());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_MUESTRA_CONSUMO_CONVERTIDO, tipoEmpresa.getTIE_MUESTRA_CONSUMO_CONVERTIDO());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_FACTOR_CONVERSION_CONSUMO, tipoEmpresa.getTIE_FACTOR_CONVERSION_CONSUMO());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_ORDEN, tipoEmpresa.getTIE_ORDEN());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_UTILIZA_MODULO_LECTURAS, tipoEmpresa.getTIE_UTILIZA_MODULO_LECTURAS());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_USA_TRANSFORMADOR, tipoEmpresa.getTIE_USA_TRANSFORMADOR());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_BAJA_PERMITE_CAMBIAR_EST_ADM, tipoEmpresa.getTIE_BAJA_PERMITE_CAMBIAR_EST_ADM());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_ACTIVO, tipoEmpresa.getTIE_ACTIVO());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_ID_USER, tipoEmpresa.getTIE_ID_USER());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_FECHA_UPDATE, tipoEmpresa.getTIE_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_LOTE_REPLICACION , tipoEmpresa.getTIE_LOTE_REPLICACION());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_MODO_CALCULO_TARIFA_CONSUMO , tipoEmpresa.getTIE_MODO_CALCULO_TARIFA_CONSUMO());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_AJUSTA_CARGO_FIJO_ALTA_SUMINISTRO , tipoEmpresa.getTIE_AJUSTA_CARGO_FIJO_ALTA_SUMINISTRO());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_AJUSTA_ESCALA_CONSUMO_ALTA_SUMINISTRO , tipoEmpresa.getTIE_AJUSTA_ESCALA_CONSUMO_ALTA_SUMINISTRO());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_AJUSTA_CARGO_FIJO_BAJA_SUMINISTRO , tipoEmpresa.getTIE_AJUSTA_CARGO_FIJO_BAJA_SUMINISTRO());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_AJUSTA_ESCALA_CONSUMO_BAJA_SUMINISTRO , tipoEmpresa.getTIE_AJUSTA_CARGO_FIJO_BAJA_SUMINISTRO());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_MEDIDOR_FUNCION , tipoEmpresa.getTIE_MEDIDOR_FUNCION());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_COLOR , tipoEmpresa.getTIE_COLOR());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_EMPRESA_COBRANZA , tipoEmpresa.getTIE_EMPRESA_COBRANZA());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_ESTADO_OPE_BAJA , tipoEmpresa.getTIE_ESTADO_OPE_BAJA());

        db.insertOrThrow(TIPO_EMPRESA, null, valores);
        return tipoEmpresa.getTIE_ID();
    }
    public boolean actualizarTipoEmpresa(RestTipoEmpresa tipoEmpresanuevo ){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_ID, tipoEmpresanuevo.getTIE_ID());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_DESCRIPCION, tipoEmpresanuevo.getTIE_DESCRIPCION());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_ABREVIATURA, tipoEmpresanuevo.getTIE_ABREVIATURA());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_ABREVIATURA_ATP, tipoEmpresanuevo.getTIE_ABREVIATURA_ATP());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_MUESTRA_CONSUMO_CONVERTIDO, tipoEmpresanuevo.getTIE_MUESTRA_CONSUMO_CONVERTIDO());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_FACTOR_CONVERSION_CONSUMO, tipoEmpresanuevo.getTIE_FACTOR_CONVERSION_CONSUMO());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_ORDEN, tipoEmpresanuevo.getTIE_ORDEN());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_UTILIZA_MODULO_LECTURAS, tipoEmpresanuevo.getTIE_UTILIZA_MODULO_LECTURAS());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_USA_TRANSFORMADOR, tipoEmpresanuevo.getTIE_USA_TRANSFORMADOR());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_BAJA_PERMITE_CAMBIAR_EST_ADM, tipoEmpresanuevo.getTIE_BAJA_PERMITE_CAMBIAR_EST_ADM());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_ACTIVO, tipoEmpresanuevo.getTIE_ACTIVO());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_ID_USER, tipoEmpresanuevo.getTIE_ID_USER());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_FECHA_UPDATE, tipoEmpresanuevo.getTIE_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_LOTE_REPLICACION , tipoEmpresanuevo.getTIE_LOTE_REPLICACION());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_MODO_CALCULO_TARIFA_CONSUMO , tipoEmpresanuevo.getTIE_MODO_CALCULO_TARIFA_CONSUMO());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_AJUSTA_CARGO_FIJO_ALTA_SUMINISTRO , tipoEmpresanuevo.getTIE_AJUSTA_CARGO_FIJO_ALTA_SUMINISTRO());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_AJUSTA_ESCALA_CONSUMO_ALTA_SUMINISTRO , tipoEmpresanuevo.getTIE_AJUSTA_ESCALA_CONSUMO_ALTA_SUMINISTRO());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_AJUSTA_CARGO_FIJO_BAJA_SUMINISTRO , tipoEmpresanuevo.getTIE_AJUSTA_CARGO_FIJO_BAJA_SUMINISTRO());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_AJUSTA_ESCALA_CONSUMO_BAJA_SUMINISTRO , tipoEmpresanuevo.getTIE_AJUSTA_CARGO_FIJO_BAJA_SUMINISTRO());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_MEDIDOR_FUNCION , tipoEmpresanuevo.getTIE_MEDIDOR_FUNCION());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_COLOR , tipoEmpresanuevo.getTIE_COLOR());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_EMPRESA_COBRANZA , tipoEmpresanuevo.getTIE_EMPRESA_COBRANZA());
        valores.put(ContratoCorpicoApp.TipoEmpresa.TIE_ESTADO_OPE_BAJA , tipoEmpresanuevo.getTIE_ESTADO_OPE_BAJA());

        String whereClause = String.format("%s=?", ContratoCorpicoApp.TipoEmpresa.TIE_ID);
        String [] whereArgs = {String.valueOf(tipoEmpresanuevo.getTIE_ID())};

        int resultado = db.update(BaseDatosCorpicoApp.Tablas.TIPO_EMPRESA,valores,whereClause,whereArgs);
        return resultado > 0;
    }
    public boolean eliminarTipoEmpresa(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = ContratoCorpicoApp.TipoEmpresa.TIE_ID + "=?";
        String [] whereArgs = {String.valueOf(id)};
        int resultado = db.delete(BaseDatosCorpicoApp.Tablas.TIPO_EMPRESA,whereClause,whereArgs);
        return resultado >0;
    }
    // TIPO USUARIO
    public Cursor obtenerTipoUsuario(){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(TIPO_USUARIO);
        return builder.query(db,null,null,null,null,null,null);
    }
    public Cursor obtenerTipoUsuarioPorId(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String seleccion = String.format("%s=?", ContratoCorpicoApp.TipoUsuario.TIU_ID);
        String [] selectionArgs = {String.valueOf(id)};

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(TIPO_USUARIO);

        return builder.query(db,null,seleccion,selectionArgs,null,null,null);
    }
    public int  insertarTipoUsuario(RestTipoUsuario tipoUsuario) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.TipoUsuario.TIU_ID, tipoUsuario.getTIU_ID());
        valores.put(ContratoCorpicoApp.TipoUsuario.TIU_DESCRIPCION, tipoUsuario.getTIU_DESCRIPCION());
        valores.put(ContratoCorpicoApp.TipoUsuario.TIU_TIPO_EMPRESA , tipoUsuario.getTIU_TIPO_EMPRESA());
        valores.put(ContratoCorpicoApp.TipoUsuario.TIU_ABREVIATURA , tipoUsuario.getTIU_ABREVIATURA());
        valores.put(ContratoCorpicoApp.TipoUsuario.TIU_SOLICITA_CIIU , tipoUsuario.getTIU_SOLICITA_CIIU());
        valores.put(ContratoCorpicoApp.TipoUsuario.TIU_PUNTOS_MEDICION_MULTIPLES , tipoUsuario.getTIU_PUNTOS_MEDICION_MULTIPLES());
        valores.put(ContratoCorpicoApp.TipoUsuario.TIU_MEDIDOR_FUNCION_MULTIPLE , tipoUsuario.getTIU_MEDIDOR_FUNCION_MULTIPLE());
        valores.put(ContratoCorpicoApp.TipoUsuario.TIU_ACTIVO , tipoUsuario.getTIU_ACTIVO());
        valores.put(ContratoCorpicoApp.TipoUsuario.TIU_ID_USER , tipoUsuario.getTIU_ID_USER());
        valores.put(ContratoCorpicoApp.TipoUsuario.TIU_FECHA_UPDATE , tipoUsuario.getTIU_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.TipoUsuario.TIU_LOTE_REPLICACION , tipoUsuario.getTIU_LOTE_REPLICACION());

        db.insertOrThrow(TIPO_USUARIO, null, valores);
        return tipoUsuario.getTIU_ID();
    }
    public boolean actualizarTipoUsuario(RestTipoUsuario tipoUsuarionuevo ){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.TipoUsuario.TIU_ID, tipoUsuarionuevo.getTIU_ID());
        valores.put(ContratoCorpicoApp.TipoUsuario.TIU_DESCRIPCION, tipoUsuarionuevo.getTIU_DESCRIPCION());
        valores.put(ContratoCorpicoApp.TipoUsuario.TIU_TIPO_EMPRESA , tipoUsuarionuevo.getTIU_TIPO_EMPRESA());
        valores.put(ContratoCorpicoApp.TipoUsuario.TIU_ABREVIATURA , tipoUsuarionuevo.getTIU_ABREVIATURA());
        valores.put(ContratoCorpicoApp.TipoUsuario.TIU_SOLICITA_CIIU , tipoUsuarionuevo.getTIU_SOLICITA_CIIU());
        valores.put(ContratoCorpicoApp.TipoUsuario.TIU_PUNTOS_MEDICION_MULTIPLES , tipoUsuarionuevo.getTIU_PUNTOS_MEDICION_MULTIPLES());
        valores.put(ContratoCorpicoApp.TipoUsuario.TIU_MEDIDOR_FUNCION_MULTIPLE , tipoUsuarionuevo.getTIU_MEDIDOR_FUNCION_MULTIPLE());
        valores.put(ContratoCorpicoApp.TipoUsuario.TIU_ACTIVO , tipoUsuarionuevo.getTIU_ACTIVO());
        valores.put(ContratoCorpicoApp.TipoUsuario.TIU_ID_USER , tipoUsuarionuevo.getTIU_ID_USER());
        valores.put(ContratoCorpicoApp.TipoUsuario.TIU_FECHA_UPDATE , tipoUsuarionuevo.getTIU_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.TipoUsuario.TIU_LOTE_REPLICACION , tipoUsuarionuevo.getTIU_LOTE_REPLICACION());

        String whereClause = String.format("%s=?", ContratoCorpicoApp.TipoUsuario.TIU_ID);
        String [] whereArgs = {String.valueOf(tipoUsuarionuevo.getTIU_ID())};

        int resultado = db.update(BaseDatosCorpicoApp.Tablas.TIPO_EMPRESA,valores,whereClause,whereArgs);
        return resultado > 0;
    }
    public boolean eliminarTipoUsuario(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = ContratoCorpicoApp.TipoUsuario.TIU_ID + "=?";
        String [] whereArgs = {String.valueOf(id)};
        int resultado = db.delete(BaseDatosCorpicoApp.Tablas.TIPO_USUARIO,whereClause,whereArgs);
        return resultado >0;
    }

    // SECTOR
    public Cursor obtenerSector(){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(SECTOR);
        return builder.query(db,null,null,null,null,null,null);
    }
    public Cursor obtenerSectorPorId(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String seleccion = String.format("%s=?", ContratoCorpicoApp.Sector.SEC_ID);
        String [] selectionArgs = {String.valueOf(id)};

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(SECTOR);

        return builder.query(db,null,seleccion,selectionArgs,null,null,null);
    }
    public int  insertarSector(RestSector tipoSector) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.Sector.SEC_ID, tipoSector.getSEC_ID());
        valores.put(ContratoCorpicoApp.Sector.SEC_ID_SERVICIO, tipoSector.getSEC_ID_SERVICIO());
        valores.put(ContratoCorpicoApp.Sector.SEC_DESCRIPCION, tipoSector.getSEC_DESCRIPCION());
        valores.put(ContratoCorpicoApp.Sector.SEC_FECHA_UPDATE, tipoSector.getSEC_FECHA_UPDATE());

        db.insertOrThrow(SECTOR, null, valores);
        return tipoSector.getSEC_ID();
    }
    public boolean actualizarSector(RestSector sectornuevo ){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.Sector.SEC_ID, sectornuevo.getSEC_ID());
        valores.put(ContratoCorpicoApp.Sector.SEC_ID_SERVICIO, sectornuevo.getSEC_ID_SERVICIO());
        valores.put(ContratoCorpicoApp.Sector.SEC_DESCRIPCION, sectornuevo.getSEC_DESCRIPCION());
        valores.put(ContratoCorpicoApp.Sector.SEC_FECHA_UPDATE, sectornuevo.getSEC_FECHA_UPDATE());

        String whereClause = String.format("%s=?", ContratoCorpicoApp.Sector.SEC_ID);
        String [] whereArgs = {String.valueOf(sectornuevo.getSEC_ID())};

        int resultado = db.update(BaseDatosCorpicoApp.Tablas.SECTOR,valores,whereClause,whereArgs);
        return resultado > 0;
    }
    public boolean eliminarSector(int id){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = ContratoCorpicoApp.Sector.SEC_ID + "=?";
        String [] whereArgs = {String.valueOf(id)};
        int resultado = db.delete(BaseDatosCorpicoApp.Tablas.SECTOR,whereClause,whereArgs);
        return resultado >0;
    }

    // PUNTO MEDICION MEDIDOR
    public Cursor obtenerPuntoMedicionMedidor(){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(PUNTO_MEDICION_MEDIDOR);
        return builder.query(db,null,null,null,null,null,null);
    }
    public Cursor obtenerPuntoMedicionMedidorPorId(Integer cliente, short suministro, byte tipoEmpresa,short puntoMedicion, Integer medidor){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String seleccion = String.format("%s=? AND %s=? AND %s=? AND %s=? AND %s=?",cliente,suministro,tipoEmpresa,puntoMedicion,medidor);
        String [] selectionArgs = {String.valueOf(cliente),String.valueOf(suministro),String.valueOf(tipoEmpresa),
                String.valueOf(puntoMedicion),String.valueOf(medidor)};

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(PUNTO_MEDICION_MEDIDOR);

        return builder.query(db,null,seleccion,selectionArgs,null,null,null);
    }
    public String  insertarPuntoMedicionMedidor(RestPuntoMedicionMedidor puntoMedicionMedidor) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_EMPRESA, puntoMedicionMedidor.getPMM_EMPRESA());
        valores.put(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_CLIENTE, puntoMedicionMedidor.getPMM_CLIENTE());
        valores.put(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_SUMINISTRO, puntoMedicionMedidor.getPMM_SUMINISTRO());
        valores.put(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_TIPO_EMPRESA, puntoMedicionMedidor.getPMM_TIPO_EMPRESA());
        valores.put(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_PUNTO_MEDICION, puntoMedicionMedidor.getPMM_PUNTO_MEDICION());
        valores.put(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_MEDIDOR, puntoMedicionMedidor.getPMM_MEDIDOR());
        valores.put(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_UBICACION_HABITACULO, puntoMedicionMedidor.getPMM_UBICACION_HABITACULO());
        valores.put(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_ADVERTENCIA, puntoMedicionMedidor.getPMM_EMPRESA());
        valores.put(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_ES_FACTURABLE, puntoMedicionMedidor.getPMM_ADVERTENCIA());
        valores.put(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_ID_USER, puntoMedicionMedidor.getPMM_ID_USER());
        valores.put(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_FECHA_UPDATE, puntoMedicionMedidor.getPMM_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_LOTE_REPLICACION, puntoMedicionMedidor.getPMM_LOTE_REPLICACION());

        db.insertOrThrow(PUNTO_MEDICION_MEDIDOR, null, valores);
        return puntoMedicionMedidor.getPMM_CLIENTE() + "#" +  puntoMedicionMedidor.getPMM_MEDIDOR() + "#" + puntoMedicionMedidor.getPMM_TIPO_EMPRESA()
                            + "#" +  puntoMedicionMedidor.getPMM_PUNTO_MEDICION() + "#" +  puntoMedicionMedidor.getPMM_MEDIDOR();
    }
    public boolean actualizarPuntoMedicionMedidor(RestPuntoMedicionMedidor puntoMedicionMedidornuevo ){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_EMPRESA, puntoMedicionMedidornuevo.getPMM_EMPRESA());
        valores.put(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_CLIENTE, puntoMedicionMedidornuevo.getPMM_CLIENTE());
        valores.put(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_SUMINISTRO, puntoMedicionMedidornuevo.getPMM_SUMINISTRO());
        valores.put(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_TIPO_EMPRESA, puntoMedicionMedidornuevo.getPMM_TIPO_EMPRESA());
        valores.put(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_PUNTO_MEDICION, puntoMedicionMedidornuevo.getPMM_PUNTO_MEDICION());
        valores.put(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_MEDIDOR, puntoMedicionMedidornuevo.getPMM_MEDIDOR());
        valores.put(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_UBICACION_HABITACULO, puntoMedicionMedidornuevo.getPMM_UBICACION_HABITACULO());
        valores.put(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_ADVERTENCIA, puntoMedicionMedidornuevo.getPMM_EMPRESA());
        valores.put(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_ES_FACTURABLE, puntoMedicionMedidornuevo.getPMM_ADVERTENCIA());
        valores.put(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_ID_USER, puntoMedicionMedidornuevo.getPMM_ID_USER());
        valores.put(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_FECHA_UPDATE, puntoMedicionMedidornuevo.getPMM_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_LOTE_REPLICACION, puntoMedicionMedidornuevo.getPMM_LOTE_REPLICACION());

        String whereClause = String.format("%s=? AND %s=? AND %s=? AND %s=? AND %s=?",
                ContratoCorpicoApp.PuntoMedicioMedidor.PMM_CLIENTE, ContratoCorpicoApp.PuntoMedicioMedidor.PMM_SUMINISTRO,
                ContratoCorpicoApp.PuntoMedicioMedidor.PMM_TIPO_EMPRESA,ContratoCorpicoApp.PuntoMedicioMedidor.PMM_PUNTO_MEDICION,
                ContratoCorpicoApp.PuntoMedicioMedidor.PMM_MEDIDOR);
        String [] whereArgs = {String.valueOf(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_CLIENTE), String.valueOf(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_SUMINISTRO),
                String.valueOf(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_TIPO_EMPRESA),String.valueOf(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_PUNTO_MEDICION),
                String.valueOf(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_MEDIDOR)};

        int resultado = db.update(BaseDatosCorpicoApp.Tablas.PUNTO_MEDICION_MEDIDOR,valores,whereClause,whereArgs);
        return resultado > 0;
    }
    public boolean eliminarPuntoMedicionMedidor(Integer cliente, short suministro, byte tipoEmpresa,short puntoMedicion, Integer medidor){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause =  String.format("%s=? AND %s=? AND %s=? AND %s=? AND %s=?",cliente,suministro,tipoEmpresa,puntoMedicion,medidor);
        String [] whereArgs = {String.valueOf(cliente),String.valueOf(suministro),String.valueOf(tipoEmpresa),
                String.valueOf(puntoMedicion),String.valueOf(medidor)};
        int resultado = db.delete(BaseDatosCorpicoApp.Tablas.PUNTO_MEDICION_MEDIDOR,whereClause,whereArgs);
        return resultado >0;
    }

    // SUMINISTROS
    public Cursor obtenerSuministro(){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(SUMINISTRO);
        return builder.query(db,null,null,null,null,null,null);
    }
    public Cursor obtenerSuministroPorId(Integer cliente, short suministro){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String seleccion = String.format("%s=? AND %s=?",cliente,suministro);
        String [] selectionArgs = {String.valueOf(cliente),String.valueOf(suministro)};

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(SUMINISTRO);

        return builder.query(db,null,seleccion,selectionArgs,null,null,null);
    }
    public String  insertarSuministro(RestSuministro suministro) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.Suministro.SUM_EMPRESA, suministro.getSUM_EMPRESA());
        valores.put(ContratoCorpicoApp.Suministro.SUM_CLIENTE, suministro.getSUM_CLIENTE());
        valores.put(ContratoCorpicoApp.Suministro.SUM_ID, suministro.getSUM_ID());
        valores.put(ContratoCorpicoApp.Suministro.SUM_SUCURSAL, suministro.getSUM_SUCURSAL());
        valores.put(ContratoCorpicoApp.Suministro.SUM_GRUPO, suministro.getSUM_GRUPO());
        valores.put(ContratoCorpicoApp.Suministro.SUM_RUTA, suministro.getSUM_RUTA());
        valores.put(ContratoCorpicoApp.Suministro.SUM_ORDEN_LECTURA, suministro.getSUM_ORDEN_LECTURA());
        valores.put(ContratoCorpicoApp.Suministro.SUM_LOCALIDAD, suministro.getSUM_LOCALIDAD());
        valores.put(ContratoCorpicoApp.Suministro.SUM_CODIGO_POSTAL, suministro.getSUM_CODIGO_POSTAL());
        valores.put(ContratoCorpicoApp.Suministro.SUM_CALLE, suministro.getSUM_CALLE());
        valores.put(ContratoCorpicoApp.Suministro.SUM_ALTURA, suministro.getSUM_ALTURA());
        valores.put(ContratoCorpicoApp.Suministro.SUM_PISO, suministro.getSUM_PISO());
        valores.put(ContratoCorpicoApp.Suministro.SUM_DEPARTAMENTO, suministro.getSUM_DEPARTAMENTO());
        valores.put(ContratoCorpicoApp.Suministro.SUM_CALLE_ENTRE1, suministro.getSUM_CALLE_ENTRE1());
        valores.put(ContratoCorpicoApp.Suministro.SUM_CALLE_ENTRE2, suministro.getSUM_CALLE_ENTRE2());
        valores.put(ContratoCorpicoApp.Suministro.SUM_ANEXO, suministro.getSUM_ANEXO());
        valores.put(ContratoCorpicoApp.Suministro.SUM_REFERENCIA_MUNICIPAL, suministro.getSUM_REFERENCIA_MUNICIPAL());
        valores.put(ContratoCorpicoApp.Suministro.SUM_CIRCUNSCRIPCION, suministro.getSUM_CIRCUNSCRIPCION());
        valores.put(ContratoCorpicoApp.Suministro.SUM_RADIO, suministro.getSUM_RADIO());
        valores.put(ContratoCorpicoApp.Suministro.SUM_MANZANA, suministro.getSUM_MANZANA());
        valores.put(ContratoCorpicoApp.Suministro.SUM_QUINTA, suministro.getSUM_QUINTA());
        valores.put(ContratoCorpicoApp.Suministro.SUM_PARCELA, suministro.getSUM_PARCELA());
        valores.put(ContratoCorpicoApp.Suministro.SUM_SUBPARCELA, suministro.getSUM_SUBPARCELA());
        valores.put(ContratoCorpicoApp.Suministro.SUM_INQUILINO, suministro.getSUM_INQUILINO());
        valores.put(ContratoCorpicoApp.Suministro.SUM_FECHA_VTO_ALQUILER, suministro.getSUM_FECHA_VTO_ALQUILER());
        valores.put(ContratoCorpicoApp.Suministro.SUM_CIIU, suministro.getSUM_CIIU());
        valores.put(ContratoCorpicoApp.Suministro.SUM_DIRECCION_POSTAL, suministro.getSUM_DIRECCION_POSTAL());
        valores.put(ContratoCorpicoApp.Suministro.SUM_GARANTE, suministro.getSUM_GARANTE());
        valores.put(ContratoCorpicoApp.Suministro.SUM_MOROSIDAD, suministro.getSUM_MOROSIDAD());
        valores.put(ContratoCorpicoApp.Suministro.SUM_FACTURABLE, suministro.getSUM_FACTURABLE());
        valores.put(ContratoCorpicoApp.Suministro.SUM_ESTADO_ADM, suministro.getSUM_ESTADO_ADM());
        valores.put(ContratoCorpicoApp.Suministro.SUM_FECHA_ESTADO_ADM, suministro.getSUM_FECHA_ESTADO_ADM());
        valores.put(ContratoCorpicoApp.Suministro.SUM_FECHA_CAMBIO_TITULARIDAD, suministro.getSUM_FECHA_CAMBIO_TITULARIDAD());
        valores.put(ContratoCorpicoApp.Suministro.SUM_TIENE_CONCEPTOS_PUNTUALES, suministro.getSUM_TIENE_CONCEPTOS_PUNTUALES());
        valores.put(ContratoCorpicoApp.Suministro.SUM_VALIDA_PUNTUAL, suministro.getSUM_VALIDA_PUNTUAL());
        valores.put(ContratoCorpicoApp.Suministro.SUM_SUMINISTRO_ANTERIOR1, suministro.getSUM_SUMINISTRO_ANTERIOR1());
        valores.put(ContratoCorpicoApp.Suministro.SUM_SUMINISTRO_ANTERIOR2, suministro.getSUM_SUMINISTRO_ANTERIOR2());
        valores.put(ContratoCorpicoApp.Suministro.SUM_SUMINISTRO_ANTERIOR3, suministro.getSUM_SUMINISTRO_ANTERIOR3());
        valores.put(ContratoCorpicoApp.Suministro.SUM_SUMINISTRO_ANTERIOR4, suministro.getSUM_SUMINISTRO_ANTERIOR4());
        valores.put(ContratoCorpicoApp.Suministro.SUM_ID_USER, suministro.getSUM_ID_USER());
        valores.put(ContratoCorpicoApp.Suministro.SUM_FECHA_UPDATE, suministro.getSUM_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.Suministro.SUM_LOTE_REPLICACION, suministro.getSUM_LOTE_REPLICACION());

        db.insertOrThrow(SUMINISTRO, null, valores);
        return suministro.getSUM_CLIENTE() + "#" +  suministro.getSUM_ID();
    }
    public boolean actualizarSuministro(RestSuministro suministronuevo ){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.Suministro.SUM_EMPRESA, suministronuevo.getSUM_EMPRESA());
        valores.put(ContratoCorpicoApp.Suministro.SUM_CLIENTE, suministronuevo.getSUM_CLIENTE());
        valores.put(ContratoCorpicoApp.Suministro.SUM_ID, suministronuevo.getSUM_ID());
        valores.put(ContratoCorpicoApp.Suministro.SUM_SUCURSAL, suministronuevo.getSUM_SUCURSAL());
        valores.put(ContratoCorpicoApp.Suministro.SUM_GRUPO, suministronuevo.getSUM_GRUPO());
        valores.put(ContratoCorpicoApp.Suministro.SUM_RUTA, suministronuevo.getSUM_RUTA());
        valores.put(ContratoCorpicoApp.Suministro.SUM_ORDEN_LECTURA, suministronuevo.getSUM_ORDEN_LECTURA());
        valores.put(ContratoCorpicoApp.Suministro.SUM_LOCALIDAD, suministronuevo.getSUM_LOCALIDAD());
        valores.put(ContratoCorpicoApp.Suministro.SUM_CODIGO_POSTAL, suministronuevo.getSUM_CODIGO_POSTAL());
        valores.put(ContratoCorpicoApp.Suministro.SUM_CALLE, suministronuevo.getSUM_CALLE());
        valores.put(ContratoCorpicoApp.Suministro.SUM_ALTURA, suministronuevo.getSUM_ALTURA());
        valores.put(ContratoCorpicoApp.Suministro.SUM_PISO, suministronuevo.getSUM_PISO());
        valores.put(ContratoCorpicoApp.Suministro.SUM_DEPARTAMENTO, suministronuevo.getSUM_DEPARTAMENTO());
        valores.put(ContratoCorpicoApp.Suministro.SUM_CALLE_ENTRE1, suministronuevo.getSUM_CALLE_ENTRE1());
        valores.put(ContratoCorpicoApp.Suministro.SUM_CALLE_ENTRE2, suministronuevo.getSUM_CALLE_ENTRE2());
        valores.put(ContratoCorpicoApp.Suministro.SUM_ANEXO, suministronuevo.getSUM_ANEXO());
        valores.put(ContratoCorpicoApp.Suministro.SUM_REFERENCIA_MUNICIPAL, suministronuevo.getSUM_REFERENCIA_MUNICIPAL());
        valores.put(ContratoCorpicoApp.Suministro.SUM_CIRCUNSCRIPCION, suministronuevo.getSUM_CIRCUNSCRIPCION());
        valores.put(ContratoCorpicoApp.Suministro.SUM_RADIO, suministronuevo.getSUM_RADIO());
        valores.put(ContratoCorpicoApp.Suministro.SUM_MANZANA, suministronuevo.getSUM_MANZANA());
        valores.put(ContratoCorpicoApp.Suministro.SUM_QUINTA, suministronuevo.getSUM_QUINTA());
        valores.put(ContratoCorpicoApp.Suministro.SUM_PARCELA, suministronuevo.getSUM_PARCELA());
        valores.put(ContratoCorpicoApp.Suministro.SUM_SUBPARCELA, suministronuevo.getSUM_SUBPARCELA());
        valores.put(ContratoCorpicoApp.Suministro.SUM_INQUILINO, suministronuevo.getSUM_INQUILINO());
        valores.put(ContratoCorpicoApp.Suministro.SUM_FECHA_VTO_ALQUILER, suministronuevo.getSUM_FECHA_VTO_ALQUILER());
        valores.put(ContratoCorpicoApp.Suministro.SUM_CIIU, suministronuevo.getSUM_CIIU());
        valores.put(ContratoCorpicoApp.Suministro.SUM_DIRECCION_POSTAL, suministronuevo.getSUM_DIRECCION_POSTAL());
        valores.put(ContratoCorpicoApp.Suministro.SUM_GARANTE, suministronuevo.getSUM_GARANTE());
        valores.put(ContratoCorpicoApp.Suministro.SUM_MOROSIDAD, suministronuevo.getSUM_MOROSIDAD());
        valores.put(ContratoCorpicoApp.Suministro.SUM_FACTURABLE, suministronuevo.getSUM_FACTURABLE());
        valores.put(ContratoCorpicoApp.Suministro.SUM_ESTADO_ADM, suministronuevo.getSUM_ESTADO_ADM());
        valores.put(ContratoCorpicoApp.Suministro.SUM_FECHA_ESTADO_ADM, suministronuevo.getSUM_FECHA_ESTADO_ADM());
        valores.put(ContratoCorpicoApp.Suministro.SUM_FECHA_CAMBIO_TITULARIDAD, suministronuevo.getSUM_FECHA_CAMBIO_TITULARIDAD());
        valores.put(ContratoCorpicoApp.Suministro.SUM_TIENE_CONCEPTOS_PUNTUALES, suministronuevo.getSUM_TIENE_CONCEPTOS_PUNTUALES());
        valores.put(ContratoCorpicoApp.Suministro.SUM_VALIDA_PUNTUAL, suministronuevo.getSUM_VALIDA_PUNTUAL());
        valores.put(ContratoCorpicoApp.Suministro.SUM_SUMINISTRO_ANTERIOR1, suministronuevo.getSUM_SUMINISTRO_ANTERIOR1());
        valores.put(ContratoCorpicoApp.Suministro.SUM_SUMINISTRO_ANTERIOR2, suministronuevo.getSUM_SUMINISTRO_ANTERIOR2());
        valores.put(ContratoCorpicoApp.Suministro.SUM_SUMINISTRO_ANTERIOR3, suministronuevo.getSUM_SUMINISTRO_ANTERIOR3());
        valores.put(ContratoCorpicoApp.Suministro.SUM_SUMINISTRO_ANTERIOR4, suministronuevo.getSUM_SUMINISTRO_ANTERIOR4());
        valores.put(ContratoCorpicoApp.Suministro.SUM_ID_USER, suministronuevo.getSUM_ID_USER());
        valores.put(ContratoCorpicoApp.Suministro.SUM_FECHA_UPDATE, suministronuevo.getSUM_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.Suministro.SUM_LOTE_REPLICACION, suministronuevo.getSUM_LOTE_REPLICACION());

        String whereClause = String.format("%s=? AND %s=?",
                ContratoCorpicoApp.Suministro.SUM_CLIENTE, ContratoCorpicoApp.Suministro.SUM_ID);
        String [] whereArgs = {String.valueOf(ContratoCorpicoApp.Suministro.SUM_CLIENTE), String.valueOf(ContratoCorpicoApp.Suministro.SUM_ID)};

        int resultado = db.update(BaseDatosCorpicoApp.Tablas.SUMINISTRO,valores,whereClause,whereArgs);
        return resultado > 0;
    }
    public boolean eliminarSuministro(Integer cliente, short suministro){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause =  String.format("%s=? AND %s=? ",cliente,suministro);
        String [] whereArgs = {String.valueOf(cliente),String.valueOf(suministro)};
        int resultado = db.delete(BaseDatosCorpicoApp.Tablas.SUMINISTRO,whereClause,whereArgs);
        return resultado >0;
    }

    // SUMINISTROS TIPO EMPRESA
    public Cursor obtenerSuministroTipoEmpresa(){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(SUMINISTRO_TIPO_EMPRESA);
        return builder.query(db,null,null,null,null,null,null);
    }
    public Cursor obtenerSuministroTipoEmpresaPorId(Integer cliente, short suministro){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String seleccion = String.format("%s=? AND %s=?",cliente,suministro);
        String [] selectionArgs = {String.valueOf(cliente),String.valueOf(suministro)};

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(SUMINISTRO_TIPO_EMPRESA);

        return builder.query(db,null,seleccion,selectionArgs,null,null,null);
    }
    public String  insertarSuministroTipoEmpresa(RestSuministroTipoEmpresa suministroTipoEmpresa) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_EMPRESA, suministroTipoEmpresa.getSTE_EMPRESA());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_CLIENTE, suministroTipoEmpresa.getSTE_CLIENTE());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_SUMINISTRO, suministroTipoEmpresa.getSTE_SUMINISTRO());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_TIPO_EMPRESA, suministroTipoEmpresa.getSTE_TIPO_EMPRESA());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_FECHA_INGRESO, suministroTipoEmpresa.getSTE_FECHA_INGRESO());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_ESTADO_OPE, suministroTipoEmpresa.getSTE_ESTADO_OPE());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_FECHA_ESTADO_OPE , suministroTipoEmpresa.getSTE_FECHA_ESTADO_OPE());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_TIPO_CONSUMO, suministroTipoEmpresa.getSTE_TIPO_CONSUMO());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_TIPO_USUARIO, suministroTipoEmpresa.getSTE_TIPO_USUARIO());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_TIPO_SERVICIO, suministroTipoEmpresa.getSTE_TIPO_SERVICIO());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_TIPO_CONEXION, suministroTipoEmpresa.getSTE_TIPO_CONEXION());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_POTENCIA, suministroTipoEmpresa.getSTE_POTENCIA());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_POSEE_PROYECTO, suministroTipoEmpresa.getSTE_POSEE_PROYECTO());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_RESERVA_CAPACIDAD, suministroTipoEmpresa.getSTE_RESERVA_CAPACIDAD());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_UNIDAD_PROVEEDORA, suministroTipoEmpresa.getSTE_UNIDAD_PROVEEDORA());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_SUB_UNIDAD_PROVEEDORA, suministroTipoEmpresa.getSTE_SUB_UNIDAD_PROVEEDORA());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_TRANSFORMADOR, suministroTipoEmpresa.getSTE_TRANSFORMADOR());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_ID_USER, suministroTipoEmpresa.getSTE_ID_USER());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_FECHA_UPDATE, suministroTipoEmpresa.getSTE_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_LOTE_REPLICACION, suministroTipoEmpresa.getSTE_LOTE_REPLICACION());

        db.insertOrThrow(SUMINISTRO_TIPO_EMPRESA, null, valores);
        return suministroTipoEmpresa.getSTE_CLIENTE() + "#" +  suministroTipoEmpresa.getSTE_SUMINISTRO();
    }
    public boolean actualizarSuministroTipoEmpresa(RestSuministroTipoEmpresa suministroTipoEmpresanuevo ){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_EMPRESA, suministroTipoEmpresanuevo.getSTE_EMPRESA());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_CLIENTE, suministroTipoEmpresanuevo.getSTE_CLIENTE());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_SUMINISTRO, suministroTipoEmpresanuevo.getSTE_SUMINISTRO());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_TIPO_EMPRESA, suministroTipoEmpresanuevo.getSTE_TIPO_EMPRESA());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_FECHA_INGRESO, suministroTipoEmpresanuevo.getSTE_FECHA_INGRESO());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_ESTADO_OPE, suministroTipoEmpresanuevo.getSTE_ESTADO_OPE());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_FECHA_ESTADO_OPE , suministroTipoEmpresanuevo.getSTE_FECHA_ESTADO_OPE());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_TIPO_CONSUMO, suministroTipoEmpresanuevo.getSTE_TIPO_CONSUMO());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_TIPO_USUARIO, suministroTipoEmpresanuevo.getSTE_TIPO_USUARIO());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_TIPO_SERVICIO, suministroTipoEmpresanuevo.getSTE_TIPO_SERVICIO());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_TIPO_CONEXION, suministroTipoEmpresanuevo.getSTE_TIPO_CONEXION());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_POTENCIA, suministroTipoEmpresanuevo.getSTE_POTENCIA());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_POSEE_PROYECTO, suministroTipoEmpresanuevo.getSTE_POSEE_PROYECTO());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_RESERVA_CAPACIDAD, suministroTipoEmpresanuevo.getSTE_RESERVA_CAPACIDAD());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_UNIDAD_PROVEEDORA, suministroTipoEmpresanuevo.getSTE_UNIDAD_PROVEEDORA());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_SUB_UNIDAD_PROVEEDORA, suministroTipoEmpresanuevo.getSTE_SUB_UNIDAD_PROVEEDORA());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_TRANSFORMADOR, suministroTipoEmpresanuevo.getSTE_TRANSFORMADOR());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_ID_USER, suministroTipoEmpresanuevo.getSTE_ID_USER());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_FECHA_UPDATE, suministroTipoEmpresanuevo.getSTE_FECHA_UPDATE());
        valores.put(ContratoCorpicoApp.SuministroTipoEmpresa.STE_LOTE_REPLICACION, suministroTipoEmpresanuevo.getSTE_LOTE_REPLICACION());

        String whereClause = String.format("%s=? AND %s=?",
                ContratoCorpicoApp.SuministroTipoEmpresa.STE_CLIENTE, ContratoCorpicoApp.SuministroTipoEmpresa.STE_SUMINISTRO);
        String [] whereArgs = {String.valueOf(ContratoCorpicoApp.SuministroTipoEmpresa.STE_CLIENTE), String.valueOf(ContratoCorpicoApp.SuministroTipoEmpresa.STE_SUMINISTRO)};

        int resultado = db.update(BaseDatosCorpicoApp.Tablas.SUMINISTRO_TIPO_EMPRESA,valores,whereClause,whereArgs);
        return resultado > 0;
    }
    public boolean eliminarSuministroTipoEmpresa(Integer cliente, short suministro){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause =  String.format("%s=? AND %s=? ",cliente,suministro);
        String [] whereArgs = {String.valueOf(cliente),String.valueOf(suministro)};
        int resultado = db.delete(BaseDatosCorpicoApp.Tablas.SUMINISTRO_TIPO_EMPRESA,whereClause,whereArgs);
        return resultado >0;
    }


    // SUMINISTROS POSICION GLOBAL
    public Cursor obtenerSuministroPosicionGlobal(){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(SUMINISTRO_POSICION_GLOBAL);
        return builder.query(db,null,null,null,null,null,null);
    }
    public Cursor obtenerSuministroPosicionGlobalPorId(Integer cliente, short suministro){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String seleccion = String.format("%s=? AND %s=?",cliente,suministro);
        String [] selectionArgs = {String.valueOf(cliente),String.valueOf(suministro)};

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(SUMINISTRO_POSICION_GLOBAL);

        return builder.query(db,null,seleccion,selectionArgs,null,null,null);
    }
    public String  insertarSuministroPosicionGlobal(RestSuministroPosicionGlobal suministroPosicionGlobal) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_EMPRESA, suministroPosicionGlobal.getSPG_EMPRESA());
        valores.put(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_CLIENTE, suministroPosicionGlobal.getSPG_EMPRESA());
        valores.put(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_SUMINISTRO, suministroPosicionGlobal.getSPG_EMPRESA());
        valores.put(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_LATITUD, suministroPosicionGlobal.getSPG_EMPRESA());
        valores.put(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_LONGITUD, suministroPosicionGlobal.getSPG_EMPRESA());
        valores.put(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_FECHA_CAPTURA, suministroPosicionGlobal.getSPG_EMPRESA());
        valores.put(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_USER_ID, suministroPosicionGlobal.getSPG_EMPRESA());
        valores.put(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_FECHA_UPDATE, suministroPosicionGlobal.getSPG_EMPRESA());

        db.insertOrThrow(SUMINISTRO_POSICION_GLOBAL, null, valores);
        return suministroPosicionGlobal.getSPG_CLIENTE() + "#" +  suministroPosicionGlobal.getSPG_SUMINISTRO();
    }
    public boolean actualizarSuministroPosicionGlobal(RestSuministroPosicionGlobal suministroPosicionGlobalnuevo ){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_EMPRESA, suministroPosicionGlobalnuevo.getSPG_EMPRESA());
        valores.put(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_CLIENTE, suministroPosicionGlobalnuevo.getSPG_EMPRESA());
        valores.put(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_SUMINISTRO, suministroPosicionGlobalnuevo.getSPG_EMPRESA());
        valores.put(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_LATITUD, suministroPosicionGlobalnuevo.getSPG_EMPRESA());
        valores.put(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_LONGITUD, suministroPosicionGlobalnuevo.getSPG_EMPRESA());
        valores.put(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_FECHA_CAPTURA, suministroPosicionGlobalnuevo.getSPG_EMPRESA());
        valores.put(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_USER_ID, suministroPosicionGlobalnuevo.getSPG_EMPRESA());
        valores.put(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_FECHA_UPDATE, suministroPosicionGlobalnuevo.getSPG_EMPRESA());

        String whereClause = String.format("%s=? AND %s=?",
                ContratoCorpicoApp.SuministroPosicionGlobal.SPG_CLIENTE, ContratoCorpicoApp.SuministroPosicionGlobal.SPG_SUMINISTRO);
        String [] whereArgs = {String.valueOf(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_CLIENTE), String.valueOf(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_SUMINISTRO)};

        int resultado = db.update(BaseDatosCorpicoApp.Tablas.SUMINISTRO_POSICION_GLOBAL,valores,whereClause,whereArgs);
        return resultado > 0;
    }
    public boolean eliminarSuministroPosicionGlobal(Integer cliente, short suministro){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause =  String.format("%s=? AND %s=? ",cliente,suministro);
        String [] whereArgs = {String.valueOf(cliente),String.valueOf(suministro)};
        int resultado = db.delete(BaseDatosCorpicoApp.Tablas.SUMINISTRO_POSICION_GLOBAL,whereClause,whereArgs);
        return resultado >0;
    }

    // TIPO TRABAJO CUADRILLA
    public Cursor obtenerTipoTrabajoCuadrilla(){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(TIPO_TRABAJO_CUADRILLA);
        return builder.query(db,null,null,null,null,null,null);
    }
    public Cursor obtenerTipoTrabajoCuadrillaPorId(Integer cuadrilla, short tipoTrabajo){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String seleccion = String.format("%s=? AND %s=?",cuadrilla,tipoTrabajo);
        String [] selectionArgs = {String.valueOf(cuadrilla),String.valueOf(tipoTrabajo)};

        SQLiteQueryBuilder builder = new  SQLiteQueryBuilder();
        builder.setTables(TIPO_TRABAJO_CUADRILLA);

        return builder.query(db,null,seleccion,selectionArgs,null,null,null);
    }
    public String  insertarTipoTrabajoCuadrilla(RestTipoTrabajoCuadrilla tipoTrabajoCuadrilla) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_CUADRILLA, tipoTrabajoCuadrilla.getTipoCuadrillaID());
        valores.put(ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_TIPO_TRABAJO, tipoTrabajoCuadrilla.getTipoTabajoId());
        valores.put(ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_FECHA_UPDATE, tipoTrabajoCuadrilla.getFecha_update());

        db.insertOrThrow(TIPO_TRABAJO_CUADRILLA, null, valores);
        return tipoTrabajoCuadrilla.getTipoCuadrillaID() + "#" +  tipoTrabajoCuadrilla.getTipoTabajoId();
    }
    public boolean actualizarTipoTrabajoCuadrilla(RestTipoTrabajoCuadrilla tipoTrabajoCuadrillanuevo ){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_CUADRILLA, tipoTrabajoCuadrillanuevo.getTipoCuadrillaID());
        valores.put(ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_TIPO_TRABAJO, tipoTrabajoCuadrillanuevo.getTipoTabajoId());
        valores.put(ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_FECHA_UPDATE, tipoTrabajoCuadrillanuevo.getFecha_update());

        String whereClause = String.format("%s=? AND %s=?",
                ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_CUADRILLA, ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_TIPO_TRABAJO);
        String [] whereArgs = {String.valueOf(ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_CUADRILLA), String.valueOf(ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_TIPO_TRABAJO)};

        int resultado = db.update(BaseDatosCorpicoApp.Tablas.TIPO_TRABAJO_CUADRILLA,valores,whereClause,whereArgs);
        return resultado > 0;
    }
    public boolean eliminarTipoTrabajoCuadrilla(Integer cuadrilla, short tipoTrabajo){
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause =  String.format("%s=? AND %s=? ",cuadrilla,tipoTrabajo);
        String [] whereArgs = {String.valueOf(cuadrilla),String.valueOf(tipoTrabajo)};
        int resultado = db.delete(BaseDatosCorpicoApp.Tablas.TIPO_TRABAJO_CUADRILLA,whereClause,whereArgs);
        return resultado >0;
    }

    public SQLiteDatabase getDB(){
        return baseDatos.getWritableDatabase();
    }
}
