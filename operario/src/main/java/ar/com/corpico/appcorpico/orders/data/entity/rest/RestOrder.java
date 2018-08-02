package ar.com.corpico.appcorpico.orders.data.entity.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrador on 16/04/2018.
 */

public class RestOrder {
    @SerializedName("fecha_solicitud")
    private String fecha_solicitud;
    @SerializedName("numero")
    private Integer numero;
    @SerializedName("tipo_trabajo")
    private String tipo_trabajo;
    @SerializedName("motivo_trabajo")
    private String motivo_trabajo;
    @SerializedName("observacion_al_operario")
    private String observacion_al_operario;
    @SerializedName("asociado")
    private Integer asociado;
    @SerializedName("suministro")
    private short suministro;
    @SerializedName("titular")
    private String titular;
    @SerializedName("calle")
    private String calle;
    @SerializedName("altura")
    private int altura;
    @SerializedName("piso")
    private String piso;
    @SerializedName("dpto")
    private String dpto;
    @SerializedName("localidad")
    private String localidad;
    @SerializedName("anexo")
    private String anexo;
    @SerializedName("latitud")
    private String latitud;
    @SerializedName("longitud")
    private String longitud;
    @SerializedName("grupo")
    private Integer grupo;
    @SerializedName("ruta")
    private Integer ruta;
    @SerializedName("orden_lectura")
    private Integer orden_lectura;
    @SerializedName("tipo_usuario")
    private String tipo_usuario;
    @SerializedName("tipo_consumo")
    private String tipo_consumo;
    @SerializedName("potencia_declarada")
    private Integer potencia_declarada;
    @SerializedName("medidor")
    private String medidor;
    @SerializedName("medidor_marca")
    private String medidor_marca;
    @SerializedName("medidor_modelo")
    private String medidor_modelo;
    @SerializedName("factorM")
    private Double factorM;
    @SerializedName("capacidad")
    private String capacidad;
    @SerializedName("tension")
    private String tension;
    @SerializedName("estado")
    private String estado;
    @SerializedName("fecha_update")
    private String fecha_update;
    @SerializedName("sector")
    private Integer sector;
    @SerializedName("fecha_asignacion")
    private String fecha_asignacion;
    @SerializedName("user_asigno")
    private String user_asigno;
    @SerializedName("fecha_culminacion")
    private String fecha_culminacion;
    @SerializedName("cuadrilla")
    private Integer cuadrilla;
    @SerializedName("observacion_del_ope")
    private String observacion_del_ope;
    @SerializedName("user_realizacion")
    private Integer user_realizacion;
    @SerializedName("user_id")
    private String user_id;
    public RestOrder(){

    }

    public RestOrder(String fecha_solicitud, Integer numero, String tipo_trabajo, String motivo_trabajo,
                     String observacion_al_operario, Integer asociado, short suministro, String titular, String calle,
                     int altura, String piso, String dpto, String localidad, String anexo, String latitud, String longitud,
                     Integer grupo, Integer ruta, Integer orden_lectura, String tipo_usuario, String tipo_consumo,
                     Integer potencia_declarada, String medidor, String medidor_marca, String medidor_modelo,
                     Double factorM, String capacidad, String tension, String estado, String fecha_update,
                     Integer sector,String fecha_asignacion, String user_asigno, String fecha_culminacion, Integer cuadrilla,
                     String observacion_del_ope,Integer user_realizacion,String user_id) {
        this.fecha_solicitud = fecha_solicitud;
        this.numero = numero;
        this.tipo_trabajo = tipo_trabajo;
        this.motivo_trabajo = motivo_trabajo;
        this.observacion_al_operario = observacion_al_operario;
        this.asociado = asociado;
        this.suministro = suministro;
        this.titular = titular;
        this.calle = calle;
        this.altura = altura;
        this.piso = piso;
        this.dpto = dpto;
        this.localidad = localidad;
        this.anexo = anexo;
        this.latitud = latitud;
        this.longitud = longitud;
        this.grupo = grupo;
        this.ruta = ruta;
        this.orden_lectura = orden_lectura;
        this.tipo_usuario = tipo_usuario;
        this.tipo_consumo = tipo_consumo;
        this.potencia_declarada = potencia_declarada;
        this.medidor = medidor;
        this.medidor_marca = medidor_marca;
        this.medidor_modelo = medidor_modelo;
        this.factorM = factorM;
        this.capacidad = capacidad;
        this.tension = tension;
        this.estado = estado;
        this.fecha_update = fecha_update;
        this.sector = sector;
        this.fecha_asignacion = fecha_asignacion;
        this.user_asigno = user_asigno;
        this.fecha_culminacion = fecha_culminacion;
        this.cuadrilla = cuadrilla;
        this.observacion_del_ope = observacion_del_ope;
        this.user_realizacion = user_realizacion;
        this.user_id = user_id;
    }

