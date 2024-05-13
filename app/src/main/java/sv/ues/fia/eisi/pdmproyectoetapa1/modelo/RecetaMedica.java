package sv.ues.fia.eisi.pdmproyectoetapa1.modelo;

public class RecetaMedica {
    private String idReceta;
    private String numeroReceta;
    private String fechaReceta;
    private String idMedico;
    public RecetaMedica() {
    }

    public String getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(String idReceta) {
        this.idReceta = idReceta;
    }

    public String getNumeroReceta() {
        return numeroReceta;
    }

    public void setNumeroReceta(String numeroReceta) {
        this.numeroReceta = numeroReceta;
    }

    public String getFechaReceta() {
        return fechaReceta;
    }

    public void setFechaReceta(String fechaReceta) {
        this.fechaReceta = fechaReceta;
    }

    public String getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(String idMedico) {
        this.idMedico = idMedico;
    }

    @Override
    public String toString() {
        return idReceta;
    }

    public RecetaMedica(String idReceta, String numeroReceta, String fechaReceta, String idMedico) {
        this.idReceta = idReceta;
        this.numeroReceta = numeroReceta;
        this.fechaReceta = fechaReceta;
        this.idMedico = idMedico;
    }

}
