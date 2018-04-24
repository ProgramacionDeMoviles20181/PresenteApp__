package com.programacionmoviles.juanpabloarangoa.presenteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisterActivity extends AppCompatActivity {

    private EditText eName,eMail,ePassword,eRepPassword, eUniversity, eCarnet, eMobile;

    private String sUniversity, sCarnet, sMobile, sName;

    String pass1,pass2;

    String sMail;

    boolean bProfile = true;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private FirebaseAuth.AuthStateListener authStateListener;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        eName     = findViewById(R.id.eNameR);
        eMail     = findViewById(R.id.eMailR);
        ePassword = findViewById(R.id.ePasswordR);
        eRepPassword = findViewById(R.id.eRepPasswordR);
        eUniversity = findViewById(R.id.eInstitucion);
        eCarnet = findViewById(R.id.eCarnet);
        eMobile = findViewById(R.id.eCelular);

    }

    public void onBackPressed() {
        setResult(RESULT_CANCELED);//status del request
        finish();
        super.onBackPressed();
    }

    public void onButtonClick(View view) {
        Intent intent = new Intent();



        sUniversity = eUniversity.getText().toString();
        sCarnet = eCarnet.getText().toString();
        sMobile = eMobile.getText().toString();
        sName = eName.getText().toString();



        pass1 = ePassword.getText().toString();
        pass2 = eRepPassword.getText().toString();

        sMail   = eMail.getText().toString().trim();
        String ePattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (sMail.matches(ePattern))
        {
            if(!sMail.contains(" ")){
                if(pass1.equals(pass2)) {
                    if (pass1.contains(" ")){
                        Toast.makeText(RegisterActivity.this,"Contraseña con espacios no recomendable" , Toast.LENGTH_LONG).show();
                    }else{
                        createAccount(sMail,pass1);
                        verifyFirebaseDataBase();
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                }else{
                    Toast.makeText(RegisterActivity.this,"Contraseñas diferentes" , Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(RegisterActivity.this,"Error, correo con espacios" , Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(RegisterActivity.this,"Email inválido", Toast.LENGTH_SHORT).show();
        }
    }

    private void verifyFirebaseDataBase() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(sMail,pass1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String Uid_user = task.getResult().getUser().getUid();
                    Log.d("Uid",Uid_user);
                }
            }
        });

        firebaseAuth.signOut();
    }

    private void createAccount(String mail, String passw) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(mail, passw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Firebase Message", task.getResult().toString());
                        if (task.isSuccessful()) {
                            String Uid_user = task.getResult().getUser().getUid();
                            Log.d("userID", Uid_user);
                            Toast.makeText(RegisterActivity.this, "Cuenta creada", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Error al crear la cuenta", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    public void onRadioButtonClicked(View view) {
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rProfe:
                bProfile = true;
                break;
            case R.id.rEstudiante:
                bProfile = false;
                break;
        }
    }
}
