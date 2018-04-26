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
import com.programacionmoviles.juanpabloarangoa.presenteapp.comunicaciones.comunicador_logout;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Estudiantes;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Profesor;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    comunicador_logout interfaz;

    TextView tProfileName, tProfileMobile, tProfileEmail, tProfileSchool;

    private DatabaseReference databaseReference;

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

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //Miro si es profesor
        databaseReference.child("profesores").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    tProfileName.setText(dataSnapshot.getValue(Profesor.class).getNombre());
                    tProfileEmail.setText(firebaseUser.getEmail());
                    tProfileMobile.setText(dataSnapshot.getValue(Profesor.class).getTelefono());
                    tProfileSchool.setText(dataSnapshot.getValue(Profesor.class).getInstitucion());

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

                    tProfileName.setText(dataSnapshot.getValue(Estudiantes.class).getNombre());
                    tProfileEmail.setText(firebaseUser.getEmail());
                    tProfileMobile.setText(dataSnapshot.getValue(Estudiantes.class).getTelefono());
                    tProfileSchool.setText(dataSnapshot.getValue(Estudiantes.class).getInstitucion());

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


        return rootView;
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            interfaz = (comunicador_logout) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString()+"must implement comunicador");
        }

    }

}
