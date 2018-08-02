package ar.com.corpico.appcorpico.orders.data.entity.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrador on 21/03/2018.
 */

public class RestTipoEmpresa {
    @SerializedName("TIE_ID")
    private byte TIE_ID;
    @SerializedName("TIE_DESCRIPCION")
    private String TIE_DESCRIPCION;
    @SerializedName("TIE_ABREVIATURA")
    private String TIE_ABREVIATURA;
    @SerializedName("TIE_ABREVIATURA_ATP")
    private String TIE_ABREVIATURA_ATP;
    @SerializedName("TIE_MUESTRA_CONSUMO_CONVERTIDO")
    private String TIE_MUESTRA_CONSUMO_CONVERTIDO;
    @SerializedName("TIE_FACTOR_CONVERSION_CONSUMO")
    private double TIE_FACTOR_CONVERSION_CONSUMO;
    @SerializedName("TIE_ORDEN")
    private short TIE_ORDEN;
    @SerializedName("TIE_UTILIZA_MODULO_LECTURAS")
    private String TIE_UTILIZA_MODULO_LECTURAS;
    @SerializedName("TIE_USA_TRANSFORMADOR")
    private String TIE_USA_TRANSFORMADOR;
    @SerializedName("TIE_BAJA_PERMITE_CAMBIAR_EST_ADM")
    private String TIE_BAJA_PERMITE_CAMBIAR_EST_ADM;
    @SerializedName("TIE_ACTIVO")
    private String TIE_ACTIVO;
    @SerializedName("TIE_ID_USER")
    private String TIE_ID_USER;
    @SerializedName("TIE_FECHA_UPDATE")
    private String TIE_FECHA_UPDATE;
    @SerializedName("TIE_LOTE_REPLICACION")
    private int TIE_LOTE_REPLICACION;
    @SerializedName("TIE_MODO_CALCULO_TARIFA_CONSUMO")
    private String TIE_MODO_CALCULO_TARIFA_CONSUMO;
    @SerializedName("TIE_AJUSTA_CARGO_FIJO_ALTA_SUMINISTRO")
    private String TIE_AJUSTA_CARGO_FIJO_ALTA_SUMINISTRO;
    @SerializedName("TIE_AJUSTA_ESCALA_CONSUMO_ALTA_SUMINISTRO")
    private String TIE_AJUSTA_ESCALA_CONSUMO_ALTA_SUMINISTRO;
    @SerializedName("TIE_AJUSTA_CARGO_FIJO_BAJA_SUMINISTRO")
    private String TIE_AJUSTA_CARGO_FIJO_BAJA_SUMINISTRO;
    @SerializedName("TIE_AJUSTA_ESCALA_CONSUMO_BAJA_SUMINISTRO")
    private String TIE_AJUSTA_ESCALA_CONSUMO_BAJA_SUMINISTRO;
    @SerializedName("TIE_MEDIDOR_FUNCION")
    private short TIE_MEDIDOR_FUNCION;
    @SerializedName("TIE_COLOR")
    private String TIE_COLOR;
    @SerializedName("TIE_EMPRESA_COBRANZA")
    private short TIE_EMPRESA_COBRANZA;
    @SerializedName("TIE_ESTADO_OPE_BAJA")
    private byte TIE_ESTADO_OPE_BAJA;

