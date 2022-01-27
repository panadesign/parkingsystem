package com.parkit.parkingsystem.dao;

import org.junit.jupiter.api.Test;
import java.sql.SQLException;


public class TicketDAOTest {

    @Test
    public void getTicketTest() throws SQLException, ClassNotFoundException {
        TicketDAO ticketDAO = new TicketDAO();
        ticketDAO.getTicket("ABCDE");
    }

}
