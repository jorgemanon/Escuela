package com.jje.programacion.escuela.utilerias;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jje.programacion.escuela.R;

public class AlumnoViewHolder extends RecyclerView.ViewHolder {

    private TextView tvNombre, tvCarrera, tvSemestre;
    private ImageView ivFoto;

    public AlumnoViewHolder(View view) {
        super(view);
        tvNombre = (TextView) view.findViewById(R.id.tvNombre);
        tvCarrera = (TextView) view.findViewById(R.id.tvCarrera);
        tvSemestre = (TextView) view.findViewById(R.id.tvSemestre);
        ivFoto = (ImageView) view.findViewById(R.id.ivFoto);
    }

    public TextView getTvNombre() {
        return tvNombre;
    }

    public void setTvNombre(TextView tvNombre) {
        this.tvNombre = tvNombre;
    }

    public TextView getTvCarrera() {
        return tvCarrera;
    }

    public void setTvCarrera(TextView tvCarrera) {
        this.tvCarrera = tvCarrera;
    }

    public TextView getTvSemestre() {
        return tvSemestre;
    }

    public void setTvSemestre(TextView tvSemestre) {
        this.tvSemestre = tvSemestre;
    }

    public ImageView getIvFoto() {
        return ivFoto;
    }

    public void setIvFoto(ImageView ivFoto) {
        this.ivFoto = ivFoto;
    }
}
