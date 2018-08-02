package ar.com.corpico.appcorpico.orders.data.entity.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrador on 19/03/2018.
 */

public class RestTipoTrabajo {
    @SerializedName("TIT_ID")
    private Integer Id;
    @SerializedName("TIT_DESCRIPCION")
    private String descripcion;
    @SerializedName("TIT_ABREVIATURA")
    private String abreviatura;
    @SerializedName("TIT_TIPO_EMPRESA")
    private int tipoEmpresa;
    @SerializedName("TIT_CLASIFICACION_TRABAJO")
    private int clasificacionTrabajo;
    @SerializedName("TIT_PERMITE_ELIMINAR")
    private String permiteEliminar;
    @SerializedName("TIT_ACTIVO")
    private String activo;
    @SerializedName("TIT_ID_USER")
    private String userId;
    @SerializedName("TIT_FECHA_UPDATE")
    private String fechaUpdate;
    @SerializedName("TIT_LOTE_REPLICACION")
    private int loteReplicacion;

    public RestTipoTrabajo(Integer id, String descripcion, String abreviatura, int tipoEmpresa, int clasificacionTrabajo,
                           String permiteEliminar, String activo, String userId, String fechaUpdate, int loteReplicacion) {
        Id = id;
        this.descripcion = descripcion;
        this.abreviatura = abreviatura;
        this.tipoEmpresa = tipoEmpresa;
        this.clasificacionTrabajo = clasificacionTrabajo;
        this.permiteEliminar = permiteEliminar;
        this.activo = activo;
        this.userId = userId;
        this.fechaUpdate = fechaUpdate;
        this.loteReplicacion = loteReplicacion;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public int getTipoEmpresa() {
        return tipoEmpresa;
    }

    public void setTipoEmpresa(int tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    public int getClasificacionTrabajo() {
        return clasificacionTrabajo;
    }

    public void setClasificacionTrabajo(int clasificacionTrabajo) {
        this.clasificacionTrabajo = clasificacionTrabajo;
    }

    public String getPermiteEliminar() {
        return permiteEliminar;
    }

    public void setPermiteEliminar(String permiteEliminar) {
        this.permiteEliminar = permiteEliminar;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFechaUpdate() {
        return fechaUpdate;
    }

    public void setFechaUpdate(String fechaUpdate) {
        this.fechaUpdate = fechaUpdate;
    }

    public int getLoteReplicacion() {
        return loteReplicacion;
    }

    public void setLoteReplicacion(int loteReplicacion) {
        this.loteReplicacion = loteReplicacion;
    }
}
