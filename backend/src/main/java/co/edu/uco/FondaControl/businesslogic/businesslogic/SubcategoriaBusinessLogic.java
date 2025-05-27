package co.edu.uco.FondaControl.businesslogic.businesslogic;

import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.SubcategoriaDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;

public interface SubcategoriaBusinessLogic {
	void registrarSubcategoria(SubcategoriaDomain subcategoria) throws FondaControlException;

	void modificarSubcategoria(UUID codigo, SubcategoriaDomain subcategoria) throws FondaControlException;

	void eliminarSubcategoria(UUID codigo) throws FondaControlException;

	List<SubcategoriaDomain> consultarSubcategoria(SubcategoriaDomain filtro) throws FondaControlException;
}
