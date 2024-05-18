package sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.ArticuloDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Articulo;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite.BaseDatosFarmacia.Tablas;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite.FarmaciaContrato.EntradaArticulo;

/**
 * Clase que implementa el acceso a la base de datos para la entidad Articulo.
 */
public class ArticuloDAOImpl implements ArticuloDAO {
    private final BaseDatosFarmacia baseDatos;

    public ArticuloDAOImpl(BaseDatosFarmacia baseDatos) {
        this.baseDatos = baseDatos;
    }

    private boolean verificarIntegridad(Articulo articulo, int operacion) {
        String[] idProveedor = {articulo.getIdProveedor()};
        String[] idTipoArticulo = {articulo.getIdTipoArticulo()};
        String[] idArticulo = {articulo.getIdArticulo()};
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        Cursor cursorProveedor = db.query(Tablas.PROVEEDOR, null,
                EntradaArticulo.ID_PROVEEDOR + " = ?", idProveedor, null,
                null, null);
        Cursor cursorTipoArticulo = db.query(Tablas.TIPO_ARTICULO, null,
                EntradaArticulo.ID_TIPO_ARTICULO + " = ?", idTipoArticulo,
                null, null, null);
        Cursor cursorArticulo = db.query(Tablas.ARTICULO, null,
                EntradaArticulo.ID_ARTICULO + " = ?", idArticulo, null, null, null);

        boolean existeProveedor = cursorProveedor.moveToFirst();
        boolean existeTipoArticulo = cursorTipoArticulo.moveToFirst();
        boolean existeArticulo = cursorArticulo.moveToFirst();

        cursorProveedor.close();
        cursorTipoArticulo.close();
        cursorArticulo.close();

        switch (operacion) {
            case 1:
                // Verificar que al insertar un articulo, el proveedor y el tipo de articulo existan
                return !existeProveedor || !existeTipoArticulo;
            case 2:
                // Verificar que al modificar un articulo, el proveedor y el tipo de articulo existan
                // y que el articulo exista
                return !existeProveedor || !existeTipoArticulo || !existeArticulo;
            case 3:
                // Verificar que al eliminar un articulo, el articulo exista
                return !existeArticulo;
            default:
                return true;
        }
    }

    @NonNull
    private static ContentValues getContentValues(Articulo obj) {
        ContentValues valores = new ContentValues();
        valores.put(EntradaArticulo.NOMBRE_ARTICULO, obj.getNombre());
        valores.put(EntradaArticulo.PRECIO_UNITARIO_ARTICULO, obj.getPrecioUnitario());
        valores.put(EntradaArticulo.STOCK_ARTICULO, obj.getStock());
        valores.put(EntradaArticulo.DESCRIPCION_ARTICULO, obj.getDescripcion());
        valores.put(EntradaArticulo.ID_PROVEEDOR, obj.getIdProveedor());
        valores.put(EntradaArticulo.ID_TIPO_ARTICULO, obj.getIdTipoArticulo());
        return valores;
    }

    @NonNull
    private static ContentValues getContentValues(Articulo obj, String idArticulo) {
        ContentValues valores = new ContentValues();
        valores.put(EntradaArticulo.ID_ARTICULO, idArticulo);
        valores.put(EntradaArticulo.NOMBRE_ARTICULO, obj.getNombre());
        valores.put(EntradaArticulo.PRECIO_UNITARIO_ARTICULO, obj.getPrecioUnitario());
        valores.put(EntradaArticulo.STOCK_ARTICULO, obj.getStock());
        valores.put(EntradaArticulo.DESCRIPCION_ARTICULO, obj.getDescripcion());
        valores.put(EntradaArticulo.ID_PROVEEDOR, obj.getIdProveedor());
        valores.put(EntradaArticulo.ID_TIPO_ARTICULO, obj.getIdTipoArticulo());
        return valores;
    }

    @Override
    public String insertar(Articulo obj) throws DAOException {

        // Verificar que al insertar un nuevo articulo, el proveedor y el tipo de articulo existan
        if (verificarIntegridad(obj, 1)) {
            throw new DAOException("ArticuloDAO: No se pueden insertar un articulo con un " +
                    "proveedor o tipo de articulo no existente.");
        }

        SQLiteDatabase db = baseDatos.getWritableDatabase();

        // Generar Pk
        String idArticulo = obj.getIdArticulo();

        ContentValues valores = getContentValues(obj, idArticulo);

        // Insertar articulo
        try {
            db.insertOrThrow(Tablas.ARTICULO, null, valores);
            return idArticulo;
        } catch (SQLException e) {
            throw new DAOException("ArticuloDAO: Error al insertar un articulo: " + e.getMessage());
        }
    }

    @Override
    public void modificar(Articulo obj) throws DAOException {
        // Verificar que el articulo, el proveedor y el tipo de articulo existan
        if (verificarIntegridad(obj, 2)) {
            throw new DAOException("ArticuloDAO: El articulo, el proveedor o el tipo de articulo " +
                    "no existen.");
        }

        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = getContentValues(obj);

        String[] idArticulo = {obj.getIdArticulo()};

        // Modificar articulo
        db.update(Tablas.ARTICULO, valores, EntradaArticulo.ID_ARTICULO + " = ?", idArticulo);
    }

