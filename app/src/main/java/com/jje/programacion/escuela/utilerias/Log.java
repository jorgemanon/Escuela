package com.jje.programacion.escuela.utilerias;

public class Log {

    public static final boolean visible = true;
    public static final String TAG = "jma";

    public static void e(String mensaje){
        if(visible) android.util.Log.e(TAG,"ERROR--->"+mensaje);
    }

    public static void d(String mensaje){
        if(visible) android.util.Log.d(TAG,"DEBUG--->"+mensaje);
    }

    public static void w(String mensaje){
        if(visible) android.util.Log.w(TAG,"WARN--->"+mensaje);
    }
    public static void i(String mensaje){
        if(visible) android.util.Log.i(TAG,"INFO--->"+mensaje);
    }

}
