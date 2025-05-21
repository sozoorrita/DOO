package co.edu.uco.FondaControl.businesslogic.facade.imp;

import co.edu.uco.FondaControl.businesslogic.facade.InformeCajaFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.data.dao.factory.Factory;
import co.edu.uco.FondaControl.dto.InformeCajaDTO;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;

public class InformeCajaImpl implements InformeCajaFacade {

    private final DAOFactory daoFactory;

    public InformeCajaImpl() throws FondaControlException {
        this.daoFactory = DAOFactory.getDAOFactory(Factory.POSTGRESQL);
    }

    @Override
    public void consolidarventasInformeCaja(InformeCajaDTO informeCaja) {
        if (UtilObjeto.esNulo(informeCaja) || UtilObjeto.esNulo(informeCaja.getCodigo())) {
            throw new IllegalArgumentException("El informe de caja y su código no pueden ser nulos.");
        }

        try {
            daoFactory.getInformeCajaDAO().update(informeCaja);
        } catch (Exception e) {
            throw new RuntimeException("Error al consolidar las ventas del informe de caja: " + e.getMessage(), e);
        }
    }
}