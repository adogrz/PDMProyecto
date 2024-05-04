package sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite;

import android.annotation.SuppressLint;
import android.content.Context;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ArticuloDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.MedicamentoDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.TipoArticuloDAO;

/**
 * Clase auxiliar que implementa {@link BaseDatosFarmacia} para llevar a cabo la conexión a la base de datos.
 */
public final class ControlBaseDatos {
    @SuppressLint("StaticFieldLeak")
    private static BaseDatosFarmacia baseDatos;
    private static final ControlBaseDatos instancia = new ControlBaseDatos();
    private Context contexto;
    private TipoArticuloDAO tipoArticulos = null;
    private ArticuloDAO articulos = null;
    private MedicamentoDAO medicamentos = null;

    private ControlBaseDatos() {
    }

    /**
     * Método que obtiene la instancia de la clase que controla la base de datos.
     * @param contexto Contexto de la aplicación.
     * @return Instancia de la clase que controla la base de datos.
     */
    public static ControlBaseDatos obtenerInstancia(Context contexto) {
        if (baseDatos == null) {
            baseDatos = new BaseDatosFarmacia(contexto);
        }
        instancia.contexto = contexto;
        return instancia;
    }

    /**
     * Método que obtiene la instancia de la clase que implementa la interfaz TipoArticuloDAO.
     * @return Instancia de la clase que implementa la interfaz TipoArticuloDAO.
     */
    public TipoArticuloDAO getTipoArticuloDAO() {
        if (tipoArticulos == null) {
            tipoArticulos = new TipoArticuloDAOImpl(baseDatos, contexto);
        }
        return tipoArticulos;
    }

    /**
     * Método que obtiene la instancia de la clase que implementa la interfaz ArticuloDAO.
     * @return Instancia de la clase que implementa la interfaz ArticuloDAO.
     */
    public ArticuloDAO getArticuloDAO() {
        if (articulos == null) {
            articulos = new ArticuloDAOImpl(baseDatos, contexto);
        }
        return articulos;
    }

    /**
     * Método que obtiene la instancia de la clase que implementa la interfaz MedicamentoDAO.
     * @return Instancia de la clase que implementa la interfaz MedicamentoDAO.
     */
    public MedicamentoDAO getMedicamentoDAO() {
        if (medicamentos == null) {
            medicamentos = new MedicamentoDAOImpl(baseDatos, contexto);
        }
        return medicamentos;
    }
}
