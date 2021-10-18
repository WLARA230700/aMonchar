package com.war.amonchar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.war.amonchar.BaseDeDatos.BD;
import com.war.amonchar.Modelo.Usuario;

public class act_editar_perfil extends AppCompatActivity {

    private static final int GALLERY_REQUEST_CODE = 100;

    ImageView btnBack, imgUsuario;
    TextView txtCambiarFotografia;
    EditText txtNombre, txtApellidos, txtBiografia;
    private Button btnSave;
    Uri fotoTemp;

    private final int Galeria = 1;
    private final int Camara = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_editar_perfil);

        final BD db = new BD(getApplicationContext());

        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
        imgUsuario = findViewById(R.id.imgUsuario);
        txtCambiarFotografia = findViewById(R.id.txtCambiarFotografia);
        txtNombre = findViewById(R.id.txtApellidos);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtBiografia = findViewById(R.id.txtBiografia);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario usuario = new Usuario(txtNombre.getText().toString(), txtApellidos.getText().toString(), txtBiografia.getText().toString(), fotoTemp);
                db.modificarUsuario(usuario);
                Toast.makeText(getApplicationContext(), "Cambios guardados", Toast.LENGTH_SHORT).show();
            }
        });

        txtCambiarFotografia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(act_editar_perfil.this);
                alertDialog.setTitle("Seleccione una imagen para su perfil");
                alertDialog.setMessage("¿Que desea utilizar?");
                //alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setIcon(android.R.drawable.btn_default);
                alertDialog.setCancelable(false);

                alertDialog.setPositiveButton("Galeria", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        //Se busca y se inserta una imagen mediante el llamado al metodo de startActivityForResult
                        startActivityForResult(Intent.createChooser(intent, "Seleccionar foto"), Galeria);
                    }
                });
                alertDialog.setNegativeButton("Camara", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, Camara);
                    }
                });

                alertDialog.show();
            }
        });

    }//Fin del Oncreate

    public void onActivityResult(int rqCode, int resCode, Intent data) {

        super.onActivityResult(rqCode, resCode, data);
        if (resCode == RESULT_OK){
            switch (rqCode){
                case Galeria:
                    fotoTemp = data.getData();
                    imgUsuario.setImageURI(fotoTemp);
                    Toast.makeText(getApplicationContext(), "Imagen cargada correctamente", Toast.LENGTH_SHORT).show();
                    break;
                case Camara:
                    if(data != null){
                        Bitmap thumball = (Bitmap)data.getExtras().get("data");
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        thumball.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis()+".jpg");
                        FileOutputStream fo;
                        try{
                            destination.createNewFile();
                            fo = new FileOutputStream(destination);
                            fo.write(bytes.toByteArray());
                            fo.close();
                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }catch (IOException ex){
                            ex.printStackTrace();
                        }
                        imgUsuario.setImageBitmap(thumball);
                        //error de la cámara
                        fotoTemp = data.getData();
                        Toast.makeText(getApplicationContext(), "Exito camara", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

        }else{
            Toast.makeText(getApplicationContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
        }

    }// fin del onActivityResult


    public void actualizarUsuario(){

    }

}// Fin de act_editar_perfil