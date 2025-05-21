package co.edu.uco.FondaControl.businesslogic.facade.imp;

import co.edu.uco.FondaControl.businesslogic.businesslogic.IndicadorInventarioBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.IndicadorInventarioDomain;
import co.edu.uco.FondaControl.businesslogic.facade.IndicadorInventarioFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.data.dao.factory.Factory;
import co.edu.uco.FondaControl.dto.IndicadorInventarioDTO;
import co.edu.uco.FondaControl.entity.IndicadorInventarioEntity;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class IndicadorInventarioImpl implements IndicadorInventarioFacade {

    private final DAOFactory daoFactory;
    private final IndicadorInventarioBusinessLogic businessLogic;

    public IndicadorInventarioImpl() {
        try {
            this.daoFactory = DAOFactory.getDAOFactory(Factory.POSTGRESQL);
        } catch (FondaControlException e) {
            throw new RuntimeException(e);
        }
        this.businessLogic = new IndicadorInventarioBusinessLogic() {
            @Override
            public void evaluarIndicadorInventario(UUID codigo, IndicadorInventarioDomain indicadorInventarioDomain) {
                if (UtilObjeto.esNulo(codigo) || UtilObjeto.esNulo(indicadorInventarioDomain)) {
                    throw new IllegalArgumentException("El código y el indicador de inventario no pueden ser nulos.");
                }

                List<IndicadorInventarioEntity> entities = daoFactory.getIndicadorInventarioDAO().listByCodigo(codigo);
                if (UtilObjeto.esNulo(entities) || entities.isEmpty()) {
                    throw new IllegalArgumentException("No se encontró un indicador de inventario con el código proporcionado.");
                }

                // Validar reglas de negocio (ejemplo: verificar si el nombre cumple con un formato)
                if (UtilTexto.getInstancia().esNula(indicadorInventarioDomain.getNombre())) {
                    throw new IllegalArgumentException("El nombre del indicador no puede estar vacío.");
                }

                // Lógica adicional de evaluación (puedes agregar más validaciones aquí)
            }

            @Override
            public void configurarIndicadorInventario(UUID codigo, IndicadorInventarioDomain indicadorInventarioDomain) {
                if (UtilObjeto.esNulo(codigo) || UtilObjeto.esNulo(indicadorInventarioDomain)) {
                    throw new IllegalArgumentException("El código y el indicador de inventario no pueden ser nulos.");
                }

                List<IndicadorInventarioEntity> entities = daoFactory.getIndicadorInventarioDAO().listByCodigo(codigo);
                if (UtilObjeto.esNulo(entities) || entities.isEmpty()) {
                    throw new IllegalArgumentException("No se encontró un indicador de inventario con el código proporcionado.");
                }

                // Actualizar los valores del indicador en cada entidad de la lista
                for (IndicadorInventarioEntity item : entities) {
                    item.setNombre(UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(indicadorInventarioDomain.getNombre()));
                }

                // Guardar los cambios en la base de datos
                daoFactory.getIndicadorInventarioDAO().update(entities);
            }

            @Override
            public void registrarIndicadorInventario(IndicadorInventarioDomain indicadorInventarioDomain) {
                daoFactory.getIndicadorInventarioDAO().create(mapToEntity(indicadorInventarioDomain));
            }

            @Override
            public List<IndicadorInventarioDomain> consultarIndicadorInventario(UUID codigo) {
                return daoFactory.getIndicadorInventarioDAO()
                        .listByCodigo(codigo)
                        .stream()
                        .map(IndicadorInventarioImpl::mapToDomain)
                        .collect(Collectors.toList());
            }
        };
    }

    @Override
    public void evaluarIndicadorInventario(UUID codigo, IndicadorInventarioDTO indicadorInventario) {
        if (UtilObjeto.esNulo(codigo) || UtilObjeto.esNulo(indicadorInventario)) {
            throw new IllegalArgumentException("El código y el indicador de inventario no pueden ser nulos.");
        }
        IndicadorInventarioDomain domain = mapToDomain(indicadorInventario);
        businessLogic.evaluarIndicadorInventario(codigo, domain);
    }

    @Override
    public void configurarIndicadorInventario(UUID codigo, IndicadorInventarioDTO indicadorInventario) {
        if (UtilObjeto.esNulo(codigo) || UtilObjeto.esNulo(indicadorInventario)) {
            throw new IllegalArgumentException("El código y el indicador de inventario no pueden ser nulos.");
        }
        IndicadorInventarioDomain domain = mapToDomain(indicadorInventario);
        businessLogic.configurarIndicadorInventario(codigo, domain);
    }

    @Override
    public void registrarIndicadorInventario(IndicadorInventarioDTO indicadorInventario) {
        if (UtilObjeto.esNulo(indicadorInventario)) {
            throw new IllegalArgumentException("El indicador de inventario no puede ser nulo.");
        }
        IndicadorInventarioDomain domain = mapToDomain(indicadorInventario);
        businessLogic.registrarIndicadorInventario(domain);
    }

    @Override
    public List<IndicadorInventarioDTO> consultarIndicadorInventario(IndicadorInventarioDTO filtro) {
        if (UtilObjeto.esNulo(filtro) || UtilObjeto.esNulo(filtro.getCodigo())) {
            throw new IllegalArgumentException("El filtro y su código no pueden ser nulos.");
        }
        return businessLogic.consultarIndicadorInventario(filtro.getCodigo())
                .stream()
                .map(IndicadorInventarioImpl::mapToDTO)
                .collect(Collectors.toList());
    }

    private static IndicadorInventarioDomain mapToDomain(IndicadorInventarioEntity entity) {
        if (UtilObjeto.esNulo(entity)) {
            return null;
        }
        return new IndicadorInventarioDomain(
                entity.getCodigo(),
                UtilTexto.getInstancia().obtenerValorDefecto(entity.getNombre())
        );
    }

    private static IndicadorInventarioDomain mapToDomain(IndicadorInventarioDTO dto) {
        if (UtilObjeto.esNulo(dto)) {
            return null;
        }
        return new IndicadorInventarioDomain(
                dto.getCodigo(),
                UtilTexto.getInstancia().obtenerValorDefecto(dto.getNombre())
        );
    }

    private static IndicadorInventarioDTO mapToDTO(IndicadorInventarioDomain domain) {
        if (UtilObjeto.esNulo(domain)) {
            return null;
        }
        return new IndicadorInventarioDTO(
                domain.getCodigo(),
                UtilTexto.getInstancia().obtenerValorDefecto(domain.getNombre())
        );
    }

    private static IndicadorInventarioEntity mapToEntity(IndicadorInventarioDomain domain) {
        if (UtilObjeto.esNulo(domain)) {
            return null;
        }
        return new IndicadorInventarioEntity(
                domain.getCodigo(),
                UtilTexto.getInstancia().obtenerValorDefecto(domain.getNombre())
        );
    }
}