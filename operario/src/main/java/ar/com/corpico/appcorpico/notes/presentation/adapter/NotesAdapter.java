package ar.com.corpico.appcorpico.notes.presentation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.notes.domain.entity.Note;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private final Context mContext;
    private List<Note> mNotes;
    private Note note;

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnItemClickListener{
        void onEditClick(ImageView edit, int position, int id,String nota);
        void onDeleteClick(ImageView delete, int position, int id);
    }


    public NotesAdapter(Context context, List<Note> notes) {
        mContext = context;
        mNotes = notes;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_notes, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        note = mNotes.get(position);
        // Asignación UI
        holder.viewOperario.setText(note.getOperario());
        holder.viewFecha.setText(note.getFecha());
        holder.viewNota.setText(note.getDescripcion());
        if(note.getEditable()==1){
            holder.viewEdit.setVisibility(View.VISIBLE);
            holder.viewDelete.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public void addMoreNotes(List<Note> notes) {
        mNotes.clear();
        mNotes.addAll(notes);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        // Referencias UI
        public TextView viewOperario;
        public TextView viewFecha;
        public TextView viewNota;
        public ImageView viewEdit;
        public ImageView viewDelete;

        public ViewHolder(View v) {
            super(v);
            viewOperario = v.findViewById(R.id.operario_note);
            viewFecha = v.findViewById(R.id.fecha_note);
            viewNota = v.findViewById(R.id.note);
            viewEdit = v.findViewById(R.id.edit_nota);
            viewDelete = v.findViewById(R.id.delete_nota);

            viewEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null) {
                        int adapterPosition = getAdapterPosition();
                        Note n = mNotes.get(adapterPosition);
                        if(RecyclerView.NO_POSITION!=adapterPosition) {
                            mListener.onEditClick(viewEdit, adapterPosition,n.getId(),n.getDescripcion());
                        }
                    }

                }});
            viewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null) {
                        int adapterPosition = getAdapterPosition();
                        Note n = mNotes.get(adapterPosition);
                        if(RecyclerView.NO_POSITION!=adapterPosition) {
                            mListener.onDeleteClick(viewEdit, adapterPosition,n.getId());
                        }
                    }
                }});

        }

    }
}
