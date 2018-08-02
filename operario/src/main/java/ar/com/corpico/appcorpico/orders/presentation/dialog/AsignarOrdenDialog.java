package ar.com.corpico.appcorpico.orders.presentation.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import ar.com.corpico.appcorpico.R;

/**
 * Created by sistemas on 17/04/2017.
 */

public class AsignarOrdenDialog extends DialogFragment {
    private ArrayList<Integer> mNumeroOT = new ArrayList<>();
    private String mTipoCuadrilla;
    private String mEstadoPost;
    private Integer mTipoCuadrillaId;
    private String mObservacion;
    private Double mLatitud;
    private Double mLongitud;

    public interface OnAsignarOrderListener {
        void onPossitiveButtonAsignarClick(String estado, ArrayList<Integer> numero,String observacion,String tipoCuadrilla,Integer tipoCuadrillaId, Double latitud, Double longitud);// Eventos Botón Positivo
        void onNegativeButtonAsignarClick();
    }

    OnAsignarOrderListener listener;

    public AsignarOrdenDialog() {
    }

    public static AsignarOrdenDialog newInstance(String estadoPost,
                                                 ArrayList<Integer> numero,
                                                 String observacion,
                                                 String tipoCuadrilla,
                                                 Integer tipoCuadrillaId,
                                                 Double latitud,
                                                 Double longitud) {
        AsignarOrdenDialog f = new AsignarOrdenDialog();

        Bundle args = new Bundle();
        args.putString("ESTADO_POST", estadoPost);
        args.putIntegerArrayList("NUMERO", numero);
        args.putString("TIPO_CUADRILLA", tipoCuadrilla);
        args.putInt("TIPO_CUADRILLA_ID", tipoCuadrillaId);
        args.putString("OBSERVACION", observacion);
        args.putDouble("LATITUD", latitud);
        args.putDouble("LONGITUD", longitud);

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

        mEstadoPost = (getArguments().getString("ESTADO_POST"));
        mNumeroOT = (getArguments().getIntegerArrayList("NUMERO"));
        mTipoCuadrilla = (getArguments().getString("TIPO_CUADRILLA"));
        mTipoCuadrillaId = (getArguments().getInt("TIPO_CUADRILLA_ID"));
        mObservacion = (getArguments().getString("OBSERVACION"));
        mLatitud = (getArguments().getDouble("LATITUD"));
        mLongitud = (getArguments().getDouble("LONGITUD"));

        android.view.View v = inflater.inflate(R.layout.dialog_asignar_orders, null);
        builder.setView(v);
        builder.setTitle("Asignar a cuadrilla " + mTipoCuadrilla);

        final TextView mObservation =  v.findViewById(R.id.observacion_text);
        //TextInputLayout mFloatLabelObservacion = v.findViewById(R.id.float_label_observacion);
        mObservation.setVisibility(View.INVISIBLE);
        //mFloatLabelObservacion.setVisibility(View.INVISIBLE);
        Button asignar =  v.findViewById(R.id.aplicar_boton);
        Button cancelar =  v.findViewById(R.id.cancelar_boton);

        asignar.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        //todo: como hago para hacer la pregunta?
                        listener.onPossitiveButtonAsignarClick(mEstadoPost,mNumeroOT,mObservacion,mTipoCuadrilla,mTipoCuadrillaId,mLatitud,mLongitud);
                        dismiss();
                    }
                }
        );
        cancelar.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        listener.onNegativeButtonAsignarClick();
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
            listener = (OnAsignarOrderListener) context;
        }catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó OnSimpleDialogListener");

        }
    }

}

