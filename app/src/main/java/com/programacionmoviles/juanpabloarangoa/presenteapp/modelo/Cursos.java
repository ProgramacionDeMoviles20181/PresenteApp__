package com.programacionmoviles.juanpabloarangoa.presenteapp.modelo;

public class Cursos {
    private String nombre, docente, aula, horario, codigo, institucion, id;

    public Cursos() {
    }

    public Cursos(String nombre, String docente, String aula, String horario, String codigo, String institucion, String id) {
        this.nombre = nombre;
        this.docente = docente;
        this.aula = aula;
        this.horario = horario;
        this.codigo = codigo;
        this.institucion = institucion;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
