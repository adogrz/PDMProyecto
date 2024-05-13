package sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ArticuloDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ClienteDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DetalleVentaDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.MedicamentoDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.MetodoPagoDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ProveedorDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.TipoArticuloDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.VentaDAO;

/**
 * Clase auxiliar que implementa {@link BaseDatosFarmacia} para llevar a cabo la conexión a la base
 * de datos.
 */
public final class ControlBaseDatos {
    private static ControlBaseDatos instancia;
    private BaseDatosFarmacia baseDatos;
    private TipoArticuloDAO tipoArticulos = null;
    private ProveedorDAO proveedores = null;
    private ArticuloDAO articulos = null;
    private MedicamentoDAO medicamentos = null;
    private VentaDAO venta = null;
    private ClienteDAO cliente = null;
    private MetodoPagoDAO metodoPago = null;
    private DetalleVentaDAO detalleVenta = null;

    private ControlBaseDatos(Context contexto) {
        if (baseDatos == null) {
            baseDatos = new BaseDatosFarmacia(contexto);
        }
    }

    /**
     * Método que obtiene la instancia de la clase.
     * @param contexto Contexto de la aplicación.
     * @return Instancia de la clase.
     */
    public static ControlBaseDatos obtenerInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new ControlBaseDatos(contexto);
        }
        return instancia;
    }

    public SQLiteDatabase getDb() {
        return baseDatos.getWritableDatabase();
    }

    public void cerrar() {
        if (baseDatos != null) {
            baseDatos.close();
        }
    }

    /**
     * Método que obtiene la instancia de la clase que implementa la interfaz TipoArticuloDAO.
     * @return Instancia de la clase que implementa la interfaz TipoArticuloDAO.
     */
    public TipoArticuloDAO getTipoArticuloDAO() {
        if (tipoArticulos == null) {
            tipoArticulos = new TipoArticuloDAOImpl(baseDatos);
        }
        return tipoArticulos;
    }

    /**
     * Método que obtiene la instancia de la clase que implementa la interfaz ProveedorDAO.
     * @return Instancia de la clase que implementa la interfaz ProveedorDAO.
     */
    public ProveedorDAO getProveedorDAO() {
        if (proveedores == null) {
            proveedores = new ProveedorDAOImpl(baseDatos);
        }
        return proveedores;
    }

    /**
     * Método que obtiene la instancia de la clase que implementa la interfaz ArticuloDAO.
     * @return Instancia de la clase que implementa la interfaz ArticuloDAO.
     */
    public ArticuloDAO getArticuloDAO() {
        if (articulos == null) {
            articulos = new ArticuloDAOImpl(baseDatos);
        }
        return articulos;
    }

    /**
     * Método que obtiene la instancia de la clase que implementa la interfaz MedicamentoDAO.
     * @return Instancia de la clase que implementa la interfaz MedicamentoDAO.
     */
    public MedicamentoDAO getMedicamentoDAO() {
        if (medicamentos == null) {
            medicamentos = new MedicamentoDAOImpl(baseDatos);
        }
        return medicamentos;
    }

    /**
     * Método que obtiene la instancia de la clase que implementa la interfaz VentaDAO.
     * @return Instancia de la clase que implementa la interfaz VentaDAO.
     */
    public VentaDAO getVentaDAO() {
        if (venta == null) {
            venta = new VentaDAOImpl(baseDatos);
        }
        return venta;
    }

    /**
     * Método que obtiene la instancia de la clase que implementa la interfaz ClienteDAO.
     * @return Instancia de la clase que implementa la interfaz ClienteDAO.
     */
    public ClienteDAO getClienteDAO(){
        if(cliente == null){
            cliente = new ClienteDAOImpl(baseDatos);
        }
        return cliente;
    }

    /**
     * Método que obtiene la instancia de la clase que implementa la interfaz MetodoPagoDAO.
     * @return Instancia de la clase que implementa la interfaz MetodoPagoDAO.
     */
    public MetodoPagoDAO getMetodoPagoDAO() {
        if (metodoPago == null) {
            metodoPago = new MetodoPagoDAOImpl(baseDatos);
        }
        return metodoPago;
    }

    /**
     * Método que obtiene la instancia de la clase que implementa la interfaz DetalleVentaDAO.
     * @return Instancia de la clase que implementa la interfaz DetalleventaDAO.
     */
    public DetalleVentaDAO getDetalleVentaDAO() {
        if (detalleVenta == null) {
            detalleVenta = new DetalleVentaDAOImpl(baseDatos);
        }
        return detalleVenta;
    }
}
