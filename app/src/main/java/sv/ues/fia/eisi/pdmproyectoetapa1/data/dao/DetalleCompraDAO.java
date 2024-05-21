package sv.ues.fia.eisi.pdmproyectoetapa1.data.dao;

import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.DetalleCompra;

public interface DetalleCompraDAO extends DAO<DetalleCompra, String> {
    DetalleCompra obtenerPorIdCompra(String idCompra) throws DAOException;
}
