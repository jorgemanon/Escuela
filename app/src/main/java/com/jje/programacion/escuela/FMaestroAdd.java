package com.jje.programacion.escuela;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jje.programacion.escuela.activitys.Principal;
import com.jje.programacion.escuela.utilerias.Log;

import java.io.File;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class FMaestroAdd extends Fragment {

    private Button btnMaestroAdd;
    private ImageView ivCaptura;
    private EditText etNombre;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Metodo para castear los componentes del layout en el fragmento
     */
    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        btnMaestroAdd = (Button) view.findViewById(R.id.btnMaestroAdd);
        ivCaptura = (ImageView) view.findViewById(R.id.ivCaptura);
        etNombre = (EditText) view.findViewById(R.id.etMaestroNombre);

        btnMaestroAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File foto = new File(new ContextWrapper(view.getContext()).getDir("", Context.MODE_PRIVATE), "mifoto.jpg").getAbsoluteFile();
                    //Log.e(foto.getAbsolutePath());
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, foto);
                    startActivityForResult(cameraIntent, 1);
                } catch (Exception ex) {
                    Log.e("Error:" + ex);
                }
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista =  inflater.inflate(R.layout.fmaestroadd, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//ocultar teclado en fragment
        return vista;
    }

    @Override
    public void onAttach(Context context) {super.onAttach(context);}

    @Override
    public void onDetach() {
        super.onDetach();
        Intent intent = new Intent(getActivity(),Principal.class);
        intent.putExtra("fragmento", "FPrincipal");
        startActivity(intent);
    }

    /*OBTENCION DE LA FOTO CAPTURADA DESDE LA APLICACION EXTERNA */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("Datos regresados-------->"+data.toString());
        Bitmap foto = (Bitmap) data.getExtras().get("data");
        if(foto instanceof Bitmap){
            ivCaptura.setImageBitmap(foto);
        }else{
            Log.e("Esta foto no es bitmap");
        }
    }

}