    public RestTipoEmpresa(byte TIE_ID, String TIE_DESCRIPCION, String TIE_ABREVIATURA, String TIE_ABREVIATURA_ATP,
                           String TIE_MUESTRA_CONSUMO_CONVERTIDO, double TIE_FACTOR_CONVERSION_CONSUMO,
                           short TIE_ORDEN, String TIE_UTILIZA_MODULO_LECTURAS, String TIE_USA_TRANSFORMADOR,
                           String TIE_BAJA_PERMITE_CAMBIAR_EST_ADM, String TIE_ACTIVO, String TIE_ID_USER,
                           String TIE_FECHA_UPDATE, int TIE_LOTE_REPLICACION, String TIE_MODO_CALCULO_TARIFA_CONSUMO,
                           String TIE_AJUSTA_CARGO_FIJO_ALTA_SUMINISTRO, String TIE_AJUSTA_ESCALA_CONSUMO_ALTA_SUMINISTRO,
                           String TIE_AJUSTA_CARGO_FIJO_BAJA_SUMINISTRO, String TIE_AJUSTA_ESCALA_CONSUMO_BAJA_SUMINISTRO,
                           short TIE_MEDIDOR_FUNCION, String TIE_COLOR, short TIE_EMPRESA_COBRANZA, byte TIE_ESTADO_OPE_BAJA) {
        this.TIE_ID = TIE_ID;
        this.TIE_DESCRIPCION = TIE_DESCRIPCION;
        this.TIE_ABREVIATURA = TIE_ABREVIATURA;
        this.TIE_ABREVIATURA_ATP = TIE_ABREVIATURA_ATP;
        this.TIE_MUESTRA_CONSUMO_CONVERTIDO = TIE_MUESTRA_CONSUMO_CONVERTIDO;
        this.TIE_FACTOR_CONVERSION_CONSUMO = TIE_FACTOR_CONVERSION_CONSUMO;
        this.TIE_ORDEN = TIE_ORDEN;
        this.TIE_UTILIZA_MODULO_LECTURAS = TIE_UTILIZA_MODULO_LECTURAS;
        this.TIE_USA_TRANSFORMADOR = TIE_USA_TRANSFORMADOR;
        this.TIE_BAJA_PERMITE_CAMBIAR_EST_ADM = TIE_BAJA_PERMITE_CAMBIAR_EST_ADM;
        this.TIE_ACTIVO = TIE_ACTIVO;
        this.TIE_ID_USER = TIE_ID_USER;
        this.TIE_FECHA_UPDATE = TIE_FECHA_UPDATE;
        this.TIE_LOTE_REPLICACION = TIE_LOTE_REPLICACION;
        this.TIE_MODO_CALCULO_TARIFA_CONSUMO = TIE_MODO_CALCULO_TARIFA_CONSUMO;
        this.TIE_AJUSTA_CARGO_FIJO_ALTA_SUMINISTRO = TIE_AJUSTA_CARGO_FIJO_ALTA_SUMINISTRO;
        this.TIE_AJUSTA_ESCALA_CONSUMO_ALTA_SUMINISTRO = TIE_AJUSTA_ESCALA_CONSUMO_ALTA_SUMINISTRO;
        this.TIE_AJUSTA_CARGO_FIJO_BAJA_SUMINISTRO = TIE_AJUSTA_CARGO_FIJO_BAJA_SUMINISTRO;
        this.TIE_AJUSTA_ESCALA_CONSUMO_BAJA_SUMINISTRO = TIE_AJUSTA_ESCALA_CONSUMO_BAJA_SUMINISTRO;
        this.TIE_MEDIDOR_FUNCION = TIE_MEDIDOR_FUNCION;
        this.TIE_COLOR = TIE_COLOR;
        this.TIE_EMPRESA_COBRANZA = TIE_EMPRESA_COBRANZA;
        this.TIE_ESTADO_OPE_BAJA = TIE_ESTADO_OPE_BAJA;
    }

    public byte getTIE_ID() {
        return TIE_ID;
    }

    public void setTIE_ID(byte TIE_ID) {
        this.TIE_ID = TIE_ID;
    }

    public String getTIE_DESCRIPCION() {
        return TIE_DESCRIPCION;
    }

    public void setTIE_DESCRIPCION(String TIE_DESCRIPCION) {
        this.TIE_DESCRIPCION = TIE_DESCRIPCION;
    }

    public String getTIE_ABREVIATURA() {
        return TIE_ABREVIATURA;
    }

    public void setTIE_ABREVIATURA(String TIE_ABREVIATURA) {
        this.TIE_ABREVIATURA = TIE_ABREVIATURA;
    }

