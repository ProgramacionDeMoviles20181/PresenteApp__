package com.programacionmoviles.juanpabloarangoa.presenteapp.modelo;

public class Asistencias {
    private String codigo_curso;
    private String id_estudiante;
    private String fecha;
    private int    numero_clase;

    public Asistencias() {}

    public Asistencias(String codigo_curso, String id_estudiante, String fecha, int numero_clase) {
        this.codigo_curso = codigo_curso;
        this.id_estudiante = id_estudiante;
        this.fecha = fecha;
        this.numero_clase = numero_clase;
    }

    public String getCodigo_curso() {
        return codigo_curso;
    }

    public void setCodigo_curso(String codigo_curso) {
        this.codigo_curso = codigo_curso;
    }

    public String getId_estudiante() {
        return id_estudiante;
    }

    public void setId_estudiante(String id_estudiante) {
        this.id_estudiante = id_estudiante;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getNumero_clase() {
        return numero_clase;
    }

    public void setNumero_clase(int numero_clase) {
        this.numero_clase = numero_clase;
    }
}
