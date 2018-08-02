package ar.com.corpico.appcorpico.orders.data.foto;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ar.com.corpico.appcorpico.orderdetailimages.FotoOrden;
import ar.com.corpico.appcorpico.orders.data.entity.rest.RestFoto;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;

/**
 * Created by Administrador on 12/03/2018.
 */

public class FotoSqliteStore implements FotoStore {
    private static final String TAG = FotoSqliteStore.class.getSimpleName();
    private static FotoSqliteStore INSTANCE;

    private ContentResolver mContentResolver;

    private FotoSqliteStore(@NonNull ContentResolver mContentResolver) {
        this.mContentResolver = Preconditions.checkNotNull(mContentResolver,
                "mContentResolver no puede ser null");
    }
    public static FotoSqliteStore getInstance(ContentResolver mContentResolver) {
        if (INSTANCE == null) {
            INSTANCE = new FotoSqliteStore(mContentResolver);
        }
        return INSTANCE;
    }
    @Override
    public void getFoto(GetFotoStoreCallBack callback) {
        Cursor fotoCursor = mContentResolver.query(ContratoCorpicoApp.Foto.URI_CONTENIDO, null, null, null, null);
        List<FotoOrden> ListFoto = new ArrayList<>();

        if (fotoCursor != null && fotoCursor.getCount()>0) {
            while(fotoCursor.moveToNext()) {
             //TODO: LE PONGO EL NFOTO O CON EL PATH ALCANZA?
                ListFoto.add(new FotoOrden(fotoCursor.getInt(fotoCursor.getColumnIndex(ContratoCorpicoApp.Foto.OTF_NUMERO)),fotoCursor.getInt(fotoCursor.getColumnIndex(ContratoCorpicoApp.Foto.OTF_NFOTO)),new DateTime(fotoCursor.getString(fotoCursor.getColumnIndex(ContratoCorpicoApp.Foto.OTF_FECHA))),fotoCursor.getString(fotoCursor.getColumnIndex(ContratoCorpicoApp.Foto.OTF_PATH)),fotoCursor.getString(fotoCursor.getColumnIndex(ContratoCorpicoApp.Foto.OTF_OBSERVACIONES))));
            }

            callback.onSuccess(ListFoto);
        }else {
            callback.onError("No existen Zonas");
        }

    }
    public List<ContentProviderOperation> replicarServidor(List<RestFoto> fotos) {
        //TODO CREAR UNA LISTA DE OPERACION DEL PROVIDER
        List<ContentProviderOperation> operations = new ArrayList<>();
        // Tabla hash para recibir los tipos de trabajos entrantes
        String ids;
        HashMap<String, RestFoto> fotoMap = new HashMap<>();
        for (RestFoto f : fotos) {
            ids = f.getOrden() + "#" + f.getnFoto();
            fotoMap.put(ids,f);
        }

        //Consultar registros remotos actuales
        Uri uri = ContratoCorpicoApp.Foto.URI_CONTENIDO;
        String select = ContratoCorpicoApp.Foto.TABLE_NAME;

        //TODO: ACA
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Zonas - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        int orden;
        short nFoto;
        String fecha;
        String observaciones;
        String id_user;
        String fecha_update;
        String path;
        int sector;
        int servicio;

        while (c.moveToNext()) {
            orden = c.getInt(c.getColumnIndex(ContratoCorpicoApp.Foto.OTF_NUMERO));
            nFoto = c.getShort(c.getColumnIndex(ContratoCorpicoApp.Foto.OTF_NFOTO));
            fecha = c.getString(c.getColumnIndex(ContratoCorpicoApp.Foto.OTF_FECHA));
            observaciones = c.getString(c.getColumnIndex(ContratoCorpicoApp.Foto.OTF_OBSERVACIONES));
            id_user = c.getString(c.getColumnIndex(ContratoCorpicoApp.Foto.OTF_ID_USER));
            fecha_update = c.getString(c.getColumnIndex(ContratoCorpicoApp.Foto.OTF_FECHA_UPDATE));
            path = c.getString(c.getColumnIndex(ContratoCorpicoApp.Foto.OTF_PATH));
            sector = c.getInt(c.getColumnIndex(ContratoCorpicoApp.Foto.OTF_SECTOR));
            servicio = c.getInt(c.getColumnIndex(ContratoCorpicoApp.Foto.OTF_SERVICIO));

            ids = orden + "#" + nFoto;
            RestFoto match = fotoMap.get(ids);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                fotoMap.remove(ids);

                Uri existingUri = ContratoCorpicoApp.Foto.crearUriFoto(orden,nFoto);

                // Comprobar si tipo de trabajos necesita ser actualizado
                boolean b = match.getOrden() != orden;
                boolean b1 = match.getnFoto() != nFoto;
                boolean b2 = match.getFecha().toString() != fecha;
                boolean b3 = match.getObservaciones() != observaciones;
                boolean b4 = match.getId_user() != id_user;
                boolean b5 = match.getFecha_update().toString() != fecha_update;
                boolean b6 = match.getPath() != path;
                boolean b7 = match.getSector() != sector;
                boolean b8 = match.getServicio() != servicio;


                if (b || b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8 ) {

                    Log.i(TAG, "Tipos de Trabajos - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoCorpicoApp.Foto.OTF_NUMERO, match.getOrden())
                            .withValue(ContratoCorpicoApp.Foto.OTF_NFOTO, match.getnFoto())
                            .withValue(ContratoCorpicoApp.Foto.OTF_FECHA, match.getFecha())
                            .withValue(ContratoCorpicoApp.Foto.OTF_OBSERVACIONES, match.getObservaciones())
                            .withValue(ContratoCorpicoApp.Foto.OTF_ID_USER, match.getId_user())
                            .withValue(ContratoCorpicoApp.Foto.OTF_FECHA_UPDATE, match.getFecha_update())
                            .withValue(ContratoCorpicoApp.Foto.OTF_PATH, match.getPath())
                            .withValue(ContratoCorpicoApp.Foto.OTF_SECTOR, match.getSector())
                            .withValue(ContratoCorpicoApp.Foto.OTF_SERVICIO, match.getServicio())
                            .build());

                } else {
                    Log.i(TAG, "Tipos de Trabajos - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoCorpicoApp.Foto.crearUriFoto(orden,nFoto);
                Log.i(TAG, "Zonas - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestFoto foto : fotoMap.values()){
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.Foto.URI_CONTENIDO)
                    .withValue(ContratoCorpicoApp.Foto.OTF_NUMERO, foto.getOrden())
                    .withValue(ContratoCorpicoApp.Foto.OTF_NFOTO, foto.getnFoto())
                    .withValue(ContratoCorpicoApp.Foto.OTF_FECHA, foto.getFecha())
                    .withValue(ContratoCorpicoApp.Foto.OTF_OBSERVACIONES, foto.getObservaciones())
                    .withValue(ContratoCorpicoApp.Foto.OTF_ID_USER, foto.getId_user())
                    .withValue(ContratoCorpicoApp.Foto.OTF_FECHA_UPDATE, foto.getFecha_update())
                    .withValue(ContratoCorpicoApp.Foto.OTF_PATH, foto.getPath())
                    .withValue(ContratoCorpicoApp.Foto.OTF_SECTOR, foto.getSector())
                    .withValue(ContratoCorpicoApp.Foto.OTF_SERVICIO, foto.getServicio())
                    .build());
        }
        //insertarFotos();
        return operations;
    }

    public void insertarFotos (){
        List<ContentProviderOperation> operations = new ArrayList<>();
        operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.Foto.URI_CONTENIDO)
                .withValue(ContratoCorpicoApp.Foto.OTF_NUMERO, 1011594)
                .withValue(ContratoCorpicoApp.Foto.OTF_NFOTO, 6)
                .withValue(ContratoCorpicoApp.Foto.OTF_FECHA, "2018-05-29")
                .withValue(ContratoCorpicoApp.Foto.OTF_OBSERVACIONES, "prueba 1")
                .withValue(ContratoCorpicoApp.Foto.OTF_ID_USER, "rodriguez")
                .withValue(ContratoCorpicoApp.Foto.OTF_FECHA_UPDATE, "2018-05-29")
                .withValue(ContratoCorpicoApp.Foto.OTF_PATH, "http://www.hermosaprogramacion.com/wp-content/uploads/2016/01/apto1.jpg")
                .withValue(ContratoCorpicoApp.Foto.OTF_SECTOR, 2)
                .withValue(ContratoCorpicoApp.Foto.OTF_SERVICIO, 3)
                .build());

        operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.Foto.URI_CONTENIDO)
                .withValue(ContratoCorpicoApp.Foto.OTF_NUMERO, 1011594)
                .withValue(ContratoCorpicoApp.Foto.OTF_NFOTO, 3)
                .withValue(ContratoCorpicoApp.Foto.OTF_FECHA, "2018-05-29")
                .withValue(ContratoCorpicoApp.Foto.OTF_OBSERVACIONES, "prueba 2")
                .withValue(ContratoCorpicoApp.Foto.OTF_ID_USER, "rodriguez")
                .withValue(ContratoCorpicoApp.Foto.OTF_FECHA_UPDATE, "2018-05-29")
                .withValue(ContratoCorpicoApp.Foto.OTF_PATH, "http://www.hermosaprogramacion.com/wp-content/uploads/2016/01/apto5.jpg")
                .withValue(ContratoCorpicoApp.Foto.OTF_SECTOR, 2)
                .withValue(ContratoCorpicoApp.Foto.OTF_SERVICIO, 3)
                .build());

        operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.Foto.URI_CONTENIDO)
                .withValue(ContratoCorpicoApp.Foto.OTF_NUMERO, 1011594)
                .withValue(ContratoCorpicoApp.Foto.OTF_NFOTO, 4)
                .withValue(ContratoCorpicoApp.Foto.OTF_FECHA, "2018-05-29")
                .withValue(ContratoCorpicoApp.Foto.OTF_OBSERVACIONES, "prueba 3")
                .withValue(ContratoCorpicoApp.Foto.OTF_ID_USER, "rodriguez")
                .withValue(ContratoCorpicoApp.Foto.OTF_FECHA_UPDATE, "2018-05-29")
                .withValue(ContratoCorpicoApp.Foto.OTF_PATH, "http://www.hermosaprogramacion.com/wp-content/uploads/2016/01/apto3.jpg")
                .withValue(ContratoCorpicoApp.Foto.OTF_SECTOR, 2)
                .withValue(ContratoCorpicoApp.Foto.OTF_SERVICIO, 3)
                .build());
        operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.Foto.URI_CONTENIDO)
                .withValue(ContratoCorpicoApp.Foto.OTF_NUMERO, 1011594)
                .withValue(ContratoCorpicoApp.Foto.OTF_NFOTO, 5)
                .withValue(ContratoCorpicoApp.Foto.OTF_FECHA, "2018-05-29")
                .withValue(ContratoCorpicoApp.Foto.OTF_OBSERVACIONES, "prueba 4")
                .withValue(ContratoCorpicoApp.Foto.OTF_ID_USER, "rodriguez")
                .withValue(ContratoCorpicoApp.Foto.OTF_FECHA_UPDATE, "2018-05-29")
                .withValue(ContratoCorpicoApp.Foto.OTF_PATH, "http://www.hermosaprogramacion.com/wp-content/uploads/2016/01/apto4.jpg")
                .withValue(ContratoCorpicoApp.Foto.OTF_SECTOR, 2)
                .withValue(ContratoCorpicoApp.Foto.OTF_SERVICIO, 3)
                .build());

        try {
            mContentResolver.applyBatch(ContratoCorpicoApp.AUTHORITY, (ArrayList<ContentProviderOperation>) operations);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }
}
