package com.jje.programacion.escuela;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.jje.programacion.escuela.utilerias.Config;
import com.jje.programacion.escuela.modelo.Alumno;
import com.jje.programacion.escuela.adapter.AlumnoAdapter;
import com.jje.programacion.escuela.modelo.Footer;
import com.jje.programacion.escuela.modelo.Item;
import com.jje.programacion.escuela.listener.RecyclerViewOnItemClickListener;
import com.jje.programacion.escuela.utilerias.SpinnerUtileria;

import java.util.ArrayList;
import java.util.List;

public class FAlumnos extends Fragment {

    /**/

    /* RECYCLER REQUIRED */
    private Spinner sCarrera,sSemestre,sGrupo;
    private List<Item> alumnosList;
    private boolean hasMore;
    private AsyncTask asyncTask;
    private RecyclerView rvAlumno;
    /* RECYCLER REQUIRED */

    public FAlumnos() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Metodo para castear los componentes del layout en el fragmento
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        sCarrera = (Spinner) view.findViewById(R.id.sCarrera);
        sSemestre = (Spinner) view.findViewById(R.id.sSemestre);
        sGrupo = (Spinner) view.findViewById(R.id.sGrupo);
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

        SpinnerUtileria.spinner(view.getContext(),sCarrera,columnas,id,nombre,0);
        SpinnerUtileria.spinner(view.getContext(),sSemestre,columnas,id,nombre,0);
        SpinnerUtileria.spinner(view.getContext(),sGrupo,columnas,id,nombre,0);

        alumnosList = getAlumnos();
        hasMore = true;

        rvAlumno = (RecyclerView) view.findViewById(R.id.rvAlumno);
        rvAlumno.setAdapter(new AlumnoAdapter(alumnosList, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                if (alumnosList.get(position) instanceof Alumno) {
                    Toast.makeText(getContext(), "Hola "+((Alumno) alumnosList.get(position)).toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }));
        rvAlumno.setLayoutManager(new LinearLayoutManager(getContext()));
        rvAlumno.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView rvAlumno, int dx, int dy) {
                super.onScrolled(rvAlumno, dx, dy);
                if (hasMore && !(hasFooter())) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) rvAlumno.getLayoutManager();
                    if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 2) {
                        alumnosList.add(new Footer());
                        Handler handler = new Handler();
                        final Runnable r = new Runnable() {
                            public void run() {
                                FAlumnos.this.rvAlumno.getAdapter().notifyItemInserted(alumnosList.size() - 1);
                            }
                        };
                        handler.post(r);
                        asyncTask = new BackgroundTask();
                        asyncTask.execute((Object[]) null);
                    }
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.falumnos, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (asyncTask != null) {
            asyncTask.cancel(false);
        }
    }

    private class BackgroundTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(Config.TIEMPO_CARGA_FOOTER);
            } catch (InterruptedException e) {
                Log.e(this.getClass().toString(), e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            int size = alumnosList.size();
            alumnosList.remove(size - 1);//removes footer
            alumnosList.addAll(getAlumnos());
            rvAlumno.getAdapter().notifyItemRangeChanged(size - 1, alumnosList.size() - size);
        }

    }

    private boolean hasFooter() {
        return alumnosList.get(alumnosList.size() - 1) instanceof Footer;
    }

    private ArrayList<Item> getAlumnos() {
        ArrayList<Item> alumnosList = new ArrayList<>(13);
        alumnosList.add(new Alumno("1","Mario","Ingenieria Sistemas Computacionales","3",R.drawable.icono+""));
        alumnosList.add(new Alumno("2","Maria","Ingenieria Sistemas Computacionales","5",R.drawable.icono+""));
        alumnosList.add(new Alumno("3","Julieta","Ingenieria Sistemas Computacionales","7",R.drawable.icono+""));
        alumnosList.add(new Alumno("4","Jorge","Ingenieria Sistemas Computacionales","2",R.drawable.icono+""));
        alumnosList.add(new Alumno("5","Cesar","Ingenieria Sistemas Computacionales","5",R.drawable.icono+""));
        alumnosList.add(new Alumno("6","Armando","Ingenieria Sistemas Computacionales","5",R.drawable.icono+""));
        alumnosList.add(new Alumno("7","Carter","Ingenieria Sistemas Computacionales","6",R.drawable.icono+""));
        alumnosList.add(new Alumno("8","Jairo","Ingenieria Sistemas Computacionales","6",R.drawable.icono+""));
        alumnosList.add(new Alumno("9","Julian","Ingenieria Sistemas Computacionales","9",R.drawable.icono+""));
        return alumnosList;
    }
}
