package ar.com.corpico.appcorpico.sqlite;

import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Administrador on 20/02/2018.
 */

public class ContratoCorpicoApp {
    //Columnas Orden_Operativa
    interface ColumnasOrdenOperativa {
    /*    String OTR_SUCURSAL = "sucursal";
        String OTR_CENTRO_ATENCION = "centro_atencion";
        String OTR_NUMERO = "numero";
        String OTR_EMPRESA = "empresa";
        String OTR_CLIENTE = "cliente";
        String OTR_SUMINISTRO = "suministro";
        String OTR_PUNTO_MEDICION = "punto_medicion";
        String OTR_FECHA_SOLICITUD = "fecha_solicitud";
        String OTR_TIPO_TRABAJO = "tipo_trabajo";
        String OTR_MOTIVO_TRABAJO = "motivo_trabajo";
        String OTR_FECHA_VENCIMIENTO = "fecha_vencimiento";
        String OTR_OBSERVACIONES_AL_OPE = "observaciones_al_ope";
        String OTR_ID_USER_GENERO = "id_user_genero";
        String OTR_FORMA_GENERACION = "fecha_generacion";
        String OTR_ESTADO_OPERATIVO_ANTES_GENERAR = "estado_operativo_antes_generar";
        String OTR_FECHA_EST_OPERATIVO_ANTES_GENERAR = "fecha_est_operativo_antes_generar";
        String OTR_ESTADO_OPERATIVO_LUEGO_GENERAR = "estado_operativo_luego_generar";
        String OTR_EMPRESA_CONTRATISTA = "empresa_contratista";
        String OTR_FECHA_ASIGNACION = "fecha_asignacion";
        String OTR_ID_USER_ASIGNO = "id_user_asigno";
        String OTR_FECHA_CULMINACION = "fecha_culminacion";
        String OTR_OPERARIO = "operario";
        String OTR_OBSERVACION_DEL_OPE = "observacion_del_ope";
        String OTR_ID_USER_RETENCION = "id_user_retencion";
        String OTR_FECHA_RETENCION = "fecha_retencion";
        String OTR_MOTIVO_RETENCION = "motivo_retencion";
        String OTR_ID_USER_LIBERACION = "id_user_liberacion";
        String OTR_FECHA_LIBERACION = "fecha_liberacion";
        String OTR_OPERARIO_REALIZACION = "operario_realizacion";
        String OTR_ESTADO = "estado";
        String OTR_ID_USER = "id_user";
        String OTR_FECHA_UPDATE = "fecha_update";
        String OTR_ID_USER_ALTA = "id_user_alta";
        String OTR_FECHA_ALTA = "fecha_alta";
        String OTR_LOTE_REPLICACION = "lote_replicacion";*/

        String fecha_solicitud = "fecha_solicitud";
        String numero = "numero";
        String asociado  = "asociado";
        String suministro = "suministro";
        String titular = "titular";
        String calle = "calle";
        String altura = "altura";
        String piso = "piso";
        String dpto = "dpto";
        String localidad = "localidad";
        String anexo = "anexo";
        String tipo_trabajo = "tipo_trabajo";
        String motivo_trabajo = "motivo_trabajo";
        String observacion_al_operario = "observacion_al_operario";
        String latitud = "latitud";
        String longitud = "longitud";
        String grupo = "grupo";
        String ruta = "ruta";
        String orden_lectura = "orden_lectura";
        String tipo_usuario = "tipo_usuario";
        String tipo_consumo = "tipo_consumo";
        String potencia_declarada = "potencia_declarada";
        String medidor = "medidor";
        String medidor_marca = "medidor_marca";
        String medidor_modelo = "medidor_modelo";
        String factorM = "factorM";
        String capacidad = "capacidad";
        String tension = "tension";
        String estado = "estado";
        String fecha_update = "fecha_update";
        String sector = "sector";
        String fecha_asignacion = "fecha_asignacion";
        String user_asigo = "user_asigno";
        String fecha_culminacion = "fecha_culminacion";
        String cuadrilla = "cuadrilla";
        String observacion_del_ope = "observacion_del_ope";
        String user_realizacion = "user_realizacion";
        String sync = "sync";
        String user_id = "user_id";
        String zona = "zona";
    }

    //Columnas Tipo_Trabajo
    interface ColumnasTipoTrabajo {
        String TIT_ID = "id";
        String TIT_DESCRIPCION = "descripcion";
        String TIT_ABREVIATURA = "abreviatura";
        String TIT_TIPO_EMPRESA = "tipo_empresa";
        String TIT_CLASIFICACION_TRABAJO = "clasificacion_trabajo";
        String TIT_PERMITE_ELIMINAR = "permite_eliminar";
        String TIT_ACTIVO = "activo";
        String TIT_ID_USER = "id_user";
        String TIT_FECHA_UPDATE = "fecha_update";
        String TIT_LOTE_REPLICACION = "lote_replicacion";
    }

    //Columnas Motivo_Trabajo
    interface ColumnasMotivoTrabajo {
        String MTR_TIPO_TRABAJO = "tipo_trabajo";
        String MTR_ID = "id";
        String MTR_DESCRIPCION = "descripcion";
        String MTR_ABREVIATURA = "abreviatura";
        String MTR_DIAS_RESOLUCION = "resolucion";
        String MTR_ALTERA_ESTADO_LUEGO_GENERAR = "altera_estado_luego_generar";
        String MTR_ESTADO_LUEGO_GENERAR = "estado_luego_generar";
        String MTR_SOLICITA_COLOCACION = "solicita_colocacion";
        String MTR_SOLICITA_RETIRO = "solicita_retiro";
        String MTR_SOLICITA_INFORMATIVO = "solicita_informacion";
        String MTR_SOLICITA_PRECINTO_HAB = "solicita_precinto_hab";
        String MTR_SOLICITA_PRECINTO_MED = "solicita_precinto_med";
        String MTR_SOLICITA_INSTALACION = "solicita_instalacion";
        String MTR_SOLICITA_MATERIALES = "solicita_materiales";
        String MTR_PERMITE_ELIMINAR = "permite_eliminar";
        String MTR_PROCESA_TOMAESTADO = "procesa_tomaestado";
        String MTR_ACTIVO = "activo";
        String MTR_ID_USER = "id_user";
        String MTR_FECHA_UPDATE = "fecha_update";
        String MTR_LOTE_REPLICACION = "lote_replicacion";
    }

    interface ColumnasTipoCuadrilla {
        String TC_ID = "id";
        String TC_ID_SERVICIO = "servicio";
        String TC_ID_SECTOR = "sector";
        String TC_DESCRIPCION = "descripcion";
        String TC_FECHA_UPDATE = "fecha_update";
        String TC_GEASYS_ID = "geasys_id";
    }

