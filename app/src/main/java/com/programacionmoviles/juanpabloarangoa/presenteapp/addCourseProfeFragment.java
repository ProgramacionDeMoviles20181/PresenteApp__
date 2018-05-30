package com.programacionmoviles.juanpabloarangoa.presenteapp;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class addCourseProfeFragment extends Fragment {

    LocationManager locationManager;

    EditText eCourseName, eCourseSchedule, eCourseClassroom, eCourseSchool, eNroStu;
    comunicador_addcourse interfaz;

    Button bAgregar;

    private double Latitud, Longitud;

    public addCourseProfeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_course_profe, container, false);

        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        getLocation();

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

                            /*
                            falta quitar espacios en sCode;
                            */
                            Cursos curso = new Cursos(sCourseClassroom,
                                    Latitud,
                                    Longitud,
                                    sCode,
                                    firebaseUser.getDisplayName(),
                                    firebaseUser.getUid(),
                                    sSchedule,
                                    sCourseSchool,
                                    sName,
                                    iNumberStu,
                                    false
                                    );

                            databaseReference.child("cursos").child(sCode).setValue(curso);

                            Toast.makeText(getActivity(),"Curso ingresado exitosamente",Toast.LENGTH_SHORT).show();

                        }
                        interfaz.returnCourse();
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

    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            //REQUEST_LOCATION = 1

        }else {
            Location location_gps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location location_net = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location location = null;
            if(location_gps != null && location_net != null) {
                if (location_gps.getAccuracy() > location_net.getAccuracy()) {
                    location = location_gps;
                } else {
                    location = location_net;
                }
            }else if(location_gps != null){
                location = location_gps;
            }else if(location_net != null){
                location = location_net;
            }

            if(location != null){

                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                Latitud=latitude;
                Longitud=longitude;
                Log.d("Latitud",Double.toString(latitude));
                Log.d("Longitud", Double.toString(longitude));

            }else{
                Log.d("Not found","Unable to find location");
            }
        }

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
