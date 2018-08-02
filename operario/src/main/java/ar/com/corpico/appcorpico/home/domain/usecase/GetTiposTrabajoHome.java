package ar.com.corpico.appcorpico.home.domain.usecase;


import com.google.common.base.Preconditions;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.home.data.TiposTrabajo_Home;
import ar.com.corpico.appcorpico.orders.data.orders.IOrdersRepository;

public class GetTiposTrabajoHome extends UseCase<GetTiposTrabajoHome.RequestValues, GetTiposTrabajoHome.ResponseValue> {
    private IOrdersRepository mOrdersRepository;

    public GetTiposTrabajoHome(IOrdersRepository ordersRepository) {
        this.mOrdersRepository = ordersRepository;
    }


    @Override
    public void executeUseCase(RequestValues requestValues) {

        IOrdersRepository.TipoTrabajoHomeRepositoryCallBack TipoTrabajoHomeCallback = new IOrdersRepository.TipoTrabajoHomeRepositoryCallBack() {
            @Override
            public void onSuccess(List<TiposTrabajo_Home> tiposTrabajosHome) {
                ResponseValue responseValue = new ResponseValue(tiposTrabajosHome);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onError(String error) {
                getUseCaseCallback().onError(error);
            }
        };


        mOrdersRepository.queryTipoTrabajoHome(TipoTrabajoHomeCallback,requestValues.getServicio(),requestValues.getSector());
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private int servicio;
        private int sector;

        public RequestValues() {
        }

        public RequestValues(int servicio, int sector) {
            this.servicio = servicio;
            this.sector = sector;
        }

        public int getServicio() {
            return servicio;
        }

        public int getSector() {
            return sector;
        }

    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final List<TiposTrabajo_Home> tiposTrabajoHome;

        public ResponseValue(List<TiposTrabajo_Home> tiposTrabajo) {
            this.tiposTrabajoHome = Preconditions.checkNotNull(tiposTrabajo, "La lista de tipos de trabajo no puede ser null");
        }

        public List<TiposTrabajo_Home> getTiposTrabajoHome() {
            return tiposTrabajoHome;
        }
    }


}
