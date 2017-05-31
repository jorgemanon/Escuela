package com.jje.programacion.escuela.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.jje.programacion.escuela.FAlumnos;
import com.jje.programacion.escuela.FPrincipal;
import com.jje.programacion.escuela.R;

public class Principal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor,new FPrincipal())
                .addToBackStack(null)
                .setCustomAnimations(
                        android.R.anim.overshoot_interpolator,
                        android.R.anim.overshoot_interpolator)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_inicio) {
            startActivity(new Intent(this,Principal.class));
            overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
        } else if (id == R.id.nav_alumno) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contenedor,new FAlumnos())
                    .addToBackStack(null)
                    .setCustomAnimations(
                            android.R.anim.overshoot_interpolator,
                            android.R.anim.overshoot_interpolator)
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
}
