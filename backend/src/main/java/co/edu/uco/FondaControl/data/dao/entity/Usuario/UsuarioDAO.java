package co.edu.uco.FondaControl.data.dao.entity.Usuario;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.data.dao.entity.CreateDAO;
import co.edu.uco.FondaControl.data.dao.entity.UpdateDAO;
import co.edu.uco.FondaControl.data.dao.entity.DeleteDAO;
import co.edu.uco.FondaControl.entity.UsuarioEntity;

import java.util.List;
import java.util.UUID;

public interface UsuarioDAO extends
        CreateDAO<UsuarioEntity>,
        UpdateDAO<UsuarioEntity, UUID>,
        DeleteDAO<UUID> {

    UsuarioEntity findById(UUID id) throws DataFondaControlException;

    List<UsuarioEntity> listByCodigo(UUID id) throws DataFondaControlException;

    List<UsuarioEntity> listByFilter(UsuarioEntity filtro) throws DataFondaControlException;

    List<UsuarioEntity> listAll() throws DataFondaControlException; // Opcional, útil para otras consultas
}
