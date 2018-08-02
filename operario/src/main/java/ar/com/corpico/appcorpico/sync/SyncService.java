package ar.com.corpico.appcorpico.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Administrador on 26/02/2018.
 */

public class SyncService extends Service {
    // Instancia del sync adapter
    private static SyncAdapter syncAdapter = null;
    // Objeto para prevenir errores entre hilos
    private static final Object lock = new Object();

    @Override
    public void onCreate() {
        super.onCreate();
        synchronized (lock) {
            if (syncAdapter == null) {
                syncAdapter = new SyncAdapter(getApplicationContext(), true);
            }
        }
    }

    /**
     * Retorna interfaz de comunicación para que el sistema llame al sync adapter
     */
    @Override
    public IBinder onBind(Intent intent) {
        return syncAdapter.getSyncAdapterBinder();
    }
}