    @Override
    public void eliminar(Articulo obj) throws DAOException {
        if (verificarIntegridad(obj, 3)) {
            throw new DAOException("ArticuloDAO: El articulo no existe.");
        }

        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = String.format("%s = ?", EntradaArticulo.ID_ARTICULO);
        String[] idArticulo = {obj.getIdArticulo()};

        // Eliminar articulo
        db.delete(Tablas.ARTICULO, whereClause, idArticulo);
    }

    @Override
    public List<Articulo> obtenerTodos() throws DAOException {
        List<Articulo> articulos = new ArrayList<>();

        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", Tablas.ARTICULO);

        try (Cursor cursor = db.rawQuery(sql, null)) {
            if (cursor == null || !cursor.moveToFirst()) {
                return articulos;
            }

            do {
                Articulo articulo = new Articulo();

                int idIndex = cursor.getColumnIndex(EntradaArticulo.ID_ARTICULO);
                int nombreIndex = cursor.getColumnIndex(EntradaArticulo.NOMBRE_ARTICULO);
                int precioUnitarioIndex = cursor.getColumnIndex(EntradaArticulo.PRECIO_UNITARIO_ARTICULO);
                int stockIndex = cursor.getColumnIndex(EntradaArticulo.STOCK_ARTICULO);
                int descripcionIndex = cursor.getColumnIndex(EntradaArticulo.DESCRIPCION_ARTICULO);
                int idProveedorIndex = cursor.getColumnIndex(EntradaArticulo.ID_PROVEEDOR);
                int idTipoArticuloIndex = cursor.getColumnIndex(EntradaArticulo.ID_TIPO_ARTICULO);

                if (idIndex == -1 || nombreIndex == -1 || precioUnitarioIndex == -1 ||
                        stockIndex == -1 || descripcionIndex == -1 || idProveedorIndex == -1 ||
                        idTipoArticuloIndex == -1) {
                    throw new DAOException("Error al obtener los índices de las columnas.");
                }

                articulo.setIdArticulo(cursor.getString(idIndex));
                articulo.setNombre(cursor.getString(nombreIndex));
                articulo.setPrecioUnitario(cursor.getDouble(precioUnitarioIndex));
                articulo.setStock(cursor.getInt(stockIndex));
                articulo.setDescripcion(cursor.getString(descripcionIndex));
                articulo.setIdProveedor(cursor.getString(idProveedorIndex));
                articulo.setIdTipoArticulo(cursor.getString(idTipoArticuloIndex));

                articulos.add(articulo);
            } while (cursor.moveToNext());
        }

        return articulos;
    }

    @Override
    public Articulo obtener(String id) throws DAOException {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String selection = String.format("%s = ?", EntradaArticulo.ID_ARTICULO);
        String[] selectionArgs = {id};

        try (Cursor cursor = db.query(Tablas.ARTICULO, null, selection, selectionArgs, null, null,
                null)) {
            if (cursor == null || !cursor.moveToFirst()) {
                throw new DAOException("No se encontró el articulo");
            }

            Articulo articulo = new Articulo();

            int idIndex = cursor.getColumnIndex(EntradaArticulo.ID_ARTICULO);
            int nombreIndex = cursor.getColumnIndex(EntradaArticulo.NOMBRE_ARTICULO);
            int precioUnitarioIndex = cursor.getColumnIndex(EntradaArticulo.PRECIO_UNITARIO_ARTICULO);
            int stockIndex = cursor.getColumnIndex(EntradaArticulo.STOCK_ARTICULO);
            int descripcionIndex = cursor.getColumnIndex(EntradaArticulo.DESCRIPCION_ARTICULO);
            int idProveedorIndex = cursor.getColumnIndex(EntradaArticulo.ID_PROVEEDOR);
            int idTipoArticuloIndex = cursor.getColumnIndex(EntradaArticulo.ID_TIPO_ARTICULO);

            if (idIndex == -1 || nombreIndex == -1 || precioUnitarioIndex == -1 ||
                    stockIndex == -1 || descripcionIndex == -1 || idProveedorIndex == -1 ||
                    idTipoArticuloIndex == -1) {
                throw new DAOException("Error al obtener los índices de las columnas.");
            }

            articulo.setIdArticulo(cursor.getString(idIndex));
            articulo.setNombre(cursor.getString(nombreIndex));
            articulo.setPrecioUnitario(cursor.getDouble(precioUnitarioIndex));
            articulo.setStock(cursor.getInt(stockIndex));
            articulo.setDescripcion(cursor.getString(descripcionIndex));
            articulo.setIdProveedor(cursor.getString(idProveedorIndex));
            articulo.setIdTipoArticulo(cursor.getString(idTipoArticuloIndex));

            return articulo;
        }
    }
}