package sv.ues.fia.eisi.pdmproyectoetapa1.modelo;

public class MetodoPago {

    private String idMetodoPago;
    private String tipoMetodoPago;
    private String metodoPagoCustom;

    public MetodoPago() {
    }

    public MetodoPago(String idMetodoPago, String tipoMetodoPago) {
        this.idMetodoPago = idMetodoPago;
        this.tipoMetodoPago = tipoMetodoPago;
    }

    public String getIdMetodoPago() {
        return idMetodoPago;
    }

    public void setIdMetodoPago(String idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
    }

    public String getTipoMetodoPago() {
        return tipoMetodoPago;
    }

    public void setTipoMetodoPago(String tipoMetodoPago) {
        this.tipoMetodoPago = tipoMetodoPago;
    }

    public String toString() {
        metodoPagoCustom = tipoMetodoPago;
        return metodoPagoCustom;
    }
}
