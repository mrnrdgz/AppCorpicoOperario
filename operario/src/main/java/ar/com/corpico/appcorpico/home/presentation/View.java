package ar.com.corpico.appcorpico.home.presentation;

import java.util.List;

import ar.com.corpico.appcorpico.home.data.Cuadrilla_Home;
import ar.com.corpico.appcorpico.home.data.Estados_Home;
import ar.com.corpico.appcorpico.home.data.TiposTrabajo_Home;
import ar.com.corpico.appcorpico.home.data.Zonas_Home;

public interface View {
    void showCuadrillas(List<Cuadrilla_Home> listCuadrillaHome);
    void showEstados(List<Estados_Home> listEstadoHome);
    void showTipoTrabajos(List<TiposTrabajo_Home> listTipoTrabajoHome);
    void showZonas(List<Zonas_Home> listZonaHome);
    void showCuadrillasError(String error);
    void showEstadosError(String error);
    void showTiposTrabajoError(String error);
    void showZonasError(String error);
    void setPresenter(Presenter presenter);
    void showListZonaEmpty();
    //void showCuadrillaProgressIndicator(boolean show);
    void onSyncFinish();
}
