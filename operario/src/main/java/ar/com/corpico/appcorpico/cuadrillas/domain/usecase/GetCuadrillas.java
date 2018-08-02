package ar.com.corpico.appcorpico.cuadrillas.domain.usecase;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.cuadrillas.data.ICuadrillasRepository;
import ar.com.corpico.appcorpico.cuadrillas.domain.entity.Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

import static com.google.common.base.Preconditions.checkNotNull;

public class GetCuadrillas extends UseCase<GetCuadrillas.RequestValues, GetCuadrillas.ResponseValue> {
    private ICuadrillasRepository mCuadrillasRepository;

    public GetCuadrillas(ICuadrillasRepository cuadrillasRepository) {
        this.mCuadrillasRepository = cuadrillasRepository;
    }

    @Override
    public void executeUseCase(GetCuadrillas.RequestValues requestValues) {

        ICuadrillasRepository.CuadrillaRepositoryCallBack findCallback = new ICuadrillasRepository.CuadrillaRepositoryCallBack() {
            @Override
            public void onSuccess(List<Cuadrilla> cuadrilla) {
                ResponseValue responseValue = new ResponseValue(cuadrilla);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onError(String error) {
                getUseCaseCallback().onError(error);
            }
        };


        mCuadrillasRepository.query(findCallback, requestValues.getFilter());
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private Criteria filter;

        public RequestValues() {
        }

        public RequestValues(Criteria filter){
            this.filter = filter;
        }

        public Criteria getFilter() {
            return filter;
        }

    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final List<Cuadrilla> cuadrillas;

        public ResponseValue(List<Cuadrilla> cuadrillas) {
            this.cuadrillas = checkNotNull(cuadrillas, "La lista de cuadrillas no puede ser null");
        }

        public List<Cuadrilla> getCuadrillas() {
            return cuadrillas;
        }
        }

}