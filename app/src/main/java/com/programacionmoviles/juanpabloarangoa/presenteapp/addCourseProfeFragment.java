package com.programacionmoviles.juanpabloarangoa.presenteapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.programacionmoviles.juanpabloarangoa.presenteapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class addCourseProfeFragment extends Fragment {

    EditText eCourseName, eCourseSchedule, eCourseClassroom, eCourseSchool;

    Button bAgregar;

    public addCourseProfeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_course_profe, container, false);

        eCourseName = view.findViewById(R.id.eCourseName);
        eCourseClassroom = view.findViewById(R.id.eClassroom);
        eCourseSchedule = view.findViewById(R.id.eSchedule);
        eCourseSchool = view.findViewById(R.id.eSchool);
        bAgregar = view.findViewById(R.id.bAgregarCursoProfe);

        bAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference databaseReference;
                databaseReference = FirebaseDatabase.getInstance().getReference();

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

            }
        });

        return view;
    }

}
