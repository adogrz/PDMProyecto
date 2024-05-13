package sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.MedicoDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Medico;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.BaseDatosFarmacia.Tablas;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.FarmaciaContrato.EntradaMedico;

public class MedicoDAOImpl implements MedicoDAO{
    private final BaseDatosFarmacia baseDatos;

    public MedicoDAOImpl(BaseDatosFarmacia baseDatos) {
        this.baseDatos = baseDatos;
    }

    private boolean verificarIntegridad(Medico medico, int operacion) {
        String[] idMedico = {medico.getIdMedico()};
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        Cursor cursorMedico = db.query(Tablas.MEDICO,
                null, EntradaMedico.ID_MEDICO + "=?",
                idMedico, null, null, null);

        boolean existeMedico = cursorMedico.moveToFirst();

        cursorMedico.close();

        switch (operacion){
            case 1:
                //verificar que al insertar un medico no exista el medico
                return existeMedico;
            case 2:
                //verificar que al modificar un medico exista el medico
                return !existeMedico;
            case 3:
                //verificar que al eliminar un medico exista el medico
                return !existeMedico;
            default:
                return false;
        }
    }

    @NonNull
    private static ContentValues getContentValues(Medico obj){
        ContentValues valores = new ContentValues();
        valores.put(EntradaMedico.NOMBRE_MEDICO, obj.getNombreMedico());
        valores.put(EntradaMedico.APELLIDO_MEDICO, obj.getApellidoMedico());
        valores.put(EntradaMedico.ESPECIALIDAD, obj.getEspecialidad());
        valores.put(EntradaMedico.JVPM, obj.getJvpm());
        return valores;
    }

    @NonNull
    private static ContentValues getContentValues(Medico obj, String idMedico){
        ContentValues valores = new ContentValues();
        valores.put(EntradaMedico.ID_MEDICO, idMedico);
        valores.put(EntradaMedico.NOMBRE_MEDICO, obj.getNombreMedico());
        valores.put(EntradaMedico.APELLIDO_MEDICO, obj.getApellidoMedico());
        valores.put(EntradaMedico.ESPECIALIDAD, obj.getEspecialidad());
        valores.put(EntradaMedico.JVPM, obj.getJvpm());
        return valores;
    }

    @Override
    public String insertar(Medico obj) throws DAOException {
        //verificar que el medico no exista
        if (verificarIntegridad(obj, 1)){
            throw new DAOException("El medico ya existe");
        }
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        //generar pk
        String idMedico = obj.getIdMedico();

        ContentValues valores = getContentValues(obj, idMedico);

        //insertar medico
        try {
            db.insertOrThrow(Tablas.MEDICO, null, valores);
            return idMedico;
        } catch (SQLiteException e){
            throw new DAOException("MedicoDAO: Error al insertar un medico: " + e.getMessage());
        }
    }

    @Override
    public void modificar(Medico obj) throws DAOException {
        //verificar que el medico exista
        if (verificarIntegridad(obj, 2)){
            throw new DAOException("El medico no existe");
        }
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = getContentValues(obj);

        String[] idMedico = {obj.getIdMedico()};

        //modificar medico
        db.update(Tablas.MEDICO, valores, EntradaMedico.ID_MEDICO + "=?", idMedico);
    }

    @Override
    public void eliminar(Medico obj) throws DAOException {
        //verificar que el medico exista
        if (verificarIntegridad(obj, 3)){
            throw new DAOException("El medico no existe");
        }
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String whereClause = String.format("%s=?", EntradaMedico.ID_MEDICO);
        String[] idMedico = {obj.getIdMedico()};

        //eliminar medico
        db.delete(Tablas.MEDICO, EntradaMedico.ID_MEDICO + "=?", idMedico);
    }

    @Override
    public List<Medico> obtenerTodos() throws DAOException {
        List<Medico> medicos = new ArrayList<>();

        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", Tablas.MEDICO);

        try(Cursor cursor = db.rawQuery(sql, null)){
            if(cursor == null || !cursor.moveToFirst()){
                return medicos;
            }
            do{
                Medico medico = new Medico();

                int idIndex = cursor.getColumnIndex(EntradaMedico.ID_MEDICO);
                int nombreMedicoIndex = cursor.getColumnIndex(EntradaMedico.NOMBRE_MEDICO);
                int apellidoMedicoIndex = cursor.getColumnIndex(EntradaMedico.APELLIDO_MEDICO);
                int especialidadIndex = cursor.getColumnIndex(EntradaMedico.ESPECIALIDAD);
                int jvpmIndex = cursor.getColumnIndex(EntradaMedico.JVPM);

                if(idIndex == -1 || nombreMedicoIndex == -1 || apellidoMedicoIndex == -1 ||
                        especialidadIndex == -1 || jvpmIndex == -1){
                    throw new DAOException("Error al obtener los indices de las columnas.");
                }
                medico.setIdMedico(cursor.getString(idIndex));
                medico.setNombreMedico(cursor.getString(nombreMedicoIndex));
                medico.setApellidoMedico(cursor.getString(apellidoMedicoIndex));
                medico.setEspecialidad(cursor.getString(especialidadIndex));
                medico.setJvpm(cursor.getString(jvpmIndex));

                medicos.add(medico);
            }while(cursor.moveToNext());
        }
        return medicos;
    }

    @Override
    public Medico obtener(String id) throws DAOException {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String selection = String.format("%s=?", EntradaMedico.ID_MEDICO);
        String[] selectionArgs = {id};

        try(Cursor cursor = db.query(Tablas.MEDICO, null, selection, selectionArgs,
                null, null, null)){
            if(cursor == null || !cursor.moveToFirst()){
                throw new DAOException("El medico no existe");
            }

            Medico medico = new Medico();

            int idIndex = cursor.getColumnIndex(EntradaMedico.ID_MEDICO);
            int nombreMedicoIndex = cursor.getColumnIndex(EntradaMedico.NOMBRE_MEDICO);
            int apellidoMedicoIndex = cursor.getColumnIndex(EntradaMedico.APELLIDO_MEDICO);
            int especialidadIndex = cursor.getColumnIndex(EntradaMedico.ESPECIALIDAD);
            int jvpmIndex = cursor.getColumnIndex(EntradaMedico.JVPM);

            if(idIndex == -1 || nombreMedicoIndex == -1 || apellidoMedicoIndex == -1 ||
                    especialidadIndex == -1 || jvpmIndex == -1){
                throw new DAOException("Error al obtener los indices de las columnas.");
            }

            medico.setIdMedico(cursor.getString(idIndex));
            medico.setNombreMedico(cursor.getString(nombreMedicoIndex));
            medico.setApellidoMedico(cursor.getString(apellidoMedicoIndex));
            medico.setEspecialidad(cursor.getString(especialidadIndex));
            medico.setJvpm(cursor.getString(jvpmIndex));

            return medico;
        }
    }
}
