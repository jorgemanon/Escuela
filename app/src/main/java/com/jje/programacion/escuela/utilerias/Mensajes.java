package com.jje.programacion.escuela.utilerias;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.jje.programacion.escuela.R;
import com.jje.programacion.escuela.activitys.MainActivity;
import com.jje.programacion.escuela.activitys.NotificationActivity;

/**
 * Created by jorgemanon on 26/06/17.
 */

public class Mensajes {

    public static void mensajeOpciones(final Context context){
        final String[] items = {"No", "SinMi", "Ubuntu"};
        AlertDialog.Builder dialogo = new AlertDialog.Builder(context);

        dialogo.setTitle("Selecciona una opción");
        dialogo.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Toast.makeText(context, items[item], Toast.LENGTH_LONG).show();
            }
        });

        dialogo.create();
        dialogo.show();
    }

    public static void mostrarNotificacion(Context context){

        Intent i = new Intent(context,NotificationActivity.class);
        i.putExtra("notificationID", Config.NOTIFICATION_ID_1);
        i.putExtra("entrar",false);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, 0);
        NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        CharSequence ticker ="Nuevo Inicio de Sesión";
        CharSequence contentTitle = "Iniciar Sesion en DGTI";
        CharSequence contentText = "Visita ahora DGTI XD";
        Notification noti = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setTicker(ticker)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(R.drawable.account)
                .addAction(R.drawable.lock, ticker, pendingIntent)
                .setVibrate(new long[] {100, 250, 100, 500})
                .build();
        nm.notify(Config.NOTIFICATION_ID_1, noti);
    }
}
