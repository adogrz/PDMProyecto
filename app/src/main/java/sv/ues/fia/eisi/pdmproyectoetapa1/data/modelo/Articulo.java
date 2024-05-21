package sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo;

import androidx.annotation.NonNull;

public class Articulo {
    private String idArticulo;
    private String nombre;
    private double precioUnitario;
    private int stock = 0;
    private String descripcion;
    private String idProveedor;
    private String idTipoArticulo;

    public Articulo() {
    }

    public Articulo(String idArticulo, String nombre, double precioUnitario, String descripcion,
                    String idProveedor, String idTipoArticulo) {
        this.idArticulo = idArticulo;
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
        this.descripcion = descripcion;
        this.idProveedor = idProveedor;
        this.idTipoArticulo = idTipoArticulo;
    }

    public String getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(String idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }
    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getIdTipoArticulo() {
        return idTipoArticulo;
    }

    public void setIdTipoArticulo(String idTipoArticulo) {
        this.idTipoArticulo = idTipoArticulo;
    }

    @NonNull
    @Override
    public String toString() {
        return nombre;
    }
}
