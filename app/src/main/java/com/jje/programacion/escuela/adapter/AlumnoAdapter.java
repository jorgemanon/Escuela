package com.jje.programacion.escuela.adapter;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jje.programacion.escuela.R;
import com.jje.programacion.escuela.modelo.Alumno;
import com.jje.programacion.escuela.utilerias.Config;
import com.jje.programacion.escuela.utilerias.Log;
import com.jje.programacion.escuela.viewholder.AlumnoViewHolder;
import com.jje.programacion.escuela.viewholder.FooterViewHolder;
import com.jje.programacion.escuela.modelo.Item;
import com.jje.programacion.escuela.listener.RecyclerViewOnItemClickListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

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

                Bitmap originalBitmap = getImagen("http://localhost/fotos/1.png");
                RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(alumnoViewHolder.getIvFoto().getResources(), originalBitmap);
                roundedDrawable.setCornerRadius(originalBitmap.getHeight());
                alumnoViewHolder.getIvFoto().setImageDrawable(roundedDrawable);
            }
        }catch(Exception e){
            Log.e("jma",e.toString());
        }

    }

    private Bitmap getImagen(String url) {
        Bitmap bm = null;
        try {
            URL _url = new URL(url);
            URLConnection con = _url.openConnection();
            con.connect();
            InputStream is = con.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e("jma","Error: "+e.getMessage());
        }
        return bm;
    }

    @Override
    public int getItemCount() {
        return alumnoLista.size();
    }
}
