package com.programacionmoviles.juanpabloarangoa.presenteapp;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.programacionmoviles.juanpabloarangoa.presenteapp.comunicaciones.comunicador_logout;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    comunicador_logout interfaz;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView =  inflater.inflate(R.layout.fragment_profile, container, false);
        final Button bLogout = rootView.findViewById(R.id.bLogout);

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
