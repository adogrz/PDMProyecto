package sv.ues.fia.eisi.pdmproyectoetapa1.dao;

import java.util.List;

public interface DAO<T, K> {
    void insertar(T obj);
    void modificar(T obj);
    void eliminar(T obj);
    List<T> obtenerTodos();
    T obtener(K id);
}
