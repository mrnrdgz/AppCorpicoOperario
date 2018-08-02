package ar.com.corpico.appcorpico.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.cuadrillas.data.CuadrillaRestStore;
import ar.com.corpico.appcorpico.cuadrillas.data.CuadrillaSqliteStore;
import ar.com.corpico.appcorpico.cuadrillas.data.CuadrillasRepository;
import ar.com.corpico.appcorpico.cuadrillas.data.ICuadrillasRepository;
import ar.com.corpico.appcorpico.cuadrillas.data.entity.RestCuadrilla;
import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.notes.data.INotesRepository;
import ar.com.corpico.appcorpico.notes.data.NoteRestStore;
import ar.com.corpico.appcorpico.notes.data.NoteSqliteStore;
import ar.com.corpico.appcorpico.notes.data.NotesRepository;
import ar.com.corpico.appcorpico.notes.data.entity.RestNotes;
import ar.com.corpico.appcorpico.operarios.data.IOperarioRepository;
import ar.com.corpico.appcorpico.operarios.data.OperarioRepository;
import ar.com.corpico.appcorpico.operarios.data.OperarioRestStore;
import ar.com.corpico.appcorpico.operarios.data.OperarioSqliteStore;
import ar.com.corpico.appcorpico.operarios.data.entity.RestOperario;
import ar.com.corpico.appcorpico.orders.data.empresaContratista.EmpresaContratistaRepository;
import ar.com.corpico.appcorpico.orders.data.empresaContratista.EmpresaContratistaRestStore;
import ar.com.corpico.appcorpico.orders.data.empresaContratista.EmpresaContratistaSqliteStore;
import ar.com.corpico.appcorpico.orders.data.empresaContratista.IEmpresaContratistaRepository;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestEmpresaContratista;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestEstado;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestEtapa;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestFoto;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestOrder;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestPuntoZona;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoCuadrilla;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoTrabajo;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoTrabajoCuadrilla;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTurnos;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestZona;
import ar.com.corpico.appcorpico.orders.data.entity.sql.OrderDataCursorMapper;
import ar.com.corpico.appcorpico.orders.data.entity.sql.OrderSqlOrderMapper;
import ar.com.corpico.appcorpico.orders.data.estado.EstadoRepository;
import ar.com.corpico.appcorpico.orders.data.estado.EstadoRestStore;
import ar.com.corpico.appcorpico.orders.data.estado.EstadoSqliteStore;
import ar.com.corpico.appcorpico.orders.data.estado.IEstadoRepository;
import ar.com.corpico.appcorpico.orders.data.etapa.EtapaRestStore;
import ar.com.corpico.appcorpico.orders.data.etapa.EtapaSqliteStore;
import ar.com.corpico.appcorpico.orders.data.etapa.EtapasRepository;
import ar.com.corpico.appcorpico.orders.data.etapa.IEtapasRepository;
import ar.com.corpico.appcorpico.orders.data.foto.FotoRepository;
import ar.com.corpico.appcorpico.orders.data.foto.FotoRestStore;
import ar.com.corpico.appcorpico.orders.data.foto.FotoSqliteStore;
import ar.com.corpico.appcorpico.orders.data.foto.IFotosRepository;
import ar.com.corpico.appcorpico.orders.data.orders.IOrdersRepository;
import ar.com.corpico.appcorpico.orders.data.orders.OrdersRepository;
import ar.com.corpico.appcorpico.orders.data.orders.OrdersRestStore;
import ar.com.corpico.appcorpico.orders.data.orders.OrdersSqliteStore;
import ar.com.corpico.appcorpico.orders.data.puntoZona.IPuntoZonasRepository;
import ar.com.corpico.appcorpico.orders.data.puntoZona.PuntoZonaRepository;
import ar.com.corpico.appcorpico.orders.data.puntoZona.PuntoZonaRestStore;
import ar.com.corpico.appcorpico.orders.data.puntoZona.PuntoZonaSqliteStore;
import ar.com.corpico.appcorpico.orders.data.tipoCuadrilla.ITipoCuadrillasRepository;
import ar.com.corpico.appcorpico.orders.data.tipoCuadrilla.TipoCuadrillaRestStore;
import ar.com.corpico.appcorpico.orders.data.tipoCuadrilla.TipoCuadrillaSqliteStore;
import ar.com.corpico.appcorpico.orders.data.tipoCuadrilla.TipoCuadrillasRepository;
import ar.com.corpico.appcorpico.orders.data.tipoTrabajo.ITipoTrabajoRepository;
import ar.com.corpico.appcorpico.orders.data.tipoTrabajo.TipoTrabajoRepository;
import ar.com.corpico.appcorpico.orders.data.tipoTrabajo.TipoTrabajoRestStore;
import ar.com.corpico.appcorpico.orders.data.tipoTrabajo.TipoTrabajoSqliteStore;
import ar.com.corpico.appcorpico.orders.data.tipoTrabajoCuadrilla.ITipoTrabajoCuadrillaRepository;
import ar.com.corpico.appcorpico.orders.data.tipoTrabajoCuadrilla.TipoTrabajoCuadrillaRepository;
import ar.com.corpico.appcorpico.orders.data.tipoTrabajoCuadrilla.TipoTrabajoCuadrillaRestStore;
import ar.com.corpico.appcorpico.orders.data.tipoTrabajoCuadrilla.TipoTrabajoCuadrillaSqliteStore;
import ar.com.corpico.appcorpico.orders.data.turno.ITurnosRepository;
import ar.com.corpico.appcorpico.orders.data.turno.TurnoRestStore;
import ar.com.corpico.appcorpico.orders.data.turno.TurnoSqliteStore;
import ar.com.corpico.appcorpico.orders.data.turno.TurnosRepository;
import ar.com.corpico.appcorpico.orders.data.zona.IZonasRepository;
import ar.com.corpico.appcorpico.orders.data.zona.ZonaRepository;
import ar.com.corpico.appcorpico.orders.data.zona.ZonaRestStore;
import ar.com.corpico.appcorpico.orders.data.zona.ZonaSqliteStore;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;

