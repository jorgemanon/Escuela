package com.jje.programacion.escuela.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;

import com.jje.programacion.escuela.FAlumno;
import com.jje.programacion.escuela.FAlumnoDetalle;
import com.jje.programacion.escuela.FMaestro;
import com.jje.programacion.escuela.FMaestroAdd;
import com.jje.programacion.escuela.FPrincipal;
import com.jje.programacion.escuela.R;
import com.jje.programacion.escuela.utilerias.Log;
import com.jje.programacion.escuela.utilerias.MyActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class Principal extends MyActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //abrirScreenshot(guardarScreenshot(crearScreenShot()));

        Bundle args = getIntent().getExtras();
        if(args!=null){
            String fragmento = args.getString("fragmento");
            switch(fragmento){
                case "FAlumnoDetalle":
                    Log.e("es alumno detalle");
                    FAlumnoDetalle fad = new FAlumnoDetalle();
                    fad.setArguments(args);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contenedor,fad)
                            .addToBackStack(null)
                            .commit();
                    break;

                case "FAlumno":
                    Log.e("es alumno");
                    FAlumno fa = new FAlumno();
                    fa.setArguments(args);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contenedor,fa)
                            .addToBackStack(null)
                            .commit();
                    break;

                case "FMaestro":
                    Log.e("es maestro");
                    FMaestro fm = new FMaestro();
                    fm.setArguments(args);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contenedor,fm)
                            .addToBackStack(null)
                            .commit();
                    break;

                case "FMaestroAdd":
                    Log.e("es maestro add");
                    FMaestroAdd fma = new FMaestroAdd();
                    fma.setArguments(args);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contenedor,fma)
                            .addToBackStack(null)
                            .commit();
                    break;

                case "FPrincipal":
                    Log.e("es principal");
                    FPrincipal fp = new FPrincipal();
                    fp.setArguments(args);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contenedor,fp)
                            .addToBackStack(null)
                            .commit();
                    break;
            }
        }else{
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contenedor,new FPrincipal())
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_inicio) {

            startActivity(new Intent(this,Principal.class));
            overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
        } else if (id == R.id.nav_alumno) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contenedor,new FAlumno())
                    .addToBackStack(null)
                    .setCustomAnimations(
                            R.anim.fragment_slide_left_enter,
                            R.anim.fragment_slide_left_exit)
                    .commit();

        } else if (id == R.id.nav_maestro) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contenedor,new FMaestro())
                    .addToBackStack(null)
                    .setCustomAnimations(
                            R.anim.fragment_slide_left_enter,
                            R.anim.fragment_slide_left_exit)
                    .commit();

        } else if (id == R.id.nav_materia) {

        } else if (id == R.id.nav_grupo) {

        } else if (id == R.id.nav_carrera) {

        } else if (id == R.id.nav_usuarios) {

        }else if (id == R.id.nav_cerrar_sesion) {
            startActivity(new Intent(this,MainActivity.class));
            overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }



    private Bitmap crearScreenShot() {
        try {
            // crear un bitmap con la captura de pantalla
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);
            return bitmap ;
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
        return null;
    }

    private File guardarScreenshot(Bitmap bitmap) {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // nombre y ruta de la imagen a incluir
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            return imageFile;

        } catch (Throwable e) {
            // Captura los distintos errores que puedan surgir
            e.printStackTrace();
        }

        return null;
    }

    private void abrirScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }


}
