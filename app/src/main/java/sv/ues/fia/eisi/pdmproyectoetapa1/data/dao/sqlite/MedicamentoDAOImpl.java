package sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite;


import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.ContentValues;
import android.database.SQLException;

import androidx.annotation.NonNull;


import java.util.ArrayList;
import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.MedicamentoDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Medicamento;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite.BaseDatosFarmacia.Tablas;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite.FarmaciaContrato.EntradaMedicamento;


public class MedicamentoDAOImpl implements MedicamentoDAO {
    private final BaseDatosFarmacia baseDatos;

    public MedicamentoDAOImpl(BaseDatosFarmacia baseDatos) {
        this.baseDatos = baseDatos;

    }

    //Verificar integridad de los datos
    private boolean verificarIntegridad(Medicamento medicamento, int operacion) {
        String[] idMedicamento = {medicamento.getIdMedicamento()};
        String[] idArticulo = {medicamento.getIdArticulo()};
        String[] idFormaFarmaceutica = {medicamento.getIdFormaFarmaceutica()};
        String[] idViaAdministracion = {medicamento.getIdViaAdministracion()};
        String[] idLaboratorio = {medicamento.getIdLaboratorio()};

        SQLiteDatabase db = baseDatos.getReadableDatabase();

        Cursor cursorMedicamento = db.query(Tablas.MEDICAMENTO, null,
                EntradaMedicamento.ID_MEDICAMENTO + "=?", idMedicamento, null, null, null);
        Cursor cursorArticulo = db.query(Tablas.ARTICULO, null,
                EntradaMedicamento.ID_ARTICULO + "=?", idArticulo, null, null, null);
        Cursor cursorFormaFarmaceutica = db.query(Tablas.FORMA_FARMACEUTICA, null,
                EntradaMedicamento.ID_FORMA_FARMACEUTICA + "=?", idFormaFarmaceutica, null, null, null);
        Cursor cursorViaAdministracion = db.query(Tablas.VIA_ADMINISTRACION, null,
                EntradaMedicamento.ID_VIA_ADMINISTRACION + "=?", idViaAdministracion, null, null, null);
        Cursor cursorLaboratorio = db.query(Tablas.LABORATORIO, null,
                EntradaMedicamento.ID_LABORATORIO + "=?", idLaboratorio, null, null, null);

        boolean existeMedicamento = cursorMedicamento.moveToFirst();
        boolean existeArticulo = cursorArticulo.moveToFirst();
        boolean existeFormaFarmaceutica = cursorFormaFarmaceutica.moveToFirst();
        boolean existeViaAdministracion = cursorViaAdministracion.moveToFirst();
        boolean existeLaboratorio = cursorLaboratorio.moveToFirst();

        cursorMedicamento.close();
        cursorArticulo.close();
        cursorFormaFarmaceutica.close();
        cursorViaAdministracion.close();
        cursorLaboratorio.close();

        //Verificar que se cumplan las operaciones
        switch (operacion) {
            case 1:
                //Verificar que al insertar un medicamento exista el articulo, la forma farmaceutica, la via de administracion y el laboratorio
                return !existeArticulo || !existeFormaFarmaceutica || !existeViaAdministracion || !existeLaboratorio;
            case 2:
                //Verificar que al modificar un medicamento exista el articulo, la forma farmaceutica, la via de administracion y el laboratorio
                return !existeMedicamento || !existeArticulo || !existeFormaFarmaceutica || !existeViaAdministracion || !existeLaboratorio;
            case 3:
                //Verificar que al eliminar un medicamento, el medicamento exista
                return !existeMedicamento;
            default:
                return true;
        }

    }

    @NonNull
    private static ContentValues getContentValues(Medicamento obj) {
        ContentValues values = new ContentValues();
        values.put(EntradaMedicamento.FECHA_EXPEDICION, obj.getFechaExpedicion());
        values.put(EntradaMedicamento.FECHA_EXPIRACION, obj.getFechaExpiracion());
        values.put(EntradaMedicamento.REQUIERE_RECETA_MEDICA, obj.getRequiereRecetaMedica());
        values.put(EntradaMedicamento.ID_ARTICULO, obj.getIdArticulo());
        values.put(EntradaMedicamento.ID_FORMA_FARMACEUTICA, obj.getIdFormaFarmaceutica());
        values.put(EntradaMedicamento.ID_VIA_ADMINISTRACION, obj.getIdViaAdministracion());
        values.put(EntradaMedicamento.ID_LABORATORIO, obj.getIdLaboratorio());
        return values;
    }

