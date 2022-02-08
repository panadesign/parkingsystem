package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.model.Ticket;
import org.mockito.Mock;
import org.mockito.Mockito;


public class TicketDAOTest {

	TicketDAO ticketDAO = new TicketDAO();

	@Mock
	Ticket ticket= Mockito.mock(Ticket.class);


}
