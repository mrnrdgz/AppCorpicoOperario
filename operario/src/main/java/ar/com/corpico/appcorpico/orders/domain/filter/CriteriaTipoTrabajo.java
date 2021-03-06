package ar.com.corpico.appcorpico.orders.domain.filter;
import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;

/**
 * Created by Administrador on 26/01/2017.
 */
public class CriteriaTipoTrabajo implements Criteria<Tipo_Trabajo> {
    private final String cuadrilla;
    //private final int cuadrilla;

    public CriteriaTipoTrabajo(String cuadrilla) {
        this.cuadrilla=cuadrilla;
    }

    @Override
    public List<Tipo_Trabajo> match(List<Tipo_Trabajo> tipoTrabajos) {
        List<Tipo_Trabajo> filteredTipoTrabajo = new ArrayList<>();
        if(cuadrilla == null || cuadrilla.equals("") ){
            filteredTipoTrabajo = tipoTrabajos;
        }else {
        //if (!cuadrilla.equals("") || cuadrilla !=null) {
            for (Tipo_Trabajo tipoTrabajo : tipoTrabajos) {
                if (tipoTrabajo.getTipoCuadrilla().equals(cuadrilla)) {
                    filteredTipoTrabajo.add(tipoTrabajo);
                }
            }
        }
        return filteredTipoTrabajo;
    }

    @Override
    public Object toSql() {
        return "select ... from order where servicio = " + cuadrilla;
    }
}
