package ar.com.corpico.appcorpico.orderdetail.presentation.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;

/**
 * Created by Administrador on 03/05/2018.
 */

public class EtapasAdapter extends ArrayAdapter<Etapa> {

    public EtapasAdapter(Context context, ArrayList<Etapa> listEtapa) {
        super(context,0,listEtapa);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // ¿Existe el view actual?
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.list_item_etapas,
                    parent,
                    false);
        }

        // Referencias UI.
        View indicator = convertView.findViewById(R.id.indicator);
        TextView fecha = convertView.findViewById(R.id.fechaEtapa_text);
        TextView observacion = convertView.findViewById(R.id.observacionEtapa_text);
        TextView usuario = convertView.findViewById(R.id.operarioEtapa_text);


        // Etapa actual..
        Etapa etapa = getItem(position);

        fecha.setText(etapa.getFecha().toString("dd-MM-yyyy hh:mm"));
        observacion.setText(etapa.getObservacion());
        usuario.setText(etapa.getUsuario());

        //Todo: el estado lo tengo en la orden..."P"
        //String estado = order.getCurrentState(order.getEtapas());
        String estado = etapa.getEstado();

        switch (estado) {
            //case "Pendiente":
            case "P":
                indicator.setBackgroundResource(R.drawable.pendientes_indicator);
                break;
            case "O": //Asignada
                indicator.setBackgroundResource(R.drawable.asignadas_indicator);
                break;
            case "CU": //Culminada
                indicator.setBackgroundResource(R.drawable.culminadas_indicator);
                break;
            case "NC": //No Culminada
                indicator.setBackgroundResource(R.drawable.noculminadas_indicator);
                break;
            case "S": //Supervisada
                indicator.setBackgroundResource(R.drawable.supervisadas_indicator);
                break;
            case "C": //Cerrada
                indicator.setBackgroundResource(R.drawable.cerradas_indicator);
                break;
            //todo: aca faltaria retenida?
        }


        return convertView;
    }

}
