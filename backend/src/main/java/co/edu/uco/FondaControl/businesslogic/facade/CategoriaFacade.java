package co.edu.uco.FondaControl.businesslogic.facade;

import java.util.List;

import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.CategoriaDTO;

public interface CategoriaFacade {
	void registrarCategoria(CategoriaDTO categoria) throws FondaControlException;

	void modificarCategoria(CategoriaDTO categoria) throws FondaControlException;

	void eliminarCategoria(CategoriaDTO categoria) throws FondaControlException;

	List<CategoriaDTO> consultarCategoria(CategoriaDTO filtro) throws FondaControlException;
}
