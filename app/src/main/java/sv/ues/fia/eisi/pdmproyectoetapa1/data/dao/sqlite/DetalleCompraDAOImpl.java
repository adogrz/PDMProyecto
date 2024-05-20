package sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.DetalleCompraDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.DetalleCompra;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite.BaseDatosFarmacia.Tablas;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite.FarmaciaContrato.EntradaDetalleCompra;

public class DetalleCompraDAOImpl implements DetalleCompraDAO {
    private final BaseDatosFarmacia baseDatos;

    public DetalleCompraDAOImpl(BaseDatosFarmacia baseDatos) {
        this.baseDatos = baseDatos;
    }

    private boolean verficarIntegridad(DetalleCompra obj, int operacion) {
        String[] idDetalleCompra = {obj.getIdDetalleCompra()};
        String[] idCompra = {obj.getIdCompra()};
        String[] idArticulo = {obj.getIdArticulo()};

        try (SQLiteDatabase db = baseDatos.getWritableDatabase();
             Cursor cursorDetalleCompra = db.query(Tablas.DETALLE_COMPRA, null,
                     EntradaDetalleCompra.ID_DETALLE_COMPRA + " = ?", idDetalleCompra, null, null,
                     null);
             Cursor cursorCompra = db.query(Tablas.COMPRA, null, EntradaDetalleCompra.ID_COMPRA
                     + " = ?", idCompra, null, null, null);
             Cursor cursorArticulo = db.query(Tablas.ARTICULO, null,
                     EntradaDetalleCompra.ID_ARTICULO + " = ?", idArticulo, null, null, null)
        ) {
            boolean existeCompra = cursorCompra.getCount() > 0;
            boolean existeArticulo = cursorArticulo.getCount() > 0;
            boolean existeDetalleCompra = cursorDetalleCompra.getCount() > 0;

            switch (operacion) {
                case 1:
                    return !existeCompra || !existeArticulo;
                case 2:
                    return !existeDetalleCompra || !existeCompra || !existeArticulo;
                case 3:
                    return !existeDetalleCompra;
                default:
                    return true;
            }
        }
    }

    private ContentValues getContentValues(DetalleCompra detalleCompraObj) {
        ContentValues valores = new ContentValues();
        valores.put(EntradaDetalleCompra.ID_DETALLE_COMPRA, detalleCompraObj.getIdDetalleCompra());
        valores.put(EntradaDetalleCompra.CANTIDAD_PRODUCTO_COMPRA, detalleCompraObj.getCantidadArticulo());
        valores.put(EntradaDetalleCompra.SUBTOTAL_COMPRA, detalleCompraObj.getSubtotalCompra());
        valores.put(EntradaDetalleCompra.ID_COMPRA, detalleCompraObj.getIdCompra());
        valores.put(EntradaDetalleCompra.ID_ARTICULO, detalleCompraObj.getIdArticulo());
        return valores;
    }

    @Override
    public String insertar(DetalleCompra obj) throws DAOException {
        // Generar Pk
        String idDetalleCompra = EntradaDetalleCompra.generarIdDetalleCompra();
        obj.setIdDetalleCompra(idDetalleCompra);

        // Verificar que al insertar un detalle de compra, la compra y el artículo existan
        if (verficarIntegridad(obj, 1)) {
            throw new DAOException("Error al insertar detalle de compra: La compra o el " +
                    "artículo no existen");
        }

        ContentValues valores = getContentValues(obj);

        try (SQLiteDatabase db = baseDatos.getWritableDatabase()) {
            db.insertOrThrow(Tablas.DETALLE_COMPRA, null, valores);
            return idDetalleCompra;
        } catch (SQLException e) {
            throw new DAOException("Error al insertar un nuevo detalle de compra");
        }
    }

    @Override
    public void modificar(DetalleCompra obj) throws DAOException {
        if (verficarIntegridad(obj, 2)) {
            throw new DAOException("Error al modificar detalle de compra: La compra, el " +
                    "artículo o el detalle de compra no existen");
        }

        ContentValues valores = getContentValues(obj);
        String id = obj.getIdDetalleCompra();
        String[] whereArgs = {id};

        try (SQLiteDatabase db = baseDatos.getWritableDatabase()) {
            db.update(Tablas.DETALLE_COMPRA, valores, EntradaDetalleCompra.ID_DETALLE_COMPRA + " = ?",
                    whereArgs);
        } catch (SQLException e) {
            throw new DAOException("Error al modificar detalle de compra");
        }
    }

    @Override
    public void eliminar(DetalleCompra obj) throws DAOException {

    }

    @Override
    public List<DetalleCompra> obtenerTodos() throws DAOException {
        return null;
    }

    @Override
    public DetalleCompra obtener(String id) throws DAOException {
        return null;
    }

    @Override
    public DetalleCompra obtenerPorIdCompra(String idCompra) throws DAOException {
        throw new DAOException("Método no implementado");
    }
}
