package com.war.amonchar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.war.amonchar.Modelo.AdapterPreparacion;
import com.war.amonchar.Modelo.Adapter_Ingredientes_agregar_receta;
import com.war.amonchar.Modelo.Adapter_categoria_detalle_receta;
import com.war.amonchar.Modelo.Ingrediente;
import com.war.amonchar.Modelo.PasoPreparacion;

import java.util.ArrayList;

public class act_detalle_receta extends AppCompatActivity {

    ImageView btnBack, img_fotografia;
    LinearLayout planSemanal, contenedorCategorias, listaIngrediente, listaPasos;
    Button btnPlanSemanal, btnAgregarPlanSemanal;
    TextView txt_nombre_receta, lblTiempoPreparacion;
    CheckBox lunes, martes, miercoles, jueves, viernes, sabado, domingo;
    CheckBox desayuno, merienda1, almuerzo, merienda2, cena, merienda3;

    Adapter_categoria_detalle_receta adapter_categoria_detalle_receta;
    Adapter_Ingredientes_agregar_receta adapter_ingredientes_agregar_receta;
    AdapterPreparacion adapterPreparacion;

    String direccionReceta = "";
    String recetaId = "";

    private DatabaseReference mDatabse;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    Boolean estadoPlanSemanal = false;
    Ingrediente ingredienteFor;
    PasoPreparacion pasoFor;

    private boolean daySelected = false;
    private boolean timeSelected = false;

