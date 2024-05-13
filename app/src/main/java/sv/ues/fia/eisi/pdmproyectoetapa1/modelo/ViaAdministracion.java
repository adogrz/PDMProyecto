package sv.ues.fia.eisi.pdmproyectoetapa1.modelo;

public class ViaAdministracion {
    private String idViaAdministracion;
    private String viaAdministracion;
    private String viaAdministracionCustom;

    public ViaAdministracion() {
    }

    public ViaAdministracion(String idViaAdministracion, String viaAdministracion) {
        this.idViaAdministracion = idViaAdministracion;
        this.viaAdministracion = viaAdministracion;
    }

    public String getIdViaAdministracion() {
        return idViaAdministracion;
    }

    public void setIdViaAdministracion(String idViaAdministracion) {
        this.idViaAdministracion = idViaAdministracion;
    }

    public String getViaAdministracion() {
        return viaAdministracion;
    }

    public void setViaAdministracion(String viaAdministracion) {
        this.viaAdministracion = viaAdministracion;
    }

    @Override
    public String toString() {
        this.viaAdministracionCustom=this.viaAdministracionCustom= viaAdministracion;
        return viaAdministracionCustom;
    }



}
