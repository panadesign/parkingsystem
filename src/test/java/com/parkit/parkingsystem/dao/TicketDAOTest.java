package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketDAOTest {

	TicketDAO ticketDAO = new TicketDAO();
	Ticket ticket = new Ticket();

	@Mock
	DataBaseConfig dataBaseConfigMock;

	@Mock
	ParkingSpot parkingSpotMock;


	@Test
	public void saveTicketTest() throws SQLException, IOException, ClassNotFoundException {
		//GIVEN
		Ticket ticket = new Ticket();
		//Connection connection = mock(Connection.class);
		//when(dataBaseConfigMock.getConnection()).thenReturn(connection);
		ticket.setParkingSpot(parkingSpotMock);
		ticket.setId(1);
		ticket.setVehicleRegNumber("AFDFD");
		ticket.setPrice(0.0);
		ticket.setInTime(new Date());
		ticket.setOutTime(new Date());

		//WHEN
		boolean response = ticketDAO.saveTicket(ticket);

		//THEN
		assertTrue(response);

	}

	@Test
	public void saveTicketErrorTest() throws SQLException, IOException, ClassNotFoundException {
		DataBaseConfig dataBaseConfig = mock(DataBaseConfig.class);
		//GIVEN
		ticketDAO = new TicketDAO();
		when(dataBaseConfig.getConnection()).thenThrow(SQLException.class);

		//WHEN
		boolean response = ticketDAO.saveTicket(ticket);

		//THEN
		assertTrue(response);
	}


	@Test
	public void updateTicketReturnTrueTest() throws SQLException {
		//GIVEN
		Ticket ticket = mock(Ticket.class);
		when(ticket.getId()).thenReturn(1);
		when(ticket.getPrice()).thenReturn(0.0);
		when(ticket.getOutTime()).thenReturn(new Date());

		//WHEN
		ticketDAO.updateTicket(ticket);

		//THEN
		assertTrue(ticketDAO.updateTicket(ticket));

	}

	@Test
	public void updateTicketReturnFalseTest() throws SQLException {
		//GIVEN
		Ticket ticket = mock(Ticket.class);
		Mockito.mockStatic(DriverManager.class);
		when(DriverManager.getConnection(anyString(), anyString(), anyString())).thenThrow(SQLException.class);

		//WHEN
		ticketDAO.updateTicket(ticket);

		//THEN
		assertFalse(ticketDAO.updateTicket(ticket));

	}
}