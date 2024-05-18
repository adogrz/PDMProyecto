package sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo;

public class Medico {
    private String idMedico;
    private String nombreMedico;
    private String apellidoMedico;
    private String especialidad;
    private String jvpm;
    public Medico() {
    }

    public String getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(String idMedico) {
        this.idMedico = idMedico;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getApellidoMedico() {
        return apellidoMedico;
    }

    public void setApellidoMedico(String apellidoMedico) {
        this.apellidoMedico = apellidoMedico;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getJvpm() {
        return jvpm;
    }

    public void setJvpm(String jvpm) {
        this.jvpm = jvpm;
    }

    public Medico(String idMedico, String nombreMedico, String apellidoMedico, String especialidad, String jvpm) {
        this.idMedico = idMedico;
        this.nombreMedico = nombreMedico;
        this.apellidoMedico = apellidoMedico;
        this.especialidad = especialidad;
        this.jvpm = jvpm;
    }
}
