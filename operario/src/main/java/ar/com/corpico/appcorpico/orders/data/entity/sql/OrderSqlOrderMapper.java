package ar.com.corpico.appcorpico.orders.data.entity.sql;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Order;

/**
 * Created by Administrador on 17/04/2018.
 */

public class OrderSqlOrderMapper extends Mapper<Order,OrderSql>{
    private static OrderSqlOrderMapper INSTANCE;

    private OrderSqlOrderMapper() {
    }

    public static OrderSqlOrderMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrderSqlOrderMapper();
        }

        return INSTANCE;
    }

    @Override
    public Order transform(OrderSql inputItem) {
        Order order = new Order();

            order.setFechaSolicitud(inputItem.getFechaSolicitud());
            order.setNumero(Integer.valueOf(inputItem.getNumero()));
            order.setTipo_Trabajo(inputItem.getTipo_Trabajo());
            order.setMotivo(inputItem.getMotivo());
            if (inputItem.getTurno().length()> 0){
                DateTime mTurno = new DateTime(inputItem.getTurno());
                order.setTurno(mTurno.toString("dd-MM-yyyy HH:mm"));
            }else{
                order.setTurno("Sin Turno");
            }
            if (inputItem.getObservacion()!= null && inputItem.getObservacion().length()>0 ){
                order.setObservacion(inputItem.getObservacion().toUpperCase());
            }
            order.setAsociado(inputItem.getAsociado());
            order.setSuministro(inputItem.getSuministro());
            order.setTitular(convertirPrimeraLetraMayuscula(inputItem.getTitular()));
            order.setState(inputItem.getState());
            order.setDomicilio(convertirPrimeraLetraMayuscula(inputItem.getDomicilio()));
            order.setLocalidad(convertirPrimeraLetraMayuscula(inputItem.getLocalidad()));
            if (inputItem.getAnexo() != null && inputItem.getAnexo().length() > 0 ){
                order.setAnexo(convertirPrimeraLetraMayuscula(inputItem.getAnexo()));
             }else{
                order.setAnexo("");
            }
            order.setLatitud(inputItem.getLatitud());
            order.setLongitud(inputItem.getLongitud());
            order.setGrupo(inputItem.getGrupo());
            order.setmRuta(inputItem.getRuta());
            order.setOrden(inputItem.getOrden());
            order.setTipo_Usuario(inputItem.getTipo_Usuario());
            order.setTarifa(inputItem.getTarifa());
            order.setPotencia_Declarada(inputItem.getPotencia_Declarada().toString());
            order.setMedidor(inputItem.getMedidor());
            order.setMarca(inputItem.getMarca());
            order.setModelo(inputItem.getModelo());
            order.setCapacidad(inputItem.getCapacidad());
            order.setTension(inputItem.getTension());
            order.setFactorM(inputItem.getFactorM());
            order.setEtapas(inputItem.getEtapas());

        return order;
    }

    @Override
    public OrderSql untransform(Order inputItem) {
        return null;
    }

    @Override
    public List<Order> transform(List<OrderSql> inputList) {
        List<Order> output = new ArrayList<>();

        if (inputList != null){
            for(OrderSql orderSql : inputList)
            {
                output.add(transform(orderSql));
            }

        }
        return output;
    }

    @Override
    public List<OrderSql> untransform(List<Order> inputList) {
        return null;
    }

    public String convertirPrimeraLetraMayuscula(String cadenaSql){
        // Convierto el domicilioSql a minúscula
        String cadenaMinuscula = cadenaSql.toLowerCase();
        // Paso a un array el dominicilio para recorrerlo y convertir las primeras letras a mayúsculas
        char[] arrayCadenaMinuscula = cadenaMinuscula.toCharArray();
        arrayCadenaMinuscula[0] = Character.toUpperCase(arrayCadenaMinuscula[0]);
        // el -2 es para evitar una excepción al caernos del arreglo
        for (int i = 0; i < cadenaMinuscula.length()- 2; i++){
            // Es 'palabra'
            if (arrayCadenaMinuscula[i] == ' ' || arrayCadenaMinuscula[i] == '.' || arrayCadenaMinuscula[i] == ','){
                // Reemplazamos
                arrayCadenaMinuscula[i + 1] = Character.toUpperCase(arrayCadenaMinuscula[i + 1]);
            }
        }
        return new String(arrayCadenaMinuscula);
    }
}
