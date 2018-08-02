package ar.com.corpico.appcorpico.operarios.domain.usecase;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.operarios.data.IOperarioRepository;

public class AddOperario extends UseCase<AddOperario.RequestValues, AddOperario.ResponseValue> {
    private IOperarioRepository mOperariosRepository;

    public AddOperario(IOperarioRepository addOperariosRepository) {
        this.mOperariosRepository = addOperariosRepository;
    }

    @Override
    public void executeUseCase(AddOperario.RequestValues requestValues) {

        IOperarioRepository.AddOperarioRepositoryCallBack addCallback = new IOperarioRepository.AddOperarioRepositoryCallBack() {
            @Override
            public void onSuccess() {
                AddOperario.ResponseValue responseValue = new AddOperario.ResponseValue();
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onError(String error) {
                getUseCaseCallback().onError(error);
            }
        };


        mOperariosRepository.add(addCallback, requestValues.addOperario(),requestValues.tipoCuadrillaId(),requestValues.servicio(),requestValues.sector());

    }

    public static final class RequestValues implements UseCase.RequestValues {
        private List<Integer> operarios;
        private int servicio;
        private int sector;
        private int tipoCuadrilla;

        public RequestValues() {
        }

        public RequestValues(List<Integer> operarios,int tipoCuadrilla, int servicio, int sector ){
            this.operarios = operarios;
            this.servicio = servicio;
            this.sector = sector;
            this.tipoCuadrilla = tipoCuadrilla;
        }

        public List<Integer> addOperario() {
            return operarios;
        }
        public int servicio() {
            return servicio;
        }
        public int sector() {
            return sector;
        }
        public int tipoCuadrillaId() {
            return tipoCuadrilla;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        public ResponseValue() {

        }

    }

}
