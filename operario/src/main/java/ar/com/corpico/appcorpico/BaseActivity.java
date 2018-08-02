package ar.com.corpico.appcorpico;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ar.com.corpico.appcorpico.cuadrillas.CuadrillasActivity;
import ar.com.corpico.appcorpico.home.HomeActivity;
import ar.com.corpico.appcorpico.login.LoginActivity;
import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.notes.NotesActivity;
import ar.com.corpico.appcorpico.orders.OrderAsignadaActivity;
import ar.com.corpico.appcorpico.orders.OrderPendienteActivity;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;

public abstract class BaseActivity extends AppCompatActivity {
    /**
     * Instancia del drawer
     */
    static boolean mgroupOrdenes = false;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    SessionsPrefsStore mPreferences;

    public enum NavDrawerItemMenu{
        HOME(R.id.nav_inicio),
        CUADRILLAS(R.id.nav_cuadrillas),
        PENDIENTES(R.id.nav_pendientes),
        ASIGNADAS(R.id.nav_asignadas),
        CULMINADAS(R.id.nav_culminadas),
        VERIFICAR(R.id.nav_verificar),
        SUPERVISADAS(R.id.nav_supervisadas),
        NOTES(R.id.nav_notes),
        CIERRE(R.id.nav_log_out),
        INVALIDO(0);

        private int itemId;

        NavDrawerItemMenu(int itemId) {
            this.itemId = itemId;
        }

        public int getItemId() {
            return itemId;
        }
    }

    /**
     * Titulo inicial del drawer
     */
    private String drawerTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ¿Es primera carga?
        mPreferences = SessionsPrefsStore.get(getSharedPreferences(
                "MisPreferencias", Context.MODE_PRIVATE));
        boolean esPrimeraCarga = mPreferences.esPrimeraCarga();

       /* if (savedInstanceState == null) {
            selectItem(R.id.nav_inicio);
        }*/

       /* if(!mPreferences.isLogged()) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return;
        }*/

      /*if(esPrimeraCarga){
            // TODO: Mostrar view de progreso
            Bundle settingsBundle = new Bundle();
            settingsBundle.putBoolean(
                    ContentResolver.SYNC_EXTRAS_MANUAL, true);
            settingsBundle.putBoolean(
                    ContentResolver.SYNC_EXTRAS_EXPEDITED, true);

            ContentResolver.requestSync(SyncAdapter.obtenerCuentaASincronizar(this), ContratoCorpicoApp.AUTHORITY, settingsBundle);

            ContentResolver.addPeriodicSync(SyncAdapter.obtenerCuentaASincronizar(this),
                    ContratoCorpicoApp.AUTHORITY,Bundle.EMPTY,60*60);
      }*/


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        setToolbar(); // Setear Toolbar como action bar

            drawerLayout = findViewById(R.id.drawer_layout);
            navigationView = findViewById(R.id.nav_view);

        // TODO: Llamar a Glide
        if (navigationView != null) {
            View headerView = navigationView.inflateHeaderView(R.layout.nav_header);
            ImageView avatar = headerView.findViewById(R.id.circle_image);
            TextView usuario = headerView.findViewById(R.id.usernameheader);
            TextView cargo = headerView.findViewById(R.id.cargoheader);
            setupDrawerContent(navigationView);
            String url_photo = ApiServiceOrders.URL_HOST + "/Photos/" + mPreferences.getName() + ".jpg";
            Glide.with(this)
                    .load(url_photo)
                    .centerCrop()
                    .error(R.drawable.perfil3)
                    .into(avatar);
            usuario.setText(mPreferences.getNombre().trim() + " " + (mPreferences.getApellido().trim()));
            cargo.setText(mPreferences.getRol().trim());
        }

        drawerTitle = getResources().getString(R.string.inicio_item);
       /* if (savedInstanceState == null) {
            selectItem(R.id.nav_inicio);
        }*/

       navigationView.setCheckedItem(getDrawerItem().getItemId());
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);

        }

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        //TODO: No me marca como presionado Ordenes Tecnicas
                        // Marcar item presionado
                        menuItem.setChecked(true);

                        int opcion = menuItem.getItemId();
                        selectItem(opcion);
                        return true;
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected NavDrawerItemMenu getDrawerItem() {
        return NavDrawerItemMenu.INVALIDO;
    }

    private void selectItem(int opcion) {

        if (opcion == R.id.nav_inicio) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(HomeActivity.ARG_SERVICIO, Integer.parseInt(mPreferences.getServicio()));
            intent.putExtra(HomeActivity.ARG_SECTOR, Integer.parseInt(mPreferences.getSector()));
            startActivity(intent);
            finish();
        }
        if (opcion == R.id.nav_cuadrillas) {
            Intent intent = new Intent(this, CuadrillasActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(CuadrillasActivity.ARG_SERVICIO, Integer.parseInt(mPreferences.getServicio()));
            intent.putExtra(CuadrillasActivity.ARG_SECTOR, Integer.parseInt(mPreferences.getSector()));
            startActivity(intent);
            finish();
        }
        if (opcion == R.id.nav_pendientes) {
            Intent intent = new Intent(this, OrderPendienteActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(OrderPendienteActivity.ARG_ESTADO, OrderPendienteActivity.ESTADO_PENDIENTE);
            intent.putExtra(OrderPendienteActivity.ARG_ESTADO_POST, OrderPendienteActivity.ESTADO_ASIGNADA);
            intent.putExtra(OrderPendienteActivity.ARG_SERVICIO, Integer.parseInt(mPreferences.getServicio()));
            startActivity(intent);
            finish();
        }
        if (opcion == R.id.nav_asignadas) {
            Intent intent = new Intent(this, OrderAsignadaActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(OrderPendienteActivity.ARG_ESTADO, OrderAsignadaActivity.ESTADO_ASIGNADA);
            intent.putExtra(OrderPendienteActivity.ARG_ESTADO_POST, OrderAsignadaActivity.ESTADO_PENDIENTE);
            intent.putExtra(OrderPendienteActivity.ARG_SERVICIO, Integer.parseInt(mPreferences.getServicio()));
            startActivity(intent);
            finish();
        }
        if (opcion == R.id.nav_culminadas) {
            Intent intent = new Intent(this, OrderPendienteActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        if (opcion == R.id.nav_no_culminadas) {
            Intent intent = new Intent(this, OrderPendienteActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        if (opcion == R.id.nav_verificar) {
            Intent intent = new Intent(this, OrderPendienteActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } if (opcion == R.id.nav_supervisadas) {
            Intent intent = new Intent(this, OrderPendienteActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        if (opcion == R.id.nav_notes) {
            Intent intent = new Intent(this, NotesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(NotesActivity.ARG_SERVICIO, Integer.parseInt(mPreferences.getServicio()));
            intent.putExtra(NotesActivity.ARG_SECTOR, Integer.parseInt(mPreferences.getSector()));
            startActivity(intent);
            finish();
        }
        if (opcion == R.id.nav_log_out) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            //Borra datos del usuario
           // mPreferences.destroy();
           // mPreferences.setPrimeraCarga(true);
            //todo: blanqueo primera carga? y borro las ordenes que esten syncronzadas
            //todo: o tendre que poner un semaforo para saber si falta sync datos?
            // todo xq si hay datos x sync en la tabla ordenes y se loguea otro usuario de otro sector
            // todo: tendria q ver si las consultas (getOrders) las estoy separando bien x usuario y sector
            startActivity(intent);
            finish();
        }

        drawerLayout.closeDrawers(); // Cerrar drawer

        //setTitle(opcion); // Setear título actual

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

}