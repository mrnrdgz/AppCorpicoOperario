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

public class EditNoteDialog extends DialogFragment {
    private int mId;
    private String mNota;


    public interface OnEditNoteListener {
        void onPossitiveButtonEditNoteClick(int id, String nota);// Eventos Botón Positivo
        void onNegativeButtonEditNoteClick();
    }

    OnEditNoteListener listener;

    public EditNoteDialog() {
    }

    public static EditNoteDialog newInstance(int id, String nota) {
        EditNoteDialog f = new EditNoteDialog();
        Bundle args = new Bundle();

        args.putInt("ID", id);
        args.putString("NOTA", nota);
        f.setArguments(args);

        return f;

    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mId = getArguments().getInt("ID");
        mNota = getArguments().getString("NOTA");
        return createAddNote();

    }

    public AlertDialog createAddNote() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_agregar_nota, null);
        builder.setView(v);
        builder.setTitle("Editar Nota");

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
                        listener.onPossitiveButtonEditNoteClick(mId,nota.getText().toString());
                        dismiss();
                    }
                }
        );
        cancelar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onNegativeButtonEditNoteClick();
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
            listener = (OnEditNoteListener) context;
        }catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó OnSimpleDialogListener");

        }
    }

}

