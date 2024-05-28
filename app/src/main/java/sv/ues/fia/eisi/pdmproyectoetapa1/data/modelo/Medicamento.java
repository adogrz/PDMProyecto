package sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Medicamento {

    @SerializedName("id_medicamento")
    private String idMedicamento;

    @SerializedName("fecha_expedicion_medicamento")
    private String fechaExpedicion;

    @SerializedName("fecha_expiracion_medicamento")
    private String fechaExpiracion;

    @SerializedName("requiere_receta_medica")
    private String requiereRecetaMedica;

    @SerializedName("id_articulo")
    private String idArticulo;

    @SerializedName("id_forma_farmaceutica")
    private String idFormaFarmaceutica;

    @SerializedName("id_via_administracion")
    private String idViaAdministracion;

    @SerializedName("id_laboratorio")
    private String idLaboratorio;

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

    @NonNull
    @Override
    public String toString() {
        return idMedicamento;
    }

    public void setIdLaboratorio(String idLaboratorio) {
        this.idLaboratorio = idLaboratorio;
    }
}
