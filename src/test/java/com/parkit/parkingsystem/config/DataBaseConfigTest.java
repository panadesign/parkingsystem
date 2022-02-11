package com.parkit.parkingsystem.config;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DataBaseConfigTest {

	private final DataBaseConfig dataBaseConfig = new DataBaseConfig();

	@Test
	public void getConnectionTest() throws SQLException, ClassNotFoundException, IOException {
		// GIVEN
		Connection connection = Mockito.mock(Connection.class);
		Mockito.mockStatic(DriverManager.class);
		when(DriverManager.getConnection(anyString(), anyString(), anyString())).thenReturn(connection);

		// WHEN
		Connection response = dataBaseConfig.getConnection();

		// THEN
		Assertions.assertEquals(connection, response);

	}

	@Test
	public void closeConnectionTest() throws SQLException {
		// GIVEN
		Connection connection = Mockito.mock(Connection.class);

		// WHEN
		dataBaseConfig.closeConnection(connection);

		// THEN
		verify(connection, Mockito.times(1)).close();
	}

	@Test
	public void closePrepareStatementTest() throws SQLException {
		// GIVEN
		PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

		// WHEN
		dataBaseConfig.closePreparedStatement(preparedStatement);

		// THEN
		verify(preparedStatement, Mockito.times(1)).close();

	}

	@Test
	public void closeResultSetTest() throws SQLException {
		// GIVEN
		ResultSet resultSet = Mockito.mock(ResultSet.class);

		// WHEN
		dataBaseConfig.closeResultSet(resultSet);

		// THEN
		verify(resultSet, Mockito.times(1)).close();

	}

	@Test
	public void closeConnectionExceptionTest() throws SQLException {

		Connection connection = Mockito.mock(Connection.class);
		Mockito.doThrow(SQLException.class).when(connection).close();
		dataBaseConfig.closeConnection(connection);

	}

	@Test
	public void closePrepareStatementExceptionTest() throws SQLException {
		PreparedStatement ps = Mockito.mock(PreparedStatement.class);
		Mockito.doThrow(SQLException.class).when(ps).close();
		dataBaseConfig.closePreparedStatement(ps);
	}

	@Test
	public void closeResultSetExceptionTest() throws SQLException {
		ResultSet rs = Mockito.mock(ResultSet.class);
		Mockito.doThrow(SQLException.class).when(rs).close();
		dataBaseConfig.closeResultSet(rs);
	}

	@Test
	public void closeResultSetFailTest() {
		dataBaseConfig.closeResultSet(null);
	}

	@Test
	public void closePrepareStatementFailTest() {
		dataBaseConfig.closePreparedStatement(null);
	}

	@Test
	public void closeConnectionFailTest() {
		dataBaseConfig.closeConnection(null);
	}
}
