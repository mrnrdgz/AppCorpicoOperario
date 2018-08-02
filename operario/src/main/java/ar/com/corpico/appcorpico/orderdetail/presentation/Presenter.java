package ar.com.corpico.appcorpico.orderdetail.presentation;

import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by Administrador on 06/01/2017.
 */

public interface Presenter {
    void loadOrder(Integer numero);
    void asignarOrder(String estadoPost,String tipoCuadrilla, List<Integer> listorder,String observacion,
                      Integer tipoCuadrillaId,Double latitud,Double longitud);
    void asignarTurno(Integer numero, @Nullable String turno);
    void anularTurno(Integer numero);
}
