package sv.ues.fia.eisi.pdmproyectoetapa1.modelo;

import java.util.Date;

public class Venta {

    private String idVenta;
    private double montoTotalVenta;
    private String fechaVenta;
    private String idMetodoPago;
    private String idCliente;

    public Venta() {
    }

    public Venta(String idVenta, double montoTotalVenta, String fechaVenta, String idMetodoPago, String idCliente) {
        this.idVenta = idVenta;
        this.montoTotalVenta = montoTotalVenta;
        this.fechaVenta = fechaVenta;
        this.idMetodoPago = idMetodoPago;
        this.idCliente = idCliente;
    }

    public String getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(String idVenta) {
        this.idVenta = idVenta;
    }

    public double getMontoTotalVenta() {
        return montoTotalVenta;
    }

    public void setMontoTotalVenta(double montoTotalVenta) {
        this.montoTotalVenta = montoTotalVenta;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getIdMetodoPago() {
        return idMetodoPago;
    }

    public void setIdMetodoPago(String idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }
}
