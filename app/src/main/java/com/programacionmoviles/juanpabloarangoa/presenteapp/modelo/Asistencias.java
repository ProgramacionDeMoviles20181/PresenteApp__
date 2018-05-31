package com.programacionmoviles.juanpabloarangoa.presenteapp.modelo;

public class Asistencias {
    private String fecha;
    private boolean asistio;

    public Asistencias() {}

    public Asistencias(String fecha, boolean asistio) {
        this.fecha = fecha;
        this.asistio = asistio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isAsistio() {
        return asistio;
    }

    public void setAsistio(boolean asistio) {
        this.asistio = asistio;
    }
}
