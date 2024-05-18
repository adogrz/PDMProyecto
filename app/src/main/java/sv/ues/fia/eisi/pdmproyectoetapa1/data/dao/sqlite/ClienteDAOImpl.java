package sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite;

import android.database.Cursor;
import java.util.List;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.NonNull;

import java.util.ArrayList;


import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.ClienteDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Cliente;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite.BaseDatosFarmacia.Tablas;

public class ClienteDAOImpl implements ClienteDAO {
    private final BaseDatosFarmacia baseDatos;

    public ClienteDAOImpl(BaseDatosFarmacia baseDatos) {
        this.baseDatos = baseDatos;
    }

    private static ContentValues getContentValues(Cliente obj) {
        ContentValues valores = new ContentValues();
        valores.put(FarmaciaContrato.EntradaCliente.NOMBRE_CLIENTE, obj.getNombreCliente());
        valores.put(FarmaciaContrato.EntradaCliente.APELLIDO_CLIENTE, obj.getApellidoCliente());
        return valores;
    }

    @NonNull
    private static ContentValues getContentValues(Cliente obj, String idCliente) {
        ContentValues valores = new ContentValues();
        valores.put(FarmaciaContrato.EntradaCliente.ID_CLIENTE, idCliente);
        valores.put(FarmaciaContrato.EntradaCliente.NOMBRE_CLIENTE, obj.getNombreCliente());
        valores.put(FarmaciaContrato.EntradaCliente.APELLIDO_CLIENTE, obj.getApellidoCliente());
        return valores;
    }

    @Override
    public String insertar(Cliente obj) throws DAOException {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String idCliente = obj.getIdCliente();

        ContentValues valores = getContentValues(obj, idCliente);

        try {
            db.insertOrThrow(Tablas.CLIENTE, null, valores);
            return idCliente;
        } catch (SQLException e) {
            throw new DAOException("ClienteDAO: Error al insertar un cliente: " + e.getMessage());
        }
    }

    @Override
    public void modificar(Cliente obj) throws DAOException {
        // Verificar si existe el cliente
        if (obtener(obj.getIdCliente()) == null) {
            throw new DAOException("El cliente no existe");
        }

        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = getContentValues(obj);

        String[] idCliente = {obj.getIdCliente()};

        // Modificar el cliente
        db.update(Tablas.CLIENTE, valores, FarmaciaContrato.EntradaCliente.ID_CLIENTE + " = ?", idCliente);
    }

    @Override
    public void eliminar(Cliente obj) throws DAOException {
        // Verificar si existe el cliente
        if (obtener(obj.getIdCliente()) == null) {
            throw new DAOException("El cliente no existe");
        }

        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = String.format("%s = ?", FarmaciaContrato.EntradaCliente.ID_CLIENTE);
        String[] idCliente = {obj.getIdCliente()};

        // Eliminar articulo
        db.delete(Tablas.CLIENTE, whereClause, idCliente);
    }

    @Override
    public List<Cliente> obtenerTodos() throws DAOException {
        List<Cliente> clientes = new ArrayList<>();

        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", Tablas.CLIENTE);

        try (Cursor cursor = db.rawQuery(sql, null)) {
            if (cursor == null || !cursor.moveToFirst()) {
                return clientes;
            }

            do {
                Cliente cliente = new Cliente();

                int idIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaCliente.ID_CLIENTE);
                int nombreClienteIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaCliente.NOMBRE_CLIENTE);
                int apellidoClienteIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaCliente.APELLIDO_CLIENTE);

                if (idIndex == -1 || nombreClienteIndex == -1 || apellidoClienteIndex == -1) {
                    throw new DAOException("Error al obtener los índices de las columnas.");
                }

                cliente.setIdCliente(cursor.getString(idIndex));
                cliente.setNombreCliente(cursor.getString(nombreClienteIndex));
                cliente.setApellidoCliente(cursor.getString(apellidoClienteIndex));

                clientes.add(cliente);
            } while (cursor.moveToNext());
        }

        return clientes;
    }

    @Override
    public Cliente obtener(String id) throws DAOException {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String selection = String.format("%s = ?", FarmaciaContrato.EntradaCliente.ID_CLIENTE);
        String[] selectionArgs = {id};

        try (Cursor cursor = db.query(Tablas.CLIENTE, null, selection, selectionArgs, null, null,
                null)) {
            if (cursor == null || !cursor.moveToFirst()) {
                throw new DAOException("No se encontró el cliente");
            }

            Cliente cliente = new Cliente();

            int idIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaCliente.ID_CLIENTE);
            int nombreClienteIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaCliente.NOMBRE_CLIENTE);
            int apellidoClienteIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaCliente.APELLIDO_CLIENTE);

            if (idIndex == -1 || nombreClienteIndex == -1 || apellidoClienteIndex == -1) {
                throw new DAOException("Error al obtener los índices de las columnas.");
            }

            cliente.setIdCliente(cursor.getString(idIndex));
            cliente.setNombreCliente(cursor.getString(nombreClienteIndex));
            cliente.setApellidoCliente(cursor.getString(apellidoClienteIndex));

            return cliente;
        }
    }
}
