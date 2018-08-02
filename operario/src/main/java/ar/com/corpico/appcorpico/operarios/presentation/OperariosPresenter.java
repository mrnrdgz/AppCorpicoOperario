package ar.com.corpico.appcorpico.operarios.presentation;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.UseCaseHandler;
import ar.com.corpico.appcorpico.operarios.domain.entity.Operario;
import ar.com.corpico.appcorpico.operarios.domain.usecase.AddOperario;
import ar.com.corpico.appcorpico.operarios.domain.usecase.GetOperarios;

import static com.google.common.base.Preconditions.checkNotNull;

public class OperariosPresenter implements Presenter {
    private View mView;
    private GetOperarios mGetOperarios;
    private AddOperario mAddOperarios;
    private final UseCaseHandler mUseCaseHandler;

    public OperariosPresenter(View mView, GetOperarios getOperarios,AddOperario addOperarios,  UseCaseHandler mUseCaseHandler) {
        this.mView = checkNotNull(mView, "View no puede ser null");
        this.mGetOperarios = checkNotNull(getOperarios,"GetOpeario no puede ser null");
        this.mAddOperarios = checkNotNull(addOperarios,"AddOpeario no puede ser null");
        this.mUseCaseHandler = checkNotNull(mUseCaseHandler,"UseCaseHandler no puede ser null");

        mView.setPresenter(this);
    }
    @Override
    public void loadOperarios(int sector) {
            //CriteriaOperario criteriaOpeario = new CriteriaOperario(servicio);

            GetOperarios.RequestValues requestValues = new GetOperarios.RequestValues(sector);

            UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback<GetOperarios.ResponseValue>() {
                @Override
                public void onSuccess(GetOperarios.ResponseValue response) {
                    List<Operario> operario = response.getOperarios();
                    if (operario.size() >= 1) {
                        // Mostrar la lista en la vista
                        mView.showOperarioList(operario);
                    } else {
                        // Mostrar estado vacío
                        mView.showOperarioList(operario);
                        mView.showOperarioEmpty();
                    }
                }

                @Override
                public void onError(String error) {
                    // Ocultar indicador de progreso
                    mView.showProgressIndicator(false);
                    mView.showOperarioError(error);
                }
            };
            mGetOperarios.setUseCaseCallback(useCaseCallback);
            mUseCaseHandler.execute(mGetOperarios, requestValues, useCaseCallback);

        }

    @Override
    public void addOperarioCuadrilla(List<Integer> idOperarios,int tipoCuadrillaId,int servicio,int sector) {
        List<Integer> mOperario = idOperarios;

        AddOperario.RequestValues requestValues = new AddOperario.RequestValues(mOperario,tipoCuadrillaId,servicio,sector);

        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback<AddOperario.ResponseValue>() {
            @Override
            public void onSuccess(AddOperario.ResponseValue response) {
             // Cerrar la activity de operarios
              mView.close();
            }

            @Override
            public void onError(String error) {
                // Mostrar error
                //mView.showCuadrillaError(error);
            }
        };

        mAddOperarios.setUseCaseCallback(useCaseCallback);
        mUseCaseHandler.execute(mAddOperarios, requestValues, useCaseCallback);

    }

}
