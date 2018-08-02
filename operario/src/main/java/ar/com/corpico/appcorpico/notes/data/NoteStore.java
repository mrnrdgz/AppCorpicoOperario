package ar.com.corpico.appcorpico.notes.data;

import java.util.List;

import ar.com.corpico.appcorpico.notes.domain.entity.Note;

/**
 * Created by Administrador on 12/03/2018.
 */

public interface NoteStore {
    void getNotes(GetNoteStoreCallBack callback,int servicio, int sector);
    interface GetNoteStoreCallBack {
        void onSuccess(List<Note> notas);
        void onError(String error);
    }
    void addNote(AddNoteStoreCallBack callback,String nota);
    interface AddNoteStoreCallBack {
        void onSuccess(String satisfaction);
        void onError(String error);
    }
    void editNote(EditNoteStoreCallBack callback,int id,String nota);
    interface EditNoteStoreCallBack {
        void onSuccess(String satisfaction);
        void onError(String error);
    }
}
