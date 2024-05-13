package sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite;

import java.util.List;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.NonNull;
import java.util.ArrayList;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.VentaDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Venta;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.BaseDatosFarmacia.Tablas;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.FarmaciaContrato.EntradaVenta;
public class VentaDAOImpl implements VentaDAO {

    private final BaseDatosFarmacia baseDatos;
    public VentaDAOImpl(BaseDatosFarmacia baseDatos){
        this.baseDatos = baseDatos;
    }

    private boolean verificarIntegridad(Venta venta, int operacion) {
        String[] idCliente = {venta.getIdCliente()};
        String[] idMetodoPago = {venta.getIdMetodoPago()};
        String[] idVenta = {venta.getIdVenta()};
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        Cursor cursorCliente = db.query(Tablas.CLIENTE, null,
                EntradaVenta.ID_CLIENTE + " = ?", idCliente, null,
                null, null);
        Cursor cursorMetodoPago = db.query(Tablas.METODO_PAGO, null,
                EntradaVenta.ID_METODO_PAGO + " = ?", idMetodoPago,
                null, null, null);
        Cursor cursorVenta = db.query(Tablas.VENTA, null,
                EntradaVenta.ID_VENTA + " = ?", idVenta,
                null, null, null);

        boolean existeCliente = cursorCliente.moveToFirst();
        boolean existeMetodoPago = cursorMetodoPago.moveToFirst();
        boolean existeVenta = cursorVenta.moveToFirst();

        cursorCliente.close();
        cursorMetodoPago.close();
        cursorVenta.close();

        switch (operacion) {
            case 1:
                // Verificar que al insertar una Venta, el Cliente y el MetodoPago existan
                return existeCliente && existeMetodoPago;
            case 2:
                // Verificar que al modificar una Venta, el Cliente y el MetodoPago existan
                // y que el articulo exista
                return existeCliente && existeMetodoPago && existeVenta;
            case 3:
                // Verificar que al eliminar una Venta exista la Venta
                return existeVenta;
            default:
                return false;
        }
    }

    private static ContentValues getContentValues(Venta obj) {
        ContentValues valores = new ContentValues();
        valores.put(EntradaVenta.MONTO_TOTAL_VENTA, obj.getMontoTotalVenta());
        valores.put(EntradaVenta.FECHA_VENTA, obj.getFechaVenta());
        return valores;
    }

    @NonNull
    private static ContentValues getContentValues(Venta obj, String idVenta) {
        ContentValues valores = new ContentValues();
        valores.put(EntradaVenta.ID_VENTA, obj.getIdVenta());
        valores.put(EntradaVenta.MONTO_TOTAL_VENTA, obj.getMontoTotalVenta());
        valores.put(EntradaVenta.FECHA_VENTA, obj.getFechaVenta());
        valores.put(EntradaVenta.ID_CLIENTE, obj.getIdCliente());
        valores.put(EntradaVenta.ID_METODO_PAGO, obj.getIdMetodoPago());
        return valores;
    }

    @Override
    public String insertar(Venta obj) throws DAOException {
        // Verificar que la Venta, el Cliente y el MetodoPago existen
        if (!verificarIntegridad(obj, 1)) {
            throw new DAOException("VentaDAO: No se pueden insertar una venta con un " +
                    "cliente o metodo de pago no existente.");
        }

        SQLiteDatabase db = baseDatos.getWritableDatabase();

        // Generar Pk
        String idVenta = obj.getIdVenta();

        ContentValues valores = getContentValues(obj, idVenta);

        // Insertar venta
        try {
            db.insertOrThrow(Tablas.VENTA, null, valores);
            return idVenta;
        } catch (SQLException e) {
            throw new DAOException("VentaDAO: Error al insertar una venta: " + e.getMessage());
        }
    }

    @Override
    public void modificar(Venta obj) throws DAOException {
        // Verificar que el cliente, el metodo de pago y la venta existan
        if (!verificarIntegridad(obj, 2)) {
            throw new DAOException("VentaDAO: El cliente, el metodo de pago o la venta" +
                    "no existen.");
        }

        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = getContentValues(obj);

        String[] idVenta = {obj.getIdVenta()};

        // Modificar venta
        db.update(Tablas.VENTA, valores, EntradaVenta.ID_VENTA + " = ?", idVenta);
    }

    @Override
    public void eliminar(Venta obj) throws DAOException {
        if (!verificarIntegridad(obj, 3)) {
            throw new DAOException("VentaDAO: La venta no existe.");
        }

        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = String.format("%s = ?", EntradaVenta.ID_VENTA);
        String[] idVenta = {obj.getIdVenta()};

        // Eliminar venta
        db.delete(Tablas.VENTA, whereClause, idVenta);
    }

