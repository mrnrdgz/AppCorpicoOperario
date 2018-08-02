package ar.com.corpico.appcorpico.orders.data;

import org.joda.time.DateTime;

import java.util.List;

import ar.com.corpico.appcorpico.cuadrillas.data.entity.RestCuadrilla;
import ar.com.corpico.appcorpico.notes.data.entity.RestNotes;
import ar.com.corpico.appcorpico.operarios.data.entity.RestOperario;
import ar.com.corpico.appcorpico.orders.data.entity.PostEtapa;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestCliente;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestEmpresaContratista;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestEstado;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestEstadoOperativo;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestEtapa;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestFoto;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestLocalidad;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMedidor;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMedidorClase;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMedidorMarca;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMedidorModelo;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestMedidorTipo;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestOrder;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestPuntoMedicionMedidor;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestPuntoZona;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestSector;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestSuministro;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestSuministroPosicionGlobal;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestSuministroTipoEmpresa;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoConexion;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoConsumo;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoCuadrilla;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoEmpresa;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoTrabajo;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoTrabajoCuadrilla;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTipoUsuario;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestTurnos;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestZona;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrador on 01/02/2018.
 */

public interface ApiServiceOrders {
    String HOST_IP_CASA ="192.168.1.35:1052";
    String HOST_IP_TRABAJO ="192.178.1.204:1052";
    String HOST_IP_CELULAR ="192.178.1.203:1052";
    String HOST_LOCAL ="nombre";

    String HOST = HOST_IP_CASA;
    String PATH = "api/";
    String URL_BASE = String.format("http://%s/%s", HOST, PATH);
    String URL_HOST = String.format("http://%s",HOST);

    @GET("TIPO_TRABAJO_CUADRILLA")
    Call<List<RestTipoTrabajoCuadrilla>> GetTipoTrabajoCuadrilla(@Query("servicio") int servicio,
                                                                 @Query("sector") int sector,
                                                                 @Query("update") DateTime update);
    //@Header("Authorization") String authorization
    @GET("TIPO_CUADRILLA")
    Call<List<RestTipoCuadrilla>> GetTipoCuadrilla(@Query("servicio") int servicio,
                                                   @Query("sector") int sector,
                                                   @Query("update") DateTime update);

    @GET("TIPO_TRABAJO_OPERATIVO")
    Call<List<RestTipoTrabajo>> GetTipoTrabajo(@Query("servicio") int servicio,
                                               @Query("sector") int sector,
                                               @Query("update") DateTime update);

    @GET("ORDEN_OPERATIVA")
    Call<List<RestOrder>> GetOrdenOperativa(@Query("servicio") int servicio,
                                            @Query("sector") int sector,
                                            @Query("update") DateTime update,
                                            @Query("primeraCarga") Boolean primeraCarga);
    //todo: ver si va con post o con put...y la respuesta de devolucion del servicio
    @POST("ORDEN_OPERATIVA")
    @Headers("Content-Type: application/json")
    Call<Boolean> PostOrdenOperativa(@Body List<RestOrder> ordersModificadas);//, @Header("Authorization") String token);

    @GET("CLIENTES")
    Call<List<RestCliente>> GetCliente(@Query("servicio") int servicio,
                                       @Query("sector") int sector,
                                       @Query("update") DateTime update);

    @GET("ETAPA_ORDEN_OPERATIVA")
    Call<List<RestEtapa>> GetEtapa(@Query("servicio") int servicio,
                                   @Query("sector") int sector,
                                   @Query("update") DateTime update);
    @POST("ETAPA_ORDEN_OPERATIVA")
    @Headers("Content-Type: application/json")
    Call<List<PostEtapa>> PostEtapa(@Body List<RestEtapa> etapa);

    @GET("TURNO_ORDEN_OPERATIVA")
    Call<List<RestTurnos>> GetTurno(@Query("servicio") int servicio,
                                    @Query("sector") int sector,
                                    @Query("update") DateTime update);

    @GET("EMPRESA_CONTRATISTA")
    Call<List<RestEmpresaContratista>> GetEmpresaContratista(@Query("servicio") int servicio,
                                                             @Query("update") DateTime update);

    @GET("ESTADO_OPERATIVO")
    Call<List<RestEstadoOperativo>> GetEstadoOperativo(@Query("servicio") int servicio,
                                                       @Query("update") DateTime update);

