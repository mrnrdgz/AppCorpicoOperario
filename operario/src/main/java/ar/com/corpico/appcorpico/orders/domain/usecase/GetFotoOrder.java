package ar.com.corpico.appcorpico.orders.domain.usecase;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.orderdetailimages.FotoOrden;
import ar.com.corpico.appcorpico.orders.data.orders.IOrdersRepository;
import ar.com.corpico.appcorpico.orders.domain.Query;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.NumberSpec;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.Specification;

import static com.google.common.base.Preconditions.checkNotNull;

public class GetFotoOrder extends UseCase<GetFotoOrder.RequestValues, GetFotoOrder.ResponseValue>{
    private IOrdersRepository mOrdersRepository;

    public GetFotoOrder(IOrdersRepository ordersRepository) {
        this.mOrdersRepository = ordersRepository;
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {

        // Obtener valores entrantes del caso de uso
        Integer numero = requestValues.getNumero();

        // Crear especificacion
        final NumberSpec numeroSpec = new NumberSpec(numero);

        Query mQuery = new Query(numeroSpec,"",1,0,0);

        mOrdersRepository.queryFotoOrder(
                new IOrdersRepository.FotoOrdersRepositoryCallback() {
                    @Override
                    public void onSuccess(List<FotoOrden> fotos) {
                        ResponseValue responseValue = new ResponseValue(fotos);
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

        private final List<FotoOrden> fotos;

        public ResponseValue(List<FotoOrden> fotos) {
            this.fotos = checkNotNull(fotos, "La lista de ordenes no puede ser null");
        }

        public List<FotoOrden> getFotoOrden() {
            return fotos;
        }
    }
}




