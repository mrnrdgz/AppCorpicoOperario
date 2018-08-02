package ar.com.corpico.appcorpico.orders.data.entity.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrador on 21/03/2018.
 */

public class RestMedidorModelo {
    @SerializedName("MMO_MARCA_MEDIDOR")
    private short MMO_MARCA_MEDIDOR;
    @SerializedName("MMO_ID")
    private short MMO_ID;
    @SerializedName("MMO_DESCRIPCION")
    private String MMO_DESCRIPCION;
    @SerializedName("MMO_TIPO_EMPRESA")
    private byte MMO_TIPO_EMPRESA;
    @SerializedName("MMO_ACTIVO")
    private String MMO_ACTIVO;
    @SerializedName("MMO_ID_USER")
    private String MMO_ID_USER;
    @SerializedName("MMO_FECHA_UPDATE")
    private String MMO_FECHA_UPDATE;
    @SerializedName("MMO_LOTE_REPLICACION")
    private int MMO_LOTE_REPLICACION;

    public RestMedidorModelo(short MMO_MARCA_MEDIDOR, short MMO_ID, String MMO_DESCRIPCION, byte MMO_TIPO_EMPRESA,
                             String MMO_ACTIVO, String MMO_ID_USER, String MMO_FECHA_UPDATE, int MMO_LOTE_REPLICACION) {
        this.MMO_MARCA_MEDIDOR = MMO_MARCA_MEDIDOR;
        this.MMO_ID = MMO_ID;
        this.MMO_DESCRIPCION = MMO_DESCRIPCION;
        this.MMO_TIPO_EMPRESA = MMO_TIPO_EMPRESA;
        this.MMO_ACTIVO = MMO_ACTIVO;
        this.MMO_ID_USER = MMO_ID_USER;
        this.MMO_FECHA_UPDATE = MMO_FECHA_UPDATE;
        this.MMO_LOTE_REPLICACION = MMO_LOTE_REPLICACION;
    }

    public short getMMO_MARCA_MEDIDOR() {
        return MMO_MARCA_MEDIDOR;
    }

    public void setMMO_MARCA_MEDIDOR(short MMO_MARCA_MEDIDOR) {
        this.MMO_MARCA_MEDIDOR = MMO_MARCA_MEDIDOR;
    }

    public short getMMO_ID() {
        return MMO_ID;
    }

    public void setMMO_ID(short MMO_ID) {
        this.MMO_ID = MMO_ID;
    }

    public String getMMO_DESCRIPCION() {
        return MMO_DESCRIPCION;
    }

    public void setMMO_DESCRIPCION(String MMO_DESCRIPCION) {
        this.MMO_DESCRIPCION = MMO_DESCRIPCION;
    }

    public byte getMMO_TIPO_EMPRESA() {
        return MMO_TIPO_EMPRESA;
    }

    public void setMMO_TIPO_EMPRESA(byte MMO_TIPO_EMPRESA) {
        this.MMO_TIPO_EMPRESA = MMO_TIPO_EMPRESA;
    }

    public String getMMO_ACTIVO() {
        return MMO_ACTIVO;
    }

    public void setMMO_ACTIVO(String MMO_ACTIVO) {
        this.MMO_ACTIVO = MMO_ACTIVO;
    }

    public String getMMO_ID_USER() {
        return MMO_ID_USER;
    }

    public void setMMO_ID_USER(String MMO_ID_USER) {
        this.MMO_ID_USER = MMO_ID_USER;
    }

    public String getMMO_FECHA_UPDATE() {
        return MMO_FECHA_UPDATE;
    }

    public void setMMO_FECHA_UPDATE(String MMO_FECHA_UPDATE) {
        this.MMO_FECHA_UPDATE = MMO_FECHA_UPDATE;
    }

    public int getMMO_LOTE_REPLICACION() {
        return MMO_LOTE_REPLICACION;
    }

    public void setMMO_LOTE_REPLICACION(int MMO_LOTE_REPLICACION) {
        this.MMO_LOTE_REPLICACION = MMO_LOTE_REPLICACION;
    }
}
