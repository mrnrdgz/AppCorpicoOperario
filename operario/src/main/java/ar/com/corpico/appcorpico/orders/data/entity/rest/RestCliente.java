package ar.com.corpico.appcorpico.orders.data.entity.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrador on 21/03/2018.
 */

public class RestCliente {
    @SerializedName("CLI_EMPRESA")
    private short CLI_EMPRESA;
    @SerializedName("CLI_ID")
    private int CLI_ID;
    @SerializedName("CLI_FECHA_INGRESO")
    private String CLI_FECHA_INGRESO;
    @SerializedName("CLI_TITULAR")
    private String CLI_TITULAR;
    @SerializedName("CLI_LOCALIDAD_RESIDENCIA")
    private short CLI_LOCALIDAD_RESIDENCIA;
    @SerializedName("CLI_CODIGO_POSTAL_RESIDENCIA")
    private String CLI_CODIGO_POSTAL_RESIDENCIA;
    @SerializedName("CLI_CALLE_RESIDENCIA")
    private String CLI_CALLE_RESIDENCIA;
    @SerializedName("CLI_ALTURA_RESIDENCIA")
    private int CLI_ALTURA_RESIDENCIA;
    @SerializedName("CLI_PISO_RESIDENCIA")
    private String CLI_PISO_RESIDENCIA;
    @SerializedName("CLI_DEPARTAMENTO_RESIDENCIA")
    private String CLI_DEPARTAMENTO_RESIDENCIA;
    @SerializedName("CLI_TELEFONO_RESIDENCIA")
    private String CLI_TELEFONO_RESIDENCIA;
    @SerializedName("CLI_TELEFONO_CELULAR")
    private String CLI_TELEFONO_CELULAR;
    @SerializedName("CLI_FAX")
    private String CLI_FAX;
    @SerializedName("CLI_E_MAIL")
    private String CLI_E_MAIL;
    @SerializedName("CLI_LOCALIDAD_LABORAL")
    private short CLI_LOCALIDAD_LABORAL;
    @SerializedName("CLI_CODIGO_POSTAL_LABORAL")
    private String CLI_CODIGO_POSTAL_LABORAL;
    @SerializedName("CLI_CALLE_LABORAL")
    private String CLI_CALLE_LABORAL;
    @SerializedName("CLI_ALTURA_LABORAL")
    private int CLI_ALTURA_LABORAL;
    @SerializedName("CLI_PISO_LABORAL")
    private String CLI_PISO_LABORAL;
    @SerializedName("CLI_DEPARTAMENTO_LABORAL")
    private String CLI_DEPARTAMENTO_LABORAL;
    @SerializedName("CLI_ANEXO")
    private String CLI_ANEXO;
    @SerializedName("CLI_TELEFONO_LABORAL")
    private String CLI_TELEFONO_LABORAL;
    @SerializedName("CLI_SITUACION_IVA")
    private byte CLI_SITUACION_IVA;
    @SerializedName("CLI_CUIT")
    private String CLI_CUIT;
    @SerializedName("CLI_SITUACION_IIBB")
    private byte CLI_SITUACION_IIBB;
    @SerializedName("CLI_NUMERO_IIBB")
    private String CLI_NUMERO_IIBB;
    @SerializedName("CLI_EXENCION_PERCEPCION_IVA")
    private double CLI_EXENCION_PERCEPCION_IVA;
    @SerializedName("CLI_EXENCION_NACIONAL")
    private double CLI_EXENCION_NACIONAL;
    @SerializedName("CLI_EXENCION_PROVINCIAL")
    private double CLI_EXENCION_PROVINCIAL;
    @SerializedName("CLI_EXENCION_MUNICIPAL")
    private double CLI_EXENCION_MUNICIPAL;
    @SerializedName("CLI_POTENCIAL")
    private String CLI_POTENCIAL;
    @SerializedName("CLI_CLASIFICACION")
    private short CLI_CLASIFICACION;
    @SerializedName("CLI_ESTADO_CIVIL")
    private short CLI_ESTADO_CIVIL;
    @SerializedName("CLI_PROFESION")
    private short CLI_PROFESION;
    @SerializedName("CLI_FECHA_NACIMIENTO")
    private String CLI_FECHA_NACIMIENTO;
    @SerializedName("CLI_NACIONALIDAD")
    private String CLI_NACIONALIDAD;
    @SerializedName("CLI_LUGAR_NACIMIENTO")
    private String CLI_LUGAR_NACIMIENTO;
    @SerializedName("CLI_SEXO")
    private String CLI_SEXO;
    @SerializedName("CLI_ESTADO")
    private byte CLI_ESTADO;
    @SerializedName("CLI_CLAVE_PERSONAL")
    private String CLI_CLAVE_PERSONAL;
    @SerializedName("CLI_ID_USER")
    private String CLI_ID_USER;
    @SerializedName("CLI_FECHA_UPDATE")
    private String CLI_FECHA_UPDATE;
    @SerializedName("CLI_LOTE_REPLICACION")
    private int CLI_LOTE_REPLICACION;

