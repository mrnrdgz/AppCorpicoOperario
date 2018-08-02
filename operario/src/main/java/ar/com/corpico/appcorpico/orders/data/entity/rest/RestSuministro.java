package ar.com.corpico.appcorpico.orders.data.entity.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrador on 21/03/2018.
 */

public class RestSuministro {
    @SerializedName("SUM_EMPRESA")
    private short SUM_EMPRESA;
    @SerializedName("SUM_CLIENTE")
    private int SUM_CLIENTE;
    @SerializedName("SUM_ID")
    private short SUM_ID;
    @SerializedName("SUM_SUCURSAL")
    private short SUM_SUCURSAL;
    @SerializedName("SUM_GRUPO")
    private short SUM_GRUPO;
    @SerializedName("SUM_RUTA")
    private short SUM_RUTA;
    @SerializedName("SUM_ORDEN_LECTURA")
    private int SUM_ORDEN_LECTURA;
    @SerializedName("SUM_LOCALIDAD")
    private short SUM_LOCALIDAD;
    @SerializedName("SUM_CODIGO_POSTAL")
    private String SUM_CODIGO_POSTAL;
    @SerializedName("SUM_CALLE")
    private String SUM_CALLE;
    @SerializedName("SUM_ALTURA")
    private int SUM_ALTURA;
    @SerializedName("SUM_PISO")
    private String SUM_PISO;
    @SerializedName("SUM_DEPARTAMENTO")
    private String SUM_DEPARTAMENTO;
    @SerializedName("SUM_CALLE_ENTRE1")
    private String SUM_CALLE_ENTRE1;
    @SerializedName("SUM_CALLE_ENTRE2")
    private String SUM_CALLE_ENTRE2;
    @SerializedName("SUM_ANEXO")
    private String SUM_ANEXO;
    @SerializedName("SUM_REFERENCIA_MUNICIPAL")
    private String SUM_REFERENCIA_MUNICIPAL;
    @SerializedName("SUM_CIRCUNSCRIPCION")
    private String SUM_CIRCUNSCRIPCION;
    @SerializedName("SUM_RADIO")
    private String SUM_RADIO;
    @SerializedName("SUM_MANZANA")
    private String SUM_MANZANA;
    @SerializedName("SUM_QUINTA")
    private String SUM_QUINTA;
    @SerializedName("SUM_PARCELA")
    private String SUM_PARCELA;
    @SerializedName("SUM_SUBPARCELA")
    private String SUM_SUBPARCELA;
    @SerializedName("SUM_INQUILINO")
    private String SUM_INQUILINO;
    @SerializedName("SUM_FECHA_VTO_ALQUILER")
    private String SUM_FECHA_VTO_ALQUILER;
    @SerializedName("SUM_CIIU")
    private short SUM_CIIU;
    @SerializedName("SUM_DIRECCION_POSTAL")
    private String SUM_DIRECCION_POSTAL;
    @SerializedName("SUM_GARANTE")
    private String SUM_GARANTE;
    @SerializedName("SUM_MOROSIDAD")
    private String SUM_MOROSIDAD;
    @SerializedName("SUM_FACTURABLE")
    private String SUM_FACTURABLE;
    @SerializedName("SUM_ESTADO_ADM")
    private byte SUM_ESTADO_ADM;
    @SerializedName("SUM_FECHA_ESTADO_ADM")
    private String SUM_FECHA_ESTADO_ADM;
    @SerializedName("SUM_FECHA_CAMBIO_TITULARIDAD")
    private String SUM_FECHA_CAMBIO_TITULARIDAD;
    @SerializedName("SUM_TIENE_CONCEPTOS_PUNTUALES")
    private String SUM_TIENE_CONCEPTOS_PUNTUALES;
    @SerializedName("SUM_VALIDA_PUNTUAL")
    private String SUM_VALIDA_PUNTUAL;
    @SerializedName("SUM_SUMINISTRO_ANTERIOR1")
    private String SUM_SUMINISTRO_ANTERIOR1;
    @SerializedName("SUM_SUMINISTRO_ANTERIOR2")
    private String SUM_SUMINISTRO_ANTERIOR2;
    @SerializedName("SUM_SUMINISTRO_ANTERIOR3")
    private String SUM_SUMINISTRO_ANTERIOR3;
    @SerializedName("SUM_SUMINISTRO_ANTERIOR4")
    private String SUM_SUMINISTRO_ANTERIOR4;
    @SerializedName("SUM_ID_USER")
    private String SUM_ID_USER;
    @SerializedName("SUM_FECHA_UPDATE")
    private String SUM_FECHA_UPDATE;
    @SerializedName("SUM_LOTE_REPLICACION")
    private int SUM_LOTE_REPLICACION;

