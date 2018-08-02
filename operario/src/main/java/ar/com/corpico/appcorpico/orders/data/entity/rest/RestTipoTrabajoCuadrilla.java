package ar.com.corpico.appcorpico.orders.data.entity.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrador on 05/02/2018.
 */

public class RestTipoTrabajoCuadrilla {
    @SerializedName("TipoCuadrilla")
    private Integer TipoCuadrillaID;
    @SerializedName("TipoTrabajo")
    private Integer TipoTabajoId;
    @SerializedName("fecha_update")
    private String Fecha_update;

    public RestTipoTrabajoCuadrilla(Integer tipoCuadrillaID, Integer tipoTabajoId, String fecha_update) {
        TipoCuadrillaID = tipoCuadrillaID;
        TipoTabajoId = tipoTabajoId;
        Fecha_update = fecha_update;
    }

    public String getFecha_update() {
        return Fecha_update;
    }

    public void setFecha_update(String fecha_update) {
        Fecha_update = fecha_update;
    }

    public Integer getTipoCuadrillaID() {
        return TipoCuadrillaID;
    }

    public void setTipoCuadrillaID(Integer tipoCuadrillaID) {
        TipoCuadrillaID = tipoCuadrillaID;
    }

    public Integer getTipoTabajoId() {
        return TipoTabajoId;
    }

    public void setTipoTabajoId(Integer tipoTabajoId) {
        TipoTabajoId = tipoTabajoId;
    }
}
