package com.programacionmoviles.juanpabloarangoa.presenteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    private int registerRequest = 001;
    private EditText eMail,ePassword;
    private String sName,sEmail,sPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        eMail     = findViewById(R.id.eMail);
        ePassword = findViewById(R.id.ePassword);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            sName     = extras.getString("name"    );
            sEmail    = extras.getString("email");
            sPassword = extras.getString("password");
        }else{
            sName     = "";
            sEmail    = "";
            sPassword = "";
        }

        Log.d("Name",sName);
        Log.d("Email",sEmail);
        Log.d("Password",sPassword);
    }

    public void onTextClick(View view) {
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivityForResult(intent,registerRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == registerRequest && resultCode == RESULT_OK){
            sName     = data.getExtras().getString("name"    );
            sEmail    = data.getExtras().getString("email"   );
            sPassword = data.getExtras().getString("password");

            Log.d("Name",sName);
            Log.d("Email",sEmail);
            Log.d("Password",sPassword);

            Toast.makeText(this,"Registro Completado", Toast.LENGTH_LONG).show();
        }else if(requestCode == registerRequest && resultCode == RESULT_CANCELED){
            Toast.makeText(this,"Registro Cancelado" , Toast.LENGTH_LONG).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onButtonClick(View view) {
        if(sEmail.equals("") || sPassword.equals("")){
            Toast.makeText(this,"Falta algún registro" , Toast.LENGTH_LONG).show();
        }else if(sEmail.equals(eMail.getText().toString()) && sPassword.equals(ePassword.getText().toString())){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);

            Log.d("Name",sName);
            Log.d("Email",sEmail);
            Log.d("Password",sPassword);

            intent.putExtra("name"    , sName);
            intent.putExtra("email"   , sEmail);
            intent.putExtra("password", sPassword);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this,"error, correo o contraseña incorrect@" , Toast.LENGTH_LONG).show();
        }
    }
}
