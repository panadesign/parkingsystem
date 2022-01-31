package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.ParkingType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.sql.*;
import static org.mockito.Mockito.when;

public class ParkingSpotDAOTest {
    @Mock
    DataBaseConfig dataBaseConfig;
    @Mock
    PreparedStatement preparedStatement;
    @Mock
    ResultSet rs;
    @Mock
    ParkingType parkingType;

    private ParkingSpotDAO parkingSpotDAO = new ParkingSpotDAO();

    @Test
    public void getNextAvailableSlotException() throws SQLException, ClassNotFoundException {
        // GIVEN
        preparedStatement.setString(1, parkingType.toString());
        parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE);
        dataBaseConfig.getConnection();

        // WHEN
        when(rs.getInt(-1)).thenThrow(Exception.class);

        // THEN
        //Assertions.assertThrows()

    }

}
