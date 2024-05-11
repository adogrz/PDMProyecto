package sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ArticuloDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.MedicamentoDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.TipoArticuloDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ViaAdministracionDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.LaboratorioDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.FormaFarmaceuticaDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ProveedorDAO;


/**
 * Clase auxiliar que implementa {@link BaseDatosFarmacia} para llevar a cabo la conexión a la base
 * de datos.
 */
public final class ControlBaseDatos {
    private static ControlBaseDatos instancia;
    private BaseDatosFarmacia baseDatos;
    private TipoArticuloDAO tipoArticulos = null;
    private ArticuloDAO articulos = null;
    private MedicamentoDAO medicamentos = null;

    private ViaAdministracionDAO viaAdministracion = null;

    private LaboratorioDAO laboratorio = null;

    private FormaFarmaceuticaDAO formaFarmaceutica = null;

    private ProveedorDAO proveedores = null;


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
    public FormaFarmaceuticaDAO getFormaFarmaceuticaDAO(){
        if(formaFarmaceutica == null){
            formaFarmaceutica = new FormaFarmaceuticaDAOImpl(baseDatos);
        }
        return formaFarmaceutica;
    }
}
