package ar.com.corpico.appcorpico.orders.data.entity.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrador on 21/03/2018.
 */

public class RestTipoConsumo {
    @SerializedName("TIC_ID")
    private short TIC_ID;
    @SerializedName("TIC_DESCRIPCION")
    private String TIC_DESCRIPCION;
    @SerializedName("TIC_TIPO_EMPRESA")
    private byte TIC_TIPO_EMPRESA;
    @SerializedName("TIC_ABREVIATURA")
    private String TIC_ABREVIATURA;
    @SerializedName("TIC_TIPO_CONSUMO")
    private String TIC_TIPO_CONSUMO;
    @SerializedName("TIC_TARIFA_CONSUMO")
    private String TIC_TARIFA_CONSUMO;
    @SerializedName("TIC_TARIFA_RESERVA")
    private String TIC_TARIFA_RESERVA;
    @SerializedName("TIC_CARGO_FIJO")
    private String TIC_CARGO_FIJO;
    @SerializedName("TIC_FACTURA_MINIMA")
    private String TIC_FACTURA_MINIMA;
    @SerializedName("TIC_TRANSPORTE")
    private String TIC_TRANSPORTE;
    @SerializedName("TIC_IMPUTACION_CONTABLE")
    private String TIC_IMPUTACION_CONTABLE;
    @SerializedName("TIC_UTILIZA_FACTOR_PRESION")
    private String TIC_UTILIZA_FACTOR_PRESION;
    @SerializedName("TIC_UTILIZA_TRANSFORMADOR")
    private String TIC_UTILIZA_TRANSFORMADOR;
    @SerializedName("TIC_CARGO_FIJO_DESCUENTO")
    private String TIC_CARGO_FIJO_DESCUENTO;
    @SerializedName("TIC_TARIFA_CONSUMO_DESCUENTO")
    private String TIC_TARIFA_CONSUMO_DESCUENTO;
    @SerializedName("TIC_ACTIVO")
    private String TIC_ACTIVO;
    @SerializedName("TIC_ID_USER")
    private String TIC_ID_USER;
    @SerializedName("TIC_FECHA_UPDATE")
    private String TIC_FECHA_UPDATE;
    @SerializedName("TIC_LOTE_REPLICACION")
    private int TIC_LOTE_REPLICACION;
    @SerializedName("TIC_SITUACION_IVA")
    private byte TIC_SITUACION_IVA;
    @SerializedName("TIC_PORCENTAJE_CONTROL_CONSUMO_REDUCIDO")
    private double TIC_PORCENTAJE_CONTROL_CONSUMO_REDUCIDO;
    @SerializedName("TIC_PORCENTAJE_CONTROL_CONSUMO_ELEVADO")
    private double TIC_PORCENTAJE_CONTROL_CONSUMO_ELEVADO;
    @SerializedName("TIC_CONSUMO_MINIMO_CONTROL")
    private double TIC_CONSUMO_MINIMO_CONTROL;
    @SerializedName("TIC_MUESTRA_EN_ATP")
    private String TIC_MUESTRA_EN_ATP;
    @SerializedName("TIC_MUESTRA_EN_ADMIN")
    private String TIC_MUESTRA_EN_ADMIN;

    public RestTipoConsumo(short TIC_ID, String TIC_DESCRIPCION, byte TIC_TIPO_EMPRESA, String TIC_ABREVIATURA,
                           String TIC_TIPO_CONSUMO, String TIC_TARIFA_CONSUMO, String TIC_TARIFA_RESERVA,
                           String TIC_CARGO_FIJO, String TIC_FACTURA_MINIMA, String TIC_TRANSPORTE,
                           String TIC_IMPUTACION_CONTABLE, String TIC_UTILIZA_FACTOR_PRESION, String TIC_UTILIZA_TRANSFORMADOR,
                           String TIC_CARGO_FIJO_DESCUENTO, String TIC_TARIFA_CONSUMO_DESCUENTO, String TIC_ACTIVO,
                           String TIC_ID_USER, String TIC_FECHA_UPDATE, int TIC_LOTE_REPLICACION, byte TIC_SITUACION_IVA,
                           double TIC_PORCENTAJE_CONTROL_CONSUMO_REDUCIDO, double TIC_PORCENTAJE_CONTROL_CONSUMO_ELEVADO,
                           double TIC_CONSUMO_MINIMO_CONTROL, String TIC_MUESTRA_EN_ATP, String TIC_MUESTRA_EN_ADMIN) {
        this.TIC_ID = TIC_ID;
        this.TIC_DESCRIPCION = TIC_DESCRIPCION;
        this.TIC_TIPO_EMPRESA = TIC_TIPO_EMPRESA;
        this.TIC_ABREVIATURA = TIC_ABREVIATURA;
        this.TIC_TIPO_CONSUMO = TIC_TIPO_CONSUMO;
        this.TIC_TARIFA_CONSUMO = TIC_TARIFA_CONSUMO;
        this.TIC_TARIFA_RESERVA = TIC_TARIFA_RESERVA;
        this.TIC_CARGO_FIJO = TIC_CARGO_FIJO;
        this.TIC_FACTURA_MINIMA = TIC_FACTURA_MINIMA;
        this.TIC_TRANSPORTE = TIC_TRANSPORTE;
        this.TIC_IMPUTACION_CONTABLE = TIC_IMPUTACION_CONTABLE;
        this.TIC_UTILIZA_FACTOR_PRESION = TIC_UTILIZA_FACTOR_PRESION;
        this.TIC_UTILIZA_TRANSFORMADOR = TIC_UTILIZA_TRANSFORMADOR;
        this.TIC_CARGO_FIJO_DESCUENTO = TIC_CARGO_FIJO_DESCUENTO;
        this.TIC_TARIFA_CONSUMO_DESCUENTO = TIC_TARIFA_CONSUMO_DESCUENTO;
        this.TIC_ACTIVO = TIC_ACTIVO;
        this.TIC_ID_USER = TIC_ID_USER;
        this.TIC_FECHA_UPDATE = TIC_FECHA_UPDATE;
        this.TIC_LOTE_REPLICACION = TIC_LOTE_REPLICACION;
        this.TIC_SITUACION_IVA = TIC_SITUACION_IVA;
        this.TIC_PORCENTAJE_CONTROL_CONSUMO_REDUCIDO = TIC_PORCENTAJE_CONTROL_CONSUMO_REDUCIDO;
        this.TIC_PORCENTAJE_CONTROL_CONSUMO_ELEVADO = TIC_PORCENTAJE_CONTROL_CONSUMO_ELEVADO;
        this.TIC_CONSUMO_MINIMO_CONTROL = TIC_CONSUMO_MINIMO_CONTROL;
        this.TIC_MUESTRA_EN_ATP = TIC_MUESTRA_EN_ATP;
        this.TIC_MUESTRA_EN_ADMIN = TIC_MUESTRA_EN_ADMIN;
    }

