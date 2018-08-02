package ar.com.corpico.appcorpico.home.presentation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.home.data.TiposTrabajo_Home;

public class TiposTrabajoHomeAdapter extends RecyclerView.Adapter<TiposTrabajoHomeAdapter.ViewHolder> {
    private final Context mContext;
    private List<TiposTrabajo_Home> mTipoTrabajoHome;
    private TiposTrabajo_Home tipoTrabajoHome;

    public TiposTrabajoHomeAdapter(Context context, List<TiposTrabajo_Home> tiposTrabajoHome) {
        mContext = context;
        mTipoTrabajoHome = tiposTrabajoHome;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_tipos_trabajos_home, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        tipoTrabajoHome = mTipoTrabajoHome.get(position);
        // Asignación UI
        holder.viewTipoTrabajo.setText(tipoTrabajoHome.getTipoTrabajo());
        holder.viewTotal.setText(String.valueOf(tipoTrabajoHome.getDe()));

    }

    @Override
    public int getItemCount() {
        return mTipoTrabajoHome.size();
    }

    public void addMoreTiposTrabajo(List<TiposTrabajo_Home> tiposTrabajoHome) {
        mTipoTrabajoHome.clear();
        mTipoTrabajoHome.addAll(tiposTrabajoHome);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        // Referencias UI
        public TextView viewTipoTrabajo;
        public TextView viewTotal;

        public ViewHolder(View v) {
            super(v);
            viewTipoTrabajo = v.findViewById(R.id.tipoTrabajoHome_text);
            viewTotal = v.findViewById(R.id.total_tipoTrabajo_text);


        }

    }
}
