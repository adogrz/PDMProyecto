package sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite;

import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ArticuloDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Articulo;

/**
 * Clase que implementa el acceso a la base de datos para la entidad Articulo.
 */
public class ArticuloDAOImpl implements ArticuloDAO {
    private final BaseDatosFarmacia baseDatos;

    public ArticuloDAOImpl(BaseDatosFarmacia baseDatos) {
        this.baseDatos = baseDatos;
    }

    @Override
    public String insertar(Articulo obj) throws DAOException {
        return null;
    }

    @Override
    public void modificar(Articulo obj) throws DAOException {

    }

    @Override
    public void eliminar(Articulo obj) throws DAOException {

    }

    @Override
    public List<Articulo> obtenerTodos() throws DAOException {
        return null;
    }

    @Override
    public Articulo obtener(String id) throws DAOException {
        return null;
    }
}
