package com.jje.programacion.escuela.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.github.snowdream.android.widget.SmartImageView;
import com.jje.programacion.escuela.R;
import com.jje.programacion.escuela.listener.RecyclerViewOnItemClickListener;

public class AlumnoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
    private TextView tvNombre, tvCarrera, tvSemestre, tvId;
    private SmartImageView ivFoto;

    public AlumnoViewHolder(View view, RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
        super(view);
        tvId = (TextView) view.findViewById(R.id.tvId);
        tvNombre = (TextView) view.findViewById(R.id.tvNombre);
        tvCarrera = (TextView) view.findViewById(R.id.tvCarrera);
        tvSemestre = (TextView) view.findViewById(R.id.tvSemestre);
        ivFoto = (SmartImageView) view.findViewById(R.id.ivFoto);
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
        itemView.setOnClickListener(this);
    }

    public TextView getTvId() {return tvId;}
    public TextView getTvNombre() {return tvNombre;}
    public TextView getTvCarrera() {return tvCarrera;}
    public TextView getTvSemestre() {return tvSemestre;}
    public SmartImageView getIvFoto() {return ivFoto;}

    @Override
    public void onClick(View v) {
        recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
    }
}
