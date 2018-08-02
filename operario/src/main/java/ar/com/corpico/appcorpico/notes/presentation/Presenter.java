package ar.com.corpico.appcorpico.notes.presentation;

public interface Presenter {
    void loadNotes(int servicio,int sector);
    void addNotes(String nota);
}
