package ar.com.corpico.appcorpico.orders.data.entity.sql;

public class OrderSqlSinZona {
    int numero;
    String latitud;
    String longitud;

    public OrderSqlSinZona(int numero, String latitud, String longitud) {
        this.numero = numero;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
