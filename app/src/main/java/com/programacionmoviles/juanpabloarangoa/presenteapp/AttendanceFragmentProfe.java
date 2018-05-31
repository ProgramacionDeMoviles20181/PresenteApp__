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
import com.programacionmoviles.juanpabloarangoa.presenteapp.Adapters.AdapterAsistenciasProfe;
import com.programacionmoviles.juanpabloarangoa.presenteapp.comunicaciones.OnGetCedulaListener;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Asistencias;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.AsistenciasProfe;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Estudiantes;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class AttendanceFragmentProfe extends Fragment {

    private String sCodeCourse;
    private String nombre,cedula;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<AsistenciasProfe> asistenciasprofeList;
    private AdapterAsistenciasProfe adapterAsistenciasProfe;
    private DataSnapshot data;
    private int faltas = 0;
    private String idEstudiante;

    public AttendanceFragmentProfe() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_attendance, container, false);

        Bundle bundle = getArguments();
        sCodeCourse = bundle.getString("codeCourse");


        recyclerView = view.findViewById(R.id.rvAsistenciasProfe);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        asistenciasprofeList = new ArrayList<>();

        adapterAsistenciasProfe = new AdapterAsistenciasProfe(asistenciasprofeList, R.layout.cardview_asistencias_profe,getActivity());
        recyclerView.setAdapter(adapterAsistenciasProfe);

        final DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        readData(databaseReference.child("Asistencias").child(sCodeCourse), new OnGetCedulaListener() {
            @Override
            public void onSuccess() {
                for(final DataSnapshot snapshotStudent:data.getChildren()){
                    final String idEstudiante = snapshotStudent.getKey();
                    readData2(databaseReference.child("estudiantes").child(idEstudiante), new OnGetCedulaListener() {
                        @Override
                        public void onSuccess() {
                            databaseReference.child("Asistencias").child(sCodeCourse).child(idEstudiante).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        for(DataSnapshot fechas:dataSnapshot.getChildren()){
                                            String date = fechas.getKey();
                                            boolean falta = fechas.child("asistencia").getValue(boolean.class);
                                            String str;
                                            if(falta){
                                                str = "asistió";
                                            }else{
                                                str = "no asistió";
                                            }
                                            Calendar calendar = Calendar.getInstance();
                                            String year = String.valueOf(calendar.get(Calendar.YEAR));
                                            AsistenciasProfe assist = new AsistenciasProfe("Nombre: "+nombre,"Cédula: "+cedula,date+"_"+year+": "+str);
                                            asistenciasprofeList.add(assist);
                                        }

                                        adapterAsistenciasProfe.notifyDataSetChanged();

                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }
                    });




                }

            }
        });

        databaseReference.child("Asistencias").child(sCodeCourse).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }

    private void readData(DatabaseReference ref, final OnGetCedulaListener listener){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                asistenciasprofeList.clear();
                if(dataSnapshot.exists()){
                    data = dataSnapshot;
                    listener.onSuccess();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void readData2(DatabaseReference ref, final OnGetCedulaListener listener){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Estudiantes est = dataSnapshot.getValue(Estudiantes.class);
                    nombre=est.getNombre();
                    cedula=est.getCedula();
                    listener.onSuccess();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
