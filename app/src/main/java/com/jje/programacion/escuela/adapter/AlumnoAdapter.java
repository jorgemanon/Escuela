package com.jje.programacion.escuela.adapter;


import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jje.programacion.escuela.R;
import com.jje.programacion.escuela.listener.RecyclerViewOnItemClickListener;
import com.jje.programacion.escuela.modelo.Alumno;
import com.jje.programacion.escuela.modelo.Item;
import com.jje.programacion.escuela.utilerias.Log;
import com.jje.programacion.escuela.viewholder.AlumnoViewHolder;
import com.jje.programacion.escuela.viewholder.FooterViewHolder;

import java.util.List;

import static com.jje.programacion.escuela.R.drawable.uni;

public class AlumnoAdapter extends RecyclerView.Adapter {

    private List<Item> alumnoLista;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
    private static final int TIPO_ALUMNO = 0;
    private static final int TIPO_FOOTER = 1;

    public AlumnoAdapter(@NonNull List<Item> alumnoLista, RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
        this.alumnoLista = alumnoLista;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (alumnoLista.get(position) instanceof Alumno) {
            return TIPO_ALUMNO;
        } else if (alumnoLista.get(position) instanceof Item) {
            return TIPO_FOOTER;
        } else {
            throw new RuntimeException("ItemViewType unknown");
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TIPO_ALUMNO) {
            return new AlumnoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alumno, parent, false),recyclerViewOnItemClickListener);
        } else {
            return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try{
            if (holder instanceof AlumnoViewHolder) {
                Alumno alumno = (Alumno) alumnoLista.get(position);
                AlumnoViewHolder alumnoViewHolder = (AlumnoViewHolder) holder;
                alumnoViewHolder.getTvId().setText("Id: "+alumno.getAlumnoId());
                alumnoViewHolder.getTvNombre().setText("Nombre: "+alumno.getNombre()+" "+alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno());
                alumnoViewHolder.getTvCarrera().setText("Carrera:"+alumno.getCarrera());
                alumnoViewHolder.getTvSemestre().setText("Semestre:"+alumno.getSemestre());

                String url = "http://192.168.2.1/fotos/"+alumno.getFoto();
                alumnoViewHolder.getIvFoto().setImageUrl(
                        url,
                        new Rect(
                                alumnoViewHolder.getIvFoto().getLeft(),
                                alumnoViewHolder.getIvFoto().getTop(),
                                alumnoViewHolder.getIvFoto().getRight(),
                                alumnoViewHolder.getIvFoto().getBottom()
                        )
                );
            }
        }catch(Exception e){
            Log.e("jma",e.toString());
        }
    }

    @Override
    public int getItemCount() {
        return alumnoLista.size();
    }
}