    public RestSuministro(short SUM_EMPRESA, int SUM_CLIENTE, short SUM_ID, short SUM_SUCURSAL, short SUM_GRUPO,
                          short SUM_RUTA, int SUM_ORDEN_LECTURA, short SUM_LOCALIDAD, String SUM_CODIGO_POSTAL,
                          String SUM_CALLE, int SUM_ALTURA, String SUM_PISO, String SUM_DEPARTAMENTO, String SUM_CALLE_ENTRE1,
                          String SUM_CALLE_ENTRE2, String SUM_ANEXO, String SUM_REFERENCIA_MUNICIPAL, String SUM_CIRCUNSCRIPCION,
                          String SUM_RADIO, String SUM_MANZANA, String SUM_QUINTA, String SUM_PARCELA, String SUM_SUBPARCELA,
                          String SUM_INQUILINO, String SUM_FECHA_VTO_ALQUILER, short SUM_CIIU, String SUM_DIRECCION_POSTAL,
                          String SUM_GARANTE, String SUM_MOROSIDAD, String SUM_FACTURABLE, byte SUM_ESTADO_ADM,
                          String SUM_FECHA_ESTADO_ADM, String SUM_FECHA_CAMBIO_TITULARIDAD,
                          String SUM_TIENE_CONCEPTOS_PUNTUALES, String SUM_VALIDA_PUNTUAL, String SUM_SUMINISTRO_ANTERIOR1,
                          String SUM_SUMINISTRO_ANTERIOR2, String SUM_SUMINISTRO_ANTERIOR3, String SUM_SUMINISTRO_ANTERIOR4,
                          String SUM_ID_USER, String SUM_FECHA_UPDATE, int SUM_LOTE_REPLICACION) {
        this.SUM_EMPRESA = SUM_EMPRESA;
        this.SUM_CLIENTE = SUM_CLIENTE;
        this.SUM_ID = SUM_ID;
        this.SUM_SUCURSAL = SUM_SUCURSAL;
        this.SUM_GRUPO = SUM_GRUPO;
        this.SUM_RUTA = SUM_RUTA;
        this.SUM_ORDEN_LECTURA = SUM_ORDEN_LECTURA;
        this.SUM_LOCALIDAD = SUM_LOCALIDAD;
        this.SUM_CODIGO_POSTAL = SUM_CODIGO_POSTAL;
        this.SUM_CALLE = SUM_CALLE;
        this.SUM_ALTURA = SUM_ALTURA;
        this.SUM_PISO = SUM_PISO;
        this.SUM_DEPARTAMENTO = SUM_DEPARTAMENTO;
        this.SUM_CALLE_ENTRE1 = SUM_CALLE_ENTRE1;
        this.SUM_CALLE_ENTRE2 = SUM_CALLE_ENTRE2;
        this.SUM_ANEXO = SUM_ANEXO;
        this.SUM_REFERENCIA_MUNICIPAL = SUM_REFERENCIA_MUNICIPAL;
        this.SUM_CIRCUNSCRIPCION = SUM_CIRCUNSCRIPCION;
        this.SUM_RADIO = SUM_RADIO;
        this.SUM_MANZANA = SUM_MANZANA;
        this.SUM_QUINTA = SUM_QUINTA;
        this.SUM_PARCELA = SUM_PARCELA;
        this.SUM_SUBPARCELA = SUM_SUBPARCELA;
        this.SUM_INQUILINO = SUM_INQUILINO;
        this.SUM_FECHA_VTO_ALQUILER = SUM_FECHA_VTO_ALQUILER;
        this.SUM_CIIU = SUM_CIIU;
        this.SUM_DIRECCION_POSTAL = SUM_DIRECCION_POSTAL;
        this.SUM_GARANTE = SUM_GARANTE;
        this.SUM_MOROSIDAD = SUM_MOROSIDAD;
        this.SUM_FACTURABLE = SUM_FACTURABLE;
        this.SUM_ESTADO_ADM = SUM_ESTADO_ADM;
        this.SUM_FECHA_ESTADO_ADM = SUM_FECHA_ESTADO_ADM;
        this.SUM_FECHA_CAMBIO_TITULARIDAD = SUM_FECHA_CAMBIO_TITULARIDAD;
        this.SUM_TIENE_CONCEPTOS_PUNTUALES = SUM_TIENE_CONCEPTOS_PUNTUALES;
        this.SUM_VALIDA_PUNTUAL = SUM_VALIDA_PUNTUAL;
        this.SUM_SUMINISTRO_ANTERIOR1 = SUM_SUMINISTRO_ANTERIOR1;
        this.SUM_SUMINISTRO_ANTERIOR2 = SUM_SUMINISTRO_ANTERIOR2;
        this.SUM_SUMINISTRO_ANTERIOR3 = SUM_SUMINISTRO_ANTERIOR3;
        this.SUM_SUMINISTRO_ANTERIOR4 = SUM_SUMINISTRO_ANTERIOR4;
        this.SUM_ID_USER = SUM_ID_USER;
        this.SUM_FECHA_UPDATE = SUM_FECHA_UPDATE;
        this.SUM_LOTE_REPLICACION = SUM_LOTE_REPLICACION;
    }

