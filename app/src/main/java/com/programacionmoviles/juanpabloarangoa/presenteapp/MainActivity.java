package com.programacionmoviles.juanpabloarangoa.presenteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private String sName,sEmail,sPassword;
    private TextView tSaludo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tSaludo = findViewById(R.id.tSaludo);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            sName     = extras.getString("name"    );
            sEmail    = extras.getString("email"   );
            sPassword = extras.getString("password");
        }else{
            sName     = "";
            sEmail    = "";
            sPassword = "";
        }

        tSaludo.setText("Bienvenido Profesor " + sName);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.mProfile:
                Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                intent.putExtra("name"    , sName);
                intent.putExtra("email"   , sEmail);
                intent.putExtra("password", sPassword);
                startActivity(intent);
                finish();
                break;

            case R.id.mLogout:
                Intent intent2 = new Intent(MainActivity.this,LoginActivity.class);
                intent2.putExtra("name"    , sName);
                intent2.putExtra("email"   , sEmail);
                intent2.putExtra("password", sPassword);
                startActivity(intent2);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
