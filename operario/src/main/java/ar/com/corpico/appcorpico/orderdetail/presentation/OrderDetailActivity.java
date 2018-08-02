package ar.com.corpico.appcorpico.orderdetail.presentation;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.joda.time.DateTime;

import java.util.ArrayList;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.UseCaseHandler;
import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orderdetail.presentation.dialog.AsignarOrderDetailDialog;
import ar.com.corpico.appcorpico.orderdetail.presentation.dialog.AsignarTurnoDialog;
import ar.com.corpico.appcorpico.orderdetailimages.FotoOrden;
import ar.com.corpico.appcorpico.orderdetailimages.GalleryActivity;
import ar.com.corpico.appcorpico.orders.data.entity.sql.OrderDataCursorMapper;
import ar.com.corpico.appcorpico.orders.data.entity.sql.OrderSqlOrderMapper;
import ar.com.corpico.appcorpico.orders.data.orders.OrdersRepository;
import ar.com.corpico.appcorpico.orders.data.orders.OrdersRestStore;
import ar.com.corpico.appcorpico.orders.data.orders.OrdersSqliteStore;
import ar.com.corpico.appcorpico.orders.data.zona.ZonaSqliteStore;
import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.usecase.AddOrdersState;
import ar.com.corpico.appcorpico.orders.domain.usecase.AddTurno;
import ar.com.corpico.appcorpico.orders.domain.usecase.CancelTurno;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetOrder;


