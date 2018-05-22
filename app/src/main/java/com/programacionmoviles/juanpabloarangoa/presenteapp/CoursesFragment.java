package com.programacionmoviles.juanpabloarangoa.presenteapp;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.programacionmoviles.juanpabloarangoa.presenteapp.Adapters.AdapterCursos;
import com.programacionmoviles.juanpabloarangoa.presenteapp.comunicaciones.comunicador_addcourse;
import com.programacionmoviles.juanpabloarangoa.presenteapp.comunicaciones.comunicador_isprofe;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Cursos;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.EstudianteCurso;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */


public class CoursesFragment extends Fragment {

    comunicador_addcourse interfaz;
    //comunicador_isprofe interfaz1;

    Boolean boolProfe;

    FloatingActionButton fabPlus;

    private ArrayList<Cursos> cursosList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterCursos;
    private RecyclerView.LayoutManager layoutManager;

    private String sCedula;
    private String codigo_curso;

    //Firebase mierdero
    //private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public CoursesFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cursos, container, false);

        //Get the arguments in order to know if the user is a teacher or a student
        Bundle bundle = getArguments();
        boolProfe = bundle.getBoolean("isprofe");

        String pro = Boolean.toString(boolProfe);

        Log.d("Profesor",pro);


        fabPlus = view.findViewById(R.id.faPlus);
        fabPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaz.addCourse();
            }
        });

        //interfaz para saber si es profesor o estudiante
        //interfaz1.isprofesor();
        //------------------------------------------------

        recyclerView = view.findViewById(R.id.rvCursos);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        cursosList = new ArrayList<>();

        adapterCursos = new AdapterCursos(cursosList, R.layout.cardview_cursos,getActivity());
        recyclerView.setAdapter(adapterCursos);

        FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        /*
        databaseReference.child("cursos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cursosList.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        final Cursos cursos = snapshot.getValue(Cursos.class);

                        if(boolProfe){
                            //Log.d("Entrando",cursos.getId_docente());
                            String id_docente = cursos.getId_docente();
                            String id_user = firebaseUser.getUid();
                            //Is a teacher, so I can check if he is in the courses in order to display just the courses he teaches
                            if(id_docente.equals(id_user)){
                                //Log.d("Anadir","Anadiendo curso");
                                //Add the course in the list to display
                                cursosList.add(cursos);
                            }
                        }else {
                            //Is a student, so I can check if he is enrolled in the courses in order to display them

                            //Obtengo el codigo del curso
                            databaseReference.child("matriculas").

                        }

                    }
                    adapterCursos.notifyDataSetChanged();
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        if(boolProfe){
            databaseReference.child("cursos").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    cursosList.clear();
                    if(dataSnapshot.exists()){
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Cursos cursos = snapshot.getValue(Cursos.class);

                            String id_docente = cursos.getId_docente();
                            String id_user = firebaseUser.getUid();
                            //Is a teacher, so I can check if he is in the courses in order to display just the courses he teaches
                            if(id_docente.equals(id_user)){
                                //Log.d("Anadir","Anadiendo curso");
                                //Add the course in the list to display
                                cursosList.add(cursos);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else {
            //Is a student
            //Getting the course codes where the student is enrolled
            databaseReference.child("matriculas").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            EstudianteCurso estudianteCurso = dataSnapshot.getValue(EstudianteCurso.class);
                            //Creo que codigo cursos debería ser un array de strings para guardar los códigos y luego comparar abajo
                            codigo_curso = estudianteCurso.getCodigoCurso();
                        }

                        EstudianteCurso estudianteCurso = dataSnapshot.getValue(EstudianteCurso.class);

                        codigo_curso = estudianteCurso.getCodigoCurso();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            //Add the courses to the list to display
            databaseReference.child("cursos").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    cursosList.clear();
                    if(dataSnapshot.exists()){
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Cursos cursos = snapshot.getValue(Cursos.class);

                            String cod_curso = cursos.getCodigo();
                            //Is a teacher, so I can check if he is in the courses in order to display just the courses he teaches
                            if(codigo_curso.equals(cod_curso)){
                                //Log.d("Anadir","Anadiendo curso");
                                //Add the course in the list to display
                                cursosList.add(cursos);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }


        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            (interfaz) = (comunicador_addcourse) activity;
            //(interfaz1) = (comunicador_isprofe) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString()+"must implement comunicador");
        }


    }




}
