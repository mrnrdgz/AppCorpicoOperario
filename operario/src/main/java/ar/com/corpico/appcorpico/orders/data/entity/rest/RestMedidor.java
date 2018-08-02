package ar.com.corpico.appcorpico.orders.data.entity.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrador on 21/03/2018.
 */

public class RestMedidor {
    @SerializedName("MED_ID")
    private int MED_ID;
    @SerializedName("MED_EMPRESA")
    private short MED_EMPRESA;
    @SerializedName("MED_ALMACEN")
    private short MED_ALMACEN;
    @SerializedName("MED_ESTADO")
    private byte MED_ESTADO;
    @SerializedName("MED_MARCA")
    private short MED_MARCA;
    @SerializedName("MED_TIPOSERIE")
    private String MED_TIPOSERIE;
    @SerializedName("MED_SERIE")
    private String MED_SERIE;
    @SerializedName("MED_MODELO")
    private short MED_MODELO;
    @SerializedName("MED_CLASE")
    private byte MED_CLASE;
    @SerializedName("MED_TIPO")
    private short MED_TIPO;
    @SerializedName("MED_RUEDAS")
    private byte MED_RUEDAS;
    @SerializedName("MED_ANO_FABRICA")
    private short MED_ANO_FABRICA;
    @SerializedName("MED_ANOS_VIDA")
    private byte MED_ANOS_VIDA;
    @SerializedName("MED_FECHA_ENTRADA")
    private String MED_FECHA_ENTRADA;
    @SerializedName("MED_VTO_GARANTIA")
    private String MED_VTO_GARANTIA;
    @SerializedName("MED_FACTOR")
    private double MED_FACTOR;
    @SerializedName("MED_POSEE_LEDS")
    private String MED_POSEE_LEDS;
    @SerializedName("MED_MOTIVO_BAJA")
    private byte MED_MOTIVO_BAJA;
    @SerializedName("MED_FECHA_BAJA")
    private String MED_FECHA_BAJA;
    @SerializedName("MED_ID_USER")
    private String MED_ID_USER;
    @SerializedName("MED_FECHA_UPDATE")
    private String MED_FECHA_UPDATE;
    @SerializedName("MED_LOTE_REPLICACION")
    private int MED_LOTE_REPLICACION;

    public RestMedidor(int MED_ID, short MED_EMPRESA, short MED_ALMACEN, byte MED_ESTADO, short MED_MARCA,
                       String MED_TIPOSERIE, String MED_SERIE, short MED_MODELO, byte MED_CLASE, short MED_TIPO,
                       byte MED_RUEDAS, short MED_ANO_FABRICA, byte MED_ANOS_VIDA, String MED_FECHA_ENTRADA,
                       String MED_VTO_GARANTIA, double MED_FACTOR, String MED_POSEE_LEDS, byte MED_MOTIVO_BAJA,
                       String MED_FECHA_BAJA, String MED_ID_USER, String MED_FECHA_UPDATE, int MED_LOTE_REPLICACION) {
        this.MED_ID = MED_ID;
        this.MED_EMPRESA = MED_EMPRESA;
        this.MED_ALMACEN = MED_ALMACEN;
        this.MED_ESTADO = MED_ESTADO;
        this.MED_MARCA = MED_MARCA;
        this.MED_TIPOSERIE = MED_TIPOSERIE;
        this.MED_SERIE = MED_SERIE;
        this.MED_MODELO = MED_MODELO;
        this.MED_CLASE = MED_CLASE;
        this.MED_TIPO = MED_TIPO;
        this.MED_RUEDAS = MED_RUEDAS;
        this.MED_ANO_FABRICA = MED_ANO_FABRICA;
        this.MED_ANOS_VIDA = MED_ANOS_VIDA;
        this.MED_FECHA_ENTRADA = MED_FECHA_ENTRADA;
        this.MED_VTO_GARANTIA = MED_VTO_GARANTIA;
        this.MED_FACTOR = MED_FACTOR;
        this.MED_POSEE_LEDS = MED_POSEE_LEDS;
        this.MED_MOTIVO_BAJA = MED_MOTIVO_BAJA;
        this.MED_FECHA_BAJA = MED_FECHA_BAJA;
        this.MED_ID_USER = MED_ID_USER;
        this.MED_FECHA_UPDATE = MED_FECHA_UPDATE;
        this.MED_LOTE_REPLICACION = MED_LOTE_REPLICACION;
    }

    public int getMED_ID() {
        return MED_ID;
    }

    public void setMED_ID(int MED_ID) {
        this.MED_ID = MED_ID;
    }

    public short getMED_EMPRESA() {
        return MED_EMPRESA;
    }

    public void setMED_EMPRESA(short MED_EMPRESA) {
        this.MED_EMPRESA = MED_EMPRESA;
    }

