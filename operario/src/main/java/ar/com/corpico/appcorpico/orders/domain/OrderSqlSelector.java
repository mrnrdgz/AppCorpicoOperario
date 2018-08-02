package ar.com.corpico.appcorpico.orders.domain;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.sql.OrderSql;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.CompositeSpec;

/**
 * Created by Administrador on 24/04/2018.
 */

public class OrderSqlSelector {
    private final Query mQuery;

    public OrderSqlSelector(Query query) {
        mQuery = query;
    }

    public List<OrderSql> selectListRows(List<OrderSql> items) {
        final CompositeSpec<OrderSql> memorySpecification
                = (CompositeSpec<OrderSql>) mQuery.getSpecification();
        List<OrderSql> affectedOrders;
        Comparator<OrderSql> comparator = mFechaSolicitudComparator;

        // Filtrar Por...
        affectedOrders = new ArrayList<>(
                Collections2.filter(items, new Predicate<OrderSql>() {
                    @Override
                    public boolean apply(OrderSql order) {
                        return memorySpecification == null
                                || memorySpecification.isSatisfiedBy(order);
                    }
                }));

        // Ordenar Por...
        if (mQuery.getFieldSort() != null) {
            switch (mQuery.getFieldSort()) {
                case "Fecha Solicitud":
                    comparator = mFechaSolicitudComparator;
                    break;
                //TODO: VER EL TEMA DEL ORDENAMIENTO COMPUESTO EJ: RUTA,ORDEN
                case "Turno":
                    comparator = mTurnoComparator;
                    break;
                case "Grupo":
                    comparator = mGrupoComparator;
                    break;
                case "Ruta":
                    comparator = mRutaComparator;
                    break;
                case "Orden":
                    comparator = mOrdenComparator;
                    break;
                // TODO: Añade un caso por cada comparador
            }
        }
        Collections.sort(affectedOrders, comparator);

        // Elegir Página...
        /*affectedOrders = CollectionsUtils.getPage(affectedOrders,
                mQuery.getPageNumber(), mQuery.getPageSize());*/

        return affectedOrders;
    }


    private Comparator<OrderSql> mFechaSolicitudComparator = new Comparator<OrderSql>() {
        @Override
        public int compare(OrderSql o1, OrderSql o2) {
            if (mQuery.getSortOrder() == Query.ASC_ORDER) {
                return o1.getFechaSolicitud().compareTo(o2.getFechaSolicitud());
            } else {
                return o2.getFechaSolicitud().compareTo(o1.getFechaSolicitud());
            }
        }
    };

    private Comparator<OrderSql> mTurnoComparator = new Comparator<OrderSql>() {
        @Override
        public int compare(OrderSql o1, OrderSql o2) {
            int resultado = 0;
            if (mQuery.getSortOrder() == Query.ASC_ORDER) {
                if ( o1.getTurno()!= null && o2.getTurno()!= null ){
                    resultado = o1.getTurno().compareTo(o2.getTurno());
                    //return o1.getTurno().compareTo(o2.getTurno());
                }
                if ( o1.getTurno() == null && o2.getTurno()!= null ){
                    resultado =  1;
                }
                if ( o1.getTurno() != null && o2.getTurno() == null ){
                    resultado =  -1;
                }
                if ( o1.getTurno() == null && o2.getTurno() == null ){
                    resultado =  0;
                }
            } else {
                if ( o1.getTurno()!= null && o2.getTurno()!= null ){
                    //return o2.getTurno().compareTo(o1.getTurno());
                    resultado = o2.getTurno().compareTo(o1.getTurno());
                }
            }
            return resultado;
        }
    };
    private Comparator<OrderSql> mRutaComparator = new Comparator<OrderSql>() {
        @Override
        public int compare(OrderSql o1, OrderSql o2) {
            int resultado = 0;
            if (mQuery.getSortOrder() == Query.ASC_ORDER) {
                if ((o1.getRuta().compareTo(o2.getRuta())==0) && (o1.getOrden().compareTo(o2.getOrden())>=0)){
                    resultado = 1;
                }if ((o1.getRuta().compareTo(o2.getRuta())== 0) && (o1.getOrden().compareTo(o2.getOrden())<0)) {
                    resultado = -1;
                }if ((o1.getRuta().compareTo(o2.getRuta()) > 0) ){
                    resultado = 1;
                }if ((o1.getRuta().compareTo(o2.getRuta()) < 0)) {
                    resultado = -1;
                }

            } else {
                if ((o1.getRuta().compareTo(o2.getRuta())==0) && (o1.getOrden().compareTo(o2.getOrden())<=0)){
                    resultado = 1;
                }if ((o1.getRuta().compareTo(o2.getRuta())== 0) && (o1.getOrden().compareTo(o2.getOrden())>0)) {
                    resultado = -1;
                }if ((o1.getRuta().compareTo(o2.getRuta()) < 0) ){
                    resultado = 1;
                }if ((o1.getRuta().compareTo(o2.getRuta()) > 0)) {
                    resultado = -1;
                }
            }
            return resultado;
        }
    };

    private Comparator<OrderSql> mGrupoComparator = new Comparator<OrderSql>() {
        @Override
        public int compare(OrderSql o1, OrderSql o2) {
            if (mQuery.getSortOrder() == Query.ASC_ORDER) {
                return o1.getGrupo().compareTo(o2.getGrupo());
            } else {
                return o2.getGrupo().compareTo(o1.getGrupo());
            }
        }
    };
    private Comparator<OrderSql> mOrdenComparator = new Comparator<OrderSql>() {
        @Override
        public int compare(OrderSql o1, OrderSql o2) {
            if (mQuery.getSortOrder() == Query.ASC_ORDER) {
                return o1.getOrden().compareTo(o2.getOrden());
            } else {
                return o2.getOrden().compareTo(o1.getOrden());
            }
        }
    };
    // TODO: Agrega más comparadores si los necesitas
}
