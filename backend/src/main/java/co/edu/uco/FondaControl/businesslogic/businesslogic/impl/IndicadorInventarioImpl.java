package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.IndicadorInventarioBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.IndicadorInventario.IndicadorInventarioEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.IndicadorInventarioDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;

import java.util.List;
import java.util.UUID;

public final class IndicadorInventarioImpl implements IndicadorInventarioBusinessLogic {

    private final DAOFactory factory;

    public IndicadorInventarioImpl(final DAOFactory factory) {
        this.factory = factory;
    }

    @Override
    public void evaluarIndicadorInventario(final UUID codigo, final IndicadorInventarioDomain domain) throws FondaControlException {
        if (UtilObjeto.esNulo(codigo)) {
            throw new IllegalArgumentException("El código del indicador no puede ser nulo para evaluar.");
        }
        if (UtilObjeto.esNulo(domain)) {
            throw new IllegalArgumentException("El indicador a evaluar no puede ser nulo.");
        }

        // Aquí iría la lógica de evaluación
    }

    @Override
    public void configurarIndicadorInventario(final UUID codigo, final IndicadorInventarioDomain domain) throws FondaControlException {
        if (UtilObjeto.esNulo(codigo)) {
            throw new IllegalArgumentException("El código del indicador no puede ser nulo para configurar.");
        }
        if (UtilObjeto.esNulo(domain)) {
            throw new IllegalArgumentException("El indicador a configurar no puede ser nulo.");
        }

        final var entity = IndicadorInventarioEntityAssembler.getInstance().toEntity(domain);
        factory.getIndicadorInventarioDAO().update(codigo, entity);
    }

    @Override
    public void registrarIndicadorInventario(final IndicadorInventarioDomain domain) throws FondaControlException {
        if (UtilObjeto.esNulo(domain)) {
            throw new IllegalArgumentException("El indicador a registrar no puede ser nulo.");
        }

        final var entity = IndicadorInventarioEntityAssembler.getInstance().toEntity(domain);
        factory.getIndicadorInventarioDAO().create(entity);
    }

    @Override
    public List<IndicadorInventarioDomain> consultarIndicadorInventario(final UUID codigo) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
            throw new IllegalArgumentException("El código del indicador no puede ser nulo ni tener el valor por defecto.");
        }

        final var entities = factory.getIndicadorInventarioDAO().listByCodigo(codigo);
        return IndicadorInventarioEntityAssembler.getInstance().toDomainList(entities);
    }

}
