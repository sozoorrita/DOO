package co.edu.uco.FondaControl.businesslogic.facade.imp;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.businesslogic.businesslogic.MesaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.MesaDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.MesaImpl;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Mesa.dto.MesaDTOAssembler;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.businesslogic.facade.MesaFacade;
import co.edu.uco.FondaControl.dto.MesaDTO;

@Service
public final class MesaImp implements MesaFacade {

    private final DAOFactory daoFactory;
    private final MesaBusinessLogic businessLogic;

    public MesaImp(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
        this.businessLogic = new MesaImpl(daoFactory);
    }

    @Override
    public void evaluarMesa(UUID codigo, MesaDTO dto) throws FondaControlException {
        validarEntrada(codigo, dto);
        MesaDomain domain = MesaDTOAssembler.getInstancia().toDomain(dto);
        businessLogic.evaluarMesa(codigo, domain);
    }

    @Override
    public void configurarMesa(UUID codigo, MesaDTO dto) throws FondaControlException {
        validarEntrada(codigo, dto);
        MesaDomain domain = MesaDTOAssembler.getInstancia().toDomain(dto);
        businessLogic.configurarMesa(codigo, domain);
    }

    @Override
    public void registrarMesa(MesaDTO dto) throws FondaControlException {
        if (UtilObjeto.esNulo(dto)) {
            throw new IllegalArgumentException("La mesa no puede ser nula.");
        }

        try {
            daoFactory.iniciarTransaccion();
            MesaDomain domain = MesaDTOAssembler.getInstancia().toDomain(dto);
            businessLogic.registrarMesa(domain);
            dto.setCodigo(domain.getCodigo());
            daoFactory.confirmarTransaccion();
        } catch (FondaControlException ex) {
            daoFactory.cancelarTransaccion();
            throw ex;
        } catch (Exception ex) {
            daoFactory.cancelarTransaccion();
            throw BusinessLogicFondaControlException.reportar(
                    "Se ha presentado un error al registrar la mesa.",
                    "Error técnico al registrar la mesa: " + ex.getMessage(),
                    ex
            );
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public void eliminarMesa(UUID codigo) throws FondaControlException {
        if (UtilObjeto.esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
            throw new IllegalArgumentException("El código de la mesa no puede ser nulo ni por defecto.");
        }
        try {
            daoFactory.iniciarTransaccion();
            businessLogic.eliminarmesa(codigo);
            daoFactory.confirmarTransaccion();
        } catch (FondaControlException ex) {
            daoFactory.cancelarTransaccion();
            throw ex;
        } catch (Exception ex) {
            daoFactory.cancelarTransaccion();
            throw BusinessLogicFondaControlException.reportar(
                    "Se ha presentado un error al eliminar la mesa.",
                    "Error técnico al eliminar la mesa: " + ex.getMessage(),
                    ex
            );
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public List<MesaDTO> consultarMesa(UUID codigo) throws FondaControlException {
        if (UtilObjeto.esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
            throw new IllegalArgumentException("El código de la mesa no puede ser nulo ni por defecto.");
        }
        List<MesaDomain> domains = businessLogic.consultarMesa(codigo);
        return MesaDTOAssembler.getInstancia().toDtoList(domains);
    }

    private void validarEntrada(UUID codigo, MesaDTO dto) {
        if (UtilObjeto.esNulo(codigo) || UtilObjeto.esNulo(dto)) {
            throw new IllegalArgumentException("El código y la mesa no pueden ser nulos.");
        }
    }
}
