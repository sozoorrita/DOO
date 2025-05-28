package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.FormaPagoBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.FormaPago.entity.FormaPagoEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.FormaPagoDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.entity.FormaPagoEntity;

import java.util.List;
import java.util.UUID;

public final class FormaPagoImpl implements FormaPagoBusinessLogic {

    private final DAOFactory factory;

    public FormaPagoImpl(final DAOFactory factory) {
        this.factory = factory;
    }

    @Override
    public void registrarFormaPago(final FormaPagoDomain domain) throws FondaControlException {
        
        if (UtilTexto.getInstancia().esNula(domain.getNombre())) {
            throw BusinessLogicFondaControlException.reportar("El nombre de la forma de pago es obligatorio.");
        }
        String nombreLimpio = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(domain.getNombre());
        
        if (nombreLimpio.length() > 50) {
            throw BusinessLogicFondaControlException.reportar(
                "El nombre de la forma de pago supera los 50 caracteres permitidos.");
        }
        
        if (!UtilTexto.getInstancia().contieneSoloLetrasYEspacios(nombreLimpio)) {
            throw BusinessLogicFondaControlException.reportar(
                "El nombre de la forma de pago solo puede contener letras y espacios.");
        }
        
        List<FormaPagoEntity> existentes = factory.getFormaPagoDAO()
            .listByFilter(FormaPagoEntity.builder().nombre(nombreLimpio).crear());
        if (!existentes.isEmpty()) {
            throw BusinessLogicFondaControlException.reportar(
                "Ya existe una forma de pago con el mismo nombre.");
        }
        
        UUID nuevoCodigo;
        do {
            nuevoCodigo = UtilUUID.generarNuevoUUID();
        } while (!factory.getFormaPagoDAO().listByCodigo(nuevoCodigo).isEmpty());
        
        FormaPagoDomain toCreate = FormaPagoDomain.crear(nuevoCodigo, nombreLimpio);
        
        factory.getFormaPagoDAO().create(
            FormaPagoEntityAssembler.getInstance().toEntity(toCreate)
        );
    }

    @Override
    public void modificarFormaPago(final FormaPagoDomain domain) throws FondaControlException {
        
        if (UtilObjeto.getInstancia().esNulo(domain) || UtilUUID.esValorDefecto(domain.getCodigo())) {
            throw new IllegalArgumentException("El código de la forma de pago no puede ser nulo para modificar.");
        }
        
        if (UtilTexto.getInstancia().esNula(domain.getNombre())) {
            throw BusinessLogicFondaControlException.reportar("El nombre de la forma de pago es obligatorio.");
        }
        String nombreLimpio = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(domain.getNombre());
        if (nombreLimpio.length() > 50) {
            throw BusinessLogicFondaControlException.reportar(
                "El nombre de la forma de pago supera los 50 caracteres permitidos.");
        }
        if (!UtilTexto.getInstancia().contieneSoloLetrasYEspacios(nombreLimpio)) {
            throw BusinessLogicFondaControlException.reportar(
                "El nombre de la forma de pago solo puede contener letras y espacios.");
        }
        
        List<FormaPagoEntity> existentes = factory.getFormaPagoDAO()
            .listByFilter(FormaPagoEntity.builder().nombre(nombreLimpio).crear());
        boolean otroConMismoNombre = existentes.stream()
            .anyMatch(e -> !e.getCodigo().equals(domain.getCodigo()));
        if (otroConMismoNombre) {
            throw BusinessLogicFondaControlException.reportar(
                "Ya existe otra forma de pago con el nombre '" + nombreLimpio + "'.");
        }
        
        FormaPagoDomain toUpdate = FormaPagoDomain.crear(domain.getCodigo(), nombreLimpio);
        factory.getFormaPagoDAO().update(domain.getCodigo(),FormaPagoEntityAssembler.getInstance().toEntity(toUpdate)
        );
    }

    @Override
    public void eliminarFormaPago(final FormaPagoDomain domain) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(domain) || UtilUUID.esValorDefecto(domain.getCodigo())) {
            throw new IllegalArgumentException("El código de la forma de pago no puede ser nulo para eliminar.");
        }
        factory.getFormaPagoDAO().delete(domain.getCodigo());
    }

    @Override
    public List<FormaPagoDomain> consultarFormaPago(final UUID codigo) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
            return FormaPagoEntityAssembler.getInstance()
                .toDomainList(factory.getFormaPagoDAO().listAll());
        }
        return FormaPagoEntityAssembler.getInstance()
            .toDomainList(factory.getFormaPagoDAO().listByCodigo(codigo));
    }
}
