package ar.com.corpico.appcorpico.home.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.home.data.Estados_Home;

public class _EstadoHomeAdapter extends ArrayAdapter<Estados_Home> {
    public _EstadoHomeAdapter(Context context, List<Estados_Home> objects) {
        super(context, 0, objects);
    }

    @Override
    public android.view.View getView(final int position, android.view.View convertView, ViewGroup parent) {
       /*
       Obtener el objeto procesado actualmente
        */
        Estados_Home estadoHome;

        /*
        Obtener LayoutInflater de la actividad
         */
        LayoutInflater inflater = (LayoutInflater) parent.getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        /*
        Evitar inflar de nuevo un elemento previamente inflado
         */
        if (convertView == null) {
            convertView = inflater.inflate(
                    R.layout.list_item_estados_home,
                    parent,
                    false);
        }

        /*
        Instancias del Texto y el Icono
         */
        TextView estado = (TextView) convertView.findViewById(R.id.estadosHome_text);
        TextView totalizado = convertView.findViewById(R.id.total_estados_text);
        //cuadrilla.setTextColor(Color.parseColor("#000000")); //Texto color Negro
        // Tipo cuadrilla actual..
        estadoHome =  getItem(position);

        /*
        Asignar valores
         */
        assert estadoHome != null;
        estado.setText(estadoHome.getEstado());
        totalizado.setText(String.valueOf(estadoHome.getDe()));


        return convertView;
    }

    @Override
    public android.view.View getDropDownView(int position, android.view.View convertView, ViewGroup parent) {
        return getView(position,convertView,parent);
    }
}
