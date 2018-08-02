package ar.com.corpico.appcorpico.orderdetailimages;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.UseCaseHandler;
import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orders.data.entity.sql.OrderDataCursorMapper;
import ar.com.corpico.appcorpico.orders.data.entity.sql.OrderSqlOrderMapper;
import ar.com.corpico.appcorpico.orders.data.orders.OrdersRepository;
import ar.com.corpico.appcorpico.orders.data.orders.OrdersRestStore;
import ar.com.corpico.appcorpico.orders.data.orders.OrdersSqliteStore;
import ar.com.corpico.appcorpico.orders.data.zona.ZonaSqliteStore;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetFotoOrder;

public class GalleryActivity extends AppCompatActivity implements OrderDetailImagesMvp.View {

    private RecyclerView listaUI;
    private LinearLayoutManager linearLayoutManager;
    private FotosAdapter adaptador;
    private GetFotoOrder mGetFotoOrder;
    private Integer mNumero;

    // Arch
    private FotosOrdenPresenter mPresenter;
    private final UseCaseHandler mUseCaseHandler = UseCaseHandler.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mNumero = extras.getInt("ORDEN");
        }

        // Preparar lista
        listaUI = findViewById(R.id.rv_fotos);

        linearLayoutManager = new LinearLayoutManager(this);
        listaUI.setLayoutManager(linearLayoutManager);

        adaptador = new FotosAdapter(this, new ArrayList<FotoOrden>(0));
        adaptador.setOnItemClickListener(new FotosAdapter.OnItemClickListener() {
            @Override
            public void onPhotoClick(ImageView photo, int position, String url) {
                showPhotoInAndroidGallery(url);
            }
        });
        listaUI.setAdapter(adaptador);

        // TODO: Recibir ID de la orden asociada
        SessionsPrefsStore prefStore = SessionsPrefsStore.get(
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE));
        ZonaSqliteStore zonaSqliteStore = ZonaSqliteStore.getInstance(getContentResolver());
        OrderDataCursorMapper orderMapper = OrderDataCursorMapper.getInstance();
        OrderSqlOrderMapper orderSqlMapper = OrderSqlOrderMapper.getInstance();
        OrdersRepository ordersRepository = OrdersRepository.getInstance(
                OrdersRestStore.getInstance(),
                OrdersSqliteStore.getInstance(getContentResolver(), orderMapper, orderSqlMapper, prefStore,zonaSqliteStore),
                prefStore,
                orderSqlMapper);

        mGetFotoOrder = new GetFotoOrder(ordersRepository);
        // Arch
        mPresenter = new FotosOrdenPresenter(this, mGetFotoOrder, mUseCaseHandler);

    }

    private void showPhotoInAndroidGallery(String url) {

        Uri uriForFile = Uri.fromFile(new File(url));
        //Uri uriForFile = FileProvider.getUriForFile(this, getPackageName(), new File(url));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uriForFile, "image/*");
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadFotosOrden(mNumero);
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle("Fotos");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void showFotosOrden(List<FotoOrden> fotosOrden) {
        adaptador.addMoreFotos(fotosOrden);
    }

}
