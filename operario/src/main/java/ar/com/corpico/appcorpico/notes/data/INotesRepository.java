package ar.com.corpico.appcorpico.notes.data;

import android.content.ContentProviderOperation;

import java.util.List;

import ar.com.corpico.appcorpico.notes.data.entity.RestNotes;
import ar.com.corpico.appcorpico.notes.domain.entity.Note;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface INotesRepository {
    void query(NotesRepositoryCallBack callback, int servicio, int sector);
    interface NotesRepositoryCallBack {
        void onSuccess(List<Note> notes);
        void onError(String error);
    }
    void add(AddNotesRepositoryCallBack callback, String nota);
    interface AddNotesRepositoryCallBack {
        void onSuccess(String satisfaccion);
        void onError(String error);
    }
    void edit(EditNotesRepositoryCallBack callback,int id, String nota);
    interface EditNotesRepositoryCallBack {
        void onSuccess(String satisfaccion);
        void onError(String error);
    }
    List<RestNotes> fetchDataNotesIfNewer();
    List<ContentProviderOperation>providerOperations(List<RestNotes> fetchedNotes);
}
