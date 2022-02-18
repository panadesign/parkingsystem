package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class FareCalculatorServiceTest {

	private static FareCalculatorService fareCalculatorService;


	private static Ticket ticket;

	@BeforeAll
	private static void setUp() {
		fareCalculatorService = new FareCalculatorService();
	}


	@BeforeEach
	private void setUpPerTest() {
		ticket = new Ticket();
	}

	@Test
	public void calculateFareCar() {
		//GIVEN
		Date inTime = new Date();
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		//WHEN
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		boolean isDiscounted = false;
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket, isDiscounted);

		//THEN
		assertEquals(Fare.CAR_RATE_PER_HOUR, ticket.getPrice());
	}

	@Test
	public void calculateFareBike() {
		//GIVEN
		Date inTime = new Date();
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		//WHEN
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		boolean isDiscounted = false;
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket, isDiscounted);

		//THEN
		assertEquals(Fare.BIKE_RATE_PER_HOUR, ticket.getPrice());
	}

	@Test
	public void calculateFareUnknownType() {
		//GIVEN
		Date inTime = new Date();
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, null, false);

		//WHEN
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		boolean isDiscounted = false;
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);

		//THEN
		assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket, isDiscounted));
	}

	@Test
	public void calculateFareBikeWithFutureInTime() {
		//GIVEN
		Date inTime = new Date();
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		//WHEN
		inTime.setTime(System.currentTimeMillis() + (60 * 60 * 1000));
		boolean isDiscounted = false;
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);

		//THEN
		assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket, isDiscounted));
	}

	@Test
	public void calculateFareBikeWithLessThanOneHourParkingTime() {
		//GIVEN
		Date inTime = new Date();
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		//WHEN
		inTime.setTime(System.currentTimeMillis() - (45 * 60 * 1000));//45 minutes parking time should give 3/4th parking fare
		boolean isDiscounted = false;
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket, isDiscounted);

		//THEN
		assertEquals((0.75 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice());
	}

	@Test
	public void calculateFareCarWithLessThanOneHourParkingTime() {
		//GIVEN
		Date inTime = new Date();
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		//WHEN
		inTime.setTime(System.currentTimeMillis() - (45 * 60 * 1000));//45 minutes parking time should give 3/4th parking fare
		boolean isDiscounted = false;
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket, isDiscounted);

		//THEN
		assertEquals((0.75 * Fare.CAR_RATE_PER_HOUR), ticket.getPrice());
	}

	@Test
	public void calculateFareCarWithMoreThanADayParkingTime() {
		//GIVEN
		Date inTime = new Date();
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		//WHEN
		inTime.setTime(System.currentTimeMillis() - (24 * 60 * 60 * 1000));//24 hours parking time should give 24 * parking fare per hour
		boolean isDiscounted = false;
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket, isDiscounted);

		//THEN
		assertEquals((24 * Fare.CAR_RATE_PER_HOUR), ticket.getPrice());
	}

	@Test
	public void calculateFareCarWithDurationLessOrEqualTo30Min() {
		//GIVEN
		Date inTime = new Date();
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		//WHEN
		inTime.setTime(System.currentTimeMillis() - (30 * 60 * 1000)); //30min parking Time
		boolean isDiscounted = false;
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket, isDiscounted);

		//THEN
		assertEquals((0.5 * Fare.CAR_PRICE_30MIN_OR_LESS), ticket.getPrice());
	}

	@Test
	public void calculateFareBikeWithDurationLessOrEqualTo30Min() {
		//GIVEN
		Date inTime = new Date();
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		//WHEN
		inTime.setTime(System.currentTimeMillis() - (30 * 60 * 1000)); //30min parking Time
		boolean isDiscounted = false;
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket, isDiscounted);

		//THEN
		assertEquals((0.5 * Fare.BIKE_PRICE_30MIN_OR_LESS), ticket.getPrice());
	}

	@Test
	public void calculateFareCarWithDiscount() {
		//GIVEN
		Date inTime = new Date();
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		//WHEN
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		boolean isDiscounted = true;
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket, isDiscounted);

		//THEN
		assertEquals((Fare.CAR_FARE_PER_HOUR_DISCOUNT_5_PERCENT), ticket.getPrice());
	}

	@Test
	public void calculateFareBikeWithDiscount() {
		//GIVEN
		Date inTime = new Date();
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		//WHEN
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		boolean isDiscounted = true;
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		fareCalculatorService.calculateFare(ticket, isDiscounted);

		//THEN
		assertEquals((Fare.BIKE_FARE_PER_HOUR_DISCOUNT_5_PERCENT), ticket.getPrice());
	}

}
