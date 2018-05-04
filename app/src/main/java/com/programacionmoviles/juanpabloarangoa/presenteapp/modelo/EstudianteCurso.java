package com.programacionmoviles.juanpabloarangoa.presenteapp.modelo;

public class EstudianteCurso {
    private String id;
    private String cedula;

    public EstudianteCurso() {
    }

    public EstudianteCurso(String id, String cedula) {
        this.id = id;
        this.cedula = cedula;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
}
