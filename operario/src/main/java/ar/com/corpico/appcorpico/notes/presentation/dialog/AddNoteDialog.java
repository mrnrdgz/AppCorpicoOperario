package ar.com.corpico.appcorpico.notes.presentation.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ar.com.corpico.appcorpico.R;

/**
 * Created by sistemas on 17/04/2017.
 */

public class AddNoteDialog extends DialogFragment {
    private String mNota;


    public interface OnAddNoteListener {
        void onPossitiveButtonAddNoteClick(String nota);// Eventos Botón Positivo
        void onNegativeButtonAddNoteClick();
    }

    OnAddNoteListener listener;

    public AddNoteDialog() {
    }

    public static AddNoteDialog newInstance() {
        AddNoteDialog f = new AddNoteDialog();

        return f;

    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createAddNote();

    }

    public AlertDialog createAddNote() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_agregar_nota, null);
        builder.setView(v);
        builder.setTitle("Agregar Nota");

        final EditText nota =  (EditText) v.findViewById(R.id.nota_text);
        nota.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        //TextInputLayout mFloatLabelNota = (TextInputLayout) v.findViewById(R.id.float_label_nota);

        //mFloatLabelNota.setVisibility(View.INVISIBLE);

        //mNota = (String) nota.getText();
        Button agregar =  v.findViewById(R.id.agregar_nota);
        Button cancelar =  v.findViewById(R.id.cancelar_nota);

        agregar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onPossitiveButtonAddNoteClick(nota.getText().toString());
                        dismiss();
                    }
                }
        );
        cancelar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onNegativeButtonAddNoteClick();
                        dismiss();
                    }
                }
        );
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnAddNoteListener) context;
        }catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó OnSimpleDialogListener");

        }
    }

}

