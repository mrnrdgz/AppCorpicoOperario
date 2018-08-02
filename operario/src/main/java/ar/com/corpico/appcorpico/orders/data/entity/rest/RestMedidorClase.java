package ar.com.corpico.appcorpico.orders.data.entity.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrador on 21/03/2018.
 */

public class RestMedidorClase {
    @SerializedName("MCL_ID")
    private byte MCL_ID;
    @SerializedName("MCL_DESCRIPCION")
    private String MCL_DESCRIPCION;
    @SerializedName("MCL_TIPO_EMPRESA")
    private byte MCL_TIPO_EMPRESA;
    @SerializedName("MCL_CAPACIDAD")
    private int MCL_CAPACIDAD;
    @SerializedName("MCL_ACTIVO")
    private String MCL_ACTIVO;
    @SerializedName("MCL_ID_USER")
    private String MCL_ID_USER;
    @SerializedName("MCL_FECHA_UPDATE")
    private String MCL_FECHA_UPDATE;
    @SerializedName("MCL_LOTE_REPLICACION")
    private int MCL_LOTE_REPLICACION;

    public RestMedidorClase(byte MCL_ID, String MCL_DESCRIPCION, byte MCL_TIPO_EMPRESA, int MCL_CAPACIDAD, String MCL_ACTIVO,
                            String MCL_ID_USER, String MCL_FECHA_UPDATE, int MCL_LOTE_REPLICACION) {
        this.MCL_ID = MCL_ID;
        this.MCL_DESCRIPCION = MCL_DESCRIPCION;
        this.MCL_TIPO_EMPRESA = MCL_TIPO_EMPRESA;
        this.MCL_CAPACIDAD = MCL_CAPACIDAD;
        this.MCL_ACTIVO = MCL_ACTIVO;
        this.MCL_ID_USER = MCL_ID_USER;
        this.MCL_FECHA_UPDATE = MCL_FECHA_UPDATE;
        this.MCL_LOTE_REPLICACION = MCL_LOTE_REPLICACION;
    }

    public byte getMCL_ID() {
        return MCL_ID;
    }

    public void setMCL_ID(byte MCL_ID) {
        this.MCL_ID = MCL_ID;
    }

    public String getMCL_DESCRIPCION() {
        return MCL_DESCRIPCION;
    }

    public void setMCL_DESCRIPCION(String MCL_DESCRIPCION) {
        this.MCL_DESCRIPCION = MCL_DESCRIPCION;
    }

    public byte getMCL_TIPO_EMPRESA() {
        return MCL_TIPO_EMPRESA;
    }

    public void setMCL_TIPO_EMPRESA(byte MCL_TIPO_EMPRESA) {
        this.MCL_TIPO_EMPRESA = MCL_TIPO_EMPRESA;
    }

    public int getMCL_CAPACIDAD() {
        return MCL_CAPACIDAD;
    }

    public void setMCL_CAPACIDAD(int MCL_CAPACIDAD) {
        this.MCL_CAPACIDAD = MCL_CAPACIDAD;
    }

    public String getMCL_ACTIVO() {
        return MCL_ACTIVO;
    }

    public void setMCL_ACTIVO(String MCL_ACTIVO) {
        this.MCL_ACTIVO = MCL_ACTIVO;
    }

    public String getMCL_ID_USER() {
        return MCL_ID_USER;
    }

    public void setMCL_ID_USER(String MCL_ID_USER) {
        this.MCL_ID_USER = MCL_ID_USER;
    }

    public String getMCL_FECHA_UPDATE() {
        return MCL_FECHA_UPDATE;
    }

    public void setMCL_FECHA_UPDATE(String MCL_FECHA_UPDATE) {
        this.MCL_FECHA_UPDATE = MCL_FECHA_UPDATE;
    }

    public int getMCL_LOTE_REPLICACION() {
        return MCL_LOTE_REPLICACION;
    }

    public void setMCL_LOTE_REPLICACION(int MCL_LOTE_REPLICACION) {
        this.MCL_LOTE_REPLICACION = MCL_LOTE_REPLICACION;
    }
}
