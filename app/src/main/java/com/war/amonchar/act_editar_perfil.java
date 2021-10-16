package com.war.amonchar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.net.Uri;
import android.os.Bundle;
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

import java.util.concurrent.Executor;

public class act_editar_perfil extends AppCompatActivity {

    private ImageView btnBack;
    private Button btnSave;
    private EditText txtNombre, txtApellidos, txtBiografia;
    private LinearLayout btnFotografia;
    private ImageView imgUsuario;
    private Uri imgUsuarioTemp;
    private TextView lblNombreUsuario;
    private Usuario usuarioLog = null;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

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
        btnFotografia = findViewById(R.id.btnFotografia);
        imgUsuario = findViewById(R.id.imgUsuario);
        lblNombreUsuario = findViewById(R.id.lblNombreUsuario);

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
                biometricPrompt.authenticate(promptInfo);
            }
        });

        btnFotografia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Code
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

}//Fin clase act_editar_perfil