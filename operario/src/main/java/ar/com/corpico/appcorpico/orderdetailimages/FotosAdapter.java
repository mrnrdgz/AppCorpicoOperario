package ar.com.corpico.appcorpico.orderdetailimages;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.data.ApiServiceOrders;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FotosAdapter extends RecyclerView.Adapter<FotosAdapter.ViewHolder> {
    private final Context mContext;
    private List<FotoOrden> mFotosOrden;
    private FotoOrden fotoOrden;

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnItemClickListener{
        void onPhotoClick(ImageView photo, int position, String url);
    }


    public FotosAdapter(Context context, List<FotoOrden> fotosOrden) {
        mContext = context;
        mFotosOrden = fotosOrden;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_foto, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        fotoOrden = mFotosOrden.get(position);
        // Asignación UI
        holder.viewObservacion.setText(fotoOrden.getObservacion());

        File file = new File(Environment.getExternalStorageDirectory ()
                + File.separator + + fotoOrden.getOrden() + "-" + fotoOrden.getNumero() +".jpg");
        if(file.exists()){
            setImageInImageView(holder.viewFoto,Environment.getExternalStorageDirectory ()
                    + File.separator + + fotoOrden.getOrden() + "-" + fotoOrden.getNumero() +".jpg");
        }else{
            getRetrofitImage(fotoOrden.getOrden(), fotoOrden.getNumero(),holder.viewFoto,Environment.getExternalStorageDirectory ()
                    + File.separator + fotoOrden.getOrden() + "-" + fotoOrden.getNumero() +".jpg");
        }
    }

    @Override
    public int getItemCount() {
        return mFotosOrden.size();
    }

    public void addMoreFotos(List<FotoOrden> fotosOrden) {
        mFotosOrden.clear();
        mFotosOrden.addAll(fotosOrden);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        // Referencias UI
        public TextView viewObservacion;
        public ImageView viewFoto;

        public ViewHolder(View v) {
            super(v);
            viewObservacion = v.findViewById(R.id.descripcion);
            viewFoto = v.findViewById(R.id.foto);


            viewFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null) {
                        int adapterPosition = getAdapterPosition();
                        FotoOrden f = mFotosOrden.get(adapterPosition);
                        if(RecyclerView.NO_POSITION!=adapterPosition) {
                            mListener.onPhotoClick(viewFoto, adapterPosition,
                                    Environment.getExternalStorageDirectory ()
                                            + File.separator + f.getOrden() + "-" + f.getNumero() +".jpg" );
                        }
                    }

                }});
        }

    }
    //private boolean DownloadImage(ResponseBody body) {
    private boolean DownloadImage(ResponseBody body,ImageView image,String path) {

        try {
            Log.d("DownloadImage","Reading and writing file");
            InputStream in = null;
            FileOutputStream out = null;

            try {
                in = body.byteStream();
                out = new FileOutputStream(path);
                int c;

                while ((c = in.read()) != -1) {
                    out.write(c);
                }
            }
            catch (IOException e) {
                Log.d("DownloadImage",e.toString());
                return false;
            }
            finally {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }

            setImageInImageView(image,path);

            return true;

        } catch (IOException e) {
            Log.d("DownloadImage",e.toString());
            return false;
        }
    }

    private void setImageInImageView(ImageView image,String path) {
        Glide.with(mContext)
                .load(path)
                .centerCrop()
                .placeholder(new ColorDrawable(Color.BLACK))
                .error(new ColorDrawable(Color.RED))
                .into(image);
    }

    void getRetrofitImage(int orden, int foto, final ImageView image, final String path) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiServiceOrders.URL_BASE)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServiceOrders service = retrofit.create(ApiServiceOrders.class);
        Call<ResponseBody> call = service.GetFotoOrden(orden,foto);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    Log.d("onResponse", "Response came from server");

                    boolean download = DownloadImage(response.body(),image,path);


                    Log.d("onResponse", "Image is downloaded and saved ? " + download );

                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }

        });
    }
}
