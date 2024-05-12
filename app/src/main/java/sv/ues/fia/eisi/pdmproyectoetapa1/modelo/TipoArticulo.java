package sv.ues.fia.eisi.pdmproyectoetapa1.modelo;

public class TipoArticulo {
    private String id;
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

    @Override
    public String toString() {
        return nombre;
    }
}
