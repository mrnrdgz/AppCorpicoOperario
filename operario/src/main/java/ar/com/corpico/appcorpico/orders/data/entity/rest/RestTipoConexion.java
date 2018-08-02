package ar.com.corpico.appcorpico.orders.data.entity.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrador on 21/03/2018.
 */

public class RestTipoConexion {
    @SerializedName("TCO_ID")
    private short TCO_ID;
    @SerializedName("TCO_DESCRIPCION")
    private String TCO_DESCRIPCION;
    @SerializedName("TCO_TIPO_EMPRESA")
    private byte TCO_TIPO_EMPRESA;
    @SerializedName("TCO_FACTOR")
    private double TCO_FACTOR;
    @SerializedName("TCO_ACTIVO")
    private String TCO_ACTIVO;
    @SerializedName("TCO_ID_USER")
    private String TCO_ID_USER;
    @SerializedName("TCO_FECHA_UPDATE")
    private String TCO_FECHA_UPDATE;
    @SerializedName("TCO_LOTE_REPLICACION")
    private int TCO_LOTE_REPLICACION;

    public RestTipoConexion(short TCO_ID, String TCO_DESCRIPCION, byte TCO_TIPO_EMPRESA, double TCO_FACTOR, String TCO_ACTIVO,
                            String TCO_ID_USER, String TCO_FECHA_UPDATE, int TCO_LOTE_REPLICACION) {
        this.TCO_ID = TCO_ID;
        this.TCO_DESCRIPCION = TCO_DESCRIPCION;
        this.TCO_TIPO_EMPRESA = TCO_TIPO_EMPRESA;
        this.TCO_FACTOR = TCO_FACTOR;
        this.TCO_ACTIVO = TCO_ACTIVO;
        this.TCO_ID_USER = TCO_ID_USER;
        this.TCO_FECHA_UPDATE = TCO_FECHA_UPDATE;
        this.TCO_LOTE_REPLICACION = TCO_LOTE_REPLICACION;
    }

    public short getTCO_ID() {
        return TCO_ID;
    }

    public void setTCO_ID(short TCO_ID) {
        this.TCO_ID = TCO_ID;
    }

    public String getTCO_DESCRIPCION() {
        return TCO_DESCRIPCION;
    }

    public void setTCO_DESCRIPCION(String TCO_DESCRIPCION) {
        this.TCO_DESCRIPCION = TCO_DESCRIPCION;
    }

    public byte getTCO_TIPO_EMPRESA() {
        return TCO_TIPO_EMPRESA;
    }

    public void setTCO_TIPO_EMPRESA(byte TCO_TIPO_EMPRESA) {
        this.TCO_TIPO_EMPRESA = TCO_TIPO_EMPRESA;
    }

    public double getTCO_FACTOR() {
        return TCO_FACTOR;
    }

    public void setTCO_FACTOR(double TCO_FACTOR) {
        this.TCO_FACTOR = TCO_FACTOR;
    }

    public String getTCO_ACTIVO() {
        return TCO_ACTIVO;
    }

    public void setTCO_ACTIVO(String TCO_ACTIVO) {
        this.TCO_ACTIVO = TCO_ACTIVO;
    }

    public String getTCO_ID_USER() {
        return TCO_ID_USER;
    }

    public void setTCO_ID_USER(String TCO_ID_USER) {
        this.TCO_ID_USER = TCO_ID_USER;
    }

    public String getTCO_FECHA_UPDATE() {
        return TCO_FECHA_UPDATE;
    }

    public void setTCO_FECHA_UPDATE(String TCO_FECHA_UPDATE) {
        this.TCO_FECHA_UPDATE = TCO_FECHA_UPDATE;
    }

    public int getTCO_LOTE_REPLICACION() {
        return TCO_LOTE_REPLICACION;
    }

    public void setTCO_LOTE_REPLICACION(int TCO_LOTE_REPLICACION) {
        this.TCO_LOTE_REPLICACION = TCO_LOTE_REPLICACION;
    }
}
