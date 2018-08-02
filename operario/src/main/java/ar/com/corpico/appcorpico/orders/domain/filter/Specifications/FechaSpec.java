package ar.com.corpico.appcorpico.orders.domain.filter.Specifications;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import ar.com.corpico.appcorpico.orders.domain.entity.Order;

/**
 * Created by Administrador on 14/06/2017.
 */

public class FechaSpec extends CompositeSpec<Order>  {
    private final String estado;
    private final DateTime desde;
    private final DateTime hasta;
    private final Boolean estadoActual;

    public FechaSpec(String estado, DateTime desde, DateTime hasta,Boolean estadoActual) {
        this.estado = estado;
        this.desde = desde;
        this.hasta = hasta;
        this.estadoActual = estadoActual;
    }

     @Override
    public boolean isSatisfiedBy(Order item) {
        /*if (desde != null && hasta != null) {
            Interval interval = new Interval(desde,hasta);
            if (!estadoActual){
                ArrayList<Etapa> etapas = item.getEtapas();
                // ¿El estado de la etapa y la fecha coinciden con los parámetros de entrada?
                for (Etapa etapa : etapas) {
                    if (estado.equals(etapa.getZona())|| estado.equals("Todos")) {
                        DateTime fechaEtapa = new DateTime(etapa.getFecha());
                        if (interval.contains(fechaEtapa)) {
                           return true;
                        }
                    }
                }

             }else{
                Etapa currentEtapa = item.getCurrentEtapa(item.getEtapas());
                String estadoEtapa = currentEtapa.getZona();
                DateTime fechaEtapa = new DateTime(currentEtapa.getFecha());
                if (estado.equals(estadoEtapa)|| estado.equals("Todos")) {
                    if (interval.contains(fechaEtapa)) {
                        return true;
                    }
                }
            }
        }
        if (desde == null && hasta == null && estadoActual) {
            Etapa currentEtapa = item.getCurrentEtapa(item.getEtapas());
            String estadoEtapa = currentEtapa.getZona();
            if (estado.equals(estadoEtapa) || estado.equals("Todos")) {
                return true;
            }
        }
        return false;*/
         /*Etapa etapaActual = item.getCurrentEtapa(item.getEtapas());
         if (desde == null && hasta == null && estadoActual && etapaActual.getZona().equals(estado) ){*/
         if (desde == null && hasta == null && estadoActual && item.getState().equals(estado) ){
             return true;
         }
        Interval interval = new Interval(desde,hasta);
        //todo: ver si queda fecha etapa o fecha solicitud
         DateTime fechaEtapa = new DateTime(item.getFechaSolicitud());
        /*if(interval.contains(fechaEtapa)){
         if(fechaEtapa.equals(desde)){
            return true;
        }else{
            return false;
        }*/

        return interval.contains(fechaEtapa) && estadoActual && item.getState().equals(estado) ;
        //Boolean b =interval.contains(fechaEtapa);
        // return b;
    }
}
