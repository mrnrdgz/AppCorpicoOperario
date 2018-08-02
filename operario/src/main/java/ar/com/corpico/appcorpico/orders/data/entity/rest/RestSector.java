package ar.com.corpico.appcorpico.orders.data.entity.rest;

/**
 * Created by Administrador on 21/03/2018.
 */

import com.google.gson.annotations.SerializedName;
public class RestSector {
    @SerializedName("SEC_ID")
    private short SEC_ID;
    @SerializedName("SEC_ID_SERVICIO")
    private short SEC_ID_SERVICIO;
    @SerializedName("SEC_DESCRIPCION")
    private String SEC_DESCRIPCION;
    @SerializedName("SEC_FECHA_UPDATE")
    private String SEC_FECHA_UPDATE;

    public RestSector(short SEC_ID, short SEC_ID_SERVICIO, String SEC_DESCRIPCION, String SEC_FECHA_UPDATE) {
        this.SEC_ID = SEC_ID;
        this.SEC_ID_SERVICIO = SEC_ID_SERVICIO;
        this.SEC_DESCRIPCION = SEC_DESCRIPCION;
        this.SEC_FECHA_UPDATE = SEC_FECHA_UPDATE;
    }

    public short getSEC_ID() {
        return SEC_ID;
    }

    public void setSEC_ID(short SEC_ID) {
        this.SEC_ID = SEC_ID;
    }

    public short getSEC_ID_SERVICIO() {
        return SEC_ID_SERVICIO;
    }

    public void setSEC_ID_SERVICIO(short SEC_ID_SERVICIO) {
        this.SEC_ID_SERVICIO = SEC_ID_SERVICIO;
    }

    public String getSEC_DESCRIPCION() {
        return SEC_DESCRIPCION;
    }

    public void setSEC_DESCRIPCION(String SEC_DESCRIPCION) {
        this.SEC_DESCRIPCION = SEC_DESCRIPCION;
    }

    public String getSEC_FECHA_UPDATE() {
        return SEC_FECHA_UPDATE;
    }

    public void setSEC_FECHA_UPDATE(String SEC_FECHA_UPDATE) {
        this.SEC_FECHA_UPDATE = SEC_FECHA_UPDATE;
    }
}
