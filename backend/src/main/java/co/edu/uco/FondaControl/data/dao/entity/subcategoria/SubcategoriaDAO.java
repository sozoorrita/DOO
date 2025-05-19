package co.edu.uco.FondaControl.data.dao.entity.subcategoria;

import java.util.UUID;

import co.edu.uco.FondaControl.data.dao.entity.CreateDAO;
import co.edu.uco.FondaControl.data.dao.entity.DeleteDAO;
import co.edu.uco.FondaControl.data.dao.entity.RetrieveDAO;
import co.edu.uco.FondaControl.data.dao.entity.UpdateDAO;
import co.edu.uco.FondaControl.entity.SubcategoriaEntity;

public interface SubcategoriaDAO extends CreateDAO<SubcategoriaEntity>, RetrieveDAO<SubcategoriaEntity, UUID>,
		UpdateDAO<SubcategoriaEntity, UUID>, DeleteDAO<UUID> {
}
