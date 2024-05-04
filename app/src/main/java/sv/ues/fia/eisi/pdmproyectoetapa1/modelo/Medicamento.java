package sv.ues.fia.eisi.pdmproyectoetapa1.modelo;

import java.util.Date;

public class Medicamento {
    private String idMedicamento;
    private Date fechaExpedicion;
    private Date fechaExpiracion;
    private Boolean requiereRecetaMedica;
    private String idArticulo;
    private String idFormaFarmaceutica;
    private String idViaAdministracion;
    private String idLaboratorio;

    public Medicamento() {
    }

    public Medicamento(String idMedicamento, Date fechaExpedicion, Date fechaExpiracion,
                       Boolean requiereRecetaMedica, String idArticulo, String idFormaFarmaceutica,
                       String idViaAdministracion, String idLaboratorio) {
        this.idMedicamento = idMedicamento;
        this.fechaExpedicion = fechaExpedicion;
        this.fechaExpiracion = fechaExpiracion;
        this.requiereRecetaMedica = requiereRecetaMedica;
        this.idArticulo = idArticulo;
        this.idFormaFarmaceutica = idFormaFarmaceutica;
        this.idViaAdministracion = idViaAdministracion;
        this.idLaboratorio = idLaboratorio;
    }

    public String getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(String idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public Date getFechaExpedicion() {
        return fechaExpedicion;
    }

    public void setFechaExpedicion(Date fechaExpedicion) {
        this.fechaExpedicion = fechaExpedicion;
    }

    public Date getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(Date fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public Boolean getRequiereRecetaMedica() {
        return requiereRecetaMedica;
    }

    public void setRequiereRecetaMedica(Boolean requiereRecetaMedica) {
        this.requiereRecetaMedica = requiereRecetaMedica;
    }

    public String getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(String idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getIdFormaFarmaceutica() {
        return idFormaFarmaceutica;
    }

    public void setIdFormaFarmaceutica(String idFormaFarmaceutica) {
        this.idFormaFarmaceutica = idFormaFarmaceutica;
    }

    public String getIdViaAdministracion() {
        return idViaAdministracion;
    }

    public void setIdViaAdministracion(String idViaAdministracion) {
        this.idViaAdministracion = idViaAdministracion;
    }

    public String getIdLaboratorio() {
        return idLaboratorio;
    }

    public void setIdLaboratorio(String idLaboratorio) {
        this.idLaboratorio = idLaboratorio;
    }
}
