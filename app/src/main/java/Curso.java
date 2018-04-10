public class Curso {
    private String nombre;
    private String docente;
    private String institucion;
    private String horario;
    private int foto;

    public Curso(int foto, String nombre, String docente, String institucion, String horario) {
        this.foto = foto;
        this.nombre = nombre;
        this.docente = docente;
        this.institucion = institucion;
        this.horario = horario;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
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

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
}
