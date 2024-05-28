package sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class TipoArticulo {
    @SerializedName("id_tipo_articulo")
    private String id;
    @SerializedName("nombre_tipo_articulo")
    private String nombre;

    public TipoArticulo() {
    }

    public TipoArticulo(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @NonNull
    @Override
    public String toString() {
        return nombre;
    }
}
