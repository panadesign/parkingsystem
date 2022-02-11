package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TicketDAOTest {

	Ticket ticket = new Ticket();
	@Mock
	ParkingSpot parkingSpotMock;
	private TicketDAO ticketDAO = new TicketDAO();
/*
	@Test
	public void saveTicketTest() {
		//GIVEN
		Ticket ticket = new Ticket();
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

	}*/
}