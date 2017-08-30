package com.jje.programacion.escuela.adapter;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.snowdream.android.widget.WebImage;
import com.jje.programacion.escuela.R;
import com.jje.programacion.escuela.listener.RecyclerViewOnItemClickListener;
import com.jje.programacion.escuela.modelo.Maestro;
import com.jje.programacion.escuela.modelo.Item;
import com.jje.programacion.escuela.utilerias.Config;
import com.jje.programacion.escuela.utilerias.Log;
import com.jje.programacion.escuela.viewholder.MaestroViewHolder;
import com.jje.programacion.escuela.viewholder.FooterViewHolder;
import java.util.List;

import static com.jje.programacion.escuela.utilerias.Config.redondearBitmapFoto;

public class MaestroAdapter extends RecyclerView.Adapter {

    private List<Item> maestroLista;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
    private static final int TIPO_MAESTRO = 0;
    private static final int TIPO_FOOTER = 1;


    public MaestroAdapter(List<Item> lista, RecyclerViewOnItemClickListener recyclerViewOnItemClickListener){
        this.maestroLista = lista;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (maestroLista.get(position) instanceof Maestro) {
            return TIPO_MAESTRO;
        } else if (maestroLista.get(position) instanceof Item) {
            return TIPO_FOOTER;
        } else {
            throw new RuntimeException("ItemViewType unknown");
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TIPO_MAESTRO) {
            return new MaestroViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maestro, parent, false),recyclerViewOnItemClickListener);
        } else {
            return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try{
            Log.i(maestroLista.toString());
            if (holder instanceof MaestroViewHolder) {
                Maestro maestro = (Maestro) maestroLista.get(position);
                MaestroViewHolder maestroViewHolder = (MaestroViewHolder) holder;
                maestroViewHolder.getTvMaestroId().setText("Id: "+maestro.getMaestroId());
                maestroViewHolder.getTvMaestroNombre().setText("Nombre: "+maestro.getNombre()+" "+maestro.getApellidoPaterno()+" "+maestro.getApellidoMaterno());

                try{
                    Bitmap imagen = new WebImage(Config.url_fotos+maestro.getFoto()).getBitmap(maestroViewHolder.getIvFoto().getContext());
                    if(imagen!=null){
                        imagen = redondearBitmapFoto(imagen,1000);
                        maestroViewHolder.getIvFoto().setImageBitmap(imagen);
                    }else{
                        maestroViewHolder.getIvFoto().setImageUrl(Config.url_fotos+maestro.getFoto(),new Rect());
                    }
                }catch(Exception e){
                    Log.e("Error imagen-->"+e);
                }

            }
        }catch(Exception e){
            Log.e(e.toString());
        }
    }

    @Override
    public int getItemCount() {
        return maestroLista.size();
    }
}
