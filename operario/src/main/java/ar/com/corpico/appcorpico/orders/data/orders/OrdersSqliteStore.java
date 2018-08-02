package ar.com.corpico.appcorpico.orders.data.orders;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import ar.com.corpico.appcorpico.home.data.Cuadrilla_Home;
import ar.com.corpico.appcorpico.home.data.Estados_Home;
import ar.com.corpico.appcorpico.home.data.TiposTrabajo_Home;
import ar.com.corpico.appcorpico.home.data.Zonas_Home;
import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.orderdetailimages.FotoOrden;
import ar.com.corpico.appcorpico.orders.data.entity.PostEtapa;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestEtapa;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestOrder;
import ar.com.corpico.appcorpico.orders.data.entity.sql.OrderDataCursorMapper;
import ar.com.corpico.appcorpico.orders.data.entity.sql.OrderSql;
import ar.com.corpico.appcorpico.orders.data.entity.sql.OrderSqlOrderMapper;
import ar.com.corpico.appcorpico.orders.data.entity.sql.OrderSqlSinZona;
import ar.com.corpico.appcorpico.orders.data.zona.ZonaSqlite;
import ar.com.corpico.appcorpico.orders.data.zona.ZonaSqliteStore;
import ar.com.corpico.appcorpico.orders.domain.OrdersSelector;
import ar.com.corpico.appcorpico.orders.domain.Query;
import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp.OrdenesOperativas;

import static com.google.maps.android.PolyUtil.containsLocation;

/**
 * Created by Administrador on 07/01/2017.
 */

public class OrdersSqliteStore implements OrderStore {
    private static final String TAG = OrdersSqliteStore.class.getSimpleName();
    private static OrdersSqliteStore INSTANCE;

    private final ContentResolver mContentResolver;
    private final OrderDataCursorMapper mOrderMapper;
    private OrderSqlOrderMapper mOrderSqlMapper;
    private final SessionsPrefsStore mSessionPrefStore;
    private final ZonaSqliteStore mZonaSqliteStore;

    private OrdersSqliteStore(@NonNull ContentResolver contentResolver,
                              @NonNull OrderDataCursorMapper mapper,
                              @NonNull OrderSqlOrderMapper sqlMapper,
                              @NonNull SessionsPrefsStore mSessionsPrefStore, ZonaSqliteStore ZonaSqliteStore) {
        mContentResolver = Preconditions.checkNotNull(contentResolver,
                "mContentResolver no puede ser null");
        mOrderMapper = Preconditions.checkNotNull(mapper,
                "mapper no puede ser null");
        mOrderSqlMapper = Preconditions.checkNotNull(sqlMapper,
                "sqlMapper no puede ser null");
        mSessionPrefStore = Preconditions.checkNotNull(mSessionsPrefStore,
                "SessionsPrefsStore no puede ser null");

        mZonaSqliteStore = Preconditions.checkNotNull(ZonaSqliteStore,
                "ZonaSqliteStore no puede ser null");

    }

    public static OrdersSqliteStore getInstance(@NonNull ContentResolver contentResolver,
                                                @NonNull OrderDataCursorMapper orderDataCursorMapper,
                                                @NonNull OrderSqlOrderMapper orderSqlMapper,
                                                @NonNull SessionsPrefsStore sessionsPrefStore,
                                                @NonNull ZonaSqliteStore zonaSqliteStore) {
        if (INSTANCE == null) {
            INSTANCE = new OrdersSqliteStore(contentResolver, orderDataCursorMapper, orderSqlMapper, sessionsPrefStore, zonaSqliteStore);
        }
        return INSTANCE;
    }

    @Override
    public void getOrders(final GetCallbacks callbacks, Query query) {
        // 1. Consultar las ordenes
        Cursor ordersCursor = mContentResolver.query(OrdenesOperativas.crearUriConDisplay(OrdenesOperativas.DISPLAY_FULL), null, null, null, null);

        List<OrderSql> ListOrderSql = new ArrayList<>();
        if (ordersCursor != null && ordersCursor.getCount() > 0) {
            while (ordersCursor.moveToNext()) {
                OrderSql ordersql = mOrderMapper.transform(ordersCursor);
                ListOrderSql.add(ordersql);
            }
            ordersCursor.close();
        }
        if (ListOrderSql.isEmpty()) {
            callbacks.onError("No existen Ordenes");
        } else {
            List<Order> order = new ArrayList<>();
            order = mOrderSqlMapper.transform(ListOrderSql);
            OrdersSelector orderSelector = new OrdersSelector(query);
            callbacks.onSuccess(orderSelector.selectListRows(order));
        }
    }

    @Override
    public void queryCuadrillaHome(GetCuadrillaHomeStoreCallBack callback, int servicio, int sector) {

        Uri uri = OrdenesOperativas.crearUriCuadrillaHome(servicio, sector);

        Cursor cuadrillaHomeCursor = mContentResolver.query(
                uri,
                null, null, null, null);
        List<Cuadrilla_Home> listCuadrillasHome = new ArrayList<>();
        if (cuadrillaHomeCursor != null) {
            while (cuadrillaHomeCursor.moveToNext()) {
                listCuadrillasHome.add(new Cuadrilla_Home(cuadrillaHomeCursor.getString(cuadrillaHomeCursor.getColumnIndex(ContratoCorpicoApp.TiposCuadrilla.TC_DESCRIPCION)).trim(),
                        cuadrillaHomeCursor.getInt(1),
                        cuadrillaHomeCursor.getInt(1)));
            }
            callback.onSuccess(listCuadrillasHome);
        } else {
            callback.onError("No hay cuadrillas para mostral");
        }
        cuadrillaHomeCursor.close();

    }

