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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.programacionmoviles.juanpabloarangoa.presenteapp.Adapters.AdapterCursos;
import com.programacionmoviles.juanpabloarangoa.presenteapp.comunicaciones.comunicador_addcourse;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Cursos;

import java.util.ArrayList;
import java.util.List;


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
                    adapterCursos.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else {
            //Is a student
            //Getting the course codes where the student is enrolled
            final List<String> courses_list = new ArrayList<>();

            databaseReference.child("matriculas").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        int i=0;
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            String codigo_curso = (String) dataSnapshot.child(String.valueOf(i)).getValue();
                            courses_list.add(codigo_curso);
                            i++;
                        }
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
                            if(courses_list.contains(cod_curso)){
                                //Log.d("Anadir","Anadiendo curso");
                                //Add the course in the list to display
                                cursosList.add(cursos);
                            }
                        }
                        adapterCursos.notifyDataSetChanged();
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
        }catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString()+"must implement comunicador");
        }


    }
}
