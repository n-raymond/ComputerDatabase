package com.excilys.mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.RowSet;

import com.excilys.model.Computer;

public class ComputerMapper implements Mapper<Computer> {
	
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String INTRODUCED = "introduced";
	private static final String DISCONTINUED = "discontinued";
	private static final String COMPANY_ID = "company_id";
	
	private static ComputerMapper _instance = null;
	
	private ComputerMapper() {
		super();
	}
	
	synchronized public static ComputerMapper getInstance() {
		if(_instance == null) {
			_instance = new ComputerMapper();
		}
		return _instance;
	}

	@Override
	public void map(Computer entity, PreparedStatement stmt) throws SQLException {
		stmt.setString(1, entity.getName());
		stmt.setTimestamp(2, entity.getIntroduced());
		stmt.setTimestamp(3, entity.getDiscontinued());
		stmt.setLong(4, entity.getCompanyId());
		
	}

	@Override
	public Computer unmap(ResultSet databaseRow) throws SQLException {
		Computer entity = new Computer(databaseRow.getLong(ID));
		entity.setName(databaseRow.getString(NAME));
		entity.setIntroduced(databaseRow.getTimestamp(INTRODUCED));
		entity.setDiscontinued(databaseRow.getTimestamp(DISCONTINUED));
		entity.setCompanyId(databaseRow.getLong(COMPANY_ID));
		return entity;
	}

}