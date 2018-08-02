package ar.com.corpico.appcorpico.orders.domain.usecase;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.orders.data.orders.IOrdersRepository;

/**
 * Created by sistemas on 21/09/2017.
 */

public class AddTurno extends UseCase<AddTurno.RequestValues, AddTurno.ResponseValue> {
    private IOrdersRepository mOrdersRepository;

    public AddTurno(IOrdersRepository ordersRepository) {
        this.mOrdersRepository = ordersRepository;
    }

    @Override
    public void executeUseCase(AddTurno.RequestValues requestValues) {

        mOrdersRepository.addTurno(requestValues.getOrderNumber(),requestValues.getTurno());
        getUseCaseCallback().onSuccess(new AddTurno.ResponseValue());
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private Integer mNumero;
        private String mTurno;


        public RequestValues() {
        }

        public RequestValues(Integer numero, String turno ){//DateTime turno) {
            mNumero = numero;
            mTurno = turno;
            // Validar lógica
        }

        public Integer getOrderNumber() {
            return mNumero;
        }

        /*public DateTime getTurno() {
            return mTurno;
        }*/
        public String getTurno() {
            return mTurno;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {


    }
}