    public RestCliente(short CLI_EMPRESA, int CLI_ID, String CLI_FECHA_INGRESO, String CLI_TITULAR,
                       short CLI_LOCALIDAD_RESIDENCIA, String CLI_CODIGO_POSTAL_RESIDENCIA,
                       String CLI_CALLE_RESIDENCIA, int CLI_ALTURA_RESIDENCIA,
                       String CLI_PISO_RESIDENCIA, String CLI_DEPARTAMENTO_RESIDENCIA, String CLI_TELEFONO_RESIDENCIA,
                       String CLI_TELEFONO_CELULAR, String CLI_FAX, String CLI_E_MAIL, short CLI_LOCALIDAD_LABORAL,
                       String CLI_CODIGO_POSTAL_LABORAL, String CLI_CALLE_LABORAL, int CLI_ALTURA_LABORAL,
                       String CLI_PISO_LABORAL, String CLI_DEPARTAMENTO_LABORAL, String CLI_ANEXO,
                       String CLI_TELEFONO_LABORAL, byte CLI_SITUACION_IVA, String CLI_CUIT, byte CLI_SITUACION_IIBB,
                       String CLI_NUMERO_IIBB, double CLI_EXENCION_PERCEPCION_IVA, double CLI_EXENCION_NACIONAL,
                       double CLI_EXENCION_PROVINCIAL, double CLI_EXENCION_MUNICIPAL, String CLI_POTENCIAL,
                       short CLI_CLASIFICACION, short CLI_ESTADO_CIVIL, short CLI_PROFESION, String CLI_FECHA_NACIMIENTO,
                       String CLI_NACIONALIDAD, String CLI_LUGAR_NACIMIENTO, String CLI_SEXO, byte CLI_ESTADO,
                       String CLI_CLAVE_PERSONAL, String CLI_ID_USER, String CLI_FECHA_UPDATE, int CLI_LOTE_REPLICACION) {
        this.CLI_EMPRESA = CLI_EMPRESA;
        this.CLI_ID = CLI_ID;
        this.CLI_FECHA_INGRESO = CLI_FECHA_INGRESO;
        this.CLI_TITULAR = CLI_TITULAR;
        this.CLI_LOCALIDAD_RESIDENCIA = CLI_LOCALIDAD_RESIDENCIA;
        this.CLI_CODIGO_POSTAL_RESIDENCIA = CLI_CODIGO_POSTAL_RESIDENCIA;
        this.CLI_CALLE_RESIDENCIA = CLI_CALLE_RESIDENCIA;
        this.CLI_ALTURA_RESIDENCIA = CLI_ALTURA_RESIDENCIA;
        this.CLI_PISO_RESIDENCIA = CLI_PISO_RESIDENCIA;
        this.CLI_DEPARTAMENTO_RESIDENCIA = CLI_DEPARTAMENTO_RESIDENCIA;
        this.CLI_TELEFONO_RESIDENCIA = CLI_TELEFONO_RESIDENCIA;
        this.CLI_TELEFONO_CELULAR = CLI_TELEFONO_CELULAR;
        this.CLI_FAX = CLI_FAX;
        this.CLI_E_MAIL = CLI_E_MAIL;
        this.CLI_LOCALIDAD_LABORAL = CLI_LOCALIDAD_LABORAL;
        this.CLI_CODIGO_POSTAL_LABORAL = CLI_CODIGO_POSTAL_LABORAL;
        this.CLI_CALLE_LABORAL = CLI_CALLE_LABORAL;
        this.CLI_ALTURA_LABORAL = CLI_ALTURA_LABORAL;
        this.CLI_PISO_LABORAL = CLI_PISO_LABORAL;
        this.CLI_DEPARTAMENTO_LABORAL = CLI_DEPARTAMENTO_LABORAL;
        this.CLI_ANEXO = CLI_ANEXO;
        this.CLI_TELEFONO_LABORAL = CLI_TELEFONO_LABORAL;
        this.CLI_SITUACION_IVA = CLI_SITUACION_IVA;
        this.CLI_CUIT = CLI_CUIT;
        this.CLI_SITUACION_IIBB = CLI_SITUACION_IIBB;
        this.CLI_NUMERO_IIBB = CLI_NUMERO_IIBB;
        this.CLI_EXENCION_PERCEPCION_IVA = CLI_EXENCION_PERCEPCION_IVA;
        this.CLI_EXENCION_NACIONAL = CLI_EXENCION_NACIONAL;
        this.CLI_EXENCION_PROVINCIAL = CLI_EXENCION_PROVINCIAL;
        this.CLI_EXENCION_MUNICIPAL = CLI_EXENCION_MUNICIPAL;
        this.CLI_POTENCIAL = CLI_POTENCIAL;
        this.CLI_CLASIFICACION = CLI_CLASIFICACION;
        this.CLI_ESTADO_CIVIL = CLI_ESTADO_CIVIL;
        this.CLI_PROFESION = CLI_PROFESION;
        this.CLI_FECHA_NACIMIENTO = CLI_FECHA_NACIMIENTO;
        this.CLI_NACIONALIDAD = CLI_NACIONALIDAD;
        this.CLI_LUGAR_NACIMIENTO = CLI_LUGAR_NACIMIENTO;
        this.CLI_SEXO = CLI_SEXO;
        this.CLI_ESTADO = CLI_ESTADO;
        this.CLI_CLAVE_PERSONAL = CLI_CLAVE_PERSONAL;
        this.CLI_ID_USER = CLI_ID_USER;
        this.CLI_FECHA_UPDATE = CLI_FECHA_UPDATE;
        this.CLI_LOTE_REPLICACION = CLI_LOTE_REPLICACION;
    }

