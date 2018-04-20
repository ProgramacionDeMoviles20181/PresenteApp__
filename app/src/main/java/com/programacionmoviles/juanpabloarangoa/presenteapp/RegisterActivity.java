package com.programacionmoviles.juanpabloarangoa.presenteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Estudiantes;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Profesor;


public class RegisterActivity extends AppCompatActivity {

    private EditText eName,eMail,ePassword,eRepPassword, eUniversity, eCarnet, eMobile,eAge;
    private int edad;
    private String sUniversity, sCarnet, sMobile, sName;

    String pass1,pass2;

    String sMail;

    boolean bProfile = true;

    private FirebaseAuth firebaseAuth;

    private FirebaseAuth.AuthStateListener authStateListener;

    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        eName        = findViewById(R.id.eNameR);
        eMail        = findViewById(R.id.eMailR);
        ePassword    = findViewById(R.id.ePasswordR);
        eRepPassword = findViewById(R.id.eRepPasswordR);
        eUniversity  = findViewById(R.id.eInstitucion);
        eCarnet      = findViewById(R.id.eCarnet);
        eMobile      = findViewById(R.id.eCelular);
        eAge         = findViewById(R.id.eAge);

        init_authStateLitener();

    }

    private void init_authStateLitener() {
        firebaseAuth = FirebaseAuth.getInstance();
        // [START auth_state_listener] ,this method execute as soon as there is a change in Auth status , such as user sign in or sign out.
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };
        // [END auth_state_listener]
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

        edad = Integer.parseInt(eAge.getText().toString());

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

    private void createAccount(String mail, String passw) {
        firebaseAuth = FirebaseAuth.getInstance();
        Log.d("ID_USER_CREATION: ", "creating user");
        firebaseAuth.createUserWithEmailAndPassword(mail, passw)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Firebase Message", task.getResult().toString());
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Cuenta creada", Toast.LENGTH_LONG).show();
                            user = task.getResult().getUser();
                            Log.d("ID_USER_CREATION: ", "user_id = "+user.getUid());
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                            if(bProfile){
                                Profesor profe = new Profesor(
                                        user.getUid(),
                                        sName,
                                        sMobile,
                                        edad,
                                        "gs://presenteapp2.appspot.com/improfile.png",
                                        sCarnet,
                                        sUniversity
                                );
                                databaseReference.child("Profesores").child(user.getUid()).setValue(profe);
                            }else{
                                Estudiantes est = new Estudiantes(
                                        user.getUid(),
                                        sName,
                                        sMobile,
                                        edad,
                                        "gs://presenteapp2.appspot.com/improfile.png",
                                        sCarnet,
                                        sUniversity
                                );
                                databaseReference.child("Estudiantes").child(user.getUid()).setValue(est);
                            }

                        } else {
                            Toast.makeText(RegisterActivity.this, "Error al crear la cuenta", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("exception",e.getMessage());
                    }
                });

    }

    public void onRadioButtonClicked(View view) {
        switch(view.getId()) {
            case R.id.rProfe:
                bProfile = true;
                break;
            case R.id.rEstudiante:
                bProfile = false;
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}
