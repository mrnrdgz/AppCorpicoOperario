package ar.com.corpico.appcorpico.cuadrillas.presentation;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.cuadrillas.domain.entity.Cuadrilla;
import ar.com.corpico.appcorpico.cuadrillas.presentation.adapter.CuadrillaAdpater;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class CuadrillasListFragment extends Fragment implements View, CuadrillaAdpater.OnEliminarOperarioListener {
    // Comunicacion con la actividad
    OnEliminarOpeListener mHostActivity;

    public interface OnEliminarOpeListener {
        void alEliminarOperario(String operario, int operarioId);
    }

    // Keys de argumentos
    public static final String ARG_TIPO_CUADRILLA = "orders.tipo_cuadrilla";
    public static final String ARG_TIPO_CUADRILLAID = "orders.tipo_cuadrillaId";
    // Dependencias
    private CuadrillasPresenter mCuadrillaPresenter;

    // Views
    private ListView mCuadrillasList;
    private CuadrillaAdpater mCuadrillaAdapter;
    private TextView mEmptyView;
    private android.view.View mProgressView;

    // Argumentos
    private String mTipoCuadrilla;
    private Integer mTipoCuadrillaId;

    public CuadrillasListFragment() {
    }

    public static CuadrillasListFragment newInstance(String tipoCuadrilla, Integer tipoCuadrillaId) {
        CuadrillasListFragment fragment = new CuadrillasListFragment();
        Bundle args = new Bundle();

        args.putString(ARG_TIPO_CUADRILLA, tipoCuadrilla);
        args.putInt(ARG_TIPO_CUADRILLAID, tipoCuadrillaId);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();

        if (arguments != null) {
            // Toman parámetros
            mTipoCuadrilla = arguments.getString(ARG_TIPO_CUADRILLA);
            mTipoCuadrillaId = arguments.getInt(ARG_TIPO_CUADRILLAID);
        }
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        android.view.View root = inflater.inflate(R.layout.cuadrillas_list_frag, container, false);

        // Obtención de referencias UI
        mCuadrillasList = root.findViewById(R.id.cuadrillas_list);
        mEmptyView = root.findViewById(R.id.cuadrillas_empty);
        mProgressView = root.findViewById(R.id.cuadrillas_progress);

        mCuadrillasList.setTextFilterEnabled(true);
        mCuadrillaAdapter = new CuadrillaAdpater(getActivity(), new ArrayList<Cuadrilla>(0), this);
        mCuadrillasList.setAdapter(mCuadrillaAdapter);

        mCuadrillasList.setFocusable(false);
        mCuadrillaPresenter.loadCuadrillas(mTipoCuadrillaId);

        return root;
    }

    @Override
    public void close() {
        mCuadrillaPresenter.loadCuadrillas(mTipoCuadrillaId);
    }

    @Override
    public void eliminarOperarioOk(int operarioId) {
        mCuadrillaPresenter.removeOperario(operarioId, mTipoCuadrillaId);
    }

    @Override
    public void showCuadrillaError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void setPresenter(CuadrillasPresenter presenter) {
        mCuadrillaPresenter = Preconditions.checkNotNull(presenter, "mOrdersPresenter no puede ser null");
    }

    @Override
    public void showCuadrillaList(List<Cuadrilla> cuadrillas) {
        if (mCuadrillaAdapter != null) {
            mCuadrillaAdapter.clear();
            mCuadrillaAdapter.addAll(cuadrillas);
            mCuadrillaAdapter.notifyDataSetChanged();
            showList(true);
        }

    }

    @Override
    public void showCuadrillaEmpty() {
        mCuadrillasList.setEmptyView(mEmptyView);
    }

    @Override
    public void showProgressIndicator(boolean show) {
        if (mProgressView != null) {
            showList(!show);
            mProgressView.setVisibility(show ? VISIBLE : GONE);
        }
    }

    public void showList(boolean show) {
        mCuadrillasList.setVisibility(show ? VISIBLE : GONE);
        // todo: visibility de mas views
    }

    @Override
    public void onButtonEliminarClickListner(String operario, int operarioId) {
        mHostActivity.alEliminarOperario(operario, operarioId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mHostActivity = (OnEliminarOpeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó OnSimpleDialogListener");

        }
    }
}
