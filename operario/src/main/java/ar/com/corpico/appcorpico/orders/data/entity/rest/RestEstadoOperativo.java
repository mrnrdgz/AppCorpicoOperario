package ar.com.corpico.appcorpico.orders.data.entity.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrador on 21/03/2018.
 */

public class RestEstadoOperativo {
    @SerializedName("EOP_ID")
    private byte EOP_ID;
    @SerializedName("EOP_DESCRIPCION")
    private String EOP_DESCRIPCION;
    @SerializedName("EOP_ABREVIATURA")
    private String EOP_ABREVIATURA;
    @SerializedName("EOP_TIPO_EMPRESA")
    private byte EOP_TIPO_EMPRESA;
    @SerializedName("EOP_SALE_A_LEER")
    private String EOP_SALE_A_LEER;
    @SerializedName("EOP_ES_FACTURABLE")
    private String EOP_ES_FACTURABLE;
    @SerializedName("EOP_CAMB_TITULARIDAD")
    private String EOP_CAMB_TITULARIDAD;
    @SerializedName("EOP_CAMB_TARIFA")
    private String EOP_CAMB_TARIFA;
    @SerializedName("EOP_CAMB_DIR_POSTAL")
    private String EOP_CAMB_DIR_POSTAL;
    @SerializedName("EOP_CAMB_DAT_IMPOSITIVOS")
    private String EOP_CAMB_DAT_IMPOSITIVOS;
    @SerializedName("EOP_BAJA_DEB_AUTOMATIVO")
    private String EOP_BAJA_DEB_AUTOMATIVO;
    @SerializedName("EOP_RECLAMOS")
    private String EOP_RECLAMOS;
    @SerializedName("EOP_ORDEN_TRABAJO")
    private String EOP_ORDEN_TRABAJO;
    @SerializedName("EOP_BAJA_SUMINISTRO")
    private String EOP_BAJA_SUMINISTRO;
    @SerializedName("EOP_COBRANZA")
    private String EOP_COBRANZA;
    @SerializedName("EOP_INCLUYE_AVISO_DEUDA")
    private String EOP_INCLUYE_AVISO_DEUDA;
    @SerializedName("EOP_POSEE_MEDIDOR")
    private String EOP_POSEE_MEDIDOR;
    @SerializedName("EOP_ACCION_SI_NO_POSEE_MED")
    private String EOP_ACCION_SI_NO_POSEE_MED;
    @SerializedName("EOP_PERMITE_RE_SOLICITAR_SERVICIO")
    private String EOP_PERMITE_RE_SOLICITAR_SERVICIO;
    @SerializedName("EOP_COLOR")
    private String EOP_COLOR;
    @SerializedName("EOP_ESTADO_CONEXION")
    private String EOP_ESTADO_CONEXION;
    @SerializedName("EOP_CLIENTE")
    private String EOP_CLIENTE;
    @SerializedName("EOP_ACTIVO")
    private String EOP_ACTIVO;
    @SerializedName("EOP_ID_USER")
    private String EOP_ID_USER;
    @SerializedName("EOP_FECHA_UPDATE")
    private String EOP_FECHA_UPDATE;
    @SerializedName("EOP_LOTE_REPLICACION")
    private int EOP_LOTE_REPLICACION;

