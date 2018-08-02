package ar.com.corpico.appcorpico.login.presentation;

import android.text.TextUtils;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.UseCaseHandler;
import ar.com.corpico.appcorpico.login.domain.entity.Session;
import ar.com.corpico.appcorpico.login.domain.usecase.LoginUser;

public class LoginPresenter implements Presenter {

    // Relación de composición
    private View mLoginView;
    private LoginUser mLoginUser;
    private final UseCaseHandler mUseCaseHandler;

    public LoginPresenter(View loginView, LoginUser loginUser,UseCaseHandler useCaseHandler) {
            mLoginView = loginView;
            mLoginView.setPresenter(this);
            mLoginUser = loginUser;
            mUseCaseHandler = useCaseHandler;
    }

    @Override
    public void onClickedSignButton(String userCode, String password) {

        boolean error = false;

        // Verificar formato de datos
        if (userCode == null || TextUtils.isEmpty(userCode)) {
            mLoginView.showUserCodeError("Este campo es necesario");
            error = true;
        } else {
            if (userCode.length() < 4) {
                mLoginView.showUserCodeError("El código de usuario debe tener 4 caracteres");
                error = true;
            }
        }


        if (password == null || TextUtils.isEmpty(password)) {
            mLoginView.showPasswordError("Este campo es necesario");
            error = true;
        } else {
            if (password.length() < 6) {
                mLoginView.showPasswordError("La contraseña debe tener al menos 8 caracteres");
                error = true;
            }
        }


        if (error) {
            return;
        }

        // Iniciar carga
        mLoginView.showProgressIndicator(true);

        // Valores de entrada
        LoginUser.RequestValues requestValues =
                new LoginUser.RequestValues(userCode, password);

        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback<LoginUser.ResponseValue>(){
            @Override
            public void onSuccess(LoginUser.ResponseValue response) {
                // Ocultar el progreso
                mLoginView.showProgressIndicator(false);

                Session session = response.getUserSession();

                // Guardar en las preferencias
                mLoginUser.keepUserSession(session);

                // Ir a home activity
                mLoginView.showHomeScreen(session.getUserServicio(),session.getUserSector());

            }

            @Override
            //todo: aca llega vacion el error
            public void onError(String error) {
                // Ocultar el progreso
                mLoginView.showProgressIndicator(false);
                mLoginView.showLoginError(error);

            }
        };

        // Ejecutar caso de uso
        //mLoginUser.execute(requestValues,
        mLoginUser.setUseCaseCallback(useCaseCallback);
        mUseCaseHandler.execute(mLoginUser, requestValues,useCaseCallback);

    }

}
