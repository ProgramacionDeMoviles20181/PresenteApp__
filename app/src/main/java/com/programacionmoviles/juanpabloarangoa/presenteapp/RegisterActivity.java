package com.programacionmoviles.juanpabloarangoa.presenteapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterActivity extends AppCompatActivity {

    private EditText eName,eMail,ePassword,eRepPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        eName     = findViewById(R.id.eNameR);
        eMail     = findViewById(R.id.eMailR);
        ePassword = findViewById(R.id.ePasswordR);
        eRepPassword = findViewById(R.id.eRepPasswordR);
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

        Log.d("password1",pass1);
        Log.d("password2",pass2);

        if(pass1.equals(pass2)) {
            intent.putExtra("name", eName.getText().toString());
            intent.putExtra("email", eMail.getText().toString());
            intent.putExtra("password", ePassword.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        }else{
            Toast.makeText(this,"Contraseñas diferente" , Toast.LENGTH_LONG).show();
        }
    }
}
