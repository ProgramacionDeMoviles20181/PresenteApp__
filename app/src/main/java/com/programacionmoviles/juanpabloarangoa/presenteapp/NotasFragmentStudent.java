package com.programacionmoviles.juanpabloarangoa.presenteapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.programacionmoviles.juanpabloarangoa.presenteapp.Adapters.AdapterNotas;
import com.programacionmoviles.juanpabloarangoa.presenteapp.comunicaciones.OnGetCedulaListener;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Estudiantes;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Notas;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotasFragmentStudent extends Fragment {

    private String sCodeCourse,sCedulaStud;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Notas> notasList;
    private AdapterNotas adapterNotas;

    public NotasFragmentStudent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notas_fragment_student, container, false);

        Bundle bundle = getArguments();
        sCodeCourse = bundle.getString("codeCourse");

        recyclerView = view.findViewById(R.id.rvNotas);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        notasList = new ArrayList<>();

        adapterNotas = new AdapterNotas(notasList, R.layout.cardview_notas,getActivity());
        recyclerView.setAdapter(adapterNotas);

        final DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        //obtener cedula del estudiante
        readData(databaseReference.child("estudiantes").child(firebaseUser.getUid()), new OnGetCedulaListener() {
            @Override
            public void onSuccess() {
                databaseReference.child("notas").child(sCodeCourse).child(sCedulaStud).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //dataSnapshot.getKey();
                        notasList.clear();
                        if(dataSnapshot.exists()){
                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                Notas nota = snapshot.getValue(Notas.class);

                                notasList.add(nota);

                            }
                            adapterNotas.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        //Ir a la tabla notas, a buscar las notas del estudiante, en el curso actual

        return view;
    }

    private void readData(DatabaseReference ref, final OnGetCedulaListener listener){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Estudiantes estudiantes = dataSnapshot.getValue(Estudiantes.class);
                sCedulaStud = estudiantes.getCedula();
                listener.onSuccess();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
