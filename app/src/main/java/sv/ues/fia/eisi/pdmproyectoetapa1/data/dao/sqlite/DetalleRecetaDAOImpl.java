package sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.ArrayList;

import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.DetalleRecetaDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.DetalleReceta;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite.BaseDatosFarmacia.Tablas;

public class DetalleRecetaDAOImpl implements DetalleRecetaDAO {
    private final BaseDatosFarmacia baseDatos;

    public DetalleRecetaDAOImpl(BaseDatosFarmacia baseDatos) {
        this.baseDatos = baseDatos;
    }

    private boolean verificarIntegridad(DetalleReceta detalleReceta, int operacion){
        String[] idDetalleReceta = {detalleReceta.getIdDetalleReceta()};
        String[] idRecetaMedica = {detalleReceta.getIdRecetaMedica()};
        String[] idMedicamento = {detalleReceta.getIdMedicamento()};
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        Cursor cursorDetalleReceta = db.query(Tablas.DETALLE_RECETA, null,
                FarmaciaContrato.EntradaDetalleReceta.ID_DETALLE_RECETA + " = ?",
                idDetalleReceta, null, null, null);
        Cursor cursorRecetaMedica = db.query(Tablas.RECETA_MEDICA, null,
                FarmaciaContrato.EntradaDetalleReceta.ID_RECETA_MEDICA + " = ?", idRecetaMedica,
                null, null, null);
        Cursor cursorMedicamento = db.query(Tablas.MEDICAMENTO, null,
                FarmaciaContrato.EntradaDetalleReceta.ID_MEDICAMENTO + " = ?", idMedicamento,
                null, null, null);

        boolean existeDetalleReceta = cursorDetalleReceta.moveToFirst();
        boolean existeRecetaMedica = cursorRecetaMedica.moveToFirst();
        boolean existeMedicamento = cursorMedicamento.moveToFirst();

        cursorDetalleReceta.close();
        cursorRecetaMedica.close();
        cursorMedicamento.close();

        switch (operacion){
            case 1:
                //verificar que al insertar un detalle de receta exista la receta medica y el medicamento
                return !existeRecetaMedica || !existeMedicamento;
            case 2:
                //verificar que al modificar un detalle de receta exista el detalle de receta, la receta medica y el medicamento
                return !existeDetalleReceta || !existeRecetaMedica || !existeMedicamento;
            case 3:
                //verificar que al eliminar un detalle de receta exista el detalle de receta
                return !existeDetalleReceta;
            default:
                return false;
        }
    }

    @NonNull
    private static ContentValues getContentValues(DetalleReceta obj){
        ContentValues valores = new ContentValues();
        valores.put(FarmaciaContrato.EntradaDetalleReceta.PERIODICIDAD, obj.getPeriodicidad());
        valores.put(FarmaciaContrato.EntradaDetalleReceta.DOSIS, obj.getDosis());
        valores.put(FarmaciaContrato.EntradaDetalleReceta.FECHA_INICIO_TRATAMIENTO, obj.getFechaInicioTratamiento());
        valores.put(FarmaciaContrato.EntradaDetalleReceta.FECHA_FIN_TRATAMIENTO, obj.getFechaFinTratamiento());
        valores.put(FarmaciaContrato.EntradaDetalleReceta.ID_RECETA_MEDICA, obj.getIdRecetaMedica());
        valores.put(FarmaciaContrato.EntradaDetalleReceta.ID_MEDICAMENTO, obj.getIdMedicamento());
        return valores;
    }

    @NonNull
    private static ContentValues getContentValues(DetalleReceta obj, String idDetalleReceta){
        ContentValues valores = new ContentValues();
        valores.put(FarmaciaContrato.EntradaDetalleReceta.ID_DETALLE_RECETA, idDetalleReceta);
        valores.put(FarmaciaContrato.EntradaDetalleReceta.PERIODICIDAD, obj.getPeriodicidad());
        valores.put(FarmaciaContrato.EntradaDetalleReceta.DOSIS, obj.getDosis());
        valores.put(FarmaciaContrato.EntradaDetalleReceta.FECHA_INICIO_TRATAMIENTO, obj.getFechaInicioTratamiento());
        valores.put(FarmaciaContrato.EntradaDetalleReceta.FECHA_FIN_TRATAMIENTO, obj.getFechaFinTratamiento());
        valores.put(FarmaciaContrato.EntradaDetalleReceta.ID_RECETA_MEDICA, obj.getIdRecetaMedica());
        valores.put(FarmaciaContrato.EntradaDetalleReceta.ID_MEDICAMENTO, obj.getIdMedicamento());
        return valores;
    }

