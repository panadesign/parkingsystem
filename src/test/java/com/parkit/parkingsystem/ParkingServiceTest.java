package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static com.parkit.parkingsystem.constants.ParkingType.CAR;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {

	@Mock
	private static InputReaderUtil inputReaderUtil;
	@Mock
	private static ParkingSpotDAO parkingSpotDAO;
	@Mock
	private static TicketDAO ticketDAO;
	private static ParkingService parkingService;

	@BeforeEach
	private void setup() {
		parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
	}

	private void setUpExitingProcess() {
		try {
			when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");

			ParkingSpot parkingSpot = new ParkingSpot(1, CAR, false);
			Ticket ticket = new Ticket();
			ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
			ticket.setParkingSpot(parkingSpot);
			ticket.setVehicleRegNumber("ABCDEF");
			when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
			when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);
			when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to set up test mock objects");
		}
	}

	@Test
	public void processExitingVehicleTest() {
		setUpExitingProcess();
		parkingService.processExitingVehicle();
		verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
	}

	@Test
	public void updateTicketFailExitingVehicle() throws Exception {
		//GIVEN
		ParkingSpot parkingSpot = new ParkingSpot(1, CAR, false);
		Ticket ticket = new Ticket();

		//WHEN
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
		ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
		ticket.setParkingSpot(parkingSpot);
		ticket.setVehicleRegNumber("ABCDEF");
		when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
		when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(false);

		//THEN
		parkingService.processExitingVehicle();
	}

	@Test
	public void processIncomingBikeWithAvailableSpotTest() throws Exception {
		// GIVEN
		int availableSpot = 4;
		String vehicleRegNumber = "XXXXX";

		// WHEN
		when(inputReaderUtil.readSelection()).thenReturn(2);
		when(parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE)).thenReturn(availableSpot);
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn(vehicleRegNumber);

		// THEN
		parkingService.processIncomingVehicle();
		verify(inputReaderUtil, Mockito.times(1)).readVehicleRegistrationNumber();
		verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
		verify(ticketDAO, Mockito.times(1)).vehicleExistInDatabase(vehicleRegNumber);
	}

	@Test
	public void processIncomingBikeFail() throws Exception {
		// GIVEN
		int availableSpot = 4;
		String vehicleRegNumber = "";

		// WHEN
		when(inputReaderUtil.readSelection()).thenReturn(2);
		when(parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE)).thenReturn(availableSpot);
		when(ticketDAO.vehicleExistInDatabase("XXXXX")).thenReturn(false);
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn(vehicleRegNumber);

		// THEN
		parkingService.processIncomingVehicle();
		verify(inputReaderUtil, Mockito.times(1)).readVehicleRegistrationNumber();
		verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
		verify(ticketDAO, Mockito.times(1)).vehicleExistInDatabase(vehicleRegNumber);
	}

	@Test
	public void processIncomingBikeWithDiscount() throws Exception {
		// GIVEN
		int availableSpot = 4;
		String vehicleRegNumber = "XXXXX";

		// WHEN
		when(inputReaderUtil.readSelection()).thenReturn(2);
		when(parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE)).thenReturn(availableSpot);
		when(ticketDAO.vehicleExistInDatabase("XXXXX")).thenReturn(true);
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn(vehicleRegNumber);

		// THEN
		parkingService.processIncomingVehicle();
		verify(inputReaderUtil, Mockito.times(1)).readVehicleRegistrationNumber();
		verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
		verify(ticketDAO, Mockito.times(1)).vehicleExistInDatabase(vehicleRegNumber);
	}

	@Test
	public void processIncomingBikeWithoutAvailableSpotTest() throws Exception {
		//GIVEN
		int unavailableSpot = -1;

		//WHEN
		when(inputReaderUtil.readSelection()).thenReturn(2);
		when(parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE)).thenReturn(unavailableSpot);

		//THEN
		parkingService.processIncomingVehicle();
		verify(inputReaderUtil, Mockito.times(0)).readVehicleRegistrationNumber();

	}

	@Test
	public void processIncomingBusFail() {
		when(inputReaderUtil.readSelection()).thenReturn(3);
		parkingService.processIncomingVehicle();
	}

	@Test
	public void processExitingVehicleException() throws Exception {
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenThrow(Exception.class);
		parkingService.processExitingVehicle();
	}
}