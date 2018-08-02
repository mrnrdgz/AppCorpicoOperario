package ar.com.corpico.appcorpico.orders.domain.filter;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.operarios.domain.entity.Operario;

public class CriteriaOperario implements Criteria<Operario>{
    private final int servicio;

    public CriteriaOperario(int servicio) {
        this.servicio=servicio;
    }

    @Override
    public List<Operario> match(List<Operario> operarios) {
        List<Operario> filteredOperarios = new ArrayList<>();
        if (servicio !=0) {
            for (Operario operario : operarios) {
                if (operario.getServicio() == servicio) {
                    filteredOperarios.add(operario);
                }
            }
        }else{
            filteredOperarios = operarios;
        }
        return filteredOperarios;
    }

    @Override
    public Object toSql() {
        return "select ... from order where state = " + servicio;
    }


}
