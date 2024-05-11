package sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite;
import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DetalleRecetaDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.DetalleReceta;

public class DetalleRecetaDAOImpl implements DetalleRecetaDAO {
    private final BaseDatosFarmacia baseDatos;

    public DetalleRecetaDAOImpl(BaseDatosFarmacia baseDatos) {
        this.baseDatos = baseDatos;
    }

    @Override
    public String insertar(DetalleReceta obj) throws DAOException {
        return null;
    }

    @Override
    public void modificar(DetalleReceta obj) throws DAOException {

    }

    @Override
    public void eliminar(DetalleReceta obj) throws DAOException {

    }

    @Override
    public List<DetalleReceta> obtenerTodos() throws DAOException {
        return null;
    }

    @Override
    public DetalleReceta obtener(String id) throws DAOException {
        return null;
    }
}