    public short getSUM_EMPRESA() {
        return SUM_EMPRESA;
    }

    public void setSUM_EMPRESA(short SUM_EMPRESA) {
        this.SUM_EMPRESA = SUM_EMPRESA;
    }

    public int getSUM_CLIENTE() {
        return SUM_CLIENTE;
    }

    public void setSUM_CLIENTE(int SUM_CLIENTE) {
        this.SUM_CLIENTE = SUM_CLIENTE;
    }

    public short getSUM_ID() {
        return SUM_ID;
    }

    public void setSUM_ID(short SUM_ID) {
        this.SUM_ID = SUM_ID;
    }

    public short getSUM_SUCURSAL() {
        return SUM_SUCURSAL;
    }

    public void setSUM_SUCURSAL(short SUM_SUCURSAL) {
        this.SUM_SUCURSAL = SUM_SUCURSAL;
    }

    public short getSUM_GRUPO() {
        return SUM_GRUPO;
    }

    public void setSUM_GRUPO(short SUM_GRUPO) {
        this.SUM_GRUPO = SUM_GRUPO;
    }

    public short getSUM_RUTA() {
        return SUM_RUTA;
    }

    public void setSUM_RUTA(short SUM_RUTA) {
        this.SUM_RUTA = SUM_RUTA;
    }

    public int getSUM_ORDEN_LECTURA() {
        return SUM_ORDEN_LECTURA;
    }

    public void setSUM_ORDEN_LECTURA(int SUM_ORDEN_LECTURA) {
        this.SUM_ORDEN_LECTURA = SUM_ORDEN_LECTURA;
    }

    public short getSUM_LOCALIDAD() {
        return SUM_LOCALIDAD;
    }

    public void setSUM_LOCALIDAD(short SUM_LOCALIDAD) {
        this.SUM_LOCALIDAD = SUM_LOCALIDAD;
    }

    public String getSUM_CODIGO_POSTAL() {
        return SUM_CODIGO_POSTAL;
    }

    public void setSUM_CODIGO_POSTAL(String SUM_CODIGO_POSTAL) {
        this.SUM_CODIGO_POSTAL = SUM_CODIGO_POSTAL;
    }

    public String getSUM_CALLE() {
        return SUM_CALLE;
    }

    public void setSUM_CALLE(String SUM_CALLE) {
        this.SUM_CALLE = SUM_CALLE;
    }

    public int getSUM_ALTURA() {
        return SUM_ALTURA;
    }

    public void setSUM_ALTURA(int SUM_ALTURA) {
        this.SUM_ALTURA = SUM_ALTURA;
    }

    public String getSUM_PISO() {
        return SUM_PISO;
    }

    public void setSUM_PISO(String SUM_PISO) {
        this.SUM_PISO = SUM_PISO;
    }

    public String getSUM_DEPARTAMENTO() {
        return SUM_DEPARTAMENTO;
    }

    public void setSUM_DEPARTAMENTO(String SUM_DEPARTAMENTO) {
        this.SUM_DEPARTAMENTO = SUM_DEPARTAMENTO;
    }

    public String getSUM_CALLE_ENTRE1() {
        return SUM_CALLE_ENTRE1;
    }

