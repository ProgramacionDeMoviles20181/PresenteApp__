package com.programacionmoviles.juanpabloarangoa.presenteapp;


import android.app.Activity;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.programacionmoviles.juanpabloarangoa.presenteapp.comunicaciones.comunicador_addcourse;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Cursos;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.EstudianteCurso;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Estudiantes;


/**
 * A simple {@link Fragment} subclass.
 */
public class addCourseStudentFragment extends Fragment {

    private boolean bIsNotInDatabase=true;

    EditText eCourseCode;
    comunicador_addcourse interfaz;

    Button bAgregarcest;

    String sCedula, sCodigoCurso;

    DatabaseReference databaseReference;

    private Cursos curso = null;
    private int n = 0;

    int cont = 0;

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

                databaseReference.child("estudiantes").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final Estudiantes estudiante = dataSnapshot.getValue(Estudiantes.class);
                        cont = estudiante.getnCursos();
                        //PrUdWV10
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                databaseReference.child("matriculas").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       bIsNotInDatabase = true;
                       if(dataSnapshot.exists()){
                           for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                               String str = (String) snapshot.getValue();
                               if(str.equals(sCodigoCurso)){
                                   bIsNotInDatabase = false;
                                   break;
                               }
                           }
                       }
                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
                });

                if(bIsNotInDatabase) {
                    databaseReference.child("matriculas").child(firebaseUser.getUid()).child(String.valueOf(cont)).setValue(sCodigoCurso);
                    cont++;
                    databaseReference.child("estudiantes").child(firebaseUser.getUid()).child("nCursos").setValue(cont);
                    Toast.makeText(getActivity(),"Curso Agregado",Toast.LENGTH_SHORT).show();
                }else{
                    //error menor, se entra al onDataChange() 2 veces al crear el curso
                    Toast.makeText(getActivity(),"El curso ya se encuentra agregado para esta cuenta",Toast.LENGTH_SHORT).show();
                }
                databaseReference.child("cursos").child(sCodigoCurso).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            curso = dataSnapshot.getValue(Cursos.class);
                            n = curso.getNro_estudiantes();

                        }else{
                            Log.d("ERROR APP","(-1)");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                if(bIsNotInDatabase) {
                    databaseReference.child("cursos").child(sCodigoCurso).child("nro_estudiantes").setValue(n + 1);
                }
            }
        });





        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            (interfaz) = (comunicador_addcourse) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString()+"must implement comunicador");
        }


    }



}
