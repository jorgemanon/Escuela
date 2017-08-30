package com.jje.programacion.escuela;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jje.programacion.escuela.ServicioEscuela.VolleyEscuela;
import com.jje.programacion.escuela.activitys.Principal;
import com.jje.programacion.escuela.adapter.AlumnoAdapter;
import com.jje.programacion.escuela.adapter.MaestroAdapter;
import com.jje.programacion.escuela.listener.RecyclerViewOnItemClickListener;
import com.jje.programacion.escuela.modelo.Alumno;
import com.jje.programacion.escuela.modelo.Footer;
import com.jje.programacion.escuela.modelo.Item;
import com.jje.programacion.escuela.modelo.Maestro;
import com.jje.programacion.escuela.utilerias.AnimacionJAVA;
import com.jje.programacion.escuela.utilerias.Config;
import com.jje.programacion.escuela.utilerias.Log;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class FMaestro extends Fragment {

    /* DECLARACION DE LAS VARIABLES PARA LOS LISTENER DEL JSON*/
    private Response.Listener listener;
    private Response.ErrorListener listenerError;
    private View view;
    private final FMaestro fragment = this;

    /* RECYCLER REQUIRED */
    private List<Item> maestro;
    private boolean hasMore;
    private AsyncTask asyncTask;
    private RecyclerView rvMaestro;
    private Button btnAgregar;
    /* RECYCLER REQUIRED */

    public FMaestro() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Metodo para castear los componentes del layout en el fragmento
     */
    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        rvMaestro = (RecyclerView) view.findViewById(R.id.rvMaestro);
        btnAgregar = (Button) view.findViewById(R.id.btnMaestroAgregar);
        maestro = new ArrayList<Item>();
        hasMore = true;
        this.view = view;

        initListenerJSON();

        rvMaestro.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView rvMaestro, int dx, int dy) {
                super.onScrolled(rvMaestro, dx, dy);
                if (hasMore && !(hasFooter())) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) rvMaestro.getLayoutManager();
                    if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 2) {
                        maestro.add(new Footer());
                        Handler handler = new Handler();
                        final Runnable r = new Runnable() {
                            public void run() {
                                FMaestro.this.rvMaestro.getAdapter().notifyItemInserted(maestro.size() - 1);
                            }
                        };
                        handler.post(r);
                        asyncTask = new FMaestro.BackgroundTask();
                        asyncTask.execute((Object[]) null);
                    }
                }
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Principal.class);
                intent.putExtra("fragmento", "FMaestroAdd");
                startActivity(intent);
            }
        });

        try{
            /*******/
            JSONObject json = new JSONObject();
            JSONObject rqt = new JSONObject();
            rqt.put("maestro","");
            json.put("rqt",rqt);
            new VolleyEscuela(view.getContext()).sendJSON(Config.url_maestro, json, listener, listenerError);
            /******/

        }catch(Exception e){
            Log.e(e.getMessage());
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fmaestro, container, false);
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
            int size = maestro.size();
            maestro.remove(size - 1);
            maestro.addAll(maestro);
            rvMaestro.getAdapter().notifyItemRangeChanged(size - 1, maestro.size() - size);
            /*******************/
        }
    }

    private boolean hasFooter() {
        if(maestro.size()==0){
            return false;
        }else{
            return maestro.get(maestro.size() - 1) instanceof Footer;
        }
    }

    /* INICIALIZAR LOS LISTENER PARA LOS EVENTOS DE RESPONSE Y RESONSE ERROR*/
    private void initListenerJSON(){
        listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    Log.d("Response--->"+response.toString());
                    JSONArray jsonArray = response.getJSONArray("maestros");

                    for(int i = 0; i < jsonArray.length(); i++){
                        maestro.add(new Maestro(
                                jsonArray.getJSONObject(i).getString("maestroId"),
                                jsonArray.getJSONObject(i).getString("nombre"),
                                jsonArray.getJSONObject(i).getString("apellidoPaterno"),
                                jsonArray.getJSONObject(i).getString("apellidoMaterno"),
                                jsonArray.getJSONObject(i).getString("foto")
                        ));
                    }
                    rvMaestro.setAdapter(new MaestroAdapter(maestro, new RecyclerViewOnItemClickListener() {
                        @Override
                        public void onClick(View v, int position) {
                            AnimacionJAVA.rotarXLeft(v,Config.TIEMPO_DETALLE_ALUMNO_ANIMACION);
                        }

                        @Override
                        public boolean onLongClick(View v, int position) {return true;}

                    }));

                    rvMaestro.setLayoutManager(new LinearLayoutManager(getContext()));
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
