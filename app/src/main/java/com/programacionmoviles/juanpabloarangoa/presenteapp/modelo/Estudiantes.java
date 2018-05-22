package com.programacionmoviles.juanpabloarangoa.presenteapp.modelo;

public class Estudiantes {
    private String id;
    private String nombre;
    private String telefono;
    private int edad;
    private String fotoLink;
    private String cedula;
    private String institucion;
    private int nCursos;

    public Estudiantes() {
    }

    public Estudiantes(String id, String nombre, String telefono, int edad, String fotoLink, String cedula, String institucion, int nCursos) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.edad = edad;
        this.fotoLink = fotoLink;
        this.cedula = cedula;
        this.institucion = institucion;
        this.nCursos = nCursos;
    }

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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getFotoLink() {
        return fotoLink;
    }

    public void setFotoLink(String fotoLink) {
        this.fotoLink = fotoLink;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public int getnCursos() {
        return nCursos;
    }

    public void setnCursos(int nCursos) {
        this.nCursos = nCursos;
    }
}

