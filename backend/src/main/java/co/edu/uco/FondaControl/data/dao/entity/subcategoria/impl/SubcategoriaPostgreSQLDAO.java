package co.edu.uco.FondaControl.data.dao.entity.subcategoria.impl;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.data.dao.entity.subcategoria.SubcategoriaDAO;
import co.edu.uco.FondaControl.entity.SubcategoriaEntity;

public class SubcategoriaPostgreSQLDAO implements SubcategoriaDAO {

	private final Connection conexion;

	public SubcategoriaPostgreSQLDAO(final Connection conexion) {
		this.conexion = conexion;
	}

	@Override
	public void create(final SubcategoriaEntity entity) {
	}

	@Override
	public List<SubcategoriaEntity> listByFilter(final SubcategoriaEntity filter) {
		return null;
	}

	@Override
	public List<SubcategoriaEntity> listAll() {
		return null;
	}

	@Override
	public List<SubcategoriaEntity> listByCodigo(final UUID codigo) {
		return null;
	}

	@Override
	public void delete(final UUID id) {
	}

	@Override
	public void update(SubcategoriaEntity id, UUID entity) {
		// TODO Auto-generated method stub

	}
}
