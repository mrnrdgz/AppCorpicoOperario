package ar.com.corpico.appcorpico.home.data;

public class Zonas_Home {
    private String zona;
    private int una;
    private int de;

    public Zonas_Home(String zona, int una, int de) {
        this.zona = zona;
        this.una = una;
        this.de = de;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
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
