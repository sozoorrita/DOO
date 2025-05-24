package co.edu.uco.FondaControl.data.dao.entity.indicadorinventario;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.data.dao.entity.CreateDAO;
import co.edu.uco.FondaControl.data.dao.entity.RetrieveDAO;
import co.edu.uco.FondaControl.data.dao.entity.UpdateDAO;
import co.edu.uco.FondaControl.entity.IndicadorInventarioEntity;

import java.util.List;
import java.util.UUID;

public interface IndicadorInventarioDAO extends
        CreateDAO<IndicadorInventarioEntity>,
        RetrieveDAO<IndicadorInventarioEntity, UUID>,
        UpdateDAO<IndicadorInventarioEntity, UUID> {

    void update(UUID uuid, IndicadorInventarioEntity entity) throws DataFondaControlException;

    IndicadorInventarioEntity findById(UUID codigo) throws DataFondaControlException;

    void update(List<IndicadorInventarioEntity> entities) throws DataFondaControlException;

    void delete(UUID id) throws DataFondaControlException;
}
