package com.programacionmoviles.juanpabloarangoa.presenteapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.programacionmoviles.juanpabloarangoa.presenteapp.Adapters.AdapterCourses;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Cursos;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */


public class CoursesFragment extends Fragment {
    public CoursesFragment() { }

    private ArrayList<Cursos> cursosList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterCursos;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference databaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cursos, container, false);

        recyclerView = view.findViewById(R.id.rvCursos);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        cursosList = new ArrayList<>();

        adapterCursos = new AdapterCourses(cursosList,
                R.layout.cardview_cursos,
                getActivity());
        recyclerView.setAdapter(adapterCursos);

        FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("cursos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cursosList.clear();
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Cursos curso = snapshot.getValue(Cursos.class);
                        cursosList.add(curso);
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
}
