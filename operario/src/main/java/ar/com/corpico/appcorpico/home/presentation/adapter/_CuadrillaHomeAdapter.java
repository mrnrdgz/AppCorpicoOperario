package ar.com.corpico.appcorpico.home.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.home.data.Cuadrilla_Home;

public class _CuadrillaHomeAdapter extends ArrayAdapter<Cuadrilla_Home> {
    public _CuadrillaHomeAdapter(Context context, List<Cuadrilla_Home> objects) {
        super(context, 0, objects);
    }

    @Override
    public android.view.View getView(final int position, android.view.View convertView, ViewGroup parent) {
       /*
       Obtener el objeto procesado actualmente
        */
        Cuadrilla_Home cuadrillaHome;

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
                    R.layout.list_item_cuadrilla_home,
                    parent,
                    false);
        }

        /*
        Instancias del Texto y el Icono
         */
        TextView cuadrilla = (TextView) convertView.findViewById(R.id.cuadrillaHome_text);
        TextView totalizado = convertView.findViewById(R.id.total_cuadrilla_text);
        //cuadrilla.setTextColor(Color.parseColor("#000000")); //Texto color Negro
        // Tipo cuadrilla actual..
        cuadrillaHome =  getItem(position);

        /*
        Asignar valores
         */
        assert cuadrillaHome != null;
        cuadrilla.setText(cuadrillaHome.getCuadrilla());
        totalizado.setText(String.valueOf(cuadrillaHome.getDe()));


        return convertView;
    }

    @Override
    public android.view.View getDropDownView(int position, android.view.View convertView, ViewGroup parent) {
        return getView(position,convertView,parent);
    }
}
