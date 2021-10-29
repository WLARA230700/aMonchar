package com.war.amonchar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.war.amonchar.Modelo.GlobalVariables;
import com.war.amonchar.Modelo.Usuario;

import de.hdodenhof.circleimageview.CircleImageView;

public class act_perfil_usuario_general extends AppCompatActivity {


    private CardView btnAgregarReceta, btnPlanSemanal, btnBeneficiosIngredientes;
    private ImageView btnListaCompra, btnInicio, icBuscar;
    private CircleImageView imgUsuario;
    private LinearLayout btnPerfil;
    private TextView lblBiografia, lblNombreUsuario, lblNombre;

    private Usuario usuarioLog = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_perfil_usuario_general);


        usuarioLog = ((GlobalVariables) getApplication()).getUsuarioLogueado();

        imgUsuario = findViewById(R.id.imgUsuario);
        imgUsuario.setImageURI(usuarioLog.getFotografia());


        btnAgregarReceta = findViewById(R.id.btnAgregarReceta);
        btnPlanSemanal = findViewById(R.id.btnPlanSemanal);
        btnBeneficiosIngredientes = findViewById(R.id.btnBeneficiosIngredientes);
        btnListaCompra = findViewById(R.id.icListaCompra);
        btnInicio = findViewById(R.id.icInicio);
        btnPerfil = findViewById(R.id.btnPerfil);
        icBuscar = findViewById(R.id.icBuscar);
        lblNombreUsuario = findViewById(R.id.lblNombreUsuario);
        lblBiografia = findViewById(R.id.lblBiografia);
        lblNombre = findViewById(R.id.lblNombre);

        lblNombreUsuario.setText(usuarioLog.getNombreUsuario());
        lblNombre.setText(usuarioLog.getNombre() + " " + usuarioLog.getApellidos());
        lblBiografia.setText(usuarioLog.getBiografia());

        btnAgregarReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();
                Intent intento = new Intent(act_perfil_usuario_general.this, act_agregar_receta.class);
                startActivity(intento);
            }
        });

        btnPlanSemanal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), act_plan_semanal.class);
                startActivity(intent);
            }
        });

        btnBeneficiosIngredientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), act_beneficios_ingredientes.class);
                startActivity(intent);
            }
        });

        btnListaCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), act_lista_compra.class);
                startActivity(intent);
            }
        });

        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), act_inicio.class);
                startActivity(intent);
            }
        });

        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), act_editar_perfil.class);
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

    }//Fin onCreate
}