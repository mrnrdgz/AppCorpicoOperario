package ar.com.corpico.appcorpico.notes.presentation;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.UseCaseHandler;
import ar.com.corpico.appcorpico.notes.domain.entity.Note;
import ar.com.corpico.appcorpico.notes.domain.usecase.AddNotes;
import ar.com.corpico.appcorpico.notes.domain.usecase.GetNotes;

import static com.google.common.base.Preconditions.checkNotNull;

public class NotesPresenter implements Presenter {

    private ar.com.corpico.appcorpico.notes.presentation.View mView;
    private GetNotes mGetNotes;
    private AddNotes mAddNotes;
    private final UseCaseHandler mUseCaseHandler;

    public NotesPresenter(ar.com.corpico.appcorpico.notes.presentation.View mView,
                          GetNotes mGetNotes,AddNotes mAddNotes,
                          UseCaseHandler mUseCaseHandler) {
        this.mView = checkNotNull(mView, "View no puede ser null");
        this.mGetNotes = checkNotNull(mGetNotes,"GetNotes no puede ser null");
        this.mAddNotes = checkNotNull(mAddNotes,"mAddNotes no puede ser null");
        this.mUseCaseHandler = checkNotNull(mUseCaseHandler,"UseCaseHandler no puede ser null");

        mView.setPresenter(this);
    }


    @Override
    public void loadNotes(int servicio, int sector) {
        GetNotes.RequestValues requestValues = new GetNotes.RequestValues(servicio, sector);

        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback<GetNotes.ResponseValue>() {
            @Override
            public void onSuccess(GetNotes.ResponseValue response) {
                List<Note> nota = response.getNotes();
                if (nota.size() >= 1) {
                    // Mostrar la lista en la vista
                    mView.showNotasList(nota);
                } else {
                    // Mostrar estado vacío
                   /* mView.showNotasList(nota);
                    mView.showNotasEmpty();*/
                }
            }

            @Override
            public void onError(String error) {
                // Ocultar indicador de progreso
                //mView.showProgressIndicator(false);
                mView.showNotasError(error);
            }
        };

        mGetNotes.setUseCaseCallback(useCaseCallback);
        mUseCaseHandler.execute(mGetNotes, requestValues, useCaseCallback);
    }

    @Override
    public void addNotes(String nota) {
        AddNotes.RequestValues requestValues = new AddNotes.RequestValues(nota);

        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback<AddNotes.ResponseValue>() {
            @Override
            public void onSuccess(AddNotes.ResponseValue response) {
                String mensaje = response.getMensaje();
                if (mensaje != null) {
                    // Mostrar mensaje
                    mView.showAddNoteSuccesfullMessage(mensaje);
                    //mView.showNotasList(notas);
                }
            }

            @Override
            public void onError(String error) {
                // Ocultar indicador de progreso
                //mView.showProgressIndicator(false);
                mView.showNotasError(error);
            }
        };

        mAddNotes.setUseCaseCallback(useCaseCallback);
        mUseCaseHandler.execute(mAddNotes, requestValues, useCaseCallback);
    }
}
