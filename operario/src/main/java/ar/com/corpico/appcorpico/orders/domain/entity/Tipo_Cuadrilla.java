package ar.com.corpico.appcorpico.orders.domain.entity;

/**
 * Created by Administrador on 22/05/2017.
 */

public class Tipo_Cuadrilla {
    private int id;
    private int servicio;
    private int sector;
    private String tipo_cuadrilla;
    private int geasysId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSector() {
        return sector;
    }

    public void setSector(int sector) {
        this.sector = sector;
    }

    public Tipo_Cuadrilla(int id, int servicio, int sector, String tipo_cuadrilla,int geasysId) {
        this.id = id;
        this.servicio = servicio;
        this.sector = sector;
        this.tipo_cuadrilla = tipo_cuadrilla;
        this.geasysId = geasysId;
    }
    public Tipo_Cuadrilla() {

    }
    public String getTipo_cuadrilla() {
        return tipo_cuadrilla;
    }

    public void setTipo_cuadrilla(String tipo_cuadrilla) {
        this.tipo_cuadrilla = tipo_cuadrilla;
    }

    public int getServicio() {
        return servicio;
    }

    public void setServicio(int servicio) {
        this.servicio = servicio;
    }

    public int getGeasysId() {
        return geasysId;
    }

    public void setGeasysId(int geasysId) {
        this.geasysId = geasysId;
    }
}
