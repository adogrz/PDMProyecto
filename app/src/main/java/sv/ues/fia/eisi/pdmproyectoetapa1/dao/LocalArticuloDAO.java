package sv.ues.fia.eisi.pdmproyectoetapa1.dao;

import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.LocalArticulo;

public interface LocalArticuloDAO extends DAO<LocalArticulo, String> {
    LocalArticulo obtenerPorIdArticulo(String idArticulo) throws DAOException;
}
