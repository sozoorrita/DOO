package co.edu.uco.FondaControl.businesslogic.facade.imp;

import co.edu.uco.FondaControl.businesslogic.businesslogic.InformeCajaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.InformeCaja.dto.InformeCajaDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.InformeCaja.entity.InformeCajaEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.InformeCajaDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.InformeCajaImpl;
import co.edu.uco.FondaControl.businesslogic.facade.InformeCajaFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.data.dao.factory.Factory;
import co.edu.uco.FondaControl.dto.InformeCajaDTO;

public final class InformeCajaImp implements InformeCajaFacade {

    private final DAOFactory daoFactory;
    private final InformeCajaBusinessLogic businessLogic;

    public InformeCajaImp() throws DataFondaControlException {
        this.daoFactory = DAOFactory.getDAOFactory(Factory.POSTGRESQL);
        this.businessLogic = new InformeCajaImpl(daoFactory);
    }

    @Override
    public void consolidarVentasInformeCaja(final InformeCajaDTO informeCajaDTO) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(informeCajaDTO) || UtilObjeto.getInstancia().esNulo(informeCajaDTO.getCodigo())) {
            throw DataFondaControlException.reportar(
                    "El informe de caja y su código no pueden ser nulos.",
                    "Se recibió un DTO nulo o sin código en el método consolidarVentasInformeCaja()."
            );
        }

        try {
            daoFactory.iniciarTransaccion();


            final InformeCajaDomain domain = InformeCajaDTOAssembler.getInstancia().toDomain(informeCajaDTO);

            businessLogic.consolidarventasInformeCaja(domain);

            daoFactory.getInformeCajaDAO().update(domain.getCodigo(), InformeCajaEntityAssembler.getInstance().toEntity(domain));

            daoFactory.confirmarTransaccion();
        } catch (final FondaControlException ex) {
            daoFactory.cancelarTransaccion();
            throw ex;
        } catch (final Exception ex) {
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
