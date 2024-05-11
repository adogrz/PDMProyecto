package sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite;

import androidx.annotation.NonNull;
import android.database.Cursor;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.BaseDatosFarmacia.Tablas;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.FarmaciaContrato.EntradaCompra;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

import android.database.SQLException;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.CompraDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Compra;


public class CompraDAOImpl implements CompraDAO {
    private final BaseDatosFarmacia baseDatos;

    public CompraDAOImpl(BaseDatosFarmacia baseDatos) {
        this.baseDatos = baseDatos;
    }

    private boolean verificarIntegridad(Compra compra, int operacion) {
        String[] idProveedor = {compra.getidProveedor()};
        String[] idCompra = {compra.getIdCompra()};
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        Cursor cursorProveedor = db.query(Tablas.PROVEEDOR, null,
                EntradaCompra.ID_PROVEEDOR + " = ?", idProveedor, null,
                null, null);
        Cursor cursorCompra = db.query(Tablas.COMPRA, null,
                EntradaCompra.ID_COMPRA + " = ?", idCompra, null, null, null);

        boolean existeProveedor = cursorProveedor.moveToFirst();
        boolean existeCompra = cursorCompra.moveToFirst();

        cursorProveedor.close();
        cursorCompra.close();

        switch (operacion) {
            case 1:
                // Verificar que al insertar una compra, el proveedor  exista
                return !existeProveedor;

            case 2:
                // Verificar que al modificar una compra, la compra o proveedor exista
                return !existeCompra || !existeProveedor;

            case 3:
                //Verificar que al eliminar exista una compra
                return !existeCompra;

            default:
                return true;
        }
    }

    @NonNull
    private static ContentValues getContentValues(Compra obj) {
        ContentValues valores = new ContentValues();
        valores.put(EntradaCompra.MONTO_TOTAL_COMPRA, obj.getMontoTotal());
        valores.put(EntradaCompra.FECHA_COMPRA, obj.getFechaCompra());
        valores.put(EntradaCompra.ID_PROVEEDOR, obj.getidProveedor());
        return valores;
    }

    @NonNull
    private static ContentValues getContentValues(Compra obj, String idCompra) {
        ContentValues valores = new ContentValues();
        valores.put(EntradaCompra.ID_COMPRA, idCompra);
        valores.put(EntradaCompra.MONTO_TOTAL_COMPRA, obj.getMontoTotal());
        valores.put(EntradaCompra.FECHA_COMPRA, obj.getFechaCompra());
        valores.put(EntradaCompra.ID_PROVEEDOR, obj.getidProveedor());

        return valores;
    }

    @Override

    public String insertar(Compra obj) throws DAOException {
            // Verificar que el compra y el proveedor
            if (verificarIntegridad(obj, 1)) {
                throw new DAOException("CompraDAO: No se pueden insertar una compra con un " +
                        "proveedor si no existente.");
            }

            SQLiteDatabase db = baseDatos.getWritableDatabase();

            // Generar Pk
            String idCompra = obj.getIdCompra();

            ContentValues valores = getContentValues(obj, idCompra);

            // Insertar articulo
            try {
                db.insertOrThrow(Tablas.COMPRA, null, valores);
                return idCompra;
            } catch (SQLException e) {
                throw new DAOException("CompraDAO: Error al insertar una compra: " + e.getMessage());
            }

    }

    @Override
    public void modificar(Compra obj) throws DAOException {
        // Verificar que la compra y proveedor  existan
        if (verificarIntegridad(obj, 2)) {
            throw new DAOException("CompraDAO: La compra, o el proveedor  " +
                    "no existen.");
        }

        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = getContentValues(obj);

        String[] idCompra = {obj.getIdCompra()};

        // Modificar articulo
        db.update(Tablas.COMPRA, valores, EntradaCompra.ID_COMPRA + " = ?", idCompra);


    }

    @Override
    public void eliminar(Compra obj) throws DAOException {
        if (verificarIntegridad(obj, 3)) {
            throw new DAOException("CompraDAO: La compra no existe.");
        }

        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = String.format("%s = ?", EntradaCompra.ID_COMPRA);
        String[] idCompra = {obj.getIdCompra()};

        // Eliminar articulo
        db.delete(Tablas.COMPRA, whereClause, idCompra);
    }

    @Override
    public List<Compra> obtenerTodos() throws DAOException {
        List<Compra> compras = new ArrayList<>();

        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", Tablas.COMPRA);

        try (Cursor cursor = db.rawQuery(sql, null)) {
            // Verifica que el cursor no esté vacío.
            if (cursor == null || !cursor.moveToFirst()) {
                return compras;
            }

            do {
                Compra compra = new Compra();

                int idCompraIndex = cursor.getColumnIndex(EntradaCompra.ID_COMPRA);
                int montoTotalCompraIndex = cursor.getColumnIndex(EntradaCompra.MONTO_TOTAL_COMPRA);
                int fechaCompraIndex = cursor.getColumnIndex(EntradaCompra.FECHA_COMPRA);
                int idProveedorIndex = cursor.getColumnIndex(EntradaCompra.ID_PROVEEDOR);

                // Verifica que las columnas existan.
                if (idCompraIndex == -1 || montoTotalCompraIndex == -1 || fechaCompraIndex == -1
                        | idProveedorIndex == -1) {
                    throw new DAOException("Error al obtener los índices de las columnas.");
                }

                compra.setIdCompra(cursor.getString(idCompraIndex));
                compra.setMontoTotal(cursor.getDouble(montoTotalCompraIndex));
                compra.setFechaCompra(cursor.getString(fechaCompraIndex));
                compra.setidProveedor(cursor.getString(idProveedorIndex));

                compras.add(compra);
            } while (cursor.moveToNext());
        }

        return compras;
    }

    @Override
    public Compra obtener(String id) throws DAOException {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String selection = String.format("%s = ?", EntradaCompra.ID_COMPRA);
        String[] selectionArgs = {id};

        try (Cursor cursor = db.query(Tablas.COMPRA, null, selection, selectionArgs, null,
                null, null)) {
            if (cursor == null || !cursor.moveToFirst()) {
                throw new DAOException("No se encontró  la compra");
            }

            Compra compra = new Compra();

            int idCompraIndex = cursor.getColumnIndex(EntradaCompra.ID_COMPRA);
            int montoTotalCompraIndex = cursor.getColumnIndex(EntradaCompra.MONTO_TOTAL_COMPRA);
            int fechaCompraIndex = cursor.getColumnIndex(EntradaCompra.FECHA_COMPRA);
            int idProveedorIndex = cursor.getColumnIndex(EntradaCompra.ID_PROVEEDOR);

            if (idCompraIndex == -1 || montoTotalCompraIndex == -1 || fechaCompraIndex == -1
                    | idProveedorIndex == -1) {
                throw new DAOException("Error al obtener los índices de las columnas.");
            }

            compra.setIdCompra(cursor.getString(idCompraIndex));
            compra.setMontoTotal(cursor.getDouble(montoTotalCompraIndex));
            compra.setFechaCompra(cursor.getString(fechaCompraIndex));
            compra.setidProveedor(cursor.getString(idProveedorIndex));

            return compra;
        }
    }
}
