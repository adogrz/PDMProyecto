package sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite;

import android.content.ContentValues;


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
    public String insertar(Medicamento obj) throws DAOException {
        String regInsertados="Registro Insertado Nº=";
        long contador=0;

        ContentValues medicamento = new ContentValues();
        medicamento.put("IDMEDICAMENTO", obj.getIdMedicamento());
        medicamento.put("FECHAEXPEDICION", obj.getFechaExpedicion().toString());
        medicamento.put("FECHAEXPIRACION", obj.getFechaExpiracion().toString());
        medicamento.put("REQUIRERECETAMEDICA", obj.getRequiereRecetaMedica());
        medicamento.put("IDARTICULO", obj.getIdArticulo());
        medicamento.put("IDFORMAFARMACEUTICA", obj.getIdFormaFarmaceutica());
        medicamento.put("IDVIAADMINISTRACION", obj.getIdViaAdministracion());
        medicamento.put("IDLABORATORIO", obj.getIdLaboratorio());

        if(contador==-1){
            DAOException e = new DAOException("Medicamento ya existe");
            throw e;
        }
        else{
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
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
