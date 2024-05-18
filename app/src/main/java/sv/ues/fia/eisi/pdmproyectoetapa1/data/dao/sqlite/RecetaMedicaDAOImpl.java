package sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.ArrayList;

import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.RecetaMedicaDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.RecetaMedica;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite.BaseDatosFarmacia.Tablas;

public class RecetaMedicaDAOImpl implements RecetaMedicaDAO {
    private final BaseDatosFarmacia baseDatos;

    public RecetaMedicaDAOImpl(BaseDatosFarmacia baseDatos) {
        this.baseDatos = baseDatos;
    }

    private boolean verificarIntegridad(RecetaMedica receta, int operacion) {
        String[] idReceta = {receta.getIdReceta()};
        String[] idMedico = {receta.getIdMedico()};
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        Cursor cursorReceta = db.query(Tablas.RECETA_MEDICA,
                    null, FarmaciaContrato.EntradaRecetaMedica.ID_RECETA_MEDICA + "=?",
                            idReceta,null,null,null);
        Cursor cursorMedico = db.query(Tablas.MEDICO, null,
                FarmaciaContrato.EntradaRecetaMedica.ID_MEDICO + "=?", idMedico, null, null, null);

        boolean existeReceta = cursorReceta.moveToFirst();
        boolean existeMedico = cursorMedico.moveToFirst();

        cursorReceta.close();
        cursorMedico.close();

        switch (operacion){
            case 1:
                //verificar que al insertar una receta exista el medico
                return !existeMedico;
            case 2:
                //verificar que al modificar una receta exista la receta y el medico
                return !existeReceta || !existeMedico;
            case 3:
                //verificar que al eliminar una receta exista la receta
                return !existeReceta;
            default:
                return true;
        }
    }
    @NonNull
    private static ContentValues getContentValues(RecetaMedica obj){
        ContentValues valores = new ContentValues();
        valores.put(FarmaciaContrato.EntradaRecetaMedica.NUMERO_RECETA, obj.getNumeroReceta());
        valores.put(FarmaciaContrato.EntradaRecetaMedica.FECHA_RECETA_MEDICA, obj.getFechaReceta());
        valores.put(FarmaciaContrato.EntradaRecetaMedica.ID_MEDICO, obj.getIdMedico());
        return valores;
    }

    @NonNull
    private static ContentValues getContentValues(RecetaMedica obj, String idReceta){
        ContentValues valores = new ContentValues();
        valores.put(FarmaciaContrato.EntradaRecetaMedica.ID_RECETA_MEDICA, idReceta);
        valores.put(FarmaciaContrato.EntradaRecetaMedica.NUMERO_RECETA, obj.getNumeroReceta());
        valores.put(FarmaciaContrato.EntradaRecetaMedica.FECHA_RECETA_MEDICA, obj.getFechaReceta());
        valores.put(FarmaciaContrato.EntradaRecetaMedica.ID_MEDICO, obj.getIdMedico());
        return valores;
    }

    @Override
    public String insertar(RecetaMedica obj) throws DAOException {
        if(verificarIntegridad(obj, 1)){
            throw new DAOException("RecetaMedicaDAO: No se puede insertar una receta"+
                    "si no existe el medico");
        }
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        //Generar Pk
        String idReceta = obj.getIdReceta();
        ContentValues valores = getContentValues(obj, idReceta);

        //Insertar Receta Medica
        try{
            db.insertOrThrow(Tablas.RECETA_MEDICA, null, valores);
            return idReceta;
        }catch(SQLException e){
            throw new DAOException("RecetaMedicaDAO: Error al insertar una receta medica: "+e.getMessage());
        }
    }

    @Override
    public void modificar(RecetaMedica obj) throws DAOException {
        //Verificar que la receta y el medico existan
        if(verificarIntegridad(obj, 2)){
            throw new DAOException("RecetaMedicaDAO: La receta o el medico no existen.");
        }
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues valores = getContentValues(obj);
        String[] idReceta = {obj.getIdReceta()};

        //Modificar Receta Medica
        db.update(Tablas.RECETA_MEDICA, valores, FarmaciaContrato.EntradaRecetaMedica.ID_RECETA_MEDICA + "=?", idReceta);
    }

    @Override
    public void eliminar(RecetaMedica obj) throws DAOException {
        if(verificarIntegridad(obj, 3)){
            throw new DAOException("RecetaMedicaDAO: La receta no existe.");
        }
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String whereClause = String.format("%s=?", FarmaciaContrato.EntradaRecetaMedica.ID_RECETA_MEDICA);
        String[] idReceta = {obj.getIdReceta()};

        //Eliminar Receta Medica
        db.delete(Tablas.RECETA_MEDICA, whereClause, idReceta);
    }

    @Override
    public List<RecetaMedica> obtenerTodos() throws DAOException {
        List<RecetaMedica> recetas = new ArrayList<>();

        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", Tablas.RECETA_MEDICA);

        try(Cursor cursor = db.rawQuery(sql, null)) {
            if (cursor == null || !cursor.moveToFirst()) {
                return recetas;
            }
            do {
                RecetaMedica receta = new RecetaMedica();

                int idIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaRecetaMedica.ID_RECETA_MEDICA);
                int numeroRecetaIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaRecetaMedica.NUMERO_RECETA);
                int fechaRecetaIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaRecetaMedica.FECHA_RECETA_MEDICA);
                int idMedicoIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaRecetaMedica.ID_MEDICO);
                if (idIndex == -1 || numeroRecetaIndex == -1 || fechaRecetaIndex == -1 || idMedicoIndex == -1) {
                    throw new DAOException("RecetaMedicaDAO: Error al obtener los indices de las columnas.");
                }
                receta.setIdReceta(cursor.getString(idIndex));
                receta.setNumeroReceta(cursor.getString(numeroRecetaIndex));
                receta.setFechaReceta(cursor.getString(fechaRecetaIndex));
                receta.setIdMedico(cursor.getString(idMedicoIndex));

                recetas.add(receta);
            } while (cursor.moveToNext());
        }
        return recetas;
    }

    @Override
    public RecetaMedica obtener(String id) throws DAOException {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String selection = String.format("%s=?", FarmaciaContrato.EntradaRecetaMedica.ID_RECETA_MEDICA);
        String[] selectionArgs = {id};

        try(Cursor cursor = db.query(Tablas.RECETA_MEDICA, null, selection, selectionArgs,
                null, null, null)) {
            if (cursor == null || !cursor.moveToFirst()) {
                throw new DAOException("No se encontró la receta medica.");
            }

            RecetaMedica receta = new RecetaMedica();

            int idIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaRecetaMedica.ID_RECETA_MEDICA);
            int numeroRecetaIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaRecetaMedica.NUMERO_RECETA);
            int fechaRecetaIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaRecetaMedica.FECHA_RECETA_MEDICA);
            int idMedicoIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaRecetaMedica.ID_MEDICO);
            if (idIndex == -1 || numeroRecetaIndex == -1 || fechaRecetaIndex == -1 || idMedicoIndex == -1) {
                throw new DAOException("RecetaMedicaDAO: Error al obtener los indices de las columnas.");
            }
            receta.setIdReceta(cursor.getString(idIndex));
            receta.setNumeroReceta(cursor.getString(numeroRecetaIndex));
            receta.setFechaReceta(cursor.getString(fechaRecetaIndex));
            receta.setIdMedico(cursor.getString(idMedicoIndex));

            return receta;
        }
    }
}
