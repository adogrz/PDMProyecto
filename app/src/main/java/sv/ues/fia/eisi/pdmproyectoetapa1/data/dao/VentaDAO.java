package sv.ues.fia.eisi.pdmproyectoetapa1.data.dao;

import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Venta;
public interface VentaDAO extends DAO<Venta, String>{
Venta obtenerIdVenta(String idVenta) throws DAOException;

}
