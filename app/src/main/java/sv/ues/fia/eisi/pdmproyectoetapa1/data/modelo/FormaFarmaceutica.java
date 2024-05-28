package sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo;

import com.google.gson.annotations.SerializedName;

public class FormaFarmaceutica {

    @SerializedName("id_forma_farmaceutica")
    private String idFormaFarmaceutica;

    @SerializedName("tipo_forma_farmaceutica")
    private String formaFarmaceutica;
    private String formaFarmaceuticaCustom;

    public FormaFarmaceutica() {
    }

    public FormaFarmaceutica(String idFormaFarmaceutica, String formaFarmaceutica) {
        this.idFormaFarmaceutica = idFormaFarmaceutica;
        this.formaFarmaceutica = formaFarmaceutica;
    }

    public String getIdFormaFarmaceutica() {
        return idFormaFarmaceutica;
    }

    public void setIdFormaFarmaceutica(String idFormaFarmaceutica) {
        this.idFormaFarmaceutica = idFormaFarmaceutica;
    }

    public String getFormaFarmaceutica() {
        return formaFarmaceutica;
    }

    public void setFormaFarmaceutica(String formaFarmaceutica) {
        this.formaFarmaceutica = formaFarmaceutica;
    }

    @Override
    public String toString() {
        this.formaFarmaceuticaCustom=this.formaFarmaceuticaCustom= formaFarmaceutica;
        return formaFarmaceuticaCustom;
    }
}