    public short getMED_ALMACEN() {
        return MED_ALMACEN;
    }

    public void setMED_ALMACEN(short MED_ALMACEN) {
        this.MED_ALMACEN = MED_ALMACEN;
    }

    public byte getMED_ESTADO() {
        return MED_ESTADO;
    }

    public void setMED_ESTADO(byte MED_ESTADO) {
        this.MED_ESTADO = MED_ESTADO;
    }

    public short getMED_MARCA() {
        return MED_MARCA;
    }

    public void setMED_MARCA(short MED_MARCA) {
        this.MED_MARCA = MED_MARCA;
    }

    public String getMED_TIPOSERIE() {
        return MED_TIPOSERIE;
    }

    public void setMED_TIPOSERIE(String MED_TIPOSERIE) {
        this.MED_TIPOSERIE = MED_TIPOSERIE;
    }

    public String getMED_SERIE() {
        return MED_SERIE;
    }

    public void setMED_SERIE(String MED_SERIE) {
        this.MED_SERIE = MED_SERIE;
    }

    public short getMED_MODELO() {
        return MED_MODELO;
    }

    public void setMED_MODELO(short MED_MODELO) {
        this.MED_MODELO = MED_MODELO;
    }

    public byte getMED_CLASE() {
        return MED_CLASE;
    }

    public void setMED_CLASE(byte MED_CLASE) {
        this.MED_CLASE = MED_CLASE;
    }

    public short getMED_TIPO() {
        return MED_TIPO;
    }

    public void setMED_TIPO(short MED_TIPO) {
        this.MED_TIPO = MED_TIPO;
    }

    public byte getMED_RUEDAS() {
        return MED_RUEDAS;
    }

    public void setMED_RUEDAS(byte MED_RUEDAS) {
        this.MED_RUEDAS = MED_RUEDAS;
    }

    public short getMED_ANO_FABRICA() {
        return MED_ANO_FABRICA;
    }

    public void setMED_ANO_FABRICA(short MED_ANO_FABRICA) {
        this.MED_ANO_FABRICA = MED_ANO_FABRICA;
    }

    public byte getMED_ANOS_VIDA() {
        return MED_ANOS_VIDA;
    }

    public void setMED_ANOS_VIDA(byte MED_ANOS_VIDA) {
        this.MED_ANOS_VIDA = MED_ANOS_VIDA;
    }

    public String getMED_FECHA_ENTRADA() {
        return MED_FECHA_ENTRADA;
    }

    public void setMED_FECHA_ENTRADA(String MED_FECHA_ENTRADA) {
        this.MED_FECHA_ENTRADA = MED_FECHA_ENTRADA;
    }

    public String getMED_VTO_GARANTIA() {
        return MED_VTO_GARANTIA;
    }

    public void setMED_VTO_GARANTIA(String MED_VTO_GARANTIA) {
        this.MED_VTO_GARANTIA = MED_VTO_GARANTIA;
    }

    public double getMED_FACTOR() {
        return MED_FACTOR;
    }

    public void setMED_FACTOR(double MED_FACTOR) {
        this.MED_FACTOR = MED_FACTOR;
    }

    public String getMED_POSEE_LEDS() {
        return MED_POSEE_LEDS;
    }

    public void setMED_POSEE_LEDS(String MED_POSEE_LEDS) {
        this.MED_POSEE_LEDS = MED_POSEE_LEDS;
    }

    public byte getMED_MOTIVO_BAJA() {
        return MED_MOTIVO_BAJA;
    }

    public void setMED_MOTIVO_BAJA(byte MED_MOTIVO_BAJA) {
        this.MED_MOTIVO_BAJA = MED_MOTIVO_BAJA;
    }

    public String getMED_FECHA_BAJA() {
        return MED_FECHA_BAJA;
    }

    public void setMED_FECHA_BAJA(String MED_FECHA_BAJA) {
        this.MED_FECHA_BAJA = MED_FECHA_BAJA;
    }

    public String getMED_ID_USER() {
        return MED_ID_USER;
    }

    public void setMED_ID_USER(String MED_ID_USER) {
        this.MED_ID_USER = MED_ID_USER;
    }

    public String getMED_FECHA_UPDATE() {
        return MED_FECHA_UPDATE;
    }

    public void setMED_FECHA_UPDATE(String MED_FECHA_UPDATE) {
        this.MED_FECHA_UPDATE = MED_FECHA_UPDATE;
    }

    public int getMED_LOTE_REPLICACION() {
        return MED_LOTE_REPLICACION;
    }

    public void setMED_LOTE_REPLICACION(int MED_LOTE_REPLICACION) {
        this.MED_LOTE_REPLICACION = MED_LOTE_REPLICACION;
    }
}
