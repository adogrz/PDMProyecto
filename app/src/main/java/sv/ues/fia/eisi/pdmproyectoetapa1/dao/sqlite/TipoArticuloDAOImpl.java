package sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.TipoArticuloDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.TipoArticulo;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.BaseDatosFarmacia.Tablas;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.FarmaciaContrato.EntradaTipoArticulo;

/**
 * Clase que implementa el acceso a la base de datos para la entidad TipoArticulo.
 */
public class TipoArticuloDAOImpl implements TipoArticuloDAO {
    private final BaseDatosFarmacia baseDatos;

    public TipoArticuloDAOImpl(BaseDatosFarmacia baseDatos) {
        this.baseDatos = baseDatos;
    }

    @Override
    public String insertar(TipoArticulo obj) throws DAOException {
       throw new DAOException("No implementado");
    }

    @Override
    public void modificar(TipoArticulo obj) throws DAOException {
        throw new DAOException("No implementado");
    }

    @Override
    public void eliminar(TipoArticulo obj) throws DAOException {
       throw new DAOException("No implementado");
    }

    /**
     * Método que obtiene todos los registros de la entidad TipoArticulo.
     * @return Lista con los registros de la entidad TipoArticulo.
     */
    @Override
    public List<TipoArticulo> obtenerTodos() throws DAOException {
        List<TipoArticulo> listaTipoArticulo = new ArrayList<>();

        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", Tablas.TIPO_ARTICULO);

        try (Cursor cursor = db.rawQuery(sql, null)) {
            // Verifica que el cursor no esté vacío.
            if (cursor == null || !cursor.moveToFirst()) {
                return listaTipoArticulo;
            }

            // Itera sobre el cursor para obtener los datos.
            do {
                TipoArticulo tipoArticulo = new TipoArticulo();

                int idIndex = cursor.getColumnIndex(EntradaTipoArticulo.ID_TIPO_ARTICULO);
                int nombreIndex = cursor.getColumnIndex(EntradaTipoArticulo.NOMBRE_TIPO_ARTICULO);

                // Verifica que las columnas existan.
                if (idIndex == -1 || nombreIndex == -1) {
                    return listaTipoArticulo;
                }

                tipoArticulo.setId(cursor.getString(idIndex));
                tipoArticulo.setNombre(cursor.getString(nombreIndex));

                listaTipoArticulo.add(tipoArticulo);
            } while (cursor.moveToNext());
        }

        return listaTipoArticulo;
    }

    @Override
    public TipoArticulo obtener(String id) throws DAOException {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String selection = String.format("%s = ?", EntradaTipoArticulo.ID_TIPO_ARTICULO);
        String[] selectionArgs = {id};
        String[] columnas = {
                EntradaTipoArticulo.ID_TIPO_ARTICULO,
                EntradaTipoArticulo.NOMBRE_TIPO_ARTICULO
        };

        try (Cursor cursor = db.query(Tablas.TIPO_ARTICULO, columnas, selection, selectionArgs, null, null, null)) {
            if (cursor == null || !cursor.moveToFirst()) {
                throw new DAOException("No se ha encontro el tipo de articulo");
            }

            TipoArticulo tipoArticulo = new TipoArticulo();

            int idIndex = cursor.getColumnIndex(EntradaTipoArticulo.ID_TIPO_ARTICULO);
            int nombreIndex = cursor.getColumnIndex(EntradaTipoArticulo.NOMBRE_TIPO_ARTICULO);

            if (idIndex == -1 || nombreIndex == -1) {
                throw new DAOException("Error al obtener los índices de las columnas.");
            }

            tipoArticulo.setId(cursor.getString(idIndex));
            tipoArticulo.setNombre(cursor.getString(nombreIndex));

            return tipoArticulo;

        }
    }
}
