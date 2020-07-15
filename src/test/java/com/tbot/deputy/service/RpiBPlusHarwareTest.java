package com.tbot.deputy.service;

import static org.junit.Assert.*;

import org.junit.Test;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

public class RpiBPlusHarwareTest {

	@Test
	public void testParsePinout() {
		RpiBPlusHardware hardware = new RpiBPlusHardware();
		Pin[] pins = hardware.parsePinout("2, 5,0,   12");
		assertEquals(4, pins.length);
		assertEquals(RaspiPin.GPIO_02, pins[0]);
		assertEquals(RaspiPin.GPIO_05, pins[1]);
		assertEquals(RaspiPin.GPIO_00, pins[2]);
		assertEquals(RaspiPin.GPIO_12, pins[3]);

		pins = hardware.parsePinout("1");
		assertEquals(1, pins.length);
		assertEquals(RaspiPin.GPIO_01, pins[0]);
	}
}
