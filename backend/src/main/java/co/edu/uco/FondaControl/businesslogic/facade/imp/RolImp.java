// src/main/java/co/edu/uco/FondaControl/businesslogic/facade/imp/RolImp.java

package co.edu.uco.FondaControl.businesslogic.facade.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import co.edu.uco.FondaControl.businesslogic.businesslogic.RolBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.RolDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.RolImpl;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.businesslogic.facade.RolFacade;
import co.edu.uco.FondaControl.dto.RolDTO;

@Service
public class RolImp implements RolFacade {

    private final RolBusinessLogic businessLogic;
    private final DAOFactory daoFactory;

    @Autowired
    public RolImp(DAOFactory daoFactory) {
        // Inyectamos la fábrica para poder cerrar la conexión tras cada operación.
        this.daoFactory = daoFactory;
        // Pasamos la misma fábrica al businessLogic, que la usará internamente.
        this.businessLogic = new RolImpl(daoFactory);
    }

    @Override
    public void registrarRol(RolDTO rol) throws FondaControlException {
        if (UtilObjeto.esNulo(rol) || UtilTexto.getInstancia().esNula(rol.getNombre())) {
            throw new IllegalArgumentException(
                    "El rol a registrar no puede ser nulo y debe contener un nombre."
            );
        }

        try {
            businessLogic.registrarRol(mapToDomain(rol));
        } finally {
            // Siempre devolvemos la conexión al pool
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public void modificarRol(RolDTO rol) throws FondaControlException {
        if (UtilObjeto.esNulo(rol)
                || UtilObjeto.esNulo(rol.getCodigo())
                || UtilTexto.getInstancia().esNula(rol.getNombre())) {
            throw new IllegalArgumentException(
                    "El rol a modificar no puede ser nulo, debe contener un código y un nombre."
            );
        }

        try {
            businessLogic.modificarRol(mapToDomain(rol));
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public void eliminarRol(RolDTO rol) throws FondaControlException {
        if (UtilObjeto.esNulo(rol) || UtilObjeto.esNulo(rol.getCodigo())) {
            throw new IllegalArgumentException(
                    "El rol a eliminar no puede ser nulo y debe contener un código."
            );
        }

        try {
            businessLogic.eliminarRol(mapToDomain(rol));
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public List<RolDTO> consultarRol(RolDTO filtro) throws FondaControlException {
        // Permitimos filtro.getCodigo() nulo para listar todos los roles
        // pero validamos que al menos exista el objeto filtro
        if (UtilObjeto.esNulo(filtro)) {
            throw new IllegalArgumentException("El filtro no puede ser nulo.");
        }

        try {
            // Pasamos filtro.getCodigo() (puede ser null para listar todos)
            List<RolDomain> resultadosDomain = businessLogic.consultarRol(filtro.getCodigo());
            return resultadosDomain.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        } finally {
            daoFactory.cerrarConexion();
        }
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
