package com.war.amonchar;


import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.war.amonchar.BaseDeDatos.BD;
import com.war.amonchar.Modelo.GlobalVariables;
import com.war.amonchar.Modelo.Usuario;

import java.io.InputStream;
import java.util.concurrent.Executor;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class act_editar_perfil extends AppCompatActivity {

    private ImageView btnBack;
    private Button btnSave;
    private EditText txtNombre, txtApellidos, txtBiografia;
    private ImageView imgUsuario;
    TextView txtCambiarFotografia;
    Uri fotoTemp;
    
    private TextView lblNombreUsuario;
    private Usuario usuarioLog = null;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    //  CONSTANTES
    private final int Galeria = 1;
    private final int Camara = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_editar_perfil);


        final BD bd = new BD(getApplicationContext());
        usuarioLog = ((GlobalVariables) getApplication()).getUsuarioLogueado();

        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
        txtNombre = findViewById(R.id.txtNombre);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtBiografia = findViewById(R.id.txtBiografia);
        imgUsuario = findViewById(R.id.imgUsuario);
        lblNombreUsuario = findViewById(R.id.lblNombreUsuario);
        txtCambiarFotografia = findViewById(R.id.txtCambiarFotografia);

        actualizarCampos();

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(act_editar_perfil.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(), "Error al autenticar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                //modificar datos
                Usuario usuario = usuarioLog;

                if(!txtNombre.getText().toString().isEmpty()) {
                    usuario.setNombre(txtNombre.getText().toString());
                }

                if(!txtApellidos.getText().toString().isEmpty()) {
                    usuario.setApellidos(txtApellidos.getText().toString());
                }

                if(!txtBiografia.getText().toString().isEmpty()){
                    usuario.setBiografia(txtBiografia.getText().toString());
                }


                if(bd.modificarUsuario(usuario)){
                    Toast.makeText(getApplicationContext(), "Perfil modificado", Toast.LENGTH_SHORT).show();
                    limpiar();
                    actualizarCampos();
                    ((GlobalVariables)getApplication()).setUsuarioLogueado(usuario);
                }else{
                    Toast.makeText(getApplicationContext(), "Hubo un problema al guardar", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Intente de nuevo", Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle(getString(R.string.lbl_titulo_autenticacion))
                .setSubtitle(getString(R.string.lbl_subtitulo_autenticacion))
                .setNegativeButtonText(getString(R.string.btn_cancelar_autenticacion))
                .build();


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /****biometricPrompt.authenticate(promptInfo);******/
                Bitmap image = ((BitmapDrawable)imgUsuario.getDrawable()).getBitmap();
                Usuario usuario = new Usuario(txtNombre.getText().toString(), txtApellidos.getText().toString(), txtBiografia.getText().toString(), image);

                /****bd.modificarUsuario(usuario);****/
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
                        if (intent.resolveActivity(getPackageManager()) != null){
                            startActivityForResult(intent, Camara);
                        }

                    }
                });

                alertDialog.show();
            }
        });

    }//Fin onCreate

    public void actualizarCampos(){
        lblNombreUsuario.setText(usuarioLog.getNombreUsuario());

        txtNombre.setHint(usuarioLog.getNombre());
        txtApellidos.setHint(usuarioLog.getApellidos());
        txtBiografia.setHint(usuarioLog.getBiografia());

    }//Fin actualizarCampos

    public void limpiar(){
        txtNombre.setText("");
        txtApellidos.setText("");
        txtBiografia.setText("");
    }//Fin limpiar

  
    public void onActivityResult(int rqCode, int resCode, Intent data) {

        super.onActivityResult(rqCode, resCode, data);
        if (resCode == RESULT_OK){
            switch (rqCode){
                case Galeria:
                    try{
                        fotoTemp = data.getData();
                        InputStream imageStream = getContentResolver().openInputStream(fotoTemp);
                        imgUsuario.setImageBitmap(BitmapFactory.decodeStream(imageStream));
                        Toast.makeText(getApplicationContext(), "Imagen cargada correctamente", Toast.LENGTH_SHORT).show();
                    }
                    catch (IOException exception){
                        exception.printStackTrace();
                    }
                    break;
                case Camara:
                    if(data != null){
                        Bundle extras = data.getExtras();
                        Bitmap image = (Bitmap)extras.get("data");
                        imgUsuario.setImageBitmap(image);

                        /*Bitmap thumball = (Bitmap)data.getExtras().get("data");
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
                        Toast.makeText(getApplicationContext(), "Exito camara", Toast.LENGTH_SHORT).show();*/
                    }
                    break;
            }

        }else{
            Toast.makeText(getApplicationContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
        }

    }// fin del onActivityResult


    /*public void actualizarUsuario(){

    }*/

}// Fin de act_editar_perfil

