package ar.com.corpico.appcorpico.orders.data.entity.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrador on 21/03/2018.
 */

public class RestMedidorMarca {
    @SerializedName("MCA_ID")
    private short MCA_ID;
    @SerializedName("MCA_DESCRIPCION")
    private String MCA_DESCRIPCION;
    @SerializedName("MCA_ABREVIATURA")
    private String MCA_ABREVIATURA;
    @SerializedName("MCA_ACTIVO")
    private String MCA_ACTIVO;
    @SerializedName("MCA_ID_USER")
    private String MCA_ID_USER;
    @SerializedName("MCA_FECHA_UPDATE")
    private String MCA_FECHA_UPDATE;
    @SerializedName("MCA_LOTE_REPLICACION")
    private int MCA_LOTE_REPLICACION;

    public RestMedidorMarca(short MCA_ID, String MCA_DESCRIPCION, String MCA_ABREVIATURA, String MCA_ACTIVO,
                            String MCA_ID_USER, String MCA_FECHA_UPDATE, int MCA_LOTE_REPLICACION) {
        this.MCA_ID = MCA_ID;
        this.MCA_DESCRIPCION = MCA_DESCRIPCION;
        this.MCA_ABREVIATURA = MCA_ABREVIATURA;
        this.MCA_ACTIVO = MCA_ACTIVO;
        this.MCA_ID_USER = MCA_ID_USER;
        this.MCA_FECHA_UPDATE = MCA_FECHA_UPDATE;
        this.MCA_LOTE_REPLICACION = MCA_LOTE_REPLICACION;
    }

    public short getMCA_ID() {
        return MCA_ID;
    }

    public void setMCA_ID(short MCA_ID) {
        this.MCA_ID = MCA_ID;
    }

    public String getMCA_DESCRIPCION() {
        return MCA_DESCRIPCION;
    }

    public void setMCA_DESCRIPCION(String MCA_DESCRIPCION) {
        this.MCA_DESCRIPCION = MCA_DESCRIPCION;
    }

    public String getMCA_ABREVIATURA() {
        return MCA_ABREVIATURA;
    }

    public void setMCA_ABREVIATURA(String MCA_ABREVIATURA) {
        this.MCA_ABREVIATURA = MCA_ABREVIATURA;
    }

    public String getMCA_ACTIVO() {
        return MCA_ACTIVO;
    }

    public void setMCA_ACTIVO(String MCA_ACTIVO) {
        this.MCA_ACTIVO = MCA_ACTIVO;
    }

    public String getMCA_ID_USER() {
        return MCA_ID_USER;
    }

    public void setMCA_ID_USER(String MCA_ID_USER) {
        this.MCA_ID_USER = MCA_ID_USER;
    }

    public String getMCA_FECHA_UPDATE() {
        return MCA_FECHA_UPDATE;
    }

    public void setMCA_FECHA_UPDATE(String MCA_FECHA_UPDATE) {
        this.MCA_FECHA_UPDATE = MCA_FECHA_UPDATE;
    }

    public int getMCA_LOTE_REPLICACION() {
        return MCA_LOTE_REPLICACION;
    }

    public void setMCA_LOTE_REPLICACION(int MCA_LOTE_REPLICACION) {
        this.MCA_LOTE_REPLICACION = MCA_LOTE_REPLICACION;
    }
}
