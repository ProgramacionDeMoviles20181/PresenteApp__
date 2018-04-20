package com.programacionmoviles.juanpabloarangoa.presenteapp.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.programacionmoviles.juanpabloarangoa.presenteapp.modelo.Cursos;
import com.programacionmoviles.juanpabloarangoa.presenteapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterCourses extends RecyclerView.Adapter<AdapterCourses.CoursesViewHolder>{

    private ArrayList<Cursos> cursosList;
    private int resource;
    private Activity activity;

    public AdapterCourses(ArrayList<Cursos> cursosList) {
        this.cursosList = cursosList;
    }

    public AdapterCourses(ArrayList<Cursos> cursosList, int resource, Activity activity) {
        this.cursosList = cursosList;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public CoursesViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity,"Abre actividad con detalle",Toast.LENGTH_SHORT).show();
            }
        });

        return new CoursesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CoursesViewHolder holder,int postition){
        Cursos cursos = cursosList.get(postition);
        holder.bindCourses(cursos,activity);

    }

    @Override
    public int getItemCount(){
        return cursosList.size();
    }

    public class CoursesViewHolder extends RecyclerView.ViewHolder{
        private TextView tNameCourse,tTeacherCourse,tDateCourse;
        private CircleImageView iFoto;

        public CoursesViewHolder(View itemView) {
            super(itemView);
            tNameCourse    = itemView.findViewById(R.id.tNameCourse);
            tTeacherCourse = itemView.findViewById(R.id.tTeacherCourse);
            tDateCourse    = itemView.findViewById(R.id.tDateCourse);
            iFoto          = itemView.findViewById(R.id.iFoto);
        }

        public void bindCourses(Cursos curso,Activity activity){
            tNameCourse.setText(curso.getNombre());
            tTeacherCourse.setText(curso.getDocente());
            tDateCourse.setText(curso.getHorario());
        }
    }
}
