package com.war.amonchar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.war.amonchar.BaseDeDatos.BD;
import com.war.amonchar.Modelo.GlobalVariables;
import com.war.amonchar.Modelo.Usuario;

public class act_inicio_sesion extends AppCompatActivity {

    Button btnIniciarSesion;

    EditText txtEmail, txtPassword;

    Switch swtRecordarSesion;

    //private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_inicio_sesion);
        getSupportActionBar().hide();
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);

        swtRecordarSesion = findViewById(R.id.swtRecordarSesion);

        final BD db = new BD(getApplicationContext());

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean logueado;
                if (swtRecordarSesion.isChecked()){
                    logueado = true;
                }else{
                    logueado = false;
                }

                if (txtEmail.getText().toString().isEmpty()){
                    txtEmail.setError("Campo requerido");
                } else if (txtPassword.getText().toString().isEmpty()){
                    txtPassword.setError("Campo requerido");
                }else if (!db.validarUsuario(txtEmail.getText().toString(), txtPassword.getText().toString(), logueado)){
                    Toast.makeText(getApplicationContext(), "Correo o contraseña inválidos", Toast.LENGTH_SHORT).show();
                }else{
                    //Toast.makeText(getApplicationContext(), String.valueOf(db.getUsuarios().get(0).isLogueado()), Toast.LENGTH_SHORT).show();

                    //Usuario usuario = getApplication().
                    ((GlobalVariables) getApplication()).setUsuarioLogueado(db.getUsuarioCorreo(txtEmail.getText().toString()));

                    if(((GlobalVariables) getApplication()).getUsuarioLogueado() != null){
                        Toast.makeText(getApplicationContext(), "Sesión iniciada", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), act_inicio.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "Hubo un problema al iniciar sesión", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }//Fin onCreate
}//Fin clase act_inicio_sesion