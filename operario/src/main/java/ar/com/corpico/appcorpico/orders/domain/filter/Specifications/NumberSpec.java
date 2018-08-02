package ar.com.corpico.appcorpico.orders.domain.filter.Specifications;

import ar.com.corpico.appcorpico.orders.domain.entity.Order;

/**
 * Created by Administrador on 03/05/2018.
 */

public class NumberSpec extends CompositeSpec<Order>{
    private Integer numero;

    public NumberSpec(Integer numero) {
        this.numero = numero;
    }

    @Override
    public boolean isSatisfiedBy(Order item) {
        return numero.equals(item.getNumero());

    }
}