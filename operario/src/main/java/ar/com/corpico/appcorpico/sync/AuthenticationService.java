package ar.com.corpico.appcorpico.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Administrador on 26/02/2018.
 */

public class AuthenticationService extends Service {
    // Instancia del autenticador
    private OrdersAuthenticator autenticador;

    @Override
    public void onCreate() {
        // Nueva instancia del autenticador
        autenticador = new OrdersAuthenticator(this);
    }

    /*
     * Ligando el servicio al framework de Android
     */
    @Override
    public IBinder onBind(Intent intent) {
        return autenticador.getIBinder();
    }
}
