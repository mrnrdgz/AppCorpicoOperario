package ar.com.corpico.appcorpico.orders.domain.entity;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ar.com.corpico.appcorpico.orderdetailimages.FotoOrden;


/**
 * Created by Administrador on 07/01/2017.
 */

public class Order {
    private DateTime mFechaSolicitud;
    private Integer mNumero;
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
    private String mZona;
    private String mLatitud;
    private String mLongitud;
    private Integer mGrupo;
    private Integer mRuta;
    private Integer mOrden;
    private String mTipo_Usuario;
    private String mTarifa;
    private String mPotencia_Declarada;
    private String mMedidor;
    private String mMarca;
    private String mModelo;
    private String mFactorM;
    private String mCapacidad;
    private String mTension;
    private String mState;
    private ArrayList<FotoOrden> mFotos;

    private Etapa mCurrentEtapa;

    public Order() {

    }

    public Order(DateTime mFechaSolicitud, Integer mNumero, String mTipo_Trabajo, String mMotivo, String mTurno,
                 List<Etapa> mEtapas, String mObservacion, String mAsociado, String mSuministro, String mTitular,
                 String mDomicilio, String mLocalidad, String mAnexo, String mZona, String mLatitud, String mLongitud,
                 Integer mGrupo, Integer mRuta, Integer mOrden, String mTipo_Usuario, String mTarifa, String mPotencia_Declarada,
                 String mMedidor, String mMarca, String mModelo, String mFactorM, String mCapacidad, String mTension,
                 String mState,ArrayList<FotoOrden> mFotos) {
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
        this.mZona = mZona;
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
        this.mFotos = mFotos;
    }

    public List<Etapa> getEtapas() {
        return mEtapas;
    }
    public ArrayList<FotoOrden> getFotos() {
        return mFotos;
    }

    public void setEtapas(List<Etapa> Etapas){
        this.mEtapas =  Etapas;
    }
    public void setFotos(ArrayList<FotoOrden> Fotos){
        this.mFotos =  Fotos;
    }

    public void addEtapas(Etapa etapa) {
        // Validar contenido
        mEtapas.add(etapa);
    }
    public void addFoto(FotoOrden foto) {
        // Validar contenido
        mFotos.add(foto);
    }

    public DateTime getFechaSolicitud() {
        return mFechaSolicitud;
    }

    public void setFechaSolicitud(DateTime fechaSolicitud) {
        this.mFechaSolicitud = fechaSolicitud;
    }

    public Integer getNumero() {
        return mNumero;
    }

    public void setNumero(Integer numero) {
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

    public String getTurno() {
        return mTurno;
    }

    public void setTurno(String turno) {
        this.mTurno = turno;
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

    public String getZona() {
        return mZona;
    }

    public void setZona(String zona) {
        this.mZona = zona;
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

    public String getPotencia_Declarada() {
        return mPotencia_Declarada;
    }

    public void setPotencia_Declarada(String potencia_Declarada) {
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

    public String getCurrentState(ArrayList etapas){
        ArrayList<Etapa> etapasOrdenadas = sortEtapas(etapas);
        mState= etapasOrdenadas.get(etapasOrdenadas.size()-1).getEstado();
        return mState;
    }
    public Etapa getCurrentEtapa(ArrayList etapas){
        if (etapas != null){
            ArrayList<Etapa> etapasOrdenadas = sortEtapas(etapas);
            mCurrentEtapa = etapasOrdenadas.get(etapasOrdenadas.size()-1);
        }
        return  mCurrentEtapa;

    }
    public ArrayList<Etapa> sortEtapas(ArrayList etapas) {
        if (etapas != null){
            Collections.sort(etapas, new Comparator<Etapa>() {
                @Override
                public int compare(Etapa p1, Etapa p2) {
                    return new DateTime(p1.getFecha()).compareTo(new DateTime(p2.getFecha()));
                }
            });
        }
        return etapas;
    }

    public String toString(){
        return "Order={" +
                "Fecha_Solicitud = '" + mFechaSolicitud + '\'' +
                "Numero = '" + mNumero + '\'' +
                "Zona = '" + mZona + '\'' +
                "Tipo_Trabajo = '" + mTipo_Trabajo + '\'' +
                "Motivo = '" + mMotivo + '\'' +
                "Etapa = '" + mEtapas + '\'' +
                "Asociado = '" + mAsociado + '\'' +
                "Suministro = '" + mSuministro + '\'' +
                "Titular = '" + mTitular + '\'' +
                "Domicilio = '" + mDomicilio + '\'' +
                "Localidad = '" + mLocalidad + '\'' +
                "Anexo = '" + mAnexo + '\'' +
                "Latitud = '" + mLatitud + '\'' +
                "Longitud = '" + mLongitud + '\'' +
                "Observacion = '" + mObservacion + '\'' +
                "Turno = '" + mTurno + '\'' +
                "Grupo = '" + mGrupo + '\'' +
                "Ruta = '" + mRuta + '\'' +
                "Orden = '" + mOrden + '\'' +
                "FotoOrden = '" + mFotos + '\'' +
                "Zona = '" + mZona + '\'' +
                '}';
    }

}
