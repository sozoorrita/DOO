package co.edu.uco.FondaControl.businesslogic.facade.imp;

import co.edu.uco.FondaControl.businesslogic.businesslogic.FormaPagoBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.FormaPagoDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.FormaPagoImpl;
import co.edu.uco.FondaControl.businesslogic.facade.FormaPagoFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.data.dao.factory.Factory;
import co.edu.uco.FondaControl.dto.FormaPagoDTO;

import java.util.List;
import java.util.stream.Collectors;

public class FormaPagoImp implements FormaPagoFacade {

    private final FormaPagoBusinessLogic businessLogic;

    public FormaPagoImp() throws DataFondaControlException {
        var daoFactory = DAOFactory.getDAOFactory(Factory.POSTGRESQL);
        this.businessLogic = new FormaPagoImpl(daoFactory);
    }

    @Override
    public void registrarFormaPago(FormaPagoDTO formaPago) throws FondaControlException {
        if (UtilObjeto.esNulo(formaPago) || UtilTexto.getInstancia().esNula(formaPago.getNombre())) {
            throw new IllegalArgumentException("La forma de pago a registrar no puede ser nula y debe contener un nombre.");
        }
        businessLogic.registrarFormaPago(mapToDomain(formaPago));
    }

    @Override
    public void modificarFormaPago(FormaPagoDTO formaPago) throws FondaControlException {
        if (UtilObjeto.esNulo(formaPago) || UtilObjeto.esNulo(formaPago.getCodigo()) 
                || UtilTexto.getInstancia().esNula(formaPago.getNombre())) {
            throw new IllegalArgumentException("La forma de pago a modificar no puede ser nula, debe contener un código y un nombre.");
        }
        businessLogic.modificarFormaPago(mapToDomain(formaPago));
    }

    @Override
    public void eliminarFormaPago(FormaPagoDTO formaPago) throws FondaControlException {
        if (UtilObjeto.esNulo(formaPago) || UtilObjeto.esNulo(formaPago.getCodigo())) {
            throw new IllegalArgumentException("La forma de pago a eliminar no puede ser nula y debe contener un código.");
        }
        businessLogic.eliminarFormaPago(mapToDomain(formaPago));
    }

    @Override
    public List<FormaPagoDTO> consultarFormaPago(FormaPagoDTO filtro) throws FondaControlException {
        if (UtilObjeto.esNulo(filtro) || UtilObjeto.esNulo(filtro.getCodigo())) {
            throw new IllegalArgumentException("El filtro no puede ser nulo y debe contener un código.");
        }
        return businessLogic.consultarFormaPago(filtro.getCodigo())
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private FormaPagoDomain mapToDomain(FormaPagoDTO dto) {
        return new FormaPagoDomain(
                dto.getCodigo(),
                UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(dto.getNombre())
        );
    }

    private FormaPagoDTO mapToDTO(FormaPagoDomain domain) {
        return new FormaPagoDTO.Builder()
                .codigo(domain.getCodigo())
                .nombre(UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(domain.getNombre()))
                .crear();
    }
}