    @Override
    public String insertar(DetalleReceta obj) throws DAOException {
        //verificar que la receta medica y el medicamento existan
        if (verificarIntegridad(obj, 1)){
            throw new DAOException("DetalleRecetaDAO: No se puede insertar un detalle de receta: " +
                    "la receta medica o el medicamento no existen");
        }
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        //generar pk
        String idDetalleReceta = obj.getIdDetalleReceta();

        ContentValues valores = getContentValues(obj, idDetalleReceta);

        //Insertar Detalle Receta
        try {
            db.insertOrThrow(Tablas.DETALLE_RECETA, null, valores);
            return idDetalleReceta;
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void modificar(DetalleReceta obj) throws DAOException {
        //verificar que el detalle de receta, la receta medica y el medicamento existan
        if (verificarIntegridad(obj, 2)){
            throw new DAOException("DetalleRecetaDAO: El detalle de receta, la receta medica o el medicamento no existen");
        }
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues valores = getContentValues(obj);
        String[] idDetalleReceta = {obj.getIdDetalleReceta()};

        //Modificar Detalle Receta
        db.update(Tablas.DETALLE_RECETA, valores, FarmaciaContrato.EntradaDetalleReceta.ID_DETALLE_RECETA + "=?", idDetalleReceta);
    }

    @Override
    public void eliminar(DetalleReceta obj) throws DAOException {
        if(verificarIntegridad(obj, 3)){
            throw new DAOException("DetalleRecetaDAO: El detalle de receta no existe");
        }

        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = String.format("%s=?", FarmaciaContrato.EntradaDetalleReceta.ID_DETALLE_RECETA);
        String[] idDetalleReceta = {obj.getIdDetalleReceta()};

        //Eliminar Detalle Receta
        db.delete(Tablas.DETALLE_RECETA, FarmaciaContrato.EntradaDetalleReceta.ID_DETALLE_RECETA + "=?", idDetalleReceta);
    }

    @Override
    public List<DetalleReceta> obtenerTodos() throws DAOException {
        List<DetalleReceta> detallesReceta = new ArrayList<>();

        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", Tablas.DETALLE_RECETA);

        try(Cursor cursor = db.rawQuery(sql, null)){
            if(cursor == null || !cursor.moveToFirst()){
                return detallesReceta;
            }

            do{
                DetalleReceta detalleReceta = new DetalleReceta();

                int idIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaDetalleReceta.ID_DETALLE_RECETA);
                int periodicidadIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaDetalleReceta.PERIODICIDAD);
                int dosisIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaDetalleReceta.DOSIS);
                int fechaInicioTratamientoIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaDetalleReceta.FECHA_INICIO_TRATAMIENTO);
                int fechaFinTratamientoIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaDetalleReceta.FECHA_FIN_TRATAMIENTO);
                int idRecetaMedicaIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaDetalleReceta.ID_RECETA_MEDICA);
                int idMedicamentoIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaDetalleReceta.ID_MEDICAMENTO);

                if(idIndex == -1 || periodicidadIndex == -1 || dosisIndex == -1 || fechaInicioTratamientoIndex == -1 ||
                        fechaFinTratamientoIndex == -1 || idRecetaMedicaIndex == -1 || idMedicamentoIndex == -1){
                    throw new DAOException("DetalleRecetaDAO: Error al obtener los indices de las columnas");
                }

                detalleReceta.setIdDetalleReceta(cursor.getString(idIndex));
                detalleReceta.setPeriodicidad(cursor.getString(periodicidadIndex));
                detalleReceta.setDosis(cursor.getString(dosisIndex));
                detalleReceta.setFechaInicioTratamiento(cursor.getString(fechaInicioTratamientoIndex));
                detalleReceta.setFechaFinTratamiento(cursor.getString(fechaFinTratamientoIndex));
                detalleReceta.setIdRecetaMedica(cursor.getString(idRecetaMedicaIndex));
                detalleReceta.setIdMedicamento(cursor.getString(idMedicamentoIndex));

                detallesReceta.add(detalleReceta);
            }while(cursor.moveToNext());
        }
        return detallesReceta;
    }

    @Override
    public DetalleReceta obtener(String id) throws DAOException {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String selection = String.format("%s=?", FarmaciaContrato.EntradaDetalleReceta.ID_DETALLE_RECETA);
        String[] selectionArgs = {id};

        try(Cursor cursor = db.query(Tablas.DETALLE_RECETA, null, selection, selectionArgs,
                null, null, null)){
            if(cursor == null || !cursor.moveToFirst()){
                throw new DAOException("DetalleRecetaDAO: No se encontró el detalle de receta");
            }

            DetalleReceta detalleReceta = new DetalleReceta();

            int idIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaDetalleReceta.ID_DETALLE_RECETA);
            int periodicidadIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaDetalleReceta.PERIODICIDAD);
            int dosisIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaDetalleReceta.DOSIS);
            int fechaInicioTratamientoIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaDetalleReceta.FECHA_INICIO_TRATAMIENTO);
            int fechaFinTratamientoIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaDetalleReceta.FECHA_FIN_TRATAMIENTO);
            int idRecetaMedicaIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaDetalleReceta.ID_RECETA_MEDICA);
            int idMedicamentoIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaDetalleReceta.ID_MEDICAMENTO);

            if(idIndex == -1 || periodicidadIndex == -1 || dosisIndex == -1 || fechaInicioTratamientoIndex == -1 ||
                    fechaFinTratamientoIndex == -1 || idRecetaMedicaIndex == -1 || idMedicamentoIndex == -1){
                throw new DAOException("DetalleRecetaDAO: Error al obtener los indices de las columnas");
            }

            detalleReceta.setIdDetalleReceta(cursor.getString(idIndex));
            detalleReceta.setPeriodicidad(cursor.getString(periodicidadIndex));
            detalleReceta.setDosis(cursor.getString(dosisIndex));
            detalleReceta.setFechaInicioTratamiento(cursor.getString(fechaInicioTratamientoIndex));
            detalleReceta.setFechaFinTratamiento(cursor.getString(fechaFinTratamientoIndex));
            detalleReceta.setIdRecetaMedica(cursor.getString(idRecetaMedicaIndex));
            detalleReceta.setIdMedicamento(cursor.getString(idMedicamentoIndex));

            return detalleReceta;

        }
    }

    @Override
    public DetalleReceta obtenerDetallePorIdReceta(String idRecetaMedica) throws DAOException {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String selection = String.format("%s=?", FarmaciaContrato.EntradaDetalleReceta.ID_RECETA_MEDICA);
        String[] selectionArgs = {idRecetaMedica};

        try(Cursor cursor = db.query(Tablas.DETALLE_RECETA, null, selection, selectionArgs,
                null, null, null)){
            if(cursor == null || !cursor.moveToFirst()){
                throw new DAOException("DetalleRecetaDAO: No se encontró el detalle de receta");
            }

            DetalleReceta detalleReceta = new DetalleReceta();

            int idIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaDetalleReceta.ID_DETALLE_RECETA);
            int periodicidadIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaDetalleReceta.PERIODICIDAD);
            int dosisIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaDetalleReceta.DOSIS);
            int fechaInicioTratamientoIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaDetalleReceta.FECHA_INICIO_TRATAMIENTO);
            int fechaFinTratamientoIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaDetalleReceta.FECHA_FIN_TRATAMIENTO);
            int idRecetaMedicaIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaDetalleReceta.ID_RECETA_MEDICA);
            int idMedicamentoIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaDetalleReceta.ID_MEDICAMENTO);

            if(idIndex == -1 || periodicidadIndex == -1 || dosisIndex == -1 || fechaInicioTratamientoIndex == -1 ||
                    fechaFinTratamientoIndex == -1 || idRecetaMedicaIndex == -1 || idMedicamentoIndex == -1){
                throw new DAOException("DetalleRecetaDAO: Error al obtener los indices de las columnas");
            }

            detalleReceta.setIdDetalleReceta(cursor.getString(idIndex));
            detalleReceta.setPeriodicidad(cursor.getString(periodicidadIndex));
            detalleReceta.setDosis(cursor.getString(dosisIndex));
            detalleReceta.setFechaInicioTratamiento(cursor.getString(fechaInicioTratamientoIndex));
            detalleReceta.setFechaFinTratamiento(cursor.getString(fechaFinTratamientoIndex));
            detalleReceta.setIdRecetaMedica(cursor.getString(idRecetaMedicaIndex));
            detalleReceta.setIdMedicamento(cursor.getString(idMedicamentoIndex));

            return detalleReceta;

        }
    }
}
