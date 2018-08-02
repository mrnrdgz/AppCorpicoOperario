package ar.com.corpico.appcorpico.home.domain.usecase;

import com.google.common.base.Preconditions;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.home.data.Estados_Home;
import ar.com.corpico.appcorpico.orders.data.orders.IOrdersRepository;

public class GetEstadosHome extends UseCase<GetEstadosHome.RequestValues, GetEstadosHome.ResponseValue> {
    private IOrdersRepository mOrdersRepository;

    public GetEstadosHome(IOrdersRepository ordersRepository) {
        this.mOrdersRepository = ordersRepository;
    }


    @Override
    public void executeUseCase(RequestValues requestValues) {

        IOrdersRepository.EstadoHomeRepositoryCallBack EstadoHomeCallback = new IOrdersRepository.EstadoHomeRepositoryCallBack() {
            @Override
            public void onSuccess(List<Estados_Home> estadosHome) {
                /*List<Cuadrilla_Home> zonas = new ArrayList<>();
                for(Zona z: zona){
                    zonas.add(z.getZona());
                }*/
                ResponseValue responseValue = new ResponseValue(estadosHome);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onError(String error) {
                getUseCaseCallback().onError(error);
            }
        };


        mOrdersRepository.queryEstadoHome(EstadoHomeCallback,requestValues.getServicio(),requestValues.getSector());
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

        private final List<Estados_Home> estadosHome;

        public ResponseValue(List<Estados_Home> estados) {
            this.estadosHome = Preconditions.checkNotNull(estados, "La lista de zonas no puede ser null");
        }

        public List<Estados_Home> getEstadosHome() {
            return estadosHome;
        }
    }

}
