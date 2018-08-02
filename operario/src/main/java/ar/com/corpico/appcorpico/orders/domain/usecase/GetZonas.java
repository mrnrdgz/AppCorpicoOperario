package ar.com.corpico.appcorpico.orders.domain.usecase;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.orders.data.zona.IZonasRepository;
import ar.com.corpico.appcorpico.orders.domain.entity.Zona;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 07/01/2017.
 */

public class GetZonas extends UseCase<GetZonas.RequestValues, GetZonas.ResponseValue> {
    private IZonasRepository mZonasRepository;

    public GetZonas(IZonasRepository zonasRepository) {
        this.mZonasRepository = zonasRepository;
    }


    @Override
    public void executeUseCase(RequestValues requestValues) {

        IZonasRepository.ZonaRepositoryCallBack findCallback = new IZonasRepository.ZonaRepositoryCallBack() {
            @Override
            public void onSuccess(List<Zona> zona) {
                List<String> zonas = new ArrayList<>();
                for(Zona z: zona){
                    zonas.add(z.getZona());
                }
                ResponseValue responseValue = new ResponseValue(zonas);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onError(String error) {
                getUseCaseCallback().onError(error);
            }
        };


        //mOrdersRepository.query(findCallback, requestValues.getFilter());
        mZonasRepository.query(findCallback);
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

        private final List<String> zona;

        public ResponseValue(List<String> zona) {
            this.zona = Preconditions.checkNotNull(zona, "La lista de zonas no puede ser null");
        }

        public List<String> getZonas() {
            return zona;
        }
    }
}
