package sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo;

public class Compra {
    private String idCompra;
    private String fechaCompra;
    private double montoTotal;
    private String idProveedor;

    public Compra() {
    }

    public Compra(String idCompra, String fechaCompra, double montoTotal, String idProveedor) {
        this.idCompra = idCompra;
        this.fechaCompra = fechaCompra;
        this.montoTotal = montoTotal;
        this.idProveedor = idProveedor;
    }

    public String getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(String idCompra) {
        this.idCompra = idCompra;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }
}
