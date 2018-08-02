package ar.com.corpico.appcorpico.home.data;

public class Cuadrilla_Home {
    private String cuadrilla;
    private int una;
    private int de;

    public Cuadrilla_Home(String cuadrilla, int una, int de) {
        this.cuadrilla = cuadrilla;
        this.una = una;
        this.de = de;
    }

    public String getCuadrilla() {
        return cuadrilla;
    }

    public void setCuadrilla(String cuadrilla) {
        this.cuadrilla = cuadrilla;
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
