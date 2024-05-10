package sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.FormaFarmaceuticaDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.FormaFarmaceutica;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.BaseDatosFarmacia.Tablas;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.FarmaciaContrato.EntradaFormaFarmaceutica;

public class FormaFarmaceuticaDAOImpl implements FormaFarmaceuticaDAO {
    private final BaseDatosFarmacia baseDatos;

    public FormaFarmaceuticaDAOImpl(BaseDatosFarmacia baseDatos) {
        this.baseDatos = baseDatos;
    }

    @Override
    public String insertar(FormaFarmaceutica obj) throws DAOException {
        throw new UnsupportedOperationException("No implementado");
    }

    @Override
    public void modificar(FormaFarmaceutica obj) throws DAOException {
        throw new UnsupportedOperationException("No implementado");
    }

    @Override
    public void eliminar(FormaFarmaceutica obj) throws DAOException {
        throw new UnsupportedOperationException("No implementado");
    }

    @Override
    public List<FormaFarmaceutica> obtenerTodos() throws DAOException {
        List<FormaFarmaceutica> listaFormaFarmaceutica = new ArrayList<>();

        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", Tablas.FORMA_FARMACEUTICA);

        try (Cursor cursor = db.rawQuery(sql, null)) {
            // Verifica que el cursor no esté vacío.
            if (cursor == null || !cursor.moveToFirst()) {
                return listaFormaFarmaceutica;
            }

            // Itera sobre el cursor para obtener los datos.
            do {
                FormaFarmaceutica formaFarmaceutica = new FormaFarmaceutica();

                int idIndex = cursor.getColumnIndex(EntradaFormaFarmaceutica.ID_FORMA_FARMACEUTICA);
                int nombreIndex = cursor.getColumnIndex(EntradaFormaFarmaceutica.TIPO_FORMA_FARMACEUTICA);

                // Verifica que las columnas existan
                if (idIndex != -1 || nombreIndex == -1) {
                    formaFarmaceutica.setIdFormaFarmaceutica(cursor.getString(idIndex));
                    formaFarmaceutica.setFormaFarmaceutica(cursor.getString(nombreIndex));

                    listaFormaFarmaceutica.add(formaFarmaceutica);
                }
            } while (cursor.moveToNext());
        }

        return listaFormaFarmaceutica;
    }

    @Override
    public FormaFarmaceutica obtener(String id) throws DAOException {
        throw new UnsupportedOperationException("No implementado");
    }


}
