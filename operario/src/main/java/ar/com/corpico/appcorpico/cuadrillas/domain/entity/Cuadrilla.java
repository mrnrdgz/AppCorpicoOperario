package ar.com.corpico.appcorpico.cuadrillas.domain.entity;

public class Cuadrilla {
    private int id;
    private int tipo_cuadrilla;
    private int operarioId;
    private String operario;
    private String fecha;
    private String fecha_update;
    private int servicio;
    private int sector;

    public Cuadrilla(int id, int tipo_cuadrilla, int operarioId, String operario, String fecha,
                     String fecha_update, int servicio, int sector) {
        this.id = id;
        this.tipo_cuadrilla = tipo_cuadrilla;
        this.operarioId = operarioId;
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

    public Integer getOperarioId() {
        return operarioId;
    }

    public void setOperarioId(int operarioId) {
        this.operarioId = operarioId;
    }

    public String getOperario() {
        return operario;
    }

    public void setOperario(String operario) {
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
