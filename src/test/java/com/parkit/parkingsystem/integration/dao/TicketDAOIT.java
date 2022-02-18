package com.parkit.parkingsystem.integration.dao;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class TicketDAOIT {

	private static TicketDAO ticketDAO;
	private static DataBasePrepareService dataBasePrepareService;

	@BeforeAll
	public static void setUp() {
		ticketDAO = new TicketDAO();
		ticketDAO.dataBaseConfig = new DataBaseTestConfig();
		dataBasePrepareService = new DataBasePrepareService();
	}

	@BeforeEach
	public void setUpPerTest() {
		dataBasePrepareService.clearDataBaseEntries();
	}

	@Test
	public void saveTicketIT() {
		//GIVEN
		Ticket ticket = new Ticket();

		//WHEN
		ticket.setInTime(new Date());
		ticket.setOutTime(new Date());
		ticket.setVehicleRegNumber("ABCDE");
		ticket.setPrice(0.0);
		ticket.setParkingSpot(new ParkingSpot(1, ParkingType.CAR, true));

		//THEN
		assertTrue(ticketDAO.saveTicket(ticket));
	}

	@Test
	public void saveTicketExceptionIT() {
		//GIVEN
		MockedStatic<DriverManager> driverManagerMockedStatic = Mockito.mockStatic(DriverManager.class);

		//WHEN
		driverManagerMockedStatic.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString())).thenThrow(SQLException.class);

		//THEN
		assertFalse(ticketDAO.saveTicket(new Ticket()));
		driverManagerMockedStatic.close();
	}

	@Test
	public void updateTicketIT() {
		//GIVEN
		Ticket ticket = new Ticket();

		//WHEN
		ticket.setOutTime(new Date());
		ticket.setPrice(0.0);

		//THEN
		assertTrue(ticketDAO.updateTicket(ticket));
	}

	@Test
	public void updateTicketExceptionIT() {
		//GIVEN
		MockedStatic<DriverManager> driverManagerMockedStatic = Mockito.mockStatic(DriverManager.class);

		//WHEN
		driverManagerMockedStatic.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString())).thenThrow(SQLException.class);

		//THEN
		assertFalse(ticketDAO.updateTicket(new Ticket()));
		driverManagerMockedStatic.close();
	}

	@Test
	public void vehicleExistInDatabaseExceptionIT() {
		//GIVEN
		MockedStatic<DriverManager> driverManagerMockedStatic = Mockito.mockStatic(DriverManager.class);

		//WHEN
		driverManagerMockedStatic.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString())).thenThrow(SQLException.class);

		//THEN
		assertFalse(ticketDAO.vehicleExistInDatabase("ABCDE"));
		driverManagerMockedStatic.close();
	}

}
