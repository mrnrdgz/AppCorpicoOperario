package ar.com.corpico.appcorpico.orders.data.entity.sql;

import org.joda.time.DateTime;

/**
 * Created by Administrador on 07/04/2018.
 */

public class EtapaSql {
    private DateTime mFecha;
    private String mEstado;
    private String mObservacion;
    private String mUsuario;

    public EtapaSql(DateTime Fecha, String Estado, String Observacion, String Usuario) {
        mFecha = Fecha;
        mEstado = Estado;
        mObservacion = Observacion;
        mUsuario=Usuario;

    }
    public EtapaSql(){

    }

    public String getUsuario() {
        return mUsuario;
    }
    public void setUsuario(String Usuario) {
        this.mUsuario = Usuario;
    }
    public DateTime getFecha() {
        return mFecha;
    }

    public void setFecha(DateTime Fecha) {
        mFecha = Fecha;
    }

    public String getEstado() {
        return mEstado;
    }

    public void setEstado(String Estado) {
        mEstado = Estado;
    }

    public String getObservacion() {
        return mObservacion;
    }

    public void setObservacion(String Observacion) {
        mObservacion = Observacion;
    }
}
