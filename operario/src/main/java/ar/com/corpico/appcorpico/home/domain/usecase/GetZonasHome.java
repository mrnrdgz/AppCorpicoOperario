package ar.com.corpico.appcorpico.home.domain.usecase;

import com.google.common.base.Preconditions;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.home.data.Zonas_Home;
import ar.com.corpico.appcorpico.orders.data.orders.IOrdersRepository;

public class GetZonasHome extends UseCase<GetZonasHome.RequestValues, GetZonasHome.ResponseValue> {
    private IOrdersRepository mOrdersRepository;

    public GetZonasHome(IOrdersRepository ordersRepository) {
            this.mOrdersRepository = ordersRepository;
        }


        @Override
        public void executeUseCase(RequestValues requestValues) {

            IOrdersRepository.ZonaHomeRepositoryCallBack ZonaHomeCallback = new IOrdersRepository.ZonaHomeRepositoryCallBack() {
                @Override
                public void onSuccess(List<Zonas_Home> zonasHome) {
                /*List<Cuadrilla_Home> zonas = new ArrayList<>();
                for(Zona z: zona){
                    zonas.add(z.getZona());
                }*/
                    ResponseValue responseValue = new ResponseValue(zonasHome);
                    getUseCaseCallback().onSuccess(responseValue);
                }

                @Override
                public void onError(String error) {
                    getUseCaseCallback().onError(error);
                }
            };


            mOrdersRepository.queryZonaHome(ZonaHomeCallback,requestValues.getServicio(),requestValues.getSector());
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

            private final List<Zonas_Home> zonasHome;

            public ResponseValue(List<Zonas_Home> zonas) {
                this.zonasHome = Preconditions.checkNotNull(zonas, "La lista de zonas no puede ser null");
            }

            public List<Zonas_Home> getZonasHome() {
                return zonasHome;
            }
        }

    }
