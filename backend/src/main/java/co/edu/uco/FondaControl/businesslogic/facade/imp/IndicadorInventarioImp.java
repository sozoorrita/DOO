package co.edu.uco.FondaControl.businesslogic.facade.imp;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import co.edu.uco.FondaControl.businesslogic.businesslogic.IndicadorInventarioBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.IndicadorInventarioDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.IndicadorInventarioImpl;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.IndicadorInventario.dto.IndicadorInventarioDTOAssembler;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.businesslogic.facade.IndicadorInventarioFacade;
import co.edu.uco.FondaControl.dto.IndicadorInventarioDTO;

@Service
public final class IndicadorInventarioImp implements IndicadorInventarioFacade {

    private final DAOFactory daoFactory;
    private final IndicadorInventarioBusinessLogic businessLogic;

    public IndicadorInventarioImp(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
        this.businessLogic = new IndicadorInventarioImpl(daoFactory);
    }

    @Override
    public void evaluarIndicadorInventario(UUID codigo, IndicadorInventarioDTO dto) throws FondaControlException {
        validarEntrada(codigo, dto);
        IndicadorInventarioDomain domain = IndicadorInventarioDTOAssembler.getInstance().toDomain(dto);
        businessLogic.evaluarIndicadorInventario(codigo, domain);
    }

    @Override
    public void configurarIndicadorInventario(UUID codigo, IndicadorInventarioDTO dto) throws FondaControlException {
        validarEntrada(codigo, dto);
        IndicadorInventarioDomain domain = IndicadorInventarioDTOAssembler.getInstance().toDomain(dto);
        businessLogic.configurarIndicadorInventario(codigo, domain);
    }

    @Override
    public void registrarIndicadorInventario(IndicadorInventarioDTO dto) throws FondaControlException {
        if (UtilObjeto.esNulo(dto)) {
            throw new IllegalArgumentException("El indicador de inventario no puede ser nulo.");
        }
        try {
            daoFactory.iniciarTransaccion();
            IndicadorInventarioDomain domain = IndicadorInventarioDTOAssembler.getInstance().toDomain(dto);
            businessLogic.registrarIndicadorInventario(domain);
            daoFactory.confirmarTransaccion();
        } catch (FondaControlException ex) {
            daoFactory.cancelarTransaccion();
            throw ex;
        } catch (Exception ex) {
            daoFactory.cancelarTransaccion();
            throw BusinessLogicFondaControlException.reportar(
                "Error al registrar el indicador de inventario.",
                "Error técnico: " + ex.getMessage(),
                ex
            );
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public List<IndicadorInventarioDTO> consultarIndicadorInventario(IndicadorInventarioDTO filtro) throws FondaControlException {
        if (UtilObjeto.esNulo(filtro)) {
            throw new IllegalArgumentException("El filtro no puede ser nulo.");
        }
        IndicadorInventarioDomain domainFilter = IndicadorInventarioDTOAssembler.getInstance().toDomain(filtro);
        List<IndicadorInventarioDomain> domains = businessLogic.consultarIndicadorInventario(domainFilter);
        return domains.stream()
                      .map(IndicadorInventarioDTOAssembler.getInstance()::toDto)
                      .collect(Collectors.toList());
    }

    @Override
    public void modificarIndicadorInventario(UUID codigo, IndicadorInventarioDTO dto) throws FondaControlException {
        validarEntrada(codigo, dto);
        IndicadorInventarioDomain domain = IndicadorInventarioDTOAssembler.getInstance().toDomain(dto);
        businessLogic.configurarIndicadorInventario(codigo, domain);
    }

    @Override
    public void eliminarIndicadorInventario(UUID codigo, IndicadorInventarioDTO indicadorInventario) throws FondaControlException {
        if (UtilObjeto.esNulo(codigo)) {
            throw new IllegalArgumentException("El código no puede ser nulo.");
        }
        businessLogic.configurarIndicadorInventario(codigo, null);
    }

    private void validarEntrada(UUID codigo, IndicadorInventarioDTO dto) {
        if (UtilObjeto.esNulo(codigo) || UtilObjeto.esNulo(dto)) {
            throw new IllegalArgumentException("Código y DTO no pueden ser nulos.");
        }
    }
}