package sv.ues.fia.eisi.pdmproyectoetapa1.modelo;

import java.util.Date;

public class Medicamento {
    private String idMedicamento;
    private String fechaExpedicion;
    private String fechaExpiracion;
    private String requiereRecetaMedica;
    private String idArticulo;
    private String idFormaFarmaceutica;
    private String idViaAdministracion;
    private String idLaboratorio;

    private String idMedicamentoCustom;

    public Medicamento() {
    }

    public Medicamento(String idMedicamento, String fechaExpedicion, String fechaExpiracion,
                       String requiereRecetaMedica, String idArticulo, String idFormaFarmaceutica,
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

    public String getFechaExpedicion() {
        return fechaExpedicion;
    }

    public void setFechaExpedicion(String fechaExpedicion) {
        this.fechaExpedicion = fechaExpedicion;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getRequiereRecetaMedica() {
        return requiereRecetaMedica;
    }

    public void setRequiereRecetaMedica(String requiereRecetaMedica) {
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

    @Override
   public String toString(){
        this.idMedicamentoCustom=this.idMedicamentoCustom=this.idMedicamento;
        return this.idMedicamentoCustom;
    }

}
