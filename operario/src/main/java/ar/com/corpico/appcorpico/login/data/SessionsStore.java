package ar.com.corpico.appcorpico.login.data;

import ar.com.corpico.appcorpico.login.domain.entity.Session;


public interface SessionsStore {

    void getSessionByUserCredentials(String userCode, String password, GetCallback callback);

    void save(Session session);
    void destroy();

    interface GetCallback{
        void onSucess(Session session);

        void onError(String error);
    }
}