    public short getTIC_ID() {
        return TIC_ID;
    }

    public void setTIC_ID(short TIC_ID) {
        this.TIC_ID = TIC_ID;
    }

    public String getTIC_DESCRIPCION() {
        return TIC_DESCRIPCION;
    }

    public void setTIC_DESCRIPCION(String TIC_DESCRIPCION) {
        this.TIC_DESCRIPCION = TIC_DESCRIPCION;
    }

    public byte getTIC_TIPO_EMPRESA() {
        return TIC_TIPO_EMPRESA;
    }

    public void setTIC_TIPO_EMPRESA(byte TIC_TIPO_EMPRESA) {
        this.TIC_TIPO_EMPRESA = TIC_TIPO_EMPRESA;
    }

    public String getTIC_ABREVIATURA() {
        return TIC_ABREVIATURA;
    }

    public void setTIC_ABREVIATURA(String TIC_ABREVIATURA) {
        this.TIC_ABREVIATURA = TIC_ABREVIATURA;
    }

    public String getTIC_TIPO_CONSUMO() {
        return TIC_TIPO_CONSUMO;
    }

    public void setTIC_TIPO_CONSUMO(String TIC_TIPO_CONSUMO) {
        this.TIC_TIPO_CONSUMO = TIC_TIPO_CONSUMO;
    }

    public String getTIC_TARIFA_CONSUMO() {
        return TIC_TARIFA_CONSUMO;
    }

    public void setTIC_TARIFA_CONSUMO(String TIC_TARIFA_CONSUMO) {
        this.TIC_TARIFA_CONSUMO = TIC_TARIFA_CONSUMO;
    }

    public String getTIC_TARIFA_RESERVA() {
        return TIC_TARIFA_RESERVA;
    }

    public void setTIC_TARIFA_RESERVA(String TIC_TARIFA_RESERVA) {
        this.TIC_TARIFA_RESERVA = TIC_TARIFA_RESERVA;
    }

    public String getTIC_CARGO_FIJO() {
        return TIC_CARGO_FIJO;
    }

    public void setTIC_CARGO_FIJO(String TIC_CARGO_FIJO) {
        this.TIC_CARGO_FIJO = TIC_CARGO_FIJO;
    }

    public String getTIC_FACTURA_MINIMA() {
        return TIC_FACTURA_MINIMA;
    }

    public void setTIC_FACTURA_MINIMA(String TIC_FACTURA_MINIMA) {
        this.TIC_FACTURA_MINIMA = TIC_FACTURA_MINIMA;
    }

    public String getTIC_TRANSPORTE() {
        return TIC_TRANSPORTE;
    }

    public void setTIC_TRANSPORTE(String TIC_TRANSPORTE) {
        this.TIC_TRANSPORTE = TIC_TRANSPORTE;
    }

    public String getTIC_IMPUTACION_CONTABLE() {
        return TIC_IMPUTACION_CONTABLE;
    }

    public void setTIC_IMPUTACION_CONTABLE(String TIC_IMPUTACION_CONTABLE) {
        this.TIC_IMPUTACION_CONTABLE = TIC_IMPUTACION_CONTABLE;
    }

    public String getTIC_UTILIZA_FACTOR_PRESION() {
        return TIC_UTILIZA_FACTOR_PRESION;
    }

