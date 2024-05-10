package sv.ues.fia.eisi.pdmproyectoetapa1.modelo;

public class Laboratorio {
    private String idLaboratorio;
    private String nombreLaboratorio;

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
        return idLaboratorio + " - " + nombreLaboratorio;
    }
}
