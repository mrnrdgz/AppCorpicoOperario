package ar.com.corpico.appcorpico.orders;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.BaseActivity;
import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.UseCaseHandler;
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
import ar.com.corpico.appcorpico.orders.data.zona.ZonaStore;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.usecase.AddOrdersState;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetOrders;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetTipoCuadrilla;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetTipoTrabajo;
import ar.com.corpico.appcorpico.orders.presentation.OrdersListFragment;
import ar.com.corpico.appcorpico.orders.presentation.OrdersMapsFragment;
import ar.com.corpico.appcorpico.orders.presentation.OrdersPresenter;
import ar.com.corpico.appcorpico.orders.presentation.TiposCuadrillaToolbarMvp;
import ar.com.corpico.appcorpico.orders.presentation.TiposCuadrillasPresenter;
import ar.com.corpico.appcorpico.orders.presentation.adapter.TipoCuadrillaAdapter;
import ar.com.corpico.appcorpico.orders.presentation.dialog.AsignarOrdenDialog;
import ar.com.corpico.appcorpico.orders.presentation.dialog.SortDialog;
import ar.com.corpico.appcorpico.ordersFilter.OrdersFilterActivity;


/**
 * Actividad para screen de ordenes pendientes
 */

public class OrderAsignadaActivity extends BaseActivity implements
        AsignarOrdenDialog.OnAsignarOrderListener,
        TiposCuadrillaToolbarMvp.View,
        SortDialog.SortDialogListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    // Claves de argumentos
    public static final String ARG_ESTADO = "orders.estado";
    public static final String ARG_ESTADO_POST = "orders.estado.post";
    public static final String ARG_SERVICIO = "orders.servicio";
    public static final String ARG_TIPO_CUADRILLA = "orders.tipo_cuadrilla";
    public static final String ARG_TIPO_CUADRILLA_ID = "orders.tipo_cuadrilla_id";
    private static final String ARG_TIPOS_TRABAJO_SELECCIONADOS = "orders.tipos_trabajo_seleccionados";
    public static final String ARG_ZONAS_SELECCIONADAS = "orders.zonas_seleccionadas";
    public static final String ARG_FECHA_INICIO = "orders.fecha_inicio";
    public static final String ARG_FECHA_FIN = "orders.fecha_fin";
    private static final String ARG_ASIGNACION_MASIVA = "ASIGNACIÓN MASIVA";
    private static final String ARG_LATITUD = "LATITUD";
    private static final String ARG_LONGITUD = "LONGITUD";

    // Valores de {ARG_ESTADO}
    public static final String ESTADO_PENDIENTE = "P";
    public static final String ESTADO_ASIGNADA = "O";

    // Argumentos
    private String mEstadoArg;
    private String mEstadoPostArg;
    private int mServicioArg;
    private String mFieldSort = "Fecha Solicitud";

    // Views
    private Spinner mMenuTipoCuadrillas;
    private TipoCuadrillaAdapter mTipoCuadrillaAdapter;

    // Dependencias
    private TiposCuadrillaToolbarMvp.Presenter mTiposCuadrillasPresenter;
    private OrdersListFragment mOrderView;
    private OrdersMapsFragment mOrderMapView;
    private GetOrders mGetOrders;
    private AddOrdersState mAddOrdersState;
    private GetTipoTrabajo mGetTipoTrabajo;
    private GetTipoCuadrilla mGetTipoCuadrilla;
    private OrdersPresenter mOrdersPresenter;
    private final UseCaseHandler mUseCaseHandler = UseCaseHandler.getInstance();

    // key respuesta del Intent para el filtrado
    public final static int OPINION_REQUEST_CODE = 1;
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;

    // Variables
    private boolean mViewMap = true;
    private String mTipoCuadrilla;
    private Integer mTipoCuadrillaId;
    private ArrayList<String> mTiposTrabajoSeleccionados = new ArrayList<>();
    private ArrayList<String> mZonasSeleccionadas = new ArrayList<>();
    private DateTime mFechaInicioSeleccionada;
    private DateTime mFechaFinSeleccionada;
    private String mSearch;
    private String mObservacion;
    private Double mLatitud;
    private Double mLongitud;

    LocationCallback mLocationCallback = new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                Log.i("OrderDetailActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());

            }
        };

    };

    protected NavDrawerItemMenu getDrawerItem() {
        return NavDrawerItemMenu.ASIGNADAS;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_list_act);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Obtener argumentos
        Bundle args = getIntent().getExtras();
        if (args != null) {
            mEstadoArg = args.getString(ARG_ESTADO);
            mServicioArg = args.getInt(ARG_SERVICIO);
            mEstadoPostArg = args.getString(ARG_ESTADO_POST);
            mObservacion = args.getString(ARG_ASIGNACION_MASIVA);
            mLatitud = args.getDouble(ARG_LATITUD);
            mLongitud = args.getDouble(ARG_LONGITUD);
        }

        // UI
        mMenuTipoCuadrillas = findViewById(R.id.spinner_toolBar);
        setUpSpinner();

        // Fuentes de datos y repositorios
        SessionsPrefsStore prefStore = SessionsPrefsStore.get(
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE));
        OrderDataCursorMapper orderMapper = OrderDataCursorMapper.getInstance();
        OrderSqlOrderMapper orderSqlMapper = OrderSqlOrderMapper.getInstance();
        ZonaSqliteStore zonaSqliteStore = ZonaSqliteStore.getInstance(getContentResolver());
        OrdersRepository ordersRepository = OrdersRepository.getInstance(
                OrdersRestStore.getInstance(),
                OrdersSqliteStore.getInstance(getContentResolver(), orderMapper, orderSqlMapper, prefStore,zonaSqliteStore),
                prefStore,
                orderSqlMapper);
        TipoCuadrillasRepository tipocuadrillasRepository = TipoCuadrillasRepository.getInstance(
                TipoCuadrillaRestStore.getInstance(),
                TipoCuadrillaSqliteStore.getInstance(getContentResolver()),
                prefStore);
        TipoTrabajoRepository tipoTrabajoRepository = TipoTrabajoRepository.getInstance(
                TipoTrabajoRestStore.getInstance(),
                TipoTrabajoSqliteStore.getInstance(getContentResolver()),
                prefStore);
        ZonaStore mZonaLocalStore = ZonaSqliteStore.getInstance(getContentResolver());

        // Casos de uso
        mGetTipoCuadrilla = new GetTipoCuadrilla(tipocuadrillasRepository);
        mGetTipoTrabajo = new GetTipoTrabajo(tipoTrabajoRepository);
        mGetOrders = new GetOrders(ordersRepository, mGetTipoTrabajo, mZonaLocalStore);
        mAddOrdersState = new AddOrdersState(ordersRepository);

        // Presentadores
        mTiposCuadrillasPresenter = new TiposCuadrillasPresenter(this, mGetTipoCuadrilla, mUseCaseHandler);
        mTiposCuadrillasPresenter.loadTiposCuadrilla(mServicioArg);

        // Establecer punto de entrada para la API de ubicación
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Gestión framework de búsqueda
        handleIntent(getIntent());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient != null && mFusedLocationClient != null) {
            requestLocationUpdates();
        } else {
            buildGoogleApiClient();
        }

        /*if (mOrderView != null) {
            mOrdersPresenter.loadOrders(
                    mTipoCuadrilla,
                    mEstadoArg,
                    mTiposTrabajoSeleccionados,
                    mZonasSeleccionadas,
                    mFechaInicioSeleccionada,
                    mFechaFinSeleccionada,
                    mSearch,
                    true,
                    mFieldSort);
        }*/

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private void iniciarFragmento() {
        mOrderView = (OrdersListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.orders_view_container);


        if (mOrderView == null) {
            // TODO: Probar cambiar variables de linea por los campos de la actividad
            mOrderView = OrdersListFragment.newInstance(
                    // mTipoCuadrilla, mEstadoArg, new ArrayList<String>(),
                    mTipoCuadrilla, mEstadoArg, mEstadoPostArg, mTipoCuadrillaId, new ArrayList<String>(),
                    new ArrayList<String>(), null, null, mSearch, mFieldSort, mObservacion, mLatitud, mLongitud);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.orders_view_container, mOrderView, "OrderView")
                    .commit();

        }
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
                //mTipoCuadrillaId = tipo_cuadrilla.getGeasysId();
                mTipoCuadrillaId = tipo_cuadrilla.getId();

                mTiposTrabajoSeleccionados.clear();
                mZonasSeleccionadas.clear();
                mSearch = "";
                mFechaInicioSeleccionada = null;
                mFechaFinSeleccionada = null;

                // Se refresca el fragmento luego de seleccionar
                showFragment();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // no-op
            }
        });
    }

    private void showFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ftList = getSupportFragmentManager().beginTransaction();
        Fragment fragmentList = fm.findFragmentById(R.id.orders_view_container);

        if ((fragmentList instanceof OrdersListFragment)) {
            mOrderView = OrdersListFragment.newInstance(mTipoCuadrilla, mEstadoArg, mEstadoPostArg, mTipoCuadrillaId, mTiposTrabajoSeleccionados,
                    mZonasSeleccionadas, mFechaInicioSeleccionada, mFechaFinSeleccionada, mSearch, mFieldSort, mObservacion, mLatitud, mLongitud);
            ftList.replace(R.id.orders_view_container, mOrderView, "OrderView")
                    .commit();
            mOrdersPresenter = new OrdersPresenter(mGetOrders, mAddOrdersState, mOrderView, mUseCaseHandler);
            mOrderView.setPresenter(mOrdersPresenter);
        }

        FragmentTransaction ftMap = getSupportFragmentManager().beginTransaction();
        Fragment fragmentMap = fm.findFragmentById(R.id.orders_view_container);

        if ((fragmentMap instanceof OrdersMapsFragment)) {
            mOrderMapView = OrdersMapsFragment.newInstance(mTipoCuadrilla, mEstadoArg, mEstadoPostArg, mTipoCuadrillaId, mTiposTrabajoSeleccionados,
                    mZonasSeleccionadas, mFechaInicioSeleccionada, mFechaFinSeleccionada, mSearch, mFieldSort, mObservacion, mLatitud, mLongitud);
            ftMap.replace(R.id.orders_view_container, mOrderMapView, "OrderViewMap")
                    .commit();
            mOrdersPresenter = new OrdersPresenter(mGetOrders, mAddOrdersState, mOrderMapView, mUseCaseHandler);
            mOrderMapView.setPresenter(mOrdersPresenter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_otpendientes, menu);

        // Associate searchable configuratio with the SearchView
        final MenuItem menuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        // Conexión entre SearchView y searchable.xml
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        /*ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        searchView.setLayoutParams(params);*/

        // Personalización del SearchView
        searchView.setSubmitButtonEnabled(true);

        /*searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 0) {
                    // Search
                } else {
                    // Do something when there's no input
                    mSearch = "";
                    mOrdersPresenter.loadOrders(mTipoCuadrilla, mEstadoArg, mTiposTrabajoSeleccionados,
                            mZonasSeleccionadas, mFechaInicioSeleccionada, mFechaFinSeleccionada, mSearch, true,mFieldSort);
                }
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        });

        return true;*/
        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                // Retornar al estado inicial de busqueda
                mSearch = "";
                mOrdersPresenter.loadOrders(
                        mTipoCuadrilla,
                        mEstadoArg,
                        mTiposTrabajoSeleccionados,
                        mZonasSeleccionadas,
                        mFechaInicioSeleccionada,
                        mFechaFinSeleccionada,
                        mSearch,
                        true,
                        mFieldSort);

                // actualiazar argumentos del fragmento
                mOrderView.setOrdersParams(mTipoCuadrilla,
                        mEstadoArg,
                        mTiposTrabajoSeleccionados,
                        mZonasSeleccionadas,
                        mFechaInicioSeleccionada,
                        mFechaFinSeleccionada,
                        mSearch,
                        true,
                        mFieldSort);

                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_filtrar:
                Intent intent = new Intent(this, OrdersFilterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(ARG_ESTADO, mEstadoArg);
                intent.putExtra(ARG_ESTADO_POST, mEstadoPostArg);
                intent.putExtra(ARG_TIPO_CUADRILLA, mTipoCuadrilla);
                intent.putStringArrayListExtra(ARG_TIPOS_TRABAJO_SELECCIONADOS, mTiposTrabajoSeleccionados);
                intent.putStringArrayListExtra(ARG_ZONAS_SELECCIONADAS, mZonasSeleccionadas);
                intent.putExtra(ARG_FECHA_INICIO, mFechaInicioSeleccionada);
                intent.putExtra(ARG_FECHA_FIN, mFechaFinSeleccionada);
                startActivityForResult(intent, OPINION_REQUEST_CODE);
                break;
            case R.id.action_map:
                mViewMap = false;
                invalidateOptionsMenu();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment fragment = fm.findFragmentById(R.id.orders_view_container);
                if (!(fragment instanceof OrdersMapsFragment)) {
                    mOrderMapView = OrdersMapsFragment.newInstance(mTipoCuadrilla, mEstadoArg, mEstadoPostArg, mTipoCuadrillaId, mTiposTrabajoSeleccionados,
                            mZonasSeleccionadas, mFechaInicioSeleccionada, mFechaFinSeleccionada, mSearch, mFieldSort, mObservacion, mLatitud, mLongitud);
                    ft.replace(R.id.orders_view_container, mOrderMapView, "OrderViewMap")
                            .commit();
                    mOrdersPresenter = new OrdersPresenter(mGetOrders, mAddOrdersState, mOrderMapView, mUseCaseHandler);
                    mOrderMapView.setPresenter(mOrdersPresenter);
                }
                break;
            case R.id.action_list:
                mViewMap = true;
                invalidateOptionsMenu();
                fm = getSupportFragmentManager();
                ft = getSupportFragmentManager().beginTransaction();
                Fragment fragment1 = fm.findFragmentById(R.id.orders_view_container);
                if (!(fragment1 instanceof OrdersListFragment)) {
                    mOrderView = OrdersListFragment.newInstance(mTipoCuadrilla, mEstadoArg, mEstadoPostArg, mTipoCuadrillaId, mTiposTrabajoSeleccionados,
                            mZonasSeleccionadas, mFechaInicioSeleccionada, mFechaFinSeleccionada, mSearch, mFieldSort, mObservacion, mLatitud, mLongitud);
                    ft.replace(R.id.orders_view_container, mOrderView, "OrderView")
                            .commit();
                    mOrdersPresenter = new OrdersPresenter(mGetOrders, mAddOrdersState, mOrderView, mUseCaseHandler);
                    mOrderView.setPresenter(mOrdersPresenter);
                }
                break;
            case R.id.action_ordenar:
                FragmentManager fragmentManager = getSupportFragmentManager();
                new SortDialog().newInstance(mFieldSort).show(fragmentManager, "SortDialog");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            mSearch = intent.getStringExtra(SearchManager.QUERY);
            //TODO: VER SI ACA NO TENGO QUE PONER LAS VARIABLES DE SELECCION --TRABAJO Y ZONAS Y FECHAS....
            /*mOrdersPresenter.loadOrders(mTipoCuadrilla, mEstadoArg, mTiposTrabajoSeleccionados, mZonasSeleccionadas,
                    mFechaInicioSeleccionada, mFechaFinSeleccionada, mSearch, true, mFieldSort);*/
            mOrderView.setOrdersParams(mTipoCuadrilla, mEstadoArg, mTiposTrabajoSeleccionados, mZonasSeleccionadas,
                    mFechaInicioSeleccionada, mFechaFinSeleccionada, mSearch, true, mFieldSort);
        }
    }

    @Override
    public void onPossitiveButtonAsignarClick(final String estado, final ArrayList<Integer> listOrders, final String observacion, String tipoCuadrilla,
                                              final Integer tipoCuadrillaId, Double latitud, Double longitud) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        // ...
                        Location result = task.getResult();

                        Double latitud = result.getLatitude();
                        Double longitud = result.getLongitude();
                        // llamo al presentador aca abajo pasandole la latitud y la longitud
                        mOrdersPresenter.asignarOrder(estado, listOrders, observacion, tipoCuadrillaId, latitud, longitud);
                    }
                });
    }

    @Override
    public void onNegativeButtonAsignarClick() {

    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mViewMap) {
            MenuItem item = menu.findItem(R.id.action_list);
            item.setVisible(false);
            item = menu.findItem(R.id.action_map);
            item.setVisible(true);
        } else {
            MenuItem item = menu.findItem(R.id.action_list);
            item.setVisible(true);
            item = menu.findItem(R.id.action_map);
            item.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OPINION_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                mTiposTrabajoSeleccionados = data.getStringArrayListExtra(ARG_TIPOS_TRABAJO_SELECCIONADOS);
                mZonasSeleccionadas = data.getStringArrayListExtra(ARG_ZONAS_SELECCIONADAS);
                mFechaInicioSeleccionada = (DateTime) data.getSerializableExtra(ARG_FECHA_INICIO);
                mFechaFinSeleccionada = (DateTime) data.getSerializableExtra(ARG_FECHA_FIN);

                //TODO: VER ACA XQ ESTO PUEDE QUE FUNCIONE PERO DEBO ACTUALIZAR EL VALOR DE LAS VARIABLES DE LA VISTA
                /*if (mViewMap){
                    OrdersListFragment mOrderFragmen = (OrdersListFragment) getSupportFragmentManager().findFragmentById(R.id.orders_view_container);
                    mOrderFragmen.setOrderFilter(mEstadoArg, mTipoTrabajoSelected, mZonaSelected, mFechaDesdeSelected, mFechaFinSeleccionada, null,true);
                }else{
                    OrdersMapsFragment mOrderMapFragment = (OrdersMapsFragment)getSupportFragmentManager().findFragmentById(R.id.orders_view_container);
                    mOrderMapFragment.setOrderFilter(mEstadoArg, mTipoTrabajoSelected, mZonaSelected, mFechaDesdeSelected, mFechaFinSeleccionada, null,true);
                }*/
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment fragment = fm.findFragmentById(R.id.orders_view_container);
                if ((fragment instanceof OrdersMapsFragment)) {
                    mOrderMapView = OrdersMapsFragment.newInstance(mTipoCuadrilla, mEstadoArg, mEstadoPostArg,mTipoCuadrillaId,mTiposTrabajoSeleccionados,
                            mZonasSeleccionadas, mFechaInicioSeleccionada, mFechaFinSeleccionada, mSearch, mFieldSort,mObservacion, mLatitud,mLongitud);
                    ft.replace(R.id.orders_view_container, mOrderMapView, "OrderViewMap")
                            .commit();
                    mOrdersPresenter = new OrdersPresenter(mGetOrders, mAddOrdersState, mOrderMapView, mUseCaseHandler);
                    mOrderMapView.setPresenter(mOrdersPresenter);
                }
                fm = getSupportFragmentManager();
                ft = getSupportFragmentManager().beginTransaction();
                Fragment fragment1 = fm.findFragmentById(R.id.orders_view_container);
                if ((fragment1 instanceof OrdersListFragment)) {
                    //TODO: VER SI ACA LLAMO CON QUERY O CON NULL (XQ SI APRETA EL FILTRAR PODER HACER UN FILTRADO CON BUSCAR....
                    //mOrderView = OrdersListFragment.newInstance(mTipoCuadrilla, mEstadoArg, mTiposTrabajoSeleccionados,mZonasSeleccionadas, mFechaInicioSeleccionada, mFechaFinSeleccionada,null);
                    mOrderView = OrdersListFragment.newInstance(mTipoCuadrilla, mEstadoArg, mEstadoPostArg,mTipoCuadrillaId,mTiposTrabajoSeleccionados,
                            mZonasSeleccionadas, mFechaInicioSeleccionada, mFechaFinSeleccionada, mSearch, mFieldSort,mObservacion, mLatitud,mLongitud);
                    ft.replace(R.id.orders_view_container, mOrderView, "OrderView")
                            .commit();
                    mOrdersPresenter = new OrdersPresenter(mGetOrders, mAddOrdersState, mOrderView, mUseCaseHandler);
                    mOrderView.setPresenter(mOrdersPresenter);
                }
            }
        }
    }

    @Override
    public void showTiposCuadrilla(List<Tipo_Cuadrilla> tiposCuadrilla) {
        mTipoCuadrillaAdapter.addAll(tiposCuadrilla);
        // Valor inicial
        if (mOrderView == null) {
            Tipo_Cuadrilla tipo_cuadrilla = (Tipo_Cuadrilla) mMenuTipoCuadrillas.getItemAtPosition(0);
            mTipoCuadrilla = tipo_cuadrilla.getTipo_cuadrilla();
            //mTipoCuadrillaId = tipo_cuadrilla.getGeasysId();
            mTipoCuadrillaId = tipo_cuadrilla.getId();
            iniciarFragmento();
            // Presentador
            mOrdersPresenter = new OrdersPresenter(mGetOrders, mAddOrdersState, mOrderView, mUseCaseHandler);
            // Seteo el presentador a la vista
            mOrderView.setPresenter(mOrdersPresenter);
        }
        //mTipoCuadrilla = (String) mMenuTipoCuadrillas.getItemAtPosition(0);
        Tipo_Cuadrilla tipo_cuadrilla = (Tipo_Cuadrilla) mMenuTipoCuadrillas.getItemAtPosition(0);
        mTipoCuadrilla = tipo_cuadrilla.getTipo_cuadrilla();
        //mTipoCuadrillaId = tipo_cuadrilla.getGeasysId();
        mTipoCuadrillaId = tipo_cuadrilla.getId();
    }

    @Override
    public void setPresenter(TiposCuadrillaToolbarMvp.Presenter presenter) {
        mTiposCuadrillasPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void onDialogSortClick(String fieldSort) {
        mFieldSort = fieldSort;
        mOrdersPresenter.loadOrders(mTipoCuadrilla, mEstadoArg, mTiposTrabajoSeleccionados, mZonasSeleccionadas,
                mFechaInicioSeleccionada, mFechaFinSeleccionada, mSearch, true, mFieldSort);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        requestLocationUpdates();
    }

    public void requestLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(120000); // two minute interval
        mLocationRequest.setFastestInterval(120000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }
    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
