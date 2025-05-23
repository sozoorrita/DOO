package co.edu.uco.FondaControl.businesslogic.facade.imp;


import co.edu.uco.FondaControl.businesslogic.businesslogic.InformeCajaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.InformeCajaDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.InformeCajaImp;
import co.edu.uco.FondaControl.businesslogic.facade.InformeCajaFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.data.dao.factory.Factory;
import co.edu.uco.FondaControl.dto.InformeCajaDTO;

public class InformeCajaImpl implements InformeCajaFacade {

    private final DAOFactory daoFactory;
    private final InformeCajaBusinessLogic businessLogic;

    public InformeCajaImpl() throws FondaControlException {
        this.daoFactory = DAOFactory.getDAOFactory(Factory.POSTGRESQL);
        this.businessLogic = new InformeCajaImp(); // usar implementación de lógica
    }

    @Override
    public void consolidarVentasInformeCaja(InformeCajaDTO informeCaja) throws FondaControlException {
        if (UtilObjeto.esNulo(informeCaja) || UtilObjeto.esNulo(informeCaja.getCodigo())) {
            throw DataFondaControlException.reportar(
                    "El informe de caja y su código no pueden ser nulos.",
                    "Se recibió un DTO nulo o sin código en consolidarVentasInformeCaja()"
            );
        }

        try {
            daoFactory.iniciarTransaccion();

            // Convertir DTO a Domain
            InformeCajaDomain domain = mapToDomain(informeCaja);

            // Aplicar lógica de negocio
            businessLogic.consolidarventasInformeCaja(domain);

            // Convertir y actualizar en base de datos si lo deseas
            daoFactory.getInformeCajaDAO().update(informeCaja); // o mapToEntity(domain)

            daoFactory.confirmarTransaccion();
        } catch (Exception e) {
            daoFactory.cancelarTransaccion();
            throw DataFondaControlException.reportar(
                    "Error al consolidar las ventas del informe de caja.",
                    "Error técnico: " + e.getMessage(),
                    e
            );
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    private InformeCajaDomain mapToDomain(InformeCajaDTO dto) {
        // Aquí deberías transformar el DTO a Domain
        // Esto depende de si tienes ventas incluidas en el DTO, si no las tienes, agrégalas manualmente
        return new InformeCajaDomain(
                dto.getCodigo(),
                dto.getCodigoSesionTrabajo(),
                dto.getFecha(),
                dto.getTotalVenta(),
                dto.getPagoEfectivo(),
                dto.getPagoTransferencia(),
                java.util.List.of() // Si aún no tienes ventas en el DTO
        );
    }
}
