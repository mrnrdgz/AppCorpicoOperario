package ar.com.corpico.appcorpico.home.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.home.data.Cuadrilla_Home;
import ar.com.corpico.appcorpico.home.data.Estados_Home;
import ar.com.corpico.appcorpico.home.data.TiposTrabajo_Home;
import ar.com.corpico.appcorpico.home.data.Zonas_Home;
import ar.com.corpico.appcorpico.home.presentation.adapter.CuadrillaHomeAdapter;
import ar.com.corpico.appcorpico.home.presentation.adapter.EstadoHomeAdapter;
import ar.com.corpico.appcorpico.home.presentation.adapter.TiposTrabajoHomeAdapter;
import ar.com.corpico.appcorpico.home.presentation.adapter.ZonaHomeAdapter;

import static android.view.View.VISIBLE;

public class HomeFragment extends Fragment implements View {
    // Keys de argumentos
    public static final String ARG_SERVICIO = "home.servicio";
    public static final String ARG_SECTOR = "home.sector";
    public static final String ARG_ES_PRIMERA_CARGA = "home.primeraCarga";

    // Dependencias
    private Presenter mHomePresenter;

    // Views
    private RecyclerView mEstadoHomeList;
    private RecyclerView mCuadrillaHomeList;
    private RecyclerView mTipoTrabajoHomeList;
    private RecyclerView mZonaHomeList;
    private CuadrillaHomeAdapter mCuadrillaHomeAdapter;
    private EstadoHomeAdapter mEstadoHomeAdapter;
    private TiposTrabajoHomeAdapter mTipoTrabajoHomeAdapter;
    private ZonaHomeAdapter mZonaHomeAdapter;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayoutManager linearLayoutManager2;
    private LinearLayoutManager linearLayoutManager3;
    private LinearLayoutManager linearLayoutManager4;
    private TextView mEmptyZonaView;
    private android.view.View mProgressCuadrillaView;
    private CardView mCuadrillaCard;
    private CardView mEstadosCard;
    private CardView mTipoTrabajoCard;
    private CardView mZonaCard;
    private TextView mFechaHome;



    // Argumentos
    private int mServicio;
    private int mSector;
    private boolean mPrimeraCarga;


    public HomeFragment() {
    }

    public static HomeFragment newInstance(int servicio, int sector, boolean esPrimeraCarga) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();

        args.putInt(ARG_SERVICIO, servicio);
        args.putInt(ARG_SECTOR, sector);
        args.putBoolean(ARG_ES_PRIMERA_CARGA, esPrimeraCarga);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();

