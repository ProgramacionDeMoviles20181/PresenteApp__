package com.programacionmoviles.juanpabloarangoa.presenteapp.modelo;

public class Cursos {
    String nombre,codigo,horario,profesor,foto;

    public Cursos(){}

    public Cursos(String nombre, String codigo, String horario, String profesor, String foto) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.horario = horario;
        this.profesor = profesor;
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
