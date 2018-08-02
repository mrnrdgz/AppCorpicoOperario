package ar.com.corpico.appcorpico.orders.data.entity.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrador on 21/03/2018.
 */

public class RestSuministroTipoEmpresa {
    @SerializedName("STE_EMPRESA")
    private short STE_EMPRESA;
    @SerializedName("STE_CLIENTE")
    private int STE_CLIENTE;
    @SerializedName("STE_SUMINISTRO")
    private short STE_SUMINISTRO;
    @SerializedName("STE_TIPO_EMPRESA")
    private byte STE_TIPO_EMPRESA;
    @SerializedName("STE_FECHA_INGRESO")
    private String STE_FECHA_INGRESO;
    @SerializedName("STE_ESTADO_OPE")
    private byte STE_ESTADO_OPE;
    @SerializedName("STE_FECHA_ESTADO_OPE")
    private String STE_FECHA_ESTADO_OPE;
    @SerializedName("STE_TIPO_CONSUMO")
    private short STE_TIPO_CONSUMO;
    @SerializedName("STE_TIPO_USUARIO")
    private short STE_TIPO_USUARIO;
    @SerializedName("STE_TIPO_SERVICIO")
    private byte STE_TIPO_SERVICIO;
    @SerializedName("STE_TIPO_CONEXION")
    private short STE_TIPO_CONEXION;
    @SerializedName("STE_POTENCIA")
    private short STE_POTENCIA;
    @SerializedName("STE_POSEE_PROYECTO")
    private String STE_POSEE_PROYECTO;
    @SerializedName("STE_RESERVA_CAPACIDAD")
    private int STE_RESERVA_CAPACIDAD;
    @SerializedName("STE_UNIDAD_PROVEEDORA")
    private short STE_UNIDAD_PROVEEDORA;
    @SerializedName("STE_SUB_UNIDAD_PROVEEDORA")
    private short STE_SUB_UNIDAD_PROVEEDORA;
    @SerializedName("STE_TRANSFORMADOR")
    private short STE_TRANSFORMADOR;
    @SerializedName("STE_ID_USER")
    private String STE_ID_USER;
    @SerializedName("STE_FECHA_UPDATE")
    private String STE_FECHA_UPDATE;
    @SerializedName("STE_LOTE_REPLICACION")
    private int STE_LOTE_REPLICACION;

    public RestSuministroTipoEmpresa(short STE_EMPRESA, int STE_CLIENTE, short STE_SUMINISTRO, byte STE_TIPO_EMPRESA,
                                     String STE_FECHA_INGRESO, byte STE_ESTADO_OPE, String STE_FECHA_ESTADO_OPE,
                                     short STE_TIPO_CONSUMO, short STE_TIPO_USUARIO, byte STE_TIPO_SERVICIO,
                                     short STE_TIPO_CONEXION, short STE_POTENCIA, String STE_POSEE_PROYECTO,
                                     int STE_RESERVA_CAPACIDAD, short STE_UNIDAD_PROVEEDORA, short STE_SUB_UNIDAD_PROVEEDORA,
                                     short STE_TRANSFORMADOR, String STE_ID_USER, String STE_FECHA_UPDATE,
                                     int STE_LOTE_REPLICACION) {
        this.STE_EMPRESA = STE_EMPRESA;
        this.STE_CLIENTE = STE_CLIENTE;
        this.STE_SUMINISTRO = STE_SUMINISTRO;
        this.STE_TIPO_EMPRESA = STE_TIPO_EMPRESA;
        this.STE_FECHA_INGRESO = STE_FECHA_INGRESO;
        this.STE_ESTADO_OPE = STE_ESTADO_OPE;
        this.STE_FECHA_ESTADO_OPE = STE_FECHA_ESTADO_OPE;
        this.STE_TIPO_CONSUMO = STE_TIPO_CONSUMO;
        this.STE_TIPO_USUARIO = STE_TIPO_USUARIO;
        this.STE_TIPO_SERVICIO = STE_TIPO_SERVICIO;
        this.STE_TIPO_CONEXION = STE_TIPO_CONEXION;
        this.STE_POTENCIA = STE_POTENCIA;
        this.STE_POSEE_PROYECTO = STE_POSEE_PROYECTO;
        this.STE_RESERVA_CAPACIDAD = STE_RESERVA_CAPACIDAD;
        this.STE_UNIDAD_PROVEEDORA = STE_UNIDAD_PROVEEDORA;
        this.STE_SUB_UNIDAD_PROVEEDORA = STE_SUB_UNIDAD_PROVEEDORA;
        this.STE_TRANSFORMADOR = STE_TRANSFORMADOR;
        this.STE_ID_USER = STE_ID_USER;
        this.STE_FECHA_UPDATE = STE_FECHA_UPDATE;
        this.STE_LOTE_REPLICACION = STE_LOTE_REPLICACION;
    }

    public short getSTE_EMPRESA() {
        return STE_EMPRESA;
    }

    public void setSTE_EMPRESA(short STE_EMPRESA) {
        this.STE_EMPRESA = STE_EMPRESA;
    }

    public int getSTE_CLIENTE() {
        return STE_CLIENTE;
    }

    public void setSTE_CLIENTE(int STE_CLIENTE) {
        this.STE_CLIENTE = STE_CLIENTE;
    }

