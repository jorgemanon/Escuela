package com.jje.programacion.escuela.utilerias;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.jje.programacion.escuela.R;

public class FooterViewHolder extends RecyclerView.ViewHolder{
    private ProgressBar progressBar;

    public FooterViewHolder(View view){
        super(view);
        progressBar = (ProgressBar) view.findViewById(R.id.footer);
    }
}
