package com.programacionmoviles.juanpabloarangoa.presenteapp;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.programacionmoviles.juanpabloarangoa.presenteapp.Adapters.AdapterCursos;
import com.programacionmoviles.juanpabloarangoa.presenteapp.comunicaciones.comunicador_addcourse;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Cursos;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.EstudianteCurso;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */


public class CoursesFragment extends Fragment {

    comunicador_addcourse interfaz;

    FloatingActionButton fabPlus;

    private ArrayList<Cursos> cursosList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterCursos;
    private RecyclerView.LayoutManager layoutManager;

    private String sCedula;

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

        databaseReference.child("cursos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cursosList.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Cursos cursos = snapshot.getValue(Cursos.class);
                        cursosList.add(cursos);
                    }
                    adapterCursos.notifyDataSetChanged();
                }
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
            (interfaz) = (comunicador_addcourse) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString()+"must implement comunicador");
        }


    }


}
