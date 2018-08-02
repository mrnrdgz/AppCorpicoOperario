package ar.com.corpico.appcorpico.orderdetailimages;

import org.joda.time.DateTime;

/**
 * Created by Administrador on 03/05/2018.
 */

public class FotoOrden  {
    private Integer orden;
    private int numero;
    private DateTime fecha;
    private String path;
    private String observacion;

    public FotoOrden(Integer orden, int numero, DateTime fecha, String path, String observacion) {
        this.orden = orden;
        this.numero = numero;
        this.fecha = fecha;
        this.path = path;
        this.observacion = observacion;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public DateTime getFecha() {
        return fecha;
    }

    public void setFecha(DateTime fecha) {
        this.fecha = fecha;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }


}