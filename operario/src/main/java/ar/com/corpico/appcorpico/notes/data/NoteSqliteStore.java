package ar.com.corpico.appcorpico.notes.data;

import android.content.AsyncQueryHandler;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.lang.ref.WeakReference;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.notes.data.entity.RestNotes;
import ar.com.corpico.appcorpico.notes.domain.entity.Note;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp.Nota;

/**
 * Created by Administrador on 12/03/2018.
 */

public class NoteSqliteStore implements NoteStore {
    private static final String TAG = NoteSqliteStore.class.getSimpleName();
    private static NoteSqliteStore INSTANCE;
    private ContentResolver mContentResolver;
    private final SessionsPrefsStore mSessionPrefStore;

    private NoteSqliteStore(@NonNull ContentResolver mContentResolver,
                            @NonNull SessionsPrefsStore mSessionsPrefStore) {
        this.mContentResolver = Preconditions.checkNotNull(mContentResolver,
                "mContentResolver no puede ser null");
        mSessionPrefStore = Preconditions.checkNotNull(mSessionsPrefStore,
                "SessionsPrefsStore no puede ser null");
    }

    public static NoteSqliteStore getInstance(@NonNull ContentResolver mContentResolver,
                                              @NonNull SessionsPrefsStore sessionsPrefStore) {
        if (INSTANCE == null) {
            INSTANCE = new NoteSqliteStore(mContentResolver,sessionsPrefStore);
        }
        return INSTANCE;
    }

    @Override
    public void getNotes(final GetNoteStoreCallBack callback,int servicio, int sector) {
        //todo: hacer un join con el usuario para sacar el nombre del OPERARIO
        Cursor notaCursor = mContentResolver.query(Nota.URI_CONTENIDO, null, null, null, null);
        List<Note> ListNota = new ArrayList<>();
        if (notaCursor != null && notaCursor.getCount()>0) {
            while(notaCursor.moveToNext()) {
                DateTime fecha_nota = new DateTime(notaCursor.getString(notaCursor.getColumnIndex(Nota.NOV_FECHA)));
                int editable = 0;
                if(notaCursor.getInt(notaCursor.getColumnIndex(Nota.NOV_IDOPERARIO)) == Integer.parseInt(mSessionPrefStore.getOperario())){
                    editable = 1;
                }
                ListNota.add(new Note(notaCursor.getInt(notaCursor.getColumnIndex(Nota.NOV_ID)),
                        fecha_nota.toString("dd-MM-yyyy HH:mm"),
                        notaCursor.getString(notaCursor.getColumnIndex(Nota.NOV_DESCRIPCION)),
                        notaCursor.getString(notaCursor.getColumnIndex(Nota.NOV_OPERARIO)),
                        notaCursor.getInt(notaCursor.getColumnIndex(Nota.NOV_IDOPERARIO)),
                        editable));

            }
            callback.onSuccess(ListNota);
        }else{
            callback.onError("No existen Estados");
        }
    }

    @Override
    public void addNote(AddNoteStoreCallBack callback, String nota) {
        // Mapear Nota-Dominio a ContentValues
        ContentValues values = new ContentValues();

        // Preinicilizacion en la entidad
        Integer id = generateUniqueId().intValue();
        values.put(Nota.NOV_ID, id.toString());
        values.put(Nota.NOV_FECHA, DateTime.now().toString());
        values.put(Nota.NOV_DESCRIPCION, nota);
        values.put(Nota.NOV_OPERARIO, mSessionPrefStore.getNombre() + " " + mSessionPrefStore.getApellido());
        values.put(Nota.NOV_IDOPERARIO, mSessionPrefStore.getOperario());
        values.put(Nota.NOV_SERVICIO, mSessionPrefStore.getServicio());
        values.put(Nota.NOV_SECTOR, mSessionPrefStore.getSector());
        values.put(Nota.NOV_SYNC, ContratoCorpicoApp.SYNC_NOT);
        values.put(Nota.NOV_FECHA_UPDATE, DateTime.now().toString());

        Uri uri = mContentResolver.insert(Nota.URI_CONTENIDO, values);

        if(uri!=null){
            callback.onSuccess("Se ha insertado la nota correctamente"); // TODO: Cambiar por Note
        }else {
            callback.onError("No se ha podido insertar la nota");
            // TODO: Obtener texto de archivo strings.xml, Resources.getString(R.string.error_add_edit_note)
        }

    }

    @Override
    public void editNote(EditNoteStoreCallBack callback, int id,String nota) {
        // Mapear Nota-Dominio a ContentValues
        ContentValues values = new ContentValues();

        // Preinicilizacion en la entidad
        values.put(Nota.NOV_FECHA, DateTime.now().toString());
        values.put(Nota.NOV_DESCRIPCION, nota);
        values.put(Nota.NOV_OPERARIO, mSessionPrefStore.getNombre() + " " + mSessionPrefStore.getApellido());
        values.put(Nota.NOV_IDOPERARIO, mSessionPrefStore.getOperario());
        values.put(Nota.NOV_SERVICIO, mSessionPrefStore.getServicio());
        values.put(Nota.NOV_SECTOR, mSessionPrefStore.getSector());
        values.put(Nota.NOV_SYNC, ContratoCorpicoApp.SYNC_NOT);
        values.put(Nota.NOV_FECHA_UPDATE, DateTime.now().toString());

        // Update en content provider
        int resultado = mContentResolver.update(Nota.crearUriNota(String.valueOf(id)),
                values,null,null);

        if(resultado > 0){
            callback.onSuccess("Se ha editado la nota correctamente"); // TODO: Cambiar por Note
        }else {
            callback.onError("No se ha podido editar la nota");
            // TODO: Obtener texto de archivo strings.xml, Resources.getString(R.string.error_add_edit_note)
        }

    }

