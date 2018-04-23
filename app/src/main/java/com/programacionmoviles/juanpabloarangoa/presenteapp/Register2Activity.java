package com.programacionmoviles.juanpabloarangoa.presenteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Register2Activity extends AppCompatActivity {

    private EditText eName2, eUniversity2, eCarnet2, eMobile2, eMail2, eEdad2;

    private String sUniversity2, sCarnet2, sMobile2, sName2;

    int iEdad2;

    boolean bProfile2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        eName2 = findViewById(R.id.eName2);
        eUniversity2 = findViewById(R.id.eInstitucion2);
        eCarnet2 = findViewById(R.id.eCarnet2);
        eMobile2 = findViewById(R.id.eCelular2);
        eMail2 = findViewById(R.id.eMail2);
        eEdad2 = findViewById(R.id.eAge2);

        //Leo los datos del intent y los seteo en nombre y correo
        Bundle extras = getIntent().getExtras();
        eName2.setText(extras.getString("EXTRA_NAME"));
        eMail2.setText(extras.getString("EXTRA_EMAIL"));
    }

    public void onBackPressed() {
        setResult(RESULT_CANCELED);//status del request
        finish();
        super.onBackPressed();
    }

    public void onButtonClick(View view) {
        Intent intent = new Intent();

        sUniversity2 = eUniversity2.getText().toString();
        sCarnet2 = eCarnet2.getText().toString();
        sMobile2 = eMobile2.getText().toString();
        iEdad2 = Integer.parseInt(eEdad2.getText().toString());



        //Necesito que estos datos que acabe de coger
        // pasarlos en un intent y escribirlos en la BD en el login, toca hacerlo en el on activity result, foto datos
        //Creo que es mejor la segunda opción, pasar los datos en un intent
        //Esto vuelve a la funcion register2_goMainActivity

        intent.putExtra("EXTRA_INSTITUCION", sUniversity2);
        intent.putExtra("EXTRA_CEDULA", sCarnet2);
        intent.putExtra("EXTRA_CELULAR", sMobile2 );
        intent.putExtra("EXTRA_EDAD", iEdad2);
        intent.putExtra("EXTRA_PERFIL", bProfile2);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onRadioButtonClicked(View view) {
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rProfe2:
                bProfile2 = true;
                break;
            case R.id.rEstudiante2:
                bProfile2 = false;
                break;
        }
    }

}
