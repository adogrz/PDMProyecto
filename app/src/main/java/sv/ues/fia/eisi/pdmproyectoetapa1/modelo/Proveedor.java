package sv.ues.fia.eisi.pdmproyectoetapa1.modelo;

public class Proveedor {
    private String idProveedor;
    private String nombre;
    private String telefono;


    public Proveedor() {
    }

    public Proveedor(String idProveedor, String nombre, String telefono) {
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
