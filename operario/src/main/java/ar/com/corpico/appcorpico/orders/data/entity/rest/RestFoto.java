package ar.com.corpico.appcorpico.orders.data.entity.rest;

import com.google.gson.annotations.SerializedName;

public class RestFoto {
    @SerializedName("OTF_ORDEN")
    private int orden;
    @SerializedName("OTF_NFOTO")
    private int nFoto;
    @SerializedName("OTF_FECHA")
    private String fecha;
    @SerializedName("OTF_OBSERVACION")
    private String observaciones;
    @SerializedName("OTF_USER_ID")
    private String id_user;
    @SerializedName("OTF_FECHA_UPDATE")
    private String fecha_update;
    @SerializedName("OTF_PATH")
    private String path;
    @SerializedName("OTF_SECTOR")
    private int sector;
    @SerializedName("OTF_SERVICIO")
    private int servicio;

    public RestFoto(int orden, int nFoto, String fecha, String observaciones,
                    String id_user, String fecha_update, String path, int sector, int servicio) {
        this.orden = orden;
        this.nFoto = nFoto;
        this.fecha = fecha;
        this.observaciones = observaciones;
        this.id_user = id_user;
        this.fecha_update = fecha_update;
        this.path = path;
        this.sector = sector;
        this.servicio = servicio;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public int getnFoto() {
        return nFoto;
    }

    public void setnFoto(int nFoto) {
        this.nFoto = nFoto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getFecha_update() {
        return fecha_update;
    }

    public void setFecha_update(String fecha_update) {
        this.fecha_update = fecha_update;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSector() {
        return sector;
    }

    public void setSector(int sector) {
        this.sector = sector;
    }

    public int getServicio() {
        return servicio;
    }

    public void setServicio(int servicio) {
        this.servicio = servicio;
    }
}
