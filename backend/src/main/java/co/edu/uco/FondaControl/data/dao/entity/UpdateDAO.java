package co.edu.uco.FondaControl.data.dao.entity;



public interface UpdateDAO<E, CODIGO> {
    void update(CODIGO codigo, E entity);

}