    public String getTIE_ABREVIATURA_ATP() {
        return TIE_ABREVIATURA_ATP;
    }

    public void setTIE_ABREVIATURA_ATP(String TIE_ABREVIATURA_ATP) {
        this.TIE_ABREVIATURA_ATP = TIE_ABREVIATURA_ATP;
    }

    public String getTIE_MUESTRA_CONSUMO_CONVERTIDO() {
        return TIE_MUESTRA_CONSUMO_CONVERTIDO;
    }

    public void setTIE_MUESTRA_CONSUMO_CONVERTIDO(String TIE_MUESTRA_CONSUMO_CONVERTIDO) {
        this.TIE_MUESTRA_CONSUMO_CONVERTIDO = TIE_MUESTRA_CONSUMO_CONVERTIDO;
    }

    public double getTIE_FACTOR_CONVERSION_CONSUMO() {
        return TIE_FACTOR_CONVERSION_CONSUMO;
    }

    public void setTIE_FACTOR_CONVERSION_CONSUMO(double TIE_FACTOR_CONVERSION_CONSUMO) {
        this.TIE_FACTOR_CONVERSION_CONSUMO = TIE_FACTOR_CONVERSION_CONSUMO;
    }

    public short getTIE_ORDEN() {
        return TIE_ORDEN;
    }

    public void setTIE_ORDEN(short TIE_ORDEN) {
        this.TIE_ORDEN = TIE_ORDEN;
    }

    public String getTIE_UTILIZA_MODULO_LECTURAS() {
        return TIE_UTILIZA_MODULO_LECTURAS;
    }

    public void setTIE_UTILIZA_MODULO_LECTURAS(String TIE_UTILIZA_MODULO_LECTURAS) {
        this.TIE_UTILIZA_MODULO_LECTURAS = TIE_UTILIZA_MODULO_LECTURAS;
    }

    public String getTIE_USA_TRANSFORMADOR() {
        return TIE_USA_TRANSFORMADOR;
    }

    public void setTIE_USA_TRANSFORMADOR(String TIE_USA_TRANSFORMADOR) {
        this.TIE_USA_TRANSFORMADOR = TIE_USA_TRANSFORMADOR;
    }

    public String getTIE_BAJA_PERMITE_CAMBIAR_EST_ADM() {
        return TIE_BAJA_PERMITE_CAMBIAR_EST_ADM;
    }

    public void setTIE_BAJA_PERMITE_CAMBIAR_EST_ADM(String TIE_BAJA_PERMITE_CAMBIAR_EST_ADM) {
        this.TIE_BAJA_PERMITE_CAMBIAR_EST_ADM = TIE_BAJA_PERMITE_CAMBIAR_EST_ADM;
    }

    public String getTIE_ACTIVO() {
        return TIE_ACTIVO;
    }

    public void setTIE_ACTIVO(String TIE_ACTIVO) {
        this.TIE_ACTIVO = TIE_ACTIVO;
    }

    public String getTIE_ID_USER() {
        return TIE_ID_USER;
    }

    public void setTIE_ID_USER(String TIE_ID_USER) {
        this.TIE_ID_USER = TIE_ID_USER;
    }

    public String getTIE_FECHA_UPDATE() {
        return TIE_FECHA_UPDATE;
    }

    public void setTIE_FECHA_UPDATE(String TIE_FECHA_UPDATE) {
        this.TIE_FECHA_UPDATE = TIE_FECHA_UPDATE;
    }

    public int getTIE_LOTE_REPLICACION() {
        return TIE_LOTE_REPLICACION;
    }

    public void setTIE_LOTE_REPLICACION(int TIE_LOTE_REPLICACION) {
        this.TIE_LOTE_REPLICACION = TIE_LOTE_REPLICACION;
    }

    public String getTIE_MODO_CALCULO_TARIFA_CONSUMO() {
        return TIE_MODO_CALCULO_TARIFA_CONSUMO;
    }

