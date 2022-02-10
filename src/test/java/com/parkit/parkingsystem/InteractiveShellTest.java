package com.parkit.parkingsystem;

import com.parkit.parkingsystem.service.InteractiveShell;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileNotFoundException;

@ExtendWith(MockitoExtension.class)
public class InteractiveShellTest {
	@Test
	public void loadInterfaceShutdownSystemTest() throws FileNotFoundException {


	}

	@Test
	public void loadMenuTest() {
		InteractiveShell.loadMenu();
	}
}