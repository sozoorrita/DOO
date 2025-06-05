package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.edu.uco.FondaControl.businesslogic.businesslogic.UsuarioBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Usuario.entity.UsuarioEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.UsuarioDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.entity.UsuarioEntity;

@Service
public final class UsuarioImpl implements UsuarioBusinessLogic {

    private final DAOFactory factory;

    public UsuarioImpl(final DAOFactory factory) {
        this.factory = factory;
    }

    @Override
    public void registrarNuevoUsuario(final UsuarioDomain usuarioDomain) throws FondaControlException {
        validarIntegridadUsuario(usuarioDomain);
        validarNoExistaUsuarioConMismoId(usuarioDomain.getId());

        final var nuevoCodigo = generarNuevoCodigoUsuario();
        final var usuarioARegistrar = new UsuarioDomain(
                nuevoCodigo,
                usuarioDomain.getNombre(),
                usuarioDomain.getCodigoRol(),
                usuarioDomain.getContrasena()
        );

        final var entity = UsuarioEntityAssembler.getInstance().toEntity(usuarioARegistrar);
        try {
            factory.getUsuarioDAO().create(entity);
        } finally {
            factory.cerrarConexion();
        }
    }

    @Override
    public void modificarUsuario(final UsuarioDomain usuarioDomain) throws FondaControlException {
        validarCodigo(usuarioDomain.getId());
        validarIntegridadUsuario(usuarioDomain);

        final var entity = UsuarioEntityAssembler.getInstance().toEntity(usuarioDomain);
        try {
            factory.getUsuarioDAO().update(usuarioDomain.getId(), entity);
        } finally {
            factory.cerrarConexion();
        }
    }

    @Override
    public void eliminarUsuario(final UsuarioDomain usuarioDomain) throws FondaControlException {
        validarCodigo(usuarioDomain.getId());
        try {
            factory.getUsuarioDAO().delete(usuarioDomain.getId());
        } finally {
            factory.cerrarConexion();
        }
    }

    @Override
    public UUID iniciarSesion(final UsuarioDomain usuarioDomain) throws FondaControlException {
        // 1) Validar que nombre, contraseña y rol no sean nulos/vacíos
        if (UtilObjeto.getInstancia().esNulo(usuarioDomain.getNombre())
                || UtilTexto.getInstancia().esNula(usuarioDomain.getContrasena())
                || UtilObjeto.getInstancia().esNulo(usuarioDomain.getCodigoRol())) {
            throw BusinessLogicFondaControlException.reportar(
                    "Todos los campos de login (nombre, contraseña y rol) son obligatorios.",
                    "Se recibió un UsuarioDomain con nombre='" + usuarioDomain.getNombre() +
                            "', password='" + usuarioDomain.getContrasena() +
                            "', codigoRol='" + usuarioDomain.getCodigoRol() + "'"
            );
        }

        try {
            // 2) Creamos un filtro de UsuarioEntity que incluya solo nombre, contrasena y codigoRol
            var filtroEntity = UsuarioEntityAssembler.getInstance().toEntity(usuarioDomain);
            //    (el assembler mapeará solo los campos no nulos: nombre, contraseña, rol)

            // 3) Consultamos con listByFilter
            List<UsuarioEntity> resultados = factory.getUsuarioDAO().listByFilter(filtroEntity);

            // 4) Si no hay resultados, las credenciales no coinciden
            if (resultados.isEmpty()) {
                throw BusinessLogicFondaControlException.reportar(
                        "Credenciales inválidas.",
                        "No se encontró ningún usuario con nombre=" + usuarioDomain.getNombre() +
                                ", rol=" + usuarioDomain.getCodigoRol()
                );
            }

            // 5) Devolver el UUID del primer usuario encontrado
            return resultados.get(0).getCodigo();

        } finally {
            // 6) Siempre cerrar conexión
            factory.cerrarConexion();
        }
    }

