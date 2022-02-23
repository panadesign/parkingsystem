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
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

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
        //FareCalculatorService fareCalculatorService = new FareCalculatorService();
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
    public void testParkingACar() throws Exception {
        //GIVEN
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        parkingService.processIncomingVehicle();

        //WHEN
        Ticket ticket= new Ticket();
        Date inTime = new Date();
        ticket.setParkingSpot(new ParkingSpot(1, ParkingType.CAR, false));
        ticket.setId(1);
        ticket.setInTime(inTime);
        ticket.setOutTime(null);
        ticket.setVehicleRegNumber("PLAQUE1");

        dataBaseTestConfig.getConnection();
        inputReaderUtil.readVehicleRegistrationNumber();
        ticketDAO.saveTicket(ticket);
        Ticket ticketSave = ticketDAO.getTicket("PLAQUE1");

        //THEN
        assertNotNull(ticketSave);
    }

    @Test
    public void testParkingSpotIsUpdatedWhileParkingACar() throws Exception {
        //GIVEN
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        parkingService.processIncomingVehicle();

        //WHEN
        Ticket ticket= new Ticket();
        Date inTime = new Date();
        ticket.setParkingSpot(new ParkingSpot(1, ParkingType.CAR, false));
        ticket.setId(1);
        ticket.setInTime(inTime);
        ticket.setOutTime(null);
        ticket.setVehicleRegNumber("PLAQUE1");

        dataBaseTestConfig.getConnection();
        inputReaderUtil.readVehicleRegistrationNumber();
        ticketDAO.saveTicket(ticket);
        Ticket ticketSave = ticketDAO.getTicket("PLAQUE1");


        //THEN
        assertTrue(parkingSpotDAO.updateParking(new ParkingSpot(1 , ParkingType.CAR, true)), String.valueOf(true));
    }

    @Test
    public void testFareIsGeneratedWhenCarExiting() {
        //TODO: check that the fare generated and out time are populated correctly in the database
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        parkingService.processIncomingVehicle();
        parkingService.processExitingVehicle();





    }

}