        if (arguments != null) {
            // Toman parámetros
            mServicio = arguments.getInt(ARG_SERVICIO);
            mSector = arguments.getInt(ARG_SECTOR);
            mPrimeraCarga = arguments.getBoolean(ARG_ES_PRIMERA_CARGA);
        }
    }



    @Nullable
    @Override
    public android.view.View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        android.view.View root = inflater.inflate(R.layout.home_list_frag, container, false);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager2 = new LinearLayoutManager(getActivity());
        linearLayoutManager3 = new LinearLayoutManager(getActivity());
        linearLayoutManager4 = new LinearLayoutManager(getActivity());

        // Obtención de referencias UI
        mCuadrillaHomeList = (RecyclerView) root.findViewById(R.id.cuadrillaHome_list);
        mCuadrillaHomeList.setLayoutManager(linearLayoutManager);
        mEstadoHomeList = (RecyclerView) root.findViewById(R.id.estadosHome_list);
        mEstadoHomeList.setLayoutManager(linearLayoutManager2);
        mTipoTrabajoHomeList = (RecyclerView) root.findViewById(R.id.tipoTrabajoHome_list);
        mTipoTrabajoHomeList.setLayoutManager(linearLayoutManager3);
        mZonaHomeList = (RecyclerView) root.findViewById(R.id.zonaHome_list);
        mZonaHomeList.setLayoutManager(linearLayoutManager4);
        mEmptyZonaView = (TextView) root.findViewById(R.id.zonaHome_empty);
        mCuadrillaCard = root.findViewById(R.id.card_cuadrillas);
        mEstadosCard = root.findViewById(R.id.card_estados);
        mTipoTrabajoCard = root.findViewById(R.id.card_Tipo_Trabajo);
        mZonaCard = root.findViewById(R.id.card_zona);
        mFechaHome = root.findViewById(R.id.fechaHome);

        mCuadrillaHomeAdapter = new CuadrillaHomeAdapter(getActivity(), new ArrayList<Cuadrilla_Home>(0));
        mCuadrillaHomeList.setAdapter(mCuadrillaHomeAdapter);

        mEstadoHomeAdapter = new EstadoHomeAdapter(getActivity(), new ArrayList<Estados_Home>(0));
        mEstadoHomeList.setAdapter(mEstadoHomeAdapter);

        mTipoTrabajoHomeAdapter = new TiposTrabajoHomeAdapter(getActivity(), new ArrayList<TiposTrabajo_Home>(0));
        mTipoTrabajoHomeList.setAdapter(mTipoTrabajoHomeAdapter);

        mZonaHomeAdapter = new ZonaHomeAdapter(getActivity(), new ArrayList<Zonas_Home>(0));
        mZonaHomeList.setAdapter(mZonaHomeAdapter);

        return root;
    }

    @Override
    public void showCuadrillas(List<Cuadrilla_Home> listCuadrillaHome) {
        mCuadrillaHomeAdapter.addMoreCuadrillas(listCuadrillaHome);
    }

    @Override
    public void showEstados(List<Estados_Home> listEstadoHome) {
        mEstadoHomeAdapter.addMoreEstados(listEstadoHome);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!mPrimeraCarga){
            mFechaHome.setText(DateTime.now().toString("dd MMMM yyyy"));
            mCuadrillaCard.setVisibility(VISIBLE);
            mEstadosCard.setVisibility(VISIBLE);
            mTipoTrabajoCard.setVisibility(VISIBLE);
            mZonaCard.setVisibility(VISIBLE);
            mHomePresenter.loadCuadrillasHome(mServicio,mSector);
            mHomePresenter.loadEstadosHome(mServicio,mSector);
            mHomePresenter.loadTiposTrabajoHome(mServicio,mSector);
            mHomePresenter.loadZonasHome(mServicio,mSector);
        }

    }

    @Override
    public void showTipoTrabajos(List<TiposTrabajo_Home> listTipoTrabajoHome) {
        mTipoTrabajoHomeAdapter.addMoreTiposTrabajo(listTipoTrabajoHome);
    }

    @Override
    public void showZonas(List<Zonas_Home> listZonaHome) {
        mZonaHomeAdapter.addMoreZonas(listZonaHome);
    }

    @Override
    public void showCuadrillasError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void showEstadosError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void showTiposTrabajoError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void showZonasError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG)
                .show();
    }


    @Override
    public void setPresenter(Presenter presenter) {
        mHomePresenter = Preconditions.checkNotNull(presenter, "mHomePresenter no puede ser null");
    }

    @Override
    public void showListZonaEmpty() {
        mEmptyZonaView.setVisibility(VISIBLE);

    }

    @Override
    public void onSyncFinish() {
        mFechaHome.setText(DateTime.now().toString("dd MMMM yyyy"));
        mCuadrillaCard.setVisibility(VISIBLE);
        mEstadosCard.setVisibility(VISIBLE);
        mTipoTrabajoCard.setVisibility(VISIBLE);
        mZonaCard.setVisibility(VISIBLE);
        mHomePresenter.loadCuadrillasHome(mServicio,mSector);
        mHomePresenter.loadEstadosHome(mServicio,mSector);
        mHomePresenter.loadTiposTrabajoHome(mServicio,mSector);
        mHomePresenter.loadZonasHome(mServicio,mSector);
    }
}
