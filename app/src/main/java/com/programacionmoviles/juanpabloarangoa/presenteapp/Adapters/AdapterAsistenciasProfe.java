package com.programacionmoviles.juanpabloarangoa.presenteapp.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.programacionmoviles.juanpabloarangoa.presenteapp.R;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Asistencias;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.AsistenciasProfe;

import java.util.ArrayList;

public class AdapterAsistenciasProfe extends RecyclerView.Adapter<AdapterAsistenciasProfe.AsistenciasProfeViewHolder> {

    private ArrayList<AsistenciasProfe> asistenciasprofeList;
    private int resource;
    private Activity activity;

    public AdapterAsistenciasProfe(ArrayList<AsistenciasProfe> asistenciasprofeList, int resource, Activity activity) {
        this.asistenciasprofeList = asistenciasprofeList;
        this.resource = resource;
        this.activity = activity;
    }

    public AdapterAsistenciasProfe(ArrayList<AsistenciasProfe> asistenciasprofeList){
        this.asistenciasprofeList = asistenciasprofeList;
    }

    @Override
    public AsistenciasProfeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity,"Abre actividad con detalle", Toast.LENGTH_SHORT).show();
            }
        });

        return new AdapterAsistenciasProfe.AsistenciasProfeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AsistenciasProfeViewHolder holder, int position) {
        AsistenciasProfe asistenciasProfe = asistenciasprofeList.get(position);
        holder.bindAsistenciasProfe(asistenciasProfe,activity);
    }

    @Override
    public int getItemCount() {
        return asistenciasprofeList.size();
    }


    public class AsistenciasProfeViewHolder extends RecyclerView.ViewHolder {

        private TextView tNombreEstudiante,tAsistencia, tCedulaEstudiante;
        public View view;

        public AsistenciasProfeViewHolder(View itemView) {
            super(itemView);
            tNombreEstudiante       = itemView.findViewById(R.id.tNombreEstudiante);
            tCedulaEstudiante    = itemView.findViewById(R.id.tCedulaEstudiante);
            tAsistencia        = itemView.findViewById(R.id.tAsistencia);
        }

        public void bindAsistenciasProfe(AsistenciasProfe asistenciasProfe, Activity activity) {
            tNombreEstudiante.setText(asistenciasProfe.getNombreEstudiante());
            tCedulaEstudiante.setText(asistenciasProfe.getCedula());
            tAsistencia.setText(asistenciasProfe.getAsistencia());
        }
    }
}
