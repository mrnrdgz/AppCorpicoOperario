package ar.com.corpico.appcorpico.orders.domain.usecase;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.orders.data.orders.IOrdersRepository;
import ar.com.corpico.appcorpico.orders.domain.Query;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.NumberSpec;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.Specification;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Administrador on 03/05/2018.
 */

public class GetOrder extends UseCase<GetOrder.RequestValues, GetOrder.ResponseValue> {
    private IOrdersRepository mOrdersRepository;

    public GetOrder(IOrdersRepository ordersRepository) {
        this.mOrdersRepository = ordersRepository;
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {

        // Obtener valores entrantes del caso de uso
        Integer numero = requestValues.getNumero();

        // Crear especificacion
        final NumberSpec numeroSpec = new NumberSpec(numero);

        Query mQuery = new Query(numeroSpec,"",1,0,0);

        mOrdersRepository.queryOrderDetail(
                new IOrdersRepository.OrdersRepositoryCallback() {
                    @Override
                    public void onSuccess(Order order) {
                        ResponseValue responseValue = new ResponseValue(order);
                        getUseCaseCallback().onSuccess(responseValue);
                    }

                    @Override
                    public void onError(String error) {
                        getUseCaseCallback().onError(error);
                    }
                },numero,
                mQuery);
        }

    public static final class RequestValues implements UseCase.RequestValues {
        private Integer numero;

        //private Criteria filter;
        private Specification filter;

        public RequestValues() {
        }

        public RequestValues(Specification filter) {
            this.filter = filter;
        }

        public RequestValues(Integer numero) {
            this.numero = numero;
        }

        public Specification getFilter() {
            return filter;
        }

        public Integer getNumero() {
            return numero;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final Order order;

        public ResponseValue(Order order) {
            this.order = checkNotNull(order, "La lista de ordenes no puede ser null");
        }

        public Order getOrder() {
            return order;
        }
    }
}