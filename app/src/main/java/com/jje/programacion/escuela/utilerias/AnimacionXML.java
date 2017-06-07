package com.jje.programacion.escuela.utilerias;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.jje.programacion.escuela.R;

public class AnimacionXML {

    public static void rotar(Context context, View view){
        Animation animacion = AnimationUtils.loadAnimation(context,R.anim.girar);
        animacion.reset();
        view.startAnimation(animacion);
    }

    public static void trasladar(Context context, View view){
        Animation animacion = AnimationUtils.loadAnimation(context,R.anim.trasladar);
        animacion.reset();
        view.startAnimation(animacion);
    }

    public static void escalar(Context context, View view){
        Animation animacion = AnimationUtils.loadAnimation(context,R.anim.escalar);
        animacion.reset();
        view.startAnimation(animacion);
    }

    public static void opacar(Context context, View view){
        Animation animacion = AnimationUtils.loadAnimation(context,R.anim.opacar);
        animacion.reset();
        view.startAnimation(animacion);
    }

    public static void animatorSetXML(View view){
        AnimatorSet as = (AnimatorSet) AnimatorInflater.loadAnimator(view.getContext(), R.animator.animation_set);
        as.setTarget(view);
        as.start();
    }
}
