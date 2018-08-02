package ar.com.corpico.appcorpico.orders.data.entity.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrador on 16/03/2018.
 */

public class _RestOrder {
    @SerializedName("OTR_SUCURSAL")
    private Integer OTR_SUCURSAL;
    @SerializedName("OTR_CENTRO_ATENCION")
    private Integer OTR_CENTRO_ATENCION;
    @SerializedName("OTR_NUMERO")
    private Integer OTR_NUMERO;
    @SerializedName("OTR_EMPRESA")
    private short OTR_EMPRESA ;
    @SerializedName("OTR_CLIENTE")
    private Integer OTR_CLIENTE;
    @SerializedName("OTR_SUMINISTRO")
    private short OTR_SUMINISTRO;
    @SerializedName("OTR_PUNTO_MEDICION")
    private short OTR_PUNTO_MEDICION ;
    @SerializedName("OTR_FECHA_SOLICITUD")
    //private DateTime OTR_FECHA_SOLICITUD;
    private String OTR_FECHA_SOLICITUD;
    @SerializedName("OTR_TIPO_TRABAJO")
    private Integer OTR_TIPO_TRABAJO;
    @SerializedName("OTR_MOTIVO_TRABAJO")
    private Integer OTR_MOTIVO_TRABAJO;
    @SerializedName("OTR_FECHA_VENCIMIENTO")
    private String OTR_FECHA_VENCIMIENTO ;
    @SerializedName("OTR_OBSERVACIONES_AL_OPE")
    private String OTR_OBSERVACIONES_AL_OPE ;
    @SerializedName("OTR_ID_USER_GENERO")
    private String OTR_ID_USER_GENERO;
    @SerializedName("OTR_FORMA_GENERACION")
    private String OTR_FORMA_GENERACION;
    @SerializedName("OTR_ESTADO_OPERATIVO_ANTES_GENERAR")
    private byte OTR_ESTADO_OPERATIVO_ANTES_GENERAR;
    @SerializedName("OTR_FECHA_EST_OPERATIVO_ANTES_GENERAR")
    private String OTR_FECHA_EST_OPERATIVO_ANTES_GENERAR ;
    @SerializedName("OTR_ESTADO_OPERATIVO_LUEGO_GENERAR")
    private byte OTR_ESTADO_OPERATIVO_LUEGO_GENERAR;
    @SerializedName("OTR_EMPRESA_CONTRATISTA")
    private short OTR_EMPRESA_CONTRATISTA;
    @SerializedName("OTR_FECHA_ASIGNACION")
    private String OTR_FECHA_ASIGNACION;
    @SerializedName("OTR_ID_USER_ASIGNO")
    private String OTR_ID_USER_ASIGNO;
    @SerializedName("OTR_FECHA_CULMINACION")
    private String OTR_FECHA_CULMINACION;
    @SerializedName("OTR_OPERARIO")
    private short OTR_OPERARIO;
    @SerializedName("OTR_OBSERVACION_DEL_OPE")
    private String OTR_OBSERVACION_DEL_OPE;
    @SerializedName("OTR_ID_USER_RETENCION")
    private String OTR_ID_USER_RETENCION;
    @SerializedName("OTR_FECHA_RETENCION")
    private String OTR_FECHA_RETENCION;
    @SerializedName("OTR_MOTIVO_RETENCION")
    private String OTR_MOTIVO_RETENCION;
    @SerializedName("OTR_ID_USER_LIBERACION")
    private String OTR_ID_USER_LIBERACION;
    @SerializedName("OTR_FECHA_LIBERACION")
    private String OTR_FECHA_LIBERACION;
    @SerializedName("OTR_OPERARIO_REALIZACION")
    private short OTR_OPERARIO_REALIZACION;
    @SerializedName("OTR_ESTADO")
    private String OTR_ESTADO;
    @SerializedName("OTR_ID_USER")
    private String OTR_ID_USER;
    @SerializedName("OTR_FECHA_UPDATE")
    private String OTR_FECHA_UPDATE;
    @SerializedName("OTR_ID_USER_ALTA")
    private String OTR_ID_USER_ALTA;
    @SerializedName("OTR_FECHA_ALTA")
    private String OTR_FECHA_ALTA;
    @SerializedName("OTR_LOTE_REPLICACION")
    private Integer OTR_LOTE_REPLICACION;

