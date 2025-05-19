package co.edu.uco.FondaControl.data.dao.entity.indicadorinventario;

import co.edu.uco.FondaControl.data.dao.entity.CreateDAO;
import co.edu.uco.FondaControl.data.dao.entity.RetrieveDAO;
import co.edu.uco.FondaControl.data.dao.entity.UpdateDAO;
import co.edu.uco.FondaControl.entity.IndicadorInventarioEntity;

import java.util.UUID;

public interface IndicadorInventarioDAO extends
        CreateDAO<IndicadorInventarioEntity>,
        RetrieveDAO<IndicadorInventarioEntity, UUID>,
        UpdateDAO<IndicadorInventarioEntity, UUID> {
    IndicadorInventarioEntity findById(UUID codigo);

    void update(UUID codigo, IndicadorInventarioEntity entity);

    void delete(UUID codigo);
}