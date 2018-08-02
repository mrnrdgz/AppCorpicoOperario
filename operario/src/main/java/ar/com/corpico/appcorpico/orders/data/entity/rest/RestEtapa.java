package ar.com.corpico.appcorpico.orders.data.entity.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrador on 30/03/2018.
 */

public class RestEtapa {
    @SerializedName("ETA_NUMERO_ORDEN")
    private int ETA_NUMERO_ORDEN;
    @SerializedName("ETA_FECHA")
    private String ETA_FECHA;
    @SerializedName("ETA_ESTADO")
    private String ETA_ESTADO;
    @SerializedName("ETA_OBSERVACION")
    private String ETA_OBSERVACION;
    @SerializedName("ETA_USUARIO")
    private String ETA_USUARIO;
    @SerializedName("ETA_ID")
    private int ETA_ID;
    @SerializedName("ETA_FECHA_UPDATE")
    private String ETA_FECHA_UPDATE;
    @SerializedName("ETA_SERVICIO")
    private int ETA_SERVICIO;
    @SerializedName("ETA_SECTOR")
    private int ETA_SECTOR;
    @SerializedName("ETA_LATITUD")
    private String ETA_LATITUD;
    @SerializedName("ETA_LONGITUD")
    private String ETA_LONGITUD;
    @SerializedName("ETA_ID_TEMP")
    private int ETA_ID_TEMP;


    public RestEtapa(int ETA_NUMERO_ORDEN, String ETA_FECHA, String ETA_ESTADO, String ETA_OBSERVACION,
                     String ETA_USUARIO, int ETA_ID, String ETA_FECHA_UPDATE, int ETA_SERVICIO, int ETA_SECTOR,
                     String ETA_LATITUD, String ETA_LONGITUD, int ETA_ID_TEMP) {
        this.ETA_NUMERO_ORDEN = ETA_NUMERO_ORDEN;
        this.ETA_FECHA = ETA_FECHA;
        this.ETA_ESTADO = ETA_ESTADO;
        this.ETA_OBSERVACION = ETA_OBSERVACION;
        this.ETA_USUARIO = ETA_USUARIO;
        this.ETA_ID = ETA_ID;
        this.ETA_FECHA_UPDATE = ETA_FECHA_UPDATE;
        this.ETA_SERVICIO = ETA_SERVICIO;
        this.ETA_SECTOR = ETA_SECTOR;
        this.ETA_LATITUD = ETA_LATITUD;
        this.ETA_LONGITUD = ETA_LONGITUD;
        this.ETA_ID_TEMP = ETA_ID_TEMP;
    }

    public int getETA_SERVICIO() {
        return ETA_SERVICIO;
    }

    public void setETA_SERVICIO(int ETA_SERVICIO) {
        this.ETA_SERVICIO = ETA_SERVICIO;
    }

    public int getETA_SECTOR() {
        return ETA_SECTOR;
    }

    public void setETA_SECTOR(int ETA_SECTOR) {
        this.ETA_SECTOR = ETA_SECTOR;
    }

    public int getETA_NUMERO_ORDEN() {
        return ETA_NUMERO_ORDEN;
    }

    public void setETA_NUMERO_ORDEN(int ETA_NUMERO_ORDEN) {
        this.ETA_NUMERO_ORDEN = ETA_NUMERO_ORDEN;
    }

    public String getETA_FECHA() {
        return ETA_FECHA;
    }

    public void setETA_FECHA(String ETA_FECHA) {
        this.ETA_FECHA = ETA_FECHA;
    }

    public String getETA_ESTADO() {
        return ETA_ESTADO;
    }

    public void setETA_ESTADO(String ETA_ESTADO) {
        this.ETA_ESTADO = ETA_ESTADO;
    }

    public String getETA_OBSERVACION() {
        return ETA_OBSERVACION;
    }

    public void setETA_OBSERVACION(String ETA_OBSERVACION) {
        this.ETA_OBSERVACION = ETA_OBSERVACION;
    }

    public String getETA_USUARIO() {
        return ETA_USUARIO;
    }

    public void setETA_USUARIO(String ETA_USUARIO) {
        this.ETA_USUARIO = ETA_USUARIO;
    }

    public int getETA_ID() {
        return ETA_ID;
    }

    public void setETA_ID(int ETA_ID) {
        this.ETA_ID = ETA_ID;
    }

    public String getETA_FECHA_UPDATE() {
        return ETA_FECHA_UPDATE;
    }

    public void setETA_FECHA_UPDATE(String ETA_FECHA_UPDATE) {
        this.ETA_FECHA_UPDATE = ETA_FECHA_UPDATE;
    }

    public String getETA_LATITUD() {
        return ETA_LATITUD;
    }

    public void setETA_LATITUD(String ETA_LATITUD) {
        this.ETA_LATITUD = ETA_LATITUD;
    }

    public String getETA_LONGITUD() {
        return ETA_LONGITUD;
    }

    public void setETA_LONGITUD(String ETA_LONGITUD) {
        this.ETA_LONGITUD = ETA_LONGITUD;
    }

    public int getETA_ID_TEMP() {
        return ETA_ID_TEMP;
    }

    public void setETA_ID_TEMP(int ETA_ID_TEMP) {
        this.ETA_ID_TEMP = ETA_ID_TEMP;
    }

    @Override
    public String toString() {
        return "RestEtapa{" +
                "ETA_NUMERO_ORDEN=" + ETA_NUMERO_ORDEN +
                ", ETA_FECHA='" + ETA_FECHA + '\'' +
                ", ETA_ESTADO='" + ETA_ESTADO + '\'' +
                ", ETA_OBSERVACION='" + ETA_OBSERVACION + '\'' +
                ", ETA_USUARIO='" + ETA_USUARIO + '\'' +
                ", ETA_ID=" + ETA_ID +
                ", ETA_FECHA_UPDATE='" + ETA_FECHA_UPDATE + '\'' +
                ", ETA_SERVICIO=" + ETA_SERVICIO +
                ", ETA_SECTOR=" + ETA_SECTOR +
                ", ETA_LATITUD='" + ETA_LATITUD + '\'' +
                ", ETA_LONGITUD='" + ETA_LONGITUD + '\'' +
                ", ETA_ID_TEMP=" + ETA_ID_TEMP +
                '}';
    }
}
