package sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite;

import android.content.Context;

import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ArticuloDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Articulo;

/**
 * Clase que implementa el acceso a la base de datos para la entidad Articulo.
 */
public class ArticuloDAOImpl implements ArticuloDAO {
    private final BaseDatosFarmacia baseDatos;
    private final Context contexto;

    public ArticuloDAOImpl(BaseDatosFarmacia baseDatos, Context contexto) {
        this.baseDatos = baseDatos;
        this.contexto = contexto;
    }

    @Override
    public void insertar(Articulo obj) {

    }

    @Override
    public void modificar(Articulo obj) {

    }

    @Override
    public void eliminar(Articulo obj) {

    }

    @Override
    public List<Articulo> obtenerTodos() {
        return null;
    }

    @Override
    public Articulo obtener(String id) {
        return null;
    }
}
