package com.jje.programacion.escuela;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.snowdream.android.widget.SmartImageView;
import com.github.snowdream.android.widget.WebImage;
import com.jje.programacion.escuela.ServicioEscuela.VolleyEscuela;
import com.jje.programacion.escuela.activitys.Principal;
import com.jje.programacion.escuela.adapter.MateriaAdapter;
import com.jje.programacion.escuela.listener.RecyclerViewOnItemClickListener;
import com.jje.programacion.escuela.modelo.Footer;
import com.jje.programacion.escuela.modelo.Item;
import com.jje.programacion.escuela.modelo.Materia;
import com.jje.programacion.escuela.utilerias.AnimacionJAVA;
import com.jje.programacion.escuela.utilerias.Config;
import com.jje.programacion.escuela.utilerias.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import static com.jje.programacion.escuela.utilerias.Config.redondearBitmap;

public class FAlumnoDetalle extends Fragment {
    /************* DECLARACION DE VARIABLES ***********/
    private TextView tvIdDetalle, tvNombreDetalle, tvApellidoPaternoDetalle, tvApellidoMaternoDetalle, tvFechaNacimientoDetalle, tvDireccionDetalle, tvTelefonoDetalle, tvSemestreDetalle, tvCarreraDetalle;
    private SmartImageView ivFotoDetalle;
    private View view;
    private Response.Listener listener;
    private Response.ErrorListener listenerError;
    private CoordinatorLayout clDetalle;

    /* RECYCLER REQUIRED */
    private List<Item> materia;
    private boolean hasMore;
    private AsyncTask asyncTask;
    private RecyclerView rvMateria;
    /* RECYCLER REQUIRED */

    public FAlumnoDetalle() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.falumno_detalle, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        clDetalle = (CoordinatorLayout) view.findViewById(R.id.clDetalle);
        AnimacionJAVA.bigScaleAnimacion(clDetalle,Config.TIEMPO_INICIO_ANIMACION);
        tvIdDetalle = (TextView) view.findViewById(R.id.tvIdDetalle);
        tvNombreDetalle = (TextView) view.findViewById(R.id.tvNombreDetalle);
        tvFechaNacimientoDetalle = (TextView) view.findViewById(R.id.tvFechaNacimientoDetalle);
        tvDireccionDetalle = (TextView) view.findViewById(R.id.tvDireccionDetalle);
        tvTelefonoDetalle = (TextView) view.findViewById(R.id.tvTelefonoDetalle);
        tvSemestreDetalle = (TextView) view.findViewById(R.id.tvSemestreDetalle);
        tvCarreraDetalle = (TextView) view.findViewById(R.id.tvCarreraDetalle);
        ivFotoDetalle = (SmartImageView) view.findViewById(R.id.ivFotoDetalle);
        rvMateria = (RecyclerView) view.findViewById(R.id.rvHistorial);



        materia = new ArrayList<Item>();
        hasMore = true;
        /*inicializacion en fragment para JSON*/
        this.view = view;
        initListenerJSON();
        rvMateria.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView rvAlumno, int dx, int dy) {
                super.onScrolled(rvAlumno, dx, dy);
                if (hasMore && !(hasFooter())) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) rvAlumno.getLayoutManager();
                    if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 2) {
                        materia.add(new Footer());
                        Handler handler = new Handler();
                        final Runnable r = new Runnable() {
                            public void run() {
                                FAlumnoDetalle.this.rvMateria.getAdapter().notifyItemInserted(materia.size() - 1);
                            }
                        };
                        handler.post(r);
                        asyncTask = new FAlumnoDetalle.BackgroundTask();
                        asyncTask.execute((Object[]) null);
                    }
                }
            }
        });

        if(getArguments()!=null){
            tvIdDetalle.setText("ID: "+getArguments().getString("alumnoId"));
            tvNombreDetalle.setText("NOMBRE: "+getArguments().getString("nombre")+" "+getArguments().getString("apellidoPaterno")+" "+getArguments().getString("apellidoMaterno"));
            tvFechaNacimientoDetalle.setText("FECHA NACIMIENTO: "+getArguments().getString("fechaNacimiento"));
            tvDireccionDetalle.setText("DIRECCION: "+getArguments().getString("direccion"));
            tvTelefonoDetalle.setText("TELEFONO: "+getArguments().getString("telefono"));
            tvSemestreDetalle.setText("SEMESTRE: "+getArguments().getString("semestre"));
            tvCarreraDetalle.setText("CARRERA: "+getArguments().getString("carrera"));

            try{
                Bitmap imagen = new WebImage(Config.url_fotos+getArguments().getString("foto")).getBitmap(ivFotoDetalle.getContext());
                if(imagen!=null){
                    imagen = redondearBitmap(imagen,10,1);
                    ivFotoDetalle.setImageBitmap(imagen);
                }else{
                    ivFotoDetalle.setImageUrl(Config.url_fotos+getArguments().getString("foto"),new Rect());
                }
            }catch(Exception e){
                Log.e("Error imagen-->"+e);
            }

            try{
                /*******/
                JSONObject json = new JSONObject();
                JSONObject rqt = new JSONObject();
                rqt.put("alumnoId",getArguments().getString("alumnoId"));
                json.put("rqt",rqt);
                new VolleyEscuela(view.getContext()).sendJSON(Config.url_historial, json, listener, listenerError);
                /******/

            }catch(Exception e){
                Log.e(e.getMessage());
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /* EVENTO QUE SE ACTIVA CUANDO SE PRESIONA EL BOTON BACK */
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        Intent intent = new Intent(getActivity(),Principal.class);
        intent.putExtra("fragmento", "FAlumno");
        startActivity(intent);
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
            int size = materia.size();
            materia.remove(size - 1);
            materia.addAll(materia);
            rvMateria.getAdapter().notifyItemRangeChanged(size - 1, materia.size() - size);
            /*******************/
        }
    }

    private boolean hasFooter() {
        if(materia.size()==0){
            return false;
        }else{
            return materia.get(materia.size() - 1) instanceof Footer;
        }
    }

    /* INICIALIZAR LOS LISTENER PARA LOS EVENTOS DE RESPONSE Y RESONSE ERROR*/
    private void initListenerJSON(){
        listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    Log.d("Response--->"+response.toString());
                    JSONArray jsonArray = response.getJSONArray("materias");
                    for(int i = 0; i < jsonArray.length(); i++){
                        materia.add(new Materia(
                                jsonArray.getJSONObject(i).getString("materia"),
                                jsonArray.getJSONObject(i).getString("maestro"),
                                jsonArray.getJSONObject(i).getString("horario"),
                                jsonArray.getJSONObject(i).getString("aula"),
                                jsonArray.getJSONObject(i).getDouble("calificacion")
                        ));
                    }

                    rvMateria.setAdapter(new MateriaAdapter(materia, new RecyclerViewOnItemClickListener() {

                        @Override
                        public void onClick(View v, int position) {

                        }

                        @Override
                        public boolean onLongClick(View v, int position) {
                            return false;
                        }
                    }));
                    rvMateria.setLayoutManager(new LinearLayoutManager(getContext()));
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
