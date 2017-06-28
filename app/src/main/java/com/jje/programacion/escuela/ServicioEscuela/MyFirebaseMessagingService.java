package com.jje.programacion.escuela.ServicioEscuela;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.jje.programacion.escuela.R;
import com.jje.programacion.escuela.utilerias.Log;
import com.jje.programacion.escuela.utilerias.Mensajes;

/**
 * Created by jorgemanon on 26/06/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(remoteMessage.toString());
        try{
            if (remoteMessage.getNotification() != null) {
                String titulo = remoteMessage.getNotification().getTitle().toString();
                String texto = remoteMessage.getNotification().getBody().toString();
                Log.e("NOTIFICACION RECIBIDA");
                Log.e("Título: " + titulo);
                Log.e("Texto: " + texto);
                Mensajes.showNotification(titulo, texto,getSystemService(Context.NOTIFICATION_SERVICE), this);
            }else{
                Log.e("Es nulo");
            }
        }catch (Exception e){
            Log.e("ERROR FIREBASE----------------------------->"+e.getMessage());
        }

    }




}
