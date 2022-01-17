package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.InputReaderUtil;

public class FareCalculatorService {
	static final int THIRTY_MINUTES_IN_MILLIS = 1800000;
	InputReaderUtil inputReaderUtil = new InputReaderUtil();
	ParkingSpotDAO parkingSpotDAO = new ParkingSpotDAO();
	TicketDAO ticketDAO = new TicketDAO();
	ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

	public void calculateFare(Ticket ticket) {
		if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
		}

		long inMillis = ticket.getInTime().getTime();
		long outMillis = ticket.getOutTime().getTime();

		double durationInMillis = outMillis - inMillis;

		double fare = 0;
		boolean duration30MinOrLess = durationInMillis <= THIRTY_MINUTES_IN_MILLIS;
		boolean vehicleExists = parkingService.verifyVehicleIsAlreadyInDatabase(ticket.getVehicleRegNumber());

		switch (ticket.getParkingSpot().getParkingType()) {
			case CAR: {
				if (duration30MinOrLess) {
					fare = Fare.CAR_PRICE_30MIN_OR_LESS;
				} else if (vehicleExists) {
					fare = Fare.CAR_FARE_PER_HOUR_DISCOUNT_5_PERCENT;
				} else {
					fare = Fare.CAR_RATE_PER_HOUR;
				}
				break;
			}
			case BIKE: {
				if (duration30MinOrLess) {
					fare = Fare.BIKE_PRICE_30MIN_OR_LESS;
				} else if (vehicleExists) {
					fare = Fare.BIKE_FARE_PER_HOUR_DISCOUNT_5_PERCENT;
				} else {
					fare = Fare.BIKE_RATE_PER_HOUR;
				}
				break;
			}
			default:
				throw new IllegalArgumentException("Unknown Parking Type");
		}

		ticket.setPrice(durationInMillis / 60 / 60 / 1000 * fare);

	}
}