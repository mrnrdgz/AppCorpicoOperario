package ar.com.corpico.appcorpico.orderdetail.presentation.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.TextView;

import ar.com.corpico.appcorpico.R;

/**
 * Created by sistemas on 17/04/2017.
 */

public class AsignarOrderDetailDialog extends DialogFragment {
    private Integer mNumeroOT;
    private String mTipoCuadrilla;
    private Integer mTipoCuadrillaId;
    private String mEstadoPost;

    public interface OnAsignarOrderDetailListener {
        void onPossitiveButtonAsignarOrderDetailClick(String estadoPost, String tipoCuadrilla, Integer numero, String observacion, Integer tipoCuadrillaId);// Eventos Botón Positivo
    }

    OnAsignarOrderDetailListener listener;

    public AsignarOrderDetailDialog() {
    }
    public static AsignarOrderDetailDialog newInstance(String estadoPost, String tipoCuadrilla, Integer numero, Integer tipoCuadrillaId) {
        AsignarOrderDetailDialog f = new AsignarOrderDetailDialog();

        Bundle args = new Bundle();
        args.putInt("NUMERO", numero);
        args.putString("TIPO_CUADRILLA", tipoCuadrilla);
        args.putInt("TIPO_CUADRILLA_ID", tipoCuadrillaId);
        args.putString("ESTADO_POST", estadoPost);

        f.setArguments(args);

        return f;

    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createAsignarOrder();

    }

    /**
     * Crea un diálogo con una lista de radios
     *
     * @return Diálogo
     */
    public AlertDialog createAsignarOrder() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        mNumeroOT = getArguments().getInt("NUMERO");
        mTipoCuadrilla = (getArguments().getString("TIPO_CUADRILLA"));
        mTipoCuadrillaId = getArguments().getInt("TIPO_CUADRILLA_ID");
        mEstadoPost = (getArguments().getString("ESTADO_POST"));

        android.view.View v = inflater.inflate(R.layout.dialog_asignar_orders, null);
        builder.setView(v);
        builder.setTitle("Asignar a cuadrilla " + mTipoCuadrilla);


        final TextView mObservation = (TextView) v.findViewById(R.id.observacion_text);
        //TextInputLayout mFloatLabelObservacion = (TextInputLayout) v.findViewById(R.id.float_label_observacion);

        //mFloatLabelObservacion.setVisibility(View.INVISIBLE);
        Button asignar = (Button) v.findViewById(R.id.aplicar_boton);
        Button cancelar = (Button) v.findViewById(R.id.cancelar_boton);

        asignar.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        listener.onPossitiveButtonAsignarOrderDetailClick(mEstadoPost,mTipoCuadrilla,mNumeroOT,mObservation.getText().toString(),mTipoCuadrillaId);
                        dismiss();
                    }
                }
        );
        cancelar.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
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
            listener = (OnAsignarOrderDetailListener) context;
        }catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó OnSimpleDialogListener");

        }
    }
}

