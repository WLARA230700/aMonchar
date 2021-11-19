package com.war.amonchar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.war.amonchar.Modelo.GlobalVariables;
import com.war.amonchar.Modelo.PlanSemanal;
import com.war.amonchar.Modelo.RecetaPlanSemanal;
import com.war.amonchar.Modelo.Usuario;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class act_perfil_usuario_general extends AppCompatActivity {


    private CardView btnAgregarReceta, btnPlanSemanal, btnBeneficiosIngredientes;
    private ImageView btnListaCompra, btnInicio, icBuscar;
    private CircleImageView imgUsuario;
    private LinearLayout btnPerfil;
    private TextView lblBiografia, lblNombreUsuario, lblNombre;

    private Usuario usuarioLog = null;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecetaPlanSemanal receta;

    ArrayList<RecetaPlanSemanal> recetasLunes  = new ArrayList<>();
    ArrayList<RecetaPlanSemanal> recetasMartes  = new ArrayList<>();
    ArrayList<RecetaPlanSemanal> recetasMiercoles  = new ArrayList<>();
    ArrayList<RecetaPlanSemanal> recetasJueves  = new ArrayList<>();
    ArrayList<RecetaPlanSemanal> recetasViernes  = new ArrayList<>();
    ArrayList<RecetaPlanSemanal> recetasSabado  = new ArrayList<>();
    ArrayList<RecetaPlanSemanal> recetasDomingo  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_perfil_usuario_general);
        getSupportActionBar().hide();

        usuarioLog = ((GlobalVariables) getApplication()).getUsuarioLogueado();

        inicializarFirebase();

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
        imgUsuario = findViewById(R.id.imgUsuario);

        imgUsuario.setImageDrawable(getDrawable(R.drawable.ic_perfil));
        if(!usuarioLog.getFotografia().toString().equals("")){
            imgUsuario.setImageURI(usuarioLog.getFotografia());
        }
        lblNombreUsuario.setText(usuarioLog.getNombreUsuario());
        lblNombre.setText(usuarioLog.getNombre() + " " + usuarioLog.getApellidos());
        lblBiografia.setText(usuarioLog.getBiografia());

        cargarPlanSemanal();

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
                intent.putParcelableArrayListExtra("recetasLunes", recetasLunes);
                intent.putParcelableArrayListExtra("recetasMartes", recetasMartes);
                intent.putParcelableArrayListExtra("recetasMiercoles", recetasMiercoles);
                intent.putParcelableArrayListExtra("recetasJueves", recetasJueves);
                intent.putParcelableArrayListExtra("recetasViernes", recetasViernes);
                intent.putParcelableArrayListExtra("recetasSabado", recetasSabado);
                intent.putParcelableArrayListExtra("recetasDomingo", recetasDomingo);
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

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance(); // Obtengo la instancia de firebase
        databaseReference = firebaseDatabase.getReference(); // Obtengo la referencia a utilizar en la base de datos
    }//fin inicializarFirebase

    public void cargarPlanSemanal(){

        databaseReference.child("PlanSemanal").orderByChild("correoUsuario")
                .equalTo(((GlobalVariables)getApplication()).getUsuarioLogueado().getCorreo())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists()){

                            for(DataSnapshot obtSnapshot : snapshot.getChildren()){

                                String dia = obtSnapshot.getValue(PlanSemanal.class).getDia();
                                String idReceta = obtSnapshot.getValue(PlanSemanal.class).getIdReceta();
                                String tiempoComida = obtSnapshot.getValue(PlanSemanal.class).getTiempoComida();

                                cargarReceta(idReceta, dia, tiempoComida);

                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    public void cargarReceta(String idReceta, String dia, String tiempoComida){
        databaseReference.child("Receta").child(idReceta).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                //recetas.clear();
                if(task.isComplete()){

                    DataSnapshot snapshot = task.getResult();

                    if(snapshot.exists()){

                        switch (dia){
                            case "Lunes":
                                receta = snapshot.getValue(RecetaPlanSemanal.class);
                                receta.setDia(dia);
                                receta.setTiempoComidaPlanSemanal(tiempoComida);
                                recetasLunes.add(receta);
                                break;

                            case "Martes":
                                receta = snapshot.getValue(RecetaPlanSemanal.class);
                                receta.setDia(dia);
                                receta.setTiempoComidaPlanSemanal(tiempoComida);
                                recetasMartes.add(receta);
                                break;

                            case "Miércoles":
                                receta = snapshot.getValue(RecetaPlanSemanal.class);
                                receta.setDia(dia);
                                receta.setTiempoComidaPlanSemanal(tiempoComida);
                                recetasMiercoles.add(receta);
                                break;

                            case "Jueves":
                                receta = snapshot.getValue(RecetaPlanSemanal.class);
                                receta.setDia(dia);
                                receta.setTiempoComidaPlanSemanal(tiempoComida);
                                recetasJueves.add(receta);
                                break;

                            case "Viernes":
                                receta = snapshot.getValue(RecetaPlanSemanal.class);
                                receta.setDia(dia);
                                receta.setTiempoComidaPlanSemanal(tiempoComida);
                                recetasViernes.add(receta);
                                break;

                            case "Sábado":
                                receta = snapshot.getValue(RecetaPlanSemanal.class);
                                receta.setDia(dia);
                                receta.setTiempoComidaPlanSemanal(tiempoComida);
                                recetasSabado.add(receta);
                                break;

                            case "Domingo":
                                receta = snapshot.getValue(RecetaPlanSemanal.class);
                                receta.setDia(dia);
                                receta.setTiempoComidaPlanSemanal(tiempoComida);
                                recetasDomingo.add(receta);
                                break;
                        }
                    }

                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        recetasLunes.clear();
        recetasMartes.clear();
        recetasMiercoles.clear();
        recetasJueves.clear();
        recetasViernes.clear();
        recetasSabado.clear();
        recetasDomingo.clear();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        cargarPlanSemanal();

        imgUsuario.setImageDrawable(getDrawable(R.drawable.ic_perfil));
        if(!usuarioLog.getFotografia().toString().equals("")){
            imgUsuario.setImageURI(usuarioLog.getFotografia());
        }
        lblNombreUsuario.setText(usuarioLog.getNombreUsuario());
        lblNombre.setText(usuarioLog.getNombre() + " " + usuarioLog.getApellidos());
        lblBiografia.setText(usuarioLog.getBiografia());
    }
}