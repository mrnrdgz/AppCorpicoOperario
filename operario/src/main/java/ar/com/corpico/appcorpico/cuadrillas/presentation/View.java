package ar.com.corpico.appcorpico.cuadrillas.presentation;

import java.util.List;

import ar.com.corpico.appcorpico.cuadrillas.domain.entity.Cuadrilla;

public interface View {
    void setPresenter(CuadrillasPresenter cuadrillasPresenter);
    void showCuadrillaList(List<Cuadrilla> cuadrilla);
    void showCuadrillaError(String error);
    void showCuadrillaEmpty();
    void showProgressIndicator(boolean show);
    void close();
    void eliminarOperarioOk(int operarioId);
}
