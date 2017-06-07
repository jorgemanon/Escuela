package com.jje.programacion.escuela.activitys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.transition.Transition;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionInflater;
import android.view.MenuItem;

import com.jje.programacion.escuela.FAlumno;
import com.jje.programacion.escuela.FAlumnoDetalle;
import com.jje.programacion.escuela.FPrincipal;
import com.jje.programacion.escuela.R;
import com.jje.programacion.escuela.utilerias.Log;
import com.jje.programacion.escuela.utilerias.MyActivity;

public class Principal extends MyActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

}
