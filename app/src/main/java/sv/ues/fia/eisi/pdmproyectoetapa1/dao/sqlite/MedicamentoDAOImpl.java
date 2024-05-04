package sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite;

import android.content.Context;

import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.MedicamentoDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Medicamento;

public class MedicamentoDAOImpl implements MedicamentoDAO {
    private final BaseDatosFarmacia baseDatos;
    private final Context contexto;

    public MedicamentoDAOImpl(BaseDatosFarmacia baseDatos, Context contexto) {
        this.baseDatos = baseDatos;
        this.contexto = contexto;
    }

    @Override
    public void insertar(Medicamento obj) {

    }

    @Override
    public void modificar(Medicamento obj) {
    }

    @Override
    public void eliminar(Medicamento obj) {

    }

    @Override
    public List<Medicamento> obtenerTodos() {
        return null;
    }

    @Override
    public Medicamento obtener(String id) {
        return null;
    }
}
