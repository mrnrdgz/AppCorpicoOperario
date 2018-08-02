package ar.com.corpico.appcorpico.orders.domain.usecase;

import android.util.Log;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.orders.data.orders.IOrdersRepository;
import ar.com.corpico.appcorpico.orders.data.zona.ZonaSqlite;
import ar.com.corpico.appcorpico.orders.data.zona.ZonaStore;
import ar.com.corpico.appcorpico.orders.domain.Query;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.filter.CriteriaTipoTrabajo;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.CompositeSpec;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.FechaSpec;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.SearchSpec;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.Specification;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.StateSpec;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.TipoTrabajoSpec;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.ZonaSpec;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Consultar lista de ordenes o detalle de orden
 */

public class GetOrders extends UseCase<GetOrders.RequestValues, GetOrders.ResponseValue> {
    private final ZonaStore mZonaLocalStore;
    private IOrdersRepository mOrdersRepository;

    private GetTipoTrabajo mGetTipoTrabajo;
    private CompositeSpec<Order> zonaSpec;

    public GetOrders(IOrdersRepository ordersRepository, GetTipoTrabajo getTipoTrabajo, ZonaStore zonaLocalStore) {
        this.mOrdersRepository = ordersRepository;
        mGetTipoTrabajo = Preconditions.checkNotNull(getTipoTrabajo);
        mZonaLocalStore = Preconditions.checkNotNull(zonaLocalStore);
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {

        // Obtener valores entrantes del caso de uso
        String estado = requestValues.getEstado();
        String tipoCuadrilla = requestValues.getTipoCuadrilla();
        DateTime desde = requestValues.getDesde();
        DateTime hasta = requestValues.getHasta();
        String search = requestValues.getSearch();
        Boolean estadoActual = requestValues.getEstadoActual();
        final String fieldSort = requestValues.getFieldSort();
        final List<String> zonasSeleccionadas = requestValues.getZona();
        final List<String> tiposTrabajosSeleccionados = requestValues.getTiposTrabajo();

        // TODO: Cargar puntos de las zonas seleccionadas desde sqlite
       /* List<ZonaPuntoSqlite> zones = mZonaLocalStore.getZones(null);


        // TODO: Crear especificaciones de zonas
        ZonaSpec zoneSpec;
        if (!zones.isEmpty()) {
            int i = 0;
            zoneSpec = new ZonaSpec(zones.get(i).getPuntos());
            do {
                zoneSpec = (ZonaSpec) zoneSpec.or(new ZonaSpec(zones.get(i).getPuntos()));
                i++;
            } while (i < zones.size());
        }*/

        // TODO: Crear otras especificaciones
        // TODO: Pasar IDs/Nombres de las zonas


        // Crear especificacion
        final StateSpec estadoSpec = new StateSpec(estado);
        final SearchSpec busquedaSpec = new SearchSpec(search);
        final FechaSpec fechasSpec = new FechaSpec(estado, desde, hasta, estadoActual);
        // TODO: Crear especificacion de zona

        // ¿No hay tipos de trabajo seleccionados?
        if (tiposTrabajosSeleccionados.size() == 0) {

            CriteriaTipoTrabajo criteriaTipoTrabajo = new CriteriaTipoTrabajo(tipoCuadrilla);

            GetTipoTrabajo.UseCaseCallback useCaseCallback = new GetTipoTrabajo.UseCaseCallback() {
                @Override
                public void onSuccess(Object response) {
                    GetTipoTrabajo.ResponseValue responseValue = (GetTipoTrabajo.ResponseValue) response;

                    List<String> tiposTrabajo = responseValue.getTipoTrabajo();
                    int j = 0;
                    CompositeSpec<Order> tipoSpec = new TipoTrabajoSpec(tiposTrabajo.get(j));
                    do {
                        if (j >= 1 && j <= tiposTrabajo.size()) {
                            tipoSpec = (CompositeSpec<Order>) tipoSpec.or(new TipoTrabajoSpec(tiposTrabajo.get(j)));
                        }
                        j = j + 1;
                    } while (j < tiposTrabajo.size());

                    //Especificacion general
                    final Specification<Order> resultadoSpec;

                    int i = 0;
                    if (zonasSeleccionadas.size() > 0) {
                    /*    CompositeSpec<Order> zonaSpec = new ZoneSpec(zonasSeleccionadas.get(i));
                        do {
                            if (i >= 1 && i <= zonasSeleccionadas.size()) {
                                zonaSpec = (CompositeSpec<Order>) zonaSpec.or(new ZoneSpec(zonasSeleccionadas.get(i)));
                            }
                            i = i + 1;
                        } while (i < zonasSeleccionadas.size());
                        resultadoSpec = estadoSpec.and(
                                busquedaSpec.and(
                                        fechasSpec.and(tipoSpec.and(zonaSpec))));*/
                        List<ZonaSqlite> zones = mZonaLocalStore.getZones(zonasSeleccionadas);

                        CompositeSpec<Order> zoneSpec = null;
                        if (!zones.isEmpty()) {
                            int l = 0;
                            zoneSpec = new ZonaSpec(zones.get(l).getPuntos());
                            do {
                                zoneSpec = (CompositeSpec<Order>) zoneSpec.or(new ZonaSpec(zones.get(l).getPuntos()));
                                l++;
                            } while (l < zones.size());
                        }
                        resultadoSpec = estadoSpec.and(
                                busquedaSpec.and(
                                        fechasSpec.and(tipoSpec.and(zoneSpec))));

                    } else {
                        resultadoSpec = estadoSpec.and(
                                busquedaSpec.and(
                                        fechasSpec.and(tipoSpec)));
                    }
                    // TODO: Cargar ordenes
                    Query mQuery = new Query(resultadoSpec, fieldSort, 1, 0, 0);
                    mOrdersRepository.query(
                            new IOrdersRepository.OrdersRepositoryCallbacks() {
                                @Override
                                public void onSuccess(List<Order> orders) {
                                    ResponseValue responseValue = new ResponseValue(orders);
                                    GetOrders.this.getUseCaseCallback().onSuccess(responseValue);
                                    //getUseCaseCallback().onSuccess(responseValue);
                                }

                                @Override
                                public void onError(String error) {
                                    GetOrders.this.getUseCaseCallback().onError(error);
                                }
                            },
                            mQuery); // TODO : Cambiar por espeicficacion total
                }

                @Override
                public void onError(String error) {
                    Log.e("GetOrders", "Error al cargar tipos de trabajo: " + error);
                }
            };
            mGetTipoTrabajo.setUseCaseCallback(useCaseCallback);
            mGetTipoTrabajo.executeUseCase(new GetTipoTrabajo.RequestValues(criteriaTipoTrabajo));
        } else {
            int j = 0;
            CompositeSpec<Order> tipoSpec = new TipoTrabajoSpec(tiposTrabajosSeleccionados.get(j));
            do {
                if (j >= 1 && j <= tiposTrabajosSeleccionados.size()) {
                    tipoSpec = (CompositeSpec<Order>) tipoSpec.or(new TipoTrabajoSpec(tiposTrabajosSeleccionados.get(j)));
                }
                j = j + 1;
            } while (j < tiposTrabajosSeleccionados.size());

            //Especificacion general
            final Specification<Order> resultadoSpec;

            int i = 0;

            if (zonasSeleccionadas.size() > 0) {
                /*CompositeSpec<Order> zonaSpec = new ZoneSpec(zonasSeleccionadas.get(i));
                do {
                    if (i >= 1 && i <= zonasSeleccionadas.size()) {
                        zonaSpec = (CompositeSpec<Order>) zonaSpec.or(new ZoneSpec(zonasSeleccionadas.get(i)));
                    }
                    i = i + 1;
                } while (i < zonasSeleccionadas.size());*/
                List<ZonaSqlite> zones = mZonaLocalStore.getZones(zonasSeleccionadas);

                CompositeSpec<Order> zoneSpec = null;
                if (!zones.isEmpty()) {
                    int l = 0;
                    zoneSpec = new ZonaSpec(zones.get(l).getPuntos());
                    do {
                        zoneSpec = (CompositeSpec<Order>) zoneSpec.or(new ZonaSpec(zones.get(l).getPuntos()));
                        l++;
                    } while (l < zones.size());
                }
                resultadoSpec = estadoSpec.and(
                        busquedaSpec.and(
                                fechasSpec.and(tipoSpec.and(zoneSpec))));
            } else {
                resultadoSpec = estadoSpec.and(
                        busquedaSpec.and(
                                fechasSpec.and(tipoSpec)));
           }

            // Cargar ordenes
            Query mQuery = new Query(resultadoSpec, fieldSort, 1, 0, 0);

            mOrdersRepository.query(
                    new IOrdersRepository.OrdersRepositoryCallbacks() {
                        @Override
                        public void onSuccess(List<Order> orders) {
                            ResponseValue responseValue = new ResponseValue(orders);
                            getUseCaseCallback().onSuccess(responseValue);
                        }

                        @Override
                        public void onError(String error) {
                            getUseCaseCallback().onError(error);
                        }
                    },
                    mQuery);
        }
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private Boolean estadoActual;
        private String search;
        private DateTime hasta;
        private DateTime desde;
        private List<String> zona;
        private List<String> tiposTrabajo;
        private String estado;
        private String tipoCuadrilla;
        private String fieldSort;

        //private Criteria filter;
        private Specification filter;

        public RequestValues() {
        }

        public RequestValues(Specification filter) {
            this.filter = filter;
        }

        //public RequestValues(String tipoCuadrilla, String estado,
        public RequestValues(String tipoCuadrilla, String estado,
                             List<String> tiposTrabajo, List<String> zona,
                             DateTime desde, DateTime hasta,
                             String search, Boolean estadoActual, String fieldSort) {
            this.tipoCuadrilla = tipoCuadrilla;
            this.estado = estado;
            this.tiposTrabajo = checkNotNull(tiposTrabajo, "tiposTrabajo no puede ser null");
            this.zona = zona;
            this.desde = desde;
            this.hasta = hasta;
            this.search = search;
            this.estadoActual = estadoActual;
            this.fieldSort = fieldSort;
        }

        public Specification getFilter() {
            return filter;
        }

        public Boolean getEstadoActual() {
            return estadoActual;
        }

        public String getSearch() {
            return search;
        }

        public DateTime getHasta() {
            return hasta;
        }

        public DateTime getDesde() {
            return desde;
        }

        public List<String> getZona() {
            return zona;
        }

        public List<String> getTiposTrabajo() {
            return tiposTrabajo;
        }

        public String getEstado() {
            return estado;
        }

        public String getTipoCuadrilla() {
            return tipoCuadrilla;
        }

        public String getFieldSort() {
            return fieldSort;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final List<Order> orders;

        public ResponseValue(List<Order> orders) {
            this.orders = checkNotNull(orders, "La lista de ordenes no puede ser null");
        }

        public List<Order> getOrders() {
            return orders;
        }
    }
}
