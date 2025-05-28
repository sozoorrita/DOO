package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.EstadoMesaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.EstadoMesa.dto.EstadoMesaDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.EstadoMesa.entity.EstadoMesaEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.EstadoMesaDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.dto.EstadoMesaDTO;
import co.edu.uco.FondaControl.entity.EstadoMesaEntity;

import java.util.List;
import java.util.UUID;

public final class EstadoMesaImpl implements EstadoMesaBusinessLogic {

    private final DAOFactory factory;

    public EstadoMesaImpl(final DAOFactory factory) {
        this.factory = factory;
    }

    @Override
    public void evaluarEstadoMesa(final UUID codigo, final EstadoMesaDomain domain) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo)) {
            throw new IllegalArgumentException("El código del estado de mesa no puede ser nulo para evaluar.");
        }

        if (UtilObjeto.getInstancia().esNulo(domain)) {
            throw new IllegalArgumentException("El estado de mesa a evaluar no puede ser nulo.");
        }

        validarIntegridadNombreEstadoMesa(domain.getNombre());

        final var nombre = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(domain.getNombre()).toLowerCase();

        final var nombresValidos = List.of("disponible", "ocupada", "reservada", "fuera de servicio");

        if (!nombresValidos.contains(nombre)) {
            throw BusinessLogicFondaControlException.reportar(
                    "El estado de mesa ingresado no es válido. Solo se permiten: Disponible, Ocupada, Reservada, Fuera de servicio."
            );
        }

    }

    @Override
    public void registrarEstadoMesa(final EstadoMesaDomain domain) throws FondaControlException {
        validarIntegridadNombreEstadoMesa(domain.getNombre());

        validarNoExistaEstadoMesaConMismoNombre(domain.getNombre());

        final var codigo = generarNuevoCodigoEstadoMesa();

        final var domainACrear = EstadoMesaDomain.crear(codigo, domain.getNombre());

        final var entity = EstadoMesaEntityAssembler.getInstance().toEntity(domainACrear);
        factory.getEstadoMesaDAO().create(entity);
    }

    @Override
    public List<EstadoMesaDomain> consultarEstadoMesa(final UUID codigo) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
            return EstadoMesaEntityAssembler.getInstance().toDomainList(factory.getEstadoMesaDAO().listAll());
        }

        return EstadoMesaEntityAssembler.getInstance().toDomainList(factory.getEstadoMesaDAO().listByCodigo(codigo));
    }


    private void validarIntegridadNombreEstadoMesa(final String nombre) throws BusinessLogicFondaControlException {
        if (UtilTexto.getInstancia().esNula(nombre)) {
            throw BusinessLogicFondaControlException.reportar("El nombre del estado de mesa es obligatorio.");
        }
        if (UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombre).length() > 50) {
            throw BusinessLogicFondaControlException.reportar("El nombre del estado de mesa supera los 50 caracteres permitidos.");
        }
        if (!UtilTexto.getInstancia().contieneSoloLetrasYEspacios(nombre)) {
            throw BusinessLogicFondaControlException.reportar("El nombre del estado de mesa solo puede contener letras y espacios.");
        }
    }

    private void validarNoExistaEstadoMesaConMismoNombre(final String nombre) throws FondaControlException {
        final var filtro = EstadoMesaEntity.builder()
                .nombre(nombre)
                .crear();

        final var resultado = factory.getEstadoMesaDAO().listByFilter(filtro);
        if (!resultado.isEmpty()) {
            throw BusinessLogicFondaControlException.reportar("Ya existe un estado de mesa con el mismo nombre.");
        }
    }


    private UUID generarNuevoCodigoEstadoMesa() throws FondaControlException {
        UUID nuevoCodigo;
        boolean existeCodigo;

        do {
            nuevoCodigo = UtilUUID.generarNuevoUUID();
            final var resultado = factory.getEstadoMesaDAO().listByCodigo(nuevoCodigo);
            existeCodigo = !resultado.isEmpty();
        } while (existeCodigo);

        return nuevoCodigo;
    }

    @Override
    public void modificarEstadoMesa(final EstadoMesaDTO dto) throws FondaControlException {
        
        final var domain = EstadoMesaDTOAssembler.getInstance().toDomain(dto);        
        evaluarEstadoMesa(dto.getCodigo(), domain);
        
        final var filtro = EstadoMesaEntity.builder()
                .nombre(domain.getNombre())
                .crear();
        final var existentes = factory.getEstadoMesaDAO().listByFilter(filtro).stream()
                .filter(e -> !e.getCodigo().equals(dto.getCodigo()))
                .toList();
        if (!existentes.isEmpty()) {
            throw BusinessLogicFondaControlException.reportar(
                "Ya existe otro estado de mesa con el nombre '" + domain.getNombre() + "'."
            );
        }

        final var entityToUpdate = EstadoMesaEntityAssembler.getInstance().toEntity(domain);
        factory.getEstadoMesaDAO().update(domain.getCodigo(), entityToUpdate);
    }
}
