package ar.com.corpico.appcorpico.orders.domain.entity;

public class ZonaPuntoSqlite {
    private String zona;
    private int id;

    public ZonaPuntoSqlite(int id, String zona) {
        this.id = id;
        this.zona = zona;
    }


    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