    interface ColumnasCliente {
        String CLI_EMPRESA = "empresa";
        String CLI_ID = "id";
        String CLI_FECHA_INGRESO = "fecha_ingreso";
        String CLI_TITULAR = "titular";
        String CLI_LOCALIDAD_RESIDENCIA = "localidad_residencia";
        String CLI_CODIGO_POSTAL_RESIDENCIA = "codigo_postal_residencia";
        String CLI_CALLE_RESIDENCIA = "calle_residencia";
        String CLI_ALTURA_RESIDENCIA = "altura_residencia";
        String CLI_PISO_RESIDENCIA = "piso_residencia";
        String CLI_DEPARTAMENTO_RESIDENCIA = "departamento_residencia";
        String CLI_TELEFONO_RESIDENCIA = "telefono_residencia";
        String CLI_TELEFONO_CELULAR = "telefono_celular";
        String CLI_FAX = "fax";
        String CLI_E_MAIL = "email";
        String CLI_LOCALIDAD_LABORAL = "localidad_laboral";
        String CLI_CODIGO_POSTAL_LABORAL = "codigo_postal_laboral";
        String CLI_CALLE_LABORAL = "calle_laboral";
        String CLI_ALTURA_LABORAL = "altura_laboral";
        String CLI_PISO_LABORAL = "piso_laboral";
        String CLI_DEPARTAMENTO_LABORAL = "departamento_laboral";
        String CLI_ANEXO = "anexo";
        String CLI_TELEFONO_LABORAL = "telefono_laboral";
        String CLI_SITUACION_IVA = "situacion_laboral";
        String CLI_CUIT = "cuit";
        String CLI_SITUACION_IIBB = "situacion_iibb";
        String CLI_NUMERO_IIBB = "numero_iibb";
        String CLI_EXENCION_PERCEPCION_IVA = "exencion_percepcion_iva";
        String CLI_EXENCION_NACIONAL = "exencion_nacional";
        String CLI_EXENCION_PROVINCIAL = "exencion_provincial";
        String CLI_EXENCION_MUNICIPAL = "exencion_municipal";
        String CLI_POTENCIAL = "potencial";
        String CLI_CLASIFICACION = "clasificacion";
        String CLI_ESTADO_CIVIL = "estado_civil";
        String CLI_PROFESION = "profesion";
        String CLI_FECHA_NACIMIENTO = "fecha_nacimiento";
        String CLI_NACIONALIDAD = "nacionalidad";
        String CLI_LUGAR_NACIMIENTO = "lugar_nacimiento";
        String CLI_SEXO = "sexo";
        String CLI_ESTADO = "estado";
        String CLI_CLAVE_PERSONAL = "clave_personal";
        String CLI_ID_USER = "id_user";
        String CLI_FECHA_UPDATE = "fecha_update";
        String CLI_LOTE_REPLICACION = "lote_replicacion";
    }

    interface ColumnasEtapa {
        String ETA_NUMERO_ORDEN = "orden";
        String ETA_FECHA = "fecha";
        String ETA_ESTADO = "estado";
        String ETA_OBSERVACION = "observacion";
        String ETA_USUARIO = "usuario_id";
        String ETA_ID = "id_etapa";
        String ETA_FECHA_UPDATE = "fecha_update";
        String ETA_SERVICIO = "servicio";
        String ETA_SECTOR  = "sector";
        String ETA_LATITUD = "latitud";
        String ETA_LONGITUD = "longitud";
        String ETA_SYNC = "sync";
        String ETA_ID_TEMP = "id_temp";
    }
    interface ColumnasTurno {
        String TUR_ORDEN = "orden";
        String TUR_TURNO = "turno";
        String TUR_FECHA_UPDATE = "fecha_update";
        String TUR_SERVICIO = "servicio";
        String TUR_SECTOR = "sector";
        //String sync = "sync";
    }

    interface ColumnasEmpresaContratista{
        String EML_ID = "id";
        String EML_DESCRIPCION = "descripcion";
        String EML_DIRECCION = "direccion";
        String EML_TELEFONO = "telefono";
        String EML_TIPO_TOMA = "tipo_toma";
        String EML_TIPO_EMPRESA = "tipo_empresa";
        String EML_ACTIVO = "activo";
        String EML_ID_USER = "user_id";
        String EML_FECHA_UPDATE = "fecha_update";
        String EML_LOTE_REPLICACION = "lote_replicacion";
    }
    interface ColumnasEstadoOperativo{
        String EOP_ID = "id";
        String EOP_DESCRIPCION = "descripcion";
        String EOP_ABREVIATURA = "abreviatura";
        String EOP_TIPO_EMPRESA = "tipo_empresa";
        String EOP_SALE_A_LEER = "sale_a_leer";
        String EOP_ES_FACTURABLE = "es_facturable";
        String EOP_CAMB_TITULARIDAD = "camb_titularidad";
        String EOP_CAMB_TARIFA = "camb_tarifa";
        String EOP_CAMB_DIR_POSTAL = "camb_dir_postal";
        String EOP_CAMB_DAT_IMPOSITIVOS = "camb_dat_impositivos";
        String EOP_BAJA_DEB_AUTOMATIVO = "baja_deb_automatico";
        String EOP_RECLAMOS = "reclamos";
        String EOP_ORDEN_TRABAJO = "orden_trabajo";
        String EOP_BAJA_SUMINISTRO = "baja_suministro";
        String EOP_COBRANZA = "cobranza";
        String EOP_INCLUYE_AVISO_DEUDA = "incluye_aviso_deuda";
        String EOP_POSEE_MEDIDOR = "posee_medidor";
        String EOP_ACCION_SI_NO_POSEE_MED = "accion_si_no_posee_med";
        String EOP_PERMITE_RE_SOLICITAR_SERVICIO = "permite_re_solicitar_serivicio";
        String EOP_COLOR = "color";
        String EOP_ESTADO_CONEXION = "estado_conexion";
        String EOP_CLIENTE = "cliente";
        String EOP_ACTIVO = "activo";
        String EOP_ID_USER = "id_user";
        String EOP_FECHA_UPDATE = "fecha_update";
        String EOP_LOTE_REPLICACION = "lote_replicacion";
    }
    interface ColumnasLocalidad{
        String LOC_ID = "id";
        String LOC_DESCRIPCION = "descripcion";
        String LOC_MUNICIPIO = "municipio";
        String LOC_INFORMA_SUCURSAL = "informa_sucursal";
        String LOC_SUCURSAL = "sucursal";
        String LOC_ZONA_TARIFARIA = "zona_tarifaria";
        String LOC_ACTIVO = "activo";
        String LOC_ID_USER = "user_id";
        String LOC_FECHA_UPDATE = "fecha_update";
        String LOC_LOTE_REPLICACION = "lote_replicacion";

    }
    interface ColumnasMedidor{
        String MED_ID = "id";
        String MED_EMPRESA = "empresa";
        String MED_ALMACEN = "almacen";
        String MED_ESTADO = "estado";
        String MED_MARCA = "marca";
        String MED_TIPOSERIE = "tipo_serie";
        String MED_SERIE = "serie";
        String MED_MODELO = "modelo";
        String MED_CLASE = "clase";
        String MED_TIPO = "tipo";
        String MED_RUEDAS = "ruedas";
        String MED_ANO_FABRICA = "ano_fabricacion";
        String MED_ANOS_VIDA = "ano_vida";
        String MED_FECHA_ENTRADA = "fecha_entrada";
        String MED_VTO_GARANTIA = "vto_garantia";
        String MED_FACTOR = "factor";
        String MED_POSEE_LEDS = "posee_leds";
        String MED_MOTIVO_BAJA = "motivo_baja";
        String MED_FECHA_BAJA = "fecha_baja";
        String MED_ID_USER = "user_id";
        String MED_FECHA_UPDATE = "fecha_update";
        String MED_LOTE_REPLICACION = "lote_replicacion";
    }
    interface ColumnasMedidorClase{
        String MCL_ID = "id";
        String MCL_DESCRIPCION = "descripcion";
        String MCL_TIPO_EMPRESA = "tipo_empresa";
        String MCL_CAPACIDAD = "capacidad";
        String MCL_ACTIVO = "activo";
        String MCL_ID_USER = "user_id";
        String MCL_FECHA_UPDATE = "fecha_update";
        String MCL_LOTE_REPLICACION = "lote_replicacion";
    }

