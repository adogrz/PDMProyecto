package sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.MetodoPagoDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.MetodoPago;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite.FarmaciaContrato.EntradaMetodoPago;

public class MetodoPagoDAOImpl implements MetodoPagoDAO {

    private final BaseDatosFarmacia baseDatos;
    public MetodoPagoDAOImpl(BaseDatosFarmacia baseDatos){this.baseDatos = baseDatos;}

    @Override
    public String insertar(MetodoPago obj) throws DAOException {
        throw new UnsupportedOperationException("No implementado");
    }

    @Override
    public void modificar(MetodoPago obj) throws DAOException {
        throw new UnsupportedOperationException("No implementado");
    }

    @Override
    public void eliminar(MetodoPago obj) throws DAOException {
        throw new UnsupportedOperationException("No implementado");
    }

    @Override
    public List<MetodoPago> obtenerTodos() throws DAOException {
        List<MetodoPago> metodoPagos = new ArrayList<>();

        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", BaseDatosFarmacia.Tablas.METODO_PAGO);

        try (Cursor cursor = db.rawQuery(sql, null)) {
            if (cursor == null || !cursor.moveToFirst()) {
                return metodoPagos;
            }

            do {
                MetodoPago metodoPago = new MetodoPago();

                int idIndex = cursor.getColumnIndex(EntradaMetodoPago.ID_METODO_PAGO);
                int tipoMetodoIndex = cursor.getColumnIndex(EntradaMetodoPago.TIPO_METODO_PAGO);

                if (idIndex == -1 || tipoMetodoIndex == -1) {
                    throw new DAOException("Error al obtener los índices de las columnas.");
                }

                metodoPago.setIdMetodoPago(cursor.getString(idIndex));
                metodoPago.setTipoMetodoPago(cursor.getString(tipoMetodoIndex));

                metodoPagos.add(metodoPago);
            } while (cursor.moveToNext());
        }

        return metodoPagos;
    }

    @Override
    public MetodoPago obtener(String id) throws DAOException {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String selection = String.format("%s = ?", EntradaMetodoPago.ID_METODO_PAGO);
        String[] selectionArgs = {id};

        try (Cursor cursor = db.query(BaseDatosFarmacia.Tablas.METODO_PAGO, null, selection, selectionArgs, null, null,
                null)) {
            if (cursor == null || !cursor.moveToFirst()) {
                throw new DAOException("No se encontró el metodo de pago");
            }

            MetodoPago metodoPago = new MetodoPago();

            int idIndex = cursor.getColumnIndex(EntradaMetodoPago.ID_METODO_PAGO);
            int tipoMetodoIndex = cursor.getColumnIndex(EntradaMetodoPago.TIPO_METODO_PAGO);

            if (idIndex == -1 || tipoMetodoIndex == -1) {
                throw new DAOException("Error al obtener los índices de las columnas.");
            }

            metodoPago.setIdMetodoPago(cursor.getString(idIndex));
            metodoPago.setTipoMetodoPago(cursor.getString(tipoMetodoIndex));

            return metodoPago;
        }
    }
}
