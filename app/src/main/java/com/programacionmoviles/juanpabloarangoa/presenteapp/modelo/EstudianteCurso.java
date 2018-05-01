package com.programacionmoviles.juanpabloarangoa.presenteapp.modelo;

public class EstudianteCurso {
    private String cedula;
    private String id;

    public EstudianteCurso() {
    }

    public EstudianteCurso(String cedula, String id) {
        this.cedula = cedula;
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
