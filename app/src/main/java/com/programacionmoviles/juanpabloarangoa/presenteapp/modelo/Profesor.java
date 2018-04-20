package com.programacionmoviles.juanpabloarangoa.presenteapp.modelo;

public class Profesor {
    private String id;
    private String nombre;
    private String telefono;
    private int    edad;
    private String foto;
    private String cedula;
    private String institucion;

    public Profesor() { }

    public Profesor(String id, String nombre, String telefono, int edad, String foto, String cedula, String institucion) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.edad = edad;
        this.foto = foto;
        this.cedula = cedula;
        this.institucion = institucion;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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
}
