package com.jje.programacion.escuela.listener;

import android.view.View;

/**
 * @author danielme.com
 */
public interface RecyclerViewOnItemClickListener{

    void onClick(View v, int position);
    boolean onLongClick(View v,int position);
}
