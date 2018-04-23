package com.programacionmoviles.juanpabloarangoa.presenteapp.modelo;

public class Notas {
    private String id_estudiante;
    private String codigo_curso;
    private float  nota;
    private int    porcentaje;
    private String numero_nota;

    public Notas() {
    }

    public Notas(String id_estudiante, String codigo_curso, float nota, int porcentaje, String numero_nota) {
        this.id_estudiante = id_estudiante;
        this.codigo_curso = codigo_curso;
        this.nota = nota;
        this.porcentaje = porcentaje;
        this.numero_nota = numero_nota;
    }

    public String getId_estudiante() {
        return id_estudiante;
    }

    public void setId_estudiante(String id_estudiante) {
        this.id_estudiante = id_estudiante;
    }

    public String getCodigo_curso() {
        return codigo_curso;
    }

    public void setCodigo_curso(String codigo_curso) {
        this.codigo_curso = codigo_curso;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getNumero_nota() {
        return numero_nota;
    }

    public void setNumero_nota(String numero_nota) {
        this.numero_nota = numero_nota;
    }
}
