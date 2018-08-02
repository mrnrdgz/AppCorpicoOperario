package ar.com.corpico.appcorpico.sqlite;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;

import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp.OrdenesOperativas;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp.Suministro;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp.Zona;

import static ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp.OrdenesOperativas.obtenerIdOrdenOperativa;

/**
 * Created by Administrador on 21/02/2018.
 */

public class CorpicoAppProvider extends ContentProvider {

    public static final String TAG = "Provider";
    public static final String URI_NO_SOPORTADA = "Uri no soportada";

    private BaseDatosCorpicoApp helper;
    private ContentResolver resolver;

    // Casos
    public static final int ORDENES_OPERATIVAS = 100;
    public static final int ORDENES_OPERATIVAS_ID = 101;
    public static final int TIPOS_TRABAJOS = 200;
    public static final int TIPOS_TRABAJOS_ID = 201;
    public static final int MOTIVOS_TRABAJOS = 300;
    public static final int MOTIVOS_TRABAJOS_ID = 301;
    public static final int TIPOS_CUADRILLAS = 400;
    public static final int TIPOS_CUADRILLAS_ID = 401;
    public static final int CLIENTES = 500;
    public static final int CLIENTES_ID = 501;
    public static final int ETAPAS = 600;
    public static final int ETAPAS_ID = 601;
    public static final int TURNOS = 700;
    public static final int TURNOS_ID = 701;
    public static final int EMPRESAS_CONTRATISTAS = 800;
    public static final int EMPRESAS_CONTRATISTAS_ID = 801;
    public static final int ESTADOS_OPERATIVO = 900;
    public static final int ESTADOS_OPERATIVO_ID = 901;
    public static final int LOCALIDADES = 1000;
    public static final int LOCALIDADES_ID = 1001;
    public static final int MEDIDORES = 1100;
    public static final int MEDIDORES_ID = 1101;
    public static final int MEDIDORES_CLASES = 1200;
    public static final int MEDIDORES_CLASES_ID = 1201;
    public static final int MEDIDORES_MARCAS = 1300;
    public static final int MEDIDORES_MARCAS_ID = 1301;
    public static final int MEDIDORES_MODELOS = 1400;
    public static final int MEDIDORES_MODELOS_ID = 1401;
    public static final int MEDIDORES_TIPOS = 1500;
    public static final int MEDIDORES_TIPOS_ID = 1501;
    public static final int PUNTO_MEDICION_MEDIDOR = 1600;
    public static final int PUNTO_MEDICION_MEDIDOR_ID = 1601;
    public static final int OPERARIO = 1700;
    public static final int OPERARIO_ID = 1701;
    public static final int TIPO_CONEXION = 1800;
    public static final int TIPO_CONEXION_ID = 1801;
    public static final int TIPO_CONSUMO = 1900;
    public static final int TIPO_CONSUMO_ID = 1901;
    public static final int TIPO_EMPRESA = 2000;
    public static final int TIPO_EMPRESA_ID = 2001;
    public static final int TIPO_USUARIO = 2100;
    public static final int TIPO_USUARIO_ID = 2101;
    public static final int SECTOR = 2200;
    public static final int SECTOR_ID = 2201;
    public static final int SUMINISTROS = 2300;
    public static final int SUMINISTROS_ID = 2301;
    public static final int SUMINISTROS_TIPO_EMPRESA = 2400;
    public static final int SUMINISTROS_TIPO_EMPRESA_ID = 2401;
    public static final int SUMINISTROS_POSICION_GLOBAL = 2500;
    public static final int SUMINISTROS_POSICION_GLOBAL_ID = 2501;
    public static final int TIPO_TRABAJO_CUADRILLA = 2600;
    public static final int TIPO_TRABAJO_CUADRILLA_ID = 2601;
    public static final int ZONAS = 2700;
    public static final int ZONAS_ID = 2701;
    public static final int FOTOS = 2800;
    public static final int FOTOS_ID = 2801;
    public static final int CUADRILLAS = 2900;
    public static final int CUADRILLAS_ID = 2901;
    public static final int ESTADOS = 3000;
    public static final int ESTADOS_ID = 3001;
    public static final int PUNTOS_ZONA = 3100;
    public static final int PUNTOS_ZONA_ID = 3101;
    public static final int NOTAS = 3200;
    public static final int NOTAS_ID = 3201;


    public static final UriMatcher uriMatcher;

