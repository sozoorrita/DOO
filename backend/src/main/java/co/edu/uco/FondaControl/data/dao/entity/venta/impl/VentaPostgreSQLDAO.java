package co.edu.uco.FondaControl.data.dao.entity.venta.impl;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.data.dao.entity.venta.VentaDAO;
import co.edu.uco.FondaControl.entity.VentaEntity;

public class VentaPostgreSQLDAO implements VentaDAO {

	private final Connection conexion;

	public VentaPostgreSQLDAO(final Connection conexion) {
		this.conexion = conexion;
	}

	@Override
	public void create(final VentaEntity entity) {
	}

	@Override
	public List<VentaEntity> listByFilter(final VentaEntity filter) {
		return null;
	}

	@Override
	public List<VentaEntity> listAll() {
		return null;
	}

	@Override
	public void delete(final UUID id) {
	}

	@Override
	public void update(VentaEntity id, UUID entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<VentaEntity> listByCodigo(final UUID codigo) {
		// TODO Auto-generated method stub
		return null;
	}
}