    public short getCLI_EMPRESA() {
        return CLI_EMPRESA;
    }

    public void setCLI_EMPRESA(short CLI_EMPRESA) {
        this.CLI_EMPRESA = CLI_EMPRESA;
    }

    public int getCLI_ID() {
        return CLI_ID;
    }

    public void setCLI_ID(int CLI_ID) {
        this.CLI_ID = CLI_ID;
    }

    public String getCLI_FECHA_INGRESO() {
        return CLI_FECHA_INGRESO;
    }

    public void setCLI_FECHA_INGRESO(String CLI_FECHA_INGRESO) {
        this.CLI_FECHA_INGRESO = CLI_FECHA_INGRESO;
    }

    public String getCLI_TITULAR() {
        return CLI_TITULAR;
    }

    public void setCLI_TITULAR(String CLI_TITULAR) {
        this.CLI_TITULAR = CLI_TITULAR;
    }

    public short getCLI_LOCALIDAD_RESIDENCIA() {
        return CLI_LOCALIDAD_RESIDENCIA;
    }

    public void setCLI_LOCALIDAD_RESIDENCIA(short CLI_LOCALIDAD_RESIDENCIA) {
        this.CLI_LOCALIDAD_RESIDENCIA = CLI_LOCALIDAD_RESIDENCIA;
    }

