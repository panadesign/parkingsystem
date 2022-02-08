package com.parkit.parkingsystem.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@ExtendWith(MockitoExtension.class)
public class InputReaderUtilTest {

	private InputReaderUtil inputReaderUtil;

	@Test
	public void readSelectionTest() {
		Integer expectedResponse = 1;
		InputStream input = new ByteArrayInputStream(expectedResponse.toString().getBytes());
		inputReaderUtil = new InputReaderUtil(input);

		Integer actualResponse = inputReaderUtil.readSelection();
		Assertions.assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void readVehicleRegistrationNumberTest() throws Exception {
		//GIVEN
		String expectedResponse = "ABCDE";
		InputStream input = new ByteArrayInputStream(expectedResponse.getBytes());
		inputReaderUtil = new InputReaderUtil(input);

		//WHEN
		String actualResponse = inputReaderUtil.readVehicleRegistrationNumber();

		//THEN
		Assertions.assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void readVehicleRegistrationNumberExceptionTest() throws Exception {

		String expectedResponse = "";
		InputStream input = new ByteArrayInputStream(expectedResponse.getBytes());
		inputReaderUtil = new InputReaderUtil(input);

		Assertions.assertThrows(Exception.class, () -> inputReaderUtil.readVehicleRegistrationNumber());
	}

}
