package ar.com.corpico.appcorpico.orders.domain.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;

/**
 * Created by sistemas on 14/02/2017.
 */

public class Etapa implements Parcelable {
    private DateTime mFecha;
    private String mEstado;
    private String mObservacion;
    private String mUsuario;

    public Etapa(DateTime Fecha, String Estado, String Observacion, String Usuario) {
        mFecha = Fecha;
        mEstado = Estado;
        mObservacion = Observacion;
        mUsuario=Usuario;

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
    public String toString(){
        return "Etapa={" +
                "Fecha = '" + mFecha + '\'' +
                "Estado = '" + mEstado + '\'' +
                "Observacion = '" + mObservacion + '\'' +
                "Usuario = '" + mUsuario + '\'' +
                '}';
    }

    protected Etapa(Parcel in) {
        mFecha = (DateTime) in.readValue(DateTime.class.getClassLoader());
        mEstado = in.readString();
        mObservacion = in.readString();
        mUsuario = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mFecha);
        dest.writeString(mEstado);
        dest.writeString(mObservacion);
        dest.writeString(mUsuario);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Etapa> CREATOR = new Parcelable.Creator<Etapa>() {
        @Override
        public Etapa createFromParcel(Parcel in) {
            return new Etapa(in);
        }

        @Override
        public Etapa[] newArray(int size) {
            return new Etapa[size];
        }
    };
}