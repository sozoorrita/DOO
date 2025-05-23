package co.edu.uco.FondaControl.data.dao.entity.Usuario;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.data.dao.entity.CreateDAO;
import co.edu.uco.FondaControl.data.dao.entity.UpdateDAO;
import co.edu.uco.FondaControl.data.dao.entity.DeleteDAO;
import co.edu.uco.FondaControl.entity.UsuarioEntity;

import java.util.UUID;

public interface UsuarioDAO extends
        CreateDAO<UsuarioEntity>,
        UpdateDAO<UsuarioEntity, UUID>,
        DeleteDAO<UsuarioEntity, UUID> {

    UsuarioEntity findById(UUID id) throws DataFondaControlException;
}