    public _RestOrder(Integer OTR_SUCURSAL, Integer OTR_CENTRO_ATENCION, Integer OTR_NUMERO, short OTR_EMPRESA,
                      Integer OTR_CLIENTE, short OTR_SUMINISTRO, short OTR_PUNTO_MEDICION, String OTR_FECHA_SOLICITUD,
                      Integer OTR_TIPO_TRABAJO, Integer OTR_MOTIVO_TRABAJO, String OTR_FECHA_VENCIMIENTO,
                      String OTR_OBSERVACIONES_AL_OPE, String OTR_ID_USER_GENERO, String OTR_FORMA_GENERACION,
                      byte OTR_ESTADO_OPERATIVO_ANTES_GENERAR, String OTR_FECHA_EST_OPERATIVO_ANTES_GENERAR,
                      byte OTR_ESTADO_OPERATIVO_LUEGO_GENERAR, short OTR_EMPRESA_CONTRATISTA, String OTR_FECHA_ASIGNACION,
                      String OTR_ID_USER_ASIGNO, String OTR_FECHA_CULMINACION, short OTR_OPERARIO,
                      String OTR_OBSERVACION_DEL_OPE, String OTR_ID_USER_RETENCION, String OTR_FECHA_RETENCION,
                      String OTR_MOTIVO_RETENCION, String OTR_ID_USER_LIBERACION, String OTR_FECHA_LIBERACION,
                      short OTR_OPERARIO_REALIZACION, String OTR_ESTADO, String OTR_ID_USER, String OTR_FECHA_UPDATE,
                      String OTR_ID_USER_ALTA, String OTR_FECHA_ALTA, Integer OTR_LOTE_REPLICACION) {
        this.OTR_SUCURSAL = OTR_SUCURSAL;
        this.OTR_CENTRO_ATENCION = OTR_CENTRO_ATENCION;
        this.OTR_NUMERO = OTR_NUMERO;
        this.OTR_EMPRESA = OTR_EMPRESA;
        this.OTR_CLIENTE = OTR_CLIENTE;
        this.OTR_SUMINISTRO = OTR_SUMINISTRO;
        this.OTR_PUNTO_MEDICION = OTR_PUNTO_MEDICION;
        this.OTR_FECHA_SOLICITUD = OTR_FECHA_SOLICITUD;
        this.OTR_TIPO_TRABAJO = OTR_TIPO_TRABAJO;
        this.OTR_MOTIVO_TRABAJO = OTR_MOTIVO_TRABAJO;
        this.OTR_FECHA_VENCIMIENTO = OTR_FECHA_VENCIMIENTO;
        this.OTR_OBSERVACIONES_AL_OPE = OTR_OBSERVACIONES_AL_OPE;
        this.OTR_ID_USER_GENERO = OTR_ID_USER_GENERO;
        this.OTR_FORMA_GENERACION = OTR_FORMA_GENERACION;
        this.OTR_ESTADO_OPERATIVO_ANTES_GENERAR = OTR_ESTADO_OPERATIVO_ANTES_GENERAR;
        this.OTR_FECHA_EST_OPERATIVO_ANTES_GENERAR = OTR_FECHA_EST_OPERATIVO_ANTES_GENERAR;
        this.OTR_ESTADO_OPERATIVO_LUEGO_GENERAR = OTR_ESTADO_OPERATIVO_LUEGO_GENERAR;
        this.OTR_EMPRESA_CONTRATISTA = OTR_EMPRESA_CONTRATISTA;
        this.OTR_FECHA_ASIGNACION = OTR_FECHA_ASIGNACION;
        this.OTR_ID_USER_ASIGNO = OTR_ID_USER_ASIGNO;
        this.OTR_FECHA_CULMINACION = OTR_FECHA_CULMINACION;
        this.OTR_OPERARIO = OTR_OPERARIO;
        this.OTR_OBSERVACION_DEL_OPE = OTR_OBSERVACION_DEL_OPE;
        this.OTR_ID_USER_RETENCION = OTR_ID_USER_RETENCION;
        this.OTR_FECHA_RETENCION = OTR_FECHA_RETENCION;
        this.OTR_MOTIVO_RETENCION = OTR_MOTIVO_RETENCION;
        this.OTR_ID_USER_LIBERACION = OTR_ID_USER_LIBERACION;
        this.OTR_FECHA_LIBERACION = OTR_FECHA_LIBERACION;
        this.OTR_OPERARIO_REALIZACION = OTR_OPERARIO_REALIZACION;
        this.OTR_ESTADO = OTR_ESTADO;
        this.OTR_ID_USER = OTR_ID_USER;
        this.OTR_FECHA_UPDATE = OTR_FECHA_UPDATE;
        this.OTR_ID_USER_ALTA = OTR_ID_USER_ALTA;
        this.OTR_FECHA_ALTA = OTR_FECHA_ALTA;
        this.OTR_LOTE_REPLICACION = OTR_LOTE_REPLICACION;
    }

