package ar.com.corpico.appcorpico.orders.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orderdetail.presentation.OrderDetailActivity;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.presentation.adapter.OrdersAdapter;
import ar.com.corpico.appcorpico.orders.presentation.dialog.AsignarOrdenDialog;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Fragmento para lista de ordenes
 */

public class OrdersListFragment extends Fragment implements OrdersListMvp.View {

    // Keys de argumentos
    public static final String ARG_TIPO_CUADRILLA = "orders.tipo_cuadrilla";
    public static final String ARG_TIPO_CUADRILLAID = "orders.tipo_cuadrillaId";
    public static final String ARG_ESTADO = "orders.estado";
    public static final String ARG_ESTADO_POST = "orders.estado.post";
    public static final String ARG_ZONAS_SELECCIONADAS = "orders.zonas_seleccionadas";
    public static final String ARG_FECHA_INICIO = "orders.fecha_inicio";
    public static final String ARG_FECHA_FIN = "orders.fecha_fin";
    public static final String ARG_SEARCH = "orders.search";
    private static final String ARG_TIPOS_TRABAJO_SELECCIONADOS = "orders.tipos_trabajo_seleccionados";
    private static final String ARG_FIELD_SORT = "orders.field_sort";
    private static final String ARG_ASIGNACION_MASIVA = "ASIGNACIÓN MASIVA";
    private static final String ARG_LATITUD = "LATITUD";
    private static final String ARG_LONGITUD = "LONGITUD";


    // Dependencias
    private OrdersListMvp.Presenter mOrdersPresenter;


    // Views
    private ListView mOrderList;
    private OrdersAdapter mOrdersAdapter;
    private TextView mEmptyView;
    private View mProgressView;


    // Argumentos
    private String mTipoCuadrilla;
    private Integer mTipoCuadrillaId;
    private String mEstado;
    private String mEstadoPost;
    private List<String> mTiposTrabajoSeleccionados;
    private List<String> mZonasSeleccionadas;
    private DateTime mFechaInicio;
    private DateTime mFechaFin;
    private String mSearch;
    private String mFieldSort;
    private String mObservacion;
    private Double mLatitud;
    private Double mLongitud;


    private ArrayList<Integer> list_items = new ArrayList<>();

    // Lógica
    private int count;

    public OrdersListFragment() {
        // Required empty public constructor
    }

    public static OrdersListFragment newInstance(
            String tipoCuadrilla, String estado,String estadoPost,Integer tipoCuadrillaId,
            ArrayList<String> tiposTrabajoSeleccionados, ArrayList<String> zonasSeleccionadas,
            DateTime fechaInicio, DateTime fechaFin, String search, String fieldSort, String observacion,Double latitud,Double longitud) {

        OrdersListFragment fragment = new OrdersListFragment();
        Bundle args = new Bundle();

        args.putString(ARG_TIPO_CUADRILLA, tipoCuadrilla);
        args.putInt(ARG_TIPO_CUADRILLAID,tipoCuadrillaId);
        args.putString(ARG_ESTADO, estado);
        args.putString(ARG_ESTADO_POST, estadoPost);
        args.putStringArrayList(ARG_ZONAS_SELECCIONADAS, (ArrayList<String>) zonasSeleccionadas);
        args.putStringArrayList(ARG_TIPOS_TRABAJO_SELECCIONADOS, tiposTrabajoSeleccionados);
        args.putSerializable(ARG_FECHA_INICIO, fechaInicio);
        args.putSerializable(ARG_FECHA_FIN, fechaFin);
        args.putString(ARG_SEARCH, search);
        args.putString(ARG_FIELD_SORT, fieldSort);
        args.putString(ARG_ASIGNACION_MASIVA, observacion);
        args.putDouble(ARG_LATITUD, latitud);
        args.putDouble(ARG_LONGITUD, longitud);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();

        if (arguments != null) {
            // Toman parámetros
            mTipoCuadrilla = arguments.getString(ARG_TIPO_CUADRILLA);
            mTipoCuadrillaId = arguments.getInt(ARG_TIPO_CUADRILLAID);
            mEstado = arguments.getString(ARG_ESTADO);
            mEstadoPost = arguments.getString(ARG_ESTADO_POST);
            mTiposTrabajoSeleccionados = arguments.getStringArrayList(ARG_TIPOS_TRABAJO_SELECCIONADOS);
            mZonasSeleccionadas = arguments.getStringArrayList(ARG_ZONAS_SELECCIONADAS);
            mFechaInicio = (DateTime) arguments.get(ARG_FECHA_INICIO);
            mFechaFin = (DateTime) arguments.get(ARG_FECHA_FIN);
            mSearch = arguments.getString(ARG_SEARCH);
            mFieldSort = arguments.getString(ARG_FIELD_SORT);
            mObservacion = arguments.getString(ARG_ASIGNACION_MASIVA);
            mLatitud = arguments.getDouble(ARG_LATITUD);
            mLongitud = arguments.getDouble(ARG_LONGITUD);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.orders_list_frag, container, false);

        // Obtención de referencias UI
        mOrderList = (ListView) root.findViewById(R.id.orders_list);
        mEmptyView = (TextView) root.findViewById(R.id.orders_empty);
        mProgressView = root.findViewById(R.id.orders_progress);

        mOrderList.setTextFilterEnabled(true);
        mOrdersAdapter = new OrdersAdapter(getActivity(), new ArrayList<Order>(0));
        mOrderList.setAdapter(mOrdersAdapter);

        mOrderList.setFocusable(false);


        mOrderList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        mOrderList.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                  long id, boolean checked) {
                if (checked) {
                    count++;
                    mOrdersAdapter.setNewSelection(position);
                    Order item = (Order) mOrderList.getAdapter().getItem(position);
                    Integer numero = item.getNumero();
                    list_items.add(numero);
                } else {
                    count--;
                    mOrdersAdapter.removeSelection(position);
                    Order item = (Order) mOrderList.getAdapter().getItem(position);
                    Integer numero = item.getNumero();
                    list_items.remove(numero);
                }
                mode.setTitle(mOrdersAdapter.getSelectionCount() + " Seleccionadas");

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                count = 0;
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.context_menu_opendientes, menu);
                mOrdersAdapter.hideAsignarButton();
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                //mOrdersAdapter.hideAsignarButton();
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.select_all:
                        count = 0;
                        mOrdersAdapter.clearSelection();
                        list_items.clear();
                        for (int i = 0; i < mOrderList.getAdapter().getCount(); i++) {
                            mOrderList.setItemChecked(i, true);
                        }
                        return true;
                    case R.id.action_asignaracuadrilla:
                        AsignarOrdenDialog a = AsignarOrdenDialog.newInstance(mEstadoPost, list_items, "Asignación Masiva" ,mTipoCuadrilla, mTipoCuadrillaId,mLatitud,mLongitud);
                                a.show(getFragmentManager(), "Asignar a Conexiones");

