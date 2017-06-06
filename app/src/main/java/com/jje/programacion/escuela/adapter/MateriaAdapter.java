package com.jje.programacion.escuela.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.jje.programacion.escuela.R;
import com.jje.programacion.escuela.listener.RecyclerViewOnItemClickListener;
import com.jje.programacion.escuela.modelo.Item;
import com.jje.programacion.escuela.modelo.Materia;
import com.jje.programacion.escuela.utilerias.Log;
import com.jje.programacion.escuela.viewholder.FooterViewHolder;
import com.jje.programacion.escuela.viewholder.MateriaViewHolder;

import java.util.List;

public class MateriaAdapter extends RecyclerView.Adapter {

    private List<Item> materiaList;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
    private static final int TIPO_MATERIA = 0;
    private static final int TIPO_FOOTER = 1;

    public MateriaAdapter(@NonNull List<Item> materiaList, RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
        this.materiaList = materiaList;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (materiaList.get(position) instanceof Materia) {
            return TIPO_MATERIA;
        } else if (materiaList.get(position) instanceof Item) {
            return TIPO_FOOTER;
        } else {
            throw new RuntimeException("ItemViewType unknown");
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TIPO_MATERIA) {
            return new MateriaViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_materia, parent, false),recyclerViewOnItemClickListener);
        } else {
            return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try{
            if (holder instanceof MateriaViewHolder) {
                Materia materia = (Materia) materiaList.get(position);
                MateriaViewHolder materiaViewHolder = (MateriaViewHolder) holder;
                materiaViewHolder.getTvMateria().setText("Materia: "+materia.getMateriaNombre());
                materiaViewHolder.getTvMaestro().setText("Maestro: "+materia.getMaestroNombre());
                materiaViewHolder.getTvHorario().setText("Horario: "+materia.getHorario());
                materiaViewHolder.getTvAula().setText("Grupo: "+materia.getAulaNombre());
                materiaViewHolder.getTvCalificacion().setText(""+materia.getCalificacion());
            }
        }catch(Exception e){
            Log.e(e.toString());
        }
    }

    @Override
    public int getItemCount() {return materiaList.size();}
}
