package ar.com.corpico.appcorpico.cuadrillas.domain.usecase;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.cuadrillas.data.ICuadrillasRepository;

public class RemoveOperario extends UseCase<RemoveOperario.RequestValues, RemoveOperario.ResponseValue> {
    private ICuadrillasRepository mCuadrillasRepository;

    public RemoveOperario(ICuadrillasRepository removeOperariosRepository) {
        this.mCuadrillasRepository = removeOperariosRepository;
    }

    @Override
    public void executeUseCase(RemoveOperario.RequestValues requestValues) {

        ICuadrillasRepository.RemoveOperarioRepositoryCallBack removeCallback = new ICuadrillasRepository.RemoveOperarioRepositoryCallBack() {
            @Override
            public void onSuccess() {
                RemoveOperario.ResponseValue responseValue = new RemoveOperario.ResponseValue();
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onError(String error) {
                getUseCaseCallback().onError(error);
            }
        };


        mCuadrillasRepository.remove(removeCallback, requestValues.getOperario());
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private int operario;

        public RequestValues() {
        }

        public RequestValues(int operario){
            this.operario = operario;
        }

        public int getOperario() {
            return operario;
        }

    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        public ResponseValue() {

        }

    }

}