    @Override
    public UsuarioDomain consultarUsuarioPorNombreYRol(String nombre, UUID codigoRol) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(nombre) || UtilObjeto.getInstancia().esNulo(codigoRol)) {
            throw new IllegalArgumentException("Nombre y código de rol no pueden ser nulos.");
        }
        try {
            // 1) Construir un dominio filtro con nombre y rol
            UsuarioDomain filtroDomain = new UsuarioDomain();
            filtroDomain.setNombre(nombre);
            filtroDomain.setCodigoRol(codigoRol);

            // 2) Convertir a entidad y consultar
            var filtroEntity = UsuarioEntityAssembler.getInstance().toEntity(filtroDomain);
            List<UsuarioEntity> entidades = factory.getUsuarioDAO().listByFilter(filtroEntity);

            // 3) Si no se encuentra ninguna coincidencia
            if (entidades.isEmpty()) {
                throw BusinessLogicFondaControlException.reportar(
                        "No se encontró ningún usuario con el nombre y rol indicados.",
                        "Usuario con nombre=[" + nombre + "] y rol=[" + codigoRol + "] no existe."
                );
            }

            // 4) Convertir la primera entidad a dominio y devolver
            return UsuarioEntityAssembler.getInstance().toDomain(entidades.get(0));

        } catch (FondaControlException ex) {
            throw ex;
        } catch (Exception ex) {
            throw BusinessLogicFondaControlException.reportar(
                    "Se ha producido un error al consultar el usuario por nombre y rol.",
                    "Error técnico durante consultarUsuarioPorNombreYRol: " + ex.getMessage(),
                    ex
            );
        } finally {
            factory.cerrarConexion();
        }
    }

    @Override
    public List<UsuarioDomain> consultarUsuarios(UsuarioDomain filtro) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(filtro)) {
            filtro = new UsuarioDomain();
        }
        try {
            var entidades = factory.getUsuarioDAO()
                    .listByFilter(UsuarioEntityAssembler.getInstance().toEntity(filtro));
            return entidades.stream()
                    .map(UsuarioEntityAssembler.getInstance()::toDomain)
                    .collect(Collectors.toList());
        } catch (FondaControlException ex) {
            throw ex;
        } catch (Exception e) {
            throw BusinessLogicFondaControlException.reportar(
                    "No se pudieron consultar los usuarios.",
                    "Error técnico al listar usuarios: " + e.getMessage(),
                    e
            );
        } finally {
            factory.cerrarConexion();
        }
    }

    @Override
    public UsuarioDomain consultarUsuarioPorCodigo(UsuarioDomain usuarioDomain) throws FondaControlException {
        validarCodigo(usuarioDomain.getId());
        try {
            var entidad = factory.getUsuarioDAO().findById(usuarioDomain.getId());
            if (UtilObjeto.getInstancia().esNulo(entidad)) {
                throw BusinessLogicFondaControlException.reportar(
                        "Usuario no encontrado.",
                        "No existe usuario con ID: " + usuarioDomain.getId()
                );
            }
            return UsuarioEntityAssembler.getInstance().toDomain(entidad);
        } catch (FondaControlException ex) {
            throw ex;
        } catch (Exception e) {
            throw BusinessLogicFondaControlException.reportar(
                    "No se pudo consultar el usuario por código.",
                    "Error técnico en consultarUsuarioPorCodigo: " + e.getMessage(),
                    e
            );
        } finally {
            factory.cerrarConexion();
        }
    }

    private void validarCodigo(final UUID codigo) throws BusinessLogicFondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
            throw BusinessLogicFondaControlException.reportar(
                    "El ID del usuario no puede ser nulo ni por defecto.",
                    "ID del usuario recibido es nulo o UUID por defecto."
            );
        }
    }

    private void validarIntegridadUsuario(final UsuarioDomain usuarioDomain) throws BusinessLogicFondaControlException {
        if (UtilObjeto.getInstancia().esNulo(usuarioDomain)) {
            throw BusinessLogicFondaControlException.reportar(
                    "El usuario no puede ser nulo.",
                    "Intento de registrar o modificar un usuario con objeto Domain nulo."
            );
        }
        if (UtilTexto.getInstancia().esNula(usuarioDomain.getNombre())) {
            throw BusinessLogicFondaControlException.reportar(
                    "El nombre del usuario es obligatorio.",
                    "Se recibió un nombre nulo o vacío para el usuario."
            );
        }
        if (usuarioDomain.getNombre().length() > 50) {
            throw BusinessLogicFondaControlException.reportar(
                    "El nombre del usuario no puede superar los 50 caracteres.",
                    "Nombre recibido: [" + usuarioDomain.getNombre() + "]"
            );
        }
        if (UtilTexto.getInstancia().esNula(usuarioDomain.getContrasena())) {
            throw BusinessLogicFondaControlException.reportar(
                    "La contraseña del usuario no puede estar vacía.",
                    "Contraseña vacía al registrar/modificar usuario con ID: " + usuarioDomain.getId()
            );
        }
        if (UtilObjeto.getInstancia().esNulo(usuarioDomain.getCodigoRol())) {
            throw BusinessLogicFondaControlException.reportar(
                    "El código de rol del usuario es obligatorio.",
                    "Intento de registrar/modificar usuario sin rol."
            );
        }
    }

    private void validarNoExistaUsuarioConMismoId(final UUID id) throws FondaControlException {
        try {
            var resultado = factory.getUsuarioDAO().listByCodigo(id);
            if (!resultado.isEmpty()) {
                throw BusinessLogicFondaControlException.reportar(
                        "Ya existe un usuario con el mismo ID. No es posible registrarlo nuevamente.",
                        "Intento de registrar usuario con ID ya existente en base de datos: " + id
                );
            }
        } finally {
            factory.cerrarConexion();
        }
    }

    private UUID generarNuevoCodigoUsuario() throws FondaControlException {
        UUID nuevoCodigo;
        boolean yaExiste;
        do {
            nuevoCodigo = UtilUUID.generarNuevoUUID();
            List<UsuarioEntity> resultado;
            try {
                resultado = factory.getUsuarioDAO().listByCodigo(nuevoCodigo);
            } finally {
                factory.cerrarConexion();
            }
            yaExiste = !resultado.isEmpty();
        } while (yaExiste);
        return nuevoCodigo;
    }
}
