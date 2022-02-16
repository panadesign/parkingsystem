package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TicketDAOTest {
	Date date = new Date();
	Ticket ticket = new Ticket();

	@Test
	public void ticketGetVehicleRegNumber() {
		ticket.setVehicleRegNumber("ABCDE");
		assertEquals("ABCDE", ticket.getVehicleRegNumber());
	}

	@Test
	public void ticketHasAParkingSpotInGetTicketTest() {
		ticket.setParkingSpot(null);
		assertEquals(null, ticket.getParkingSpot());
	}

	@Test
	public void ticketHasOutTimeTest() {
		ticket.setOutTime(date);
		assertEquals(date, ticket.getOutTime());
	}
}