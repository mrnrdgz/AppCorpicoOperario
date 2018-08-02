package ar.com.corpico.appcorpico.orders.data.entity.sql;

import android.database.Cursor;

import org.joda.time.DateTime;

import java.util.List;

import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;

/**
 * Created by Administrador on 07/04/2018.
 */

public class EtapaDataCursorMapper extends Mapper<EtapaSql, Cursor>  {
    private static EtapaDataCursorMapper INSTANCE;

    private EtapaDataCursorMapper() {
    }

    public static EtapaDataCursorMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EtapaDataCursorMapper();
        }

        return INSTANCE;
    }

    @Override
    public EtapaSql transform(Cursor inputItem) {
        EtapaSql etapaSql = new EtapaSql();
        etapaSql.setFecha(new DateTime(inputItem.getString(inputItem.getColumnIndex(ContratoCorpicoApp.Etapas.ETA_FECHA))));
        etapaSql.setEstado(inputItem.getString(inputItem.getColumnIndex(ContratoCorpicoApp.Etapas.ETA_ESTADO)));
        etapaSql.setObservacion(inputItem.getString(inputItem.getColumnIndex(ContratoCorpicoApp.Etapas.ETA_OBSERVACION)));
        etapaSql.setUsuario(inputItem.getString(inputItem.getColumnIndex(ContratoCorpicoApp.Etapas.ETA_FECHA)));
        return etapaSql;
    }

    @Override
    public Cursor untransform(EtapaSql inputItem) {
        return null;
    }

    @Override
    public List<EtapaSql> transform(List<Cursor> inputList) {
        return null;
    }

    @Override
    public List<Cursor> untransform(List<EtapaSql> inputList) {
        return null;
    }
}
