package sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo;

public class Local {
    private String idLocal;
    private String nombreLocal;
    private String idDireccion;

    public Local() {
    }

    public Local(String idLocal, String nombreLocal, String idDireccion) {
        this.idLocal = idLocal;
        this.nombreLocal = nombreLocal;
        this.idDireccion = idDireccion;
    }

    public String getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(String idLocal) {
        this.idLocal = idLocal;
    }

    public String getNombreLocal() {
        return nombreLocal;
    }

    public void setNombreLocal(String nombreLocal) {
        this.nombreLocal = nombreLocal;
    }

    public String getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(String idDireccion) {
        this.idDireccion = idDireccion;
    }

    @Override
    public String toString() {
        return nombreLocal;
    }
}
