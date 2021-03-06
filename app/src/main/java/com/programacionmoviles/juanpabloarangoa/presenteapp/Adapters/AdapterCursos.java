package com.programacionmoviles.juanpabloarangoa.presenteapp.Adapters;

import android.app.Activity;
import android.support.v7.widget.ActivityChooserView;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityUnitTestCase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.programacionmoviles.juanpabloarangoa.presenteapp.R;
import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Cursos;

import java.util.ArrayList;
import java.util.List;

public class AdapterCursos extends RecyclerView.Adapter<AdapterCursos.CursoViewHolder> {

    private ArrayList<Cursos> cursosList;
    private int resource;
    private Activity activity;

    public AdapterCursos(ArrayList<Cursos> cursosList, int resource, Activity activity) {
        this.cursosList = cursosList;
        this.resource = resource;
        this.activity = activity;
    }

    public AdapterCursos(ArrayList<Cursos> cursosList){
        this.cursosList = cursosList;
    }

    @Override
    public CursoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity,"Abre actividad con detalle", Toast.LENGTH_SHORT).show();
            }
        });

        return new CursoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CursoViewHolder holder, int position) {
        Cursos curso = cursosList.get(position);
        holder.bindCurso(curso,activity);
    }

    @Override
    public int getItemCount() {
        return cursosList.size();
    }

    public class CursoViewHolder extends RecyclerView.ViewHolder{

        private TextView tNombre, tAula, tHorario, tDocente, tCodigo, tInstitucion;

        public CursoViewHolder(View itemView){
            super(itemView);
            tNombre =  itemView.findViewById(R.id.tNombreCurso);
            tAula = itemView.findViewById(R.id.tAulaCurso);
            tHorario = itemView.findViewById(R.id.tHorarioCurso);
            tDocente = itemView.findViewById(R.id.tDocenteCurso);
            tCodigo = itemView.findViewById(R.id.tCodigoCurso);
            tInstitucion = itemView.findViewById(R.id.tInstitucionCurso);

        }

        public void bindCurso(Cursos curso, Activity activity){
            tNombre.setText(curso.getNombre());
            tAula.setText(curso.getAula());
            tHorario.setText(curso.getHorario());
            tDocente.setText(curso.getDocente());
            tCodigo.setText(curso.getCodigo());
            tInstitucion.setText(curso.getInstitucion());
        }

    }
}
