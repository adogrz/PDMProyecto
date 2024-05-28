package sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo;

import com.google.gson.annotations.SerializedName;

public class Laboratorio {

    @SerializedName("id_laboratorio")
    private String idLaboratorio;

    @SerializedName("nombre_laboratorio")
    private String nombreLaboratorio;
    private String laboratorioCustom;

    public Laboratorio() {
    }

    public Laboratorio(String idLaboratorio, String nombreLaboratorio) {
        this.idLaboratorio = idLaboratorio;
        this.nombreLaboratorio = nombreLaboratorio;
    }

    public String getIdLaboratorio() {
        return idLaboratorio;
    }

    public void setIdLaboratorio(String idLaboratorio) {
        this.idLaboratorio = idLaboratorio;
    }

    public String getNombreLaboratorio() {
        return nombreLaboratorio;
    }

    public void setNombreLaboratorio(String nombreLaboratorio) {
        this.nombreLaboratorio = nombreLaboratorio;
    }

    @Override
    public String toString() {
        this.laboratorioCustom=this.laboratorioCustom= nombreLaboratorio;
        return laboratorioCustom;
    }
}
