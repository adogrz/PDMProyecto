package sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.LocalArticuloDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.LocalArticulo;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.BaseDatosFarmacia.Tablas;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.FarmaciaContrato.EntradaLocalArticulo;

public class LocalArticuloDAOImpl implements LocalArticuloDAO {
    private final BaseDatosFarmacia baseDatos;

    public LocalArticuloDAOImpl(BaseDatosFarmacia baseDatos) {
        this.baseDatos = baseDatos;
    }

    private boolean verificarIntegridad(LocalArticulo obj) {
        String[] idLocal = {obj.getIdLocal()};
        String[] idArticulo = {obj.getIdArticulo()};
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        Cursor cursorLocal = db.query(Tablas.LOCAL, null,
                EntradaLocalArticulo.ID_LOCAL + " = ?", idLocal, null, null, null);
        Cursor cursorArticulo = db.query(Tablas.ARTICULO, null,
                EntradaLocalArticulo.ID_ARTICULO + " = ?", idArticulo, null, null, null);

        boolean existeLocal = cursorLocal.moveToFirst();
        boolean existeArticulo = cursorArticulo.moveToFirst();

        cursorLocal.close();
        cursorArticulo.close();

        return !(existeLocal && existeArticulo);
    }

    private ContentValues getContentValues(LocalArticulo obj) {
        ContentValues valores = new ContentValues();
        valores.put(EntradaLocalArticulo.ID_LOCAL, obj.getIdLocal());
        valores.put(EntradaLocalArticulo.ID_ARTICULO, obj.getIdArticulo());
        return valores;
    }

    @Override
    public String insertar(LocalArticulo obj) throws DAOException {
        // Verificar que al insertar un nuevo registro, el idLocal y el idArticulo existan
        if (verificarIntegridad(obj)) {
            throw new DAOException("No existe el local ni el artículo asociado");
        }

        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = getContentValues(obj);

        // Insertar localArticulo
        try {
            db.insertOrThrow(Tablas.LOCAL_ARTICULO, null, valores);
        } catch (SQLException e) {
            throw new DAOException("LocalArticuloDAO: Error al insertar un registro de la tabla localArticulo");
        }

        return null;
    }

    @Override
    public void modificar(LocalArticulo obj) throws DAOException {
        if (verificarIntegridad(obj)) {
            throw new DAOException("No existe el local ni el artículo asociado");
        }

        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = getContentValues(obj);

        String[] whereArgs = {obj.getIdLocal(), obj.getIdArticulo()};

        // Modificar localArticulo
        try {
            db.update(Tablas.LOCAL_ARTICULO, valores,
                    String.format("%s = ? AND %s = ?", EntradaLocalArticulo.ID_LOCAL, EntradaLocalArticulo.ID_ARTICULO),
                    whereArgs);
        } catch (SQLException e) {
            throw new DAOException("LocalArticuloDAO: Error al modificar un registro de la tabla localArticulo");
        }
    }

    @Override
    public void eliminar(LocalArticulo obj) throws DAOException {
        throw new DAOException("No implementado");
    }

    @Override
    public List<LocalArticulo> obtenerTodos() throws DAOException {
        throw new DAOException("No implementado");
    }

    @Override
    public LocalArticulo obtener(String id) throws DAOException {
        throw new DAOException("No implementado");
    }

    @Override
    public LocalArticulo obtenerPorIdArticulo(String idArticulo) throws DAOException {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("%s = ?", EntradaLocalArticulo.ID_ARTICULO);
        String[] selectionArgs = {idArticulo};

        try (Cursor cursor = db.query(Tablas.LOCAL_ARTICULO, null, sql, selectionArgs, null, null, null)) {
            if (cursor == null || !cursor.moveToFirst()) {
                throw new DAOException("No se encontró el registro");
            }

            LocalArticulo localArticulo = new LocalArticulo();

            int idLocalIndex = cursor.getColumnIndex(EntradaLocalArticulo.ID_LOCAL);
            int idArticuloIndex = cursor.getColumnIndex(EntradaLocalArticulo.ID_ARTICULO);

            localArticulo.setIdLocal(cursor.getString(idLocalIndex));
            localArticulo.setIdArticulo(cursor.getString(idArticuloIndex));

            return localArticulo;
        }
    }
}
