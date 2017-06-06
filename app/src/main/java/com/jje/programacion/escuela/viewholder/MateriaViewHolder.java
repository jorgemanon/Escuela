package com.jje.programacion.escuela.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.jje.programacion.escuela.R;
import com.jje.programacion.escuela.listener.RecyclerViewOnItemClickListener;

public class MateriaViewHolder extends RecyclerView.ViewHolder implements OnClickListener{

    /********** DECLARACION DE VARIABLES PARA COMPONENTES ***********/
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
    private TextView tvMateria, tvMaestro, tvHorario, tvAula, tvCalificacion;

    public MateriaViewHolder(View view, RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
        super(view);
        tvMateria = (TextView) view.findViewById(R.id.tvMateria);
        tvMaestro = (TextView) view.findViewById(R.id.tvMaestro);
        tvHorario = (TextView) view.findViewById(R.id.tvHorario);
        tvAula = (TextView) view.findViewById(R.id.tvAula);
        tvCalificacion = (TextView) view.findViewById(R.id.tvCalificacion);
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
        itemView.setOnClickListener(this);
    }

    public TextView getTvMateria() {return tvMateria;}
    public TextView getTvMaestro() {return tvMaestro;}
    public TextView getTvHorario() {return tvHorario;}
    public TextView getTvAula() {return tvAula;}
    public TextView getTvCalificacion() {return tvCalificacion;}

    @Override
    public void onClick(View v) {
        recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
    }
}
