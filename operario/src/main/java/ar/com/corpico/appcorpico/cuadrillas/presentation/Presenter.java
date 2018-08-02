package ar.com.corpico.appcorpico.cuadrillas.presentation;

public interface Presenter {
    void loadCuadrillas(int tipoCuadrilla);
    void addOperario();
    void removeOperario(int operarioId,int cuadrillaId);
}