    @GET("LOCALIDADS")
    Call<List<RestLocalidad>> GetLocalidad(@Query("update") DateTime update);

    @GET("MEDIDORS")
    Call<List<RestMedidor>> GetMedidor(@Query("servicio") int servicio,
                                       @Query("update") DateTime update);

    @GET("MEDIDOR_CLASE")
    Call<List<RestMedidorClase>> GetMedidorClase(@Query("servicio") int servicio,
                                                 @Query("update") DateTime update);

    @GET("MEDIDOR_MARCA")
    Call<List<RestMedidorMarca>> GetMedidorMarca(@Query("update") DateTime update);


    @GET("MEDIDOR_MODELO")
    Call<List<RestMedidorModelo>> GetMedidorModelo(@Query("servicio") int servicio,
                                                   @Query("update") DateTime update);

    @GET("MEDIDOR_TIPO")
    Call<List<RestMedidorTipo>> GetMedidorTipo(@Query("servicio") int servicio,
                                               @Query("update") DateTime update);

    @GET("OPERARIOS")
    Call<List<RestOperario>> GetOperario(@Query("servicio") int servicio,
                                         @Query("update") DateTime update);

    @GET("PUNTO_MEDICION_MEDIDOR")
    Call<List<RestPuntoMedicionMedidor>> GetPuntoMedicionMedidor(@Query("servicio") int servicio,
                                                                 @Query("update") DateTime update);

    @GET("TIPO_CONEXION")
    Call<List<RestTipoConexion>> GetTipoConexion(@Query("servicio") int servicio,
                                                 @Query("update") DateTime update);

    @GET("TIPO_CONSUMO")
    Call<List<RestTipoConsumo>> GetTipoConsumo(@Query("servicio") int servicio,
                                               @Query("update") DateTime update);

    @GET("TIPO_EMPRESA")
    Call<List<RestTipoEmpresa>> GetTipoEmpresa();

    @GET("TIPO_USUARIO")
    Call<List<RestTipoUsuario>> GetTipoUsuario(@Query("servicio") int servicio,
                                               @Query("update") DateTime update);

    @GET("SECTORS")
    Call<List<RestSector>> GetSector(@Query("servicio") int servicio,
                                     @Query("update") DateTime update);

    @GET("SUMINISTROES")
    Call<List<RestSuministro>> GetSuministro(@Query("update") DateTime update);

    @GET("SUMINISTRO_TIPO_EMPRESA")
    Call<List<RestSuministroTipoEmpresa>> GetSuministroTipoEmpresa(@Query("servicio") int servicio,
                                                                   @Query("update") DateTime update);

    @GET("SUMINISTRO_POSICION_GLOBAL")
    Call<List<RestSuministroPosicionGlobal>> GetSuministroPosicionGlobal(@Query("update") DateTime update);

    @GET("ORDEN_OPERATIVA_PUNTO_ZONA")
    Call<List<RestPuntoZona>> GetPuntoZona(@Query("update") DateTime update);

    @GET("ORDEN_OPERATIVA_ZONA")
    Call<List<RestZona>> GetZona(@Query("update") DateTime update);

    @GET("ORDEN_OPERATIVA_ESTADO")
    Call<List<RestEstado>> GetEstado();


    @GET("CUADRILLAS")
    Call<List<RestCuadrilla>> GetCuadrilla(@Query("servicio") int servicio,
                                           @Query("sector") int sector,
                                           @Query("update") DateTime update);
    @GET("NOTAS")
    Call<List<RestNotes>> GetNote(@Query("servicio") int servicio,
                                  @Query("sector") int sector,
                                  @Query("update") DateTime update);
    @GET("ORDEN_OPERATIVA_FOTO")
    Call<List<RestFoto>> GetFoto(@Query("servicio") int servicio,
                                 @Query("sector") int sector,
                                 @Query("update") DateTime update);

    @GET("ORDEN_OPERATIVA/{orderId}/photos/{imageId}")
    Call<ResponseBody> GetFotoOrden(@Query("orderId") int orderId,
                                    @Query("imageId") int imageId);

   //* TODO AGREGAR AL SERVICIO (ANALIZAR) LAS TABLAS ORDEN_OPERATIVA_MEDIDOR, ORDEN_OPERATIVA_MEDIDOR_FUNCION ==> UNA AUX DE ESTA
    //CON LOS CAMPOS DE MARCA...SERIE...TIPO Y MODELO DE MEDIDOR PARA Q LORENA CULMINE


}

