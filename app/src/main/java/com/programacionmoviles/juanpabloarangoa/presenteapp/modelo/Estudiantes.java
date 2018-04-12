package com.programacionmoviles.juanpabloarangoa.presenteapp.modelo;

public class Estudiantes {
    private String id;
    private String nombre;

    public Estudiantes(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Estudiantes(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
