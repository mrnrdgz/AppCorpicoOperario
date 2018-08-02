package ar.com.corpico.appcorpico.cuadrillas.presentation.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.Button;

import ar.com.corpico.appcorpico.R;

public class EliminiarOperarioDialog extends DialogFragment {
    private int mOperarioId;
    private String mOperario;
    public interface OnEliminarOperaarioListener {
        void onPossitiveEliminarOperarioClick(int operarioId);// Eventos Botón Positivo
        void onNegativeEliminarOperarioClick();
    }

    OnEliminarOperaarioListener listener;

    public EliminiarOperarioDialog() {
    }

    public static EliminiarOperarioDialog newInstance(String operario, int operarioId) {
        EliminiarOperarioDialog f = new EliminiarOperarioDialog();

        Bundle args = new Bundle();
        args.putString("OPERARIO", operario);
        args.putInt("OPERARIO_ID", operarioId);

        f.setArguments(args);

        return f;

    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createEliminiarOperarioDialog();

    }

    /**
     * Crea un diálogo con una lista de radios
     *
     * @return Diálogo
     */
    public AlertDialog createEliminiarOperarioDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        mOperario = (getArguments().getString("OPERARIO"));
        mOperarioId = (getArguments().getInt("OPERARIO_ID"));
        android.view.View v = inflater.inflate(R.layout.dialog_eliminar_operario, null);
        builder.setView(v);
        builder.setTitle("Sacar de la Cuadrilla a: " + mOperario);

        Button eliminar =  v.findViewById(R.id.aplicar_boton);
        Button cancelar =  v.findViewById(R.id.cancelar_boton);

        eliminar.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        listener.onPossitiveEliminarOperarioClick(mOperarioId);
                        dismiss();
                    }
                }
        );
        cancelar.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        listener.onNegativeEliminarOperarioClick();
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
            listener = (OnEliminarOperaarioListener) context;
        }catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó OnSimpleDialogListener");

        }
    }

}
