package ar.com.corpico.appcorpico.orders.data.entity.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrador on 21/03/2018.
 */

public class RestMotivoTrabajoOperativo {
    @SerializedName("MTR_TIPO_TRABAJO")
    private short MTR_TIPO_TRABAJO;
    @SerializedName("MTR_ID")
    private short MTR_ID;
    @SerializedName("MTR_DESCRIPCION")
    private String MTR_DESCRIPCION;
    @SerializedName("MTR_ABREVIATURA")
    private String MTR_ABREVIATURA;
    @SerializedName("MTR_DIAS_RESOLUCION")
    private short MTR_DIAS_RESOLUCION;
    @SerializedName("MTR_ALTERA_ESTADO_LUEGO_GENERAR")
    private String MTR_ALTERA_ESTADO_LUEGO_GENERAR;
    @SerializedName("MTR_ESTADO_LUEGO_GENERAR")
    private byte MTR_ESTADO_LUEGO_GENERAR;
    @SerializedName("MTR_SOLICITA_COLOCACION")
    private String MTR_SOLICITA_COLOCACION;
    @SerializedName("MTR_SOLICITA_RETIRO")
    private String MTR_SOLICITA_RETIRO;
    @SerializedName("MTR_SOLICITA_INFORMATIVO")
    private String MTR_SOLICITA_INFORMATIVO;
    @SerializedName("MTR_SOLICITA_PRECINTO_HAB")
    private String MTR_SOLICITA_PRECINTO_HAB;
    @SerializedName("MTR_SOLICITA_PRECINTO_MED")
    private String MTR_SOLICITA_PRECINTO_MED;
    @SerializedName("MTR_SOLICITA_INSTALACION")
    private String MTR_SOLICITA_INSTALACION;
    @SerializedName("MTR_SOLICITA_MATERIALES")
    private String MTR_SOLICITA_MATERIALES;
    @SerializedName("MTR_PERMITE_ELIMINAR")
    private String MTR_PERMITE_ELIMINAR;
    @SerializedName("MTR_PROCESA_TOMAESTADO")
    private String MTR_PROCESA_TOMAESTADO;
    @SerializedName("MTR_ACTIVO")
    private String MTR_ACTIVO;
    @SerializedName("MTR_ID_USER")
    private String MTR_ID_USER;
    @SerializedName("MTR_FECHA_UPDATE")
    private String MTR_FECHA_UPDATE;
    @SerializedName("MTR_LOTE_REPLICACION")
    private int MTR_LOTE_REPLICACION;

