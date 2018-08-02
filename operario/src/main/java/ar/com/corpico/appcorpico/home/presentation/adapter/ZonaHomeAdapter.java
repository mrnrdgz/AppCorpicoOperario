package ar.com.corpico.appcorpico.home.presentation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.home.data.Zonas_Home;

public class ZonaHomeAdapter extends RecyclerView.Adapter<ZonaHomeAdapter.ViewHolder> {
    private final Context mContext;
    private List<Zonas_Home> mZonaHome;
    private Zonas_Home zonaHome;

    public ZonaHomeAdapter(Context context, List<Zonas_Home> zonasHome) {
        mContext = context;
        mZonaHome = zonasHome;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_zonas_home, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        zonaHome = mZonaHome.get(position);
        // Asignación UI
        holder.viewZona.setText(zonaHome.getZona());
        holder.viewTotal.setText(String.valueOf(zonaHome.getDe()));

    }

    @Override
    public int getItemCount() {
        return mZonaHome.size();
    }

    public void addMoreZonas(List<Zonas_Home> zonasHome) {
        mZonaHome.clear();
        mZonaHome.addAll(zonasHome);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        // Referencias UI
        public TextView viewZona;
        public TextView viewTotal;

        public ViewHolder(View v) {
            super(v);
            viewZona = v.findViewById(R.id.zonasHome_text);
            viewTotal = v.findViewById(R.id.total_zona_text);


        }

    }
}
