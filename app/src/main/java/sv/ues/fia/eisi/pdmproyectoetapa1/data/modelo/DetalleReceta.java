package sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo;

public class DetalleReceta {
    private String idDetalleReceta;
    private String periodicidad;
    private String dosis;
    private String fechaInicioTratamiento;
    private String fechaFinTratamiento;
    private String idRecetaMedica;
    private String idMedicamento;
    public DetalleReceta(){
    }

    public String getIdDetalleReceta() {
        return idDetalleReceta;
    }

    public void setIdDetalleReceta(String idDetalleReceta) {
        this.idDetalleReceta = idDetalleReceta;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getFechaInicioTratamiento() {
        return fechaInicioTratamiento;
    }

    public void setFechaInicioTratamiento(String fechaInicioTratamiento) {
        this.fechaInicioTratamiento = fechaInicioTratamiento;
    }

    public String getFechaFinTratamiento() {
        return fechaFinTratamiento;
    }

    public void setFechaFinTratamiento(String fechaFinTratamiento) {
        this.fechaFinTratamiento = fechaFinTratamiento;
    }

    public String getIdRecetaMedica() {
        return idRecetaMedica;
    }

    public void setIdRecetaMedica(String idRecetaMedica) {
        this.idRecetaMedica = idRecetaMedica;
    }

    public String getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(String idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public DetalleReceta(String idDetalleReceta, String periodicidad, String dosis, String fechaInicioTratamiento, String fechaFinTratamiento, String idRecetaMedica, String idMedicamento) {
        this.idDetalleReceta = idDetalleReceta;
        this.periodicidad = periodicidad;
        this.dosis = dosis;
        this.fechaInicioTratamiento = fechaInicioTratamiento;
        this.fechaFinTratamiento = fechaFinTratamiento;
        this.idRecetaMedica = idRecetaMedica;
        this.idMedicamento = idMedicamento;
    }
}