    public String getCLI_CODIGO_POSTAL_RESIDENCIA() {
        return CLI_CODIGO_POSTAL_RESIDENCIA;
    }

    public void setCLI_CODIGO_POSTAL_RESIDENCIA(String CLI_CODIGO_POSTAL_RESIDENCIA) {
        this.CLI_CODIGO_POSTAL_RESIDENCIA = CLI_CODIGO_POSTAL_RESIDENCIA;
    }

    public String getCLI_CALLE_RESIDENCIA() {
        return CLI_CALLE_RESIDENCIA;
    }

    public void setCLI_CALLE_RESIDENCIA(String CLI_CALLE_RESIDENCIA) {
        this.CLI_CALLE_RESIDENCIA = CLI_CALLE_RESIDENCIA;
    }

    public int getCLI_ALTURA_RESIDENCIA() {
        return CLI_ALTURA_RESIDENCIA;
    }

    public void setCLI_ALTURA_RESIDENCIA(int CLI_ALTURA_RESIDENCIA) {
        this.CLI_ALTURA_RESIDENCIA = CLI_ALTURA_RESIDENCIA;
    }

    public String getCLI_PISO_RESIDENCIA() {
        return CLI_PISO_RESIDENCIA;
    }

    public void setCLI_PISO_RESIDENCIA(String CLI_PISO_RESIDENCIA) {
        this.CLI_PISO_RESIDENCIA = CLI_PISO_RESIDENCIA;
    }

    public String getCLI_DEPARTAMENTO_RESIDENCIA() {
        return CLI_DEPARTAMENTO_RESIDENCIA;
    }

    public void setCLI_DEPARTAMENTO_RESIDENCIA(String CLI_DEPARTAMENTO_RESIDENCIA) {
        this.CLI_DEPARTAMENTO_RESIDENCIA = CLI_DEPARTAMENTO_RESIDENCIA;
    }

    public String getCLI_TELEFONO_RESIDENCIA() {
        return CLI_TELEFONO_RESIDENCIA;
    }

    public void setCLI_TELEFONO_RESIDENCIA(String CLI_TELEFONO_RESIDENCIA) {
        this.CLI_TELEFONO_RESIDENCIA = CLI_TELEFONO_RESIDENCIA;
    }

    public String getCLI_TELEFONO_CELULAR() {
        return CLI_TELEFONO_CELULAR;
    }

    public void setCLI_TELEFONO_CELULAR(String CLI_TELEFONO_CELULAR) {
        this.CLI_TELEFONO_CELULAR = CLI_TELEFONO_CELULAR;
    }

    public String getCLI_FAX() {
        return CLI_FAX;
    }

    public void setCLI_FAX(String CLI_FAX) {
        this.CLI_FAX = CLI_FAX;
    }

    public String getCLI_E_MAIL() {
        return CLI_E_MAIL;
    }

    public void setCLI_E_MAIL(String CLI_E_MAIL) {
        this.CLI_E_MAIL = CLI_E_MAIL;
    }

    public short getCLI_LOCALIDAD_LABORAL() {
        return CLI_LOCALIDAD_LABORAL;
    }

    public void setCLI_LOCALIDAD_LABORAL(short CLI_LOCALIDAD_LABORAL) {
        this.CLI_LOCALIDAD_LABORAL = CLI_LOCALIDAD_LABORAL;
    }

    public String getCLI_CODIGO_POSTAL_LABORAL() {
        return CLI_CODIGO_POSTAL_LABORAL;
    }

    public void setCLI_CODIGO_POSTAL_LABORAL(String CLI_CODIGO_POSTAL_LABORAL) {
        this.CLI_CODIGO_POSTAL_LABORAL = CLI_CODIGO_POSTAL_LABORAL;
    }

