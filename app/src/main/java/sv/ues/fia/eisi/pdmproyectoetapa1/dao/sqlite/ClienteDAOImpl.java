package sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite;

import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ClienteDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Cliente;

public class ClienteDAOImpl implements ClienteDAO {
    private final BaseDatosFarmacia baseDatos;

    public ClienteDAOImpl(BaseDatosFarmacia baseDatos) {
        this.baseDatos = baseDatos;
    }

    @Override
    public String insertar(Cliente obj) throws DAOException {
        return null;
    }

    @Override
    public void modificar(Cliente obj) throws DAOException {

    }

    @Override
    public void eliminar(Cliente obj) throws DAOException {

    }

    @Override
    public List<Cliente> obtenerTodos() throws DAOException {
        return null;
    }

    @Override
    public Cliente obtener(String id) throws DAOException {
        return null;
    }
}
