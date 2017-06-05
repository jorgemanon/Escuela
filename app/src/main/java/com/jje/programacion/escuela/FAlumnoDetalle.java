package com.jje.programacion.escuela;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.github.snowdream.android.widget.SmartImageView;
import com.github.snowdream.android.widget.WebImage;
import com.jje.programacion.escuela.R;
import com.jje.programacion.escuela.utilerias.Config;
import com.jje.programacion.escuela.utilerias.Log;

import static com.jje.programacion.escuela.utilerias.Config.redondearBitmap;
import static com.jje.programacion.escuela.utilerias.Config.redondearBitmapFoto;

public class FAlumnoDetalle extends Fragment {
    /************* DECLARACION DE VARIABLES ***********/
    private TextView tvIdDetalle, tvNombreDetalle, tvApellidoPaternoDetalle, tvApellidoMaternoDetalle, tvFechaNacimientoDetalle, tvDireccionDetalle, tvTelefonoDetalle, tvSemestreDetalle, tvCarreraDetalle;
    private SmartImageView ivFotoDetalle;
    private OnFragmentInteractionListener mListener;

    public FAlumnoDetalle() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.falumno_detalle, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvIdDetalle = (TextView) view.findViewById(R.id.tvIdDetalle);
        tvNombreDetalle = (TextView) view.findViewById(R.id.tvNombreDetalle);
        tvFechaNacimientoDetalle = (TextView) view.findViewById(R.id.tvFechaNacimientoDetalle);
        tvDireccionDetalle = (TextView) view.findViewById(R.id.tvDireccionDetalle);
        tvTelefonoDetalle = (TextView) view.findViewById(R.id.tvTelefonoDetalle);
        tvSemestreDetalle = (TextView) view.findViewById(R.id.tvSemestreDetalle);
        tvCarreraDetalle = (TextView) view.findViewById(R.id.tvCarreraDetalle);
        ivFotoDetalle = (SmartImageView) view.findViewById(R.id.ivFotoDetalle);

        Log.e(getArguments().toString());

        String id = getArguments().getString("alumnoId");
        String nombre = getArguments().getString("nombre")+" "+getArguments().getString("apellidoPaterno")+" "+getArguments().getString("apellidoMaterno");
        String fechaNacimiento = getArguments().getString("fechaNacimiento");
        String direccion = getArguments().getString("direccion");
        String telefono = getArguments().getString("telefono");
        String semestre = getArguments().getString("semestre");
        String carrera = getArguments().getString("carrera");

        if(getArguments()!=null){
            tvIdDetalle.setText("ID: "+id);
            tvNombreDetalle.setText("NOMBRE: "+nombre);
            tvFechaNacimientoDetalle.setText("FECHA NACIMIENTO: "+fechaNacimiento);
            tvDireccionDetalle.setText("DIRECCION: "+direccion);
            tvTelefonoDetalle.setText("TELEFONO: "+telefono);
            tvSemestreDetalle.setText("SEMESTRE: "+semestre);
            tvCarreraDetalle.setText("CARRERA: "+carrera);

            try{
                /***********************************
                ivFotoDetalle.setImageUrl(Config.url_fotos+getArguments().getString("foto"),new Rect());
                Log.e(Config.url_fotos+getArguments().getString("foto"));
                /***********************************/
                Bitmap imagen = new WebImage(Config.url_fotos+getArguments().getString("foto"),1000,700).getBitmap(ivFotoDetalle.getContext());
                imagen = redondearBitmap(imagen,10,1);
                ivFotoDetalle.setImageBitmap(imagen);
                /***********************************/
            }catch(Exception e){
                Log.e("Error imagen-->"+e);
            }
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
