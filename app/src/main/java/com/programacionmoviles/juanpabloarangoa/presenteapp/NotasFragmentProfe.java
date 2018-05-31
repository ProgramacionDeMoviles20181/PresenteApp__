package com.programacionmoviles.juanpabloarangoa.presenteapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Notas;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotasFragmentProfe extends Fragment {

    private String sCodeCourse;
    private EditText eCedula, eNumNota, ePorcentajeNota, eDescripcionNota, eValorNota;
    Button bAgregarNota;

    public NotasFragmentProfe() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_notas, container, false);

        Bundle bundle = getArguments();
        sCodeCourse = bundle.getString("codeCourse");

        eCedula = view.findViewById(R.id.eCedulaStudent);
        eNumNota = view.findViewById(R.id.eNumeroNota);
        ePorcentajeNota = view.findViewById(R.id.ePorcentajeNota);
        eDescripcionNota = view.findViewById(R.id.eDescripcionNota);
        eValorNota = view.findViewById(R.id.eValorNota);
        bAgregarNota = view.findViewById(R.id.bIngresarNota);

        bAgregarNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DatabaseReference databaseReference;
                databaseReference = FirebaseDatabase.getInstance().getReference();

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                int value1 = Integer.parseInt(eNumNota.getText().toString());
                float valueNota = Float.parseFloat(eValorNota.getText().toString());
                //databaseReference.child("notas").child(sCodeCourse).child(eCedula.getText().toString()).child(eNumNota.getText().toString()).setValue(valueNota);

                String sDescripcionNota = eDescripcionNota.getText().toString();
                double dValorNota = Double.parseDouble(eValorNota.getText().toString());
                int iPorcentaje = Integer.parseInt(ePorcentajeNota.getText().toString());

                Notas notas = new Notas(sDescripcionNota,dValorNota,iPorcentaje);

                databaseReference.child("notas").child(sCodeCourse).child(eCedula.getText().toString()).child(eNumNota.getText().toString()).setValue(notas);
                Toast.makeText(getContext(),"Nota ingresada exitosamente",Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

}
