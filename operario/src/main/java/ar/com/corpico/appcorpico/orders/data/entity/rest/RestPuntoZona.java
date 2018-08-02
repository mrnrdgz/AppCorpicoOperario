package ar.com.corpico.appcorpico.orders.data.entity.rest;

import com.google.gson.annotations.SerializedName;

public class RestPuntoZona {
    @SerializedName("PZON_ID")
    private int PZON_ID;
    @SerializedName("PZON_ID_ZONA")
    private int PZON_ID_ZONA;
    @SerializedName("PZON_INDICE")
    private int PZON_INDICE;
    @SerializedName("PZON_LATITUD")
    private double PZON_LATITUD;
    @SerializedName("PZON_LONGITUD")
    private double PZON_LONGITUD;
    @SerializedName("PZON_FECHA_UPDATE")
    private String PZON_FECHA_UPDATE;

    public RestPuntoZona(int PZON_ID, int PZON_ID_ZONA, int PZON_INDICE, double PZON_LATITUD,
                         double PZON_LONGITUD, String PZON_FECHA_UPDATE) {
        this.PZON_ID = PZON_ID;
        this.PZON_ID_ZONA = PZON_ID_ZONA;
        this.PZON_INDICE = PZON_INDICE;
        this.PZON_LATITUD = PZON_LATITUD;
        this.PZON_LONGITUD = PZON_LONGITUD;
        this.PZON_FECHA_UPDATE = PZON_FECHA_UPDATE;
    }

    public int getPZON_ID() {
        return PZON_ID;
    }

    public void setPZON_ID(int PZON_ID) {
        this.PZON_ID = PZON_ID;
    }

    public int getPZON_ID_ZONA() {
        return PZON_ID_ZONA;
    }

    public void setPZON_ID_ZONA(int PZON_ID_ZONA) {
        this.PZON_ID_ZONA = PZON_ID_ZONA;
    }

    public int getPZON_INDICE() {
        return PZON_INDICE;
    }

    public void setPZON_INDICE(int PZON_INDICE) {
        this.PZON_INDICE = PZON_INDICE;
    }

    public double getPZON_LATITUD() {
        return PZON_LATITUD;
    }

    public void setPZON_LATITUD(double PZON_LATITUD) {
        this.PZON_LATITUD = PZON_LATITUD;
    }

    public double getPZON_LONGITUD() {
        return PZON_LONGITUD;
    }

    public void setPZON_LONGITUD(double PZON_LONGITUD) {
        this.PZON_LONGITUD = PZON_LONGITUD;
    }

    public String getPZON_FECHA_UPDATE() {
        return PZON_FECHA_UPDATE;
    }

    public void setPZON_FECHA_UPDATE(String PZON_FECHA_UPDATE) {
        this.PZON_FECHA_UPDATE = PZON_FECHA_UPDATE;
    }
}