    @NonNull
    private static ContentValues getContentValues(Medicamento obj, String idMedicamento) {
        ContentValues values = new ContentValues();
        values.put(EntradaMedicamento.ID_MEDICAMENTO, idMedicamento);
        values.put(EntradaMedicamento.FECHA_EXPEDICION, obj.getFechaExpedicion());
        values.put(EntradaMedicamento.FECHA_EXPIRACION, obj.getFechaExpiracion());
        values.put(EntradaMedicamento.REQUIERE_RECETA_MEDICA, obj.getRequiereRecetaMedica());
        values.put(EntradaMedicamento.ID_ARTICULO, obj.getIdArticulo());
        values.put(EntradaMedicamento.ID_FORMA_FARMACEUTICA, obj.getIdFormaFarmaceutica());
        values.put(EntradaMedicamento.ID_VIA_ADMINISTRACION, obj.getIdViaAdministracion());
        values.put(EntradaMedicamento.ID_LABORATORIO, obj.getIdLaboratorio());
        return values;
    }

    @Override
    public String insertar(Medicamento obj) throws DAOException {

       //Verificar que al insertar medicamento,
        // el articulo, la forma farmaceutica, la via de administracion y el laboratorio existan
        if (verificarIntegridad(obj, 1)) {
            throw new DAOException("MedicamentoDAO: No se puede insertar un medicamento con un " +
                    "articulo, forma farmaceutica, via de administracion o laboratorio no existente");
        }

        SQLiteDatabase db = baseDatos.getWritableDatabase();

        //Generar Pk
        String idMedicamento = obj.getIdMedicamento();
        ContentValues values = getContentValues(obj, idMedicamento);

        //Insertar Medicamento
        try {
            db.insertOrThrow(Tablas.MEDICAMENTO, null, values);
            return idMedicamento;
        } catch (SQLException e) {
            throw new DAOException("MedicamentoDAO: Error al insertar medicamento " + e.getMessage());
        }
    }

    @Override
    public void modificar(Medicamento obj) throws DAOException {
        //Verificar que al modificar medicamento,
        // el articulo, la forma farmaceutica, la via de administracion y el laboratorio existan
        if (verificarIntegridad(obj, 2)) {
            throw new DAOException("MedicamentoDAO: No se puede modificar un medicamento con un " +
                    "articulo, forma farmaceutica, via de administracion o laboratorio no existente");
        }

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues values = getContentValues(obj);

        String[] idMedicamento = {obj.getIdMedicamento()};

        //Modificar articulo
        db.update(Tablas.MEDICAMENTO, values, EntradaMedicamento.ID_MEDICAMENTO + "=?", idMedicamento);
    }

    @Override
    public void eliminar(Medicamento obj) throws DAOException {
        //Verificar que al eliminar medicamento, el medicamento exista
        if (verificarIntegridad(obj, 3)) {
            throw new DAOException("MedicamentoDAO: No se puede eliminar un medicamento no existente");
        }

        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = String.format("%s=?", EntradaMedicamento.ID_MEDICAMENTO);
        String[] idMedicamento = {obj.getIdMedicamento()};

        //Eliminar medicamento
        db.delete(Tablas.MEDICAMENTO, whereClause, idMedicamento);
    }

