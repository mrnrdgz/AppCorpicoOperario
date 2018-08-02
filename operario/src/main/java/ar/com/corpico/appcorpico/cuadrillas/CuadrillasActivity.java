package ar.com.corpico.appcorpico.cuadrillas;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.BaseActivity;
import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.UseCaseHandler;
import ar.com.corpico.appcorpico.cuadrillas.data.CuadrillaRestStore;
import ar.com.corpico.appcorpico.cuadrillas.data.CuadrillaSqliteStore;
import ar.com.corpico.appcorpico.cuadrillas.data.CuadrillasRepository;
import ar.com.corpico.appcorpico.cuadrillas.domain.usecase.GetCuadrillas;
import ar.com.corpico.appcorpico.cuadrillas.domain.usecase.RemoveOperario;
import ar.com.corpico.appcorpico.cuadrillas.presentation.CuadrillasListFragment;
import ar.com.corpico.appcorpico.cuadrillas.presentation.CuadrillasPresenter;
import ar.com.corpico.appcorpico.cuadrillas.presentation.dialog.EliminiarOperarioDialog;
import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.operarios.OperarioActivity;
import ar.com.corpico.appcorpico.orders.data.tipoCuadrilla.TipoCuadrillaRestStore;
import ar.com.corpico.appcorpico.orders.data.tipoCuadrilla.TipoCuadrillaSqliteStore;
import ar.com.corpico.appcorpico.orders.data.tipoCuadrilla.TipoCuadrillasRepository;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetTipoCuadrilla;
import ar.com.corpico.appcorpico.orders.presentation.TiposCuadrillaToolbarMvp;
import ar.com.corpico.appcorpico.orders.presentation.TiposCuadrillasPresenter;
import ar.com.corpico.appcorpico.orders.presentation.adapter.TipoCuadrillaAdapter;

