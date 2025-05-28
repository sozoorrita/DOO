package co.edu.uco.FondaControl.businesslogic.facade.imp;

import co.edu.uco.FondaControl.businesslogic.businesslogic.RolBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.RolDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.RolImpl;
import co.edu.uco.FondaControl.businesslogic.facade.RolFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.data.dao.factory.Factory;
import co.edu.uco.FondaControl.dto.RolDTO;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class RolImp implements RolFacade {

    private final RolBusinessLogic businessLogic;

    public RolImp() throws DataFondaControlException {
        var daoFactory = DAOFactory.getDAOFactory(Factory.POSTGRESQL);
        this.businessLogic = new RolImpl(daoFactory);
    }

    @Override
    public void registrarRol(RolDTO rol) throws FondaControlException {
        if (UtilObjeto.esNulo(rol) || UtilTexto.getInstancia().esNula(rol.getNombre())) {
            throw new IllegalArgumentException("El rol a registrar no puede ser nulo y debe contener un nombre.");
        }
        businessLogic.registrarRol(mapToDomain(rol));
    }

    @Override
    public void modificarRol(RolDTO rol) throws FondaControlException {
        if (UtilObjeto.esNulo(rol) || UtilObjeto.esNulo(rol.getCodigo()) 
                || UtilTexto.getInstancia().esNula(rol.getNombre())) {
            throw new IllegalArgumentException("El rol a modificar no puede ser nulo, debe contener un código y un nombre.");
        }
        businessLogic.modificarRol(mapToDomain(rol));
    }

    @Override
    public void eliminarRol(RolDTO rol) throws FondaControlException {
        if (UtilObjeto.esNulo(rol) || UtilObjeto.esNulo(rol.getCodigo())) {
            throw new IllegalArgumentException("El rol a eliminar no puede ser nulo y debe contener un código.");
        }
        businessLogic.eliminarRol(mapToDomain(rol));
    }

    @Override
    public List<RolDTO> consultarRol(RolDTO filtro) throws FondaControlException {
        if (UtilObjeto.esNulo(filtro) || UtilObjeto.esNulo(filtro.getCodigo())) {
            throw new IllegalArgumentException("El filtro no puede ser nulo y debe contener un código.");
        }
        return businessLogic.consultarRol(filtro.getCodigo())
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private RolDomain mapToDomain(RolDTO dto) {
        return new RolDomain(
                dto.getCodigo(),
                UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(dto.getNombre())
        );
    }

    private RolDTO mapToDTO(RolDomain domain) {
        return new RolDTO.Builder()
                .codigo(domain.getCodigo())
                .nombre(UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(domain.getNombre()))
                .crear();
    }
}