    public void setTIE_MODO_CALCULO_TARIFA_CONSUMO(String TIE_MODO_CALCULO_TARIFA_CONSUMO) {
        this.TIE_MODO_CALCULO_TARIFA_CONSUMO = TIE_MODO_CALCULO_TARIFA_CONSUMO;
    }

    public String getTIE_AJUSTA_CARGO_FIJO_ALTA_SUMINISTRO() {
        return TIE_AJUSTA_CARGO_FIJO_ALTA_SUMINISTRO;
    }

    public void setTIE_AJUSTA_CARGO_FIJO_ALTA_SUMINISTRO(String TIE_AJUSTA_CARGO_FIJO_ALTA_SUMINISTRO) {
        this.TIE_AJUSTA_CARGO_FIJO_ALTA_SUMINISTRO = TIE_AJUSTA_CARGO_FIJO_ALTA_SUMINISTRO;
    }

    public String getTIE_AJUSTA_ESCALA_CONSUMO_ALTA_SUMINISTRO() {
        return TIE_AJUSTA_ESCALA_CONSUMO_ALTA_SUMINISTRO;
    }

    public void setTIE_AJUSTA_ESCALA_CONSUMO_ALTA_SUMINISTRO(String TIE_AJUSTA_ESCALA_CONSUMO_ALTA_SUMINISTRO) {
        this.TIE_AJUSTA_ESCALA_CONSUMO_ALTA_SUMINISTRO = TIE_AJUSTA_ESCALA_CONSUMO_ALTA_SUMINISTRO;
    }

    public String getTIE_AJUSTA_CARGO_FIJO_BAJA_SUMINISTRO() {
        return TIE_AJUSTA_CARGO_FIJO_BAJA_SUMINISTRO;
    }

    public void setTIE_AJUSTA_CARGO_FIJO_BAJA_SUMINISTRO(String TIE_AJUSTA_CARGO_FIJO_BAJA_SUMINISTRO) {
        this.TIE_AJUSTA_CARGO_FIJO_BAJA_SUMINISTRO = TIE_AJUSTA_CARGO_FIJO_BAJA_SUMINISTRO;
    }

    public String getTIE_AJUSTA_ESCALA_CONSUMO_BAJA_SUMINISTRO() {
        return TIE_AJUSTA_ESCALA_CONSUMO_BAJA_SUMINISTRO;
    }

    public void setTIE_AJUSTA_ESCALA_CONSUMO_BAJA_SUMINISTRO(String TIE_AJUSTA_ESCALA_CONSUMO_BAJA_SUMINISTRO) {
        this.TIE_AJUSTA_ESCALA_CONSUMO_BAJA_SUMINISTRO = TIE_AJUSTA_ESCALA_CONSUMO_BAJA_SUMINISTRO;
    }

    public short getTIE_MEDIDOR_FUNCION() {
        return TIE_MEDIDOR_FUNCION;
    }

    public void setTIE_MEDIDOR_FUNCION(short TIE_MEDIDOR_FUNCION) {
        this.TIE_MEDIDOR_FUNCION = TIE_MEDIDOR_FUNCION;
    }

    public String getTIE_COLOR() {
        return TIE_COLOR;
    }

    public void setTIE_COLOR(String TIE_COLOR) {
        this.TIE_COLOR = TIE_COLOR;
    }

    public short getTIE_EMPRESA_COBRANZA() {
        return TIE_EMPRESA_COBRANZA;
    }

    public void setTIE_EMPRESA_COBRANZA(short TIE_EMPRESA_COBRANZA) {
        this.TIE_EMPRESA_COBRANZA = TIE_EMPRESA_COBRANZA;
    }

    public byte getTIE_ESTADO_OPE_BAJA() {
        return TIE_ESTADO_OPE_BAJA;
    }

    public void setTIE_ESTADO_OPE_BAJA(byte TIE_ESTADO_OPE_BAJA) {
        this.TIE_ESTADO_OPE_BAJA = TIE_ESTADO_OPE_BAJA;
    }
}
