package sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.LaboratorioDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Laboratorio;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite.BaseDatosFarmacia.Tablas;

public class LaboratorioDAOImpl implements LaboratorioDAO {
    private final BaseDatosFarmacia baseDatos;

    public LaboratorioDAOImpl(BaseDatosFarmacia baseDatos) {
        this.baseDatos = baseDatos;
    }

    @Override
    public String insertar(Laboratorio obj) throws DAOException {
    throw new DAOException("No implementado");
    }

    @Override
    public void modificar(Laboratorio obj) throws DAOException {
        throw new DAOException("No implementado");
    }

    @Override
    public void eliminar(Laboratorio obj) throws DAOException {
        throw new DAOException("No implementado");
    }

    /**
     * Método que obtiene todos los registros de la entidad Laboratorio.
     * @return Lista con los registros de la entidad Laboratorio.
     */
    @Override
    public List<Laboratorio> obtenerTodos() throws DAOException {
        List<Laboratorio> listaLaboratorio = new ArrayList<>();

        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", Tablas.LABORATORIO);

        try (Cursor cursor = db.rawQuery(sql, null)) {
            // Verifica que el cursor no esté vacío.
            if (cursor == null || !cursor.moveToFirst()) {
                return listaLaboratorio;
            }

            // Itera sobre el cursor para obtener los datos.
            do {
                Laboratorio laboratorio = new Laboratorio();

                int idIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaLaboratorio.ID_LABORATORIO);
                int nombreIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaLaboratorio.NOMBRE_LABORATORIO);

                //verifica que las columnas existan
                if(idIndex != -1||nombreIndex==-1){
                    laboratorio.setIdLaboratorio(cursor.getString(idIndex));
                    laboratorio.setNombreLaboratorio(cursor.getString(nombreIndex));
                }

                listaLaboratorio.add(laboratorio);
            } while (cursor.moveToNext());
        }

        return listaLaboratorio;

    }

    @Override
    public Laboratorio obtener(String id) throws DAOException {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String selection = String.format("%s=?", FarmaciaContrato.EntradaLaboratorio.ID_LABORATORIO);
        String[] selectionArgs = {id};


        try (Cursor cursor = db.query(Tablas.LABORATORIO, null, selection, selectionArgs, null, null, null)) {
            if (cursor == null || !cursor.moveToFirst()) {
                throw new DAOException("No se ha encontro el laboratorio");
            }
            Laboratorio laboratorio = new Laboratorio();

            int idIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaLaboratorio.ID_LABORATORIO);
            int nombreIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaLaboratorio.NOMBRE_LABORATORIO);

            if (idIndex == -1 || nombreIndex == -1) {
                throw new DAOException("Error al obtener los índices de las columnas.");
            }


            laboratorio.setIdLaboratorio(cursor.getString(idIndex));
            laboratorio.setNombreLaboratorio(cursor.getString(nombreIndex));

            return laboratorio;

        }
    }

}
