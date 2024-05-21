package sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo;

public class DetalleCompra {
    private String idDetalleCompra;
    private int cantidadArticulo;
    private double subtotalCompra;
    private String idCompra;
    private String idArticulo;

    public DetalleCompra() {
    }

    public DetalleCompra(int cantidadArticulo, double subtotalCompra, String idCompra,
                         String idArticulo) {
        this.cantidadArticulo = cantidadArticulo;
        this.subtotalCompra = subtotalCompra;
        this.idCompra = idCompra;
        this.idArticulo = idArticulo;
    }

    public String getIdDetalleCompra() {
        return idDetalleCompra;
    }

    public void setIdDetalleCompra(String idDetalleCompra) {
        this.idDetalleCompra = idDetalleCompra;
    }

    public int getCantidadArticulo() {
        return cantidadArticulo;
    }

    public void setCantidadArticulo(int cantidadArticulo) {
        this.cantidadArticulo = cantidadArticulo;
    }

    public double getSubtotalCompra() {
        return subtotalCompra;
    }

    public void setSubtotalCompra(double subtotalCompra) {
        this.subtotalCompra = subtotalCompra;
    }

    public String getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(String idCompra) {
        this.idCompra = idCompra;
    }

    public String getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(String idArticulo) {
        this.idArticulo = idArticulo;
    }
}