    public Integer getSUCURSAL() {
        return OTR_SUCURSAL;
    }

    public void setSUCURSAL(Integer OTR_SUCURSAL) {
        this.OTR_SUCURSAL = OTR_SUCURSAL;
    }

    public Integer getCENTRO_ATENCION() {
        return OTR_CENTRO_ATENCION;
    }

    public void setCENTRO_ATENCION(Integer OTR_CENTRO_ATENCION) {
        this.OTR_CENTRO_ATENCION = OTR_CENTRO_ATENCION;
    }

    public Integer getNUMERO() {
        return OTR_NUMERO;
    }

    public void setNUMERO(Integer OTR_NUMERO) {
        this.OTR_NUMERO = OTR_NUMERO;
    }

    public short getEMPRESA() {
        return OTR_EMPRESA;
    }

    public void setEMPRESA(short OTR_EMPRESA) {
        this.OTR_EMPRESA = OTR_EMPRESA;
    }

    public Integer getCLIENTE() {
        return OTR_CLIENTE;
    }

    public void setCLIENTE(Integer OTR_CLIENTE) {
        this.OTR_CLIENTE = OTR_CLIENTE;
    }

    public short getSUMINISTRO() {
        return OTR_SUMINISTRO;
    }

    public void setSUMINISTRO(short OTR_SUMINISTRO) {
        this.OTR_SUMINISTRO = OTR_SUMINISTRO;
    }

    public short getPUNTO_MEDICION() {
        return OTR_PUNTO_MEDICION;
    }

    public void setPUNTO_MEDICION(short OTR_PUNTO_MEDICION) {
        this.OTR_PUNTO_MEDICION = OTR_PUNTO_MEDICION;
    }

    public String getFECHA_SOLICITUD() {
        return OTR_FECHA_SOLICITUD;
    }

    public void setFECHA_SOLICITUD(String OTR_FECHA_SOLICITUD) {
        this.OTR_FECHA_SOLICITUD = OTR_FECHA_SOLICITUD;
    }

    public Integer getTIPO_TRABAJO() {
        return OTR_TIPO_TRABAJO;
    }

    public void setTIPO_TRABAJO(Integer OTR_TIPO_TRABAJO) {
        this.OTR_TIPO_TRABAJO = OTR_TIPO_TRABAJO;
    }

    public Integer getMOTIVO_TRABAJO() {
        return OTR_MOTIVO_TRABAJO;
    }