    @Override
    public void queryTipoTrabajoHome(GetTiposTrabajoHomeStoreCallBack callback, int servicio, int sector) {
        Uri uri = OrdenesOperativas.crearUriTipoTrabajoHome(servicio, sector);

        Cursor tipoTrabajoHomeCursor = mContentResolver.query(uri,
                null,
                null,
                null,
                null);
        List<TiposTrabajo_Home> listTiposTrabajoHome = new ArrayList<>();
        if (tipoTrabajoHomeCursor != null) {
            while (tipoTrabajoHomeCursor.moveToNext()) {
                //TODO: ACA TENGO Q TRAER LA TABLA ORDEN_OPERATIVA_ETADOS...AL SERVER NO LA ESTOY TRAYENDO O LO CAMBIO
                // DESDE EL ADAPTADOR?
                listTiposTrabajoHome.add(new TiposTrabajo_Home(tipoTrabajoHomeCursor.getString(tipoTrabajoHomeCursor.getColumnIndex(ContratoCorpicoApp.TiposTrabajo.TIT_DESCRIPCION)).trim(),
                        tipoTrabajoHomeCursor.getInt(1),
                        tipoTrabajoHomeCursor.getInt(1)));
            }
            callback.onSuccess(listTiposTrabajoHome);
        } else {
            callback.onError("No hay Tipos de Trabajo para mostral");
        }
        tipoTrabajoHomeCursor.close();


    }

    @Override
    public void queryEstadosHome(GetEstadosHomeStoreCallBack callback, int servicio, int sector) {
        Uri uri = OrdenesOperativas.crearUriEstadoHome(servicio, sector);
        Cursor estadoHomeCursor = mContentResolver.query(uri,
                null,
                null,
                null,
                null);
        List<Estados_Home> listEstadosHome = new ArrayList<>();
        if (estadoHomeCursor != null) {
            while (estadoHomeCursor.moveToNext()) {
                listEstadosHome.add(new Estados_Home(estadoHomeCursor.getString(estadoHomeCursor.getColumnIndex(OrdenesOperativas.estado)).trim(),
                        estadoHomeCursor.getInt(1),
                        estadoHomeCursor.getInt(1)));
            }
            callback.onSuccess(listEstadosHome);
        } else {
            callback.onError("No hay cuadrillas para mostral");
        }
        estadoHomeCursor.close();

    }

