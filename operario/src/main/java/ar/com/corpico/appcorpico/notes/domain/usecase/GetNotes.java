package ar.com.corpico.appcorpico.notes.domain.usecase;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.notes.data.INotesRepository;
import ar.com.corpico.appcorpico.notes.domain.entity.Note;

import static com.google.common.base.Preconditions.checkNotNull;

public class GetNotes extends UseCase<GetNotes.RequestValues, GetNotes.ResponseValue> {
    private INotesRepository mNotesRepository;

    public GetNotes(INotesRepository notessRepository) {
        mNotesRepository = notessRepository;
    }

    @Override
    public void executeUseCase(GetNotes.RequestValues requestValues) {

        INotesRepository.NotesRepositoryCallBack findCallback = new INotesRepository.NotesRepositoryCallBack() {
            @Override
            public void onSuccess(List<Note> note) {
                ResponseValue responseValue = new ResponseValue(note);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onError(String error) {
                getUseCaseCallback().onError(error);
            }
        };


        mNotesRepository.query(findCallback, requestValues.getServicio(),requestValues.getSector());
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private int servicio;
        private int sector;

        public RequestValues() {
        }

        public RequestValues(int servicio,int sector){
            this.servicio = servicio;
            this.sector = sector;
        }

        public int getServicio() {
            return servicio;
        }

        public int getSector() {
            return sector;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final List<Note> notes;

        public ResponseValue(List<Note> notes) {
            this.notes = checkNotNull(notes, "La lista de notas no puede ser null");
        }

        public List<Note> getNotes() {
            return notes;
        }
    }

}
