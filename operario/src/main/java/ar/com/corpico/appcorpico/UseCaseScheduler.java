package ar.com.corpico.appcorpico;

/**
 * Created by Administrador on 06/04/2018.
 */

public interface UseCaseScheduler {

    void execute(Runnable runnable);

    <V extends UseCase.ResponseValue> void notifyResponse(final V response,
                                                          final UseCase.UseCaseCallback<V> useCaseCallback);

    <V extends UseCase.ResponseValue> void onError(String error,
            final UseCase.UseCaseCallback<V> useCaseCallback);

}
