package ar.com.corpico.appcorpico.orders.data.entity.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrador on 01/02/2018.
 */

public class RestTipoCuadrilla {
    @SerializedName("TC_ID")
    private Integer TipoCuadrillaID;
    @SerializedName("TC_ID_SERVICIO")
    private Integer TipoCuadrillaServicioId;
    @SerializedName("TC_ID_SECTOR")
    private Integer TipoCuadrillaSectorId;
    @SerializedName("TC_DESCRIPCION")
    private String TipoCuadrilla;
    @SerializedName("TC_FECHA_UPDATE")
    private String TipoCuadrillaUpdate;
    @SerializedName("TC_GEASYS_ID")
    private Integer TipoCuadrillaGeasysId;

    public RestTipoCuadrilla(Integer tipoCuadrillaID, Integer tipoCuadrillaServicioId,
                             Integer tipoCuadrillaSectorId, String tipoCuadrilla, String tipoCuadrillaUpdate,
                             Integer tipoCuadrillaGeasysId) {
        TipoCuadrillaID = tipoCuadrillaID;
        TipoCuadrillaServicioId = tipoCuadrillaServicioId;
        TipoCuadrillaSectorId = tipoCuadrillaSectorId;
        TipoCuadrilla = tipoCuadrilla;
        TipoCuadrillaUpdate = tipoCuadrillaUpdate;
        TipoCuadrillaGeasysId = tipoCuadrillaGeasysId;
    }

    public Integer getID() {
        return TipoCuadrillaID;
    }

    public void setID(Integer TipoCuadrillaID) {
        this.TipoCuadrillaID = TipoCuadrillaID;
    }

    public Integer getSERVICIO() {
        return TipoCuadrillaServicioId;
    }

    public void setSERVICIO(Integer TipoCuadrillaServicioId) {
        this.TipoCuadrillaServicioId = TipoCuadrillaServicioId;
    }

    public Integer getSECTOR() {
        return TipoCuadrillaSectorId;
    }

    public void setSECTOR(Integer TipoCuadrillaSectorId) {
        this.TipoCuadrillaSectorId = TipoCuadrillaSectorId;
    }

    public String getTipoCuadrilla() {
        return TipoCuadrilla;
    }

    public void setTipoCuadrilla(String TipoCuadrilla) {
        this.TipoCuadrilla = TipoCuadrilla;
    }

    public String getTipoCuadrillaUpdate() {
        return TipoCuadrillaUpdate;
    }

    public void setTipoCuadrillaUpdate(String tipoCuadrillaUpdate) {
        TipoCuadrillaUpdate = tipoCuadrillaUpdate;
    }

    public Integer getTipoCuadrillaID() {
        return TipoCuadrillaID;
    }

    public void setTipoCuadrillaID(Integer tipoCuadrillaID) {
        TipoCuadrillaID = tipoCuadrillaID;
    }

    public Integer getTipoCuadrillaServicioId() {
        return TipoCuadrillaServicioId;
    }

    public void setTipoCuadrillaServicioId(Integer tipoCuadrillaServicioId) {
        TipoCuadrillaServicioId = tipoCuadrillaServicioId;
    }

    public Integer getTipoCuadrillaSectorId() {
        return TipoCuadrillaSectorId;
    }

    public void setTipoCuadrillaSectorId(Integer tipoCuadrillaSectorId) {
        TipoCuadrillaSectorId = tipoCuadrillaSectorId;
    }

    public Integer getTipoCuadrillaGeasysId() {
        return TipoCuadrillaGeasysId;
    }

    public void setTipoCuadrillaGeasysId(Integer tipoCuadrillaGeasysId) {
        TipoCuadrillaGeasysId = tipoCuadrillaGeasysId;
    }
}
