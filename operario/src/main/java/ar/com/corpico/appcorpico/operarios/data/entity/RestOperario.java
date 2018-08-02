package ar.com.corpico.appcorpico.operarios.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrador on 21/03/2018.
 */

public class RestOperario {
    @SerializedName("OPE_ID")
    private short OPE_ID;
    @SerializedName("OPE_DESCRIPCION")
    private String OPE_DESCRIPCION;
    @SerializedName("OPE_CONTRATISTA")
    private short OPE_CONTRATISTA;
    @SerializedName("OPE_SUCURSAL")
    private short OPE_SUCURSAL;
    @SerializedName("OPE_ACTIVO")
    private String OPE_ACTIVO;
    @SerializedName("OPE_ID_USER")
    private String OPE_ID_USER;
    @SerializedName("OPE_FECHA_UPDATE")
    private String  OPE_FECHA_UPDATE;
    @SerializedName("OPE_LOTE_REPLICACION")
    private int OPE_LOTE_REPLICACION;

    public RestOperario(short OPE_ID, String OPE_DESCRIPCION, short OPE_CONTRATISTA, short OPE_SUCURSAL, String OPE_ACTIVO,
                        String OPE_ID_USER, String  OPE_FECHA_UPDATE, int OPE_LOTE_REPLICACION) {
        this.OPE_ID = OPE_ID;
        this.OPE_DESCRIPCION = OPE_DESCRIPCION;
        this.OPE_CONTRATISTA = OPE_CONTRATISTA;
        this.OPE_SUCURSAL = OPE_SUCURSAL;
        this.OPE_ACTIVO = OPE_ACTIVO;
        this.OPE_ID_USER = OPE_ID_USER;
        this.OPE_FECHA_UPDATE = OPE_FECHA_UPDATE;
        this.OPE_LOTE_REPLICACION = OPE_LOTE_REPLICACION;
    }

    public short getOPE_ID() {
        return OPE_ID;
    }

    public void setOPE_ID(short OPE_ID) {
        this.OPE_ID = OPE_ID;
    }

    public String getOPE_DESCRIPCION() {
        return OPE_DESCRIPCION;
    }

    public void setOPE_DESCRIPCION(String OPE_DESCRIPCION) {
        this.OPE_DESCRIPCION = OPE_DESCRIPCION;
    }

    public short getOPE_CONTRATISTA() {
        return OPE_CONTRATISTA;
    }

    public void setOPE_CONTRATISTA(short OPE_CONTRATISTA) {
        this.OPE_CONTRATISTA = OPE_CONTRATISTA;
    }

    public short getOPE_SUCURSAL() {
        return OPE_SUCURSAL;
    }

    public void setOPE_SUCURSAL(short OPE_SUCURSAL) {
        this.OPE_SUCURSAL = OPE_SUCURSAL;
    }

    public String getOPE_ACTIVO() {
        return OPE_ACTIVO;
    }

    public void setOPE_ACTIVO(String OPE_ACTIVO) {
        this.OPE_ACTIVO = OPE_ACTIVO;
    }

    public String getOPE_ID_USER() {
        return OPE_ID_USER;
    }

    public void setOPE_ID_USER(String OPE_ID_USER) {
        this.OPE_ID_USER = OPE_ID_USER;
    }

    public String  getOPE_FECHA_UPDATE() {
        return OPE_FECHA_UPDATE;
    }

    public void setOPE_FECHA_UPDATE(String  OPE_FECHA_UPDATE) {
        this.OPE_FECHA_UPDATE = OPE_FECHA_UPDATE;
    }

    public int getOPE_LOTE_REPLICACION() {
        return OPE_LOTE_REPLICACION;
    }

    public void setOPE_LOTE_REPLICACION(int OPE_LOTE_REPLICACION) {
        this.OPE_LOTE_REPLICACION = OPE_LOTE_REPLICACION;
    }
}
