package com.programacionmoviles.juanpabloarangoa.presenteapp.modelo;

public class Cursos {
    private String aula;
    private double latitud_aula;
    private double longitud_aula;
    private String codigo;
    private String docente;
    private String id_docente;
    private String horario;
    private String institucion;
    private String nombre;
    int nro_estudiantes;
    private boolean isstarted;


    public Cursos(){}

    public Cursos(String aula, double latitud_aula, double longitud_aula, String codigo, String docente, String id_docente, String horario, String institucion, String nombre, int nro_estudiantes, boolean isstarted) {
        this.aula = aula;
        this.latitud_aula = latitud_aula;
        this.longitud_aula = longitud_aula;
        this.codigo = codigo;
        this.docente = docente;
        this.id_docente = id_docente;
        this.horario = horario;
        this.institucion = institucion;
        this.nombre = nombre;
        this.nro_estudiantes = nro_estudiantes;
        this.isstarted = isstarted;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public double getLatitud_aula() {
        return latitud_aula;
    }

    public void setLatitud_aula(double latitud_aula) {
        this.latitud_aula = latitud_aula;
    }

    public double getLongitud_aula() {
        return longitud_aula;
    }

    public void setLongitud_aula(double longitud_aula) {
        this.longitud_aula = longitud_aula;
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

    public boolean getisstarted(){return isstarted;}

    public void setisstarted(boolean isstarted){this.isstarted = isstarted;}
}

