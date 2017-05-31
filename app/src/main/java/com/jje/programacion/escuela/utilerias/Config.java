package com.jje.programacion.escuela.utilerias;

import android.app.Activity;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Config extends Activity{

    /* DIRECCIONES DE LOS SERVICIOS PHP*/
    //public static final String servidor = "http://192.168.15.36/";//vaio-ubuntu
    public static final String servidor = "http://192.168.2.1/";//mac-airmovil
    public static final String url_login = servidor+"Servicio/Login.php";
    public static final String url_carrera = servidor+"Servicio/Carrera.php";
    public static final String url_semestre = servidor+"Servicio/Semestre.php";
    public static final String url_aula = servidor+"Servicio/Aula.php";
    public static final String url_alumno = servidor+"Servicio/Alumno.php";
    public static final String url_fotos = servidor+"fotos/";


    public static RequestQueue requestQueue;
    public static int TIEMPO_CARGA_FOOTER = 2000;//TIEMPO DE ESPERA AL CARGAR EL RECYCLER

    public static RequestQueue getInstance(Context context){
        if(requestQueue == null){
            requestQueue= Volley.newRequestQueue(context);
        }
        return requestQueue;
    }

}
