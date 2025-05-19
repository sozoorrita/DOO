package co.edu.uco.FondaControl.data.dao.entity.indicadorinventario.impl.postgresql;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.data.dao.entity.indicadorinventario.IndicadorInventarioDAO;
import co.edu.uco.FondaControl.entity.IndicadorInventarioEntity;

public class IndicadorInventarioPostgreSQLDAO implements IndicadorInventarioDAO {

	private final Connection conexion;

	public IndicadorInventarioPostgreSQLDAO(final Connection conexion) {
		this.conexion = conexion;
	}

	@Override
	public void create(final IndicadorInventarioEntity entity) {
	}

	@Override
	public List<IndicadorInventarioEntity> listByFilter(final IndicadorInventarioEntity filter) {
		return null;
	}

	@Override
	public List<IndicadorInventarioEntity> listAll() {
		return null;
	}

	@Override
	public List<IndicadorInventarioEntity> listByCodigo(final UUID codigo) {
		return null;
	}

	@Override
	public IndicadorInventarioEntity findById(final UUID codigo) {
		return null;
	}

	@Override
	public void update(final UUID codigo, final IndicadorInventarioEntity entity) {
	}

	@Override
	public void delete(final UUID codigo) {
	}

	@Override
	public void update(IndicadorInventarioEntity codigo, UUID entity) {
		// TODO Auto-generated method stub

	}
}
