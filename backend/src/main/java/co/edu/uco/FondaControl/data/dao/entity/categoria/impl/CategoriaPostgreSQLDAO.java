package co.edu.uco.FondaControl.data.dao.entity.categoria.impl;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.data.dao.entity.categoria.CategoriaDAO;
import co.edu.uco.FondaControl.entity.CategoriaEntity;

public class CategoriaPostgreSQLDAO implements CategoriaDAO {

	private final Connection conexion;

	public CategoriaPostgreSQLDAO(final Connection conexion) {
		this.conexion = conexion;
	}

	@Override
	public void create(final CategoriaEntity entity) {
	}

	@Override
	public List<CategoriaEntity> listByFilter(final CategoriaEntity filter) {
		return null;
	}

	@Override
	public List<CategoriaEntity> listAll() {
		return null;
	}

	@Override
	public List<CategoriaEntity> listByCodigo(final UUID codigo) {
		return null;
	}

	@Override
	public void delete(final UUID id) {
	}


	@Override
	public void update(CategoriaEntity id, UUID entity) {
		// TODO Auto-generated method stub
		
	}
}
