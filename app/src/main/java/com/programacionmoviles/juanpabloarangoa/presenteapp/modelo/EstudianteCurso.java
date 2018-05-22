package com.programacionmoviles.juanpabloarangoa.presenteapp.modelo;

public class EstudianteCurso {
    private String id_estudiante;
    private String codigoCurso;

    public EstudianteCurso() {
    }

    public EstudianteCurso(String id_estudiante, String codigoCurso) {
        this.id_estudiante = id_estudiante;
        this.codigoCurso = codigoCurso;
    }

    public String getId_estudiante() {
        return id_estudiante;
    }

    public void setId_estudiante(String id_estudiante) {
        this.id_estudiante = id_estudiante;
    }

    public String getCodigoCurso() {
        return codigoCurso;
    }

    public void setCodigoCurso(String codigoCurso) {
        this.codigoCurso = codigoCurso;
    }
}
