package sv.ues.fia.eisi.pdmproyectoetapa1.data.dao;

import java.util.List;

/**
 * Interfaz que define los métodos que deben implementar las clases DAO.
 *
 * @param <T>
 * @param <K>
 */
public interface DAO<T, K> {
    String insertar(T obj) throws DAOException;

    void modificar(T obj) throws DAOException;

    void eliminar(T obj) throws DAOException;

    List<T> obtenerTodos() throws DAOException;

    T obtener(K id) throws DAOException;
}
