package ar.com.corpico.appcorpico.home.presentation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.home.data.Estados_Home;

public class EstadoHomeAdapter extends RecyclerView.Adapter<EstadoHomeAdapter.ViewHolder> {
    private final Context mContext;
    private List<Estados_Home> mEstadosHome;
    private Estados_Home estadoHome;

    public EstadoHomeAdapter(Context context, List<Estados_Home> estadosHome) {
        mContext = context;
        mEstadosHome = estadosHome;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_estados_home, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        estadoHome = mEstadosHome.get(position);
        // Asignación UI
        holder.viewEstado.setText(estadoHome.getEstado());
        holder.viewTotal.setText(String.valueOf(estadoHome.getDe()));

    }

    @Override
    public int getItemCount() {
        return mEstadosHome.size();
    }

    public void addMoreEstados(List<Estados_Home> estadosHome) {
        mEstadosHome.clear();
        mEstadosHome.addAll(estadosHome);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        // Referencias UI
        public TextView viewEstado;
        public TextView viewTotal;

        public ViewHolder(View v) {
            super(v);
            viewEstado = v.findViewById(R.id.estadosHome_text);
            viewTotal = v.findViewById(R.id.total_estados_text);

        }

    }
}
