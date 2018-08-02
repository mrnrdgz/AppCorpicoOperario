package ar.com.corpico.appcorpico.orders.domain.usecase;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.orders.data.orders.IOrdersRepository;

public class CancelTurno extends UseCase<CancelTurno.RequestValues, CancelTurno.ResponseValue> {
    private IOrdersRepository mOrdersRepository;

    public CancelTurno(IOrdersRepository ordersRepository) {
        this.mOrdersRepository = ordersRepository;
    }

    @Override
    public void executeUseCase(CancelTurno.RequestValues requestValues) {

        mOrdersRepository.cancelTurno(requestValues.getOrderNumber());
        getUseCaseCallback().onSuccess(new CancelTurno.ResponseValue());
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private Integer mNumero;


        public RequestValues() {
        }

        public RequestValues(Integer numero ){//DateTime turno) {
            mNumero = numero;
            // Validar lógica
        }

        public Integer getOrderNumber() {
            return mNumero;
        }

    }

    public static final class ResponseValue implements UseCase.ResponseValue {


    }
}