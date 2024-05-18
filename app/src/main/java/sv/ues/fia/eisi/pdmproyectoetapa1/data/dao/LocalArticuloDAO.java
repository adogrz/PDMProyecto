package sv.ues.fia.eisi.pdmproyectoetapa1.data.dao;

import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.LocalArticulo;

public interface LocalArticuloDAO extends DAO<LocalArticulo, String> {
    LocalArticulo obtenerPorIdArticulo(String idArticulo) throws DAOException;
}
