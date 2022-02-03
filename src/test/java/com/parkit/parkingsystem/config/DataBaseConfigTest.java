package com.parkit.parkingsystem.config;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
		PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

		dataBaseConfig.closePreparedStatement(preparedStatement);

		verify(preparedStatement, Mockito.times(1)).close();

	}

}
