package com.jje.programacion.escuela;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.jje.programacion.escuela.utilerias.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jje.programacion.escuela.ServicioEscuela.VolleyEscuela;
import com.jje.programacion.escuela.utilerias.Config;
import com.jje.programacion.escuela.modelo.Alumno;
import com.jje.programacion.escuela.adapter.AlumnoAdapter;
import com.jje.programacion.escuela.modelo.Footer;
import com.jje.programacion.escuela.modelo.Item;
import com.jje.programacion.escuela.listener.RecyclerViewOnItemClickListener;
import com.jje.programacion.escuela.utilerias.SpinnerUtileria;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class FAlumnos extends Fragment {

    /* DECLARACION DE LAS VARIABLES PARA LOS LISTENER DEL JSON*/
    private Response.Listener listener;
    private Response.ErrorListener listenerError;
    private View view;

    /* RECYCLER REQUIRED */
    private Spinner sCarrera,sSemestre,sGrupo;
    private List<Item> alumno;
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
        rvAlumno = (RecyclerView) view.findViewById(R.id.rvAlumno);
        hasMore = true;
        /*inicializacion en fragment para JSON*/
        this.view = view;
        initListenerJSON();
        rvAlumno.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView rvAlumno, int dx, int dy) {
                super.onScrolled(rvAlumno, dx, dy);
                if (hasMore && !(hasFooter())) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) rvAlumno.getLayoutManager();
                    if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 2) {
                        alumno.add(new Footer());
                        Handler handler = new Handler();
                        final Runnable r = new Runnable() {
                            public void run() {
                                FAlumnos.this.rvAlumno.getAdapter().notifyItemInserted(alumno.size() - 1);
                            }
                        };
                        handler.post(r);
                        asyncTask = new BackgroundTask();
                        asyncTask.execute((Object[]) null);
                    }
                }
            }
        });

        try{
            new VolleyEscuela(view.getContext()).sendJSON(Config.url_carrera,new JSONObject(), listener, listenerError);
            new VolleyEscuela(view.getContext()).sendJSON(Config.url_semestre,new JSONObject(), listener, listenerError);
            new VolleyEscuela(view.getContext()).sendJSON(Config.url_aula,new JSONObject(), listener, listenerError);
            new VolleyEscuela(view.getContext()).sendJSON(Config.url_alumno,new JSONObject(), listener, listenerError);

        }catch(Exception e){
            Log.e("jma",e.getMessage());
        }
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
            /*******************/
            int size = alumno.size();
            alumno.remove(size - 1);
            alumno.addAll(alumno);
            rvAlumno.getAdapter().notifyItemRangeChanged(size - 1, alumno.size() - size);
            /*******************/
        }
    }

    private boolean hasFooter() {
        Log.e("jma","Tamaño-->"+alumno.size());
        if(alumno.size()==0){
            return false;
        }else{
            return alumno.get(alumno.size() - 1) instanceof Footer;
        }

    }

    /* INICIALIZAR LOS LISTENER PARA LOS EVENTOS DE RESPONSE Y RESONSE ERROR*/
    private void initListenerJSON(){
        listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    List<String> id = new ArrayList<String>();
                    List<String> nombre = new ArrayList<String>();
                    alumno = new ArrayList<Item>();
                    JSONArray jsonArray = null;
                    switch(response.getString("tipo")){
                        case "carrera":
                            Log.e("jma","Es carrera");
                            jsonArray = response.getJSONArray("carreras");
                            for(int i = 0; i < jsonArray.length(); i++){
                                id.add(jsonArray.getJSONObject(i).getString("carreraId"));
                                nombre.add(jsonArray.getJSONObject(i).getString("nombre"));
                            }
                            SpinnerUtileria.spinner(view.getContext(),sCarrera,new String[]{"_id","nombre"},id,nombre,0);
                            break;
                        case "semestre":
                            Log.e("jma","Es semestre");
                            jsonArray = response.getJSONArray("semestres");
                            for(int i = 0; i < jsonArray.length(); i++){
                                id.add(jsonArray.getJSONObject(i).getString("semestreId"));
                                nombre.add(jsonArray.getJSONObject(i).getString("nombre"));
                            }
                            SpinnerUtileria.spinner(view.getContext(),sSemestre,new String[]{"_id","nombre"},id,nombre,0);
                            break;
                        case "aula":
                            Log.e("jma","Es aula");
                            jsonArray = response.getJSONArray("aulas");
                            for(int i = 0; i < jsonArray.length(); i++){
                                id.add(jsonArray.getJSONObject(i).getString("aulaId"));
                                nombre.add(jsonArray.getJSONObject(i).getString("nombre"));
                            }
                            SpinnerUtileria.spinner(view.getContext(),sGrupo,new String[]{"_id","nombre"},id,nombre,0);
                            break;
                        case "alumno":
                            Log.e("jma","Es alumno");
                            jsonArray = response.getJSONArray("alumnos");
                            for(int i = 0; i < jsonArray.length(); i++){
                                alumno.add(new Alumno(
                                        jsonArray.getJSONObject(i).getString("alumnoId"),
                                        jsonArray.getJSONObject(i).getString("nombre"),
                                        jsonArray.getJSONObject(i).getString("apellidoPaterno"),
                                        jsonArray.getJSONObject(i).getString("apellidoMaterno"),
                                        jsonArray.getJSONObject(i).getString("fechaNacimiento"),
                                        jsonArray.getJSONObject(i).getString("direccion"),
                                        jsonArray.getJSONObject(i).getString("telefono"),
                                        jsonArray.getJSONObject(i).getString("semestre"),
                                        jsonArray.getJSONObject(i).getString("carrera"),
                                        jsonArray.getJSONObject(i).getString("foto")
                                ));
                            }
                            rvAlumno.setAdapter(new AlumnoAdapter(alumno, new RecyclerViewOnItemClickListener() {
                                @Override
                                public void onClick(View v, int position) {
                                    if (alumno.get(position) instanceof Alumno && alumno.size()>0) {
                                        Toast.makeText(getContext(), "Hola "+((Alumno) alumno.get(position)).toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }));
                            rvAlumno.setLayoutManager(new LinearLayoutManager(getContext()));
                            break;
                    }
                    Log.e("jma","Response--->"+response.toString());
                }catch(Exception e){}

            }
        };

        listenerError = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                com.jje.programacion.escuela.utilerias.Log.e("jma", "Error Respuesta en JSON: " + error.getMessage());
                error.printStackTrace();

            }
        };
    }
}
