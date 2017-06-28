package com.jje.programacion.escuela.activitys;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jje.programacion.escuela.R;
import com.jje.programacion.escuela.ServicioEscuela.VolleyEscuela;
import com.jje.programacion.escuela.utilerias.Config;
import com.jje.programacion.escuela.utilerias.Log;
import com.jje.programacion.escuela.utilerias.MyActivity;

import org.json.JSONObject;

public class MainActivity extends MyActivity {

    private EditText ETUsuario;
    private EditText ETContrasena;
    private Button BEntrar;
    private String usuario;
    private String contrasena;
    private MainActivity mainActivity = this;

    /* DECLARACION DE LAS VARIABLES PARA LOS LISTENER DEL JSON*/
    private Response.Listener listener;
    private Response.ErrorListener listenerError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        redondearImagePerfil();
        usuario = verLlave("usuario");
        contrasena = verLlave(usuario);
        ETUsuario.setText(usuario);
        ETContrasena.setText(contrasena);
        initListenerJSON();
        if(getIntent().getExtras()!=null){
            if(getIntent().getExtras().getBoolean("entrar")){
                BEntrar.callOnClick();
            }

        }else{
            BEntrar.callOnClick();
        }

        //Mensajes.mostrarNotificacion(getApplicationContext());
    }

    /* INICIALIZAR LOS LISTENER PARA LOS EVENTOS DE RESPONSE Y RESONSE ERROR*/
    private void initListenerJSON(){
        listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    if(response.getBoolean("confirmacion")){
                        startActivity(new Intent(mainActivity,Principal.class));
                        overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
                    }else{
                        Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                    }
                    Log.e("Response--->"+response.toString());
                }catch(Exception e){
                    Log.e("ERROR-->"+e.getMessage());
                }
            }
        };

        listenerError = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e( "Error Respuesta en JSON: " + error.getMessage());
                error.printStackTrace();

            }
        };
    }

    public void initComponents(){
        añadirLlave("usuario","carter");
        añadirLlave("carter","1234");
        ETUsuario = (EditText) findViewById(R.id.ETUsuario);
        ETContrasena = (EditText) findViewById(R.id.ETContrasena);
        BEntrar = (Button) findViewById(R.id.BEntrar);
        BEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject json = new JSONObject();
                JSONObject rqt = new JSONObject();
                try{
                    rqt.put("usuario",ETUsuario.getText().toString());
                    rqt.put("password",ETContrasena.getText().toString());
                    json.put("rqt",rqt);
                    Log.e("rqt-->"+json.toString());
                }catch(Exception e){
                    Log.e(e.getMessage());
                }
                VolleyEscuela escuela = new VolleyEscuela(getApplicationContext());
                escuela.sendJSON(Config.url_login,json, listener, listenerError);
            }
        });
    }

    public void redondearImagePerfil(){
        Bitmap originalBitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.portada)).getBitmap();
        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);
        roundedDrawable.setCornerRadius(originalBitmap.getHeight());
        ImageView imageView = (ImageView) findViewById(R.id.IPerfil);
        imageView.setImageDrawable(roundedDrawable);
    }

    public void añadirLlave(String llave, String valor){
        SharedPreferences prefs = getSharedPreferences("escuela", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(llave, valor);
        editor.commit();
    }

    public String verLlave(String llave){
        SharedPreferences prefs = getSharedPreferences("escuela",Context.MODE_PRIVATE);
        return prefs.getString(llave,"null");
    }
}