    interface ColumnasMedidorMarca{
        String MCA_ID = "id";
        String MCA_DESCRIPCION = "descripcion";
        String MCA_ABREVIATURA = "abreviatura";
        String MCA_ACTIVO = "activo";
        String MCA_ID_USER = "user_id";
        String MCA_FECHA_UPDATE = "fecha_update";
        String MCA_LOTE_REPLICACION = "lote_replicacion";
    }
    interface ColumnasMedidorModelo{
        String MMO_MARCA_MEDIDOR = "marca";
        String MMO_ID = "id";
        String MMO_DESCRIPCION = "descripcion";
        String MMO_TIPO_EMPRESA = "tipo_empresa";
        String MMO_ACTIVO = "activo";
        String MMO_ID_USER = "user_id";
        String MMO_FECHA_UPDATE = "fecha_update";
        String MMO_LOTE_REPLICACION = "lote_replicacion";
    }

    interface ColumnasMedidorTipo{
        String MET_ID = "id";
        String MET_DESCRIPCION = "descripcion";
        String MET_TIPO_EMPRESA = "tipo_empresa";
        String MET_ACTIVO = "activo";
        String MET_ID_USER = "user_id";
        String MET_FECHA_UPDATE = "fecha_update";
        String MET_LOTE_REPLICACION = "lote_replicacion";
    }

    interface ColumnasPuntoMedicionMedidor{
        String PMM_EMPRESA = "empresa";
        String PMM_CLIENTE = "cliente";
        String PMM_SUMINISTRO = "suministro";
        String PMM_TIPO_EMPRESA = "tipo_empresa";
        String PMM_PUNTO_MEDICION = "punto_medicion";
        String PMM_MEDIDOR = "medidor";
        String PMM_UBICACION_HABITACULO = "ubicacion_habitaculo";
        String PMM_ADVERTENCIA = "advertencia";
        String PMM_ES_FACTURABLE = "es_facturable";
        String PMM_ID_USER = "user_id";
        String PMM_FECHA_UPDATE = "fecha_update";
        String PMM_LOTE_REPLICACION = "lote_replicacion";
    }
    interface ColumnasOperario{
        String OPE_ID = "id";
        String OPE_DESCRIPCION = "descripcion";
        String OPE_CONTRATISTA = "contratista";
        String OPE_SUCURSAL = "sucursal";
        String OPE_ACTIVO = "activo";
        String OPE_ID_USER = "user_id";
        String OPE_FECHA_UPDATE = "fecha_update";
        String OPE_LOTE_REPLICACION = "lote_replicacion";
    }
    interface ColumnasTipoConexion{
        String TCO_ID = "id";
        String TCO_DESCRIPCION = "descripcion";
        String TCO_TIPO_EMPRESA = "tipo_empresa";
        String TCO_FACTOR = "factor";
        String TCO_ACTIVO = "activo";
        String TCO_ID_USER = "user_id";
        String TCO_FECHA_UPDATE = "fecha_update";
        String TCO_LOTE_REPLICACION = "lote_replicacion";
    }
    interface ColumnasTipoConsumo{
        String TIC_ID = "id";
        String TIC_DESCRIPCION = "descripcion";
        String TIC_TIPO_EMPRESA = "tipo_empresa";
        String TIC_ABREVIATURA = "abreviatura";
        String TIC_TIPO_CONSUMO = "tipo_consumo";
        String TIC_TARIFA_CONSUMO = "tarifa_consumo";
        String TIC_TARIFA_RESERVA = "tarifa_reserva";
        String TIC_CARGO_FIJO = "cargo_fijo";
        String TIC_FACTURA_MINIMA = "factura_minima";
        String TIC_TRANSPORTE = "transporte";
        String TIC_IMPUTACION_CONTABLE = "imputacion_contable";
        String TIC_UTILIZA_FACTOR_PRESION = "utiliza_factor_presion";
        String TIC_UTILIZA_TRANSFORMADOR = "utiliza_transformador";
        String TIC_CARGO_FIJO_DESCUENTO = "cargo_fijo_descuento";
        String TIC_TARIFA_CONSUMO_DESCUENTO = "tarifa_consumo_descuento";
        String TIC_ACTIVO = "activo";
        String TIC_ID_USER = "user_id";
        String TIC_FECHA_UPDATE = "fecha_update";
        String TIC_LOTE_REPLICACION = "lote_replicacion";
        String TIC_SITUACION_IVA = "situacion_iva";
        String TIC_PORCENTAJE_CONTROL_CONSUMO_REDUCIDO = "porcentaje_control_consumo_reducido";
        String TIC_PORCENTAJE_CONTROL_CONSUMO_ELEVADO = "porcentaje_control_consumo_elevado";
        String TIC_CONSUMO_MINIMO_CONTROL = "consumo_minimo_control";
        String TIC_MUESTRA_EN_ATP = "muestra_en_atp";
        String TIC_MUESTRA_EN_ADMIN = "muestra_en_admin";
    }
    interface ColumnasTipoEmpresa{
        String TIE_ID = "id";
        String TIE_DESCRIPCION = "descripcion";
        String TIE_ABREVIATURA = "abreviatura";
        String TIE_ABREVIATURA_ATP = "abreviatura_atp";
        String TIE_MUESTRA_CONSUMO_CONVERTIDO = "muestra_consumo_convertido";
        String TIE_FACTOR_CONVERSION_CONSUMO = "factor_conversion_consumo";
        String TIE_ORDEN = "orden";
        String TIE_UTILIZA_MODULO_LECTURAS = "utiliza_modulo_lecturas";
        String TIE_USA_TRANSFORMADOR = "usa_transformacion";
        String TIE_BAJA_PERMITE_CAMBIAR_EST_ADM = "baja_permite_cambiar_est_adm";
        String TIE_ACTIVO = "activo";
        String TIE_ID_USER = "usar_id";
        String TIE_FECHA_UPDATE = "fecha_update";
        String TIE_LOTE_REPLICACION = "lote_replicacion";
        String TIE_MODO_CALCULO_TARIFA_CONSUMO = "modo_calculo_tarfifa_consumo";
        String TIE_AJUSTA_CARGO_FIJO_ALTA_SUMINISTRO = "ajusta_cargo_fijo_alta_suministro";
        String TIE_AJUSTA_ESCALA_CONSUMO_ALTA_SUMINISTRO = "ajusta_escala_consumo_alta_suministro";
        String TIE_AJUSTA_CARGO_FIJO_BAJA_SUMINISTRO = "ajusta_cargo_fijo_baja_suministro";
        String TIE_AJUSTA_ESCALA_CONSUMO_BAJA_SUMINISTRO = "ajusta_escala_consumo_baja_suministro";
        String TIE_MEDIDOR_FUNCION = "medidor_funcion";
        String TIE_COLOR = "color";
        String TIE_EMPRESA_COBRANZA = "empresa_cobranza";
        String TIE_ESTADO_OPE_BAJA = "estado_ope_baja";
    }