    public String getCLI_CALLE_LABORAL() {
        return CLI_CALLE_LABORAL;
    }

    public void setCLI_CALLE_LABORAL(String CLI_CALLE_LABORAL) {
        this.CLI_CALLE_LABORAL = CLI_CALLE_LABORAL;
    }

    public int getCLI_ALTURA_LABORAL() {
        return CLI_ALTURA_LABORAL;
    }

    public void setCLI_ALTURA_LABORAL(int CLI_ALTURA_LABORAL) {
        this.CLI_ALTURA_LABORAL = CLI_ALTURA_LABORAL;
    }

    public String getCLI_PISO_LABORAL() {
        return CLI_PISO_LABORAL;
    }

    public void setCLI_PISO_LABORAL(String CLI_PISO_LABORAL) {
        this.CLI_PISO_LABORAL = CLI_PISO_LABORAL;
    }

    public String getCLI_DEPARTAMENTO_LABORAL() {
        return CLI_DEPARTAMENTO_LABORAL;
    }

    public void setCLI_DEPARTAMENTO_LABORAL(String CLI_DEPARTAMENTO_LABORAL) {
        this.CLI_DEPARTAMENTO_LABORAL = CLI_DEPARTAMENTO_LABORAL;
    }

    public String getCLI_ANEXO() {
        return CLI_ANEXO;
    }

    public void setCLI_ANEXO(String CLI_ANEXO) {
        this.CLI_ANEXO = CLI_ANEXO;
    }

    public String getCLI_TELEFONO_LABORAL() {
        return CLI_TELEFONO_LABORAL;
    }

    public void setCLI_TELEFONO_LABORAL(String CLI_TELEFONO_LABORAL) {
        this.CLI_TELEFONO_LABORAL = CLI_TELEFONO_LABORAL;
    }

    public byte getCLI_SITUACION_IVA() {
        return CLI_SITUACION_IVA;
    }

    public void setCLI_SITUACION_IVA(byte CLI_SITUACION_IVA) {
        this.CLI_SITUACION_IVA = CLI_SITUACION_IVA;
    }

    public String getCLI_CUIT() {
        return CLI_CUIT;
    }

    public void setCLI_CUIT(String CLI_CUIT) {
        this.CLI_CUIT = CLI_CUIT;
    }

    public byte getCLI_SITUACION_IIBB() {
        return CLI_SITUACION_IIBB;
    }

    public void setCLI_SITUACION_IIBB(byte CLI_SITUACION_IIBB) {
        this.CLI_SITUACION_IIBB = CLI_SITUACION_IIBB;
    }

    public String getCLI_NUMERO_IIBB() {
        return CLI_NUMERO_IIBB;
    }

    public void setCLI_NUMERO_IIBB(String CLI_NUMERO_IIBB) {
        this.CLI_NUMERO_IIBB = CLI_NUMERO_IIBB;
    }

    public double getCLI_EXENCION_PERCEPCION_IVA() {
        return CLI_EXENCION_PERCEPCION_IVA;
    }

    public void setCLI_EXENCION_PERCEPCION_IVA(double CLI_EXENCION_PERCEPCION_IVA) {
        this.CLI_EXENCION_PERCEPCION_IVA = CLI_EXENCION_PERCEPCION_IVA;
    }

    public double getCLI_EXENCION_NACIONAL() {
        return CLI_EXENCION_NACIONAL;
    }

    public void setCLI_EXENCION_NACIONAL(double CLI_EXENCION_NACIONAL) {
        this.CLI_EXENCION_NACIONAL = CLI_EXENCION_NACIONAL;
    }

    public double getCLI_EXENCION_PROVINCIAL() {
        return CLI_EXENCION_PROVINCIAL;
    }

    public void setCLI_EXENCION_PROVINCIAL(double CLI_EXENCION_PROVINCIAL) {
        this.CLI_EXENCION_PROVINCIAL = CLI_EXENCION_PROVINCIAL;
    }

