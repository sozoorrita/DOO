package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.SesionTrabajoBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.SesionTrabajo.entity.SesionTrabajoEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.SesionTrabajoDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.entity.SesionTrabajoEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public final class SesionTrabajoImpl implements SesionTrabajoBusinessLogic {

    private final DAOFactory factory;

    public SesionTrabajoImpl(final DAOFactory factory) {
        this.factory = factory;
    }

    @Override
    public void iniciarSesionTrabajo(final SesionTrabajoDomain sesionTrabajoDomain) throws FondaControlException {
        validarIntegridadSesionParaInicio(sesionTrabajoDomain);

        final SesionTrabajoEntity sesionExistente = factory.getSesionTrabajoDAO()
                .findByUsuario(sesionTrabajoDomain.getUsuario().getId());

        if (!UtilObjeto.getInstancia().esNulo(sesionExistente) && sesionExistente.getFechaCierre() == null) {
            throw BusinessLogicFondaControlException.reportar(
                    "Ya existe una sesión de trabajo abierta para este usuario.",
                    "Intento de iniciar nueva sesión con sesión activa para el usuario ID: "
                            + sesionTrabajoDomain.getUsuario().getId()
            );
        }

        final SesionTrabajoDomain sesionNueva = SesionTrabajoDomain.crearParaRegistro(
                sesionTrabajoDomain.getUsuario(),
                sesionTrabajoDomain.getBaseCaja(),
                LocalDateTime.now()
        );

        final var sesionEntity = SesionTrabajoEntityAssembler.getInstance().toEntity(sesionNueva);
        factory.getSesionTrabajoDAO().create(sesionEntity);
    }

    @Override
    public void cerrarSesionTrabajo(final SesionTrabajoDomain sesionTrabajoDomain) throws FondaControlException {
        validarCodigoSesion(sesionTrabajoDomain.getCodigo());

        final SesionTrabajoEntity sesionExistente = factory.getSesionTrabajoDAO()
                .findById(sesionTrabajoDomain.getCodigo());

        if (UtilObjeto.getInstancia().esNulo(sesionExistente)) {
            throw BusinessLogicFondaControlException.reportar(
                    "No se encontró la sesión de trabajo a cerrar.",
                    "No existe una sesión de trabajo con el ID: " + sesionTrabajoDomain.getCodigo()
            );
        }

        if (sesionExistente.getFechaCierre() != null) {
            throw BusinessLogicFondaControlException.reportar(
                    "La sesión ya fue cerrada.",
                    "La sesión de trabajo con ID: " + sesionExistente.getCodigo()
                            + " ya tiene fecha de cierre: " + sesionExistente.getFechaCierre()
            );
        }

        sesionExistente.setFechaCierre(LocalDateTime.now());
        factory.getSesionTrabajoDAO().update(sesionExistente.getCodigo(), sesionExistente);
    }

    @Override
    public List<SesionTrabajoDomain> consultarSesionTrabajo(SesionTrabajoDomain filtro) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(filtro)) {
            filtro = SesionTrabajoDomain.obtenerValorDefecto();
        }

        try {
            var entityFilter = SesionTrabajoEntityAssembler.getInstance().toEntity(filtro);
            // Aquí se usa el tipo correcto en lugar de Object
            List<SesionTrabajoEntity> entities = factory
                    .getSesionTrabajoDAO()
                    .listByFilter(entityFilter);

            return SesionTrabajoEntityAssembler
                    .getInstance()
                    .toDomainList(entities);
        } catch (Exception e) {
            throw BusinessLogicFondaControlException.reportar(
                    "No se pudieron consultar las sesiones de trabajo.",
                    "Error técnico al consultar sesiones: " + e.getMessage(),
                    e
            );
        }
    }

    private void validarCodigoSesion(final UUID codigo) throws BusinessLogicFondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
            throw BusinessLogicFondaControlException.reportar(
                    "El código de la sesión no puede ser nulo ni por defecto.",
                    "Se recibió un código de sesión inválido para cierre: " + codigo
            );
        }
    }

    private void validarIntegridadSesionParaInicio(final SesionTrabajoDomain sesion)
            throws BusinessLogicFondaControlException {
        if (UtilObjeto.getInstancia().esNulo(sesion)) {
            throw BusinessLogicFondaControlException.reportar(
                    "La sesión de trabajo no puede ser nula.",
                    "Intento de iniciar sesión de trabajo con objeto nulo."
            );
        }

        if (UtilObjeto.getInstancia().esNulo(sesion.getUsuario())) {
            throw BusinessLogicFondaControlException.reportar(
                    "La sesión debe estar asociada a un usuario.",
                    "Usuario nulo al intentar iniciar sesión de trabajo."
            );
        }

        if (sesion.getBaseCaja() == null || sesion.getBaseCaja().signum() < 0) {
            throw BusinessLogicFondaControlException.reportar(
                    "La base de caja es obligatoria y no puede ser negativa.",
                    "Base de caja inválida al iniciar sesión: " + sesion.getBaseCaja()
            );
        }
    }
}
