package ar.com.corpico.appcorpico.operarios.domain.usecase;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.operarios.data.IOperarioRepository;
import ar.com.corpico.appcorpico.operarios.domain.entity.Operario;

import static com.google.common.base.Preconditions.checkNotNull;

public class GetOperarios extends UseCase<GetOperarios.RequestValues, GetOperarios.ResponseValue> {
    private IOperarioRepository mOperariosRepository;

    public GetOperarios(IOperarioRepository operariosRepository) {
        mOperariosRepository = operariosRepository;
    }

    @Override
    public void executeUseCase(GetOperarios.RequestValues requestValues) {

        IOperarioRepository.OperarioRepositoryCallBack findCallback = new IOperarioRepository.OperarioRepositoryCallBack() {
            @Override
            public void onSuccess(List<Operario> operario) {
                ResponseValue responseValue = new ResponseValue(operario);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onError(String error) {
                getUseCaseCallback().onError(error);
            }
        };


        mOperariosRepository.query(findCallback, requestValues.getServicio());
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private int servicio;

        public RequestValues() {
        }

        public RequestValues(int servicio){
            this.servicio = servicio;
        }

        public int getServicio() {
            return servicio;
        }

    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final List<Operario> operarios;

        public ResponseValue(List<Operario> operarios) {
            this.operarios = checkNotNull(operarios, "La lista de operarios no puede ser null");
        }

        public List<Operario> getOperarios() {
            return operarios;
        }
    }

}
