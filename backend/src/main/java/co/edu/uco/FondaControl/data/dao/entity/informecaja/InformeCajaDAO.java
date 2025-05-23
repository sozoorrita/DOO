package co.edu.uco.FondaControl.data.dao.entity.informecaja;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.data.dao.entity.UpdateDAO;
import co.edu.uco.FondaControl.dto.InformeCajaDTO;
import co.edu.uco.FondaControl.entity.InformeCajaEntity;

import java.util.UUID;

public interface InformeCajaDAO extends
        UpdateDAO<InformeCajaEntity, UUID>{

    void update(InformeCajaDTO informeCaja) throws DataFondaControlException;
}