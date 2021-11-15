package com.war.amonchar.BaseDeDatos;

import com.google.firebase.database.FirebaseDatabase;

public class FireBase extends android.app.Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //Se genera la conexión con la base de datos de forma persistente
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
