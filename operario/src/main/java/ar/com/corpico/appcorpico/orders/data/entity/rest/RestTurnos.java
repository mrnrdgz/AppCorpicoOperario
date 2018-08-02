package ar.com.corpico.appcorpico.orders.data.entity.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrador on 30/03/2018.
 */

public class RestTurnos {
    @SerializedName("TUR_ORDEN")
    private int TUR_ORDEN;
    @SerializedName("TUR_TURNO")
    private String TUR_TURNO;
    @SerializedName("TUR_FECHA_UPDATE")
    private String TUR_FECHA_UPDATE;
    @SerializedName("TUR_SERVICIO")
    private int TUR_SERVICIO;
    @SerializedName("TUR_SECTOR")
    private int TUR_SECTOR;


    public RestTurnos(int TUR_ORDEN, String TUR_TURNO, String TUR_FECHA_UPDATE, int TUR_SERVICIO, int TUR_SECTOR) {
        this.TUR_ORDEN = TUR_ORDEN;
        this.TUR_TURNO = TUR_TURNO;
        this.TUR_FECHA_UPDATE = TUR_FECHA_UPDATE;
        this.TUR_SERVICIO = TUR_SERVICIO;
        this.TUR_SECTOR = TUR_SECTOR;
    }

    public int getTUR_SERVICIO() {
        return TUR_SERVICIO;
    }

    public void setTUR_SERVICIO(int TUR_SERVICIO) {
        this.TUR_SERVICIO = TUR_SERVICIO;
    }

    public int getTUR_SECTOR() {
        return TUR_SECTOR;
    }

    public void setTUR_SECTOR(int TUR_SECTOR) {
        this.TUR_SECTOR = TUR_SECTOR;
    }

    public int getTUR_ORDEN() {
        return TUR_ORDEN;
    }

    public void setTUR_ORDEN(int TUR_ORDEN) {
        this.TUR_ORDEN = TUR_ORDEN;
    }

    public String getTUR_TURNO() {
        return TUR_TURNO;
    }

    public void setTUR_TURNO(String TUR_TURNO) {
        this.TUR_TURNO = TUR_TURNO;
    }

    public String getTUR_FECHA_UPDATE() {
        return TUR_FECHA_UPDATE;
    }

    public void setTUR_FECHA_UPDATE(String TUR_FECHA_UPDATE) {
        this.TUR_FECHA_UPDATE = TUR_FECHA_UPDATE;
    }
}