    @Override
    public List<Venta> obtenerTodos() throws DAOException {
        List<Venta> ventas = new ArrayList<>();

        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", Tablas.VENTA);

        try (Cursor cursor = db.rawQuery(sql, null)) {
            if (cursor == null || !cursor.moveToFirst()) {
                return ventas;
            }

            do {
                Venta venta = new Venta();

                int idIndex = cursor.getColumnIndex(EntradaVenta.ID_VENTA);
                int montoTotalIndex = cursor.getColumnIndex(EntradaVenta.MONTO_TOTAL_VENTA);
                int fechaVentaIndex = cursor.getColumnIndex(EntradaVenta.FECHA_VENTA);
                int idClienteIndex = cursor.getColumnIndex(EntradaVenta.ID_CLIENTE);
                int idMetodoPagoIndex = cursor.getColumnIndex(EntradaVenta.ID_METODO_PAGO);

                if (idIndex == -1 || montoTotalIndex == -1 || fechaVentaIndex == -1 ||
                        idClienteIndex == -1 || idMetodoPagoIndex == -1) {
                    throw new DAOException("Error al obtener los índices de las columnas.");
                }

                venta.setIdVenta(cursor.getString(idIndex));
                venta.setMontoTotalVenta(cursor.getDouble(montoTotalIndex));
                venta.setFechaVenta(cursor.getString(fechaVentaIndex));
                venta.setIdCliente(cursor.getString(idClienteIndex));
                venta.setIdMetodoPago(cursor.getString(idMetodoPagoIndex));

                ventas.add(venta);
            } while (cursor.moveToNext());
        }

        return ventas;
    }

    @Override
    public Venta obtener(String id) throws DAOException {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String selection = String.format("%s = ?", EntradaVenta.ID_VENTA);
        String[] selectionArgs = {id};

        try (Cursor cursor = db.query(Tablas.VENTA, null, selection, selectionArgs, null, null,
                null)) {
            if (cursor == null || !cursor.moveToFirst()) {
                throw new DAOException("No se encontró la venta");
            }

            Venta venta = new Venta();

            int idIndex = cursor.getColumnIndex(EntradaVenta.ID_VENTA);
            int montoTotalIndex = cursor.getColumnIndex(EntradaVenta.MONTO_TOTAL_VENTA);
            int fechaVentaIndex = cursor.getColumnIndex(EntradaVenta.FECHA_VENTA);
            int idClienteIndex = cursor.getColumnIndex(EntradaVenta.ID_CLIENTE);
            int idMetodoPagoIndex = cursor.getColumnIndex(EntradaVenta.ID_METODO_PAGO);

            if (idIndex == -1 || montoTotalIndex == -1 || fechaVentaIndex == -1 ||
                    idClienteIndex == -1 || idMetodoPagoIndex == -1) {
                throw new DAOException("Error al obtener los índices de las columnas.");
            }

            venta.setIdVenta(cursor.getString(idIndex));
            venta.setMontoTotalVenta(cursor.getDouble(montoTotalIndex));
            venta.setFechaVenta(cursor.getString(fechaVentaIndex));
            venta.setIdCliente(cursor.getString(idClienteIndex));
            venta.setIdMetodoPago(cursor.getString(idMetodoPagoIndex));

            return venta;
        }
    }

    public Venta obtenerIdVenta(String idVenta) throws DAOException{
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String selection = String.format("%s = ?", EntradaVenta.ID_VENTA);
        String[] selectionArgs = {idVenta};

        try (Cursor cursor = db.query(Tablas.VENTA, null, selection, selectionArgs, null, null, null)) {
            if (cursor == null || !cursor.moveToFirst()) {
                return null;
            }

            Venta venta = new Venta();

            int idIndex = cursor.getColumnIndex(EntradaVenta.ID_VENTA);
            int montoTotalIndex = cursor.getColumnIndex(EntradaVenta.MONTO_TOTAL_VENTA);
            int fechaVentaIndex = cursor.getColumnIndex(EntradaVenta.FECHA_VENTA);
            int idClienteIndex = cursor.getColumnIndex(EntradaVenta.ID_CLIENTE);
            int idMetodoPagoIndex = cursor.getColumnIndex(EntradaVenta.ID_METODO_PAGO);

            if (idIndex == -1 || montoTotalIndex == -1 || fechaVentaIndex == -1 ||
                    idClienteIndex == -1 || idMetodoPagoIndex == -1) {
                throw new DAOException("Error al obtener los índices de las columnas.");
            }

            venta.setIdVenta(cursor.getString(idIndex));
            venta.setMontoTotalVenta(cursor.getDouble(montoTotalIndex));
            venta.setFechaVenta(cursor.getString(fechaVentaIndex));
            venta.setIdCliente(cursor.getString(idClienteIndex));
            venta.setIdMetodoPago(cursor.getString(idMetodoPagoIndex));

            return venta;
        }
    }

}