    public RestMotivoTrabajoOperativo(short MTR_TIPO_TRABAJO, short MTR_ID, String MTR_DESCRIPCION, String MTR_ABREVIATURA,
                                      short MTR_DIAS_RESOLUCION, String MTR_ALTERA_ESTADO_LUEGO_GENERAR,
                                      byte MTR_ESTADO_LUEGO_GENERAR, String MTR_SOLICITA_COLOCACION, String MTR_SOLICITA_RETIRO,
                                      String MTR_SOLICITA_INFORMATIVO, String MTR_SOLICITA_PRECINTO_HAB,
                                      String MTR_SOLICITA_PRECINTO_MED, String MTR_SOLICITA_INSTALACION,
                                      String MTR_SOLICITA_MATERIALES, String MTR_PERMITE_ELIMINAR, String MTR_PROCESA_TOMAESTADO,
                                      String MTR_ACTIVO, String MTR_ID_USER, String MTR_FECHA_UPDATE,
                                      int MTR_LOTE_REPLICACION) {
        this.MTR_TIPO_TRABAJO = MTR_TIPO_TRABAJO;
        this.MTR_ID = MTR_ID;
        this.MTR_DESCRIPCION = MTR_DESCRIPCION;
        this.MTR_ABREVIATURA = MTR_ABREVIATURA;
        this.MTR_DIAS_RESOLUCION = MTR_DIAS_RESOLUCION;
        this.MTR_ALTERA_ESTADO_LUEGO_GENERAR = MTR_ALTERA_ESTADO_LUEGO_GENERAR;
        this.MTR_ESTADO_LUEGO_GENERAR = MTR_ESTADO_LUEGO_GENERAR;
        this.MTR_SOLICITA_COLOCACION = MTR_SOLICITA_COLOCACION;
        this.MTR_SOLICITA_RETIRO = MTR_SOLICITA_RETIRO;
        this.MTR_SOLICITA_INFORMATIVO = MTR_SOLICITA_INFORMATIVO;
        this.MTR_SOLICITA_PRECINTO_HAB = MTR_SOLICITA_PRECINTO_HAB;
        this.MTR_SOLICITA_PRECINTO_MED = MTR_SOLICITA_PRECINTO_MED;
        this.MTR_SOLICITA_INSTALACION = MTR_SOLICITA_INSTALACION;
        this.MTR_SOLICITA_MATERIALES = MTR_SOLICITA_MATERIALES;
        this.MTR_PERMITE_ELIMINAR = MTR_PERMITE_ELIMINAR;
        this.MTR_PROCESA_TOMAESTADO = MTR_PROCESA_TOMAESTADO;
        this.MTR_ACTIVO = MTR_ACTIVO;
        this.MTR_ID_USER = MTR_ID_USER;
        this.MTR_FECHA_UPDATE = MTR_FECHA_UPDATE;
        this.MTR_LOTE_REPLICACION = MTR_LOTE_REPLICACION;
    }

    public short getMTR_TIPO_TRABAJO() {
        return MTR_TIPO_TRABAJO;
    }

    public void setMTR_TIPO_TRABAJO(short MTR_TIPO_TRABAJO) {
        this.MTR_TIPO_TRABAJO = MTR_TIPO_TRABAJO;
    }

    public short getMTR_ID() {
        return MTR_ID;
    }

    public void setMTR_ID(short MTR_ID) {
        this.MTR_ID = MTR_ID;
    }

    public String getMTR_DESCRIPCION() {
        return MTR_DESCRIPCION;
    }

    public void setMTR_DESCRIPCION(String MTR_DESCRIPCION) {
        this.MTR_DESCRIPCION = MTR_DESCRIPCION;
    }

    public String getMTR_ABREVIATURA() {
        return MTR_ABREVIATURA;
    }

    public void setMTR_ABREVIATURA(String MTR_ABREVIATURA) {
        this.MTR_ABREVIATURA = MTR_ABREVIATURA;
    }

    public short getMTR_DIAS_RESOLUCION() {
        return MTR_DIAS_RESOLUCION;
    }

    public void setMTR_DIAS_RESOLUCION(short MTR_DIAS_RESOLUCION) {
        this.MTR_DIAS_RESOLUCION = MTR_DIAS_RESOLUCION;
    }

    public String getMTR_ALTERA_ESTADO_LUEGO_GENERAR() {
        return MTR_ALTERA_ESTADO_LUEGO_GENERAR;
    }

    public void setMTR_ALTERA_ESTADO_LUEGO_GENERAR(String MTR_ALTERA_ESTADO_LUEGO_GENERAR) {
        this.MTR_ALTERA_ESTADO_LUEGO_GENERAR = MTR_ALTERA_ESTADO_LUEGO_GENERAR;
    }

    public byte getMTR_ESTADO_LUEGO_GENERAR() {
        return MTR_ESTADO_LUEGO_GENERAR;
    }

    public void setMTR_ESTADO_LUEGO_GENERAR(byte MTR_ESTADO_LUEGO_GENERAR) {
        this.MTR_ESTADO_LUEGO_GENERAR = MTR_ESTADO_LUEGO_GENERAR;
    }

    public String getMTR_SOLICITA_COLOCACION() {
        return MTR_SOLICITA_COLOCACION;
    }

    public void setMTR_SOLICITA_COLOCACION(String MTR_SOLICITA_COLOCACION) {
        this.MTR_SOLICITA_COLOCACION = MTR_SOLICITA_COLOCACION;
    }

