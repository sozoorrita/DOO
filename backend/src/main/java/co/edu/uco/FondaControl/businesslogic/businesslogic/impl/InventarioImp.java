package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;


import co.edu.uco.FondaControl.businesslogic.businesslogic.InventarioBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.InventarioDomain;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.entity.InventarioEntity;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;

import java.util.UUID;

public class InventarioImp implements InventarioBusinessLogic {

    private final DAOFactory factory;

    public InventarioImp(DAOFactory factory) {
        this.factory = factory;
    }

    @Override
    public void actualizarCantidadEnInventario(InventarioDomain inventarioDomain) {
        if (UtilObjeto.getInstancia().esNulo(inventarioDomain) || UtilObjeto.getInstancia().esNulo(inventarioDomain.getCodigo())) {
            return;
        }

        InventarioEntity entity = factory.getInventarioDAO().findById(inventarioDomain.getCodigo());

        if (UtilObjeto.getInstancia().esNulo(entity)) {
            return;
        }

        entity.setCantidad(inventarioDomain.getCantidad());
        factory.getInventarioDAO().update(inventarioDomain.getCodigo(), entity);
    }

    @Override
    public void consultarCantidadInventario(UUID codigo) {
        if (UtilObjeto.getInstancia().esNulo(codigo)) {
            return;
        }

        InventarioEntity entity = factory.getInventarioDAO().findById(codigo);

        if (UtilObjeto.getInstancia().esNulo(entity)) {
            return;
        }

        // Lógica para manejar la cantidad recuperada
        int cantidad = entity.getCantidad();
    }

    @Override
    public void gestionarInventarioManualmente(InventarioDomain inventarioDomain) {
        if (UtilObjeto.getInstancia().esNulo(inventarioDomain)) {
            return;
        }

        InventarioEntity entity = new InventarioEntity();
        entity.setCodigo(inventarioDomain.getCodigo());
        entity.setCantidad(inventarioDomain.getCantidad());

        factory.getInventarioDAO().createOrUpdate(entity);
    }
}