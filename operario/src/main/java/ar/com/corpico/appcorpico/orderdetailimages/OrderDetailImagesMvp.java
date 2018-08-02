package ar.com.corpico.appcorpico.orderdetailimages;

import java.util.List;

public interface OrderDetailImagesMvp {
    interface View {
        void showFotosOrden(List<FotoOrden> fotosOrden);
    }

    interface Presenter{
        void loadFotosOrden(Integer orden);
    }

}
