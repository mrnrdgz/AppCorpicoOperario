package ar.com.corpico.appcorpico.orders.data.entity.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrador on 21/03/2018.
 */

public class RestEmpresaContratista {
    @SerializedName("EML_ID")
    private short EML_ID;
    @SerializedName("EML_DESCRIPCION")
    private String EML_DESCRIPCION;
    @SerializedName("EML_DIRECCION")
    private String EML_DIRECCION;
    @SerializedName("EML_TELEFONO")
    private String EML_TELEFONO;
    @SerializedName("EML_TIPO_TOMA")
    private String EML_TIPO_TOMA;
    @SerializedName("EML_TIPO_EMPRESA")
    private short EML_TIPO_EMPRESA;
    @SerializedName("EML_ACTIVO")
    private String EML_ACTIVO;
    @SerializedName("EML_ID_USER")
    private String EML_ID_USER;
    @SerializedName("EML_FECHA_UPDATE")
    private String EML_FECHA_UPDATE;
    @SerializedName("EML_LOTE_REPLICACION")
    private int EML_LOTE_REPLICACION;

    public RestEmpresaContratista(short EML_ID, String EML_DESCRIPCION, String EML_DIRECCION, String EML_TELEFONO,
                                  String EML_TIPO_TOMA, short EML_TIPO_EMPRESA, String EML_ACTIVO, String EML_ID_USER,
                                  String EML_FECHA_UPDATE, int EML_LOTE_REPLICACION) {
        this.EML_ID = EML_ID;
        this.EML_DESCRIPCION = EML_DESCRIPCION;
        this.EML_DIRECCION = EML_DIRECCION;
        this.EML_TELEFONO = EML_TELEFONO;
        this.EML_TIPO_TOMA = EML_TIPO_TOMA;
        this.EML_TIPO_EMPRESA = EML_TIPO_EMPRESA;
        this.EML_ACTIVO = EML_ACTIVO;
        this.EML_ID_USER = EML_ID_USER;
        this.EML_FECHA_UPDATE = EML_FECHA_UPDATE;
        this.EML_LOTE_REPLICACION = EML_LOTE_REPLICACION;
    }

    public short getEML_ID() {
        return EML_ID;
    }

    public void setEML_ID(short EML_ID) {
        this.EML_ID = EML_ID;
    }

    public String getEML_DESCRIPCION() {
        return EML_DESCRIPCION;
    }

    public void setEML_DESCRIPCION(String EML_DESCRIPCION) {
        this.EML_DESCRIPCION = EML_DESCRIPCION;
    }

    public String getEML_DIRECCION() {
        return EML_DIRECCION;
    }

    public void setEML_DIRECCION(String EML_DIRECCION) {
        this.EML_DIRECCION = EML_DIRECCION;
    }

    public String getEML_TELEFONO() {
        return EML_TELEFONO;
    }

    public void setEML_TELEFONO(String EML_TELEFONO) {
        this.EML_TELEFONO = EML_TELEFONO;
    }

    public String getEML_TIPO_TOMA() {
        return EML_TIPO_TOMA;
    }

    public void setEML_TIPO_TOMA(String EML_TIPO_TOMA) {
        this.EML_TIPO_TOMA = EML_TIPO_TOMA;
    }

    public short getEML_TIPO_EMPRESA() {
        return EML_TIPO_EMPRESA;
    }

    public void setEML_TIPO_EMPRESA(short EML_TIPO_EMPRESA) {
        this.EML_TIPO_EMPRESA = EML_TIPO_EMPRESA;
    }

    public String getEML_ACTIVO() {
        return EML_ACTIVO;
    }

    public void setEML_ACTIVO(String EML_ACTIVO) {
        this.EML_ACTIVO = EML_ACTIVO;
    }

    public String getEML_ID_USER() {
        return EML_ID_USER;
    }

    public void setEML_ID_USER(String EML_ID_USER) {
        this.EML_ID_USER = EML_ID_USER;
    }

    public String getEML_FECHA_UPDATE() {
        return EML_FECHA_UPDATE;
    }

    public void setEML_FECHA_UPDATE(String EML_FECHA_UPDATE) {
        this.EML_FECHA_UPDATE = EML_FECHA_UPDATE;
    }

    public int getEML_LOTE_REPLICACION() {
        return EML_LOTE_REPLICACION;
    }

    public void setEML_LOTE_REPLICACION(int EML_LOTE_REPLICACION) {
        this.EML_LOTE_REPLICACION = EML_LOTE_REPLICACION;
    }
}