    //public static final String AUTORIDAD = "ar.com.corpico.appcorpico";

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "ordenes_operativas", ORDENES_OPERATIVAS);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "ordenes_operativas/*", ORDENES_OPERATIVAS_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "tipos_trabajos", TIPOS_TRABAJOS);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "tipos_trabajos/*", TIPOS_TRABAJOS_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "motivos_trabajos", MOTIVOS_TRABAJOS);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "motivos_trabajos/*", MOTIVOS_TRABAJOS_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "tipos_cuadrillas", TIPOS_CUADRILLAS);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "tipos_cuadrillas/*", TIPOS_CUADRILLAS_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "clientes", CLIENTES);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "clientes/*", CLIENTES_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "etapas", ETAPAS);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "etapas/*", ETAPAS_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "turnos", TURNOS);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "turnos/*", TURNOS_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "empresas_contratista", EMPRESAS_CONTRATISTAS);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "empresas_contratista/*", EMPRESAS_CONTRATISTAS_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "estados_operativo", ESTADOS_OPERATIVO);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "estados_operativo/*", ESTADOS_OPERATIVO_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "localidades", LOCALIDADES);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "localidades/*", LOCALIDADES_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "medidores", MEDIDORES);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "medidores/*", MEDIDORES_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "medidores_clases", MEDIDORES_CLASES);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "medidores_clases/*", MEDIDORES_CLASES_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "medidores_marcas", MEDIDORES_MARCAS);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "medidores_marcas/*", MEDIDORES_MARCAS_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "medidores_modelos", MEDIDORES_MODELOS);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "medidores_modelos/*", MEDIDORES_MODELOS_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "medidores_tipos", MEDIDORES_TIPOS);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "medidores_tipos/*", MEDIDORES_TIPOS_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "punto_medicion_medidor", PUNTO_MEDICION_MEDIDOR);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "punto_medicion_medidor/*", PUNTO_MEDICION_MEDIDOR_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "operario", OPERARIO);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "operario/*", OPERARIO_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "tipo_conexion", TIPO_CONEXION);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "tipo_conexion/*", TIPO_CONEXION_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "tipo_consumos", TIPO_CONSUMO);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "tipo_consumos/*", TIPO_CONSUMO_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "tipo_empresas", TIPO_EMPRESA);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "tipo_empresas/*", TIPO_EMPRESA_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "tipo_usuarios", TIPO_USUARIO);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "tipo_usuarios/*", TIPO_USUARIO_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "sectores", SECTOR);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "sectores/*", SECTOR_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "suministros", SUMINISTROS);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "suministros/*", SUMINISTROS_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "suministros_tipo_empresa", SUMINISTROS_TIPO_EMPRESA);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "suministros_tipo_empresa/*", SUMINISTROS_TIPO_EMPRESA_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "suministros_posicion_global", SUMINISTROS_POSICION_GLOBAL);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "suministros_posicion_global/*", SUMINISTROS_POSICION_GLOBAL_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "tipo_trabajo_cuadrilla", TIPO_TRABAJO_CUADRILLA);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "tipo_trabajo_cuadrilla/*", TIPO_TRABAJO_CUADRILLA_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "zonas", ZONAS);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "zonas/*", ZONAS_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "fotos", FOTOS);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "fotos/*", FOTOS_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "cuadrillas", CUADRILLAS);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "cuadrillas/*", CUADRILLAS_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "estados", ESTADOS);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "estados/*", ESTADOS_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "puntos_zona", PUNTOS_ZONA);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "puntos_zona/*", PUNTOS_ZONA_ID);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "notas", NOTAS);
        uriMatcher.addURI(ContratoCorpicoApp.AUTHORITY, "notas/*", NOTAS_ID);

    }

    public CorpicoAppProvider() {
    }

    @Override
    public boolean onCreate() {
        // Inicializando gestor BD
        helper = new BaseDatosCorpicoApp(getContext());
        resolver = getContext().getContentResolver();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder) {
        // Obtener base de datos
        SQLiteDatabase bd = helper.getReadableDatabase();

        // Comparar Uri
        int match = uriMatcher.match(uri);

        // string auxiliar para los ids
        int id;
        String filtro;

        Cursor c = null;

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        switch (match) {
            case ORDENES_OPERATIVAS:
                // Obtener filtro
                filtro = OrdenesOperativas.tieneFiltro(uri)
                        ? construirFiltro(uri.getQueryParameter("filtro")) : null;

                // Obtener parámetro "Display" de la uri
                String displayParamConDisplay = OrdenesOperativas.obtenerParamConDisplay(uri);

                switch (displayParamConDisplay) {
                    case OrdenesOperativas.DISPLAY_ORDEN:
                        String displayParamOrden = OrdenesOperativas.obtenerParamOrden(uri);
                        String INNER_JOIN = "select fecha_solicitud, numero, tipo_trabajo, motivo_trabajo,\n" +
                                "turno,\n" +
                                "observacion_al_operario,asociado,estado,suministro, titular, \n" +
                                "calle,altura, piso, dpto,localidad, anexo, grupo, ruta,orden_lectura,\n" +
                                "latitud,longitud, \n" +
                                "tipo_usuario,tipo_consumo,potencia_declarada,medidor,medidor_marca, medidor_modelo,capacidad,tension,factorM\n" +
                                "from orden_operativa \n" +
                                "left join turno ON orden_operativa.numero = turno.orden  \n" +
                                "where orden_operativa.numero = ?";

                        c = bd.rawQuery(INNER_JOIN, new String[]{displayParamOrden});
                        break;
                    case OrdenesOperativas.DISPLAY_FULL:
                        String JOIN = "select fecha_solicitud, numero, tipo_trabajo, motivo_trabajo,\n" +
                                "turno,\n" +
                                "observacion_al_operario,asociado,estado,suministro, titular, \n" +
                                "calle,altura, piso, dpto,localidad, anexo, grupo, ruta,orden_lectura,\n" +
                                "latitud,longitud, \n" +
                                "tipo_usuario,tipo_consumo,potencia_declarada,medidor,medidor_marca, medidor_modelo,capacidad,tension,factorM\n" +
                                "from orden_operativa \n" +
                                "left join turno ON orden_operativa.numero = turno.orden";

                        c = bd.rawQuery(JOIN, new String[]{});
                        break;
                    case OrdenesOperativas.DISPLAY_HOME:
                        String displayParamCuadrillaHome[] = OrdenesOperativas.obtenerParamHome(uri).split("#");
                        /*String INNER = "select descripcion,COUNT(cuadrilla) \n" +
                                " from orden_operativa \n" +
                                " inner join tipo_cuadrilla on cuadrilla = id and servicio=? and tipo_cuadrilla.sector=orden_operativa.sector \n" +
                                " where tipo_cuadrilla.sector=? and estado =? group by descripcion";*/

                        String INNER = "select descripcion,COUNT(cuadrilla) \n" +
                                " from tipo_cuadrilla \n" +
                                " left join orden_operativa on cuadrilla = id and servicio=? and tipo_cuadrilla.sector=orden_operativa.sector and estado =? \n" +
                                " where tipo_cuadrilla.sector=?  group by descripcion";
                        c = bd.rawQuery(INNER, new String[]{displayParamCuadrillaHome[0], "O", displayParamCuadrillaHome[1]});
                        break;
                    case OrdenesOperativas.DISPLAY_ESTADO_HOME:
                        String displayParamEstadoHome[] = OrdenesOperativas.obtenerParamHome(uri).split("#");
                        String HOME_ESTADOS = "select estado.estado,COUNT(orden_operativa.estado) \n" +
                                " from estado \n" +
                                " left join orden_operativa on orden_operativa.estado = estado.id  \n" +
                                " where estado.id != ? and estado.id != ? and estado.id != ? and estado.id != ? \n" +
                                " group by estado.estado";
                        c = bd.rawQuery(HOME_ESTADOS, new String[]{"A", "C", "R", "O"}); //"P","O","E","N","S","V"
                        break;
                    case OrdenesOperativas.DISPLAY_TIPO_TRABAJO_HOME:
                        String displayParamTipoTrabajoHome[] = OrdenesOperativas.obtenerParamHome(uri).split("#");
                        String HOME_TRABAJOS = "select tipo_trabajo.descripcion,COUNT(orden_operativa.tipo_trabajo) \n" +
                                " from tipo_trabajo \n" +
                                " left join orden_operativa on orden_operativa.tipo_trabajo = tipo_trabajo.descripcion and  orden_operativa.estado=? \n" +
                                " group by tipo_trabajo.descripcion";
                        //todo: ver si aca pongo los otros estados No ejecutadas y verifacar
                        c = bd.rawQuery(HOME_TRABAJOS, new String[]{"P"});
                        break;
                    case OrdenesOperativas.DISPLAY_ZONA_HOME:
                        String displayParamZonaHome[] = OrdenesOperativas.obtenerParamHome(uri).split("#");
                        String HOME_ZONAS = "select zona,COUNT(zona) \n" +
                                " from orden_operativa \n" +
                                " where estado=? \n" +
                                " group by zona";
                        //todo: ver si aca pongo los otros estados No ejecutadas y verifacar
                        c = bd.rawQuery(HOME_ZONAS, new String[]{"P"});
                        break;
                    case OrdenesOperativas.DISPLAY_SIN_ZONA:
                        String displayParamSinZona[] = OrdenesOperativas.obtenerParamSinZona(uri).split("#");
                        String SIN_ZONAS = "select numero,latitud,longitud \n" +
                                " from orden_operativa \n" +
                                " where latitud!=? and longitud!=? and estado=?";
                        //todo: ver si aca pongo los otros estados No ejecutadas y verifacar
                        c = bd.rawQuery(SIN_ZONAS, new String[]{"","","P"});
                        break;
                    case OrdenesOperativas.DISPLAY_SINGLE:
                        builder.setTables(OrdenesOperativas.TABLE_NAME);
                        c = builder.query(bd, null,
                                null, null, null, null, filtro);
                        break;

                }

                /*if (displayParamCuadrillaHome != null && displayParamConTurno == null) {
                    String INNER_JOIN = "select descripcion,COUNT(cuadrilla) \n" +
                            " from orden_operativa \n" +
                            " inner join tipo_cuadrilla on cuadrilla = id and servicio=? and tipo_cuadrilla.sector=orden_operativa.sector \n" +
                            " where tipo_cuadrilla.sector=? and estado =? group by descripcion";

                    c = bd.rawQuery(INNER_JOIN, new String[]{displayParamCuadrillaHome[0],displayParamCuadrillaHome[1],"O"});
                }else {

                    if (displayParamConDisplay == null) {

                    } else {

                        if (displayParamConDisplay.equals(OrdenesOperativas.NUMERO_ORDEN_KEY)) {
                            String INNER_JOIN = "select fecha_solicitud, numero, tipo_trabajo, motivo_trabajo,\n" +
                                    "turno,\n" +
                                    "observacion_al_operario,asociado,estado,suministro, titular, \n" +
                                    "calle,altura, piso, dpto,localidad, anexo, grupo, ruta,orden_lectura,\n" +
                                    "latitud,longitud, \n" +
                                    "tipo_usuario,tipo_consumo,potencia_declarada,medidor,medidor_marca, medidor_modelo,capacidad,tension,factorM\n" +
                                    "from orden_operativa \n" +
                                    "left join turno ON orden_operativa.numero = turno.orden  \n" +
                                    "where orden_operativa.numero = ?";*/

                // c = bd.rawQuery(INNER_JOIN, new String[]{});
                      /*  }

                        if (displayParamConTurno.equals(OrdenesOperativas.DISPLAY_FULL)) {
                            String INNER_JOIN = "select fecha_solicitud, numero, tipo_trabajo, motivo_trabajo,\n" +
                                    "turno,\n" +
                                    "observacion_al_operario,asociado,estado,suministro, titular, \n" +
                                    "calle,altura, piso, dpto,localidad, anexo, grupo, ruta,orden_lectura,\n" +
                                    "latitud,longitud, \n" +
                                    "tipo_usuario,tipo_consumo,potencia_declarada,medidor,medidor_marca, medidor_modelo,capacidad,tension,factorM\n" +
                                    "from orden_operativa \n" +
                                    "left join turno ON orden_operativa.numero = turno.orden";

                            c = bd.rawQuery(INNER_JOIN, new String[]{});
                        }
                    }
               }*/
                break;
            case ORDENES_OPERATIVAS_ID:
                // Consultando una cabecera de pedido
                id = OrdenesOperativas.obtenerIdOrdenOperativa(uri);
                builder.setTables(OrdenesOperativas.TABLE_NAME);
                c = builder.query(bd, null,
                        OrdenesOperativas.numero + "=" + "\'" + id + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs, null, null, null);
                break;
            case TIPOS_TRABAJOS:
                // Obtener filtro
                filtro = ContratoCorpicoApp.TiposTrabajo.tieneFiltro(uri)
                        ? construirFiltro(uri.getQueryParameter("filtro")) : null;

                String cuadrillaParam = ContratoCorpicoApp.TiposTrabajo.obtenerParamCuadrilla(uri);

                if (cuadrillaParam == null) {
                    builder.setTables(ContratoCorpicoApp.TiposTrabajo.TABLE_NAME);
                    c = builder.query(bd, null,
                            null, null, null, null, filtro);
                } else {
                    if (cuadrillaParam.equals(ContratoCorpicoApp.TiposTrabajo.CUADRILLA_DEFAULT)) {
                        String INNER_JOIN = "select tc.descripcion,tt.descripcion  from tipo_trabajo_cuadrilla ttc \n" +
                                "INNER JOIN tipo_trabajo  tt ON ttc.tipo_trabajo = tt.id \n" +
                                "INNER JOIN tipo_cuadrilla tc ON tc.id = ttc.cuadrilla ";
                        c = bd.rawQuery(INNER_JOIN, new String[]{});
                    }

                }


                break;
            case TIPOS_TRABAJOS_ID:
                // Consultando una cabecera de pedido
                id = ContratoCorpicoApp.TiposTrabajo.obtenerIdTipoTrabajo(uri);
                builder.setTables(ContratoCorpicoApp.TiposTrabajo.TABLE_NAME);
                c = builder.query(bd, null,
                        ContratoCorpicoApp.TiposTrabajo.TIT_ID + "=" + "\'" + id + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs, null, null, null);
                break;
            case MOTIVOS_TRABAJOS:
                // Obtener filtro
                filtro = ContratoCorpicoApp.MotivosTrabajo.tieneFiltro(uri)
                        ? construirFiltro(uri.getQueryParameter("filtro")) : null;

                builder.setTables(ContratoCorpicoApp.MotivosTrabajo.TABLE_NAME);
                c = builder.query(bd, null,
                        null, null, null, null, filtro);
                break;
            case MOTIVOS_TRABAJOS_ID:
                // Consultando una cabecera de pedido
                id = ContratoCorpicoApp.MotivosTrabajo.obtenerIdMotivoTrabajo(uri);
                builder.setTables(ContratoCorpicoApp.MotivosTrabajo.TABLE_NAME);
                c = builder.query(bd, null,
                        ContratoCorpicoApp.MotivosTrabajo.MTR_ID + "=" + "\'" + id + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs, null, null, null);
                break;
            case TIPOS_CUADRILLAS:
                // Obtener filtro
                filtro = ContratoCorpicoApp.TiposCuadrilla.tieneFiltro(uri)
                        ? construirFiltro(uri.getQueryParameter("filtro")) : null;

                builder.setTables(ContratoCorpicoApp.TiposCuadrilla.TABLE_NAME);
                c = builder.query(bd, null,
                        null, null, null, null, filtro);
                break;
            case TIPOS_CUADRILLAS_ID:
                // Consultando una cabecera de pedido
                id = ContratoCorpicoApp.TiposCuadrilla.obtenerIdTipoCuadrilla(uri);
                builder.setTables(ContratoCorpicoApp.TiposCuadrilla.TABLE_NAME);
                c = builder.query(bd, null,
                        ContratoCorpicoApp.TiposCuadrilla.TC_ID + "=" + "\'" + id + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs, null, null, null);
                break;
            case CLIENTES:
                //
                builder.setTables(ContratoCorpicoApp.Clientes.TABLE_NAME);
                c = builder.query(bd, null,
                        null, null, null, null, null);
                break;
            case CLIENTES_ID:
                // Consultando una cabecera de pedido
                id = ContratoCorpicoApp.Clientes.obtenerIdCliente(uri);
                builder.setTables(ContratoCorpicoApp.Clientes.TABLE_NAME);
                c = builder.query(bd, null,
                        ContratoCorpicoApp.Clientes.CLI_ID + "=" + "\'" + id + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs, null, null, null);
                break;
            case ETAPAS:
                //// Obtener filtro
                filtro = ContratoCorpicoApp.Etapas.tieneFiltro(uri)
                        ? construirFiltro(uri.getQueryParameter("filtro")) : null;
                builder.setTables(ContratoCorpicoApp.Etapas.TABLE_NAME);

                // ID de orden asociada
                String ordenParam = ContratoCorpicoApp.Etapas.obtenerParamOrden(uri);

                if (ordenParam != null) {
                    c = builder.query(bd, null,
                            ContratoCorpicoApp.Etapas.ETA_NUMERO_ORDEN + "=?",
                            new String[]{ordenParam}, null, null, null, filtro);
                } else {
                    c = builder.query(bd, null,
                            selection, selectionArgs, null, null, null, filtro);
                }
                break;
            case ETAPAS_ID:
                // Consultando una cabecera de pedido
                id = ContratoCorpicoApp.Etapas.obtenerIdEtapa(uri);
                builder.setTables(ContratoCorpicoApp.Etapas.TABLE_NAME);
                c = builder.query(bd, null,
                        ContratoCorpicoApp.Etapas.ETA_ID + "=" + "\'" + id + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs, null, null, null);
                break;
            case TURNOS:
                builder.setTables(ContratoCorpicoApp.Turnos.TABLE_NAME);
                // ID de orden asociada
                String turnoParam = ContratoCorpicoApp.Turnos.obtenerParamOrden(uri);

                if (turnoParam != null) {
                    c = builder.query(bd, null,
                            ContratoCorpicoApp.Turnos.TUR_ORDEN + "=?",
                            new String[]{turnoParam}, null, null, null, null);
                } else {
                    c = builder.query(bd, null,
                            selection, selectionArgs, null, null, null, null);
                }

                break;
            case TURNOS_ID:
                // Consultando una cabecera de pedido
                id = ContratoCorpicoApp.Turnos.obtenerIdTurno(uri);
                builder.setTables(ContratoCorpicoApp.Turnos.TABLE_NAME);
                c = builder.query(bd, null,
                        ContratoCorpicoApp.Turnos.TUR_ORDEN + "=" + "\'" + id + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs, null, null, null);
                break;
            case EMPRESAS_CONTRATISTAS:
                //
                builder.setTables(ContratoCorpicoApp.EmpresasContratista.TABLE_NAME);
                c = builder.query(bd, null,
                        null, null, null, null, null);
                break;
            case EMPRESAS_CONTRATISTAS_ID:
                // Consultando una cabecera de pedido
                id = ContratoCorpicoApp.EmpresasContratista.obtenerIdEmpresaContratista(uri);
                builder.setTables(ContratoCorpicoApp.EmpresasContratista.TABLE_NAME);
                c = builder.query(bd, null,
                        ContratoCorpicoApp.EmpresasContratista.EML_ID + "=" + "\'" + id + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs, null, null, null);
                break;
            case ESTADOS_OPERATIVO:
                //
                builder.setTables(ContratoCorpicoApp.EstadoOperativo.TABLE_NAME);
                c = builder.query(bd, null,
                        null, null, null, null, null);
                break;
            case ESTADOS_OPERATIVO_ID:
                // Consultando una cabecera de pedido
                id = ContratoCorpicoApp.EstadoOperativo.obtenerIdEstadoOperativo(uri);
                builder.setTables(ContratoCorpicoApp.EstadoOperativo.TABLE_NAME);
                c = builder.query(bd, null,
                        ContratoCorpicoApp.EstadoOperativo.EOP_ID + "=" + "\'" + id + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs, null, null, null);
                break;
            case LOCALIDADES:
                //
                builder.setTables(ContratoCorpicoApp.Localidad.TABLE_NAME);
                c = builder.query(bd, null,
                        null, null, null, null, null);
                break;
            case LOCALIDADES_ID:
                // Consultando una cabecera de pedido
                id = ContratoCorpicoApp.Localidad.obtenerIdLocalidad(uri);
                builder.setTables(ContratoCorpicoApp.Localidad.TABLE_NAME);
                c = builder.query(bd, null,
                        ContratoCorpicoApp.Localidad.LOC_ID + "=" + "\'" + id + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs, null, null, null);
                break;
            case MEDIDORES:
                //
                builder.setTables(ContratoCorpicoApp.Medidor.TABLE_NAME);
                c = builder.query(bd, null,
                        null, null, null, null, null);
                break;
            case MEDIDORES_ID:
                // Consultando una cabecera de pedido
                id = ContratoCorpicoApp.Medidor.obtenerIdMedidor(uri);
                builder.setTables(ContratoCorpicoApp.Medidor.TABLE_NAME);
                c = builder.query(bd, null,
                        ContratoCorpicoApp.Medidor.MED_ID + "=" + "\'" + id + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs, null, null, null);
                break;
            case MEDIDORES_CLASES:
                //
                builder.setTables(ContratoCorpicoApp.MedidorClase.TABLE_NAME);
                c = builder.query(bd, null,
                        null, null, null, null, null);
                break;
            case MEDIDORES_CLASES_ID:
                // Consultando una cabecera de pedido
                id = ContratoCorpicoApp.MedidorClase.obtenerIdMedidorClase(uri);
                builder.setTables(ContratoCorpicoApp.MedidorClase.TABLE_NAME);
                c = builder.query(bd, null,
                        ContratoCorpicoApp.MedidorClase.MCL_ID + "=" + "\'" + id + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs, null, null, null);
                break;
            case MEDIDORES_MARCAS:
                //
                builder.setTables(ContratoCorpicoApp.MedidorMarca.TABLE_NAME);
                c = builder.query(bd, null,
                        null, null, null, null, null);
                break;
            case MEDIDORES_MARCAS_ID:
                // Consultando una cabecera de pedido
                id = ContratoCorpicoApp.MedidorMarca.obtenerIdMedidorMarca(uri);
                builder.setTables(ContratoCorpicoApp.MedidorMarca.TABLE_NAME);
                c = builder.query(bd, null,
                        ContratoCorpicoApp.MedidorMarca.MCA_ID + "=" + "\'" + id + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs, null, null, null);
                break;
            case MEDIDORES_MODELOS:
                //
                builder.setTables(ContratoCorpicoApp.MedidorModelo.TABLE_NAME);
                c = builder.query(bd, null,
                        null, null, null, null, null);
                break;
            case MEDIDORES_MODELOS_ID:
                // Consultando una cabecera de pedido
                id = ContratoCorpicoApp.MedidorModelo.obtenerIdMedidorModelo(uri);
                builder.setTables(ContratoCorpicoApp.MedidorModelo.TABLE_NAME);
                c = builder.query(bd, null,
                        ContratoCorpicoApp.MedidorModelo.MMO_ID + "=" + "\'" + id + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs, null, null, null);
                break;
            case MEDIDORES_TIPOS:
                //
                builder.setTables(ContratoCorpicoApp.MedidorTipo.TABLE_NAME);
                c = builder.query(bd, null,
                        null, null, null, null, null);
                break;
            case MEDIDORES_TIPOS_ID:
                // Consultando una cabecera de pedido
                id = ContratoCorpicoApp.MedidorTipo.obtenerIdMedidorTipo(uri);
                builder.setTables(ContratoCorpicoApp.MedidorTipo.TABLE_NAME);
                c = builder.query(bd, null,
                        ContratoCorpicoApp.MedidorTipo.MET_ID + "=" + "\'" + id + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs, null, null, null);
                break;
            case PUNTO_MEDICION_MEDIDOR:
                //
                builder.setTables(ContratoCorpicoApp.PuntoMedicioMedidor.TABLE_NAME);
                c = builder.query(bd, null,
                        null, null, null, null, null);
                break;
            case PUNTO_MEDICION_MEDIDOR_ID:
                // Consultando una cabecera de pedido
                String[] ids = ContratoCorpicoApp.PuntoMedicioMedidor.obtenerIdPuntoMedicionMedidor(uri);
                String seleccion = String.format("%s=? AND %s=? AND %s=? AND %s=? AND %s=?",
                        ContratoCorpicoApp.PuntoMedicioMedidor.PMM_CLIENTE, ContratoCorpicoApp.PuntoMedicioMedidor.PMM_SUMINISTRO,
                        ContratoCorpicoApp.PuntoMedicioMedidor.PMM_TIPO_EMPRESA, ContratoCorpicoApp.PuntoMedicioMedidor.PMM_PUNTO_MEDICION,
                        ContratoCorpicoApp.PuntoMedicioMedidor.PMM_MEDIDOR);

                builder.setTables(ContratoCorpicoApp.PuntoMedicioMedidor.TABLE_NAME);
                c = builder.query(bd, null, seleccion,
                        selectionArgs, null, null, null);
                break;
            case OPERARIO:
                // Obtener parámetro "Display" de la uri
                String displayUriConSector = ContratoCorpicoApp.Operario.obtenerUriConSector(uri);

                if (displayUriConSector == null) {
                    builder.setTables(ContratoCorpicoApp.Operario.TABLE_NAME);
                    c = builder.query(bd, null,
                            null, null, null, null, null);
                } else {
                    String INNER_JOIN = "select operario.id,operario.descripcion,\n" +
                            "tipo_empresa,operario.contratista\n" +
                            "from operario \n" +
                            "left join empresa_contratista ON operario.contratista = empresa_contratista.id \n" +
                            "where  operario.contratista = ? and operario.id not in (select cuadrilla.operario \n" +
                            " from cuadrilla where operario.id = cuadrilla.operario)";
                    c = bd.rawQuery(INNER_JOIN, new String[]{displayUriConSector});
                }
                break;
            case OPERARIO_ID:
                // Consultando una cabecera de pedido
                id = ContratoCorpicoApp.Operario.obtenerIdOperario(uri);
                builder.setTables(ContratoCorpicoApp.Operario.TABLE_NAME);
                c = builder.query(bd, null,
                        ContratoCorpicoApp.Operario.OPE_ID + "=" + "\'" + id + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs, null, null, null);
                break;
            case TIPO_CONEXION:
                //
                builder.setTables(ContratoCorpicoApp.TipoConexion.TABLE_NAME);
                c = builder.query(bd, null,
                        null, null, null, null, null);
                break;
            case TIPO_CONEXION_ID:
                // Consultando una cabecera de pedido
                id = ContratoCorpicoApp.TipoConexion.obtenerIdTipoConexion(uri);
                builder.setTables(ContratoCorpicoApp.TipoConexion.TABLE_NAME);
                c = builder.query(bd, null,
                        ContratoCorpicoApp.TipoConexion.TCO_ID + "=" + "\'" + id + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs, null, null, null);
                break;
            case TIPO_CONSUMO:
                //
                builder.setTables(ContratoCorpicoApp.TipoConsumo.TABLE_NAME);
                c = builder.query(bd, null,
                        null, null, null, null, null);
                break;
            case TIPO_CONSUMO_ID:
                // Consultando una cabecera de pedido
                id = ContratoCorpicoApp.TipoConsumo.obtenerIdTipoConsumo(uri);
                builder.setTables(ContratoCorpicoApp.TipoConsumo.TABLE_NAME);
                c = builder.query(bd, null,
                        ContratoCorpicoApp.TipoConsumo.TIC_ID + "=" + "\'" + id + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs, null, null, null);
                break;
            case TIPO_EMPRESA:
                //
                builder.setTables(ContratoCorpicoApp.TipoEmpresa.TABLE_NAME);
                c = builder.query(bd, null,
                        null, null, null, null, null);
                break;
            case TIPO_EMPRESA_ID:
                // Consultando una cabecera de pedido
                id = ContratoCorpicoApp.TipoEmpresa.obtenerIdTipoEmpresa(uri);
                builder.setTables(ContratoCorpicoApp.TipoEmpresa.TABLE_NAME);
                c = builder.query(bd, null,
                        ContratoCorpicoApp.TipoEmpresa.TIE_ID + "=" + "\'" + id + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs, null, null, null);
                break;
            case TIPO_USUARIO:
                //
                builder.setTables(ContratoCorpicoApp.TipoUsuario.TABLE_NAME);
                c = builder.query(bd, null,
                        null, null, null, null, null);
                break;
            case TIPO_USUARIO_ID:
                // Consultando una cabecera de pedido
                id = ContratoCorpicoApp.TipoUsuario.obtenerIdTipoUsuario(uri);
                builder.setTables(ContratoCorpicoApp.TipoUsuario.TABLE_NAME);
                c = builder.query(bd, null,
                        ContratoCorpicoApp.TipoUsuario.TIU_ID + "=" + "\'" + id + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs, null, null, null);
                break;
            case SECTOR:
                //
                builder.setTables(ContratoCorpicoApp.Sector.TABLE_NAME);
                c = builder.query(bd, null,
                        null, null, null, null, null);
                break;
            case SECTOR_ID:
                // Consultando una cabecera de pedido
                id = ContratoCorpicoApp.Sector.obtenerIdSector(uri);
                builder.setTables(ContratoCorpicoApp.Sector.TABLE_NAME);
                c = builder.query(bd, null,
                        ContratoCorpicoApp.Sector.SEC_ID + "=" + "\'" + id + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs, null, null, null);
                break;
            case NOTAS:
                //
                builder.setTables(ContratoCorpicoApp.Nota.TABLE_NAME);
                c = builder.query(bd, null,
                        null, null, null, null, null);
                break;
            case NOTAS_ID:
                // Consultando una cabecera de pedido
                id = Integer.parseInt(ContratoCorpicoApp.Nota.obtenerIdNota(uri));
                builder.setTables(ContratoCorpicoApp.Nota.TABLE_NAME);
                c = builder.query(bd, null,
                        ContratoCorpicoApp.Nota.NOV_ID + "=" + "\'" + id + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs, null, null, null);
                break;
            case SUMINISTROS:
                //
                builder.setTables(Suministro.TABLE_NAME);
                c = builder.query(bd, null,
                        null, null, null, null, null);
                break;
            case SUMINISTROS_ID:
                // Consultando una cabecera de pedido
                ids = Suministro.obtenerIdSuministro(uri);
                seleccion = String.format("%s=? AND %s=?",
                        Suministro.SUM_CLIENTE, Suministro.SUM_ID);

                builder.setTables(Suministro.TABLE_NAME);
                c = builder.query(bd, null, seleccion,
                        selectionArgs, null, null, null);
                break;
            case SUMINISTROS_TIPO_EMPRESA:
                //
                builder.setTables(ContratoCorpicoApp.SuministroTipoEmpresa.TABLE_NAME);
                c = builder.query(bd, null,
                        null, null, null, null, null);
                break;
            case SUMINISTROS_TIPO_EMPRESA_ID:
                // Consultando una cabecera de pedido
                ids = ContratoCorpicoApp.SuministroTipoEmpresa.obtenerIdSuministroTipoEmpresa(uri);
                seleccion = String.format("%s=? AND %s=?",
                        ContratoCorpicoApp.SuministroTipoEmpresa.STE_CLIENTE, ContratoCorpicoApp.SuministroTipoEmpresa.STE_SUMINISTRO);

                builder.setTables(ContratoCorpicoApp.SuministroTipoEmpresa.TABLE_NAME);
                c = builder.query(bd, null, seleccion,
                        selectionArgs, null, null, null);
                break;
            case SUMINISTROS_POSICION_GLOBAL:
                //
                builder.setTables(ContratoCorpicoApp.SuministroPosicionGlobal.TABLE_NAME);
                c = builder.query(bd, null,
                        null, null, null, null, null);
                break;
            case SUMINISTROS_POSICION_GLOBAL_ID:
                // Consultando una cabecera de pedido
                ids = ContratoCorpicoApp.SuministroPosicionGlobal.obtenerIdSuministroPosicionGlobal(uri);
                seleccion = String.format("%s=? AND %s=?",
                        ContratoCorpicoApp.SuministroPosicionGlobal.SPG_CLIENTE, ContratoCorpicoApp.SuministroPosicionGlobal.SPG_SUMINISTRO);

                builder.setTables(ContratoCorpicoApp.SuministroPosicionGlobal.TABLE_NAME);
                c = builder.query(bd, null, seleccion,
                        selectionArgs, null, null, null);
                break;
            case TIPO_TRABAJO_CUADRILLA:
                //
                builder.setTables(ContratoCorpicoApp.TipoTrabajoCuadrilla.TABLE_NAME);
                c = builder.query(bd, null,
                        null, null, null, null, null);
                break;
            case TIPO_TRABAJO_CUADRILLA_ID:
                // Consultando una cabecera de pedido
                ids = ContratoCorpicoApp.TipoTrabajoCuadrilla.obtenerIdTipoTrabajoCuadrilla(uri);
                seleccion = String.format("%s=? AND %s=?",
                        ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_CUADRILLA, ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_TIPO_TRABAJO);

                builder.setTables(ContratoCorpicoApp.TipoTrabajoCuadrilla.TABLE_NAME);
                c = builder.query(bd, null, seleccion,
                        selectionArgs, null, null, null);
                break;
            case ZONAS:
                //
                String displayParamConFiltro = Zona.obtenerParamZonaFilter(uri);
                if(displayParamConFiltro == null){
                    builder.setTables(Zona.TABLE_NAME);
                    c = builder.query(bd,null,
                            null, null,
                            null, null, "id");
                }else{
                    /*String FILTRO = "select id \n" +
                            "from zona \n" +
                            "where descripcion = ?";

                    c = bd.rawQuery(FILTRO, new String[]{displayParamConFiltro});*/
                    builder.setTables(Zona.TABLE_NAME);
                    c = builder.query(bd, new String [] {Zona.ZON_ID} ,
                            Zona.ZON_DESCRIPCION + "=" + "\'" + displayParamConFiltro + "\'",
                            selectionArgs, null, null, null);

                }
                break;
            case ZONAS_ID:
                // Consultando una cabecera de pedido
                id = Zona.obtenerIdZona(uri);
                builder.setTables(Zona.TABLE_NAME);
                c = builder.query(bd,
                        projection,
                        Zona.ZON_ID + "=" + "\'" + id + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs, null, null, null);
                break;
            case PUNTOS_ZONA:
                // obtener el parametro con el que se realiza la llamada
                String ParamConDisplay = ContratoCorpicoApp.PuntoZona.obtenerParamConDisplay(uri);
                if(ParamConDisplay == null){
                    builder.setTables(ContratoCorpicoApp.PuntoZona.TABLE_NAME);
                    c = builder.query(bd, projection,
                            selection, selectionArgs,
                            null, null, null);
                }else{
                    String JOIN_ZONA = "select id_zona, descripcion,\n" +
                            "latitud,longitud\n" +
                            "from punto_zona \n" +
                            "left join zona ON punto_zona.id_zona = zona.id" ;
                    c = bd.rawQuery(JOIN_ZONA, new String[]{});

                }


                break;
            case PUNTOS_ZONA_ID:
                // Consultando una cabecera de pedido
                id = ContratoCorpicoApp.PuntoZona.obtenerIdPuntoZona(uri);
                builder.setTables(ContratoCorpicoApp.PuntoZona.TABLE_NAME);
                c = builder.query(bd, null,
                        ContratoCorpicoApp.PuntoZona.PZON_ID + "=" + "\'" + id + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs, null, null, null);
                break;
            case ESTADOS:
                // Estados
                builder.setTables(ContratoCorpicoApp.Estado.TABLE_NAME);
                c = builder.query(bd, null,
                        null, null, null, null, null);

                break;
            case ESTADOS_ID:
                String sid;
                // Consultando Estados
                sid = ContratoCorpicoApp.Estado.obtenerIdEstado(uri);
                builder.setTables(ContratoCorpicoApp.Estado.TABLE_NAME);
                c = builder.query(bd, null,
                        ContratoCorpicoApp.Estado.OOS_ID + "=" + "\'" + sid + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs, null, null, null);
                break;
            case CUADRILLAS:
                //
                String displayParamCuadrilla = ContratoCorpicoApp.Cuadrilla.obtenerParamConOperario(uri);
                if (displayParamCuadrilla == null) {
                    builder.setTables(ContratoCorpicoApp.Cuadrilla.TABLE_NAME);
                    c = builder.query(bd, null,
                            null, null, null, null, null);

                } else {
                    if (displayParamCuadrilla.equals(ContratoCorpicoApp.Cuadrilla.DISPLAY_FULL)) {
                        String INNER_JOIN = "select cuadrilla.id, tipo_cuadrilla, operario, fecha,\n" +
                                "cuadrilla.fecha_update,\n" +
                                "servicio,sector,descripcion \n" +
                                "from cuadrilla \n" +
                                "join operario ON cuadrilla.operario = operario.id";
                        //"where cuadrilla.tipo_cuadrilla = ?";

                        c = bd.rawQuery(INNER_JOIN, new String[]{});
                    }
                }

                break;
            case CUADRILLAS_ID:
                // Consultando una cabecera de pedido
                id = ContratoCorpicoApp.Cuadrilla.obtenerIdCuadrilla(uri);
                builder.setTables(ContratoCorpicoApp.Cuadrilla.TABLE_NAME);
                c = builder.query(bd, null,
                        ContratoCorpicoApp.Cuadrilla.CU_ID + "=" + "\'" + id + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs, null, null, null);
                break;
            case FOTOS:
                //
                builder.setTables(ContratoCorpicoApp.Foto.TABLE_NAME);

                String fotoordenParam = ContratoCorpicoApp.Etapas.obtenerParamOrden(uri);

                if (fotoordenParam != null) {
                    c = builder.query(bd, null,
                            ContratoCorpicoApp.Foto.OTF_NUMERO + "=?",
                            new String[]{fotoordenParam}, null, null, null, null);
                } else {
                    c = builder.query(bd, null,
                            selection, selectionArgs, null, null, null, null);
                }
                break;
            case FOTOS_ID:
                // Consultando una cabecera de pedido
                ids = ContratoCorpicoApp.Foto.obtenerIdFoto(uri);
                seleccion = String.format("%s=? AND %s=?",
                        ContratoCorpicoApp.Foto.OTF_NUMERO, ContratoCorpicoApp.Foto.OTF_NFOTO);

                builder.setTables(ContratoCorpicoApp.SuministroPosicionGlobal.TABLE_NAME);
                c = builder.query(bd, null, seleccion,
                        selectionArgs, null, null, null);
                break;
            default:
                throw new UnsupportedOperationException(URI_NO_SOPORTADA);
        }

        //assert c != null;
        c.setNotificationUri(resolver, uri);

        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ORDENES_OPERATIVAS:
                return ContratoCorpicoApp.generarMime("ordenes_operativas");
            case ORDENES_OPERATIVAS_ID:
                return ContratoCorpicoApp.generarMimeItem("ordenes_operativas");
            case TIPOS_TRABAJOS:
                return ContratoCorpicoApp.generarMime("tipos_trabajos");
            case TIPOS_TRABAJOS_ID:
                return ContratoCorpicoApp.generarMimeItem("tipos_trabajos");
            case MOTIVOS_TRABAJOS:
                return ContratoCorpicoApp.generarMime("motivos_trabajos");
            case MOTIVOS_TRABAJOS_ID:
                return ContratoCorpicoApp.generarMimeItem("motivos_trabajos");
            case TIPOS_CUADRILLAS:
                return ContratoCorpicoApp.generarMime("tipos_cuadrillas");
            case TIPOS_CUADRILLAS_ID:
                return ContratoCorpicoApp.generarMimeItem("tipos_cuadrillas");
            case CLIENTES:
                return ContratoCorpicoApp.generarMime("tipos_cuadrillas");
            case CLIENTES_ID:
                return ContratoCorpicoApp.generarMimeItem("tipos_cuadrillas");
            case TURNOS:
                return ContratoCorpicoApp.generarMime("turnos");
            case TURNOS_ID:
                return ContratoCorpicoApp.generarMimeItem("turnos");
            case ETAPAS:
                return ContratoCorpicoApp.generarMime("etapas");
            case ETAPAS_ID:
                return ContratoCorpicoApp.generarMimeItem("etapas");
            case EMPRESAS_CONTRATISTAS:
                return ContratoCorpicoApp.generarMime("empresas_contratista");
            case EMPRESAS_CONTRATISTAS_ID:
                return ContratoCorpicoApp.generarMimeItem("empresas_contratista");
            case ESTADOS_OPERATIVO:
                return ContratoCorpicoApp.generarMime("estados_operativo");
            case ESTADOS_OPERATIVO_ID:
                return ContratoCorpicoApp.generarMimeItem("estados_operativo");
            case LOCALIDADES:
                return ContratoCorpicoApp.generarMime("localidades");
            case LOCALIDADES_ID:
                return ContratoCorpicoApp.generarMimeItem("localidades");
            case MEDIDORES:
                return ContratoCorpicoApp.generarMime("medidores");
            case MEDIDORES_ID:
                return ContratoCorpicoApp.generarMimeItem("medidores");
            case MEDIDORES_CLASES:
                return ContratoCorpicoApp.generarMime("medidores_clases");
            case MEDIDORES_CLASES_ID:
                return ContratoCorpicoApp.generarMimeItem("medidores_clases");
            case MEDIDORES_MARCAS:
                return ContratoCorpicoApp.generarMime("medidores_marcas");
            case MEDIDORES_MARCAS_ID:
                return ContratoCorpicoApp.generarMimeItem("medidores_marcas");
            case MEDIDORES_MODELOS:
                return ContratoCorpicoApp.generarMime("medidores_modelos");
            case MEDIDORES_MODELOS_ID:
                return ContratoCorpicoApp.generarMimeItem("medidores_modelos");
            case MEDIDORES_TIPOS:
                return ContratoCorpicoApp.generarMime("medidores_tipos");
            case MEDIDORES_TIPOS_ID:
                return ContratoCorpicoApp.generarMimeItem("medidores_tipos");
            case PUNTO_MEDICION_MEDIDOR:
                return ContratoCorpicoApp.generarMime("punto_medicion_medidor");
            case PUNTO_MEDICION_MEDIDOR_ID:
                return ContratoCorpicoApp.generarMimeItem("punto_medicion_medidor");
            case OPERARIO:
                return ContratoCorpicoApp.generarMime("operario");
            case OPERARIO_ID:
                return ContratoCorpicoApp.generarMimeItem("operario");
            case TIPO_CONEXION:
                return ContratoCorpicoApp.generarMime("tipo_conexion");
            case TIPO_CONEXION_ID:
                return ContratoCorpicoApp.generarMimeItem("tipo_conexion");
            case TIPO_CONSUMO:
                return ContratoCorpicoApp.generarMime("tipo_consumo");
            case TIPO_CONSUMO_ID:
                return ContratoCorpicoApp.generarMimeItem("tipo_consumo");
            case TIPO_EMPRESA:
                return ContratoCorpicoApp.generarMime("tipo_empresa");
            case TIPO_EMPRESA_ID:
                return ContratoCorpicoApp.generarMimeItem("tipo_empresa");
            case TIPO_USUARIO:
                return ContratoCorpicoApp.generarMime("tipo_usuario");
            case TIPO_USUARIO_ID:
                return ContratoCorpicoApp.generarMimeItem("tipo_usuario");
            case SECTOR:
                return ContratoCorpicoApp.generarMime("sector");
            case SECTOR_ID:
                return ContratoCorpicoApp.generarMimeItem("sector");
            case SUMINISTROS:
                return ContratoCorpicoApp.generarMime("suministro");
            case SUMINISTROS_ID:
                return ContratoCorpicoApp.generarMimeItem("suministro");
            case SUMINISTROS_TIPO_EMPRESA:
                return ContratoCorpicoApp.generarMime("suministro_tipo_empresa");
            case SUMINISTROS_TIPO_EMPRESA_ID:
                return ContratoCorpicoApp.generarMimeItem("suministro_tipo_empresa");
            case SUMINISTROS_POSICION_GLOBAL:
                return ContratoCorpicoApp.generarMime("suministro_posicion_global");
            case SUMINISTROS_POSICION_GLOBAL_ID:
                return ContratoCorpicoApp.generarMimeItem("suministro_posicion_global");
            case TIPO_TRABAJO_CUADRILLA:
                return ContratoCorpicoApp.generarMime("tipo_trabajo_cuadrilla");
            case TIPO_TRABAJO_CUADRILLA_ID:
                return ContratoCorpicoApp.generarMimeItem("tipo_trabajo_cuadrilla");
            case ZONAS:
                return ContratoCorpicoApp.generarMime("zona");
            case ZONAS_ID:
                return ContratoCorpicoApp.generarMimeItem("zona");
            case FOTOS:
                return ContratoCorpicoApp.generarMime("foto");
            case FOTOS_ID:
                return ContratoCorpicoApp.generarMimeItem("foto");
            case CUADRILLAS:
                return ContratoCorpicoApp.generarMime("cuadrilla");
            case CUADRILLAS_ID:
                return ContratoCorpicoApp.generarMimeItem("cuadrilla");
            case ESTADOS:
                return ContratoCorpicoApp.generarMime("estado");
            case ESTADOS_ID:
                return ContratoCorpicoApp.generarMimeItem("estado");
            case PUNTOS_ZONA:
                return ContratoCorpicoApp.generarMime("punto_zona");
            case PUNTOS_ZONA_ID:
                return ContratoCorpicoApp.generarMimeItem("punto_zona");
            case NOTAS:
                return ContratoCorpicoApp.generarMime("nota");
            case NOTAS_ID:
                return ContratoCorpicoApp.generarMimeItem("nota");
            default:
                throw new UnsupportedOperationException("Uri desconocida =>" + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "Inserción en " + uri + "( " + values.toString() + " )\n");

        SQLiteDatabase bd = helper.getWritableDatabase();

        Integer id = null;
        Integer cuadrilla, tipoTrabajo;
        String fecha_update;

        switch (uriMatcher.match(uri)) {
            case ORDENES_OPERATIVAS:
                id = values.getAsInteger(OrdenesOperativas.numero);
                bd.insertOrThrow(OrdenesOperativas.TABLE_NAME, null, values);
                notificarCambio(uri);
                return OrdenesOperativas.crearUriOrdenOperativa(id);
            case TIPOS_TRABAJOS:
                id = values.getAsInteger(ContratoCorpicoApp.TiposTrabajo.TIT_ID);
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.TIPO_TRABAJO, null, values);
                notificarCambio(uri);
                return ContratoCorpicoApp.TiposTrabajo.crearUriTipoTrabajo(id); // todo: ver
            case MOTIVOS_TRABAJOS:
                id = values.getAsInteger(ContratoCorpicoApp.MotivosTrabajo.MTR_ID);
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.MOTIVO_TRABAJO, null, values);
                notificarCambio(uri);
                return ContratoCorpicoApp.MotivosTrabajo.crearUriMotivoTrabajo(id);
            case TIPOS_CUADRILLAS:
                id = values.getAsInteger(ContratoCorpicoApp.TiposCuadrilla.TC_ID);
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.TIPO_CUADRILLA, null, values);
                notificarCambio(uri);
                return ContratoCorpicoApp.TiposCuadrilla.crearUriTipoCuadrilla(id);
            case CLIENTES:
                id = values.getAsInteger(ContratoCorpicoApp.Clientes.CLI_ID);
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.CLIENTE, null, values);
                notificarCambio(uri);
                return ContratoCorpicoApp.Clientes.crearUriCliente(id);
            case ETAPAS:
                id = values.getAsInteger(ContratoCorpicoApp.Etapas.ETA_ID);
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.ETAPA, null, values);
                notificarCambio(uri);
                return ContratoCorpicoApp.Etapas.crearUriEtapa(id);
            case TURNOS:
                id = values.getAsInteger(ContratoCorpicoApp.Turnos.TUR_ORDEN);
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.TURNO, null, values);
                notificarCambio(uri);
                return ContratoCorpicoApp.Turnos.crearUriTurno(id);
            case EMPRESAS_CONTRATISTAS:
                id = values.getAsInteger(ContratoCorpicoApp.EmpresasContratista.EML_ID);
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.EMPRESA_CONTRATISTA, null, values);
                notificarCambio(uri);
                return ContratoCorpicoApp.EmpresasContratista.crearUriEmpresaContratista(id);
            case ESTADOS_OPERATIVO:
                id = values.getAsInteger(ContratoCorpicoApp.EstadoOperativo.EOP_ID);
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.ESTADO_OPERATIVO, null, values);
                notificarCambio(uri);
                return ContratoCorpicoApp.EstadoOperativo.crearUriEstadoOperativo(id);
            case LOCALIDADES:
                id = values.getAsInteger(ContratoCorpicoApp.Localidad.LOC_ID);
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.LOCALIDAD, null, values);
                notificarCambio(uri);
                return ContratoCorpicoApp.Localidad.crearUriLocalidad(id);
            case MEDIDORES:
                id = values.getAsInteger(ContratoCorpicoApp.Medidor.MED_ID);
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.MEDIDOR, null, values);
                notificarCambio(uri);
                return ContratoCorpicoApp.Medidor.crearUriMedidor(id);
            case MEDIDORES_CLASES:
                id = values.getAsInteger(ContratoCorpicoApp.MedidorClase.MCL_ID);
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.MEDIDOR_CLASE, null, values);
                notificarCambio(uri);
                return ContratoCorpicoApp.MedidorClase.crearUriMedidorClase(id);
            case MEDIDORES_MARCAS:
                id = values.getAsInteger(ContratoCorpicoApp.MedidorMarca.MCA_ID);
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.MEDIDOR_MARCA, null, values);
                notificarCambio(uri);
                return ContratoCorpicoApp.MedidorMarca.crearUriMedidorMarca(id);
            case MEDIDORES_MODELOS:
                id = values.getAsInteger(ContratoCorpicoApp.MedidorModelo.MMO_ID);
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.MEDIDOR_MODELO, null, values);
                notificarCambio(uri);
                return ContratoCorpicoApp.MedidorModelo.crearUriMedidorModelo(id);
            case MEDIDORES_TIPOS:
                id = values.getAsInteger(ContratoCorpicoApp.MedidorTipo.MET_ID);
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.MEDIDOR_TIPO, null, values);
                notificarCambio(uri);
                return ContratoCorpicoApp.MedidorTipo.crearUriMedidorTipo(id);
            case PUNTO_MEDICION_MEDIDOR:
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.PUNTO_MEDICION_MEDIDOR, null, values);
                notificarCambio(uri);
                Integer cliente = values.getAsInteger(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_CLIENTE);
                short suministro = values.getAsShort(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_SUMINISTRO);
                byte tipoEmpresa = values.getAsByte(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_TIPO_EMPRESA);
                short puntoMedicion = values.getAsShort(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_PUNTO_MEDICION);
                Integer medidor = values.getAsInteger(ContratoCorpicoApp.PuntoMedicioMedidor.PMM_CLIENTE);
                return ContratoCorpicoApp.PuntoMedicioMedidor.crearUriPuntoMedicionMedidor(cliente, suministro, tipoEmpresa, puntoMedicion, medidor);
            case OPERARIO:
                id = values.getAsInteger(ContratoCorpicoApp.Operario.OPE_ID);
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.OPERARIO, null, values);
                notificarCambio(uri);
                return ContratoCorpicoApp.Operario.crearUriOperario(id);
            case TIPO_CONEXION:
                id = values.getAsInteger(ContratoCorpicoApp.TipoConexion.TCO_ID);
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.TIPO_CONEXION, null, values);
                notificarCambio(uri);
                return ContratoCorpicoApp.TipoConexion.crearUriTipoConexion(id);
            case TIPO_CONSUMO:
                id = values.getAsInteger(ContratoCorpicoApp.TipoConsumo.TIC_ID);
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.TIPO_CONSUMO, null, values);
                notificarCambio(uri);
                return ContratoCorpicoApp.TipoConsumo.crearUriTipoConsumo(id);
            case TIPO_EMPRESA:
                id = values.getAsInteger(ContratoCorpicoApp.TipoEmpresa.TIE_ID);
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.TIPO_EMPRESA, null, values);
                notificarCambio(uri);
                return ContratoCorpicoApp.TipoEmpresa.crearUriTipoEmpresa(id);
            case TIPO_USUARIO:
                id = values.getAsInteger(ContratoCorpicoApp.TipoUsuario.TIU_ID);
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.TIPO_USUARIO, null, values);
                notificarCambio(uri);
                return ContratoCorpicoApp.TipoUsuario.crearUriTipoUsuario(id);
            case SECTOR:
                id = values.getAsInteger(ContratoCorpicoApp.Sector.SEC_ID);
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.SECTOR, null, values);
                notificarCambio(uri);
                return ContratoCorpicoApp.Sector.crearUriSector(id);
            case SUMINISTROS:
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.SUMINISTRO, null, values);
                notificarCambio(uri);
                cliente = values.getAsInteger(Suministro.SUM_CLIENTE);
                suministro = values.getAsShort(Suministro.SUM_ID);
                return Suministro.crearUriSuministro(cliente, suministro);
            case SUMINISTROS_TIPO_EMPRESA:
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.SUMINISTRO_TIPO_EMPRESA, null, values);
                notificarCambio(uri);
                cliente = values.getAsInteger(ContratoCorpicoApp.SuministroTipoEmpresa.STE_CLIENTE);
                suministro = values.getAsShort(ContratoCorpicoApp.SuministroTipoEmpresa.STE_SUMINISTRO);
                return ContratoCorpicoApp.SuministroTipoEmpresa.crearUriSuministroTipoEmpresa(cliente, suministro);
            case SUMINISTROS_POSICION_GLOBAL:
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.SUMINISTRO_POSICION_GLOBAL, null, values);
                notificarCambio(uri);
                cliente = values.getAsInteger(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_CLIENTE);
                suministro = values.getAsShort(ContratoCorpicoApp.SuministroPosicionGlobal.SPG_SUMINISTRO);
                return ContratoCorpicoApp.SuministroPosicionGlobal.crearUriSuministroPosicionGlobal(cliente, suministro);
            case TIPO_TRABAJO_CUADRILLA:
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.TIPO_TRABAJO_CUADRILLA, null, values);
                notificarCambio(uri);
                cuadrilla = values.getAsInteger(ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_CUADRILLA);
                tipoTrabajo = values.getAsInteger(ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_TIPO_TRABAJO);
                fecha_update = values.getAsString(ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_FECHA_UPDATE);
                return ContratoCorpicoApp.TipoTrabajoCuadrilla.crearUriTipoTrabajoCuadrilla(cuadrilla, tipoTrabajo);
            case ZONAS:
                id = values.getAsInteger(Zona.ZON_ID);
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.ZONA, null, values);
                notificarCambio(uri);
                return Zona.crearUriZona(id);
            case PUNTOS_ZONA:
                id = values.getAsInteger(ContratoCorpicoApp.PuntoZona.PZON_ID);
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.PUNTO_ZONA, null, values);
                notificarCambio(uri);
                return ContratoCorpicoApp.PuntoZona.crearUriPuntoZona(id);
            case ESTADOS:
                String sid;
                sid = values.getAsString(ContratoCorpicoApp.Estado.OOS_ID);
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.ESTADO, null, values);
                notificarCambio(uri);
                return ContratoCorpicoApp.Estado.crearUriEstado(sid);
            case NOTAS:
                String nid;
                nid = values.getAsString(ContratoCorpicoApp.Nota.NOV_ID);
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.NOTA, null, values);
                notificarCambio(uri);
                return ContratoCorpicoApp.Nota.crearUriNota(nid);
            case CUADRILLAS:
                id = values.getAsInteger(ContratoCorpicoApp.Cuadrilla.CU_ID);
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.CUADRILLA, null, values);
                notificarCambio(uri);
                return ContratoCorpicoApp.Cuadrilla.crearUriCuadrilla(id);
            case FOTOS:
                bd.insertOrThrow(BaseDatosCorpicoApp.Tablas.FOTO, null, values);
                notificarCambio(uri);
                int orden = values.getAsInteger(ContratoCorpicoApp.Foto.OTF_NUMERO);
                short nfoto = values.getAsShort(ContratoCorpicoApp.Foto.OTF_NFOTO);
                return ContratoCorpicoApp.Foto.crearUriFoto(orden, nfoto);
            default:
                throw new UnsupportedOperationException("Uri no soportada");
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "delete: " + uri);

        SQLiteDatabase bd = helper.getWritableDatabase();
        int id;
        int afectados;

        switch (uriMatcher.match(uri)) {
            case ORDENES_OPERATIVAS_ID:
                // Obtener id
                id = obtenerIdOrdenOperativa(uri);
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.ORDEN_OPERATIVA,
                        OrdenesOperativas.numero + " = ? ",
                        new String[]{String.valueOf(id)}
                );
                notificarCambio(uri);
                break;
            case TIPOS_TRABAJOS_ID:
                // Obtener id
                id = ContratoCorpicoApp.TiposTrabajo.obtenerIdTipoTrabajo(uri);
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.TIPO_TRABAJO,
                        ContratoCorpicoApp.TiposTrabajo.TIT_ID + " = ? ",
                        new String[]{String.valueOf(id)}
                );
                notificarCambio(uri);
                break;
            case MOTIVOS_TRABAJOS_ID:
                // Obtener id
                id = ContratoCorpicoApp.MotivosTrabajo.obtenerIdMotivoTrabajo(uri);
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.MOTIVO_TRABAJO,
                        ContratoCorpicoApp.MotivosTrabajo.MTR_ID + " = ? ",
                        new String[]{String.valueOf(id)}
                );
                notificarCambio(uri);
                break;
            case TIPOS_CUADRILLAS_ID:
                // Obtener id
                id = ContratoCorpicoApp.TiposCuadrilla.obtenerIdTipoCuadrilla(uri);
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.TIPO_CUADRILLA,
                        ContratoCorpicoApp.TiposCuadrilla.TC_ID + " = ? ",
                        new String[]{String.valueOf(id)}
                );
                notificarCambio(uri);
            case CLIENTES_ID:
                // Obtener id
                id = ContratoCorpicoApp.Clientes.obtenerIdCliente(uri);
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.CLIENTE,
                        ContratoCorpicoApp.Clientes.CLI_ID + " = ? ",
                        new String[]{String.valueOf(id)}
                );
                notificarCambio(uri);
                break;
            case ETAPAS_ID:
                // Obtener id
                id = ContratoCorpicoApp.Etapas.obtenerIdEtapa(uri);
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.ETAPA,
                        ContratoCorpicoApp.Etapas.ETA_ID + " = ? ",
                        new String[]{String.valueOf(id)}
                );
                notificarCambio(uri);
                break;
            case TURNOS_ID:
                // Obtener id
                id = ContratoCorpicoApp.Turnos.obtenerIdTurno(uri);
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.TURNO,
                        ContratoCorpicoApp.Turnos.TUR_ORDEN + " = ? ",
                        new String[]{String.valueOf(id)}
                );
                notificarCambio(uri);
                break;
            case EMPRESAS_CONTRATISTAS_ID:
                // Obtener id
                id = ContratoCorpicoApp.EmpresasContratista.obtenerIdEmpresaContratista(uri);
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.EMPRESA_CONTRATISTA,
                        ContratoCorpicoApp.EmpresasContratista.EML_ID + " = ? ",
                        new String[]{String.valueOf(id)}
                );
                notificarCambio(uri);
                break;
            case ESTADOS_OPERATIVO_ID:
                // Obtener id
                id = ContratoCorpicoApp.EstadoOperativo.obtenerIdEstadoOperativo(uri);
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.ESTADO_OPERATIVO,
                        ContratoCorpicoApp.EmpresasContratista.EML_ID + " = ? ",
                        new String[]{String.valueOf(id)}
                );
                notificarCambio(uri);
                break;
            case LOCALIDADES_ID:
                // Obtener id
                id = ContratoCorpicoApp.Localidad.obtenerIdLocalidad(uri);
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.LOCALIDAD,
                        ContratoCorpicoApp.Localidad.LOC_ID + " = ? ",
                        new String[]{String.valueOf(id)}
                );
                notificarCambio(uri);
                break;
            case MEDIDORES_ID:
                // Obtener id
                id = ContratoCorpicoApp.Medidor.obtenerIdMedidor(uri);
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.MEDIDOR,
                        ContratoCorpicoApp.Medidor.MED_ID + " = ? ",
                        new String[]{String.valueOf(id)}
                );
                notificarCambio(uri);
                break;
            case MEDIDORES_CLASES_ID:
                // Obtener id
                id = ContratoCorpicoApp.MedidorClase.obtenerIdMedidorClase(uri);
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.MEDIDOR_CLASE,
                        ContratoCorpicoApp.MedidorClase.MCL_ID + " = ? ",
                        new String[]{String.valueOf(id)}
                );
                notificarCambio(uri);
                break;
            case MEDIDORES_MARCAS_ID:
                // Obtener id
                id = ContratoCorpicoApp.MedidorMarca.obtenerIdMedidorMarca(uri);
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.MEDIDOR_MARCA,
                        ContratoCorpicoApp.MedidorMarca.MCA_ID + " = ? ",
                        new String[]{String.valueOf(id)}
                );
                notificarCambio(uri);
                break;
            case MEDIDORES_MODELOS_ID:
                // Obtener id
                id = ContratoCorpicoApp.MedidorModelo.obtenerIdMedidorModelo(uri);
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.MEDIDOR_MODELO,
                        ContratoCorpicoApp.MedidorModelo.MMO_ID + " = ? ",
                        new String[]{String.valueOf(id)}
                );
                notificarCambio(uri);
                break;
            case MEDIDORES_TIPOS_ID:
                // Obtener id
                id = ContratoCorpicoApp.MedidorTipo.obtenerIdMedidorTipo(uri);
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.MEDIDOR_TIPO,
                        ContratoCorpicoApp.MedidorTipo.MET_ID + " = ? ",
                        new String[]{String.valueOf(id)}
                );
                notificarCambio(uri);
                break;
            case PUNTO_MEDICION_MEDIDOR_ID:
                // Obtener id
                String[] ids = ContratoCorpicoApp.PuntoMedicioMedidor.obtenerIdPuntoMedicionMedidor(uri);
                String seleccion = String.format("%s=? AND %s=? AND %s=? AND %s=? AND %s=?",
                        ContratoCorpicoApp.PuntoMedicioMedidor.PMM_CLIENTE, ContratoCorpicoApp.PuntoMedicioMedidor.PMM_SUMINISTRO,
                        ContratoCorpicoApp.PuntoMedicioMedidor.PMM_TIPO_EMPRESA, ContratoCorpicoApp.PuntoMedicioMedidor.PMM_PUNTO_MEDICION,
                        ContratoCorpicoApp.PuntoMedicioMedidor.PMM_MEDIDOR);
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.PUNTO_MEDICION_MEDIDOR,
                        seleccion, ids
                        //new String[]{String.valueOf(ids)}
                );
                notificarCambio(uri);
                break;
            case OPERARIO_ID:
                // Obtener id
                id = ContratoCorpicoApp.Operario.obtenerIdOperario(uri);
                /*afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.OPERARIO,
                        ContratoCorpicoApp.Operario.OPE_ID + " = ? ",
                        new String[]{String.valueOf(id)}
                );*/
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.CUADRILLA,
                        ContratoCorpicoApp.Cuadrilla.CU_OPERARIO + " = ? ",
                        new String[]{String.valueOf(id)}
                );
                notificarCambio(uri);
                break;
            case TIPO_CONEXION_ID:
                // Obtener id
                id = ContratoCorpicoApp.TipoConexion.obtenerIdTipoConexion(uri);
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.TIPO_CONEXION,
                        ContratoCorpicoApp.TipoConexion.TCO_ID + " = ? ",
                        new String[]{String.valueOf(id)}
                );
                notificarCambio(uri);
                break;
            case TIPO_CONSUMO_ID:
                // Obtener id
                id = ContratoCorpicoApp.TipoConsumo.obtenerIdTipoConsumo(uri);
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.TIPO_CONSUMO,
                        ContratoCorpicoApp.TipoConsumo.TIC_ID + " = ? ",
                        new String[]{String.valueOf(id)}
                );
                notificarCambio(uri);
                break;
            case TIPO_EMPRESA_ID:
                // Obtener id
                id = ContratoCorpicoApp.TipoEmpresa.obtenerIdTipoEmpresa(uri);
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.TIPO_EMPRESA,
                        ContratoCorpicoApp.TipoEmpresa.TIE_ID + " = ? ",
                        new String[]{String.valueOf(id)}
                );
                notificarCambio(uri);
                break;
            case TIPO_USUARIO_ID:
                // Obtener id
                id = ContratoCorpicoApp.TipoUsuario.obtenerIdTipoUsuario(uri);
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.TIPO_USUARIO,
                        ContratoCorpicoApp.TipoUsuario.TIU_ID + " = ? ",
                        new String[]{String.valueOf(id)}
                );
                notificarCambio(uri);
                break;
            case SECTOR_ID:
                // Obtener id
                id = ContratoCorpicoApp.Sector.obtenerIdSector(uri);
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.SECTOR,
                        ContratoCorpicoApp.Sector.SEC_ID + " = ? ",
                        new String[]{String.valueOf(id)}
                );
                notificarCambio(uri);
                break;
            case NOTAS_ID:
                // Obtener id
                id = Integer.parseInt(ContratoCorpicoApp.Nota.obtenerIdNota(uri));
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.NOTA,
                        ContratoCorpicoApp.Nota.NOV_ID + " = ? ",
                        new String[]{String.valueOf(id)}
                );
                notificarCambio(uri);
                break;
            case SUMINISTROS_ID:
                // Obtener id
                ids = Suministro.obtenerIdSuministro(uri);
                seleccion = String.format("%s=? AND %s=?", Suministro.SUM_CLIENTE, Suministro.SUM_ID);
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.SUMINISTRO,
                        seleccion, ids
                        //new String[]{String.valueOf(ids)}
                );
                notificarCambio(uri);
                break;
            case FOTOS_ID:
                // Obtener id
                ids = ContratoCorpicoApp.Foto.obtenerIdFoto(uri);
                seleccion = String.format("%s=? AND %s=?", ContratoCorpicoApp.Foto.OTF_NUMERO, ContratoCorpicoApp.Foto.OTF_NFOTO);
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.FOTO,
                        seleccion, ids
                        //new String[]{String.valueOf(ids)}
                );
                notificarCambio(uri);
                break;
            case SUMINISTROS_TIPO_EMPRESA_ID:
                // Obtener id
                ids = ContratoCorpicoApp.SuministroTipoEmpresa.obtenerIdSuministroTipoEmpresa(uri);
                seleccion = String.format("%s=? AND %s=?",
                        ContratoCorpicoApp.SuministroTipoEmpresa.STE_CLIENTE, ContratoCorpicoApp.SuministroTipoEmpresa.STE_SUMINISTRO);
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.SUMINISTRO_TIPO_EMPRESA,
                        seleccion, ids
                        //new String[]{String.valueOf(ids)}
                );
                notificarCambio(uri);
                break;
            case SUMINISTROS_POSICION_GLOBAL_ID:
                // Obtener id
                ids = ContratoCorpicoApp.SuministroPosicionGlobal.obtenerIdSuministroPosicionGlobal(uri);
                seleccion = String.format("%s=? AND %s=?",
                        ContratoCorpicoApp.SuministroPosicionGlobal.SPG_CLIENTE, ContratoCorpicoApp.SuministroPosicionGlobal.SPG_SUMINISTRO);
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.SUMINISTRO_POSICION_GLOBAL,
                        seleccion, ids
                        //new String[]{String.valueOf(ids)}
                );
                notificarCambio(uri);
                break;
            case TIPO_TRABAJO_CUADRILLA_ID:
                // Obtener id
                ids = ContratoCorpicoApp.TipoTrabajoCuadrilla.obtenerIdTipoTrabajoCuadrilla(uri);
                seleccion = String.format("%s=? AND %s=?",
                        ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_CUADRILLA, ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_TIPO_TRABAJO);
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.TIPO_TRABAJO_CUADRILLA,
                        seleccion, ids
                        //new String[]{String.valueOf(ids)}
                );
                notificarCambio(uri);
                break;
            case ZONAS_ID:
                // Obtener id
                id = Zona.obtenerIdZona(uri);
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.ZONA,
                        Zona.ZON_ID + " = ? ",
                        new String[]{String.valueOf(id)}
                );
                notificarCambio(uri);
                break;
            case PUNTOS_ZONA_ID:
                // Obtener id
                id = ContratoCorpicoApp.PuntoZona.obtenerIdPuntoZona(uri);
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.PUNTO_ZONA,
                        ContratoCorpicoApp.PuntoZona.PZON_ID + " = ? ",
                        new String[]{String.valueOf(id)}
                );
                notificarCambio(uri);
                break;
            case ESTADOS_ID:
                String sid;
                // Obtener id
                sid = ContratoCorpicoApp.Estado.obtenerIdEstado(uri);
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.ESTADO,
                        ContratoCorpicoApp.Estado.OOS_ID + " = ? ",
                        new String[]{sid}
                );
                notificarCambio(uri);
                break;
            case CUADRILLAS_ID:
                // Obtener id
                id = ContratoCorpicoApp.Cuadrilla.obtenerIdCuadrilla(uri);
                afectados = bd.delete(
                        BaseDatosCorpicoApp.Tablas.CUADRILLA,
                        ContratoCorpicoApp.Cuadrilla.CU_ID + " = ? ",
                        new String[]{String.valueOf(id)}
                );
                notificarCambio(uri);
                break;
            default:
                throw new UnsupportedOperationException(URI_NO_SOPORTADA);
        }
        return afectados;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        SQLiteDatabase bd = helper.getWritableDatabase();
        int id;
        int afectados;
        String sid;

        switch (uriMatcher.match(uri)) {
            case ORDENES_OPERATIVAS_ID:
                id = OrdenesOperativas.obtenerIdOrdenOperativa(uri);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.ORDEN_OPERATIVA, values,
                        OrdenesOperativas.numero + " = ?", new String[]{String.valueOf(id)});
                notificarCambio(uri);
                break;

            case TIPOS_TRABAJOS_ID:
                id = ContratoCorpicoApp.TiposTrabajo.obtenerIdTipoTrabajo(uri);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.TIPO_TRABAJO, values,
                        ContratoCorpicoApp.TiposTrabajo.TIT_ID + " = ?", new String[]{String.valueOf(id)});
                notificarCambio(uri);
                break;
            case MOTIVOS_TRABAJOS_ID:
                id = ContratoCorpicoApp.MotivosTrabajo.obtenerIdMotivoTrabajo(uri);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.MOTIVO_TRABAJO, values,
                        ContratoCorpicoApp.MotivosTrabajo.MTR_ID + " = ?", new String[]{String.valueOf(id)});
                notificarCambio(uri);
                break;
            case TIPOS_CUADRILLAS_ID:
                id = ContratoCorpicoApp.TiposCuadrilla.obtenerIdTipoCuadrilla(uri);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.TIPO_CUADRILLA, values,
                        ContratoCorpicoApp.TiposCuadrilla.TC_ID + " = ?", new String[]{String.valueOf(id)});
                notificarCambio(uri);
                break;
            case CLIENTES_ID:
                id = ContratoCorpicoApp.Clientes.obtenerIdCliente(uri);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.CLIENTE, values,
                        ContratoCorpicoApp.Clientes.CLI_ID + " = ?", new String[]{String.valueOf(id)});
                notificarCambio(uri);
                break;
            case TURNOS_ID:
                id = ContratoCorpicoApp.Turnos.obtenerIdTurno(uri);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.TURNO, values,
                        ContratoCorpicoApp.Turnos.TUR_ORDEN + " = ?", new String[]{String.valueOf(id)});
                notificarCambio(uri);
                break;
            case CUADRILLAS_ID:
                id = ContratoCorpicoApp.Cuadrilla.obtenerIdCuadrilla(uri);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.CUADRILLA, values,
                        ContratoCorpicoApp.Cuadrilla.CU_ID + " = ?", new String[]{String.valueOf(id)});
                notificarCambio(uri);
                break;
            case ETAPAS_ID:
                id = ContratoCorpicoApp.Etapas.obtenerIdEtapa(uri);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.ETAPA, values,
                        ContratoCorpicoApp.Etapas.ETA_ID + " = ?", new String[]{String.valueOf(id)});
                notificarCambio(uri);
                break;
            case EMPRESAS_CONTRATISTAS_ID:
                id = ContratoCorpicoApp.EmpresasContratista.obtenerIdEmpresaContratista(uri);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.EMPRESA_CONTRATISTA, values,
                        ContratoCorpicoApp.EmpresasContratista.EML_ID + " = ?", new String[]{String.valueOf(id)});
                notificarCambio(uri);
                break;
            case ESTADOS_OPERATIVO_ID:
                id = ContratoCorpicoApp.EstadoOperativo.obtenerIdEstadoOperativo(uri);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.ESTADO_OPERATIVO, values,
                        ContratoCorpicoApp.EstadoOperativo.EOP_ID + " = ?", new String[]{String.valueOf(id)});
                notificarCambio(uri);
                break;
            case LOCALIDADES_ID:
                id = ContratoCorpicoApp.Localidad.obtenerIdLocalidad(uri);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.LOCALIDAD, values,
                        ContratoCorpicoApp.Localidad.LOC_ID + " = ?", new String[]{String.valueOf(id)});
                notificarCambio(uri);
                break;
            case MEDIDORES_ID:
                id = ContratoCorpicoApp.Medidor.obtenerIdMedidor(uri);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.MEDIDOR, values,
                        ContratoCorpicoApp.Medidor.MED_ID + " = ?", new String[]{String.valueOf(id)});
                notificarCambio(uri);
                break;
            case MEDIDORES_CLASES_ID:
                id = ContratoCorpicoApp.MedidorClase.obtenerIdMedidorClase(uri);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.MEDIDOR_CLASE, values,
                        ContratoCorpicoApp.MedidorClase.MCL_ID + " = ?", new String[]{String.valueOf(id)});
                notificarCambio(uri);
                break;
            case MEDIDORES_MARCAS_ID:
                id = ContratoCorpicoApp.MedidorMarca.obtenerIdMedidorMarca(uri);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.MEDIDOR_MARCA, values,
                        ContratoCorpicoApp.MedidorMarca.MCA_ID + " = ?", new String[]{String.valueOf(id)});
                notificarCambio(uri);
                break;
            case MEDIDORES_MODELOS_ID:
                id = ContratoCorpicoApp.MedidorModelo.obtenerIdMedidorModelo(uri);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.MEDIDOR_MODELO, values,
                        ContratoCorpicoApp.MedidorModelo.MMO_ID + " = ?", new String[]{String.valueOf(id)});
                notificarCambio(uri);
                break;
            case MEDIDORES_TIPOS_ID:
                id = ContratoCorpicoApp.MedidorTipo.obtenerIdMedidorTipo(uri);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.MEDIDOR_TIPO, values,
                        ContratoCorpicoApp.MedidorTipo.MET_ID + " = ?", new String[]{String.valueOf(id)});
                notificarCambio(uri);
                break;
            case PUNTO_MEDICION_MEDIDOR_ID:
                String[] ids = ContratoCorpicoApp.PuntoMedicioMedidor.obtenerIdPuntoMedicionMedidor(uri);
                String seleccion = String.format("%s=? AND %s=? AND %s=? AND %s=? AND %s=?",
                        ContratoCorpicoApp.PuntoMedicioMedidor.PMM_CLIENTE, ContratoCorpicoApp.PuntoMedicioMedidor.PMM_SUMINISTRO,
                        ContratoCorpicoApp.PuntoMedicioMedidor.PMM_TIPO_EMPRESA, ContratoCorpicoApp.PuntoMedicioMedidor.PMM_PUNTO_MEDICION,
                        ContratoCorpicoApp.PuntoMedicioMedidor.PMM_MEDIDOR);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.PUNTO_MEDICION_MEDIDOR, values,
                        seleccion, ids);
                notificarCambio(uri);
                break;
            case OPERARIO_ID:
                id = ContratoCorpicoApp.Operario.obtenerIdOperario(uri);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.OPERARIO, values,
                        ContratoCorpicoApp.Operario.OPE_ID + " = ?", new String[]{String.valueOf(id)});
                notificarCambio(uri);
                break;
            case TIPO_CONEXION_ID:
                id = ContratoCorpicoApp.TipoConexion.obtenerIdTipoConexion(uri);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.TIPO_CONEXION, values,
                        ContratoCorpicoApp.TipoConexion.TCO_ID + " = ?", new String[]{String.valueOf(id)});
                notificarCambio(uri);
                break;
            case TIPO_CONSUMO_ID:
                id = ContratoCorpicoApp.TipoConsumo.obtenerIdTipoConsumo(uri);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.TIPO_CONSUMO, values,
                        ContratoCorpicoApp.TipoConsumo.TIC_ID + " = ?", new String[]{String.valueOf(id)});
                notificarCambio(uri);
                break;
            case TIPO_EMPRESA_ID:
                id = ContratoCorpicoApp.TipoEmpresa.obtenerIdTipoEmpresa(uri);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.TIPO_EMPRESA, values,
                        ContratoCorpicoApp.TipoEmpresa.TIE_ID + " = ?", new String[]{String.valueOf(id)});
                notificarCambio(uri);
                break;
            case TIPO_USUARIO_ID:
                id = ContratoCorpicoApp.TipoUsuario.obtenerIdTipoUsuario(uri);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.TIPO_USUARIO, values,
                        ContratoCorpicoApp.TipoUsuario.TIU_ID + " = ?", new String[]{String.valueOf(id)});
                notificarCambio(uri);
                break;
            case SECTOR_ID:
                id = ContratoCorpicoApp.Sector.obtenerIdSector(uri);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.SECTOR, values,
                        ContratoCorpicoApp.Sector.SEC_ID + " = ?", new String[]{String.valueOf(id)});
                notificarCambio(uri);
                break;
            case SUMINISTROS_ID:
                ids = Suministro.obtenerIdSuministro(uri);
                seleccion = String.format("%s=? AND %s=?",
                        Suministro.SUM_CLIENTE, Suministro.SUM_ID);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.SUMINISTRO, values,
                        seleccion, ids);
                notificarCambio(uri);
                break;
            case SUMINISTROS_TIPO_EMPRESA_ID:
                ids = ContratoCorpicoApp.SuministroTipoEmpresa.obtenerIdSuministroTipoEmpresa(uri);
                seleccion = String.format("%s=? AND %s=?",
                        ContratoCorpicoApp.ColumnasSuministroTipoEmpresa.STE_CLIENTE, ContratoCorpicoApp.SuministroTipoEmpresa.STE_SUMINISTRO);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.SUMINISTRO_TIPO_EMPRESA, values,
                        seleccion, ids);
                notificarCambio(uri);
                break;
            case SUMINISTROS_POSICION_GLOBAL_ID:
                ids = ContratoCorpicoApp.SuministroPosicionGlobal.obtenerIdSuministroPosicionGlobal(uri);
                seleccion = String.format("%s=? AND %s=?",
                        ContratoCorpicoApp.SuministroPosicionGlobal.SPG_CLIENTE, ContratoCorpicoApp.SuministroPosicionGlobal.SPG_SUMINISTRO);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.SUMINISTRO_POSICION_GLOBAL, values,
                        seleccion, ids);
                notificarCambio(uri);
                break;
            case TIPO_TRABAJO_CUADRILLA_ID:
                ids = ContratoCorpicoApp.TipoTrabajoCuadrilla.obtenerIdTipoTrabajoCuadrilla(uri);
                seleccion = String.format("%s=? AND %s=?",
                        ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_CUADRILLA, ContratoCorpicoApp.TipoTrabajoCuadrilla.TTC_TIPO_TRABAJO);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.TIPO_TRABAJO_CUADRILLA, values,
                        seleccion, ids);
                notificarCambio(uri);
                break;
            case ZONAS_ID:
                id = Zona.obtenerIdZona(uri);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.ZONA, values,
                        Zona.ZON_ID + " = ?", new String[]{String.valueOf(id)});
                notificarCambio(uri);
                break;
            case PUNTOS_ZONA_ID:
                id = ContratoCorpicoApp.PuntoZona.obtenerIdPuntoZona(uri);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.PUNTO_ZONA, values,
                        ContratoCorpicoApp.PuntoZona.PZON_ID + " = ?", new String[]{String.valueOf(id)});
                notificarCambio(uri);
                break;
            case ESTADOS_ID:
                sid = ContratoCorpicoApp.Estado.obtenerIdEstado(uri);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.ESTADO, values,
                        ContratoCorpicoApp.Estado.OOS_ID + " = ?", new String[]{sid});
                notificarCambio(uri);
                break;
            case NOTAS_ID:
                sid = ContratoCorpicoApp.Nota.obtenerIdNota(uri);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.NOTA, values,
                        ContratoCorpicoApp.Nota.NOV_ID + " = ?", new String[]{sid});
                notificarCambio(uri);
                break;
            case FOTOS_ID:
                ids = ContratoCorpicoApp.Foto.obtenerIdFoto(uri);
                seleccion = String.format("%s=? AND %s=?",
                        ContratoCorpicoApp.Foto.OTF_NUMERO, ContratoCorpicoApp.Foto.OTF_NFOTO);
                afectados = bd.update(BaseDatosCorpicoApp.Tablas.FOTO, values,
                        seleccion, ids);
                notificarCambio(uri);
                break;
            default:
                throw new UnsupportedOperationException(URI_NO_SOPORTADA);
        }
        return afectados;
    }

    private String construirFiltro(String filtro) {
        String sentencia = null;
        //TODO: VER SI LO VOY UTILIZAR AGREGARLO EN SU MOMENTO
        switch (filtro) {
            case OrdenesOperativas.FILTRO_ESTADO:
                sentencia = "orden_operativa.estado";
                break;
            case OrdenesOperativas.FILTRO_FECHA:
                sentencia = "orden_operativa.fecha";
                break;
            case ContratoCorpicoApp.Etapas.FILTRO_NUMERO:
                sentencia = "etapa.numero";
                break;
        }

        return sentencia;
    }

    private void notificarCambio(Uri uri) {
        resolver.notifyChange(uri, null);
    }

    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        final SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        try {
            final int numOperations = operations.size();
            final ContentProviderResult[] results = new ContentProviderResult[numOperations];
            for (int i = 0; i < numOperations; i++) {
                results[i] = operations.get(i).apply(this, results, i);
            }
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }
}