    private ArrayList<CheckBox> diasSemana = new ArrayList<>();
    private ArrayList<CheckBox> tiempoComida = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_detalle_receta);
        getSupportActionBar().hide();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            direccionReceta = bundle.getString("nombreReceta") + "_" + bundle.getString("idReceta");
            recetaId = bundle.getString("idReceta");
        }

        lunes = findViewById(R.id.lunes);
        martes = findViewById(R.id.martes);
        miercoles = findViewById(R.id.miercoles);
        jueves = findViewById(R.id.jueves);
        viernes = findViewById(R.id.viernes);
        sabado = findViewById(R.id.sabado);
        domingo = findViewById(R.id.domingo);

        desayuno = findViewById(R.id.desayuno);
        merienda1 = findViewById(R.id.merienda1);
        almuerzo = findViewById(R.id.almuerzo);
        merienda2 = findViewById(R.id.merienda2);
        cena = findViewById(R.id.cena);
        merienda3 = findViewById(R.id.merienda3);

        btnBack = findViewById(R.id.btnBack);
        btnPlanSemanal = findViewById(R.id.btnPlanSemanal);
        btnAgregarPlanSemanal = findViewById(R.id.btnAgregarPlanSemanal);

        txt_nombre_receta = findViewById(R.id.txt_nombre_receta);

        contenedorCategorias = findViewById(R.id.contenedorCategorias);
        planSemanal = findViewById(R.id.planSemanal);
        listaIngrediente = findViewById(R.id.listaIngrediente);
        listaPasos = findViewById(R.id.listaPasos);

        lblTiempoPreparacion = findViewById(R.id.lblTiempoPreparacion);

        img_fotografia = findViewById(R.id.img_fotografia);

        planSemanal.setVisibility(View.GONE);

        cargarDiasSemana();
        cargarTiemposComida();

        btnPlanSemanal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                estadoPlanSemanal = mostrarPlanSemanal(planSemanal, btnPlanSemanal, estadoPlanSemanal);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAgregarPlanSemanal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarPlanSemanal();
            }
        });

        initListenerDiasSemana();
        initListenerTiempoComida();

       //Inisializar
        mDatabse = FirebaseDatabase.getInstance().getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        //Extracción de datos de la BD
        mDatabse.child("Receta").child(recetaId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    GenericTypeIndicator<ArrayList<String>> indicator = new GenericTypeIndicator<ArrayList<String>>(){};

                    int tiempo_preparacion = dataSnapshot.child("tiempo_preparacion").getValue(int.class);
                    String medida_tiempo_preparacion = dataSnapshot.child("medida_tiempo_preparacion").getValue(String.class);
                    String nombreReceta = dataSnapshot.child("nombre_receta").getValue().toString();

                    ArrayList<String> categorias = dataSnapshot.child("categorias").getValue(indicator);
                    ArrayList<String> cantidad_ingredientes = dataSnapshot.child("cantidad_ingredientes").getValue(indicator);
                    ArrayList<String> nombreIngredientes = dataSnapshot.child("ingredientes").getValue(indicator);
                    ArrayList<String> numero_pasos_db = dataSnapshot.child("pasos").getValue(indicator);

                    ArrayList<Ingrediente> ingredientes = new ArrayList<>();
                    ArrayList<PasoPreparacion> pasos = new ArrayList<>();

                    for(int i = 0; i < cantidad_ingredientes.size(); i++){
                        ingredienteFor = new Ingrediente(nombreIngredientes.get(i), cantidad_ingredientes.get(i));
                        ingredientes.add(ingredienteFor);
                    }


                    for(int i = 0; i < numero_pasos_db.size(); i++){
                        pasoFor = new PasoPreparacion(i + 1, numero_pasos_db.get(i));
                        pasos.add(pasoFor);
                    }

                    adapter_categoria_detalle_receta = new Adapter_categoria_detalle_receta(getApplicationContext(), categorias);
                    adapter_ingredientes_agregar_receta = new Adapter_Ingredientes_agregar_receta(getApplicationContext(), ingredientes);
                    adapterPreparacion = new AdapterPreparacion(getApplicationContext(), pasos);

                    for(int i = 0; i < adapter_ingredientes_agregar_receta.getCount(); i++){
                        View view = adapter_ingredientes_agregar_receta.getView(i, null, listaIngrediente);
                        listaIngrediente.addView(view);
                    }

                    for(int i = 0; i < adapterPreparacion.getCount(); i++){
                        View view = adapterPreparacion.getView(i, null, listaPasos);
                        listaPasos.addView(view);
                    }

                    for(int i = 0; i < adapter_categoria_detalle_receta.getCount(); i++){
                        View view = adapter_categoria_detalle_receta.getView(i, null, contenedorCategorias);
                        contenedorCategorias.addView(view);
                    }

                    txt_nombre_receta.setText(nombreReceta);
                    lblTiempoPreparacion.setText(tiempo_preparacion + " "+ medida_tiempo_preparacion);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        descargarViaUrl();
    }//Fin onCreate

    public void cargarDiasSemana(){
        diasSemana.add(lunes);
        diasSemana.add(martes);
        diasSemana.add(miercoles);
        diasSemana.add(jueves);
        diasSemana.add(viernes);
        diasSemana.add(sabado);
        diasSemana.add(domingo);
    }

    public void cargarTiemposComida(){
        tiempoComida.add(desayuno);
        tiempoComida.add(merienda1);
        tiempoComida.add(almuerzo);
        tiempoComida.add(merienda2);
        tiempoComida.add(cena);
        tiempoComida.add(merienda3);
    }

    public void initListenerDiasSemana(){

        for(int i = 0; i < diasSemana.size(); i++){
            int pos = i;
            diasSemana.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    //getDiaSemana(pos, b);
                    Toast.makeText(getApplicationContext(), getDiaSemana(pos, b), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public String getDiaSemana(int pos, boolean checked){
        String dia;
        if(checked){
            daySelected = true;
            dia = diasSemana.get(pos).getText().toString();
        }else{
            daySelected = false;
            dia = "";
        }

        for(int i = 0; i < diasSemana.size(); i++){
            if(checked && i != pos){
                diasSemana.get(i).setEnabled(false);
            }else if(checked == false && i != pos){
                diasSemana.get(i).setEnabled(true);
            }
        }

        return dia;
    }

    public void initListenerTiempoComida(){

        for(int i = 0; i < tiempoComida.size(); i++){
            int pos = i;
            tiempoComida.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    getTiempoComida(pos, b);
                }
            });
        }

    }

    public void getTiempoComida(int pos, boolean checked){
        for(int i = 0; i < tiempoComida.size(); i++){
            if(checked){
                timeSelected = true;
            }else{
                timeSelected = false;
            }

            if(checked && i != pos){
                tiempoComida.get(i).setEnabled(false);
            }else if(checked == false && i != pos){
                tiempoComida.get(i).setEnabled(true);
            }
        }
    }

    public void agregarPlanSemanal() {
        //code
    }

    //Método para descargar URL de la base de datos
    public void descargarViaUrl(){

        StorageReference imageRef = storageReference.child("Fotografia_Recetas/"+ direccionReceta);

        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //Cargar una imagen URI usando Glide
                Glide.with(act_detalle_receta.this)
                        .load(uri)
                        .centerCrop()
                        .error(R.drawable.ic_launcher_background)
                        .into(img_fotografia);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public boolean mostrarPlanSemanal(LinearLayout opciones, Button planSemanal, Boolean bool){

        if(bool){
            opciones.setVisibility(View.GONE);
            Drawable arrow = getDrawable(R.drawable.ic_yellow_arrow_down);
            planSemanal.setCompoundDrawablesWithIntrinsicBounds(null, null, arrow, null);
            bool = false;
        }else{
            opciones.setVisibility(View.VISIBLE);
            Drawable arrow = getDrawable(R.drawable.ic_yellow_arrow_up);
            planSemanal.setCompoundDrawablesWithIntrinsicBounds(null, null, arrow, null);
            bool = true;
        }

        return bool;

    }//Fin mostrarPlanSemanal

}//Fin clase act_detalle_receta