package ar.com.corpico.appcorpico.home.domain.usecase;

import com.google.common.base.Preconditions;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.home.data.Cuadrilla_Home;
import ar.com.corpico.appcorpico.orders.data.orders.IOrdersRepository;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

public class GetCuadrillasHome extends UseCase<GetCuadrillasHome.RequestValues, GetCuadrillasHome.ResponseValue> {
    private IOrdersRepository mOrdersRepository;

    public GetCuadrillasHome(IOrdersRepository ordersRepository) {
        this.mOrdersRepository = ordersRepository;
    }


    @Override
    public void executeUseCase(RequestValues requestValues) {

        IOrdersRepository.CuadrillaHomeRepositoryCallBack CuadrillaHomeCallback = new IOrdersRepository.CuadrillaHomeRepositoryCallBack() {
            @Override
            public void onSuccess(List<Cuadrilla_Home> cuadrillasHome) {
                /*List<Cuadrilla_Home> zonas = new ArrayList<>();
                for(Zona z: zona){
                    zonas.add(z.getZona());
                }*/
                ResponseValue responseValue = new ResponseValue(cuadrillasHome);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onError(String error) {
                getUseCaseCallback().onError(error);
            }
        };


        mOrdersRepository.queryCuadrillaHome(CuadrillaHomeCallback,requestValues.getServicio(),requestValues.getSector());
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private int servicio;
        private int sector;
        Criteria filter;

        public RequestValues() {
        }
        public RequestValues(Criteria filter){
            this.filter = filter;
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

        public Criteria getFilter() {
            return filter;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final List<Cuadrilla_Home> cuadrillaaHome;

        public ResponseValue(List<Cuadrilla_Home> zona) {
            this.cuadrillaaHome = Preconditions.checkNotNull(zona, "La lista de zonas no puede ser null");
        }

        public List<Cuadrilla_Home> getCuadrillaaHome() {
            return cuadrillaaHome;
        }
    }

}
