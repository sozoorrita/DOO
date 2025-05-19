package co.edu.uco.FondaControl.data.dao.entity;

public interface UpdateDAO<CODIGO, E> {
    void update(CODIGO codigo, E entity);
}