    interface ColumnasTipoUsuario{
        String TIU_ID = "id";
        String TIU_DESCRIPCION = "descripcion";
        String TIU_TIPO_EMPRESA = "tipo_empresa";
        String TIU_ABREVIATURA = "abreviatura";
        String TIU_SOLICITA_CIIU = "solicita_ciiu";
        String TIU_PUNTOS_MEDICION_MULTIPLES = "puntos_medicion_multiples";
        String TIU_MEDIDOR_FUNCION_MULTIPLE = "medidor_funcion_multiple";
        String TIU_ACTIVO = "activo";
        String TIU_ID_USER = "user_id";
        String TIU_FECHA_UPDATE = "fecha_update";
        String TIU_LOTE_REPLICACION = "lote_replicacion";
    }
    interface ColumnasSector{
        String SEC_ID = "id";
        String SEC_ID_SERVICIO = "servicio";
        String SEC_DESCRIPCION = "descripcion";
        String SEC_FECHA_UPDATE = "fecha_update";
    }
    interface ColumnasSuministro{
        String SUM_EMPRESA = "empresa";
        String SUM_CLIENTE = "cliente";
        String SUM_ID = "suministro";
        String SUM_SUCURSAL = "sucursal";
        String SUM_GRUPO = "grupo";
        String SUM_RUTA = "ruta";
        String SUM_ORDEN_LECTURA = "orden_lectura";
        String SUM_LOCALIDAD = "localidad";
        String SUM_CODIGO_POSTAL = "codigo_postal";
        String SUM_CALLE = "calle";
        String SUM_ALTURA = "altura";
        String SUM_PISO = "piso";
        String SUM_DEPARTAMENTO = "departamento";
        String SUM_CALLE_ENTRE1 = "calle_entre1";
        String SUM_CALLE_ENTRE2 = "calle_entre2";
        String SUM_ANEXO = "anexo";
        String SUM_REFERENCIA_MUNICIPAL = "referencia_municipal";
        String SUM_CIRCUNSCRIPCION = "circunscripcion";
        String SUM_RADIO = "radio";
        String SUM_MANZANA = "manzana";
        String SUM_QUINTA = "quinta";
        String SUM_PARCELA = "parcela";
        String SUM_SUBPARCELA = "subparcela";
        String SUM_INQUILINO = "inquilino";
        String SUM_FECHA_VTO_ALQUILER = "fecha_vto_alquiler";
        String SUM_CIIU = "ciiu";
        String SUM_DIRECCION_POSTAL = "direccion_postal";
        String SUM_GARANTE = "garante";
        String SUM_MOROSIDAD = "morosidad";
        String SUM_FACTURABLE = "facturable";
        String SUM_ESTADO_ADM = "estado_adm";
        String SUM_FECHA_ESTADO_ADM = "fecha_estado_adm";
        String SUM_FECHA_CAMBIO_TITULARIDAD = "fecha_cambio_titularidad";
        String SUM_TIENE_CONCEPTOS_PUNTUALES = "tiene_conceptos_puntuales";
        String SUM_VALIDA_PUNTUAL = "valida_puntual";
        String SUM_SUMINISTRO_ANTERIOR1 = "suministro_anterior1";
        String SUM_SUMINISTRO_ANTERIOR2 = "suministro_anterior2";
        String SUM_SUMINISTRO_ANTERIOR3 = "suministro_anterior3";
        String SUM_SUMINISTRO_ANTERIOR4 = "suministro_anterior4";
        String SUM_ID_USER = "user_id";
        String SUM_FECHA_UPDATE = "fecha_update";
        String SUM_LOTE_REPLICACION = "lote_replicacion";
    }
    interface ColumnasSuministroTipoEmpresa{
        String STE_EMPRESA = "empresa";
        String STE_CLIENTE = "cliente";
        String STE_SUMINISTRO = "suministro";
        String STE_TIPO_EMPRESA = "tipo_empresa";
        String STE_FECHA_INGRESO = "fecha_ingreso";
        String STE_ESTADO_OPE = "estado_ope";
        String STE_FECHA_ESTADO_OPE = "fecha_estado_ope";
        String STE_TIPO_CONSUMO = "tipo_consumo";
        String STE_TIPO_USUARIO = "tipo_usuario";
        String STE_TIPO_SERVICIO = "tipo_servicio";
        String STE_TIPO_CONEXION = "tipo_conexion";
        String STE_POTENCIA = "potencia";
        String STE_POSEE_PROYECTO = "posee_proyecto";
        String STE_RESERVA_CAPACIDAD = "reserva_capacidad";
        String STE_UNIDAD_PROVEEDORA = "unidad_proveedora";
        String STE_SUB_UNIDAD_PROVEEDORA = "sub_unidad_proveedora";
        String STE_TRANSFORMADOR = "transformador";
        String STE_ID_USER = "user_id";
        String STE_FECHA_UPDATE = "fecha_update";
        String STE_LOTE_REPLICACION = "lote_replicacion";
    }

    interface  ColumnasSuministroPosicionGlobal {
        String SPG_EMPRESA = "empresa";
        String SPG_CLIENTE = "cliente";
        String SPG_SUMINISTRO = "suministro";
        String SPG_LATITUD = "latitud";
        String SPG_LONGITUD = "longitud";
        String SPG_FECHA_CAPTURA = "fecha_captura";
        String SPG_USER_ID = "user_id";
        String SPG_FECHA_UPDATE = "fecha_update";
    }


    interface ColumnasTipoTrabajoCuadrilla {
        String TTC_CUADRILLA = "cuadrilla";
        String TTC_TIPO_TRABAJO = "tipo_trabajo";
        String TTC_FECHA_UPDATE = "fecha_update";
    }

    interface ColumnasZona{
        String ZON_ID = "id";
        String ZON_DESCRIPCION = "descripcion";
        String ZON_OBSERVACION = "observacion";
        String ZON_FECHA_UPDATE = "fecha_update";
    }
    interface ColumnasPuntoZona{
        String PZON_ID = "id";
        String PZON_ID_ZONA = "id_zona";
        String PZON_INDICE  = "indice";
        String PZON_LATITUD = "latitud";
        String PZON_LONGITUD = "longitud";
        String PZON_FECHA_UPDATE = "fecha_update";
    }
    interface ColumnasFoto{
        String OTF_NUMERO = "orden";
        String OTF_NFOTO = "numero";
        String OTF_FECHA = "fecha";
        String OTF_OBSERVACIONES = "observaciones";
        String OTF_ID_USER	= "id_user";
        String OTF_FECHA_UPDATE	= "fecha_update";
        String OTF_PATH	= "path";
        String OTF_SECTOR	= "sector";
        String OTF_SERVICIO	= "servicio";
    }
    interface ColumnasCuadrilla{
        String CU_ID = "id";
        String CU_TIPO_CUADRILLA = "tipo_cuadrilla";
        String CU_OPERARIO = "operario";
        String CU_FECHA = "fecha";
        String CU_FECHA_UPDATE = "fecha_update";
        String CU_SERVICIO = "servicio";
        String CU_SECTOR = "sector";
        String CU_SYNC = "sync";

    }
    interface ColumnasEstado{
        String OOS_ID = "id";
        String OOS_DESCRIPCION = "estado";
        String OOS_FECHA_UPDATE = "fecha_update";

    }
    interface ColumnasNota{
        String NOV_ID = "id";
        String NOV_FECHA = "fecha";
        String NOV_DESCRIPCION = "descripcion";
        String NOV_SERVICIO = "servicio";
        String NOV_SECTOR = "sector";
        String NOV_OPERARIO = "operario";
        String NOV_IDOPERARIO = "operario_id";
        String NOV_SYNC = "sync";
        String NOV_FECHA_UPDATE = "fecha_update";
    }

    // SYNCRONIZACION
    public static final int SYNC_OK = 0;
    public static final int SYNC_NOT = 1;
    /**
     * Autoridad del Content Provider
     */
    public final static String AUTHORITY = "ar.com.corpico.appcorpico";
    /**
     * URI de contenido principal
     */
    public final static Uri URI_BASE = Uri.parse("content://" + AUTHORITY);

