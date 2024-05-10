package sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ViaAdministracionDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.ViaAdministracion;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.BaseDatosFarmacia.Tablas;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.FarmaciaContrato.EntradaViaAdministracion;

public class ViaAdministracionDAOImpl implements ViaAdministracionDAO {
    private final BaseDatosFarmacia baseDatos;

    public ViaAdministracionDAOImpl(BaseDatosFarmacia baseDatos) {
        this.baseDatos = baseDatos;
    }


    @Override
    public String insertar(ViaAdministracion obj) throws DAOException {
        throw new UnsupportedOperationException("No implementado");
    }

    @Override
    public void modificar(ViaAdministracion obj) throws DAOException {
        throw new UnsupportedOperationException("No implementado");
    }

    @Override
    public void eliminar(ViaAdministracion obj) throws DAOException {
        throw new UnsupportedOperationException("No implementado");
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

                int idIndex = cursor.getColumnIndex(EntradaViaAdministracion.ID_VIA_ADMINISTRACION);
                int nombreIndex = cursor.getColumnIndex(EntradaViaAdministracion.TIPO_VIA_ADMINISTRACION);

                //verifica que las columnas existan
                if(idIndex != -1||nombreIndex==-1){
                    viaAdministracion.setIdViaAdministracion(cursor.getString(idIndex));
                    viaAdministracion.setViaAdministracion(cursor.getString(nombreIndex));
                    listaViaAdministracion.add(viaAdministracion);
                }
            }while (cursor.moveToNext());

        }
        return listaViaAdministracion;
    }

    @Override
    public ViaAdministracion obtener(String id) throws DAOException {
        throw new UnsupportedOperationException("No implementado");
    }

}




