// src/main/java/co/edu/uco/FondaControl/businesslogic/businesslogic/impl/RolImpl.java

package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.RolBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Rol.entity.RolEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.RolDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.entity.RolEntity;

import java.util.List;
import java.util.UUID;

public final class RolImpl implements RolBusinessLogic {

    private final DAOFactory factory;

    public RolImpl(final DAOFactory factory) {
        this.factory = factory;
    }

    @Override
    public void registrarRol(final RolDomain rolDomain) throws FondaControlException {
        validarIntegridadNombreRol(rolDomain.getNombre());
        validarNoExistaRolConMismoNombre(rolDomain.getNombre());

        // Generamos un nuevo código y armamos la entidad
        final UUID codigo = UtilUUID.generarNuevoUUID();
        RolEntity entity = RolEntityAssembler.getInstance()
                .toEntity(RolDomain.crear(codigo, rolDomain.getNombre()));

        // Insertamos el rol y cerramos conexión al finalizar
        try {
            factory.getRolDAO().create(entity);
        } finally {
            factory.cerrarConexion();
        }
    }

    @Override
    public void modificarRol(final RolDomain rolDomain) throws FondaControlException {
        validarIntegridadNombreRol(rolDomain.getNombre());

        RolEntity entity = RolEntityAssembler.getInstance().toEntity(rolDomain);
        try {
            factory.getRolDAO().update(rolDomain.getCodigo(), entity);
        } finally {
            factory.cerrarConexion();
        }
    }

    @Override
    public void eliminarRol(final RolDomain rolDomain) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(rolDomain.getCodigo())) {
            throw new IllegalArgumentException("El código del rol no puede ser nulo para eliminar.");
        }

        try {
            factory.getRolDAO().delete(rolDomain.getCodigo());
        } finally {
            factory.cerrarConexion();
        }
    }

    @Override
    public List<RolDomain> consultarRol(final UUID codigo) throws FondaControlException {
        // Si el filtro es nulo, devolvemos todos los roles; si no, por código
        if (UtilObjeto.getInstancia().esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
            try {
                List<RolEntity> entidades = factory.getRolDAO().listAll();
                return RolEntityAssembler.getInstance().toDomainList(entidades);
            } finally {
                factory.cerrarConexion();
            }
        } else {
            try {
                List<RolEntity> entidades = factory.getRolDAO().listByCodigo(codigo);
                return RolEntityAssembler.getInstance().toDomainList(entidades);
            } finally {
                factory.cerrarConexion();
            }
        }
    }

    // ------------------ Métodos privados de validación ------------------

    private void validarIntegridadNombreRol(final String nombre) throws BusinessLogicFondaControlException {
        if (UtilTexto.getInstancia().esNula(nombre)) {
            throw BusinessLogicFondaControlException.reportar("El nombre del rol es obligatorio.");
        }
        String nombreTrim = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombre);
        if (nombreTrim.length() > 50) {
            throw BusinessLogicFondaControlException.reportar("El nombre del rol supera los 50 caracteres permitidos.");
        }
        if (!UtilTexto.getInstancia().contieneSoloLetrasYEspacios(nombreTrim)) {
            throw BusinessLogicFondaControlException.reportar("El nombre del rol solo puede contener letras y espacios.");
        }
    }

    private void validarNoExistaRolConMismoNombre(final String nombre) throws FondaControlException {
        // Creamos un filtro RolEntity solo con el nombre
        RolEntity filtroEntity = RolEntityAssembler.getInstance()
                .toEntity(RolDomain.crear(null, nombre)); // código null indica búsqueda por nombre

        try {
            List<RolEntity> resultado = factory.getRolDAO().listByFilter(filtroEntity);
            if (!resultado.isEmpty()) {
                throw BusinessLogicFondaControlException.reportar("Ya existe un rol con el mismo nombre.");
            }
        } finally {
            factory.cerrarConexion();
        }
    }
}