    public short getSTE_SUMINISTRO() {
        return STE_SUMINISTRO;
    }

    public void setSTE_SUMINISTRO(short STE_SUMINISTRO) {
        this.STE_SUMINISTRO = STE_SUMINISTRO;
    }

    public byte getSTE_TIPO_EMPRESA() {
        return STE_TIPO_EMPRESA;
    }

    public void setSTE_TIPO_EMPRESA(byte STE_TIPO_EMPRESA) {
        this.STE_TIPO_EMPRESA = STE_TIPO_EMPRESA;
    }

    public String getSTE_FECHA_INGRESO() {
        return STE_FECHA_INGRESO;
    }

    public void setSTE_FECHA_INGRESO(String STE_FECHA_INGRESO) {
        this.STE_FECHA_INGRESO = STE_FECHA_INGRESO;
    }

    public byte getSTE_ESTADO_OPE() {
        return STE_ESTADO_OPE;
    }

    public void setSTE_ESTADO_OPE(byte STE_ESTADO_OPE) {
        this.STE_ESTADO_OPE = STE_ESTADO_OPE;
    }

    public String getSTE_FECHA_ESTADO_OPE() {
        return STE_FECHA_ESTADO_OPE;
    }

    public void setSTE_FECHA_ESTADO_OPE(String STE_FECHA_ESTADO_OPE) {
        this.STE_FECHA_ESTADO_OPE = STE_FECHA_ESTADO_OPE;
    }

    public short getSTE_TIPO_CONSUMO() {
        return STE_TIPO_CONSUMO;
    }

    public void setSTE_TIPO_CONSUMO(short STE_TIPO_CONSUMO) {
        this.STE_TIPO_CONSUMO = STE_TIPO_CONSUMO;
    }

    public short getSTE_TIPO_USUARIO() {
        return STE_TIPO_USUARIO;
    }

    public void setSTE_TIPO_USUARIO(short STE_TIPO_USUARIO) {
        this.STE_TIPO_USUARIO = STE_TIPO_USUARIO;
    }

    public byte getSTE_TIPO_SERVICIO() {
        return STE_TIPO_SERVICIO;
    }

    public void setSTE_TIPO_SERVICIO(byte STE_TIPO_SERVICIO) {
        this.STE_TIPO_SERVICIO = STE_TIPO_SERVICIO;
    }

    public short getSTE_TIPO_CONEXION() {
        return STE_TIPO_CONEXION;
    }

    public void setSTE_TIPO_CONEXION(short STE_TIPO_CONEXION) {
        this.STE_TIPO_CONEXION = STE_TIPO_CONEXION;
    }

    public short getSTE_POTENCIA() {
        return STE_POTENCIA;
    }

    public void setSTE_POTENCIA(short STE_POTENCIA) {
        this.STE_POTENCIA = STE_POTENCIA;
    }

    public String getSTE_POSEE_PROYECTO() {
        return STE_POSEE_PROYECTO;
    }

    public void setSTE_POSEE_PROYECTO(String STE_POSEE_PROYECTO) {
        this.STE_POSEE_PROYECTO = STE_POSEE_PROYECTO;
    }

    public int getSTE_RESERVA_CAPACIDAD() {
        return STE_RESERVA_CAPACIDAD;
    }

    public void setSTE_RESERVA_CAPACIDAD(int STE_RESERVA_CAPACIDAD) {
        this.STE_RESERVA_CAPACIDAD = STE_RESERVA_CAPACIDAD;
    }

    public short getSTE_UNIDAD_PROVEEDORA() {
        return STE_UNIDAD_PROVEEDORA;
    }

    public void setSTE_UNIDAD_PROVEEDORA(short STE_UNIDAD_PROVEEDORA) {
        this.STE_UNIDAD_PROVEEDORA = STE_UNIDAD_PROVEEDORA;
    }

    public short getSTE_SUB_UNIDAD_PROVEEDORA() {
        return STE_SUB_UNIDAD_PROVEEDORA;
    }

    public void setSTE_SUB_UNIDAD_PROVEEDORA(short STE_SUB_UNIDAD_PROVEEDORA) {
        this.STE_SUB_UNIDAD_PROVEEDORA = STE_SUB_UNIDAD_PROVEEDORA;
    }

    public short getSTE_TRANSFORMADOR() {
        return STE_TRANSFORMADOR;
    }

    public void setSTE_TRANSFORMADOR(short STE_TRANSFORMADOR) {
        this.STE_TRANSFORMADOR = STE_TRANSFORMADOR;
    }

    public String getSTE_ID_USER() {
        return STE_ID_USER;
    }

    public void setSTE_ID_USER(String STE_ID_USER) {
        this.STE_ID_USER = STE_ID_USER;
    }

    public String getSTE_FECHA_UPDATE() {
        return STE_FECHA_UPDATE;
    }

    public void setSTE_FECHA_UPDATE(String STE_FECHA_UPDATE) {
        this.STE_FECHA_UPDATE = STE_FECHA_UPDATE;
    }

    public int getSTE_LOTE_REPLICACION() {
        return STE_LOTE_REPLICACION;
    }

    public void setSTE_LOTE_REPLICACION(int STE_LOTE_REPLICACION) {
        this.STE_LOTE_REPLICACION = STE_LOTE_REPLICACION;
    }
}
