package sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite;

import android.content.ContentValues;

import androidx.annotation.NonNull;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.database.SQLException;

import java.util.ArrayList;
import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DetalleVentaDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.BaseDatosFarmacia.Tablas;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.DetalleVenta;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.FarmaciaContrato.EntradaDetalleVenta;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Venta;

public class DetalleVentaDAOImpl implements DetalleVentaDAO {

    private final BaseDatosFarmacia baseDatos;
    public DetalleVentaDAOImpl(BaseDatosFarmacia baseDatos){
        this.baseDatos = baseDatos;
    }

    private boolean verificarIntegridad(DetalleVenta detalleVenta, int operacion) {
        String[] idVenta = {detalleVenta.getIdVenta()};
        String[] idArticulo = {detalleVenta.getIdArticulo()};
        String[] idDetalleVenta = {detalleVenta.getIdDetalleVenta()};
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        Cursor cursorVenta = db.query(Tablas.VENTA, null,
                EntradaDetalleVenta.ID_VENTA + " = ?", idVenta, null,
                null, null);
        Cursor cursorArticulo = db.query(Tablas.ARTICULO, null,
                EntradaDetalleVenta.ID_ARTICULO + " = ?", idArticulo,
                null, null, null);
        Cursor cursorDetalleVenta = db.query(Tablas.DETALLE_VENTA, null,
                EntradaDetalleVenta.ID_DETALLE_VENTA + " = ?", idDetalleVenta, null, null, null);

        boolean existeVenta = cursorVenta.moveToFirst();
        boolean existeArticulo = cursorArticulo.moveToFirst();
        boolean existeDetalleVenta = cursorDetalleVenta.moveToFirst();

        cursorVenta.close();
        cursorArticulo.close();
        cursorDetalleVenta.close();

        switch (operacion) {
            case 1:
                // Verificar que al insertar un DetalleVenta, la Venta y el Articulo existan
                return existeVenta && existeArticulo;
            case 2:
                // Verificar que al modificar un DetalleVenta, la Venta y el Articulo existan
                // y que el DetalleVenta exista
                return existeVenta && existeArticulo && existeDetalleVenta;
            case 3:
                // Verificar que al eliminar un DetalleVenta exista el DetalleVenta
                return existeDetalleVenta;
            default:
                return false;
        }
    }

    private static ContentValues getContentValues(DetalleVenta obj) {
        ContentValues valores = new ContentValues();
        valores.put(FarmaciaContrato.EntradaDetalleVenta.SUBTOTAL_VENTA, obj.getSubtotalVenta());
        valores.put(FarmaciaContrato.EntradaDetalleVenta.CANTIDAD_PRODUCTO_VENTA, obj.getCantidadProductoVenta());
        return valores;
    }

    @NonNull
    private static ContentValues getContentValues(DetalleVenta obj, String idDetalleVenta) {
        ContentValues valores = new ContentValues();
        valores.put(FarmaciaContrato.EntradaDetalleVenta.ID_DETALLE_VENTA, idDetalleVenta);
        valores.put(FarmaciaContrato.EntradaDetalleVenta.SUBTOTAL_VENTA, obj.getSubtotalVenta());
        valores.put(FarmaciaContrato.EntradaDetalleVenta.CANTIDAD_PRODUCTO_VENTA, obj.getCantidadProductoVenta());
        valores.put(FarmaciaContrato.EntradaDetalleVenta.ID_VENTA, obj.getIdVenta());
        valores.put(FarmaciaContrato.EntradaDetalleVenta.ID_ARTICULO, obj.getIdArticulo());
        return valores;
    }


    @Override
    public String insertar(DetalleVenta obj) throws DAOException {
        // Verificar que la Venta, el Articulo y el DetalleVenta existen
        if (!verificarIntegridad(obj, 1)) {
            throw new DAOException("VentaDAO: No se pueden insertar una venta con un " +
                    "cliente o metodo de pago no existente.");
        }

        SQLiteDatabase db = baseDatos.getWritableDatabase();

        // Generar Pk
        String idDetalleVenta = obj.getIdDetalleVenta();

        ContentValues valores = getContentValues(obj, idDetalleVenta);

        // Insertar detalleVenta
        try {
            db.insertOrThrow(Tablas.DETALLE_VENTA, null, valores);
            return idDetalleVenta;
        } catch (SQLException e) {
            throw new DAOException("VentaDAO: Error al insertar una ventao: " + e.getMessage());
        }
    }

    @Override
    public void modificar(DetalleVenta obj) throws DAOException {
        // Verificar que el detalleventa, la venta y el cliente existen
        if (!verificarIntegridad(obj, 2)) {
            throw new DAOException("ArticuloDAO: El articulo, el proveedor o el tipo de articulo " +
                    "no existen.");
        }

        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = getContentValues(obj);

        String[] idDetalleVenta = {obj.getIdDetalleVenta()};

        // Modificar detalleVenta
        db.update(Tablas.DETALLE_VENTA, valores, EntradaDetalleVenta.ID_DETALLE_VENTA + " = ?", idDetalleVenta);
    }

    @Override
    public void eliminar(DetalleVenta obj) throws DAOException {
        if (!verificarIntegridad(obj, 3)) {
            throw new DAOException("DetalleVentaDAO: El detalle no existe.");
        }

        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = String.format("%s = ?", EntradaDetalleVenta.ID_DETALLE_VENTA);
        String[] idDetalleVenta = {obj.getIdDetalleVenta()};

        // Eliminar DetalleVenta
        db.delete(Tablas.DETALLE_VENTA, whereClause, idDetalleVenta);
    }

