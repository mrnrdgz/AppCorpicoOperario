package ar.com.corpico.appcorpico.orders.domain.filter;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.cuadrillas.domain.entity.Cuadrilla;

public class CriteriaCuadrilla implements Criteria<Cuadrilla>{
        private final int cuadrillaId;

        public CriteriaCuadrilla(int cuadrillaId) {

            this.cuadrillaId=cuadrillaId;
        }

        @Override
        public List<Cuadrilla> match(List<Cuadrilla> cuadrillas) {
            List<Cuadrilla> filteredCuadrillas = new ArrayList<>();
            if ( cuadrillaId != 0) {
                for (Cuadrilla cuadrilla : cuadrillas) {
                    if (cuadrilla.getTipo_cuadrilla() == cuadrillaId) {
                        filteredCuadrillas.add(cuadrilla);
                    }
                }
            }
            return filteredCuadrillas;
        }

        @Override
        public Object toSql() {
            return "select ... from order where cuadrilla = " + cuadrillaId;
        }
    }

