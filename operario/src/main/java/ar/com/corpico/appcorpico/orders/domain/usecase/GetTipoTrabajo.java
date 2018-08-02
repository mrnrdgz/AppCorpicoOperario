package ar.com.corpico.appcorpico.orders.domain.usecase;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.orders.data.tipoTrabajo.ITipoTrabajoRepository;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 07/01/2017.
 */

public class GetTipoTrabajo extends UseCase<GetTipoTrabajo.RequestValues, GetTipoTrabajo.ResponseValue> {
    //private IOrdersRepository mOrdersRepository;
    private ITipoTrabajoRepository mTipoTrabajoRepository;

    public GetTipoTrabajo(ITipoTrabajoRepository tipoTrabajoRepository) {
        this.mTipoTrabajoRepository = tipoTrabajoRepository;
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {
        //todo: hacer una especificacion para usar cuadrilla y mandar la especificanno
        ITipoTrabajoRepository.TipoTrabajoRepositoryCallBack findCallback = new ITipoTrabajoRepository.TipoTrabajoRepositoryCallBack() {
            @Override
            public void onSuccess(List<Tipo_Trabajo> tipoTrabajo) {
                List<String> tipoTrabajos = new ArrayList<>();
                for(Tipo_Trabajo tipo: tipoTrabajo){
                    tipoTrabajos.add(tipo.getTipoTrabajo());
                }
                ResponseValue responseValue = new ResponseValue(tipoTrabajos);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onError(String error) {
                getUseCaseCallback().onError(error);
            }
        };


        mTipoTrabajoRepository.query(findCallback, requestValues.getFilter());
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

        private final List<String> tipoTrabajo;

        public ResponseValue(List<String> tipoTrabajo) {
            this.tipoTrabajo = Preconditions.checkNotNull(tipoTrabajo, "La lista de tipos de trabajos no puede ser null");
        }

        public List<String> getTipoTrabajo() {
            return tipoTrabajo;
        }
    }
}