    public void setSUM_CALLE_ENTRE1(String SUM_CALLE_ENTRE1) {
        this.SUM_CALLE_ENTRE1 = SUM_CALLE_ENTRE1;
    }

    public String getSUM_CALLE_ENTRE2() {
        return SUM_CALLE_ENTRE2;
    }

    public void setSUM_CALLE_ENTRE2(String SUM_CALLE_ENTRE2) {
        this.SUM_CALLE_ENTRE2 = SUM_CALLE_ENTRE2;
    }

    public String getSUM_ANEXO() {
        return SUM_ANEXO;
    }

    public void setSUM_ANEXO(String SUM_ANEXO) {
        this.SUM_ANEXO = SUM_ANEXO;
    }

    public String getSUM_REFERENCIA_MUNICIPAL() {
        return SUM_REFERENCIA_MUNICIPAL;
    }

    public void setSUM_REFERENCIA_MUNICIPAL(String SUM_REFERENCIA_MUNICIPAL) {
        this.SUM_REFERENCIA_MUNICIPAL = SUM_REFERENCIA_MUNICIPAL;
    }

    public String getSUM_CIRCUNSCRIPCION() {
        return SUM_CIRCUNSCRIPCION;
    }

    public void setSUM_CIRCUNSCRIPCION(String SUM_CIRCUNSCRIPCION) {
        this.SUM_CIRCUNSCRIPCION = SUM_CIRCUNSCRIPCION;
    }

    public String getSUM_RADIO() {
        return SUM_RADIO;
    }

    public void setSUM_RADIO(String SUM_RADIO) {
        this.SUM_RADIO = SUM_RADIO;
    }

    public String getSUM_MANZANA() {
        return SUM_MANZANA;
    }

    public void setSUM_MANZANA(String SUM_MANZANA) {
        this.SUM_MANZANA = SUM_MANZANA;
    }

    public String getSUM_QUINTA() {
        return SUM_QUINTA;
    }

    public void setSUM_QUINTA(String SUM_QUINTA) {
        this.SUM_QUINTA = SUM_QUINTA;
    }

    public String getSUM_PARCELA() {
        return SUM_PARCELA;
    }

    public void setSUM_PARCELA(String SUM_PARCELA) {
        this.SUM_PARCELA = SUM_PARCELA;
    }

    public String getSUM_SUBPARCELA() {
        return SUM_SUBPARCELA;
    }

    public void setSUM_SUBPARCELA(String SUM_SUBPARCELA) {
        this.SUM_SUBPARCELA = SUM_SUBPARCELA;
    }

    public String getSUM_INQUILINO() {
        return SUM_INQUILINO;
    }

    public void setSUM_INQUILINO(String SUM_INQUILINO) {
        this.SUM_INQUILINO = SUM_INQUILINO;
    }

    public String getSUM_FECHA_VTO_ALQUILER() {
        return SUM_FECHA_VTO_ALQUILER;
    }

    public void setSUM_FECHA_VTO_ALQUILER(String SUM_FECHA_VTO_ALQUILER) {
        this.SUM_FECHA_VTO_ALQUILER = SUM_FECHA_VTO_ALQUILER;
    }

    public short getSUM_CIIU() {
        return SUM_CIIU;
    }

    public void setSUM_CIIU(short SUM_CIIU) {
        this.SUM_CIIU = SUM_CIIU;
    }

    public String getSUM_DIRECCION_POSTAL() {
        return SUM_DIRECCION_POSTAL;
    }

    public void setSUM_DIRECCION_POSTAL(String SUM_DIRECCION_POSTAL) {
        this.SUM_DIRECCION_POSTAL = SUM_DIRECCION_POSTAL;
    }

    public String getSUM_GARANTE() {
        return SUM_GARANTE;
    }

    public void setSUM_GARANTE(String SUM_GARANTE) {
        this.SUM_GARANTE = SUM_GARANTE;
    }

    public String getSUM_MOROSIDAD() {
        return SUM_MOROSIDAD;
    }

    public void setSUM_MOROSIDAD(String SUM_MOROSIDAD) {
        this.SUM_MOROSIDAD = SUM_MOROSIDAD;
    }

    public String getSUM_FACTURABLE() {
        return SUM_FACTURABLE;
    }

    public void setSUM_FACTURABLE(String SUM_FACTURABLE) {
        this.SUM_FACTURABLE = SUM_FACTURABLE;
    }