    public RestEstadoOperativo(byte EOP_ID, String EOP_DESCRIPCION, String EOP_ABREVIATURA, byte EOP_TIPO_EMPRESA,
                               String EOP_SALE_A_LEER, String EOP_ES_FACTURABLE, String EOP_CAMB_TITULARIDAD,
                               String EOP_CAMB_TARIFA, String EOP_CAMB_DIR_POSTAL, String EOP_CAMB_DAT_IMPOSITIVOS,
                               String EOP_BAJA_DEB_AUTOMATIVO, String EOP_RECLAMOS, String EOP_ORDEN_TRABAJO,
                               String EOP_BAJA_SUMINISTRO, String EOP_COBRANZA, String EOP_INCLUYE_AVISO_DEUDA,
                               String EOP_POSEE_MEDIDOR, String EOP_ACCION_SI_NO_POSEE_MED,
                               String EOP_PERMITE_RE_SOLICITAR_SERVICIO, String EOP_COLOR, String EOP_ESTADO_CONEXION,
                               String EOP_CLIENTE, String EOP_ACTIVO,
                               String EOP_ID_USER, String EOP_FECHA_UPDATE, int EOP_LOTE_REPLICACION) {
        this.EOP_ID = EOP_ID;
        this.EOP_DESCRIPCION = EOP_DESCRIPCION;
        this.EOP_ABREVIATURA = EOP_ABREVIATURA;
        this.EOP_TIPO_EMPRESA = EOP_TIPO_EMPRESA;
        this.EOP_SALE_A_LEER = EOP_SALE_A_LEER;
        this.EOP_ES_FACTURABLE = EOP_ES_FACTURABLE;
        this.EOP_CAMB_TITULARIDAD = EOP_CAMB_TITULARIDAD;
        this.EOP_CAMB_TARIFA = EOP_CAMB_TARIFA;
        this.EOP_CAMB_DIR_POSTAL = EOP_CAMB_DIR_POSTAL;
        this.EOP_CAMB_DAT_IMPOSITIVOS = EOP_CAMB_DAT_IMPOSITIVOS;
        this.EOP_BAJA_DEB_AUTOMATIVO = EOP_BAJA_DEB_AUTOMATIVO;
        this.EOP_RECLAMOS = EOP_RECLAMOS;
        this.EOP_ORDEN_TRABAJO = EOP_ORDEN_TRABAJO;
        this.EOP_BAJA_SUMINISTRO = EOP_BAJA_SUMINISTRO;
        this.EOP_COBRANZA = EOP_COBRANZA;
        this.EOP_INCLUYE_AVISO_DEUDA = EOP_INCLUYE_AVISO_DEUDA;
        this.EOP_POSEE_MEDIDOR = EOP_POSEE_MEDIDOR;
        this.EOP_ACCION_SI_NO_POSEE_MED = EOP_ACCION_SI_NO_POSEE_MED;
        this.EOP_PERMITE_RE_SOLICITAR_SERVICIO = EOP_PERMITE_RE_SOLICITAR_SERVICIO;
        this.EOP_COLOR = EOP_COLOR;
        this.EOP_ESTADO_CONEXION = EOP_ESTADO_CONEXION;
        this.EOP_CLIENTE = EOP_CLIENTE;
        this.EOP_ACTIVO = EOP_ACTIVO;
        this.EOP_ID_USER = EOP_ID_USER;
        this.EOP_FECHA_UPDATE = EOP_FECHA_UPDATE;
        this.EOP_LOTE_REPLICACION = EOP_LOTE_REPLICACION;
    }

    public byte getEOP_ID() {
        return EOP_ID;
    }

    public void setEOP_ID(byte EOP_ID) {
        this.EOP_ID = EOP_ID;
    }

    public String getEOP_DESCRIPCION() {
        return EOP_DESCRIPCION;
    }

    public void setEOP_DESCRIPCION(String EOP_DESCRIPCION) {
        this.EOP_DESCRIPCION = EOP_DESCRIPCION;
    }

    public String getEOP_ABREVIATURA() {
        return EOP_ABREVIATURA;
    }

    public void setEOP_ABREVIATURA(String EOP_ABREVIATURA) {
        this.EOP_ABREVIATURA = EOP_ABREVIATURA;
    }

    public byte getEOP_TIPO_EMPRESA() {
        return EOP_TIPO_EMPRESA;
    }

    public void setEOP_TIPO_EMPRESA(byte EOP_TIPO_EMPRESA) {
        this.EOP_TIPO_EMPRESA = EOP_TIPO_EMPRESA;
    }

    public String getEOP_SALE_A_LEER() {
        return EOP_SALE_A_LEER;
    }

    public void setEOP_SALE_A_LEER(String EOP_SALE_A_LEER) {
        this.EOP_SALE_A_LEER = EOP_SALE_A_LEER;
    }

    public String getEOP_ES_FACTURABLE() {
        return EOP_ES_FACTURABLE;
    }

    public void setEOP_ES_FACTURABLE(String EOP_ES_FACTURABLE) {
        this.EOP_ES_FACTURABLE = EOP_ES_FACTURABLE;
    }

    public String getEOP_CAMB_TITULARIDAD() {
        return EOP_CAMB_TITULARIDAD;
    }

    public void setEOP_CAMB_TITULARIDAD(String EOP_CAMB_TITULARIDAD) {
        this.EOP_CAMB_TITULARIDAD = EOP_CAMB_TITULARIDAD;
    }

    public String getEOP_CAMB_TARIFA() {
        return EOP_CAMB_TARIFA;
    }

    public void setEOP_CAMB_TARIFA(String EOP_CAMB_TARIFA) {
        this.EOP_CAMB_TARIFA = EOP_CAMB_TARIFA;
    }

    public String getEOP_CAMB_DIR_POSTAL() {
        return EOP_CAMB_DIR_POSTAL;
    }

    public void setEOP_CAMB_DIR_POSTAL(String EOP_CAMB_DIR_POSTAL) {
        this.EOP_CAMB_DIR_POSTAL = EOP_CAMB_DIR_POSTAL;
    }

    public String getEOP_CAMB_DAT_IMPOSITIVOS() {
        return EOP_CAMB_DAT_IMPOSITIVOS;
    }

    public void setEOP_CAMB_DAT_IMPOSITIVOS(String EOP_CAMB_DAT_IMPOSITIVOS) {
        this.EOP_CAMB_DAT_IMPOSITIVOS = EOP_CAMB_DAT_IMPOSITIVOS;
    }

