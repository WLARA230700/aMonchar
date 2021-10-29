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
import com.war.amonchar.Modelo.Usuario;

public class act_registro_usuario extends AppCompatActivity {

    Button btnRegistrarse;

    EditText txtNombreUsuario, txtEmail, txtContrasenia, txtContrasenia2;

    Switch switchTerminos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_registro_usuario);
        getSupportActionBar().hide();
        btnRegistrarse = findViewById(R.id.btnRegistrarse);

        txtNombreUsuario = findViewById(R.id.txtNombreUsuario);
        txtEmail = findViewById(R.id.txtEmail);
        txtContrasenia = findViewById(R.id.txtContrasenia);
        txtContrasenia2 = findViewById(R.id.txtContrasenia2);

        switchTerminos = findViewById(R.id.switchTerminos);

        final BD db = new BD(getApplicationContext());

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtNombreUsuario.getText().toString().isEmpty()){
                    txtNombreUsuario.setError("Campo requerido");
                }else if (txtEmail.getText().toString().isEmpty()){
                    txtEmail.setError("Campo requerido");
                }else if (txtContrasenia.getText().toString().isEmpty()){
                    txtContrasenia.setError("Campo requerido");
                }else if (txtContrasenia2.getText().toString().isEmpty()){
                    txtContrasenia2.setError("Campo requerido");
                }else if (!txtContrasenia.getText().toString().equals(txtContrasenia2.getText().toString())){
                    txtContrasenia2.setError("Las contraseñas no coinciden");
                }else if (!switchTerminos.isChecked()){
                    Toast.makeText(getApplicationContext(), "Debe aceptar los términos y condiciones", Toast.LENGTH_SHORT).show();
                }else{
                    Usuario usuario = new Usuario("@"+txtNombreUsuario.getText().toString(), txtEmail.getText().toString(),
                            txtContrasenia.getText().toString());

                    if(db.getUsuario("@"+txtNombreUsuario.getText().toString()) == null){
                        if (db.agregarUsuario(usuario)){
                            Toast.makeText(getApplicationContext(), "Usuario agregado correctamente", Toast.LENGTH_SHORT).show();
                            limpiar();
                            finish();

                        }else{
                            Toast.makeText(getApplicationContext(), "Error al agregar el usuario", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Error: Nombre de usuario ya existe", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    public void limpiar(){
        txtNombreUsuario.setText("");
        txtEmail.setText("");
        txtContrasenia.setText("");
        txtContrasenia2.setText("");
    }

}// Fin de la clase