    @Override
    public List<DetalleVenta> obtenerTodos() throws DAOException {
        List<DetalleVenta> detalleVentas = new ArrayList<>();

        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", Tablas.DETALLE_VENTA);

        try (Cursor cursor = db.rawQuery(sql, null)) {
            if (cursor == null || !cursor.moveToFirst()) {
                return detalleVentas;
            }

            do {
                DetalleVenta detalleVenta = new DetalleVenta();

                int idDetalleVenta = cursor.getColumnIndex(EntradaDetalleVenta.ID_DETALLE_VENTA);
                int idVenta = cursor.getColumnIndex(EntradaDetalleVenta.ID_VENTA);
                int idArticulo = cursor.getColumnIndex(EntradaDetalleVenta.ID_ARTICULO);
                int cantidadProductoVenta = cursor.getColumnIndex(EntradaDetalleVenta.CANTIDAD_PRODUCTO_VENTA);
                int subtotalVenta = cursor.getColumnIndex(EntradaDetalleVenta.SUBTOTAL_VENTA);

                if (idDetalleVenta == -1 || idVenta == -1 || idArticulo == -1 ||
                        cantidadProductoVenta == -1 || subtotalVenta == -1) {
                    throw new DAOException("Error al obtener los índices de las columnas.");
                }

                detalleVenta.setIdDetalleVenta(cursor.getString(idDetalleVenta));
                detalleVenta.setIdVenta(cursor.getString(idVenta));
                detalleVenta.setIdArticulo(cursor.getString(idArticulo));
                detalleVenta.setCantidadProductoVenta(cursor.getInt(cantidadProductoVenta));
                detalleVenta.setSubtotalVenta(cursor.getDouble(subtotalVenta));

                detalleVentas.add(detalleVenta);

            } while (cursor.moveToNext());
        }

        return detalleVentas;
    }

    @Override
    public DetalleVenta obtener(String id) throws DAOException {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String selection = String.format("%s = ?", EntradaDetalleVenta.ID_DETALLE_VENTA);
        String[] selectionArgs = {id};

        try (Cursor cursor = db.query(Tablas.DETALLE_VENTA, null, selection, selectionArgs, null, null,
                null)) {
            if (cursor == null || !cursor.moveToFirst()) {
                throw new DAOException("No se encontró el detalle de venta");
            }

            DetalleVenta detalleVenta = new DetalleVenta();

            int idDetalleVenta = cursor.getColumnIndex(EntradaDetalleVenta.ID_DETALLE_VENTA);
            int idVenta = cursor.getColumnIndex(EntradaDetalleVenta.ID_VENTA);
            int idArticulo = cursor.getColumnIndex(EntradaDetalleVenta.ID_ARTICULO);
            int cantidadProductoVenta = cursor.getColumnIndex(EntradaDetalleVenta.CANTIDAD_PRODUCTO_VENTA);
            int subtotalVenta = cursor.getColumnIndex(EntradaDetalleVenta.SUBTOTAL_VENTA);

            if (idDetalleVenta == -1 || idVenta == -1 || idArticulo == -1 ||
                    cantidadProductoVenta == -1 || subtotalVenta == -1) {
                throw new DAOException("Error al obtener los índices de las columnas.");
            }

            detalleVenta.setIdDetalleVenta(cursor.getString(idDetalleVenta));
            detalleVenta.setIdVenta(cursor.getString(idVenta));
            detalleVenta.setIdArticulo(cursor.getString(idArticulo));
            detalleVenta.setCantidadProductoVenta(cursor.getInt(cantidadProductoVenta));
            detalleVenta.setSubtotalVenta(cursor.getDouble(subtotalVenta));

            return detalleVenta;
        }
    }

    @Override
    public DetalleVenta obtenerPorIdVenta(String idVenta) throws DAOException{
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String selection = String.format("%s = ?", EntradaDetalleVenta.ID_VENTA);
        String[] selectionArgs = {idVenta};

        try (Cursor cursor = db.query(Tablas.DETALLE_VENTA, null, selection, selectionArgs, null, null, null)) {
            if (cursor == null || !cursor.moveToFirst()) {
                throw new DAOException("No se encontró el detalle de venta");
            }

            DetalleVenta detalleVenta = new DetalleVenta();

            int idDetalleVenta = cursor.getColumnIndex(EntradaDetalleVenta.ID_DETALLE_VENTA);
            int idArticulo = cursor.getColumnIndex(EntradaDetalleVenta.ID_ARTICULO);
            int cantidadProductoVenta = cursor.getColumnIndex(EntradaDetalleVenta.CANTIDAD_PRODUCTO_VENTA);
            int subtotalVenta = cursor.getColumnIndex(EntradaDetalleVenta.SUBTOTAL_VENTA);

            if (idDetalleVenta == -1 || idArticulo == -1 ||
                    cantidadProductoVenta == -1 || subtotalVenta == -1) {
                throw new DAOException("Error al obtener los índices de las columnas.");
            }

            detalleVenta.setIdDetalleVenta(cursor.getString(idDetalleVenta));
            detalleVenta.setIdVenta(idVenta);
            detalleVenta.setIdArticulo(cursor.getString(idArticulo));
            detalleVenta.setCantidadProductoVenta(cursor.getInt(cantidadProductoVenta));
            detalleVenta.setSubtotalVenta(cursor.getDouble(subtotalVenta));

            return detalleVenta;
        }
    }

}
