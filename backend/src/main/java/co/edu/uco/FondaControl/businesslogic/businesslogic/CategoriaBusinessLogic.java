package co.edu.uco.FondaControl.businesslogic.businesslogic;

import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.CategoriaDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;

public interface CategoriaBusinessLogic {
	void registrarCategoria(CategoriaDomain categoria) throws FondaControlException;

	void modificarCategoria(UUID codigo, CategoriaDomain categoria) throws FondaControlException;

	void eliminarCategoria(UUID codigo) throws FondaControlException;

	List<CategoriaDomain> consultarCategoria(CategoriaDomain filtro) throws FondaControlException;
}