    public void setMOTIVO_TRABAJO(Integer OTR_MOTIVO_TRABAJO) {
        this.OTR_MOTIVO_TRABAJO = OTR_MOTIVO_TRABAJO;
    }

    public String getFECHA_VENCIMIENTO() {
        return OTR_FECHA_VENCIMIENTO;
    }

    public void setFECHA_VENCIMIENTO(String OTR_FECHA_VENCIMIENTO) {
        this.OTR_FECHA_VENCIMIENTO = OTR_FECHA_VENCIMIENTO;
    }

    public String getOBSERVACIONES_AL_OPE() {
        return OTR_OBSERVACIONES_AL_OPE;
    }

    public void setOBSERVACIONES_AL_OPE(String OTR_OBSERVACIONES_AL_OPE) {
        this.OTR_OBSERVACIONES_AL_OPE = OTR_OBSERVACIONES_AL_OPE;
    }

    public String getID_USER_GENERO() {
        return OTR_ID_USER_GENERO;
    }

    public void setID_USER_GENERO(String OTR_ID_USER_GENERO) {
        this.OTR_ID_USER_GENERO = OTR_ID_USER_GENERO;
    }

    public String getFORMA_GENERACION() {
        return OTR_FORMA_GENERACION;
    }

    public void setFORMA_GENERACION(String OTR_FORMA_GENERACION) {
        this.OTR_FORMA_GENERACION = OTR_FORMA_GENERACION;
    }

    public byte getESTADO_OPERATIVO_ANTES_GENERAR() {
        return OTR_ESTADO_OPERATIVO_ANTES_GENERAR;
    }

    public void setESTADO_OPERATIVO_ANTES_GENERAR(byte OTR_ESTADO_OPERATIVO_ANTES_GENERAR) {
        this.OTR_ESTADO_OPERATIVO_ANTES_GENERAR = OTR_ESTADO_OPERATIVO_ANTES_GENERAR;
    }

    public String getFECHA_EST_OPERATIVO_ANTES_GENERAR() {
        return OTR_FECHA_EST_OPERATIVO_ANTES_GENERAR;
    }

    public void setFECHA_EST_OPERATIVO_ANTES_GENERAR(String OTR_FECHA_EST_OPERATIVO_ANTES_GENERAR) {
        this.OTR_FECHA_EST_OPERATIVO_ANTES_GENERAR = OTR_FECHA_EST_OPERATIVO_ANTES_GENERAR;
    }

    public byte getESTADO_OPERATIVO_LUEGO_GENERAR() {
        return OTR_ESTADO_OPERATIVO_LUEGO_GENERAR;
    }

    public void setESTADO_OPERATIVO_LUEGO_GENERAR(byte OTR_ESTADO_OPERATIVO_LUEGO_GENERAR) {
        this.OTR_ESTADO_OPERATIVO_LUEGO_GENERAR = OTR_ESTADO_OPERATIVO_LUEGO_GENERAR;
    }

    public short getEMPRESA_CONTRATISTA() {
        return OTR_EMPRESA_CONTRATISTA;
    }

    public void setEMPRESA_CONTRATISTA(short OTR_EMPRESA_CONTRATISTA) {
        this.OTR_EMPRESA_CONTRATISTA = OTR_EMPRESA_CONTRATISTA;
    }

    public String getFECHA_ASIGNACION() {
        return OTR_FECHA_ASIGNACION;
    }

    public void setFECHA_ASIGNACION(String OTR_FECHA_ASIGNACION) {
        this.OTR_FECHA_ASIGNACION = OTR_FECHA_ASIGNACION;
    }

    public String getID_USER_ASIGNO() {
        return OTR_ID_USER_ASIGNO;
    }

    public void setID_USER_ASIGNO(String OTR_ID_USER_ASIGNO) {
        this.OTR_ID_USER_ASIGNO = OTR_ID_USER_ASIGNO;
    }

    public String getFECHA_CULMINACION() {
        return OTR_FECHA_CULMINACION;
    }

