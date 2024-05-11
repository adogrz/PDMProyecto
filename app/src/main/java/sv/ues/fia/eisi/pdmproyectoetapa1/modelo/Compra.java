package sv.ues.fia.eisi.pdmproyectoetapa1.modelo;

public class Compra {
    private String idCompra;
    private String idProveedor;
    private double montoTotal;
    private String fechaCompra;


    public Compra() {

    }

    public Compra(String idCompra, double montoTotal, String fechaCompra, String idProveedor) {
        this.idCompra = idCompra;
        this.idProveedor = idProveedor;
        this.montoTotal = montoTotal;
        this.fechaCompra = fechaCompra;
    }

    public String getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(String idCompra) {
        this.idCompra = idCompra;
    }

    public String getidProveedor() {
        return idProveedor;
    }

    public void setidProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }


}