    public String getMTR_SOLICITA_RETIRO() {
        return MTR_SOLICITA_RETIRO;
    }

    public void setMTR_SOLICITA_RETIRO(String MTR_SOLICITA_RETIRO) {
        this.MTR_SOLICITA_RETIRO = MTR_SOLICITA_RETIRO;
    }

    public String getMTR_SOLICITA_INFORMATIVO() {
        return MTR_SOLICITA_INFORMATIVO;
    }

    public void setMTR_SOLICITA_INFORMATIVO(String MTR_SOLICITA_INFORMATIVO) {
        this.MTR_SOLICITA_INFORMATIVO = MTR_SOLICITA_INFORMATIVO;
    }

    public String getMTR_SOLICITA_PRECINTO_HAB() {
        return MTR_SOLICITA_PRECINTO_HAB;
    }

    public void setMTR_SOLICITA_PRECINTO_HAB(String MTR_SOLICITA_PRECINTO_HAB) {
        this.MTR_SOLICITA_PRECINTO_HAB = MTR_SOLICITA_PRECINTO_HAB;
    }

    public String getMTR_SOLICITA_PRECINTO_MED() {
        return MTR_SOLICITA_PRECINTO_MED;
    }

    public void setMTR_SOLICITA_PRECINTO_MED(String MTR_SOLICITA_PRECINTO_MED) {
        this.MTR_SOLICITA_PRECINTO_MED = MTR_SOLICITA_PRECINTO_MED;
    }

    public String getMTR_SOLICITA_INSTALACION() {
        return MTR_SOLICITA_INSTALACION;
    }

    public void setMTR_SOLICITA_INSTALACION(String MTR_SOLICITA_INSTALACION) {
        this.MTR_SOLICITA_INSTALACION = MTR_SOLICITA_INSTALACION;
    }

    public String getMTR_SOLICITA_MATERIALES() {
        return MTR_SOLICITA_MATERIALES;
    }

    public void setMTR_SOLICITA_MATERIALES(String MTR_SOLICITA_MATERIALES) {
        this.MTR_SOLICITA_MATERIALES = MTR_SOLICITA_MATERIALES;
    }

    public String getMTR_PERMITE_ELIMINAR() {
        return MTR_PERMITE_ELIMINAR;
    }

    public void setMTR_PERMITE_ELIMINAR(String MTR_PERMITE_ELIMINAR) {
        this.MTR_PERMITE_ELIMINAR = MTR_PERMITE_ELIMINAR;
    }

    public String getMTR_PROCESA_TOMAESTADO() {
        return MTR_PROCESA_TOMAESTADO;
    }

    public void setMTR_PROCESA_TOMAESTADO(String MTR_PROCESA_TOMAESTADO) {
        this.MTR_PROCESA_TOMAESTADO = MTR_PROCESA_TOMAESTADO;
    }

    public String getMTR_ACTIVO() {
        return MTR_ACTIVO;
    }

    public void setMTR_ACTIVO(String MTR_ACTIVO) {
        this.MTR_ACTIVO = MTR_ACTIVO;
    }

    public String getMTR_ID_USER() {
        return MTR_ID_USER;
    }

    public void setMTR_ID_USER(String MTR_ID_USER) {
        this.MTR_ID_USER = MTR_ID_USER;
    }

    public String getMTR_FECHA_UPDATE() {
        return MTR_FECHA_UPDATE;
    }

    public void setMTR_FECHA_UPDATE(String MTR_FECHA_UPDATE) {
        this.MTR_FECHA_UPDATE = MTR_FECHA_UPDATE;
    }

    public int getMTR_LOTE_REPLICACION() {
        return MTR_LOTE_REPLICACION;
    }

    public void setMTR_LOTE_REPLICACION(int MTR_LOTE_REPLICACION) {
        this.MTR_LOTE_REPLICACION = MTR_LOTE_REPLICACION;
    }
}
