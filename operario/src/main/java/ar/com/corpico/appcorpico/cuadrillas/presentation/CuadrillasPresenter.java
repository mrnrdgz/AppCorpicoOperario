package ar.com.corpico.appcorpico.cuadrillas.presentation;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.UseCaseHandler;
import ar.com.corpico.appcorpico.cuadrillas.domain.entity.Cuadrilla;
import ar.com.corpico.appcorpico.cuadrillas.domain.usecase.GetCuadrillas;
import ar.com.corpico.appcorpico.cuadrillas.domain.usecase.RemoveOperario;
import ar.com.corpico.appcorpico.orders.domain.filter.CriteriaCuadrilla;

import static com.google.common.base.Preconditions.checkNotNull;

public class CuadrillasPresenter implements Presenter {

    private View mView;
    private GetCuadrillas mGetCuadrillas;
    private RemoveOperario mRemoveOperario;
    private final UseCaseHandler mUseCaseHandler;

    public CuadrillasPresenter(View mView,
                               GetCuadrillas getCuadrillas,
                               RemoveOperario removeOperario,
                               UseCaseHandler mUseCaseHandler) {
        this.mView = checkNotNull(mView, "View no puede ser null");
        this.mGetCuadrillas = checkNotNull(getCuadrillas,"GetTiposCuadrillas no puede ser null");
        this.mRemoveOperario = checkNotNull(removeOperario,"RemoveOperario no puede ser null");
        this.mUseCaseHandler = checkNotNull(mUseCaseHandler,"UseCaseHandler no puede ser null");

        mView.setPresenter(this);
    }

    @Override
    public void loadCuadrillas(int cuadrillaId) {
        CriteriaCuadrilla criteriaCuadrilla = new CriteriaCuadrilla(cuadrillaId);

        GetCuadrillas.RequestValues requestValues = new GetCuadrillas.RequestValues(criteriaCuadrilla);

        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback<GetCuadrillas.ResponseValue>() {
            @Override
            public void onSuccess(GetCuadrillas.ResponseValue response) {
                List<Cuadrilla> cuadrilla = response.getCuadrillas();
                if (cuadrilla.size() >= 1) {
                    // Mostrar la lista en la vista
                    mView.showCuadrillaList(cuadrilla);
                } else {
                    // Mostrar estado vacío
                    mView.showCuadrillaList(cuadrilla);
                    mView.showCuadrillaEmpty();
                }
            }

            @Override
            public void onError(String error) {
                // Ocultar indicador de progreso
                mView.showProgressIndicator(false);
                mView.showCuadrillaError(error);
            }
        };

        mGetCuadrillas.setUseCaseCallback(useCaseCallback);
        mUseCaseHandler.execute(mGetCuadrillas, requestValues, useCaseCallback);

    }
    @Override
    public void addOperario() {

    }

    @Override
    public void removeOperario(int operario, final int cuadrillaId) {
        int mOperario = operario;

        RemoveOperario.RequestValues requestValues = new RemoveOperario.RequestValues(mOperario);

        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback<RemoveOperario.ResponseValue>() {
            @Override
            public void onSuccess(RemoveOperario.ResponseValue response) {
                    // Refrescar la vista
                    //mView.refreshCuadrillaList();
                    loadCuadrillas(cuadrillaId);
            }

            @Override
            public void onError(String error) {
                // Mostrar error
                mView.showCuadrillaError(error);
            }
        };

        mRemoveOperario.setUseCaseCallback(useCaseCallback);
        mUseCaseHandler.execute(mRemoveOperario, requestValues, useCaseCallback);

    }
}
