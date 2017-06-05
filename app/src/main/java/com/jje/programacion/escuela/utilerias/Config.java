package com.jje.programacion.escuela.utilerias;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

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

    /****************** METODO PARA REDONDEAR BITMAP ******************/
    public static Bitmap redondearBitmapFoto(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(700, 700, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, 500, 500);
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, pixels, pixels, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /****************** METODO PARA REDONDEAR BITMAP ******************/
    public static Bitmap redondearBitmap(Bitmap bitmap, int pixels,int figura) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        int width = (figura == 0)? bitmap.getHeight() : bitmap.getWidth();
        final Rect rect = new Rect(0, 0, width, bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

}