    public List<ContentProviderOperation> replicarServidor(List<RestNotes> notes) {
        //Crea lista de operaciones content provider
        List<ContentProviderOperation> operations = new ArrayList<>();

        // Tabla hash para recibir los tipos de cuadrillas entrantes
        HashMap<Integer, RestNotes> notaMap = new HashMap<>();
        for (RestNotes note : notes) {
            notaMap.put(note.getId(), note);
        }

        //Consultar registros remotos actuales
        Uri uri = Nota.URI_CONTENIDO;
        String select = Nota.TABLE_NAME;
        Cursor c = mContentResolver.query(uri, null, null, null, null);
        assert c != null;

        Log.i(TAG, "Notas - Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        int id;
        String fecha;
        String descripcion;
        int servicio;
        int sector;
        String operario;
        int operario_id;
        String fecha_update;

        while (c.moveToNext()) {

            id = c.getInt(c.getColumnIndex(Nota.NOV_ID));
            fecha = c.getString(c.getColumnIndex(Nota.NOV_FECHA));
            descripcion = c.getString(c.getColumnIndex(Nota.NOV_DESCRIPCION));
            servicio = c.getInt(c.getColumnIndex(Nota.NOV_SERVICIO));
            sector = c.getInt(c.getColumnIndex(Nota.NOV_SECTOR));
            operario = c.getString(c.getColumnIndex(Nota.NOV_OPERARIO));
            operario_id = c.getInt(c.getColumnIndex(Nota.NOV_IDOPERARIO));
            fecha_update = c.getString(c.getColumnIndex(Nota.NOV_FECHA_UPDATE));


            RestNotes match = notaMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                notaMap.remove(id);

                Uri existingUri = Nota.crearUriNota(String.valueOf(id));//PASAR LOS ID A INTEGER

                // Comprobar si tipo de cuadrillas necesita ser actualizado
                boolean b = match.getId() != id;
                boolean b1 = !match.getFecha().equals(fecha);
                boolean b2 = !match.getDescripcion().equals(descripcion);
                boolean b3 = match.getServicio()!=(servicio);
                boolean b4 = match.getSector()!=(sector);
                boolean b5 = !match.getOperario().equals(operario);
                boolean b6 = !match.getUpdate().equals(fecha_update);
                boolean b7 = match.getOperario_id()!=(operario_id);

                if (b || b1 || b2 || b3 || b4 || b5 || b6 || b7) {

                    Log.i(TAG, "Notas - Programando actualización de: " + existingUri);

                    operations.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(Nota.NOV_FECHA, match.getFecha())
                            .withValue(Nota.NOV_DESCRIPCION, match.getDescripcion())
                            .withValue(Nota.NOV_SERVICIO, match.getServicio())
                            .withValue(Nota.NOV_SECTOR, match.getSector())
                            .withValue(Nota.NOV_OPERARIO, match.getOperario())
                            .withValue(Nota.NOV_IDOPERARIO, match.getOperario_id())
                            .withValue(Nota.NOV_FECHA_UPDATE, match.getUpdate())
                            .build());
                } else {
                    Log.i(TAG, "Estados - No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = Nota.crearUriNota(String.valueOf(id));
                Log.i(TAG, "Estado - Programando eliminación de: " + deleteUri);
                operations.add(ContentProviderOperation.newDelete(deleteUri).build());
            }
        }
        c.close();
        // Insertar items resultantes
        for (RestNotes nota : notaMap.values()){
            Integer nov_id = generateUniqueId().intValue();
            operations.add(ContentProviderOperation.newInsert(Nota.URI_CONTENIDO)
                    .withValue(Nota.NOV_ID,nota.getId())
                    .withValue(Nota.NOV_FECHA, nota.getFecha())
                    .withValue(Nota.NOV_DESCRIPCION, nota.getDescripcion())
                    .withValue(Nota.NOV_SERVICIO, nota.getServicio())
                    .withValue(Nota.NOV_SECTOR, nota.getSector())
                    .withValue(Nota.NOV_OPERARIO, nota.getOperario())
                    .withValue(Nota.NOV_IDOPERARIO, nota.getOperario_id())
                    .withValue(Nota.NOV_FECHA_UPDATE, nota.getUpdate())
                    .build());
        }
        return operations;
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

    public static class AsyncQueryEstados extends AsyncQueryHandler {
        private WeakReference<AsyncOpListener> mListener;

        public interface AsyncOpListener {
            void onQueryComplete(int token, Object cookie, Cursor cursor);
        }

        public AsyncQueryEstados(ContentResolver cr) {
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