    public String getFecha_solicitud() {
        return fecha_solicitud;
    }

    public void setFecha_solicitud(String fecha_solicitud) {
        this.fecha_solicitud = fecha_solicitud;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getTipo_trabajo() {
        return tipo_trabajo;
    }

    public void setTipo_trabajo(String tipo_trabajo) {
        this.tipo_trabajo = tipo_trabajo;
    }

    public String getMotivo_trabajo() {
        return motivo_trabajo;
    }

    public void setMotivo_trabajo(String motivo_trabajo) {
        this.motivo_trabajo = motivo_trabajo;
    }

    public String getObservacion_al_operario() {
        return observacion_al_operario;
    }

    public void setObservacion_al_operario(String observacion_al_operario) {
        this.observacion_al_operario = observacion_al_operario;
    }

    public Integer getAsociado() {
        return asociado;
    }

    public void setAsociado(Integer asociado) {
        this.asociado = asociado;
    }

    public short getSuministro() {
        return suministro;
    }

    public void setSuministro(short suministro) {
        this.suministro = suministro;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getDpto() {
        return dpto;
    }

    public void setDpto(String dpto) {
        this.dpto = dpto;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getAnexo() {
        return anexo;
    }

    public void setAnexo(String anexo) {
        this.anexo = anexo;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public Integer getGrupo() {
        return grupo;
    }

    public void setGrupo(Integer grupo) {
        this.grupo = grupo;
    }

    public Integer getRuta() {
        return ruta;
    }

    public void setRuta(Integer ruta) {
        this.ruta = ruta;
    }

    public Integer getOrden_lectura() {
        return orden_lectura;
    }

    public void setOrden_lectura(Integer orden_lectura) {
        this.orden_lectura = orden_lectura;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public String getTipo_consumo() {
        return tipo_consumo;
    }

    public void setTipo_consumo(String tipo_consumo) {
        this.tipo_consumo = tipo_consumo;
    }

    public Integer getPotencia_declarada() {
        return potencia_declarada;
    }

    public void setPotencia_declarada(Integer potencia_declarada) {
        this.potencia_declarada = potencia_declarada;
    }

    public String getMedidor() {
        return medidor;
    }

    public void setMedidor(String medidor) {
        this.medidor = medidor;
    }

    public String getMedidor_marca() {
        return medidor_marca;
    }

    public void setMedidor_marca(String medidor_marca) {
        this.medidor_marca = medidor_marca;
    }

    public String getMedidor_modelo() {
        return medidor_modelo;
    }

    public void setMedidor_modelo(String medidor_modelo) {
        this.medidor_modelo = medidor_modelo;
    }

    public Double getFactorM() {
        return factorM;
    }

    public void setFactorM(Double factorM) {
        this.factorM = factorM;
    }

    public String getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }

    public String getTension() {
        return tension;
    }

    public void setTension(String tension) {
        this.tension = tension;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha_update() {
        return fecha_update;
    }

    public void setFecha_update(String fecha_update) {
        this.fecha_update = fecha_update;
    }

    public Integer getSector() {
        return sector;
    }

    public void setSector(Integer sector) {
        this.sector = sector;
    }

    public String getFecha_asignacion() {
        return fecha_asignacion;
    }

    public void setFecha_asignacion(String fecha_asignacion) {
        this.fecha_asignacion = fecha_asignacion;
    }

    public String getUser_asigno() {
        return user_asigno;
    }

    public void setUser_asigno(String user_asigno) {
        this.user_asigno = user_asigno;
    }

    public String getFecha_culminacion() {
        return fecha_culminacion;
    }

    public void setFecha_culminacion(String fecha_culminacion) {
        this.fecha_culminacion = fecha_culminacion;
    }

    public Integer getCuadrilla() {
        return cuadrilla;
    }

    public void setCuadrilla(Integer cuadrilla) {
        this.cuadrilla = cuadrilla;
    }

    public String getObservacion_del_ope() {
        return observacion_del_ope;
    }

    public void setObservacion_del_ope(String observacion_del_ope) {
        this.observacion_del_ope = observacion_del_ope;
    }

    public Integer getUser_realizacion() {
        return user_realizacion;
    }

    public void setUser_realizacion(Integer user_realizacion) {
        this.user_realizacion = user_realizacion;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
