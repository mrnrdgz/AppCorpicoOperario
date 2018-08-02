package ar.com.corpico.appcorpico.home.data;

public class Estados_Home {
    private String estado;
    private int una;
    private int de;

    public Estados_Home(String estado, int una, int de) {
        this.estado = estado;
        this.una = una;
        this.de = de;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
