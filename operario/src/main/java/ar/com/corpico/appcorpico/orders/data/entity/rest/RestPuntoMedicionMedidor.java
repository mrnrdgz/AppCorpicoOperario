package ar.com.corpico.appcorpico.orders.data.entity.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrador on 21/03/2018.
 */

public class RestPuntoMedicionMedidor {
    @SerializedName("PMM_EMPRESA")
    private short PMM_EMPRESA;
    @SerializedName("PMM_CLIENTE")
    private int PMM_CLIENTE;
    @SerializedName("PMM_SUMINISTRO")
    private short PMM_SUMINISTRO;
    @SerializedName("PMM_TIPO_EMPRESA")
    private byte PMM_TIPO_EMPRESA;
    @SerializedName("PMM_PUNTO_MEDICION")
    private short PMM_PUNTO_MEDICION;
    @SerializedName("PMM_MEDIDOR")
    private int PMM_MEDIDOR;
    @SerializedName("PMM_UBICACION_HABITACULO")
    private byte PMM_UBICACION_HABITACULO;
    @SerializedName("PMM_ADVERTENCIA")
    private short PMM_ADVERTENCIA;
    @SerializedName("PMM_ES_FACTURABLE")
    private String PMM_ES_FACTURABLE;
    @SerializedName("PMM_ID_USER")
    private String PMM_ID_USER;
    @SerializedName("PMM_FECHA_UPDATE")
    private String PMM_FECHA_UPDATE;
    @SerializedName("PMM_LOTE_REPLICACION")
    private int PMM_LOTE_REPLICACION;

    public RestPuntoMedicionMedidor(short PMM_EMPRESA, int PMM_CLIENTE, short PMM_SUMINISTRO, byte PMM_TIPO_EMPRESA,
                                    short PMM_PUNTO_MEDICION, int PMM_MEDIDOR, byte PMM_UBICACION_HABITACULO,
                                    short PMM_ADVERTENCIA, String PMM_ES_FACTURABLE, String PMM_ID_USER,
                                    String PMM_FECHA_UPDATE, int PMM_LOTE_REPLICACION) {
        this.PMM_EMPRESA = PMM_EMPRESA;
        this.PMM_CLIENTE = PMM_CLIENTE;
        this.PMM_SUMINISTRO = PMM_SUMINISTRO;
        this.PMM_TIPO_EMPRESA = PMM_TIPO_EMPRESA;
        this.PMM_PUNTO_MEDICION = PMM_PUNTO_MEDICION;
        this.PMM_MEDIDOR = PMM_MEDIDOR;
        this.PMM_UBICACION_HABITACULO = PMM_UBICACION_HABITACULO;
        this.PMM_ADVERTENCIA = PMM_ADVERTENCIA;
        this.PMM_ES_FACTURABLE = PMM_ES_FACTURABLE;
        this.PMM_ID_USER = PMM_ID_USER;
        this.PMM_FECHA_UPDATE = PMM_FECHA_UPDATE;
        this.PMM_LOTE_REPLICACION = PMM_LOTE_REPLICACION;
    }

    public short getPMM_EMPRESA() {
        return PMM_EMPRESA;
    }

    public void setPMM_EMPRESA(short PMM_EMPRESA) {
        this.PMM_EMPRESA = PMM_EMPRESA;
    }

    public int getPMM_CLIENTE() {
        return PMM_CLIENTE;
    }

    public void setPMM_CLIENTE(int PMM_CLIENTE) {
        this.PMM_CLIENTE = PMM_CLIENTE;
    }

    public short getPMM_SUMINISTRO() {
        return PMM_SUMINISTRO;
    }

    public void setPMM_SUMINISTRO(short PMM_SUMINISTRO) {
        this.PMM_SUMINISTRO = PMM_SUMINISTRO;
    }

    public byte getPMM_TIPO_EMPRESA() {
        return PMM_TIPO_EMPRESA;
    }

    public void setPMM_TIPO_EMPRESA(byte PMM_TIPO_EMPRESA) {
        this.PMM_TIPO_EMPRESA = PMM_TIPO_EMPRESA;
    }

    public short getPMM_PUNTO_MEDICION() {
        return PMM_PUNTO_MEDICION;
    }

    public void setPMM_PUNTO_MEDICION(short PMM_PUNTO_MEDICION) {
        this.PMM_PUNTO_MEDICION = PMM_PUNTO_MEDICION;
    }

    public int getPMM_MEDIDOR() {
        return PMM_MEDIDOR;
    }

    public void setPMM_MEDIDOR(int PMM_MEDIDOR) {
        this.PMM_MEDIDOR = PMM_MEDIDOR;
    }

    public byte getPMM_UBICACION_HABITACULO() {
        return PMM_UBICACION_HABITACULO;
    }

    public void setPMM_UBICACION_HABITACULO(byte PMM_UBICACION_HABITACULO) {
        this.PMM_UBICACION_HABITACULO = PMM_UBICACION_HABITACULO;
    }

    public short getPMM_ADVERTENCIA() {
        return PMM_ADVERTENCIA;
    }

    public void setPMM_ADVERTENCIA(short PMM_ADVERTENCIA) {
        this.PMM_ADVERTENCIA = PMM_ADVERTENCIA;
    }

    public String getPMM_ES_FACTURABLE() {
        return PMM_ES_FACTURABLE;
    }

    public void setPMM_ES_FACTURABLE(String PMM_ES_FACTURABLE) {
        this.PMM_ES_FACTURABLE = PMM_ES_FACTURABLE;
    }

    public String getPMM_ID_USER() {
        return PMM_ID_USER;
    }

    public void setPMM_ID_USER(String PMM_ID_USER) {
        this.PMM_ID_USER = PMM_ID_USER;
    }

    public String getPMM_FECHA_UPDATE() {
        return PMM_FECHA_UPDATE;
    }

    public void setPMM_FECHA_UPDATE(String PMM_FECHA_UPDATE) {
        this.PMM_FECHA_UPDATE = PMM_FECHA_UPDATE;
    }

    public int getPMM_LOTE_REPLICACION() {
        return PMM_LOTE_REPLICACION;
    }

    public void setPMM_LOTE_REPLICACION(int PMM_LOTE_REPLICACION) {
        this.PMM_LOTE_REPLICACION = PMM_LOTE_REPLICACION;
    }
}
