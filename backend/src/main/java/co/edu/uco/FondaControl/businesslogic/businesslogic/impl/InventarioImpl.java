package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.InventarioBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.InventarioDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.IndicadorInventarioDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.data.dao.entity.inventario.InventarioDAO;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.entity.InventarioEntity;

import java.util.UUID;

public class InventarioImpl implements InventarioBusinessLogic {

    private final DAOFactory daoFactory;
    private final InventarioDAO dao;

    public InventarioImpl(final DAOFactory daoFactory) throws DataFondaControlException {
        this.daoFactory = daoFactory;
        this.dao = daoFactory.getInventarioDAO();
    }

    @Override
    public void actualizarCantidadEnInventario(UUID codigo,InventarioDomain inventario) throws FondaControlException {
        if (UtilObjeto.esNulo(inventario)) {
            throw new IllegalArgumentException("El inventario no puede ser nulo.");
        }

        dao.update(inventario.getCodigo(), toEntity(inventario));
    }

    @Override
    public void consultarCantidadInventario(UUID codigo) throws FondaControlException {
        if (UtilObjeto.esNulo(codigo)) {
            throw new IllegalArgumentException("El código del inventario no puede ser nulo.");
        }

        dao.findById(codigo);
    }

    @Override
    public void gestionarInventarioManualmente(InventarioDomain inventarioDomain) throws FondaControlException {
        if (UtilObjeto.esNulo(inventarioDomain)) {
            throw new IllegalArgumentException("El inventario no puede ser nulo.");
        }

        dao.createOrUpdate(toEntity(inventarioDomain));
    }

    private InventarioEntity toEntity(InventarioDomain domain) {
        return new InventarioEntity(
                domain.getCodigo(),
                domain.getNombreProducto(),
                domain.getCantidad(),
                domain.getCodigoIndicador()
        );
    }

    private InventarioDomain toDomain(InventarioEntity entity) {
        IndicadorInventarioDomain indicador = new IndicadorInventarioDomain(
                entity.getCodigoIndicador(),
                ""
        );

        return new InventarioDomain(
                entity.getCodigo(),
                entity.getNombreProducto(),
                entity.getCantidad(),
                indicador
        );
    }
}

