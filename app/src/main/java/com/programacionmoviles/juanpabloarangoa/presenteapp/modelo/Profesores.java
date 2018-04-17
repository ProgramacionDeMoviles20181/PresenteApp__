package com.programacionmoviles.juanpabloarangoa.presenteapp.modelo;

public class Profesores {
    private String id;
    private String nombres;
    private String telefono;
    private String institucion;
    private String carnet;

    public Profesores() {
    }

    public Profesores(String id, String nombres, String telefono, String institucion, String carnet) {
        this.id = id;
        this.nombres = nombres;
        this.telefono = telefono;
        this.institucion = institucion;
        this.carnet = carnet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }
}
