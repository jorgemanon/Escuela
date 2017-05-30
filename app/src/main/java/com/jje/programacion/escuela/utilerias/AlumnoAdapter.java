package com.jje.programacion.escuela.utilerias;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jje.programacion.escuela.R;

import java.util.List;

public class AlumnoAdapter extends RecyclerView.Adapter {
    private List<Item> alumnoLista;

    private static final int TYPE_COLOR = 0;
    private static final int TYPE_FOOTER = 1;

    public AlumnoAdapter(@NonNull List<Item> alumnoLista) {
        this.alumnoLista = alumnoLista;
    }

    @Override
    public int getItemViewType(int position) {
        if (alumnoLista.get(position) instanceof Alumno) {
            return TYPE_COLOR;
        } else if (alumnoLista.get(position) instanceof Item) {
            return TYPE_FOOTER;
        } else {
            throw new RuntimeException("ItemViewType unknown");
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_COLOR) {
            return new AlumnoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alumno, parent, false));
        } else {
            return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AlumnoViewHolder) {
            Alumno alumno = (Alumno) alumnoLista.get(position);
            AlumnoViewHolder alumnoViewHolder = (AlumnoViewHolder) holder;
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
