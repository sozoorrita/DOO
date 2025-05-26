package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.InventarioBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Inventario.entity.InventarioEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.InventarioDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;

import java.util.UUID;

public final class InventarioImpl implements InventarioBusinessLogic {

    private final DAOFactory daoFactory;

    public InventarioImpl(final DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void actualizarCantidadEnInventario(final UUID codigo, final InventarioDomain inventario) throws FondaControlException {
        if (UtilObjeto.esNulo(inventario)) {
            throw new IllegalArgumentException("El inventario no puede ser nulo.");
        }

        if (UtilObjeto.getInstancia().esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
            throw new IllegalArgumentException("El código del inventario no puede ser nulo ni tener el valor por defecto.");
        }

        var entity = InventarioEntityAssembler.getInstance().toEntity(inventario);
        daoFactory.getInventarioDAO().update(codigo, entity);
    }

    @Override
    public void consultarCantidadInventario(final UUID codigo) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
            throw new IllegalArgumentException("El código del inventario no puede ser nulo ni tener el valor por defecto.");
        }

        daoFactory.getInventarioDAO().findById(codigo);
    }

    @Override
    public void gestionarInventarioManualmente(final InventarioDomain inventarioDomain) throws FondaControlException {
        if (UtilObjeto.esNulo(inventarioDomain)) {
            throw new IllegalArgumentException("El inventario no puede ser nulo.");
        }

        var entity = InventarioEntityAssembler.getInstance().toEntity(inventarioDomain);
        daoFactory.getInventarioDAO().createOrUpdate(entity);
    }
}