public class CuadrillasActivity extends BaseActivity implements TiposCuadrillaToolbarMvp.View,
        CuadrillasListFragment.OnEliminarOpeListener,
        EliminiarOperarioDialog.OnEliminarOperaarioListener{
    // Claves de argumentos
    public static final String ARG_SERVICIO = "cuadrilla.servicio";
    public static final String ARG_SECTOR = "cuadrilla.sector";

    // Argumentos
    private int mServicioArg;
    private int mSectorArg;

    // Views
    private Spinner mMenuTipoCuadrillas;
    private TipoCuadrillaAdapter mTipoCuadrillaAdapter;

    // Dependencias
    private TiposCuadrillaToolbarMvp.Presenter mTiposCuadrillasPresenter;
    private CuadrillasPresenter mCuadrillasPresenter;
    private CuadrillasListFragment mCuadrillaListFragment;
    private GetTipoCuadrilla mGetTipoCuadrilla;
    private GetCuadrillas mGetCuadrilla;
    private RemoveOperario mRemoveOperario;
    private final UseCaseHandler mUseCaseHandler = UseCaseHandler.getInstance();

    // Variables
    private String mTipoCuadrilla;
    private Integer mTipoCuadrillaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cuadrillas_list_act);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Obtener argumentos
        Bundle args = getIntent().getExtras();
        if (args != null) {
            mServicioArg = args.getInt(ARG_SERVICIO);
            mSectorArg = args.getInt(ARG_SECTOR);
        }

        // Obtención de referencias UI
        mMenuTipoCuadrillas = findViewById(R.id.spinner_toolBar);
        setUpSpinner();

        // Fuentes de datos y repositorios
        SessionsPrefsStore prefStore = SessionsPrefsStore.get(
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE));
        CuadrillasRepository cuadrillasRepository = CuadrillasRepository.getInstance(
                CuadrillaRestStore.getInstance(),
                CuadrillaSqliteStore.getInstance(getContentResolver()),
                prefStore);
        TipoCuadrillasRepository tipocuadrillasRepository = TipoCuadrillasRepository.getInstance(
                TipoCuadrillaRestStore.getInstance(),
                TipoCuadrillaSqliteStore.getInstance(getContentResolver()),
                prefStore);

        // Casos de uso
        mGetTipoCuadrilla = new GetTipoCuadrilla(tipocuadrillasRepository);
        mGetCuadrilla = new GetCuadrillas(cuadrillasRepository);
        mRemoveOperario = new RemoveOperario(cuadrillasRepository);

        // Presentadores
        mTiposCuadrillasPresenter = new TiposCuadrillasPresenter(this, mGetTipoCuadrilla, mUseCaseHandler);


    }
    protected NavDrawerItemMenu getDrawerItem() {
        return NavDrawerItemMenu.CUADRILLAS;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cuadrilla, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_agragarOperario:
                Intent intent = new Intent(this, OperarioActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(OperarioActivity.ARG_SECTOR, mSectorArg);
                intent.putExtra(OperarioActivity.ARG_SERVICIO, mServicioArg);
                intent.putExtra(OperarioActivity.ARG_TIPO_CUADRILLA,mTipoCuadrillaId);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpSpinner() {

        // Adaptador
        mTipoCuadrillaAdapter = new TipoCuadrillaAdapter(this, new ArrayList<Tipo_Cuadrilla>(0));
        mTipoCuadrillaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMenuTipoCuadrillas.setAdapter(mTipoCuadrillaAdapter);


        // Click en menu de tipos de cuadrillas
        mMenuTipoCuadrillas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                View item = mMenuTipoCuadrillas.getSelectedView();
                ((TextView) item).setTextColor(Color.WHITE);

                Tipo_Cuadrilla tipo_cuadrilla = (Tipo_Cuadrilla) mMenuTipoCuadrillas.getSelectedItem();
                mTipoCuadrilla = tipo_cuadrilla.getTipo_cuadrilla();
                mTipoCuadrillaId = tipo_cuadrilla.getId();

                // Se refresca el fragmento luego de seleccionar
                initListMvp();
                mCuadrillasPresenter.loadCuadrillas(mTipoCuadrillaId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // no-op
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Se cargan items del spinner de tipos de cuadrilla
        if(mCuadrillaListFragment == null){
            mTiposCuadrillasPresenter.loadTiposCuadrilla(mServicioArg);
        }
        if(mCuadrillaListFragment != null){
            mCuadrillasPresenter.loadCuadrillas(mTipoCuadrillaId);
        }

    }

    private void initListMvp() {
        mCuadrillaListFragment = (CuadrillasListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.cuadrillas_view_container);


        if (mCuadrillaListFragment == null) {
            mCuadrillaListFragment = CuadrillasListFragment.newInstance(mTipoCuadrilla, mTipoCuadrillaId);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.cuadrillas_view_container, mCuadrillaListFragment, "CuadrillaView")
                    .commit();
        }

        mCuadrillasPresenter = new CuadrillasPresenter(mCuadrillaListFragment, mGetCuadrilla,
                mRemoveOperario, mUseCaseHandler);
        mCuadrillaListFragment.setPresenter(mCuadrillasPresenter);
    }

    @Override
    public void showTiposCuadrilla(List<Tipo_Cuadrilla> tiposCuadrilla) {
        mTipoCuadrillaAdapter.addAll(tiposCuadrilla);

        Tipo_Cuadrilla tipo_cuadrilla = (Tipo_Cuadrilla) mMenuTipoCuadrillas.getItemAtPosition(0);
        mTipoCuadrilla = tipo_cuadrilla.getTipo_cuadrilla();
        mTipoCuadrillaId = tipo_cuadrilla.getId();

        //initListMvp();
    }

    @Override
    public void setPresenter(TiposCuadrillaToolbarMvp.Presenter presenter) {
        mTiposCuadrillasPresenter = (TiposCuadrillasPresenter) Preconditions.checkNotNull(presenter, "mOrdersPresenter no puede ser null");
    }

    @Override
    public void alEliminarOperario(String operario, int operarioId) {
        EliminiarOperarioDialog a = EliminiarOperarioDialog.newInstance(operario,operarioId);
        a.show(getSupportFragmentManager(), "Eliminar Operario");
    }

    @Override
    public void onPossitiveEliminarOperarioClick(int operarioId) {
        mCuadrillaListFragment.eliminarOperarioOk(operarioId);
    }

    @Override
    public void onNegativeEliminarOperarioClick() {
        // No-op
    }
}
