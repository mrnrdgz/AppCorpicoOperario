package ar.com.corpico.appcorpico.login.domain.entity;

import android.support.annotation.NonNull;

/**
 * Representa las sesiones de usuario en el sistema
 */

public class Session {

    @NonNull
    private String userCode;
    @NonNull
    private String userName;
    @NonNull
    private String token;
    @NonNull
    private String userRol;
    @NonNull
    private String userServicio;
    @NonNull
    private String userSector;
    @NonNull
    private String userOperario;
    @NonNull
    private String userNombre;
    @NonNull
    private String userApellido;


    public Session(@NonNull String userCode, @NonNull String userName, @NonNull String token,String userRol,
                   String userServicio, String userSector, String userOperario, String userNombre, String userApellido) {
        this.userCode = userCode;
        this.userName = userName;
        this.token = token;
        this.userRol = userRol;
        this.userServicio = userServicio;
        this.userSector = userSector;
        this.userOperario = userOperario;
        this.userNombre = userNombre;
        this.userApellido = userApellido;
    }

    @NonNull

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(@NonNull String userCode) {
        this.userCode = userCode;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }

    @NonNull
    public String getToken() {
        return token;
    }

    public void setToken(@NonNull String token) {
        this.token = token;
    }

    @NonNull
    public String getUserRol() {
        return userRol;
    }

    public void setUserRol(@NonNull String userRol) {
        this.userRol = userRol;
    }

    @NonNull
    public String getUserServicio() {
        return userServicio;
    }

    public void setUserServicio(@NonNull String userServicio) {
        this.userServicio = userServicio;
    }

    @NonNull
    public String getUserSector() {
        return userSector;
    }

    public void setUserSector(@NonNull String userSector) {
        this.userSector = userSector;
    }

    @NonNull
    public String getUserOperario() {
        return userOperario;
    }

    public void setUserOperario(@NonNull String userOperario) {
        this.userOperario = userOperario;
    }

    @NonNull
    public String getUserNombre() {
        return userNombre;
    }

    public void setUserNombre(@NonNull String userNombre) {
        this.userNombre = userNombre;
    }

    @NonNull
    public String getUserApellido() {
        return userApellido;
    }

    public void setUserApellido(@NonNull String userApellido) {
        this.userApellido = userApellido;
    }
}
