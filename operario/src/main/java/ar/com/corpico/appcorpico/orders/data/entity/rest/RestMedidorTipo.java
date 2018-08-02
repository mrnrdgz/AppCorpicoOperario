package ar.com.corpico.appcorpico.orders.data.entity.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrador on 21/03/2018.
 */

public class RestMedidorTipo {
    @SerializedName("MET_ID")
    private short MET_ID;
    @SerializedName("MET_DESCRIPCION")
    private String MET_DESCRIPCION;
    @SerializedName("MET_TIPO_EMPRESA")
    private byte MET_TIPO_EMPRESA;
    @SerializedName("MET_ACTIVO")
    private String MET_ACTIVO;
    @SerializedName("MET_ID_USER")
    private String MET_ID_USER;
    @SerializedName("MET_FECHA_UPDATE")
    private String MET_FECHA_UPDATE;
    @SerializedName("MET_LOTE_REPLICACION")
    private int MET_LOTE_REPLICACION;

    public RestMedidorTipo(short MET_ID, String MET_DESCRIPCION, byte MET_TIPO_EMPRESA, String MET_ACTIVO, String MET_ID_USER,
                           String MET_FECHA_UPDATE, int MET_LOTE_REPLICACION) {
        this.MET_ID = MET_ID;
        this.MET_DESCRIPCION = MET_DESCRIPCION;
        this.MET_TIPO_EMPRESA = MET_TIPO_EMPRESA;
        this.MET_ACTIVO = MET_ACTIVO;
        this.MET_ID_USER = MET_ID_USER;
        this.MET_FECHA_UPDATE = MET_FECHA_UPDATE;
        this.MET_LOTE_REPLICACION = MET_LOTE_REPLICACION;
    }

    public short getMET_ID() {
        return MET_ID;
    }

    public void setMET_ID(short MET_ID) {
        this.MET_ID = MET_ID;
    }

    public String getMET_DESCRIPCION() {
        return MET_DESCRIPCION;
    }

    public void setMET_DESCRIPCION(String MET_DESCRIPCION) {
        this.MET_DESCRIPCION = MET_DESCRIPCION;
    }

    public byte getMET_TIPO_EMPRESA() {
        return MET_TIPO_EMPRESA;
    }

    public void setMET_TIPO_EMPRESA(byte MET_TIPO_EMPRESA) {
        this.MET_TIPO_EMPRESA = MET_TIPO_EMPRESA;
    }

    public String getMET_ACTIVO() {
        return MET_ACTIVO;
    }

    public void setMET_ACTIVO(String MET_ACTIVO) {
        this.MET_ACTIVO = MET_ACTIVO;
    }

    public String getMET_ID_USER() {
        return MET_ID_USER;
    }

    public void setMET_ID_USER(String MET_ID_USER) {
        this.MET_ID_USER = MET_ID_USER;
    }

    public String getMET_FECHA_UPDATE() {
        return MET_FECHA_UPDATE;
    }

    public void setMET_FECHA_UPDATE(String MET_FECHA_UPDATE) {
        this.MET_FECHA_UPDATE = MET_FECHA_UPDATE;
    }

    public int getMET_LOTE_REPLICACION() {
        return MET_LOTE_REPLICACION;
    }

    public void setMET_LOTE_REPLICACION(int MET_LOTE_REPLICACION) {
        this.MET_LOTE_REPLICACION = MET_LOTE_REPLICACION;
    }
}
