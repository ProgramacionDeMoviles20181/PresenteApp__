package com.programacionmoviles.juanpabloarangoa.presenteapp.modelo;

public class Cursos {
    private String aula;
    private String codigo;
    private String docente;
    private String id_docente;
    private String horario;
    private String institucion;
    private String nombre;
    int nro_estudiantes;


    public Cursos(){}

    public Cursos(String aula, String codigo, String docente, String id_docente, String horario, String institucion, String nombre, int nro_estudiantes) {
        this.aula = aula;
        this.codigo = codigo;
        this.docente = docente;
        this.id_docente = id_docente;
        this.horario = horario;
        this.institucion = institucion;
        this.nombre = nombre;
        this.nro_estudiantes = nro_estudiantes;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }

    public String getId_docente() {
        return id_docente;
    }

    public void setId_docente(String id_docente) {
        this.id_docente = id_docente;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNro_estudiantes() {
        return nro_estudiantes;
    }

    public void setNro_estudiantes(int nro_estudiantes) {
        this.nro_estudiantes = nro_estudiantes;
    }
}
