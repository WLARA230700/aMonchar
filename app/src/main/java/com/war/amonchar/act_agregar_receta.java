package com.war.amonchar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.war.amonchar.Modelo.AdapterDropdownItem;
import com.war.amonchar.Modelo.AdapterPreparacion;
import com.war.amonchar.Modelo.Adapter_Ingredientes_agregar_receta;
import com.war.amonchar.Modelo.GlobalVariables;
import com.war.amonchar.Modelo.Ingrediente;
import com.war.amonchar.Modelo.PasoPreparacion;
import com.war.amonchar.Modelo.Receta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class act_agregar_receta extends AppCompatActivity {

    // Miembros de clase para elementos UI
    private ImageView btnBack, imgReceta;
    private EditText txtTiempoPreparacion, txtNombreReceta,txtCantidadIngrediente1, txtDescripcionIngrediente1, txtDescripcionPaso1;
    private TextView lblNumeroIngrediente, lblPaso1;
    private CheckBox catVegetariano, catMar, catCarnesRojas, catCarnesBlancas, catPostres, catBebidas;
    private Button btnAgregarCampoIngrediente, btnAgregarCampoPaso, btnGuardar;
    private Spinner preparacionSpinner, tiemposComidaSpinner;
    private LinearLayout ingredientesAgregados, pasosAgregados;

    private ProgressDialog progressDialog;

    // Arrays de opciones para los spinner
    private String[] tiempoPreparacion = {"Minutos", "Horas"};
    private String[] tiempoComida = {"Desayuno", "Merienda de ma??ana", "Almuerzo", "Merienda de tarde", "Cena", "Merienda de noche"};

    // Formato para las opciones de spinner
    private AdapterDropdownItem adapter;

    // Miembros de clase para la conexi??n con la base de datos
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    // Miembros de clase para la conexi??n con Firebase Storage
    private StorageReference storageReference;

    // Constantes de verificaci??n de permisos
    private  static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;

    // Constantes para lanzar activities de c??mara y galer??a
    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALLERY_CODE = 103;

    // Constante de cantidad categor??as
    private static final int QUANTITY_CATEGORIES = 6;

    // Vectores para permisos de c??mara y almacenamiento
    private String[] cameraPermissions; // c??mara y almacenamiento
    private String [] storagePermissions;// solo almacenamiento

    private Uri imgRecetaTemp;
    private Uri imgRecetaSubida;

    private ArrayList<CheckBox> listaCategorias = new ArrayList<>();

    private ArrayList<Ingrediente> listaIngredientes = new ArrayList<>();

    private ArrayList<PasoPreparacion> listaPasos = new ArrayList<>();

    private Adapter_Ingredientes_agregar_receta adapterIngredientes;

    private AdapterPreparacion adapterPreparacion;

    private int contadorPasos = 1;

    private int contadorCategoriasSeleccionadas = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_agregar_receta);
        getSupportActionBar().hide();

        // Inicializaci??n de elementos de UI
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

        ingredientesAgregados = findViewById(R.id.ingredientesAgregados);
        pasosAgregados = findViewById(R.id.pasosAgregados);

        // Asignar opciones a los spinner
        asignarSpinner();

        progressDialog = new ProgressDialog(this);

        inicializarArrayCategorias();

        //Inicializa los permisos en los vectores
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        // Inicializa la conexi??n con la base de datos guardando en la variable databaseReference
        inicializarFirebase();
        verificarCategorias();

        // Declaraci??n de eventos onClick
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarReceta();
            }
        });

        imgReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Se muestran las opciones para cargar una fotograf??a
                obtenerFotograf??a();

            }
        });

        btnAgregarCampoIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarIngrediente();
            }
        });

        btnAgregarCampoPaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarPasos();
            }
        });



    }// Fin m??todo onCreate

    public void crearProgressDialog(){
        progressDialog.setTitle("Subiendo receta...");
        progressDialog.setMessage("No cierre la aplicaci??n mientras se muestra este mensaje");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void inicializarArrayCategorias(){

        listaCategorias.add(catVegetariano);
        listaCategorias.add(catMar);
        listaCategorias.add(catCarnesRojas);
        listaCategorias.add(catCarnesBlancas);
        listaCategorias.add(catPostres);
        listaCategorias.add(catBebidas);

    }//Fin m??todo guardarCategorias

    public void verificarCategorias(){
        for(int i = 0; i < listaCategorias.size(); i++){
            int cont = i;
            listaCategorias.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(contadorCategoriasSeleccionadas < 3 && isChecked){
                        listaCategorias.get(cont).setChecked(true);
                        contadorCategoriasSeleccionadas++;
                    }else{
                        listaCategorias.get(cont).setChecked(false);
                    }

                    if(!isChecked){
                        contadorCategoriasSeleccionadas--;
                    }
                }
            });
        }
    }

    public void obtenerFotograf??a(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getString(R.string.lbl_titulo_alertDialog_cargar_fotografia));
        alertDialog.setCancelable(true);

        alertDialog.setPositiveButton(getString(R.string.btn_capturar_Fotografia_camara), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                // Verifica los permisos de c??mara
                if (!checkCameraPermission()){
                    // Pide los permisos al usuario
                    requestCameraPermission();
                }
                else{
                    // Muestra el activity de c??mara
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
                    // Muestra el activity de galer??a
                    PickFromGallery();
                }

            }
        });

        alertDialog.show();

    }// Fin m??todo obtenerFotograf??a

    public void inicializarFirebase(){
        // Se inicializa esta clase como objeto de conexi??n con firebase
        FirebaseApp.initializeApp(this);

        // Se obtiene la instancia de la base de datos de firebase.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // Se obtiene la referencia de la instancia de la base de datos de firebase
        databaseReference = firebaseDatabase.getReference();

        storageReference = FirebaseStorage.getInstance().getReference();
    }// Fin m??todo inicializarFirebase

    public void asignarSpinner(){

        ArrayList<String> listTiemposPreparacion = new ArrayList<>(Arrays.asList(tiempoPreparacion));
        ArrayList<String> listTiemposComida = new ArrayList<>(Arrays.asList(tiempoComida));

        adapter  = new AdapterDropdownItem(getApplicationContext(), listTiemposPreparacion);
        preparacionSpinner.setAdapter(adapter);

        adapter  = new AdapterDropdownItem(getApplicationContext(), listTiemposComida);
        tiemposComidaSpinner.setAdapter(adapter);

    }// Fin m??todo asignarSpinner

    public void guardarIngrediente(){

        int contador = 0;

        if(!txtDescripcionIngrediente1.getText().toString().equals("") && !txtCantidadIngrediente1.getText().toString().equals("")){

            String cantidadIngrediente = txtCantidadIngrediente1.getText().toString();

            if(!cantidadIngrediente.equals("0")){
                Ingrediente ingrediente = new Ingrediente(txtDescripcionIngrediente1.getText().toString(),
                        txtCantidadIngrediente1.getText().toString());

                listaIngredientes.add(ingrediente);

                adapterIngredientes = new Adapter_Ingredientes_agregar_receta(getApplicationContext(), listaIngredientes);

                if(ingredientesAgregados.getChildCount() > 0){
                    contador = ingredientesAgregados.getChildCount();
                }

                for(int i = contador; i < adapterIngredientes.getCount(); i++){
                    View view = adapterIngredientes.getView(i, null, ingredientesAgregados);
                    ingredientesAgregados.addView(view);
                }

                limpiarCamposIngredientes();
            }else{
                Toast.makeText(getApplicationContext(), "El campo de cantidad debe ser superior a 0", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(getApplicationContext(), "Debe rellenar ambos espacios", Toast.LENGTH_SHORT).show();
        }

    }

    public void limpiarCamposIngredientes(){
        txtCantidadIngrediente1.setText("");
        txtDescripcionIngrediente1.setText("");
    }

    public void guardarPasos(){

        int contador = 0;

        if(!txtDescripcionPaso1.getText().toString().equals("")){

            PasoPreparacion paso = new PasoPreparacion(Integer.parseInt(lblPaso1.getText().toString()),
                    txtDescripcionPaso1.getText().toString());

            listaPasos.add(paso);

            adapterPreparacion = new AdapterPreparacion(getApplicationContext(), listaPasos);

            if(pasosAgregados.getChildCount() > 0){
                contador = pasosAgregados.getChildCount();
            }

            for(int i = contador; i < adapterPreparacion.getCount(); i++){
                View view = adapterPreparacion.getView(i, null, pasosAgregados);
                pasosAgregados.addView(view);
            }

            contadorPasos++;
            lblPaso1.setText(contadorPasos+"");

            txtDescripcionPaso1.setText("");
        }else{
            Toast.makeText(getApplicationContext(), "Debe rellenar la descripci??n del paso", Toast.LENGTH_SHORT).show();
        }

    }

    public ArrayList<String> getCategoriasSeleccionadas(){

        ArrayList<String> categorias = new ArrayList<>();

        for (int i = 0; i < listaCategorias.size(); i++){
            if(listaCategorias.get(i).isChecked()){
                categorias.add(listaCategorias.get(i).getText().toString());
            }
        }

        return categorias;
    }// Fin m??todo getCategoriasSeleccionadas

    public ArrayList<String> getCantidadIngredientes(){

        ArrayList<String> cantIngredientes = new ArrayList<>();

        if(!listaIngredientes.isEmpty()){

            for (int i = 0; i < listaIngredientes.size(); i++){

                cantIngredientes.add(listaIngredientes.get(i).getCantidad());

            }

        }else{
            if(!txtCantidadIngrediente1.getText().equals("")){
                cantIngredientes.add(txtCantidadIngrediente1.getText().toString());
            }
        }

        return cantIngredientes;
    }// Fin m??todo getCantidadIngredientes

    public ArrayList<String> getIngredientes(){

        ArrayList<String> ingredientes = new ArrayList<>();

        if(!listaIngredientes.isEmpty()){
            for (int i = 0; i < listaIngredientes.size(); i++){

                ingredientes.add(listaIngredientes.get(i).getNombre());

            }
        }else{
            if(!txtDescripcionIngrediente1.getText().equals("")){
                ingredientes.add(txtDescripcionIngrediente1.getText().toString());
            }
        }

        return ingredientes;
    }

    public ArrayList<String> getPasos(){

        ArrayList<String> pasos = new ArrayList<>();

        if(!listaPasos.isEmpty()){
            for (int i = 0; i < listaPasos.size(); i++){

                pasos.add(listaPasos.get(i).getDescripcion());

            }
        }else{
            if(!txtDescripcionPaso1.getText().equals("")){
                pasos.add(txtDescripcionPaso1.getText().toString());
            }
        }

        return pasos;
    }

    public void guardarReceta(){

        //Se verifica que los campos necesarios est??n llenos
        if(validar()){

            if(!getCantidadIngredientes().isEmpty() || !getIngredientes().isEmpty() || !getPasos().isEmpty()){

                crearProgressDialog();

                String idReceta = UUID.randomUUID().toString();

                String nombreReceta = txtNombreReceta.getText().toString();

                StorageReference imagesRef = storageReference.child("Fotografia_Recetas");

                final StorageReference nombre_archivo = imagesRef.child(nombreReceta+"_"+idReceta);

                UploadTask uploadTask = nombre_archivo.putFile(imgRecetaTemp);

                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if(!task.isSuccessful()){
                            throw task.getException();
                        }

                        return nombre_archivo.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if(task.isSuccessful()){

                            imgRecetaSubida = task.getResult();

                            Receta receta = new Receta(
                                    idReceta,
                                    Integer.parseInt(txtTiempoPreparacion.getText().toString()),
                                    preparacionSpinner.getSelectedItem().toString(),
                                    tiemposComidaSpinner.getSelectedItem().toString(),
                                    getCategoriasSeleccionadas(),
                                    nombreReceta,
                                    imgRecetaSubida.toString(),
                                    getCantidadIngredientes(),
                                    getIngredientes(),
                                    getPasos());

                            databaseReference.child("Receta").child(receta.getId()).setValue(receta);

                            progressDialog.dismiss();

                            Toast.makeText(getApplicationContext(), getString(R.string.msg_agregado_correctamente), Toast.LENGTH_SHORT).show();

                            finish();

                        }else{
                            Toast.makeText(getApplicationContext(), getString(R.string.msg_error_upload_fotografia), Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Fallo continuaci??n task upload","Resultado del fallo: " + e);
                    }
                });

            }else{
                Toast.makeText(getApplicationContext(), getString(R.string.msg_campos_requeridos), Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(getApplicationContext(), getString(R.string.msg_campos_requeridos), Toast.LENGTH_SHORT).show();
        }

    }

    public boolean validar(){

        if(txtTiempoPreparacion.getText().toString().isEmpty() ||
                getCategoriasSeleccionadas().isEmpty() ||
                txtNombreReceta.getText().toString().isEmpty() ||
                imgRecetaTemp == null ||
                listaIngredientes.isEmpty() ||
                listaPasos.isEmpty()){
            return false;
        }else{
            return true;
        }

    }// Fin m??todo validar

    private void PickFromGallery() {

        // intent para elegir la imagen de la galer??a, la imagen se devolver?? en el m??todo onActivityResult
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);

    } //Fin PickFromGallery

    private void PickFromCamera() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Titulo de la Imagen");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Descripci??n de la imagen");

        imgRecetaTemp = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        // intent para capturar fotograf??a con la c??mara
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgRecetaTemp);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);

    }// Fin PickFromCamera

    private boolean checkCameraPermission(){

        // Verifica si el permiso de la c??mara est?? habilitado
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result && result1;

    }//Fin checkCameraPermission

    private boolean checkStoragePermission(){

        // Comprobar si el permiso de almacenamiento est?? habilitado
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result;

    }// Fin checkStoragePermission

    private void requestCameraPermission(){

        // Solicita el permiso de la c??mara
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);

    }//Fin requestCameraPermission

    private  void requestStoragePermission(){

        // solicita el permiso de almacenamiento
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);

    }//FIn requestStoragePermission

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Evaluaci??n de la solicitud de permisos
        switch (requestCode){
            case CAMERA_REQUEST_CODE:

                if (grantResults.length>0){
                    // Otorga los permisos
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    // Verifica los permisos obtenidos
                    if(cameraAccepted && storageAccepted){
                        // Lanza el activity de c??mara
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
                        // Lanza el activity de galer??a
                        PickFromGallery();
                    }
                    else{
                        Toast.makeText(this, getString(R.string.msg_permiso_almacenamiento_requerido), Toast.LENGTH_SHORT).show();
                    }
                }

            break;

        }// Fin del switch

    }//Fin m??todo onRequestPermissionsResult

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
                        .setAspectRatio(1, 1)
                        .start(this);

            }else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                //Croped image received
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK){

                    Uri resultUri = result.getUri();
                    imgRecetaTemp = resultUri;
                    //set Image
                    imgReceta.setImageURI(imgRecetaTemp);
                    //Toast.makeText(this, ""+imgReceta, Toast.LENGTH_SHORT).show();

                }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){

                    //ERROR
                    Exception error = result.getError();
                    //Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();

                }

            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}