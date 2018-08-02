package ar.com.corpico.appcorpico.notes.data.entity;

import com.google.gson.annotations.SerializedName;

public class RestNotes {
    @SerializedName("NOV_ID")
    private int id;
    @SerializedName("NOV_FECHA")
    private String fecha;
    @SerializedName("NOV_DESCRIPCION")
    private String descripcion;
    @SerializedName("NOV_SERVICIO")
    private int servicio;
    @SerializedName("NOV_SECTOR")
    private int sector;
    @SerializedName("NOV_OPERARIO")
    private String operario;
    @SerializedName("NOV_IDOPERARIO")
    private int operario_id;
    @SerializedName("NOV_FECHA_UPDATE")
    private String update;

    public RestNotes(int id, String fecha, String descripcion, int servicio, int sector,
                     String operario, int operario_id, String update) {
        this.id = id;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.servicio = servicio;
        this.sector = sector;
        this.operario = operario;
        this.operario_id = operario_id;
        this.update = update;
    }

    public void setOperario(String operario) {
        this.operario = operario;
    }

    public int getOperario_id() {
        return operario_id;
    }

    public String getOperario() {
        return operario;
    }

    public void setOperario_id(int operario_id) {
        this.operario_id = operario_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getServicio() {
        return servicio;
    }

    public void setServicio(int servicio) {
        this.servicio = servicio;
    }

    public int getSector() {
        return sector;
    }

    public void setSector(int sector) {
        this.sector = sector;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }
}