    private static final String RUTA_ORDENES_OPERATIVAS = "ordenes_operativas";
    private static final String RUTA_TIPOS_TRABAJO = "tipos_trabajos";
    private static final String RUTA_MOTIVOS_TRABAJO = "motivos_trabajos";
    private static final String RUTA_TIPOS_CUADRILLA = "tipos_cuadrillas";
    private static final String RUTA_CLIENTES = "clientes";
    private static final String RUTA_ETAPAS = "etapas";
    private static final String RUTA_TURNOS = "turnos";
    private static final String RUTA_EMPRESAS_CONTRATISTA = "empresas_contratista";
    private static final String RUTA_ESTADO_OPERATIVO = "estados_operativo";
    private static final String RUTA_LOCALIDADES = "localidades";
    private static final String RUTA_MEDIDORES = "medidores";
    private static final String RUTA_MEDIDORES_CLASES = "medidores_clases";
    private static final String RUTA_MEDIDORES_MARCAS = "medidores_marcas";
    private static final String RUTA_MEDIDORES_MODELOS = "medidores_modelos";
    private static final String RUTA_MEDIDORES_TIPOS = "medidores_tipos";
    private static final String RUTA_PUNTO_MEDICION_MEDIDOR = "punto_medicion_medidor";
    private static final String RUTA_OPERARIO = "operario";
    private static final String RUTA_TIPO_CONEXION = "tipo_conexion";
    private static final String RUTA_TIPO_CONSUMOS = "tipo_consumos";
    private static final String RUTA_TIPO_EMPRESAS = "tipo_empresas";
    private static final String RUTA_TIPO_USUARIOS = "tipo_usuarios";
    private static final String RUTA_SECTORES = "sectores";
    private static final String RUTA_SUMINISTROS = "suministros";
    private static final String RUTA_SUMINISTROS_TIPO_EMPRESA = "suministros_tipo_empresa";
    private static final String RUTA_SUMINISTROS_POSICION_GLOBAL = "suministros_posicion_global";
    private static final String RUTA_TIPO_TRABAJO_CUADRILLAS = "tipo_trabajo_cuadrilla";
    private static final String RUTA_ZONAS = "zonas";
    private static final String RUTA_PUNTOS_ZONA = "puntos_zona";
    private static final String RUTA_FOTOS = "fotos";
    private static final String RUTA_CUADRILLAS = "cuadrillas";
    private static final String RUTA_ESTADOS = "estados";
    private static final String RUTA_NOTAS = "notas";


    public static final String BASE_CONTENIDOS = "appcorpico.";

    public static final String TIPO_CONTENIDO = "vnd.android.cursor.dir/vnd." + BASE_CONTENIDOS;

    public static final String TIPO_CONTENIDO_ITEM = "vnd.android.cursor.item/vnd." + BASE_CONTENIDOS;

    private ContratoCorpicoApp() {
    }

    public static String generarMime(String id) {
        if (id != null) {
            return TIPO_CONTENIDO + id;
        } else {
            return null;
        }
    }

    public static String generarMimeItem(String id) {
        if (id != null) {
            return TIPO_CONTENIDO_ITEM + id;
        } else {
            return null;
        }
    }

    public static class OrdenesOperativas implements ColumnasOrdenOperativa {
        public static final String TABLE_NAME = "orden_operativa";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_ORDENES_OPERATIVAS).build();

        public static final String PARAMETRO_FILTRO = "filtro";
        public static final String FILTRO_FECHA = "fecha";
        public static final String FILTRO_ESTADO = "estado";

        // Valores para el parámetro de Uri "display"
        public static final String DISPLAY_KEY = "display";
        public static final String DISPLAY_DEFAULT = "default";
        public static final String DISPLAY_FULL = "full";
        public static final String DISPLAY_SINGLE = "single";
        //Valores para el parametro de Uri "orden"
        public static final String NUMERO_ORDEN_KEY = "orden_numero";
        public static final String HOME_SERVICIO_KEY = "servicio_home";
        public static final String HOME_SECTOR_KEY = "sector_home";
        public static final String DISPLAY_HOME = "cuadrilla_home";
        public static final String DISPLAY_ESTADO_HOME = "estado_home";
        public static final String DISPLAY_ZONA_HOME = "zona_home";
        public static final String DISPLAY_TURNO = "turno";
        public static final String DISPLAY_ORDEN = "orden";
        public static final String DISPLAY_TIPO_TRABAJO_HOME = "tipo_trabajo_home";
        public static final String DISPLAY_SIN_ZONA = "sin_zona";


        public static int obtenerIdOrdenOperativa(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static Uri crearUriOrdenOperativa(Integer id) {
            return URI_CONTENIDO.buildUpon().appendPath(id.toString()).build();
        }
        public static Uri crearUriOrdenDetail(int numero) {
            Uri uri = null;

            if (numero != 0) {
                uri = URI_CONTENIDO.buildUpon()
                        .appendQueryParameter(DISPLAY_KEY,DISPLAY_ORDEN)
                        .appendQueryParameter(NUMERO_ORDEN_KEY, String.valueOf(numero))
                        .build();
            }
            return uri;
        }

        public static Uri crearUriCuadrillaHome(int servicio, int sector) {
            Uri uri = null;

            if (servicio !=0 && sector != 0) {
                uri = URI_CONTENIDO.buildUpon()
                        .appendQueryParameter(DISPLAY_KEY,DISPLAY_HOME)
                        .appendQueryParameter(HOME_SERVICIO_KEY, String.valueOf(servicio))
                        .appendQueryParameter(HOME_SECTOR_KEY, String.valueOf(sector))
                        .build();
            }
            return uri;
        }
        public static Uri crearUriEstadoHome(int servicio, int sector) {
            Uri uri = null;

            if (servicio !=0 && sector != 0) {
                uri = URI_CONTENIDO.buildUpon()
                        .appendQueryParameter(DISPLAY_KEY,DISPLAY_ESTADO_HOME)
                        .appendQueryParameter(HOME_SERVICIO_KEY, String.valueOf(servicio))
                        .appendQueryParameter(HOME_SECTOR_KEY, String.valueOf(sector))
                        .build();
            }
            return uri;
        }
        public static Uri crearUriSinZona(int servicio, int sector) {
            Uri uri = null;

            if (servicio !=0 && sector != 0) {
                uri = URI_CONTENIDO.buildUpon()
                        .appendQueryParameter(DISPLAY_KEY,DISPLAY_SIN_ZONA)
                        .appendQueryParameter(HOME_SERVICIO_KEY, String.valueOf(servicio))
                        .appendQueryParameter(HOME_SECTOR_KEY, String.valueOf(sector))
                        .build();
            }
            return uri;
        }
        public static Uri crearUriTipoTrabajoHome(int servicio, int sector) {
            Uri uri = null;

            if (servicio !=0 && sector != 0) {
                uri = URI_CONTENIDO.buildUpon()
                        .appendQueryParameter(DISPLAY_KEY,DISPLAY_TIPO_TRABAJO_HOME)
                        .appendQueryParameter(HOME_SERVICIO_KEY, String.valueOf(servicio))
                        .appendQueryParameter(HOME_SECTOR_KEY, String.valueOf(sector))
                        .build();
            }
            return uri;
        }
        public static Uri crearUriZonaHome(int servicio, int sector) {
            Uri uri = null;

            if (servicio !=0 && sector != 0) {
                uri = URI_CONTENIDO.buildUpon()
                        .appendQueryParameter(DISPLAY_KEY,DISPLAY_ZONA_HOME)
                        .appendQueryParameter(HOME_SERVICIO_KEY, String.valueOf(servicio))
                        .appendQueryParameter(HOME_SECTOR_KEY, String.valueOf(sector))
                        .build();
            }
            return uri;
        }


        /*    content:{PROVIDER}/ordenes_operativas?display=[full|default]    */
        public static Uri crearUriConDisplay(@Nullable String display) {
            Uri uri = URI_CONTENIDO.buildUpon()
                    .appendQueryParameter(DISPLAY_KEY, DISPLAY_DEFAULT)
                    .build();

            if (display !=null && display.equals(DISPLAY_FULL)) {
                uri = URI_CONTENIDO.buildUpon()
                        .appendQueryParameter(DISPLAY_KEY, DISPLAY_FULL)
                        .build();
            }

            if (display !=null && display.equals(DISPLAY_SINGLE)) {
                uri = URI_CONTENIDO.buildUpon()
                        .appendQueryParameter(DISPLAY_KEY, DISPLAY_SINGLE)
                        .build();
            }
            if (display !=null && display.equals(DISPLAY_HOME)) {
                uri = URI_CONTENIDO.buildUpon()
                        .appendQueryParameter(DISPLAY_KEY, DISPLAY_HOME)
                        .build();
            }
            if (display !=null && display.equals(DISPLAY_TURNO)) {
                uri = URI_CONTENIDO.buildUpon()
                        .appendQueryParameter(DISPLAY_KEY, DISPLAY_TURNO)
                        .build();
            }
            if (display !=null && display.equals(DISPLAY_ORDEN)) {
                uri = URI_CONTENIDO.buildUpon()
                        .appendQueryParameter(DISPLAY_KEY, DISPLAY_ORDEN)
                        .build();
            }
            if (display !=null && display.equals(DISPLAY_ESTADO_HOME)) {
                uri = URI_CONTENIDO.buildUpon()
                        .appendQueryParameter(DISPLAY_KEY, DISPLAY_ESTADO_HOME)
                        .build();
            }
            if (display !=null && display.equals(DISPLAY_TIPO_TRABAJO_HOME)) {
                uri = URI_CONTENIDO.buildUpon()
                        .appendQueryParameter(DISPLAY_KEY, DISPLAY_TIPO_TRABAJO_HOME)
                        .build();
            }
            if (display !=null && display.equals(DISPLAY_ZONA_HOME)) {
                uri = URI_CONTENIDO.buildUpon()
                        .appendQueryParameter(DISPLAY_KEY, DISPLAY_ZONA_HOME)
                        .build();
            }
            if (display !=null && display.equals(DISPLAY_SIN_ZONA)) {
                uri = URI_CONTENIDO.buildUpon()
                        .appendQueryParameter(DISPLAY_KEY, DISPLAY_SIN_ZONA)
                        .build();
            }
            return uri;
        }

        public static String obtenerParamConDisplay(Uri uri){
            String visualizaParamDisplay = null;

            if (uri.getQueryParameter(DISPLAY_KEY) != null){
                visualizaParamDisplay = uri.getQueryParameter(DISPLAY_KEY);

            }
            return visualizaParamDisplay;
        }
        public static String obtenerParamHome(Uri uri){
            String visualizaParamHome = null;

             visualizaParamHome = uri.getQueryParameter(HOME_SERVICIO_KEY) + "#" + uri.getQueryParameter(HOME_SECTOR_KEY);

            return visualizaParamHome;
        }
        public static String obtenerParamSinZona(Uri uri){
            String visualizaParamSinZona = null;

            visualizaParamSinZona = uri.getQueryParameter(HOME_SERVICIO_KEY) + "#" + uri.getQueryParameter(HOME_SECTOR_KEY);

            return visualizaParamSinZona;
        }
        public static Uri crearUriConOrden(@Nullable String numero) {
            Uri uri = null;

            if (numero !=null ) {
                uri = URI_CONTENIDO.buildUpon().appendQueryParameter(NUMERO_ORDEN_KEY, numero).build();
            }

            return uri;
        }
        public static String obtenerParamOrden(Uri uri){
            return uri.getQueryParameter(NUMERO_ORDEN_KEY);
        }


        public static boolean tieneFiltro(Uri uri) {
            return uri != null && uri.getQueryParameter(PARAMETRO_FILTRO) != null;
        }
    }

