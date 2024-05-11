package sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.MedicamentoDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Medicamento;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.BaseDatosFarmacia.Tablas;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.FarmaciaContrato.EntradaMedicamento;



public class MedicamentoDAOImpl implements MedicamentoDAO {
    private final BaseDatosFarmacia baseDatos;

    public MedicamentoDAOImpl(BaseDatosFarmacia baseDatos) {
        this.baseDatos = baseDatos;

    }

    @Override
    public String insertar(Medicamento obj) throws DAOException {
        throw new UnsupportedOperationException("No implementado");
        /**String regInsertados="Registro Insertado Nº=";
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
         return regInsertados;*/

    }

    @Override
    public void modificar(Medicamento obj) throws DAOException {
        throw new UnsupportedOperationException("No implementado");
    }

    @Override
    public void eliminar(Medicamento obj) throws DAOException {
        throw new UnsupportedOperationException("No implementado");
    }

    @Override
    public List<Medicamento> obtenerTodos() throws DAOException {
        List<Medicamento> listaMedicamento = new ArrayList<>();

        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", BaseDatosFarmacia.Tablas.MEDICAMENTO);

        try(Cursor cursor = db.rawQuery(sql, null)) {
            if (cursor == null || !cursor.moveToFirst()) {
                return listaMedicamento;
            }

            do {
                Medicamento medicamento = new Medicamento();
                int idIndex=cursor.getColumnIndex(EntradaMedicamento.ID_MEDICAMENTO);
                int fechaExpedicionIndex=cursor.getColumnIndex(EntradaMedicamento.FECHA_EXPEDICION);
                int fechaExpiracionIndex=cursor.getColumnIndex(EntradaMedicamento.FECHA_EXPIRACION);
                int requiereRecetaMedicaIndex=cursor.getColumnIndex(EntradaMedicamento.REQUIERE_RECETA_MEDICA);
                int idArticuloIndex=cursor.getColumnIndex(EntradaMedicamento.ID_ARTICULO);
                int idFormaFarmaceuticaIndex=cursor.getColumnIndex(EntradaMedicamento.ID_FORMA_FARMACEUTICA);
                int idViaAdministracionIndex=cursor.getColumnIndex(EntradaMedicamento.ID_VIA_ADMINISTRACION);
                int idLaboratorioIndex=cursor.getColumnIndex(EntradaMedicamento.ID_LABORATORIO);



                if (idIndex != -1 || fechaExpedicionIndex != -1 || fechaExpiracionIndex != -1
                        || requiereRecetaMedicaIndex != -1 || idArticuloIndex != -1 || idFormaFarmaceuticaIndex != -1
                        || idViaAdministracionIndex != -1 || idLaboratorioIndex != -1)
                {
                    medicamento.setIdMedicamento(cursor.getString(idIndex));
                    medicamento.setFechaExpedicion(cursor.getString(fechaExpedicionIndex));
                    medicamento.setFechaExpiracion(cursor.getString(fechaExpiracionIndex));
                    medicamento.setRequiereRecetaMedica(cursor.getString(requiereRecetaMedicaIndex));
                    medicamento.setIdArticulo(cursor.getString(idArticuloIndex));
                    medicamento.setIdFormaFarmaceutica(cursor.getString(idFormaFarmaceuticaIndex));
                    medicamento.setIdViaAdministracion(cursor.getString(idViaAdministracionIndex));
                    medicamento.setIdLaboratorio(cursor.getString(idLaboratorioIndex));


                    listaMedicamento.add(medicamento);
                }
            } while (cursor.moveToNext());
        }
        return listaMedicamento;
    }

    @Override
    public Medicamento obtener(String id) throws DAOException {
        throw new UnsupportedOperationException("No implementado");
    }
}
