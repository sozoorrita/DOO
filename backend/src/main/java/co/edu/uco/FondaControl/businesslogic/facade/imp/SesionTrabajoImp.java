package co.edu.uco.FondaControl.businesslogic.facade.imp;

import co.edu.uco.FondaControl.businesslogic.businesslogic.SesionTrabajoBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.SesionTrabajoDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.UsuarioDomain;
import co.edu.uco.FondaControl.businesslogic.facade.SesionTrabajoFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.SesionTrabajoDTO;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;


public class SesionTrabajoImp implements SesionTrabajoFacade {

    private final SesionTrabajoBusinessLogic businessLogic;

    public SesionTrabajoImp(SesionTrabajoBusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
    }

    @Override
    public void iniciarSesionTrabajo(SesionTrabajoDTO sesionTrabajo) throws FondaControlException {
        if (UtilObjeto.esNulo(sesionTrabajo)) {
            throw new IllegalArgumentException("La sesión de trabajo no puede ser nula.");
        }
        SesionTrabajoDomain domain = mapToDomain(sesionTrabajo);
        businessLogic.iniciarSesionTrabajo(domain);
    }

    @Override
    public void cerrarSesionTrabajo(SesionTrabajoDTO sesionTrabajo) throws FondaControlException {
        if (UtilObjeto.esNulo(sesionTrabajo)) {
            throw new IllegalArgumentException("La sesión de trabajo no puede ser nula.");
        }
        SesionTrabajoDomain domain = mapToDomain(sesionTrabajo);
        businessLogic.cerrarSesionTrabajo(domain);
    }

    private SesionTrabajoDomain mapToDomain(SesionTrabajoDTO dto) {
        if (UtilObjeto.esNulo(dto)) {
            return null;
        }
        UsuarioDomain usuario = new UsuarioDomain(
                dto.getIdUsuario(),
                UtilTexto.getInstancia().obtenerValorDefecto(dto.getNombreUsuario()),
                null,
                null
        );
        return new SesionTrabajoDomain(
                dto.getCodigo(),
                usuario,
                dto.getBaseCaja(),
                dto.getFechaApertura(),
                dto.getFechaCierre()
        );
    }

    private SesionTrabajoDTO mapToDTO(SesionTrabajoDomain domain) {
        if (UtilObjeto.esNulo(domain)) {
            return null;
        }
        return new SesionTrabajoDTO(
                domain.getCodigo(),
                domain.getUsuario().getId(),
                domain.getUsuario().getNombre(),
                domain.getBaseCaja(),
                domain.getFechaApertura(),
                domain.getFechaCierre()
        );
    }
}
