package com.programacionmoviles.juanpabloarangoa.presenteapp.modelo;

public class Notas {
    private String descripcion;
    private double  nota;
    private int    porcentaje;

    public Notas() {
    }

    public Notas(String descripcion, double nota, int porcentaje) {
        this.descripcion = descripcion;
        this.nota = nota;
        this.porcentaje = porcentaje;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getNota() {
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
}
