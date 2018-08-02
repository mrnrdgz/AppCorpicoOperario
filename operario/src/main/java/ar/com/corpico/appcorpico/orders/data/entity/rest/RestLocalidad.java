package ar.com.corpico.appcorpico.orders.data.entity.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrador on 21/03/2018.
 */

public class RestLocalidad {
    @SerializedName("LOC_ID")
    private short LOC_ID;
    @SerializedName("LOC_DESCRIPCION")
    private String LOC_DESCRIPCION;
    @SerializedName("LOC_MUNICIPIO")
    private short LOC_MUNICIPIO;
    @SerializedName("LOC_INFORMA_SUCURSAL")
    private String LOC_INFORMA_SUCURSAL;
    @SerializedName("LOC_SUCURSAL")
    private short LOC_SUCURSAL;
    @SerializedName("LOC_ZONA_TARIFARIA")
    private byte LOC_ZONA_TARIFARIA;
    @SerializedName("LOC_ACTIVO")
    private String LOC_ACTIVO;
    @SerializedName("LOC_ID_USER")
    private String LOC_ID_USER;
    @SerializedName("LOC_FECHA_UPDATE")
    private String LOC_FECHA_UPDATE;
    @SerializedName("LOC_LOTE_REPLICACION")
    private int LOC_LOTE_REPLICACION;

    public RestLocalidad(short LOC_ID, String LOC_DESCRIPCION, short LOC_MUNICIPIO, String LOC_INFORMA_SUCURSAL,
                         short LOC_SUCURSAL, byte LOC_ZONA_TARIFARIA, String LOC_ACTIVO,
                         String LOC_ID_USER, String LOC_FECHA_UPDATE, int LOC_LOTE_REPLICACION) {
        this.LOC_ID = LOC_ID;
        this.LOC_DESCRIPCION = LOC_DESCRIPCION;
        this.LOC_MUNICIPIO = LOC_MUNICIPIO;
        this.LOC_INFORMA_SUCURSAL = LOC_INFORMA_SUCURSAL;
        this.LOC_SUCURSAL = LOC_SUCURSAL;
        this.LOC_ZONA_TARIFARIA = LOC_ZONA_TARIFARIA;
        this.LOC_ACTIVO = LOC_ACTIVO;
        this.LOC_ID_USER = LOC_ID_USER;
        this.LOC_FECHA_UPDATE = LOC_FECHA_UPDATE;
        this.LOC_LOTE_REPLICACION = LOC_LOTE_REPLICACION;
    }

    public short getLOC_ID() {
        return LOC_ID;
    }

    public void setLOC_ID(short LOC_ID) {
        this.LOC_ID = LOC_ID;
    }

    public String getLOC_DESCRIPCION() {
        return LOC_DESCRIPCION;
    }

    public void setLOC_DESCRIPCION(String LOC_DESCRIPCION) {
        this.LOC_DESCRIPCION = LOC_DESCRIPCION;
    }

    public short getLOC_MUNICIPIO() {
        return LOC_MUNICIPIO;
    }

    public void setLOC_MUNICIPIO(short LOC_MUNICIPIO) {
        this.LOC_MUNICIPIO = LOC_MUNICIPIO;
    }

    public String getLOC_INFORMA_SUCURSAL() {
        return LOC_INFORMA_SUCURSAL;
    }

    public void setLOC_INFORMA_SUCURSAL(String LOC_INFORMA_SUCURSAL) {
        this.LOC_INFORMA_SUCURSAL = LOC_INFORMA_SUCURSAL;
    }

    public short getLOC_SUCURSAL() {
        return LOC_SUCURSAL;
    }

    public void setLOC_SUCURSAL(short LOC_SUCURSAL) {
        this.LOC_SUCURSAL = LOC_SUCURSAL;
    }

    public byte getLOC_ZONA_TARIFARIA() {
        return LOC_ZONA_TARIFARIA;
    }

    public void setLOC_ZONA_TARIFARIA(byte LOC_ZONA_TARIFARIA) {
        this.LOC_ZONA_TARIFARIA = LOC_ZONA_TARIFARIA;
    }

    public String getLOC_ACTIVO() {
        return LOC_ACTIVO;
    }

    public void setLOC_ACTIVO(String LOC_ACTIVO) {
        this.LOC_ACTIVO = LOC_ACTIVO;
    }

    public String getLOC_ID_USER() {
        return LOC_ID_USER;
    }

    public void setLOC_ID_USER(String LOC_ID_USER) {
        this.LOC_ID_USER = LOC_ID_USER;
    }

    public String getLOC_FECHA_UPDATE() {
        return LOC_FECHA_UPDATE;
    }

    public void setLOC_FECHA_UPDATE(String LOC_FECHA_UPDATE) {
        this.LOC_FECHA_UPDATE = LOC_FECHA_UPDATE;
    }

    public int getLOC_LOTE_REPLICACION() {
        return LOC_LOTE_REPLICACION;
    }

    public void setLOC_LOTE_REPLICACION(int LOC_LOTE_REPLICACION) {
        this.LOC_LOTE_REPLICACION = LOC_LOTE_REPLICACION;
    }
}