    @Override
    public void queryZonasHome(GetZonasHomeStoreCallBack callback, int servicio, int sector) {
        // Obtengo las ordenes que no tienen zona
        Uri uriOrdersSinZona = OrdenesOperativas.crearUriSinZona(servicio, sector);
        Cursor sinZona = mContentResolver.query(uriOrdersSinZona,
                null,
                null,
                null,
                null);

        // Setear por primera vez la zona de las ordenes que no la posean
        List<OrderSqlSinZona> listOrdersSinZona = new ArrayList<>();
        List<Zonas_Home> listZonasHome = new ArrayList<>();
        if (sinZona != null) {
            while (sinZona.moveToNext()) {
                listOrdersSinZona.add(new OrderSqlSinZona(
                        sinZona.getInt(sinZona.getColumnIndex(OrdenesOperativas.numero)),
                        sinZona.getString(sinZona.getColumnIndex(OrdenesOperativas.latitud)),
                        sinZona.getString(sinZona.getColumnIndex(OrdenesOperativas.longitud))));
            }
        }
        assert sinZona != null;
        sinZona.close();

        // Calculo y seteo las zonas de cada orden
        if (listOrdersSinZona.size() > 0) {
            // Upadate Zona de las Ordenes sin zona
            try {
                setOrdersZonas(listOrdersSinZona);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (OperationApplicationException e) {
                e.printStackTrace();
            }
        }
            // Totalizar las Ordenes por Zona
            Uri uriZonaHome = OrdenesOperativas.crearUriZonaHome(servicio, sector);
            Cursor zonaHomeCursor = mContentResolver.query(uriZonaHome,
                    null,
                    null,
                    null,
                    null);
            //List<Zonas_Home> listZonasHome = new ArrayList<>();
            if (zonaHomeCursor != null) {
                while (zonaHomeCursor.moveToNext()) {
                    // todo: hacer el top(4) "where z>2 limit 4"
                    String zona = zonaHomeCursor.getString(zonaHomeCursor.getColumnIndex(OrdenesOperativas.zona));
                    if (zona != null) {
                        listZonasHome.add(new Zonas_Home(zonaHomeCursor.getString(zonaHomeCursor.getColumnIndex(OrdenesOperativas.zona)),
                                zonaHomeCursor.getInt(1),
                                zonaHomeCursor.getInt(1)));
                    }

                }
                //callback.onSuccess(listZonasHome);
            }
            assert zonaHomeCursor != null;
            zonaHomeCursor.close();
        /*else{
            callback.onError("No hay zonas para mostral");
        }*/
        callback.onSuccess(listZonasHome);
    }

    @Override
    public void getOrder(final GetCallback callback, Integer orden, Query query) {
        // 1. Consultar las ordenes
        Cursor ordersCursor = mContentResolver.query(ContratoCorpicoApp.OrdenesOperativas.crearUriOrdenDetail(orden), null, null, null, null);
        List<OrderSql> listOrderSql = new ArrayList<>();
        if (ordersCursor != null && ordersCursor.getCount() > 0) {
            while (ordersCursor.moveToNext()) {
                OrderSql orderSql = mOrderMapper.transform(ordersCursor);

                Cursor etapasCursor = mContentResolver.query(ContratoCorpicoApp.Etapas.crearUriConOrden(orden.toString()), null, null, null, null);
                List<Etapa> ListEtapa = new ArrayList<>();
                if (etapasCursor != null && etapasCursor.getCount() > 0) {
                    while (etapasCursor.moveToNext()) {
                        String fecha = etapasCursor.getString(etapasCursor.getColumnIndex(ContratoCorpicoApp.Etapas.ETA_FECHA));
                        String estado = etapasCursor.getString(etapasCursor.getColumnIndex(ContratoCorpicoApp.Etapas.ETA_ESTADO)).trim();
                        if (etapasCursor.getString(etapasCursor.getColumnIndex(ContratoCorpicoApp.Etapas.ETA_OBSERVACION)) != null) {
                            String observacion = etapasCursor.getString(etapasCursor.getColumnIndex(ContratoCorpicoApp.Etapas.ETA_OBSERVACION)).trim();
                        }
                        String usuario = etapasCursor.getString(etapasCursor.getColumnIndex(ContratoCorpicoApp.Etapas.ETA_USUARIO)).trim();

                        Etapa e = new Etapa(new DateTime(etapasCursor.getString(etapasCursor.getColumnIndex(ContratoCorpicoApp.Etapas.ETA_FECHA))),
                                etapasCursor.getString(etapasCursor.getColumnIndex(ContratoCorpicoApp.Etapas.ETA_ESTADO)).trim(),
                                etapasCursor.getString(etapasCursor.getColumnIndex(ContratoCorpicoApp.Etapas.ETA_OBSERVACION)).trim(),
                                etapasCursor.getString(etapasCursor.getColumnIndex(ContratoCorpicoApp.Etapas.ETA_USUARIO)).trim());
                        ListEtapa.add(e);
                    }
                    orderSql.setEtapas(ListEtapa);
                    etapasCursor.close();
                }
                listOrderSql.add(orderSql);
            }
            ordersCursor.close();
            //callback.onSuccess(ListOrderSql);
            List<Order> orders = new ArrayList<>();
            orders = mOrderSqlMapper.transform(listOrderSql);
            //OrdersSelector orderSelector = new OrdersSelector(query);
            //callback.onSuccess(orderSelector.selectListRows(orders));
            callback.onSuccess(orders);

        } else {
            callback.onError("No existen Ordenes");
        }

    }

    @Override
    public void getFotoOrder(GetFotoCallback callback, Integer orden, Query query) {
        Cursor fotosCursor = mContentResolver.query(ContratoCorpicoApp.Foto.crearUriConOrden(orden.toString()), null, null, null, null);
        List<FotoOrden> ListFotos = new ArrayList<>();
        if (fotosCursor != null && fotosCursor.getCount() > 0) {
            while (fotosCursor.moveToNext()) {
                FotoOrden f = new FotoOrden(fotosCursor.getInt(fotosCursor.getColumnIndex(ContratoCorpicoApp.Foto.OTF_NUMERO)),
                        fotosCursor.getInt(fotosCursor.getColumnIndex(ContratoCorpicoApp.Foto.OTF_NFOTO)),
                        new DateTime(fotosCursor.getString(fotosCursor.getColumnIndex(ContratoCorpicoApp.Foto.OTF_FECHA))),
                        fotosCursor.getString(fotosCursor.getColumnIndex(ContratoCorpicoApp.Foto.OTF_PATH)),
                        fotosCursor.getString(fotosCursor.getColumnIndex(ContratoCorpicoApp.Foto.OTF_OBSERVACIONES)).trim());
                ListFotos.add(f);
            }
            fotosCursor.close();
            callback.onSuccess(ListFotos);
        } else {
            callback.onError("No existen Fotos");
        }
    }

    @Override
    public void addOrderEtape(String estado, ArrayList<Integer> numeros, String observacion, Integer tipoCuadrillaId,
                              Double latitud, Double longitud) {
        List<ContentProviderOperation> operations = new ArrayList<>();
        String mLatitud, mLongitud;

        for (Integer numero : numeros) {
            Integer id = generateUniqueId().intValue();
            mLatitud = latitud * -1 + "S";
            mLongitud = longitud * -1 + "W";

            // Inserta Etapas
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.Etapas.URI_CONTENIDO)
                    //todo: como hago con el id q se incrementa en la base de datos?
                    // voy a tener q tener un id local q luego q ingrese en el remoto traerlo para ponerlo fijo
                    // como hago esto?
                    //fechaconvalorentero
                    .withValue(ContratoCorpicoApp.Etapas.ETA_ID, id)
                    .withValue(ContratoCorpicoApp.Etapas.ETA_ID_TEMP, id)
                    .withValue(ContratoCorpicoApp.Etapas.ETA_NUMERO_ORDEN, numero)
                    .withValue(ContratoCorpicoApp.Etapas.ETA_FECHA, DateTime.now().toString())
                    .withValue(ContratoCorpicoApp.Etapas.ETA_ESTADO, estado)
                    .withValue(ContratoCorpicoApp.Etapas.ETA_OBSERVACION, observacion)
                    .withValue(ContratoCorpicoApp.Etapas.ETA_USUARIO, mSessionPrefStore.getName())
                    .withValue(ContratoCorpicoApp.Etapas.ETA_FECHA_UPDATE, DateTime.now().toString())
                    .withValue(ContratoCorpicoApp.Etapas.ETA_SERVICIO, mSessionPrefStore.getServicio())
                    .withValue(ContratoCorpicoApp.Etapas.ETA_SECTOR, mSessionPrefStore.getSector())
                    .withValue(ContratoCorpicoApp.Etapas.ETA_LATITUD, mLatitud)
                    .withValue(ContratoCorpicoApp.Etapas.ETA_LONGITUD, mLongitud)
                    .withValue(ContratoCorpicoApp.Etapas.ETA_SYNC, ContratoCorpicoApp.SYNC_NOT)
                    .build());
            // Update Ordenes
            //todo: ver xq no me esta actualizando el valor del estado en la orden...xq deberia actualizarme la lista
            //osea sacarlas de la lista de pendientes una vez q las asigna xq pasan a estado "O"
            switch (estado) {
                case "O":
                    operations.add(ContentProviderOperation.newUpdate(OrdenesOperativas.crearUriOrdenOperativa(numero))
                            .withValue(OrdenesOperativas.sector, mSessionPrefStore.getSector())
                            .withValue(OrdenesOperativas.fecha_asignacion, DateTime.now().toString())
                            .withValue(OrdenesOperativas.user_asigo, mSessionPrefStore.getOperario())
                            .withValue(OrdenesOperativas.cuadrilla, tipoCuadrillaId)
                            .withValue(OrdenesOperativas.estado, estado)
                            .withValue(OrdenesOperativas.fecha_update, DateTime.now().toString())
                            .withValue(OrdenesOperativas.user_id, mSessionPrefStore.getName())
                            .withValue(OrdenesOperativas.sync, ContratoCorpicoApp.SYNC_NOT)
                            .build());
                    break;
                case "P":
                    operations.add(ContentProviderOperation.newUpdate(OrdenesOperativas.crearUriOrdenOperativa(numero))
                            .withValue(OrdenesOperativas.sector, 0)
                            .withValue(OrdenesOperativas.fecha_asignacion, "")
                            .withValue(OrdenesOperativas.user_asigo, "")
                            .withValue(OrdenesOperativas.cuadrilla, 0)
                            .withValue(OrdenesOperativas.estado, estado)
                            .withValue(OrdenesOperativas.fecha_update, DateTime.now().toString())
                            .withValue(OrdenesOperativas.user_id, mSessionPrefStore.getName())
                            .withValue(OrdenesOperativas.sync, ContratoCorpicoApp.SYNC_NOT)
                            .build());
                    break;
                case "E":
                    operations.add(ContentProviderOperation.newUpdate(OrdenesOperativas.crearUriOrdenOperativa(numero))
                            .withValue(OrdenesOperativas.fecha_culminacion, DateTime.now().toString())
                            .withValue(OrdenesOperativas.observacion_del_ope, observacion)
                            .withValue(OrdenesOperativas.user_realizacion, mSessionPrefStore.getOperario())
                            .withValue(OrdenesOperativas.estado, estado)
                            .withValue(OrdenesOperativas.fecha_update, DateTime.now().toString())
                            .withValue(OrdenesOperativas.user_id, mSessionPrefStore.getName())
                            .withValue(OrdenesOperativas.sync, ContratoCorpicoApp.SYNC_NOT)
                            .build());
                    break;
                case "N":
                    operations.add(ContentProviderOperation.newUpdate(OrdenesOperativas.crearUriOrdenOperativa(numero))
                            .withValue(OrdenesOperativas.observacion_del_ope, observacion)
                            .withValue(OrdenesOperativas.user_realizacion, mSessionPrefStore.getOperario())
                            .withValue(OrdenesOperativas.estado, estado)
                            .withValue(OrdenesOperativas.fecha_update, DateTime.now().toString())
                            .withValue(OrdenesOperativas.user_id, mSessionPrefStore.getName())
                            .withValue(OrdenesOperativas.sync, ContratoCorpicoApp.SYNC_NOT)
                            .build());
                    break;
                case "S":
                    operations.add(ContentProviderOperation.newUpdate(OrdenesOperativas.crearUriOrdenOperativa(numero))
                            .withValue(OrdenesOperativas.estado, estado)
                            .withValue(OrdenesOperativas.fecha_update, DateTime.now().toString())
                            .withValue(OrdenesOperativas.user_id, mSessionPrefStore.getName())
                            .withValue(OrdenesOperativas.sync, ContratoCorpicoApp.SYNC_NOT)
                            .build());
                    break;
                case "V":
                    operations.add(ContentProviderOperation.newUpdate(OrdenesOperativas.crearUriOrdenOperativa(numero))
                            .withValue(OrdenesOperativas.estado, estado)
                            .withValue(OrdenesOperativas.fecha_update, DateTime.now().toString())
                            .withValue(OrdenesOperativas.user_id, mSessionPrefStore.getName())
                            .withValue(OrdenesOperativas.sync, ContratoCorpicoApp.SYNC_NOT)
                            .build());
                    break;
            }

        }
        try {
            mContentResolver.applyBatch(ContratoCorpicoApp.AUTHORITY, (ArrayList<ContentProviderOperation>) operations);
        } catch (RemoteException | OperationApplicationException e) {
            e.printStackTrace();
        }
    }

    @Override
    // Inserta o Modifica un Turno
    public void addTurno(Integer numero, String turno) {
        List<ContentProviderOperation> operations = new ArrayList<>();
        // Consulto si esa Orden ya tiene turno para saber si modifico o inserto
        Cursor turnosCursor = mContentResolver.query(ContratoCorpicoApp.Turnos.crearUriConOrden(numero.toString()), null, null, null, null);
        // Hago un Update
        if (turnosCursor != null && turnosCursor.getCount() > 0) {
            while (turnosCursor.moveToNext()) {
                operations.add(ContentProviderOperation.newUpdate(ContratoCorpicoApp.Turnos.crearUriTurno(numero))
                        .withValue(ContratoCorpicoApp.Turnos.TUR_FECHA_UPDATE, DateTime.now().toString())
                        .withValue(ContratoCorpicoApp.Turnos.TUR_TURNO, new DateTime(turno).toString())
                        .build());
            }
        } else {
            // Hago un Insert
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.Turnos.URI_CONTENIDO)
                    .withValue(ContratoCorpicoApp.Turnos.TUR_ORDEN, numero)
                    .withValue(ContratoCorpicoApp.Turnos.TUR_FECHA_UPDATE, DateTime.now().toString())
                    .withValue(ContratoCorpicoApp.Turnos.TUR_TURNO, new DateTime(turno).toString())
                    .withValue(ContratoCorpicoApp.Turnos.TUR_SERVICIO, mSessionPrefStore.getServicio())
                    .withValue(ContratoCorpicoApp.Turnos.TUR_SECTOR, mSessionPrefStore.getSector())
                    .build());
        }
        try {
            mContentResolver.applyBatch(ContratoCorpicoApp.AUTHORITY, (ArrayList<ContentProviderOperation>) operations);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }

    @Override
    // Inserta o Modifica un Turno
    public void cancelTurno(Integer numero) {
        List<ContentProviderOperation> operations = new ArrayList<>();
        // Consulto si esa Orden ya tiene turno para saber si modifico o inserto
        Cursor turnosCursor = mContentResolver.query(ContratoCorpicoApp.Turnos.crearUriConOrden(numero.toString()), null, null, null, null);
        // Hago un Update
        if (turnosCursor != null && turnosCursor.getCount() > 0) {
            while (turnosCursor.moveToNext()) {
                operations.add(ContentProviderOperation.newDelete(ContratoCorpicoApp.Turnos.crearUriTurno(numero))
                        .build());
            }
        }
        try {
            mContentResolver.applyBatch(ContratoCorpicoApp.AUTHORITY, (ArrayList<ContentProviderOperation>) operations);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getCuadrillaxTipo(GetCuadrillaxTipoStoreCallBack callback, Criteria filter) {

    }


    public void save(List<Order> orders) {

    }

    public List<ContentProviderOperation> replicarServidor(List<RestOrder> ordenes) {
        // TODO: ¿Hay datos? Controlar
        List<ContentProviderOperation> operations = new ArrayList<>();

        // Tabla hash para recibir los tipos de ordenes entrantes
        HashMap<Integer, RestOrder> orderMap = new HashMap<>();
        for (RestOrder order : ordenes) {
            orderMap.put(order.getNumero(), order);
        }

        //Consultar registros remotos actuales
        //Uri uri = ContratoCorpicoApp.OrdenesOperativas.URI_CONTENIDO;
        Uri uri = OrdenesOperativas.crearUriConDisplay(OrdenesOperativas.DISPLAY_SINGLE);
        String select = ContratoCorpicoApp.OrdenesOperativas.TABLE_NAME;
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Ordenes - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String fecha_solicitud;
        Integer numero;
        Integer asociado;
        short suministro;
        String titular;
        String calle;
        int altura;
        String piso;
        String dpto;
        String localidad;
        String anexo;
        String tipo_trabajo;
        String motivo_trabajo;
        String observacion_al_operario;
        String latitud;
        String longitud;
        Integer grupo;
        Integer ruta;
        Integer orden_lectura;
        String tipo_usuario;
        String tipo_consumo;
        Integer potencia_declarada;
        String medidor;
        String medidor_marca;
        String medidor_modelo;
        Double factorM;
        String capacidad;
        String tension;
        String estado;
        String fecha_update;
        Integer sector;
        String fecha_asignacion;
        String user_asigno;
        String fecha_culminacion;
        Integer cuadrilla;
        String observacion_del_ope;
        Integer user_realizacion;
        String user_id;

        while (c.moveToNext()) {

            fecha_solicitud = c.getString(c.getColumnIndex(OrdenesOperativas.fecha_solicitud));
            numero = c.getInt(c.getColumnIndex(OrdenesOperativas.numero));
            asociado = c.getInt(c.getColumnIndex(OrdenesOperativas.asociado));
            suministro = c.getShort(c.getColumnIndex(OrdenesOperativas.suministro));
            titular = c.getString(c.getColumnIndex(OrdenesOperativas.titular));
            calle = c.getString(c.getColumnIndex(OrdenesOperativas.calle));
            altura = c.getInt(c.getColumnIndex(OrdenesOperativas.altura));
            piso = c.getString(c.getColumnIndex(OrdenesOperativas.piso));
            dpto = c.getString(c.getColumnIndex(OrdenesOperativas.dpto));
            localidad = c.getString(c.getColumnIndex(OrdenesOperativas.localidad));
            anexo = c.getString(c.getColumnIndex(OrdenesOperativas.anexo));
            tipo_trabajo = c.getString(c.getColumnIndex(OrdenesOperativas.tipo_trabajo));
            motivo_trabajo = c.getString(c.getColumnIndex(OrdenesOperativas.motivo_trabajo));
            observacion_al_operario = c.getString(c.getColumnIndex(OrdenesOperativas.observacion_al_operario));
            latitud = c.getString(c.getColumnIndex(OrdenesOperativas.latitud));
            longitud = c.getString(c.getColumnIndex(OrdenesOperativas.longitud));
            grupo = c.getInt(c.getColumnIndex(OrdenesOperativas.grupo));
            ruta = c.getInt(c.getColumnIndex(OrdenesOperativas.ruta));
            orden_lectura = c.getInt(c.getColumnIndex(OrdenesOperativas.orden_lectura));
            tipo_usuario = c.getString(c.getColumnIndex(OrdenesOperativas.tipo_usuario));
            tipo_consumo = c.getString(c.getColumnIndex(OrdenesOperativas.tipo_consumo));
            potencia_declarada = c.getInt(c.getColumnIndex(OrdenesOperativas.potencia_declarada));
            medidor = c.getString(c.getColumnIndex(OrdenesOperativas.medidor));
            medidor_marca = c.getString(c.getColumnIndex(OrdenesOperativas.medidor_marca));
            medidor_modelo = c.getString(c.getColumnIndex(OrdenesOperativas.medidor_modelo));
            factorM = c.getDouble(c.getColumnIndex(OrdenesOperativas.factorM));
            capacidad = c.getString(c.getColumnIndex(OrdenesOperativas.capacidad));
            tension = c.getString(c.getColumnIndex(OrdenesOperativas.tension));
            estado = c.getString(c.getColumnIndex(OrdenesOperativas.estado));
            fecha_update = c.getString(c.getColumnIndex(OrdenesOperativas.fecha_update));
            sector = c.getInt(c.getColumnIndex(OrdenesOperativas.sector));
            fecha_asignacion = c.getString(c.getColumnIndex(OrdenesOperativas.fecha_asignacion));
            ;
            user_asigno = c.getString(c.getColumnIndex(OrdenesOperativas.user_asigo));
            ;
            fecha_culminacion = c.getString(c.getColumnIndex(OrdenesOperativas.fecha_culminacion));
            ;
            cuadrilla = c.getInt(c.getColumnIndex(OrdenesOperativas.cuadrilla));
            observacion_del_ope = c.getString(c.getColumnIndex(OrdenesOperativas.observacion_del_ope));
            ;
            user_realizacion = c.getInt(c.getColumnIndex(OrdenesOperativas.user_realizacion));
            user_id = c.getString(c.getColumnIndex(OrdenesOperativas.user_id));

            RestOrder match = orderMap.get(numero);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                orderMap.remove(numero);

                Uri existingUri = OrdenesOperativas.crearUriOrdenOperativa(numero);

                // Comprobar si la orden necesita ser actualizado
                boolean b = match.getFecha_solicitud() != fecha_solicitud;
                boolean b1 = match.getNumero() != numero;
                boolean b2 = match.getAsociado() != asociado;
                boolean b3 = match.getSuministro() != suministro;
                boolean b4 = match.getTitular() != titular;
                boolean b5 = match.getCalle() != calle;
                boolean b6 = match.getAltura() != altura;
                boolean b7 = match.getPiso() != piso;
                boolean b8 = match.getDpto() != dpto;
                boolean b9 = match.getLocalidad() != localidad;
                boolean b10 = match.getAnexo() != anexo;
                boolean b11 = match.getTipo_trabajo() != tipo_trabajo;
                boolean b12 = match.getMotivo_trabajo() != motivo_trabajo;
                boolean b13 = match.getObservacion_al_operario() != observacion_al_operario;
                boolean b14 = match.getLatitud() != latitud;
                boolean b15 = match.getLongitud() != longitud;
                boolean b16 = match.getGrupo() != grupo;
                boolean b17 = match.getRuta() != ruta;
                boolean b18 = match.getOrden_lectura() != orden_lectura;
                boolean b19 = match.getTipo_usuario() != tipo_usuario;
                boolean b20 = match.getTipo_consumo() != tipo_consumo;
                boolean b21 = match.getPotencia_declarada() != potencia_declarada;
                boolean b22 = match.getMedidor() != medidor;
                boolean b23 = match.getMedidor_marca() != medidor_marca;
                boolean b24 = match.getMedidor_modelo() != medidor_modelo;
                boolean b25 = match.getFactorM() != factorM;
                boolean b26 = match.getCapacidad() != capacidad;
                boolean b27 = match.getTension() != tension;
                boolean b28 = match.getEstado() != estado;
                boolean b29 = match.getSector() != sector;
                boolean b30 = match.getFecha_asignacion() != fecha_asignacion;
                boolean b31 = match.getUser_asigno() != user_asigno;
                boolean b32 = match.getFecha_culminacion() != fecha_culminacion;
                boolean b33 = !match.getCuadrilla().equals(cuadrilla);
                boolean b34 = !match.getObservacion_del_ope().equals(observacion_del_ope);
                boolean b35 = match.getUser_realizacion() != user_realizacion;
                boolean b36 = match.getFecha_update() != fecha_update;
                boolean b37 = match.getUser_id() != user_id;


                if (b || b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8 || b9 || b10 || b11 || b12 || b13 || b14 || b15
                        || b16 || b17 || b18 || b19 || b20 || b21 || b22 || b23 || b24 || b25 || b26 || b27 || b28 || b29
                        || b30 || b31 || b32 || b33 || b34 || b35 || b36 || b37) {

                    Log.i(TAG, "Ordenes - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(OrdenesOperativas.fecha_solicitud, match.getFecha_solicitud())
                            .withValue(OrdenesOperativas.numero, match.getNumero())
                            .withValue(OrdenesOperativas.tipo_trabajo, match.getTipo_trabajo())
                            .withValue(OrdenesOperativas.motivo_trabajo, match.getMotivo_trabajo())
                            .withValue(OrdenesOperativas.observacion_al_operario, match.getObservacion_al_operario())
                            .withValue(OrdenesOperativas.asociado, match.getAsociado())
                            .withValue(OrdenesOperativas.suministro, match.getSuministro())
                            .withValue(OrdenesOperativas.titular, match.getTitular())
                            .withValue(OrdenesOperativas.calle, match.getCalle())
                            .withValue(OrdenesOperativas.altura, match.getAltura())
                            .withValue(OrdenesOperativas.piso, match.getPiso())
                            .withValue(OrdenesOperativas.dpto, match.getDpto())
                            .withValue(OrdenesOperativas.localidad, match.getLocalidad())
                            .withValue(OrdenesOperativas.anexo, match.getAnexo())
                            .withValue(OrdenesOperativas.latitud, match.getLatitud())
                            .withValue(OrdenesOperativas.longitud, match.getLongitud())
                            .withValue(OrdenesOperativas.grupo, match.getGrupo())
                            .withValue(OrdenesOperativas.ruta, match.getRuta())
                            .withValue(OrdenesOperativas.orden_lectura, match.getOrden_lectura())
                            .withValue(OrdenesOperativas.tipo_usuario, match.getTipo_usuario())
                            .withValue(OrdenesOperativas.tipo_consumo, match.getTipo_consumo())
                            .withValue(OrdenesOperativas.potencia_declarada, match.getPotencia_declarada())
                            .withValue(OrdenesOperativas.medidor, match.getMedidor())
                            .withValue(OrdenesOperativas.medidor_marca, match.getMedidor_marca())
                            .withValue(OrdenesOperativas.medidor_modelo, match.getMedidor_modelo())
                            .withValue(OrdenesOperativas.factorM, match.getFactorM())
                            .withValue(OrdenesOperativas.capacidad, match.getCapacidad())
                            .withValue(OrdenesOperativas.tension, match.getTension())
                            .withValue(OrdenesOperativas.estado, match.getEstado())
                            .withValue(OrdenesOperativas.fecha_update, match.getFecha_update())
                            .withValue(OrdenesOperativas.sector, match.getSector())
                            .withValue(OrdenesOperativas.fecha_asignacion, match.getFecha_asignacion())
                            .withValue(OrdenesOperativas.user_asigo, match.getUser_asigno())
                            .withValue(OrdenesOperativas.fecha_culminacion, match.getFecha_culminacion())
                            .withValue(OrdenesOperativas.cuadrilla, match.getCuadrilla())
                            .withValue(OrdenesOperativas.observacion_del_ope, match.getObservacion_del_ope())
                            .withValue(OrdenesOperativas.user_realizacion, match.getUser_realizacion())
                            .withValue(OrdenesOperativas.user_id, match.getUser_realizacion())
                            .withValue(OrdenesOperativas.sync, ContratoCorpicoApp.SYNC_OK)
                            .build());
                } else {
                    Log.i(TAG, "Ordenes - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = OrdenesOperativas.crearUriOrdenOperativa(numero);
                Log.i(TAG, "Ordenes - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestOrder order : orderMap.values()) {
            operations.add(ContentProviderOperation.newInsert(OrdenesOperativas.URI_CONTENIDO)
                    .withValue(OrdenesOperativas.fecha_solicitud, order.getFecha_solicitud())
                    .withValue(OrdenesOperativas.numero, order.getNumero())
                    .withValue(OrdenesOperativas.tipo_trabajo, order.getTipo_trabajo())
                    .withValue(OrdenesOperativas.motivo_trabajo, order.getMotivo_trabajo())
                    .withValue(OrdenesOperativas.observacion_al_operario, order.getObservacion_al_operario())
                    .withValue(OrdenesOperativas.asociado, order.getAsociado())
                    .withValue(OrdenesOperativas.suministro, order.getSuministro())
                    .withValue(OrdenesOperativas.titular, order.getTitular())
                    .withValue(OrdenesOperativas.calle, order.getCalle())
                    .withValue(OrdenesOperativas.altura, order.getAltura())
                    .withValue(OrdenesOperativas.piso, order.getPiso())
                    .withValue(OrdenesOperativas.dpto, order.getDpto())
                    .withValue(OrdenesOperativas.localidad, order.getLocalidad())
                    .withValue(OrdenesOperativas.anexo, order.getAnexo())
                    .withValue(OrdenesOperativas.latitud, order.getLatitud())
                    .withValue(OrdenesOperativas.longitud, order.getLongitud())
                    .withValue(OrdenesOperativas.grupo, order.getGrupo())
                    .withValue(OrdenesOperativas.ruta, order.getRuta())
                    .withValue(OrdenesOperativas.orden_lectura, order.getOrden_lectura())
                    .withValue(OrdenesOperativas.tipo_usuario, order.getTipo_usuario())
                    .withValue(OrdenesOperativas.tipo_consumo, order.getTipo_consumo())
                    .withValue(OrdenesOperativas.potencia_declarada, order.getPotencia_declarada())
                    .withValue(OrdenesOperativas.medidor, order.getMedidor())
                    .withValue(OrdenesOperativas.medidor_marca, order.getMedidor_marca())
                    .withValue(OrdenesOperativas.medidor_modelo, order.getMedidor_modelo())
                    .withValue(OrdenesOperativas.factorM, order.getFactorM())
                    .withValue(OrdenesOperativas.capacidad, order.getCapacidad())
                    .withValue(OrdenesOperativas.tension, order.getTension())
                    .withValue(OrdenesOperativas.estado, order.getEstado())
                    .withValue(OrdenesOperativas.fecha_update, order.getFecha_update())
                    .withValue(OrdenesOperativas.sector, order.getSector())
                    .withValue(OrdenesOperativas.fecha_asignacion, order.getFecha_asignacion())
                    .withValue(OrdenesOperativas.user_asigo, order.getUser_asigno())
                    .withValue(OrdenesOperativas.fecha_culminacion, order.getFecha_culminacion())
                    .withValue(OrdenesOperativas.cuadrilla, order.getCuadrilla())
                    .withValue(OrdenesOperativas.observacion_del_ope, order.getObservacion_del_ope())
                    .withValue(OrdenesOperativas.user_realizacion, order.getUser_realizacion())
                    .withValue(OrdenesOperativas.user_id, order.getUser_realizacion())
                    .withValue(OrdenesOperativas.sync, ContratoCorpicoApp.SYNC_OK)
                    .build());
        }
        //TODO: NO LA PUEDO PONER ACA XQ TODAVIA NO TIENE GRABADOS LOS DATOS DE LAS ZONAS ENTONCES LA CONSULTA DA VACIA
        //setOrdersZonas(ordenes);
        return operations;

    }

    public List<RestEtapa> obtenerEtapasInsertadas() {
        List<RestEtapa> listEtapasInsertadas = new ArrayList<>();
        //hago la llamada al los registros sucios de sqlite
        Cursor c = obtenerRegistrosSuciosEtapas();
        // Recorrer el cursor y llenar la lista
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                RestEtapa etapa = new RestEtapa(c.getInt(c.getColumnIndex(ContratoCorpicoApp.Etapas.ETA_NUMERO_ORDEN)),
                        c.getString(c.getColumnIndex(ContratoCorpicoApp.Etapas.ETA_FECHA)),
                        c.getString(c.getColumnIndex(ContratoCorpicoApp.Etapas.ETA_ESTADO)).trim(),
                        c.getString(c.getColumnIndex(ContratoCorpicoApp.Etapas.ETA_OBSERVACION)).trim(),
                        c.getString(c.getColumnIndex(ContratoCorpicoApp.Etapas.ETA_USUARIO)).trim(),
                        c.getInt(c.getColumnIndex(ContratoCorpicoApp.Etapas.ETA_ID)),
                        c.getString(c.getColumnIndex(ContratoCorpicoApp.Etapas.ETA_FECHA_UPDATE)).trim(),
                        c.getInt(c.getColumnIndex(ContratoCorpicoApp.Etapas.ETA_SERVICIO)),
                        c.getInt(c.getColumnIndex(ContratoCorpicoApp.Etapas.ETA_SECTOR)),
                        c.getString(c.getColumnIndex(ContratoCorpicoApp.Etapas.ETA_LATITUD)),
                        c.getString(c.getColumnIndex(ContratoCorpicoApp.Etapas.ETA_LONGITUD)),
                        c.getInt(c.getColumnIndex(ContratoCorpicoApp.Etapas.ETA_ID_TEMP)));
                listEtapasInsertadas.add(etapa);
            }
            c.close();
        }
        return listEtapasInsertadas;
    }

    private Cursor obtenerRegistrosSuciosEtapas() {
        //TODO: VER XQ ACA TENDRIA Q AGREGAR EL SECTOR Y EL SERVICIO
        // NO ME ESTA HACIENDO BIEN LA CONSULTA
        Uri uri = ContratoCorpicoApp.Etapas.URI_CONTENIDO;
        String selection = ContratoCorpicoApp.Etapas.ETA_SYNC + "=?";
        String[] selectionArgs = new String[]{String.valueOf(ContratoCorpicoApp.SYNC_NOT)};

        return mContentResolver.query(uri, null, selection, selectionArgs, null);
    }

    public void actualizarEtapasId(List<PostEtapa> etapasid) throws RemoteException, OperationApplicationException {
        List<ContentProviderOperation> operations = new ArrayList<>();
        for (PostEtapa postEtapa : etapasid) {
            //TODO: CONTROLAR SI ESTA BIEN COMO ESTOY ACTUALIZANDO ....BUSCO X ID TEMP...Y NO POR ORDEN
            Uri existingUri = ContratoCorpicoApp.Etapas.crearUriEtapa(postEtapa.getId_temp());
            operations.add(ContentProviderOperation.newUpdate(existingUri)
                    .withValue(ContratoCorpicoApp.Etapas.ETA_ID, postEtapa.getId())
                    .withValue(ContratoCorpicoApp.Etapas.ETA_SYNC, ContratoCorpicoApp.SYNC_OK)
                    .build());
        }
        mContentResolver.applyBatch(ContratoCorpicoApp.AUTHORITY, (ArrayList<ContentProviderOperation>) operations);
    }

    public List<RestOrder> obtenerOrdersModificadas() {
        List<RestOrder> listOrdersModificadas = new ArrayList<>();
        //hago la llamada al los registros sucios de sqlite
        Cursor c = obtenerRegistrosSuciosOrders();
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                RestOrder order = new RestOrder(c.getString(c.getColumnIndex(OrdenesOperativas.fecha_solicitud)),
                        c.getInt(c.getColumnIndex(OrdenesOperativas.numero)),
                        c.getString(c.getColumnIndex(OrdenesOperativas.tipo_trabajo)),
                        c.getString(c.getColumnIndex(OrdenesOperativas.motivo_trabajo)),
                        c.getString(c.getColumnIndex(OrdenesOperativas.observacion_al_operario)),
                        c.getInt(c.getColumnIndex(OrdenesOperativas.asociado)),
                        c.getShort(c.getColumnIndex(OrdenesOperativas.suministro)),
                        c.getString(c.getColumnIndex(OrdenesOperativas.titular)),
                        c.getString(c.getColumnIndex(OrdenesOperativas.calle)),
                        c.getInt(c.getColumnIndex(OrdenesOperativas.altura)),
                        c.getString(c.getColumnIndex(OrdenesOperativas.piso)),
                        c.getString(c.getColumnIndex(OrdenesOperativas.dpto)),
                        c.getString(c.getColumnIndex(OrdenesOperativas.localidad)),
                        c.getString(c.getColumnIndex(OrdenesOperativas.anexo)),
                        c.getString(c.getColumnIndex(OrdenesOperativas.latitud)),
                        c.getString(c.getColumnIndex(OrdenesOperativas.longitud)),
                        c.getInt(c.getColumnIndex(OrdenesOperativas.grupo)),
                        c.getInt(c.getColumnIndex(OrdenesOperativas.ruta)),
                        c.getInt(c.getColumnIndex(OrdenesOperativas.orden_lectura)),
                        c.getString(c.getColumnIndex(OrdenesOperativas.tipo_usuario)),
                        c.getString(c.getColumnIndex(OrdenesOperativas.tipo_consumo)),
                        c.getInt(c.getColumnIndex(OrdenesOperativas.potencia_declarada)),
                        c.getString(c.getColumnIndex(OrdenesOperativas.medidor)),
                        c.getString(c.getColumnIndex(OrdenesOperativas.medidor_marca)),
                        c.getString(c.getColumnIndex(OrdenesOperativas.medidor_modelo)),
                        c.getDouble(c.getColumnIndex(OrdenesOperativas.factorM)),
                        c.getString(c.getColumnIndex(OrdenesOperativas.capacidad)),
                        c.getString(c.getColumnIndex(OrdenesOperativas.tension)),
                        c.getString(c.getColumnIndex(OrdenesOperativas.estado)),
                        c.getString(c.getColumnIndex(OrdenesOperativas.fecha_update)),
                        c.getInt(c.getColumnIndex(OrdenesOperativas.sector)),
                        c.getString(c.getColumnIndex(OrdenesOperativas.fecha_asignacion)),
                        c.getString(c.getColumnIndex(OrdenesOperativas.user_asigo)),
                        c.getString(c.getColumnIndex(OrdenesOperativas.fecha_culminacion)),
                        c.getInt(c.getColumnIndex(OrdenesOperativas.cuadrilla)),
                        c.getString(c.getColumnIndex(OrdenesOperativas.observacion_del_ope)),
                        c.getInt(c.getColumnIndex(OrdenesOperativas.user_realizacion)),
                        c.getString(c.getColumnIndex(OrdenesOperativas.user_id)));
                listOrdersModificadas.add(order);
            }
        }
        c.close();
        return listOrdersModificadas;
    }

    private Cursor obtenerRegistrosSuciosOrders() {
        //TODO: VER XQ ACA TENDRIA Q AGREGAR EL SECTOR Y EL SERVICIO
        // NO ME ESTA HACIENDO BIEN LA CONSULTA
        Uri uri = ContratoCorpicoApp.OrdenesOperativas.URI_CONTENIDO;
        String selection = OrdenesOperativas.sync + "=?";
        String[] selectionArgs = new String[]{String.valueOf(ContratoCorpicoApp.SYNC_NOT)};

        return mContentResolver.query(uri, null, selection, selectionArgs, null);
    }

    public void actualizarOrders(List<RestOrder> ordersModificadas) throws RemoteException, OperationApplicationException {
        List<ContentProviderOperation> operations = new ArrayList<>();
        for (RestOrder postOrder : ordersModificadas) {
            //TODO: CONTROLAR SI ESTA BIEN COMO ESTOY ACTUALIZANDO
            Uri existingUri = ContratoCorpicoApp.OrdenesOperativas.crearUriOrdenOperativa(postOrder.getNumero());
            operations.add(ContentProviderOperation.newUpdate(existingUri)
                    .withValue(OrdenesOperativas.sync, ContratoCorpicoApp.SYNC_OK)
                    .build());
        }
        //todo ver si el try catch lo hago aca o en el repositorio

        mContentResolver.applyBatch(ContratoCorpicoApp.AUTHORITY, (ArrayList<ContentProviderOperation>) operations);

    }

    private Long generateUniqueId() {
        long val = -1;
        do {
            final UUID uid = UUID.randomUUID();
            final ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
            buffer.putLong(uid.getLeastSignificantBits());
            buffer.putLong(uid.getMostSignificantBits());
            final BigInteger bi = new BigInteger(buffer.array());
            val = bi.longValue();
        } while (val < 0);
        return val;
    }

    private void setOrdersZonas(List<OrderSqlSinZona> orders) throws RemoteException, OperationApplicationException {
        //todo: llamar a la fncion mandalole la lista de ordenes
        //para las ordenes llamar a la order sqliSTore getZ getZonas
        // agregar a sqlitezona la descripcion....
        //update de ordenes operatativa del campo zona
        Double latitud = null;
        Double longitud = null;
        List<ContentProviderOperation> operations = new ArrayList<>();
        List<ZonaSqlite> zonas = mZonaSqliteStore.getZones();
        for (OrderSqlSinZona order : orders) {
            if (order.getLatitud() != null && order.getLongitud() != null) {
                latitud = Double.valueOf((order.getLatitud().substring(0, 7))) * -1;
                longitud = Double.valueOf((order.getLongitud().substring(0, 7))) * -1;
                List<LatLng> l = new ArrayList<>();
                for (ZonaSqlite zona : zonas) {
                    l = zona.getPuntos();
                    if (containsLocation(new LatLng(latitud, longitud), zona.getPuntos(), true)) {
                        //todo: cargar la lista de operaciones para luego hacer el update de zona en las ordenes
                        operations.add(ContentProviderOperation.newUpdate(
                                ContratoCorpicoApp.OrdenesOperativas.crearUriOrdenOperativa(order.getNumero()))
                                .withValue(OrdenesOperativas.zona, zona.getZona())
                                .build());
                    }
                }
            }

                mContentResolver.applyBatch(ContratoCorpicoApp.AUTHORITY,
                        (ArrayList<ContentProviderOperation>) operations);

        }
    }
}
