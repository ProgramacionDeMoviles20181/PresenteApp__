package com.programacionmoviles.juanpabloarangoa.presenteapp.modelo;

public class AsistenciasProfe {
    private String nombreEstudiante;
    private String cedula;
    private String asistencia;

    public AsistenciasProfe() {
    }

    public AsistenciasProfe(String nombreEstudiante, String cedula, String asistencia) {
        this.nombreEstudiante = nombreEstudiante;
        this.cedula = cedula;
        this.asistencia = asistencia;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(String asistencia) {
        this.asistencia = asistencia;
    }
}
