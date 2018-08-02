package ar.com.corpico.appcorpico.operarios.presentation;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.operarios.domain.entity.Operario;
import ar.com.corpico.appcorpico.operarios.presentation.adapter.OperarioAdapter;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class OperariosListFragment extends Fragment implements View {
    // Comunicacion con la actividad
    OnCerrarOperariosListener mHostActivity;

    public interface OnCerrarOperariosListener {
        void alInsertarOperario();
    }
    // Keys de argumentos
    public static final String ARG_SECTOR = "operario.sector";
    public static final String ARG_TIPO_CUADRILLA = "operario.tipo_cuadrilla";
    public static final String ARG_SERVICIO = "operario.servicio";
    // Dependencias
    private OperariosPresenter mOperarioPresenter;
    // Argumentos
    private int mSector;
    private int mServicio;
    private int mTipoCuadrillaId;

    // Views
    private ListView mOperariosList;
    private OperarioAdapter mOperarioAdapter;
    private TextView mEmptyView;
    private android.view.View mProgressView;

    public OperariosListFragment() {
    }

    public static OperariosListFragment newInstance(int sector,int servicio,int tipoCuadrillaId){
        OperariosListFragment frag = new OperariosListFragment();
        Bundle args = new Bundle();

        args.putInt(ARG_SECTOR, sector);
        args.putInt(ARG_SERVICIO, servicio);
        args.putInt(ARG_TIPO_CUADRILLA, tipoCuadrillaId);

        frag.setArguments(args);
        return frag;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();

        if (arguments != null) {
            // Toman parámetros
            mSector = arguments.getInt(ARG_SECTOR);
            mServicio = arguments.getInt(ARG_SERVICIO);
            mTipoCuadrillaId = arguments.getInt(ARG_TIPO_CUADRILLA);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        android.view.View root = inflater.inflate(R.layout.operarios_list_frag, container, false);

        // Obtención de referencias UI
        mOperariosList = (ListView) root.findViewById(R.id.operarios_list);
        mEmptyView = (TextView) root.findViewById(R.id.operarios_empty);
        mProgressView = root.findViewById(R.id.operarios_progress);

        mOperariosList.setTextFilterEnabled(true);
        mOperarioAdapter = new OperarioAdapter(getActivity(), new ArrayList<Operario>(0));
        mOperariosList.setAdapter(mOperarioAdapter);




        mOperarioPresenter.loadOperarios(mSector);

        return root;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_ingresarOperario:
                List<Integer> ids = mOperarioAdapter.getOperariosSelectedIds();
                mOperarioPresenter.addOperarioCuadrilla(ids,mTipoCuadrillaId,mServicio,mSector);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(OperariosPresenter presenter) {
        mOperarioPresenter = Preconditions.checkNotNull(presenter, "mOperarioPresenter no puede ser null");
    }

    @Override
    public void showOperarioList(List<Operario> operarios) {
        if (mOperarioAdapter != null) {
            mOperarioAdapter.clear();
            mOperarioAdapter.addAll(operarios);
            mOperarioAdapter.notifyDataSetChanged();
            showList(true);
        }
    }

    @Override
    public void showOperarioError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void showOperarioEmpty() {
        mOperariosList.setEmptyView(mEmptyView);
    }

    @Override
    public void showProgressIndicator(boolean show) {
        if (mProgressView != null) {
            showList(!show);
            mProgressView.setVisibility(show ? VISIBLE : GONE);
        }
    }

    @Override
    public void close() {
        mHostActivity.alInsertarOperario();
    }

    public void showList(boolean show) {
        mOperariosList.setVisibility(show ? VISIBLE : GONE);
        // todo: visibility de mas views
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mHostActivity = (OnCerrarOperariosListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó OnSimpleDialogListener");

        }
    }
}