                        //mOrdersPresenter.asignarOrder(mEstadoPost, list_items, ARG_ASIGNACION_MASIVA,mTipoCuadrillaId);
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                mOrdersAdapter.clearSelection();
                mOrdersAdapter.showAsignarButton();

            }
        });

        mOrderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //mOrderList.setOnItemClickLister()(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Order currentOrder = mOrdersAdapter.getItem(i);
                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                intent.putExtra("ESTADO_POST", mEstadoPost);
                intent.putExtra("TIPO_CUADRILLA", mTipoCuadrilla);
                intent.putExtra("NUMERO", currentOrder.getNumero());
                intent.putExtra("TIPO_CUADRILLA_ID", mTipoCuadrillaId);
                intent.putExtra("OBSERVACION", mObservacion);
                intent.putExtra("TURNO", currentOrder.getTurno());
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
         mOrdersPresenter.loadOrders(mTipoCuadrilla, mEstado, mTiposTrabajoSeleccionados,
                mZonasSeleccionadas, mFechaInicio, mFechaFin, mSearch, true, mFieldSort);
    }


    @Override
    public void close() {
        mOrdersPresenter.loadOrders(mTipoCuadrilla, mEstado, mTiposTrabajoSeleccionados,
                mZonasSeleccionadas, mFechaInicio, mFechaFin, mSearch, true, mFieldSort);
    }

    @Override
    public void setOrdersParams(String tipoCuadrilla, String estado, List<String> tipoTrabajo, List<String> zona, DateTime desde, DateTime hasta, String search, Boolean estadoActual, String fieldSort) {
        mTipoCuadrilla = tipoCuadrilla;
        mEstado = estado;
        mTiposTrabajoSeleccionados = tipoTrabajo;
        mZonasSeleccionadas = zona;
        mFechaInicio = desde;
        mFechaFin = hasta;
        mSearch = search;
        mFieldSort = fieldSort;
    }

    @Override
    public void showOrderError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void setPresenter(OrdersListMvp.Presenter presenter) {
        mOrdersPresenter = Preconditions.checkNotNull(presenter, "mOrdersPresenter no puede ser null");
    }

    @Override
    public void showOrderList(List<Order> orders) {
        mOrdersAdapter.clear();
        mOrdersAdapter.addAll(orders);
        mOrdersAdapter.notifyDataSetChanged();
        showList(true);
    }

    @Override
    public void showOrdesEmpty() {
        mOrderList.setEmptyView(mEmptyView);
    }

    @Override
    public void showProgressIndicator(boolean show) {
        if (mProgressView != null) {
            showList(!show);
            mProgressView.setVisibility(show ? VISIBLE : GONE);
        }
    }

    public void showList(boolean show) {
        mOrderList.setVisibility(show ? VISIBLE : GONE);
        // todo: visibility de mas views
    }

}
