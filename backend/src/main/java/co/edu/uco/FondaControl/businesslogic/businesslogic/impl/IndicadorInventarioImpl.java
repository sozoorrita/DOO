package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.IndicadorInventarioBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.IndicadorInventario.entity.IndicadorInventarioEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.IndicadorInventarioDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
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

        validarIntegridadNombreIndicadorInventario(domain.getNombre());

        final var nombre = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(domain.getNombre()).toLowerCase();

        final var nombresValidos = List.of("abastecido", "desabastecido");

        if (!nombresValidos.contains(nombre)) {
            throw BusinessLogicFondaControlException.reportar(
                    "El indicador de inventario ingresado no es válido. Solo se permiten: Abastecido y Desabastecido."
            );
        }

    }


    @Override
    public void configurarIndicadorInventario(final UUID codigo, final IndicadorInventarioDomain domain) throws FondaControlException {
        if (UtilObjeto.esNulo(codigo)) {
            throw new IllegalArgumentException("El código del indicador no puede ser nulo para configurar.");
        }
        if (UtilObjeto.esNulo(domain)) {
            throw new IllegalArgumentException("El indicador a configurar no puede ser nulo.");
        }

        final var entity = IndicadorInventarioEntityAssembler.getInstance().toEntity(domain);
        factory.getIndicadorInventarioDAO().update(codigo, entity);
    }

    @Override
    public void registrarIndicadorInventario(final IndicadorInventarioDomain indicadorInventario) throws FondaControlException {
        ValidarIntegridadInformacionRegistrarNuevoIndicadorInventario(indicadorInventario);

        validarNoExistaIndicadorInventarioConMismoNombre(indicadorInventario.getNombre());

        var codigo = generarNuevoCodigoIndicadorInventario();

        var indicadorInventarioDomainAcrear = new  IndicadorInventarioDomain(codigo, indicadorInventario.getNombre());



        var indicadorInventarioEntity = IndicadorInventarioEntityAssembler.getInstance().toEntity(indicadorInventarioDomainAcrear);
        factory.getIndicadorInventarioDAO().create(indicadorInventarioEntity);
    }

    private void ValidarIntegridadInformacionRegistrarNuevoIndicadorInventario(IndicadorInventarioDomain indicadorInventario) throws BusinessLogicFondaControlException {
        validarIntegridadNombreIndicadorInventario(indicadorInventario.getNombre());


    }

    private void validarIntegridadNombreIndicadorInventario(String nombreIndicadorInventario) throws BusinessLogicFondaControlException {
        if (UtilTexto.getInstancia().estaVacia(nombreIndicadorInventario)){
            throw BusinessLogicFondaControlException.reportar("El nombre del indicador de inventario es un dato obligatorio.");
        }
        if (UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombreIndicadorInventario).length() > 50) {
            throw BusinessLogicFondaControlException.reportar("El nombre del indicador inventario supera los 50 caracteres permitidos");
        }
        if( !UtilTexto.getInstancia().contieneSoloLetrasYEspacios(nombreIndicadorInventario)) {
            throw BusinessLogicFondaControlException.reportar("El nombre del indicador de inventario solo puede contener letras y espacios.");
        }

    }
    private void validarNoExistaIndicadorInventarioConMismoNombre(String nombreIndicadorInventario) throws BusinessLogicFondaControlException, DataFondaControlException {
        var filtro = new IndicadorInventarioEntity();
        filtro.setNombre(nombreIndicadorInventario);

        var litaResultado = factory.getIndicadorInventarioDAO().listByFilter(filtro);
        if (!litaResultado.isEmpty()) {
            throw BusinessLogicFondaControlException.reportar("Ya existe un indicador de inventario con el mismo nombre no es posible crearlo nuevamente: ");
        }
    }
    private UUID generarNuevoCodigoIndicadorInventario() throws DataFondaControlException {
        UUID nuevoCodigo;
        boolean existeCodigo;

        do {
            nuevoCodigo = UtilUUID.generarNuevoUUID();
            var listaResultado = factory.getIndicadorInventarioDAO().listByCodigo(nuevoCodigo);
            existeCodigo = !listaResultado.isEmpty();
        } while (existeCodigo);

        return nuevoCodigo;
    }


    @Override
    public List<IndicadorInventarioDomain> consultarIndicadorInventario(final UUID codigo) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
            throw new IllegalArgumentException("El código del indicador no puede ser nulo ni tener el valor por defecto.");
        }

        final var entities = factory.getIndicadorInventarioDAO().listByCodigo(codigo);
        return IndicadorInventarioEntityAssembler.getInstance().toDomainList(entities);
    }

}
