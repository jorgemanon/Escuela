package com.jje.programacion.escuela.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.github.snowdream.android.widget.SmartImageView;
import com.jje.programacion.escuela.R;
import com.jje.programacion.escuela.listener.RecyclerViewOnItemClickListener;

public class MaestroViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
    private TextView tvMaestroNombre, tvMaestroId;
    private SmartImageView ivMaestroFoto;

    public MaestroViewHolder(View view, RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
        super(view);
        tvMaestroId = (TextView) view.findViewById(R.id.tvMaestroId);
        tvMaestroNombre = (TextView) view.findViewById(R.id.tvMaestroNombre);
        ivMaestroFoto = (SmartImageView) view.findViewById(R.id.ivMaestroFoto);
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public TextView getTvMaestroNombre() {return tvMaestroNombre;}
    public TextView getTvMaestroId() {return tvMaestroId;}
    public SmartImageView getIvFoto() {return ivMaestroFoto;}

    @Override
    public void onClick(View v) {
        recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
    }

    @Override
    public boolean onLongClick(View v) {
        return recyclerViewOnItemClickListener.onLongClick(v, getAdapterPosition());
    }
}
