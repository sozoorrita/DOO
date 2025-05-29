package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.IndicadorInventarioBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.IndicadorInventario.entity.IndicadorInventarioEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.IndicadorInventarioDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.entity.IndicadorInventarioEntity;

import java.util.List;
import java.util.UUID;

public final class IndicadorInventarioImpl implements IndicadorInventarioBusinessLogic {

    private final DAOFactory factory;

    public IndicadorInventarioImpl(final DAOFactory factory) {
        this.factory = factory;
    }

    @Override
    public void evaluarIndicadorInventario(final UUID codigo, final IndicadorInventarioDomain domain) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo)) {
            throw new IllegalArgumentException("El código del indicador no puede ser nulo para evaluar.");
        }
        if (UtilObjeto.getInstancia().esNulo(domain)) {
            throw new IllegalArgumentException("El indicador a evaluar no puede ser nulo.");
        }

        String nombre = UtilTexto.getInstancia()
            .quitarEspaciosBlancoInicioFin(domain.getNombre())
            .toLowerCase();
        if (UtilTexto.getInstancia().esNula(nombre)) {
            throw BusinessLogicFondaControlException.reportar("El nombre del indicador de inventario es obligatorio.");
        }
        if (nombre.length() > 50) {
            throw BusinessLogicFondaControlException.reportar("El nombre del indicador de inventario excede los 50 caracteres permitidos.");
        }

        List<String> permitidos = List.of("abastecido", "desabastecido");
        if (!permitidos.contains(nombre)) {
            throw BusinessLogicFondaControlException.reportar(
                "El indicador de inventario no es válido. Solo: Abastecido, Desabastecido."
            );
        }
    }

    @Override
    public void configurarIndicadorInventario(final UUID codigo, final IndicadorInventarioDomain domain) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo)) {
            throw new IllegalArgumentException("El código del indicador no puede ser nulo para configurar.");
        }
        if (UtilObjeto.getInstancia().esNulo(domain)) {
            throw new IllegalArgumentException("El indicador a configurar no puede ser nulo.");
        }

        evaluarIndicadorInventario(codigo, domain);

        IndicadorInventarioEntity entity = IndicadorInventarioEntityAssembler
            .getInstance()
            .toEntity(domain);
        factory.getIndicadorInventarioDAO().update(codigo, entity);
    }

    @Override
    public void registrarIndicadorInventario(final IndicadorInventarioDomain domain) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(domain)) {
            throw new IllegalArgumentException("El indicador a registrar no puede ser nulo.");
        }

        String nombreLimpio = UtilTexto.getInstancia()
            .quitarEspaciosBlancoInicioFin(domain.getNombre());
        if (UtilTexto.getInstancia().esNula(nombreLimpio)) {
            throw BusinessLogicFondaControlException.reportar("El nombre del indicador de inventario es obligatorio.");
        }
        if (nombreLimpio.length() > 50) {
            throw BusinessLogicFondaControlException.reportar("El nombre del indicador de inventario excede los 50 caracteres permitidos.");
        }

        IndicadorInventarioEntity filtro = new IndicadorInventarioEntity();
        filtro.setNombre(nombreLimpio);
        List<IndicadorInventarioEntity> encontrados = factory.getIndicadorInventarioDAO().listByFilter(filtro);
        if (!encontrados.isEmpty()) {
            throw BusinessLogicFondaControlException.reportar("Ya existe un indicador de inventario con ese nombre.");
        }

        UUID nuevoCodigo;
        do {
            nuevoCodigo = UtilUUID.generarNuevoUUID();
        } while (!factory.getIndicadorInventarioDAO().listByCodigo(nuevoCodigo).isEmpty());

        IndicadorInventarioDomain nuevoDomain = new IndicadorInventarioDomain(nuevoCodigo, nombreLimpio);
        IndicadorInventarioEntity entity = IndicadorInventarioEntityAssembler
            .getInstance()
            .toEntity(nuevoDomain);

        factory.getIndicadorInventarioDAO().create(entity);
    }

    @Override
    public List<IndicadorInventarioDomain> consultarIndicadorInventario(final IndicadorInventarioDomain domain) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(domain) || UtilUUID.esValorDefecto(domain.getCodigo())) {
            return IndicadorInventarioEntityAssembler
                .getInstance()
                .toDomainList(factory.getIndicadorInventarioDAO().listAll());
        }
        return IndicadorInventarioEntityAssembler
            .getInstance()
            .toDomainList(factory.getIndicadorInventarioDAO().listByCodigo(domain.getCodigo()));
    }

    @Override
    public void eliminarIndicadorInventario(final UUID codigo) throws FondaControlException {
    // Validar el código recibido
    if (UtilObjeto.getInstancia().esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
        throw BusinessLogicFondaControlException.reportar(
                "El código del indicador de inventario no puede ser nulo ni su valor por defecto.",
                "Código recibido para eliminar indicador de inventario es nulo o por defecto."
        );
    }

    var existentes = factory.getIndicadorInventarioDAO().listByCodigo(codigo);
    if (existentes == null || existentes.isEmpty()) {
        throw BusinessLogicFondaControlException.reportar(
                "No existe un indicador de inventario con el código suministrado.",
                "Intento de eliminar indicador de inventario inexistente con código: " + codigo
        );
    }

    factory.getIndicadorInventarioDAO().delete(codigo);
}

    @Override
    public void modificarIndicadorInventario(UUID codigo, IndicadorInventarioDomain indicadorInventarioDomain) throws FondaControlException {
    // Validar parámetros de entrada
    if (UtilObjeto.esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
        throw BusinessLogicFondaControlException.reportar(
                "El código del indicador de inventario no puede ser nulo ni su valor por defecto.",
                "Código recibido para modificar indicador de inventario es nulo o por defecto."
        );
    }
    if (UtilObjeto.esNulo(indicadorInventarioDomain)) {
        throw BusinessLogicFondaControlException.reportar(
                "El indicador de inventario a modificar no puede ser nulo.",
                "Intento de modificar un indicador de inventario nulo."
        );
    }

    // Evaluar lógica de negocio (nombre, permitido, etc.)
    evaluarIndicadorInventario(codigo, indicadorInventarioDomain);

    // Comprobar existencia
    var existentes = factory.getIndicadorInventarioDAO().listByCodigo(codigo);
    if (existentes == null || existentes.isEmpty()) {
        throw BusinessLogicFondaControlException.reportar(
                "No existe un indicador de inventario con el código suministrado para modificar.",
                "Intento de modificar indicador de inventario inexistente con código: " + codigo
        );
    }

    // Transformar a entity y actualizar
    IndicadorInventarioEntity entity = IndicadorInventarioEntityAssembler
            .getInstance()
            .toEntity(new IndicadorInventarioDomain(codigo, indicadorInventarioDomain.getNombre()));
    factory.getIndicadorInventarioDAO().update(codigo, entity);
}
}