    public byte getSUM_ESTADO_ADM() {
        return SUM_ESTADO_ADM;
    }

    public void setSUM_ESTADO_ADM(byte SUM_ESTADO_ADM) {
        this.SUM_ESTADO_ADM = SUM_ESTADO_ADM;
    }

    public String getSUM_FECHA_ESTADO_ADM() {
        return SUM_FECHA_ESTADO_ADM;
    }

    public void setSUM_FECHA_ESTADO_ADM(String SUM_FECHA_ESTADO_ADM) {
        this.SUM_FECHA_ESTADO_ADM = SUM_FECHA_ESTADO_ADM;
    }

    public String getSUM_FECHA_CAMBIO_TITULARIDAD() {
        return SUM_FECHA_CAMBIO_TITULARIDAD;
    }

    public void setSUM_FECHA_CAMBIO_TITULARIDAD(String SUM_FECHA_CAMBIO_TITULARIDAD) {
        this.SUM_FECHA_CAMBIO_TITULARIDAD = SUM_FECHA_CAMBIO_TITULARIDAD;
    }

    public String getSUM_TIENE_CONCEPTOS_PUNTUALES() {
        return SUM_TIENE_CONCEPTOS_PUNTUALES;
    }

    public void setSUM_TIENE_CONCEPTOS_PUNTUALES(String SUM_TIENE_CONCEPTOS_PUNTUALES) {
        this.SUM_TIENE_CONCEPTOS_PUNTUALES = SUM_TIENE_CONCEPTOS_PUNTUALES;
    }

    public String getSUM_VALIDA_PUNTUAL() {
        return SUM_VALIDA_PUNTUAL;
    }

    public void setSUM_VALIDA_PUNTUAL(String SUM_VALIDA_PUNTUAL) {
        this.SUM_VALIDA_PUNTUAL = SUM_VALIDA_PUNTUAL;
    }

    public String getSUM_SUMINISTRO_ANTERIOR1() {
        return SUM_SUMINISTRO_ANTERIOR1;
    }

    public void setSUM_SUMINISTRO_ANTERIOR1(String SUM_SUMINISTRO_ANTERIOR1) {
        this.SUM_SUMINISTRO_ANTERIOR1 = SUM_SUMINISTRO_ANTERIOR1;
    }

    public String getSUM_SUMINISTRO_ANTERIOR2() {
        return SUM_SUMINISTRO_ANTERIOR2;
    }

    public void setSUM_SUMINISTRO_ANTERIOR2(String SUM_SUMINISTRO_ANTERIOR2) {
        this.SUM_SUMINISTRO_ANTERIOR2 = SUM_SUMINISTRO_ANTERIOR2;
    }

    public String getSUM_SUMINISTRO_ANTERIOR3() {
        return SUM_SUMINISTRO_ANTERIOR3;
    }

    public void setSUM_SUMINISTRO_ANTERIOR3(String SUM_SUMINISTRO_ANTERIOR3) {
        this.SUM_SUMINISTRO_ANTERIOR3 = SUM_SUMINISTRO_ANTERIOR3;
    }

    public String getSUM_SUMINISTRO_ANTERIOR4() {
        return SUM_SUMINISTRO_ANTERIOR4;
    }

    public void setSUM_SUMINISTRO_ANTERIOR4(String SUM_SUMINISTRO_ANTERIOR4) {
        this.SUM_SUMINISTRO_ANTERIOR4 = SUM_SUMINISTRO_ANTERIOR4;
    }

    public String getSUM_ID_USER() {
        return SUM_ID_USER;
    }

    public void setSUM_ID_USER(String SUM_ID_USER) {
        this.SUM_ID_USER = SUM_ID_USER;
    }

    public String getSUM_FECHA_UPDATE() {
        return SUM_FECHA_UPDATE;
    }

    public void setSUM_FECHA_UPDATE(String SUM_FECHA_UPDATE) {
        this.SUM_FECHA_UPDATE = SUM_FECHA_UPDATE;
    }

    public int getSUM_LOTE_REPLICACION() {
        return SUM_LOTE_REPLICACION;
    }

    public void setSUM_LOTE_REPLICACION(int SUM_LOTE_REPLICACION) {
        this.SUM_LOTE_REPLICACION = SUM_LOTE_REPLICACION;
    }
}
