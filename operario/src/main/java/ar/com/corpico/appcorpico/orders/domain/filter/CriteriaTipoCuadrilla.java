package ar.com.corpico.appcorpico.orders.domain.filter;
import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoCuadrilla;

/**
 * Created by Administrador on 26/01/2017.
 */
//public class CriteriaCuadrilla implements Criteria<Tipo_Cuadrilla> {
public class CriteriaTipoCuadrilla implements Criteria<RestTipoCuadrilla> {
    //private final String servicio;
    private final int sector;

    public CriteriaTipoCuadrilla(int sector) {

        this.sector=sector;
    }

    @Override
    //public List<Tipo_Cuadrilla> match(List<Tipo_Cuadrilla> tipoCuadrillas) {
    public List<RestTipoCuadrilla> match(List<RestTipoCuadrilla> tipoCuadrillas) {
        //List<Tipo_Cuadrilla> filteredTipoCuadrillas = new ArrayList<>();
        List<RestTipoCuadrilla> filteredTipoCuadrillas = new ArrayList<>();
        if ( sector != 0) {
            for (RestTipoCuadrilla tipoCuadrilla : tipoCuadrillas) {
                if (tipoCuadrilla.getSECTOR() != null && tipoCuadrilla.getSECTOR() == sector) {
                    filteredTipoCuadrillas.add(tipoCuadrilla);
                }
            }
        }
        return filteredTipoCuadrillas;
    }

    @Override
    public Object toSql() {
        return "select ... from order where servicio = " + sector;
    }
}
