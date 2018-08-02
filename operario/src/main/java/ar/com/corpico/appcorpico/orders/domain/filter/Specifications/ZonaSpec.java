package ar.com.corpico.appcorpico.orders.domain.filter.Specifications;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import ar.com.corpico.appcorpico.orders.data.zona.ZonaSqlite;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;

import static com.google.maps.android.PolyUtil.containsLocation;

public class ZonaSpec extends CompositeSpec<Order> {
    private final List<LatLng> mPuntos;

    public ZonaSpec(List<LatLng> puntos) {
        mPuntos = puntos;
    }

    public static ZonaSpec composite(List<ZonaSqlite> zones) {
        ZonaSpec zoneSpec = null;
        if (!zones.isEmpty()) {
            int i = 0;
            zoneSpec = new ZonaSpec(zones.get(i).getPuntos());
            do {
                zoneSpec = (ZonaSpec) zoneSpec.or(new ZonaSpec(zones.get(i).getPuntos()));
                i++;
            } while (i < zones.size());
        }
        return zoneSpec;
    }

    @Override
    public boolean isSatisfiedBy(Order item) {
        if (item.getLatitud() == null || item.getLongitud() == null) {
            return false;
        }

        Double latitud = null;
        Double longitud = null;

        latitud = new Double((item.getLatitud().substring(0, 7))).doubleValue() * -1;
        longitud = new Double((item.getLongitud().substring(0, 7))).doubleValue() * -1;

        return containsLocation(new LatLng(latitud, longitud), mPuntos, true);
    }
}
