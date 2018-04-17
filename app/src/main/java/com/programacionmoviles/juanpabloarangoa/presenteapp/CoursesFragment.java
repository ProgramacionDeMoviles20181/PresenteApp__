package com.programacionmoviles.juanpabloarangoa.presenteapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */


public class CoursesFragment extends Fragment {

    //private ArrayList<Curso> cursos;
    private RecyclerView rvCursos;

    public CoursesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cursos, container, false);

        rvCursos = (RecyclerView) v.findViewById(R.id.rvCursos);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rvCursos.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvCursos.setLayoutManager(linearLayoutManager);

        //inicializarDatos();
        //inicializarAdaptador();

        return v;
    }

    /*
    public void inicializarDatos(){
        cursos = new ArrayList<>();
        cursos.add("Matematicas I", "UdeA", "MJ 14-16");
        cursos.add("Control I", "UdeA", "WV 6-8");
    }
    */
    /*
    public CursoAdaptador adaptador;
    private void inicializarAdaptador(){
        adaptador = new CursoAdaptador(cursos,getActivity());
        rvCursos.setAdapter(adaptador);
    }
    */
}
