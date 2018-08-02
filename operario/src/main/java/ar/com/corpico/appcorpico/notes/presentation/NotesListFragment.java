package ar.com.corpico.appcorpico.notes.presentation;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.notes.domain.entity.Note;
import ar.com.corpico.appcorpico.notes.presentation.adapter.NotesAdapter;

public class NotesListFragment extends Fragment implements View {
    public interface OnShowEditListener {
        void onShowEdit(int id,String nota);// Eventos Botón Positivo
        void onShowDelete(int id);
    }

    OnShowEditListener listener;
    // Keys de argumentos
    public static final String ARG_SERVICIO = "notes.servicio";
    public static final String ARG_SECTOR = "notes.sector";
    // Dependencias
    private NotesPresenter mNotesPresenter;

    // Views
    private RecyclerView mNotasList;
    private LinearLayoutManager linearLayoutManager;
    private NotesAdapter mNotesAdapter;
    private TextView mEmptyView;
    private android.view.View mProgressView;

    // Argumentos
    private int mServicio;
    private int mSector;

    public NotesListFragment() {
    }

    public static NotesListFragment newInstance(int servicio, int sector) {
        NotesListFragment fragment = new NotesListFragment();
        Bundle args = new Bundle();

        args.putInt(ARG_SERVICIO, servicio);
        args.putInt(ARG_SECTOR, sector);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();

        if (arguments != null) {
            // Toman parámetros
            mServicio = arguments.getInt(ARG_SERVICIO);
            mSector = arguments.getInt(ARG_SECTOR);
        }
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        android.view.View root = inflater.inflate(R.layout.notes_list_frag, container, false);

        // Obtención de referencias UI
        mNotasList = root.findViewById(R.id.rv_notes);
        //mEmptyView = root.findViewById(R.id.cuadrillas_empty);
        //mProgressView = root.findViewById(R.id.cuadrillas_progress);

        linearLayoutManager = new LinearLayoutManager(getContext());
        mNotasList.setLayoutManager(linearLayoutManager);

        mNotesAdapter = new NotesAdapter(getContext(), new ArrayList<Note>(0));

        mNotasList.setAdapter(mNotesAdapter);
        mNotesAdapter.setOnItemClickListener(new NotesAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(ImageView edit, int position, int id,String nota) {
                listener.onShowEdit(id,nota);
            }

            @Override
            public void onDeleteClick(ImageView delete, int position, int id) {
                listener.onShowDelete(id);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mNotesPresenter.loadNotes(mServicio,mSector);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        mNotesPresenter = (NotesPresenter) Preconditions.checkNotNull(presenter, "mNotesPresenter no puede ser null");
    }

    @Override
    public void showNotasList(List<Note> notas) {
        mNotesAdapter.addMoreNotes(notas);
    }

    @Override
    public void showListNewsEmpty() {

    }

    @Override
    public void showNotasError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void addNotes(String nota) {
        mNotesPresenter.addNotes(nota);
    }

    @Override
    public void showAddNoteSuccesfullMessage(String mensaje) {
        Toast.makeText(getActivity(), mensaje, Toast.LENGTH_LONG)
                .show();
        //todo: esta bien llamar esto aca? xq no llama al release
        mNotesPresenter.loadNotes(mServicio,mSector);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnShowEditListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó OnSimpleDialogListener");

        }
    }

}
