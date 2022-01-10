package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

	public void calculateFare(Ticket ticket) {
		if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
		}

		long inMillis = ticket.getInTime().getTime();
		long outMillis = ticket.getOutTime().getTime();

		float duration = outMillis - inMillis;

		switch (ticket.getParkingSpot().getParkingType()) {
			case CAR: {
				if (duration <= 1800000) {
					ticket.setPrice(duration / 60 / 60 / 1000 * Fare.CAR_PRICE_30MIN_OR_LESS);
				} else {
					ticket.setPrice(duration / 60 / 60 / 1000 * Fare.CAR_RATE_PER_HOUR);
				}
				break;
			}
			case BIKE: {
				if (duration <= 1800000) {
					ticket.setPrice(duration / 60 / 60 / 1000 * Fare.BIKE_PRICE_30MIN_OR_LESS);
				} else {
					ticket.setPrice(duration / 60 / 60 / 1000 * Fare.BIKE_RATE_PER_HOUR);
				}
				break;
			}
			default:
				throw new IllegalArgumentException("Unkown Parking Type");
		}
	}
}