package ar.com.corpico.appcorpico.cuadrillas.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.cuadrillas.domain.entity.Cuadrilla;

public class CuadrillaAdpater extends ArrayAdapter<Cuadrilla> {
    OnEliminarOperarioListener listenerEliminarOperarioAdapter;
    public interface OnEliminarOperarioListener {
        void onButtonEliminarClickListner(String operario,int operarioId);
    }
    public CuadrillaAdpater(Context context, List<Cuadrilla> objects,
                            OnEliminarOperarioListener listenerEliminarOperarioAdapter) {
        super(context, 0, objects);
        this.listenerEliminarOperarioAdapter = listenerEliminarOperarioAdapter;
    }

    @Override
    public android.view.View getView(final int position, android.view.View convertView, ViewGroup parent) {
       // Obtener el objeto procesado actualmente
        Cuadrilla cuadrilla;

        // Obtener LayoutInflater de la actividad
        LayoutInflater inflater = (LayoutInflater) parent.getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Evitar inflar de nuevo un elemento previamente inflado
        if (convertView == null) {
            convertView = inflater.inflate(
                    R.layout.list_item_cuadrilla,
                    parent,
                    false);
        }

        // Instancias del Texto y  la foto
        ImageView foto = convertView.findViewById(R.id.operario_foto);
        TextView operario = (TextView) convertView.findViewById(R.id.operario_text);
        ImageButton eliminar = convertView.findViewById(R.id.eliminarOperario);

        // Operario actual..
        cuadrilla =  getItem(position);

        // Asignar valores
        operario.setText(cuadrilla.getOperario());

        eliminar.setFocusable(true);

        eliminar.setOnClickListener(new android.view.View.OnClickListener()
        {
            @Override
            public void onClick(android.view.View view) {
                if (listenerEliminarOperarioAdapter != null) {
                    int operarioId;
                    String operario;
                    operario = getItem(position).getOperario();
                    operarioId = getItem(position).getOperarioId();
                    listenerEliminarOperarioAdapter.onButtonEliminarClickListner(operario,operarioId);
                }
            }
        });

        return convertView;
    }

    @Override
    public android.view.View getDropDownView(int position, android.view.View convertView, ViewGroup parent) {
        return getView(position,convertView,parent);
    }

}
