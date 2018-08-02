package ar.com.corpico.appcorpico.login.data;

import android.content.SharedPreferences;

import com.google.common.base.Preconditions;

import ar.com.corpico.appcorpico.login.domain.entity.Session;


public class SessionsPrefsStore implements SessionsStore {

    private static final String PREF_PRIMERA_CARGA = "PREF_PRIMERA_CARGA";
    private final SharedPreferences mSharedPreferences;
    private static final String PREF_ULTIMA_SYNC = "PREF_ULTIMA_SYNC";

    private boolean isLogged = false;

    private static SessionsPrefsStore INSTANCE = null;


    private SessionsPrefsStore(SharedPreferences sharedPreferences) {
        mSharedPreferences = Preconditions.checkNotNull(sharedPreferences);
    }

    public static SessionsPrefsStore get(SharedPreferences sharedPreferences) {
        if (INSTANCE == null) {
            INSTANCE = new SessionsPrefsStore(sharedPreferences);
        }
        return INSTANCE;
    }

    @Override
    public void getSessionByUserCredentials(String userCode, String password, GetCallback callback) {
        // No-op
    }

    @Override
    public void save(Session session) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("codeUser", session.getUserCode());
        editor.putString("name", session.getUserName());
        editor.putString("token", session.getToken());
        editor.putString("userRol", session.getUserRol());
        editor.putString("userServicio", session.getUserServicio());
        editor.putString("userSector", session.getUserSector());
        editor.putString("userOperario", session.getUserOperario());
        editor.putString("userNombre", session.getUserNombre());
        editor.putString("userApellido",session.getUserApellido());
        editor.apply();

        isLogged = true;
    }

    @Override
    public void destroy() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("codeUser", null);
        editor.putString("name", null);
        editor.putString("token", null);
        editor.putString("userRol", null);
        editor.putString("userServicio", null);
        editor.putString("userSector", null);
        editor.putString("userOperario", null);
        editor.putString("userNombre", null);
        editor.putString("userApellido",null);
        editor.apply();

        isLogged = false;
    }

    public String getServicio(){
        return mSharedPreferences.getString("userServicio","");
    }
    public String getSector(){
        return mSharedPreferences.getString("userSector","");
    }
    public String getUserId(){
        return mSharedPreferences.getString("codeUser","");
    }
    public String getName(){
        return mSharedPreferences.getString("name","");
    }
    public String getOperario(){
        return mSharedPreferences.getString("userOperario","");
    }
    public String getRol(){
        return mSharedPreferences.getString("userRol","");
    }
    public String getNombre(){
        return mSharedPreferences.getString("userNombre","");
    }
    public String getApellido(){
        return mSharedPreferences.getString("userApellido","");
    }
    public boolean isLogged() {
        return isLogged;
    }

    public boolean esPrimeraCarga() {
        // TODO: Revisar nuevas versiones
        return mSharedPreferences.getBoolean(PREF_PRIMERA_CARGA, true);
    }

    public void setPrimeraCarga(boolean es) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(PREF_PRIMERA_CARGA, es);
        editor.apply();
    }
    public String getUltimaSync(){
        return mSharedPreferences.getString(PREF_ULTIMA_SYNC, "2000-01-01");
    }
    public void setUltimaSync(String ultimaSync){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(PREF_ULTIMA_SYNC, ultimaSync);
        editor.apply();
    }
}
