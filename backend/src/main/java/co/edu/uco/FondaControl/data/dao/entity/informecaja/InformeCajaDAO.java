package co.edu.uco.FondaControl.data.dao.entity.informecaja;

import co.edu.uco.FondaControl.dto.InformeCajaDTO;
import co.edu.uco.FondaControl.entity.InformeCajaEntity;
import co.edu.uco.FondaControl.data.dao.entity.UpdateDAO;

import java.util.UUID;

public interface InformeCajaDAO extends
        UpdateDAO<InformeCajaEntity, UUID>{

    void update(UUID uuid, InformeCajaEntity entity);

    void update(InformeCajaDTO informeCajaDTO);
}