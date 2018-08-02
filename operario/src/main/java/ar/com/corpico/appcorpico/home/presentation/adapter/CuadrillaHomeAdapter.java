package ar.com.corpico.appcorpico.home.presentation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.home.data.Cuadrilla_Home;

public class CuadrillaHomeAdapter extends RecyclerView.Adapter<CuadrillaHomeAdapter.ViewHolder> {
    private final Context mContext;
    private List<Cuadrilla_Home> mCuadrillaHome;
    private Cuadrilla_Home cuadrillaHome;

    public CuadrillaHomeAdapter(Context context, List<Cuadrilla_Home> cuadrillasHome) {
        mContext = context;
        mCuadrillaHome = cuadrillasHome;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_cuadrilla_home, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cuadrillaHome = mCuadrillaHome.get(position);
        // Asignación UI
        holder.viewCuadrilla.setText(cuadrillaHome.getCuadrilla());
        holder.viewTotal.setText(String.valueOf(cuadrillaHome.getDe()));

    }

    @Override
    public int getItemCount() {
        return mCuadrillaHome.size();
    }

    public void addMoreCuadrillas(List<Cuadrilla_Home> cuadrillasHome) {
        mCuadrillaHome.clear();
        mCuadrillaHome.addAll(cuadrillasHome);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        // Referencias UI
        public TextView viewCuadrilla;
        public TextView viewTotal;

        public ViewHolder(View v) {
            super(v);
            viewCuadrilla = v.findViewById(R.id.cuadrillaHome_text);
            viewTotal = v.findViewById(R.id.total_cuadrilla_text);


        }

    }
}
