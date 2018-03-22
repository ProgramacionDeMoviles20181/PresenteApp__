package com.programacionmoviles.juanpabloarangoa.presenteapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ProfileActivity extends AppCompatActivity {
    private String sName,sEmail,sPassword;
    private TextView tShowName, tShowEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tShowName = findViewById(R.id.tShowName);
        tShowEmail = findViewById(R.id.tShowEmail);

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

        tShowName.setText(sName);
        tShowEmail.setText(sEmail);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2,menu);
        return true;
    }

    @SuppressLint("LongLogTag")
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.d("Iniciando onOptionsItemSelected()","");
        switch (id){
            case R.id.mMain:
                Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
                intent.putExtra("name"    , sName);
                intent.putExtra("email"   , sEmail);
                intent.putExtra("password", sPassword);

                Log.d("Name",sName);
                Log.d("Email",sEmail);
                Log.d("Password",sPassword);

                startActivity(intent);
                finish();
                break;

            case R.id.mLogout2:
                Intent intent2 = new Intent(ProfileActivity.this,LoginActivity.class);
                intent2.putExtra("name"    , sName);
                intent2.putExtra("email"   , sEmail);
                intent2.putExtra("password", sPassword);

                Log.d("Name",sName);
                Log.d("Email",sEmail);
                Log.d("Password",sPassword);

                startActivity(intent2);
                finish();
                break;
        }

        Log.d("Cerrando onOptionsItemSelected()","");

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
        intent.putExtra("name"    , sName);
        intent.putExtra("email"   , sEmail);
        intent.putExtra("password", sPassword);

        startActivity(intent);
        finish();

        super.onBackPressed();
    }
}
