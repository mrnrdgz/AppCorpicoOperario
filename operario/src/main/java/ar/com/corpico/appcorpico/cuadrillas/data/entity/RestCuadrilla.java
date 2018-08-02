package ar.com.corpico.appcorpico.cuadrillas.data.entity;

import com.google.gson.annotations.SerializedName;

public class RestCuadrilla {
    @SerializedName("CU_ID")
    private int id;
    @SerializedName("CU_TIPO_CUADRILLA")
    private int tipo_cuadrilla;
    @SerializedName("CU_OPERARIO")
    private int operario;
    @SerializedName("CU_FECHA")
    private String fecha;
    @SerializedName("CU_FECHA_UPDATE")
    private String fecha_update;
    @SerializedName("CU_SERVICIO")
    private int servicio;
    @SerializedName("CU_SECTOR")
    private int sector;

    public RestCuadrilla(int id, int tipo_cuadrilla, int operario, String fecha,
                         String fecha_update, int servicio, int sector) {
        this.id = id;
        this.tipo_cuadrilla = tipo_cuadrilla;
        this.operario = operario;
        this.fecha = fecha;
        this.fecha_update = fecha_update;
        this.servicio = servicio;
        this.sector = sector;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipo_cuadrilla() {
        return tipo_cuadrilla;
    }

    public void setTipo_cuadrilla(int tipo_cuadrilla) {
        this.tipo_cuadrilla = tipo_cuadrilla;
    }

    public int getOperario() {
        return operario;
    }

    public void setOperario(int operario) {
        this.operario = operario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFecha_update() {
        return fecha_update;
    }

    public void setFecha_update(String fecha_update) {
        this.fecha_update = fecha_update;
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
}
