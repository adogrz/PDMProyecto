package sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite;

import android.content.ContentValues;

import androidx.annotation.NonNull;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.database.SQLException;
import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DetalleVentaDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.BaseDatosFarmacia.Tablas;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.DetalleVenta;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.FarmaciaContrato.EntradaDetalleVenta;

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
        throw new UnsupportedOperationException("No implementado");
    }

    @Override
    public List<DetalleVenta> obtenerTodos() throws DAOException {
        return null;
    }

    @Override
    public DetalleVenta obtener(String id) throws DAOException {
        return null;
    }

}
