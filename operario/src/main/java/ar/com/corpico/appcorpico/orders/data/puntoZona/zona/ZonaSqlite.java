package ar.com.corpico.appcorpico.orders.data.puntoZona.zona;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class ZonaSqlite {
    private int id;
    private String mZona;
    private List<LatLng> points;

    public ZonaSqlite(int anInt, String zona, List<LatLng> listLatLng) {
        id =anInt;
        mZona = zona;
        points = listLatLng;
    }

    public List<LatLng> getPuntos() {
        return points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getZona() {
        return mZona;
    }

    public void setZona(String zona) {
        mZona = zona;
    }
}
