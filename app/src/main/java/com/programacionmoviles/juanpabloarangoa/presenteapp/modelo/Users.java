package com.programacionmoviles.juanpabloarangoa.presenteapp.modelo;

/**
 * Created by Daniel on 5/04/2018.
 */

public class Users {
    private String id, nombre, foto;
    private int edad;

    public Users(String id, String nombre,  int edad, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.foto = foto;
    }

    public Users(String id, String nombre,  int edad) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
    }

    public Users(){}    //Es necesario crear un constructor vacío

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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



    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
