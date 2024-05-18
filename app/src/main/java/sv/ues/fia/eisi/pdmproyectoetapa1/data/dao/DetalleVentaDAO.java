package sv.ues.fia.eisi.pdmproyectoetapa1.data.dao;

import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.DetalleVenta;
public interface DetalleVentaDAO extends DAO<DetalleVenta, String>{

    DetalleVenta obtenerPorIdVenta(String idVenta) throws DAOException;

}
