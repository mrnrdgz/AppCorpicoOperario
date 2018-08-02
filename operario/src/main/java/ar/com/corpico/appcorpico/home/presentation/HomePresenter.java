package ar.com.corpico.appcorpico.home.presentation;

import com.google.common.base.Preconditions;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.UseCaseHandler;
import ar.com.corpico.appcorpico.home.data.Cuadrilla_Home;
import ar.com.corpico.appcorpico.home.data.Estados_Home;
import ar.com.corpico.appcorpico.home.data.TiposTrabajo_Home;
import ar.com.corpico.appcorpico.home.data.Zonas_Home;
import ar.com.corpico.appcorpico.home.domain.usecase.GetCuadrillasHome;
import ar.com.corpico.appcorpico.home.domain.usecase.GetEstadosHome;
import ar.com.corpico.appcorpico.home.domain.usecase.GetTiposTrabajoHome;
import ar.com.corpico.appcorpico.home.domain.usecase.GetZonasHome;

public class HomePresenter implements Presenter{
    private GetCuadrillasHome mGetCuadrillasHome;
    private GetEstadosHome mGetEstadosHome;
    private GetTiposTrabajoHome mGetTiposTrabajoHome;
    private GetZonasHome mGetZonasHome;

    private View mHomeView;
    private final UseCaseHandler mUseCaseHandler;

    public HomePresenter(GetCuadrillasHome getCuadrillasHome,
                         GetEstadosHome getEstadosHome,
                         GetTiposTrabajoHome getTiposTrabajoHome,
                         GetZonasHome getZonasHome,
                         View homeView, UseCaseHandler useCaseHandler) {
        this.mGetCuadrillasHome = Preconditions.checkNotNull(getCuadrillasHome, "getCuadrillasHome no puede ser null");
        this.mGetEstadosHome = Preconditions.checkNotNull(getEstadosHome, "getEstadosHome no puede ser null");
        this.mGetTiposTrabajoHome = Preconditions.checkNotNull(getTiposTrabajoHome, "getTiposTrabajoHome no puede ser null");
        this.mGetZonasHome = Preconditions.checkNotNull(getZonasHome, "getZonasHome no puede ser null");
        this.mHomeView = Preconditions.checkNotNull(homeView, "homeView no puede ser null");
        this.mUseCaseHandler = Preconditions.checkNotNull(useCaseHandler, "useCaseHandler no puede ser null");

        mHomeView.setPresenter(this);
    }

    @Override
    public void loadCuadrillasHome(int servicio, int sector) {
        // Parámetro #1
        GetCuadrillasHome.RequestValues requestValues = new GetCuadrillasHome.RequestValues(servicio,sector);

        // Parámetro #2
        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback<GetCuadrillasHome.ResponseValue>() {
            @Override
            public void onSuccess(GetCuadrillasHome.ResponseValue response) {

                // ¿La lista tiene uno o más elementos?
                List<Cuadrilla_Home> cuadrillas = response.getCuadrillaaHome();

                if (cuadrillas.size() >= 1) {
                    // Mostrar la lista en la vista
                    mHomeView.showCuadrillas(cuadrillas);

                } else {
                    // Mostrar estado vacío
                    mHomeView.showCuadrillas(cuadrillas);
                }
            }

            @Override
            public void onError(String error) {
                // Ocultar indicador de progreso
                mHomeView.showCuadrillasError(error);
            }
        };

        // Ejecutar obtención de ordenes en un hilo de trabajo
        mGetCuadrillasHome.setUseCaseCallback(useCaseCallback);
        mUseCaseHandler.execute(mGetCuadrillasHome, requestValues, useCaseCallback);
    }

    @Override
    public void loadEstadosHome(int servicio, int sector) {

        // Parámetro #1
        GetEstadosHome.RequestValues requestValues = new GetEstadosHome.RequestValues(servicio,sector);

        // Parámetro #2
        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback<GetEstadosHome.ResponseValue>() {
            @Override
            public void onSuccess(GetEstadosHome.ResponseValue response) {

                // ¿La lista tiene uno o más elementos?
                List<Estados_Home> estados = response.getEstadosHome();

                if (estados.size() >= 1) {
                    // Mostrar la lista en la vista
                    mHomeView.showEstados(estados);

                }
            }

            @Override
            public void onError(String error) {
                mHomeView.showEstadosError(error);
            }
        };

        // Ejecutar obtención de ordenes en un hilo de trabajo
        mGetEstadosHome.setUseCaseCallback(useCaseCallback);
        mUseCaseHandler.execute(mGetEstadosHome,requestValues, useCaseCallback);

    }

    @Override
    public void loadTiposTrabajoHome(int servicio, int sector) {

        // Parámetro #1
        GetTiposTrabajoHome.RequestValues requestValues = new GetTiposTrabajoHome.RequestValues(servicio,sector);

        // Parámetro #2
        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback<GetTiposTrabajoHome.ResponseValue>() {
            @Override
            public void onSuccess(GetTiposTrabajoHome.ResponseValue response) {
                // ¿La lista tiene uno o más elementos?
                List<TiposTrabajo_Home> tiposTrabajos = response.getTiposTrabajoHome();

                if (tiposTrabajos.size() >= 1) {
                    // Mostrar la lista en la vista
                    mHomeView.showTipoTrabajos(tiposTrabajos);

                }
            }

            @Override
            public void onError(String error) {
                mHomeView.showTiposTrabajoError(error);
            }
        };

        // Ejecutar obtención de ordenes en un hilo de trabajo
        mGetTiposTrabajoHome.setUseCaseCallback(useCaseCallback);
        mUseCaseHandler.execute(mGetTiposTrabajoHome,requestValues, useCaseCallback);



    }

    @Override
    public void loadZonasHome(int servicio, int sector) {
    // Parámetro #1
        GetZonasHome.RequestValues requestValues = new GetZonasHome.RequestValues(servicio,sector);

        // Parámetro #2
        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback<GetZonasHome.ResponseValue>() {
            @Override
            public void onSuccess(GetZonasHome.ResponseValue response) {
                // ¿La lista tiene uno o más elementos?
                List<Zonas_Home> zonas = response.getZonasHome();

                if (zonas.size() >= 1) {
                    // Mostrar la lista en la vista
                    mHomeView.showZonas(zonas);

                }else{
                    mHomeView.showListZonaEmpty();

                }
            }

            @Override
            public void onError(String error) {
                mHomeView.showZonasError(error);
            }
        };

        // Ejecutar obtención de ordenes en un hilo de trabajo
        mGetZonasHome.setUseCaseCallback(useCaseCallback);
        mUseCaseHandler.execute(mGetZonasHome,requestValues, useCaseCallback);

    }
}