    public void setTIC_UTILIZA_FACTOR_PRESION(String TIC_UTILIZA_FACTOR_PRESION) {
        this.TIC_UTILIZA_FACTOR_PRESION = TIC_UTILIZA_FACTOR_PRESION;
    }

    public String getTIC_UTILIZA_TRANSFORMADOR() {
        return TIC_UTILIZA_TRANSFORMADOR;
    }

    public void setTIC_UTILIZA_TRANSFORMADOR(String TIC_UTILIZA_TRANSFORMADOR) {
        this.TIC_UTILIZA_TRANSFORMADOR = TIC_UTILIZA_TRANSFORMADOR;
    }

    public String getTIC_CARGO_FIJO_DESCUENTO() {
        return TIC_CARGO_FIJO_DESCUENTO;
    }

    public void setTIC_CARGO_FIJO_DESCUENTO(String TIC_CARGO_FIJO_DESCUENTO) {
        this.TIC_CARGO_FIJO_DESCUENTO = TIC_CARGO_FIJO_DESCUENTO;
    }

    public String getTIC_TARIFA_CONSUMO_DESCUENTO() {
        return TIC_TARIFA_CONSUMO_DESCUENTO;
    }

    public void setTIC_TARIFA_CONSUMO_DESCUENTO(String TIC_TARIFA_CONSUMO_DESCUENTO) {
        this.TIC_TARIFA_CONSUMO_DESCUENTO = TIC_TARIFA_CONSUMO_DESCUENTO;
    }

    public String getTIC_ACTIVO() {
        return TIC_ACTIVO;
    }

    public void setTIC_ACTIVO(String TIC_ACTIVO) {
        this.TIC_ACTIVO = TIC_ACTIVO;
    }

    public String getTIC_ID_USER() {
        return TIC_ID_USER;
    }

    public void setTIC_ID_USER(String TIC_ID_USER) {
        this.TIC_ID_USER = TIC_ID_USER;
    }

    public String getTIC_FECHA_UPDATE() {
        return TIC_FECHA_UPDATE;
    }

    public void setTIC_FECHA_UPDATE(String TIC_FECHA_UPDATE) {
        this.TIC_FECHA_UPDATE = TIC_FECHA_UPDATE;
    }

    public int getTIC_LOTE_REPLICACION() {
        return TIC_LOTE_REPLICACION;
    }

    public void setTIC_LOTE_REPLICACION(int TIC_LOTE_REPLICACION) {
        this.TIC_LOTE_REPLICACION = TIC_LOTE_REPLICACION;
    }

    public byte getTIC_SITUACION_IVA() {
        return TIC_SITUACION_IVA;
    }

    public void setTIC_SITUACION_IVA(byte TIC_SITUACION_IVA) {
        this.TIC_SITUACION_IVA = TIC_SITUACION_IVA;
    }

    public double getTIC_PORCENTAJE_CONTROL_CONSUMO_REDUCIDO() {
        return TIC_PORCENTAJE_CONTROL_CONSUMO_REDUCIDO;
    }

    public void setTIC_PORCENTAJE_CONTROL_CONSUMO_REDUCIDO(double TIC_PORCENTAJE_CONTROL_CONSUMO_REDUCIDO) {
        this.TIC_PORCENTAJE_CONTROL_CONSUMO_REDUCIDO = TIC_PORCENTAJE_CONTROL_CONSUMO_REDUCIDO;
    }

    public double getTIC_PORCENTAJE_CONTROL_CONSUMO_ELEVADO() {
        return TIC_PORCENTAJE_CONTROL_CONSUMO_ELEVADO;
    }

    public void setTIC_PORCENTAJE_CONTROL_CONSUMO_ELEVADO(double TIC_PORCENTAJE_CONTROL_CONSUMO_ELEVADO) {
        this.TIC_PORCENTAJE_CONTROL_CONSUMO_ELEVADO = TIC_PORCENTAJE_CONTROL_CONSUMO_ELEVADO;
    }

    public double getTIC_CONSUMO_MINIMO_CONTROL() {
        return TIC_CONSUMO_MINIMO_CONTROL;
    }

    public void setTIC_CONSUMO_MINIMO_CONTROL(double TIC_CONSUMO_MINIMO_CONTROL) {
        this.TIC_CONSUMO_MINIMO_CONTROL = TIC_CONSUMO_MINIMO_CONTROL;
    }

    public String getTIC_MUESTRA_EN_ATP() {
        return TIC_MUESTRA_EN_ATP;
    }

    public void setTIC_MUESTRA_EN_ATP(String TIC_MUESTRA_EN_ATP) {
        this.TIC_MUESTRA_EN_ATP = TIC_MUESTRA_EN_ATP;
    }

    public String getTIC_MUESTRA_EN_ADMIN() {
        return TIC_MUESTRA_EN_ADMIN;
    }

    public void setTIC_MUESTRA_EN_ADMIN(String TIC_MUESTRA_EN_ADMIN) {
        this.TIC_MUESTRA_EN_ADMIN = TIC_MUESTRA_EN_ADMIN;
    }
}
