package co.edu.uco.FondaControl.data.dao.entity;

public interface DeleteDAO<CODIGO, U> {
    void delete(CODIGO codigo);
}
