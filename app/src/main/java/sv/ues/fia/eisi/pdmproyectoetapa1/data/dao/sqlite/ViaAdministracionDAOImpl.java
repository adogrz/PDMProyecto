package sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.ViaAdministracionDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.ViaAdministracion;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite.BaseDatosFarmacia.Tablas;

public class ViaAdministracionDAOImpl implements ViaAdministracionDAO {
    private final BaseDatosFarmacia baseDatos;

    public ViaAdministracionDAOImpl(BaseDatosFarmacia baseDatos) {
        this.baseDatos = baseDatos;
    }


    @Override
    public String insertar(ViaAdministracion obj) throws DAOException {
        throw new DAOException("No implementado");
    }

    @Override
    public void modificar(ViaAdministracion obj) throws DAOException {
        throw new DAOException("No implementado");
    }

    @Override
    public void eliminar(ViaAdministracion obj) throws DAOException {
        throw new DAOException("No implementado");
    }

    @Override
    public List<ViaAdministracion> obtenerTodos() throws DAOException {
        List<ViaAdministracion> listaViaAdministracion = new ArrayList<>();

        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s",Tablas.VIA_ADMINISTRACION);

        try (Cursor cursor = db.rawQuery(sql, null)) {
            // Verifica que el cursor no esté vacío.
            if (cursor == null || !cursor.moveToFirst()) {
                return listaViaAdministracion;
            }

            // Itera sobre el cursor para obtener los datos.
            do{
                ViaAdministracion viaAdministracion = new ViaAdministracion();

                int idIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaViaAdministracion.ID_VIA_ADMINISTRACION);
                int tipoIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaViaAdministracion.TIPO_VIA_ADMINISTRACION);

                //verifica que las columnas existan
                if(idIndex != -1||tipoIndex==-1){
                    viaAdministracion.setIdViaAdministracion(cursor.getString(idIndex));
                    viaAdministracion.setViaAdministracion(cursor.getString(tipoIndex));
                    listaViaAdministracion.add(viaAdministracion);
                }
            }while (cursor.moveToNext());

        }
        return listaViaAdministracion;
    }

    @Override
    public ViaAdministracion obtener(String id) throws DAOException {
       SQLiteDatabase db = baseDatos.getReadableDatabase();

       String selection = String.format("%s=?", FarmaciaContrato.EntradaViaAdministracion.ID_VIA_ADMINISTRACION);
       String[] selectionArgs = {id};


       try(Cursor cursor = db.query(Tablas.VIA_ADMINISTRACION,null,selection,selectionArgs,null,null,null)) {
           if (cursor == null || !cursor.moveToFirst()) {
               throw new DAOException("No se encontró la vía de administración");
           }

           ViaAdministracion viaAdministracion = new ViaAdministracion();

           int idIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaViaAdministracion.ID_VIA_ADMINISTRACION);
           int tipoIndex = cursor.getColumnIndex(FarmaciaContrato.EntradaViaAdministracion.TIPO_VIA_ADMINISTRACION);

           if (idIndex == -1 || tipoIndex == -1) {
               throw new DAOException("Error al obtener los índices de las columnas.");
           }

           viaAdministracion.setIdViaAdministracion(cursor.getString(idIndex));
           viaAdministracion.setViaAdministracion(cursor.getString(tipoIndex));

           return viaAdministracion;
       }

    }

}




