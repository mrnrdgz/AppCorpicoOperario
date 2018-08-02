package ar.com.corpico.appcorpico.home.presentation;

public interface Presenter {
    void loadCuadrillasHome(int servicio, int sector);
    void loadEstadosHome(int servicio, int sector);
    void loadTiposTrabajoHome(int servicio, int sector);
    void loadZonasHome(int servicio, int sector);
}
