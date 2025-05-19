package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.IndicadorInventarioBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.IndicadorInventarioDomain;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.entity.IndicadorInventarioEntity;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;

import java.util.List;
import java.util.UUID;

public class IndicadorInventarioImp implements IndicadorInventarioBusinessLogic {

    private final DAOFactory factory;

    public IndicadorInventarioImp(DAOFactory factory) {
        this.factory = factory;
    }

    @Override
    public void evaluarIndicadorInventario(UUID codigo, IndicadorInventarioDomain indicadorInventarioDomain) {
        if (UtilObjeto.getInstancia().esNulo(codigo)) {
            return;
        }

        // Recuperar el indicador desde la base de datos
        IndicadorInventarioEntity entity = factory.getIndicadorInventarioDAO().findById(codigo);

        if (UtilObjeto.getInstancia().esNulo(entity)) {
            return;
        }

        // Evaluar el indicador (lógica de evaluación)
        if (entity.getNombre().equalsIgnoreCase(indicadorInventarioDomain.getNombre())) {

        }
    }



    @Override
    public void configurarIndicadorInventario(UUID codigo, IndicadorInventarioDomain indicadorInventarioDomain) {
        IndicadorInventarioEntity indicadorInventarioEntity = null;
        factory.getIndicadorInventarioDAO().update(indicadorInventarioEntity, codigo);
    }

    @Override
    public void registrarIndicadorInventario(IndicadorInventarioDomain indicadorInventarioDomain) {
        IndicadorInventarioEntity indicadorInventarioEntity = null;
        factory.getIndicadorInventarioDAO().create(indicadorInventarioEntity);

    }


    @Override
    public List<IndicadorInventarioDomain> consultarIndicadorInventario(UUID codigo) {
        IndicadorInventarioEntity indicadorInventarioFilter = null;
        List<IndicadorInventarioEntity> listaIndicadorInventario = factory.getIndicadorInventarioDAO().listByFilter(indicadorInventarioFilter);

        if (UtilObjeto.getInstancia().esNulo(listaIndicadorInventario) || listaIndicadorInventario.isEmpty()) {
            return List.of();
        }

        return listaIndicadorInventario.stream()
                .map(entity -> new IndicadorInventarioDomain(entity.getCodigo(), entity.getNombre()))
                .toList();
    }
}