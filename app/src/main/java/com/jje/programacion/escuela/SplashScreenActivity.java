package com.jje.programacion.escuela;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jje.programacion.escuela.activitys.MainActivity;
import com.jje.programacion.escuela.utilerias.AnimacionJAVA;
import com.jje.programacion.escuela.utilerias.Config;

public class SplashScreenActivity extends Activity {
    private LinearLayout contenedorSplash;
    private ImageView ivSplashScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_screen);

        contenedorSplash = (LinearLayout) findViewById(R.id.contenedorSplash);
        ivSplashScreen = (ImageView) findViewById(R.id.ivSplashScreen);
        redondearImageView(ivSplashScreen);

        AnimacionJAVA.rotarXRightSplash(ivSplashScreen, Config.TIEMPO_INICIO_ANIMACION);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(SplashScreenActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, Config.SPLASH_SCREEN_DELAY);

    }

    public void redondearImageView(ImageView image){
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(this.getResources(), bitmap);
        roundedDrawable.setCornerRadius(bitmap.getHeight());
        image.setImageDrawable(roundedDrawable);
    }

}