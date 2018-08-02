package ar.com.corpico.appcorpico.orders.data.sector;

import android.content.AsyncQueryHandler;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.common.base.Preconditions;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ar.com.corpico.appcorpico.orders.data.entity.rest.RestSector;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;

/**
 * Created by Administrador on 12/03/2018.
 */

public class SectorSqliteStore implements SectorStore {
    private static final String TAG = SectorSqliteStore.class.getSimpleName();
    private SectorSqliteStore INSTANCE;
    private ContentResolver mContentResolver;


    public SectorSqliteStore(@NonNull ContentResolver mContentResolver) {
        this.mContentResolver = Preconditions.checkNotNull(mContentResolver, "mContentResolver no puede ser null");
    }

    public SectorSqliteStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SectorSqliteStore(mContentResolver);
        }
        return INSTANCE;
    }

    @Override
    public void getSector(final GetSectorStoreCallBack callback, Criteria filter) {
        AsyncQuerySector handler = new AsyncQuerySector(mContentResolver);
        handler.setQueryListener(new  AsyncQuerySector.AsyncOpListener() {
            @Override
            public void onQueryComplete(int token, Object cookie, Cursor cursor) {
                List<RestSector> ListSector = new ArrayList<>();
                if (cursor != null && cursor.getCount()>0) {
                    while(cursor.moveToNext()) {
                        ListSector.add(new RestSector(cursor.getShort(0), cursor.getShort(1), cursor.getString(2), cursor.getString(3)));
                    }
                    callback.onSuccess(ListSector);
                }else{
                    callback.onError("No existen Sectores");
                }
            }
        });
// todo: paso 1 para dejarlo sucio localmente campo sync
        handler.startQuery(0, null, ContratoCorpicoApp.TipoUsuario.URI_CONTENIDO, null, null, null, null);
    }
    public List<ContentProviderOperation> replicarServidor(List<RestSector> sectors) {
        //Crea lista de operaciones content provider
        List<ContentProviderOperation> operations = new ArrayList<>();

        // Tabla hash para recibir los tipos de cuadrillas entrantes
        HashMap<Integer, RestSector> sectorMap = new HashMap<>();
        for (RestSector sec : sectors) {
            sectorMap.put((int) sec.getSEC_ID(), sec);
        }

        //Consultar registros remotos actuales
        Uri uri = ContratoCorpicoApp.Sector.URI_CONTENIDO;
        String select = ContratoCorpicoApp.Sector.TABLE_NAME;
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Sectores - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        short id;
        short servicio;
        String descripcion;
        String fecha_update;

        while (c.moveToNext()) {
            id = c.getShort(0);
            servicio = c.getShort(1);
            descripcion = c.getString(2);
            fecha_update = c.getString(3);


            RestSector match = sectorMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                sectorMap.remove(id);

                Uri existingUri = ContratoCorpicoApp.Sector.crearUriSector((int) id);//PASAR LOS ID A INTEGER

                // Comprobar si tipo de cuadrillas necesita ser actualizado
                boolean b = match.getSEC_ID() != id;
                boolean b1 = match.getSEC_ID_SERVICIO() != servicio;
                boolean b2 =  match.getSEC_DESCRIPCION() != descripcion;
                boolean b3 = match.getSEC_FECHA_UPDATE() != fecha_update;


                if (b || b1 || b2 || b3 ) {

                    Log.i(TAG, "Sectores - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContratoCorpicoApp.Sector.SEC_ID, match.getSEC_ID())
                            .withValue(ContratoCorpicoApp.Sector.SEC_ID_SERVICIO , match.getSEC_ID_SERVICIO())
                            .withValue(ContratoCorpicoApp.Sector.SEC_DESCRIPCION , match.getSEC_DESCRIPCION())
                            .withValue(ContratoCorpicoApp.Sector.SEC_FECHA_UPDATE , match.getSEC_FECHA_UPDATE())
                            .build());
                } else {
                    Log.i(TAG, "Sectores - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContratoCorpicoApp.Sector.crearUriSector((int) id);
                Log.i(TAG, "Sectores - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestSector sector : sectorMap.values()){
            operations.add(ContentProviderOperation.newInsert(ContratoCorpicoApp.Sector.URI_CONTENIDO)
                    .withValue(ContratoCorpicoApp.Sector.SEC_ID, sector.getSEC_ID())
                    .withValue(ContratoCorpicoApp.Sector.SEC_ID_SERVICIO , sector.getSEC_ID_SERVICIO())
                    .withValue(ContratoCorpicoApp.Sector.SEC_DESCRIPCION , sector.getSEC_DESCRIPCION())
                    .withValue(ContratoCorpicoApp.Sector.SEC_FECHA_UPDATE , sector.getSEC_FECHA_UPDATE())
                    .build());
        }
        return operations;
    }
    public static class AsyncQuerySector extends AsyncQueryHandler {
        private WeakReference<AsyncOpListener> mListener;

        public interface AsyncOpListener {
            void onQueryComplete(int token, Object cookie, Cursor cursor);
        }

        public AsyncQuerySector(ContentResolver cr) {
            super(cr);
        }

        public void setQueryListener(AsyncOpListener listener) {
            mListener = new WeakReference<>(listener);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            final AsyncOpListener listener = mListener.get();
            if (listener != null) {
                listener.onQueryComplete(token, cookie, cursor);
            } else if (cursor != null) {
                cursor.close();
            }
        }
    }
}
