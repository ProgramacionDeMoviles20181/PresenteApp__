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
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Cursos;

/**
 * A simple {@link Fragment} subclass.
 */
public class addCourseProfeFragment extends Fragment {

    EditText eCourseName, eCourseSchedule, eCourseClassroom, eCourseSchool, eNroStu;

    Button bAgregar;

    public addCourseProfeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_course_profe, container, false);

        eCourseName = view.findViewById(R.id.eCourseName);
        eCourseClassroom = view.findViewById(R.id.eClassroom);
        eCourseSchedule = view.findViewById(R.id.eSchedule);
        eCourseSchool = view.findViewById(R.id.eSchool);
        eNroStu = view.findViewById(R.id.eNumberStudents);
        bAgregar = view.findViewById(R.id.bAgregarCursoProfe);

        bAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference databaseReference;
                databaseReference = FirebaseDatabase.getInstance().getReference();

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                final String sName = eCourseName.getText().toString();
                final String sSchedule = eCourseSchedule.getText().toString();
                final String sCourseClassroom = eCourseClassroom.getText().toString();
                final String sCourseSchool = eCourseSchool.getText().toString();
                final int iNumberStu = Integer.parseInt(eNroStu.getText().toString());


                //Generate random code for the course
                final String  sCode = sName.substring(0,2) + sCourseSchool.substring(0,2) + sSchedule.substring(0,2) + sCourseClassroom.substring(0,2);

                databaseReference.child("cursos").child(sCode).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            //No haga nada
                        }else{
                            //falta mirar como añadir posición actual del profe
                            Cursos curso = new Cursos(sCourseClassroom,
                                    6.1875752,
                                    -75.6524865,
                                    sCode,
                                    firebaseUser.getDisplayName(),
                                    firebaseUser.getUid(),
                                    sSchedule,
                                    sCourseSchool,
                                    sName,
                                    iNumberStu
                                    );

                            databaseReference.child("cursos").child(sCode).setValue(curso);

                            Toast.makeText(getActivity(),"Curso ingresado exitosamente",Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                eCourseSchedule.setText("");
                eCourseClassroom.setText("");
                eCourseName.setText("");
                eCourseSchool.setText("");
                eNroStu.setText("");

            }
        });

        return view;
    }



}
