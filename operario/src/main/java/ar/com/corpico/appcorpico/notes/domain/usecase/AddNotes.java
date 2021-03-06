package ar.com.corpico.appcorpico.notes.domain.usecase;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.notes.data.INotesRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddNotes extends UseCase<AddNotes.RequestValues, AddNotes.ResponseValue> {
    private INotesRepository mNotesRepository;

    public AddNotes(INotesRepository notessRepository) {
        mNotesRepository = notessRepository;
    }

    @Override
    public void executeUseCase(AddNotes.RequestValues requestValues) {

        INotesRepository.AddNotesRepositoryCallBack addCallback = new INotesRepository.AddNotesRepositoryCallBack() {
            @Override
            public void onSuccess(String satisfaction) {
                ResponseValue responseValue = new ResponseValue(satisfaction);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onError(String error) {
                getUseCaseCallback().onError(error);
            }
        };


        mNotesRepository.add(addCallback, requestValues.getNota());
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private String nota;

        public RequestValues() {
        }

        public RequestValues(String nota){
            this.nota = nota;
        }

        public String getNota() {
            return nota;
        }

    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private final String satisfaction;

        public ResponseValue(String satisfaction) {
            this.satisfaction = checkNotNull(satisfaction, "La lista de notas no puede ser null");
        }

        public String getMensaje() {
            return satisfaction;
        }

        /*private final List<Note> notes;

        public ResponseValue(List<Note> notes) {
            this.notes = checkNotNull(notes, "La lista de notas no puede ser null");
        }

        public List<Note> getNotes() {
            return notes;
        }*/
    }

}
