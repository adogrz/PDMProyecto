package sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite;

import android.database.Cursor;
import java.util.List;
import java.util.ArrayList;


import android.database.sqlite.SQLiteDatabase;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ClienteDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Cliente;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.BaseDatosFarmacia.Tablas;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.FarmaciaContrato.EntradaCliente;

public class ClienteDAOImpl implements ClienteDAO {
    private final BaseDatosFarmacia baseDatos;

    public ClienteDAOImpl(BaseDatosFarmacia baseDatos) {
        this.baseDatos = baseDatos;
    }

    @Override
    public String insertar(Cliente obj) throws DAOException {
        throw new DAOException("No implementado");
    }

    @Override
    public void modificar(Cliente obj) throws DAOException {
        throw new DAOException("No implementado");
    }

    @Override
    public void eliminar(Cliente obj) throws DAOException {
        throw new DAOException("No implementado");
    }

    @Override
    public List<Cliente> obtenerTodos() throws DAOException {
       List<Cliente> listaCliente = new ArrayList<>();

            SQLiteDatabase db = baseDatos.getReadableDatabase();
            String sql = String.format("SELECT * FROM %s", Tablas.CLIENTE);

            try (Cursor cursor = db.rawQuery(sql, null)) {
                // Verifica que el cursor no esté vacío.
                if (cursor == null || !cursor.moveToFirst()) {
                    return listaCliente;
                }

                do {
                    Cliente cliente = new Cliente();

                    int idIndex = cursor.getColumnIndex(EntradaCliente.ID_CLIENTE);
                    int nombreIndex = cursor.getColumnIndex(EntradaCliente.NOMBRE_CLIENTE);
                    int apellidoIndex = cursor.getColumnIndex(EntradaCliente.APELLIDO_CLIENTE);

                    // Verifica que las columnas existan.
                    if (idIndex == -1 || nombreIndex == -1 || apellidoIndex == -1) {
                        throw new DAOException("Error al obtener los índices de las columnas.");
                    }

                    cliente.setIdCliente(cursor.getString(idIndex));
                    cliente.setNombreCliente(cursor.getString(nombreIndex));
                    cliente.setApellidoCliente(cursor.getString(apellidoIndex));

                    listaCliente.add(cliente);
                } while (cursor.moveToNext());
            }
            return listaCliente;
    }

    @Override
    public Cliente obtener(String id) throws DAOException {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String selection = String.format("%s=?", EntradaCliente.ID_CLIENTE);
        String[] selectionArgs = {id};
        String [] columnas = {
                EntradaCliente.ID_CLIENTE,
                EntradaCliente.NOMBRE_CLIENTE,
                EntradaCliente.APELLIDO_CLIENTE
        };

        try(Cursor cursor = db.query(Tablas.CLIENTE, columnas, selection, selectionArgs, null,
                null, null)) {
            if (cursor == null || !cursor.moveToFirst()) {
                throw new DAOException("No se encontró el cliente");
            }
            Cliente cliente = new Cliente();

            int idIndex = cursor.getColumnIndex(EntradaCliente.ID_CLIENTE);
            int nombreIndex = cursor.getColumnIndex(EntradaCliente.NOMBRE_CLIENTE);
            int apellidoIndex = cursor.getColumnIndex(EntradaCliente.APELLIDO_CLIENTE);

            if (idIndex == -1 || nombreIndex == -1 || apellidoIndex == -1) {
                throw new DAOException("Error al obtener los índices de las columnas.");
            }

            cliente.setIdCliente(cursor.getString(idIndex));
            cliente.setNombreCliente(cursor.getString(nombreIndex));
            cliente.setApellidoCliente(cursor.getString(apellidoIndex));

            return cliente;
        }
    }
}
