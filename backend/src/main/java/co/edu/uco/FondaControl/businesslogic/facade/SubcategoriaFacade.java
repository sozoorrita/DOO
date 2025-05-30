package co.edu.uco.FondaControl.businesslogic.facade;

import java.util.List;

import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.SubcategoriaDTO;

public interface SubcategoriaFacade {
	void registrarSubcategoria(SubcategoriaDTO subcategoria) throws FondaControlException;

	void modificarSubcategoria(SubcategoriaDTO subcategoria) throws FondaControlException;

	void eliminarSubcategoria(SubcategoriaDTO subcategoria) throws FondaControlException;

	void consultarSubcategoriaPorCodigo(SubcategoriaDTO subcategoria) throws FondaControlException;

	List<SubcategoriaDTO> consultarSubcategoria(SubcategoriaDTO filtro) throws FondaControlException;
}
