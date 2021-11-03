package com.war.amonchar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.war.amonchar.Modelo.AdapterDropdownItem;
import com.war.amonchar.Modelo.GlobalVariables;

import java.util.ArrayList;
import java.util.Arrays;

public class act_agregar_receta extends AppCompatActivity {

    // Miembros de clase para elementos UI
    private ImageView btnBack, imgReceta;
    private EditText txtTiempoPreparacion, txtNombreReceta,txtCantidadIngrediente1, txtDescripcionIngrediente1, txtDescripcionPaso1;
    private TextView lblNumeroIngrediente, lblPaso1;
    private ListView ingredientesExtra, pasosExtra;
    private CheckBox catVegetariano, catMar, catCarnesRojas, catCarnesBlancas, catPostres, catBebidas;
    private Button btnAgregarCampoIngrediente, btnAgregarCampoPaso, btnGuardar;
    private Spinner preparacionSpinner, tiemposComidaSpinner;

    // Arrays de opciones para los spinner
    private String[] tiempoPreparacion = {"Minutos", "Horas"};
    private String[] tiempoComida = {"Desayuno", "Merienda de mañana", "Almuerzo", "Merienda de tarde", "Cena", "Merienda de noche"};

    // Formato para las opciones de spinner
    private AdapterDropdownItem adapter;

    // Miembros de clase para la conexión con la base de datos
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    // Constantes de verificación de permisos
    private  static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;

    // Constantes para lanzar activities de cámara y galería
    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALLERY_CODE = 103;

    // Vectores para permisos de cámara y almacenamiento
    private String[] cameraPermissions; // cámara y almacenamiento
    private String [] storagePermissions;// solo almacenamiento

