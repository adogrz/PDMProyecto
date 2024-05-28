package sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo;

import com.google.gson.annotations.SerializedName;

public class LocalArticulo {
    @SerializedName("id_sucursal")
    private String idLocal;
    @SerializedName("id_articulo")
    private String idArticulo;

    public LocalArticulo() {
    }

    public LocalArticulo(String idLocal, String idArticulo) {
        this.idLocal = idLocal;
        this.idArticulo = idArticulo;
    }

    public String getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(String idLocal) {
        this.idLocal = idLocal;
    }

    public String getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(String idArticulo) {
        this.idArticulo = idArticulo;
    }
}
