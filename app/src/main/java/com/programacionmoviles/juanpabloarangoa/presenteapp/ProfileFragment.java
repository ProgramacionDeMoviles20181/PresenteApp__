package com.programacionmoviles.juanpabloarangoa.presenteapp;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.programacionmoviles.juanpabloarangoa.presenteapp.comunicaciones.comunicador_getProfilePic;
import com.programacionmoviles.juanpabloarangoa.presenteapp.comunicaciones.comunicador_logout;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Estudiantes;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Profesor;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private comunicador_logout interfaz;
    private comunicador_getProfilePic intefaz2;

    private TextView tProfileName, tProfileMobile, tProfileEmail, tProfileSchool;

    private DatabaseReference databaseReference;
    private CircleImageView profileImage;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView =  inflater.inflate(R.layout.fragment_profile, container, false);
        final Button bLogout = rootView.findViewById(R.id.bLogout);

        tProfileEmail = rootView.findViewById(R.id.tProfileEmail);
        tProfileMobile = rootView.findViewById(R.id.tProfileMobile);
        tProfileSchool = rootView.findViewById(R.id.tProfileSchool);
        tProfileName = rootView.findViewById(R.id.tProfileName);
        profileImage = rootView.findViewById(R.id.iFoto1);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //Picasso.get().load(url).into(iFoto);
        //Miro si es profesor
        databaseReference.child("profesores").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Profesor profe = dataSnapshot.getValue(Profesor.class);
                    tProfileName.setText(profe.getNombre());
                    tProfileEmail.setText(firebaseUser.getEmail());
                    tProfileMobile.setText(profe.getTelefono());
                    tProfileSchool.setText(profe.getInstitucion());
                    String urlImage = profe.getFoto();
                    Picasso.get().load(urlImage).into(profileImage);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Miro si es estudiante
        databaseReference.child("estudiantes").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Estudiantes estudiante = dataSnapshot.getValue(Estudiantes.class);
                    tProfileName.setText(estudiante.getNombre());
                    tProfileEmail.setText(firebaseUser.getEmail());
                    tProfileMobile.setText(estudiante.getTelefono());
                    tProfileSchool.setText(estudiante.getInstitucion());
                    String urlImage = estudiante.getFotoLink();
                    Picasso.get().load(urlImage).into(profileImage);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaz.envioDatosLogOut(bLogout.getId());
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intefaz2.loadGalleryImage(profileImage.getId());
            }
        });


        return rootView;
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            interfaz = (comunicador_logout) activity;
            intefaz2 = (comunicador_getProfilePic) activity;

        }catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString()+"must implement comunicador");
        }

    }

}
