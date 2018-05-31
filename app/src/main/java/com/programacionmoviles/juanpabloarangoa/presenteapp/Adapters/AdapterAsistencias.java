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
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Notas;

import java.util.ArrayList;

public class AdapterAsistencias extends RecyclerView.Adapter<AdapterAsistencias.AsistenciasViewHolder>  {

    private ArrayList<Asistencias> asistenciasList;
    private int resource;
    private Activity activity;

    public AdapterAsistencias(ArrayList<Asistencias> asistenciasList, int resource, Activity activity) {
        this.asistenciasList = asistenciasList;
        this.resource = resource;
        this.activity = activity;
    }

    public AdapterAsistencias(ArrayList<Asistencias> asistenciasList){
        this.asistenciasList = asistenciasList;
    }

    @Override
    public AdapterAsistencias.AsistenciasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity,"Abre actividad con detalle", Toast.LENGTH_SHORT).show();
            }
        });

        return new AdapterAsistencias.AsistenciasViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterAsistencias.AsistenciasViewHolder holder, int position) {
        Asistencias asistencias = asistenciasList.get(position);
        holder.bindAsistencias(asistencias,activity);
    }

    @Override
    public int getItemCount() {
        return asistenciasList.size();
    }

    public class AsistenciasViewHolder extends RecyclerView.ViewHolder {

        private TextView tFecha,tAsistio;
        public View view;

        public AsistenciasViewHolder(View itemView) {
            super(itemView);
            tFecha       = itemView.findViewById(R.id.tFecha);
            tAsistio        = itemView.findViewById(R.id.tAsistio);
        }

        public void bindAsistencias(Asistencias asistencias, Activity activity) {
            tFecha.setText(asistencias.getFecha());
            boolean asistio = asistencias.isAsistio();
            String sAsistio;
            if(asistio){
                sAsistio = "asisitió";
            }else {
                sAsistio = "no asistió";
            }
            tAsistio.setText(sAsistio);


        }

    }
}
