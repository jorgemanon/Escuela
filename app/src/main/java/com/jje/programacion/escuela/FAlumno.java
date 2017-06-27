package com.jje.programacion.escuela;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jje.programacion.escuela.activitys.Principal;
import com.jje.programacion.escuela.utilerias.AnimacionJAVA;
import com.jje.programacion.escuela.utilerias.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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

public class FAlumno extends Fragment {

    /* DECLARACION DE LAS VARIABLES PARA LOS LISTENER DEL JSON*/
    private Response.Listener listener;
    private Response.ErrorListener listenerError;
    private View view;
    private final FAlumno fragment = this;

    /* RECYCLER REQUIRED */
    private Spinner sCarrera,sSemestre,sGrupo;
    private Button btAlumnoAceptar;
    private List<Item> alumno;
    private boolean hasMore;
    private AsyncTask asyncTask;
    private RecyclerView rvAlumno;
    /* RECYCLER REQUIRED */

    public FAlumno() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Metodo para castear los componentes del layout en el fragmento
     */
    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        sCarrera = (Spinner) view.findViewById(R.id.sCarrera);
        sSemestre = (Spinner) view.findViewById(R.id.sSemestre);
        sGrupo = (Spinner) view.findViewById(R.id.sGrupo);
        rvAlumno = (RecyclerView) view.findViewById(R.id.rvAlumno);
        btAlumnoAceptar = (Button) view.findViewById(R.id.btAlumnoAceptar);
        alumno = new ArrayList<Item>();
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
                                FAlumno.this.rvAlumno.getAdapter().notifyItemInserted(alumno.size() - 1);
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
            /*******/
            JSONObject json = new JSONObject();
            JSONObject rqt = new JSONObject();
            rqt.put("carrera","");
            rqt.put("semestre","");
            rqt.put("grupo","");
            json.put("rqt",rqt);
            new VolleyEscuela(view.getContext()).sendJSON(Config.url_alumno, json, listener, listenerError);
            /******/

        }catch(Exception e){
            Log.e(e.getMessage());
        }

        btAlumnoAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    JSONObject json = new JSONObject();
                    JSONObject rqt = new JSONObject();
                    rqt.put("carrera",(sCarrera.getSelectedItemId()!=0)? sCarrera.getSelectedItemId(): "");
                    rqt.put("semestre",(sSemestre.getSelectedItemId()!=0)? sSemestre.getSelectedItemId(): "");
                    rqt.put("grupo",(sGrupo.getSelectedItemId()!=0)? sGrupo.getSelectedItemId(): "");
                    json.put("rqt",rqt);
                    alumno = new ArrayList<Item>();
                    new VolleyEscuela(view.getContext()).sendJSON(Config.url_alumno, json, listener, listenerError);
                }catch(Exception e){
                    Log.e(e.toString());
                }
            }
        });

        AdapterView.OnItemSelectedListener sListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
                AnimacionJAVA.littleScaleAnimacion_spinner(view,Config.TIEMPO_SPINNER_ANIMACION);
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        };

        sCarrera.setOnItemSelectedListener(sListener);
        sSemestre.setOnItemSelectedListener(sListener);
        sGrupo.setOnItemSelectedListener(sListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.falumno, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Intent intent = new Intent(getActivity(),Principal.class);
        intent.putExtra("fragmento", "FPrincipal");
        startActivity(intent);
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
                Log.e(e.getMessage());
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
                    JSONArray jsonArray = null;

                    Log.e("Response-->"+response);

                    switch(response.getString("tipo")){
                        case "carrera":
                            Log.w("Es carrera");
                            jsonArray = response.getJSONArray("carreras");
                            for(int i = 0; i < jsonArray.length(); i++){
                                id.add(jsonArray.getJSONObject(i).getString("carreraId"));
                                nombre.add(jsonArray.getJSONObject(i).getString("nombre"));
                            }
                            SpinnerUtileria.spinner(view.getContext(),sCarrera,new String[]{"_id","nombre"},id,nombre,0);
                            break;
                        case "semestre":
                            Log.w("Es semestre");
                            jsonArray = response.getJSONArray("semestres");
                            for(int i = 0; i < jsonArray.length(); i++){
                                id.add(jsonArray.getJSONObject(i).getString("semestreId"));
                                nombre.add(jsonArray.getJSONObject(i).getString("nombre"));
                            }
                            SpinnerUtileria.spinner(view.getContext(),sSemestre,new String[]{"_id","nombre"},id,nombre,0);
                            break;
                        case "aula":
                            Log.w("Es aula");
                            jsonArray = response.getJSONArray("aulas");
                            for(int i = 0; i < jsonArray.length(); i++){
                                id.add(jsonArray.getJSONObject(i).getString("aulaId"));
                                nombre.add(jsonArray.getJSONObject(i).getString("nombre"));
                            }
                            SpinnerUtileria.spinner(view.getContext(),sGrupo,new String[]{"_id","nombre"},id,nombre,0);
                            break;
                        case "alumno":
                            Log.w("Es alumno");
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

                                    AnimacionJAVA.rotarXLeft(v,Config.TIEMPO_DETALLE_ALUMNO_ANIMACION);
                                    if (alumno.size()>0 && alumno.get(position) instanceof Alumno) {
                                        final Intent intent = new Intent(getActivity(),Principal.class);
                                        intent.putExtra("fragmento", "FAlumnoDetalle");
                                        intent.putExtra("alumnoId", ((Alumno) alumno.get(position)).getAlumnoId());
                                        intent.putExtra("nombre", ((Alumno) alumno.get(position)).getNombre());
                                        intent.putExtra("apellidoPaterno", ((Alumno) alumno.get(position)).getApellidoPaterno());
                                        intent.putExtra("apellidoMaterno", ((Alumno) alumno.get(position)).getApellidoMaterno());
                                        intent.putExtra("fechaNacimiento", ((Alumno) alumno.get(position)).getFechaNacimiento());
                                        intent.putExtra("direccion", ((Alumno) alumno.get(position)).getDireccion());
                                        intent.putExtra("telefono", ((Alumno) alumno.get(position)).getTelefono());
                                        intent.putExtra("semestre", ((Alumno) alumno.get(position)).getSemestre());
                                        intent.putExtra("carrera", ((Alumno) alumno.get(position)).getCarrera());
                                        intent.putExtra("foto", ((Alumno) alumno.get(position)).getFoto());
                                        new Thread(new Runnable() {
                                            public void run() {
                                                try {
                                                    Thread.sleep(Config.TIEMPO_DETALLE_ALUMNO_ANIMACION);
                                                    startActivity(intent);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();
                                    }
                                }

                                @Override
                                public boolean onLongClick(View v, int position) {
                                    Log.e("Esta siendo presionado-->");
                                    AnimacionJAVA.bigScaleAnimacion(v,Config.TIEMPO_INICIO_ANIMACION);
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                Thread.sleep(Config.TIEMPO_INICIO_ANIMACION);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }.run();
                                    return true;
                                }

                            }));
                            rvAlumno.setLayoutManager(new LinearLayoutManager(getContext()));
                            break;
                    }
                    Log.d("Response--->"+response.toString());
                }catch(Exception e){
                    Log.e("Error-->"+e.toString());
                }
            }
        };

        listenerError = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                com.jje.programacion.escuela.utilerias.Log.e( "Error Respuesta en JSON: " + error.getMessage());
                error.printStackTrace();

            }
        };
    }
}
