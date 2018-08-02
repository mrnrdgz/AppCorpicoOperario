package ar.com.corpico.appcorpico.orders.data.entity.sql;

import android.database.Cursor;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp.OrdenesOperativas;

/**
 * Created by Administrador on 06/04/2018.
 */

public class OrderDataCursorMapper extends Mapper<OrderSql, Cursor> {

    private static OrderDataCursorMapper INSTANCE;

    private OrderDataCursorMapper() {
    }

    public static OrderDataCursorMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrderDataCursorMapper();
        }

        return INSTANCE;
    }

    @Override
    public OrderSql transform(Cursor inputItem) {
        OrderSql orderSql = new OrderSql();
        orderSql.setFechaSolicitud(new DateTime(inputItem.getString(inputItem.getColumnIndex(OrdenesOperativas.fecha_solicitud))));
        orderSql.setNumero(inputItem.getString(inputItem.getColumnIndex(OrdenesOperativas.numero)));
        orderSql.setTipo_Trabajo(inputItem.getString(inputItem.getColumnIndex(OrdenesOperativas.tipo_trabajo)));
        orderSql.setMotivo(inputItem.getString(inputItem.getColumnIndex(OrdenesOperativas.motivo_trabajo)));
        if (inputItem.getString(inputItem.getColumnIndex(ContratoCorpicoApp.Turnos.TUR_TURNO)) == null ) {
            orderSql.setTurno("");
        }else{
            orderSql.setTurno(new DateTime(inputItem.getString(inputItem.getColumnIndex(ContratoCorpicoApp.Turnos.TUR_TURNO))).toString());
        }

        orderSql.setObservacion(inputItem.getString(inputItem.getColumnIndex(OrdenesOperativas.observacion_al_operario)));
        orderSql.setAsociado(inputItem.getString(inputItem.getColumnIndex(OrdenesOperativas.asociado)));
        orderSql.setSuministro(inputItem.getString(inputItem.getColumnIndex(OrdenesOperativas.suministro)));
        orderSql.setTitular(inputItem.getString(inputItem.getColumnIndex(OrdenesOperativas.titular)));
        orderSql.setState(inputItem.getString(inputItem.getColumnIndex(OrdenesOperativas.estado)));
        orderSql.setDomicilio(inputItem.getString(inputItem.getColumnIndex(OrdenesOperativas.calle)).trim() + " " + inputItem.getInt(inputItem.getColumnIndex(OrdenesOperativas.altura )) + " " + inputItem.getString(inputItem.getColumnIndex(OrdenesOperativas.piso)).trim() + " " + inputItem.getString(inputItem.getColumnIndex(OrdenesOperativas.dpto)).trim());
        orderSql.setLocalidad(inputItem.getString(inputItem.getColumnIndex(OrdenesOperativas.localidad)));
        orderSql.setAnexo(inputItem.getString(inputItem.getColumnIndex(OrdenesOperativas.anexo)));
        orderSql.setLatitud(inputItem.getString(inputItem.getColumnIndex(OrdenesOperativas.latitud)));
        orderSql.setLongitud(inputItem.getString(inputItem.getColumnIndex(OrdenesOperativas.longitud)));
        orderSql.setGrupo(inputItem.getInt(inputItem.getColumnIndex(OrdenesOperativas.grupo)));
        orderSql.setmRuta(inputItem.getInt(inputItem.getColumnIndex(OrdenesOperativas.ruta)));
        orderSql.setOrden(inputItem.getInt(inputItem.getColumnIndex(OrdenesOperativas.orden_lectura)));
        orderSql.setTipo_Usuario(inputItem.getString(inputItem.getColumnIndex(OrdenesOperativas.tipo_usuario)));
        orderSql.setTarifa(inputItem.getString(inputItem.getColumnIndex(OrdenesOperativas.tipo_consumo)));
        orderSql.setPotencia_Declarada(inputItem.getInt(inputItem.getColumnIndex(OrdenesOperativas.potencia_declarada)));
        orderSql.setMedidor(inputItem.getString(inputItem.getColumnIndex(OrdenesOperativas.medidor)));
        orderSql.setMarca(inputItem.getString(inputItem.getColumnIndex(OrdenesOperativas.medidor_marca)));
        orderSql.setModelo(inputItem.getString(inputItem.getColumnIndex(OrdenesOperativas.medidor_modelo)));
        orderSql.setCapacidad(inputItem.getString(inputItem.getColumnIndex(OrdenesOperativas.capacidad)));
        orderSql.setTension(inputItem.getString(inputItem.getColumnIndex(OrdenesOperativas.tension)));
        orderSql.setFactorM(inputItem.getString(inputItem.getColumnIndex(OrdenesOperativas.factorM)));


        //private ArrayList<Etapa> mEtapas;



        return orderSql;
    }

    @Override
    public Cursor untransform(OrderSql inputItem) {
        return null;
    }

    @Override
    public List<OrderSql> transform(List<Cursor> inputList) {
        List<OrderSql > output = new ArrayList<>();
        Cursor c = inputList.get(0);

        if(c!=null && c.getCount()>0){
            while (c.moveToNext()){
                output.add(transform(c));
            }
        }
        return output;
    }

    @Override
    public List<Cursor> untransform(List<OrderSql> inputList) {
        return null;
    }
}
