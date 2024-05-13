package sv.ues.fia.eisi.pdmproyectoetapa1.dao;

import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Venta;
public interface VentaDAO extends DAO<Venta, String>{
Venta obtenerIdVenta(String idVenta) throws DAOException;

}
