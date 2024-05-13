package sv.ues.fia.eisi.pdmproyectoetapa1.modelo;

public class FormaFarmaceutica {
    private String idFormaFarmaceutica;
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
