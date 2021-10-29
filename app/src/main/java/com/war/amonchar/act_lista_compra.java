package com.war.amonchar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.war.amonchar.BaseDeDatos.BD;
import com.war.amonchar.Modelo.AdapterIngredientes;
import com.war.amonchar.Modelo.Ingrediente;

import java.util.ArrayList;

public class act_lista_compra extends AppCompatActivity {

    ImageView btnBack;

    EditText txtAgregarIngrediente;

    ArrayList<Ingrediente> ingredientes;
    AdapterIngredientes adapterIngredientes;

    ListView listaCompra;

    CardView btnAgregarIngrediente;

    int idIngrediente = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_lista_compra);
        getSupportActionBar().hide();

        btnBack = findViewById(R.id.btnBack);
        listaCompra = findViewById(R.id.listaCompra);
        btnAgregarIngrediente = findViewById(R.id.btnAgregarIngrediente);
        txtAgregarIngrediente = findViewById(R.id.txtAgregarIngrediente);

        final BD db = new BD(getApplicationContext());

        ingredientes = db.getIngredientes();

        adapterIngredientes = new AdapterIngredientes(getApplicationContext(), ingredientes);
        listaCompra.setAdapter(adapterIngredientes);

        Toast.makeText(getApplicationContext(), "Cantidad de ingredientes: " + db.getIngredientes().size(), Toast.LENGTH_SHORT).show();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listaCompra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idIngrediente = ingredientes.get(position).getId();
                txtAgregarIngrediente.setText(ingredientes.get(position).getNombre());
            }
        });

        listaCompra.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                db.eliminarIngrediente(ingredientes.get(position).getId());
                Toast.makeText(getApplicationContext(), "Eliminado", Toast.LENGTH_SHORT).show();
                ingredientes = db.getIngredientes();
                adapterIngredientes = new AdapterIngredientes(getApplicationContext(), ingredientes);
                listaCompra.setAdapter(adapterIngredientes);
                idIngrediente = -1;
                return true;
            }
        });

        btnAgregarIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtAgregarIngrediente.getText().toString().isEmpty()){
                    if (idIngrediente == -1){
                        Ingrediente ingrediente = new Ingrediente(txtAgregarIngrediente.getText().toString());
                        agregar(db, ingrediente, "Agregado correctamente");
                    }else{
                        Ingrediente ingrediente = new Ingrediente(idIngrediente, txtAgregarIngrediente.getText().toString());
                        agregar(db, ingrediente, "Modificado correctamente");
                    }
                }
            }
        });

    }// Fin del onCreate

    public void agregar(BD db, Ingrediente ingrediente, String mensaje){
        db.modificarIngrediente(ingrediente);
        ingredientes = db.getIngredientes();
        adapterIngredientes = new AdapterIngredientes(getApplicationContext(), ingredientes);
        listaCompra.setAdapter(adapterIngredientes);
        idIngrediente = -1;
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
        txtAgregarIngrediente.setText("");
    }

}// Fin de la clase