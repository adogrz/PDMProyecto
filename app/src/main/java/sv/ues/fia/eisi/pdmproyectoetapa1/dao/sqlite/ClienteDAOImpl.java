package sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite;

import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ClienteDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Cliente;

public class ClienteDAOImpl implements ClienteDAO {
    private final BaseDatosFarmacia baseDatos;

    public ClienteDAOImpl(BaseDatosFarmacia baseDatos) {
        this.baseDatos = baseDatos;
    }

    @Override
    public void insertar(Cliente obj) {

    }

    @Override
    public void modificar(Cliente obj) {

    }

    @Override
    public void eliminar(Cliente obj) {

    }

    @Override
    public List<Cliente> obtenerTodos() {
        return null;
    }

    @Override
    public Cliente obtener(String id) {
        return null;
    }
}
