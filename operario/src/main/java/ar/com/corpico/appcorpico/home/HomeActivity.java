package ar.com.corpico.appcorpico.home;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import ar.com.corpico.appcorpico.BaseActivity;
import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.UseCaseHandler;
import ar.com.corpico.appcorpico.home.domain.usecase.GetCuadrillasHome;
import ar.com.corpico.appcorpico.home.domain.usecase.GetEstadosHome;
import ar.com.corpico.appcorpico.home.domain.usecase.GetTiposTrabajoHome;
import ar.com.corpico.appcorpico.home.domain.usecase.GetZonasHome;
import ar.com.corpico.appcorpico.home.presentation.HomeFragment;
import ar.com.corpico.appcorpico.home.presentation.HomePresenter;
import ar.com.corpico.appcorpico.login.LoginActivity;
import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orders.data.entity.sql.OrderDataCursorMapper;
import ar.com.corpico.appcorpico.orders.data.entity.sql.OrderSqlOrderMapper;
import ar.com.corpico.appcorpico.orders.data.orders.OrdersRepository;
import ar.com.corpico.appcorpico.orders.data.orders.OrdersRestStore;
import ar.com.corpico.appcorpico.orders.data.orders.OrdersSqliteStore;
import ar.com.corpico.appcorpico.orders.data.tipoCuadrilla.TipoCuadrillaRestStore;
import ar.com.corpico.appcorpico.orders.data.tipoCuadrilla.TipoCuadrillaSqliteStore;
import ar.com.corpico.appcorpico.orders.data.tipoCuadrilla.TipoCuadrillasRepository;
import ar.com.corpico.appcorpico.orders.data.tipoTrabajo.TipoTrabajoRepository;
import ar.com.corpico.appcorpico.orders.data.tipoTrabajo.TipoTrabajoRestStore;
import ar.com.corpico.appcorpico.orders.data.tipoTrabajo.TipoTrabajoSqliteStore;
import ar.com.corpico.appcorpico.orders.data.zona.ZonaSqliteStore;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;
import ar.com.corpico.appcorpico.sync.SyncAdapter;

import static android.view.View.VISIBLE;

public class HomeActivity extends BaseActivity {
    // Claves de argumentos
    public static final String ARG_SERVICIO = "home.servicio";
    public static final String ARG_SECTOR = "home.sector";

    // Dependencias
    private HomeFragment mHomeView;
    private HomePresenter mHomePresenter;
    private GetCuadrillasHome mGetCuadrillasHome;
    private GetEstadosHome mGetEstadosHome;
    private GetTiposTrabajoHome mGetTiposTrabajoHome;
    private GetZonasHome mGetZonasHome;
    private final UseCaseHandler mUseCaseHandler = UseCaseHandler.getInstance();

    // Variable
    boolean esPrimeraCarga;

    private int mServicioArg;
    private int mSectorArg;

    // View
    private View mProgressContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Obtener argumentos
        Bundle args = getIntent().getExtras();
        if (args != null) {
            mServicioArg = args.getInt(ARG_SERVICIO);
            mSectorArg = args.getInt(ARG_SECTOR);
        }

        // Fuentes de datos y repositorios
        SessionsPrefsStore sessionsPrefsStore = SessionsPrefsStore.get(
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE));
        OrderDataCursorMapper orderMapper = OrderDataCursorMapper.getInstance();
        OrderSqlOrderMapper orderSqlMapper = OrderSqlOrderMapper.getInstance();
        ZonaSqliteStore zonaSqliteStore = ZonaSqliteStore.getInstance(getContentResolver());
        OrdersRepository ordersRepository = OrdersRepository.getInstance(
                OrdersRestStore.getInstance(),
                OrdersSqliteStore.getInstance(getContentResolver(), orderMapper, orderSqlMapper, sessionsPrefsStore,zonaSqliteStore),
                sessionsPrefsStore,
                orderSqlMapper);
        TipoCuadrillasRepository tipocuadrillasRepository = TipoCuadrillasRepository.getInstance(
                TipoCuadrillaRestStore.getInstance(),
                TipoCuadrillaSqliteStore.getInstance(getContentResolver()),
                sessionsPrefsStore);
        TipoTrabajoRepository tipoTrabajoRepository = TipoTrabajoRepository.getInstance(
                TipoTrabajoRestStore.getInstance(),
                TipoTrabajoSqliteStore.getInstance(getContentResolver()),
                sessionsPrefsStore);

        // Transición de Home a Login
        esPrimeraCarga = sessionsPrefsStore.esPrimeraCarga();
        if(!sessionsPrefsStore.isLogged()) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_home);
        mProgressContainer = findViewById(R.id.progress_container);

        setTitle("Inicio");

        if(esPrimeraCarga){
            // Si es primera carga muestra la barra de progreso
            mProgressContainer.setVisibility(VISIBLE);

            Bundle settingsBundle = new Bundle();
            settingsBundle.putBoolean(
                    ContentResolver.SYNC_EXTRAS_MANUAL, true);
            settingsBundle.putBoolean(
                    ContentResolver.SYNC_EXTRAS_EXPEDITED, true);

            ContentResolver.requestSync(SyncAdapter.obtenerCuentaASincronizar(this), ContratoCorpicoApp.AUTHORITY, settingsBundle);

            ContentResolver.addPeriodicSync(SyncAdapter.obtenerCuentaASincronizar(this),
                    ContratoCorpicoApp.AUTHORITY,Bundle.EMPTY,60*60);
        }

        // Vista
        iniciarFragmento();

        // Casos de uso
        mGetCuadrillasHome = new GetCuadrillasHome(ordersRepository);
        mGetEstadosHome = new GetEstadosHome(ordersRepository);
        mGetTiposTrabajoHome = new GetTiposTrabajoHome(ordersRepository);
        mGetZonasHome = new GetZonasHome(ordersRepository);

        // Presentador
        mHomePresenter = new HomePresenter(mGetCuadrillasHome,mGetEstadosHome,mGetTiposTrabajoHome,mGetZonasHome,mHomeView,mUseCaseHandler);
        mHomeView.setPresenter(mHomePresenter);

    }

    private void iniciarFragmento() {
        mHomeView = (HomeFragment) getSupportFragmentManager()
                .findFragmentById(R.id.home_view_container);


        if (mHomeView == null) {
            mHomeView = HomeFragment.newInstance(mServicioArg,mSectorArg,esPrimeraCarga); // Añadir parámetro de si e priemra carga

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.home_view_container, mHomeView, "HomeView")
                    .commit();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(mSyncMessageReceiver,
                new IntentFilter("home-syncadapter-message"));

        // Esconde el boton de menu toolbar
        if(esPrimeraCarga){
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mSyncMessageReceiver);
        super.onPause();
    }

    protected NavDrawerItemMenu getDrawerItem() {
        return NavDrawerItemMenu.HOME;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private BroadcastReceiver mSyncMessageReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean sync = intent.getBooleanExtra("sync", false);

            if(sync){
                // Esconde la barra de progreso
                mProgressContainer.setVisibility(View.GONE);
                // Muestra el boton de menú
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                // Ordenar al fragmento cargar datos con el presentador
                mHomeView.onSyncFinish();
            }else {
                // Mostrar mensaje avisando que la sincronizaicón falló
                Toast.makeText(getApplicationContext(), "La Syncronización ha fallado ", Toast.LENGTH_LONG).show();

            }
        }
    };
}
