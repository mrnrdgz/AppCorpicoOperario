package ar.com.corpico.appcorpico.orders.data.entity.sql;

import org.joda.time.DateTime;

import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;

/**
 * Created by Administrador on 30/03/2018.
 */

public class OrderSql {
    private DateTime mFechaSolicitud;
    private String mNumero;
    private String mTipo_Trabajo;
    private String mMotivo;
    private String mTurno;
    private List<Etapa> mEtapas;
    private String mObservacion;
    private String mAsociado;
    private String mSuministro;
    private String mTitular;
    private String mDomicilio;
    private String mLocalidad;
    private String mAnexo;
    private String mLatitud;
    private String mLongitud;
    private Integer mGrupo;
    private Integer mRuta;
    private Integer mOrden;
    private String mTipo_Usuario;
    private String mTarifa;
    private Integer mPotencia_Declarada;
    private String mMedidor;
    private String mMarca;
    private String mModelo;
    private String mFactorM;
    private String mCapacidad;
    private String mTension;
    private String mState;

    private Etapa mCurrentEtapa;


    public OrderSql() {

    }

    public OrderSql(DateTime mFechaSolicitud, String mNumero, String mTipo_Trabajo, String mMotivo,String mTurno,
                 List<Etapa> mEtapas, String mObservacion, String mAsociado, String mSuministro, String mTitular,
                 String mDomicilio, String mLocalidad, String mAnexo, String mLatitud, String mLongitud,
                 Integer mGrupo, Integer mRuta, Integer mOrden, String mTipo_Usuario, String mTarifa, Integer mPotencia_Declarada,
                 String mMedidor, String mMarca, String mModelo, String mFactorM, String mCapacidad, String mTension, String mState) {
        this.mFechaSolicitud = mFechaSolicitud;
        this.mNumero = mNumero;
        this.mTipo_Trabajo = mTipo_Trabajo;
        this.mMotivo = mMotivo;
        this.mTurno = mTurno;
        this.mEtapas = mEtapas;
        this.mObservacion = mObservacion;
        this.mAsociado = mAsociado;
        this.mSuministro = mSuministro;
        this.mTitular = mTitular;
        this.mDomicilio = mDomicilio;
        this.mLocalidad = mLocalidad;
        this.mAnexo = mAnexo;
        this.mLatitud = mLatitud;
        this.mLongitud = mLongitud;
        this.mGrupo = mGrupo;
        this.mRuta = mRuta;
        this.mOrden = mOrden;
        this.mTipo_Usuario = mTipo_Usuario;
        this.mTarifa = mTarifa;
        this.mPotencia_Declarada = mPotencia_Declarada;
        this.mMedidor = mMedidor;
        this.mMarca = mMarca;
        this.mModelo = mModelo;
        this.mFactorM = mFactorM;
        this.mCapacidad = mCapacidad;
        this.mTension = mTension;
        this.mState = mState;
    }

    public void addEtapa (Etapa e){
        this.mEtapas.add(e);
    }

    public void setEtapas(List<Etapa> Etapas){
        this.mEtapas =  Etapas;
    }
    public List<Etapa> getEtapas() {
        return mEtapas;
    }
    public DateTime getFechaSolicitud() {
        return mFechaSolicitud;
    }

    public void setFechaSolicitud(DateTime fechaSolicitud) {
        this.mFechaSolicitud = fechaSolicitud;
    }

    public String getNumero() {
        return mNumero;
    }

    public void setNumero(String numero) {
        this.mNumero = numero;
    }

    public String getTipo_Trabajo() {
        return mTipo_Trabajo;
    }

    public void setTipo_Trabajo(String tipo_Trabajo) {
        this.mTipo_Trabajo = tipo_Trabajo;
    }

    public String getMotivo() {
        return mMotivo;
    }

    public void setMotivo(String motivo) {
        this.mMotivo = motivo;
    }


    public String getObservacion() {
        return mObservacion;
    }

    public void setObservacion(String observacion) {
        this.mObservacion = observacion;
    }

    public String getAsociado() {
        return mAsociado;
    }

    public void setAsociado(String asociado) {
        this.mAsociado = asociado;
    }

    public String getSuministro() {
        return mSuministro;
    }

    public void setSuministro(String suministro) {
        this.mSuministro = suministro;
    }

    public String getTitular() {
        return mTitular;
    }

    public void setTitular(String titular) {
        this.mTitular = titular;
    }

    public String getDomicilio() {
        return mDomicilio;
    }

    public void setDomicilio(String domicilio) {
        this.mDomicilio = domicilio;
    }

    public String getLocalidad() {
        return mLocalidad;
    }

    public void setLocalidad(String localidad) {
        this.mLocalidad = localidad;
    }

    public String getAnexo() {
        return mAnexo;
    }

    public void setAnexo(String anexo) {
        this.mAnexo = anexo;
    }

    public String getLatitud() {
        return mLatitud;
    }

    public void setLatitud(String latitud) {
        this.mLatitud = latitud;
    }

    public String getLongitud() {
        return mLongitud;
    }

    public void setLongitud(String longitud) {
        this.mLongitud = longitud;
    }

    public Integer getGrupo() {
        return mGrupo;
    }

    public void setGrupo(Integer grupo) {
        this.mGrupo = grupo;
    }

    public Integer getRuta() {
        return mRuta;
    }

    public void setmRuta(Integer ruta) {
        this.mRuta = ruta;
    }

    public Integer getOrden() {
        return mOrden;
    }

    public void setOrden(Integer orden) {
        this.mOrden = orden;
    }

    public String getTipo_Usuario() {
        return mTipo_Usuario;
    }

    public void setTipo_Usuario(String tipo_Usuario) {
        this.mTipo_Usuario = tipo_Usuario;
    }

    public String getTarifa() {
        return mTarifa;
    }

    public void setTarifa(String tarifa) {
        this.mTarifa = tarifa;
    }

    public Integer getPotencia_Declarada() {
        return mPotencia_Declarada;
    }

    public void setPotencia_Declarada(Integer potencia_Declarada) {
        this.mPotencia_Declarada = potencia_Declarada;
    }

    public String getMedidor() {
        return mMedidor;
    }

    public void setMedidor(String medidor) {
        this.mMedidor = medidor;
    }

    public String getMarca() {
        return mMarca;
    }

    public void setMarca(String marca) {
        this.mMarca = marca;
    }

    public String getModelo() {
        return mModelo;
    }

    public void setModelo(String modelo) {
        this.mModelo = modelo;
    }

    public String getFactorM() {
        return mFactorM;
    }

    public void setFactorM(String factorM) {
        this.mFactorM = factorM;
    }

    public String getCapacidad() {
        return mCapacidad;
    }

    public void setCapacidad(String capacidad) {
        this.mCapacidad = capacidad;
    }

    public String getTension() {
        return mTension;
    }

    public void setTension(String tension) {
        this.mTension = tension;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        this.mState = state;
    }

    public String getTurno() {
        return mTurno;
    }

    public void setTurno(String mTurno) {
        this.mTurno = mTurno;
    }
}

