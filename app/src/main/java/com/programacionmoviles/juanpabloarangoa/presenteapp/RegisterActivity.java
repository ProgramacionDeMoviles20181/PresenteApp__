package com.programacionmoviles.juanpabloarangoa.presenteapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisterActivity extends AppCompatActivity {

    private EditText eName,eMail,ePassword,eRepPassword;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        eName     = findViewById(R.id.eNameR);
        eMail     = findViewById(R.id.eMailR);
        ePassword = findViewById(R.id.ePasswordR);
        eRepPassword = findViewById(R.id.eRepPasswordR);

        firebaseAuth      = FirebaseAuth.getInstance();
    }

    public void onBackPressed() {
        setResult(RESULT_CANCELED);//status del request
        finish();
        super.onBackPressed();
    }

    public void onButtonClick(View view) {
        Intent intent = new Intent();

        String pass1,pass2;

        pass1 = ePassword.getText().toString();
        pass2 = eRepPassword.getText().toString();

        if(pass1.equals(pass2)) {
            createAccount(eMail.getText().toString(),pass1);
            setResult(RESULT_OK, intent);
            finish();
        }else{
            Toast.makeText(RegisterActivity.this,"Contraseñas diferente" , Toast.LENGTH_LONG).show();
        }
    }

    private void createAccount(String mail, String passw) {
        firebaseAuth.createUserWithEmailAndPassword(mail,passw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this,"Cuenta creada" , Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(RegisterActivity.this,"Error al crear la cuenta" , Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}