    public double getCLI_EXENCION_MUNICIPAL() {
        return CLI_EXENCION_MUNICIPAL;
    }

    public void setCLI_EXENCION_MUNICIPAL(double CLI_EXENCION_MUNICIPAL) {
        this.CLI_EXENCION_MUNICIPAL = CLI_EXENCION_MUNICIPAL;
    }

    public String getCLI_POTENCIAL() {
        return CLI_POTENCIAL;
    }

    public void setCLI_POTENCIAL(String CLI_POTENCIAL) {
        this.CLI_POTENCIAL = CLI_POTENCIAL;
    }

    public short getCLI_CLASIFICACION() {
        return CLI_CLASIFICACION;
    }

    public void setCLI_CLASIFICACION(short CLI_CLASIFICACION) {
        this.CLI_CLASIFICACION = CLI_CLASIFICACION;
    }

    public short getCLI_ESTADO_CIVIL() {
        return CLI_ESTADO_CIVIL;
    }

    public void setCLI_ESTADO_CIVIL(short CLI_ESTADO_CIVIL) {
        this.CLI_ESTADO_CIVIL = CLI_ESTADO_CIVIL;
    }

    public short getCLI_PROFESION() {
        return CLI_PROFESION;
    }

    public void setCLI_PROFESION(short CLI_PROFESION) {
        this.CLI_PROFESION = CLI_PROFESION;
    }

    public String getCLI_FECHA_NACIMIENTO() {
        return CLI_FECHA_NACIMIENTO;
    }

    public void setCLI_FECHA_NACIMIENTO(String CLI_FECHA_NACIMIENTO) {
        this.CLI_FECHA_NACIMIENTO = CLI_FECHA_NACIMIENTO;
    }

    public String getCLI_NACIONALIDAD() {
        return CLI_NACIONALIDAD;
    }

    public void setCLI_NACIONALIDAD(String CLI_NACIONALIDAD) {
        this.CLI_NACIONALIDAD = CLI_NACIONALIDAD;
    }

    public String getCLI_LUGAR_NACIMIENTO() {
        return CLI_LUGAR_NACIMIENTO;
    }

    public void setCLI_LUGAR_NACIMIENTO(String CLI_LUGAR_NACIMIENTO) {
        this.CLI_LUGAR_NACIMIENTO = CLI_LUGAR_NACIMIENTO;
    }

    public String getCLI_SEXO() {
        return CLI_SEXO;
    }

    public void setCLI_SEXO(String CLI_SEXO) {
        this.CLI_SEXO = CLI_SEXO;
    }

    public byte getCLI_ESTADO() {
        return CLI_ESTADO;
    }

    public void setCLI_ESTADO(byte CLI_ESTADO) {
        this.CLI_ESTADO = CLI_ESTADO;
    }

    public String getCLI_CLAVE_PERSONAL() {
        return CLI_CLAVE_PERSONAL;
    }

    public void setCLI_CLAVE_PERSONAL(String CLI_CLAVE_PERSONAL) {
        this.CLI_CLAVE_PERSONAL = CLI_CLAVE_PERSONAL;
    }

    public String getCLI_ID_USER() {
        return CLI_ID_USER;
    }

    public void setCLI_ID_USER(String CLI_ID_USER) {
        this.CLI_ID_USER = CLI_ID_USER;
    }

    public String getCLI_FECHA_UPDATE() {
        return CLI_FECHA_UPDATE;
    }

    public void setCLI_FECHA_UPDATE(String CLI_FECHA_UPDATE) {
        this.CLI_FECHA_UPDATE = CLI_FECHA_UPDATE;
    }

    public int getCLI_LOTE_REPLICACION() {
        return CLI_LOTE_REPLICACION;
    }

    public void setCLI_LOTE_REPLICACION(int CLI_LOTE_REPLICACION) {
        this.CLI_LOTE_REPLICACION = CLI_LOTE_REPLICACION;
    }
}
