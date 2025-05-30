package co.edu.uco.FondaControl.businesslogic.facade.imp;

import org.springframework.stereotype.Service;

import co.edu.uco.FondaControl.businesslogic.businesslogic.InformeCajaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.InformeCajaDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.InformeCajaImpl;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.InformeCaja.dto.InformeCajaDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.InformeCaja.entity.InformeCajaEntityAssembler;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.businesslogic.facade.InformeCajaFacade;
import co.edu.uco.FondaControl.dto.InformeCajaDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InformeCajaImp implements InformeCajaFacade {

    private final DAOFactory daoFactory;
    private final InformeCajaBusinessLogic businessLogic;

    public InformeCajaImp(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
        this.businessLogic = new InformeCajaImpl(daoFactory);
    }

    @Override
    public void crearInformeCaja(InformeCajaDTO informeCajaDTO) throws FondaControlException {
        if (UtilObjeto.esNulo(informeCajaDTO)) {
            throw new IllegalArgumentException("El informe de caja no puede ser nulo.");
        }
        try {
            daoFactory.iniciarTransaccion();

            InformeCajaDomain domain = InformeCajaDTOAssembler.getInstancia()
                    .toDomain(informeCajaDTO);
            businessLogic.crearInformeCaja(domain);

            // Propagar el código generado de vuelta al DTO
            informeCajaDTO.setCodigo(domain.getCodigo());

            daoFactory.confirmarTransaccion();
        } catch (FondaControlException ex) {
            daoFactory.cancelarTransaccion();
            throw ex;
        } catch (Exception ex) {
            daoFactory.cancelarTransaccion();
            throw BusinessLogicFondaControlException.reportar(
                    "Se ha presentado un problema creando el informe de caja.",
                    "Error técnico inesperado en crearInformeCaja(): " + ex.getMessage(),
                    ex
            );
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public void eliminarInformeCaja(InformeCajaDTO informeCajaDTO) throws FondaControlException {
        if (UtilObjeto.esNulo(informeCajaDTO) || UtilObjeto.esNulo(informeCajaDTO.getCodigo())) {
            throw new IllegalArgumentException("El informe de caja y su código no pueden ser nulos.");
        }
        try {
            daoFactory.iniciarTransaccion();

            InformeCajaDomain domain = InformeCajaDTOAssembler.getInstancia()
                    .toDomain(informeCajaDTO);
            businessLogic.eliminarInformeCaja(domain);

            daoFactory.confirmarTransaccion();
        } catch (FondaControlException ex) {
            daoFactory.cancelarTransaccion();
            throw ex;
        } catch (Exception ex) {
            daoFactory.cancelarTransaccion();
            throw BusinessLogicFondaControlException.reportar(
                    "Se ha presentado un problema eliminando el informe de caja.",
                    "Error técnico inesperado en eliminarInformeCaja(): " + ex.getMessage(),
                    ex
            );
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public void consultarInformeCajaPorCodigo(InformeCajaDTO informeCajaDTO) throws FondaControlException {
        if (UtilObjeto.esNulo(informeCajaDTO) || UtilObjeto.esNulo(informeCajaDTO.getCodigo())) {
            throw new IllegalArgumentException("El informe de caja y su código no pueden ser nulos.");
        }
        try {
            InformeCajaDomain domain = InformeCajaDTOAssembler.getInstancia()
                    .toDomain(informeCajaDTO);
            businessLogic.consultarInformeCajaPorcodigo(domain);

            // Copiar los datos del dominio de vuelta al DTO
            InformeCajaDTO resultado = InformeCajaDTOAssembler.getInstancia()
                    .toDto(domain);
            informeCajaDTO.setCodigoSesionTrabajo(resultado.getCodigoSesionTrabajo());
            informeCajaDTO.setTotalVenta(resultado.getTotalVenta());
            informeCajaDTO.setPagoEfectivo(resultado.getPagoEfectivo());
            informeCajaDTO.setPagoTransferencia(resultado.getPagoTransferencia());
        } catch (FondaControlException ex) {
            throw ex;
        } catch (Exception ex) {
            throw BusinessLogicFondaControlException.reportar(
                    "Se ha presentado un problema consultando el informe de caja por código.",
                    "Error técnico inesperado en consultarInformeCajaPorCodigo(): " + ex.getMessage(),
                    ex
            );
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public List<InformeCajaDTO> consultarInformeCaja(InformeCajaDTO filtro) throws FondaControlException {
        try {
            InformeCajaDomain filterDomain = UtilObjeto.esNulo(filtro)
                    ? new InformeCajaDomain()
                    : InformeCajaDTOAssembler.getInstancia().toDomain(filtro);

            List<InformeCajaDomain> domains = businessLogic.consultarInformeCaja(filterDomain);

            return domains.stream()
                    .map(InformeCajaDTOAssembler.getInstancia()::toDto)
                    .collect(Collectors.toList());
        } catch (FondaControlException ex) {
            throw ex;
        } catch (Exception ex) {
            throw BusinessLogicFondaControlException.reportar(
                    "Se ha presentado un problema consultando los informes de caja.",
                    "Error técnico inesperado en consultarInformeCaja(): " + ex.getMessage(),
                    ex
            );
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public void consolidarVentasInformeCaja(InformeCajaDTO informeCajaDTO) throws FondaControlException {
        if (UtilObjeto.esNulo(informeCajaDTO) || UtilObjeto.esNulo(informeCajaDTO.getCodigo())) {
            throw new IllegalArgumentException("El informe de caja y su código no pueden ser nulos.");
        }

        try {
            daoFactory.iniciarTransaccion();

            InformeCajaDomain domain = InformeCajaDTOAssembler.getInstancia()
                    .toDomain(informeCajaDTO);

            businessLogic.consolidarventasInformeCaja(domain);

            daoFactory.getInformeCajaDAO()
                    .update(domain.getCodigo(),
                            InformeCajaEntityAssembler.getInstance().toEntity(domain));

            daoFactory.confirmarTransaccion();
        } catch (FondaControlException ex) {
            daoFactory.cancelarTransaccion();
            throw ex;
        } catch (Exception ex) {
            daoFactory.cancelarTransaccion();
            throw BusinessLogicFondaControlException.reportar(
                    "Se ha presentado un problema consolidando las ventas del informe de caja.",
                    "Error técnico inesperado en consolidarVentasInformeCaja(): " + ex.getMessage(),
                    ex
            );
        } finally {
            daoFactory.cerrarConexion();
        }
    }
}
