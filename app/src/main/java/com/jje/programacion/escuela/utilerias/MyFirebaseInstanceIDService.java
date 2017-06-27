package com.jje.programacion.escuela.utilerias;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        try{
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            Log.e("TOKEN PUSH: " + refreshedToken);
        }catch(Exception e){
            Log.e("Error-->"+e.getMessage());
        }
    }
}