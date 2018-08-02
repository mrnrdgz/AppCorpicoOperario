package ar.com.corpico.appcorpico.orderdetail.presentation;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orderdetail.presentation.adapter.EtapasAdapter;
import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;

public class EtapaActivity extends AppCompatActivity {
    private ListView listEtapas;
    private EtapasAdapter mEtapasAdapter;
    private ArrayList<Etapa> mEtapas;
    private TextView mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etapa);

        listEtapas = findViewById(R.id.etapas_list);
        mEmptyView = findViewById(R.id.orders_empty);

        setToolbar();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mEtapas = extras.getParcelableArrayList("ETAPAS");
        }
        if (mEtapas != null && mEtapas.size() > 0 ){
            mEtapasAdapter = new EtapasAdapter(this, mEtapas);
            listEtapas.setAdapter(mEtapasAdapter);
        }else{
            mEtapasAdapter = new EtapasAdapter(this, new ArrayList<Etapa>(0));
            listEtapas.setEmptyView(mEmptyView);
        }

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
}
