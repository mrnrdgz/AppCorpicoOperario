package ar.com.corpico.appcorpico.orders.data.entity.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrador on 21/03/2018.
 */

public class RestSuministroPosicionGlobal {
    @SerializedName("SPG_EMPRESA")
    private short SPG_EMPRESA;
    @SerializedName("SPG_CLIENTE")
    private int SPG_CLIENTE;
    @SerializedName("SPG_SUMINISTRO")
    private short SPG_SUMINISTRO;
    @SerializedName("SPG_LATITUD")
    private String SPG_LATITUD;
    @SerializedName("SPG_LONGITUD")
    private String SPG_LONGITUD;
    @SerializedName("SPG_FECHA_CAPTURA")
    private String SPG_FECHA_CAPTURA;
    @SerializedName("SPG_USER_ID")
    private String SPG_USER_ID;
    @SerializedName("SPG_FECHA_UPDATE")
    private String SPG_FECHA_UPDATE;

    public RestSuministroPosicionGlobal(short SPG_EMPRESA, int SPG_CLIENTE, short SPG_SUMINISTRO, String SPG_LATITUD,
                                        String SPG_LONGITUD, String SPG_FECHA_CAPTURA, String SPG_USER_ID,
                                        String SPG_FECHA_UPDATE) {
        this.SPG_EMPRESA = SPG_EMPRESA;
        this.SPG_CLIENTE = SPG_CLIENTE;
        this.SPG_SUMINISTRO = SPG_SUMINISTRO;
        this.SPG_LATITUD = SPG_LATITUD;
        this.SPG_LONGITUD = SPG_LONGITUD;
        this.SPG_FECHA_CAPTURA = SPG_FECHA_CAPTURA;
        this.SPG_USER_ID = SPG_USER_ID;
        this.SPG_FECHA_UPDATE = SPG_FECHA_UPDATE;
    }

    public short getSPG_EMPRESA() {
        return SPG_EMPRESA;
    }

    public void setSPG_EMPRESA(short SPG_EMPRESA) {
        this.SPG_EMPRESA = SPG_EMPRESA;
    }

    public int getSPG_CLIENTE() {
        return SPG_CLIENTE;
    }

    public void setSPG_CLIENTE(int SPG_CLIENTE) {
        this.SPG_CLIENTE = SPG_CLIENTE;
    }

    public short getSPG_SUMINISTRO() {
        return SPG_SUMINISTRO;
    }

    public void setSPG_SUMINISTRO(short SPG_SUMINISTRO) {
        this.SPG_SUMINISTRO = SPG_SUMINISTRO;
    }

    public String getSPG_LATITUD() {
        return SPG_LATITUD;
    }

    public void setSPG_LATITUD(String SPG_LATITUD) {
        this.SPG_LATITUD = SPG_LATITUD;
    }

    public String getSPG_LONGITUD() {
        return SPG_LONGITUD;
    }

    public void setSPG_LONGITUD(String SPG_LONGITUD) {
        this.SPG_LONGITUD = SPG_LONGITUD;
    }

    public String getSPG_FECHA_CAPTURA() {
        return SPG_FECHA_CAPTURA;
    }

    public void setSPG_FECHA_CAPTURA(String SPG_FECHA_CAPTURA) {
        this.SPG_FECHA_CAPTURA = SPG_FECHA_CAPTURA;
    }

    public String getSPG_USER_ID() {
        return SPG_USER_ID;
    }

    public void setSPG_USER_ID(String SPG_USER_ID) {
        this.SPG_USER_ID = SPG_USER_ID;
    }

    public String getSPG_FECHA_UPDATE() {
        return SPG_FECHA_UPDATE;
    }

    public void setSPG_FECHA_UPDATE(String SPG_FECHA_UPDATE) {
        this.SPG_FECHA_UPDATE = SPG_FECHA_UPDATE;
    }
}
