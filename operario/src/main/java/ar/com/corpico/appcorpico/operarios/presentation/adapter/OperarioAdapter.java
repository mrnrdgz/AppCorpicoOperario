package ar.com.corpico.appcorpico.operarios.presentation.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.operarios.domain.entity.Operario;

import static android.widget.CompoundButton.*;

public class OperarioAdapter extends ArrayAdapter<Operario> {
    private List<Boolean> mChecked;

    public OperarioAdapter(Context context, List<Operario> operarios) {
        super(context, 0, operarios);
        mChecked = new ArrayList<>();
    }


    @Override
    public android.view.View getView(final int position, android.view.View convertView, ViewGroup parent) {
        // Obtener el objeto procesado actualmente
        Operario operario;

        // Obtener LayoutInflater de la actividad
        LayoutInflater inflater = (LayoutInflater) parent.getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Evitar inflar de nuevo un elemento previamente inflado
        if (convertView == null) {
            convertView = inflater.inflate(
                    R.layout.list_item_operarios,
                    parent,
                    false);
        }

        // Instancias del Texto
        TextView ope = convertView.findViewById(R.id.operario_text);
        CheckBox cBox = convertView.findViewById(R.id.operario_checkBox);

        // Operario actual..
        operario = getItem(position);

        // Asignar valores
        ope.setText(operario.getOperario());

        cBox.setTag(position); // set the tag so we can identify the correct row in the listener
        cBox.setChecked(mChecked.get(position)); // set the status as we stored it
        cBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mChecked.set((Integer) buttonView.getTag(), isChecked); // get the tag so we know the row and store the status
            }
        });

        return convertView;
    }


    @Override
    public void addAll(@NonNull Collection<? extends Operario> collection) {
        for (int i = 0; i < collection.size(); i++) {
            mChecked.add(false);
        }
        super.addAll(collection);
    }

    @Override
    public void clear() {
        mChecked.clear();
        super.clear();
    }

    public List<Integer> getOperariosSelectedIds() {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < mChecked.size(); i++) {
            if (mChecked.get(i)) {
                result.add(getItem(i).getOperarioId());
            }
        }

        return result;
    }


    @Override
    public android.view.View getDropDownView(int position, android.view.View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

}
