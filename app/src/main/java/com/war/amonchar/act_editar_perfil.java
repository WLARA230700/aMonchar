package com.war.amonchar;


import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Build;
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

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.war.amonchar.BaseDeDatos.BD;
import com.war.amonchar.Modelo.GlobalVariables;
import com.war.amonchar.Modelo.Usuario;

import java.util.concurrent.Executor;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class act_editar_perfil extends AppCompatActivity{

    private ActionBar actionBar;
    private ImageView btnBack;
    private Button btnSave;
    private EditText txtNombre, txtApellidos, txtBiografia;
    private CircleImageView imgUsuario;
    private String  nombreUsuario, correo, contrasenia, nombre, apellidos, biografia;
    TextView txtCambiarFotografia;
    Uri fotoTemp;

    private TextView lblNombreUsuario;
    private Usuario usuarioLog = null;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    //Permiso de la clase Constants
    private  static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;
    //selección de imagen Constants
    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALLERY_CODE = 103;
    // matrices de permisos
    private String[] cameraPermissions; // cámara y almacenamiento
    private String [] storagePermissions;// solo almacenamiento
    private final int auth = 3;

    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;

    private BD globalDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_editar_perfil);

        actionBar = getSupportActionBar();
        //Titulo
        actionBar.setTitle("Editar Perfil");
        //Boton Negro
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        final BD bd = new BD(getApplicationContext());
        globalDB = bd;
        usuarioLog = ((GlobalVariables) getApplication()).getUsuarioLogueado();

        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
        txtNombre = findViewById(R.id.txtNombre);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtBiografia = findViewById(R.id.txtBiografia);
        imgUsuario = findViewById(R.id.imgUsuario);
        lblNombreUsuario = findViewById(R.id.lblNombreUsuario);
        txtCambiarFotografia = findViewById(R.id.txtCambiarFotografia);

        imgUsuario.setImageDrawable(getDrawable(R.drawable.ic_perfil));
        if(!usuarioLog.getFotografia().toString().equals("")){
            imgUsuario.setImageURI(usuarioLog.getFotografia());
        }

        fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
        keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

        //Inicializamos Permisos arrays
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

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
                modificarDatos();

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Intente de nuevo", Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle(getString(R.string.lbl_titulo_autenticacion))
                .setSubtitle(getString(R.string.lbl_subtitulo_autenticacion_fingerprint))
                .setNegativeButtonText(getString(R.string.btn_cancelar_autenticacion))
                .build();




        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fingerprintManager != null){

                    if(fingerprintManager.isHardwareDetected()){

                        if(fingerprintManager.hasEnrolledFingerprints()){
                            biometricPrompt.authenticate(promptInfo);
                        }else{
                            if(keyguardManager.isDeviceSecure()){
                                showPasswordScreen();
                            }else{
                                modificarDatos();
                            }
                        }

                    }else{
                        if(keyguardManager.isDeviceSecure()){
                            showPasswordScreen();
                        }else{
                            modificarDatos();
                        }
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                }
            }
        });

      txtCambiarFotografia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // muestra el cuadro de diálogo de selección de imagen
                imagePickDialog();
            }
        });

    }//Fin onCreate

    private void imagePickDialog(){
        // opciones para mostrar en el diálogo
        String[] options = {"Camara", "Galeria"};
        //dialogo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Titulo
        builder.setTitle("Seleccionar imagen");
        // establecer elementos / opciones
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // manejar clicks
                if (which==0){
                    //click en camara
                    if (!checkCameraPermission()){
                        requestCameraPermission();
                    }
                    else{
                        // permiso ya otorgado
                        PickFromCamera();
                    }

                }
                else if (which==1){
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    }
                    else{
                        // permiso ya otorgado
                        PickFromGallery();
                    }
                }
            }
        });

        // Crear / mostrar diálogo
        builder.create().show();
    }// Fin imagePickDialog

    private void PickFromGallery() {
        // intento de elegir la imagen de la galería, la imagen se devolverá en el método onActivityResult
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
    } //Fin PickFromGallery

    private void PickFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Titulo de la Imagen");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Descripción de la imagen");
        //put image Uri
        fotoTemp = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        // Intento de abrir la cámara para la imagen
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fotoTemp);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }// Fin PickFromCamera

    private boolean checkStoragePermission(){
        //comprobar si el permiso de almacenamiento está habilitado o no
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result;
    }// Fin checkStoragePermission

    private  void requestStoragePermission(){
        // solicita el permiso de almacenamiento
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }//FIn requestStoragePermission

    private boolean checkCameraPermission(){
        // verifica si el permiso de la cámara está habilitado o no
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }//Fin checkCameraPermission

    private void requestCameraPermission(){
        // solicita el permiso de la cámara
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }//Fin requestCameraPermission

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed(); //regrese haciendo clic en el botón de barra de acción
        return super.onSupportNavigateUp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // resultado del permiso permitido / denegado

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if (grantResults.length>0){

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if(cameraAccepted && storageAccepted){
                        // ambos permisos permitidos
                        PickFromCamera();
                    }
                    else{
                        Toast.makeText(this, "Se requieren permisos de cámara y almacenamiento", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;
            case STORAGE_REQUEST_CODE:{
                if (grantResults.length>0){

                    // si se permite devolver verdadero de lo contrario falso
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted){
                        // permiso de almacenamiento permitido
                        PickFromGallery();
                    }
                    else{
                        Toast.makeText(this, "Se requiere permiso de almacenamiento", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //image picked from camera or gallery will be received hare
        if (resultCode == RESULT_OK){
            //Image is picked
            if(requestCode == IMAGE_PICK_GALLERY_CODE){
                //Picked from gallery

                //crop image
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);

            }
            else if(requestCode == IMAGE_PICK_CAMERA_CODE){
                //Picked from camera
                //crop Image
                CropImage.activity(fotoTemp)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);

            }
            else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                //Croped image received
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK){
                    Uri resultUri = result.getUri();
                    fotoTemp = resultUri;
                    //set Image
                    imgUsuario.setImageURI(fotoTemp);
                    Toast.makeText(this, ""+imgUsuario, Toast.LENGTH_SHORT).show();
                }
                else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    //ERROR
                    Exception error = result.getError();
                    Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
                }

            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

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

    public void showPasswordScreen(){

        KeyguardManager keyguardManager = ContextCompat
                .getSystemService(this, KeyguardManager.class);
        if (keyguardManager != null
                || android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.LOLLIPOP) {

            if (keyguardManager.isKeyguardSecure()) {
                Intent intent = keyguardManager
                        .createConfirmDeviceCredentialIntent(getString(R.string.lbl_titulo_autenticacion), getString(R.string.lbl_subtitulo_autenticacion_pincode));
                this.startActivityForResult(intent, auth);
            } else {
                finishAffinity();
            }

        }

    }//Fin metodo showPasswordScreen

    public void modificarDatos(){

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

        if(fotoTemp != null){
            usuario.setFotografia(fotoTemp);
        }

        if(globalDB.modificarUsuario(usuario)){
            Toast.makeText(getApplicationContext(), "Perfil modificado", Toast.LENGTH_SHORT).show();
            limpiar();
            actualizarCampos();
            ((GlobalVariables)getApplication()).setUsuarioLogueado(usuario);
            finish();
        }else{
            Toast.makeText(getApplicationContext(), "Hubo un problema al guardar", Toast.LENGTH_SHORT).show();
        }



    }//Fin metodo modificarDatos

}// Fin de act_editar_perfil

