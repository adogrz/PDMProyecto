package sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ProveedorDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Proveedor;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.BaseDatosFarmacia.Tablas;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.FarmaciaContrato.EntradaProveedor;

public class ProveedorDAOImpl implements ProveedorDAO {
    private final BaseDatosFarmacia baseDatos;

    public ProveedorDAOImpl(BaseDatosFarmacia baseDatos) {
        this.baseDatos = baseDatos;
    }

    @Override
    public String insertar(Proveedor obj) throws DAOException {
        throw new DAOException("No implementado");
    }

    @Override
    public void modificar(Proveedor obj) throws DAOException {
        throw new DAOException("No implementado");
    }

    @Override
    public void eliminar(Proveedor obj) throws DAOException {
        throw new DAOException("No implementado");
    }

    @Override
    public List<Proveedor> obtenerTodos() throws DAOException {
        List<Proveedor> listaProveedor = new ArrayList<>();

        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", Tablas.PROVEEDOR);

        try (Cursor cursor = db.rawQuery(sql, null)) {
            // Verifica que el cursor no esté vacío.
            if (cursor == null || !cursor.moveToFirst()) {
                return listaProveedor;
            }

            do {
                Proveedor proveedor = new Proveedor();

                int idIndex = cursor.getColumnIndex(EntradaProveedor.ID_PROVEEDOR);
                int nombreIndex = cursor.getColumnIndex(EntradaProveedor.NOMBRE_PROVEEDOR);
                int telefonoIndex = cursor.getColumnIndex(EntradaProveedor.TELEFONO_PROVEEDOR);

                // Verifica que las columnas existan.
                if (idIndex == -1 || nombreIndex == -1 || telefonoIndex == -1) {
                    throw new DAOException("Error al obtener los índices de las columnas.");
                }

                proveedor.setIdProveedor(cursor.getString(idIndex));
                proveedor.setNombre(cursor.getString(nombreIndex));
                proveedor.setTelefono(cursor.getString(telefonoIndex));

                listaProveedor.add(proveedor);
            } while (cursor.moveToNext());
        }

        return listaProveedor;
    }

    @Override
    public Proveedor obtener(String id) throws DAOException {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String selection = String.format("%s = ?", EntradaProveedor.ID_PROVEEDOR);
        String[] selectionArgs = {id};
        String[] columnas = {
                EntradaProveedor.ID_PROVEEDOR,
                EntradaProveedor.NOMBRE_PROVEEDOR,
                EntradaProveedor.TELEFONO_PROVEEDOR
        };

        try (Cursor cursor = db.query(Tablas.PROVEEDOR, columnas, selection, selectionArgs, null,
                null, null)) {
            if (cursor == null || !cursor.moveToFirst()) {
                throw new DAOException("No se encontró el proveedor");
            }

            Proveedor proveedor = new Proveedor();

            int idIndex = cursor.getColumnIndex(EntradaProveedor.ID_PROVEEDOR);
            int nombreIndex = cursor.getColumnIndex(EntradaProveedor.NOMBRE_PROVEEDOR);
            int telefonoIndex = cursor.getColumnIndex(EntradaProveedor.TELEFONO_PROVEEDOR);

            if (idIndex == -1 || nombreIndex == -1 || telefonoIndex == -1) {
                throw new DAOException("Error al obtener los índices de las columnas.");
            }

            proveedor.setIdProveedor(cursor.getString(idIndex));
            proveedor.setNombre(cursor.getString(nombreIndex));
            proveedor.setTelefono(cursor.getString(telefonoIndex));

            return proveedor;
        }
    }
}