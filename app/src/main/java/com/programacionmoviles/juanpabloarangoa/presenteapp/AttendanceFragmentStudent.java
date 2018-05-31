package com.programacionmoviles.juanpabloarangoa.presenteapp;


import android.os.Bundle;
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
import com.programacionmoviles.juanpabloarangoa.presenteapp.Adapters.AdapterAsistencias;
import com.programacionmoviles.juanpabloarangoa.presenteapp.Adapters.AdapterNotas;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Asistencias;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AttendanceFragmentStudent extends Fragment {

    private String sCodeCourse;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Asistencias> asistenciasList;
    private AdapterAsistencias adapterAsistencias;
    boolean asist;

    public AttendanceFragmentStudent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_attendance_fragment_student, container, false);

        Bundle bundle = getArguments();
        sCodeCourse = bundle.getString("codeCourse");

        recyclerView = view.findViewById(R.id.rvAsistencias);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        asistenciasList = new ArrayList<>();

        adapterAsistencias = new AdapterAsistencias(asistenciasList, R.layout.cardview_asistencias,getActivity());
        recyclerView.setAdapter(adapterAsistencias);

        final DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        databaseReference.child("Asistencias").child(sCodeCourse).child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                asistenciasList.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        String fecha = snapshot.getKey();
                        asist= snapshot.child("asistencia").getValue(boolean.class);
                        /*databaseReference.child("Asistencias").child(sCodeCourse).child(firebaseUser.getUid())
                                .child(fecha).child("asistencia").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    asist = dataSnapshot.getValue(boolean.class);
                                    Log.d("Booleano",String.valueOf(asist));
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });*/

                        Log.d("Booleano",String.valueOf(asist));
                        Asistencias asistencias = new Asistencias(fecha+"_2018",asist);

                        asistenciasList.add(asistencias);
                    }
                    adapterAsistencias.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

}
