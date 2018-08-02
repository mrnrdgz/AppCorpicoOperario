package ar.com.corpico.appcorpico.orders.data.entity.rest;

/**
 * Created by Administrador on 21/03/2018.
 */

import com.google.gson.annotations.SerializedName;
public class RestZona {
    @SerializedName("ZON_ID")
    private short ZON_ID;
    @SerializedName("ZON_DESCRIPCION")
    private String ZON_DESCRIPCION;
    @SerializedName("ZON_OBSERVACION")
    private String ZON_OBSERVACION;
    @SerializedName("ZON_FECHA_UPDATE")
    private String ZON_FECHA_UPDATE;

    public RestZona(short ZON_ID, String ZON_DESCRIPCION, String ZON_OBSERVACION, String ZON_FECHA_UPDATE) {
        this.ZON_ID = ZON_ID;
        this.ZON_DESCRIPCION = ZON_DESCRIPCION;
        this.ZON_OBSERVACION = ZON_OBSERVACION;
        this.ZON_FECHA_UPDATE = ZON_FECHA_UPDATE;
    }

    public short getZON_ID() {
        return ZON_ID;
    }

    public void setZON_ID(short ZON_ID) {
        this.ZON_ID = ZON_ID;
    }

    public String getZON_DESCRIPCION() {
        return ZON_DESCRIPCION;
    }

    public void setZON_DESCRIPCION(String ZON_DESCRIPCION) {
        this.ZON_DESCRIPCION = ZON_DESCRIPCION;
    }

    public String getZON_OBSERVACION() {
        return ZON_OBSERVACION;
    }

    public void setZON_OBSERVACION(String ZON_OBSERVACION) {
        this.ZON_OBSERVACION = ZON_OBSERVACION;
    }

    public String getZON_FECHA_UPDATE() {
        return ZON_FECHA_UPDATE;
    }

    public void setZON_FECHA_UPDATE(String ZON_FECHA_UPDATE) {
        this.ZON_FECHA_UPDATE = ZON_FECHA_UPDATE;
    }
}
