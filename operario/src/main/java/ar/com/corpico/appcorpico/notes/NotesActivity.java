package ar.com.corpico.appcorpico.notes;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import ar.com.corpico.appcorpico.BaseActivity;
import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.UseCaseHandler;
import ar.com.corpico.appcorpico.login.data.SessionsPrefsStore;
import ar.com.corpico.appcorpico.notes.data.NoteRestStore;
import ar.com.corpico.appcorpico.notes.data.NoteSqliteStore;
import ar.com.corpico.appcorpico.notes.data.NotesRepository;
import ar.com.corpico.appcorpico.notes.domain.usecase.AddNotes;
import ar.com.corpico.appcorpico.notes.domain.usecase.EditNotes;
import ar.com.corpico.appcorpico.notes.domain.usecase.GetNotes;
import ar.com.corpico.appcorpico.notes.presentation.NotesListFragment;
import ar.com.corpico.appcorpico.notes.presentation.NotesPresenter;
import ar.com.corpico.appcorpico.notes.presentation.dialog.AddNoteDialog;
import ar.com.corpico.appcorpico.notes.presentation.dialog.EditNoteDialog;

public class NotesActivity extends BaseActivity implements AddNoteDialog.OnAddNoteListener,
        EditNoteDialog.OnEditNoteListener,
        NotesListFragment.OnShowEditListener{
    // Claves de argumentos
    public static final String ARG_SERVICIO = "cuadrilla.servicio";
    public static final String ARG_SECTOR = "cuadrilla.sector";

    // Argumentos
    private int mServicioArg;
    private int mSectorArg;


    // Dependencias
    private NotesListFragment mNotesListView;
    private GetNotes mGetNotes;
    private AddNotes mAddNotes;
    private EditNotes mEditNotes;
    private final UseCaseHandler mUseCaseHandler = UseCaseHandler.getInstance();
    private NotesPresenter mNotesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_list_act);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Obtener argumentos
        Bundle args = getIntent().getExtras();
        if (args != null) {
            mServicioArg = args.getInt(ARG_SERVICIO);
            mSectorArg = args.getInt(ARG_SECTOR);
        }

        // Fuentes de datos y repositorios
        SessionsPrefsStore prefStore = SessionsPrefsStore.get(
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE));
        NotesRepository notesRepository = NotesRepository.getInstance(
                NoteRestStore.getInstance(),
                NoteSqliteStore.getInstance(getContentResolver(),prefStore),
                prefStore);

        // FloatingButton
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                DialogFragment newFragment = AddNoteDialog.newInstance();
                newFragment.show(ft, "AddNoteDialog");

            }
        });

        // View
        initListFragment();

        // Casos de uso
        mGetNotes = new GetNotes(notesRepository);
        mAddNotes = new AddNotes(notesRepository);

        // Presentadores
        mNotesPresenter = new NotesPresenter(mNotesListView, mGetNotes,mAddNotes, mUseCaseHandler);


    }
    protected NavDrawerItemMenu getDrawerItem() {
        return NavDrawerItemMenu.NOTES;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search_notes:

                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();

      /*  // Se cargan las notas
        if(mNotesListView != null){
            mNotesPresenter.loadNotes(mServicioArg,mSectorArg);
        }*/
    }

    private void initListFragment() {
        mNotesListView = (NotesListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.notes_view_container);


        if (mNotesListView == null) {
            mNotesListView = NotesListFragment.newInstance(mServicioArg, mSectorArg);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.notes_view_container, mNotesListView, "NotesView")
                    .commit();
        }


    }


    @Override
    public void onPossitiveButtonAddNoteClick(String nota) {
        mNotesListView.addNotes(nota);
    }

    @Override
    public void onNegativeButtonAddNoteClick() {
        Toast.makeText(this, "Botón CANCELAR", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowEdit(int id,String nota) {
        //Todo llamar al dialog para editar
       // Toast.makeText(this, "Botón EDITAR ID= " + id, Toast.LENGTH_SHORT).show();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        DialogFragment newFragment = EditNoteDialog.newInstance(id,nota);
        newFragment.show(ft, "EditNoteDialog");

    }

    @Override
    public void onShowDelete(int id) {
        //Todo llamar al dialog para borrar
        Toast.makeText(this, "Botón BORRAR ID= " + id, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPossitiveButtonEditNoteClick(int id, String nota) {

    }

    @Override
    public void onNegativeButtonEditNoteClick() {

    }
}