    public void setFECHA_CULMINACION(String OTR_FECHA_CULMINACION) {
        this.OTR_FECHA_CULMINACION = OTR_FECHA_CULMINACION;
    }

    public short getOPERARIO() {
        return OTR_OPERARIO;
    }

    public void setOPERARIO(short OTR_OPERARIO) {
        this.OTR_OPERARIO = OTR_OPERARIO;
    }

    public String getOBSERVACION_DEL_OPE() {
        return OTR_OBSERVACION_DEL_OPE;
    }

    public void setOBSERVACION_DEL_OPE(String OTR_OBSERVACION_DEL_OPE) {
        this.OTR_OBSERVACION_DEL_OPE = OTR_OBSERVACION_DEL_OPE;
    }

    public String getID_USER_RETENCION() {
        return OTR_ID_USER_RETENCION;
    }

    public void setID_USER_RETENCION(String OTR_ID_USER_RETENCION) {
        this.OTR_ID_USER_RETENCION = OTR_ID_USER_RETENCION;
    }

    public String getFECHA_RETENCION() {
        return OTR_FECHA_RETENCION;
    }

    public void setFECHA_RETENCION(String OTR_FECHA_RETENCION) {
        this.OTR_FECHA_RETENCION = OTR_FECHA_RETENCION;
    }

    public String getMOTIVO_RETENCION() {
        return OTR_MOTIVO_RETENCION;
    }

    public void setMOTIVO_RETENCION(String OTR_MOTIVO_RETENCION) {
        this.OTR_MOTIVO_RETENCION = OTR_MOTIVO_RETENCION;
    }

    public String getID_USER_LIBERACION() {
        return OTR_ID_USER_LIBERACION;
    }

    public void setID_USER_LIBERACION(String OTR_ID_USER_LIBERACION) {
        this.OTR_ID_USER_LIBERACION = OTR_ID_USER_LIBERACION;
    }

    public String getFECHA_LIBERACION() {
        return OTR_FECHA_LIBERACION;
    }

    public void setFECHA_LIBERACION(String OTR_FECHA_LIBERACION) {
        this.OTR_FECHA_LIBERACION = OTR_FECHA_LIBERACION;
    }

    public short getOPERARIO_REALIZACION() {
        return OTR_OPERARIO_REALIZACION;
    }

    public void setOPERARIO_REALIZACION(short OTR_OPERARIO_REALIZACION) {
        this.OTR_OPERARIO_REALIZACION = OTR_OPERARIO_REALIZACION;
    }

    public String getESTADO() {
        return OTR_ESTADO;
    }

    public void setESTADO(String OTR_ESTADO) {
        this.OTR_ESTADO = OTR_ESTADO;
    }

    public String getID_USER() {
        return OTR_ID_USER;
    }

    public void setID_USER(String OTR_ID_USER) {
        this.OTR_ID_USER = OTR_ID_USER;
    }

    public String getFECHA_UPDATE() {
        return OTR_FECHA_UPDATE;
    }

    public void setFECHA_UPDATE(String OTR_FECHA_UPDATE) {
        this.OTR_FECHA_UPDATE = OTR_FECHA_UPDATE;
    }

    public String getID_USER_ALTA() {
        return OTR_ID_USER_ALTA;
    }

    public void setID_USER_ALTA(String OTR_ID_USER_ALTA) {
        this.OTR_ID_USER_ALTA = OTR_ID_USER_ALTA;
    }

    public String getFECHA_ALTA() {
        return OTR_FECHA_ALTA;
    }

    public void setFECHA_ALTA(String OTR_FECHA_ALTA) {
        this.OTR_FECHA_ALTA = OTR_FECHA_ALTA;
    }

    public Integer getLOTE_REPLICACION() {
        return OTR_LOTE_REPLICACION;
    }

    public void setLOTE_REPLICACION(Integer OTR_LOTE_REPLICACION) {
        this.OTR_LOTE_REPLICACION = OTR_LOTE_REPLICACION;
    }
}

