package sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite;

import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.MedicamentoDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Medicamento;

public class MedicamentoDAOImpl implements MedicamentoDAO {
    private final BaseDatosFarmacia baseDatos;

    public MedicamentoDAOImpl(BaseDatosFarmacia baseDatos) {
        this.baseDatos = baseDatos;
    }

    @Override
    public void insertar(Medicamento obj) throws DAOException {

    }

    @Override
    public void modificar(Medicamento obj) throws DAOException {

    }

    @Override
    public void eliminar(Medicamento obj) throws DAOException {

    }

    @Override
    public List<Medicamento> obtenerTodos() throws DAOException {
        return null;
    }

    @Override
    public Medicamento obtener(String id) throws DAOException {
        return null;
    }
}
