package ar.com.corpico.appcorpico.orders.domain.usecase;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.orders.data.orders.IOrdersRepository;

/**
 * Created by Administrador on 07/01/2017.
 */

public class AddOrdersState extends UseCase<AddOrdersState.RequestValues, AddOrdersState.ResponseValue>{
    private IOrdersRepository mOrdersRepository;

    public AddOrdersState(IOrdersRepository ordersRepository) {
        this.mOrdersRepository = ordersRepository;
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {

        mOrdersRepository.addOrderState(requestValues.getStateName(), (ArrayList<Integer>) requestValues.getOrderNumber(),requestValues.getObservation(),requestValues.getTipoCuadrillaId(),requestValues.getLatitud(),requestValues.getLongitud());
        getUseCaseCallback().onSuccess(new ResponseValue());
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private List<Integer> orderNumber;
        private String stateName;
        private String observation;
        private Integer tipoCuadrillaId;
        private Double latitud;
        private Double longitud;


        public RequestValues() {
        }

        public RequestValues(String stateName,List<Integer> orderNumber, String observation,
                             Integer tipoCuadrillaId,Double latitud, Double longitud) {
            this.orderNumber = orderNumber;
            this.stateName = stateName;
            this.observation=observation;
            this.tipoCuadrillaId = tipoCuadrillaId;
            this.latitud = latitud;
            this.longitud = longitud;
            // Validar lógica
        }

        public List<Integer> getOrderNumber() {
            return orderNumber;
        }

        public String getStateName() {
            return stateName;
        }
        public String getObservation(){
            return observation;
        }
        public Integer getTipoCuadrillaId(){
            return tipoCuadrillaId;
        }
        public Double getLatitud(){
            return latitud;
        }
        public Double getLongitud(){
            return longitud;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {


    }
}
