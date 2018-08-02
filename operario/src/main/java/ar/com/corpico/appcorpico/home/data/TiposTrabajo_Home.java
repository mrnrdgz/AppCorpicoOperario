package ar.com.corpico.appcorpico.home.data;

public class TiposTrabajo_Home {
    private String tipoTrabajo;
    private int una;
    private int de;

    public TiposTrabajo_Home(String tipoTrabajo, int una, int de) {
        this.tipoTrabajo = tipoTrabajo;
        this.una = una;
        this.de = de;
    }

    public String getTipoTrabajo() {
        return tipoTrabajo;
    }

    public void setTipoTrabajo(String tipoTrabajo) {
        this.tipoTrabajo = tipoTrabajo;
    }

    public int getUna() {
        return una;
    }

    public void setUna(int una) {
        this.una = una;
    }

    public int getDe() {
        return de;
    }

    public void setDe(int de) {
        this.de = de;
    }
}
