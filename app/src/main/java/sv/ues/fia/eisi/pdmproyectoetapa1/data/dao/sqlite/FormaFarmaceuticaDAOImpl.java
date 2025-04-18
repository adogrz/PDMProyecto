package sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.FormaFarmaceuticaDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.FormaFarmaceutica;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite.BaseDatosFarmacia.Tablas;

public class FormaFarmaceuticaDAOImpl implements FormaFarmaceuticaDAO {
    private final BaseDatosFarmacia baseDatos;

    public FormaFarmaceuticaDAOImpl(BaseDatosFarmacia baseDatos) {
        this.baseDatos = baseDatos;
    }

    @Override
    public String insertar(FormaFarmaceutica obj) throws DAOException {
        throw new DAOException("No implementado");
    }

    @Override
    public void modificar(FormaFarmaceutica obj) throws DAOException {
        throw new DAOException("No implementado");
    }

    @Override
    public void eliminar(FormaFarmaceutica obj) throws DAOException {
        throw new DAOException("No implementado");
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

                int idIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaFormaFarmaceutica.ID_FORMA_FARMACEUTICA);
                int formaIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaFormaFarmaceutica.TIPO_FORMA_FARMACEUTICA);

                // Verifica que las columnas existan
                if (idIndex != -1 || formaIndex == -1) {
                    formaFarmaceutica.setIdFormaFarmaceutica(cursor.getString(idIndex));
                    formaFarmaceutica.setFormaFarmaceutica(cursor.getString(formaIndex));

                    listaFormaFarmaceutica.add(formaFarmaceutica);
                }
            } while (cursor.moveToNext());
        }

        return listaFormaFarmaceutica;
    }

    @Override
    public FormaFarmaceutica obtener(String id) throws DAOException {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String selection = String.format("%s = ?", FarmaciaContrato.EntradaFormaFarmaceutica.ID_FORMA_FARMACEUTICA);
        String[] selectionArgs = {id};


        try(Cursor cursor=db.query(Tablas.FORMA_FARMACEUTICA,null,selection,selectionArgs,null,
                null,null)) {

            if(cursor == null || !cursor.moveToFirst()) {
                throw new DAOException("No se encontró la forma farmacéutica");
            }
            FormaFarmaceutica formaFarmaceutica = new FormaFarmaceutica();

            int idIndex = cursor.getColumnIndex((FarmaciaContrato.EntradaFormaFarmaceutica.ID_FORMA_FARMACEUTICA));
            int formaIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaFormaFarmaceutica.TIPO_FORMA_FARMACEUTICA);

            if (idIndex == -1 || formaIndex == -1) {
                throw new DAOException("Error al obtener los índices de las columnas.");
            }
            formaFarmaceutica.setIdFormaFarmaceutica(cursor.getString(idIndex));
            formaFarmaceutica.setFormaFarmaceutica(cursor.getString(formaIndex));

            return formaFarmaceutica;
        }
    }


}
