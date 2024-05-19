package sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.CompraDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Compra;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite.BaseDatosFarmacia.Tablas;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite.FarmaciaContrato.EntradaCompra;

public class CompraDAOImpl implements CompraDAO {
    private final BaseDatosFarmacia baseDatos;

    public CompraDAOImpl(BaseDatosFarmacia baseDatos) {
        this.baseDatos = baseDatos;
    }

    private boolean verificarIntegridad(Compra compraObj, int tipoVerificacion) {
        String[] idProveedor = {compraObj.getIdProveedor()};
        String[] idCompra = {compraObj.getIdCompra()};
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        Cursor cursorProveedor = db.query(Tablas.PROVEEDOR, null, EntradaCompra.ID_PROVEEDOR + " = ?",
                idProveedor, null, null, null);
        Cursor cursorCompra = db.query(Tablas.COMPRA, null, EntradaCompra.ID_COMPRA + " = ?",
                idCompra, null, null, null);

        boolean existeProveedor = cursorProveedor.getCount() > 0;
        boolean existeCompra = cursorCompra.getCount() > 0;

        cursorProveedor.close();
        cursorCompra.close();
        db.close();

        switch (tipoVerificacion) {
            case 1:
                return !existeProveedor;
            case 2:
                return !existeCompra || !existeProveedor;
            case 3:
                return !existeCompra;
            default:
                return true;
        }
    }

    private ContentValues getContentValues(Compra compraObj) {
        ContentValues valores = new ContentValues();
        valores.put(EntradaCompra.ID_COMPRA, compraObj.getIdCompra());
        valores.put(EntradaCompra.ID_PROVEEDOR, compraObj.getIdProveedor());
        valores.put(EntradaCompra.FECHA_COMPRA, compraObj.getFechaCompra());
        valores.put(EntradaCompra.MONTO_TOTAL_COMPRA, compraObj.getMontoTotal());
        return valores;
    }

    @Override
    public String insertar(Compra obj) throws DAOException {
        // Verificar que al insertar una nueva compra, el proveedor exista
        if (verificarIntegridad(obj, 1)) {
            throw new DAOException("Error al insertar una nueva compra. El proveedor no existe.");
        }

        String idCompra = obj.getIdCompra();
        ContentValues valores = getContentValues(obj);

        try (SQLiteDatabase db = baseDatos.getWritableDatabase()) {
            db.insertOrThrow(Tablas.COMPRA, null, valores);
            return idCompra;
        } catch (SQLException e) {
            throw new DAOException("Error al insertar una nueva compra.");
        }
    }

    @Override
    public void modificar(Compra obj) throws DAOException {
        // Verificar que al modificar una compra, la compra y el proveedor existan
        if (verificarIntegridad(obj, 2)) {
            throw new DAOException("Error al modificar la compra. La compra o el proveedor no existen.");
        }

        ContentValues valores = getContentValues(obj);
        String[] whereArgs = {obj.getIdCompra()};

        try (SQLiteDatabase db = baseDatos.getWritableDatabase()) {
            db.update(Tablas.COMPRA, valores, EntradaCompra.ID_COMPRA + " = ?", whereArgs);
        }
    }

    @Override
    public void eliminar(Compra obj) throws DAOException {
        // Verificar que al eliminar una compra, la compra exista
        if (verificarIntegridad(obj, 3)) {
            throw new DAOException("Error al eliminar la compra. La compra no existe.");
        }

        String[] whereArgs = {obj.getIdCompra()};

        try (SQLiteDatabase db = baseDatos.getWritableDatabase()) {
            db.delete(Tablas.COMPRA, EntradaCompra.ID_COMPRA + " = ?", whereArgs);
        }
    }

    @Override
    public List<Compra> obtenerTodos() throws DAOException {
        return null;
    }

    @Override
    public Compra obtener(String id) throws DAOException {
        return null;
    }
}
