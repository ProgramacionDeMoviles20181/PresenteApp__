package com.programacionmoviles.juanpabloarangoa.presenteapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.programacionmoviles.juanpabloarangoa.presenteapp.comunicaciones.comunicador_showCourse;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Cursos;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Estudiantes;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Profesor;

public class ShowCouseFragment extends Fragment {

    private TextView tPrueba;
    private String sCodigoCurso,sName,sCodigoUser;

    private DatabaseReference databaseReference;

    private boolean isProfe;

    comunicador_showCourse interfaz;

    public ShowCouseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_show_couse, container, false);
        tPrueba = view.findViewById(R.id.tPrueba);

        Bundle extra = getArguments();
        sCodigoCurso = extra.getString("cursoCodigo");

        FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();



        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        sCodigoUser = firebaseUser.getUid();

        //obtener perfil del usuario y su nombre
        //*********************************************************//
        isProfe = interfaz.getProfileBool();
        if (isProfe){
            databaseReference.child("profesores").child(sCodigoUser).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Profesor profe = dataSnapshot.getValue(Profesor.class);
                    sName = profe.getNombre();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else{
            databaseReference.child("estudiantes").child(sCodigoUser).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Estudiantes estudiante = dataSnapshot.getValue(Estudiantes.class);
                    sName = estudiante.getNombre();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        //*********************************************************//

        databaseReference.child("cursos").child(sCodigoCurso).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //esto lo puse para ver que si se conectara bien a la DB
                //*********************************************************//
                Cursos curso =  dataSnapshot.getValue(Cursos.class);
                String strShow = "curso: ";
                strShow += curso.getNombre() + "\n";

                if(isProfe){
                    strShow += "Profesor: "+sName;
                }else{
                    strShow += "Estudiante: "+sName;
                }
                tPrueba.setText(strShow);
                //*********************************************************//

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            (interfaz) = (comunicador_showCourse) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString()+"must implement comunicador");
        }
    }
}
