package com.jje.programacion.escuela.utilerias;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jje.programacion.escuela.R;

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
        if (holder instanceof AlumnoViewHolder) {
            Alumno alumno = (Alumno) alumnoLista.get(position);
            AlumnoViewHolder alumnoViewHolder = (AlumnoViewHolder) holder;
            alumnoViewHolder.getTvNombre().setText("Id: "+alumno.getId());
            alumnoViewHolder.getTvNombre().setText("Nombre: "+alumno.getNombre());
            alumnoViewHolder.getTvCarrera().setText("Carrera:"+alumno.getCarrera());
            alumnoViewHolder.getTvSemestre().setText("Semestre:"+alumno.getSemestre());
            alumnoViewHolder.getIvFoto().setImageResource(Integer.parseInt(alumno.getFoto()));
        }
    }

    @Override
    public int getItemCount() {
        return alumnoLista.size();
    }
}
