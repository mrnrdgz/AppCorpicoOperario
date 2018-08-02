package ar.com.corpico.appcorpico.operarios.presentation;

import java.util.List;

public interface Presenter {
    void loadOperarios(int sector);
    void addOperarioCuadrilla(List<Integer> idOperarios,int tipoCuadrillaId,int servicio,int sector);
}
