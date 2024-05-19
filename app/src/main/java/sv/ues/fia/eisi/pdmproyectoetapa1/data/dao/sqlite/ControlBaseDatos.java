package sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.ArticuloDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.CompraDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.DetalleRecetaDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.MedicamentoDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.MedicoDAO;

import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.LocalArticuloDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.LocalDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.ClienteDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.DetalleVentaDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.MetodoPagoDAO;

import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.ProveedorDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.RecetaMedicaDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.TipoArticuloDAO;

import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.ViaAdministracionDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.LaboratorioDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.FormaFarmaceuticaDAO;

import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.VentaDAO;

/**
 * Clase auxiliar que implementa {@link BaseDatosFarmacia} para llevar a cabo la conexión a la base
 * de datos.
 */
public final class ControlBaseDatos {
    private static ControlBaseDatos instancia;
    private BaseDatosFarmacia baseDatos;
    private TipoArticuloDAO tipoArticulos = null;
    private ProveedorDAO proveedores = null;
    private LocalDAO locales = null;
    private ArticuloDAO articulos = null;
    private MedicamentoDAO medicamentos = null;
    private RecetaMedicaDAO recetas = null;
    private DetalleRecetaDAO detallesReceta = null;
    private MedicoDAO medicos = null;
    private ViaAdministracionDAO viaAdministracion = null;
    private LaboratorioDAO laboratorio = null;
    private FormaFarmaceuticaDAO formaFarmaceutica = null;
    private ClienteDAO clientes = null;
    private LocalArticuloDAO localesArticulos = null;
    private VentaDAO venta = null;
    private MetodoPagoDAO metodoPago = null;
    private DetalleVentaDAO detalleVenta = null;
    private CompraDAO compras = null;

    public ControlBaseDatos(Context contexto) {
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
     * Método que obtiene la instancia de la clase que implementa la interfaz LocalDAO.
     * @return Instancia de la clase que implementa la interfaz LocalDAO.
     */
    public LocalDAO getLocalDAO() {
        if (locales == null) {
            locales = new LocalDAOImpl(baseDatos);
        }
        return locales;
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

    public MedicamentoDAO getMedicamentosDAO(){
        if(medicamentos == null){
            medicamentos=new MedicamentoDAOImpl(baseDatos);
        }
        return medicamentos;
    }

    /**
     * Método que obtiene la instancia de la clase que implementa la interfaz ViaAdministracionDAO.
     * @return Instancia de la clase que implementa la interfaz ViaAdministracionDAO.
     *  */
    public  ViaAdministracionDAO getViaAdministracionDAO(){
        if(viaAdministracion == null){
            viaAdministracion = new ViaAdministracionDAOImpl(baseDatos);
        }
        return viaAdministracion;
    }

    /**
     * Método que obtiene la instancia de la clase que implementa la interfaz LaboratorioDAO.
     * @return Instancia de la clase que implementa la interfaz LaboratorioDAO.
     */
    public LaboratorioDAO getLaboratorioDAO(){
        if(laboratorio == null){
            laboratorio = new LaboratorioDAOImpl(baseDatos);
        }
        return laboratorio;
    }

    /**
     * Método que obtiene la instancia de la clase que implementa la interfaz FormaFarmaceuticaDAO.
     * @return Instancia de la clase que implementa la interfaz FormaFarmaceuticaDAO.
     */
    public FormaFarmaceuticaDAO getFormaFarmaceuticaDAO() {
        if (formaFarmaceutica == null) {
            formaFarmaceutica = new FormaFarmaceuticaDAOImpl(baseDatos);
        }
        return formaFarmaceutica;
    }

    public LocalArticuloDAO getLocalArticuloDAO() {
        if (localesArticulos == null) {
            localesArticulos = new LocalArticuloDAOImpl(baseDatos);
        }
        return localesArticulos;
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

    public ClienteDAO getClienteDAO() {
        if (clientes == null) {
            clientes = new ClienteDAOImpl(baseDatos);
        }
        return clientes;
    }

     /* Método que obtiene la instancia de la clase que implementa la interfaz RecetaMedicaDAO.
     * @return Instancia de la clase que implementa la interfaz RecetaMedicaDAO.
     */
    public RecetaMedicaDAO getRecetaMedicaDAO() {
        if (recetas == null) {
            recetas = new RecetaMedicaDAOImpl(baseDatos);
        }
        return recetas;
    }

    /**
     * Método que obtiene la instancia de la clase que implementa la interfaz DetalleRecetaDAO.
     * @return Instancia de la clase que implementa la interfaz DetalleRecetaDAO.
     */
    public DetalleRecetaDAO getDetalleRecetaDAO() {
        if (detallesReceta == null) {
            detallesReceta = new DetalleRecetaDAOImpl(baseDatos);
        }
        return detallesReceta;
    }
    public MedicoDAO getMedicoDAO() {
        if (medicos == null) {
            medicos = new MedicoDAOImpl(baseDatos);
        }
        return medicos;
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

    public CompraDAO getCompraDAO() {
        if (compras == null) {
            compras = new CompraDAOImpl(baseDatos);
        }
        return compras;
    }
}
