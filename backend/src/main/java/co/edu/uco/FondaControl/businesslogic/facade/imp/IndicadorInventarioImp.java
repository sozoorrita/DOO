package co.edu.uco.FondaControl.businesslogic.facade.imp;

import co.edu.uco.FondaControl.businesslogic.businesslogic.IndicadorInventarioBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.IndicadorInventario.dto.IndicadorInventarioDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.IndicadorInventarioDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.IndicadorInventarioImpl;
import co.edu.uco.FondaControl.businesslogic.facade.IndicadorInventarioFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.data.dao.factory.Factory;
import co.edu.uco.FondaControl.dto.IndicadorInventarioDTO;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public final class IndicadorInventarioImp implements IndicadorInventarioFacade {

    private final DAOFactory daoFactory;
    private final IndicadorInventarioBusinessLogic indicadicadorInventariobusinessLogic;

    public IndicadorInventarioImp() throws DataFondaControlException {
        daoFactory = DAOFactory.getDAOFactory(Factory.POSTGRESQL);
        indicadicadorInventariobusinessLogic = new IndicadorInventarioImpl(daoFactory);
    }

    @Override
    public void evaluarIndicadorInventario(final UUID codigo, final IndicadorInventarioDTO dto) throws FondaControlException {
        validarEntrada(codigo, dto);
        final var domain = IndicadorInventarioDTOAssembler.getInstance().toDomain(dto);
        indicadicadorInventariobusinessLogic.evaluarIndicadorInventario(codigo, domain);
    }

    @Override
    public void configurarIndicadorInventario(final UUID codigo, final IndicadorInventarioDTO dto) throws FondaControlException {
        validarEntrada(codigo, dto);
        final var domain = IndicadorInventarioDTOAssembler.getInstance().toDomain(dto);
        indicadicadorInventariobusinessLogic.configurarIndicadorInventario(codigo, domain);
    }

    @Override
    public void registrarIndicadorInventario(final IndicadorInventarioDTO indicadorInventario) throws FondaControlException {
        try {
            daoFactory.iniciarTransaccion();

            final var domain = IndicadorInventarioDTOAssembler.getInstance().toDomain(indicadorInventario);
            indicadicadorInventariobusinessLogic.registrarIndicadorInventario(domain);

            daoFactory.confirmarTransaccion();
        } catch (FondaControlException exception) {
            daoFactory.cancelarTransaccion();
            throw exception;
        } catch (Exception exception) {
            daoFactory.cancelarTransaccion();

            final var mensajeUsuario = "Se ha presentado un error al registrar el indicador de inventario.";
            final var mensajeTecnico = "Error técnico al registrar el indicador de inventario: " + exception.getMessage();

            throw BusinessLogicFondaControlException.reportar(mensajeUsuario, mensajeTecnico, exception);
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public List<IndicadorInventarioDTO> consultarIndicadorInventario(final IndicadorInventarioDTO filtro) throws FondaControlException {
        if (UtilObjeto.esNulo(filtro) || UtilObjeto.esNulo(filtro.getCodigo())) {
            throw new IllegalArgumentException("El filtro no puede ser nulo y debe contener un código.");
        }

        final var resultado = indicadicadorInventariobusinessLogic.consultarIndicadorInventario(filtro.getCodigo());

        return resultado.stream()
                .map(IndicadorInventarioDTOAssembler.getInstance()::toDto)
                .collect(Collectors.toList());
    }

    private void validarEntrada(final UUID codigo, final IndicadorInventarioDTO dto) {
        if (UtilObjeto.esNulo(codigo) || UtilObjeto.esNulo(dto)) {
            throw new IllegalArgumentException("El código y el indicador no pueden ser nulos.");
        }
    }
}
