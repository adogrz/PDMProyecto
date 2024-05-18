package sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.LocalDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Local;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite.BaseDatosFarmacia.Tablas;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite.FarmaciaContrato.EntradaLocal;

public class LocalDAOImpl implements LocalDAO {
    private final BaseDatosFarmacia baseDatos;

    public LocalDAOImpl(BaseDatosFarmacia baseDatos) {
        this.baseDatos = baseDatos;
    }

    @Override
    public String insertar(Local obj) throws DAOException {
        throw new DAOException("No implementado");
    }

    @Override
    public void modificar(Local obj) throws DAOException {
        throw new DAOException("No implementado");
    }

    @Override
    public void eliminar(Local obj) throws DAOException {
        throw new DAOException("No implementado");
    }

    @Override
    public List<Local> obtenerTodos() throws DAOException {
        List<Local> listaLocal = new ArrayList<>();
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", Tablas.LOCAL);

        try (Cursor cursor = db.rawQuery(sql, null)) {
            if (cursor == null || !cursor.moveToFirst()) {
                return listaLocal;
            }

            do {
                Local local = new Local();

                int idIndex = cursor.getColumnIndex(EntradaLocal.ID_LOCAL);
                int nombreIndex = cursor.getColumnIndex(EntradaLocal.NOMBRE_LOCAL);
                int idDireccionIndex = cursor.getColumnIndex(EntradaLocal.ID_DIRECCION);

                if (idIndex == -1 || nombreIndex == -1 || idDireccionIndex == -1) {
                    throw new DAOException("Error al obtener los índices de las columnas.");
                }

                local.setIdLocal(cursor.getString(idIndex));
                local.setNombreLocal(cursor.getString(nombreIndex));
                local.setIdDireccion(cursor.getString(idDireccionIndex));

                listaLocal.add(local);
            } while (cursor.moveToNext());
        }

        return listaLocal;
    }

    @Override
    public Local obtener(String id) throws DAOException {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String selection = String.format("%s = ?", EntradaLocal.ID_LOCAL);
        String[] selectionArgs = {id};
        String[] columnas = {
                EntradaLocal.ID_LOCAL,
                EntradaLocal.NOMBRE_LOCAL,
                EntradaLocal.ID_DIRECCION
        };

        try (Cursor cursor = db.query(Tablas.LOCAL, columnas, selection, selectionArgs, null, null,
                null)) {
            if (cursor == null || !cursor.moveToFirst()) {
                throw new DAOException("No se ha encontrado el registro");
            }

            Local local = new Local();

            int idIndex = cursor.getColumnIndex(EntradaLocal.ID_LOCAL);
            int nombreIndex = cursor.getColumnIndex(EntradaLocal.NOMBRE_LOCAL);
            int idDireccionIndex = cursor.getColumnIndex(EntradaLocal.ID_DIRECCION);

            if (idIndex == -1 || nombreIndex == -1 || idDireccionIndex == -1) {
                throw new DAOException("Error al obtener los índices de las columnas.");
            }

            local.setIdLocal(cursor.getString(idIndex));
            local.setNombreLocal(cursor.getString(nombreIndex));
            local.setIdDireccion(cursor.getString(idDireccionIndex));

            return local;
        }
    }
}