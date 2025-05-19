package co.edu.uco.FondaControl.data.dao.entity.producto.impl;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.data.dao.entity.producto.ProductoDAO;
import co.edu.uco.FondaControl.entity.ProductoEntity;

public class ProductoPostgreSQLDAO implements ProductoDAO {

	private final Connection conexion;

	public ProductoPostgreSQLDAO(final Connection conexion) {
		this.conexion = conexion;
	}

	@Override
	public void create(final ProductoEntity entity) {
	}

	@Override
	public List<ProductoEntity> listByFilter(final ProductoEntity filter) {
		return null;
	}

	@Override
	public List<ProductoEntity> listAll() {
		return null;
	}

	@Override
	public List<ProductoEntity> listByCodigo(final UUID codigo) {
		return null;
	}

	@Override
	public void delete(final UUID id) {
	}

	@Override
	public void update(ProductoEntity id, UUID entity) {
		// TODO Auto-generated method stub

	}
}
