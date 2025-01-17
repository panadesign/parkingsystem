package com.parkit.parkingsystem.integration;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

	private static final DataBaseConfig dataBaseTestConfig = new DataBaseTestConfig();
	private static ParkingSpotDAO parkingSpotDAO;
	private static TicketDAO ticketDAO;
	private static DataBasePrepareService dataBasePrepareService;


	@Mock
	private static InputReaderUtil inputReaderUtil;

	@BeforeAll
	private static void setUp() {
		parkingSpotDAO = new ParkingSpotDAO();
		parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
		ticketDAO = new TicketDAO();
		ticketDAO.dataBaseConfig = dataBaseTestConfig;
		dataBasePrepareService = new DataBasePrepareService();
	}

	@BeforeEach
	private void setUpPerTest() throws Exception {
		when(inputReaderUtil.readSelection()).thenReturn(1);
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
		dataBasePrepareService.clearDataBaseEntries();
	}

	@AfterAll
	private static void tearDown() {
	}

	@Test
	public void testParkingACar() {
		//GIVEN
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		int initialAvailableSpot = parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR);
		parkingService.processIncomingVehicle();

		//WHEN
		Ticket ticketSave = ticketDAO.getTicket("ABCDEF");
		int currentAvailableSpot = parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR);

		//THEN
		assertNotEquals(currentAvailableSpot, initialAvailableSpot);
		assertNotNull(ticketSave);
	}

	@Test
	public void testParkingLotExit() throws Exception {
		//GIVEN
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		parkingService.processIncomingVehicle();
		Ticket tempTicket = ticketDAO.getTicket("ABCDEF");
		Thread.sleep(1000);
		assertNull(tempTicket.getOutTime());
		assertEquals(tempTicket.getPrice(), 0);

		//WHEN
		parkingService.processExitingVehicle();
		Ticket ticketSave = ticketDAO.getTicket("ABCDEF");

		//THEN
		assertNotNull(ticketSave.getOutTime());
		assertNotNull(ticketSave.getPrice());

	}

}
