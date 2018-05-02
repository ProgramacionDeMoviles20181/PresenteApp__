package com.programacionmoviles.juanpabloarangoa.presenteapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.EstudianteCurso;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Estudiantes;


/**
 * A simple {@link Fragment} subclass.
 */
public class addCourseStudentFragment extends Fragment {

    EditText eCourseCode;

    Button bAgregarcest;

    String sCedula, sCodigoCurso;

    DatabaseReference databaseReference;


    public addCourseStudentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_course_student, container, false);

        eCourseCode = view.findViewById(R.id.eCourseCodeAdd);
        bAgregarcest = view.findViewById(R.id.bIngresarCursoEst);



        bAgregarcest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sCodigoCurso = eCourseCode.getText().toString();
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                databaseReference = FirebaseDatabase.getInstance().getReference();

                //Extraer cedula del estudiante

                databaseReference.child("estudiantes").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            Estudiantes estudiantes = dataSnapshot.getValue(Estudiantes.class);
                            sCedula = estudiantes.getCedula();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                databaseReference.child("cursos").child(sCodigoCurso).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            //Agrego al estudiante en la tabla cursos
                            EstudianteCurso estudianteCurso = new EstudianteCurso(firebaseUser.getUid(),sCedula);
                            databaseReference.child("cursos").child(sCodigoCurso).child("estudiantes").child(sCedula).setValue(estudianteCurso);
                            Toast.makeText(getActivity(),"Matricula Exitosa",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });





        return view;
    }

}
