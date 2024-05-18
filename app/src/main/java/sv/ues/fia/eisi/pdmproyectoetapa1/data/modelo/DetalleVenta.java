package sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo;

public class DetalleVenta {
    private String idDetalleVenta;
    private int cantidadProductoVenta;
    private double subtotalVenta;
    private String idVenta;
    private String idArticulo;

    public DetalleVenta() {
    }

    public DetalleVenta(String idDetalleVenta, int cantidadProductoVenta, double subtotalVenta, String idVenta, String idArticulo) {
        this.idDetalleVenta = idDetalleVenta;
        this.cantidadProductoVenta = cantidadProductoVenta;
        this.subtotalVenta = subtotalVenta;
        this.idVenta = idVenta;
        this.idArticulo = idArticulo;
    }

    public String getIdDetalleVenta() {
        return idDetalleVenta;
    }

    public void setIdDetalleVenta(String idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    public int getCantidadProductoVenta() {
        return cantidadProductoVenta;
    }

    public void setCantidadProductoVenta(int cantidadProductoVenta) {
        this.cantidadProductoVenta = cantidadProductoVenta;
    }

    public double getSubtotalVenta() {
        return subtotalVenta;
    }

    public void setSubtotalVenta(double subtotalVenta) {
        this.subtotalVenta = subtotalVenta;
    }

    public String getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(String idVenta) {
        this.idVenta = idVenta;
    }

    public String getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(String idArticulo) {
        this.idArticulo = idArticulo;
    }
}
