package com.programacionmoviles.juanpabloarangoa.presenteapp;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Cursos;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Estudiantes;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartclassFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    MapView mapView;
    GoogleMap mMap;
    boolean boolProfe;
    Cursos course;

    DatabaseReference databaseReference;

    Button bStartclass;
    Button bStopclass;
    TextView tCurrentCourse;

    LocationManager locationManager;
    Estudiantes estudiante;

    private FusedLocationProviderClient mFusedLocationClient;
    private double Latitud;
    private double Longitud;
    private ProgressDialog dialog;
    private double defaultDistance = 0.6;
    private boolean bAux=false;


    public StartclassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_startclass, container, false);


        //Get the arguments in order to know if the user is a teacher or a student
        Bundle bundle = getArguments();
        boolProfe = bundle.getBoolean("isprofe");

        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        mapView = view.findViewById(R.id.map);

        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        bStartclass = view.findViewById(R.id.bStartClass);
        bStopclass  = view.findViewById(R.id.bStopClass);
        tCurrentCourse = view.findViewById(R.id.tCurrentCourse);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Cargando Cursos..");
        dialog.show();

        if(boolProfe) {
            bStartclass.setText("Iniciar clase");
        }
        else {
            bStartclass.setText("Ingresar a clase");
            bStopclass.setVisibility(View.INVISIBLE);
        }

        if(boolProfe){
            databaseReference.child("cursos").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        Cursos cursoActual = null;
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Cursos curso = snapshot.getValue(Cursos.class);
                            String user_id = firebaseUser.getUid();
                            String profe_id = curso.getId_docente();
                            if(boolProfe){
                                if(profe_id.equals(user_id)){
                                    Calendar calendar = Calendar.getInstance();
                                    int day = calendar.get(Calendar.DAY_OF_WEEK);
                                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                                    String dayOfWeek;
                                    switch (day) {
                                        case Calendar.MONDAY:
                                            dayOfWeek = "l";
                                            break;
                                        case Calendar.TUESDAY:
                                            dayOfWeek = "m";
                                            break;
                                        case Calendar.WEDNESDAY:
                                            dayOfWeek = "w";
                                            break;
                                        case Calendar.THURSDAY:
                                            dayOfWeek = "j";
                                            break;
                                        case Calendar.FRIDAY:
                                            dayOfWeek = "v";
                                            break;
                                        default:
                                            dayOfWeek = "s";
                                            break;
                                    }


                                    String horario = curso.getHorario();
                                    String dia1 = String.valueOf(horario.charAt(0));
                                    String dia2 = String.valueOf(horario.charAt(2));
                                    int hora1 = Integer.parseInt(String.valueOf(horario.charAt(4))+String.valueOf(horario.charAt(5)));
                                    int hora2 = Integer.parseInt(String.valueOf(horario.charAt(7))+String.valueOf(horario.charAt(8)));
                                    if(dayOfWeek.equals(dia1) || dayOfWeek.equals(dia2)){
                                        if(hour>=hora1 && hour <hora2) {
                                            cursoActual = curso;
                                            break;
                                        }
                                    }
                                }
                            }

                        }
                        if(cursoActual==null){
                            tCurrentCourse.setText("Usted no tiene clases programadas");
                        }else{
                            String str = "";
                            str += "Clase: "+cursoActual.getNombre()+"\n";
                            str += "Horario: "+cursoActual.getHorario()+"\n";
                            tCurrentCourse.setText(str);
                            bStartclass.setEnabled(true);
                        }
                        course = cursoActual;
                    }
                    dialog.dismiss();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else{
            final List<String> courses_list = new ArrayList<>();

            databaseReference.child("estudiantes").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    estudiante = dataSnapshot.getValue(Estudiantes.class);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            databaseReference.child("matriculas").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(estudiante!=null) {
                        for (int cnt = 0; cnt < estudiante.getnCursos(); cnt++) {
                            String sCurso = dataSnapshot.child(String.valueOf(cnt)).getValue(String.class);
                            courses_list.add(sCurso);
                        }
                    }else{
                        Log.d("ERROR APP","(-2)");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            databaseReference.child("cursos").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        Cursos cursoActual = null;
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Cursos curso = snapshot.getValue(Cursos.class);
                            if(courses_list.contains(curso.getCodigo())){
                                Calendar calendar = Calendar.getInstance();
                                int day = calendar.get(Calendar.DAY_OF_WEEK);
                                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                                String dayOfWeek;
                                switch (day) {
                                    case Calendar.MONDAY:
                                        dayOfWeek = "l";
                                        break;
                                    case Calendar.TUESDAY:
                                        dayOfWeek = "m";
                                        break;
                                    case Calendar.WEDNESDAY:
                                        dayOfWeek = "w";
                                        break;
                                    case Calendar.THURSDAY:
                                        dayOfWeek = "j";
                                        break;
                                    case Calendar.FRIDAY:
                                        dayOfWeek = "v";
                                        break;
                                    default:
                                        dayOfWeek = "s";
                                        break;
                                }


                                String horario = curso.getHorario();
                                String dia1 = String.valueOf(horario.charAt(0));
                                String dia2 = String.valueOf(horario.charAt(2));
                                int hora1 = Integer.parseInt(String.valueOf(horario.charAt(4))+String.valueOf(horario.charAt(5)));
                                int hora2 = Integer.parseInt(String.valueOf(horario.charAt(7))+String.valueOf(horario.charAt(8)));
                                if(dayOfWeek.equals(dia1) || dayOfWeek.equals(dia2)){
                                    if(hour>=hora1 && hour <hora2) {
                                        cursoActual = curso;
                                        break;
                                    }
                                }
                            }
                        }if(cursoActual==null){
                            tCurrentCourse.setText("Usted no tiene clases programadas");
                        }else{
                            String str = "";
                            str += "Clase: "+cursoActual.getNombre()+"\n";
                            str += "Horario: "+cursoActual.getHorario()+"\n";
                            tCurrentCourse.setText(str);
                            bStartclass.setEnabled(true);
                        }
                        course = cursoActual;
                    }
                    dialog.dismiss();
                    refreshLocation();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        bStartclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(boolProfe){
                    databaseReference.child("cursos").child(course.getCodigo()).child("isstarted").setValue(true);
                    bStopclass.setEnabled(true);
                    bStartclass.setEnabled(false);
                }else{
                    databaseReference.child(course.getCodigo()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                course = dataSnapshot.getValue(Cursos.class);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    if(course.isIsstarted()){
                        if(isCloseTo()){
                            Calendar calendar = Calendar.getInstance();
                            final int day = calendar.get(Calendar.DAY_OF_MONTH);
                            final int month = calendar.get(Calendar.MONTH);
                            databaseReference.child("Asistencias").child(course.getCodigo()).child(firebaseUser.getUid())
                                    .child(String.valueOf(day)+"_" +String.valueOf(month)).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(!dataSnapshot.exists()){
                                        databaseReference.child("Asistencias").child(course.getCodigo()).child(firebaseUser.getUid())
                                                .child(String.valueOf(day)+"_" +String.valueOf(month)).child("asistencia").setValue(true);
                                        Toast.makeText(getContext(),"Usted ha ingresado a la clase",Toast.LENGTH_SHORT).show();
                                        bAux = true;
                                    }else{
                                        if(bAux){
                                            bAux=false;
                                        }else{
                                            Toast.makeText(getContext(),"Usted ya ha ingresado a la clase",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }else{
                            Toast.makeText(getContext(),"Usted se encuentra muy lejos de la clase\nFavor acercarse",Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(getContext(),"La clase aún no ha comenzado",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        bStopclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("cursos").child(course.getCodigo()).child("isstarted").setValue(false);
                bStopclass.setEnabled(false);
                bStartclass.setEnabled(true);

            }
        });

        return view;
    }

    private boolean isCloseTo() {
        LatLng courseLoc = new LatLng(course.getLatitud_aula(),course.getLongitud_aula());
        LatLng userLoc   = new LatLng(Latitud,Longitud);
        double distancia = CalculationByDistance(courseLoc,userLoc);

        Log.d("Points Distance",String.valueOf(distancia));

        return (distancia < defaultDistance);
    }

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }

    private void refreshLocation() {
        LatLng courseLocation;
        getLocation();
        LatLng myLocation = new LatLng(Latitud, Longitud);
        if(course != null){
            courseLocation = new LatLng(course.getLatitud_aula(), course.getLongitud_aula());
        }else {
            courseLocation = new LatLng(6.2669533,-75.5691113);
        }
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(courseLocation).title("Universidad de Antioquia").snippet("Alma Mater").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
        //mMap.addMarker(new MarkerOptions().position(myLocation).title("Universidad de Antioquia").snippet("Alma Mater").icon(BitmapDescriptorFactory.fromResource(R.drawable.improfile)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(courseLocation, 17));

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng courseLocation;
        getLocation();
        LatLng myLocation = new LatLng(Latitud, Longitud);
        if(course != null){
            courseLocation = new LatLng(course.getLatitud_aula(), course.getLongitud_aula());
        }else {
            courseLocation = new LatLng(6.2669533,-75.5691113);
        }
        mMap.addMarker(new MarkerOptions().position(courseLocation).title("Universidad de Antioquia").snippet("Alma Mater").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
        //mMap.addMarker(new MarkerOptions().position(myLocation).title("Universidad de Antioquia").snippet("Alma Mater").icon(BitmapDescriptorFactory.fromResource(R.drawable.improfile)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(courseLocation, 17));

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);



    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