    public static class TiposTrabajo implements ColumnasTipoTrabajo {
        public static final String TABLE_NAME = "tipo_trabajo";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_TIPOS_TRABAJO).build();

        public static final String PARAMETRO_FILTRO = "filtro";

        public static final String CUADRILLA_KEY = "cuadrilla";
        public static final String CUADRILLA_DEFAULT = "default";
        //public static final String FILTRO_FECHA = "fecha";

        public static int obtenerIdTipoTrabajo(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static Uri crearUriTipoTrabajo(Integer id) {
            return URI_CONTENIDO.buildUpon().appendPath(id.toString()).build();
        }

        public static boolean tieneFiltro(Uri uri) {
            return uri != null && uri.getQueryParameter(PARAMETRO_FILTRO) != null;
        }
        public static Uri crearUriConCuadrilla(@Nullable String cuadrilla) {
            Uri uri = null;

            if (cuadrilla !=null && cuadrilla.equals("default") ) {
                uri = URI_CONTENIDO.buildUpon().appendQueryParameter(CUADRILLA_KEY, cuadrilla).build();
            }

            return uri;
        }
        public static String obtenerParamCuadrilla(Uri uri){
            return uri.getQueryParameter(CUADRILLA_KEY);

    }
    }

    public static class MotivosTrabajo implements ColumnasMotivoTrabajo {
        public static final String TABLE_NAME = "motivo_trabajo";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_MOTIVOS_TRABAJO).build();

        public static final String PARAMETRO_FILTRO = "filtro";
        public static final String FILTRO_TIPO_TRABAJO = "tipo_trabajo";
        //public static final String FILTRO_FECHA = "fecha";

        public static int obtenerIdMotivoTrabajo(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static Uri crearUriMotivoTrabajo(Integer id) {
            return URI_CONTENIDO.buildUpon().appendPath(id.toString()).build();
        }

        public static boolean tieneFiltro(Uri uri) {
            return uri != null && uri.getQueryParameter(PARAMETRO_FILTRO) != null;
        }
    }

    public static class TiposCuadrilla implements ColumnasTipoCuadrilla {
        public static final String TABLE_NAME = "tipo_cuadrilla";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_TIPOS_CUADRILLA).build();

        public static final String PARAMETRO_FILTRO = "filtro";
        public static final String FILTRO_SECTOR = "sector";
        //public static final String FILTRO_FECHA = "fecha";

        public static int obtenerIdTipoCuadrilla(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static Uri crearUriTipoCuadrilla(Integer id) {
            return URI_CONTENIDO.buildUpon().appendPath(id.toString()).build();
        }

        public static boolean tieneFiltro(Uri uri) {
            return uri != null && uri.getQueryParameter(PARAMETRO_FILTRO) != null;
        }
    }

    public static class Clientes implements ColumnasCliente {
        public static final String TABLE_NAME = "cliente";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_CLIENTES).build();

        public static int obtenerIdCliente(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static Uri crearUriCliente(Integer id) {
            return URI_CONTENIDO.buildUpon().appendPath(id.toString()).build();
        }
    }
    public static class Etapas implements ColumnasEtapa {
        public static final String TABLE_NAME = "etapa";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_ETAPAS).build();

        public static final String PARAMETRO_FILTRO = "filtro";
        public static final String FILTRO_NUMERO = "numero";

        // Valores para el parámetro de Uri "orden"
        public static final String ORDEN_KEY = "orden";


        public static int obtenerIdEtapa(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static Uri crearUriEtapa(Integer id) {
            return URI_CONTENIDO.buildUpon().appendPath(id.toString()).build();
        }
        public static boolean tieneFiltro(Uri uri) {
            return uri != null && uri.getQueryParameter(PARAMETRO_FILTRO) != null;
        }
        // ordenes/*/etapas

        // etapas?orden={valor}
        public static Uri crearUriConOrden(@Nullable String numero) {
            Uri uri = null;

            if (numero !=null ) {
                uri = URI_CONTENIDO.buildUpon().appendQueryParameter(ORDEN_KEY, numero).build();
            }

            return uri;
        }
        public static String obtenerParamOrden(Uri uri){
            return uri.getQueryParameter(ORDEN_KEY);
        }

    }
    public static class Turnos implements ColumnasTurno {
        public static final String TABLE_NAME = "turno";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_TURNOS).build();

        public static final String ORDEN_KEY = "orden";

