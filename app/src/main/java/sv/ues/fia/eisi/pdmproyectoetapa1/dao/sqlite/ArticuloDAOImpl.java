package sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ArticuloDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Articulo;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.BaseDatosFarmacia.Tablas;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.FarmaciaContrato.EntradaArticulo;

/**
 * Clase que implementa el acceso a la base de datos para la entidad Articulo.
 */
public class ArticuloDAOImpl implements ArticuloDAO {
    private final BaseDatosFarmacia baseDatos;

    public ArticuloDAOImpl(BaseDatosFarmacia baseDatos) {
        this.baseDatos = baseDatos;
    }

    @Override
    public String insertar(Articulo obj) throws DAOException {
        throw new DAOException("No implementado");
    }

    @Override
    public void modificar(Articulo obj) throws DAOException {
        throw new DAOException("No implementado");
    }

    @Override
    public void eliminar(Articulo obj) throws DAOException {
        throw new DAOException("No implementado");
    }

    @Override
    public List<Articulo> obtenerTodos() throws DAOException {
       List<Articulo> listaArticulos = new ArrayList<>();

        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s",Tablas.ARTICULO);

        try(Cursor cursor = db.rawQuery(sql, null)) {
            // Verifica que el cursor no esté vacío.
            if (cursor == null || !cursor.moveToFirst()) {
                return listaArticulos;
            }

            do {
                Articulo articulo = new Articulo();

                int idIndex = cursor.getColumnIndex(EntradaArticulo.ID_ARTICULO);
                int nombreIndex = cursor.getColumnIndex(EntradaArticulo.NOMBRE_ARTICULO);
                int preciounitarioIndex = cursor.getColumnIndex(EntradaArticulo.PRECIO_UNITARIO_ARTICULO);
                int stockIndex = cursor.getColumnIndex(EntradaArticulo.STOCK_ARTICULO);
                int descripcionIndex = cursor.getColumnIndex(EntradaArticulo.DESCRIPCION_ARTICULO);
                int proveedorIndex = cursor.getColumnIndex(EntradaArticulo.ID_PROVEEDOR);
                int tipoIndex = cursor.getColumnIndex(EntradaArticulo.ID_TIPO_ARTICULO);

                //verifica que las columnas existan
                if (idIndex == -1 || nombreIndex == -1 || preciounitarioIndex == -1 || stockIndex == -1 || descripcionIndex == -1 || proveedorIndex == -1 || tipoIndex == -1) {
                    throw new DAOException("Error al obtener los índices de las columnas.");
                }

                articulo.setIdArticulo(cursor.getString(idIndex));
                articulo.setNombre(cursor.getString(nombreIndex));
                articulo.setPrecioUnitario(cursor.getDouble(preciounitarioIndex));
                articulo.setStock(cursor.getInt(stockIndex));
                articulo.setDescripcion(cursor.getString(descripcionIndex));
                articulo.setIdProveedor(cursor.getString(proveedorIndex));
                articulo.setIdTipoArticulo(cursor.getString(tipoIndex));

                listaArticulos.add(articulo);



            } while (cursor.moveToNext());

        }
        return listaArticulos;
    }

    @Override
    public Articulo obtener(String id) throws DAOException {
       SQLiteDatabase db=baseDatos.getReadableDatabase();

       String selection=String.format("%s=?",EntradaArticulo.ID_ARTICULO);
         String[] selectionArgs={id};
            String[] columnas={
                    EntradaArticulo.ID_ARTICULO,
                    EntradaArticulo.NOMBRE_ARTICULO,
                    EntradaArticulo.PRECIO_UNITARIO_ARTICULO,
                    EntradaArticulo.STOCK_ARTICULO,
                    EntradaArticulo.DESCRIPCION_ARTICULO,
                    EntradaArticulo.ID_PROVEEDOR,
                    EntradaArticulo.ID_TIPO_ARTICULO
            };

            try(Cursor cursor=db.query(Tablas.ARTICULO,columnas,selection,selectionArgs,null,null,null)){
                if(cursor==null||!cursor.moveToFirst()){
                    throw new DAOException("No se encontró el artículo");
                }

                Articulo articulo=new Articulo();

                int idIndex=cursor.getColumnIndex(EntradaArticulo.ID_ARTICULO);
                int nombreIndex=cursor.getColumnIndex(EntradaArticulo.NOMBRE_ARTICULO);
                int preciounitarioIndex=cursor.getColumnIndex(EntradaArticulo.PRECIO_UNITARIO_ARTICULO);
                int stockIndex=cursor.getColumnIndex(EntradaArticulo.STOCK_ARTICULO);
                int descripcionIndex=cursor.getColumnIndex(EntradaArticulo.DESCRIPCION_ARTICULO);
                int proveedorIndex=cursor.getColumnIndex(EntradaArticulo.ID_PROVEEDOR);
                int tipoIndex=cursor.getColumnIndex(EntradaArticulo.ID_TIPO_ARTICULO);

                if(idIndex==-1||nombreIndex==-1||preciounitarioIndex==-1||stockIndex==-1||descripcionIndex==-1||proveedorIndex==-1||tipoIndex==-1){
                    throw new DAOException("Error al obtener los índices de las columnas.");
                }

                articulo.setIdArticulo(cursor.getString(idIndex));
                articulo.setNombre(cursor.getString(nombreIndex));
                articulo.setPrecioUnitario(cursor.getDouble(preciounitarioIndex));
                articulo.setStock(cursor.getInt(stockIndex));
                articulo.setDescripcion(cursor.getString(descripcionIndex));
                articulo.setIdProveedor(cursor.getString(proveedorIndex));
                articulo.setIdTipoArticulo(cursor.getString(tipoIndex));

                return articulo;
            }
    }
}