    private Uri imgRecetaTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_agregar_receta);
        getSupportActionBar().hide();

        // Inicialización de elementos de UI
        btnBack = findViewById(R.id.btnBack);
        imgReceta = findViewById(R.id.imgReceta);

        btnAgregarCampoIngrediente = findViewById(R.id.btnAgregarCampoIngrediente);
        btnAgregarCampoPaso = findViewById(R.id.btnAgregarCampoPaso);
        btnGuardar = findViewById(R.id.btnGuardar);

        txtTiempoPreparacion = findViewById(R.id.txtTiempoPreparacion);
        txtNombreReceta = findViewById(R.id.txtNombreReceta);
        txtCantidadIngrediente1 = findViewById(R.id.txtCantidadIngrediente1);
        txtDescripcionIngrediente1 = findViewById(R.id.txtDescripcionIngrediente1);
        txtDescripcionPaso1 = findViewById(R.id.txtDescripcionPaso1);

        lblNumeroIngrediente = findViewById(R.id.lblNumeroIngrediente);
        lblPaso1 = findViewById(R.id.lblPaso1);

        catVegetariano = findViewById(R.id.catVegetariano);
        catMar = findViewById(R.id.catMar);
        catCarnesRojas = findViewById(R.id.catCarnesRojas);
        catCarnesBlancas = findViewById(R.id.catCarnesBlancas);
        catPostres = findViewById(R.id.catPostres);
        catBebidas= findViewById(R.id.catBebidas);

        preparacionSpinner = findViewById(R.id.preparacionSpinner);
        tiemposComidaSpinner = findViewById(R.id.tiemposComidaSpinner);

        ingredientesExtra = findViewById(R.id.ingredientesExtra);
        pasosExtra = findViewById(R.id.pasosExtra);

        // Asignar opciones a los spinner
        asignarSpinner();

        //Inicializa los permisos en los vectores
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        // Inicializa la conexión con la base de datos guardando en la variable databaseReference
        inicializarFirebase();

        // Declaración de eventos onClick
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        imgReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Se muestran las opciones para cargar una fotografía
                obtenerFotografía();

            }
        });

    }// Fin método onCreate

    public void obtenerFotografía(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getString(R.string.lbl_titulo_alertDialog_cargar_fotografia));
        alertDialog.setCancelable(true);

        alertDialog.setPositiveButton(getString(R.string.btn_capturar_Fotografia_camara), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                // Verifica los permisos de cámara
                if (!checkCameraPermission()){
                    // Pide los permisos al usuario
                    requestCameraPermission();
                }
                else{
                    // Muestra el activity de cámara
                    PickFromCamera();
                }
            }
        });

        alertDialog.setNegativeButton(getString(R.string.btn_capturar_Fotografia_galeria), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                // Verifica los permisos de almacenamiento
                if (!checkStoragePermission()){
                    // Pide los permisos al usuario
                    requestStoragePermission();
                }
                else{
                    // Muestra el activity de galería
                    PickFromGallery();
                }

            }
        });

        alertDialog.show();

    }// Fin método obtenerFotografía

    public void inicializarFirebase(){
        // Se inicializa esta clase como objeto de conexión con firebase
        FirebaseApp.initializeApp(this);

        // Se obtiene la instancia de la base de datos de firebase.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // Se obtiene la referencia de la instancia de la base de datos de firebase
        databaseReference = firebaseDatabase.getReference();
    }// Fin método inicializarFirebase

    public void asignarSpinner(){

        ArrayList<String> listTiemposPreparacion = new ArrayList<>(Arrays.asList(tiempoPreparacion));
        ArrayList<String> listTiemposComida = new ArrayList<>(Arrays.asList(tiempoComida));

        adapter  = new AdapterDropdownItem(getApplicationContext(), listTiemposPreparacion);
        preparacionSpinner.setAdapter(adapter);

        adapter  = new AdapterDropdownItem(getApplicationContext(), listTiemposComida);
        tiemposComidaSpinner.setAdapter(adapter);

    }// Fin método asignarSpinner

    public void guardarReceta(){

        //Se verifica que los campos necesarios estén llenos
        if(validar()){

        }else{
            Toast.makeText(getApplicationContext(), getString(R.string.msg_campos_requeridos), Toast.LENGTH_SHORT).show();
        }

    }

    public boolean validar(){




        return true;
    }// Fin método validar

    private void PickFromGallery() {

        // intent para elegir la imagen de la galería, la imagen se devolverá en el método onActivityResult
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);

    } //Fin PickFromGallery

    private void PickFromCamera() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Titulo de la Imagen");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Descripción de la imagen");

        imgRecetaTemp = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        // intent para capturar fotografía con la cámara
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgRecetaTemp);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);

    }// Fin PickFromCamera

    private boolean checkCameraPermission(){

        // Verifica si el permiso de la cámara está habilitado
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result && result1;

    }//Fin checkCameraPermission

    private boolean checkStoragePermission(){

        // Comprobar si el permiso de almacenamiento está habilitado
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result;

    }// Fin checkStoragePermission

    private void requestCameraPermission(){

        // Solicita el permiso de la cámara
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);

    }//Fin requestCameraPermission

    private  void requestStoragePermission(){

        // solicita el permiso de almacenamiento
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);

    }//FIn requestStoragePermission

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Evaluación de la solicitud de permisos
        switch (requestCode){
            case CAMERA_REQUEST_CODE:

                if (grantResults.length>0){
                    // Otorga los permisos
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    // Verifica los permisos obtenidos
                    if(cameraAccepted && storageAccepted){
                        // Lanza el activity de cámara
                        PickFromCamera();
                    }else{
                        Toast.makeText(this, getString(R.string.msg_permiso_camara_almacenamiento_requerido), Toast.LENGTH_SHORT).show();
                    }
                }

            break;

            case STORAGE_REQUEST_CODE:

                if (grantResults.length>0){
                    // Otorga el permiso
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    // Verifica el permiso obtenido
                    if (storageAccepted){
                        // Lanza el activity de galería
                        PickFromGallery();
                    }
                    else{
                        Toast.makeText(this, getString(R.string.msg_permiso_almacenamiento_requerido), Toast.LENGTH_SHORT).show();
                    }
                }

            break;

        }// Fin del switch

    }//Fin método onRequestPermissionsResult

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
                CropImage.activity(imgRecetaTemp)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(16, 9)
                        .start(this);

            }else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                //Croped image received
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK){

                    Uri resultUri = result.getUri();
                    imgRecetaTemp = resultUri;
                    //set Image
                    imgReceta.setImageURI(imgRecetaTemp);
                    Toast.makeText(this, ""+imgReceta, Toast.LENGTH_SHORT).show();

                }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){

                    //ERROR
                    Exception error = result.getError();
                    Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();

                }

            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}