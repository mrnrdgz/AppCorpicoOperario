package ar.com.corpico.appcorpico.orders.data.entity.rest;

/**
 * Created by Administrador on 21/03/2018.
 */

import com.google.gson.annotations.SerializedName;
public class RestTipoUsuario {
    @SerializedName("TIU_ID")
    private short TIU_ID;
    @SerializedName("TIU_DESCRIPCION")
    private String TIU_DESCRIPCION;
    @SerializedName("TIU_TIPO_EMPRESA")
    private byte TIU_TIPO_EMPRESA;
    @SerializedName("TIU_ABREVIATURA")
    private String TIU_ABREVIATURA;
    @SerializedName("TIU_SOLICITA_CIIU")
    private String TIU_SOLICITA_CIIU;
    @SerializedName("TIU_PUNTOS_MEDICION_MULTIPLES")
    private String TIU_PUNTOS_MEDICION_MULTIPLES;
    @SerializedName("TIU_MEDIDOR_FUNCION_MULTIPLE")
    private String TIU_MEDIDOR_FUNCION_MULTIPLE;
    @SerializedName("TIU_ACTIVO")
    private String TIU_ACTIVO;
    @SerializedName("TIU_ID_USER")
    private String TIU_ID_USER;
    @SerializedName("TIU_FECHA_UPDATE")
    private String TIU_FECHA_UPDATE;
    @SerializedName("TIU_LOTE_REPLICACION")
    private int TIU_LOTE_REPLICACION;

    public RestTipoUsuario(short TIU_ID, String TIU_DESCRIPCION, byte TIU_TIPO_EMPRESA, String TIU_ABREVIATURA,
                           String TIU_SOLICITA_CIIU, String TIU_PUNTOS_MEDICION_MULTIPLES, String TIU_MEDIDOR_FUNCION_MULTIPLE,
                           String TIU_ACTIVO, String TIU_ID_USER, String TIU_FECHA_UPDATE, int TIU_LOTE_REPLICACION) {
        this.TIU_ID = TIU_ID;
        this.TIU_DESCRIPCION = TIU_DESCRIPCION;
        this.TIU_TIPO_EMPRESA = TIU_TIPO_EMPRESA;
        this.TIU_ABREVIATURA = TIU_ABREVIATURA;
        this.TIU_SOLICITA_CIIU = TIU_SOLICITA_CIIU;
        this.TIU_PUNTOS_MEDICION_MULTIPLES = TIU_PUNTOS_MEDICION_MULTIPLES;
        this.TIU_MEDIDOR_FUNCION_MULTIPLE = TIU_MEDIDOR_FUNCION_MULTIPLE;
        this.TIU_ACTIVO = TIU_ACTIVO;
        this.TIU_ID_USER = TIU_ID_USER;
        this.TIU_FECHA_UPDATE = TIU_FECHA_UPDATE;
        this.TIU_LOTE_REPLICACION = TIU_LOTE_REPLICACION;
    }

    public short getTIU_ID() {
        return TIU_ID;
    }

    public void setTIU_ID(short TIU_ID) {
        this.TIU_ID = TIU_ID;
    }

    public String getTIU_DESCRIPCION() {
        return TIU_DESCRIPCION;
    }

    public void setTIU_DESCRIPCION(String TIU_DESCRIPCION) {
        this.TIU_DESCRIPCION = TIU_DESCRIPCION;
    }

    public byte getTIU_TIPO_EMPRESA() {
        return TIU_TIPO_EMPRESA;
    }

    public void setTIU_TIPO_EMPRESA(byte TIU_TIPO_EMPRESA) {
        this.TIU_TIPO_EMPRESA = TIU_TIPO_EMPRESA;
    }

    public String getTIU_ABREVIATURA() {
        return TIU_ABREVIATURA;
    }

    public void setTIU_ABREVIATURA(String TIU_ABREVIATURA) {
        this.TIU_ABREVIATURA = TIU_ABREVIATURA;
    }

    public String getTIU_SOLICITA_CIIU() {
        return TIU_SOLICITA_CIIU;
    }

    public void setTIU_SOLICITA_CIIU(String TIU_SOLICITA_CIIU) {
        this.TIU_SOLICITA_CIIU = TIU_SOLICITA_CIIU;
    }

    public String getTIU_PUNTOS_MEDICION_MULTIPLES() {
        return TIU_PUNTOS_MEDICION_MULTIPLES;
    }

    public void setTIU_PUNTOS_MEDICION_MULTIPLES(String TIU_PUNTOS_MEDICION_MULTIPLES) {
        this.TIU_PUNTOS_MEDICION_MULTIPLES = TIU_PUNTOS_MEDICION_MULTIPLES;
    }

    public String getTIU_MEDIDOR_FUNCION_MULTIPLE() {
        return TIU_MEDIDOR_FUNCION_MULTIPLE;
    }

    public void setTIU_MEDIDOR_FUNCION_MULTIPLE(String TIU_MEDIDOR_FUNCION_MULTIPLE) {
        this.TIU_MEDIDOR_FUNCION_MULTIPLE = TIU_MEDIDOR_FUNCION_MULTIPLE;
    }

    public String getTIU_ACTIVO() {
        return TIU_ACTIVO;
    }

    public void setTIU_ACTIVO(String TIU_ACTIVO) {
        this.TIU_ACTIVO = TIU_ACTIVO;
    }

    public String getTIU_ID_USER() {
        return TIU_ID_USER;
    }

    public void setTIU_ID_USER(String TIU_ID_USER) {
        this.TIU_ID_USER = TIU_ID_USER;
    }

    public String getTIU_FECHA_UPDATE() {
        return TIU_FECHA_UPDATE;
    }

    public void setTIU_FECHA_UPDATE(String TIU_FECHA_UPDATE) {
        this.TIU_FECHA_UPDATE = TIU_FECHA_UPDATE;
    }

    public int getTIU_LOTE_REPLICACION() {
        return TIU_LOTE_REPLICACION;
    }

    public void setTIU_LOTE_REPLICACION(int TIU_LOTE_REPLICACION) {
        this.TIU_LOTE_REPLICACION = TIU_LOTE_REPLICACION;
    }
}