public class OrderDetailActivity extends AppCompatActivity implements
        AsignarOrderDetailDialog.OnAsignarOrderDetailListener,
        ar.com.corpico.appcorpico.orderdetail.presentation.View,
        OnMapReadyCallback,
        AsignarTurnoDialog.OnAsignarTurnoDialogListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private static final int LOCATION_REQUEST_CODE = 1;
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;

    // Google Maps
    private GoogleMap mGoogleMap;

    // Componentes arquitectura
    private final UseCaseHandler mUseCaseHandler = UseCaseHandler.getInstance();
    private OrdersDetailPresenter mPresenter;
    private SupportMapFragment mMapFragment;
    private AddOrdersState mAddOrdersState;
    private AddTurno mAddTurno;
    private CancelTurno mCancelTurno;
    private GetOrder mGetOrder;

    // Variables
    private String mLat;
    private String mLng;
    private String mTurno;
    private DateTime mFechaSolicitud;
    private Integer mNumero;
    private Integer mTipoCuadrillaId;
    private String mTipoCuadrilla;
    private ArrayList<Etapa> mEtapas;
    private String mEstadoPost;
    private ArrayList<FotoOrden> mFotos;

    //View
    TextView numero;
    TextView fecha;
    TextView turno;
    TextView motivo;
    TextView tipoTrabajo;
    TextView titular;
    TextView asociado;
    TextView domicilio;
    TextView anexo;
    TextView grupo;
    TextView ruta;
    TextView orden;
    TextView tipousuario;
    TextView tarifa;
    TextView potenciadeclarada;
    TextView medidor;
    TextView marca;
    TextView modelo;
    TextView factorM;
    TextView capacidad;
    TextView tension;
    TextView observacion;
    ImageButton galleryIButton;
    ImageButton etapaIButton;

    LocationCallback mLocationCallback = new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                Log.i("OrderDetailActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());

            }
        };

    };

    // Código de resultados para persmisos
    private final int REQUEST_LOCATION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_detail);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Obtención de views
        numero = findViewById(R.id.numero_text);
        fecha = findViewById(R.id.fecha_text);
        turno = findViewById(R.id.turno_text);
        motivo = findViewById(R.id.motivo_text);
        tipoTrabajo = findViewById(R.id.tipotrabajo_text);
        titular = findViewById(R.id.titular_text);
        asociado = findViewById(R.id.asociado_text);
        domicilio = findViewById(R.id.domicilio_text);
        anexo = findViewById(R.id.anexo_text);
        grupo = findViewById(R.id.grupo_text);
        ruta = findViewById(R.id.ruta_text);
        orden = findViewById(R.id.orden_text);
        tipousuario = findViewById(R.id.tipousuario_text);
        tarifa = findViewById(R.id.tarifa_text);
        potenciadeclarada = findViewById(R.id.potenciadeclarada_text);
        medidor = findViewById(R.id.medidor_text);
        marca = findViewById(R.id.marca_text);
        modelo = findViewById(R.id.modelo_text);
        factorM = findViewById(R.id.factorm_text);
        capacidad = findViewById(R.id.capacidad_text);
        tension = findViewById(R.id.tension_text);
        observacion = findViewById(R.id.observacion_text);
        galleryIButton = findViewById(R.id.button_gallery);
        etapaIButton = findViewById(R.id.button_etapa);

        etapaIButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent i = new Intent(OrderDetailActivity.this, EtapaActivity.class);
                i.putExtra("ETAPAS", mEtapas);
                startActivity(i);
            }
        });

        galleryIButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent i = new Intent(OrderDetailActivity.this, GalleryActivity.class);
                i.putExtra("ORDEN", mNumero);
                startActivity(i);
            }
        });

        setToolbar();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mNumero = (Integer) extras.get("NUMERO");
            mTipoCuadrilla = (String) extras.get("TIPO_CUADRILLA");
            mTipoCuadrillaId = (Integer) extras.get("TIPO_CUADRILLA_ID");
            mEstadoPost = (String) extras.get("ESTADO_POST");
            mTurno = (String) extras.get("TURNO");
        }


        // Fuentes de datos y repositorios
        SessionsPrefsStore PrefStore = SessionsPrefsStore.get(
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE));
        OrderDataCursorMapper orderMapper = OrderDataCursorMapper.getInstance();
        OrderSqlOrderMapper orderSqlMapper = OrderSqlOrderMapper.getInstance();
        ZonaSqliteStore zonaSqliteStore = ZonaSqliteStore.getInstance(getContentResolver());
        OrdersRepository ordersRepository = OrdersRepository.getInstance(
                OrdersRestStore.getInstance(),
                OrdersSqliteStore.getInstance(getContentResolver(), orderMapper, orderSqlMapper, PrefStore,zonaSqliteStore),
                PrefStore,
                orderSqlMapper);


        /**
         * <<create>> Casos de uso
         */
        mAddOrdersState = new AddOrdersState(ordersRepository);
        mAddTurno = new AddTurno(ordersRepository);
        mCancelTurno = new CancelTurno(ordersRepository);
        mGetOrder = new GetOrder(ordersRepository);

        mPresenter = new OrdersDetailPresenter(mAddOrdersState, mAddTurno, mCancelTurno,mGetOrder, this, mUseCaseHandler);
        setPresenter(mPresenter);

        mMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this); // Init Map

        // Establecer punto de entrada para la API de ubicación
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient != null && mFusedLocationClient != null) {
            requestLocationUpdates();
        } else {
            buildGoogleApiClient();
        }
        // TODO: Cargar orden por ID
        mPresenter.loadOrder(mNumero);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }
    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_ot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_asignaracuadrilla:
                // mPresenter.asignarCuadrillaClick();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment prev = getSupportFragmentManager().findFragmentByTag("AsignarconexionDialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                DialogFragment newFragment = AsignarOrderDetailDialog.newInstance(mEstadoPost, mTipoCuadrilla, mNumero, mTipoCuadrillaId);
                newFragment.show(ft, "AsignarconexionDialog");
                break;
            case R.id.action_turno:
                FragmentTransaction turnoTransaccion = getSupportFragmentManager().beginTransaction();
                turnoTransaccion.addToBackStack(null);
                DialogFragment asignarTurnoDialog;

                asignarTurnoDialog = AsignarTurnoDialog.newInstance(mTurno);
                asignarTurnoDialog.show(turnoTransaccion, "AsignarTurnoDialog");

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public Double sanitizeCoordinate(Double rawDouble) {
        return rawDouble * -1;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        UiSettings uiSettings = mGoogleMap.getUiSettings();
        uiSettings.setScrollGesturesEnabled(true);
        uiSettings.setTiltGesturesEnabled(true);

        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        Double mLt, mLn;
        CameraPosition cameraPosition;

        if (mLat != null && mLng != null) {
            mLt = sanitizeCoordinate(Double.parseDouble(mLat.substring(0, 7)));
            mLn = sanitizeCoordinate(Double.parseDouble(mLng.substring(0, 7)));
            /*mLt = converLocationDD(mLat);
            mLn = converLocationDD(mLng);*/
            LatLng mLatLng = new LatLng(mLt, mLn);
            mGoogleMap.addMarker(new MarkerOptions()
                    .position(mLatLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

            cameraPosition = CameraPosition.builder()
                    .target(mLatLng)
                    .zoom(16) // TODO: Mover a constante
                    .build();
        } else {
            LatLng pico = new LatLng(-35.658103, -63.757882); // TODO: Mover a constantes
            cameraPosition = new CameraPosition.Builder()
                    .target(pico)
                    .zoom(14)
                    .build();
        }

        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            // ¿Permisos asignados?
            if (permissions.length > 0 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                Toast.makeText(this, "Error de permisos", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void onPossitiveButtonAsignarOrderDetailClick(final String estadoPost, final String tipoCuadrilla, Integer numero, final String observacion, final Integer tipoCuadrillaId) {
        final ArrayList<Integer> mNumeroOT = new ArrayList<>();
        mNumeroOT.add(numero);

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
        // Get the last known location
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        // ...
                        Location result = task.getResult();

                        Double latitud = result.getLatitude();
                        Double longitud = result.getLongitude();
                        // llamo al presentador aca abajo pasandole la latitud y la longitud
                        mPresenter.asignarOrder(estadoPost, tipoCuadrilla, mNumeroOT, observacion, tipoCuadrillaId,latitud,longitud);
                    }
                });

    }

    @Override
    public void closeDetail() {
        this.finish();
    }


    /*
    public void showAnularTurnoDialogo(int year, int month, int day){
        crear dialogo;
        mostrar_dialogo(year, month, day);
    }
     */

    @Override
    public void showOrderError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void setPresenter(Presenter presenterDetail) {
        mPresenter = (OrdersDetailPresenter) presenterDetail;
    }

    @Override
    public void onPossitiveButtonTurnoClick(String turno) {
        mPresenter.asignarTurno(mNumero, turno);
    }

    @Override
    public void onNegativeButtonTurnoClick() {
        mPresenter.anularTurno(mNumero);
    }
    @Override
    public void refreshTurno(String turno) {
        TextView mTurno = this.findViewById(R.id.turno_text);
        mTurno.setText(turno);
    }

    @Override
    public void showOrder(Order o) {
        if (o != null) {
            numero.setText("Orden Nº " + o.getNumero());
            mFechaSolicitud = o.getFechaSolicitud();
            fecha.setText(mFechaSolicitud.toString("dd-MM-yyyy"));
            turno.setText(o.getTurno());
            tipoTrabajo.setText(o.getTipo_Trabajo());
            motivo.setText(o.getMotivo());
            titular.setText(o.getTitular());
            asociado.setText(o.getAsociado() + "/" + o.getSuministro());
            domicilio.setText(o.getDomicilio());
            anexo.setText(o.getAnexo());
            grupo.setText(String.valueOf(o.getGrupo()));
            ruta.setText(String.valueOf(o.getRuta()));
            orden.setText(String.valueOf(o.getOrden()));
            tipousuario.setText(o.getTipo_Usuario());
            tarifa.setText(o.getTarifa());
            potenciadeclarada.setText(o.getPotencia_Declarada());
            medidor.setText(o.getMedidor());
            marca.setText(o.getMarca());
            modelo.setText(o.getModelo());
            factorM.setText(o.getFactorM());
            capacidad.setText(o.getCapacidad());
            tension.setText(o.getTension());
            observacion.setText(o.getObservacion());
            mEtapas = (ArrayList<Etapa>) o.getEtapas();
            mFotos =  o.getFotos();

            //Datos para georrefenciar el domicilio
            mLat = o.getLatitud();
            mLng = o.getLongitud();

            //Georreferencia el domicilio
            mMapFragment.getMapAsync(this); // Init Map
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
       /* if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitud = mLastLocation.getLatitude();
            mLongitud = mLastLocation.getLongitude();
        }*/
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
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        //mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //mGoogleApiClient.disconnect();
    }
    @Override
    public void onPause() {
        super.onPause();
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    private boolean isLocationPermissionGranted() {
        int permission = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permission == PackageManager.PERMISSION_GRANTED;
    }

    private void manageDeniedPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Aquí muestras confirmación explicativa al usuario
            // por si rechazó los permisos anteriormente
        } else {
            ActivityCompat.requestPermissions(
                    this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        }
    }
    public Double converLocationDD(String coordenada){
        double DD;
        int degrees, minutes, seconds;

        degrees = Integer.parseInt((coordenada.substring(0,2)));
        minutes = Integer.parseInt((coordenada.substring(3,5)));
        seconds = Integer.parseInt((coordenada.substring(5,7)));
        String cuadrante = coordenada.substring(7);

            DD = ((seconds/3600) + (minutes/60) + degrees)*-1;


        return DD;

    }

}