        public static int obtenerIdTurno(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static Uri crearUriTurno(Integer id) {
            return URI_CONTENIDO.buildUpon().appendPath(id.toString()).build();
        }
        public static Uri crearUriConOrden(@Nullable String numero) {
            Uri uri = null;

            if (numero !=null ) {
                uri = URI_CONTENIDO.buildUpon().appendQueryParameter(ORDEN_KEY, numero).build();
            }

            return uri;
        }
        public static String obtenerParamOrden(Uri uri){

            return uri.getQueryParameter(ORDEN_KEY);
        }
    }
    public static class EmpresasContratista implements ColumnasEmpresaContratista {
        public static final String TABLE_NAME = "empresa_contratista";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_EMPRESAS_CONTRATISTA).build();

        public static int obtenerIdEmpresaContratista(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static Uri crearUriEmpresaContratista(Integer id) {
            return URI_CONTENIDO.buildUpon().appendPath(id.toString()).build();
        }
    }
    public static class EstadoOperativo implements ColumnasEstadoOperativo {
        public static final String TABLE_NAME = "estado_operativo";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_ESTADO_OPERATIVO).build();

        public static int obtenerIdEstadoOperativo(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static Uri crearUriEstadoOperativo(Integer id) {
            return URI_CONTENIDO.buildUpon().appendPath(id.toString()).build();
        }
    }
    public static class Localidad implements ColumnasLocalidad {
        public static final String TABLE_NAME = "localidad";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_LOCALIDADES).build();

        public static int obtenerIdLocalidad(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static Uri crearUriLocalidad(Integer id) {
            return URI_CONTENIDO.buildUpon().appendPath(id.toString()).build();
        }
    }
    public static class Medidor implements ColumnasMedidor {
        public static final String TABLE_NAME = "medidor";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_MEDIDORES).build();

        public static int obtenerIdMedidor(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static Uri crearUriMedidor(Integer id) {
            return URI_CONTENIDO.buildUpon().appendPath(id.toString()).build();
        }
    }
    public static class MedidorClase implements ColumnasMedidorClase {
        public static final String TABLE_NAME = "medidor_clase";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_MEDIDORES_CLASES).build();

        public static int obtenerIdMedidorClase(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static Uri crearUriMedidorClase(Integer id) {
            return URI_CONTENIDO.buildUpon().appendPath(id.toString()).build();
        }
    }
    public static class MedidorMarca implements ColumnasMedidorMarca {
        public static final String TABLE_NAME = "medidor_marca";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_MEDIDORES_MARCAS).build();

        public static int obtenerIdMedidorMarca(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static Uri crearUriMedidorMarca(Integer id) {
            return URI_CONTENIDO.buildUpon().appendPath(id.toString()).build();
        }
    }
    public static class MedidorModelo implements ColumnasMedidorModelo {
        public static final String TABLE_NAME = "medidor_modelo";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_MEDIDORES_MODELOS).build();

        public static int obtenerIdMedidorModelo(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static Uri crearUriMedidorModelo(Integer id) {
            return URI_CONTENIDO.buildUpon().appendPath(id.toString()).build();
        }
    }
    public static class MedidorTipo implements ColumnasMedidorTipo {
        public static final String TABLE_NAME = "medidor_tipo";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_MEDIDORES_TIPOS).build();

        public static int obtenerIdMedidorTipo(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static Uri crearUriMedidorTipo(Integer id) {
            return URI_CONTENIDO.buildUpon().appendPath(id.toString()).build();
        }
    }
    public static class PuntoMedicioMedidor implements ColumnasPuntoMedicionMedidor {
        public static final String TABLE_NAME = "punto_medicion_medidor";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_PUNTO_MEDICION_MEDIDOR).build();

        public static Uri crearUriPuntoMedicionMedidor(Integer cliente, short suministro,
                                                       byte tipoEmpresa,short ptoMedMedidor,
                                                       Integer medidor) {
            // Uri de la forma 'puntoMedidonMedidor/:id#:secuencia'
            return URI_CONTENIDO.buildUpon()
                    .appendPath(String.format("%s#%s#%s#%s#%s", cliente, suministro,tipoEmpresa,ptoMedMedidor,medidor))
                    .build();
        }

