package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class TicketDAOTest {

	TicketDAO ticketDAO = new TicketDAO();
	Ticket ticket = new Ticket();

	@Mock
	DataBaseConfig dataBaseConfigMock;
	@Mock
	Connection connectionMock;
	@Mock
	PreparedStatement preparedStatementMock;
	@Mock
	ParkingSpot parkingSpotMock;
	@Mock
	Date dateMock;


	@Test
	public void saveTicketErrorTest() throws SQLException, IOException, ClassNotFoundException {

		//GIVEN
		ticket.setId(1);
		ticket.setVehicleRegNumber("ABCDE");
		ticket.setParkingSpot(parkingSpotMock);
		ticket.setInTime(dateMock);
		ticket.setPrice(0.0);

		//WHEN
		ticketDAO.saveTicket(ticket);

		//THEN
		assertFalse(ticketDAO.saveTicket(ticket));

	}


	@Test
	public void updateTicketTest() throws SQLException, IOException, ClassNotFoundException {

		Ticket ticket = mock(Ticket.class);
		when(ticket.getParkingSpot()).thenReturn(parkingSpotMock);
		when(ticket.getPrice()).thenReturn(0.0);
		when(ticket.getParkingSpot()).thenReturn(parkingSpotMock);
		when(ticket.getVehicleRegNumber()).thenReturn("ABCDE");
		when(ticket.getInTime()).thenReturn(new java.util.Date(System.currentTimeMillis() - (60 * 60 * 1000)));
		when(ticket.getOutTime()).thenReturn(new java.util.Date(System.currentTimeMillis() - (60 * 60 * 1000)));
		ticketDAO.updateTicket(ticket);

		assertTrue(ticketDAO.updateTicket(ticket));

	}
}