/**
 * Sincronizador
 */

public class SyncAdapter extends AbstractThreadedSyncAdapter {
    private static final String TAG = SyncAdapter.class.getSimpleName();

    private SessionsPrefsStore PrefStore;
    private ContentResolver mCR;
    private OrderDataCursorMapper orderMapper;
    private OrderSqlOrderMapper orderSqlMapper;
    private ZonaSqliteStore zonaSqliteStore;
    private IOrdersRepository mOrdersRepository;
    private ITipoCuadrillasRepository mTipoCuadrillasRepository;
    private ITipoTrabajoRepository mTipoTrabajoRepository;
    private IEtapasRepository mEtapasRepository;
    private ITurnosRepository mTurnosRepository;
    private ITipoTrabajoCuadrillaRepository mTipoTrabajoCuadrillasRepository;
    private IZonasRepository mZonaRepository;
    private IPuntoZonasRepository mPuntoZonaRepository;
    private IFotosRepository mFotoRepository;
    private ICuadrillasRepository mCuadrillasRepository;
    private IOperarioRepository mOperarioRepository;
    private IEmpresaContratistaRepository mEmpresaContratistaRepository;
    private IEstadoRepository mEstadoRepository;
    private INotesRepository mNotesRepository;

    /*IClientesRepository mClientesRepository = ClientesRepository.getInstance(new ClienteRestStore(),
            new ClienteSqliteStore(mCR),PrefStore);
    IEmpresaContratistaRepository mEmpresaContratistasRepository = EmpresaContratistaRepository.getInstance(new EmpresaContratistaRestStore(),
            new EmpresaContratistaSqliteStore(mCR),PrefStore);
    IEstadosOperativoRepository mEstadosOperativoRepository = EstadosOperativoRepository.getInstance(new EstadoOperativoRestStore(),
            new EstadoOperativoSqliteStore(mCR),PrefStore);
    ILocalidadRepository mLocalidadRepository = LocalidadRepository.getInstance(new LocalidadRestStore(),
            new LocalidadSqliteStore(mCR),PrefStore);
    IMedidorRepository mMedidorRepository = MedidorRepository.getInstance(new MedidorRestStore(),
            new MedidorSqliteStore(mCR),PrefStore);
    IMedidorClaseRepository mMedidorClaseRepository = MedidorClaseRepository.getInstance(new MedidorClaseRestStore(),
            new MedidorClaseSqliteStore(mCR),PrefStore);
    IMedidorMarcaRepository mMedidorMarcaRepository = MedidorMarcaRepository.getInstance(new MedidorMarcaRestStore(),
            new MedidorMarcaSqliteStore(mCR),PrefStore);
    IMedidorModeloRepository mMedidorModeloRepository = MedidorModeloRepository.getInstance(new MedidorModeloRestStore(),
            new MedidorModeloSqliteStore(mCR),PrefStore);
    IMedidorTipoRepository mMedidorTipoRepository = MedidorTipoRepository.getInstance(new MedidorTipoRestStore(),
            new MedidorTipoSqliteStore(mCR),PrefStore);
    IPuntoMedicionMedidorRepository mPuntoMedicionMedidorRepository = PuntoMedicionMedidorRepository.getInstance(new PuntoMedicionMedidorRestStore(),
            new PuntoMedicionMedidorSqliteStore(mCR),PrefStore);
    IOperarioRepository mOperarioRepository = OperarioRepository.getInstance(new OperarioRestStore(),
            new OperarioSqliteStore(mCR),PrefStore);
    ITipoConexionRepository mTipoConexionRepository = TipoConexionRepository.getInstance(new TipoConexionRestStore(),
            new TipoConexionSqliteStore(mCR),PrefStore);
    ITipoConsumoRepository mTipoConsumoRepository = TipoConsumoRepository.getInstance(new TipoConsumoRestStore(),
            new TipoConsumoSqliteStore(mCR),PrefStore);
    ITipoEmpresaRepository mTipoEmpresaRepository = TipoEmpresaRepository.getInstance(new TipoEmpresaRestStore(),
            new TipoEmpresaSqliteStore(mCR),PrefStore);
    ITipoUsuarioRepository mTipoUsuarioRepository = TipoUsuarioRepository.getInstance(new TipoUsuarioRestStore(),
            new TipoUsuarioSqliteStore(mCR),PrefStore);
    ISectorRepository mSectorRepository = SectorRepository.getInstance(new SectorRestStore(),
            new SectorSqliteStore(mCR),PrefStore);
    ISuministroRepository mSuministroRepository = SuministroRepository.getInstance(new SuministroRestStore(),
            new SuministroSqliteStore(mCR),PrefStore);
    ISuministroTipoEmpresaRepository mSuministroTipoEmpresaRepository = SuministroTipoEmpresaRepository.getInstance(new SuministroTipoEmpresaRestStore(),
            new SuministroTipoEmpresaSqliteStore(mCR),PrefStore);
    ISuministroPosicionGlobalRepository mSuministroPosicionGlobalRepository = SuministroPosicionGlobalRepository.getInstance(new SuministroPosicionGlobalRestStore(),
            new SuministroPosicionGlobalSqliteStore(mCR),PrefStore);*/



    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mCR = context.getContentResolver();
        PrefStore = SessionsPrefsStore.get(getContext().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE));
        orderMapper = OrderDataCursorMapper.getInstance();
        orderSqlMapper = OrderSqlOrderMapper.getInstance();
        zonaSqliteStore = ZonaSqliteStore.getInstance(mCR);
        mOrdersRepository = OrdersRepository.getInstance(OrdersRestStore.getInstance(),
                OrdersSqliteStore.getInstance(mCR,orderMapper, orderSqlMapper,PrefStore,zonaSqliteStore),PrefStore,orderSqlMapper );
        mTipoCuadrillasRepository = TipoCuadrillasRepository.getInstance(TipoCuadrillaRestStore.getInstance(),
                TipoCuadrillaSqliteStore.getInstance(mCR),PrefStore);
        mTipoTrabajoRepository = TipoTrabajoRepository.getInstance(TipoTrabajoRestStore.getInstance(),
                TipoTrabajoSqliteStore.getInstance(mCR),PrefStore);
        mEtapasRepository = EtapasRepository.getInstance(EtapaRestStore.getInstance(),
                EtapaSqliteStore.getInstance(mCR),PrefStore);
        mTurnosRepository = TurnosRepository.getInstance(TurnoRestStore.getInstance(),
                TurnoSqliteStore.getInstance(mCR),PrefStore);
        mTipoTrabajoCuadrillasRepository = TipoTrabajoCuadrillaRepository.getInstance(TipoTrabajoCuadrillaRestStore.getInstance(),
                TipoTrabajoCuadrillaSqliteStore.getInstance(mCR),PrefStore);
        mZonaRepository = ZonaRepository.getInstance(ZonaRestStore.getInstance(),
                ZonaSqliteStore.getInstance(mCR),PrefStore);
        mPuntoZonaRepository = PuntoZonaRepository.getInstance(PuntoZonaRestStore.getInstance(),
                PuntoZonaSqliteStore.getInstance(mCR),PrefStore);
        mFotoRepository = FotoRepository.getInstance(FotoRestStore.getInstance(),
                FotoSqliteStore.getInstance(mCR),PrefStore);
        mCuadrillasRepository = CuadrillasRepository.getInstance(CuadrillaRestStore.getInstance(),
                CuadrillaSqliteStore.getInstance(mCR),PrefStore);
        mOperarioRepository = OperarioRepository.getInstance(OperarioRestStore.getInstance(),
                OperarioSqliteStore.getInstance(mCR),PrefStore);
        mEmpresaContratistaRepository = EmpresaContratistaRepository.getInstance(EmpresaContratistaRestStore.getInstance(),
                EmpresaContratistaSqliteStore.getInstance(mCR),PrefStore);
        mEstadoRepository = EstadoRepository.getInstance(EstadoRestStore.getInstance(),
                EstadoSqliteStore.getInstance(mCR),PrefStore);
        mNotesRepository = NotesRepository.getInstance(NoteRestStore.getInstance(),
                NoteSqliteStore.getInstance(mCR,PrefStore),PrefStore);

    }

    /**
     * Constructor para mantener compatibilidad en versiones inferiores a 3.0
     */
    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mCR = context.getContentResolver();
    }

    public static void inicializarSyncAdapter(Context context) {
        obtenerCuentaASincronizar(context);
    }

    @Override
    public void onPerformSync(Account account,
                              Bundle extras,
                              String authority,
                              ContentProviderClient provider,
                              final SyncResult syncResult) {

        Log.i(TAG, "onPerformSync()...");


                // Executor
                ExecutorService executor = Executors.newSingleThreadExecutor();

                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            realizarSincronizacionLocal();
                            realizarSincronizacionRemota();
                            // todo: ver de poder controlar x logica si se hizo la sync local y remota y luego ejecutar estas acciones
                            // separarlas en dos metodos ...
                            PrefStore.setUltimaSync(DateTime.now().toString());
                            PrefStore.setPrimeraCarga(false);

                            // Crear mensaje de finalización de sincronización
                            Intent intent = new Intent("home-syncadapter-message");
                            intent.putExtra("sync", true);
                            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);

                        } catch (RemoteException | OperationApplicationException e) {
                            e.printStackTrace();
                        }
                    }});

    }

    private void realizarSincronizacionLocal() throws RemoteException,
            OperationApplicationException {
        Log.i(TAG, "Actualizando el cliente.");

        final ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        //realizarSincronizacionRemota();

                //ZONA
                List<RestZona> fetchedZona = mZonaRepository.fetchDataZonaIfNewer();
                ops.addAll(mZonaRepository.providerOperations(fetchedZona));

                // PUNTO ZONA
                List<RestPuntoZona> fetchedPuntoZona = mPuntoZonaRepository.fetchDataPuntoZonaIfNewer();
                ops.addAll(mPuntoZonaRepository.providerOperations(fetchedPuntoZona));

                //Orders
                List<RestOrder> fetchedOrders = mOrdersRepository.fetchDataOrdersIfNewer();
                //todo: aca controlo si no me trae nada del servidor? ver esto como lo analizo y con todos
                if(fetchedOrders.size() > 0){
                    ops.addAll(mOrdersRepository.providerOperations(fetchedOrders));
                }

                //Tipo Trabajo x Cuadrillas
                List<RestTipoTrabajoCuadrilla> fetchedTipoTrabajoCuadrillas = mTipoTrabajoCuadrillasRepository.fetchDataTipoTrabajoCuadrillaIfNewer();
                ops.addAll(mTipoTrabajoCuadrillasRepository.providerOperations(fetchedTipoTrabajoCuadrillas));

                //Cuadrillas
                List<RestTipoCuadrilla> fetchedTipoCuadrillas = mTipoCuadrillasRepository.fetchDataCuadrillaIfNewer();
                ops.addAll(mTipoCuadrillasRepository.providerOperations(fetchedTipoCuadrillas));

                //Tipos Trabajos
                List<RestTipoTrabajo> fetchedTipoTrabajo = mTipoTrabajoRepository.fetchDataTipoTrabajoIfNewer();
                ops.addAll(mTipoTrabajoRepository.providerOperations(fetchedTipoTrabajo));

                //Etapas
                List<RestEtapa> fetchedEtapas = mEtapasRepository.fetchDataEtapaIfNewer();
                ops.addAll(mEtapasRepository.providerOperations(fetchedEtapas));

                //Turnos
                List<RestTurnos> fetchedTurnos = mTurnosRepository.fetchDataTurnoIfNewer();
                ops.addAll(mTurnosRepository.providerOperations(fetchedTurnos));


                //FOTOS
                List<RestFoto> fetchedFoto = mFotoRepository.fetchDataFotoIfNewer();
                ops.addAll(mFotoRepository.providerOperations(fetchedFoto));

                //Cuadrillas
                List<RestCuadrilla> fetchedCuadrillas = mCuadrillasRepository.fetchDataCuadrillaIfNewer();
                ops.addAll(mCuadrillasRepository.providerOperations(fetchedCuadrillas));
                /*//Clientes
                List<RestCliente> fetchedClientes = mClientesRepository.fetchDataClienteIfNewer();
                ops.addAll(mClientesRepository.providerOperations(fetchedClientes));*/

                //Empresa Contratista
                List<RestEmpresaContratista> fetchedEmpresaContratista = mEmpresaContratistaRepository.fetchDataEmpresaContratistaIfNewer();
                ops.addAll(mEmpresaContratistaRepository.providerOperations(fetchedEmpresaContratista));

                /*//Estado Operativo
                List<RestEstadoOperativo> fetchedEstadoOperativo = mEstadosOperativoRepository.fetchDataEstadoOperativoIfNewer();
                ops.addAll(mEstadosOperativoRepository.providerOperations(fetchedEstadoOperativo));

                //Localidad
                List<RestLocalidad> fetchedLocalidad = mLocalidadRepository.fetchDataLocalidadIfNewer();
                ops.addAll(mLocalidadRepository.providerOperations(fetchedLocalidad));

               //Medidor
                List<RestMedidor> fetchedMedidor = mMedidorRepository.fetchDataMedidorIfNewer();
                ops.addAll(mMedidorRepository.providerOperations(fetchedMedidor));

                //Medidor Clase
                List<RestMedidorClase> fetchedMedidorClase = mMedidorClaseRepository.fetchDataMedidorClaseIfNewer();
                ops.addAll(mMedidorClaseRepository.providerOperations(fetchedMedidorClase));

                //Medidor Marca
                List<RestMedidorMarca> fetchedMedidorMarca = mMedidorMarcaRepository.fetchDataMedidorMarcaIfNewer();
                ops.addAll(mMedidorMarcaRepository.providerOperations(fetchedMedidorMarca));

                //Medidor Modelo
                List<RestMedidorModelo> fetchedMedidorModelo = mMedidorModeloRepository.fetchDataMedidorModeloIfNewer();
                ops.addAll(mMedidorModeloRepository.providerOperations(fetchedMedidorModelo));

                //Medidor Tipo
                List<RestMedidorTipo> fetchedMedidorTipo = mMedidorTipoRepository.fetchDataMedidorTipoIfNewer();
                ops.addAll(mMedidorTipoRepository.providerOperations(fetchedMedidorTipo));

                //Medidor Punto Medidicion Medidor
                List<RestPuntoMedicionMedidor> fetchedPuntoMedicionMedidor = mPuntoMedicionMedidorRepository.fetchDataPuntoMedicionMedidorIfNewer();
                ops.addAll(mPuntoMedicionMedidorRepository.providerOperations(fetchedPuntoMedicionMedidor));*/

                //Operario
                List<RestOperario> fetchedOperario = mOperarioRepository.fetchDataOperarioIfNewer();
                ops.addAll(mOperarioRepository.providerOperations(fetchedOperario));

                // Estado
                List<RestEstado> fetchedEstado = mEstadoRepository.fetchDataEstadoIfNewer();
                ops.addAll(mEstadoRepository.providerOperations(fetchedEstado));

                // Nota
                List<RestNotes> fetchedNota = mNotesRepository.fetchDataNotesIfNewer();
                ops.addAll(mNotesRepository.providerOperations(fetchedNota));

                /*//Tipo Conexion
                List<RestTipoConexion> fetchedTipoConexion = mTipoConexionRepository.fetchDataTipoConexionIfNewer();
                ops.addAll(mTipoConexionRepository.providerOperations(fetchedTipoConexion));

                //Tipo Consumo
                List<RestTipoConsumo> fetchedTipoConsumo = mTipoConsumoRepository.fetchDataTipoConsumoIfNewer();
                ops.addAll(mTipoConsumoRepository.providerOperations(fetchedTipoConsumo));

                //Tipo Empresa
                List<RestTipoEmpresa> fetchedTipoEmpresa = mTipoEmpresaRepository.fetchDataTipoEmpresaIfNewer();
                ops.addAll(mTipoEmpresaRepository.providerOperations(fetchedTipoEmpresa));

                //Tipo Usuario
                List<RestTipoUsuario> fetchedTipoUsuario = mTipoUsuarioRepository.fetchDataTipoUsuarioIfNewer();
                ops.addAll(mTipoUsuarioRepository.providerOperations(fetchedTipoUsuario));

                //Sector
                List<RestSector> fetchedSector = mSectorRepository.fetchDataSectorIfNewer();
                ops.addAll(mSectorRepository.providerOperations(fetchedSector));

                //Suministro
                List<RestSuministro> fetchedSuministro = mSuministroRepository.fetchDataSuministroIfNewer();
                ops.addAll(mSuministroRepository.providerOperations(fetchedSuministro));

                //Suministro Tipo Empresa
                List<RestSuministroTipoEmpresa> fetchedSuministroTipoEmpresa = mSuministroTipoEmpresaRepository.fetchDataSuministroTipoEmpresaIfNewer();
                ops.addAll(mSuministroTipoEmpresaRepository.providerOperations(fetchedSuministroTipoEmpresa));

                //Suministro Posicion Global
                List<RestSuministroPosicionGlobal> fetchedSuministroPosicionGlobal = mSuministroPosicionGlobalRepository.fetchDataSuministroPosicionGlobalIfNewer();
                ops.addAll(mSuministroPosicionGlobalRepository.providerOperations(fetchedSuministroPosicionGlobal));
                */


                mCR.applyBatch(ContratoCorpicoApp.AUTHORITY, ops);



    }

    private void realizarSincronizacionRemota() throws RemoteException, OperationApplicationException {
        Log.i(TAG, "Actualizando el servidor...");

        // 1. SQLite > obtenerEtapasModificadas, REST > enviarEtapasModificadas
        // 2. obtenerEtapasInsertadas
        // ...
        // obtener registros sucios por cada operacion es un motodo x cada uno

        //TODO: QUE HAGO ACA? XQ SYNCREMOTA ES BOOLEANA QUE DEBERIA HACER SI ES TRUO O FALSE?
        mOrdersRepository.SyncRemota();

    }

    public static void sincronizarAhora(Context context, boolean onlyUpload) {
        Log.i(TAG, "Realizando petición de sincronización manual.");
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        if (onlyUpload)
            bundle.putBoolean(ContentResolver.SYNC_EXTRAS_UPLOAD, true);
        ContentResolver.requestSync(obtenerCuentaASincronizar(context),
                context.getString(R.string.provider_authority), bundle);
                //ContratoCorpicoApp.AUTHORITY, bundle);
    }

    /**
     * Crea u obtiene una cuenta existente
     *
     * @param context Contexto para acceder al administrador de cuentas
     * @return cuenta auxiliar.
     */
    public static Account obtenerCuentaASincronizar(Context context) {
        // Obtener instancia del administrador de cuentas
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Crear cuenta por defecto
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.account_type));

        // Comprobar existencia de la cuenta
        if (null == accountManager.getPassword(newAccount)) {

            // Añadir la cuenta al account manager sin password y sin datos de usuario
            if (!accountManager.addAccountExplicitly(newAccount, "", null))
                return null;

        }
        Log.i(TAG, "Cuenta de usuario obtenida.");
        return newAccount;
    }

}
