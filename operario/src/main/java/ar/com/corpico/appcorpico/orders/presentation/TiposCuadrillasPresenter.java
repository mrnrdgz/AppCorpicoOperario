package ar.com.corpico.appcorpico.orders.presentation;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.UseCaseHandler;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.filter.CriteriaTipoCuadrilla;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetTipoCuadrilla;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Administrador on 09/08/2017.
 */

public class TiposCuadrillasPresenter implements TiposCuadrillaToolbarMvp.Presenter {

    private TiposCuadrillaToolbarMvp.View mView;
    private GetTipoCuadrilla mGetTiposCuadrillas;
    private final UseCaseHandler mUseCaseHandler;

    public TiposCuadrillasPresenter(TiposCuadrillaToolbarMvp.View mView, GetTipoCuadrilla mGetTiposCuadrillas,UseCaseHandler mUseCaseHandler) {
        this.mView = checkNotNull(mView, "View no puede ser null");
        this.mGetTiposCuadrillas = checkNotNull(mGetTiposCuadrillas,"GetTiposCuadrillas no puede ser null");
        this.mUseCaseHandler = checkNotNull(mUseCaseHandler,"UseCaseHandler no puede ser null");

        mView.setPresenter(this);
    }

    @Override
    //public void loadTiposCuadrilla(String servicio) {
    public void loadTiposCuadrilla(int sector) {
        //CriteriaCuadrilla criteriaTipoCuadrilla = new CriteriaCuadrilla(servicio);
        CriteriaTipoCuadrilla criteriaTipoCuadrilla = new CriteriaTipoCuadrilla(sector);

        GetTipoCuadrilla.RequestValues requestValues = new GetTipoCuadrilla.RequestValues(criteriaTipoCuadrilla);

        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback<GetTipoCuadrilla.ResponseValue>() {
            @Override
            public void onSuccess(GetTipoCuadrilla.ResponseValue response) {
                List<Tipo_Cuadrilla> tipoCuadrilla = response.getTipoCuadrilla();
                if (tipoCuadrilla.size() >= 1) {
                    // Mostrar la lista en la vista
                    /*List<Tipo_Cuadrilla> tiposCuadrilla = new ArrayList<>();
                    for(int i=0;i< tipoCuadrilla.size();i++ ){
                        tiposCuadrilla.add(tipoCuadrilla.get(i).getTipo_cuadrilla(),tipoCuadrilla.get(i).getGeasysId());
                    }*/
                    mView.showTiposCuadrilla(tipoCuadrilla);
                } else {
                    // Mostrar estado vacío
                    //mView.showTiposCuadrilla(tipoCuadrilla);
                }
            }

            @Override
            public void onError(String error) {

            }
        };
        //mGetTiposCuadrillas.execute(requestValues, useCaseCallback);
        mGetTiposCuadrillas.setUseCaseCallback(useCaseCallback);
        mUseCaseHandler.execute(mGetTiposCuadrillas, requestValues, useCaseCallback);

    }
}
