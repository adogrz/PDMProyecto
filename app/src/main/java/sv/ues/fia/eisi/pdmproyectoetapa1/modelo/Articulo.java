package sv.ues.fia.eisi.pdmproyectoetapa1.modelo;

public class Articulo {
    private String idArticulo;
    private String nombre;
    private Double precioUnitario;
    private int stock;
    private String descripcion;
    private String idProveedor;
    private String idTipoArticulo;
    private String  articuloCustom;

    public Articulo() {
    }

    public Articulo(String idArticulo, String nombre, Double precioUnitario, int stock,
                    String descripcion, String idProveedor, String idTipoArticulo) {
        this.idArticulo = idArticulo;
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
        this.stock = stock;
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

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
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

    @Override
    public String toString() {
        this.articuloCustom=this.articuloCustom=this.nombre;
        return articuloCustom;
    }
}
