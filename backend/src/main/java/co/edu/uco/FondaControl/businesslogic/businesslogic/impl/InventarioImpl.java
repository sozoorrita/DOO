package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import java.util.UUID;

import co.edu.uco.FondaControl.businesslogic.businesslogic.InventarioBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Inventario.entity.InventarioEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.InventarioDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.entity.InventarioEntity;


public final class InventarioImpl implements InventarioBusinessLogic {

	private final DAOFactory daoFactory;

	public InventarioImpl(final DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void actualizarCantidadEnInventario(final UUID codigo, final InventarioDomain inventario)
			throws FondaControlException {
		if (UtilObjeto.getInstancia().esNulo(inventario)) {
			throw BusinessLogicFondaControlException.reportar("El inventario no puede ser nulo.",
					"Se recibió InventarioDomain null en actualizarCantidadEnInventario(...)");
		}
		if (UtilObjeto.getInstancia().esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
			throw BusinessLogicFondaControlException.reportar(
					"El código del inventario no puede ser nulo ni por defecto.",
					"Parámetro codigo inválido en actualizarCantidadEnInventario(...)");
		}
		if (UtilObjeto.getInstancia().esNulo(inventario.getProducto())
				|| UtilObjeto.getInstancia().esNulo(inventario.getProducto().getCodigo())
				|| UtilUUID.esValorDefecto(inventario.getProducto().getCodigo())) {
			throw BusinessLogicFondaControlException.reportar(
					"El producto es obligatorio y debe tener un código válido.",
					"InventarioDomain.getProducto() o su código son nulos o por defecto en actualizarCantidadEnInventario(...)");
		}

		InventarioEntity entity = InventarioEntityAssembler.getInstance().toEntity(inventario);
		daoFactory.getInventarioDAO().update(codigo, entity);
	}

	@Override
	public void consultarCantidadInventario(final UUID codigo) throws FondaControlException {
		if (UtilObjeto.getInstancia().esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
			throw BusinessLogicFondaControlException.reportar(
					"El código del inventario no puede ser nulo ni por defecto.",
					"Parámetro codigo inválido en consultarCantidadInventario(...)");
		}
		daoFactory.getInventarioDAO().findById(codigo);
	}

	@Override
	public void gestionarInventarioManualmente(final InventarioDomain inventarioDomain) throws FondaControlException {
		if (UtilObjeto.getInstancia().esNulo(inventarioDomain)) {
			throw BusinessLogicFondaControlException.reportar("El inventario no puede ser nulo.",
					"Se recibió InventarioDomain null en gestionarInventarioManualmente(...)");
		}
		if (UtilObjeto.getInstancia().esNulo(inventarioDomain.getProducto())
				|| UtilObjeto.getInstancia().esNulo(inventarioDomain.getProducto().getCodigo())
				|| UtilUUID.esValorDefecto(inventarioDomain.getProducto().getCodigo())) {
			throw BusinessLogicFondaControlException.reportar(
					"El producto es obligatorio y debe tener un código válido.",
					"InventarioDomain.getProducto() o su código son nulos o por defecto en gestionarInventarioManualmente(...)");
		}

		InventarioEntity entity = InventarioEntityAssembler.getInstance().toEntity(inventarioDomain);
		daoFactory.getInventarioDAO().createOrUpdate(entity);
	}

	@Override
	public void registrarInventario(final InventarioDomain inventarioDomain) throws FondaControlException {
		if (UtilObjeto.getInstancia().esNulo(inventarioDomain)) {
			throw BusinessLogicFondaControlException.reportar("El inventario no puede ser nulo.",
					"Se recibió InventarioDomain null en registrarInventario(...)");
		}
		if (UtilObjeto.getInstancia().esNulo(inventarioDomain.getProducto())
				|| UtilObjeto.getInstancia().esNulo(inventarioDomain.getProducto().getCodigo())
				|| UtilUUID.esValorDefecto(inventarioDomain.getProducto().getCodigo())) {
			throw BusinessLogicFondaControlException.reportar(
					"El producto es obligatorio y debe tener un código válido.",
					"InventarioDomain.getProducto() o su código son nulos o por defecto en registrarInventario(...)");
		}

		// Generar y asignar un nuevo código único
		UUID nuevoCodigo;
		do {
			nuevoCodigo = UtilUUID.generarNuevoUUID();
		} while (!daoFactory.getInventarioDAO().listByCodigo(nuevoCodigo).isEmpty());
		inventarioDomain.setCodigo(nuevoCodigo);

		InventarioEntity entity = InventarioEntityAssembler.getInstance().toEntity(inventarioDomain);
		daoFactory.getInventarioDAO().create(entity);
	}

	@Override
	public void consultarInventario(final UUID codigo) throws FondaControlException {
		if (UtilObjeto.getInstancia().esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
			throw BusinessLogicFondaControlException.reportar(
					"El código del inventario no puede ser nulo ni por defecto.",
					"Parámetro codigo inválido en consultarInventario(...)");
		}
		var entity = daoFactory.getInventarioDAO().findById(codigo);
		if (UtilObjeto.getInstancia().esNulo(entity)) {
			throw BusinessLogicFondaControlException.reportar(
					"Inventario no encontrado.",
					"findById devolvió null para código: " + codigo);
		}
	}

	@Override
	public void eliminarInventario(final UUID codigo) throws FondaControlException {
		if (UtilObjeto.getInstancia().esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
			throw BusinessLogicFondaControlException.reportar(
					"El código del inventario no puede ser nulo ni por defecto.",
					"Parámetro codigo inválido en eliminarInventario(...)");
		}
		daoFactory.getInventarioDAO().delete(codigo);
	}
}
