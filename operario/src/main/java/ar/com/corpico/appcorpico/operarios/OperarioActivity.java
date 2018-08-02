package ar.com.corpico.appcorpico.operarios;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.UseCaseHandler;
import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.operarios.data.OperarioRepository;
import ar.com.corpico.appcorpico.operarios.data.OperarioRestStore;
import ar.com.corpico.appcorpico.operarios.data.OperarioSqliteStore;
import ar.com.corpico.appcorpico.operarios.domain.usecase.AddOperario;
import ar.com.corpico.appcorpico.operarios.domain.usecase.GetOperarios;
import ar.com.corpico.appcorpico.operarios.presentation.OperariosListFragment;
import ar.com.corpico.appcorpico.operarios.presentation.OperariosPresenter;

public class OperarioActivity extends AppCompatActivity implements
        OperariosListFragment.OnCerrarOperariosListener{

    // Claves de argumentos
    public static final String ARG_SECTOR = "operario.sector";
    public static final String ARG_TIPO_CUADRILLA = "operario.tipo_cuadrilla";
    public static final String ARG_SERVICIO = "operario.servicio";

    // Argumentos
    private int mSectorArg;
    private int mServicioArg;
    private int mTipoCuadrillaId;
    //todo: tengo que hacer el join con empresa contratista...el servicio es oml_tipo_empresa en empresa contratista

    // Dependencias
    private OperariosListFragment mOperarioView;
    private GetOperarios mGetOperario;
    private AddOperario mAddOperario;
    private OperariosPresenter mOpeariosPresenter;
    private final UseCaseHandler mUseCaseHandler = UseCaseHandler.getInstance();
    private SessionsPrefsStore prefStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operarios_list_act);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Obtener argumentos
        Bundle args = getIntent().getExtras();
        if (args != null) {
            mSectorArg = args.getInt(ARG_SECTOR);
            mServicioArg = args.getInt(ARG_SERVICIO);
            mTipoCuadrillaId = args.getInt(ARG_TIPO_CUADRILLA);
        }

        setToolbar();

        // Fuentes de datos y repositorios
        prefStore = SessionsPrefsStore.get(
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE));
        OperarioRepository operariosRepository = OperarioRepository.getInstance(
                OperarioRestStore.getInstance(),
                OperarioSqliteStore.getInstance(getContentResolver()),
                prefStore);

        // Casos de uso
        mGetOperario = new GetOperarios(operariosRepository);
        mAddOperario = new AddOperario(operariosRepository);
        // Iniciar Fragmento
        initListMvp();
        // Presentador
        mOpeariosPresenter = new OperariosPresenter(mOperarioView,mGetOperario, mAddOperario,mUseCaseHandler);

    }
    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle("");
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mOperarioView != null) {
            mOpeariosPresenter.loadOperarios(mSectorArg);
        }

    }
    private void initListMvp() {
        mOperarioView = (OperariosListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.operarios_view_container);


        if (mOperarioView == null) {
            mOperarioView = OperariosListFragment.newInstance(mSectorArg,mServicioArg,mTipoCuadrillaId);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.operarios_view_container, mOperarioView, "OpearioView")
                    .commit();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_operario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void alInsertarOperario() {
        //this.finish();
        //onBackPressed();
        super.onBackPressed();
    }

}