    @Override
    public List<Medicamento> obtenerTodos() throws DAOException {

        List<Medicamento> listaMedicamento = new ArrayList<>();

        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", Tablas.MEDICAMENTO);

        try (Cursor cursor = db.rawQuery(sql, null)) {
            // Verifica que el cursor no esté vacío.
            if (cursor == null || !cursor.moveToFirst()) {
                return listaMedicamento;
            }

            do {
                Medicamento medicamento = new Medicamento();
                int idIndex = cursor.getColumnIndex(EntradaMedicamento.ID_MEDICAMENTO);
                int fechaExpedicionIndex = cursor.getColumnIndex(EntradaMedicamento.FECHA_EXPEDICION);
                int fechaExpiracionIndex = cursor.getColumnIndex(EntradaMedicamento.FECHA_EXPIRACION);
                int requiereRecetaMedicaIndex = cursor.getColumnIndex(EntradaMedicamento.REQUIERE_RECETA_MEDICA);
                int idArticuloIndex = cursor.getColumnIndex(EntradaMedicamento.ID_ARTICULO);
                int idFormaFarmaceuticaIndex = cursor.getColumnIndex(EntradaMedicamento.ID_FORMA_FARMACEUTICA);
                int idViaAdministracionIndex = cursor.getColumnIndex(EntradaMedicamento.ID_VIA_ADMINISTRACION);
                int idLaboratorioIndex = cursor.getColumnIndex(EntradaMedicamento.ID_LABORATORIO);


                // Verifica que las columnas existan.
                if (idIndex != -1 || fechaExpedicionIndex != -1 || fechaExpiracionIndex != -1
                        || requiereRecetaMedicaIndex != -1 || idArticuloIndex != -1 || idFormaFarmaceuticaIndex != -1
                        || idViaAdministracionIndex != -1 || idLaboratorioIndex != -1) {
                    medicamento.setIdMedicamento(cursor.getString(idIndex));
                    medicamento.setFechaExpedicion(cursor.getString(fechaExpedicionIndex));
                    medicamento.setFechaExpiracion(cursor.getString(fechaExpiracionIndex));
                    medicamento.setRequiereRecetaMedica(cursor.getString(requiereRecetaMedicaIndex));
                    medicamento.setIdArticulo(cursor.getString(idArticuloIndex));
                    medicamento.setIdFormaFarmaceutica(cursor.getString(idFormaFarmaceuticaIndex));
                    medicamento.setIdViaAdministracion(cursor.getString(idViaAdministracionIndex));
                    medicamento.setIdLaboratorio(cursor.getString(idLaboratorioIndex));


                    listaMedicamento.add(medicamento);
                }
            } while (cursor.moveToNext());
        }
        return listaMedicamento;
    }

    @Override
    public Medicamento obtener(String id) throws DAOException {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String selection = String.format("%s=?", EntradaMedicamento.ID_MEDICAMENTO);
        String[] selectionArgs = {id};

        try (Cursor cursor = db.query(Tablas.MEDICAMENTO, null, selection, selectionArgs, null, null, null)) {
            if (cursor == null || !cursor.moveToFirst()) {
                throw new DAOException("No se ha encontrado el medicamento");
            }
            Medicamento medicamento = new Medicamento();

            int idIndex = cursor.getColumnIndex(EntradaMedicamento.ID_MEDICAMENTO);
            int fechaExpedicionIndex = cursor.getColumnIndex(EntradaMedicamento.FECHA_EXPEDICION);
            int fechaExpiracionIndex = cursor.getColumnIndex(EntradaMedicamento.FECHA_EXPIRACION);
            int requiereRecetaMedicaIndex = cursor.getColumnIndex(EntradaMedicamento.REQUIERE_RECETA_MEDICA);
            int idArticuloIndex = cursor.getColumnIndex(EntradaMedicamento.ID_ARTICULO);
            int idFormaFarmaceuticaIndex = cursor.getColumnIndex(EntradaMedicamento.ID_FORMA_FARMACEUTICA);
            int idViaAdministracionIndex = cursor.getColumnIndex(EntradaMedicamento.ID_VIA_ADMINISTRACION);
            int idLaboratorioIndex = cursor.getColumnIndex(EntradaMedicamento.ID_LABORATORIO);

            if (idIndex == -1 || fechaExpedicionIndex == -1 || fechaExpiracionIndex == -1
                    || requiereRecetaMedicaIndex == -1 || idArticuloIndex == -1 || idFormaFarmaceuticaIndex == -1
                    || idViaAdministracionIndex == -1 || idLaboratorioIndex == -1) {
                throw new DAOException("Error al obtner los indices de las columnas.");
            }
            medicamento.setIdMedicamento(cursor.getString(idIndex));
            medicamento.setFechaExpedicion(cursor.getString(fechaExpedicionIndex));
            medicamento.setFechaExpiracion(cursor.getString(fechaExpiracionIndex));
            medicamento.setRequiereRecetaMedica(cursor.getString(requiereRecetaMedicaIndex));
            medicamento.setIdArticulo(cursor.getString(idArticuloIndex));
            medicamento.setIdFormaFarmaceutica(cursor.getString(idFormaFarmaceuticaIndex));
            medicamento.setIdViaAdministracion(cursor.getString(idViaAdministracionIndex));
            medicamento.setIdLaboratorio(cursor.getString(idLaboratorioIndex));

            return medicamento;
        }
    }

}
