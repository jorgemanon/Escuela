package com.jje.programacion.escuela;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.jje.programacion.escuela.utilerias.Alumno;
import com.jje.programacion.escuela.utilerias.AlumnoAdapter;
import com.jje.programacion.escuela.utilerias.Log;
import com.jje.programacion.escuela.utilerias.RecyclerTouchListener;
import com.jje.programacion.escuela.utilerias.SpinnerUtileria;

import java.util.ArrayList;
import java.util.List;

public class ColapseActivity extends AppCompatActivity {


    private Spinner sCarrera,sSemestre,sGrupo;
    private RecyclerView recyclerView;
    private AlumnoAdapter mAdapter;
    private List<Alumno> alumnoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colapse);
        sCarrera = (Spinner) findViewById(R.id.sCarrera1);
        sSemestre = (Spinner) findViewById(R.id.sSemestre1);
        sGrupo = (Spinner) findViewById(R.id.sGrupo1);

        String[] columnas = new String[]{"_id","nombre"};
        List id = new ArrayList();
        id.add(1);
        id.add(2);
        id.add(3);
        id.add(4);

        List nombre = new ArrayList();
        nombre.add("Ingenieria en Sistemas Computacionales");
        nombre.add("Ingenieria Civil");
        nombre.add("Ingenieria en Informatica");
        nombre.add("Contabilidad");

        SpinnerUtileria.spinner(getApplicationContext(),sCarrera,columnas,id,nombre,0);
        SpinnerUtileria.spinner(getApplicationContext(),sSemestre,columnas,id,nombre,0);
        SpinnerUtileria.spinner(getApplicationContext(),sGrupo,columnas,id,nombre,0);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerAlumno1);

        mAdapter = new AlumnoAdapter(alumnoList);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Alumno alumno = alumnoList.get(position);
                Toast.makeText(view.getContext(), alumno.getNombre() + " es seleccionado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        prepareMovieData();
    }

    private void prepareMovieData() {
        alumnoList.add(new Alumno("Jorge", "ISC", "7 Semestre",R.drawable.menu_maestro));
        alumnoList.add(new Alumno("Armando", "II", "8 Semestre",R.drawable.menu_maestro));
        mAdapter.notifyDataSetChanged();
    }

}
