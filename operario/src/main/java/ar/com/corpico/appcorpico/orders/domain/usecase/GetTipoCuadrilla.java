package ar.com.corpico.appcorpico.orders.domain.usecase;

import com.google.common.base.Preconditions;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.orders.data.tipoCuadrilla.ITipoCuadrillasRepository;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 07/01/2017.
 */

public class GetTipoCuadrilla extends UseCase<GetTipoCuadrilla.RequestValues, GetTipoCuadrilla.ResponseValue> {
    private ITipoCuadrillasRepository mTipoCuadrillasRepository;

    public GetTipoCuadrilla(ITipoCuadrillasRepository tipocuadrillasRepository) {
        this.mTipoCuadrillasRepository = tipocuadrillasRepository;
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {

        ITipoCuadrillasRepository.TipoCuadrillaRepositoryCallBack findCallback = new ITipoCuadrillasRepository.TipoCuadrillaRepositoryCallBack() {
            @Override
            //public void onSuccess(List<Tipo_Cuadrilla> tipoCuadrilla) {
            public void onSuccess(List<Tipo_Cuadrilla> tipoCuadrilla) {
                ResponseValue responseValue = new ResponseValue(tipoCuadrilla);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onError(String error) {
                getUseCaseCallback().onError(error);
            }
        };


        mTipoCuadrillasRepository.query(findCallback, requestValues.getFilter());
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

        //private final List<Tipo_Cuadrilla> tipoCuadrilla;
        private final List<Tipo_Cuadrilla> tipoCuadrilla;

        //public ResponseValue(List<Tipo_Cuadrilla> tipoCuadrilla) {
        public ResponseValue(List<Tipo_Cuadrilla> tipoCuadrilla) {
            this.tipoCuadrilla = Preconditions.checkNotNull(tipoCuadrilla, "La lista de cuadrillas no puede ser null");
        }

        //public List<Tipo_Cuadrilla> getMedidor() {
        public List<Tipo_Cuadrilla> getTipoCuadrilla() {
            return tipoCuadrilla;
        }
    }
}
