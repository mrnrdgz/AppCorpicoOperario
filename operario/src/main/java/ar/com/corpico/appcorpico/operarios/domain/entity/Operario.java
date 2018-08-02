package ar.com.corpico.appcorpico.operarios.domain.entity;

public class Operario {
    private String operario;
    private int operarioId;
    private int servicio;
    private int sector;

    public Operario(String operario, int operarioId, int servicio, int sector) {
        this.operario = operario;
        this.operarioId = operarioId;
        this.servicio = servicio;
        this.sector = sector;
    }

    public String getOperario() {
        return operario;
    }

    public void setOperario(String operario) {
        this.operario = operario;
    }

    public int getOperarioId() {
        return operarioId;
    }

    public void setOperarioId(int operarioId) {
        this.operarioId = operarioId;
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
