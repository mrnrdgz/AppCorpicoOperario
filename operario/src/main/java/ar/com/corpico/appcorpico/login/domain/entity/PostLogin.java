package ar.com.corpico.appcorpico.login.domain.entity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by sistemas on 14/11/2017.
 */

public class PostLogin {
    @SerializedName(".expires")
    @Expose
    private String expires;
    @SerializedName(".issued")
    @Expose
    private String issued;
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("expires_in")
    @Expose
    private Integer expiresIn;
    @SerializedName("token_type")
    @Expose
    private String tokenType;
    @SerializedName("Id")
    @Expose
    private String userCode;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("Rol")
    @Expose
    private String Rol;
    @SerializedName("Servicio")
    @Expose
    private String userServicio;
    @SerializedName("Sector")
    @Expose
    private String userSector;
    @SerializedName("Operario")
    @Expose
    private String userOperario;
    @SerializedName("Nombre")
    @Expose
    private String userNombre;
    @SerializedName("Apellido")
    @Expose
    private String userApellido;

    public String getRol() {
        return Rol;
    }

    public void setUserRol(String Rol) {
        this.Rol = Rol;
    }

    public String getUserServicio() {
        return userServicio;
    }

    public void setUserServicio(String userServicio) {
        this.userServicio = userServicio;
    }

    public String getUserSector() {
        return userSector;
    }

    public void setUserSector(String userSector) {
        this.userSector = userSector;
    }

    public String getUserOperario() {
        return userOperario;
    }

    public void setUserOperario(String userOperario) {
        this.userOperario = userOperario;
    }

    public String getUserNombre() {
        return userNombre;
    }

    public void setUserNombre(String userNombre) {
        this.userNombre = userNombre;
    }

    public String getUserApellido() {
        return userApellido;
    }

    public void setUserApellido(String userApellido) {
        this.userApellido = userApellido;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIssued() {
        return issued;
    }

    public void setIssued(String issued) {
        this.issued = issued;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }
}
