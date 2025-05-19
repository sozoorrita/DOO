package co.edu.uco.FondaControl.data.dao.entity.informecaja.impl.postgresql;

import co.edu.uco.FondaControl.data.dao.entity.informecaja.InformeCajaDAO;
import co.edu.uco.FondaControl.entity.InformeCajaEntity;


import java.sql.Connection;
import java.util.UUID;

public class InformeCajaPostgreSQLDAO implements InformeCajaDAO {
    private Connection connection;
    public InformeCajaPostgreSQLDAO(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void update(UUID uuid, InformeCajaEntity entity) {

    }
	@Override
	public void update(InformeCajaEntity codigo, UUID entity) {
		// TODO Auto-generated method stub
		
	}
}
