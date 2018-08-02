package ar.com.corpico.appcorpico.orders.presentation;

import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;

/**
 * Contrato Mvp Toolbar Tipos de cuadrilla
 */

public interface TiposCuadrillaToolbarMvp {

    interface View{
        void showTiposCuadrilla(List<Tipo_Cuadrilla> tiposCuadrilla);
        void setPresenter(Presenter presenter);
    }

    interface Presenter{
        //void loadTiposCuadrilla(String servicio);
        void loadTiposCuadrilla(int sector);
    }
}
