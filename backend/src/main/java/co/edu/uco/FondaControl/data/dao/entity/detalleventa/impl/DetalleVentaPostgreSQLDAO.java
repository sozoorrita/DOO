package co.edu.uco.FondaControl.data.dao.entity.detalleventa.impl;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.data.dao.entity.detalleventa.DetalleVentaDAO;
import co.edu.uco.FondaControl.entity.DetalleVentaEntity;

public class DetalleVentaPostgreSQLDAO implements DetalleVentaDAO {

	private final Connection conexion;

	public DetalleVentaPostgreSQLDAO(final Connection conexion) {
		this.conexion = conexion;
	}

	@Override
	public void create(final DetalleVentaEntity entity) {
	}

	@Override
	public List<DetalleVentaEntity> listByFilter(final DetalleVentaEntity filter) {
		return null;
	}

	@Override
	public List<DetalleVentaEntity> listAll() {
		return null;
	}

	@Override
	public List<DetalleVentaEntity> listByCodigo(final UUID codigo) {
		return null;
	}

	@Override
	public void delete(final UUID id) {
	}

	@Override
	public void update(DetalleVentaEntity id, UUID entity) {
		// TODO Auto-generated method stub

	}
}
