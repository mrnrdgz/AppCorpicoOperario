package ar.com.corpico.appcorpico.login;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.UseCaseHandler;
import ar.com.corpico.appcorpico.login.data.SessionsCloudStore;
import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.login.data.SessionsRepository;
import ar.com.corpico.appcorpico.login.domain.usecase.LoginUser;
import ar.com.corpico.appcorpico.login.presentation.LoginFragment;
import ar.com.corpico.appcorpico.login.presentation.LoginPresenter;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private final UseCaseHandler mUseCaseHandler = UseCaseHandler.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /**
         <<create> LoginFragment
         **/
        LoginFragment loginView;
        loginView = (LoginFragment) getSupportFragmentManager()
                .findFragmentById(R.id.login_view_container);

        if (loginView == null) {
            loginView = LoginFragment.newInstance(null, null);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.login_view_container, loginView)
                    .commit();
        }

        /**
         * <<create>> Almacénes
         */
        SessionsPrefsStore prefsStore = SessionsPrefsStore.get(getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE));
        SessionsCloudStore restStore = SessionsCloudStore.get();

        /**
         * <<create>> SessionsRepository
         */
        SessionsRepository repository = new SessionsRepository(prefsStore, restStore);

        /**
         * <<create>> LoginUser
         */
        LoginUser loginUser = new LoginUser(repository);

        /**
         * <<create>> LoginPresenter
         */

        LoginPresenter loginPresenter = new LoginPresenter(loginView, loginUser, mUseCaseHandler);
    }

}

