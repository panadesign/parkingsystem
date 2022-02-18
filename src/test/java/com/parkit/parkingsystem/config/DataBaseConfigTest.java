package com.parkit.parkingsystem.config;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

public class DataBaseConfigTest {

	private final DataBaseConfig dataBaseConfig = new DataBaseConfig();

	@Test
	public void getConnectionTest() throws SQLException, ClassNotFoundException, IOException {
		// GIVEN
		Connection connection = Mockito.mock(Connection.class);

		MockedStatic<DriverManager> driverManagerMockedStatic = Mockito.mockStatic(DriverManager.class);
		driverManagerMockedStatic.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString())).thenReturn(connection);

		// WHEN
		Connection response = dataBaseConfig.getConnection();

		// THEN
		Assertions.assertEquals(connection, response);
		driverManagerMockedStatic.close();

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
		//GIVEN
		Connection connection = Mockito.mock(Connection.class);

		//WHEN
		Mockito.doThrow(SQLException.class).when(connection).close();

		//THEN
		dataBaseConfig.closeConnection(connection);

	}

	@Test
	public void closePrepareStatementExceptionTest() throws SQLException {
		//GIVEN
		PreparedStatement ps = Mockito.mock(PreparedStatement.class);

		//WHEN
		Mockito.doThrow(SQLException.class).when(ps).close();

		//THEN
		dataBaseConfig.closePreparedStatement(ps);
	}

	@Test
	public void closeResultSetExceptionTest() throws SQLException {
		//GIVEN
		ResultSet rs = Mockito.mock(ResultSet.class);

		//WHEN
		Mockito.doThrow(SQLException.class).when(rs).close();

		//THEN
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
