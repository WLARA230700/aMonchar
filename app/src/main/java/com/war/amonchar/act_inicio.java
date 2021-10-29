package com.war.amonchar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.war.amonchar.BaseDeDatos.BD;
import com.war.amonchar.Modelo.GlobalVariables;

public class act_inicio extends AppCompatActivity {

    ImageView icUsuario, icListaCompra, icBuscar, btnCerrarSesion;
    TextView lbHolaUsuario;
    LinearLayout receta1;
    BD db;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        //System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_inicio);
        getSupportActionBar().hide();

        db = new BD(getApplicationContext());

        icUsuario = findViewById(R.id.icUsuario);
        icListaCompra = findViewById(R.id.icListaCompra);
        icBuscar = findViewById(R.id.icBuscar);
        receta1 = findViewById(R.id.receta1);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        lbHolaUsuario = findViewById(R.id.lbHolaUsuario);
        lbHolaUsuario.setText("¡Hola " + ((GlobalVariables) getApplication()).getUsuarioLogueado().getNombreUsuario() + "!");

        icUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act_inicio.this, act_perfil_usuario_general.class);
                startActivity(intent);
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion(db);
            }
        });

        icListaCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act_inicio.this, act_lista_compra.class);
                startActivity(intent);
            }
        });

        icBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), act_buscar_general.class);
                startActivity(intent);
            }
        });

        receta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), act_detalle_receta.class);
                startActivity(intent);
            }
        });

    }//Fin onCreate

    private void cerrarSesion(BD db) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("¿Seguro que desea cerrar sesión?");
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setCancelable(true);

        alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.validarUsuario(((GlobalVariables) getApplication()).getUsuarioLogueado().getCorreo(),
                        ((GlobalVariables) getApplication()).getUsuarioLogueado().getContrasenia(),
                        false);
                ((GlobalVariables) getApplication()).getUsuarioLogueado().setLogueado(false);
                Toast.makeText(getApplicationContext(), "Sesión Cerrada", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), act_bienvenida.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        alertDialog.show();
        /*

        // opciones para mostrar en el diálogo
        String[] options = {"Sí"};
        //dialogo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Titulo
        builder.setTitle("¿Seguro que desea cerrar sesión?");
        // establecer elementos / opciones
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // manejar clicks
                if (which==0){
                    ((GlobalVariables) getApplication()).getUsuarioLogueado().setLogueado(false);
                    db.modificarUsuario(((GlobalVariables) getApplication()).getUsuarioLogueado());
                    Toast.makeText(getApplicationContext(), "Sesión Cerrada", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        // Crear / mostrar diálogo
        builder.create().show();*/
    }
}