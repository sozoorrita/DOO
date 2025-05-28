package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.InventarioBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Inventario.entity.InventarioEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.InventarioDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.entity.InventarioEntity;

import java.util.UUID;

public final class InventarioImpl implements InventarioBusinessLogic {

    private final DAOFactory daoFactory;

    public InventarioImpl(final DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void actualizarCantidadEnInventario(final UUID codigo, final InventarioDomain inventario) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(inventario)) {
            throw BusinessLogicFondaControlException.reportar(
                "El inventario no puede ser nulo.",
                "Se recibió InventarioDomain null en actualizarCantidadEnInventario(...)"
            );
        }
        if (UtilObjeto.getInstancia().esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
            throw BusinessLogicFondaControlException.reportar(
                "El código del inventario no puede ser nulo ni por defecto.",
                "Parámetro codigo inválido en actualizarCantidadEnInventario(...)"
            );
        }
        if (UtilTexto.getInstancia().esNula(inventario.getNombreProducto())) {
            throw BusinessLogicFondaControlException.reportar(
                "El nombre del producto es obligatorio.",
                "InventarioDomain.getNombreProducto() devolvió null o vacío."
            );
        }

        InventarioEntity entity = InventarioEntityAssembler.getInstance().toEntity(inventario);
        daoFactory.getInventarioDAO().update(codigo, entity);
    }

    @Override
    public void consultarCantidadInventario(final UUID codigo) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
            throw BusinessLogicFondaControlException.reportar(
                "El código del inventario no puede ser nulo ni por defecto.",
                "Parámetro codigo inválido en consultarCantidadInventario(...)"
            );
        }
        daoFactory.getInventarioDAO().findById(codigo);
    }

    @Override
    public void gestionarInventarioManualmente(final InventarioDomain inventarioDomain) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(inventarioDomain)) {
            throw BusinessLogicFondaControlException.reportar(
                "El inventario no puede ser nulo.",
                "Se recibió InventarioDomain null en gestionarInventarioManualmente(...)"
            );
        }
        if (UtilTexto.getInstancia().esNula(inventarioDomain.getNombreProducto())) {
            throw BusinessLogicFondaControlException.reportar(
                "El nombre del producto es obligatorio.",
                "InventarioDomain.getNombreProducto() devolvió null o vacío."
            );
        }

        InventarioEntity entity = InventarioEntityAssembler.getInstance().toEntity(inventarioDomain);
        daoFactory.getInventarioDAO().createOrUpdate(entity);
    }
}
