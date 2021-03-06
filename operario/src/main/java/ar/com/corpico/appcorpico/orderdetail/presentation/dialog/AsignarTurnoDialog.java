package ar.com.corpico.appcorpico.orderdetail.presentation.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.Calendar;
import java.util.Locale;

import ar.com.corpico.appcorpico.R;

/**
 * Created by sistemas on 15/09/2017.
 */

public class AsignarTurnoDialog extends DialogFragment {
    private TimePicker mTimePicker;
    private String Turno;
    private DateTime mTurno;
    private int mDia;
    private int mMes;
    private int mAnio;

    private int mHora;
    private int mMinutos;
    public AsignarTurnoDialog(){
    }

    public interface OnAsignarTurnoDialogListener {
        void onPossitiveButtonTurnoClick(String turno);// Eventos Botón Positivo
        void onNegativeButtonTurnoClick();// Eventos Botón Negativo
    }

    OnAsignarTurnoDialogListener listener;
    public static AsignarTurnoDialog newInstance (String turno){
        AsignarTurnoDialog f = new AsignarTurnoDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("TURNO",turno);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Turno = getArguments().getString("TURNO");
        if(!Turno.equals("Sin Turno")){
            mTurno = DateTime.parse(Turno, DateTimeFormat.forPattern("dd-MM-yyyy HH:mm"));
            mDia = mTurno.getDayOfMonth();
            mMes = mTurno.getMonthOfYear();
            mAnio = mTurno.getYear();
            mHora = mTurno.getHourOfDay();
            mMinutos = mTurno.getMinuteOfHour();
        }else{
            mTurno = DateTime.now();
            mDia = mTurno.getDayOfMonth();
            mMes = mTurno.getMonthOfYear();
            mAnio = mTurno.getYear();
            mHora = mTurno.getHourOfDay();
            mMinutos = mTurno.getMinuteOfHour();
        }

    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
            return onCreateAsignarTurnoDialog();
    }
    public AlertDialog onCreateAsignarTurnoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.datePicker);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        android.view.View v = inflater.inflate(R.layout.dialog_asignar_turno, null);
        builder.setView(v);
        builder.setTitle("Turnos");

        final DatePicker mDatePicker = (DatePicker) v.findViewById(R.id.datePicker);
        mTimePicker = (TimePicker) v.findViewById(R.id.timePicker);

        DateTime d = new DateTime(mAnio, mMes, mDia,mHora,mMinutos);
        Calendar c = d.toCalendar(Locale.getDefault());

        mDatePicker.init(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH),new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year,month,dayOfMonth);

                DateTime mFecha = new DateTime(c);

                mAnio = mFecha.getYear();
                mMes = mFecha.getMonthOfYear();
                mDia = mFecha.getDayOfMonth();
            }
        });

        mTimePicker.setIs24HourView(true);
        setTime(mHora,mMinutos);
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                //updateDisplay(hourOfDay, minute);
                mHora = hourOfDay;
                mMinutos = minute;
            }
        });

        Button anular = (Button) v.findViewById(R.id.anular_boton);
        Button asignar = (Button) v.findViewById(R.id.asignar_boton);
        Button cancelar = (Button) v.findViewById(R.id.cancelar_boton);

        if(!Turno.equals("Sin Turno")){
            anular.setEnabled(true);
        }else{
            anular.setEnabled(false);
        }

        anular.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        listener.onNegativeButtonTurnoClick();
                        dismiss();
                    }
                }
        );
        asignar.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        //DateTime mTurno = new DateTime(mAnio,mMes,mDia,mHora,mMinutos);
                        mTurno = new DateTime(mAnio,mMes,mDia,mHora,mMinutos);
                        listener.onPossitiveButtonTurnoClick(mTurno.toString());
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
    private void setTime(int hour, int minute) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTimePicker.setHour(hour);
            mTimePicker.setMinute(minute);
        } else {
            mTimePicker.setCurrentHour(hour);
            mTimePicker.setCurrentMinute(minute);
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (AsignarTurnoDialog.OnAsignarTurnoDialogListener) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó OnSimpleDialogListener");
        }
    }

    @Override
    public void onViewCreated(android.view.View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
