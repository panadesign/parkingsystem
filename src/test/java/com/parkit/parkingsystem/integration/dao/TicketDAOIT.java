package com.parkit.parkingsystem.integration.dao;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TicketDAOIT {

	private static final DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	public static Ticket ticket;
	private static ParkingSpotDAO parkingSpotDAO;
	private static TicketDAO ticketDAO;
	private static DataBasePrepareService dataBasePrepareService;
	private static Connection connection;
	@Mock
	private static InputReaderUtil inputReaderUtil;
	Logger logger;

	@BeforeAll
	public static void setUp() throws Exception {
		parkingSpotDAO = new ParkingSpotDAO();
		parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
		ticketDAO = new TicketDAO();
		ticketDAO.dataBaseConfig = dataBaseTestConfig;
		dataBasePrepareService = new DataBasePrepareService();
	}

	@BeforeEach
	public void setUpPerTest() {
		//when(inputReaderUtil.readSelection()).thenReturn(1);
		dataBasePrepareService.clearDataBaseEntries();
	}

	@Test
	public void saveTicketIT() throws SQLException {
		try {
			ticket = new Ticket();
			ticket.setInTime(new Date());
			ticket.setOutTime(new Date());
			ticket.setVehicleRegNumber("ABCDE");
			ticket.setPrice(0.0);
			ticket.setParkingSpot(new ParkingSpot(1, ParkingType.CAR, true));

			ticketDAO.saveTicket(ticket);
			assertTrue(ticketDAO.saveTicket(ticket));
		} catch (Exception ex) {
			assertThrows(Exception.class, () -> ticketDAO.saveTicket(ticket));
		}
	}

	@Test
	public void updateTicketIT() throws Exception {

		ticket = new Ticket();
		ticket.setOutTime(new Date());
		ticket.setPrice(0.0);

		ticketDAO.updateTicket(ticket);
		assertTrue(ticketDAO.updateTicket(ticket));
	}

	@Test
	public void updateTicketExceptionIT() throws Exception {
		try {
			ticketDAO.updateTicket(ticket);

		} catch (Exception e) {
			assertThrows(Exception.class, () -> ticketDAO.updateTicket(ticket));
		}

		ticketDAO.updateTicket(ticket);

	}

}
