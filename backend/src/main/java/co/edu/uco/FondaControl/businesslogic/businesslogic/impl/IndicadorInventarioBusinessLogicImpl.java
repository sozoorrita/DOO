// IndicadorInventarioBusinessLogicImpl.java
package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.IndicadorInventarioBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.IndicadorInventarioDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.entity.IndicadorInventarioEntity;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class IndicadorInventarioBusinessLogicImpl implements IndicadorInventarioBusinessLogic {

    private final DAOFactory factory;

    public IndicadorInventarioBusinessLogicImpl(DAOFactory factory) {
        this.factory = factory;
    }

    @Override
    public void evaluarIndicadorInventario(UUID codigo, IndicadorInventarioDomain domain) throws FondaControlException {
        if (UtilObjeto.esNulo(codigo) || UtilObjeto.esNulo(domain)) {
            throw new IllegalArgumentException("Código o dominio nulo");
        }
        // Lógica de evaluación
    }

    @Override
    public void configurarIndicadorInventario(UUID codigo, IndicadorInventarioDomain domain) throws FondaControlException {
        IndicadorInventarioEntity entity = new IndicadorInventarioEntity(domain.getCodigo(), domain.getNombre());
        factory.getIndicadorInventarioDAO().update(codigo, entity);
    }

    @Override
    public void registrarIndicadorInventario(IndicadorInventarioDomain domain) throws FondaControlException {
        IndicadorInventarioEntity entity = new IndicadorInventarioEntity(domain.getCodigo(), domain.getNombre());
        factory.getIndicadorInventarioDAO().create(entity);
    }

    @Override
    public List<IndicadorInventarioDomain> consultarIndicadorInventario(UUID codigo) throws FondaControlException {
        List<IndicadorInventarioEntity> entities = factory.getIndicadorInventarioDAO().listByCodigo(codigo);
        return entities.stream()
                .map(e -> new IndicadorInventarioDomain(e.getCodigo(), e.getNombre()))
                .collect(Collectors.toList());
    }
}
