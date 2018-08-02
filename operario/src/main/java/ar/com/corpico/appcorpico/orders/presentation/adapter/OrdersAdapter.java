package ar.com.corpico.appcorpico.orders.presentation.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;


/**
 * Created by Administrador on 07/01/2017.
 */

public class OrdersAdapter extends ArrayAdapter<Order> {

    //TODO ESTA BIEN QUE DEBLARE ORDER ACA? XQ SINO NO LO VEIA DENTRO DEL ONCLICK DEL IMAGEBUTTON
    private Order order;
    private ArrayList<Integer> mSelection = new ArrayList<Integer>();
    private boolean hideAsignarButton = false;
    private DateTime mTurno;

    public OrdersAdapter(Context context, List<Order> objects) {
        super(context,0,objects);
    }


    public void setNewSelection(int position) {
        mSelection.add(position);
        notifyDataSetChanged();
    }
    public ArrayList<Integer> getCurrentCheckedPosition() {
        return mSelection;
    }
    public void removeSelection(int position) {
        mSelection.remove(Integer.valueOf(position));
        notifyDataSetChanged();
    }
    public void clearSelection() {
        mSelection = new ArrayList<Integer>();
        notifyDataSetChanged();
    }
    public int getSelectionCount() {
        return mSelection.size();
    }

    public boolean hideAsignarButton(){
        hideAsignarButton=true;
        return hideAsignarButton;
    }
    public boolean showAsignarButton(){
        hideAsignarButton=false;
        return hideAsignarButton;
    }
    @Override
    public android.view.View getView(final int position, android.view.View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // ¿Existe el view actual?
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.list_item_order,
                    parent,
                    false);
        }

        // Referencias UI.
        android.view.View indicator = convertView.findViewById(R.id.indicator);
        TextView titular = (TextView) convertView.findViewById(R.id.titular_text);
        TextView domicilio = (TextView) convertView.findViewById(R.id.domicilio_text);
        TextView tipo = (TextView) convertView.findViewById(R.id.tipo_text);
        TextView fecha = (TextView) convertView.findViewById(R.id.fechaSolicitud_text);
        TextView ruta = (TextView) convertView.findViewById(R.id.ruta_text);
        TextView  turno = (TextView) convertView.findViewById(R.id.turno_text);

        indicator.setFocusable(false);
        titular.setFocusable(false);
        domicilio.setFocusable(false);
        tipo.setFocusable(false);
        fecha.setFocusable(false);

        // Orden actual..
        order =  getItem(position);

        titular.setText(order.getTitular());
        domicilio.setText(order.getDomicilio());
        tipo.setText(order.getTipo_Trabajo());
        fecha.setText(order.getFechaSolicitud().toString("dd-MM-yyyy"));
        ruta.setText((String)(order.getRuta().toString()));
        turno.setText(order.getTurno());
        //turno.setText(order.getTurno().toString("dd-MM-yyyy hh:mm"));

        //Todo: el estado lo tengo en la orden..."P"
        //String estado = order.getCurrentState(order.getEtapas());
        String estado = order.getState();

        switch (estado) {
            case "P": //case "Pendiente":
                indicator.setBackgroundResource(R.drawable.pendientes_indicator);
                break;
            case "O": //Asignada
                indicator.setBackgroundResource(R.drawable.asignadas_indicator);
                break;
            case "E": //Ejecutada
                indicator.setBackgroundResource(R.drawable.culminadas_indicator);
                break;
            case "N": //No Ejecutada
                indicator.setBackgroundResource(R.drawable.noculminadas_indicator);
                break;
            case "S": //Supervisada
                indicator.setBackgroundResource(R.drawable.supervisadas_indicator);
                break;
            case "V": //Verificar Ejecusion
                indicator.setBackgroundResource(R.drawable.cerradas_indicator);
                break;
        }

        convertView.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));


        if (mSelection.contains(position)) {
            convertView.setBackgroundColor(ContextCompat.getColor(getContext(),  R.color.colorMark));
        }

        return convertView;
    }

}