    public String getEOP_BAJA_DEB_AUTOMATIVO() {
        return EOP_BAJA_DEB_AUTOMATIVO;
    }

    public void setEOP_BAJA_DEB_AUTOMATIVO(String EOP_BAJA_DEB_AUTOMATIVO) {
        this.EOP_BAJA_DEB_AUTOMATIVO = EOP_BAJA_DEB_AUTOMATIVO;
    }

    public String getEOP_RECLAMOS() {
        return EOP_RECLAMOS;
    }

    public void setEOP_RECLAMOS(String EOP_RECLAMOS) {
        this.EOP_RECLAMOS = EOP_RECLAMOS;
    }

    public String getEOP_ORDEN_TRABAJO() {
        return EOP_ORDEN_TRABAJO;
    }

    public void setEOP_ORDEN_TRABAJO(String EOP_ORDEN_TRABAJO) {
        this.EOP_ORDEN_TRABAJO = EOP_ORDEN_TRABAJO;
    }

    public String getEOP_BAJA_SUMINISTRO() {
        return EOP_BAJA_SUMINISTRO;
    }

    public void setEOP_BAJA_SUMINISTRO(String EOP_BAJA_SUMINISTRO) {
        this.EOP_BAJA_SUMINISTRO = EOP_BAJA_SUMINISTRO;
    }

    public String getEOP_COBRANZA() {
        return EOP_COBRANZA;
    }

    public void setEOP_COBRANZA(String EOP_COBRANZA) {
        this.EOP_COBRANZA = EOP_COBRANZA;
    }

    public String getEOP_INCLUYE_AVISO_DEUDA() {
        return EOP_INCLUYE_AVISO_DEUDA;
    }

    public void setEOP_INCLUYE_AVISO_DEUDA(String EOP_INCLUYE_AVISO_DEUDA) {
        this.EOP_INCLUYE_AVISO_DEUDA = EOP_INCLUYE_AVISO_DEUDA;
    }

    public String getEOP_POSEE_MEDIDOR() {
        return EOP_POSEE_MEDIDOR;
    }

    public void setEOP_POSEE_MEDIDOR(String EOP_POSEE_MEDIDOR) {
        this.EOP_POSEE_MEDIDOR = EOP_POSEE_MEDIDOR;
    }

    public String getEOP_ACCION_SI_NO_POSEE_MED() {
        return EOP_ACCION_SI_NO_POSEE_MED;
    }

    public void setEOP_ACCION_SI_NO_POSEE_MED(String EOP_ACCION_SI_NO_POSEE_MED) {
        this.EOP_ACCION_SI_NO_POSEE_MED = EOP_ACCION_SI_NO_POSEE_MED;
    }

    public String getEOP_PERMITE_RE_SOLICITAR_SERVICIO() {
        return EOP_PERMITE_RE_SOLICITAR_SERVICIO;
    }

    public void setEOP_PERMITE_RE_SOLICITAR_SERVICIO(String EOP_PERMITE_RE_SOLICITAR_SERVICIO) {
        this.EOP_PERMITE_RE_SOLICITAR_SERVICIO = EOP_PERMITE_RE_SOLICITAR_SERVICIO;
    }

    public String getEOP_COLOR() {
        return EOP_COLOR;
    }

    public void setEOP_COLOR(String EOP_COLOR) {
        this.EOP_COLOR = EOP_COLOR;
    }

    public String getEOP_ESTADO_CONEXION() {
        return EOP_ESTADO_CONEXION;
    }

    public void setEOP_ESTADO_CONEXION(String EOP_ESTADO_CONEXION) {
        this.EOP_ESTADO_CONEXION = EOP_ESTADO_CONEXION;
    }

    public String getEOP_CLIENTE() {
        return EOP_CLIENTE;
    }

    public void setEOP_CLIENTE(String EOP_CLIENTE) {
        this.EOP_CLIENTE = EOP_CLIENTE;
    }

    public String getEOP_ACTIVO() {
        return EOP_ACTIVO;
    }

    public void setEOP_ACTIVO(String EOP_ACTIVO) {
        this.EOP_ACTIVO = EOP_ACTIVO;
    }

    public String getEOP_ID_USER() {
        return EOP_ID_USER;
    }

    public void setEOP_ID_USER(String EOP_ID_USER) {
        this.EOP_ID_USER = EOP_ID_USER;
    }

    public String getEOP_FECHA_UPDATE() {
        return EOP_FECHA_UPDATE;
    }

    public void setEOP_FECHA_UPDATE(String EOP_FECHA_UPDATE) {
        this.EOP_FECHA_UPDATE = EOP_FECHA_UPDATE;
    }

    public int getEOP_LOTE_REPLICACION() {
        return EOP_LOTE_REPLICACION;
    }

    public void setEOP_LOTE_REPLICACION(int EOP_LOTE_REPLICACION) {
        this.EOP_LOTE_REPLICACION = EOP_LOTE_REPLICACION;
    }
}
