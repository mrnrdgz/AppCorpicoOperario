package ar.com.corpico.appcorpico.orders.data.entity.sql;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrador on 06/04/2018.
 */

public abstract class Mapper<M,N> {

    public abstract M transform(N inputItem);

    public abstract N untransform(M inputItem);

    public abstract List<M> transform(List<N> inputList);

    public abstract List<N> untransform(List<M> inputList);

    protected List<M> basicTransform(List<N> inputList){
        List<M> outputList = new ArrayList<>();
        for (N item : inputList) {
            M outputItem = transform(item);
            if (outputItem != null) {
                outputList.add(outputItem);
            }
        }
        return outputList;
    }
}
