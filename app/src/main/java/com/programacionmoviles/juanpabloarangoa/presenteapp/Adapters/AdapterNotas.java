package com.programacionmoviles.juanpabloarangoa.presenteapp.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.programacionmoviles.juanpabloarangoa.presenteapp.R;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Notas;

import java.util.ArrayList;

public class AdapterNotas extends RecyclerView.Adapter<AdapterNotas.NotasViewHolder>  {

    private ArrayList<Notas> notasList;
    private int resource;
    private Activity activity;

    public AdapterNotas(ArrayList<Notas> notasList, int resource, Activity activity) {
        this.notasList = notasList;
        this.resource = resource;
        this.activity = activity;
    }

    public AdapterNotas(ArrayList<Notas> notasList){
        this.notasList = notasList;
    }

    @Override
    public NotasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity,"Abre actividad con detalle", Toast.LENGTH_SHORT).show();
            }
        });

        return new AdapterNotas.NotasViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotasViewHolder holder, int position) {
        Notas nota = notasList.get(position);
        holder.bindCurso(nota,activity);
    }

    @Override
    public int getItemCount() {
        return notasList.size();
    }

    public class NotasViewHolder extends RecyclerView.ViewHolder{

        private TextView tGrade,tDescription,tPerc;
        public View view;

        public NotasViewHolder(View itemView) {
            super(itemView);
            tGrade       = itemView.findViewById(R.id.tDescripción);
            tPerc        = itemView.findViewById(R.id.tPorcentaje);
            tDescription = itemView.findViewById(R.id.tNota);
        }

        public void bindCurso(Notas nota, Activity activity) {
            tGrade.setText("Nota: "+String.format("%.2f",nota.getNota()));
            tDescription.setText("Descripcion: "+nota.getDescripcion());
            tPerc.setText("Porcentaje: "+String.valueOf(nota.getPorcentaje())+"%");
            

        }
    }
}