        public static String[] obtenerIdPuntoMedicionMedidor(Uri uri) {
            return uri.getLastPathSegment().split("#");
        }
    }
    public static class Operario implements ColumnasOperario {
        public static final String TABLE_NAME = "operario";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_OPERARIO).build();

        public static final String SECTOR_KEY = "sector";

        public static int obtenerIdOperario(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static Uri crearUriOperario(Integer id) {
            return URI_CONTENIDO.buildUpon().appendPath(id.toString()).build();
        }
        public static Uri crearUriConSector(String servicioKey, @Nullable String sector) {
            Uri uri = null;

            if (sector !=null ) {
                uri = URI_CONTENIDO.buildUpon().appendQueryParameter(SECTOR_KEY, sector).build();
            }

            return uri;
        }
        public static String obtenerUriConSector(Uri uri){
            return uri.getQueryParameter(SECTOR_KEY);
        }

        /*public static Uri crearUriConSector(Integer sector, Integer tipoCuadrilla) {
            // Uri de la forma 'puntoMedidonMedidor/:id#:secuencia'
            return URI_CONTENIDO.buildUpon()
                    .appendPath(String.format("%s#%s", sector, tipoCuadrilla))
                    .build();
        }

        public static String[] obtenerUriConSector(Uri uri) {
            return uri.getLastPathSegment().split("#");
        }*/
    }
    public static class TipoConexion implements ColumnasTipoConexion {
        public static final String TABLE_NAME = "tipo_conexion";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_TIPO_CONEXION).build();

        public static int obtenerIdTipoConexion(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static Uri crearUriTipoConexion(Integer id) {
            return URI_CONTENIDO.buildUpon().appendPath(id.toString()).build();
        }
    }
    public static class TipoConsumo implements ColumnasTipoConsumo {
        public static final String TABLE_NAME = "tipo_consumo";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_TIPO_CONSUMOS).build();

        public static int obtenerIdTipoConsumo(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static Uri crearUriTipoConsumo(Integer id) {
            return URI_CONTENIDO.buildUpon().appendPath(id.toString()).build();
        }
    }
    public static class TipoEmpresa implements ColumnasTipoEmpresa {
        public static final String TABLE_NAME = "tipo_empresa";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_TIPO_EMPRESAS).build();

        public static int obtenerIdTipoEmpresa(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static Uri crearUriTipoEmpresa(Integer id) {
            return URI_CONTENIDO.buildUpon().appendPath(id.toString()).build();
        }
    }
    public static class TipoUsuario implements ColumnasTipoUsuario {
        public static final String TABLE_NAME = "tipo_usuario";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_TIPO_USUARIOS).build();

        public static int obtenerIdTipoUsuario(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static Uri crearUriTipoUsuario(Integer id) {
            return URI_CONTENIDO.buildUpon().appendPath(id.toString()).build();
        }
    }
    public static class Sector implements ColumnasSector{
        public static final String TABLE_NAME = "sector";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_SECTORES).build();

        public static int obtenerIdSector(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static Uri crearUriSector(Integer id) {
            return URI_CONTENIDO.buildUpon().appendPath(id.toString()).build();
        }
    }

    public static class Suministro implements ColumnasSuministro {
        public static final String TABLE_NAME = "suministro";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_SUMINISTROS).build();

        public static Uri crearUriSuministro(Integer cliente, short suministro) {
            // Uri de la forma 'puntoMedidonMedidor/:id#:secuencia'
            // suministros/{cliente}/{suministro}
            return URI_CONTENIDO.buildUpon()
                    .appendPath(String.format("%s#%s", cliente, suministro))
                    .build();
        }

        public static String[] obtenerIdSuministro(Uri uri) {
            return uri.getLastPathSegment().split("#");
        }
    }
    public static class SuministroTipoEmpresa implements ColumnasSuministroTipoEmpresa {
        public static final String TABLE_NAME = "suministro_tipo_empresa";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_SUMINISTROS_TIPO_EMPRESA).build();

        public static Uri crearUriSuministroTipoEmpresa(Integer cliente, short suministro) {
            // Uri de la forma 'puntoMedidonMedidor/:id#:secuencia'
            return URI_CONTENIDO.buildUpon()
                    .appendPath(String.format("%s#%s", cliente, suministro))
                    .build();
        }

        public static String[] obtenerIdSuministroTipoEmpresa(Uri uri) {
            return uri.getLastPathSegment().split("#");
        }
    }
    public static class SuministroPosicionGlobal implements ColumnasSuministroPosicionGlobal {
        public static final String TABLE_NAME = "suministro_posicion_global";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_SUMINISTROS_POSICION_GLOBAL).build();

        public static Uri crearUriSuministroPosicionGlobal(Integer cliente, short suministro) {
            // Uri de la forma 'puntoMedidonMedidor/:id#:secuencia'
            return URI_CONTENIDO.buildUpon()
                    .appendPath(String.format("%s#%s", cliente, suministro))
                    .build();
        }

        public static String[] obtenerIdSuministroPosicionGlobal(Uri uri) {
            return uri.getLastPathSegment().split("#");
        }
    }
    public static class TipoTrabajoCuadrilla implements ColumnasTipoTrabajoCuadrilla {
        public static final String TABLE_NAME = "tipo_trabajo_cuadrilla";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_TIPO_TRABAJO_CUADRILLAS).build();

        public static Uri crearUriTipoTrabajoCuadrilla(Integer cuadrilla, Integer tipoTrabajo) {
            // Uri de la forma 'puntoMedidonMedidor/:id#:secuencia'
            return URI_CONTENIDO.buildUpon()
                    .appendPath(String.format("%s#%s", cuadrilla, tipoTrabajo))
                    .build();
        }

        public static String[] obtenerIdTipoTrabajoCuadrilla(Uri uri) {
            return uri.getLastPathSegment().split("#");
        }
    }
    public static class Zona implements ColumnasZona{
        public static final String TABLE_NAME = "zona";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_ZONAS).build();

        public static final String ZONA_KEY = "display.zona";
        public static final String DISPLAY_FILTER = "filter";
        public static final String ZONA_DESCRIPCION_KEY = "zona.descripcion";


        public static int obtenerIdZona(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static Uri crearUriZona(Integer id) {
            return URI_CONTENIDO.buildUpon().appendPath(id.toString()).build();
        }

        public static Uri crearUriZonaFilter(String descripcion) {
            Uri uri = null;

            if (descripcion != null) {
                uri = URI_CONTENIDO.buildUpon()
                        .appendQueryParameter(ZONA_KEY,DISPLAY_FILTER)
                        .appendQueryParameter(ZONA_DESCRIPCION_KEY, descripcion)
                        .build();
            }
            return uri;
        }
        public static String obtenerParamZonaFilter(Uri uri){
            String visualizaParamZonaFilter = null;

            visualizaParamZonaFilter = uri.getQueryParameter(ZONA_DESCRIPCION_KEY);

            return visualizaParamZonaFilter;
        }
    }
    public static class PuntoZona implements ColumnasPuntoZona{
        public static final String TABLE_NAME = "punto_zona";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_PUNTOS_ZONA).build();

        public static final String DISPLAY_KEY = "display";
        public static final String DISPLAY_ZONA = "zona";

        public static int obtenerIdPuntoZona(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static Uri crearUriPuntoZona(Integer id) {
            return URI_CONTENIDO.buildUpon().appendPath(id.toString()).build();
        }
        public static Uri crearUriConDisplay(@Nullable String display) {
            Uri uri = URI_CONTENIDO.buildUpon()
                    .appendQueryParameter(DISPLAY_KEY, DISPLAY_ZONA)
                    .build();
            return uri;
        }
        public static String obtenerParamConDisplay(Uri uri){
            String visualizaParam = null;

            if (uri.getQueryParameter(DISPLAY_KEY) != null){
                visualizaParam = uri.getQueryParameter(DISPLAY_KEY);

            }
            return visualizaParam;
        }
    }
    public static class Cuadrilla implements ColumnasCuadrilla{
        public static final String TABLE_NAME = "cuadrilla";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_CUADRILLAS).build();

        // Valores para el parámetro de Uri "display"
        public static final String DISPLAY_KEY = "display";
        public static final String DISPLAY_DEFAULT = "default";
        public static final String DISPLAY_FULL = "full";
        public static final String DISPLAY_SINGLE = "single";

        public static int obtenerIdCuadrilla(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static Uri crearUriCuadrilla(Integer id) {
            return URI_CONTENIDO.buildUpon().appendPath(id.toString()).build();
        }
        public static Uri crearUriConOperario(@Nullable String display) {
            Uri uri = URI_CONTENIDO.buildUpon()
                    .appendQueryParameter(DISPLAY_KEY, DISPLAY_DEFAULT)
                    .build();

            if (display !=null && display.equals(DISPLAY_FULL)) {
                uri = URI_CONTENIDO.buildUpon()
                        .appendQueryParameter(DISPLAY_KEY, DISPLAY_FULL)
                        .build();
            }

            if (display !=null && display.equals(DISPLAY_SINGLE)) {
                uri = URI_CONTENIDO.buildUpon()
                        .appendQueryParameter(DISPLAY_KEY, DISPLAY_SINGLE)
                        .build();
            }
            return uri;
        }
        public static String obtenerParamConOperario(Uri uri){
            String visualizaParamConOperario = null;


            if (uri.getQueryParameter(DISPLAY_KEY) != null){
                visualizaParamConOperario = uri.getQueryParameter(DISPLAY_KEY);

            }
            //return uri.getQueryParameter(DISPLAY_KEY) + "#" + uri.getQueryParameter(SERVICIO_KEY);
            //return uri.getLastPathSegment().split("#");
            return visualizaParamConOperario;
        }
    }

    public static class Foto implements ColumnasFoto{
        public static final String TABLE_NAME = "foto";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_FOTOS).build();
        // Valores para el parámetro de Uri "orden"
        public static final String ORDEN_KEY = "orden";

        public static String[] obtenerIdFoto(Uri uri) {
            return uri.getLastPathSegment().split("#");
        }

        public static Uri crearUriFoto(Integer orden, short nfoto) {
            return URI_CONTENIDO.buildUpon()
                    .appendPath(String.format("%s#%s", orden, nfoto))
                    .build();
        }

        public static Uri crearUriConOrden(@Nullable String numero) {
            Uri uri = null;

            if (numero !=null ) {
                uri = URI_CONTENIDO.buildUpon().appendQueryParameter(ORDEN_KEY, numero).build();
            }

            return uri;
        }
        public static String obtenerParamOrden(Uri uri){
            return uri.getQueryParameter(ORDEN_KEY);
        }

    }
    public static class Estado implements ColumnasEstado{
        public static final String TABLE_NAME = "estado";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_ESTADOS).build();

        public static String obtenerIdEstado(Uri uri) {
            return (uri.getPathSegments().get(1));
        }

        public static Uri crearUriEstado(String id) {
            return URI_CONTENIDO.buildUpon().appendPath(id).build();
        }
    }
    public static class Nota implements ColumnasNota{
        public static final String TABLE_NAME = "nota";
        public static final Uri URI_CONTENIDO = URI_BASE.buildUpon().appendPath(RUTA_NOTAS).build();

        public static String obtenerIdNota(Uri uri) {
            return (uri.getPathSegments().get(1));
        }

        public static Uri crearUriNota(String id) {
            return URI_CONTENIDO.buildUpon().appendPath(id).build();
        }
    }
}
