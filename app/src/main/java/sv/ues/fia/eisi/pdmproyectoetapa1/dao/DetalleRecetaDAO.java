package sv.ues.fia.eisi.pdmproyectoetapa1.dao;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.DetalleReceta;
public interface DetalleRecetaDAO extends DAO<DetalleReceta, String>{
    //metodo para obtener el detalle de una receta por el id de la RecetaMedica
    DetalleReceta obtenerDetallePorIdReceta(String idRecetaMedica) throws DAOException;
}
