package ar.com.corpico.appcorpico.notes.presentation;

import java.util.List;

import ar.com.corpico.appcorpico.notes.domain.entity.Note;

public interface View {
    void setPresenter(Presenter presenter);
    void showNotasList(List<Note> notas);
    void showListNewsEmpty();
    void showNotasError(String error);
    void addNotes(String nota);
    void showAddNoteSuccesfullMessage(String mensaje);
}
