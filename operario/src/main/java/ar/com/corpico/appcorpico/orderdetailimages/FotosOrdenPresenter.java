package ar.com.corpico.appcorpico.orderdetailimages;

import com.google.common.base.Preconditions;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.UseCaseHandler;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetFotoOrder;

public class FotosOrdenPresenter implements OrderDetailImagesMvp.Presenter {
    private final UseCaseHandler mUseCaseHandler;
    private OrderDetailImagesMvp.View mView;
    private GetFotoOrder mGetFotoOrder;

    public FotosOrdenPresenter(OrderDetailImagesMvp.View mView, GetFotoOrder mGetFotoOrder,UseCaseHandler useCaseHandler) {
        this.mView = mView;
        this.mGetFotoOrder = mGetFotoOrder;
        mUseCaseHandler = Preconditions.checkNotNull(useCaseHandler, "El UseCaseHandler no puede ser null");
    }

    @Override
    public void loadFotosOrden(Integer orden) {
        // Obtener lista de fotos de orden
        GetFotoOrder.RequestValues requestValues = new GetFotoOrder.RequestValues(orden);

        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback<GetFotoOrder.ResponseValue>() {
            @Override
            public void onSuccess(GetFotoOrder.ResponseValue response) {
                List<FotoOrden> fotos = response.getFotoOrden();
                mView.showFotosOrden(fotos);
            }

            @Override
            public void onError(String error) {

            }
        };
        // Ejecutar obtención de ordenes en un hilo de trabajo
        mGetFotoOrder.setUseCaseCallback(useCaseCallback);
        mUseCaseHandler.execute(mGetFotoOrder, requestValues, useCaseCallback);
    }
}
