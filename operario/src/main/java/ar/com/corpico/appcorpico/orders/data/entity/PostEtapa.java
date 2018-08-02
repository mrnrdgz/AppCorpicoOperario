package ar.com.corpico.appcorpico.orders.data.entity;

import com.google.gson.annotations.SerializedName;

public class PostEtapa {
    @SerializedName("id")
    private int id;
    @SerializedName("id_tmp")
    private int id_temp;
    @SerializedName("orden")
    private int orden;

    public PostEtapa(int id, int id_temp, int orden) {
        this.id = id;
        this.orden = orden;
        this.id_temp = id_temp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public int getId_temp() {
        return id_temp;
    }

    public void setId_temp(int id_temp) {
        this.id_temp = id_temp;
    }

    @Override
    public String toString() {
        return "PostEtapa{" +
                "id=" + id +
                ", id_temp=" + id_temp +
                ", orden=" + orden +
                '}';
    }
}
