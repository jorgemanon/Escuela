package com.jje.programacion.escuela.utilerias;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

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
                showNotification(titulo, texto);
            }else{
                Log.e("Es nulo");
            }
        }catch (Exception e){
            Log.e("ERROR FIREBASE----------------------------->"+e.getMessage());
        }

    }

    private void showNotification(String title, String text) {
        NotificationCompat.Builder notificationBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.stat_sys_warning)
                        .setContentTitle(title)
                        .setContentText(text);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }


}
